<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>


<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (30/07/2012) --%>
<%--  Modificado por: Andres Caucali (28/08/2018)--%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.framework.gestionarusuarios.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery-1.7.1.min.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/tableExport.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/jquery.base64.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/png/html2canvas.js'/>"></script>
		<script type="text/javascript">
			var _selectCondicionTypeIndex = 1;
			
			function nuevo() 
			{
				abrirVentanaModal('<sslext:rewrite action="/framework/usuarios/crearUsuario"/>', "UsuarioAdd", 780, 580);
			}
			
			function modificar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)) 
					abrirVentanaModal('<sslext:rewrite action="/framework/usuarios/modificarUsuario"/>?usuarioId='+ document.gestionarUsuariosForm.seleccionados.value, "UsuarioEdit", 780, 580);
			}
			
			function copiar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)) 
					window.location.href='<sslext:rewrite action="/framework/usuarios/copiarUsuario"/>?usuarioId=' + document.gestionarUsuariosForm.seleccionados.value;
			}
			
			function propiedades() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)) 
					abrirVentanaModal('<sslext:rewrite action="/framework/usuarios/propiedadesUsuario"/>?usuarioId=' + document.gestionarUsuariosForm.seleccionados.value, "Usuario", 450, 470);
			}
			
			<%-- Esta funcion permite Eliminar un Usuario y es llamada desde el Cuerpo del Visor Tipo Lista --%>
			function eliminar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)) 
				{
					var eliminar = confirm('<vgcutil:message key="jsp.framework.gestionarusuarios.eliminarusuarios.confirm" />');
					if (eliminar) 
						window.location.href='<sslext:rewrite action="/framework/usuarios/eliminarUsuario"/>?usuarioId=' + document.gestionarUsuariosForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
				}
			}
			
			function reporteUsuarioDetallado() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)) 
					abrirReporte('<html:rewrite action="/framework/usuarios/reporteUsuarioDetallado"/>?usuarioId=' + document.gestionarUsuariosForm.seleccionados.value, 'usuarioDetallado', '750', '550');
			}
			
			function reporteUsuarioDetalladoExcel() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarUsuariosForm.seleccionados)){
					window.location.href='<sslext:rewrite action="/framework/usuarios/reporteUsuarioDetalladoExcel"/>?usuarioId=' + document.gestionarUsuariosForm.seleccionados.value;
				} 
					
			}
			
			function reporteUsuariosResumidoExcel() 
			{					
					var selectCondicionType = document.getElementById('selectCondicionType');
					if (selectCondicionType != null && url != null)
						url = '&selectCondicionType=' + selectCondicionType.value;
					else
						url = '?selectCondicionType=' + selectCondicionType.value;
					window.location.href='<sslext:rewrite action="/framework/usuarios/reporteUsuariosResumidoExcel"/>?selectType='+ selectCondicionType.value;
			 		
			}
			
			function reporteUsuariosResumido() 
			{
				abrirReporte('<html:rewrite action="/framework/usuarios/reporteUsuariosResumido"/>', 'usuariosResumido', '750', '550');
			}
			
			function configurarVisorUsuarios() 
			{
				configurarVisorLista('com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase', 'visorUsuarios', '<vgcutil:message key="jsp.framework.gestionarusuarios.titulo" />');
			}
			
			function buscar() 
			{
				var url = null;
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					url = '?filtroNombre=' + filtroNombre.value;
				var selectCondicionType = document.getElementById('selectCondicionType');
				if (selectCondicionType != null && url != null)
					url = url + '&selectCondicionType=' + selectCondicionType.value;
				else
					url = '?selectCondicionType=' + selectCondicionType.value;

				window.document.gestionarUsuariosForm.action = '<html:rewrite action="/framework/usuarios/gestionarUsuarios"/>' + url;				
				window.document.gestionarUsuariosForm.submit();
			}
			
			function limpiarFiltros() 
			{
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					filtroNombre.value = "";
				var selectCondicionType = document.getElementById('selectCondicionType');
				if (selectCondicionType != null)
					selectCondicionType.selectedIndex = _selectCondicionTypeIndex;
			}
			
			function exportarToXls()
			{
								
				$(document.getElementById('visorUsuarios')).tableExport({type:'excel',escape:'false'});
			}
			
			function activarUsuarios() 
			{
				
				if (verificarSeleccionMultiple(document.gestionarUsuariosForm.seleccionados))
				{
					var arrObjetosId = getObjetos(document.gestionarUsuariosForm.seleccionados);
					window.location.href='<sslext:rewrite action="/framework/usuarios/activarUsuarios"/>?usuarios='+ arrObjetosId;
				}
			}
			
			function reporteOrganizacion() 
			{
				abrirVentanaModal('<html:rewrite action="/framework/usuarios/reporteUsuariosOrganizacion"/>', 'reporteUsuariosOrganizacion', 600, 370);
			}
			
			function reporteGrupo() 
			{
				abrirVentanaModal('<html:rewrite action="/framework/usuarios/reporteGrupo"/>', 'reporteUsuariosGrupo', 600, 380);
			}

		</script>

		<jsp:include flush="true" page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>

		<html:form action="/framework/usuarios/gestionarUsuarios" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />			

			<vgcinterfaz:contenedorForma idContenedor="body-usuarios">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.gestionarusuarios.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/framework/usuarios/gestionarUsuarios' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem >
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo" >
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="USUARIO_PRINT_SET"/>
								<vgcinterfaz:menuAnidado key="menu.archivo.presentacionpreliminar">
									<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar.resumida" onclick="reporteUsuariosResumido();" permisoId="USUARIO_PRINT"/>
									<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar.detallada" onclick="reporteUsuarioDetallado();" permisoId="USUARIO_PRINT"/>
								</vgcinterfaz:menuAnidado>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicionUsuarios" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="USUARIO_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="USUARIO_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="USUARIO_DELETE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiar();" permisoId="USUARIO_COPY" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades();" permisoId="USUARIO_PROPERTIES" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<jsp:include page="/paginas/framework/menu/menuVer.jsp"></jsp:include>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientasUsuarios" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambiarclave" permisoId="USUARIO_CLAVE" onclick="cambiarClave();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" permisoId="USUARIO_SET_VIEW" onclick="configurarVisorUsuarios();" agregarSeparador="true"/>
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								 --%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Evaluacion --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEvaluacion" key="menu.evaluacion">
								<vgcinterfaz:botonMenu key="menu.evaluacion.organizacion" onclick="reporteOrganizacion();" permisoId="USUARIO_PRINT" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.grupo" onclick="reporteGrupo();"  permisoId="USUARIO_PRINT"/>
														
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

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
											<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="gestionarUsuariosForm" property="filtro.nombre" />">
										
										<!-- 
											<logic:notEmpty name="gestionarUsuariosForm" property="filtro.nombre">
												<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="gestionarUsuariosForm" property="filtro.nombre" />">
											</logic:notEmpty>
											<logic:empty name="gestionarUsuariosForm" property="filtro.nombre">
												<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="">									
											</logic:empty>
										-->
										</td>
									</tr>
						
									<%-- Condicion --%>
									<tr class="barraFiltrosForma" style="height: 20px;">
										<td style="width: 150px;"><vgcutil:message key="jsp.framework.gestionarusuarios.columna.estatus" /></td>
										<td style="width: 110px;">
											<bean:define id="filtroCondicionValue">
												<logic:notEmpty name="gestionarUsuariosForm" property="filtro.condicion">
													<bean:write name="gestionarUsuariosForm" property="filtro.condicion" />
												</logic:notEmpty>
												<logic:empty name="gestionarUsuariosForm" property="filtro.condicion">
													0
												</logic:empty>
											</bean:define>
											<select class="cuadroCombinado" name="selectCondicionType" id="selectCondicionType">
												<logic:iterate name="gestionarUsuariosForm" property="filtro.tiposCondiciones" id="filtroCondicionType">
													<bean:define id="tipo" toScope="page" name='filtroCondicionType' property='filtroCondicionTypeId' type="Byte"></bean:define>
													<bean:define id="nombre" toScope="page"><bean:write name='filtroCondicionType' property='nombre' /></bean:define>
													<logic:equal name='filtroCondicionType' property='filtroCondicionTypeId' value='<%=filtroCondicionValue%>'>
														<option value="<%=tipo%>" selected><%=nombre%></option>
													</logic:equal>
													<logic:notEqual name='filtroCondicionType' property='filtroCondicionTypeId' value='<%=filtroCondicionValue.toString()%>'>
														<option value="<%=tipo%>"><%=nombre%></option>
													</logic:notEqual>
												</logic:iterate>
											</select>
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
				
					<vgcinterfaz:barraHerramientas nombre="barraGestionarUsuarios">
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_ADD">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.nuevo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_EDIT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_DELETE">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.eliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_COPY">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="copiar" pathImagenes="/componentes/barraHerramientas/" nombre="copiar" onclick="javascript:copiar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.copiar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PROPERTIES">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedades();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.propiedades" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfResumido" onclick="javascript:reporteUsuariosResumido();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.resumida" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfDetallado" onclick="javascript:reporteUsuarioDetallado();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.detallada" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:reporteUsuarioDetalladoExcel();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.usuarios.reportes.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:reporteUsuariosResumidoExcel();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.usuarios.reporte.resumido.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_ACTIVAR">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="configuracion"
								pathImagenes="/componentes/barraHerramientas/" nombre="configuracion"
								onclick="javascript:activarUsuarios();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.activar.usuarios" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaUsuarios" nombre="visorUsuarios" seleccionMultiple="true" nombreForma="gestionarUsuariosForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="jsp.framework.gestionarusuarios.nousuarios" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="UId" width="200px" onclick="javascript:consultar(self.document.gestionarUsuariosForm, 'UId', null)">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.login" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="fullName" width="300px" onclick="javascript:consultar(self.document.gestionarUsuariosForm, 'fullName', null)">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.nombrecompleto" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="isConnected" width="100px">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.conectado" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="nroSesiones" width="100px">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.nrosesiones" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="isAdmin" width="100px" onclick="javascript:consultar(self.document.gestionarUsuariosForm, 'isAdmin', null)">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.isadmin" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px" onclick="javascript:consultar(self.document.gestionarUsuariosForm, 'estatus', null)">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.estatus" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="bloqueado" width="100px" onclick="javascript:consultar(self.document.gestionarUsuariosForm, 'bloqueado', null)">
						<vgcutil:message key="jsp.framework.gestionarusuarios.columna.bloqueado" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="usuario">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="usuario" property="usuarioId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="UId">
							<bean:write name="usuario" property="UId" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fullName">
							<bean:write name="usuario" property="fullName" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="isConnected" align="center">
							<vgclogica:tamanoColeccionMayorQue name="usuario" property="sesiones" value="0">
								<vgcutil:message key="comunes.si" />
							</vgclogica:tamanoColeccionMayorQue>
							<vgclogica:tamanoColeccionIgual name="usuario" property="sesiones" value="0">
								<vgcutil:message key="comunes.no" />
							</vgclogica:tamanoColeccionIgual>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nroSesiones" align="center">
							<bean:write name="usuario" property="nroSesiones" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="isAdmin" align="center">
							<logic:equal name="usuario" property="isAdmin" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<logic:equal name="usuario" property="isAdmin" value="false">
								<vgcutil:message key="comunes.no" />
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

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaUsuarios" labelPage="inPagina" action="javascript:consultar(gestionarUsuariosForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarUsuariosForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaUsuarios" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-usuarios'), 280);
			var visor = document.getElementById('visorUsuarios');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
