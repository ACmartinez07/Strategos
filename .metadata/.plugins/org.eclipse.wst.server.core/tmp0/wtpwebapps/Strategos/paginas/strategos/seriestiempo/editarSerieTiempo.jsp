<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%-- Modificado por: Kerwin Arias (25/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarSerieTiempoForm" property="serieId" value="0">
			<vgcutil:message key="jsp.editarserietiempo.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarSerieTiempoForm" property="serieId" value="0">
			<vgcutil:message key="jsp.editarserietiempo.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarSerieTiempoForm" property="fija">
				<bean:write name="editarSerieTiempoForm" property="fija" />
			</logic:notEmpty>
			<logic:empty name="editarSerieTiempoForm" property="fija">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function validar(forma) 
			{
				if (validateEditarSerieTiempoForm(forma)) 
				{
					window.document.editarSerieTiempoForm.action = '<html:rewrite action="/seriestiempo/guardarSerieTiempo"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{			
				if (validar(document.editarSerieTiempoForm)) 
					window.document.editarSerieTiempoForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarSerieTiempoForm.action = '<html:rewrite action="/seriestiempo/cancelarGuardarSerieTiempo"/>';
				window.document.editarSerieTiempoForm.submit();
			}

			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarSerieTiempoForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/seriestiempo/guardarSerieTiempo" focus="nombre">

			<html:hidden property="serieId" />

			<vgcinterfaz:contenedorForma width="450px" height="175px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarSerieTiempoForm" property="serieId" value="0">
						<vgcutil:message key="jsp.editarserietiempo.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarSerieTiempoForm" property="serieId" value="0">
						<vgcutil:message key="jsp.editarserietiempo.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos bordeFichaDatostabla" style="height: 100%;">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarserietiempo.ficha.nombre" /></td>
						<td><html:text property="nombre" onkeypress="ejecutarPorDefecto(event)" size="45" maxlength="50" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarserietiempo.ficha.identificador" /></td>
						<td><html:text property="identificador" onkeypress="ejecutarPorDefecto(event)" size="10" maxlength="5" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarserietiempo.ficha.fija" /></td>
						<td><html:checkbox property="fija" onkeypress="ejecutarPorDefecto(event)" styleClass="botonSeleccionMultiple" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarserietiempo.ficha.preseleccionada" /></td>
						<td><html:checkbox property="preseleccionada" onkeypress="ejecutarPorDefecto(event)" styleClass="botonSeleccionMultiple" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarSerieTiempoForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarSerieTiempoForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
