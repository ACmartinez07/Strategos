<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (17/07/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarUnidadMedidaForm" property="unidadId" value="0">
			<vgcutil:message key="jsp.editarunidadmedida.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarUnidadMedidaForm" property="unidadId" value="0">
			<vgcutil:message key="jsp.editarunidadmedida.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) {
				if (validateEditarUnidadMedidaForm(forma)) {
					window.document.editarUnidadMedidaForm.action = '<html:rewrite action="/unidadesmedida/guardarUnidadMedida"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function guardar() {			
				if (validar(document.editarUnidadMedidaForm)) {
					window.document.editarUnidadMedidaForm.submit();
				}
			}

			function cancelar() {			
				window.document.editarUnidadMedidaForm.action = '<html:rewrite action="/unidadesmedida/cancelarGuardarUnidadMedida"/>';
				window.document.editarUnidadMedidaForm.submit();
				this.close();
			}

			function ejecutarPorDefecto(e) {			
				if(e.keyCode==13) {
					guardar();
				}
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarUnidadMedidaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/unidadesmedida/guardarUnidadMedida" focus="nombre" onsubmit="if (validar(document.editarUnidadMedidaForm)) return true; else return false;">

			<html:hidden property="unidadId" />

			<vgcinterfaz:contenedorForma width="580px" height="165px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarUnidadMedidaForm" property="unidadId" value="0">
						<vgcutil:message key="jsp.editarunidadmedida.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarUnidadMedidaForm" property="unidadId" value="0">
						<vgcutil:message key="jsp.editarunidadmedida.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="6" align="center" height="100%">
					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarunidadmedida.ficha.nombre" /></td>
						<td><html:text property="nombre" size="56" maxlength="100" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarunidadmedida.ficha.tipo" /></td>
						<td><html:checkbox property="tipo" styleClass="botonSeleccionMultiple" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarUnidadMedidaForm" property="bloqueado" value="true">
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
		<html:javascript formName="editarUnidadMedidaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
 
	</tiles:put>
</tiles:insert>
