<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionar.estatus.iniciativa.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{
				abrirVentanaModal('<html:rewrite action="/iniciativas/estatus/editar/crear" />', "Estatus", 460, 210);
			}

			function modificar() 
			{
				if ((document.gestionarIniciativaEstatusForm.seleccionados.value == null) || (document.gestionarIniciativaEstatusForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var estadoId = document.gestionarIniciativaEstatusForm.seleccionados.value;
				abrirVentanaModal('<html:rewrite action="/iniciativas/estatus/editar/modificar" />?id=' + estadoId, "Estatus", 460, 210);
			}

			function eliminar() 
			{
				if ((document.gestionarIniciativaEstatusForm.seleccionados.value == null) || (document.gestionarIniciativaEstatusForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				
				var estadoId = document.gestionarIniciativaEstatusForm.seleccionados.value;
				respuesta = confirm ('<vgcutil:message key="jsp.gestionar.estatus.iniciativa.eliminar.confirmar" />');
				if (respuesta) 
					window.location.href='<html:rewrite action="/iniciativas/estatus/eliminar"/>?id=' + estadoId;
			}

			function reporte()
			{
				var url = '';
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					url = url + '&filtroNombre=' + filtroNombre.value;
				
				abrirReporte('<html:rewrite action="/iniciativas/estatus/generarReporte.action"/>?atributoOrden=' + gestionarIniciativaEstatusForm.atributoOrden.value + '&tipoOrden=' + gestionarIniciativaEstatusForm.tipoOrden.value + url, 'reporte');
			}
			
			function actualizar()
			{
				window.location.href='<html:rewrite action="/iniciativas/estatus/gestionarEstatus"/>';
			}
	
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/iniciativas/estatus/gestionarEstatus" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-estado-iniciativa">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionar.estatus.iniciativa.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
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
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="INICIATIVA_ESTATUS_PRINT" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="INICIATIVA_ESTATUS_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="INICIATIVA_ESTATUS_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="INICIATIVA_ESTATUS_DELETE" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

					</vgcinterfaz:contenedorMenuHorizontal>

				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarEstado">

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
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaIniciativaEstatus" width="100%" messageKeyNoElementos="jsp.gestionar.estatus.iniciativa.noregistros" nombre="visorEstatusIniciativas" seleccionSimple="true" nombreForma="gestionarIniciativaEstatusForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="40px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="porcentajeInicial" width="100px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.porcentaje.inicial" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="porcentajeFinal" width="100px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.porcentaje.final" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="sistema" width="15px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.sistema" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="bloquearMedicion" width="130px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.bloquear.medicion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="bloquearIndicador" width="130px">
						<vgcutil:message key="jsp.gestionar.estatus.iniciativa.columna.bloquear.indicadores" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="iniciativaEstatus">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="iniciativaEstatus" property="id" />
						</vgcinterfaz:visorListaFilaId>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="iniciativaEstatus" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeInicial" align="center">
							<bean:write name="iniciativaEstatus" property="porcentajeInicialFormateado" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeFinal" align="center">
							<bean:write name="iniciativaEstatus" property="porcentajeFinalFormateado" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="sistema" align="center">
							<vgcst:imagenTrueFalseTag name="iniciativaEstatus" property="sistema" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="bloquearMedicion" align="center">
							<vgcst:imagenTrueFalseTag name="iniciativaEstatus" property="bloquearMedicion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="bloquearIndicador" align="center">
							<vgcst:imagenTrueFalseTag name="iniciativaEstatus" property="bloquearIndicadores" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-estado-iniciativa'), 204);	

			var visor = document.getElementById('visorEstatusIniciativas');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>
</tiles:insert>
