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
		..:: <vgcutil:message key="jsp.gestionarvistasdatos.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			var reportes = new Array(<bean:write name="paginaVistas" property="total"/>);
			<logic:iterate id="reporte" name="paginaVistas" property="lista" indexId="indexReportes">
				reportes[<bean:write name="indexReportes"/>] = new Array(2);
				reportes[<bean:write name="indexReportes"/>][0] = '<bean:write name="reporte" property="reporteId"/>';
				reportes[<bean:write name="indexReportes"/>][1] = '<bean:write name="reporte" property="corte"/>';
			</logic:iterate>

			function nuevaVista() 
			{
				var nombreForma = '?nombreForma=' + 'gestionarVistasDatosForm';
				var nombreCampoOculto = '&nombreCampoOculto=' + 'corte';
				var funcionCierre = '&funcionCierre=' + 'onNuevaVista&funcion=corte';

				abrirVentanaModal('<html:rewrite action="/reportes/corteReporte" />' + nombreForma + nombreCampoOculto + funcionCierre, 'tipoCorte', '390', '210');
			}

			function onNueva(corte) 
			{
				if (corte == 1)
					window.location.href='<html:rewrite action="/vistasdatos/configurarVistaDatos" />?corte=' + corte;
				else
					window.location.href='<html:rewrite action="/vistasdatos/editarReporteTranversal" />';
			}
		
			function modificarVista() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados))
				{
					for (var i = 0; i < reportes.length; i++) 
					{
						if (reportes[i][0] == document.gestionarVistasDatosForm.seleccionados.value) 
						{
							if (reportes[i][1] == 1)
							{
								<logic:equal name="gestionarVistasDatosForm" property="editarForma" value="true">
									window.location.href='<html:rewrite action="/vistasdatos/modificarConfigurarVistaDatos" />?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
								</logic:equal>
								<logic:notEqual name="gestionarVistasDatosForm" property="editarForma" value="true">
									<logic:equal name="gestionarVistasDatosForm" property="verForma" value="true">
										activarBloqueoEspera();
										window.location.href='<html:rewrite action="/vistasdatos/verConfigurarVistaDatos" />?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
									</logic:equal>
								</logic:notEqual>
							}
							else if (reportes[i][1] == 2)
							{
								<logic:equal name="gestionarVistasDatosForm" property="editarForma" value="true">
									window.location.href='<html:rewrite action="/vistasdatos/editarReporteTranversal" />?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
								</logic:equal>
								<logic:notEqual name="gestionarVistasDatosForm" property="editarForma" value="true">
									<logic:equal name="gestionarVistasDatosForm" property="verForma" value="true">
										activarBloqueoEspera();
										window.location.href='<html:rewrite action="/vistasdatos/verReporteTranversal" />?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
									</logic:equal>
								</logic:notEqual>
							}
							break;
						}
					}
				}
			}
		
			function eliminarVista() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados))
				{
					var eliminar = confirm('<vgcutil:message key="jsp.gestionarvistasdatos.eliminarvista.confirmar" />');
					if (eliminar)
					{
						activarBloqueoEspera();
						window.location.href='<html:rewrite action="/vistasdatos/eliminar"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value + '&ts=<%= (new Date()).getTime() %>';
					}
				}
			}
		
			function propiedades() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados)) 
					abrirVentanaModal('<html:rewrite action="/vistasdatos/propiedades" />?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value, "Propiedades", 440, 460);
			}
		
			function reporteVista() 
			{
				abrirReporte('<html:rewrite action="/vistasdatos/generarReporteVistas"/>?atributoOrden=' + document.gestionarVistasDatosForm.atributoOrdenVistas.value + '&tipoOrden=' + document.gestionarVistasDatosForm.tipoOrdenVistas.value, 'Reporte');
			}
			
			function mostrar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados)) 
				{
					/*
					abrirVentanaModal('<html:rewrite action="/vistasdatos/seleccionarVista"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value, 450, 440);
					*/
					
					for (var i = 0; i < reportes.length; i++) 
					{
						if (reportes[i][0] == document.gestionarVistasDatosForm.seleccionados.value) 
						{
							if (reportes[i][1] == 1)
							{
								/*
								activarBloqueoEspera();
								*/
								
								abrirReporte('<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value);
								/*
							    window.location.href='<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
								*/
							
							}
							
							else
							{
								/*
								activarBloqueoEspera();
								*/
								
								abrirReporte('<html:rewrite action="/reportes/mostrarReporte"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value);
								
								/*
								window.location.href='<html:rewrite action="/reportes/mostrarReporte"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
								*/
							}
							
						}
					}
					
				}
			}

			function imprimir(tipo) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados))
				{
					for (var i = 0; i < reportes.length; i++) 
					{
						if (reportes[i][0] == document.gestionarVistasDatosForm.seleccionados.value)
						{
							var url = '?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value;
							url = url + '&orientacion=H&corte=' + reportes[i][1];
							if (typeof tipo != "undefined") 
								url = url + "&tipoPresentacion=" + tipo;

							abrirReporte('<html:rewrite action="/vistasdatos/imprimir"/>' + url, 'Reporte');
						}
					}
				}
			}
			
			function exportarToXls()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarVistasDatosForm.seleccionados))
				{
					for (var i = 0; i < reportes.length; i++) 
					{
						if (reportes[i][0] == document.gestionarVistasDatosForm.seleccionados.value)
							abrirReporte('<html:rewrite action="/vistasdatos/exportarXLS"/>?reporteId=' + document.gestionarVistasDatosForm.seleccionados.value + '&corte=' + reportes[i][1], 'Reporte');
					}
				}
			}
			
		</script>
		
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Representación de la Forma --%>
		<html:form action="/vistasdatos/gestionarVistaDatos" styleClass="formaHtmlGestionar">
		
			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrdenVistas" />
			<html:hidden property="tipoOrdenVistas" />
			<html:hidden property="seleccionados" />
			
			<input type="hidden" name="corte">
		
			<vgcinterfaz:contenedorForma idContenedor="body-reporte">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarvistasdatos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
		
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/vistasdatos/gestionarVistaDatos' />
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
								<logic:equal name="gestionarVistasDatosForm" property="editarForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" />
								</logic:equal>
								<logic:notEqual name="gestionarVistasDatosForm" property="editarForma" value="true">
									<logic:equal name="gestionarVistasDatosForm" property="verForma" value="true">
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
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoVista" onclick="javascript:nuevaVista();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="gestionarVistasDatosForm" property="editarForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarVista" onclick="javascript:modificarVista();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:notEqual name="gestionarVistasDatosForm" property="editarForma" value="true">
							<logic:equal name="gestionarVistasDatosForm" property="verForma" value="true">
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
						<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedadesVista" onclick="javascript:propiedades();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.propiedades" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DATOS_REPORTE_VIEW" nombreImagen="vistas_detalle" pathImagenes="/componentes/barraHerramientas/" nombre="aceptar" onclick="javascript:mostrar();">
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
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfVista" onclick="javascript:reporteVista();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						
					</vgcinterfaz:barraHerramientas>
		
				</vgcinterfaz:contenedorFormaBarraGenerica>
		
				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaVistas" messageKeyNoElementos="jsp.gestionarvistasdatos.noregistros" nombre="visorDatosVistas" seleccionSimple="true" nombreForma="gestionarVistasDatosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
		
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultarV2(document.gestionarVistasDatosForm, gestionarVistasDatosForm.paginaSeleccionadaVistas, gestionarVistasDatosForm.atributoOrdenVistas, gestionarVistasDatosForm.tipoOrdenVistas, 'nombre', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:columnaVisorLista nombre="public" width="100px" onclick="javascript:consultarV2(document.gestionarVistasDatosForm, gestionarVistasDatosForm.paginaSeleccionadaVistas, gestionarVistasDatosForm.atributoOrdenVistas, gestionarVistasDatosForm.tipoOrdenVistas, 'publico', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.public" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="720px" onclick="javascript:consultarV2(document.gestionarVistasDatosForm, gestionarVistasDatosForm.paginaSeleccionadaVistas, gestionarVistasDatosForm.atributoOrdenVistas, gestionarVistasDatosForm.tipoOrdenVistas, 'descripcion', null)">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>
		
					<vgcinterfaz:filasVisorLista nombreObjeto="reporte">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="reporte" property="reporteId" />
						</vgcinterfaz:visorListaFilaId>
		
						<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaV2(document.gestionarVistasDatosForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
						<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaV2(document.gestionarVistasDatosForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
						<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickFilaV2(document.gestionarVistasDatosForm.seleccionados, null, 'visorDatosVistas', this)</vgcinterfaz:visorListaFilaEventoOnclick>
		
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="reporte" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="public" align="center">
							<logic:equal name="reporte" property="publico" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<logic:equal name="reporte" property="publico" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="reporte" property="descripcion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
		
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
		
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaVistas" labelPage="inPagina" action="javascript:consultar(gestionarVistasDatosForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEmpty name="gestionarVistasDatosForm" property="atributoOrden">
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
