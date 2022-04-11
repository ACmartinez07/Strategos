<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (11/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarClaseIndicadoresForm" property="claseId" value="0">
			<vgcutil:message key="jsp.editarclaseindicadores.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarClaseIndicadoresForm" property="claseId" value="0">
			<vgcutil:message key="jsp.editarclaseindicadores.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarClaseIndicadoresForm" property="bloqueado">
				<bean:write name="editarClaseIndicadoresForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarClaseIndicadoresForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

            <%-- Esta funcion permite validar la Clase Indicador que se va a Guardar --%>
			function validar(forma) 
			{
				if (validateEditarClaseIndicadoresForm(forma)) 
				{
				  	window.document.editarClaseIndicadoresForm.action = '<html:rewrite action="/indicadores/clasesindicadores/guardarClaseIndicadores"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}
			
			<%-- Esta funcion permite Guardar una Clase Indicador --%>
			function guardar() 
			{			   
				if (validar(document.editarClaseIndicadoresForm)) 
					window.document.editarClaseIndicadoresForm.submit();
			}

			<%-- Esta funcion permite Cancelar la operacion que se eata realizando --%>
			function cancelar() 
			{
				window.document.editarClaseIndicadoresForm.action = '<html:rewrite action="/indicadores/clasesindicadores/cancelarClaseIndicadores"/>';
				window.document.editarClaseIndicadoresForm.submit();
				this.close();
			}

			<%-- Esta funcion Guarda la Clase Indicador que se esta creando si Pulso Enter --%>			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarClaseIndicadoresForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/clasesindicadores/guardarClaseIndicadores" focus="nombre" onsubmit="if (validar(document.editarClaseIndicadoresForm)) return true; else return false;">

			<html:hidden property="claseId" />
			<html:hidden property="padreId" />

			<vgcinterfaz:contenedorForma width="600px" height="320px">

				<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaTitulo>..::	
				
				    <%-- Para el caso de Nuevo --%>
					<logic:equal name="editarClaseIndicadoresForm" property="claseId" value="0">
						<vgcutil:message key="jsp.editarclaseindicadores.titulo.nuevo" />
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarClaseIndicadoresForm" property="claseId" value="0">
						<vgcutil:message key="jsp.editarclaseindicadores.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" style="height: 240px; text-align:center; border-spacing:0px; border-collapse: collapse; padding: 0px;">
					<%-- Este es el cuerpo de la "Ficha de Datos" --%>
					<tr>
						<%-- Nombre --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseindicadores.ficha.nombre" /></td>
						<td><html:text property="nombre" size="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="150" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
					<tr>
						<%-- Descripcion --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseindicadores.ficha.descripcion" /></td>
						<td><html:textarea rows="7" cols="50" property="descripcion" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
					</tr>
					<tr>
						<%-- Codigo Parcial de Enlace --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseindicadores.ficha.codigoparcialenlace" /></td>
						<td><html:text property="enlaceParcial" size="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
					<tr>
						<%-- Visible --%>
						<td align="right"><vgcutil:message key="jsp.editarclaseindicadores.ficha.visible" /></td>
						<td align="left"><html:checkbox styleClass="botonSeleccionMultiple" property="visible" /></td>
					</tr>
				</table>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarClaseIndicadoresForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarClaseIndicadoresForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>
	</tiles:put>
</tiles:insert>
