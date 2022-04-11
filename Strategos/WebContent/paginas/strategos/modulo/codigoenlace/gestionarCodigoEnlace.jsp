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
		<vgcutil:message key="jsp.gestionarcodigoenlace.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			function nuevo() 
			{
				abrirVentanaModal('<html:rewrite action="/codigoenlace/crearCodigoEnlace" />', "CodigoEnlace", 460, 210);
			}

			function modificar() 
			{
				if ((document.gestionarCodigoEnlaceForm.seleccionados.value == null) || (document.gestionarCodigoEnlaceForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var id = document.gestionarCodigoEnlaceForm.seleccionados.value;
				abrirVentanaModal('<html:rewrite action="/codigoenlace/modificarCodigoEnlace" />?id=' + id, "CodigoEnlace", 460, 210);
			}

			function eliminar() 
			{
				if ((document.gestionarCodigoEnlaceForm.seleccionados.value == null) || (document.gestionarCodigoEnlaceForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				
				var id = document.gestionarCodigoEnlaceForm.seleccionados.value;
				respuesta = confirm ('<vgcutil:message key="jsp.gestionarcodigoenlace.eliminar.confirmar" />');
				if (respuesta) 
					window.location.href='<html:rewrite action="/codigoenlace/eliminarCodigoEnlace"/>?id=' + id;
			}

			function reporte()
			{
				var url = '';
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					url = url + '&filtroNombre=' + filtroNombre.value;
				
				abrirReporte('<html:rewrite action="/codigoenlace/generarReporteCodigoEnlace.action"/>?atributoOrden=' + gestionarCodigoEnlaceForm.atributoOrden.value + '&tipoOrden=' + gestionarCodigoEnlaceForm.tipoOrden.value + url, 'reporte');
			}
			
			function actualizar()
			{
				window.location.href='<html:rewrite action="/codigoenlace/gestionarCodigoEnlace"/>';
			}
			
			function limpiarFiltros() 
			{
				gestionarCodigoEnlaceForm.filtroNombre.value = "";
				buscar();
			}
	
			function buscar() 
			{
				gestionarCodigoEnlaceForm.submit();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/codigoenlace/gestionarCodigoEnlace" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-codigoenlace">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarcodigoenlace.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
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
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="CODIGO_ENLACE_PRINT" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="CODIGO_ENLACE_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="CODIGO_ENLACE_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="CODIGO_ENLACE_DELETE" />
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

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarCodigoEnlace">
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
					
					<%-- Filtros --%>
					<table class="tablaSpacing0Padding0Width100">
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarcodigoenlace.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px">
								<img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />">
								<img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaCodigoEnlace" width="100%" messageKeyNoElementos="jsp.gestionarcodigoenlace.noregistros" nombre="visorCodigoEnlace" seleccionSimple="true" nombreForma="gestionarCodigoEnlaceForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="codigo" width="30%" onclick="javascript:consultar(document.gestionarCodigoEnlaceForm,'codigo', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.codigo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="nombre" onclick="javascript:consultar(document.gestionarCodigoEnlaceForm,'nombre', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="bi" onclick="javascript:consultar(document.gestionarCodigoEnlaceForm,'bi', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.bi" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="categoria" onclick="javascript:consultar(document.gestionarCodigoEnlaceForm,'categoria', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.categoria" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="codigoEnlace">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="codigoEnlace" property="id" />
						</vgcinterfaz:visorListaFilaId>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="codigo" esValorSelector="true">
							<bean:write name="codigoEnlace" property="codigo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="codigoEnlace" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="bi" esValorSelector="true">
							<bean:write name="codigoEnlace" property="bi" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="categoria" esValorSelector="true">
							<bean:write name="codigoEnlace" property="categoria" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaCodigoEnlace" labelPage="inPagina" action="javascript:consultar(gestionarCodigoEnlaceForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEmpty name="gestionarCodigoEnlaceForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaCodigoEnlace" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-codigoenlace'), 250);	

			var visor = document.getElementById('visorCodigoEnlace');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - (_anchoAreBar + 20)) + "px";
		</script>
	</tiles:put>

</tiles:insert>
