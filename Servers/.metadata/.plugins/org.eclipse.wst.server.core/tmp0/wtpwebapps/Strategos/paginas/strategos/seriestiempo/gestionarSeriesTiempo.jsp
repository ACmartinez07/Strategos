<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarseriestiempo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var serieReal = '<bean:write name="gestionarSeriesTiempoForm" property="serieReal.identificador" />';
			var serieProgramado = '<bean:write name="gestionarSeriesTiempoForm" property="serieProgramado.identificador" />';
			var serieMinimo = '<bean:write name="gestionarSeriesTiempoForm" property="serieMinimo.identificador" />';
			var serieMaximo = '<bean:write name="gestionarSeriesTiempoForm" property="serieMaximo.identificador" />';
			
			function nuevo() 
			{
				window.location.href='<html:rewrite action="/seriestiempo/crearSerieTiempo" />';
			}
	
			function modificar() 
			{
				if ((document.gestionarSeriesTiempoForm.seleccionados.value == null) || (document.gestionarSeriesTiempoForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var serieId = document.gestionarSeriesTiempoForm.seleccionados.value;
				var identificador = document.gestionarSeriesTiempoForm.valoresSeleccionados.value.toUpperCase();
				if ((identificador == serieReal.toUpperCase()) || (identificador == serieProgramado.toUpperCase()) || (identificador == serieMinimo.toUpperCase()) || (identificador == serieMaximo.toUpperCase())) 
					alert('<vgcutil:message key="jsp.gestionarseriestiempo.modificar.serie" arg0="' + identificador + '" />');	
				else 
					window.location.href='<html:rewrite action="/seriestiempo/modificarSerieTiempo" />?serieId=' + serieId;
			}
	
			function eliminar() 
			{
				if ((document.gestionarSeriesTiempoForm.seleccionados.value == null) || (document.gestionarSeriesTiempoForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var serieId = document.gestionarSeriesTiempoForm.seleccionados.value;
				var identificador = document.gestionarSeriesTiempoForm.valoresSeleccionados.value.toUpperCase();
				if ((identificador == serieReal.toUpperCase()) || (identificador == serieProgramado.toUpperCase()) || (identificador == serieMinimo.toUpperCase()) || (identificador == serieMaximo.toUpperCase())) 
					alert('<vgcutil:message key="jsp.gestionarseriestiempo.eliminar.serie" arg0="' + identificador + '" />');	
				else 
				{
					var eliminar = confirm('<vgcutil:message key="jsp.gestionarseriestiempo.eliminarserietiempo.confirmar" />');
					if (eliminar) 
						window.location.href='<html:rewrite action="/seriestiempo/eliminarSerieTiempo"/>?serieId=' + serieId + '&ts=<%= (new Date()).getTime() %>';
				}
			}
	
			function reporte() 
			{
				abrirReporte('<html:rewrite action="/seriestiempo/generarReporteSeriesTiempo.action"/>?atributoOrden=' + gestionarSeriesTiempoForm.atributoOrden.value + '&tipoOrden=' + gestionarSeriesTiempoForm.tipoOrden.value, 'reporte');
			}
	
			function limpiarFiltros() 
			{
			  	gestionarSeriesTiempoForm.filtroNombre.value = "";
			  	gestionarSeriesTiempoForm.submit();
			}
	
			function buscar() 
			{
				gestionarSeriesTiempoForm.submit();
			}
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/seriestiempo/gestionarSeriesTiempo" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-series">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarseriestiempo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/seriestiempo/gestionarSeriesTiempo' />
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
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="SERIE_TIEMPO_PRINT" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="SERIE_TIEMPO_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="SERIE_TIEMPO_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="SERIE_TIEMPO_DELETE" />
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
							<td width="10px"><vgcutil:message key="jsp.gestionarseriestiempo.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" maxlength="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarSeriesTiempo">

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

				<%-- Visor Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaSeriesTiempo" nombre="tablaSeriesTiempo" seleccionSimple="true" nombreForma="gestionarSeriesTiempoForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" messageKeyNoElementos="jsp.gestionarseriestiempo.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

					<%-- Encabezados --%>
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%" onclick="javascript:consultar(self.document.gestionarSeriesTiempoForm,'nombre', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="identificador" onclick="javascript:consultar(self.document.gestionarSeriesTiempoForm,'identificador', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.identificador" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="fija" onclick="javascript:consultar(self.document.gestionarSeriesTiempoForm,'fija', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.fija" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="preseleccionada" onclick="javascript:consultar(self.document.gestionarSeriesTiempoForm,'preseleccionada', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.preseleccionada" />
					</vgcinterfaz:columnaVisorLista>

					<%-- Filas --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="serieTiempo">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="serieTiempo" property="serieId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="serieTiempo" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="identificador" esValorSelector="true">
							<bean:write name="serieTiempo" property="identificador" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fija">
							<%-- Si --%>
							<logic:equal name="serieTiempo" property="fija" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="serieTiempo" property="fija" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="preseleccionada">
							<%-- Si --%>
							<logic:equal name="serieTiempo" property="preseleccionada" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="serieTiempo" property="preseleccionada" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaSeriesTiempo" labelPage="inPagina" action="javascript:consultar(gestionarSeriesTiempoForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaSeriesTiempo" property="infoOrden" />
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-series'), 255);	

			var visor = document.getElementById('tablaSeriesTiempo');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
