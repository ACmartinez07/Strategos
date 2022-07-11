<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">

	function seleccionarNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/gestionarPerspectivas"/>?verIndicadoresLogroPlan=<bean:write name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan"/>&selectedPerspectivaId=' + nodoId + marcadorAncla;
	}

	function openNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/gestionarPerspectivas"/>?verIndicadoresLogroPlan=<bean:write name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan"/>&openPerspectivaId=' + nodoId + marcadorAncla;
	}

	function closeNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/gestionarPerspectivas"/>?verIndicadoresLogroPlan=<bean:write name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan"/>&closePerspectivaId=' + nodoId + marcadorAncla;
	}

	function gestionarModelo() 
	{
		ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/modelos/listaModelo" />?funcion=getModeloId&planId=<bean:write name="gestionarPlanForm" property="planId" />', document.gestionarPerspectivasForm.seleccionadoId, 'onGestionarModelo()');
	}

	function onGestionarModelo() 
	{
		if ((document.gestionarPerspectivasForm.seleccionadoId.value == null) || (document.gestionarPerspectivasForm.seleccionadoId.value == '')) 
			gestionarModeloCausaEfecto();
		else if (document.gestionarPerspectivasForm.seleccionadoId.value.indexOf(',') == -1) 
			onSeleccionarModeloCausaEfecto();
		else 
			seleccionarModeloCausaEfecto();
	}
	
	function gestionarModeloCausaEfecto()
	{
		window.location.href='<html:rewrite action="/planes/modelos/gestionarModelos" />?planId=<bean:write name="gestionarPlanForm" property="planId" />';
	}

	function seleccionarModeloCausaEfecto() 
	{
		var url = '?planId=<bean:write name="gestionarPlanForm" property="planId" />';
		var nombreForma = '&nombreForma=' + 'gestionarPerspectivasForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'seleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onSeleccionarModeloCausaEfecto()';

		abrirVentanaModal('<html:rewrite action="/planes/modelos/listaModelo" />' + url + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarModelos', '500', '575');
	}

    function onSeleccionarModeloCausaEfecto()
    {
		window.location.href='<html:rewrite action="/planes/modelos/editarModelo" />?planId=<bean:write name="gestionarPlanForm" property="planId" />&editar=0&source=2&modeloId=' + document.gestionarPerspectivasForm.seleccionadoId.value;
    }
	
	function nuevaPerspectiva() 
	{		
		//abrirVentanaModal('<html:rewrite action="/planes/perspectivas/crearPerspectiva"/>', "PerspectivaAdd", 680, 460);
		window.location.href = '<html:rewrite action="/planes/perspectivas/crearPerspectiva" />';
	}
	
	function modificarPerspectiva() 
	{
		<logic:equal scope="session" name="editarPerspectiva" value="true">			
			//abrirVentanaModal('<html:rewrite action="/planes/perspectivas/modificarPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />', "PerspectivaEdit", 680, 460);
			window.location.href = '<html:rewrite action="/planes/perspectivas/modificarPerspectiva" />?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />';
		</logic:equal>
		<logic:notEqual scope="session" name="editarPerspectiva" value="true">
			<logic:equal scope="session" name="verPerspectiva" value="true">				
				abrirVentanaModal('<html:rewrite action="/planes/perspectivas/verPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />', "PerspectivaEdit", 680, 460);
				window.location.href = '<html:rewrite action="/planes/perspectivas/verPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />';
			</logic:equal>
		</logic:notEqual>
	}

	function modificarPlan() 
	{	
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />';
		<logic:equal scope="session" name="editarPlan" value="true">			
			abrirVentanaModal('<html:rewrite action="/planes/modificarPlan"/>?planId=' + planId, "PlanEdit", 450, 470);
		</logic:equal>
		<logic:notEqual scope="session" name="editarPlan" value="true">
			<logic:equal scope="session" name="verPlan" value="true">				
				abrirVentanaModal('<html:rewrite action="/planes/verPlan"/>?planId='+ planId, "PlanEdit", 450, 470);
			</logic:equal>
		</logic:notEqual>
	}

	function eliminarPerspectiva() 
	{
	    var nombrePers = '<bean:write name="perspectiva" property="nombre"/>';
		var respuesta = confirm ('<vgcutil:message key="jsp.gestionarperspectivas.eliminarperspectivas.confirmar" arg0="' + nombrePers + '" />');
		if (respuesta) 
			window.location.href = '<html:rewrite action="/planes/perspectivas/eliminarPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId"/>' + '&ts=<%=(new java.util.Date()).getTime()%>';
	}

	function reporte() 
	{
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />';		
		abrirReporte('<html:rewrite action="/planes/perspectivas/generarReportePerspectivas.action"/>?planId=' + planId, 'reporte');
	}

	function propiedadesPerspectiva() 
	{
		abrirVentanaModal('<html:rewrite action="/planes/perspectivas/propiedadesPerspectiva" />?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />', "Perspectiva", 450, 455);
	}

	function visualizarPlan() 
	{
		window.location.href = '<html:rewrite action="/planes/visualizarPlan" />?defaultLoader=true&planId=<bean:write name="gestionarPlanForm" property="planId" scope="session" />';
	}
	
	function gestionarIniciativasDePlan()
	{
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />';
		window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />?planId=' + planId + '&source=Plan';
	}
	
	function gestionarPerspectivas(verIndicadoresLogroPlan) 
	{
		var ejecucion = true;
		window.location.href = '<html:rewrite action="/planes/perspectivas/gestionarPerspectivas" />?verIndicadoresLogroPlan=' + verIndicadoresLogroPlan+'&ejecucion=' +ejecucion;
	}

	function calcularPlan() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />';

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
    }

	function asignarPesosIndicadoresPlanPerspectiva() 
	{
		abrirVentanaModal('<html:rewrite action="/planes/indicadores/asignarPesosIndicadoresPlanPerspectiva" />?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&funcionCierre=actualizarPerspectivas()', 'asignarPesosIndicadoresPlan', '730', '575');
	}

	function asignarPesosIndicadoresPlanObjetivo() 
	{
		abrirVentanaModal('<html:rewrite action="/planes/indicadores/editarPesosIndicadoresPlanObjetivo" />?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&funcionCierre=actualizarPerspectivas()', 'asignarPesosIndicadoresPlan', '730', '575');
	}

	function actualizarPerspectivas() 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/gestionarPerspectivas"/>?verIndicadoresLogroPlan=<bean:write name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan"/>';
	}
	
    function copiarPerspectiva()
    {
    	window.location.href='<html:rewrite action="/planes/perspectivas/copiarLeerPerspectiva" />?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&accion=copiar';
    }

    function reporteEjecucionPlan() 
	{
    	abrirVentanaModal('<html:rewrite action="/reportes/planes/ejecucion" />?source=Plan&perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&planId=<bean:write name="gestionarPlanForm" property="planId" />', "EjecucionDelPlan", 500, 480);
	}
    
    function reporteMetaPlan()
	{
    	abrirVentanaModal('<html:rewrite action="/reportes/planes/meta" />?source=Plan&perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&planId=<bean:write name="gestionarPlanForm" property="planId" />', "MetaPlan", 400, 370);
	}
    
    function reporteExplicacionesPlan()
	{
   		abrirVentanaModal('<html:rewrite action="/reportes/planes/explicaciones" />?source=Plan&perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&planId=<bean:write name="gestionarPlanForm" property="planId" />', "ExplicacionesPlan", 480, 350);
	}
    
    function reporteExplicaciones(url)
	{
		abrirReporte('<html:rewrite action="/reportes/planes/explicacionReportePlan" />' + url);
	}
    
	function visualizarPerspectivasRelacionadas() 
	{		
		var url = '?defaultLoader=true&perspectivaId=<bean:write name="perspectiva" property="perspectivaId"/>'
				+ '&accion=refrescar';
		window.location.href='<html:rewrite action="/planes/perspectivas/visualizarPerspectivasRelacionadas" />' + url;
	}
	
	function enviarEmailPerspecitva()
	{
		ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/perspectivas/enviarEmail" />?objetoId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&tipoObjeto=Perspectiva', document.gestionarPerspectivasForm.respuesta, 'onEnviarEmailPerspecitva()');
	}

	function onEnviarEmailPerspecitva()
	{
		var respuesta = document.gestionarPerspectivasForm.respuesta.value.split("|");
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
	
	function setAnchoPanel()
	{
		if (startHorizontal && splitPlanPosicionNueva != 0 && splitPlanPosicionActual != splitPlanPosicionNueva)
		{
			startHorizontal = false;
			var tipo = "Ancho";
			var panel = "Strategos.Panel.Plan";
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/setPanel" />?panel=' + panel + '&tipo=' + tipo + '&tamano=' + splitPlanPosicionNueva, document.gestionarPerspectivasForm.respuesta, 'onSetPanel()');
		}
	}
	
	function setAltoPanel()
	{
		if (startVertical && splitPlanVerticalPosicionNueva != 0 && splitPlanVerticalPosicionActual != splitPlanVerticalPosicionNueva)
		{
			startVertical = false;
			var tipo = "Alto";
			var panel = "Strategos.Panel.Plan";
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/setPanel" />?panel=' + panel + '&tipo=' + tipo + '&tamano=' + splitPlanVerticalPosicionNueva, document.gestionarPerspectivasForm.respuesta, 'onSetPanel()');
		}
	}
	
	function onSetPanel()
	{
	}
	
	function reporteConsolidadoPlan()
	{
		abrirVentanaModal('<html:rewrite action="/reportes/planes/consolidado" />?source=Plan&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />', "ConsolidadoDelPlan", 400, 330);
	}
	
	function trasladarMetas(){
		var nombreForma = '?nombreForma=' + 'trasladarMetasForm';
		
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />';
		var parametros = '&planId=' + planId;
		
		abrirVentanaModal('<html:rewrite action="/planes/perspectivas/trasladarMetas" />' + nombreForma + parametros, 'trasladarMetas', '560', '230');

	}

</script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<%-- Forma asociada al Action - Jsp --%>
<html:form action="/planes/perspectivas/gestionarPerspectivas" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="verIndicadoresLogroPlan" />
	<html:hidden property="seleccionadoId" />
	<html:hidden property="respuesta" />
	
	<input type="hidden" name="perspectivaVisualizarId">	
	<input type="hidden" name="perspectivaVisualizarNombre">		

	<bean:define id="tipoCalculoPerspectivaAutomatico">
		<bean:write name="gestionarPerspectivasForm" property="tipoCalculoPerspectivaAutomatico" />
	</bean:define>
	<bean:define id="tipoCalculoPerspectivaManual">
		<bean:write name="gestionarPerspectivasForm" property="tipoCalculoPerspectivaManual" />
	</bean:define>

	<bean:define id="mostrarBarraSuperior" type="String" value="false"></bean:define>
	<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.perspectivas.gestionarperspectivas.barrasuperior.mostrar" valor="true">
		<bean:define id="mostrarBarraSuperior" type="String" value="true"></bean:define>
	</vgcutil:valorPropertyIgual>

	<%-- Representación de la Forma --%>
	<vgcinterfaz:contenedorForma idContenedor="body-perspectiva" mostrarBarraSuperior="<%= mostrarBarraSuperior %>" >

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.gestionarperspectivas.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>
		
		

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

				<%-- Menú: Archivo --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">

						<%-- Validación: solo se puede crear Perspectiva si hay un elemento en la Plantilla de Planes --%>
						<logic:notEmpty name="gestionarPerspectivasForm" property="elementoPlantillaPlanes">
							<vgcinterfaz:botonMenu onclick="nuevaPerspectiva();" permisoId="PLAN_PERSPECTIVA_ADD">
								<vgcutil:message key="menu.edicion.nuevo" />: <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
							</vgcinterfaz:botonMenu>
						</logic:notEmpty>

						<%-- Validación: si la Perspectiva seleccionada NO es Raíz, entonces se modifica la Perspectiva  --%>
						<logic:notEmpty name="perspectiva" property="padreId" scope="session">
							<logic:equal scope="session" name="editarPerspectiva" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPerspectiva();" />
							</logic:equal>
							<logic:notEqual scope="session" name="editarPerspectiva" value="true">
								<logic:equal scope="session" name="verPerspectiva" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPerspectiva();" />
								</logic:equal>
							</logic:notEqual>
							<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiarPerspectiva();" permisoId="PLAN_PERSPECTIVA_COPY" />
						</logic:notEmpty>

						<%-- Validación: si la Perspectiva seleccionada SI es Raíz, entonces se modifica el Plan --%>
						<logic:empty name="perspectiva" property="padreId" scope="session">
							<logic:equal scope="session" name="editarPlan" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPlan();" />
							</logic:equal>
							<logic:notEqual scope="session" name="editarPlan" value="true">
								<logic:equal scope="session" name="verPlan" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPlan();" />
								</logic:equal>
							</logic:notEqual>
						</logic:empty>

						<%-- Validación: si la Perspectiva seleccionada NO es Raíz, entonces se permite Eliminar y Propiedades de la Perspectiva  --%>
						<logic:notEmpty name="perspectiva" property="padreId" scope="session">
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarPerspectiva();" permisoId="PLAN_PERSPECTIVA_DELETE" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesPerspectiva();" permisoId="PLAN_PERSPECTIVA" />
							<logic:equal name="perspectiva" property="tipoCalculo" value="<%= tipoCalculoPerspectivaAutomatico %>">
								<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="nodoArbolHijos" value="1">
									<vgcinterfaz:botonMenu key="menu.edicion.asignarpesos" onclick="asignarPesosIndicadoresPlanPerspectiva();" permisoId="PLAN_PESO" agregarSeparador="true" />
								</vgclogica:tamanoColeccionMayorQue>
							</logic:equal>
							<logic:equal name="perspectiva" property="tipoCalculo" value="<%= tipoCalculoPerspectivaManual %>">
								<logic:greaterThan name="paginaIndicadores" property="total" value="1">
									<vgcinterfaz:botonMenu key="menu.edicion.asignarpesos" onclick="asignarPesosIndicadoresPlanPerspectiva();" permisoId="PLAN_PESO" agregarSeparador="true" />
								</logic:greaterThan>
							</logic:equal>
						</logic:notEmpty>
						<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="PLAN_PERSPECTIVA_EMAIL" onclick="enviarEmailPerspecitva();" />						

					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">
						<vgcinterfaz:botonMenu onclick="gestionarIniciativasDePlan();" permisoId="INICIATIVA" agregarSeparador="true">
							<bean:write name="gestionarPlanForm" property="plantillaPlanes.nombreIniciativaPlural" />
						</vgcinterfaz:botonMenu>
						<vgcinterfaz:botonMenu key="menu.ver.modelo.causaefecto" onclick="gestionarModelo()" permisoId="PLAN_MODELO_VER" />
						<vgcinterfaz:botonMenu key="menu.ver.diseño.causaefecto" onclick="gestionarModeloCausaEfecto()" permisoId="PLAN_MODELO_DISENO" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ver.visualizarplan" onclick="visualizarPlan()" permisoId="PLAN_EJECUTIVO" agregarSeparador="true" />
						<%-- 
						<vgcinterfaz:botonMenu key="menu.ver.indicadoresasociadosproblemas" onclick="alert('Función no disponible')" permisoId="PROBLEMA" />
						<vgcinterfaz:botonMenu key="menu.ver.planesrelacionados" onclick="alert('Función no disponible')" permisoId="PLAN" agregarSeparador="true" />
						 --%>

						<%-- Validación: Se muestran los Indicadores de Logro del Plan --%>
						<logic:equal name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
							<vgcinterfaz:botonMenu key="menu.ver.indicadores.plan" icon="/componentes/menu/activo.gif" onclick="" permisoId="INDICADOR" />
						</logic:equal>
						<logic:notEqual name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
							<vgcinterfaz:botonMenu key="menu.ver.indicadores.plan" onclick="gestionarPerspectivas(true);" permisoId="INDICADOR" />
						</logic:notEqual>

						<%-- Validación: Se muestran los Indicadores de Logro de los Hijos del Plan --%>
						<logic:equal name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
							<logic:notEmpty name="perspectiva" property="padreId" scope="session">
								<vgcinterfaz:botonMenu key="menu.ver.plan" onclick="gestionarPerspectivas(false);" permisoId="PLAN" agregarSeparador="true" />
							</logic:notEmpty>
							<logic:empty name="perspectiva" property="padreId" scope="session">
								<vgcinterfaz:botonMenu key="menu.ver.plan" onclick="gestionarPerspectivas(false);" permisoId="PLAN" />
							</logic:empty>
						</logic:equal>
						<logic:notEqual name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
							<logic:notEmpty name="perspectiva" property="padreId" scope="session">
								<vgcinterfaz:botonMenu key="menu.ver.plan" icon="/componentes/menu/activo.gif" onclick="" permisoId="PLAN" agregarSeparador="true" />
							</logic:notEmpty>
							<logic:empty name="perspectiva" property="padreId" scope="session">
								<vgcinterfaz:botonMenu key="menu.ver.plan" icon="/componentes/menu/activo.gif" onclick="" permisoId="PLAN" />
							</logic:empty>
						</logic:notEqual>
						<logic:notEmpty name="perspectiva" property="padreId" scope="session">
							<vgcinterfaz:botonMenu key="menu.ver.objetivos.relacionados" onclick="visualizarPerspectivasRelacionadas();" permisoId="PLAN_PERSPECTIVA_RELACION" />
						</logic:notEmpty>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Reportes --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuReportes" key="menu.reportes">
						<vgcinterfaz:botonMenu key="menu.reportes.informe.plan.consolidado" onclick="reporteConsolidadoPlan()" permisoId="PLAN_REPORTE" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.reportes.informe.plan.objetivos" onclick="reporteEjecucionPlan()" permisoId="PLAN_REPORTE" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.reportes.informe.plan.meta" onclick="reporteMetaPlan()" permisoId="PLAN_REPORTE" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.reportes.informe.plan.explicaciones" onclick="reporteExplicacionesPlan()" permisoId="PLAN_REPORTE" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				
				<%-- Menú: Mediciones --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuMediciones" key="menu.mediciones">
						<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularPlan()" permisoId="INDICADOR_MEDICION_CALCULAR" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				
				<%-- Menú: Plan --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuPlan" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.mediciones.copiar.metas" onclick="trasladarMetas()" permisoId="INDICADOR_MEDICION_CALCULAR" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarPerspectivas">

				<%-- Validación: solo se puede crear Perspectiva si hay un elemento en la Plantilla de Planes --%>
				<logic:notEmpty name="gestionarPerspectivasForm" property="elementoPlantillaPlanes">
					<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_PERSPECTIVA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevaPerspectiva();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />: <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEmpty>

				<%-- Validación: si la Perspectiva seleccionada NO es Raíz, entonces se modifica la Perspectiva  --%>
				<logic:notEmpty name="perspectiva" property="padreId" scope="session">
					<logic:equal scope="session" name="editarPerspectiva" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarPerspectiva();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
					<logic:notEqual scope="session" name="editarPerspectiva" value="true">
						<logic:equal scope="session" name="verPerspectiva" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarPerspectiva();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</logic:notEqual>
				</logic:notEmpty>

				<%-- Validación: si la Perspectiva seleccionada SI es Raíz, entonces se modifica el Plan --%>
				<logic:empty name="perspectiva" property="padreId" scope="session">
					<logic:equal scope="session" name="editarPlan" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarPlan();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
					<logic:notEqual scope="session" name="editarPlan" value="true">
						<logic:equal scope="session" name="verPlan" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarPlan();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</logic:notEqual>
				</logic:empty>

				<%-- Validación: si la Perspectiva seleccionada NO es Raíz, entonces se permite Eliminar y Propiedades de la Perspectiva  --%>
				<logic:notEmpty name="perspectiva" property="padreId" scope="session">
					<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_PERSPECTIVA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarPerspectiva();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesPerspectiva();">
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.propiedades" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEmpty>

				<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_MODELO_VER" nombreImagen="modelo" pathImagenes="/componentes/barraHerramientas/" nombre="modelo" onclick="javascript:gestionarModelo();">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.ver.modelo.causaefecto" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				
				<logic:notEmpty name="perspectiva" property="padreId" scope="session">
					<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_PERSPECTIVA_RELACION" nombreImagen="relacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="relacion" onclick="javascript:visualizarPerspectivasRelacionadas();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.objetivos.relacionados" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEmpty>
				
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>

				<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_PERSPECTIVA_EMAIL" nombreImagen="email" pathImagenes="/componentes/barraHerramientas/" nombre="email" onclick="javascript:enviarEmailPerspecitva();">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.email" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				
				<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar" pathImagenes="/componentes/barraHerramientas/" nombre="refrescar" onclick="javascript:actualizarPerspectivas();">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.ver.refrescar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>

			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>
		
		<%-- Este es el "Visor Tipo Arbol" --%>
		<treeview:treeview 
			useFrame="false" 
			name="arbolPerspectivas" 
			scope="session" 
			baseObject="perspectivaRoot" 
			isRoot="true" 
			fieldName="Nombre" 
			fieldId="perspectivaId" 
			fieldChildren="hijos" 
			lblUrlObjectId="perspectivaId" 
			styleClass="treeview" 
			checkbox="false" 
			pathImages="/paginas/strategos/planes/imagenes/gestionarPerspectivas/" 
			urlJs="/componentes/visorArbol/visorArbol.js" 
			nameSelectedId="perspectivaId" 
			urlName="javascript:seleccionarNodo(perspectivaId, marcadorAncla);" 
			urlConnectorOpen="javascript:openNodo(perspectivaId, marcadorAncla);" 
			urlConnectorClose="javascript:closeNodo(perspectivaId, marcadorAncla);" 
			lblUrlAnchor="marcadorAncla" 
			useNodeImage="true" />

		<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.perspectivas.gestionarperspectivas.barrainferior.mostrar" valor="true">
			<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
			<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			</vgcinterfaz:contenedorFormaBarraInferior>
		</vgcutil:valorPropertyIgual>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-perspectiva'), 180);
</script>
<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.perspectivas.gestionarperspectivas.barrainferior.mostrar" valor="true">
<script>
	<%-- Arma la descripción al final del árbol --%>
    var variable = document.getElementById('barraInferior');
    var nombrePerspectiva = '<bean:write name="perspectiva" property="nombre" />';
    variable.innerHTML = "<b><vgcutil:message key='jsp.gestionararbol.nodoseleccionado' /></b>: [" + nombrePerspectiva.toLowerCase() + "]";
</script>
</vgcutil:valorPropertyIgual>
