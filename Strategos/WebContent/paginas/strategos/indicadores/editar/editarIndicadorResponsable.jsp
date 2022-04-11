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

<%-- Responsables --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

	<%-- Responsables Fijar Meta--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsablefijarmeta" /></td>
		<td>
			<html:text property="responsableFijarMeta" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;" />&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableFijarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.fijar.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableFijarMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>

	<%-- Responsables Lograr Meta--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsablelograrmeta" /></td>

		<td>
			<html:text property="responsableLograrMeta" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;"/>&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableLograrMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.lograr.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableLograrMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>

	<%-- Responsables Seguimiento--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsableseguimiento" /></td>
		<td>
			<html:text property="responsableSeguimiento" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;"/>&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableSeguimiento();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.seguimiento" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableSeguimiento();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>

	<%-- Responsables Lograr Meta--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsablecargarmeta" /></td>
		<td>
			<html:text property="responsableCargarMeta" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;"/>&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableCargarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.cargar.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableCargarMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>

	<%-- Responsables Lograr Meta--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsablecargarejecutado" /></td>
		<td>
			<html:text property="responsableCargarEjecutado" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;"/>&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableCargarEjecutado();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.cargar.ejecutado" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableCargarEjecutado();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>
	
	<%-- Responsables Notificacion--%>
	<tr>
		<td align="left"><vgcutil:message key="jsp.editarindicador.ficha.responsablenotificacion" /></td>
		<td>
			<html:text property="responsableNotificacion" onkeypress="ejecutarPorDefecto(event)" size="66" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" style="width: 440px;" />&nbsp;
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<img style="cursor: pointer" onclick="seleccionarResponsableNotificacion();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarindicador.ficha.seleccionarresponsable.notificacion" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableNotificacion();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
			</logic:notEqual>
		</td>
	</tr>

	<tr height="205px">
		<td colspan="2">&nbsp;</td>
	</tr>
</table>
