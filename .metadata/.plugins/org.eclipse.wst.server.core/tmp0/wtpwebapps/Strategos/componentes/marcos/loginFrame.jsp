<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (11/10/2012) -->
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

		<%-- Título de la página --%>
		<title><vgcutil:message key="aplicacion.nombre" />&nbsp;-&nbsp;<tiles:insert attribute='title' /></title>
		
		<%-- Estilos de la aplicación --%>
		<tiles:insert attribute="styleCss" />
		
		<%-- Js comunes --%>
		<tiles:insert attribute="jsCommons" />
		<script type="text/javascript">
			$(window).on('unload', function(){
				logout();
			});
			
			function logout()
			{
				if(typeof cerrarAplicacion === 'function')
					cerrarAplicacion();
			}
		</script>
	</head>
	<body class="cuerpo" onload="inicializarComunesEventoWindowOnLoad();" oncontextmenu="return false;">
		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
		<bean:define toScope="page" id="estiloInferior" value=""></bean:define>
		<bean:define toScope="page" id="logo1" value=""></bean:define>
		<bean:define toScope="page" id="logo2" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				estiloSuperior = estilos.getEstiloSuperior();
				estiloInferior = estilos.getEstiloInferior();
				logo1 = estilos.getLogo1();
				logo2 = estilos.getLogo2();
			}
			else
				hayEstilo = "false";
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
			<bean:define toScope="page" id="estiloInferior" value="<%=estiloInferior%>"></bean:define>
			<bean:define toScope="page" id="logo1" value="<%=logo1%>"></bean:define>
			<bean:define toScope="page" id="logo2" value="<%=logo2%>"></bean:define>
		</logic:equal>
		<div id="fondo"></div>
		
		<%-- Mensajes de información de la aplicación --%>
		<tiles:insert attribute="messages" />
		
		<!-- Este es el "Contenedor Principal" -->
		<table class="contenedorPrincipal" id="bodyTable">
		
			<!-- Esta es la "Barra Superior del Contenedor Principal" -->
			<logic:equal scope="page" name="hayEstilo" value="true">
				<logic:notEmpty scope="page" name="estiloSuperior">
					<tr>
						<td colspan="3" style="<%=estiloSuperior%>">
							&nbsp;
						</td>
					</tr>
				</logic:notEmpty>
				<logic:empty scope="page" name="estiloSuperior">
					<tr>
						<td colspan="3" class="barraSuperiorPrincipalLogin">
							&nbsp;
						</td>
					</tr>
				</logic:empty>
			</logic:equal>

			<!-- Este es el "Cuerpo del Contenedor Principal" -->
			<tr>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="logo1">
						<td style="position:absolute; top:150px; left:50px;">
							<img src="<html:rewrite page='/componentes/logos/logo.gif'/>" border="0">
						</td>
					</logic:notEmpty>
				</logic:equal>
				<td align="center">
					<table class="bordeContenedorForma" cellpadding="0" cellspacing="0">
						<tr align="center">
							<td><tiles:insert attribute='body' /></td>
						</tr>
					</table>
				</td>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="logo2">
						<td id="logo" name="logo" style="position:absolute; top:150px;">
							<img src="<html:rewrite page='/componentes/logos/logo2.gif'/>" border="0">
						</td>
					</logic:notEmpty>
				</logic:equal>
			</tr>
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.piepagina.mostrar" valor="true">
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="estiloInferior">
						<tr>
							<td colspan="3" style="<%=estiloInferior%>">
								<tiles:insert attribute="footer" />
							</td>
						</tr>
					</logic:notEmpty>
					<logic:empty scope="page" name="estiloInferior">
						<tr>
							<td colspan="3" class="barraInferiorPrincipal">
								<tiles:insert attribute="footer" />
							</td>
						</tr>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<tr>
						<td colspan="3" class="barraInferiorPrincipal">
							<tiles:insert attribute="footer" />
						</td>
					</tr>
				</logic:equal>
			</vgcutil:valorPropertyIgual>

		</table>
		
		<%-- Barra de Progreso --%>
		<tiles:insert attribute="barraProgreso" />

		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="errorJsp" />

		<%-- Funciones de Cierre que se ejecutan al final de la página Html --%>
		<tiles:insert attribute="funcionesCierre" />

	</body>
	<script type="text/javascript">
		var logo = document.getElementById("logo");
		if (logo != null)
			logo.style.left = screen.width -200;
	</script>
</html>
