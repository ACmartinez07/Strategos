<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (04/06/2022) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.asignar.pesos.portafolio.titulo" />
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
		var _setCloseParent = false;
		var errmsgrango = '<vgcutil:message key="jsp.protegerliberar.errorrango"/>';
		
		function validarRango(desdeobj, hastaobj, desdeAntobj, hastaAntobj, errmsg)
	  	{
	  		desde = parseInt(desdeobj.value);
			hasta = parseInt(hastaobj.value);
			if (hasta<desde) 
			{
				alert(errmsg);
				desdeobj.value = desdeAntobj.value;
				hastaobj.value = hastaAntobj.value;				
			} 
			else 
			{
				desdeAntobj.value = desde;
				hastaAntobj.value = hasta;				
			}
		}

		function guardar()
		{
			
			var xml = '?funcion=calcular';	
			activarBloqueoEspera();
			document.editarInstrumentosForm.action = '<html:rewrite action="/instrumentos/calcularIndicadoresEjecucion"/>'+xml;
			document.editarInstrumentosForm.submit();
		}
		
		function cancelar() 
		{
			this.close();			
		}
		
		function onClose()
		{
			if (_setCloseParent)
				cancelar();
		}
		
		function validar()
		{
			
		}
	
			
		</script>
		
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Representación de la Forma --%>
		<html:form action="/instrumentos/calcularIndicadores" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="instrumentoId" />
			<input type="hidden" name="mesInicialAnt" value="1" />
			<input type="hidden" name="mesFinalAnt" value="12" />
			
			<vgcinterfaz:contenedorForma>
			
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.calcular.instrumentos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="360px" width="440px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="bordeFichaDatostabla bordeFichaDatos">
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
										<tr>
											<td>
												<b>
													<vgcutil:message key="jsp.protegerliberar.rango" />													
												</b>
											</td>
										</tr>
										<tr>
											<td height="10">&nbsp;</td>
										</tr>	
										<tr>
											<td align="right">
												<vgcutil:message key="jsp.protegerliberar.ano" />
											</td>
											<td>
												<bean:define id="anoCalculo" toScope="page">
													<bean:write name="editarInstrumentosForm" property="ano" />
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
											<td align="right"><vgcutil:message
													key="jsp.asistente.grafico.trimestre" /></td>
											<td>
												<table class="contenedorTextoSeleccion" cellpadding="3"
													cellspacing="3">
													<tr>
														<td><bean:define id="valor" toScope="page">
																<bean:write name="editarInstrumentosForm"
																	property="mesInicial" />
															</bean:define> <html:select property="mesInicial" value="<%=valor%>"
																onchange="validarRango(document.editarInstrumentosForm.mesInicial, document.editarInstrumentosForm.mesFinal, document.editarInstrumentosForm.mesInicialAnt, document.editarInstrumentosForm.mesFinalAnt, errmsgrango);"
																styleClass="cuadroTexto">
																<%
																for (int i = 1; i <= 4; i++) {
																%>
																<html:option value="<%=String.valueOf(i)%>">
																	<%=i%>
																</html:option>
																<%
																}
																%>
															</html:select></td>
														<td align="left">
															<table class="contenedorTextoSeleccion" cellpadding="3"
																cellspacing="3">
																<tr>
																	<td><vgcutil:message
																			key="jsp.asistente.grafico.trimestre" /></td>
																	<td><bean:define id="valor" toScope="page">
																			<bean:write name="editarInstrumentosForm"
																				property="mesFinal" />
																		</bean:define> <html:select property="mesFinal" value="<%=valor%>"
																			onchange="validarRango(document.editarInstrumentosForm.mesInicial, document.editarInstrumentosForm.mesFinal, document.editarInstrumentosForm.mesInicialAnt, document.editarInstrumentosForm.mesFinalAnt, errmsgrango);"
																			styleClass="cuadroTexto">
																			<%
																			for (int i = 1; i <= 4; i++) {
																			%>
																			<html:option value="<%=String.valueOf(i)%>">
																				<%=i%>
																			</html:option>
																			<%
																			}
																			%>
																		</html:select></td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>

									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
										<tr>
											<td colspan="3">
												<html:radio property="alcance" value="1">
													<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.instrumento" />
												</html:radio>
											</td>
										</tr>
										
										<tr>
											<td colspan="3">
												<html:radio property="alcance" value="4">
													<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.todos" />
												</html:radio>
											</td>
										</tr>									
	
									</table>
								</td>
							</tr>
						</table>

						
					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<table style="width: 100%;">
						<tr>
							<td align="right">
								
								<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> 
									<vgcutil:message key="menu.mediciones.calcular" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> 
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="boton.cancelar" />
								</a>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
			
		
	</tiles:put>

</tiles:insert>