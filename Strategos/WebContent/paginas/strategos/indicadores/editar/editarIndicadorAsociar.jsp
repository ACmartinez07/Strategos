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

<%-- Asociar --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0">
	<!-- Tipo de Asociado -->
	<tr>
		<td align="left" valign="top" width="110px"><vgcutil:message key="jsp.editarindicador.ficha.tiporelacion" /></td>
		<td align="left" valign="top">
			<div style="overflow:auto; visibility:visible; height:200px; width:200px;" >
				<html:hidden property="seriesIndicador" />
				<bean:define scope="page" id="serieDisabled" value="false"></bean:define>
				<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
					<bean:define scope="page" id="serieDisabled" value="true"></bean:define>
				</logic:equal>
				<logic:iterate id="serie" name="editarIndicadorForm" property="seriesTiempo">
					<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
						<input type="checkbox" class="botonSeleccionMultiple" value='<bean:write name="serie" property="serieId" />' name='serie' onclick="eventoOnClickSerie(this);" disabled="<%= ((new Boolean(serieDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>
					</logic:equal>
					<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
						<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
							<input type="checkbox" class="botonSeleccionMultiple" value='<bean:write name="serie" property="serieId" />' name='serie' onclick="eventoOnClickSerie(this);" disabled="<%= ((new Boolean(serieDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>
						</logic:equal>
						<logic:notEqual name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
							<input type="checkbox" class="botonSeleccionMultiple" value='<bean:write name="serie" property="serieId" />' name='serie' onclick="eventoOnClickSerie(this);" />
						</logic:notEqual>
					</logic:notEqual>
					<bean:write name="serie" property="nombre" />
					<br>
				</logic:iterate>
			</div>
		</td>
	</tr>
</table>
