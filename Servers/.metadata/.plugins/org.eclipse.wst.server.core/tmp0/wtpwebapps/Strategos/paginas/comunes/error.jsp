<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page isErrorPage="true"%>

<%-- Modificado por: Kerwin Arias (27/05/2012) --%>
<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
<bean:define toScope="page" id="estiloSuperior_left" value=""></bean:define>
<%
	com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
	if (estilos != null)
	{
		hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
		estiloSuperior = estilos.getEstiloSuperiorForma();
		estiloSuperior_left = estilos.getEstiloSuperiorFormaIzquierda();
	}
	else
		hayEstilo = "false";
%>
<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
<logic:equal scope="page" name="hayEstilo" value="true">
	<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
	<bean:define toScope="page" id="estiloSuperior_left" value="<%=estiloSuperior_left%>"></bean:define>
</logic:equal>
<tiles:insert definition="doc.errorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String"><vgcutil:message key="jsp.error.titulo" /></tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="javascript">
				
			var detallesOcultos = false;
			
			function actualizarDetalles() 
			{
				if (detallesOcultos) {
					var elemento = document.getElementById('detailedError');
			    	elemento.style.visibility='visible';
					detallesOcultos = false;
				} else {
					var elemento = document.getElementById('detailedError');
			    	elemento.style.visibility='hidden';
					detallesOcultos = true;
				}
			}

			function regresar() 
			{
				history.back();
			}

		</script>

		<div id="errorJspWindow" style="position:absolute; left:15%; top:15%; width:70%; height:70%; visibility:visible; background-color:blue; z-index:55">
		<%-- Este es el "Contenedor Secundario o Forma" --%>
		<table class="contenedorForma" cellspacing="2" cellpadding="2" width="100%" height="100%">

			<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
			<tr>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="estiloSuperior">
						<td style="<%=estiloSuperior%>">..:: <vgcutil:message key="jsp.error.titulo" /></td>
						<td style="<%=estiloSuperior_left%>" align="right" width="15%">
							<img
							src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.regresar.alt"/>"> <a
							onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
							onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar();"
							class="mouseFueraBarraSuperiorForma"><vgcutil:message
							key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</logic:notEmpty>
					<logic:empty scope="page" name="estiloSuperior">
						<td class="barraSuperiorForma">..:: <vgcutil:message key="jsp.error.titulo" /></td>
						<td class="barraSuperiorForma" align="right" width="15%">
							<img
							src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.regresar.alt"/>"> <a
							onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
							onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar();"
							class="mouseFueraBarraSuperiorForma"><vgcutil:message
							key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<td class="barraSuperiorForma">..:: <vgcutil:message key="jsp.error.titulo" /></td>
					<td class="barraSuperiorForma" align="right" width="15%">
						<img
						src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
						border="0" width="10" height="10"
						title="<vgcutil:message key="boton.regresar.alt"/>"> <a
						onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
						onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar();"
						class="mouseFueraBarraSuperiorForma"><vgcutil:message
						key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</logic:equal>
			</tr>

			<%-- Cuerpo del "Contenedor Secundario o Forma" --%>
			<tr>
				<td colspan="2"><%-- Este es la "Ficha de Datos" --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">

					<%-- Este es el cuerpo de la "Ficha de Datos" --%>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="center"><h2><img src="<html:rewrite page='/componentes/mensajes/error.gif'/>" border="0" width="20" height="20"> <vgcutil:message key="jsp.error.mensaje" /></h2></td>												
					</tr>
					<tr>
						<td><hr width="100%"></td>
					</tr>
					<tr>
						<td><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" width="10" height="10"> <vgcutil:message key="jsp.error.mensaje.intro1" /></td>						
					</tr>					
					<tr>
						<td><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" width="10" height="10"> <vgcutil:message key="jsp.error.mensaje.intro2" /></td>						
					</tr>
					<tr>
						<td><hr width="100%"></td>
					</tr>
					<logic:notEmpty name="errorForm" scope="request" property="message">
						<logic:notEqual name="errorForm" scope="request" property="message" value="">					
							<tr>
								<td align="center"><vgcutil:message key="jsp.error.etiqueta.error" /> <input type="text" class="cuadroTexto" size="100%" value="<bean:write name="errorForm" property="message" scope="request" />" /></td>						
							</tr>					
						</logic:notEqual>
					</logic:notEmpty>
					<tr>
						<td><hr width="100%"></td>
					</tr>					
					<tr>
						<td>
							<div id="detailedError" style="z-index:10" align="center">
							<table>
								<logic:notEmpty name="errorForm" scope="request" property="cause">
									<logic:notEqual name="errorForm" scope="request" property="cause" value="">
										<tr>
											<td><vgcutil:message key="jsp.error.etiqueta.causa" /></td>						
										</tr>								
										<tr>
											<td><textarea class="cuadroTexto" cols="110" rows="10"><bean:write name="errorForm" property="cause" scope="request" /></textarea></td>						
										</tr>
									</logic:notEqual>
								</logic:notEmpty>
								
								<logic:notEmpty name="errorForm" scope="request" property="stackTrace">
									<logic:notEqual name="errorForm" scope="request" property="stackTrace" value="">
										
										<tr>
											<td><vgcutil:message key="jsp.error.etiqueta.traza" /></td>						
										</tr>								
										<tr>
											<td><textarea class="cuadroTexto" cols="110" rows="10"><bean:write name="errorForm" property="stackTrace" scope="request" /></textarea></td>						
										</tr>
									</logic:notEqual>
								</logic:notEmpty>
							</table>
							</div>
						</td>
					</tr>

				</table>
				</td>
			</tr>			
			
			<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
			<tr class="barraInferiorForma">
				<td colspan="2">
											
					<%-- Para el caso de Nuevo, Modificar y Propiedades --%>
					<img src="<html:rewrite page='/componentes/formulario/acciones.gif'/>" border="0" width="10" height="10"> <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="#" onclick="actualizarDetalles()" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.detalles" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					
				</td>
			</tr>

		</table>							

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="javascript">
		
			actualizarDetalles();
		
		</script>
		
		</div>

	</tiles:put>

</tiles:insert>
