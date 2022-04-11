<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.propiedadesgrupo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cerrarVentana() {
				this.close();
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/grupos/propiedadesGrupo" styleClass="formaHtml">

			<html:hidden property="grupoId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..:: <vgcutil:message key="jsp.framework.propiedadesgrupo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesGrupo">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<tr>
								<td><img src="<html:rewrite page='/paginas/framework/imagenes/grupos.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write name="editarGrupoForm" property="grupo" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general.totalpermisos" />:</b>&nbsp;<bean:write name="editarGrupoForm" property="totalPermisosAsociados" /></td>
							</tr>
							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Fecha Creado-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general.creado" />:&nbsp;<bean:write name="editarGrupoForm" property="creado" /></td>
							</tr>

							<!-- Creado por-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general.creadopor" />:<b>&nbsp;<bean:write name="editarGrupoForm" property="creadoUsuarioNombre" /> </b></td>
							</tr>

							<!-- Fecha modificado-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general.modificado" />:&nbsp;<bean:write name="editarGrupoForm" property="modificado" /></td>
							</tr>

							<!-- Modificado por-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesgrupo.pestana.general.modificadopor" />:&nbsp;<b><bean:write name="editarGrupoForm" property="modificadoUsuarioNombre" /> </b></td>
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

	</tiles:put>
</tiles:insert>
