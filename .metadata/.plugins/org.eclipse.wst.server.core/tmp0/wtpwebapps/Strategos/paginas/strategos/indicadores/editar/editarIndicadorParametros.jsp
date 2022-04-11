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

<%-- Parámetros --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
	<tr>
		<td>
		<table class="contenedorBotonesSeleccion" width="315px" height="162px">
			<tr>
				<td><b><vgcutil:message key="jsp.editarindicador.ficha.caracteristica" /></b></td>
			</tr>
			<tr>
				<bean:define id="retoAumento">
					<bean:write name="editarIndicadorForm" property="caracteristicaRetoAumento" />
				</bean:define>
				<td><html:radio property="caracteristica" onclick="eventoOnclickCaracteristica()" value="<%=retoAumento%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.retoaumento" /></td>
			</tr>
			<tr>
				<bean:define id="retoDisminucion">
					<bean:write name="editarIndicadorForm" property="caracteristicaRetoDisminucion" />
				</bean:define>
				<td><html:radio property="caracteristica" onclick="eventoOnclickCaracteristica()" value="<%=retoDisminucion%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.retodisminucion" /></td>
			</tr>

			<tr>
				<bean:define id="condicionValorMaximo">
					<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMaximo" />
				</bean:define>
				<td><html:radio property="caracteristica" onclick="eventoOnclickCaracteristica()" value="<%=condicionValorMaximo%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionvaloresmaximos" /></td>
			</tr>

			<tr>
				<bean:define id="condicionValorMinimo">
					<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMinimo" />
				</bean:define>
				<td><html:radio property="caracteristica" onclick="eventoOnclickCaracteristica()" value="<%=condicionValorMinimo%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionvaloresminimos" /></td>
			</tr>

			<tr>
				<bean:define id="condicionBandas">
					<bean:write name="editarIndicadorForm" property="caracteristicaCondicionBandas" />
				</bean:define>
				<td><html:radio property="caracteristica" onclick="eventoOnclickCaracteristica()" value="<%=condicionBandas%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.condicionbandas" /></td>
			</tr>
		</table>

		</td>

		<td>
			<table class="contenedorBotonesSeleccion" width="315px">
				<tr>
					<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.tipocorte" /></b></td>
				</tr>

				<tr>
					<bean:define id="corteLongitudinal">
						<bean:write name="editarIndicadorForm" property="tipoCorteLongitudinal" />
					</bean:define>
					<td width="110px"><html:radio onclick="eventoOnClickCorte()" property="corte" value="<%=corteLongitudinal%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/> <vgcutil:message key="jsp.editarindicador.ficha.longitudinal" /></td>


					<bean:define id="corteTransversal">
						<bean:write name="editarIndicadorForm" property="tipoCorteTransversal" />
					</bean:define>
					<td><html:radio onclick="eventoOnClickCorte()" property="corte" value="<%=corteTransversal%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/><vgcutil:message key="jsp.editarindicador.ficha.transversal" /></td>
				</tr>
			</table>
			<br>
			<table width="315px">
				<tr id="trPeriodos" name="trPeriodos">
					<td align="left">
						<table class="contenedorBotonesSeleccion" width="315px">
							<tr>
								<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.medicionesseingresaran" /></b></td>
							</tr>
							<tr>
								<bean:define id="medicionEnPeriodo"><bean:write name="editarIndicadorForm" property="tipoMedicionEnPeriodo" /></bean:define>
								<td width="110px"><html:radio property="tipoCargaMedicion" value="<%=medicionEnPeriodo%>" /><vgcutil:message key="jsp.editarindicador.ficha.enelperiodo" /></td>
								<bean:define id="medicionAlPeriodo"><bean:write name="editarIndicadorForm" property="tipoMedicionAlPeriodo" /></bean:define>
								<td><html:radio property="tipoCargaMedicion" value="<%=medicionAlPeriodo%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/><vgcutil:message key="jsp.editarindicador.ficha.alperiodo" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="trTipoMediciones" name="trTipoMediciones" style="display:none">
					<td>
						<table class="contenedorBotonesSeleccion" width="315px">
							<tr>
								<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.medicionessetomaran" /></b></td>
							</tr>
							<tr>
								<bean:define id="medicionSeSuman"><bean:write name="editarIndicadorForm" property="tipoSumaSumarMediciones" /></bean:define>
								<td width="110px"><html:radio property="tipoSumaMedicion" value="<%=medicionSeSuman%>" /><vgcutil:message key="jsp.editarindicador.ficha.sumandomediciones" /></td>
								<bean:define id="medicionSeToman"><bean:write name="editarIndicadorForm" property="tipoSumaUltimoPeriodo" /></bean:define>
								<td><html:radio property="tipoSumaMedicion" value="<%=medicionSeToman%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/><vgcutil:message key="jsp.editarindicador.ficha.ultimamedicion" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
			<table class="contenedorBotonesSeleccion" width="315px">
				<tr>
					<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.tipo" /></b></td>
				</tr>

				<tr>
					<bean:define id="resGuiaResultado">
						<bean:write name="editarIndicadorForm" property="tipoResGuiaResultado" />
					</bean:define>
					<td width="110px"><html:radio disabled="<%= (Boolean.parseBoolean(bloquearForma)) %>" property="tipoGuiaResultado" value="<%=resGuiaResultado%>" />&nbsp;<vgcutil:message key="jsp.editarindicador.ficha.resultado" /></td>

					<bean:define id="resGuiaGuia">
						<bean:write name="editarIndicadorForm" property="tipoResGuiaGuia" />
					</bean:define>
					<td><html:radio disabled="<%= (Boolean.parseBoolean(bloquearForma)) %>" property="tipoGuiaResultado" value="<%=resGuiaGuia%>" />&nbsp;<vgcutil:message key="jsp.editarindicador.ficha.guia" /></td>
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
			<bean:define scope="page" id="valorInicialCeroDisabled" value="false"></bean:define> 
			<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
				<bean:define scope="page" id="valorInicialCeroDisabled" value="true"></bean:define>
			</logic:equal>
			<html:checkbox disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || (new Boolean(valorInicialCeroDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" styleClass="botonSeleccionMultiple" property="valorInicialCero">
			</html:checkbox><vgcutil:message key="jsp.editarindicador.ficha.asignarcerovalorinicialcomienzoejercicio" />
		</td>
	</tr>

	<tr>
		<td colspan="2"><bean:define id="tipoAcotamientoValorFijo">
			<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorFijo" />
		</bean:define><bean:define id="tipoAcotamientoValorIndicador">
			<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorIndicador" />
		</bean:define>
		<table class="contenedorBotonesSeleccion" width="655px" cellpadding="3" cellspacing="1">
			<%-- Título: Zona Verde--%>
			<tr height="20px">
				<td colspan="2"><b>Parámetros de Acotamiento</b></td>
			</tr>

			<tr height="25px">
				<td id="parametroAcotamientoPrimario" width="50%">
				<table class="panelContenedor" height="25px">
					<tr>
						<td colspan="2"><html:radio property="parametroTipoAcotamientoSuperior" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" onclick="eventoOnclickParametroTipoAcotamientoSuperior()" value="<%= tipoAcotamientoValorFijo%>" />Valor Absoluto&nbsp;<html:radio property="parametroTipoAcotamientoSuperior" onclick="eventoOnclickParametroTipoAcotamientoSuperior()" value="<%= tipoAcotamientoValorIndicador%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>Indicador</td>
					</tr>
					<tr>
						<td width="60px">Superior</td>
						<td id="parametroAcotamientoSuperior">
							<div id="parametroAcotamientoSuperiorFijo">
								<html:text property="parametroSuperiorValorFijo" size="20" maxlength="50" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>
							</div>
							<div id="parametroAcotamientoSuperiorIndicador">
								<html:text property="parametroSuperiorIndicadorNombre" size="33" readonly="true" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
								<img style="cursor: pointer" onclick="seleccionarIndicadorParametroSuperior();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="Seleccionar Indicador de Parámetro Superior">&nbsp;
								<img style="cursor: pointer" onclick="limpiarIndicadorParametroSuperior();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</div>
						</td>
					</tr>
				</table>
				</td>
				<td id="parametroAcotamientoSecundario" width="50%">
				<table class="panelContenedor" height="25px">
					<tr>
						<td colspan="2"><html:radio property="parametroTipoAcotamientoInferior" onclick="eventoOnclickParametroTipoAcotamientoInferior()" value="<%= tipoAcotamientoValorFijo%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>Valor Absoluto&nbsp;<html:radio property="parametroTipoAcotamientoInferior" onclick="eventoOnclickParametroTipoAcotamientoInferior()" value="<%= tipoAcotamientoValorIndicador%>" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>Indicador</td>
					</tr>
					<tr>
						<td width="60px">Inferior</td>
						<td id="parametroAcotamientoInferior">
						<div id="parametroAcotamientoInferiorFijo"><html:text property="parametroInferiorValorFijo" size="20" maxlength="50" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/></div>
						<div id="parametroAcotamientoInferiorIndicador">
							<html:text property="parametroInferiorIndicadorNombre" size="33" readonly="true" disabled="<%= ((new Boolean(iniciativaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
							<img style="cursor: pointer" onclick="seleccionarIndicadorParametroInferior();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="Seleccionar Indicador de Parámetro Inferior">&nbsp;
							<img style="cursor: pointer" onclick="limpiarIndicadorParametroInferior();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
						</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
