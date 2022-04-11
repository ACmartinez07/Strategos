<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (19/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesorganizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			var soloLecturaOriginal = false;

			function cerrarVentana() {
				var soloLectura = window.document.editarOrganizacionForm.soloLectura.checked;
				if (soloLecturaOriginal != soloLectura) {
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/organizaciones/guardarOrganizacionSoloLectura" />?organizacionId=' + window.document.editarOrganizacionForm.organizacionId.value + '&soloLectura=' + soloLectura , null, 'finalizarPropiedades()');
				} else {
					this.close();
				}
			}

			function finalizarPropiedades() {
				this.close();
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/guardarOrganizacionSoloLectura" styleClass="formaHtml">

			<html:hidden property="organizacionId" />

			<vgcinterfaz:contenedorForma width="445px" height="480px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15" marginTop="5px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesorganizacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesOrganizacion">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesorganizacion.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nombre Indicador -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/organizacion.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="session" name="editarOrganizacionForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Insumo -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesorganizacion.pestana.general.observacion" /></b> &nbsp;<bean:write scope="session" name="editarOrganizacionForm" property="observaciones" /></td>
							</tr>
							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>
							<tr>
								<td><bean:define id="soloLecturaApagado" value="true"></bean:define><vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="ORGANIZACION_READONLY">
									<bean:define id="soloLecturaApagado" value="false"></bean:define>
								</vgcutil:tienePermiso><html:checkbox property="soloLectura" disabled="<%= (new Boolean(soloLecturaApagado)).booleanValue() %>" styleClass="botonSeleccionMultiple" /><vgcutil:message key="jsp.propiedadesorganizacion.pestana.general.general.sololectura" /></td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script language="Javascript1.1">
			soloLecturaOriginal = window.document.editarOrganizacionForm.soloLectura.checked;
		</script>

	</tiles:put>
</tiles:insert>
