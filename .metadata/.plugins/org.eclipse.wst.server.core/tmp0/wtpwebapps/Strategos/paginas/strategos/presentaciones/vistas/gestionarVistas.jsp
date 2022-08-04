<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (03/08/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">

	function propiedadesVista() 
	{
		var vistaId = document.gestionarVistasForm.seleccionadosVistas.value;
		if ((vistaId != null) && (vistaId != '')) 
			alert("Función por implementar");
	}

	function reporteVista() 
	{
		abrirReporte('<html:rewrite action="/presentaciones/vistas/generarReporteVistas.action"/>?atributoOrden=' + gestionarVistasForm.atributoOrdenVistas.value + '&tipoOrden=' + gestionarVistasForm.tipoOrdenVistas.value,'reporte');
	}

	function nuevaVista() 
	{		
		//abrirVentanaModal('<html:rewrite action="/presentaciones/vistas/crearVista" />', "VistaAdd", 580, 330);
		window.location.href = '<html:rewrite action="/presentaciones/vistas/crearVista" />';
	}

	function modificarVista() 
	{
		var vistaId = document.gestionarVistasForm.seleccionadosVistas.value;
		if ((vistaId != null) && (vistaId != ''))
		{
			<logic:equal name="gestionarVistasForm" property="editarForma" value="true">
				window.location.href='<html:rewrite action="/presentaciones/vistas/modificarVista" />?vistaId=' + vistaId;
				//abrirVentanaModal('<html:rewrite action="/presentaciones/vistas/modificarVista" />?vistaId=' + vistaId, "VistaEdit", 580, 330);
			</logic:equal>
			<logic:notEqual name="gestionarVistasForm" property="editarForma" value="true">
				<logic:equal name="gestionarVistasForm" property="verForma" value="true">
					//abrirVentanaModal('<html:rewrite action="/presentaciones/vistas/verVista" />?vistaId=' + vistaId, "VistaEdit", 580, 330);
					window.location.href ='<html:rewrite action="/presentaciones/vistas/verVista" />?vistaId=' + vistaId;
				</logic:equal>
			</logic:notEqual>
		}
	}

	function eliminarVista() 
	{
		var vistaId = document.gestionarVistasForm.seleccionadosVistas.value;
		if ((vistaId != null) && (vistaId != '')) 
		{
			var eliminar = confirm('<vgcutil:message key="jsp.gestionarvistas.eliminarvista.confirmar" />');
			if (eliminar) 
				window.location.href='<html:rewrite action="/presentaciones/vistas/eliminarVista"/>?vistaId=' + vistaId + '&ts=<%= (new Date()).getTime() %>';
		}
	}

	function limpiarFiltrosVista() 
	{
	  	gestionarVistasForm.filtroNombre.value = "";
	  	gestionarVistasForm.submit();
	}

	function buscarVistas() 
	{
		gestionarVistasForm.submit();
	}

	function eventoClickVista() 
	{
		gestionarVistasForm.submit();
	}

	function mostrarVista() 
	{
		var vistaId = document.gestionarVistasForm.seleccionadosVistas.value;
		if ((vistaId != null) && (vistaId != '')) 
			window.location.href='<html:rewrite action="/presentaciones/vistas/mostrarVista" />?defaultLoader=true&vistaId=' + vistaId;
	}

</script>

<%-- Representación de la Forma --%>
<html:form action="/presentaciones/vistas/gestionarVistas" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="paginaSeleccionadaVistas" />
	<html:hidden property="atributoOrdenVistas" />
	<html:hidden property="tipoOrdenVistas" />
	<html:hidden property="seleccionadosVistas" />

	<vgcinterfaz:contenedorForma idContenedor="body-vistas">
		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarvistas.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

				<%-- Menú: Archivo --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivoVista" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteVista();" permisoId="VISTA_PRINT" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionVista" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevaPagina();" permisoId="VISTA_ADD" />
						<logic:equal name="gestionarVistasForm" property="editarForma" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" aplicaOrganizacion="true" />
						</logic:equal>
						<logic:notEqual name="gestionarVistasForm" property="editarForma" value="true">
							<logic:equal name="gestionarVistasForm" property="verForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" aplicaOrganizacion="true" />
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarPagina();" permisoId="VISTA_DELETE" aplicaOrganizacion="true" />
						<%-- <vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesVista();" permisoId="" />--%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVerVista" key="menu.ver">
						<vgcinterfaz:botonMenu key="menu.ver.grafico" onclick="mostrarVista();" permisoId="VISTA" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>


		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarPaginas">

				<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoVista" onclick="javascript:nuevaVista();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<logic:equal name="gestionarVistasForm" property="editarForma" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarVista" onclick="javascript:modificarVista();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:notEqual name="gestionarVistasForm" property="editarForma" value="true">
					<logic:equal name="gestionarVistasForm" property="verForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarVista" onclick="javascript:modificarVista();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasBoton permisoId="VISTA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarVista" onclick="javascript:eliminarVista();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<%--
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedadesVista" onclick="javascript:propiedadesPagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				--%>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="grafico" pathImagenes="/paginas/strategos/presentaciones/vistas/imagenes/barraHerramientas/" nombre="graficoVista" onclick="javascript:mostrarVista();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="boton.grafico.alt" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfVista" onclick="javascript:reporteVista();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- Visor Tipo Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaVistas" messageKeyNoElementos="jsp.gestionarvistas.noregistros" nombre="visorVistas" seleccionSimple="true" nombreForma="gestionarVistasForm" nombreCampoSeleccionados="seleccionadosVistas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

			<vgcinterfaz:columnaVisorLista nombre="nombre" width="400px" onclick="javascript:consultarV2(document.gestionarVistasForm, gestionarVistasForm.paginaSeleccionadaVistas, gestionarVistasForm.atributoOrdenVistas, gestionarVistasForm.tipoOrdenVistas, 'nombre', null)">
				<vgcutil:message key="jsp.gestionarvistas.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="fechaInicio" width="100px" onclick="javascript:consultarV2(document.gestionarVistasForm, gestionarVistasForm.paginaSeleccionadaVistas, gestionarVistasForm.atributoOrdenVistas, gestionarVistasForm.tipoOrdenVistas, 'fechaInicio', null)">
				<vgcutil:message key="jsp.gestionarvistas.columna.fechainicio" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="fechaFin" width="100px" onclick="javascript:consultarV2(document.gestionarVistasForm, gestionarVistasForm.paginaSeleccionadaVistas, gestionarVistasForm.atributoOrdenVistas, gestionarVistasForm.tipoOrdenVistas, 'fechaFin', null)">
				<vgcutil:message key="jsp.gestionarvistas.columna.fechafin" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="vista">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="vista" property="vistaId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickVista()</vgcinterfaz:visorListaFilaEventoOnclick>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="vista" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fechaInicio">
					<bean:write name="vista" property="fechaInicio" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fechaFin">
					<bean:write name="vista" property="fechaFin" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaVistas" labelPage="inPagina" action="javascript:consultar(gestionarVistasForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarVistasForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaVistas" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-vistas'), 225);
</script>
