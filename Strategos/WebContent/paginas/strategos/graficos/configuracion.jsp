<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/09/2012) --%>
<bean:define id="bloquearForma" toScope="page">
	<logic:notEmpty name="graficoForm" property="bloqueado">
		<bean:write name="graficoForm" property="bloqueado" />
	</logic:notEmpty>
	<logic:empty name="graficoForm" property="bloqueado">
		false
	</logic:empty>
</bean:define>

<bean:define id="listaTiposSerie" toScope="page" name="graficoForm" property="tiposSerie" />
<div id="modal1" class="modalmask">
	<div class="modalbox movedown" style="width:700px;padding: 0px;">
		<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px;border-collapse: collapse;">
			<tr height="30px">
				<td colspan="2">
					<a href="javascript:closeModal(true);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a>
				</td>
			</tr>
			<tr>
				<td class="tabtd">
					<div class="demo">
						<div id="tabs">
							<ul>
								<li><a href="#tabs-1"><vgcutil:message key="jsp.grafico.editor.tab.insumo" /></a></li>
								<li><a href="#tabs-2"><vgcutil:message key="jsp.grafico.editor.tab.periodo" /></a></li>
								<li><a href="#tabs-3"><vgcutil:message key="jsp.grafico.editor.tab.titulo" /></a></li>
								<li><a href="#tabs-4"><vgcutil:message key="jsp.grafico.editor.tab.tipo" /></a></li>
								<li><a href="#tabs-5"><vgcutil:message key="jsp.grafico.editor.tab.serie" /></a></li>
								<li><a href="#tabs-6"><vgcutil:message key="jsp.grafico.editor.tab.otros" /></a></li>
							</ul>
							<div id="tabs-1">
								<table class="tabTablaPrincipal" style="font-size:8;">
									<tr>
										<logic:empty property="className" name="graficoForm">
											<logic:notEqual name="graficoForm" property="bloqueado" value="true">
												<td align="left">
													<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
												</td>
												<td>
													<html:select property="frecuencia"
														styleClass="cuadroTexto" size="1"
														onchange="eventoCambioFrecuencia()">
														<html:optionsCollection property="frecuencias"
															value="frecuenciaId" label="nombre" />
													</html:select>
												</td>
											</logic:notEqual>
											<logic:equal name="graficoForm" property="bloqueado" value="true">
												<td align="left">
													<vgcutil:message key="jsp.grafico.editor.tab.frecuencia.grafico" />
												</td>
												<td>
													<html:text disabled="true" property="frecuenciaNombre" size="10" maxlength="10" styleClass="cuadroTexto" />
													<html:hidden property="frecuencia" />
												</td>
											</logic:equal>
										</logic:empty>
										<logic:notEmpty property="className" name="graficoForm">
											<logic:equal name="graficoForm" property="className" value="Celda">
												<logic:notEqual name="graficoForm" property="bloqueado" value="true">
													<td align="left">
														<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
													</td>
													<td>
														<html:select property="frecuencia"
															styleClass="cuadroTexto" size="1"
															onchange="eventoCambioFrecuencia()">
															<html:optionsCollection property="frecuencias"
																value="frecuenciaId" label="nombre" />
														</html:select>
													</td>
												</logic:notEqual>
												<logic:equal name="graficoForm" property="bloqueado" value="true">
													<td align="left">
														<vgcutil:message key="jsp.grafico.editor.tab.frecuencia.grafico" />
													</td>
													<td>
														<html:text disabled="true" property="frecuenciaNombre" size="10" maxlength="10" styleClass="cuadroTexto" />
														<html:hidden property="frecuencia" />
													</td>
												</logic:equal>
											</logic:equal>
											<logic:notEqual name="graficoForm" property="className" value="Celda">
												<logic:empty property="objetosIds" name="graficoForm">
													<td align="left">
														<vgcutil:message key="jsp.grafico.editor.tab.frecuencia.grafico" />
													</td>
													<td>
														<html:text disabled="true" property="frecuenciaNombre" size="10" maxlength="10" styleClass="cuadroTexto" />
														<html:hidden property="frecuencia" />
													</td>
												</logic:empty>
												<logic:notEmpty property="objetosIds" name="graficoForm">
													<td align="left">
														<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
													</td>
													<td>
														<html:select property="frecuencia"
															styleClass="cuadroTexto" size="1"
															onchange="eventoCambioFrecuencia()">
															<html:optionsCollection property="frecuencias"
																value="frecuenciaId" label="nombre" />
														</html:select>
													</td>
												</logic:notEmpty>
											</logic:notEqual>
										</logic:notEmpty>
									</tr>
									<tr>
										<td>
											<br>
										</td>
									</tr>
									<logic:empty property="className" name="graficoForm">
										<logic:notEqual name="graficoForm" property="bloqueado" value="true">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo" /></td>
											</tr>
											<tr align="center">
												<td>
													<input type="button" style="width: 150px"
													class="cuadroTexto"
													value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
													onclick="seleccionarIndicadores();">
												</td>
												<td><input type="button" style="width: 150px"
													class="cuadroTexto"
													value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
													onclick="removerInsumo();">
												</td>
											</tr>
										</logic:notEqual>
										<logic:equal name="graficoForm" property="bloqueado" value="true">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo.bloqueado" /></td>
											</tr>
										</logic:equal>
									</logic:empty>
									<logic:notEmpty property="className" name="graficoForm">
										<logic:equal name="graficoForm" property="className" value="Celda">
											<logic:notEqual name="graficoForm" property="bloqueado" value="true">
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo" /></td>
												</tr>
												<tr align="center">
													<td>
														<input type="button" style="width: 150px"
														class="cuadroTexto"
														value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
														onclick="seleccionarIndicadores();">
													</td>
													<td>
														<input type="button" style="width: 150px"
														class="cuadroTexto"
														value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
														onclick="removerInsumo();">
													</td>
												</tr>
											</logic:notEqual>
											<logic:equal name="graficoForm" property="bloqueado" value="true">
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo.bloqueado" /></td>
												</tr>
											</logic:equal>
										</logic:equal>
										<logic:notEqual name="graficoForm" property="className" value="Celda">
											<logic:empty property="objetosIds" name="graficoForm">
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo.bloqueado" /></td>
												</tr>
											</logic:empty>
											<logic:notEmpty property="objetosIds" name="graficoForm">
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo" /></td>
												</tr>
												<tr align="center">
													<td>
														<input type="button" style="width: 150px"
														class="cuadroTexto"
														value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
														onclick="seleccionarIndicadores();">
													</td>
													<td>
														<input type="button" style="width: 150px"
														class="cuadroTexto"
														value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
														onclick="removerInsumo();">
													</td>
												</tr>
											</logic:notEmpty>
										</logic:notEqual>
									</logic:notEmpty>
									<tr height="128px" valign="top">
										<td colspan="2">
											<table class="tabtablainsumo">
												<tr valign="top">
													<td>
														<table id="tablaListaInsumos">
															<tbody class="cuadroTexto">
															</tbody>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div id="tabs-2">
								<table class="tabTablaPrincipal">
									<tr>
										<td colspan="2"><vgcutil:message
												key="jsp.asistente.grafico.rango" /></td>
									</tr>
									<tr>
										<td width="50%"><vgcutil:message
												key="jsp.asistente.grafico.inicial" /></td>
										<td width="50%"><vgcutil:message
												key="jsp.asistente.grafico.final" /></td>
									</tr>
									<tr>
										<bean:define id="maximoPeriodo" name="graficoForm"
											property="numeroMaximoPeriodos" type="java.lang.Integer"
											toScope="page" />
										<td width="50%">
											<table class="tabla contenedorBotonesSeleccion">
												<tr id="trAnoInicial">
													<td><vgcutil:message
															key="jsp.asistente.grafico.ano" /></td>
													<td><bean:define id="anoCalculoInicial"
															toScope="page">
															<bean:write name="graficoForm" property="anoInicial" />
														</bean:define> <html:select property="anoInicial" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
															value="<%=anoCalculoInicial%>"
															onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
															styleClass="cuadroTexto">
															<%
																for (int i = 1900; i <= 2050; i++) {
															%>
															<html:option value="<%=String.valueOf(i)%>">
																<%=i%>
															</html:option>
															<%
																}
															%>
														</html:select></td>
												</tr>
												<tr id="trPeriodoInicial">
													<td><span id="tdPeriodoInicial"></span></td>
													<td>
														<logic:notEqual name="graficoForm" property="bloqueado" value="true">
															<select id="selectPeriodoInicial"
																onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																class="cuadroTexto">
															</select>
														</logic:notEqual>
														<logic:equal name="graficoForm" property="bloqueado" value="true">
															<select id="selectPeriodoInicial" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																class="cuadroTexto">
															</select>
														</logic:equal>
													</td>
												</tr>
												<tr id="trPeriodoInicialDate">
													<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
													<td>
														<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaDesde" size="10" maxlength="10" styleClass="cuadroTexto" />
														<logic:notEqual name="graficoForm" property="bloqueado" value="true">
															<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
														</logic:notEqual>
													</td>
												</tr>
											</table>
										</td>
										<td width="50%">
											<table class="tabla contenedorBotonesSeleccion">
												<tr id="trAnoFinal">
													<td><vgcutil:message
															key="jsp.asistente.grafico.ano" /></td>
													<td><bean:define id="anoCalculoFinal"
															toScope="page">
															<bean:write name="graficoForm" property="anoFinal" />
														</bean:define> <html:select property="anoFinal" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
															value="<%=anoCalculoFinal%>"
															onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
															styleClass="cuadroTexto">
															<%
																for (int i = 1900; i <= 2050; i++) {
															%>
															<html:option value="<%=String.valueOf(i)%>">
																<%=i%>
															</html:option>
															<%
																}
															%>
														</html:select></td>
												</tr>
												<tr id="trPeriodoFinal">
													<td><span id="tdPeriodoFinal"></span></td>
													<td>
														<logic:notEqual name="graficoForm" property="bloqueado" value="true">
															<select id="selectPeriodoFinal" 
																onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
															class="cuadroTexto">
															</select>
														</logic:notEqual>
														<logic:equal name="graficoForm" property="bloqueado" value="true">
															<select id="selectPeriodoFinal" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
															class="cuadroTexto">
															</select>
														</logic:equal>
													</td>
												</tr>
												<tr id="trPeriodoFinalDate">
													<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
													<td>
														<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaHasta" size="10" maxlength="10" styleClass="cuadroTexto" />
														<logic:notEqual name="graficoForm" property="bloqueado" value="true">
															<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
														</logic:notEqual>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<br>
										</td>
									</tr>
									<tr>
										<td align="left"><vgcutil:message key="jsp.asistente.grafico.agruparFrecuencia" /></td>
										<td>
											<logic:notEqual name="graficoForm" property="bloqueado" value="true">
												<select 
													id="selectFrecuenciasCompatibles" 
													name="selectFrecuenciasCompatibles" 
													class="cuadroTexto" 
													size="1"
													onchange="eventoCambioFrecuenciasCompatibles()">
												</select>
											</logic:notEqual>
											<logic:equal name="graficoForm" property="bloqueado" value="true">
												<select 
													id="selectFrecuenciasCompatibles" 
													name="selectFrecuenciasCompatibles" 
													class="cuadroTexto" 
													size="1"
													onchange="eventoCambioFrecuenciasCompatibles()"
													disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
												</select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td>
											<br>
										</td>
									</tr>
									<tr>
										<td>
											<html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="acumular" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.grafico.editor.acumular" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</div>
							<div id="tabs-3">
								<table class="tabTablaPrincipal">
									<tr>
										<td colspan="2"><vgcutil:message
												key="jsp.asistente.grafico.titulosgraficos" /><br>
											<br></td>
									</tr>
									<tr>
										<td><vgcutil:message
												key="jsp.asistente.grafico.titulosgraficos.titulo" />
										</td>
									</tr>
									<tr>
										<td>
											<table class="tabTablaPrincipal">
												<tr>
													<td>
														<html:text property="titulo" size="57"
															disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="100"
															styleClass="cuadroTexto" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td><vgcutil:message
												key="jsp.asistente.grafico.titulosgraficos.ejey" /></td>
									</tr>
									<tr>
										<td><html:text property="tituloEjeY" size="57"
												disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
												styleClass="cuadroTexto" /></td>
									</tr>
									<tr>
										<td><vgcutil:message
												key="jsp.asistente.grafico.titulosgraficos.ejex" /></td>
									</tr>
									<tr>
										<td><html:text property="tituloEjeX" size="57"
												disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
												styleClass="cuadroTexto" /></td>
									</tr>
									<tr id="trTitleEjez" style="display:none">
										<td><vgcutil:message
												key="jsp.asistente.grafico.titulosgraficos.ejez" /></td>
									</tr>
									<tr id="trLabelEjez" style="display:none">
										<td><html:text property="tituloEjeZ" size="57"
												disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
												styleClass="cuadroTexto" /></td>
									</tr>
								</table>
							</div>
							<div id="tabs-4">
								<table class="tabTablaPrincipal">
									<tr>
										<td>
											<table class="tabla">
												<tr>
													<td>
														<table class="tabtablaImagenes">
															<tr>
																<td bgcolor="#FFFFFF">
																	<img id="graficoLinea" name="graficoLinea"
																	src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Linea.png'/>"
																	border="0" width="72px" height="72px"
																	alt="<vgcutil:message key="jsp.asistente.grafico.tipo.lineas" />" 
																	onmouseover="changeImages('graficoLinea', 'graficoLineaIn')"
																	onmouseout="changeImages('graficoLinea', 'graficoLineaOut')" 
																	onclick="setTipo(1, true)"
																	style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoColumna" name="graficoColumna"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Columna.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />"
																		onclick="setTipo(2, true)"
																		onmouseover="changeImages('graficoColumna', 'graficoColumnaIn')"
																		onmouseout="changeImages('graficoColumna', 'graficoColumnaOut')"
																		style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoBarra" name="graficoBarra"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />"
																		onclick="setTipo(3, true)"
																		onmouseover="changeImages('graficoBarra', 'graficoBarraIn')"
																		onmouseout="changeImages('graficoBarra', 'graficoBarraOut')"
																		style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoArea" name="graficoArea"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Area.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.area" />"
																		onclick="setTipo(4, true)"
																		onmouseover="changeImages('graficoArea', 'graficoAreaIn')"
																		onmouseout="changeImages('graficoArea', 'graficoAreaOut')"
																		style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoTorta" name="graficoTorta"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />"
																		style="cursor:pointer"
																		onclick="setTipo(5, true)"
																		onmouseover="changeImages('graficoTorta', 'graficoTortaIn')"
																		onmouseout="changeImages('graficoTorta', 'graficoTortaOut')">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoGauge" name="graficoGauge"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Gauge.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.gauge" />"
																		style="cursor:pointer"
																		onclick="setTipo(14, true)"
																		onmouseover="changeImages('graficoGauge', 'graficoGaugeIn')"
																		onmouseout="changeImages('graficoGauge', 'graficoGaugeOut')">
																</td>
															</tr>
															<tr>
																<td bgcolor="#FFFFFF">
																	<img id="graficoPareto" name="graficoPareto"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pareto.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.pareto" />"
																		style="cursor:pointer"
																		onclick="setTipo(6)"
																		onmouseover="changeImages('graficoPareto', 'graficoParetoIn')"
																		onmouseout="changeImages('graficoPareto', 'graficoParetoOut')"/>
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoCombinado"
																		name="graficoCombinado"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Combinado.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.combinado" />"
																		onclick="setTipo(8)"
																		onmouseover="changeImages('graficoCombinado', 'graficoCombinadoIn')"
																		onmouseout="changeImages('graficoCombinado', 'graficoCombinadoOut')">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoCascada" name="graficoCascada"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Cascada.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.cascada" />"
																		onclick="setTipo(9, true)"
																		onmouseover="changeImages('graficoCascada', 'graficoCascadaIn')"
																		onmouseout="changeImages('graficoCascada', 'graficoCascadaOut')"
																		style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoTorta3D" name="graficoTorta3D"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3D.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />"
																		style="cursor:pointer"
																		onclick="setTipo(10, true)"
																		onmouseover="changeImages('graficoTorta3D', 'graficoTorta3DIn')"
																		onmouseout="changeImages('graficoTorta3D', 'graficoTorta3DOut')">>
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoBarra3D" name="graficoBarra3D"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3D.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />"
																		onclick="setTipo(11, true)"
																		onmouseover="changeImages('graficoBarra3D', 'graficoBarra3DIn')"
																		onmouseout="changeImages('graficoBarra3D', 'graficoBarra3DOut')"
																		style="cursor:pointer">
																</td>
																<td bgcolor="#FFFFFF">
																	<img id="graficoBarraApilada3D"
																		name="graficoBarraApilada3D"
																		src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Apilada3D.png'/>"
																		border="0" width="72px" height="72px"
																		alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barraapilada3d" />"
																		onclick="setTipo(12, true)"
																		onmouseover="changeImages('graficoBarraApilada3D', 'graficoBarraApilada3DIn')"
																		onmouseout="changeImages('graficoBarraApilada3D', 'graficoBarraApilada3DOut')"
																		style="cursor:pointer">
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td><vgcutil:message
												key="jsp.asistente.grafico.tipografico.tipo.seleccionado" />
											&nbsp;<span id="tipoGraficoSeleccionado"></span></td>
									</tr>
								</table>
							</div>
							<div id="tabs-5">
								<table class="tabTablaPrincipal">
									<tr>
										<td height="10px" style="padding-top: 10px;">
											<table class="cuadroTextoTitle">
												<tr valign="top">
													<td style="width:50px">&nbsp;</td>
													<td align="center" style="width:260px"><vgcutil:message key="jsp.grafico.editor.serie" /></td>
													<td align="center" style="width:40px"><vgcutil:message key="jsp.grafico.editor.color" /></td>
													<td align="center" style="width:90px"><vgcutil:message key="jsp.grafico.editor.tipodegrafico" /></td>
													<td align="center" style="width:85px"><vgcutil:message key="jsp.grafico.editor.mostrar.organizacion" /></td>
													<td align="center"><vgcutil:message key="jsp.grafico.editor.mostrar.nivelclase" /></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr height="128px" valign="top">
										<td colspan="2"><html:hidden property="insumosSerie" />
											<table>
												<tr valign="top">
													<td>
														<table id="tablaListaSeries" class="tabtablainsumoSerie">
															<tbody class="cuadroTexto">
																<logic:iterate name="graficoForm" property="series"
																	id="serie">
																	<logic:notEmpty property="indicador" name="serie">
																		<tr
																			id="row_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />">
																			<logic:empty property="id" name="serie">
																				<td><input type="checkbox" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					name="visible_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					id="visible_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					checked>
																				</td>
																				<td><input size="47" maxlength="100"
																					type="text" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					id="serie_<bean:write name='serie' property='id' />"
																					name="serie_<bean:write name='serie' property='id' />"
																					value="<bean:write name='serie' property='nombreLeyenda' />">
																				</td>
																				<td></td>
																				<td></td>
																				<td></td>
																				<td></td>
																			</logic:empty>
																			<logic:notEmpty property="id" name="serie">
																				<td><input type="checkbox" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					name="visible_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					id="visible_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					checked></td>
																				<td><input size="47" maxlength="100"
																					type="text" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					id="serie_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					name="serie_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					value="<bean:write name='serie' property='nombreLeyenda' />">
																				</td>
																				<logic:notEmpty property="color" name="serie">
																					<td>
																						<input type="text" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																							onclick="javascript:mostrarColores(this,'document.graficoForm.color_decimal_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />', 'document.graficoForm.color_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />');"
																							name="color_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																							size="3" readonly
																							style="background-color:<bean:write name='serie' property='color' />">
																						<input type="hidden"
																							value="<bean:write name='serie' property='color' />"
																							id="color_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																							name="color_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />">
																						<input type="hidden"
																							value="<bean:write name='serie' property='colorDecimal' />"
																							id="color_decimal_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																							name="color_decimal_hidden_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />">
																					</td>
																				</logic:notEmpty>
																				<logic:empty property="color" name="serie">
																					<td>&nbsp;</td>
																				</logic:empty>
																				<td width="80">
																					<bean:define id="tipoSerie"
																						toScope="page">
																						<bean:write name='serie' property='tipoSerie' />
																					</bean:define>
																					<select class="cuadroCombinado" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																						name="tipo_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																						id="tipo_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />">
																						<logic:iterate name="listaTiposSerie" id="objTipoSerie">
																							<bean:define id="tipo" toScope="page" name='objTipoSerie' property='tipo' type="Integer"></bean:define>
																							<bean:define id="nombre" toScope="page">
																								<bean:write name='objTipoSerie' property='nombre' />
																							</bean:define>
																							<logic:equal name='serie'
																								property='tipoSerie'
																								value='<%=tipo.toString()%>'>
																								<option value="<%=tipo%>" selected><%=nombre%></option>
																							</logic:equal>
																							<logic:notEqual name='serie' property='tipoSerie' value='<%=tipo.toString()%>'>
																								<option value="<%=tipo%>"><%=nombre%></option>
																							</logic:notEqual>
																						</logic:iterate>
																					</select>
																				</td>
																				<td>
																					<input type="checkbox" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					name="showOrganizacion_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					id="showOrganizacion_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					checked>
																				</td>
																				<td>
																					<input size="47" maxlength="100"
																					type="text" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																					id="nivelClase_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					name="nivelClase_<bean:write name='serie' property='indicador.indicadorId' />_<bean:write name='serie' property='serieIndicador.pk.serieId' />"
																					value="">
																				</td>
																			</logic:notEmpty>
																		</tr>
																	</logic:notEmpty>
																</logic:iterate>
															</tbody>
														</table>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<!--   inicio Paleta popUp  -->
											<div id="The_colorPicker" style="width: 250px; position: absolute; z-index: 1; display: none;">
												<div style="width: 140px; height: 220px; padding-right: 5px; padding-bottom: 5px; padding-left: 5px; position: relative; display: inline;">
													<div style="left: 80px; width: 16px; height: 16px; position: relative; display: inline;">
														<img style="float: right; display:block; height:16px; width:16px; cursor: pointer;" src="<html:rewrite page='/paginas/comunes/jQuery/paletaColor/css/cancel.png'/>" onclick="javascript:hideColorPicker();">
													</div>
													<div id="paletaColor"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<div id="tabs-6">
								<table class="tabTablaPrincipal">
									<tr>
										<td>
											<html:checkbox property="verTituloImprimir" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.grafico.editor.vertituloimprimir" />
											</html:checkbox>
										</td>
									</tr>
									<logic:notEqual name="graficoForm" property="tipo" value="8">
										<tr>
											<td>
												<html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="ajustarEscala" styleClass="botonSeleccionMultiple">
													<vgcutil:message key="jsp.grafico.editor.ajustarescala" />
												</html:checkbox>
											</td>
										</tr>
									</logic:notEqual>
									<tr>
										<td>
											<html:checkbox property="impVsAnoAnterior" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.grafico.editor.impVsAnoAnterior" />
											</html:checkbox>
										</td>
									</tr>
									<logic:equal name="graficoForm"
										property="mostrarCondicion" value="true">
										<tr>
											<td><html:checkbox property="condicion" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
													styleClass="botonSeleccionMultiple">
													<vgcutil:message key="jsp.grafico.editor.condicion" />
												</html:checkbox><br> <br> <br> <br> <br> <br></td>
										</tr>
									</logic:equal>
									<logic:equal name="graficoForm" property="mostrarCondicion" value="false">
										<tr>
											<td>
												<br><br><br><br><br><br><br>
											</td>
										</tr>
									</logic:equal>
								</table>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
