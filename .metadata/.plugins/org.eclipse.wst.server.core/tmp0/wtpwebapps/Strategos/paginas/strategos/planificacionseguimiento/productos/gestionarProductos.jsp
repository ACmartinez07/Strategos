<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (03/08/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript">

	function nuevoProducto() {
		window.location.href='<html:rewrite action="/planificacionseguimiento/productos/crearProducto" />';
	}
	
	function modificarProducto() {
		if ((document.gestionarProductosForm.seleccionados.value == null) || (document.gestionarProductosForm.seleccionados.value == "")) {
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var productoId = document.gestionarProductosForm.seleccionados.value;				
		window.location.href='<html:rewrite action="/planificacionseguimiento/productos/modificarProducto" />?productoId=' + productoId;
	}
	
	function eliminarProducto() {
		if ((document.gestionarProductosForm.seleccionados.value == null) || (document.gestionarProductosForm.seleccionados.value == "")) {
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var productoId = document.gestionarProductosForm.seleccionados.value;
		var eliminar = confirm('<vgcutil:message key="jsp.gestionarproductos.eliminarproducto.confirmar" />');
		if (eliminar) {
			window.location.href='<html:rewrite action="/planificacionseguimiento/productos/eliminarProducto"/>?productoId=' + productoId + '&ts=<%= (new java.util.Date()).getTime() %>';
		}
	}
	
	function limpiarFiltrosProductos() {
		gestionarProductosForm.filtroNombre.value = "";
		gestionarProductosForm.submit();
	}
	
	function buscarProductos() {
		gestionarProductosForm.submit();
	}
	
	function getActionSubmitProductos() {
		return '<html:rewrite action="/planificacionseguimiento/productos/gestionarProductos"/>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIniciativas" primerParametro="true" />;
	}

</script>

<bean:define id="alertaProductoEnEsperaComienzo" toScope="page">
	<bean:write name="gestionarProductosForm" property="alertaProductoEnEsperaComienzo" />
</bean:define>

<bean:define id="alertaProductoEntregado" toScope="page">
	<bean:write name="gestionarProductosForm" property="alertaProductoEntregado" />
</bean:define>

<bean:define id="alertaProductoNoEntregado" toScope="page">
	<bean:write name="gestionarProductosForm" property="alertaProductoNoEntregado" />
</bean:define>

<bean:define id="tipoLLamadaGestionarProductosAutonomo" toScope="page">
	<bean:write name="gestionarProductosForm" property="tipoLlamadaGestionarProductosAutonomo" />
</bean:define>

<bean:define id="tipoLLamadaGestionarProductosIniciativa" toScope="page">
	<bean:write name="gestionarProductosForm" property="tipoLlamadaGestionarProductosIniciativa" />
</bean:define>

<bean:define id="estiloFormaGestionarProductos" value="formaHtmlCompleta"></bean:define>

<logic:equal name="gestionarProductosForm" property="llamada" value="<%= tipoLLamadaGestionarProductosAutonomo %>">
	<bean:define id="estiloFormaGestionarProductos" value="formaHtmlCompleta"></bean:define>
</logic:equal>

<%-- Representación de la Forma --%>
<html:form action="/planificacionseguimiento/productos/gestionarProductos" styleClass="<%= estiloFormaGestionarProductos %>">

	<%-- Atributos de la Forma --%>
	<html:hidden property="paginaProductos" />
	<html:hidden property="atributoOrdenProductos" />
	<html:hidden property="tipoOrdenProductos" />
	<html:hidden property="iniciativaId" />
	<html:hidden property="seleccionados" />
	<html:hidden property="valoresSeleccionados" />

	<vgcinterfaz:contenedorForma>

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarproductos.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			<html:rewrite action='/planificacionseguimiento/productos/gestionarProductos' />
		</vgcinterfaz:contenedorFormaBotonActualizar>

		<%-- Botón Regresar --%>
		<vgcinterfaz:contenedorFormaBotonRegresar>
		javascript:irAtras(2)
		</vgcinterfaz:contenedorFormaBotonRegresar>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>
			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionProductos" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoProducto();" permisoId="PRODUCTO_ADD" aplicaOrganizacion="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarProducto();" permisoId="PRODUCTO_EDIT" aplicaOrganizacion="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarProducto();" permisoId="PRODUCTO_DELETE" aplicaOrganizacion="true" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaProductos" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- filtro --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
			<vgcinterfaz:barraHerramientas nombre="barraGestionarProductos">
				<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PRODUCTO_ADD">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoProducto();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcutil:tienePermiso>
				<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PRODUCTO_EDIT">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarProducto();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcutil:tienePermiso>
				<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PRODUCTO_DELETE">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarProducto();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcutil:tienePermiso>
			</vgcinterfaz:barraHerramientas>
		</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- Visor Lista --%>
		<vgcinterfaz:visorLista nombre="visorProductos" namePaginaLista="paginaProductos" scopePaginaLista="request" seleccionSimple="true" nombreForma="gestionarProductosForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" messageKeyNoElementos="jsp.gestionarproductos.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="20px">
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="50%" onclick="javascript:consultarConfigurable(document.gestionarProductosForm, getActionSubmitProductos(), document.gestionarProductosForm.paginaProductos, document.gestionarProductosForm.atributoOrdenProductos, document.gestionarProductosForm.tipoOrdenProductos, 'nombre', null);">
				<vgcutil:message key="jsp.gestionarproductos.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaInicio" width="70px" onclick="javascript:consultarConfigurable(document.gestionarProductosForm, getActionSubmitProductos(), document.gestionarProductosForm.paginaProductos, document.gestionarProductosForm.atributoOrdenProductos, document.gestionarProductosForm.tipoOrdenProductos, 'fechaInicio', null);">
				<vgcutil:message key="jsp.gestionarproductos.columna.entrega" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="reprogramado" width="110px" onclick="javascript:consultarConfigurable(document.gestionarProductosForm, getActionSubmitProductos(), document.gestionarProductosForm.paginaProductos, document.gestionarProductosForm.atributoOrdenProductos, document.gestionarProductosForm.tipoOrdenProductos, 'fechaInicio', null);">
				<vgcutil:message key="jsp.gestionarproductos.columna.reprogramado" />
			</vgcinterfaz:columnaVisorLista>

			<%-- Filas --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="producto">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="producto" property="productoId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta">
					<vgcst:imagenAlertaProducto name="producto" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
					<bean:write name="producto" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaInicio">
					<bean:write name="producto" property="fechaInicioFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="reprogramado">
					<bean:write name="producto" property="fechaFinFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>

		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaProductos" labelPage="inPagina" action="javascript:consultarV2(gestionarProductosForm, gestionarProductosForm.paginaProductos, gestionarProductosForm.atributoOrdenProductos, gestionarProductosForm.tipoOrdenProductos, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferiorProducto">
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>

</html:form>
