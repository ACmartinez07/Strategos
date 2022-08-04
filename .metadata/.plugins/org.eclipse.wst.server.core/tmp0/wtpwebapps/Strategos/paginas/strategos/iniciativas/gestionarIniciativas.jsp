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

<%-- Modificado por: Kerwin Arias (09/01/2012) --%>
<%-- Modificado por: Andres Caucali (19/09/2018) --%>
<%-- Funciones JavaScript locales de la p�gina Jsp --%>
<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/Actividad.js'/>"></script>
<script type="text/javascript">
	var _tituloIniciativa = '<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />';
	var _showFiltro = false;
	var _selectHitoricoTypeIndex = 1;

	function nuevoIniciativa() 
	{
		var url = '?source=Iniciativa' + document.gestionarIniciativasForm.source.value;
		url = url+"&planId="+ document.gestionarIniciativasForm.planId.value;
		
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			url = url+"&desdeInstrumento=true";
			<logic:notEmpty name="gestionarIniciativasForm" property="instrumentoId">
				url = url + "&instrumentoId=" + document.gestionarIniciativasForm.instrumentoId.value;
			</logic:notEmpty>
		</logic:equal>					
		//abrirVentanaModal('<html:rewrite action="/iniciativas/crearIniciativa"/>' + url , "IniciativaAdd", 1080, 800);
		window.location.href = '<html:rewrite action="/iniciativas/crearIniciativa" />' + url;
	}

	function modificarIniciativa() 
	{
		var url = "";
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			url = url+"&desdeInstrumento=true";
			<logic:notEmpty name="gestionarIniciativasForm" property="instrumentoId">
				url = url + "&instrumentoId=" + document.gestionarIniciativasForm.instrumentoId.value;
			</logic:notEmpty>
		</logic:equal>	
		<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">
			<logic:notEmpty name="gestionarIniciativasForm" property="portafolioId">
				url = url + "&portafolioId=" + document.gestionarIniciativasForm.portafolioId.value;
			</logic:notEmpty>
		</logic:equal>
		
		<logic:equal name="gestionarIniciativasForm" property="editarForma" value="true">					
			//abrirVentanaModal('<html:rewrite action="/iniciativas/modificarIniciativa"/>?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + url, "IniciativaEdit", 1080, 800);
			window.location.href='<html:rewrite action="/iniciativas/modificarIniciativa" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + url;
		</logic:equal>
		<logic:notEqual name="gestionarIniciativasForm" property="editarForma" value="true">
			<logic:equal name="gestionarIniciativasForm" property="verForma" value="true">				
				//abrirVentanaModal('<html:rewrite action="/iniciativas/verIniciativa"/>?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + url, "IniciativaEdit", 1080, 800);
				window.location.href='<html:rewrite action="/iniciativas/verIniciativa" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + url;
			</logic:equal>
		</logic:notEqual>
	}

	function eliminarIniciativa() 
	{
		var url = '';
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			url = url+"&desdeInstrumento=true";
			<logic:notEmpty name="gestionarIniciativasForm" property="instrumentoId">
				url = url + "&instrumentoId=" + document.gestionarIniciativasForm.instrumentoId.value;
			</logic:notEmpty>
		</logic:equal>
		
		activarBloqueoEspera();
		var eliminar = confirm('<vgcutil:message key="jsp.gestionariniciativas.eliminariniciativas.confirmar" arg0="' + _tituloIniciativa + '" />');
		if (eliminar) 
			window.location.href='<html:rewrite action="/iniciativas/eliminarIniciativa"/>?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + url + '&ts=<%= (new Date()).getTime() %>';
		else 
			desactivarBloqueoEspera();
	}

	function propiedadesIniciativa() 
	{
		abrirVentanaModal('<html:rewrite action="/iniciativas/propiedadesIniciativa" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value, "Iniciativa", 480, 485);
	}

	function limpiarFiltrosIniciativas() 
	{
		gestionarIniciativasForm.filtroNombre.value = "";
		gestionarIniciativasForm.submit();
	}

	function buscarIniciativas() 
	{
		gestionarIniciativasForm.submit();i
	}

	function planificacionSeguimiento() 
	{
		var url = '';
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			url = url+"&desdeInstrumento=true";
			window.location.href='<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>?defaultLoader=true'+
					'&iniciativaId='+ document.gestionarIniciativasForm.seleccionadoId.value + '&funcion=inicio&planId=null'+
					url;
					
		</logic:equal>	
		<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
			var actividad = new Actividad();
			actividad.url = '<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>';
			actividad.ShowList(true, document.gestionarIniciativasForm.seleccionadoId.value, 'Inicio', null, url);
		</logic:notEqual>	
		
	}

	function gestionarProductos() 
	{	
 		window.location.href='<html:rewrite action="/planificacionseguimiento/productos/gestionarProductosAutonomo"  />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
	}

	function eventoClickIniciativa(objetoFilaId) 
	{
		activarBloqueoEspera();
		if (document.gestionarIniciativasForm.source.value == "Plan")
			document.gestionarIniciativasForm.action = getActionSubmitIniciativasGestionFromPlan();
		else if (document.gestionarIniciativasForm.source.value == "portafolio")
		{
			refrescarPortafolio(document.gestionarIniciativasForm.seleccionadoId.value);
			return;
		}
		else if (document.gestionarIniciativasForm.source.value == "instrumentos")
		{
			refrescarInstrumento(document.gestionarIniciativasForm.seleccionadoId.value);
			return;
		}
		else
			document.gestionarIniciativasForm.action = getActionSubmitIniciativasGestion();			
		document.gestionarIniciativasForm.submit();
	}

	function reporteIniciativas() 
	{
		var url = '?source=Iniciativa' + document.gestionarIniciativasForm.source.value;
		url = url + '&planId=' + document.gestionarIniciativasForm.planId.value;
		url = url + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			url = url + '&selectHitoricoType=' + selectHitoricoType.value;

		if(document.gestionarIniciativasForm.seleccionadoId.value == "" || document.gestionarIniciativasForm.seleccionadoId.value == null){
			alert('<vgcutil:message key="jsp.iniciativas.reporte.detallado" />');
		}
		else{
			
			abrirVentanaModal('<html:rewrite action="/reportes/iniciativas/ejecucion" />' + url, "EjecucionDeLasIniciativas", 500, 490);
		}
	}
	
	function reporteIniciativasResumido() 
	{
		url = '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			url = url + '&selectHitoricoType=' + selectHitoricoType.value;

    	abrirVentanaModal('<html:rewrite action="/reportes/iniciativas/ejecucionResumida" />?' + url, "EjecucionDeLasIniciativas", 500, 490);
	}
	
	function reporteIniciativasResumidoEjecucion() 
	{
		url = '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			url = url + '&selectHitoricoType=' + selectHitoricoType.value;

    	abrirVentanaModal('<html:rewrite action="/reportes/iniciativas/resumidaEjecucion" />?' + url, "EjecucionDeLasIniciativas", 500, 490);
	}
	
	
	function reporteDetalladoIniciativaPorProductos() 
	{
		abrirReporte('<html:rewrite action="/planificacionseguimiento/productos/generarReporteDetalladoIniciativaPorProductos" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value, 'reporte');
	}
	
	function getActionSubmitIniciativas() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativas"/>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIniciativas" primerParametro="true" /> + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	function getActionSubmitIniciativasGestion() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativas" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	function getActionSubmitIniciativasGestionFromPlan() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	function registrarSeguimiento() 
	{
		window.location.href='<html:rewrite action="/planificacionseguimiento/productos/iniciarRegistroSeguimiento" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&organizacionId=<bean:write name="organizacionId" scope="session" />';
	}
	
	function editarClaves()
	{
		window.location.href = '<html:rewrite action="/framework/usuarios/cambiarClaveUsuario" />';
	}
	
	function gestionarAnexosIniciativa(inciativaId) 
	{
		var url = '';
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			url = url+"?desdeInstrumento=true";
		</logic:equal>	
		
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasForm.seleccionadoId))
		{
			var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>' + url;
			explicacion.ShowList(true, document.gestionarIniciativasForm.seleccionadoId.value, 'Iniciativa', 0);
		}
	}
	
	function enviarEmailIniciativa()
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasForm.seleccionadoId))
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/iniciativas/enviarEmail" />?objetoId=' + document.gestionarIniciativasForm.seleccionadoId.value + "&tipoObjeto=Iniciativa", document.gestionarIniciativasForm.respuesta, 'onEnviarEmailIniciativa()');
	}

	function onEnviarEmailIniciativa()
	{
		var respuesta = document.gestionarIniciativasForm.respuesta.value.split("|");
		if (respuesta.length > 0)
		{
			if (respuesta[0] == "true")
			{
				if (respuesta[1] == "true")
				{
					var to = respuesta[2]; 
					var cc = respuesta[3];
					var asunto = respuesta[4];
					
					SendEmail(to, cc, undefined, asunto, undefined);
				}
				else
					alert('<vgcutil:message key="jsp.email.reponsable.correo.empty" />');
			}
			else
				alert('<vgcutil:message key="jsp.email.objeto.reponsable.empty" />');
		}
		else
			alert('<vgcutil:message key="jsp.email.objeto.invalido" />');
	}
	
	function visualizarIniciativasRelacionadas() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasForm.seleccionadoId))
		{
			var url = '?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&accion=refrescar';
			window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas" />' + url;
		}
	}
	
	function graficarIniciativa(tipoGrafico)
	{
		abrirVentanaModal('<html:rewrite action="/iniciativa/grafico/configurar" />?funcion=configurar&tipoGrafico=' + tipoGrafico + '&source=Plan&planId=' + document.gestionarIniciativasForm.planId.value, "graficoIniciativas", 470, 330);
	}
	
	function onGraficarIniciativa(xml)
	{
		window.location.href='<html:rewrite action="/iniciativa/grafico/crearGrafico"/>' + xml;
	}
	
	function showFiltro()
	{
		var tblFiltro = document.getElementById('tblFiltro');
		var trFilterTop = document.getElementById('trFilterTop');
		var trFilterBottom = document.getElementById('trFilterBottom');
		if (tblFiltro != null)
		{
			if (_showFiltro)
			{
				_showFiltro = false;
				tblFiltro.style.display = "none";
				resizeAltoForma(230);
			}
			else
			{
				_showFiltro = true;
				tblFiltro.style.display = "";
				resizeAltoForma(332);
			}
			if (trFilterTop != null)
				trFilterTop.style.display = tblFiltro.style.display;
			if (trFilterBottom != null)
				trFilterBottom.style.display = tblFiltro.style.display;
		}
	}
	
	function resizeAltoForma(alto)
	{
		if (typeof(alto) == "undefined")
			alto = 230;
			
		resizeAlto(document.getElementById('body-iniciativas'), alto);
	}
	
	function limpiarFiltros()
	{
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			filtroNombre.value = "";
		if (filtroAnio != null)
			filtroAnio.value = "";
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			selectHitoricoType.selectedIndex = _selectHitoricoTypeIndex;
		
		
	}
	
	function refrescar()
	{
		var url = '?source=' + document.gestionarIniciativasForm.source.value;
		url = url + '&planId=' + document.gestionarIniciativasForm.planId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			url = url + '&selectHitoricoType=' + selectHitoricoType.value;
		var selectEstatusType = document.getElementById('selectEstatusType');
		if (selectEstatusType != null)
			url = url + '&selectEstatusType=' + selectEstatusType.value;	
		var selectTipos = document.getElementById('selectTipos');
		if (selectTipos != null)
			url = url + '&selectTipos=' + selectTipos.value;
	
		var filtroAnio = document.getElementById('filtroAnio');
		if (filtroAnio != null)
			url = url + '&filtroAnio=' + filtroAnio.value;
		
		<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
			window.location.href='<html:rewrite action="/instrumentos/gestionarInstrumentos" />' + url;
		</logic:equal>
		
		<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
			<logic:notEmpty scope="session" name="planActivoId">
				window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />' + url;
			</logic:notEmpty>
			<logic:empty scope="session" name="planActivoId">
				window.location.href = '<html:rewrite action="/iniciativas/gestionarIniciativas" />' + url;
			</logic:empty>
		</logic:notEqual>	
				
	}

	function visualizarHistoria()
	{
		var url = '?source=' + document.gestionarIniciativasForm.source.value;
		url = url + '&planId=' + document.gestionarIniciativasForm.planId.value;
		
		abrirVentanaModal('<html:rewrite action="/iniciativas/historicoIniciativa" />' + url, "Historia", 770, 485);
	}
	
	function asociarIniciativa() 
	{
		var queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&seleccionMultiple=true';
		abrirSelectorIniciativas('gestionarIniciativasForm', 'nombreIniciativa', 'seleccionadoIniciativaId', null, null, 'onAsociarIniciativa()', null, queryStringFiltros);
	}
	
	function onAsociarIniciativa() 
	{
		window.location.href = '<html:rewrite action="/portafolios/asociarIniciativa"/>?portafolioId=' + document.gestionarIniciativasForm.portafolioId.value + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoIniciativaId.value;
	}
	
	function desasociarIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasForm.seleccionadoId))
		{
			var desasociar = confirm('Esta seguro que desea desasociar la iniciativa seleccionada?');
			if (desasociar) 
				window.location.href='<html:rewrite action="/portafolios/desasociarIniciativa"/>?portafolioId=' + document.gestionarIniciativasForm.portafolioId.value + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		}
	}
	
	function asociarInstrumento() 
	{
		var queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&seleccionMultiple=true';
		abrirSelectorIniciativas('gestionarIniciativasForm', 'nombreIniciativa', 'seleccionadoIniciativaId', null, null, 'onAsociarInstrumento()', null, queryStringFiltros);
	}
	
	function onAsociarInstrumento() 
	{
		window.location.href = '<html:rewrite action="/instrumentos/asociarIniciativa"/>?instrumentoId=' + document.gestionarIniciativasForm.instrumentoId.value + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoIniciativaId.value;
	}
	
	function desasociarInstrumento() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasForm.seleccionadoId))
		{
			var desasociar = confirm('Esta seguro que desea desasociar la iniciativa seleccionada?');
			if (desasociar) 
				window.location.href='<html:rewrite action="/instrumentos/desasociarIniciativa"/>?instrumentoId=' + document.gestionarIniciativasForm.instrumentoId.value + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		}
	}	
	
	function configurarVisorIniciativas() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIniciativas', _tituloIniciativa);
	}
	
	function desbloquearCulminado(){
		
		activarBloqueoEspera();
		var desbloquear = confirm('<vgcutil:message key="jsp.gestionariniciativas.desbloquear.culminado.confirmar"/>');
		if (desbloquear) 
			window.location.href='<html:rewrite action="/iniciativas/desbloquearCulminadoSalvar"/>?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		else 
			desactivarBloqueoEspera();
	}
	
	
	function protegerLiberarIniciativas(){
		var proteger;
		
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarIniciativasForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarIniciativas()';
		var parametros = '&proteger=' + proteger;
		parametros = parametros + '&origen=1';
		parametros = parametros + '&iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value;
		parametros = parametros + '&planId=<bean:write name="gestionarIniciativasForm" property="planId" />';

		
		
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
				
		abrirVentanaModal('<html:rewrite action="/iniciativas/protegerLiberar"/>' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarIniciativa', '750', '560');
	}
	
	 function generarReporteDatosBasicos()
	 {
		 abrirVentanaModal('<html:rewrite action="/reportes/iniciativas/datosBasicos" />', "Iniciativa", 520, 460);
	 }
	
	
</script>
<%-- Representaci�n de la Forma --%>
<html:form action="/iniciativas/gestionarIniciativas" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionadoId" />
	<html:hidden property="tipoAlerta" />
	<html:hidden property="respuesta" />
	<html:hidden property="source" />
	<html:hidden property="planId" />
	<html:hidden property="portafolioId" />
	<html:hidden property="instrumentoId" />

	<input type="hidden" name="nombreIniciativa" value="">
	<input type="hidden" name="seleccionadoIniciativaId" value="">

	<bean:define id="tipoCalculoEstadoIniciativaPorActividades">
		<bean:write name="gestionarIniciativasForm" property="tipoCalculoEstadoIniciativaPorActividades" />
	</bean:define>
	<bean:define id="tipoCalculoEstadoIniciativaPorSeguimientos">
		<bean:write name="gestionarIniciativasForm" property="tipoCalculoEstadoIniciativaPorSeguimientos" />
	</bean:define>

	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<vgcinterfaz:contenedorForma idContenedor="body-iniciativas">

		<%-- T�tulo --%>
		<vgcinterfaz:contenedorFormaTitulo>
			<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">..:: <vgcutil:message key="jsp.gestionariniciativas.titulo" arg0="<%= tituloIniciativa %>" /></logic:notEqual>
			<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">..:: <vgcutil:message key="jsp.gestionarportafolios.titulo" arg0="<%= tituloIniciativa %>" /></logic:equal>
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Men� --%>
		<vgcinterfaz:contenedorFormaBarraMenus>
			<%-- Inicio del Men� --%>
			<vgcinterfaz:contenedorMenuHorizontal>
				<%-- Men�: Archivo --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivoIniciativas" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteResumidoIniciativas();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Men�: Edici�n --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionIniciativas" key="menu.edicion">
						<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoIniciativa();" permisoId="INICIATIVA_ADD" aplicaOrganizacion="true" />
						</logic:notEqual>
						<logic:equal name="gestionarIniciativasForm" property="editarForma" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIniciativa();" />
						</logic:equal>
						<logic:notEqual name="gestionarIniciativasForm" property="editarForma" value="true">
							<logic:equal name="gestionarIniciativasForm" property="verForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIniciativa();" />
							</logic:equal>
						</logic:notEqual>
						<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarIniciativa();" permisoId="INICIATIVA_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						</logic:notEqual>
						<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
							<vgcinterfaz:botonMenu key="menu.edicion.asociar" onclick="asociarInstrumento();" permisoId="PORTAFOLIO_INICIATIVA_ADD" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.desasociar" onclick="desasociarInstrumento();" permisoId="PORTAFOLIO_INICIATIVA_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						</logic:equal>
						<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.edicion.asociar" onclick="asociarIniciativa();" permisoId="PORTAFOLIO_INICIATIVA_ADD" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.desasociar" onclick="desasociarIniciativa();" permisoId="PORTAFOLIO_INICIATIVA_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						</logic:equal>
						<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesIniciativa();" permisoId="INICIATIVA" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="INICIATIVA_EMAIL" onclick="enviarEmailIniciativa();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Men�: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVerIniciativas" key="menu.ver">
						<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="<%= tipoCalculoEstadoIniciativaPorActividades %>">
							<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.ver.planificacionseguimiento" onclick="planificacionSeguimiento();" permisoId="INICIATIVA_SEGUIMIENTO" aplicaOrganizacion="true"  agregarSeparador="true" />
						</logic:equal>
						<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.ver.iniciativas.relacionadas" permisoId="INICIATIVA_RELACION" onclick="visualizarIniciativasRelacionadas();" />
						</logic:equal>
						<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.ver.iniciativas.relacionadas" permisoId="INICIATIVA_RELACION" onclick="visualizarIniciativasRelacionadas();" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.ver.marcar.historico" permisoId="INICIATIVA_HISTORICO" onclick="visualizarHistoria();" />
						</logic:notEqual>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Men�: Evaluaci�n --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEvaluacionIniciativas" key="menu.evaluacion">
						<logic:notEmpty scope="session" name="planActivoId">
							<vgcinterfaz:menuAnidado key="menu.evaluacion.graficos" agregarSeparador="true">
								<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.iniciativa.graficar.estatus" permisoId="INICIATIVA_EVALUAR_GRAFICO_ESTATUS" aplicaOrganizacion="true" onclick="graficarIniciativa(0);" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.iniciativa.graficar.porcentajes" permisoId="INICIATIVA_EVALUAR_GRAFICO_PORCENTAJE" aplicaOrganizacion="true" onclick="graficarIniciativa(1);" />
							</vgcinterfaz:menuAnidado>
							
								<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reporteIniciativas();" permisoId="INICIATIVA_EVALUAR_REPORTE_DETALLADO" />
								<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="<%= tipoCalculoEstadoIniciativaPorSeguimientos %>">
									<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reporteDetalladoIniciativaPorProductos();" />
								</logic:equal>
								<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.resumido" onclick="reporteIniciativasResumido();" permisoId="INICIATIVA_EVALUAR_REPORTE_RESUMIDO" />
								<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado.ejecucion" onclick="reporteIniciativasResumidoEjecucion();" permisoId="INICIATIVA_EVALUAR_REPORTE_RESUMIDO" />
						
								
						</logic:notEmpty>
						 
						<logic:empty scope="session" name="planActivoId">
							<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reporteIniciativas();" permisoId="INICIATIVA_EVALUAR_REPORTE_DETALLADO" />
							<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.resumido" onclick="reporteIniciativasResumido();" permisoId="INICIATIVA_EVALUAR_REPORTE_RESUMIDO" />
							<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado.ejecucion" onclick="reporteIniciativasResumidoEjecucion();" permisoId="INICIATIVA_EVALUAR_REPORTE_RESUMIDO" />
							<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.datos.basicos" onclick="generarReporteDatosBasicos();" permisoId="INICIATIVA_EVALUAR_REPORTE_DATOS_BASICOS" />
							<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="<%= tipoCalculoEstadoIniciativaPorSeguimientos %>">
								<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reporteDetalladoIniciativaPorProductos();" />
								
							</logic:equal>
							
						</logic:empty>
						
						
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				
				<%-- Men�: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasIniciativas" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorIniciativas();" />
						<vgcinterfaz:botonMenu key="menu.herramientas.culminado" onclick="desbloquearCulminado();" />
						<vgcinterfaz:botonMenu key="menu.herramientas.bloquear.iniciativa" onclick="protegerLiberarIniciativas();" permisoId="PROTEGER_LIBERAR_INICIATIVA" aplicaOrganizacion="true" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarIniciativas">
				<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_ADD" aplicaOrganizacion="true" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEqual>
				<logic:equal name="gestionarIniciativasForm" property="editarForma" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:notEqual name="gestionarIniciativasForm" property="editarForma" value="true">
					<logic:equal name="gestionarIniciativasForm" property="verForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarIniciativa();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
				</logic:notEqual>
				<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_DELETE" aplicaOrganizacion="true" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasSeparador />
				
				<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_INICIATIVA_ADD" nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="vincularIniciativa" onclick="javascript:asociarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.asociar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_INICIATIVA_DELETE" nombreImagen="desasociar" pathImagenes="/componentes/barraHerramientas/" nombre="desvincularIniciativa" onclick="javascript:desasociarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.desasociar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:equal name="gestionarIniciativasForm" property="source" value="instrumentos">
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_INICIATIVA_ADD" nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="vincularIniciativa" onclick="javascript:asociarInstrumento();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.asociar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_INICIATIVA_DELETE" nombreImagen="desasociar" pathImagenes="/componentes/barraHerramientas/" nombre="desvincularIniciativa" onclick="javascript:desasociarInstrumento();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.desasociar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:showFiltro();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.filtro" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					
				</logic:equal>
				<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesIniciativa();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<logic:notEmpty scope="session" name="planActivoId">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_EVALUAR_REPORTE_RESUMIDO" nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteIniciativas();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionariniciativas.menu.reportes.resumido" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEmpty>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="EXPLICACION" nombreImagen="explicaciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="gestionarAnexos" onclick="javascript:gestionarAnexosIniciativa();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="jsp.visualizariniciativasplan.columna.explicaciones" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="<%= tipoCalculoEstadoIniciativaPorActividades %>">
					<%--<logic:greaterThan name="gestionarIniciativasPaginaPeriodos" property="total" value="0"> --%>
					<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="INICIATIVA_SEGUIMIENTO">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="planificacion" pathImagenes="/paginas/strategos/iniciativas/imagenes/" nombre="planificacionSeguimiento" onclick="javascript:planificacionSeguimiento();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionariniciativas.planificacionseguimiento" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcutil:tienePermiso>
					<%--</logic:greaterThan> --%>
				</logic:equal>
				<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="<%= tipoCalculoEstadoIniciativaPorSeguimientos %>">
					<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="INICIATIVA_SEGUIMIENTO">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="productos" pathImagenes="/paginas/strategos/iniciativas/imagenes/" nombre="gestionarProductos" onclick="javascript:gestionarProductos();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionariniciativas.gestionarproductos" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcutil:tienePermiso>
				</logic:equal>
				
				<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_EMAIL" nombreImagen="email" pathImagenes="/componentes/barraHerramientas/" nombre="email" onclick="javascript:enviarEmailIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.email" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
	
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_RELACION" nombreImagen="relacionIniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="relacionIniciativa" onclick="javascript:visualizarIniciativasRelacionadas();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.iniciativas.relacionadas" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEqual>
		
				<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
					<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
						<logic:notEmpty scope="session" name="planActivoId">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_EVALUAR_GRAFICO_ESTATUS" nombreImagen="graficoasistente" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoAsistenteIndicador" onclick="javascript:graficarIniciativa(0);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.evaluacion.graficos.iniciativa.graficar.estatus" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_EVALUAR_GRAFICO_PORCENTAJE" nombreImagen="graficoplantillas" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoPlantillasIndicador" onclick="javascript:graficarIniciativa(1);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.evaluacion.graficos.iniciativa.graficar.porcentajes" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEmpty>
					</logic:notEqual>
					
						
					
									
					<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_HISTORICO" nombreImagen="historicos" pathImagenes="/paginas/strategos/barraHerramientas/" nombre="historicos" onclick="javascript:visualizarHistoria();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.marcar.historico" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:showFiltro();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.filtro" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:notEqual>
				</logic:notEqual>
				
				<%--botones reportes --%>
								
			</vgcinterfaz:barraHerramientas>
			
			<%-- Filtro --%>
			<table class="tablaSpacing0Padding0Width100Collapse">
				<tr id="trFilterTop" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
				<tr class="barraFiltrosForma">
					<td style="width: 420px;">
						<jsp:include flush="true" page="/paginas/strategos/iniciativas/filtroIniciativas.jsp"></jsp:include>
					</td>
				</tr>
				<tr id="trFilterBottom" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
			</table>
		</vgcinterfaz:contenedorFormaBarraGenerica>
		
		<bean:define id="listavacia">
			<vgcutil:message key="jsp.gestionariniciativas.noregistros" arg0="<%= tituloIniciativa %>"/>
		</bean:define>
		
		<vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" nombre="visorIniciativas" seleccionSimple="true" nombreForma="gestionarIniciativasForm" nombreCampoSeleccionados="seleccionadoId" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" messageKeyNoElementos="<%= listavacia %>" >
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
				<vgcutil:message key="jsp.gestionariniciativasplan.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'nombre', null)">
				<vgcutil:message key="jsp.gestionariniciativas.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="70px">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.porcentajecompletado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaUltimaMedicion" width="100px">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.fechaUltimaMedicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="condicion" width="40px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.condicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.estatus" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="tipo" width="100px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.tipoProyecto" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="anioformproy" width="100px" >
				<vgcutil:message key="jsp.gestionariniciativas.columna.anioFormProy" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="140px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'frecuencia', null)">
				<vgcutil:message key="jsp.gestionariniciativas.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="organizacion" width="340px" >
				<vgcutil:message key="jsp.gestionarresponsables.columna.organizacion" />
			</vgcinterfaz:columnaVisorLista>
			
						
			<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
				<vgcinterfaz:columnaVisorLista nombre="responsableFijarMeta" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableFijarMetaId', null)">
				<vgcutil:message key="jsp.gestionariniciativas.columna.responsablefijarmeta" />
				</vgcinterfaz:columnaVisorLista>
				<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableLograrMetaId', null)">
					<vgcutil:message key="jsp.gestionariniciativas.columna.responsablelograrmeta" />
				</vgcinterfaz:columnaVisorLista>
				<vgcinterfaz:columnaVisorLista nombre="responsableSeguimiento" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableSeguimientoId', null)">
					<vgcutil:message key="jsp.gestionariniciativas.columna.responsableseguimiento" />
				</vgcinterfaz:columnaVisorLista>
				<vgcinterfaz:columnaVisorLista nombre="responsableCargarMeta" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableCargarMetaId', null)">
					<vgcutil:message key="jsp.gestionariniciativas.columna.responsablecargarmeta" />
				</vgcinterfaz:columnaVisorLista>
				<vgcinterfaz:columnaVisorLista nombre="responsableCargarEjecutado" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableCargarEjecutadoId', null)">
					<vgcutil:message key="jsp.gestionariniciativas.columna.responsablecargarejecutado" />
				</vgcinterfaz:columnaVisorLista>
			</logic:notEqual>
			
			

			<%-- Filas --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">
				<vgcinterfaz:visorListaFilaId>
					<bean:write name="iniciativa" property="iniciativaId" />
				</vgcinterfaz:visorListaFilaId>
				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickIniciativa('<bean:write name="iniciativa" property="iniciativaId" />');</vgcinterfaz:visorListaFilaEventoOnclick>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIniciativa name="iniciativa" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="iniciativa" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center">
					<bean:write name="iniciativa" property="porcentajeCompletadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaUltimaMedicion" align="center">
					<bean:write name="iniciativa" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
					<bean:write name="iniciativa" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="condicion" align="center">
					<vgcst:imagenHistoricoIniciativa name="iniciativa" property="condicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
					<bean:write name="iniciativa" property="estatus.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo" align="center">
					<logic:notEmpty name="iniciativa" property="tipoProyecto">
						<bean:write name="iniciativa" property="tipoProyecto.nombre" />
					</logic:notEmpty>
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="anioformproy" align="center">
					<bean:write name="iniciativa" property="anioFormulacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<logic:notEmpty name="iniciativa" property="organizacion">
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacion" >
						<bean:write name="iniciativa" property="organizacion.nombre" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
				</logic:notEmpty>
				
				
				<logic:notEqual name="gestionarIniciativasForm" property="source" value="instrumentos">
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableFijarMeta">
					<logic:notEmpty name="iniciativa" property="responsableFijarMeta">
						<bean:write name="iniciativa" property="responsableFijarMeta.nombreCargo" />
					</logic:notEmpty>
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta">
						<logic:notEmpty name="iniciativa" property="responsableLograrMeta">
							<bean:write name="iniciativa" property="responsableLograrMeta.nombreCargo" />
						</logic:notEmpty>
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableSeguimiento">
						<logic:notEmpty name="iniciativa" property="responsableSeguimiento">
							<bean:write name="iniciativa" property="responsableSeguimiento.nombreCargo" />
						</logic:notEmpty>
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableCargarMeta">
						<logic:notEmpty name="iniciativa" property="responsableCargarMeta">
							<bean:write name="iniciativa" property="responsableCargarMeta.nombreCargo" />
						</logic:notEmpty>
					</vgcinterfaz:valorFilaColumnaVisorLista>			
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableCargarEjecutado">
						<logic:notEmpty name="iniciativa" property="responsableCargarEjecutado">
							<bean:write name="iniciativa" property="responsableCargarEjecutado.nombreCargo" />
						</logic:notEmpty>
					</vgcinterfaz:valorFilaColumnaVisorLista>
				</logic:notEqual>			
				
				
			</vgcinterfaz:filasVisorLista>

		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaIniciativas" labelPage="inPagina" action="javascript:consultarV2(gestionarIniciativasForm, gestionarIniciativasForm.pagina, gestionarIniciativasForm.atributoOrden, gestionarIniciativasForm.tipoOrden, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
			<vgcinterfaz:contenedorFormaBarraInferior>
				<logic:notEmpty name="gestionarIniciativasForm" property="atributoOrden">
					<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIniciativasForm" property="atributoOrden" />  [<bean:write name="gestionarIniciativasForm" property="tipoOrden" />]
				</logic:notEmpty>
			</vgcinterfaz:contenedorFormaBarraInferior>
		</logic:notEqual>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	if (gestionarIniciativasForm.source.value != "portafolio")
		resizeAltoForma(230);
	else
		resizeAltoFormaDividida(true);
	
	var selectHitoricoType = document.getElementById('selectHitoricoType');
	if (selectHitoricoType != null)
		_selectHitoricoTypeIndex = selectHitoricoType.selectedIndex;
</script>
