<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarResponsableForm" property="responsableId" value="0">
			<vgcutil:message key="jsp.editarresponsable.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:equal name="editarResponsableForm" property="responsableId" value="0">
			<vgcutil:message key="jsp.editarresponsable.titulo.modificar" />
		</logic:equal>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
						
			function validar(forma) {
				if (validateEditarResponsableForm(forma)) {					
					window.document.editarResponsableForm.action = '<html:rewrite action="/responsables/guardarResponsable"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function guardar() {
				if (validar(document.editarResponsableForm)) {
					window.document.editarResponsableForm.submit();
				}
			}

			function cancelar() {
				window.document.editarResponsableForm.action = '<html:rewrite action="/responsables/cancelarGuardarResponsable"/>';
				window.document.editarResponsableForm.submit();
				this.close();
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}

			function limpiarUsuario() 
			{				
				document.editarResponsableForm.usuarioId.value = "";
				document.editarResponsableForm.nombreUsuario.value = "";
			}

			function seleccionarUsuario() 
			{								
				abrirSelectorUsuariosResponsables('editarResponsableForm', 'nombreUsuario', 'usuarioId', document.editarResponsableForm.usuarioId.value, 'true', 'establecerNombre()');				
			}
			
			function establecerNombre() 
			{
				document.editarResponsableForm.nombre.value = document.editarResponsableForm.nombreUsuario.value; 
			}		

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarResponsableForm.responsableId" />

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarResponsableForm.cargo" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="responsables/guardarResponsable" focus="cargo" onsubmit="if (validar(document.editarResponsableForm)) return true; else return false;">

			<html:hidden property="responsableId" />
			<html:hidden property="usuarioId" />

			<vgcinterfaz:contenedorForma width="650px" height="420px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarResponsableForm" property="responsableId" value="0">
						<vgcutil:message key="jsp.editarresponsable.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarResponsableForm" property="responsableId" value="0">
						<vgcutil:message key="jsp.editarresponsable.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="8" align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.cargo" /></td>
						<td><html:text property="cargo" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.nombreusuario" /></td>
						<td>
							<html:text property="nombreUsuario" size="55" readonly="true" styleClass="cuadroTexto" onchange="establecerNombre()" />&nbsp;
							<img style="cursor: pointer" onclick="seleccionarUsuario()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.editarresponsable.ficha.seleccionarusuario" />">&nbsp;
							<img style="cursor: pointer" onclick="limpiarUsuario()" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
						</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.nombre" /></td>
						<td><html:text property="nombre" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.ubicacion" /></td>
						<td><html:text property="ubicacion" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="250" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.email" /></td>
						<td><html:text property="email" onkeypress="ejecutarPorDefecto(event)" size="60" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
						<hr width="100%">
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right"><html:checkbox property="tipo" onkeypress="ejecutarPorDefecto(event)" styleClass="botonSeleccionMultiple" /> <vgcutil:message
							key="jsp.editarresponsable.ficha.tipo" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
						<hr width="100%">
						</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarresponsable.ficha.notas" /></td>
						<td><html:textarea property="notas" onkeypress="ejecutarPorDefecto(event)" cols="58" rows="5" styleClass="cuadroTexto" /></td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarResponsableForm" property="bloqueado" value="true">
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
		<html:javascript formName="editarResponsableForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
