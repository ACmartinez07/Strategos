<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>
<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	var _selectCondicionTypeIndex = 1;
	var _altoPrefijoListo = false;
	var _showFiltroInst = false;

	function nuevoInstrumento(){		
		abrirVentanaModal('<html:rewrite action="/instrumentos/crearInstrumento"/>', "InstrumentoAdd", 1130, 650);
	}
	
	function modificarInstrumento(instrumentoId) 
	{
		if ((document.gestionarInstrumentosForm.seleccionados.value == null) || (document.gestionarInstrumentosForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}
		var instrumentoId = document.gestionarInstrumentosForm.seleccionados.value;
		
		abrirVentanaModal('<html:rewrite action="/instrumentos/modificarInstrumento"/>?instrumentoId=' + instrumentoId , "InstrumentoEdit", 1130, 650);
	}

	function eliminarInstrumento(instrumentoId) 
	{
		if ((document.gestionarInstrumentosForm.seleccionados.value == null) || (document.gestionarInstrumentosForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var instrumentoId = document.gestionarInstrumentosForm.seleccionados.value;	
		var eliminar = confirm ('<vgcutil:message key="jsp.gestionarinstrumento.eliminar.confirmar" />');		
		if (eliminar)
			window.location.href='<html:rewrite action="/instrumentos/eliminarInstrumento"/>?instrumentoId=' + instrumentoId + '&ts=<%= (new Date()).getTime() %>';
	}
	
	function reportePdf(){
		
		abrirReporte('<html:rewrite action="/instrumentos/reporteBasicoPdf"/>', 'reporte');
	}
	
	function gestionarCooperantes(){
		window.location.href='<html:rewrite action="/instrumentos/gestionarCooperantes" />';
	}
	
	function gestionarTiposConvenio(){
		window.location.href='<html:rewrite action="/instrumentos/gestionarConvenios" />';
	}
		
	function limpiarFiltrosInstrumento()
	{
		
		var filtroNombre = document.getElementById('nombreCorto');
		if (filtroNombre != null)
			filtroNombre.value = "";
		var anio = document.getElementById('anio');
		if (anio != null)
			anio.value = "";
		var estatus = document.getElementById('estatus');
		if (estatus != null)
			estatus.value = 0;	
		var convenio = document.getElementById('selectTipoConvenio');
		if (convenio != null)
			convenio.value = 0;	
		var cooperativo = document.getElementById('selectCooperante');
		if (cooperativo != null)
			cooperativo.value = 0;
		
	}
	
	function refrescarInstrumento(iniciativaSeleccionadaId)
	{
		var url = '?instrumentoId=' + document.gestionarInstrumentosForm.seleccionados.value;
		
		
		var filtroNombre = document.getElementById('nombreCorto');
		if (filtroNombre != null)
			url = url + '&nombreCorto=' + filtroNombre.value;
		var anio = document.getElementById('anio');
		if (anio != null)
			url = url + '&anio=' + anio.value;
		var estatus = document.getElementById('estatus');
		if (estatus != null)
			url = url + '&estatus=' + estatus.value;	
		var convenio = document.getElementById('selectTipoConvenio');
		if (convenio != null)
			url = url + '&con=' + convenio.value;	
		var cooperativo = document.getElementById('selectCooperante');
		if (cooperativo != null)
			url = url + '&cop=' + cooperativo.value;
		
				
		if (typeof(iniciativaSeleccionadaId) != "undefined")
			url = url + '&iniciativaSeleccionadaId=' + iniciativaSeleccionadaId;

		window.location.href='<html:rewrite action="/instrumentos/gestionarInstrumentos" />' + url;
	}
	
	function eventoClickInstrumento(objetoFilaId) 
	{
		activarBloqueoEspera();
		refrescarInstrumento();
	}
	
	function reporteInstrumentosEjecucion() 
	{
		url = '&instrumentoId=' + document.gestionarInstrumentosForm.seleccionados.value;
		
		var filtroNombre = document.getElementById('nombreCorto');
		if (filtroNombre != null)
			url = url + '&nombreCorto=' + filtroNombre.value;
		var anio = document.getElementById('anio');
		if (anio != null)
			url = url + '&anio=' + anio.value;
		var estatus = document.getElementById('estatus');
		if (estatus != null)
			url = url + '&estatus=' + estatus.value;	
		var convenio = document.getElementById('selectTipoConvenio');
		if (convenio != null)
			url = url + '&con=' + convenio.value;	
		var cooperativo = document.getElementById('selectCooperante');
		if (cooperativo != null)
			url = url + '&cop=' + cooperativo.value;
    	abrirVentanaModal('<html:rewrite action="/instrumentos/resumidaEjecucion" />?' + url, "EjecucionDeLosInstrumentos", 490, 450);
	}

	function reporteInstrumentosEjecucionDetalle() 
	{
		url = '&instrumentoId=' + document.gestionarInstrumentosForm.seleccionados.value;
		
		var filtroNombre = document.getElementById('nombreCorto');
		if (filtroNombre != null)
			url = url + '&nombreCorto=' + filtroNombre.value;
		var anio = document.getElementById('anio');
		if (anio != null)
			url = url + '&anio=' + anio.value;
		var estatus = document.getElementById('estatus');
		if (estatus != null)
			url = url + '&estatus=' + estatus.value;	
		var convenio = document.getElementById('selectTipoConvenio');
		if (convenio != null)
			url = url + '&con=' + convenio.value;	
		var cooperativo = document.getElementById('selectCooperante');
		if (cooperativo != null)
			url = url + '&cop=' + cooperativo.value;
    	abrirVentanaModal('<html:rewrite action="/instrumentos/resumidaEjecucionDetalle" />?' + url, "EjecucionDeLosInstrumentos", 490, 450);
	}
	
	function gestionarAnexos(instrumentoId) 
	{
		var url = '';
				
		var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>' + url;
			explicacion.ShowList(true, document.gestionarInstrumentosForm.seleccionados.value, 'Instrumento', 5);
	}
	
	function filtrarInstrumento(){
		
		var tblFiltroInst = document.getElementById('tblFiltroInst');
		var trFilterInstTop = document.getElementById('trFilterInstTop');
		var trFilterInstBottom = document.getElementById('trFilterInstBottom');
		if (tblFiltroInst != null)
		{
			if (_showFiltroInst)
			{
				_showFiltroInst = false;
				tblFiltroInst.style.display = "none";
				resizeAltoForma(230);
			}
			else
			{
				_showFiltroInst = true;
				tblFiltroInst.style.display = "";
				resizeAltoForma(332);
			}
			if (trFilterInstTop != null)
				trFilterInstTop.style.display = tblFiltroInst.style.display;
			if (trFilterInstBottom != null)
				trFilterInstBottom.style.display = tblFiltroInst.style.display;
		}
		
	}
	
</script>

<%-- Representación de la Forma --%>
<html:form action="/instrumentos/gestionarInstrumentos" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="seleccionadoId" />

	<vgcinterfaz:contenedorForma idContenedor="body-instrumentos">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarinstrumentos.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

			
				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoInstrumento();" permisoId="INSTRUMENTOS_ADD" />
						<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarInstrumento();" permisoId="INSTRUMENTOS_EDIT" />
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarInstrumento();" permisoId="INSTRUMENTOS_DELETE" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
			

				<%-- Menú: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVerVista" key="menu.ver">
						<vgcinterfaz:botonMenu key="jsp.gestionarinstrumentos.cooperante.titulo" onclick="gestionarCooperantes();" permisoId="INSTRUMENTOS_VIEW_COOP" />
						<vgcinterfaz:botonMenu key="jsp.gestionarinstrumentos.tipo.titulo" onclick="gestionarTiposConvenio();" permisoId="INSTRUMENTOS_VIEW_TIPO" />
						
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEvaluacionIniciativas" key="menu.evaluacion">						
						<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.resumido" onclick="reporteInstrumentosEjecucion();" permisoId="INSTRUMENTOS" />
						<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reporteInstrumentosEjecucionDetalle();" permisoId="INSTRUMENTOS" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaIniciativas" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
			
			
			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarPortafolios">

				<vgcinterfaz:barraHerramientasBoton permisoId="INSTRUMENTOS_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoInstrumento();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="INSTRUMENTOS_EDIT" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarInstrumento();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.modificar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="INSTRUMENTOS_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarInstrumento();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfResumido" onclick="javascript:reportePdf();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar.resumida" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="explicaciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="gestionarAnexos" onclick="javascript:gestionarAnexos();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="jsp.visualizariniciativasplan.columna.explicaciones" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:filtrarInstrumento();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.ver.filtro" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>					

			</vgcinterfaz:barraHerramientas>
			
			<%-- Filtro --%>
			<table class="tablaSpacing0Padding0Width100Collapse">
				<tr id="trFilterInstTop" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
				<tr class="barraFiltrosForma">
					<td style="width: 420px;">
						<jsp:include flush="true" page="/paginas/strategos/instrumentos/filtroInstrumentos.jsp"></jsp:include>
					</td>
				</tr>
				<tr id="trFilterInstBottom" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
			</table>
			

		</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- Visor Tipo Lista --%>
		
		<vgcinterfaz:visorLista namePaginaLista="paginaInstrumentos" width="100%" messageKeyNoElementos="jsp.gestionarvistasdatos.noregistros" nombre="visorInstrumentos" seleccionSimple="true" nombreForma="gestionarInstrumentosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
		
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" >
						<vgcutil:message key="jsp.pagina.instrumentos.nombre" />
					</vgcinterfaz:columnaVisorLista>
									
					<vgcinterfaz:columnaVisorLista nombre="tipo" width="150px">
						<vgcutil:message key="jsp.pagina.instrumentos.tipo" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="cooperante" width="150px" >
						<vgcutil:message key="jsp.pagina.instrumentos.cooperante" />
					</vgcinterfaz:columnaVisorLista>	
							
					<vgcinterfaz:columnaVisorLista nombre="fecha" width="100px" >
						<vgcutil:message key="jsp.pagina.instrumentos.fecha" />
					</vgcinterfaz:columnaVisorLista>	
					
					<vgcinterfaz:columnaVisorLista nombre="fechaTerminacion" width="100px" >
						<vgcutil:message key="jsp.pagina.instrumentos.fecha.terminacion" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="350px" >
						<vgcutil:message key="jsp.pagina.instrumentos.descripcion" />
					</vgcinterfaz:columnaVisorLista>
										
					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px" >
						<vgcutil:message key="jsp.pagina.instrumentos.estatus" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="unidad" width="200px" >
						<vgcutil:message key="jsp.pagina.instrumentos.unidad" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="objetivo" width="500px" >
						<vgcutil:message key="jsp.pagina.instrumentos.objetivo" />
					</vgcinterfaz:columnaVisorLista>
														
					<vgcinterfaz:columnaVisorLista nombre="responsable" width="250px" >
						<vgcutil:message key="jsp.pagina.instrumentos.responsable.cgi" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="responsablepgn" width="250px" >
						<vgcutil:message key="jsp.pagina.instrumentos.responsable.pgn" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:filasVisorLista nombreObjeto="instrumentos">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="instrumentos" property="instrumentoId" />
						</vgcinterfaz:visorListaFilaId>	
						
						<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickInstrumento('<bean:write name="instrumentos" property="instrumentoId" />');</vgcinterfaz:visorListaFilaEventoOnclick>
									
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="instrumentos" property="nombreCorto" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo">
						
							<logic:notEmpty name="instrumentos" property="tipoConvenio">
								<bean:write name="instrumentos" property="tipoConvenio.nombre" />
							</logic:notEmpty>&nbsp;
						
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="cooperante">
							
							<logic:notEmpty name="instrumentos" property="cooperante">
								<bean:write name="instrumentos" property="cooperante.nombre" />
							</logic:notEmpty>&nbsp;
					
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha">
							<logic:notEmpty name="instrumentos" property="fechaInicio" >
								<bean:write name="instrumentos" property="fechaInicioTexto" />
							</logic:notEmpty>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaTerminacion">
							<logic:notEmpty name="instrumentos" property="fechaTerminacion" >
								<bean:write name="instrumentos" property="fechaTerminacionTexto" />
							</logic:notEmpty>
						</vgcinterfaz:valorFilaColumnaVisorLista>
												
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="instrumentos" property="nombreInstrumento" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus">
							<logic:equal name="instrumentos" property="estatus" value="1">
								<vgcutil:message key="jsp.pagina.instrumentos.estatus.sinIniciar" />
							</logic:equal>
							<logic:equal name="instrumentos" property="estatus" value="2">
								<vgcutil:message key="jsp.pagina.instrumentos.estatus.ejecucion" />
							</logic:equal>
							<logic:equal name="instrumentos" property="estatus" value="3">
								<vgcutil:message key="jsp.pagina.instrumentos.estatus.cancelado" />
							</logic:equal>
							<logic:equal name="instrumentos" property="estatus" value="4">
								<vgcutil:message key="jsp.pagina.instrumentos.estatus.suspendido" />
							</logic:equal>
							<logic:equal name="instrumentos" property="estatus" value="5">
								<vgcutil:message key="jsp.pagina.instrumentos.estatus.culminado" />
							</logic:equal>						
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
							<bean:write name="instrumentos" property="areasCargo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
											
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="objetivo">
							<bean:write name="instrumentos" property="objetivoInstrumento" />
						</vgcinterfaz:valorFilaColumnaVisorLista>					
											
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsable">
							<bean:write name="instrumentos" property="responsableCgi" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsablepgn">
							<bean:write name="instrumentos" property="nombreReposnsableAreas" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						
		
					</vgcinterfaz:filasVisorLista>
		
		</vgcinterfaz:visorLista>		
		
	
		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaInstrumentos" labelPage="inPagina" action="javascript:consultar(gestionarInstrumentosForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>
		
		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarInstrumentosForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaInstrumentos" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
		
	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-instrumentos'), 230);
	
	var visor = document.getElementById('visorInstrumentos');
	if (visor != null)
		visor.style.width = (parseInt(_myWidth) - 140) + "px";
</script>
