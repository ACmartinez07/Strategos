<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>


<%-- Creado por: Kerwin Arias (19/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesiniciativa.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var soloLecturaOriginal = false;

			function cerrarVentana() 
			{
				var soloLectura = window.document.editarIniciativaForm.bloqueado.checked;
				if (soloLecturaOriginal != soloLectura) 
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/iniciativas/guardarIniciativaSoloLectura" />?iniciativaId=' + window.document.editarIniciativaForm.iniciativaId.value + '&soloLectura=' + soloLectura , null, 'finalizarPropiedades()');
				else 
					this.close();
			}

			function finalizarPropiedades() 
			{
				this.close();
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/guardarIniciativaSoloLectura">

			<html:hidden property="iniciativaId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..::<vgcutil:message key="jsp.propiedadesiniciativa.titulo.ficha" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="345" width="390" nombre="general">
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesiniciativa.pestana.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<table cellspacing="0" class="panelContenedor">

							<!-- Campo Responsable Seguimiento -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/iniciativas.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarIniciativaForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Campo Responsable Seguimiento -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesiniciativa.ficha.responsableseguimiento" />:<b> <bean:write scope="request" name="editarIniciativaForm" property="responsableSeguimiento" /></b></td>
							</tr>

							<!-- Campo Responsable Fijar Meta-->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesiniciativa.ficha.responsablefijarmeta" />:<b> <bean:write scope="request" name="editarIniciativaForm" property="responsableFijarMeta" /></b></td>
							</tr>

							<!-- Campo Responsable Lograr Meta-->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesiniciativa.ficha.responsablelograrmeta" />:<b> <bean:write scope="request" name="editarIniciativaForm" property="responsableLograrMeta" /></b></td>
							</tr>

							<!-- Campo Responsable Lograr Meta-->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesiniciativa.ficha.responsablecargarmeta" />:<b> <bean:write scope="request" name="editarIniciativaForm" property="responsableCargarMeta" /></b></td>
							</tr>

							<!-- Campo Responsable Lograr Meta-->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesiniciativa.ficha.responsablecargarejecutado" />:<b> <bean:write scope="request" name="editarIniciativaForm" property="responsableCargarEjecutado" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<tr>
								<td><bean:define id="soloLecturaApagado" value="true"></bean:define><vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="INICIATIVA_READONLY">
									<bean:define id="soloLecturaApagado" value="false"></bean:define>
								</vgcutil:tienePermiso><html:checkbox property="bloqueado" disabled="<%= (new Boolean(soloLecturaApagado)).booleanValue() %>" styleClass="botonSeleccionMultiple" /><vgcutil:message key="jsp.propiedadesorganizacion.pestana.general.general.sololectura" /></td>
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

		<script type="text/javascript">
			soloLecturaOriginal = window.document.editarIniciativaForm.bloqueado.checked;
		</script>

	</tiles:put>
</tiles:insert>
