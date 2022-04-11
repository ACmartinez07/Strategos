<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (11/05/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.propiedadesusuario.titulo" />
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
		<html:form action="/framework/usuarios/propiedadesUsuario" styleClass="formaHtml">

			<html:hidden property="usuarioId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..:: <vgcutil:message key="jsp.framework.propiedadesusuario.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesUsuario">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<tr>
								<td><img src="<html:rewrite page='/paginas/framework/imagenes/usuarios.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write name="editarUsuarioForm" property="fullName" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.login" />:</b>&nbsp;<bean:write name="editarUsuarioForm" property="UId" /></td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.esadmin" />:</b>&nbsp;<logic:equal name="editarUsuarioForm" property="isAdmin" value="true">
									<vgcutil:message key="comunes.si" />
								</logic:equal><logic:equal name="editarUsuarioForm" property="isAdmin" value="false">
									<vgcutil:message key="comunes.no" />
								</logic:equal></td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.estatus" />:</b>&nbsp;
								<logic:equal name="editarUsuarioForm" property="estatus" value="0">
									<vgcutil:message key="jsp.framework.editarusuario.label.estatus.activo" />
								</logic:equal>
								<logic:equal name="editarUsuarioForm" property="estatus" value="1">
									<vgcutil:message key="jsp.framework.editarusuario.label.estatus.inactivo" />
								</logic:equal>
								<logic:equal name="editarUsuarioForm" property="estatus" value="2">
									<vgcutil:message key="jsp.framework.editarusuario.label.estatus.deshabilitado" />
								</logic:equal>
								</td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.bloqueado" />:</b>&nbsp;
								<logic:equal name="editarUsuarioForm" property="bloqueado" value="true">
									<vgcutil:message key="comunes.si" />
								</logic:equal><logic:equal name="editarUsuarioForm" property="bloqueado" value="false">
									<vgcutil:message key="comunes.no" />
								</logic:equal></td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.grupos" />:</b>&nbsp;<bean:write name="editarUsuarioForm" property="totalGruposAsociados" /></td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.organizaciones" />:</b>&nbsp;<bean:write name="editarUsuarioForm" property="totalOrganizacionesAsociadas" /></td>
							</tr>
							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Fecha Creado-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.creado" />:&nbsp;<bean:write name="editarUsuarioForm" property="creado" /></td>
							</tr>

							<!-- Creado por-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.creadopor" />:<b>&nbsp;<bean:write name="editarUsuarioForm" property="creadoUsuarioNombre" /> </b></td>
							</tr>

							<!-- Fecha modificado-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.modificado" />:&nbsp;<bean:write name="editarUsuarioForm" property="modificado" /></td>
							</tr>

							<!-- Modificado por-->
							<tr>
								<td><vgcutil:message key="jsp.framework.propiedadesusuario.pestana.general.modificadopor" />:&nbsp;<b><bean:write name="editarUsuarioForm" property="modificadoUsuarioNombre" /> </b></td>
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
