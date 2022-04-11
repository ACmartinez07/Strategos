<%@page import="com.visiongc.framework.web.util.HtmlUtil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (29/10/2012) -->
<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
<bean:define toScope="page" id="estiloInferior" value=""></bean:define>
<%
	com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
	if (estilos != null)
	{
		hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
		estiloInferior = estilos.getEstiloInferior();
	}
	else
		hayEstilo = "false";
%>
<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
<logic:equal scope="page" name="hayEstilo" value="true">
	<bean:define toScope="page" id="estiloInferior" value="<%=estiloInferior%>"></bean:define>
</logic:equal>

<table class="bordeContenedorFormaComplemento" style="border: 0px;">
	<tr>
		<logic:notEmpty scope="session" name="usuario">
			<logic:notEmpty scope="session" name="alerta">
				<logic:equal scope="session" name="alerta" property="hayAlertas" value="true">
					<!-- alerta -->
					<td id="tdAlertas" style="text-align:right;">
						<img ondblclick="javascript:if(window.gestionarAlertas)gestionarAlertas();" src="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getLocalPort()%><%=request.getContextPath()%>/componentes/alertas/alert_animada.gif" border="0" width="16" height="16" alt="<vgcutil:message key="jsp.alertas.hayalertas" />">&nbsp;
					</td>
				</logic:equal>
				<logic:notEqual scope="session" name="alerta" property="hayAlertas" value="true">
					<td id="tdAlertas" style="text-align:right;">
						&nbsp;
					</td>
				</logic:notEqual>
			</logic:notEmpty>
			<logic:empty scope="session" name="alerta">
				<td id="tdAlertas" style="text-align:right;">
					&nbsp;
				</td>
			</logic:empty>
		</logic:notEmpty>
		<logic:empty scope="session" name="usuario">
			<td id="tdAlertas" style="text-align:right;">
				&nbsp;
			</td>
		</logic:empty>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<logic:notEmpty scope="page" name="estiloInferior">
				<!-- opcion 1 -->
				<td id="tdIdentificacion" style="<%=estiloInferior%>">
					<b><vgcutil:message key="jsp.barrastatus.usuario"/></b>:&nbsp;
					<logic:empty scope="session" name="usuario">
						<vgcutil:message key="jsp.barrastatus.noconectado"/>
					</logic:empty>
					<logic:notEmpty scope="session" name="usuario">
						<%=new com.visiongc.framework.web.util.HtmlUtil().addString(((com.visiongc.framework.model.Usuario)request.getSession().getAttribute("usuario")).getFullName(), 50, (byte) 1)%>
					</logic:notEmpty>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.ip"/></b>:&nbsp;<%=request.getRemoteAddr()%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.host"/></b>:&nbsp;<%=request.getRemoteHost()%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.fecha"/></b>:&nbsp;<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.hora"/></b>:&nbsp;<%=(new SimpleDateFormat("HH:mm")).format(new Date())%>		
				</td>
			</logic:notEmpty>
			<logic:empty scope="page" name="estiloInferior">
				<!-- opcion 2 -->
				<td id="tdIdentificacion" class="barraInferiorIdentificacion" style="text-align:right;">
					<b><vgcutil:message key="jsp.barrastatus.usuario"/></b>:&nbsp;
					<logic:empty scope="session" name="usuario">
						<vgcutil:message key="jsp.barrastatus.noconectado"/>
					</logic:empty>
					<logic:notEmpty scope="session" name="usuario">
						<%=new com.visiongc.framework.web.util.HtmlUtil().addString(((com.visiongc.framework.model.Usuario)request.getSession().getAttribute("usuario")).getFullName(), 50, (byte) 1)%>
					</logic:notEmpty>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.ip"/></b>:&nbsp;<%=request.getRemoteAddr()%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.host"/></b>:&nbsp;<%=request.getRemoteHost()%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.fecha"/></b>:&nbsp;<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>
					&nbsp;|&nbsp;
					<b><vgcutil:message key="jsp.barrastatus.hora"/></b>:&nbsp;<%=(new SimpleDateFormat("HH:mm")).format(new Date())%>		
				</td>
			</logic:empty>
		</logic:equal>
		<logic:equal scope="page" name="hayEstilo" value="false">
			<!-- opcion 3 -->
			<td id="tdIdentificacion" class="barraInferiorIdentificacion" style="text-align:right; width: 300px;">
				<b><vgcutil:message key="jsp.barrastatus.usuario"/></b>:&nbsp;
				<logic:empty scope="session" name="usuario">
					<vgcutil:message key="jsp.barrastatus.noconectado"/>
				</logic:empty>
				<logic:notEmpty scope="session" name="usuario">
					<%=new com.visiongc.framework.web.util.HtmlUtil().addString(((com.visiongc.framework.model.Usuario)request.getSession().getAttribute("usuario")).getFullName(), 50, (byte) 1)%>
				</logic:notEmpty>
				&nbsp;|&nbsp;
				<b><vgcutil:message key="jsp.barrastatus.ip"/></b>:&nbsp;<%=request.getRemoteAddr()%>
				&nbsp;|&nbsp;
				<b><vgcutil:message key="jsp.barrastatus.host"/></b>:&nbsp;<%=request.getRemoteHost()%>
				&nbsp;|&nbsp;
				<b><vgcutil:message key="jsp.barrastatus.fecha"/></b>:&nbsp;<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>
				&nbsp;|&nbsp;
				<b><vgcutil:message key="jsp.barrastatus.hora"/></b>:&nbsp;<%=(new SimpleDateFormat("HH:mm")).format(new Date())%>		
			</td>
		</logic:equal>
	</tr>
</table>
<script type="text/javascript">
	var tdIdentificacion = 900;
	var tdAlerta = (parseInt(_myWidth) - tdIdentificacion);
	var objeto = document.getElementById('tdIdentificacion');
	if (objeto != null)
		objeto.style.width = tdIdentificacion + "px";
	var objeto = document.getElementById('tdAlertas');
	if (objeto != null)
	{
		if (tdAlerta < 0)
			objeto.style.width = _myWidth + "px";
		else
			objeto.style.width = tdAlerta + "px";
	}
</script>
