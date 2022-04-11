<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarunidadesmedida.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{				
				abrirVentanaModal('<html:rewrite action="/unidadesmedida/crearUnidadMedida"/>', "UnidadMedidaAdd", 750, 370);
			}
	
			function modificar() 
			{
				if ((document.gestionarUnidadesMedidaForm.seleccionados.value == null) || (document.gestionarUnidadesMedidaForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var unidadId = document.gestionarUnidadesMedidaForm.seleccionados.value;
				var nombre = document.gestionarUnidadesMedidaForm.valoresSeleccionados.value;
				if (nombre == "%") 
					alert('<vgcutil:message key="jsp.gestionarunidadesmedida.eliminarunidadmedida.modificar.porcentaje" />');	
				else 					
					abrirVentanaModal('<html:rewrite action="/unidadesmedida/modificarUnidadMedida"/>?unidadId=' + unidadId , "UnidadMedidaEdit", 750, 370);
			}
	
			function eliminar() {
				if ((document.gestionarUnidadesMedidaForm.seleccionados.value == null) || (document.gestionarUnidadesMedidaForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var unidadId = document.gestionarUnidadesMedidaForm.seleccionados.value;
				var nombre = document.gestionarUnidadesMedidaForm.valoresSeleccionados.value;				
				if (nombre == "%") {
					alert('<vgcutil:message key="jsp.gestionarunidadesmedida.eliminarunidadmedida.eliminar.porcentaje" />');	
				} else {
					var eliminar = confirm('<vgcutil:message key="jsp.gestionarunidadesmedida.eliminarunidadmedida.confirmar" />');
					if (eliminar) {
						window.location.href='<html:rewrite action="/unidadesmedida/eliminarUnidadMedida"/>?unidadId=' + unidadId + '&ts=<%= (new Date()).getTime() %>';
					}
				}
			}
	
			function reporte() {			
				abrirReporte('<html:rewrite action="/unidadesmedida/generarReporteUnidadesMedida.action"/>?atributoOrden=' + gestionarUnidadesMedidaForm.atributoOrden.value + '&tipoOrden=' + gestionarUnidadesMedidaForm.tipoOrden.value, 'reporte');
			}
	
			function limpiarFiltros() {
			  gestionarUnidadesMedidaForm.filtroNombre.value = "";
			  gestionarUnidadesMedidaForm.submit();
			}
	
			function buscar() {
				gestionarUnidadesMedidaForm.submit();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/unidadesmedida/gestionarUnidadesMedida" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-unidades">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarunidadesmedida.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/unidadesmedida/gestionarUnidadesMedida' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>

					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="UNIDAD_PRINT" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="UNIDAD_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="UNIDAD_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="UNIDAD_DELETE" />
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
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarunidadesmedida.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarUnidadesMedida">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.eliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaUnidades" width="100%" messageKeyNoElementos="jsp.gestionarunidadesmedida.noregistros" nombre="visorUnidades" seleccionSimple="true" nombreForma="gestionarUnidadesMedidaForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="90%" onclick="javascript:consultar(document.gestionarUnidadesMedidaForm,'nombre', null)">
						<vgcutil:message key="jsp.gestionarunidadesmedida.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="tipo" onclick="javascript:consultar(document.gestionarUnidadesMedidaForm,'tipo', null)">
						<vgcutil:message key="jsp.gestionarunidadesmedida.columna.tipo" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="unidadMedida">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="unidadMedida" property="unidadId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="unidadMedida" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo">
							<%-- Si --%>
							<logic:equal name="unidadMedida" property="tipo" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="unidadMedida" property="tipo" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaUnidades" labelPage="inPagina" action="javascript:consultar(gestionarUnidadesMedidaForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEmpty name="gestionarUnidadesMedidaForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaUnidades" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-unidades'), 250);	

			var visor = document.getElementById('visorUnidades');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - (_anchoAreBar + 20)) + "px";
		</script>
	</tiles:put>

</tiles:insert>
