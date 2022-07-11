<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (01/10/2012) -->

<%-- Funciones JavaScript externas de la página Jsp --%>
<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript">	
	_anchoAreBar = 135;
	function gestionarOrganizaciones() 
	{
		<logic:empty scope="session" name="activarOrganizacion">
			window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones" />?defaultLoader=true&mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />';
		</logic:empty>
		<logic:notEmpty scope="session" name="activarOrganizacion">
			<logic:notEmpty scope="session" name="activarOrganizacion" property="objeto">
				<logic:equal scope="session" name="activarOrganizacion" property="objeto.activo" value="true">
					window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones" />?defaultLoader=true&mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />';
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	}	

	//=======================================================================JavaScript para lanzar el Explorador de Plan
	//===================================================================================================================

	function gestionarPlan() 
	{
		ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/gestionarPlan" />?funcion=getPlanActivoId', document.barraAreaForm.planId, 'finalizarGestionarPlan()');
	}

	function finalizarGestionarPlan() 
	{
		if ((document.barraAreaForm.planId.value == null) || (document.barraAreaForm.planId.value == ''))
			window.location.href='<html:rewrite action="/planes/gestionarPlanesEstrategicos" />';
		else if (document.barraAreaForm.planId.value.indexOf(',') == -1)
			window.location.href='<html:rewrite action="/planes/gestionarPlan" />?defaultLoader=true&planId=' + document.barraAreaForm.planId.value;
		else
			seleccionarPlan();
	}

	function seleccionarPlan() 
	{
		var queryStringFiltros;
		queryStringFiltros = "&mostrarSoloPlanesActivos=true";
		abrirSelectorPlanes('barraAreaForm', 'nombrePlan', 'planId', '', 'funcionCierreSelectorPlanes()', null, queryStringFiltros);
	}

	function funcionCierreSelectorPlanes() 
	{
		if ((document.barraAreaForm.planId.value != null) && (document.barraAreaForm.planId.value != "")) 
			window.location.href='<html:rewrite action="/planes/gestionarPlan" />?defaultLoader=true&planId=' +  document.barraAreaForm.planId.value;
	}
	
	//===================================================================================================================
	//===================================================================================================================

	function gestionarIniciativas() 
	{
		var planActivoId = "";
		<logic:notEmpty scope="session" name="planActivoId">
			planActivoId = <bean:write scope="session" name="planActivoId"/>;
		</logic:notEmpty>
		if (planActivoId != "")
			window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />?defaultLoader=true&planId=' + planActivoId + '&source=Plan';
		else
			window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativas" />?defaultLoader=true&source=General&actualizarForma=true';
	}

	function gestionarIndicadores() 
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores" />?defaultLoader=true';
	}	
	
	function gestionarPresentacionesEjecutiva() 
	{
		window.location.href='<html:rewrite action="/presentaciones/vistas/gestionarVistas" />?defaultLoader=true';
	}
	
	function gestionarProblemas() 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas" />?defaultLoader=true&tipo=0';												    
	}

	function gestionarRiesgo() 
	{
		
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas" />?defaultLoader=true&tipo=1';	
			
		
		/* nuevo modulo riesgos para pgn */
		
		/*
		window.location.href="http://localhost:4200" ;
		*/	    
	}
	
	
	function gestionarAdministracion() 
	{	
		window.location.href='<html:rewrite action="/framework/administracion" />?defaultLoader=true';
	}

	function selectores() 
	{
		window.location.href='<html:rewrite page="/paginas/strategos/selectores.jsp" />';
	}
	
	function vistasDatos() 
	{
		window.location.href='<html:rewrite action="/vistasdatos/gestionarVistaDatos" />?defaultLoader=true';
	}	
	
	function reporteGrafico() 
	{
		window.location.href='<html:rewrite action="/reportesgrafico/gestionarReporteGrafico" />?defaultLoader=true';
	}	

	function gestionarPortafolios() 
	{
		window.location.href='<html:rewrite action="/portafolios/gestionarPortafolios" />?defaultLoader=true';
	}
	
	function gestionarInstrumentos() 
	{
		window.location.href='<html:rewrite action="/instrumentos/gestionarInstrumentos" />?defaultLoader=true';
	}
	
	function gestionarAlertas()
	{
		window.location.href='<html:rewrite action="/alertas/gestionarAlertas" />?defaultLoader=true';
	}
	
	function informesCualitativos()
	{
		closeDialog();

		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, '<bean:write scope="session" name="organizacionId"/>', 'Organizacion', 1);
	}

	function informesEjecutivos()
	{
		closeDialog();

		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, '<bean:write scope="session" name="organizacionId"/>', 'Organizacion', 2);
	}

	function eventos()
	{
		closeDialog();

		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, '<bean:write scope="session" name="organizacionId"/>', 'Organizacion', 3);
	}

	function noticias()
	{
		closeDialog();

		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, '<bean:write scope="session" name="organizacionId"/>', 'Organizacion', 4);
	}
	
	function informes(element)
	{
		if (element == undefined || element == "") return;
		var o = document.getElementById(element);
		if (!o) return;
		
		var pos = GetObjectCoordinates(o);
		_dialogOpen = true;
		
		var message = '<ul>';
		<logic:notEmpty scope="session" name="activarInforme">
			<logic:equal scope="session" name="activarInforme" property="hayCualitativos" value="true">
				message = '<li><a href="javascript:informesCualitativos();">' + '<vgcutil:message key="jsp.extension.informes.cualitativos" />' +'</a></li>';
			</logic:equal>
			<logic:equal scope="session" name="activarInforme" property="hayEjecutivos" value="true">
				message = message + '<li><a href="javascript:informesEjecutivos();">' + '<vgcutil:message key="jsp.extension.informes.ejecutivos" />' +'</a></li>';
			</logic:equal>
			<logic:equal scope="session" name="activarInforme" property="hayEventos" value="true">
				message = message + '<li><a href="javascript:eventos();">' + '<vgcutil:message key="jsp.extension.informes.eventos" />' +'</a></li>';
			</logic:equal>
			<logic:equal scope="session" name="activarInforme" property="hayNoticias" value="true">
				message = message + '<li><a href="javascript:noticias();">' + '<vgcutil:message key="jsp.extension.informes.noticias" />' +'</a></li>';
			</logic:equal>
		</logic:notEmpty>
		message = message + '</ul>';
		
		// ******************************
		// Ejemplos de Control de Dialog Box
		// Ejemplo con JQuery y Estilos
		// ******************************
		// get the screen height and width  
		var maskHeight = _myHeightReal;  
		var maskWidth = _myWidthReal;
		
		// calculate the values for center alignment
		var dialogTop =  pos.y;  
		var dialogLeft = pos.x; 
		
		// assign values to the overlay and dialog box
		//$('#dialog-overlay').css({visibility: "visible"}).show();
		$('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
		$('#dialog-box').css({top:dialogTop, left:(dialogLeft+100), height:100, width: 350}).show();
		
		// display the message
		$('#dialog-bottom').css({visibility: "hidden"});
		$('#dialog-titulo').html('<vgcutil:message key="jsp.extension.titulo" />');
		$('#dialog-message').html(message);
		//$('#fondo').css({visibility: "visible"});
		// ******************************

		// ******************************
		// Ejemplo con JQuery y Estilos
		// ******************************
		//		
	    //var caja2 = $('<div><p>Esta es una segunda caja de diálogo...</p><p>En esta ocasión generé en tiempo de ejecución el DIV con Javascript y luego, sin llegar a mostrar el DIV en la página, lo convierto en una caja de diálogo.</p></div>');
		//caja2.dialog(
		//{
		//	title: 'Edit Scope',
		//	modal : true,
		//	resizable : false,
		//	draggable : false,
		//	buttons: [
		//	          {
		//        		text: "Cancel",
		//         		click: function() 
		//         		{ 
		//         			$(this).dialog("close");
		//        		}
		//      		  },
		//       		  {
		//         		text: "Process",
		//         		click: function() 
		//         		{
		//         			$(this).dialog("close"); // Close dialog 
		//         			processScheduler(); // Process scheduler
		//         		} 
		//       		  }]			
		//});
		// ******************************

		// ******************************
		// Ejemplo con Estilos Sin JQuery
		// ******************************
		//el = document.getElementById("overlay");
		//el.style.top = (pos.x-80);
		//el.style.left = (pos.y-400);
		//el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
		// ******************************
	}
	
	function processScheduler()
	{
	}
	
	function bancoProyectos() 
	{		
		window.location.href="http://tyr.defensoria.gov.co/gestionideas?id=" + '<bean:write scope="session" name="organizacionId"/>' ;	
	}
	
</script>

			

<form name="barraAreaForm" class="form-group">
	<input type="hidden" name="planId">
	<input type="hidden" name="nombrePlan">
	<bean:define toScope="page" id="develop" value="false"></bean:define>
	
	<vgcinterfaz:barraAreas width="100px" nombre="strategos">
		
		<!-- Organizaciones -->
		<logic:empty scope="session" name="activarOrganizacion">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" permisoId="ORGANIZACION" nombre="organizaciones" onclick="gestionarOrganizaciones();" urlImage="/paginas/strategos/imagenes/organizacion.png">
				<vgcutil:message key="barraareas.strategos.organizaciones" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarOrganizacion">
			<logic:notEmpty scope="session" name="activarOrganizacion" property="objeto">
				<logic:equal scope="session" name="activarOrganizacion" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" permisoId="ORGANIZACION" nombre="organizaciones" onclick="gestionarOrganizaciones();" urlImage="/paginas/strategos/imagenes/organizacion.png">
						<vgcutil:message key="barraareas.strategos.organizaciones" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
		<!-- Indicadores -->
		<logic:empty scope="session" name="activarIndicador">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="INDICADOR_RAIZ" nombre="indicadores" onclick="gestionarIndicadores();" urlImage="/paginas/strategos/imagenes/indicadores.gif">
				<vgcutil:message key="barraareas.strategos.indicadores" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarIndicador">
			<logic:notEmpty scope="session" name="activarIndicador" property="objeto">
				<logic:equal scope="session" name="activarIndicador" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="INDICADOR_RAIZ" nombre="indicadores" onclick="gestionarIndicadores();" urlImage="/paginas/strategos/imagenes/indicadores.gif">
						<vgcutil:message key="barraareas.strategos.indicadores" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
		<!-- Problemas -->
		<logic:empty scope="session" name="activarProblema">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="PROBLEMA" nombre="problemas" onclick="gestionarProblemas();" urlImage="/paginas/strategos/imagenes/problemas.png">
				<vgcutil:message key="barraareas.strategos.accionescorrectivas" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarProblema">
			<logic:notEmpty scope="session" name="activarProblema" property="objeto">
				<logic:equal scope="session" name="activarProblema" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="PROBLEMA" nombre="problemas" onclick="gestionarProblemas();" urlImage="/paginas/strategos/imagenes/problemas.png">
						<vgcutil:message key="barraareas.strategos.accionescorrectivas" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>

		
		
		<!-- Banco Proyectos
		<vgcinterfaz:botonBarraAreas permisoId="" onclick="bancoProyectos();" nombre="riesgo" urlImage="/paginas/strategos/imagenes/portafolio.gif" >
			<vgcutil:message key="barraareas.strategos.banco" />
		</vgcinterfaz:botonBarraAreas> --> 
		
		

		<!-- 	Riesgo 
		<logic:notEmpty scope="session" name="activarRiesgo">
			<logic:notEmpty scope="session" name="activarRiesgo" property="objeto">
				<logic:equal scope="session" name="activarRiesgo" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas permisoId="" onclick="gestionarRiesgo();" nombre="riesgo" urlImage="/paginas/strategos/imagenes/riesgo.gif" >
						<vgcutil:message key="barraareas.strategos.riesgo" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
		-->
	
		<!-- Plan -->
		<logic:empty scope="session" name="activarPlan">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="PLAN" nombre="planes" onclick="gestionarPlan();" urlImage="/paginas/strategos/imagenes/planes.gif">
				<vgcutil:message key="barraareas.strategos.planes" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarPlan">
			<logic:notEmpty scope="session" name="activarPlan" property="objeto">
				<logic:equal scope="session" name="activarPlan" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="PLAN" nombre="planes" onclick="gestionarPlan();" urlImage="/paginas/strategos/imagenes/planes.gif">
						<vgcutil:message key="barraareas.strategos.planes" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
		<!-- Iniciativas -->
		<logic:empty scope="session" name="activarIniciativa">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="INICIATIVA" nombre="iniciativas" onclick="gestionarIniciativas();" urlImage="/paginas/strategos/imagenes/iniciativas.gif" agregarSeparador="true">
				<vgcutil:message key="barraareas.strategos.iniciativas" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarIniciativa">
			<logic:notEmpty scope="session" name="activarIniciativa" property="objeto">
				<logic:equal scope="session" name="activarIniciativa" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="INICIATIVA" nombre="iniciativas" onclick="gestionarIniciativas();" urlImage="/paginas/strategos/imagenes/iniciativas.gif" agregarSeparador="true">
						<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
			
		<!-- Presentacion Ejecutiva -->
		<logic:empty scope="session" name="activarPresentacionEjecutiva">
			<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA" nombre="presentacionEjecutiva" onclick="gestionarPresentacionesEjecutiva();" urlImage="/paginas/strategos/imagenes/PresentacionEjecutiva.png">
				<vgcutil:message key="barraareas.strategos.presentacionejecutiva" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarPresentacionEjecutiva">
			<logic:notEmpty scope="session" name="activarPresentacionEjecutiva" property="objeto">
				<logic:equal scope="session" name="activarPresentacionEjecutiva" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA" nombre="presentacionEjecutiva" onclick="gestionarPresentacionesEjecutiva();" urlImage="/paginas/strategos/imagenes/PresentacionEjecutiva.png">
						<vgcutil:message key="barraareas.strategos.presentacionejecutiva" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
		<!-- Portafolio -->
		<logic:empty scope="session" name="activarPortafolio">
			<vgcinterfaz:botonBarraAreas permisoId="PORTAFOLIO" onclick="gestionarPortafolios();" nombre="portafolios" urlImage="/paginas/strategos/imagenes/Portafolio.png">
				<vgcutil:message key="barraareas.strategos.portafolios" />
			</vgcinterfaz:botonBarraAreas>
		</logic:empty>
		<logic:notEmpty scope="session" name="activarPortafolio">
			<logic:notEmpty scope="session" name="activarPortafolio" property="objeto">
				<logic:equal scope="session" name="activarPortafolio" property="objeto.activo" value="true">
					<vgcinterfaz:botonBarraAreas permisoId="" onclick="gestionarPortafolios();" nombre="portafolios" urlImage="/paginas/strategos/imagenes/Portafolio.png">
						<vgcutil:message key="barraareas.strategos.portafolios" />
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
		
		<!-- Instrumentos 	 -->	
	
		<vgcinterfaz:botonBarraAreas permisoId="INSTRUMENTOS" onclick="gestionarInstrumentos();" nombre="instrumentos" urlImage="/paginas/strategos/imagenes/riesgo.gif">
			<vgcutil:message key="barraareas.strategos.instrumentos" />
		</vgcinterfaz:botonBarraAreas>
		
		
			 	
		
			 	
		<!-- Reportes Graficos -->
		
		<vgcinterfaz:botonBarraAreas permisoId="" onclick="reporteGrafico();" nombre="reporteGrafico" urlImage="/paginas/strategos/indicadores/imagenes/barraHerramientas/grafico_1.gif" >
			<vgcutil:message key="barraareas.strategos.reporte" />
		</vgcinterfaz:botonBarraAreas>
	
	
		<!-- Informes -->
		<logic:notEmpty scope="session" name="activarInforme">
			<logic:equal scope="session" name="activarInforme" property="hayInformen" value="true">
				<logic:empty scope="session" name="activarVistaDatos">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif">
						<vgcutil:message key="barraareas.strategos.vistadedatos" />
					</vgcinterfaz:botonBarraAreas>
				</logic:empty>
				<logic:notEmpty scope="session" name="activarVistaDatos">
					<logic:notEmpty scope="session" name="activarVistaDatos" property="objeto">
						<logic:equal scope="session" name="activarVistaDatos" property="objeto.activo" value="true">
							<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif">
								<vgcutil:message key="barraareas.strategos.vistadedatos" />
							</vgcinterfaz:botonBarraAreas>
						</logic:equal>
					</logic:notEmpty>
				</logic:notEmpty>
				<vgcinterfaz:botonBarraAreas permisoId="" onclick="informes(this.id);" nombre="informe" urlImage="/paginas/strategos/imagenes/informe.gif" agregarSeparador="true">
					<vgcutil:message key="barraareas.strategos.informes" />
				</vgcinterfaz:botonBarraAreas>
			</logic:equal>
			
			
			
			<!-- Vista de Datos -->
			<logic:notEqual scope="session" name="activarInforme" property="hayInformen" value="true">
				<logic:empty scope="session" name="activarVistaDatos">
					<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif" agregarSeparador="true">
						<vgcutil:message key="barraareas.strategos.vistadedatos" />
					</vgcinterfaz:botonBarraAreas>
				</logic:empty>
				<logic:notEmpty scope="session" name="activarVistaDatos">
					<logic:notEmpty scope="session" name="activarVistaDatos" property="objeto">
						<logic:equal scope="session" name="activarVistaDatos" property="objeto.activo" value="true">
							<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif" agregarSeparador="true">
								<vgcutil:message key="barraareas.strategos.vistadedatos" />
							</vgcinterfaz:botonBarraAreas>
						</logic:equal>
					</logic:notEmpty>
				</logic:notEmpty>
			</logic:notEqual>
		</logic:notEmpty>
		
		<!-- Vista de Datos -->
		<logic:empty scope="session" name="activarInforme">
			<logic:empty scope="session" name="activarVistaDatos">
				<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif" agregarSeparador="true">
					<vgcutil:message key="barraareas.strategos.vistadedatos" />
				</vgcinterfaz:botonBarraAreas>
			</logic:empty>
			<logic:notEmpty scope="session" name="activarVistaDatos">
				<logic:notEmpty scope="session" name="activarVistaDatos" property="objeto">
					<logic:equal scope="session" name="activarVistaDatos" property="objeto.activo" value="true">
						<vgcinterfaz:botonBarraAreas aplicaOrganizacion="true" permisoId="VISTA_DATOS" onclick="vistasDatos();" nombre="vistasDatos" urlImage="/paginas/strategos/imagenes/vistaDatos.gif" agregarSeparador="true">
							<vgcutil:message key="barraareas.strategos.vistadedatos" />
						</vgcinterfaz:botonBarraAreas>
					</logic:equal>
				</logic:notEmpty>
			</logic:notEmpty>
		</logic:empty>
		
		
		
		
		<!-- Administracion -->
		<vgcinterfaz:botonBarraAreas permisoId="CONFIGURACION" nombre="adminus" onclick="gestionarAdministracion();" urlImage="/paginas/strategos/imagenes/administracion.gif">
			<vgcutil:message key="barraareas.strategos.administracion" />
		</vgcinterfaz:botonBarraAreas>
	
		<!-- Selectores -->
		<logic:notEmpty scope="session" name="enviroment">
			<logic:notEmpty scope="session" name="enviroment" property="tipo">
				<logic:equal scope="session" name="enviroment" property="tipo" value="enhance">
					<vgcinterfaz:botonBarraAreas nombre="Selector" onclick="selectores();" urlImage="/paginas/strategos/imagenes/administracion.gif">
						Selectores
					</vgcinterfaz:botonBarraAreas>
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	
	</vgcinterfaz:barraAreas>
</form>