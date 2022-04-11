<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarCausaForm" property="causaId" value="0">
			<vgcutil:message key="jsp.editarcausa.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarCausaForm" property="causaId" value="0">
			<vgcutil:message key="jsp.editarcausa.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function validar(forma) 
			{
				if (validateEditarCausaForm(forma)) 
				{
					window.document.editarCausaForm.action = '<html:rewrite action="/causas/guardarCausa"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{			   
				if (validar(document.editarCausaForm)) 
					window.document.editarCausaForm.submit();
			}

			function cancelar() 
			{
				window.document.editarCausaForm.action = '<html:rewrite action="/causas/cancelarGuardarCausa"/>';
				window.document.editarCausaForm.submit();
				this.close();
			}

			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarCausaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/causas/guardarCausa" focus="nombre" onsubmit="if (validar(document.editarCausaForm)) return true; else return false;">

			<html:hidden property="causaId" />
			<html:hidden property="padreId" />

			<vgcinterfaz:contenedorForma width="620px" height="250px">

				<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaTitulo>..::	
				
				    <%-- Para el caso de Nuevo --%>
					<logic:equal name="editarCausaForm" property="causaId" value="0">
						<vgcutil:message key="jsp.editarcausa.titulo.nuevo" />
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarCausaForm" property="causaId" value="0">
						<vgcutil:message key="jsp.editarcausa.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="10" align="center" height="100%">

					<%-- Este es el cuerpo de la "Ficha de Datos" --%>
					<tr>
						<%-- Nombre --%>
						<td align="right"><vgcutil:message key="jsp.editarcausa.ficha.nombre" /></td>
						<td><html:text property="nombre" size="61" disabled="false" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>

					<tr>
						<%-- Descripcion --%>
						<td align="right"><vgcutil:message key="jsp.editarcausa.ficha.descripcion" /></td>
						<td><html:textarea rows="7" cols="60" property="descripcion" styleClass="cuadroTexto" /></td>
					</tr>
				</table>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarCausaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarCausaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		
	</tiles:put>
</tiles:insert>