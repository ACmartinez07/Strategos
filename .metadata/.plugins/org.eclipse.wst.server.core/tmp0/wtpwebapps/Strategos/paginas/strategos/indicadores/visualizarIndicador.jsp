<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (25/09/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.visualizarindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/editarIndicadorFormulaJs.jsp'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/editarIndicadorCualitativoJs.jsp'/>"></script>
		<bean:define id="naturalezaSimple">
			<bean:write name="editarIndicadorForm" property="naturalezaSimple" />
		</bean:define>
		<bean:define id="naturalezaFormula">
			<bean:write name="editarIndicadorForm" property="naturalezaFormula" />
		</bean:define>
		<bean:define id="naturalezaCualitativoOrdinal">
			<bean:write name="editarIndicadorForm" property="naturalezaCualitativoOrdinal" />
		</bean:define>
		<bean:define id="naturalezaCualitativoNominal">
			<bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />
		</bean:define>
		<bean:define id="naturalezaSumatoria">
			<bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />
		</bean:define>
		<bean:define id="naturalezaPromedio">
			<bean:write name="editarIndicadorForm" property="naturalezaPromedio" />
		</bean:define>
		<bean:define id="naturalezaIndice">
			<bean:write name="editarIndicadorForm" property="naturalezaIndice" />
		</bean:define>
	
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var separadorCategorias = '<bean:write name="editarIndicadorForm" property="separadorCategorias" />';
		
			function cancel() 
			{
				this.close();
			}
			
			function asignarSeriesTiempo() 
			{
				len = document.editarIndicadorForm.elements.length;
			
				var index = 0;
			
				var checkeds = document.editarIndicadorForm.seriesIndicador.value;
				for(index = 0; index < len; index++) 
				{
					if(document.editarIndicadorForm.elements[index].name == 'serie') 
					{
						if (checkeds.indexOf('<bean:write name="editarIndicadorForm" property="separadorSeries"/>' 
												+ document.editarIndicadorForm.elements[index].value
												+ '<bean:write name="editarIndicadorForm" property="separadorSeries"/>') >= 0) 
							document.editarIndicadorForm.elements[index].checked = true;
					}
				}
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/visualizarIndicador">
			<html:hidden property="naturaleza" />
			
			<%-- Campos hidden para el manejo de la fórmula --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">			
			
			<%-- Campos hidden para el manejo de la naturaleza cualitativa --%>
			<html:hidden property="escalaCualitativa" />
			<input id="categoriaSeleccionada" type="hidden" name="categoriaSeleccionada" />
			
			<vgcinterfaz:contenedorForma width="610px" height="480px" bodyAlign="left" bodyValign="middle" bodyCellpadding="15">
			
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.visualizarindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancel()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<vgcinterfaz:contenedorPaneles height="355" width="400" nombre="visualizarIndicador">
					<vgcinterfaz:panelContenedor anchoPestana="145" nombre="datosBasicos">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">
							<tr>
								<td align="right" valign="top" width="200px"><vgcutil:message key="jsp.propiedadesindicador.organizacion" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="organizacion" property="nombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.propiedadesindicador.clase" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="claseNombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.nombre" /></td>
								<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="nombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.descripcion" /></td>
								<td colspan="2"><textarea readonly="true" class="cuadroTexto" cols="50" rows="6"><bean:write name="editarIndicadorForm" property="descripcion" /></textarea></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.comportamiento" /></td>
								<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="comportamiento" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.fuente" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="fuente" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.orden" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="1" value="<bean:write name="editarIndicadorForm" property="orden" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.propiedadesindicador.naturaleza" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="10" value="<bean:write name="editarIndicadorForm" property="naturalezaNombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.propiedadesindicador.frecuencia" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="10" value="<bean:write name="editarIndicadorForm" property="frecuenciaNombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="middle"><vgcutil:message key="jsp.propiedadesindicador.unidad" /></td>
								<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="10" value="<bean:write name="editarIndicadorForm" property="unidadNombre" />"></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.nrodecimales" /></td>
								<td colspan="2"><html:text property="numeroDecimales" size="1" readonly="true" styleClass="cuadroTexto" /></td>
							</tr>
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.mostrararbol" /></td>
								<td colspan="2"><html:checkbox disabled="true" property="mostrarEnArbol" styleClass="botonSeleccionMultiple" /></td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="145" nombre="definicion">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.definicion" />
						</vgcinterfaz:panelContenedorTitulo>
					
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">
							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaSimple%>">
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.codigoenlace" /></td>
									<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="codigoEnlace" />"></td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.codigoparcialenlace" /></td>
									<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="enlaceParcial" />"></td>
								</tr>
							</logic:equal>

							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaFormula%>">
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.codigoenlace" /></td>
									<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="codigoEnlace" />"></td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.codigoparcialenlace" /></td>
									<td colspan="2"><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="enlaceParcial" />"></td>
								</tr>
								<tr>
									<td colspan="3">
										<table class="contenedorBotonesSeleccion" width="100%" height="80px">
											<tr>
												<td colspan="3"><b><vgcutil:message key="jsp.editarindicador.ficha.formula.indicadores" /></b></td>
											</tr>
											<tr height="80px">
												<td colspan="3">
													<input id="insumoSeleccionado" type="hidden" name="insumoSeleccionado" />
													<html:hidden property="insumosFormula" />
													<vgcinterfaz:splitterHorizontal anchoPorDefecto="255" splitterId="splitInsumosFormula" overflowPanelDerecho="auto" overflowPanelIzquierdo="auto">
														<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitInsumosFormula">
															<table id="tablaListaInsumosFormula">
																<tbody class="cuadroTexto">
																</tbody>
															</table>
														</vgcinterfaz:splitterHorizontalPanelIzquierdo>
														<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitInsumosFormula">
															<table id="tablaRutaCompletaInsumoFormula">
																<tbody class="cuadroTexto">
																</tbody>
															</table>
														</vgcinterfaz:splitterHorizontalPanelDerecho>
													</vgcinterfaz:splitterHorizontal>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td valign="top" colspan="3"><vgcutil:message key="jsp.editarindicador.ficha.formula.editorformula" /></td>
								</tr>
								<tr>
									<td colspan="3"><textarea readonly="true" class="cuadroTexto" cols="70" rows="6"><bean:write name="editarIndicadorForm" property="formula" /></textarea></td>
								</tr>
							</logic:equal>

							<bean:define toScope="page" id="cualitativo" value="false"></bean:define>
							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaCualitativoOrdinal%>">
								<bean:define toScope="page" id="cualitativo" value="true"></bean:define>
							</logic:equal>
							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaCualitativoNominal%>">
								<bean:define toScope="page" id="cualitativo" value="true"></bean:define>
							</logic:equal>
							<logic:equal scope="page" name="cualitativo" value="true">
								<tr>
									<td colspan="3" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.cualitativo.categoriasmedicion" /></td>
								</tr>
								<tr height="25px">
									<td colspan="3" rowspan="5" class="cuadroTexto" valign="top">
										<div style="position:relative; overflow: auto; height: 225px">
											<table id="tablaListaCategorias" style="width: 100%">
												<tbody class="cuadroTexto">
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</logic:equal>
							
							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaSumatoria%>">
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorSumatoria" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorSumatoriaFrecuencia" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.unidad" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorSumatoriaUnidad" />"></td>
									<td>&nbsp;</td>
								</tr>
							</logic:equal>

							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaPromedio%>">
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorPromedio" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorPromedioFrecuencia" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.unidad" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorPromedioUnidad" />"></td>
									<td>&nbsp;</td>
								</tr>
							</logic:equal>

							<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaIndice%>">
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorIndice" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorIndiceFrecuencia" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right" valign="middle"><vgcutil:message key="jsp.editarindicador.ficha.unidad" /></td>
									<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="editarIndicadorForm" property="indicadorIndiceUnidad" />"></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="3">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="3">
										<bean:define type="java.lang.String" id="diferenciaPorcentualIndicadorIndice" name="editarIndicadorForm" property="tipoVariacionDiferenciaPorcentualIndicadorIndice"></bean:define>
										<bean:define id="diferenciaAbsolutaIndicadorIndice" type="java.lang.String" name="editarIndicadorForm" property="tipoVariacionDiferenciaAbsolutaIndicadorIndice"></bean:define>
										<bean:define id="variacionPorcentualIndicadorIndice" type="java.lang.String" name="editarIndicadorForm" property="tipoVariacionVariacionPorcentualIndicadorIndice"></bean:define>
										<table class="panelContenedor">
											<tr>
												<td align="left" valign="top" width="255px">
													<table class="contenedorBotonesSeleccion" width="255px" height="230px">
														<tr height="20px">
															<td align="left" valign="top"><b><vgcutil:message key="jsp.editarindicador.ficha.indice.variacion" /></b></td>
														</tr>
														<tr height="20px">
															<td align="left" valign="top">&nbsp;</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoVariacion" value="<%= diferenciaPorcentualIndicadorIndice %>"></html:radio></td>
																		<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceDifPorcentual.gif" />"></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoVariacion" value="<%= diferenciaAbsolutaIndicadorIndice %>"></html:radio></td>
																		<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceDifAbsoluta.gif" />"></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoVariacion" value="<%= variacionPorcentualIndicadorIndice %>"></html:radio></td>
																		<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceVarPorcentual.gif" />"></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<td width="10px">&nbsp;</td>
												<td align="left" valign="top" width="255px">
													<bean:define type="java.lang.String" id="anoActualPeriodoAnteriorIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoAnteriorIndicadorIndice"></bean:define> 
													<bean:define type="java.lang.String" id="anoAnteriorMismoPeriodoIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice"></bean:define> 
													<bean:define type="java.lang.String" id="anoActualPrimerPeriodoIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPrimerPeriodoIndicadorIndice"></bean:define> 
													<bean:define type="java.lang.String" id="anoActualPeriodoCierreAnteriorIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice"></bean:define>
													<table class="contenedorBotonesSeleccion" width="255px" height="230px">
														<tr height="20px">
															<td align="left" valign="top"><b><vgcutil:message key="jsp.editarindicador.ficha.indice.comparacion" /></b></td>
														</tr>
														<tr height="20px">
															<td align="left" valign="top">&nbsp;</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoComparacion" value="<%= anoActualPeriodoAnteriorIndicadorIndice %>"></html:radio></td>
																		<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoAnteriorIndicadorIndiceNombre" /></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
															<table class="panelContenedor">
																<tr>
																	<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoComparacion" value="<%= anoAnteriorMismoPeriodoIndicadorIndice %>"></html:radio></td>
																	<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoAnteriorMismoPeriodoIndicadorIndiceNombre" /></td>
																</tr>
															</table>
															</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoComparacion" value="<%= anoActualPrimerPeriodoIndicadorIndice %>"></html:radio></td>
																		<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPrimerPeriodoIndicadorIndiceNombre" /></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px"><html:radio disabled="true" property="indicadorIndiceTipoComparacion" value="<%= anoActualPeriodoCierreAnteriorIndicadorIndice %>"></html:radio></td>
																		<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndiceNombre" /></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table class="panelContenedor">
																	<tr>
																		<td width="20px">&nbsp;</td>
																		<td><input type="text" class="cuadroTexto" size="40" name="tipoComparacionIndicadorIndiceFormula" readonly="readonly" disabled="disabled"></td>
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
							</logic:equal>

						</table>
					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="145" nombre="asociar">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.asociar" />
						</vgcinterfaz:panelContenedorTitulo>
						<%-- Cuerpo de la Pestaña --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">

							<!-- Tipo de Asociado -->
							<tr>
								<td align="left" valign="top" width="110px"><vgcutil:message key="jsp.editarindicador.ficha.tiporelacion" /></td>
								<td align="left" valign="top">
									<div style="overflow:auto; visibility:visible; height:200px; width:200px;" >
										<html:hidden property="seriesIndicador" />
										<logic:iterate id="serie" name="editarIndicadorForm" property="seriesTiempo">
											<input type="checkbox" class="botonSeleccionMultiple" value='<bean:write name="serie" property="serieId" />' name='serie' disabled="true"/><bean:write name="serie" property="nombre" /><br>
										</logic:iterate>
									</div>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="90px" nombre="parametros">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.parametros" />
						</vgcinterfaz:panelContenedorTitulo>

						<%-- Cuerpo de la Pestaña --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" width="255px" height="162px">
										<tr>
											<td><b><vgcutil:message key="jsp.editarindicador.ficha.caracteristica" /></b></td>
										</tr>
										<tr>
											<bean:define id="retoAumento">
												<bean:write name="editarIndicadorForm" property="caracteristicaRetoAumento" />
											</bean:define>
											<td><html:radio property="caracteristica" value="<%=retoAumento%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.retoaumento" /></td>
										</tr>
										<tr>
											<bean:define id="retoDisminucion">
												<bean:write name="editarIndicadorForm" property="caracteristicaRetoDisminucion" />
											</bean:define>
											<td><html:radio property="caracteristica" value="<%=retoDisminucion%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.retodisminucion" /></td>
										</tr>
										<tr>
											<bean:define id="condicionValorMaximo">
												<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMaximo" />
											</bean:define>
											<td><html:radio property="caracteristica" value="<%=condicionValorMaximo%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionvaloresmaximos" /></td>
										</tr>
										<tr>
											<bean:define id="condicionValorMinimo">
												<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMinimo" />
											</bean:define>
											<td><html:radio property="caracteristica" value="<%=condicionValorMinimo%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionvaloresminimos" /></td>
										</tr>
										<tr>
											<bean:define id="condicionBandas">
												<bean:write name="editarIndicadorForm" property="caracteristicaCondicionBandas" />
											</bean:define>
											<td><html:radio property="caracteristica" value="<%=condicionBandas%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionbandas" /></td>
										</tr>
									</table>
								</td>
								<td>
									<table class="contenedorBotonesSeleccion" width="255px">
										<tr>
											<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.tipocorte" /></b></td>
										</tr>
										<tr>
											<bean:define id="corteLongitudinal">
												<bean:write name="editarIndicadorForm" property="tipoCorteLongitudinal" />
											</bean:define>
											<td width="110px"><html:radio property="corte" value="<%=corteLongitudinal%>" disabled="true"/> <vgcutil:message key="jsp.editarindicador.ficha.longitudinal" /></td>
	
	
											<bean:define id="corteTransversal">
												<bean:write name="editarIndicadorForm" property="tipoCorteTransversal" />
											</bean:define>
											<td><html:radio property="corte" value="<%=corteTransversal%>" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.transversal" /></td>
										</tr>
									</table>
									<br>
									<table width="255px">
										<logic:notEqual name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaSumatoria%>">
											<tr id="trPeriodos" name="trPeriodos">
												<td colspan="2">
													<table class="contenedorBotonesSeleccion" width="255px">
														<tr>
															<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.medicionesseingresaran" /></b></td>
														</tr>
														<tr>
															<bean:define id="medicionEnPeriodo"><bean:write name="editarIndicadorForm" property="tipoMedicionEnPeriodo" /></bean:define>
															<td width="110px"><html:radio property="tipoCargaMedicion" disabled="true" value="<%=medicionEnPeriodo%>" /><vgcutil:message key="jsp.editarindicador.ficha.enelperiodo" /></td>
															<bean:define id="medicionAlPeriodo"><bean:write name="editarIndicadorForm" property="tipoMedicionAlPeriodo" /></bean:define>
															<td><html:radio property="tipoCargaMedicion" value="<%=medicionAlPeriodo%>" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.alperiodo" /></td>
														</tr>
													</table>
												</td>
										</tr>
										</logic:notEqual>
										<logic:equal name="editarIndicadorForm" property="naturaleza" value="<%=naturalezaSumatoria%>">
											<tr id="trTipoMediciones" name="trTipoMediciones">
												<td>
													<table class="contenedorBotonesSeleccion" width="255px">
														<tr>
															<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.medicionessetomaran" /></b></td>
														</tr>
														<tr>
															<bean:define id="medicionSeSuman"><bean:write name="editarIndicadorForm" property="tipoSumaSumarMediciones" /></bean:define>
															<td width="110px"><html:radio property="tipoSumaMedicion" value="<%=medicionSeSuman%>" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.sumandomediciones" /></td>
															<bean:define id="medicionSeToman"><bean:write name="editarIndicadorForm" property="tipoSumaUltimoPeriodo" /></bean:define>
															<td><html:radio property="tipoSumaMedicion" value="<%=medicionSeToman%>" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.ultimamedicion" /></td>
														</tr>
													</table>
												</td>
											</tr>
										</logic:equal>
									</table>
									<br>
									<table class="contenedorBotonesSeleccion" width="255px">
										<tr>
											<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.tipo" /></b></td>
										</tr>
										<tr>
											<bean:define id="resGuiaResultado">
												<bean:write name="editarIndicadorForm" property="tipoResGuiaResultado" />
											</bean:define>
											<td width="110px"><html:radio disabled="true" property="tipoGuiaResultado" value="<%=resGuiaResultado%>" />&nbsp;<vgcutil:message key="jsp.editarindicador.ficha.resultado" /></td>
	
											<bean:define id="resGuiaGuia">
												<bean:write name="editarIndicadorForm" property="tipoResGuiaGuia" />
											</bean:define>
											<td><html:radio disabled="true" property="tipoGuiaResultado" value="<%=resGuiaGuia%>" />&nbsp;<vgcutil:message key="jsp.editarindicador.ficha.guia" /></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<hr width="100%">
								</td>
							</tr>
							<tr>
								<td align="left" colspan="2">
									<html:checkbox disabled="true" styleClass="botonSeleccionMultiple" property="valorInicialCero" /><vgcutil:message key="jsp.editarindicador.ficha.asignarcerovalorinicialcomienzoejercicio" />
								</td>
							</tr>

							<tr>
								<td colspan="2">
									<bean:define id="tipoAcotamientoValorFijo">
										<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorFijo" />
									</bean:define>
									<bean:define id="tipoAcotamientoValorIndicador">
										<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorIndicador" />
									</bean:define>
									<table class="contenedorBotonesSeleccion" width="510px" cellpadding="3" cellspacing="1">
										<%-- Título: Zona Verde--%>
										<tr height="20px">
											<td colspan="2"><b>Parámetros de Acotamiento</b></td>
										</tr>
										<tr height="25px">
											<%--
											<td id="parametroAcotamientoPrimario" width="50%">
												<table class="panelContenedor" height="25px">
													<tr>
														<td colspan="2">
															<html:radio property="parametroTipoAcotamientoSuperior" disabled="true" value="<%= tipoAcotamientoValorFijo%>" />Valor Absoluto&nbsp;
															<html:radio property="parametroTipoAcotamientoSuperior" disabled="true" value="<%= tipoAcotamientoValorIndicador%>" />Indicador
														</td>
													</tr>
													<tr>
														<td width="60px">Superior</td>
														<td id="parametroAcotamientoSuperior">
															<div id="parametroAcotamientoSuperiorFijo">
																<html:text property="parametroSuperiorValorFijo" size="20" maxlength="50" styleClass="cuadroTexto" readonly="true"/>
															</div>
															<div id="parametroAcotamientoSuperiorIndicador">
																<html:text property="parametroSuperiorIndicadorNombre" size="33" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
															</div>
														</td>
													</tr>
												</table>
											</td>
											--%>
											<td id="parametroAcotamientoSecundario" width="50%">
												<table class="panelContenedor" height="25px">
													<tr>
														<td colspan="2">
															<html:radio property="parametroTipoAcotamientoInferior" value="<%= tipoAcotamientoValorFijo%>" disabled="true"/>Valor Absoluto&nbsp;
															<html:radio property="parametroTipoAcotamientoInferior" value="<%= tipoAcotamientoValorIndicador%>" disabled="true"/>Indicador
														</td>
													</tr>
													<tr>
														<td width="60px">Inferior</td>
														<td id="parametroAcotamientoInferior">
															<logic:equal name="editarIndicadorForm" property="parametroTipoAcotamientoInferior" value="1">
																<div id="parametroAcotamientoInferiorFijo">
																	<html:text property="parametroInferiorValorFijo" size="20" maxlength="50" styleClass="cuadroTexto" readonly="true"/>
																</div>
															</logic:equal>
															<logic:equal name="editarIndicadorForm" property="parametroTipoAcotamientoInferior" value="2">
																<div id="parametroAcotamientoInferiorIndicador">
																	<html:text property="parametroInferiorIndicadorNombre" size="33" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
																</div>
															</logic:equal>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
							
					<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="alertas">

						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>

						<%-- Cuerpo de la Pestaña --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" border="0">
	
										<%-- Titulo --%>
										<tr>
											<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.controlbandametaprogramado" /></b></td>
										</tr>
										<tr>
											<%-- Zona Verde --%>
											<td>
											<table class="contenedorBotonesSeleccion" width="255px" cellpadding="3" cellspacing="1">
	
												<%-- Título: Zona Verde--%>
												<tr>
													<td><b><vgcutil:message key="jsp.editarindicador.ficha.zonaverde" /></b></td>
												</tr>
	
												<%-- Botones de Radio --%>
												<tr>
													<bean:define id="alertaPorcentaje">
														<bean:write name="editarIndicadorForm" property="tipoAlertaPorcentaje" />
													</bean:define>
													<td>
														<html:radio property="alertaTipoZonaVerde" disabled="true" value="<%=alertaPorcentaje%>" /><vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
													</td>
												</tr>
												<tr>
													<bean:define id="valorAbsolutoMagnitud">
														<bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />
													</bean:define>
													<td>
														<html:radio property="alertaTipoZonaVerde" disabled="true" value="<%=valorAbsolutoMagnitud%>" /><vgcutil:message key="jsp.editarindicador.ficha.valorabsolutomagnitud" />
													</td>
												</tr>
												<tr>
													<bean:define id="zonaValorAbsolutoIndicador">
														<bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />
													</bean:define>
													<td>
														<html:radio property="alertaTipoZonaVerde" disabled="true" value="<%=zonaValorAbsolutoIndicador%>" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutoindicador" />
													</td>
												</tr>
	
												<%-- Porcentaje --%>
												<tr>
													<td id="celdaZonaVerde">
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaVerde" value="0">
															<vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
														</logic:equal>
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaVerde" value="1">
															<vgcutil:message key="jsp.editarindicador.ficha.magnitud" />
														</logic:equal>
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaVerde" value="2">
															<vgcutil:message key="jsp.editarindicador.ficha.indicador" />
														</logic:equal>
														<html:text property="alertaMetaZonaVerde" readonly="true" size="5" maxlength="3" styleClass="cuadroTexto" />
													</td>
												</tr>
	
												<%-- Botones de Radio: Fijo o Variable --%>
												<logic:notEqual name="editarIndicadorForm" property="alertaTipoZonaVerde" value="0">
													<tr>
														<td>
															<div id="divAlertaValorVerde" style="visibility: visible;">
																<html:radio disabled="true" property="alertaValorVariableZonaVerde" value="0" />
																<vgcutil:message key="jsp.editarindicador.ficha.fijo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio property="alertaValorVariableZonaVerde" disabled="true" value="1" /> 
																<vgcutil:message key="jsp.editarindicador.ficha.variable" />
															</div>
														</td>
													</tr>
												</logic:notEqual>
											</table>
	
											</td>
	
											<%-- Zona Amarilla --%>
											<td>
											<table class="contenedorBotonesSeleccion" width="255px" cellpadding="3" cellspacing="1">
	
												<%-- Título: Zona Amarilla --%>
												<tr>
													<td><b><vgcutil:message key="jsp.editarindicador.ficha.zonaamarilla" /></b></td>
												</tr>
	
												<%-- Botones de Radio --%>
												<tr>
													<bean:define id="alertaPorcentaje">
														<bean:write name="editarIndicadorForm" property="tipoAlertaPorcentaje" />
													</bean:define>
													<td><html:radio disabled="true" property="alertaTipoZonaAmarilla" value="<%=alertaPorcentaje%>" /> <vgcutil:message key="jsp.editarindicador.ficha.porcentaje" /></td>
												</tr>
												<tr>
													<bean:define id="valorAbsolutoMagnitud">
														<bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />
													</bean:define>
													<td><html:radio disabled="true" property="alertaTipoZonaAmarilla" value="<%=valorAbsolutoMagnitud%>" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutomagnitud" /></td>
												</tr>
												<tr>
													<bean:define id="zonaValorAbsolutoIndicador">
														<bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />
													</bean:define>
													<td><html:radio disabled="true" property="alertaTipoZonaAmarilla" value="<%=zonaValorAbsolutoIndicador%>" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutoindicador" /></td>
	
												</tr>
	
												<%-- Porcentaje --%>
												<tr>
													<td id="celdaZonaAmarilla">
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="0">
															<vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
														</logic:equal>
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="1">
															<vgcutil:message key="jsp.editarindicador.ficha.magnitud" />
														</logic:equal>
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="2">
															<vgcutil:message key="jsp.editarindicador.ficha.indicador" />
														</logic:equal>
														<logic:notEqual name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="2">
															<html:text property="alertaMetaZonaAmarilla" size="5" readonly="true" styleClass="cuadroTexto" />
														</logic:notEqual>
														<logic:equal name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="2">
															<html:text property="nombreIndicadorZonaAmarilla" size="25" readonly="true" styleClass="cuadroTexto" />
														</logic:equal>
													</td>
												</tr>
	
												<%-- Botones de Radio: Fijo o Variable --%>
												<logic:notEqual name="editarIndicadorForm" property="alertaTipoZonaAmarilla" value="0">
													<tr>
														<td>
															<div id="divAlertaValorAmarilla" style="visibility: visible;">
																<html:radio property="alertaValorVariableZonaAmarilla" value="0" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.fijo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<html:radio property="alertaValorVariableZonaAmarilla" value="1" disabled="true"/><vgcutil:message key="jsp.editarindicador.ficha.variable" />
															</div>
														</td>
													</tr>
												</logic:notEqual>
	
											</table>
											</td>
	
										</tr>
	
										<tr>
											<td>&nbsp;</td>
										</tr>
	
									</table>
								</td>
							</tr>
							<tr height="10px">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>
							
					<%-- Responsables --%>
					<vgcinterfaz:panelContenedor anchoPestana="145" nombre="responsables">

						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>

						<%-- Cuerpo de la Pestaña --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">

							<%-- Responsables Fijar Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.responsablefijarmeta" /></td>
								<td>
									<html:text property="responsableFijarMeta" size="50" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								</td>
							</tr>

							<%-- Responsables Lograr Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.responsablelograrmeta" /></td>
								<td>
									<html:text property="responsableLograrMeta" size="50" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								</td>
							</tr>

							<%-- Responsables Seguimiento--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.responsableseguimiento" /></td>
								<td>
									<html:text property="responsableSeguimiento" size="50" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								</td>
							</tr>

							<%-- Responsables Lograr Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.responsablecargarmeta" /></td>
								<td>
									<html:text property="responsableCargarMeta" size="50" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								</td>
							</tr>

							<%-- Responsables Lograr Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.responsablecargarejecutado" /></td>
								<td>
									<html:text property="responsableCargarEjecutado" size="50" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								</td>
							</tr>

							<tr height="185px">
								<td colspan="2">&nbsp;</td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

					<%-- Relaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="145" nombre="relaciones">

						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.relaciones" />
						</vgcinterfaz:panelContenedorTitulo>

						<%-- Cuerpo de la Pestaña --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="530px">
							<%-- Url --%>
							<tr height="18px">
								<td align="left" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.url" /></td>
							</tr>
							<tr>
								<td align="left" valign="top"><html:textarea styleClass="cuadroTexto" cols="65" rows="6" property="url" readonly="true"></html:textarea></td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			asignarSeriesTiempo();
			if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaFormula" />)
				setListaInsumosFormula('<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>', '<bean:write name="editarIndicadorForm" property="separadorRuta" ignore="true"/>');
			else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoOrdinal" />) 
				setEscalaCualitativa();
			else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />) 
				setEscalaCualitativa();
		</script>
	</tiles:put>
</tiles:insert>