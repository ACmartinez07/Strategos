<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<bean:define id="seleccionados">
	<logic:notEmpty name="gestionarActividadesForm" property="seleccionados">
		<bean:write name="gestionarActividadesForm" property="seleccionados" />
	</logic:notEmpty>
	<logic:empty name="gestionarActividadesForm" property="seleccionados">
		0
	</logic:empty>
</bean:define>

<table id="tablaMarcoActividadesGeneral" class="tablaSpacing0Padding0Width100Collapse">
	<tr>
		<td>
			<div id="marcoActividadesGeneral" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: auto" onscroll="moveScroll(this)">
				<table class="tablaSpacing0Padding0Width100Collapse">
					<tr>
						<td>
							<table class="tablaSpacing0Padding0Width100Collapse">
								<tr>
									<td width="100%" valign="bottom">
										<div id="marcoEncabezadoActividades" style="position: relative; top: 0; left: 0; width: 100%; border: none; overflow: hidden">
											<table width="1500px" border="0" cellpadding="2" cellspacing="1">
												<%-- Encabezado del "Visor Tipo Lista" --%>
												<tr class="encabezadoListView" height="20px">
													<td align="center" width="35px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.alerta" />
														<img name="alerta" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="35px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.relacion" />
														<img name="relacion" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="400px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.nombre" />
														<img name="nombre" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.porcentajecompletado" />
														<img name="porcentajecompletado" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.porcentajeejecutado" />
														<img name="porcentajeejecutado" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.porcentajeesperado" />
														<img name="porcentajeesperado" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.ultimoperiodo" />
														<img name="ultimoPeriodo" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.peso" />
														<img name="peso" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.comienzo.plan" />
														<img name="comienzoPlan" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.fin.plan" />
														<img name="finPlan" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.inicio" />
														<img name="comienzoReal" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="70px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.culminacion" />
														<img name="finReal" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
													<td align="center" width="400px" class="mouseFueraEncabezadoListView">
														<vgcutil:message key="jsp.gestionaractividades.columna.responsable.lograr.meta" />
														<img name="responsableLograrMetaId" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />">
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table class="tablaSpacing0Padding0Width100Collapse">
								<tr>
									<td>
										<div id="marcoExterno" style="position: relative; top: 0; left: 0; height: 100%; width: 100%; border: none; overflow: hidden">
											<table class="tablaSpacing0Padding0Width100Collapse">
												<tr>
													<td width="100%" valign="top">
														<div id="marcoActividades" style="position: relative; top: 0; left: 0; width: 100%; height: 100%; border: none; overflow: hidden">
															<table id="visorActividades" class="listView" border="0" cellpadding="2" cellspacing="0" width="1500px">
																<%-- Cuerpo del Visor Tipo Lista --%>
																<logic:iterate name="paginaActividades" property="lista" scope="request" id="pryActividad">
																	<tr onclick="eventoClickFilaCombinada(this)" id="<bean:write name="pryActividad" property="actividadId" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaCombinada(this)" onMouseOut="eventoMouseFueraFilaCombinada(this)" style="height: 29px;">
																		<td style="width: 35px; text-align: center;">
																			<logic:notEmpty name="pryActividad" property="alerta">
																				<vgcst:imagenAlertaIniciativa name="pryActividad" property="alerta" />
																			</logic:notEmpty>
																		</td>
																		<td width="35px" align="center">
																			<logic:notEmpty name="pryActividad" property="relacion">
																				<p ondblclick="javascript:showRelacion('<bean:write name="pryActividad" property="objetoAsociadoId" />', '<bean:write name="pryActividad" property="objetoAsociadoClassName" />');">
																					<vgcst:imagenRelacion name="pryActividad" property="relacion" />
																				</p>
																			</logic:notEmpty>
																			<logic:empty name="pryActividad" property="relacion">
																				&nbsp;
																			</logic:empty>
																		</td>
																		<td width="400px" title="<bean:write name="pryActividad" property="nombre" />">
																			<logic:iterate id="identador" name="pryActividad" property="identacion">&nbsp;</logic:iterate>
																			<logic:equal name="pryActividad" property="tieneHijos" value="true">-</logic:equal>
																			<logic:equal name="pryActividad" property="tieneHijos" value="false">&nbsp;</logic:equal>&nbsp;<script>truncarTexto('<bean:write name="pryActividad" property="nombre" />', 500);</script>
																			<logic:equal name="pryActividad" property="actividadId" value="<%=seleccionados%>">
																				<a name="ancla"></a>
																			</logic:equal>
																		</td>
																		<td width="70px" align="center">
																			<logic:notEmpty name="pryActividad" property="porcentajeCompletadoFormateado">
																				<bean:write name="pryActividad" property="porcentajeCompletadoFormateado" />
																			</logic:notEmpty>
																		</td>
																		<td width="70px" align="center">
																			<logic:notEmpty name="pryActividad" property="porcentajeEjecutadoFormateado">
																				<bean:write name="pryActividad" property="porcentajeEjecutadoFormateado" />
																			</logic:notEmpty>
																		</td>
																		<td width="70px" align="center">
																			<logic:notEmpty name="pryActividad" property="porcentajeEsperadoFormateado">
																				<bean:write name="pryActividad" property="porcentajeEsperadoFormateado" />
																			</logic:notEmpty>
																		</td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="fechaUltimaMedicion" /></td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="pryInformacionActividad.pesoFormateado" /></td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="comienzoPlanFormateada" /></td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="finPlanFormateada" /></td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="comienzoRealFormateada" /></td>
																		<td width="70px" align="center"><bean:write name="pryActividad" property="finRealFormateada" /></td>
																		<td width="400px" align="center">
																			<logic:notEmpty name="pryActividad" property="responsableLograrMeta">
																				<bean:write name="pryActividad" property="responsableLograrMeta.nombreCargo" />
																			</logic:notEmpty>
																		</td>
																	</tr>
																</logic:iterate>
															</table>
															<table class="listView" border="0" cellpadding="2" cellspacing="0" width="800px">
																<%-- Validación cuando no hay registros --%>
																<logic:equal name="paginaActividades" property="total" value="0" scope="request">
																	<tr class="mouseFueraCuerpoListView" height="20px">
																		<td valign="middle" align="center">
																			<vgcutil:message key="jsp.gestionaractividades.noregistros" />
																		</td>
																	</tr>
																</logic:equal>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
		<td valign="top">
			<div id="filaGantt" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: auto" onscroll="moveScroll(this)">
				<table class="tablaSpacing0Padding0Width100Collapse">
					<tr>
						<td valign="top" id="columnaEscala">
							<table width="<bean:write name="gestionarActividadesForm" property="totalPixeles" />px" cellpadding="2" cellspacing="0" border="0">
								<tr height="28px" bgcolor="#EDEDED">
									<logic:iterate name="gestionarActividadesForm" property="escalaSuperior" id="periodo">
										<td class="bordeEncabezadoGantt" colspan="<bean:write name="periodo" property="periodosAno" />" width="<bean:write name="periodo" property="pixeles" />"><b><bean:write name="periodo" property="anoPeriodo" /></b></td>
									</logic:iterate>
								</tr>
								<tr height="30px" bgcolor="#EDEDED">
									<logic:iterate name="gestionarActividadesForm" property="escalaInferior" id="periodo">
										<td class="bordeEncabezadoGantt" align="center" width="<bean:write name="periodo" property="pixeles" />"><bean:write name="periodo" property="strPeriodo" /></td>
									</logic:iterate>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td id="columnaGantt" valign="top" align="left">
							<table id="tablaGantt" class="listView" width="<bean:write name="gestionarActividadesForm" property="totalPixeles" />px" border="0" cellpadding="2" cellspacing="0">
								<logic:iterate name="gestionarActividadesForm" property="listaActividades" scope="session" id="periodoActividad">
									<tr class="mouseFueraCuerpoListView" onclick="eventoClickFilaCombinada(this)" onMouseOver="eventoMouseEncimaFilaCombinada(this)" onMouseOut="eventoMouseFueraFilaCombinada(this)" title="<bean:write name="periodoActividad" property="actividad.nombre" />" style="height: 29px;" >
										<td>
											<div style="position: relative; z-index: 2; top: 7px; left: 0px">
												<img border="0" src="<html:rewrite page='/paginas/strategos/planificacionseguimiento/actividades/imagenes/vacia.gif'/>" height="3px;" width="<bean:write name="periodoActividad" property="longitudLineaBaseReal" />px"><img border="0" src="<html:rewrite page='/paginas/strategos/planificacionseguimiento/actividades/imagenes/real.gif'/>" height="3px" width="<bean:write name="periodoActividad" property="longitudLineaTiempoReal" />px">
											</div>
											<bean:define id="srcImagenActividad">
												<logic:equal name="periodoActividad" property="actividad.tieneHijos" value="true">
													<html:rewrite page='/paginas/strategos/planificacionseguimiento/actividades/imagenes/base.gif' />
												</logic:equal>
												<logic:equal name="periodoActividad" property="actividad.tieneHijos" value="false">
													<html:rewrite page='/paginas/strategos/planificacionseguimiento/actividades/imagenes/programado.gif' />
												</logic:equal>
											</bean:define>
											<div style="position: relative; z-index: 1; top: 0px; left: 0px">
												<img border="0" src="<html:rewrite page='/paginas/strategos/planificacionseguimiento/actividades/imagenes/vacia.gif'/>" height="3px" width="<bean:write name="periodoActividad" property="longitudLineaBasePlan" />px"><img border="0" height="10px;" src="<bean:write name="srcImagenActividad"/>" width="<bean:write name="periodoActividad" property="longitudLineaTiempoPlan" />px">
											</div>
										</td>
									</tr>
								</logic:iterate>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
