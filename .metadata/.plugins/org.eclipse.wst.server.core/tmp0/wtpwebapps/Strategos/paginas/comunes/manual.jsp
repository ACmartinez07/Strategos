<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>..:: <vgcutil:message key="jsp.manual.titulo" /></title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page='/componentes/estilos/estilos.css'/>">
		<META http-equiv=Content-Type content=text/html;CHARSET=iso-8859-1>
	</head>
	<body class="cuerpo">
		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				estiloSuperior = estilos.getEstiloSuperiorForma();
			}
			else
				hayEstilo = "false";
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
		</logic:equal>
	
		<!-- Este es el "Border del Contenedor Secundario o Forma" -->
		<table class="bordeContenedorForma" cellpadding="0" cellspacing="0" width="100%" >
			<tr valign="top">
				<td><!-- Este es el "Contenedor Secundario o Forma" -->
		
				<table class="contenedorForma" cellspacing="2" cellpadding="2" width="100%" height="100%">
		
					<!-- Barra Superior del "Contenedor Secundario o Forma" -->
					<tr>
						<logic:equal scope="page" name="hayEstilo" value="true">
							<logic:notEmpty scope="page" name="estiloSuperior">
								<td style="<%=estiloSuperior%>" colspan="2">..:: <vgcutil:message key="jsp.manual.titulo" /></td>
							</logic:notEmpty>
							<logic:empty scope="page" name="estiloSuperior">
								<td class="barraSuperiorForma" colspan="2">..:: <vgcutil:message key="jsp.manual.titulo" /></td>
							</logic:empty>
						</logic:equal>
						<logic:equal scope="page" name="hayEstilo" value="false">
							<td class="barraSuperiorForma" colspan="2">..:: <vgcutil:message key="jsp.manual.titulo" /></td>
						</logic:equal>
					</tr>
		
					<!-- Cuerpo del "Contenedor Secundario o Forma" -->
					<tr>
						<td colspan="2"><!-- Esta es la "Ficha de Datos" -->
							<table class="bordeFichaDatosIngreso" cellpadding="3" cellspacing="0" width="100%" height="100%">
			
								<!-- Este es el cuerpo de la "Ficha de Datos" -->
								<tr>
									<td align="center">
										<embed src="<html:rewrite page='/componentes/comunes/manual/index.html'/>" width="100%" height="100%" >
									</td>
								</tr>
			
							</table>
						</td>
					</tr>
		
					<!-- Barra Inferior del "Contenedor Secundario o Forma" -->
					<tr class="barraInferiorForma">
						<td colspan="2">
							<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" 
								border="0"
								width="10" height="10">
							<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();" class="mouseFueraBarraInferiorForma">
								<vgcutil:message key="boton.cerrar" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
		
				</table>
				</td>
			</tr>
		</table>
	</body>
</html>