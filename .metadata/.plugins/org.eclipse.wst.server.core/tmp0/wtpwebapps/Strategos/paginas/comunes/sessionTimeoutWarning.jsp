<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (06/02/2012) --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>..:: <vgcutil:message key="jsp.sessiontimeoutwarning.titulo" /></title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/componentes/estilos/estilos.css'/>">
<META http-equiv=Content-Type content=text/html;CHARSET=iso-8859-1>
</head>
<body class="cuerpo">

	<bean:define id="time"><%=request.getParameter("time")%></bean:define>
	<bean:define id="mensaje" value="mensaje"></bean:define>
	<logic:empty name="time">
		<bean:define id="mensaje">
			<vgcutil:message key="jsp.sessiontimeoutwarning.expiredmessage" />
		</bean:define>
	</logic:empty>
	<logic:notEmpty name="time">
		<logic:equal name="time" value="0">
			<bean:define id="mensaje">
				<vgcutil:message key="jsp.sessiontimeoutwarning.expiredmessage" />
			</bean:define>
		</logic:equal>
		<logic:notEqual name="time" value="0">
			<bean:define id="mensaje">
				<vgcutil:message key="jsp.sessiontimeoutwarning.warningmessage" />
			</bean:define>
		</logic:notEqual>
	</logic:notEmpty>

	<%
		if ((time == null) || (time.equals("")) || (time.equals("0"))) 
			time = Integer.toString(request.getSession().getMaxInactiveInterval() / 60);
		mensaje = mensaje.replaceAll("numMinutos", time);
	%>

	<%-- Funciones JavaScript locales de la página Jsp --%>
	<script type="text/JavaScript">
		
		//v4.01
		function findObjetoHtml(nombre, documento) 
		{ 
			var p, i, x;
			if (!documento)
				documento = document;
			if ((p = nombre.indexOf("?")) > 0 && parent.frames.length) 
			{
				documento = parent.frames[nombre.substring(p + 1)].document;
				nombre = nombre.substring(0, p);
			}
			if (!(x = documento[nombre]) && documento.all)
				x = documento.all[nombre];
			for (i = 0; !x && i < documento.forms.length; i++)
				x = documento.forms[i][nombre];
			for (i = 0; !x && documento.layers && i < documento.layers.length; i++)
				x = findObjetoHtml(nombre, documento.layers[i].document);
			if (!x && documento.getElementById)
				x = documento.getElementById(nombre);
			return x;
		}
		
		//v6.0
		function changePropertyObjetoHtml(objName, theProp, theValue) 
		{
			var obj = findObjetoHtml(objName);
			if (obj && (theProp.indexOf("style.")==-1 || obj.style)) 
			{
				if (theValue == true || theValue == false)
					eval("obj." + theProp + "=" + theValue);
				else
					eval("obj." + theProp + "='" + theValue + "'");
			}
		}
	</script>

	<!-- Este es el "Border del Contenedor Secundario o Forma" -->
	<table class="bordeContenedorForma" cellpadding="0" cellspacing="0">
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
		<tr valign="top">
			<td>
			
			<!-- Este es el "Contenedor Secundario o Forma" -->
			<table class="contenedorForma" cellspacing="2" cellpadding="2" width="100%" height="100%">
	
				<!-- Barra Superior del "Contenedor Secundario o Forma" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloSuperior">
							<td style="<%=estiloSuperior%>" colspan="2">..:: <vgcutil:message key="jsp.sessiontimeoutwarning.titulo" /></td>
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloSuperior">
							<td class="barraSuperiorForma" colspan="2">..:: <vgcutil:message key="jsp.sessiontimeoutwarning.titulo" /></td>
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td class="barraSuperiorForma" colspan="2">..:: <vgcutil:message key="jsp.sessiontimeoutwarning.titulo" /></td>
					</logic:equal>
				</tr>
	
				<!-- Cuerpo del "Contenedor Secundario o Forma" -->
				<tr>
					<td colspan="2">
					
					<!-- Esta es la "Ficha de Datos" -->
					<table class="bordeFichaDatos" cellpadding="3" cellspacing="0"
						height="100%">
	
						<!-- Este es el cuerpo de la "Ficha de Datos" -->
						<tr>
							<td><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" width="10" height="10"> <%=mensaje%></td>
						</tr>
	
					</table>
	
					</td>
				</tr>
	
				<!-- Barra Inferior del "Contenedor Secundario o Forma" -->
				<tr class="barraInferiorForma">
					<td colspan="2"><img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0"
						width="10" height="10"> <a
						onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();"
						class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cerrar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	
			</table>
			</td>
		</tr>
	</table>

</body>
</html>
