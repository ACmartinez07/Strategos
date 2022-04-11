<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (11/10/2012) -->
<html>
	<head>
	
		<%-- Título de la página --%>
		<title><vgcutil:message key="aplicacion.nombre" />&nbsp;-&nbsp;<tiles:insert attribute='title' /></title>
		
		<%-- Estilos de la aplicación --%>
		<tiles:insert attribute="styleCss" />
		
		<%-- Js comunes --%>
		<tiles:insert attribute="jsCommons" />
		
	</head>

	<body class="cuerpo" onload="inicializarComunesEventoWindowOnLoad();" oncontextmenu="return false;">
		<div id="fondo"></div>
		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
		<bean:define toScope="page" id="estiloInferior" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				estiloSuperior = estilos.getEstiloSuperior();
				estiloInferior = estilos.getEstiloInferior();
			}
			else
				hayEstilo = "false";
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
			<bean:define toScope="page" id="estiloInferior" value="<%=estiloInferior%>"></bean:define>
		</logic:equal>

		<%-- Barra de Progreso --%>
		<tiles:insert attribute="barraProgreso" />
		
		<%-- Mensajes de información de la aplicación --%>
		<tiles:insert attribute="messages" />
		
		<!-- Este es el "Contenedor Principal" -->
		<table class="contenedorPrincipal" id="bodyTable">
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.encabezado.mostrar" valor="true">
				<!-- Esta es la "Barra Superior del Contenedor Principal" -->
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="estiloSuperior">
						<tr>
							<td align="center" style="<%=estiloSuperior%>">
								<tiles:insert attribute="header" />
							</td>
						</tr>
					</logic:notEmpty>
					<logic:empty scope="page" name="estiloSuperior">
						<tr>
							<td align="center" class="barraSuperiorPrincipal">
								<tiles:insert attribute="header" />
							</td>
						</tr>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<tr>
						<td align="center" class="barraSuperiorPrincipal">
							<tiles:insert attribute="header" />
						</td>
					</tr>
				</logic:equal>
			</vgcutil:valorPropertyIgual>
		
			<!-- Este es el "Cuerpo del Contenedor Principal" -->
			<tr>
				<td>
				<table class="bordeContenedorForma" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="center" width="100%"><tiles:insert attribute='body' /></td>
					</tr>
				</table>
				</td>
			</tr>
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.piepagina.mostrar" valor="true">
				<!-- Esta es la "Barra Inferior del Contenedor Principal" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloInferior">
							<td style="<%=estiloInferior%>" colspan="2"><tiles:insert attribute="footer" /></td>		
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloInferior">
							<td class="barraInferiorPrincipal" colspan="2"><tiles:insert attribute="footer" /></td>		
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td class="barraInferiorPrincipal" colspan="2"><tiles:insert attribute="footer" /></td>		
					</logic:equal>
				</tr>
			</vgcutil:valorPropertyIgual>
		
		</table>
		
		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="errorJsp" />
		
		<%-- Funciones de Cierre que se ejecutan al final de la página Html --%>
		<tiles:insert attribute="funcionesCierre" />
	
	</body>

</html>
