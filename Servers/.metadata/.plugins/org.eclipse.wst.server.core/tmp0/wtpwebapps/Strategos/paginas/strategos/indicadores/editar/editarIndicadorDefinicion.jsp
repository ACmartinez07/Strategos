<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<bean:define id="bloquearForma" toScope="page">
	<logic:notEmpty name="editarIndicadorForm" property="bloqueado">
		<bean:write name="editarIndicadorForm" property="bloqueado" />
	</logic:notEmpty>
	<logic:empty name="editarIndicadorForm" property="bloqueado">
		false
	</logic:empty>
</bean:define>
<bean:define scope="page" id="iniciativaDisabled" value="false"></bean:define>
<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
	<bean:define scope="page" id="iniciativaDisabled" value="true"></bean:define>
</logic:equal>

<%-- Definición --%>
<vgcinterfaz:contenedorPaneles height="275" width="661" nombre="editarIndicadorPanelDefinicion" mostrarSelectoresPaneles="false">
	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionSimple" mostrarBorde="false">
		<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

			<!-- Campo Codigo de Enlace-->
			<tr>
				<td style="text-align: left; width: 150px;"><vgcutil:message key="jsp.editarindicador.ficha.codigoenlace" /></td>
				<td>
					<html:text property="codigoEnlace" size="62" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="100" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" />
					<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
						<logic:notEqual name="editarIndicadorForm" property="bloquearIndicadorIniciativa" value="true">
							<logic:equal scope="session" name="cliente" value="PGN">
								<img style="cursor:pointer" onclick="seleccionarCodigoEnlace()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionar.codigoenlace.seleccionar' />">
								<img style="cursor:pointer" onclick="limpiarCodigoEnlace()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							</logic:equal>
						</logic:notEqual>
					</logic:notEqual>
				</td>										
			</tr>

			<!-- Campo Codigo de Enlace Parcial-->
			<tr>
				<td style="text-align: left; width: 150px;"><vgcutil:message key="jsp.editarindicador.ficha.codigoparcialenlace" /></td>
				<td><html:text property="enlaceParcial" size="62" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
			</tr>

			<tr height="264px">
				<td colspan="2">&nbsp;</td>
			</tr>

		</table>

	</vgcinterfaz:panelContenedor>

	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionFormula" mostrarBorde="false">
		<%-- Naturaleza Fórmula --%>
		<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

			<!-- Campo Codigo de Enlace-->
			<tr>
				<td>
					<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

						<!-- Campo Codigo de Enlace-->
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.codigoenlace" /></td>
							<td align="right"><html:text property="codigoEnlaceFormula" size="78" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="100" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
						</tr>

						<!-- Campo Codigo de Enlace Parcial-->
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.codigoparcialenlace" /></td>
							<td align="right"><html:text property="enlaceParcialFormula" size="78" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="20" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!-- Indicadores -->
			<tr>
				<td align="center">
					<table class="contenedorBotonesSeleccion" width="100%" height="130px">
						<tr>
							<td colspan="3"><b><vgcutil:message key="jsp.editarindicador.ficha.formula.indicadores" /></b></td>
						</tr>
						<tr>
							<td>
								<input id="insumoSeleccionado" type="hidden" name="insumoSeleccionado" /> 
								<html:hidden property="insumosFormula" />
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true"> 
									<input type="button" style="width:90%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />" onclick="seleccionarIndicadoresInsumoFormula('editarIndicadorForm', 'agregarIndicadoresInsumoFormula()', getURL());">
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:90%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />" onclick="seleccionarIndicadoresInsumoFormula('editarIndicadorForm', 'agregarIndicadoresInsumoFormula()', getURL());">
								</logic:equal>
							</td>
							<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
								<td><input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertarenformula" />" onclick="insertarInsumoEnFormula();"></td>
								<td><input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />" onclick="removerInsumoFormula();"></td>
							</logic:notEqual>
							<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
								<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertarenformula" />" onclick="insertarInsumoEnFormula();"></td>
								<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />" onclick="removerInsumoFormula();"></td>
							</logic:equal>
						</tr>
						<tr height="80px">
							<td colspan="3">
								<bean:define scope="page" id="anchoListadoIndicadoresInsumos" value="350"></bean:define>
								<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
									<bean:define scope="page" id="anchoListadoIndicadoresInsumos" value="100%"></bean:define>
								</logic:equal>
								<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoListadoIndicadoresInsumos %>" splitterId="splitInsumosFormula" overflowPanelDerecho="auto" overflowPanelIzquierdo="auto">
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

			<!-- Editor de Fórmula -->
			<tr>
				<td align="center">
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="4"><b><vgcutil:message key="jsp.editarindicador.ficha.formula.editorformula" /></b></td>
					</tr>
					<tr>
						<td colspan="4">
							<html:checkbox styleClass="botonSeleccionMultiple" property="asignarInventario"></html:checkbox>
							<vgcutil:message key="jsp.editarindicador.ficha.asignarInventario" />
						</td>
					</tr>
					<tr>
						<td colspan="4"><html:textarea property="formula" rows="4" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" style="width:100%" styleClass="cuadroTexto" onkeyup="validarTextoFormula()" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:textarea></td>
					</tr>
					
					<tr>
						<td><input id="insumoFormulaSeleccionado" type="hidden" name="insumoFormulaSeleccionado" />

						<table cellpadding="2" cellspacing="0" border="0">
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="+" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '+')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="-" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '-')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="*" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '*')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="/" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '/')"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="+" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '+')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="-" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '-')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="*" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '*')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="/" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '/')"></td>
								</logic:equal>
							</tr>
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="(" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '(')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="." onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '.')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value=")" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ')')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="^" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '^')"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="(" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '(')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="." onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '.')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value=")" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ')')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="^" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '^')"></td>
								</logic:equal>
							</tr>
						</table>
						</td>
						<td>
						<table cellpadding="2" cellspacing="0" border="0">
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="0" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '0')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="1" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '1')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="2" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '2')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="3" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '3')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="4" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '4')"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="0" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '0')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="1" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '1')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="2" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '2')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="3" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '3')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="4" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '4')"></td>
								</logic:equal>
							</tr>
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="5" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '5')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="6" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '6')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="7" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '7')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="8" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '8')"></td>
									<td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="9" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '9')"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="5" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '5')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="6" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '6')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="7" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '7')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="8" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '8')"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="9" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '9')"></td>
								</logic:equal>
							</tr>
						</table>
						</td>
						<td>
						<table cellpadding="2" cellspacing="0" border="0">
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":S" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':S')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.sumatoria" />"></td>
									<td><input type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":@" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':@')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.anoanterior" />"></td>
									<td><input type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":#" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':#')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.cierredelano" />"></td>
									<td><input type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":%" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':%')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.primerperiodo" />"></td>
									<!-- <td><input type="button" style="width:20px;height:20px" class="cuadroTexto" value="[P]" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '[P]')"></td> -->
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":S" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':S')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.sumatoria" />"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":@" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':@')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.anoanterior" />"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":#" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':#')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.cierredelano" />"></td>
									<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:30px; height:20px; text-align:center;" class="cuadroTexto" value=":%" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, ':%')" title="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.periodo.primerperiodo" />"></td>
									<!-- <td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:20px;height:20px" class="cuadroTexto" value="[P]" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, '[P]')"></td> -->
								</logic:equal>
							</tr>
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td colspan="5"><input type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.limpiar" />" onclick="document.editarIndicadorForm.formula.value=''"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td colspan="5"><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.limpiar" />" onclick="document.editarIndicadorForm.formula.value=''"></td>
								</logic:equal>
							</tr>
						</table>
						</td>
						<td>
						<table cellpadding="2" cellspacing="0" border="0">
							<tr>
								<td><html:select property="funcion" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto">
									<html:optionsCollection property="funcionesFormula" value="interfaz" label="nombre" />
								</html:select></td>
							</tr>
							<tr>
								<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
									<td colspan="5"><input type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertar" />" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, document.editarIndicadorForm.funcion.options[document.editarIndicadorForm.funcion.selectedIndex].value)"></td>
								</logic:notEqual>
								<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
									<td colspan="5"><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertar" />" onclick="insertAtCursorPosition(document.editarIndicadorForm.formula, document.editarIndicadorForm.funcion.options[document.editarIndicadorForm.funcion.selectedIndex].value)"></td>
								</logic:equal>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

	</vgcinterfaz:panelContenedor>

	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionCualitativo" mostrarBorde="false">

		<table class="contenedorBotonesSeleccion" width="530px" height="320px">
			<tr height="20px">
				<td align="left" valign="top"><b><vgcutil:message key="jsp.editarindicador.ficha.cualitativo.categoriasmedicion" /></b></td>
			</tr>
			<tr height="20px">
				<td align="left" valign="top">&nbsp;</td>
			</tr>
			<tr height="270">
				<td align="left" valign="top">
				<table class="panelContenedor">
					<tr height="25px">
						<td align="right" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.cualitativo.categoria" /></td>
						<td align="left" valign="top" width="340px"><html:select disabled="<%= Boolean.parseBoolean(bloquearForma) %>" style="width: 330px" property="categoriaId" styleClass="cuadroTexto" size="1">
							<html:option value=""></html:option>
							<html:optionsCollection property="categorias" value="categoriaId" label="nombre" />
						</html:select></td>
						<td align="left" valign="top"><input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.cualitativo.agregar" />" onclick="agregarCategoriaEscala();"></td>
					</tr>
					<tr height="25px">
						<td colspan="2" rowspan="5" class="cuadroTexto" valign="top">
						<div style="position:relative; overflow: scroll; height: 235px">
						<table id="tablaListaCategorias" style="width: 100%">
							<tbody class="cuadroTexto">
							</tbody>
						</table>
						</div>
						</td>
						<td align="left" valign="top"><input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.cualitativo.insertar" />" onclick="insertarCategoriaEscala();"></td>
					</tr>
					<tr height="25px">
						<td align="left" valign="top"><input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.cualitativo.remover" />" onclick="removerCategoriaEscala();"></td>
					</tr>
					<tr height="30px">
						<td>&nbsp;</td>
					</tr>
					<tr height="60px">
						<td>
						<div id="definicionCualitativoOrden">
						<table class="panelContenedor">
							<tr>
								<td><vgcutil:message key="jsp.editarindicador.ficha.cualitativo.orden" /></td>
							</tr>
							<tr>
								<td align="left" valign="top"><img style="cursor: pointer" title="<vgcutil:message key="boton.subir.alt"/>" src="<html:rewrite page="/componentes/barraHerramientas/flechaArriba_1.gif"/>" border="0" onclick="moverCategoriaEscala('arriba')"></td>
							</tr>
							<tr>
								<td align="left" valign="top"><img style="cursor: pointer" title="<vgcutil:message key="boton.bajar.alt"/>" src="<html:rewrite page="/componentes/barraHerramientas/flechaAbajo_1.gif"/>" border="0" onclick="moverCategoriaEscala('abajo')"></td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
					<tr height="80px">
						<td align="left" valign="top">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</vgcinterfaz:panelContenedor>

	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionSumatoria" mostrarBorde="false">
		<%-- Cuerpo de la Pestaña --%>
		<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0">
			<%-- Indicador Base --%>
			<tr height="25px">
				<td align="right" width="150px"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" />&nbsp;&nbsp;</td>
				<td width="600px"><html:text property="indicadorSumatoria" size="80" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarIndicadorBase('sumatoria');" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarindicadorbase" />">&nbsp;<img style="cursor: pointer" onclick="limpiarIndicadorBase('sumatoria');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
			</tr>
			<%-- Frecuencia --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" />&nbsp;&nbsp;</td>
				<td><html:text property="indicadorSumatoriaFrecuencia" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<%-- Unidad --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.unidad" />&nbsp;&nbsp;</td>
				<td><html:text property="indicadorSumatoriaUnidad" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</vgcinterfaz:panelContenedor>

	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionPromedio" mostrarBorde="false">
		<%-- Cuerpo de la Pestaña --%>
		<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0">
			<%-- Indicador Base --%>
			<tr height="25px">
				<td align="right" width="150px"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" />&nbsp;&nbsp;</td>

				<td width="600px"><html:text property="indicadorPromedio" size="80" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarIndicadorBase('promedio');" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarindicadorbase" />">&nbsp;<img style="cursor: pointer" onclick="limpiarIndicadorBase('promedio');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
			</tr>
			<%-- Frecuencia --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" />&nbsp;&nbsp;</td>

				<td><html:text property="indicadorPromedioFrecuencia" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<%-- Unidad --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.unidad" />&nbsp;&nbsp;</td>

				<td><html:text property="indicadorPromedioUnidad" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</vgcinterfaz:panelContenedor>

	<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionIndice" mostrarBorde="false">
		<%-- Cuerpo de la Pestaña --%>
		<table class="panelContenedor" cellpadding="0" cellspacing="0">
			<%-- Indicador Base --%>
			<tr height="25px">
				<td align="right" width="150px"><vgcutil:message key="jsp.editarindicador.ficha.indicadorbase" />&nbsp;&nbsp;</td>
				<td width="600px"><html:text property="indicadorIndice" size="80" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarIndicadorBase('indice');" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarindicadorbase" />">&nbsp;<img style="cursor: pointer" onclick="limpiarIndicadorBase('indice');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
			</tr>
			<%-- Frecuencia --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" />&nbsp;&nbsp;</td>
				<td><html:text property="indicadorIndiceFrecuencia" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<%-- Unidad --%>
			<tr height="25px">
				<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.unidad" />&nbsp;&nbsp;</td>
				<td><html:text property="indicadorIndiceUnidad" size="85" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><bean:define type="java.lang.String" id="diferenciaPorcentualIndicadorIndice" name="editarIndicadorForm" property="tipoVariacionDiferenciaPorcentualIndicadorIndice"></bean:define><bean:define id="diferenciaAbsolutaIndicadorIndice" type="java.lang.String" name="editarIndicadorForm" property="tipoVariacionDiferenciaAbsolutaIndicadorIndice"></bean:define><bean:define id="variacionPorcentualIndicadorIndice" type="java.lang.String" name="editarIndicadorForm" property="tipoVariacionVariacionPorcentualIndicadorIndice"></bean:define>
				<table class="panelContenedor">
					<tr>
						<td align="left" valign="top" width="300px">
						<table class="contenedorBotonesSeleccion" width="300px" height="230px">
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
										<td width="20px"><html:radio property="indicadorIndiceTipoVariacion" value="<%= diferenciaPorcentualIndicadorIndice %>"></html:radio></td>
										<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceDifPorcentual.gif" />"></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td>
								<table class="panelContenedor">
									<tr>
										<td width="20px"><html:radio property="indicadorIndiceTipoVariacion" value="<%= diferenciaAbsolutaIndicadorIndice %>"></html:radio></td>
										<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceDifAbsoluta.gif" />"></td>
									</tr>
								</table>

								</td>
							</tr>
							<tr>
								<td>
								<table class="panelContenedor">
									<tr>
										<td width="20px"><html:radio property="indicadorIndiceTipoVariacion" value="<%= variacionPorcentualIndicadorIndice %>"></html:radio></td>
										<td><img alt="SSS" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/indicadorIndiceVarPorcentual.gif" />"></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
						<td width="10px">&nbsp;</td>
						<td align="left" valign="top" width="350px">
							<bean:define type="java.lang.String" id="anoActualPeriodoAnteriorIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoAnteriorIndicadorIndice"></bean:define> 
							<bean:define type="java.lang.String" id="anoAnteriorMismoPeriodoIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice"></bean:define> 
							<bean:define type="java.lang.String" id="anoActualPrimerPeriodoIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPrimerPeriodoIndicadorIndice"></bean:define> 
							<bean:define type="java.lang.String" id="anoActualPeriodoCierreAnteriorIndicadorIndice" name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice"></bean:define>
							<table class="contenedorBotonesSeleccion" width="350px" height="230px">
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
											<td width="20px"><html:radio property="indicadorIndiceTipoComparacion" onclick="eventoCambioTipoComparacionIndicadorIndice()" value="<%= anoActualPeriodoAnteriorIndicadorIndice %>"></html:radio></td>
											<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoAnteriorIndicadorIndiceNombre" /></td>
										</tr>
									</table>

									</td>
								</tr>
								<tr>
									<td>
									<table class="panelContenedor">
										<tr>
											<td width="20px"><html:radio property="indicadorIndiceTipoComparacion" onclick="eventoCambioTipoComparacionIndicadorIndice()" value="<%= anoAnteriorMismoPeriodoIndicadorIndice %>"></html:radio></td>
											<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoAnteriorMismoPeriodoIndicadorIndiceNombre" /></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td>
									<table class="panelContenedor">
										<tr>
											<td width="20px"><html:radio property="indicadorIndiceTipoComparacion" onclick="eventoCambioTipoComparacionIndicadorIndice()" value="<%= anoActualPrimerPeriodoIndicadorIndice %>"></html:radio></td>
											<td><bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPrimerPeriodoIndicadorIndiceNombre" /></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td>
									<table class="panelContenedor">
										<tr>
											<td width="20px"><html:radio property="indicadorIndiceTipoComparacion" onclick="eventoCambioTipoComparacionIndicadorIndice()" value="<%= anoActualPeriodoCierreAnteriorIndicadorIndice %>"></html:radio></td>
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
		</table>
	</vgcinterfaz:panelContenedor>

</vgcinterfaz:contenedorPaneles>
