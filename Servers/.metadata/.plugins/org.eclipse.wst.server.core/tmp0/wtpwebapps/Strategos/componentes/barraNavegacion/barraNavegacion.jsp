<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (14/11/2012) -->
<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
<bean:define toScope="page" id="estiloLetras" value=""></bean:define>
<%
	com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
	if (estilos != null)
	{
		hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
		estiloSuperior = estilos.getEstiloSuperior();
		estiloLetras = estilos.getEstiloLetras();
	}
	else
		hayEstilo = "false";
%>
<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
<logic:equal scope="page" name="hayEstilo" value="true">
	<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
	<bean:define toScope="page" id="estiloLetras" value="<%=estiloLetras%>"></bean:define>
</logic:equal>

<logic:notEmpty name="navigationBar" scope="session">
	<logic:equal scope="page" name="hayEstilo" value="true">
		<logic:notEmpty scope="page" name="estiloLetras">
			<logic:iterate name="navigationBar" id="url" scope="session" property="barra">
				<a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="<bean:write name="url" property="url" />" style="<%=estiloLetras%>"><bean:write name="url" property="nombre" />&nbsp;&gt;&nbsp;</a>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty scope="page" name="estiloLetras">
			<logic:iterate name="navigationBar" id="url" scope="session" property="barra">
				<a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="<bean:write name="url" property="url" />" class="mouseFueraBarraNavegacionPrincipal"><bean:write name="url" property="nombre" />&nbsp;&gt;&nbsp;</a>
			</logic:iterate>
		</logic:empty>
	</logic:equal>
	<logic:equal scope="page" name="hayEstilo" value="false">
		<logic:iterate name="navigationBar" id="url" scope="session" property="barra">
			<a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="<bean:write name="url" property="url" />" class="mouseFueraBarraNavegacionPrincipal"><bean:write name="url" property="nombre" />&nbsp;&gt;&nbsp;</a>
		</logic:iterate>
	</logic:equal>
</logic:notEmpty>


