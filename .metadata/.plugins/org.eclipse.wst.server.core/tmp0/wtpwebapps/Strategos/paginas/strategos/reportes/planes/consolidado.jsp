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
		..:: <vgcutil:message key="jsp.reportes.plan.consolidado.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
				<%-- Validacion del Tipo de Reporte --%>
				var url = '?mes=' + document.reporteForm.mesInicial.value; 
				url = url + '&ano=' + document.reporteForm.anoInicial.value; 
				url = url + '&planId=' + document.reporteForm.planId.value;
				url = url + '&objetoSeleccionadoId=' + document.reporteForm.objetoSeleccionadoId.value;
				url = url + '&alcance=' + (document.reporteForm.alcance[0].checked ? "1" : "2");
				url = url + '&tipoPeriodo=' + (document.reporteForm.tipoPeriodo[0].checked ? "1" : "2");
				url = url + '&visualizarIndicadores=' + (document.getElementById("checkIndicadores").checked ? "1" : "0");
				url = url + '&visualizarIniciativas=' + (document.getElementById("checkIniciativas").checked ? "1" : "0");
				url = url + '&visualizarActividades=' + (document.getElementById("checkActividad").checked ? "1" : "0");
				url = url + '&selectHitoricoType=' + (document.reporteForm.historico[0].checked ? "0" : (document.reporteForm.historico[1].checked ? "2" : "1"));
				url = url + "&orientacion=H";
				
				if (document.reporteForm.tipoReporte[0].checked)
	    	 		abrirReporte('<html:rewrite action="/reportes/planes/consolidadoReportePdf"/>' + url, 'Reporte');
				else if (document.reporteForm.tipoReporte[1].checked)
					abrirReporte('<html:rewrite action="/reportes/planes/consolidadoReporteXls"/>' + url, 'Reporte');
	    	 	cancelar();
			}
			
			function eventoOnclickTipoPeriodo()
			{
				if (document.reporteForm.tipoPeriodo[0].checked)
				{
					document.reporteForm.mesInicial.disabled = true;
					document.reporteForm.anoInicial.disabled = true;
				}
				else
				{
					document.reporteForm.mesInicial.disabled = false;
					document.reporteForm.anoInicial.disabled = false;
				}
			}
			
			function eventoOnclickVisualizarIniciativas()
			{
				if (document.getElementById("checkIniciativas").checked)
				{
					document.reporteForm.historico[0].disabled = false;
					document.reporteForm.historico[1].disabled = false;
					document.reporteForm.historico[2].disabled = false;
				}
				else
				{
					document.reporteForm.historico[0].disabled = true;
					document.reporteForm.historico[1].disabled = true;
					document.reporteForm.historico[2].disabled = true;
				}
			}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/planes/meta">

			<html:hidden property="planId" />
			<html:hidden property="objetoSeleccionadoId" />
			
			<vgcinterfaz:contenedorForma width="360px" height="310px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.plan.consolidado.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles width="310px" height="190px" nombre="reporteCriterios">

					<%-- Panel: Periodos --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="parametros">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.periodo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">

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
								<td colspan="4"><hr width="100%"></td>
							</tr>
							
							<!-- Periodos -->
							<tr>
								<td colspan="4">
									<bean:define id="periodoAlCorte" toScope="page">
										<bean:write name="reporteForm" property="periodoAlCorte" />
									</bean:define>
									<html:radio property="tipoPeriodo" value="<%= periodoAlCorte %>" onclick="eventoOnclickTipoPeriodo()">
										<vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.tipo.selector.periodo.corte" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<bean:define id="periodoPorPeriodo" toScope="page">
										<bean:write name="reporteForm" property="periodoPorPeriodo" />
									</bean:define>
									<html:radio property="tipoPeriodo" value="<%= periodoPorPeriodo %>" onclick="eventoOnclickTipoPeriodo()">
										<vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.tipo.selector.periodo.periodo" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td colspan="4" style="padding-left: 20px;">
									<table class="panelContenedor panelContenedorTabla">
										<tr>
											<td align="left"><vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.periodo" /> : </td>
											<td>
												<html:select property="mesInicial" disabled="true" styleClass="cuadroTexto">
													<html:optionsCollection property="grupoMeses" value="clave" label="valor" />
												</html:select>
											</td>
											<td align="left"><vgcutil:message key="jsp.reportes.plan.consolidado.plantilla.ano" /> : </td>
											<td>
												<html:select property="anoInicial" disabled="true" styleClass="cuadroTexto">
													<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
												</html:select>
											</td>
										</tr>
									</table>
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
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombreOrganizacion" />', 30);</script></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombrePlan" />', 30);</script></b></td> 
							</tr>
							<tr>
								<td colspan="4"><hr width="100%"></td>
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
						</table>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Visualizar --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="visualizar">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
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
									<table class="panelContenedor panelContenedorTabla">
										<tr>
											<td colspan="3">
												&nbsp;&nbsp;
												<input type="checkbox" name="checkIndicadores" id="checkIndicadores" checked >
												<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.indicadores" />
											</td>
										</tr>
										
										<tr>
											<td colspan="3">
												&nbsp;&nbsp;
												<input type="checkbox" name="checkIniciativas" id="checkIniciativas" onclick="eventoOnclickVisualizarIniciativas()" checked>
												<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.visualizar.iniciativas" />
											</td>
										</tr>
										<tr>
											<td style="padding-left: 20px; width: 20px; text-align: center;">
												<input type="radio" id="historico" name="historico" value="0" class="botonSeleccionSimple" checked>
											</td>
											<td colspan="2"><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.todos.historico" /></td>
										</tr>
										<tr>
											<td style="padding-left: 20px; width: 20px; text-align: center;">
												<input type="radio" id="historico" name="historico" value="2" class="botonSeleccionSimple">
											</td>
											<td colspan="2"><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.historico" /></td>
										</tr>
										<tr>
											<td style="padding-left: 20px; width: 20px; text-align: center;">
												<input type="radio" id="historico" name="historico" value="1" class="botonSeleccionSimple">
											</td>
											<td colspan="2"><vgcutil:message key="jsp.reportes.plan.ejecucion.reporte.iniciativa.marcada.no.historico" /></td>
										</tr>
										<!-- Actividades -->
										<tr>
											<td colspan="3">
												&nbsp;&nbsp; 
												<input type="checkbox" name="checkActividad" id="checkActividad" checked>
												<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.visualizar.actividades" />
											</td>
										</tr>
									</table>
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

						<table class="panelContenedor panelContenedorTabla">

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
								<td colspan="4"><hr width="100%"></td>
							</tr>
							
							<!-- Tipo Reporte -->
							<tr>
								<td align="center" colspan="4"><b><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" /></b></td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />&nbsp;&nbsp;&nbsp;
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />								
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
	</tiles:put>
</tiles:insert>