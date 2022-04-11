<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.editarforo.titulo.nuevo" />

		<%-- Comentario --%>
		<logic:greaterThan name="editarForoForm" property="tipo" value="-1">
			<vgcutil:message key="jsp.editarforo.titulo.nuevo" />
		</logic:greaterThan>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) {
				if (validateEditarForoForm(forma)) {
					window.document.editarForoForm.action = '<html:rewrite action="/foros/guardarForo"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';					
					return true;
				} else {
					return false;
				}
			}

			function guardar() {			
				if (validar(document.editarForoForm)) {
					window.document.editarForoForm.submit();
				}
			}

			function cancelar() {			
				window.document.editarForoForm.action = '<html:rewrite action="/foros/cancelarGuardarForo"/>';
				window.document.editarForoForm.submit();
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarForoForm.asunto" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/foros/guardarForo" focus="asunto" onsubmit="if (validar(document.editarForoForm)) return true; else return false;">

			<html:hidden property="foroId" />
			<html:hidden property="padreId" />
			<html:hidden property="tipo" />
			<html:hidden property="objetoKey" />
			<html:hidden property="objetoId" />

			<vgcinterfaz:contenedorForma width="500px" height="280px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.editarforo.titulo.nuevo" />

					<%-- Comentario --%>
					<logic:greaterThan name="editarForoForm" property="tipo" value="-1">
						<vgcutil:message key="jsp.editarforo.titulo.nuevo" />
					</logic:greaterThan>

				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarforo.ficha.organizacion" /></b></td>
						<td><bean:write name="editarForoForm" property="nombreOrganizacion" /></td>
					</tr>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarforo.ficha.objeto" /></b></td>
						<td><bean:write name="editarForoForm" property="nombreTipoObjetoKey" /> (<bean:write name="editarForoForm" property="nombreObjetoKey" />)</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarforo.ficha.asunto" /></td>
						<td><html:text property="asunto" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="100" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarforo.ficha.email" /></td>
						<td><html:text property="email" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="100" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarforo.ficha.comentario" /></td>
						<td><html:textarea rows="8" cols="60" property="comentario" styleClass="cuadroTexto"></html:textarea></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarForoForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarForoForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>

	</tiles:put>
</tiles:insert>
