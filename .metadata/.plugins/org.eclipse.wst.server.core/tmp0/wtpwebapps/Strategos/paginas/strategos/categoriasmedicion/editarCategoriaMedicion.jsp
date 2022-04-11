<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (08/04/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarCategoriaMedicionForm" property="categoriaId" value="0">
			<vgcutil:message key="jsp.editarcategoriamedicion.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarCategoriaMedicionForm" property="categoriaId" value="0">
			<vgcutil:message key="jsp.editarcategoriamedicion.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			function cancelar() {				
				window.document.editarCategoriaMedicionForm.action = '<html:rewrite action="/categoriasmedicion/cancelarGuardarCategoriaMedicion"/>';
				window.document.editarCategoriaMedicionForm.submit();	
				this.close();
			}
		
			function guardar() {				
				if (validar(document.editarCategoriaMedicionForm)) {
					window.document.editarCategoriaMedicionForm.submit();					
				}	
			}

			function validar(forma) {
				if (validateEditarCategoriaMedicionForm(forma)) {
					window.document.editarCategoriaMedicionForm.action = '<html:rewrite action="/categoriasmedicion/guardarCategoriaMedicion"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarCategoriaMedicionForm.nombre" />
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/categoriasmedicion/guardarCategoriaMedicion" focus="nombre" onsubmit="if (validar(document.editarCategoriaMedicionForm)) return true; else return false;">

			<html:hidden property="categoriaId" />

			<vgcinterfaz:contenedorForma width="560px" height="150px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarCategoriaMedicionForm" property="categoriaId" value="0">
						<vgcutil:message key="jsp.editarcategoriamedicion.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarCategoriaMedicionForm" property="categoriaId" value="0">
						<vgcutil:message key="jsp.editarcategoriamedicion.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="20" align="center" height="100%">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarcategoriamedicion.ficha.nombre" /></td>
						<td><html:text property="nombre" size="60" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarCategoriaMedicionForm" property="bloqueado" value="true">
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
		<html:javascript formName="editarCategoriaMedicionForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
