<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (25/09/2012) -->
<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
<bean:define toScope="page" id="logo1" value=""></bean:define>
<bean:define toScope="page" id="estiloLetras" value=""></bean:define>
<%
	com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
	if (estilos != null)
	{
		hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
		estiloSuperior = estilos.getEstiloSuperior();
		estiloLetras = estilos.getEstiloLetras();
		logo1 = estilos.getLogo1();
	}
	else
		hayEstilo = "false";
%>
<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
<logic:equal scope="page" name="hayEstilo" value="true">
	<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
	<bean:define toScope="page" id="logo1" value="<%=logo1%>"></bean:define>
	<bean:define toScope="page" id="estiloLetras" value="<%=estiloLetras%>"></bean:define>
</logic:equal>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0">
			<tr>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="logo1">
						<td><img src="<html:rewrite page='/componentes/logos/logo_pequeno.png'/>" border="0"></td>
					</logic:notEmpty>
					<logic:empty scope="page" name="logo1">
						<td><img src="<html:rewrite page='/componentes/barraAplicacion/logoAplicacion2.gif'/>" border="0" ></td>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<td><img src="<html:rewrite page='/componentes/barraAplicacion/logoAplicacion2.gif'/>" border="0" ></td>
				</logic:equal>
			</tr>
		</table>
		</td>				
		<td width="300px" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="estiloLetras">
						<td align="center">
							<logic:empty scope="session" name="soloAdministracion">
								<img src="<html:rewrite page='/componentes/barraAplicacion/inicio.gif'/>"  border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.inicio.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" style="<%=estiloLetras%>" onclick="inicio()"><vgcutil:message key="jsp.barraaplicacion.inicio" /></a>
							</logic:empty>
						</td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/manual.gif'/>" border="0" width="10" height="10" title="<vgcutil:message	key="jsp.barraaplicacion.manual.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" style="<%=estiloLetras%>" onclick="abrirManual()"><vgcutil:message key="jsp.manual.titulo" /></a></td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/acercade.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.acerca.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" style="<%=estiloLetras%>" onclick="acerca()"><vgcutil:message key="jsp.barraaplicacion.acerca" /></a></td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.salir.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" style="<%=estiloLetras%>" onclick="salir()"><vgcutil:message key="jsp.barraaplicacion.salir" /></a></td>
					</logic:notEmpty>
					<logic:empty scope="page" name="estiloLetras">
						<td align="center">
							<logic:empty scope="session" name="soloAdministracion">
								<img src="<html:rewrite page='/componentes/barraAplicacion/inicio.gif'/>"  border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.inicio.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" sytle="color:#ffffff"  onclick="inicio()"><vgcutil:message key="jsp.barraaplicacion.inicio" /></a>
							</logic:empty>
						</td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/manual.gif'/>" border="0" width="10" height="10" title="<vgcutil:message	key="jsp.barraaplicacion.manual.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="abrirManual()"><vgcutil:message key="jsp.manual.titulo" /></a></td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/acercade.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.acerca.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="acerca()"><vgcutil:message key="jsp.barraaplicacion.acerca" /></a></td>
						<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.salir.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="salir()"><vgcutil:message key="jsp.barraaplicacion.salir" /></a></td>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<td align="center">
						<logic:empty scope="session" name="soloAdministracion">
							<img src="<html:rewrite page='/componentes/barraAplicacion/inicio.gif'/>"  border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.inicio.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" sytle="color:#ffffff"  onclick="inicio()"><vgcutil:message key="jsp.barraaplicacion.inicio" /></a>
						</logic:empty>
					</td>
					<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/manual.gif'/>" border="0" width="10" height="10" title="<vgcutil:message	key="jsp.barraaplicacion.manual.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="abrirManual()"><vgcutil:message key="jsp.manual.titulo" /></a></td>
					<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/acercade.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.acerca.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="acerca()"><vgcutil:message key="jsp.barraaplicacion.acerca" /></a></td>
					<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.salir.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="salir()"><vgcutil:message key="jsp.barraaplicacion.salir" /></a></td>
				</logic:equal>
			</tr>
		</table>
		</td>
	</tr>
</table>
