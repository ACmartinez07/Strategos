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
		..:: <vgcutil:message key="jsp.reportes.iniciativa.ejecucion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			function init()
			{
				document.getElementById("checkIniciativas").checked = document.reporteForm.visualizarIniciativas.value == "true" ? true : false;
				document.getElementById("checkIniciativasEjecutado").checked = document.reporteForm.visualizarIniciativasEjecutado.value == "true" ? true : false;
				document.getElementById("checkIniciativasMeta").checked = document.reporteForm.visualizarIniciativasMeta.value == "true" ? true : false;
				document.getElementById("checkIniciativasAlerta").checked = document.reporteForm.visualizarIniciativasAlerta.value == "true" ? true : false;
				document.getElementById("checkActividad").checked = document.reporteForm.visualizarActividad.value == "true" ? true : false;
				document.getElementById("checkActividadEjecutado").checked = document.reporteForm.visualizarActividadEjecutado.value == "true" ? true : false;
				document.getElementById("checkActividadMeta").checked = document.reporteForm.visualizarActividadMeta.value == "true" ? true : false;
				document.getElementById("checkActividadAlerta").checked = document.reporteForm.visualizarActividadAlerta.value == "true" ? true : false;
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
				url = url + '&visualizarIniciativas=' + (document.getElementById("checkIniciativas").checked ? "1" : "0");
				url = url + '&visualizarIniciativasEjecutado=' + (document.getElementById("checkIniciativasEjecutado").checked ? "1" : "0");
				url = url + '&visualizarIniciativasMeta=' + (document.getElementById("checkIniciativasMeta").checked ? "1" : "0");
				url = url + '&visualizarIniciativasAlerta=' + (document.getElementById("checkIniciativasAlerta").checked ? "1" : "0");
				url = url + '&visualizarActividad=' + (document.getElementById("checkActividad").checked ? "1" : "0");
				url = url + '&visualizarActividadEjecutado=' + (document.getElementById("checkActividadEjecutado").checked ? "1" : "0");
				url = url + '&visualizarActividadMeta=' + (document.getElementById("checkActividadMeta").checked ? "1" : "0");
				url = url + '&visualizarActividadAlerta=' + (document.getElementById("checkActividadAlerta").checked ? "1" : "0");
				url = url + '&filtroNombre=' + document.reporteForm.filtroNombre.value;
				url = url + '&selectHitoricoType=' + document.reporteForm.selectHitoricoType.value;
				url = url + '&orientacion=H';
				url = url + '&tipo='+document.reporteForm.tipo.value;
				url = url + '&estatus='+document.reporteForm.estatus.value;
				url = url + '&ano=' +document.reporteForm.ano.value;
												
				if(document.reporteForm.todosAno.checked==true){
					url = url + '&todos=' + true;
				}else{
					url = url + '&todos=' + false;
				}
				
	    	 	abrirReporte('<html:rewrite action="/reportes/iniciativas/ejecucionReporte"/>' + url);
	        	window.close();
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

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/planes/ejecucion">

			<html:hidden property="nombrePlan" />
			<html:hidden property="planId" />
			<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			<html:hidden property="visualizarIniciativas" />
			<html:hidden property="visualizarIniciativasEjecutado" />
			<html:hidden property="visualizarIniciativasMeta" />
			<html:hidden property="visualizarIniciativasAlerta" />
			<html:hidden property="visualizarActividad" />
			<html:hidden property="visualizarActividadEjecutado" />
			<html:hidden property="visualizarActividadMeta" />
			<html:hidden property="visualizarActividadAlerta" />
			
			<input type='hidden' name='filtroNombre' value='<bean:write name="reporteForm" property="filtro.nombre" />'>
			<input type='hidden' name='selectHitoricoType' value='<bean:write name="reporteForm" property="filtro.historico" />'>
			
			<vgcinterfaz:contenedorForma width="460px" height="460px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.detallado.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="340px" width="400px" nombre="reporteCriterios">
				
				<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<logic:notEmpty name="reporteForm" property="planId">
								<tr>
									<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.plan" /> : </td>
									<td colspan="1"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
								</tr>
							</logic:notEmpty>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
									<bean:define id="alcanceObjetivo" toScope="page">
										<bean:write name="reporteForm" property="alcanceObjetivo" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceObjetivo %>">
										<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector.objetivo" />
									</html:radio>
								</td>
							</tr>
							<logic:notEmpty name="reporteForm" property="planId">
								<tr>
									<td colspan="3">
										<bean:define id="alcancePlan" toScope="page">
											<bean:write name="reporteForm" property="alcancePlan" />
										</bean:define>
										<html:radio property="alcance" value="<%= alcancePlan %>">
											<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector.plan" />
										</html:radio>
									</td>
								</tr>
							</logic:notEmpty>
							<logic:empty name="reporteForm" property="planId">
								<tr>
									<td colspan="3">
										<bean:define id="alcanceOrganizacion" toScope="page">
											<bean:write name="reporteForm" property="alcanceOrganizacion" />
										</bean:define>
										<html:radio property="alcance" value="<%= alcanceOrganizacion %>">
											<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector.organizacion" />
										</html:radio>
									</td>
								</tr>
							</logic:empty>
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
										&nbsp;
										<vgcutil:message key="jsp.editariniciativa.ficha.anioformulacion" />
										&nbsp;&nbsp;
										<bean:define id="anoCalculo" toScope="page">
											<bean:write name="reporteForm" property="ano" />
										</bean:define>
										<html:select property="ano" value="<%= anoCalculo %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select>
								</td>
							</tr>		
							
							<tr>
								<%-- Visible --%>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.editariniciativa.ficha.todos.ano" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<html:checkbox styleClass="botonSeleccionMultiple" property="todosAno" />
								</td>
								
							</tr>												
																				
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.editariniciativa.ficha.tipoproyecto" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="reporteForm" property='tipo' id="tipo" >																	
										<logic:iterate name="reporteForm" property="tipos" id="tip">
											<bean:define id="tipoProyectoId" toScope="page"><bean:write name='tip' property='tipoProyectoId' /></bean:define>
											<bean:define id="nombre" toScope="page"><bean:write name='tip' property='nombre' /></bean:define>											
											<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>											
										</logic:iterate>
										<option value="0" selected>Todos</option>		
									</select>
								
								</td>
							</tr>
														
														
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.visualizariniciativasplan.columna.estatus" />
									&nbsp;
									&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="estatus" id="estatus" property="estatus" width="5px">
											<option value="0" selected><vgcutil:message key="estado.sin.iniciar" /></option>
											<option value="1" selected><vgcutil:message key="estado.sin.iniciar.desfasada" /></option>
											<option value="2" selected><vgcutil:message key="estado.en.ejecucion.sin.retrasos" /></option>
											<option value="3" selected><vgcutil:message key="estado.en.ejecucion.con.retrasos.superables" /></option>
											<option value="4" selected><vgcutil:message key="estado.en.ejecucion.con.retrasos.significativos" /></option>
											<option value="5" selected><vgcutil:message key="estado.concluidas" /></option>
											<option value="6" selected>Cancelado</option>
											<option value="7" selected>Suspendido</option>
											<option value="8" selected>Todos</option>
									</select>
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

					<%-- Panel: Parametros --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="parametros">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.titulo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="1"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<logic:notEmpty name="reporteForm" property="planId">
								<tr>
									<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.plan" /> : </td>
									<td colspan="3"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
								</tr>
							</logic:notEmpty>
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							<!-- Encabezado selector de fechas -->
							<tr>
								<td align="center" colspan="1"></td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.mes" /> </b> </td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.ano" /> </b> </td>
							</tr>
														
							<!-- Fecha Inicio -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.desde" /> : </td>
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
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.hasta" /> : </td>
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
					
					

					<%-- Panel: Visualizar --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="visualizar">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="1" width="300"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<logic:notEmpty name="reporteForm" property="planId">
								<tr>
									<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.plan" /> : </td>
									<td colspan="15" width="300"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
								</tr>
							</logic:notEmpty>
							
							<tr>
								<td colspan="15"><hr width="100%"></td>
							</tr>
							<!-- Objetivo -->
							<tr>
								<td colspan="15" width="300">
									<%-- Contenedor de Paneles --%>
									<vgcinterfaz:contenedorPaneles height="220" width="375" nombre="visualizarObjetos">
									
										<!-- Iniciativas -->
										<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="iniciativas">
											<%-- Título del Panel: Datos Básicos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.iniciativas" />
											</vgcinterfaz:panelContenedorTitulo>
					
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativas" id="checkIniciativas" onclick="eventoOnclickVisualizarIniciativas()">
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.iniciativas" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasEjecutado" id="checkIniciativasEjecutado" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.iniciativas.ejecutado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasMeta" id="checkIniciativasMeta" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.iniciativas.programado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkIniciativasAlerta" id="checkIniciativasAlerta" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.iniciativas.alerta" />
													</td>
												</tr>
												<!-- Actividades -->
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividad" id="checkActividad" onclick="eventoOnclickVisualizarActividades()" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.actividades" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadEjecutado" id="checkActividadEjecutado" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.actividades.ejecutado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadMeta" id="checkActividadMeta" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.actividades.programado" />
													</td>
												</tr>
												<tr>
													<td colspan="3">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="checkbox" name="checkActividadAlerta" id="checkActividadAlerta" disabled>
														<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.actividades.alerta" />
													</td>
												</tr>
											</table>
										</vgcinterfaz:panelContenedor>
									</vgcinterfaz:contenedorPaneles>
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