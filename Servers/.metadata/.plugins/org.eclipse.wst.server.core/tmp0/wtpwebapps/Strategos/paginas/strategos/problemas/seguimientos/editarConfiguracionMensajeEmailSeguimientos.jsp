<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="Javascript" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/seguimientosJs/editarConfiguracionMensajeEmailSeguimientosJs.jsp'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) {
				if (validateEditarConfiguracionMensajeEmailSeguimientosForm(forma)) {					
					window.document.editarConfiguracionMensajeEmailSeguimientosForm.action = '<html:rewrite action="/problemas/seguimientos/guardarConfiguracionMensajeEmailSeguimientos"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function guardar() {			
				if (validar(document.editarConfiguracionMensajeEmailSeguimientosForm)) {
					window.document.editarConfiguracionMensajeEmailSeguimientosForm.submit();
					window.close();
					return true;
				} else {
					return false;
				}				
			}

			function cancelar() {
				window.document.editarConfiguracionMensajeEmailSeguimientosForm.action = '<html:rewrite action="/problemas/seguimientos/cancelarGuardarConfiguracionMensajeEmailSeguimientos"/>';
				window.document.editarConfiguracionMensajeEmailSeguimientosForm.submit();				
				window.close();
			}
			
			function InsertarMacro(valorMacro) {
				insertAtCursorPosition(window.document.editarConfiguracionMensajeEmailSeguimientosForm.mensaje, valorMacro);
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarConfiguracionMensajeEmailSeguimientosForm.descripcion" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/seguimientos/guardarConfiguracionMensajeEmailSeguimientos" focus="descripcion" onsubmit="if (guardar()) return true; else return false;">

			<html:hidden property="mensajeId" />

			<vgcinterfaz:contenedorForma width="450px" height="315px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<tr>
						<td colspan="2">
						<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" height="100%">

							<%-- Título del agrupador --%>
							<tr>
								<td><b><bean:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.envio" /></b></td>
							</tr>

							<%-- Campo: Acuse de Recibo --%>
							<tr>

								<td><html:checkbox property="acuseRecibo" styleClass="botonSeleccionMultiple" /><vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.acuserecibo" /></td>
							</tr>

							<%-- Campo: Solo Auxiliar --%>
							<tr>
								<td><html:checkbox property="soloAuxiliar" styleClass="botonSeleccionMultiple" /><vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.soloauxiliar" /></td>
							</tr>

						</table>
						</td>
					</tr>

					<%-- Campo: Descripción --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.descripcion" /></td>
						<td><html:text property="descripcion" size="50" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>

					<%-- Campo: Mensaje --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.mensaje" /></td>
						<td><html:textarea property="mensaje" styleClass="cuadroTexto" cols="48" rows="5" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)" /></td>
					</tr>

					<%-- Campo: Macros --%>
					<tr>
						<td colspan="2"><input type="button" onclick="InsertarMacro('&ACCION')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.accion" />" name="" class="cuadroTexto"> <input type="button" onclick="InsertarMacro('&DESCRIPCION')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.descripcion" />" name="" class="cuadroTexto"> <input type="button" onclick="InsertarMacro('&RESPONSABLE')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.responsable" />" name="" class="cuadroTexto"> <input type="button" onclick="InsertarMacro('&AUXILIAR')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.auxiliar" />" name=""
							class="cuadroTexto"></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" onclick="InsertarMacro('&SUPERVISOR')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.supervisor" />" name="" class="cuadroTexto"> <input type="button" onclick="InsertarMacro('&REPORTE')" style="width: 100px" value="<vgcutil:message key="jsp.editarconfiguracionmensajeemailseguimientos.ficha.macro.nroreporte" />" name="" class="cuadroTexto"></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarConfiguracionMensajeEmailSeguimientosForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarConfiguracionMensajeEmailSeguimientosForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
