<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesproblema.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			var soloLecturaOriginal = false;
			
			function cerrar() {
				var problemaId = '<bean:write scope="request" name="editarProblemaForm" property="problemaId" />';
				var soloLectura = window.document.editarProblemaForm.readOnly.checked;		
				window.location.href='<html:rewrite action="/problemas/guardarProblemaSoloLectura"/>?problemaId=' + problemaId + '&soloLectura=' + soloLectura + '&ts=<%= (new Date()).getTime() %>';
				this.close();
				
			}			
						
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/guardarProblemaSoloLectura" styleClass="formaHtml">

			<html:hidden property="problemaId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..::<vgcutil:message key="jsp.propiedadesproblema.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesProblema">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesproblema.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nombre -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/problemas.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarProblemaForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Estado -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesproblema.pestana.general.estado" /></b>: <bean:write scope="request" name="editarProblemaForm" property="nombreEstado" /></td>
							</tr>

							<!-- Fecha Estimada Inicio -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesproblema.pestana.general.fechaestimadainicio" /></b>: <bean:write scope="request" name="editarProblemaForm" property="fechaEstimadaInicio" /></td>
							</tr>

							<!-- Fecha Estimada Final -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesproblema.pestana.general.fechaestimadafin" /></b>: <bean:write scope="request" name="editarProblemaForm" property="fechaEstimadaFinal" /></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Solo lectura-->
							<tr>
								<td><html:checkbox property="readOnly" styleClass="botonSeleccionMultiple" /> <vgcutil:message key="jsp.propiedadesproblema.pestana.general.sololectura" /></td>
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

		<script language="Javascript1.1">
			soloLecturaOriginal = window.document.editarProblemaForm.readOnly.checked;
		</script>

		<html:javascript formName="editarProblemaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>

	</tiles:put>
</tiles:insert>
