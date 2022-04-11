<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarEstadoAccionesForm" property="estadoId" value="0">
			<vgcutil:message key="jsp.editarestadoacciones.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarEstadoAccionesForm" property="estadoId" value="0">
			<vgcutil:message key="jsp.editarestadoacciones.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function cancelar() {
				window.document.editarEstadoAccionesForm.action = '<html:rewrite action="/estadosacciones/cancelarGuardarEstadoAcciones"/>';
				window.document.editarEstadoAccionesForm.submit();
				this.close();
			}

			function guardar() {
				if (validar(document.editarEstadoAccionesForm)) {					
					window.document.editarEstadoAccionesForm.submit();
				}
			}

			function validar(forma) {
				if (validateEditarEstadoAccionesForm(forma)) {
					window.document.editarEstadoAccionesForm.action = '<html:rewrite action="/estadosacciones/guardarEstadoAcciones"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarEstadoAccionesForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/estadosacciones/guardarEstadoAcciones" focus="nombre" onsubmit="if (validar(document.editarEstadoAccionesForm)) return true; else return false;">

			<html:hidden property="estadoId" />

			<vgcinterfaz:contenedorForma width="420px" height="165px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarEstadoAccionesForm" property="estadoId" value="0">
						<vgcutil:message key="jsp.editarestadoacciones.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarEstadoAccionesForm" property="estadoId" value="0">
						<vgcutil:message key="jsp.editarestadoacciones.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarestadoacciones.ficha.nombre" /></td>
						<td><html:text property="nombre" size="40" maxlength="15" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarestadoacciones.ficha.aplicaseguimiento" /></td>
						<td><html:checkbox property="aplicaSeguimiento" styleClass="botonSeleccionMultiple" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarestadoacciones.ficha.indicafinalizacion" /></td>
						<td><html:checkbox property="indicaFinalizacion" styleClass="botonSeleccionMultiple" /></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarEstadoAccionesForm" property="bloqueado" value="true">
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
		<html:javascript formName="editarEstadoAccionesForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>