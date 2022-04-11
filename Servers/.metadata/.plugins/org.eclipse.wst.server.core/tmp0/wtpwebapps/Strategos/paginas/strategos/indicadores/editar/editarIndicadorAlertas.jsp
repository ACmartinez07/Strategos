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

<%-- Alertas --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" style="width: 100%; height: 100%; border-spacing:0px; border-collapse: separate; padding: 3px;">
	<%-- 
	<tr>
		<td>
		<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
			<tr>
				<td><b><vgcutil:message key="jsp.editarindicador.ficha.controlbandasminimosymaximos" /></b></td>
			</tr>

			<tr>
				<td><vgcutil:message key="jsp.editarindicador.ficha.porcentaje" /></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	--%>
	<tr style="height: 100%;">
		<td>
		<table class="contenedorBotonesSeleccion" style="width: 100%; height: 100%; border-spacing:3px; border-collapse: separate; padding: 3px;">

			<%-- Titulo --%>
			<tr>
				<td colspan="2"><b><vgcutil:message key="jsp.editarindicador.ficha.controlbandametaprogramado" /></b></td>
			</tr>

			<bean:define scope="page" id="alertaDisabled" value="false"></bean:define>
			<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
				<bean:define scope="page" id="alertaDisabled" value="true"></bean:define>
			</logic:equal>
			<tr>
				<%-- Zona Verde --%>
				<td>
				<table class="contenedorBotonesSeleccion" width="312px" cellpadding="3" cellspacing="1">

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
							<html:radio property="alertaTipoZonaVerde" disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" value="<%=alertaPorcentaje%>" onclick="establecerVisibilidad('divAlertaValorVerde', 'Verde'); establecerTipoAlerta(this.value, 'alertaMetaZonaVerde', 'celdaZonaVerde')" /> <vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
						</td>
					</tr>
					<tr>
						<bean:define id="valorAbsolutoMagnitud">
							<bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />
						</bean:define>
						<td><html:radio property="alertaTipoZonaVerde" disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" value="<%=valorAbsolutoMagnitud%>" onclick="establecerVisibilidad('divAlertaValorVerde', 'Verde'); establecerTipoAlerta(this.value, 'alertaMetaZonaVerde', 'celdaZonaVerde')" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutomagnitud" /></td>
					</tr>
					<tr>
						<bean:define id="zonaValorAbsolutoIndicador">
							<bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />
						</bean:define>
						<td><html:radio property="alertaTipoZonaVerde" disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" value="<%=zonaValorAbsolutoIndicador%>" onclick="establecerVisibilidad('divAlertaValorVerde', 'Verde'); establecerTipoAlerta(this.value, 'alertaMetaZonaVerde', 'celdaZonaVerde')" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutoindicador" /></td>

					</tr>

					<%-- Porcentaje --%>
					<tr>
						<td id="celdaZonaVerde">
							<vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
							<html:text property="alertaMetaZonaVerde" disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" size="27" maxlength="6" styleClass="cuadroTexto" />
						</td>
					</tr>

					<%-- Botones de Radio: Fijo o Variable --%>
					<tr>
						<td>
							<div id="divAlertaValorVerde" style="visibility: visible;">
								<html:radio disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" property="alertaValorVariableZonaVerde" value="0" />
								<vgcutil:message key="jsp.editarindicador.ficha.fijo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio property="alertaValorVariableZonaVerde" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" value="1" /> 
								<vgcutil:message key="jsp.editarindicador.ficha.variable" />
							</div>
						</td>
					</tr>
				</table>

				</td>

				<%-- Zona Amarilla --%>
				<td>
				<table class="contenedorBotonesSeleccion" width="312px" cellpadding="3" cellspacing="1">

					<%-- Título: Zona Amarilla --%>
					<tr>
						<td><b><vgcutil:message key="jsp.editarindicador.ficha.zonaamarilla" /></b></td>
					</tr>

					<%-- Botones de Radio --%>
					<tr>
						<bean:define id="alertaPorcentaje">
							<bean:write name="editarIndicadorForm" property="tipoAlertaPorcentaje" />
						</bean:define>
						<td><html:radio disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" property="alertaTipoZonaAmarilla" value="<%=alertaPorcentaje%>" onclick="establecerVisibilidad('divAlertaValorAmarilla', 'Amarilla');establecerTipoAlerta(this.value, 'alertaMetaZonaAmarilla', 'celdaZonaAmarilla')" /> <vgcutil:message key="jsp.editarindicador.ficha.porcentaje" /></td>
					</tr>
					<tr>
						<bean:define id="valorAbsolutoMagnitud">
							<bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />
						</bean:define>
						<td><html:radio disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" property="alertaTipoZonaAmarilla" value="<%=valorAbsolutoMagnitud%>" onclick="establecerVisibilidad('divAlertaValorAmarilla', 'Amarilla');establecerTipoAlerta(this.value, 'alertaMetaZonaAmarilla', 'celdaZonaAmarilla')" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutomagnitud" /></td>
					</tr>
					<tr>
						<bean:define id="zonaValorAbsolutoIndicador">
							<bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />
						</bean:define>
						<td><html:radio disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" property="alertaTipoZonaAmarilla" value="<%=zonaValorAbsolutoIndicador%>" onclick="establecerVisibilidad('divAlertaValorAmarilla', 'Amarilla');establecerTipoAlerta(this.value, 'alertaMetaZonaAmarilla', 'celdaZonaAmarilla')" /> <vgcutil:message key="jsp.editarindicador.ficha.valorabsolutoindicador" /></td>

					</tr>

					<%-- Porcentaje --%>
					<tr>
						<td id="celdaZonaAmarilla">
							<vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />
							<html:text property="alertaMetaZonaAmarilla" size="27" disabled="<%= ((new Boolean(alertaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" maxlength="6" styleClass="cuadroTexto" />
						</td>
					</tr>

					<%-- Botones de Radio: Fijo o Variable --%>
					<tr>
						<td>
						<div id="divAlertaValorAmarilla" style="visibility: visible;"><html:radio property="alertaValorVariableZonaAmarilla" value="0" /> <vgcutil:message key="jsp.editarindicador.ficha.fijo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio property="alertaValorVariableZonaAmarilla" value="1" /> <vgcutil:message key="jsp.editarindicador.ficha.variable" /></div>

						</td>
					</tr>

				</table>
				</td>

			</tr>

			<tr style="height: 160px;">
				<td>&nbsp;</td>
			</tr>

		</table>
		</td>
	</tr>
</table>
