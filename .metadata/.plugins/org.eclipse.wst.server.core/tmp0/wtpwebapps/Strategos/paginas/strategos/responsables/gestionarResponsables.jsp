<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarresponsables.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">	
		
			function nuevo() {				
				abrirVentanaModal('<html:rewrite action="/responsables/crearResponsable"/>', "ResponsableAdd", 750, 530);
			}
	
			function modificar() {
				if ((document.gestionarResponsablesForm.seleccionados.value == null) || (document.gestionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var responsableId = document.gestionarResponsablesForm.seleccionados.value;			
				abrirVentanaModal('<html:rewrite action="/responsables/modificarResponsable"/>?responsableId=' + responsableId, "ResponsableEdit", 750, 530);
			}
	        
	        function asociar() {
	        	if ((document.gestionarResponsablesForm.seleccionados.value == null) || (document.gestionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var responsableId = document.gestionarResponsablesForm.seleccionados.value;				           
				window.location.href='<html:rewrite action="/responsables/asociarResponsables" />?responsableId=' + responsableId;
			}
	               
			function eliminar() {
				if ((document.gestionarResponsablesForm.seleccionados.value == null) || (document.gestionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var responsableId = document.gestionarResponsablesForm.seleccionados.value;
				respuesta = confirm ('<vgcutil:message key="jsp.gestionarresponsables.eliminarresponsable.confirmar" />');		
				if (respuesta){
					window.location.href='<html:rewrite action="/responsables/eliminarResponsable"/>?responsableId=' + responsableId + '&ts=<%= (new Date()).getTime() %>';
				}
			}
			
			function propiedades() {
				if ((document.gestionarResponsablesForm.seleccionados.value == null) || (document.gestionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var responsableId = document.gestionarResponsablesForm.seleccionados.value;
		 		abrirVentanaModal('<html:rewrite action="/responsables/propiedadesResponsable" />?responsableId='+responsableId , "responsable", 450, 455);
			}			
			
			function reporteAsociacionesUno(responsableId){
				if ((document.gestionarResponsablesForm.seleccionados.value == null) || (document.gestionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var responsableId = document.gestionarResponsablesForm.seleccionados.value;			
				abrirReporte('<html:rewrite action="/responsables/generarReporteResponsables.action"/>?atributoOrden=' + gestionarResponsablesForm.atributoOrden.value + '&tipoOrden=' + gestionarResponsablesForm.tipoOrden.value + '&mostrarAsociaciones=true&responsableId=' + responsableId, 'reporte');	
			}
			
			function reporteAsociacionesVarios() {
				abrirReporte('<html:rewrite action="/responsables/generarReporteResponsables.action"/>?atributoOrden=' + gestionarResponsablesForm.atributoOrden.value + '&tipoOrden=' + gestionarResponsablesForm.tipoOrden.value + '&mostrarAsociaciones=true', 'reporte');		
			}
			
			function reporteListadoSimple() {
				abrirReporte('<html:rewrite action="/responsables/generarReporteResponsables.action"/>?atributoOrden=' + gestionarResponsablesForm.atributoOrden.value + '&tipoOrden=' + gestionarResponsablesForm.tipoOrden.value, 'reporte');		
			}						
			
			function limpiarFiltros() {
				gestionarResponsablesForm.filtroCargo.value = "";
				gestionarResponsablesForm.filtroNombre.value = "";
			    gestionarResponsablesForm.submit();
			}
			
			function buscar() {
				gestionarResponsablesForm.submit();
			}        

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/responsables/gestionarResponsables" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-responsables">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.gestionarresponsables.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/responsables/gestionarResponsables' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>

					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
								<vgcinterfaz:menuAnidado key="menu.archivo.presentacionpreliminar" agregarSeparador="true">
									<vgcinterfaz:botonMenu key="jsp.gestionarresponsables.menu.archivo.reporte.listadosimple" onclick="reporteListadoSimple();" permisoId="RESPONSABLE_PRINT" />
									<vgcinterfaz:botonMenu key="jsp.gestionarresponsables.menu.archivo.reporte.asociacionesuno" onclick="reporteAsociacionesUno();" permisoId="RESPONSABLE_PRINT" />
									<vgcinterfaz:botonMenu key="jsp.gestionarresponsables.menu.archivo.reporte.asociacionesvarios" onclick="reporteAsociacionesVarios();" permisoId="RESPONSABLE_PRINT" />
								</vgcinterfaz:menuAnidado>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="RESPONSABLE_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="RESPONSABLE_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="RESPONSABLE_DELETE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades();" permisoId="RESPONSABLE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.edicion.asociar" onclick="asociar();" permisoId="RESPONSABLE_EDIT" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

					</vgcinterfaz:contenedorMenuHorizontal>

				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarresponsables.columna.cargo" /></td>
							<td width="10px"><html:text property="filtroCargo" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarresponsables.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarResponsables">

						<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.eliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedades();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.propiedades" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />

						<vgcinterfaz:barraHerramientasBoton nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="asociar" onclick="javascript:asociar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.asociar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="asociacionUno" pathImagenes="/paginas/strategos/responsables/imagenes/" nombre="asociacionUno" onclick="javascript:reporteAsociacionesUno();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarresponsables.menu.archivo.reporte.asociacionesuno" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="asociacionVarios" pathImagenes="/paginas/strategos/responsables/imagenes/" nombre="asociacionVarios" onclick="javascript:reporteAsociacionesVarios();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarresponsables.menu.archivo.reporte.asociacionesvarios" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteListadoSimple();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarresponsables.menu.archivo.reporte.listadosimple" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaResponsables" width="100%" messageKeyNoElementos="jsp.gestionarresponsables.noregistros" nombre="visorResponsables" seleccionSimple="true" nombreForma="gestionarResponsablesForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

					<vgcinterfaz:columnaVisorLista nombre="cargo" width="30%" onclick="javascript:consultar(document.gestionarResponsablesForm, 'cargo', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.cargo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="30%" onclick="javascript:consultar(document.gestionarResponsablesForm, 'nombre', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="email" width="15%" onclick="javascript:consultar(document.gestionarResponsablesForm, 'email', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.email" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="organizacion" width="12%" onclick="javascript:consultar(document.gestionarResponsablesForm, 'organizacionId', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.organizacion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="tipo" width="5%" onclick="javascript:consultar(document.gestionarResponsablesForm, 'tipo', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.tipo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="grupo" width="8%">
						<vgcutil:message key="jsp.gestionarresponsables.columna.grupo" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="responsable">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="responsable" property="responsableId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="cargo">
							<bean:write name="responsable" property="cargo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="responsable" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="email">
							<bean:write name="responsable" property="email" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacion">
							<logic:notEmpty name="responsable" property="organizacion">
								<bean:write name="responsable" property="organizacion.nombre" />
							</logic:notEmpty>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo">
							<%-- Si --%>
							<logic:equal name="responsable" property="tipo" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="responsable" property="tipo" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="grupo">
							<logic:notEmpty name="responsable" property="responsables">
								<vgcutil:message key="comunes.si" />
							</logic:notEmpty>
							<logic:empty name="responsable" property="responsables">
								<vgcutil:message key="comunes.no" />
							</logic:empty>
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaResponsables" labelPage="inPagina" action="javascript:consultar(gestionarResponsablesForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEmpty name="gestionarResponsablesForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaResponsables" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-responsables'), 280);	

			var visor = document.getElementById('visorResponsables');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>
</tiles:insert>
