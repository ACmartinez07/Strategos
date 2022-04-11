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

<%-- Relaciones --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0">
	<%-- Url --%>
	<tr height="18px">
		<td align="left" valign="top"><vgcutil:message key="jsp.editarindicador.ficha.url" /></td>
	</tr>
	<tr>
		<td align="left" valign="top"><html:textarea styleClass="cuadroTexto" cols="105" rows="5" property="url" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"></html:textarea></td>
	</tr>
</table>
