<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesaccioncorrectiva.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			var soloLecturaOriginal = false;

			function cerrar() {
				var accionId = '<bean:write name="accionCorrectiva" property="accionId" scope="session" />';
				var soloLectura = window.document.editarAccionForm.readOnly.checked;				
				window.location.href='<html:rewrite action="/problemas/acciones/guardarAccionSoloLectura"/>?accionId=' + accionId + '&soloLectura=' + soloLectura + '&ts=<%= (new Date()).getTime() %>';
				this.close();	
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/acciones/propiedadesAccion" styleClass="formaHtml">

			<html:hidden property="accionId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesaccioncorrectiva.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesAccion">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table cellspacing="0" class="panelContenedor">

							<!-- Campo nombre -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/problemas.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarAccionForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Campo Fecha Estimada Inicio -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general.fechaestimadainicio" /></b>: <bean:write scope="request" name="editarAccionForm" property="fechaEstimadaInicio" /></td>
							</tr>

							<!-- Campo Fecha Estimada Finalizada -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general.fechaestimadafinalizacion" /></b>: <bean:write scope="request" name="editarAccionForm" property="fechaEstimadaFinal" /></td>
							</tr>

							<!-- Campo Fecha Real de Inicio -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general.fecharealinicio" />:</b> <bean:write scope="request" name="editarAccionForm" property="fechaRealInicio" /></td>
							</tr>

							<!-- Campo Responsable Real de Finalización  -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general.fecharealfinalizacion" />:</b> <bean:write scope="request" name="editarAccionForm" property="fechaRealFinal" /></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Solo lectura-->
							<tr>
								<td><html:checkbox property="readOnly" styleClass="botonSeleccionMultiple" /> &nbsp;<vgcutil:message key="jsp.propiedadesaccioncorrectiva.pestana.general.sololectura" /></td>
							</tr>

						</table>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
