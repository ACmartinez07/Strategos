<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.visiongc.framework.model.Error"%>
<%@page import="com.visiongc.framework.model.Usuario"%>
<%@page import="java.util.Date"%>
<%@page import="com.visiongc.framework.FrameworkService"%>
<%@page import="com.visiongc.framework.impl.FrameworkServiceFactory;"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page isErrorPage="true"%>

<%-- Modificado por: Kerwin Arias (27/05/2012) --%>

<%
	try 
	{
		StringWriter s = new StringWriter();
		PrintWriter errMsg = new PrintWriter(s);
		exception.printStackTrace(errMsg);

		request.setAttribute("errorJsp.exception", exception);

		Error error = new Error();

		error.setErrTimestamp(new Date());

		error.setErrSource(exception.toString());
		error.setErrDescription(exception.toString());

		error.setErrStackTrace(s.toString());

		Throwable causa = exception.getCause();
		String causas = "";
		int nroCausas = 0;
		while ((causa != null) && (nroCausas < 6)) 
		{
			causas = causas + causa.getMessage() + "\n";
			nroCausas++;
			causa = causa.getCause();
		}

		if (!causas.equals("")) 
			error.setErrCause(causas);

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario != null) 
			error.setErrUserId(String.valueOf(usuario.getUId()));
		else
			error.setErrUserId(String.valueOf(1));

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();

		frameworkService.registrarError(error);

		frameworkService.close();

		// Instrucción agregada para evitar que los servidores web arrojen un error
		// interno de servidor, en vez de la página de error
		// NO BORRAR!!!!!
		response.setStatus(HttpServletResponse.SC_OK);

	} 
	catch (Throwable t) 
	{
	}
%>

<tiles:insert definition="doc.errorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">JSP&nbsp;-&nbsp;<vgcutil:message
			key="jsp.error.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="javascript">

		var errorJspDetallesOcultos = false;

		function actualizarDetallesErrorJsp() {
			if (errorJspDetallesOcultos) {
				var elemento = document.getElementById('errorJspDetalles');
		    	elemento.style.visibility='visible';
				errorJspDetallesOcultos = false;
			} else {
				var elemento = document.getElementById('errorJspDetalles');
		    	elemento.style.visibility='hidden';
				errorJspDetallesOcultos = true;
			}
		}

		function cerrarVentanaErrorJsp() {
			history.back();
		}

		</script>

		<div id="errorJspWindow"
			style="position:absolute; left:0px; top:0px; width:100%; height:100%; visibility:visible; z-index:55"
			align="center">

		<table width="100%" height="100%"
			background="<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />">
			<tr>
				<td align="center" valign="middle"><vgcinterfaz:contenedorForma
					width="70%" height="70%">

					<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.error.titulo" />
					</vgcinterfaz:contenedorFormaTitulo>
					<vgcinterfaz:contenedorFormaBotonRegresar>
				javascript:cerrarVentanaErrorJsp();
				</vgcinterfaz:contenedorFormaBotonRegresar>
					<table class="bordeFichaDatos" cellpadding="3" cellspacing="0"
						height="100%">

						<%-- Este es el cuerpo de la "Ficha de Datos" --%>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="center">
							<h2><img
								src="<html:rewrite page='/componentes/mensajes/error.gif'/>"
								border="0" width="20" height="20"> <vgcutil:message
								key="jsp.error.mensaje" /></h2>
							</td>
						</tr>
						<tr>
							<td>
							<hr width="100%">
							</td>
						</tr>
						<tr>
							<td><img
								src="<html:rewrite page='/componentes/formulario/buscar.gif'/>"
								border="0" width="10" height="10"> <vgcutil:message
								key="jsp.error.mensaje.intro1" /></td>
						</tr>
						<tr>
							<td><img
								src="<html:rewrite page='/componentes/formulario/buscar.gif'/>"
								border="0" width="10" height="10"> <vgcutil:message
								key="jsp.error.mensaje.intro2" /></td>
						</tr>
						<tr>
							<td>
							<hr width="100%">
							</td>
						</tr>
						<tr>
							<td align="center"><vgcutil:message
								key="jsp.error.etiqueta.error" /> <input type="text"
								class="cuadroTexto" size="100%"
								value="<%=exception.getMessage()%>" /></td>
						</tr>
						<tr>
							<td>
							<hr width="100%">
							</td>
						</tr>
						<tr>
							<td>
							<div id="errorJspDetalles" align="center">
							<table class="trazaPila" cellpadding="3" cellspacing="0">
								<tr>
									<td><vgcutil:message key="jsp.error.etiqueta.traza" /></td>
								</tr>
								<tr>
									<td><textarea class="cuadroTexto" cols="110" rows="10">
								<%
								exception.printStackTrace(new PrintWriter(out));
								%>
								</textarea></td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					<vgcinterfaz:contenedorFormaBarraInferior>
						<img
							src="<html:rewrite page='/componentes/formulario/acciones.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="#" onclick="actualizarDetallesErrorJsp()"
							class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.detalles" /></a>
					</vgcinterfaz:contenedorFormaBarraInferior>
				</vgcinterfaz:contenedorForma></td>
			</tr>
		</table>
		<script language="javascript">
			actualizarDetallesErrorJsp();
		</script></div>

	</tiles:put>

</tiles:insert>
