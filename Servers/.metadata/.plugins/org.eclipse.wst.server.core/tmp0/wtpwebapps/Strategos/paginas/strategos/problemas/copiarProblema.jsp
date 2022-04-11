<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.copiarproblema.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externos de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) {				
   				if (validateEditarProblemaForm(forma)) {
					window.document.editarProblemaForm.action = '<html:rewrite action="/problemas/guardarCopiaProblema"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}			

			function guardar() {
				if (validar(document.editarProblemaForm)) {
					window.document.editarProblemaForm.submit();
				}
			}

			function cancelar() {
				window.document.editarProblemaForm.action = '<html:rewrite action="/problemas/cancelarGuardarProblema"/>';
				window.document.editarProblemaForm.submit();
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}
			
			function seleccionarFechaFormulacion() {
				mostrarCalendario('document.all.fecha' , document.editarProblemaForm.fecha.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarProblemaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/guardarCopiaProblema" focus="nombre" onsubmit="if (validar(document.editarProblemaForm)) return true; else return false;">

			<html:hidden property="claseId" />
			<html:hidden property="problemaId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="nombreClase" />
			<html:hidden property="fechaEstimadaInicio" />
			<html:hidden property="fechaEstimadaFinal" />

			<vgcinterfaz:contenedorForma width="530px" height="170px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.copiarproblema.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarproblema.ficha.fechaformulacion" /></td>
						<td><html:text property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" /> <img style="cursor: pointer" onclick="seleccionarFechaFormulacion();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />"></td>
					</tr>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.copiarproblema.ficha.clase" /></b></td>
						<td><input type="text" name="clase" size="60" class="cuadroTexto" readonly="true"></td>
					</tr>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.copiarproblema.ficha.nombre" /></b></td>
						<td><input type="text" name="problema" size="60" class="cuadroTexto" readonly="true"></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarproblema.ficha.nuevonombre" /></td>
						<td colspan="2"><html:text property="nombre" size="60" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarProblemaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">		
			window.document.editarProblemaForm.clase.value = '<bean:write scope="request" name="editarProblemaForm" property="nombreClase" />';
			window.document.editarProblemaForm.problema.value = '<bean:write scope="request" name="editarProblemaForm" property="nombre" />';
			window.document.editarProblemaForm.nombre.value = '<vgcutil:message key="jsp.copiarproblema.ficha.copiade" />' + " " + window.document.editarProblemaForm.nombre.value;
		</script>

	</tiles:put>
</tiles:insert>
