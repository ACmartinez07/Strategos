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
		<vgcutil:message key="jsp.propiedadesseguimiento.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			var soloLecturaOriginal = false;
			
			function cerrar() {
				var seguimientoId = '<bean:write scope="request" name="editarSeguimientoForm" property="seguimientoId" />';
				var soloLectura = window.document.editarSeguimientoForm.soloLectura.checked;		
				window.location.href='<html:rewrite action="/problemas/seguimientos/guardarSeguimientoSoloLectura"/>?seguimientoId=' + seguimientoId + '&soloLectura=' + soloLectura + '&ts=<%= (new Date()).getTime() %>';
				this.close();
				
			}			
						
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/seguimientos/guardarSeguimientoSoloLectura" styleClass="formaHtml">

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..::<vgcutil:message key="jsp.propiedadesseguimiento.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesSeguimiento">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nombre -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/accionesCorrectivas.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarSeguimientoForm" property="nombreAccion" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Responsable -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.responsable" /></b>: <bean:write scope="request" name="editarSeguimientoForm" property="nombreResponsable" /></td>
							</tr>

							<!-- Supervisa -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.supervisor" /></b>: <bean:write scope="request" name="editarSeguimientoForm" property="nombreSupervisor" /></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Respondido -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.respondido" />: <bean:write scope="request" name="editarSeguimientoForm" property="fechaRecepcion" /></td>
							</tr>

							<!-- Respondido por -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.respondidopor" />:<b> <bean:write scope="request" name="editarSeguimientoForm" property="preparadoPor" /> </b></td>
							</tr>

							<!-- Aprobado -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.aprobado" />: <bean:write scope="request" name="editarSeguimientoForm" property="fechaAprobacion" /></td>
							</tr>

							<!-- Aprobado por -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.aprobadopor" />:<b> <bean:write scope="request" name="editarSeguimientoForm" property="aprobadoPor" /> </b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Solo lectura -->
							<tr>
								<td><html:checkbox property="soloLectura" styleClass="botonSeleccionMultiple" /> <vgcutil:message key="jsp.propiedadesseguimiento.ficha.pestana.general.sololectura" /></td>
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
			soloLecturaOriginal = window.document.editarSeguimientoForm.soloLectura.checked;
		</script>

	</tiles:put>
</tiles:insert>