<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (10/02/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesactividad.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planificacionseguimiento/actividades/guardarActividad" styleClass="formaHtml">

			<html:hidden property="actividadId" />

			<vgcinterfaz:contenedorForma width="450px" height="485px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15" marginTop="5px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesactividad.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesActividad">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesactividad.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nombre -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/administracion.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarActividadForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Estado -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesactividad.pestana.general.responsableseguimiento" /></b>: <bean:write scope="request" name="editarActividadForm" property="responsableSeguimiento" /></td>
							</tr>

							<!-- Fecha Estimada Inicio -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesactividad.pestana.general.responsablefijarmeta" /></b>: <bean:write scope="request" name="editarActividadForm" property="responsableFijarMeta" /></td>
							</tr>

							<!-- Fecha Estimada Final -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesactividad.pestana.general.responsablelograrmeta" /></b>: <bean:write scope="request" name="editarActividadForm" property="responsableLograrMeta" /></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:this.close();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
