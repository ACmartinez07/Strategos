<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (03/08/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.reportesgrafico.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			var reportes = new Array(<bean:write name="paginaVistas" property="total"/>);
			<logic:iterate id="reporteGrafico" name="paginaVistas" property="lista" indexId="indexReportes">
				reportes[<bean:write name="indexReportes"/>] = new Array(2);
				reportes[<bean:write name="indexReportes"/>][0] = '<bean:write name="reporteGrafico" property="reporteId"/>';
			</logic:iterate>

			function nuevoReporte(){
				window.location.href='<html:rewrite action="/reportesgrafico/configurarReporteGrafico" />';
			}
			
			function modificarVista() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarReporteGraficoForm.seleccionados))
				{
					for (var i = 0; i < reportes.length; i++) 
					{
						if (reportes[i][0] == document.gestionarReporteGraficoForm.seleccionados.value) 
						{
							
							<logic:equal name="gestionarReporteGraficoForm" property="editarForma" value="true">
								window.location.href='<html:rewrite action="/reportesgrafico/modificarReporteGrafico" />?reporteId=' + document.gestionarReporteGraficoForm.seleccionados.value;
							</logic:equal>
																					
						}
					}
				}
			}		
			
			function eliminarVista() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarReporteGraficoForm.seleccionados))
				{
					var eliminar = confirm('<vgcutil:message key="jsp.gestionarvistasdatos.eliminarvista.confirmar" />');
					if (eliminar)
					{
						activarBloqueoEspera();
						window.location.href='<html:rewrite action="/reportesgrafico/eliminarReporteGrafico"/>?reporteId=' + document.gestionarReporteGraficoForm.seleccionados.value + '&ts=<%= (new Date()).getTime() %>';
					}
				}
			}
			
			function graficarReporte()
		    {
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarReporteGraficoForm.seleccionados))
				{
		    		abrirVentanaModal('<html:rewrite action="/reportesgrafico/graficarReporte" />?reporteId=' + document.gestionarReporteGraficoForm.seleccionados.value ,'graficarReporte', '1100', '750');
		    
				}
		    }
		</script>
		
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Representación de la Forma --%>
		<html:form action="/reportesgrafico/gestionarReporteGrafico" styleClass="formaHtmlGestionar">
		
			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrdenVistas" />
			<html:hidden property="tipoOrdenVistas" />
			<html:hidden property="seleccionados" />
			
			<input type="hidden" name="corte">
		
			<vgcinterfaz:contenedorForma idContenedor="body-reporte">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.reportesgrafico.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
		
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/reportesgrafico/gestionarReporteGrafico' />
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
							<vgcinterfaz:menuBotones id="menuArchivoVista" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteVista();" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicionVista" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevaVista();" permisoId="VISTA_DATOS_ADD" />
								<logic:equal name="gestionarReporteGraficoForm" property="editarForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" />
								</logic:equal>
								<logic:notEqual name="gestionarReporteGraficoForm" property="editarForma" value="true">
									<logic:equal name="gestionarReporteGraficoForm" property="verForma" value="true">
										<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" />
									</logic:equal>
								</logic:notEqual>
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarPagina();" permisoId="VISTA_DATOS_DELETE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar.vista" onclick="mostrar();" permisoId="VISTA_DATOS_REPORTE_VIEW" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>
				
				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
		
					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarPaginas">
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoVista" onclick="javascript:nuevoReporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="gestionarReporteGraficoForm" property="editarForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarVista" onclick="javascript:modificarVista();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:notEqual name="gestionarReporteGraficoForm" property="editarForma" value="true">
							<logic:equal name="gestionarReporteGraficoForm" property="verForma" value="true">
								<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarVista" onclick="javascript:modificarVista();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.modificar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarVista" onclick="javascript:eliminarVista();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.eliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_REPORTE_VIEW" nombreImagen="vistas_detalle" pathImagenes="/componentes/barraHerramientas/" nombre="aceptar" onclick="javascript:graficarReporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar.vista" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<%-- 
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_REPORTE_PRINT" nombreImagen="imprimir"
							pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
							onclick="javascript:imprimir();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.imprimir.vista.datos" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_REPORTE_EXPORT" nombreImagen="exportar"
							pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
							onclick="javascript:exportarToXls();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.vista.datos.excel" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_REPORTE_EXPORT" nombreImagen="html"
							pathImagenes="/componentes/barraHerramientas/" nombre="html"
							onclick="javascript:imprimir('HTML');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.vista.datos.html" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						--%>
						
						
					</vgcinterfaz:barraHerramientas>
		
				</vgcinterfaz:contenedorFormaBarraGenerica>
		
				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaVistas" messageKeyNoElementos="jsp.gestionarvistasdatos.noregistros" nombre="visorDatosVistas" seleccionSimple="true" nombreForma="gestionarReporteGraficoForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
		
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultarV2(document.gestionarReporteGraficoForm, gestionarReporteGraficoForm.paginaSeleccionadaVistas, gestionarReporteGraficoForm.atributoOrdenVistas, gestionarReporteGraficoForm.tipoOrdenVistas, 'nombre', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:columnaVisorLista nombre="public" width="100px" onclick="javascript:consultarV2(document.gestionarReporteGraficoForm, gestionarReporteGraficoForm.paginaSeleccionadaVistas, gestionarReporteGraficoForm.atributoOrdenVistas, gestionarReporteGraficoForm.tipoOrdenVistas, 'publico', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.public" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="720px" onclick="javascript:consultarV2(document.gestionarReporteGraficoForm, gestionarReporteGraficoForm.paginaSeleccionadaVistas, gestionarReporteGraficoForm.atributoOrdenVistas, gestionarReporteGraficoForm.tipoOrdenVistas, 'descripcion', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="fecha" width="200px" onclick="javascript:consultarV2(document.gestionarReporteGraficoForm, gestionarReporteGraficoForm.paginaSeleccionadaVistas, gestionarReporteGraficoForm.atributoOrdenVistas, gestionarReporteGraficoForm.tipoOrdenVistas, 'descripcion', null)">
						<vgcutil:message key="jsp.reportesgrafico.columna.fecha" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:filasVisorLista nombreObjeto="reporteGrafico">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="reporteGrafico" property="reporteId" />
						</vgcinterfaz:visorListaFilaId>
		
						<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaV2(document.gestionarReporteGraficoForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
						<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaV2(document.gestionarReporteGraficoForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
						<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickFilaV2(document.gestionarReporteGraficoForm.seleccionados, null, 'visorDatosVistas', this)</vgcinterfaz:visorListaFilaEventoOnclick>
		
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="reporteGrafico" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="public" align="center">
							<logic:equal name="reporteGrafico" property="publico" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<logic:equal name="reporteGrafico" property="publico" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="reporteGrafico" property="descripcion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha">
							<bean:write name="reporteGrafico" property="fecha" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
		
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaVistas" labelPage="inPagina" action="javascript:consultar(gestionarReporteGraficoForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEmpty name="gestionarReporteGraficoForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaVistas" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-reporte'), 225);
		</script>

	</tiles:put>

</tiles:insert>
