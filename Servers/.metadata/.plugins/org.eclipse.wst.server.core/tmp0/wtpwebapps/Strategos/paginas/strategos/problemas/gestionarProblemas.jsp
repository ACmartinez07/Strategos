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

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	function actualizar() 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas" />?tipo=' + document.gestionarProblemasForm.tipo.value;
	}

	function nuevoProblema() 
	{
		window.location.href='<html:rewrite action="/problemas/crearProblema" />';
	}

	function modificarProblema() 
	{
		if ((document.gestionarProblemasForm.seleccionados.value == null) || (document.gestionarProblemasForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}
		var problemaId = document.gestionarProblemasForm.seleccionados.value;		

		<logic:equal name="gestionarProblemasForm" property="editarForma" value="true">
			window.location.href='<html:rewrite action="/problemas/modificarProblema" />?problemaId=' + problemaId;
		</logic:equal>
		<logic:notEqual name="gestionarProblemasForm" property="editarForma" value="true">
			<logic:equal name="gestionarProblemasForm" property="verForma" value="true">
				window.location.href='<html:rewrite action="/problemas/verProblema" />?problemaId=' + problemaId;
			</logic:equal>
		</logic:notEqual>
	}

	function copiarProblema() 
	{
		if ((document.gestionarProblemasForm.seleccionados.value == null) || (document.gestionarProblemasForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var problemaId = document.gestionarProblemasForm.seleccionados.value;
		window.location.href='<html:rewrite action="/problemas/copiarProblema" />?problemaId=' + problemaId;
	}

	function eliminarProblema() 
	{
		if ((document.gestionarProblemasForm.seleccionados.value == null) || (document.gestionarProblemasForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var problemaId = document.gestionarProblemasForm.seleccionados.value;
		var eliminar = confirm('<vgcutil:message key="jsp.gestionarproblemas.eliminarproblema.confirmar" />');
		if (eliminar) 
			window.location.href='<html:rewrite action="/problemas/eliminarProblema"/>?problemaId=' + problemaId + '&ts=<%= (new Date()).getTime() %>';
	}	

	function propiedadesProblema() 
	{
		if ((document.gestionarProblemasForm.seleccionados.value == null) || (document.gestionarProblemasForm.seleccionados.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var problemaId = document.gestionarProblemasForm.seleccionados.value;
		abrirVentanaModal('<html:rewrite action="/problemas/propiedadesProblema" />?problemaId=' + problemaId, "Problema", 450, 470);
	}
	
	function reporteProblemas() 
	{	
		abrirReporte('<html:rewrite action="/problemas/generarReporteProblemas.action"/>?atributoOrden=' + gestionarProblemasForm.atributoOrden.value + '&tipoOrden=' + gestionarProblemasForm.tipoOrden.value, 'reporte');
	}

    function gestionarAccionesCorrectivas() 
    {
    	if ((document.gestionarProblemasForm.seleccionados.value == null) || (document.gestionarProblemasForm.seleccionados.value == "")) 
    	{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var problemaId = document.gestionarProblemasForm.seleccionados.value;
	   window.location.href='<html:rewrite action="/problemas/acciones/gestionarAcciones"/>?problemaId=' + problemaId;
    }

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
<bean:define id="noregistro" toScope="page">
	<logic:equal name="gestionarProblemasForm" property="tipo" value="0">
		jsp.gestionarproblemas.noregistros
	</logic:equal>
	<logic:equal name="gestionarProblemasForm" property="tipo" value="1">
		jsp.gestionaraccionespreventivas.noregistros
	</logic:equal>
</bean:define>

<%-- Representación de la Forma --%>
<html:form action="/problemas/gestionarProblemas" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="tipo" />

	<vgcinterfaz:contenedorForma idContenedor="body-problemas">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			<logic:equal name="gestionarProblemasForm" property="tipo" value="0">
				..:: <vgcutil:message key="jsp.gestionarproblemas.titulo" />
			</logic:equal>
			<logic:equal name="gestionarProblemasForm" property="tipo" value="1">
				..:: <vgcutil:message key="jsp.gestionaracciones.preventinas.titulo" />
			</logic:equal>
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
					<vgcinterfaz:menuBotones id="menuArchivoProblemas" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteProblemas();" permisoId="PROBLEMA_PRINT" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionProblemas" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoProblema();" permisoId="PROBLEMA_ADD" />
						<logic:equal name="gestionarProblemasForm" property="editarForma" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarProblema();" />
						</logic:equal>
						<logic:notEqual name="gestionarProblemasForm" property="editarForma" value="true">
							<logic:equal name="gestionarProblemasForm" property="verForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarProblema();" />
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarProblema();" permisoId="PROBLEMA_DELETE" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiarProblema();" permisoId="PROBLEMA_COPY" />
						<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesProblema();" permisoId="PROBLEMA" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasProblemas" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" agregarSeparador="true"/>
						<%-- 
						<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
						--%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaProblemas" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarProblemas">
				<vgcinterfaz:barraHerramientasBoton permisoId="PROBLEMA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="nuevoProblema();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<logic:equal name="gestionarProblemasForm" property="editarForma" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="modificarProblema();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:notEqual name="gestionarProblemasForm" property="editarForma" value="true">
					<logic:equal name="gestionarProblemasForm" property="verForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="modificarProblema();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasBoton permisoId="PROBLEMA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="eliminarProblema();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="propiedadesProblema();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="PROBLEMA_COPY" nombreImagen="copiar" pathImagenes="/componentes/barraHerramientas/" nombre="copiar" onclick="copiarProblema();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.copiar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="ACCION" nombreImagen="accionesCorrectivas" pathImagenes="/paginas/strategos/problemas/imagenes/barraHerramientas/" nombre="accionesCorrectivas" onclick="gestionarAccionesCorrectivas();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<logic:equal name="gestionarProblemasForm" property="tipo" value="0">
							<vgcutil:message key="jsp.gestionarproblemas.boton.accionescorrectivas" />
						</logic:equal>
						<logic:equal name="gestionarProblemasForm" property="tipo" value="1">
							<vgcutil:message key="jsp.gestionarproblemas.boton.accionespreventivas" />
						</logic:equal>
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteProblemas();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- Visor Tipo Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaProblemas" width="100%" nombre="visorProblemas" seleccionSimple="true" nombreForma="gestionarProblemasForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="<%= noregistro %>" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="70%" onclick="javascript:consultar(document.gestionarProblemasForm, 'nombre', null)">
				<vgcutil:message key='jsp.gestionarproblemas.columna.nombre' />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="fecha" width="15%" onclick="javascript:consultar(document.gestionarProblemasForm, 'fecha', null)">
				<vgcutil:message key='jsp.gestionarproblemas.columna.fecha' />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="estado" width="15%">
				<vgcutil:message key='jsp.gestionarproblemas.columna.estado' />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="problema">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="problema" property="problemaId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="problema" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha">
					<bean:write name="problema" property="fechaFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estado">
					<logic:notEmpty name="problema" property="estadoId">
						<bean:write name="problema" property="estado.nombre" />
					</logic:notEmpty>
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaProblemas" labelPage="inPagina" action="javascript:consultar(gestionarProblemasForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarProblemasForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaProblemas" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	var objeto = document.getElementById('body-problemas');
	if (objeto != null)
	{
		var width = (parseInt(_myWidth) - 140);
		var spliter = document.getElementById('splitProblemasPanelIzquierdo');
		if (spliter != null)
			width = width - parseInt(spliter.style.width.replace("px", ""));
		else
			width = (parseInt(_myWidth) - 540);
		
		objeto.style.width = width + "px";
		objeto.style.height = (parseInt(_myHeight) - 230) + "px";
		var visor = document.getElementById('visorProblemas');
		if (visor != null)
			visor.style.width = (width - 10) + "px";
	}
</script>
