<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">
		..:: <bean:message key="jsp.framework.gestionargrupos.titulo" />
	</tiles:put>

	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp" />

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			function nuevo() 
			{				
				//abrirVentanaModal('<sslext:rewrite action="/framework/grupos/crearGrupo"/>', "GrupoAdd", 750, 570);
				window.location.href="<sslext:rewrite action='/framework/grupos/crearGrupo'/>";
			}
			
			function modificar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarGruposForm.seleccionados)) 					
					//abrirVentanaModal('<sslext:rewrite action="/framework/grupos/modificarGrupo"/>?grupoId=' + document.gestionarGruposForm.seleccionados.value, "GrupoEdit", 750, 570);
					window.location.href="<sslext:rewrite action='/framework/grupos/modificarGrupo'/>?grupoId=" + document.gestionarGruposForm.seleccionados.value;
			}
			
			function copiar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarGruposForm.seleccionados)) 
					window.location.href="<sslext:rewrite action='/framework/grupos/copiarGrupo'/>?grupoId=" + document.gestionarGruposForm.seleccionados.value;
			}
			
			function eliminar() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarGruposForm.seleccionados)) 
				{
					var eliminar = confirm('<vgcutil:message key="jsp.framework.gestionargrupos.eliminargrupos.confirm" />');
					if (eliminar) 
						window.location.href='<sslext:rewrite action="/framework/grupos/eliminarGrupo"/>?grupoId=' + document.gestionarGruposForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
				}
			}
			
			function propiedades() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarGruposForm.seleccionados)) 
					abrirVentanaModal('<sslext:rewrite action="/framework/grupos/propiedadesGrupo"/>?grupoId=' + document.gestionarGruposForm.seleccionados.value, "Grupo", 450, 470);
			}
			
			function reporte() 
			{
				reporteGrupos();
			}
			
			function reporteGrupos() 
			{
				abrirReporte('<html:rewrite action="/framework/grupos/reporteGrupos"/>', 'grupos', '750', '550');
			}
			
		</script>

		<html:form action="/framework/grupos/gestionarGrupos" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-grupos">
				<vgcinterfaz:contenedorFormaTitulo>..:: <bean:message key="jsp.framework.gestionargrupos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/framework/grupos/gestionarGrupos' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="GRUPO_PRINT_SET"/>
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" permisoId="GRUPO_PRINT"/>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicionGrupos" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="GRUPO_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="GRUPO_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="GRUPO_DELETE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiar();" permisoId="GRUPO_COPY" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades();" permisoId="GRUPO_PROPERTIES" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<jsp:include page="/paginas/framework/menu/menuVer.jsp"></jsp:include>
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
					<vgcinterfaz:barraHerramientas nombre="barraGestionarUsuarios">
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_ADD">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.nuevo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_EDIT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_DELETE">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.eliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_COPY">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="copiar" pathImagenes="/componentes/barraHerramientas/" nombre="copiar" onclick="javascript:copiar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.copiar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_PROPERTIES">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedades();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.propiedades" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="GRUPO_PRINT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaGrupos" width="100%" messageKeyNoElementos="jsp.framework.gestionargrupos.nogrupos" nombre="visorGrupos" seleccionSimple="true" nombreForma="gestionarGruposForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="grupo" width="500px" onclick="javascript:consultar(self.document.gestionarGruposForm,'grupo', null)">
						<vgcutil:message key="jsp.framework.gestionargrupos.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="creado" width="130px" onclick="javascript:consultar(self.document.gestionarGruposForm,'creado', null)">
						<vgcutil:message key="jsp.framework.gestionargrupos.columna.creado" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="modificado" width="130px" onclick="javascript:consultar(self.document.gestionarGruposForm,'modificado', null)">
						<vgcutil:message key="jsp.framework.gestionargrupos.columna.modificado" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="grupo">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="grupo" property="grupoId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="grupo">
							<bean:write name="grupo" property="grupo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="creado">
							<bean:write name="grupo" property="creado" formatKey="formato.timestamp" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="modificado">
							<bean:write name="grupo" property="modificado" formatKey="formato.timestamp" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaGrupos" labelPage="inPagina" action="javascript:consultar(gestionarGruposForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarGruposForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaGrupos" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-grupos'), 250);
			var visor = document.getElementById('visorGrupos');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
