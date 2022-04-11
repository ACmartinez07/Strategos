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

	function reportePagina() 
	{		
		abrirReporte('<html:rewrite action="/presentaciones/paginas/generarReportePaginas.action"/>?atributoOrden=' + gestionarPaginasForm.atributoOrdenPaginas.value + '&tipoOrden=' + gestionarPaginasForm.tipoOrdenPaginas.value,'reporte');
	}
	
	function nuevaPagina() 
	{
		var vistaId = document.gestionarVistasForm.seleccionadosVistas.value;
		if ((vistaId != null) && (vistaId != '')) 
			window.location.href='<html:rewrite action="/presentaciones/paginas/crearPagina" />';
	}

	function modificarPagina() 
	{
		var paginaId = document.gestionarPaginasForm.seleccionadosPaginas.value;
		if ((paginaId != null) && (paginaId != '')) 
		{
			<logic:equal name="gestionarPaginasForm" property="editarForma" value="true">
				window.location.href='<html:rewrite action="/presentaciones/paginas/modificarPagina" />?paginaId=' + paginaId;
			</logic:equal>
			<logic:notEqual name="gestionarPaginasForm" property="editarForma" value="true">
				<logic:equal name="gestionarPaginasForm" property="verForma" value="true">
					window.location.href='<html:rewrite action="/presentaciones/paginas/verPagina" />?paginaId=' + paginaId;
				</logic:equal>
			</logic:notEqual>
		}		
	}

	function eliminarPagina() {
		var paginaId = document.gestionarPaginasForm.seleccionadosPaginas.value;
		if ((paginaId != null) && (paginaId != '')) {
			var eliminar = confirm('<vgcutil:message key="jsp.gestionarpaginas.eliminarpagina.confirmar" />');
			if (eliminar) {
				window.location.href='<html:rewrite action="/presentaciones/paginas/eliminarPagina"/>?paginaId=' + paginaId + '&ts=<%= (new Date()).getTime() %>';
			}
		}
	}
	
	function propiedadesPagina() {
		var paginaId = document.gestionarPaginasForm.seleccionadosPaginas.value;
		if ((paginaId != null) && (paginaId != '')) {
		    alert("Función por implementar");
			//abrirVentanaModal('<html:rewrite action="/presentaciones/paginas/propiedadesPagina" />?paginaId=' + paginaId, "Pagina", 460, 460);				
		}
	}
	
	function cambiarOrdenPagina(subir) {
		var paginaId = document.gestionarPaginasForm.seleccionadosPaginas.value;
		if ((paginaId != null) && (paginaId != '')) {
			window.location.href='<html:rewrite action="/presentaciones/paginas/cambiarOrdenPagina"/>?paginaId=' + paginaId + '&subir='+ subir;
		}		
	}

	function gestionarCeldas() {		
		var paginaId = document.gestionarPaginasForm.seleccionadosPaginas.value;
		if ((paginaId != null) && (paginaId != '')) {
			window.location.href='<html:rewrite action="/presentaciones/celdas/gestionarCeldas"/>?paginaId=' + paginaId;
		}	
	}

	function limpiarFiltrosPagina() {
	  gestionarPaginasForm.filtroDescripcion.value = "";			  
	  gestionarPaginasForm.submit();
	}

	function buscarPaginas() {
		gestionarPaginasForm.submit();
	}


	function eventoClickPagina() {
		document.gestionarPaginasForm.submit();
	}

</script>

<%-- Representación de la Forma --%>
<html:form action="/presentaciones/paginas/gestionarPaginas" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrdenPaginas" />
	<html:hidden property="tipoOrdenPaginas" />
	<html:hidden property="seleccionadosPaginas" />

	<vgcinterfaz:contenedorForma idContenedor="body-paginas">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarpaginas.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			<html:rewrite action='/presentaciones/vistas/gestionarVistas' />
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
					<vgcinterfaz:menuBotones id="menuArchivoPagina" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reportePagina();" permisoId="PAGINA_PRINT" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionPagina" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevaPagina();" permisoId="PAGINA_ADD" />
						<logic:equal name="gestionarPaginasForm" property="editarForma" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" aplicaOrganizacion="true" />
						</logic:equal>
						<logic:notEqual name="gestionarPaginasForm" property="editarForma" value="true">
							<logic:equal name="gestionarPaginasForm" property="verForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarPagina();" aplicaOrganizacion="true" />
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarPagina();" permisoId="PAGINA_DELETE" aplicaOrganizacion="true" />
						<%--<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesPagina();" permisoId="" />--%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaPagina" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>


		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarPaginas">

				<vgcinterfaz:barraHerramientasBoton permisoId="PAGINA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoPagina" onclick="javascript:nuevaPagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarPagina" onclick="javascript:modificarPagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.modificar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="PAGINA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarPagina" onclick="javascript:eliminarPagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<%--
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedadesPagina" onclick="javascript:propiedadesPagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				--%>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="PAGINA_EDIT" nombreImagen="flechaArriba" pathImagenes="/componentes/barraHerramientas/" nombre="subirPagina" onclick="javascript:cambiarOrdenPagina(1);">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="boton.subir.alt" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="PAGINA_EDIT" nombreImagen="flechaAbajo" pathImagenes="/componentes/barraHerramientas/" nombre="bajarPagina" onclick="javascript:cambiarOrdenPagina(0);">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="boton.bajar.alt" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfPagina" onclick="javascript:reportePagina();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<%-- 
				<vgcinterfaz:barraHerramientasBoton nombreImagen="vistas_detalle" pathImagenes="/componentes/barraHerramientas/" nombre="celdasPagina" onclick="javascript:gestionarCeldas();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="boton.celdas.alt" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				 --%>
			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>


		<%-- Visor Tipo Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaPaginas" messageKeyNoElementos="jsp.gestionarpaginas.noregistros" nombre="visorPaginas" seleccionSimple="true" nombreForma="gestionarPaginasForm" nombreCampoSeleccionados="seleccionadosPaginas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

			<vgcinterfaz:columnaVisorLista nombre="numero" width="100px">
				<vgcutil:message key="jsp.gestionarpaginas.columna.pagina.numero" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="fechaInicio" width="550px">
				<vgcutil:message key="jsp.gestionarpaginas.columna.descripcion" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="fechaFin" width="100px">
				<vgcutil:message key="jsp.gestionarpaginas.columna.celdas" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="pagina">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="pagina" property="paginaId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickPagina()
				</vgcinterfaz:visorListaFilaEventoOnclick>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="numero" align="right">
					<bean:write name="pagina" property="numero" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fechaInicio">
					<bean:write name="pagina" property="descripcion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fechaFin">
					<img src="<html:rewrite page='/paginas/strategos/presentaciones/paginas/imagenes/celdas.gif'/>" border="0" width="10" height="10">&nbsp;&nbsp;<bean:write name="pagina" property="filas" /> x <bean:write name="pagina" property="columnas" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaPaginas" labelPage="inPagina" action="javascript:consultar(gestionarPaginasForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarPaginasForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaPaginas" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-paginas'), 225);
</script>
