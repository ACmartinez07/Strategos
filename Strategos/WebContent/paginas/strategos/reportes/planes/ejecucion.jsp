<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Gustavo Chaparro (01/09/2013) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

		<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.reportes.plan.ejecucion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
		function init()
		{
			document.getElementById("checkObjetivo").checked = document.reporteForm.visualizarObjetivo.value == "true" ? true : false;
			document.getElementById("checkObjetivoEstadoParcial").checked = document.reporteForm.visualizarObjetivoEstadoParcial.value == "true" ? true : false;
			document.getElementById("checkObjetivoEstadoAnual").checked = document.reporteForm.visualizarObjetivoEstadoAnual.value == "true" ? true : false;
			document.getElementById("checkObjetivoAlerta").checked = document.reporteForm.visualizarObjetivoAlerta.value == "true" ? true : false;
			document.getElementById("checkIndicadores").checked = document.reporteForm.visualizarIndicadores.value == "true" ? true : false;
			document.getElementById("checkIndicadoresEjecutado").checked = document.reporteForm.visualizarIndicadoresEjecutado.value == "true" ? true : false;
			document.getElementById("checkIndicadoresMeta").checked = document.reporteForm.visualizarIndicadoresMeta.value == "true" ? true : false;
			document.getElementById("checkIndicadoresEstadoParcial").checked = document.reporteForm.visualizarIndicadoresEstadoParcial.value == "true" ? true : false;
			document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked = document.reporteForm.visualizarIndicadoresEstadoParcialSuavisado.value == "true" ? true : false;
			document.getElementById("checkIndicadoresEstadoAnual").checked = document.reporteForm.visualizarIndicadoresEstadoAnual.value == "true" ? true : false;
			document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked = document.reporteForm.visualizarIndicadoresEstadoAnualSuavisado.value == "true" ? true : false;
			document.getElementById("checkIndicadoresAlerta").checked = document.reporteForm.visualizarIndicadoresAlerta.value == "true" ? true : false;
			document.getElementById("checkIniciativas").checked = document.reporteForm.visualizarIniciativas.value == "true" ? true : false;
			document.getElementById("checkIniciativasEjecutado").checked = document.reporteForm.visualizarIniciativasEjecutado.value == "true" ? true : false;
			document.getElementById("checkIniciativasMeta").checked = document.reporteForm.visualizarIniciativasMeta.value == "true" ? true : false;
			document.getElementById("checkIniciativasAlerta").checked = document.reporteForm.visualizarIniciativasAlerta.value == "true" ? true : false;
			document.getElementById("checkActividad").checked = document.reporteForm.visualizarActividad.value == "true" ? true : false;
			document.getElementById("checkActividadEjecutado").checked = document.reporteForm.visualizarActividadEjecutado.value == "true" ? true : false;
			document.getElementById("checkActividadMeta").checked = document.reporteForm.visualizarActividadMeta.value == "true" ? true : false;
			document.getElementById("checkActividadAlerta").checked = document.reporteForm.visualizarActividadAlerta.value == "true" ? true : false;
			eventoOnclickVisualizarObjetivo();
			eventoOnclickVisualizarIndicadores();
			eventoOnclickVisualizarIniciativas();
			eventoOnclickVisualizarActividades();
		}

		function cancelar() 
		{
			window.close();						
		}
		
		function generarReporte() 
		{
		 	var desde = parseInt(document.reporteForm.anoInicial.value + "" + (document.reporteForm.mesInicial.value.length == 1 ? "00" : (document.reporteForm.mesInicial.value.length == 2 ? "0" : "")) + document.reporteForm.mesInicial.value);
			var hasta = parseInt(document.reporteForm.anoFinal.value + "" + (document.reporteForm.mesFinal.value.length == 1 ? "00" : (document.reporteForm.mesFinal.value.length == 2 ? "0" : "")) + document.reporteForm.mesFinal.value);
			if (hasta<desde) 
			{
				alert('<vgcutil:message key="jsp.alerta.rango.fechas.invalido" /> ');
				return false;
			} 
			
			<%-- Validacion del Tipo de Reporte --%>
			var url = '?mesInicial=' + document.reporteForm.mesInicial.value; 
			url = url + '&anoInicial=' + document.reporteForm.anoInicial.value; 
			url = url + '&mesFinal=' + document.reporteForm.mesFinal.value; 
			url = url + '&anoFinal=' + document.reporteForm.anoFinal.value;
			url = url + '&source=' + document.reporteForm.source.value;
			url = url + '&planId=' + document.reporteForm.planId.value;
			url = url + '&objetoSeleccionadoId=' + document.reporteForm.objetoSeleccionadoId.value;
			url = url + '&alcance=' + (document.reporteForm.alcance[0].checked ? "1" : "2");
			url = url + '&visualizarObjetivo=' + (document.getElementById("checkObjetivo").checked ? "1" : "0");
			url = url + '&visualizarObjetivoEstadoParcial=' + (document.getElementById("checkObjetivoEstadoParcial").checked ? "1" : "0");
			url = url + '&visualizarObjetivoEstadoAnual=' + (document.getElementById("checkObjetivoEstadoAnual").checked ? "1" : "0");
			url = url + '&visualizarObjetivoAlerta=' + (document.getElementById("checkObjetivoAlerta").checked ? "1" : "0");
			url = url + '&visualizarIndicadores=' + (document.getElementById("checkIndicadores").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresEjecutado=' + (document.getElementById("checkIndicadoresEjecutado").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresMeta=' + (document.getElementById("checkIndicadoresMeta").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresEstadoParcial=' + (document.getElementById("checkIndicadoresEstadoParcial").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresEstadoParcialSuavisado=' + (document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresEstadoAnual=' + (document.getElementById("checkIndicadoresEstadoAnual").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresEstadoAnualSuavisado=' + (document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked ? "1" : "0");
			url = url + '&visualizarIndicadoresAlerta=' + (document.getElementById("checkIndicadoresAlerta").checked ? "1" : "0");
			url = url + '&visualizarIniciativas=' + (document.getElementById("checkIniciativas").checked ? "1" : "0");
			url = url + '&visualizarIniciativasEjecutado=' + (document.getElementById("checkIniciativasEjecutado").checked ? "1" : "0");
			url = url + '&visualizarIniciativasMeta=' + (document.getElementById("checkIniciativasMeta").checked ? "1" : "0");
			url = url + '&visualizarIniciativasAlerta=' + (document.getElementById("checkIniciativasAlerta").checked ? "1" : "0");
			url = url + '&visualizarActividad=' + (document.getElementById("checkActividad").checked ? "1" : "0");
			url = url + '&visualizarActividadEjecutado=' + (document.getElementById("checkActividadEjecutado").checked ? "1" : "0");
			url = url + '&visualizarActividadMeta=' + (document.getElementById("checkActividadMeta").checked ? "1" : "0");
			url = url + '&visualizarActividadAlerta=' + (document.getElementById("checkActividadAlerta").checked ? "1" : "0");
			url = url + "&orientacion=H";
			var selectHitoricoType = document.getElementById('selectHitoricoType');
			if (selectHitoricoType != null)
				url = url + '&selectHitoricoType=' + selectHitoricoType.value;

			if (document.reporteForm.tipoReporte[0].checked)
				abrirReporte('<html:rewrite action="/reportes/planes/ejecucionReporte"/>' + url);
			else if (document.reporteForm.tipoReporte[1].checked)
				abrirReporte('<html:rewrite action="/reportes/planes/ejecucionReporteXls"/>' + url);
			
    	 	cancelar();
		}
		
		function eventoOnclickVisualizarObjetivo()
		{
			if (document.getElementById("checkObjetivo").checked)
			{
				document.getElementById("checkObjetivoEstadoParcial").disabled = false;
				document.getElementById("checkObjetivoEstadoAnual").disabled = false;
				document.getElementById("checkObjetivoAlerta").disabled = false;

				document.getElementById("checkObjetivoEstadoParcial").checked = true;
				document.getElementById("checkObjetivoEstadoAnual").checked = true;
				document.getElementById("checkObjetivoAlerta").checked = true;
			}
			else
			{
				document.getElementById("checkObjetivoEstadoParcial").checked = false;
				document.getElementById("checkObjetivoEstadoAnual").checked = false;
				document.getElementById("checkObjetivoAlerta").checked = false;
				
				document.getElementById("checkObjetivoEstadoParcial").disabled = true;
				document.getElementById("checkObjetivoEstadoAnual").disabled = true;
				document.getElementById("checkObjetivoAlerta").disabled = true;
			}
		}
		
		function eventoOnclickVisualizarIndicadores()
		{
			if (document.getElementById("checkIndicadores").checked)
			{
				document.getElementById("checkIndicadoresEjecutado").disabled = false;
				document.getElementById("checkIndicadoresMeta").disabled = false;
				document.getElementById("checkIndicadoresEstadoParcial").disabled = false;
				document.getElementById("checkIndicadoresEstadoAnual").disabled = false;
				document.getElementById("checkIndicadoresAlerta").disabled = false;

				document.getElementById("checkIndicadoresEjecutado").checked = true;
				document.getElementById("checkIndicadoresMeta").checked = true;
				document.getElementById("checkIndicadoresEstadoParcial").checked = true;
				document.getElementById("checkIndicadoresEstadoAnual").checked = true;
				document.getElementById("checkIndicadoresAlerta").checked = true;

				document.getElementById("checkIndicadoresEstadoParcialSuavisado").disabled = false;
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked = true;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").disabled = false;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked = true;
			}
			else
			{
				document.getElementById("checkIndicadoresEjecutado").checked = false;
				document.getElementById("checkIndicadoresMeta").checked = false;
				document.getElementById("checkIndicadoresEstadoParcial").checked = false;
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked = false;
				document.getElementById("checkIndicadoresEstadoAnual").checked = false;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked = false;
				document.getElementById("checkIndicadoresAlerta").checked = false;

				document.getElementById("checkIndicadoresEjecutado").disabled = true;
				document.getElementById("checkIndicadoresMeta").disabled = true;
				document.getElementById("checkIndicadoresEstadoParcial").disabled = true;
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").disabled = true;
				document.getElementById("checkIndicadoresEstadoAnual").disabled = true;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").disabled = true;
				document.getElementById("checkIndicadoresAlerta").disabled = true;
			}
		}
		
		function eventoOnclickVisualizarIniciativas()
		{
			if (document.getElementById("checkIniciativas").checked)
			{
				document.getElementById("checkIniciativasEjecutado").disabled = false;
				document.getElementById("checkIniciativasMeta").disabled = false;
				document.getElementById("checkIniciativasAlerta").disabled = false;
				document.getElementById("checkActividad").disabled = false;
				document.getElementById("checkActividadEjecutado").disabled = false;
				document.getElementById("checkActividadMeta").disabled = false;
				document.getElementById("checkActividadAlerta").disabled = false;
				document.getElementById("selectHitoricoType").disabled = false;
				
				document.getElementById("checkIniciativasEjecutado").checked = true;
				document.getElementById("checkIniciativasMeta").checked = true;
				document.getElementById("checkIniciativasAlerta").checked = true;
				document.getElementById("checkActividad").checked = true;
				document.getElementById("checkActividadEjecutado").checked = true;
				document.getElementById("checkActividadMeta").checked = true;
				document.getElementById("checkActividadAlerta").checked = true;
			}
			else
			{
				document.getElementById("checkIniciativasEjecutado").checked = false;
				document.getElementById("checkIniciativasMeta").checked = false;
				document.getElementById("checkIniciativasAlerta").checked = false;
				document.getElementById("checkActividad").checked = false;
				document.getElementById("checkActividadEjecutado").checked = false;
				document.getElementById("checkActividadMeta").checked = false;
				document.getElementById("checkActividadAlerta").checked = false;

				document.getElementById("checkIniciativasEjecutado").disabled = true;
				document.getElementById("checkIniciativasMeta").disabled = true;
				document.getElementById("checkIniciativasAlerta").disabled = true;
				document.getElementById("checkActividad").disabled = true;
				document.getElementById("checkActividadEjecutado").disabled = true;
				document.getElementById("checkActividadMeta").disabled = true;
				document.getElementById("checkActividadAlerta").disabled = true;
				document.getElementById("selectHitoricoType").disabled = true;
			}
		}

		function eventoOnclickVisualizarActividades()
		{
			if (document.getElementById("checkActividad").checked)
			{
				document.getElementById("checkActividadEjecutado").disabled = false;
				document.getElementById("checkActividadMeta").disabled = false;
				document.getElementById("checkActividadAlerta").disabled = false;

				document.getElementById("checkActividadEjecutado").checked = true;
				document.getElementById("checkActividadMeta").checked = true;
				document.getElementById("checkActividadAlerta").checked = true;
			}
			else
			{
				document.getElementById("checkActividadEjecutado").checked = false;
				document.getElementById("checkActividadMeta").checked = false;
				document.getElementById("checkActividadAlerta").checked = false;

				document.getElementById("checkActividadEjecutado").disabled = true;
				document.getElementById("checkActividadMeta").disabled = true;
				document.getElementById("checkActividadAlerta").disabled = true;
			}
		}
		
		function eventoOnclickVisualizarIndicadoresEstadoParcialSuavisado()
		{
			if (document.getElementById("checkIndicadoresEstadoParcial").checked)
			{
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").disabled = false;
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked = true;
			}
			else
			{
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").checked = false;
				document.getElementById("checkIndicadoresEstadoParcialSuavisado").disabled = true;
			}
		}

		function eventoOnclickVisualizarIndicadoresEstadoAnualSuavisado()
		{
			if (document.getElementById("checkIndicadoresEstadoAnual").checked)
			{
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").disabled = false;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked = true;
			}
			else
			{
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").checked = false;
				document.getElementById("checkIndicadoresEstadoAnualSuavisado").disabled = true;
			}
		}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/planes/ejecucion">

			<html:hidden property="nombrePlan" />
			<html:hidden property="planId" />
			<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			<html:hidden property="visualizarObjetivo" />
			<html:hidden property="visualizarObjetivoEstadoParcial" />
			<html:hidden property="visualizarObjetivoEstadoAnual" />
			<html:hidden property="visualizarObjetivoAlerta" />
			<html:hidden property="visualizarIndicadores" />
			<html:hidden property="visualizarIndicadoresEjecutado" />
			<html:hidden property="visualizarIndicadoresMeta" />
			<html:hidden property="visualizarIndicadoresEstadoParcial" />
			<html:hidden property="visualizarIndicadoresEstadoParcialSuavisado" />
			<html:hidden property="visualizarIndicadoresEstadoAnual" />
			<html:hidden property="visualizarIndicadoresEstadoAnualSuavisado" />
			<html:hidden property="visualizarIndicadoresAlerta" />
			<html:hidden property="visualizarIniciativas" />
			<html:hidden property="visualizarIniciativasEjecutado" />
			<html:hidden property="visualizarIniciativasMeta" />
			<html:hidden property="visualizarIniciativasAlerta" />
			<html:hidden property="visualizarActividad" />
			<html:hidden property="visualizarActividadEjecutado" />
			<html:hidden property="visualizarActividadMeta" />
			<html:hidden property="visualizarActividadAlerta" />
			
			<vgcinterfaz:contenedorForma width="460px" height="460px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.plan.ejecucion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="340px" width="400px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="parametros">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.titulo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
							</tr>
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							<!-- Encabezado selector de fechas -->
							<tr>
								<td align="center" colspan="1"></td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.mes" /> </b> </td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.ano" /> </b> </td>
							</tr>
														
							<!-- Fecha Inicio -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.desde" /> : </td>
								<td>
									<html:select property="mesInicial" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoMeses" value="clave" label="valor" />
									</html:select>
								</td>
								<td>
									<html:select property="anoInicial" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								
								</td>
							</tr>
							<!-- Fecha Final -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.hasta" /> : </td>
								<td >
									<html:select property="mesFinal" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoMeses" value="clave" label="valor" />
									</html:select>
								</td>
								<td>
									<html:select property="anoFinal" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
									<bean:define id="alcanceObjetivo" toScope="page">
										<bean:write name="reporteForm" property="alcanceObjetivo" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceObjetivo %>">
										<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.selector.objetivo" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<bean:define id="alcancePlan" toScope="page">
										<bean:write name="reporteForm" property="alcancePlan" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcancePlan %>">
										<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.selector.plan" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Visualizar --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="visualizar">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" />:</td>
								<td colspan="15" width="300"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="15" width="300"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
							</tr>
							
							<tr>
								<td colspan="15"><hr width="100%"></td>
							</tr>
							<!-- Objetivo -->
							<tr>
								<td colspan="15" width="300">
									<%-- Contenedor de Paneles --%>
									<vgcinterfaz:contenedorPaneles height="220" width="375" nombre="visualizarObjetos">
									
										<%-- Panel: Objetivos --%>
										<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="objetivos">
											<%-- Título del Panel: Datos Básicos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.objetivos" />
											</vgcinterfaz:panelContenedorTitulo>
					
											<table class="panelContenedor panelContenedorTabla">
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;
														<input type="checkbox" name="checkObjetivo" id="checkObjetivo" onclick="eventoOnclickVisualizarObjetivo()">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.objetivos" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkObjetivoEstadoParcial" id="checkObjetivoEstadoParcial">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.objetivos.estadoparcial" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkObjetivoEstadoAnual" id="checkObjetivoEstadoAnual">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.objetivos.estadoanual" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkObjetivoAlerta" id="checkObjetivoAlerta">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.objetivos.alerta" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
											</table>
										</vgcinterfaz:panelContenedor>
							
										<!-- Indicadores -->
										<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="indicadores">
											<%-- Título del Panel: Datos Básicos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores" />
											</vgcinterfaz:panelContenedorTitulo>
					
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadores" id="checkIndicadores" onclick="eventoOnclickVisualizarIndicadores()">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresEjecutado" id="checkIndicadoresEjecutado" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.ejecutado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresMeta" id="checkIndicadoresMeta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.meta" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresEstadoParcial" id="checkIndicadoresEstadoParcial" onclick="eventoOnclickVisualizarIndicadoresEstadoParcialSuavisado()" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.estadoparcial" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresEstadoParcialSuavisado" id="checkIndicadoresEstadoParcialSuavisado" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.estadoparcial.suavisado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresEstadoAnual" id="checkIndicadoresEstadoAnual" onclick="eventoOnclickVisualizarIndicadoresEstadoAnualSuavisado()" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.estadoanual" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresEstadoAnualSuavisado" id="checkIndicadoresEstadoAnualSuavisado" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.estadoanual.suavisado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIndicadoresAlerta" id="checkIndicadoresAlerta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores.alerta" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;
													</td>
												</tr>
											</table>
										</vgcinterfaz:panelContenedor>

										<!-- Iniciativas -->
										<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="iniciativas">
											<%-- Título del Panel: Datos Básicos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas" />
											</vgcinterfaz:panelContenedorTitulo>
					
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativas" id="checkIniciativas" onclick="eventoOnclickVisualizarIniciativas()">
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas" />
													</td>
												</tr>
												<tr>
													<td style="padding-left: 25px;" colspan="3">
														<select class="cuadroCombinado" name="selectHitoricoType" id="selectHitoricoType">
															<option value="0" selected><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.todos.historico" /></option>
															<option value="2"><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.historico" /></option>
															<option value="1"><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.no.historico" /></option>
														</select>
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasEjecutado" id="checkIniciativasEjecutado" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas.ejecutado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasMeta" id="checkIniciativasMeta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas.programado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasAlerta" id="checkIniciativasAlerta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas.alerta" />
													</td>
												</tr>
												<!-- Actividades -->
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividad" id="checkActividad" onclick="eventoOnclickVisualizarActividades()" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.actividades" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadEjecutado" id="checkActividadEjecutado" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.actividades.ejecutado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadMeta" id="checkActividadMeta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.actividades.programado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadAlerta" id="checkActividadAlerta" disabled>
														<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.actividades.alerta" />
													</td>
												</tr>
											</table>
										</vgcinterfaz:panelContenedor>
									</vgcinterfaz:contenedorPaneles>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
					
					<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.salida" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombreOrganizacion" />', 30);</script></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombrePlan" />', 30);</script></b></td> 
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<!-- Tipo Reporte -->
							<tr>
								<td align="center" colspan="3"><b><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" /></b></td>
							</tr>
														
							<tr>
								<td colspan="3">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />	
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
					
					
					
				</vgcinterfaz:contenedorPaneles>
				
				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarReporte();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<%-- Cancelar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			init();
		</script>
	</tiles:put>
</tiles:insert>