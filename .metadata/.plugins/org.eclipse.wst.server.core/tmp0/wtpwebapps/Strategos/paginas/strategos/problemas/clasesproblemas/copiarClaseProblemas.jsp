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
		<vgcutil:message key="jsp.copiarclaseproblemas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			
			function validar(forma) 
			{
				if (validateEditarClaseProblemasForm(forma)) 
				{
				  	window.document.editarClaseProblemasForm.action = '<html:rewrite action="/problemas/clasesproblemas/guardarCopiaClaseProblemas"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
				{
					return false;
				}
			}
			
			function guardar() 
			{				 
				if (validar(document.editarClaseProblemasForm)) 
					window.document.editarClaseProblemasForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarClaseProblemasForm.action = '<html:rewrite action="/problemas/clasesproblemas/cancelarClaseProblemas"/>';
				window.document.editarClaseProblemasForm.submit();
			}
			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/clasesproblemas/guardarCopiaClaseProblemas" focus="nombre" onsubmit="if (validar(document.editarClaseProblemasForm)) return true; else return false;">

			<html:hidden property="claseId" />
			<html:hidden property="padreId" />
			<html:hidden property="nombrePadre" />
			<html:hidden property="descripcion" />
			<html:hidden property="tipo" />

			<vgcinterfaz:contenedorForma width="490px" height="190px">

				<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.copiarclaseproblemas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarclaseproblemas.ficha.clasefuente" /></td>
						<td><input type="text" name="fuente" size="60" class="cuadroTexto" readonly="true"></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarclaseproblemas.ficha.clasedestino" /></td>
						<td><input type="text" name="destino" size="60" class="cuadroTexto" readonly="true"></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarclaseproblemas.ficha.nuevonombre" /></td>
						<td><html:text property="nombre" size="60" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
					<tr>
						<td colspan="2">
						<hr width="100%">
						</td>
					</tr>
					<tr>
						<td colspan="2"><html:checkbox property="copiarProblemas" styleClass="botonSeleccionMultiple" /> <vgcutil:message key="jsp.copiarclaseproblemas.ficha.copiarproblema" /></td>
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

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">		
			window.document.editarClaseProblemasForm.fuente.value = '<bean:write scope="request" name="editarClaseProblemasForm" property="nombre" />';
			window.document.editarClaseProblemasForm.destino.value = '<bean:write scope="request" name="editarClaseProblemasForm" property="nombrePadre" />';
			window.document.editarClaseProblemasForm.nombre.value = '<vgcutil:message key="jsp.copiarclaseproblemas.ficha.copiade" />' + " " + window.document.editarClaseProblemasForm.nombre.value;			
		</script>

	</tiles:put>

</tiles:insert>
