<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionar.modelos.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			function buscar() 
			{
				if (validar())
					refrescar();
			}
			
			function validar()
			{
				return true;
			}

			function limpiarBusqueda() 
			{
				document.gestionarModelosForm.filtroNombre.value = '';
				buscar();
			}
			
			function actualizar() 
			{
				document.gestionarModelosForm.submit();
			}
			
			function reporte() 
			{
				abrirReporte('<html:rewrite action="/planes/modelos/imprimirModelos"/>?planId=<bean:write name="gestionarModelosForm" property="planId" />&filtroNombre=' + document.gestionarModelosForm.filtroNombre.value, 'reporte');
			}
			
			function refrescar()
			{
				document.gestionarModelosForm.action = '<html:rewrite action="/planes/modelos/gestionarModelos"/>?planId=<bean:write name="gestionarPlanForm" property="planId" />';
				document.gestionarModelosForm.submit();
			}
			
			function regresar()
			{
				window.location.href='<html:rewrite action="/planes/gestionarPlan" />?planId=<bean:write name="gestionarModelosForm" property="planId" />';
			}

			function nuevo()
			{
				window.location.href='<html:rewrite action="/planes/modelos/editarModelo" />?planId=<bean:write name="gestionarModelosForm" property="planId" />&editar=1&modeloId=0';				
			}

			function modificar()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarModelosForm.seleccionados))
					window.location.href='<html:rewrite action="/planes/modelos/editarModelo" />?planId=<bean:write name="gestionarModelosForm" property="planId" />&editar=1&modeloId=' + document.gestionarModelosForm.seleccionados.value;
			}

			function eliminar()
			{
				if ((document.gestionarModelosForm.seleccionados.value == null) || (document.gestionarModelosForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/modelos/eliminarModelo" />?planId=<bean:write name="gestionarModelosForm" property="planId" />&Id=' + document.gestionarModelosForm.seleccionados.value, document.gestionarModelosForm.respuesta, 'onEliminar()');
			}

			function onEliminar()
			{
				var respuesta = document.gestionarModelosForm.respuesta.value;
				document.gestionarModelosForm.respuesta.value = "";
				if (respuesta == 10000)
				{
					alert('<vgcutil:message key="jsp.eliminarregistro.eliminacionok" />');
					refrescar();
				}
				else if (respuesta == 10004)
					alert('<vgcutil:message key="jsp.eliminarregistro.relacion" />');
				else  if (respuesta == 9999)
					alert('<vgcutil:message key="jsp.eliminarregistro.noencontrado" />');
			}
			
			function editarModeloCausaEfecto()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarModelosForm.seleccionados))
					window.location.href='<html:rewrite action="/planes/modelos/editarModelo" />?planId=<bean:write name="gestionarModelosForm" property="planId" />&editar=0&modeloId=' + document.gestionarModelosForm.seleccionados.value;	
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<html:form action="/planes/modelos/gestionarModelos" styleClass="formaHtmlGestionar">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="respuesta" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-modelos">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionar.modelos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
		
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:refrescar()
				</vgcinterfaz:contenedorFormaBotonActualizar>
		
				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:regresar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>
					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="PLAN_MODELO_DISENO_PRINT" aplicaOrganizacion="true" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Edicion --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="PLAN_MODELO_DISENO_ADD" aplicaOrganizacion="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="PLAN_MODELO_DISENO_EDIT" aplicaOrganizacion="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="PLAN_MODELO_DISENO_DELETE" aplicaOrganizacion="true" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">
								<vgcinterfaz:botonMenu key="menu.ver.modelo.causaefecto" onclick="editarModeloCausaEfecto(0)" permisoId="PLAN_MODELO_VER" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" agregarSeparador="true"/>
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								--%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0" border="0">
						<tr class="barraFiltrosForma">
							<td width="115px"><vgcutil:message key="jsp.gestionar.modelo.filtro.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarBusqueda()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaModelos" nombre="visorModelos" messageKeyNoElementos="jsp.gestionar.modelo.nomodelos" seleccionSimple="true" nombreForma="gestionarModelosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="400px" onclick="javascript:consultar(self.document.gestionarModelosForm,'nombre', null)">
						<vgcutil:message key="jsp.gestionar.modelos.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="720px">
						<vgcutil:message key="jsp.gestionar.modelos.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="modelo">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="modelo" property="pk.modeloId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" align="left">
							<bean:write name="modelo" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>					
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion" align="left">
							<bean:write name="modelo" property="descripcion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>					
					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaModelos" labelPage="inPagina" action="javascript:consultar(gestionarModelosForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarModelosForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarModelosForm" property="atributoOrden" />  [<bean:write name="gestionarModelosForm" property="tipoOrden" />]
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-modelos'), 225);	

			var visor = document.getElementById('visorModelos');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>
	</tiles:put>

</tiles:insert>
