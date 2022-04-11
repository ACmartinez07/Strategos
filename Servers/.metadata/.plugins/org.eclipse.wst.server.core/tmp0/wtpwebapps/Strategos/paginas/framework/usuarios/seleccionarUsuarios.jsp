<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.seleccionarusuarios.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">	

			function seleccionar() {
				this.opener.document.<bean:write name="seleccionarUsuariosForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarUsuariosForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarUsuariosForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarUsuariosForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarUsuariosForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarUsuariosForm.seleccionados.value;

				//Valida que la función no esté vacia
				<logic:notEmpty name="seleccionarUsuariosForm" property="funcionCierre" scope="request">
				this.opener.<bean:write name="seleccionarUsuariosForm" property="funcionCierre" scope="request" />
				</logic:notEmpty>

				this.close();
			}
			
			function buscar() 
			{
				var url = null;
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					url = '?filtroNombre=' + filtroNombre.value;
				
				url = url + '&desdeResponsable=' + 'true';
							
				window.document.seleccionarUsuariosForm = '<html:rewrite action="/framework/usuarios/seleccionarUsuarios" />' + url;				
				window.document.seleccionarUsuariosForm.submit();
			}
			
			function limpiarFiltros() 
			{
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					filtroNombre.value = "";
				
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/framework/usuarios/seleccionarUsuarios" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="mostrarAdministradores" />
			<html:hidden property="organizacionId" />
			<html:hidden property="funcionCierre" />
			<html:hidden property="desdeResponsable" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.framework.seleccionarusuarios.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarUsuariosForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>
				
				<%-- Barra de Herramientas --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
				
					<%-- Filtros --%>
					<table id="tblFiltro" class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px; width: 100%;">
						<tr class="barraFiltrosForma">
							<td style="width: 260px;">
								<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
									<%-- Nombre --%>
									<tr class="barraFiltrosForma">
										<td style="width: 150px;"><vgcutil:message key="jsp.framework.gestionarusuarios.columna.nombrecompleto" /></td>
										<td style="width: 110px;">
											<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="seleccionarUsuariosForm" property="filtroNombre" />">
									
										</td>
									</tr>
						
									
								</table>
							</td>
							<%-- Botones --%>
							<td style="width: 50px;">
								<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
									<tr class="barraFiltrosForma" style="height: 20px;">
										<td colspan="2" style="width: 30px;">
											<a class="boton" title="<vgcutil:message key="boton.buscar.alt" />" onclick="buscar()"><vgcutil:message key="boton.buscar.alt" /></a>
										</td>
									</tr>
									<tr class="barraFiltrosForma" style="height: 20px;">
										<td colspan="2" style="width: 30px;">
											<a class="boton" title="<vgcutil:message key="boton.limpiar.alt" />" onclick="limpiarFiltros()"><vgcutil:message key="boton.limpiar.alt" /></a>
										</td>
									</tr>
								</table>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
					
				</vgcinterfaz:contenedorFormaBarraGenerica>	

				<vgcinterfaz:visorLista namePaginaLista="paginaSeleccionarUsuarios" nombre="visorUsuarios" esSelector="true" nombreForma="seleccionarUsuariosForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" messageKeyNoElementos="jsp.framework.seleccionarusuarios.nousuarios" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

					<vgcinterfaz:columnaVisorLista align="center" width="220" onclick="javascript:consultar(seleccionarUsuariosForm, 'fullName', null);" nombre="fullName">
						<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.fullname" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista align="center" width="220" onclick="javascript:consultar(seleccionarUsuariosForm, 'UId', null);" nombre="UId">
						<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.uid" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultar(seleccionarUsuariosForm, 'isAdmin', null);" nombre="isAdmin">
						<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.isadmin" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px" onclick="javascript:consultar(seleccionarUsuariosForm, 'estatus', null)">
						<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.estatus" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista align="center" nombre="bloqueado" width="60px" onclick="javascript:consultar(seleccionarUsuariosForm, 'bloqueado', null)">
						<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.bloqueado" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:filasVisorLista nombreObjeto="usuario">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="usuario" property="usuarioId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fullName" esValorSelector="true">
							<bean:write name="usuario" property="fullName" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="UId">
							<bean:write name="usuario" property="UId" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="isAdmin">
							<logic:equal value="true" name="usuario" property="isAdmin">
								<vgcutil:message key="commons.yes" />
							</logic:equal>
							<logic:equal value="false" name="usuario" property="isAdmin">
								<vgcutil:message key="commons.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
							<logic:equal name="usuario" property="estatus" value="0">
								<vgcutil:message key="jsp.framework.editarusuario.label.estatus.activo" />
							</logic:equal>
							<logic:equal name="usuario" property="estatus" value="1">
								<vgcutil:message key="jsp.framework.editarusuario.label.estatus.inactivo" />
							</logic:equal>
							<logic:equal name="usuario" property="estatus" value="2">
								<vgcutil:message key="jsp.framework.editarusuario.label.estatus.deshabilitado" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="bloqueado" align="center">
							<logic:equal name="usuario" property="bloqueado" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<logic:equal name="usuario" property="bloqueado" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
