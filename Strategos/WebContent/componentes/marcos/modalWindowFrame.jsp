<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Creado por: Kerwin Arias (23/07/2012) -->

<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<%-- Título de la página --%>
		<title><vgcutil:message key="aplicacion.nombre" />&nbsp;-&nbsp;<tiles:insert attribute='title' /></title>
		
		<%-- Estilos de la aplicación --%>
		<tiles:insert attribute="styleCss" />
		
		<%-- Js comunes --%>
		<tiles:insert attribute="jsCommons" />
	</head>

	<body id="bodyCuerpo" class="cuerpo" onload="inicializarComunesEventoWindowOnLoad();" oncontextmenu="return false;" >
		<div id="fondo"></div>
		<%-- Barra de Progreso --%>
		<tiles:insert attribute="barraProgreso" />
		
		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="messages" />
		
		<!-- Este es el "Contenedor Principal" -->
		<table class="contenedorPrincipal" id="bodyTable">
			<!-- Este es el "Cuerpo del Contenedor Principal" -->
			<tr>
				<td>
					<table id="contenedorTable" class="bordeContenedorForma" style="border-collapse: collapse; border-spacing: 0; padding: 0px;">
						<tr align="center">
							<td><tiles:insert attribute='body' /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="errorJsp" />
		
		<%-- Funciones de Cierre que se ejecutan al final de la página Html --%>
		<tiles:insert attribute="funcionesCierre" />
	</body>
</html>
