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
		<logic:equal name="editarTiposConvenioForm" property="tiposConvenioId" value="0">
			<vgcutil:message key="jsp.editarconvenio.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarTiposConvenioForm" property="tiposConvenioId" value="0">
			<vgcutil:message key="jsp.editarconvenio.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<script type="text/javascript">
		
			function cancelar() {				
				window.document.editarTiposConvenioForm.action = '<html:rewrite action="/instrumentos/cancelarGuardarConvenio"/>';
				window.document.editarTiposConvenioForm.submit();			
			}
		
			function guardar() {				
				if (validar(document.editarTiposConvenioForm)) {
					window.document.editarTiposConvenioForm.submit();					
				}	
			}

			function validar(forma) {
				if (validateEditarTiposConvenioForm(forma)) {
					window.document.editarTiposConvenioForm.action = '<html:rewrite action="/instrumentos/guardarConvenio"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
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
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarTiposConvenioForm.nombre" />
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/guardarConvenio" focus="nombre" onsubmit="if (validar(document.editarTiposConvenioForm)) return true; else return false;">

			<html:hidden property="tiposConvenioId" />

			<vgcinterfaz:contenedorForma width="500px" height="225px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarTiposConvenioForm" property="tiposConvenioId" value="0">
						<vgcutil:message key="jsp.editarconvenio.titulo.nuevo" />
					</logic:equal>
			
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarTiposConvenioForm" property="tiposConvenioId" value="0">
						<vgcutil:message key="jsp.editarconvenio.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">
				
					<tr>
						<td align="right"><vgcutil:message key="jsp.pagina.convenio.nombre" /></td>
						<td><html:text property="nombre" size="45" maxlength="150" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="right"><vgcutil:message key="jsp.gestionarvistasdatos.columna.descripcion" /></td>
						<td><html:textarea property="descripcion" cols="45" rows="7" styleClass="cuadroTexto" /></td>
					</tr>
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarTiposConvenioForm" property="bloqueado" value="true">
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
		

	</tiles:put>
	
	<html:javascript formName="editarTiposConvenioForm" dynamicJavascript="true" staticJavascript="false" />
	<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
	
</tiles:insert>
