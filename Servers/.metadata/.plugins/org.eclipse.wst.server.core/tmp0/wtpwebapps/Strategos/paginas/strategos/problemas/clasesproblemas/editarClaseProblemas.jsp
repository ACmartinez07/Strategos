<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarClaseProblemasForm" property="claseId" value="0">
			<vgcutil:message key="jsp.editarclaseproblemas.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarClaseProblemasForm" property="claseId" value="0">
			<vgcutil:message key="jsp.editarclaseproblemas.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarClaseProblemasForm" property="bloqueado">
				<bean:write name="editarClaseProblemasForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarClaseProblemasForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) 
			{
				if (validateEditarClaseProblemasForm(forma)) 
				{
				  	window.document.editarClaseProblemasForm.action = '<html:rewrite action="/problemas/clasesproblemas/guardarClaseProblemas"/>' + '?ts=<%= (new java.util.Date()).getTime() %>&tipo=' + window.document.editarClaseProblemasForm.tipo.value;
					return true;
				} 
				else 
					return false;
			}			

			function guardar() 
			{
				if (validar(document.editarClaseProblemasForm)) 
					window.document.editarClaseProblemasForm.submit();
			}

			function cancelar() 
			{
				window.document.editarClaseProblemasForm.action = '<html:rewrite action="/problemas/clasesproblemas/cancelarClaseProblemas"/>?tipo=' + window.document.editarClaseProblemasForm.tipo.value;
				window.document.editarClaseProblemasForm.submit();
			}
			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
			function corregirLongitud(cuadroTexto, longitudTexto) 
			{
				var texto = cuadroTexto;				
				var longitud = texto.value.length;				
				if (longitud > longitudTexto) 
				{
					texto.value = texto.value.substring(0, longitudTexto);
					alert('<vgcutil:message key="jsp.longitud.maxima" />');
					texto.focus();
				}
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarClaseProblemasForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/clasesproblemas/guardarClaseProblemas" focus="nombre" onsubmit="if (validar(document.editarClaseProblemasForm)) return true; else return false;">

			<html:hidden property="claseId" />
			<html:hidden property="padreId" />
			<html:hidden property="tipo" />

			<vgcinterfaz:contenedorForma width="490px" height="180px">

				<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaTitulo>..::	
				
				    <%-- Para el caso de Nuevo --%>
					<logic:equal name="editarClaseProblemasForm" property="claseId" value="0">
						<vgcutil:message key="jsp.editarclaseproblemas.titulo.nuevo" />
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarClaseProblemasForm" property="claseId" value="0">
						<vgcutil:message key="jsp.editarclaseproblemas.titulo.modificar" />
					</logic:notEqual>

				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<%-- Este es el cuerpo de la "Ficha de Datos" --%>
					<tr>
						<%-- Nombre --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseproblemas.ficha.nombre" /></td>
						<td><html:text property="nombre" size="60" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>

					<tr>
						<%-- Descripcion --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseproblemas.ficha.descripcion" /></td>
						<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="5" cols="59" property="descripcion" styleClass="cuadroTexto" onkeyup="corregirLongitud(descripcion, 2000);" /></td>
					</tr>

				</table>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarClaseProblemasForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarClaseProblemasForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>

</tiles:insert>