<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>


<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarinstrumentos.convenio.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<script type="text/javascript">
			var _selectCondicionTypeIndex = 1;
			var _altoPrefijoListo = false;
		
			function nuevoConvenio(){
				window.location.href='<html:rewrite action="/instrumentos/crearConvenio" />';
			}
			
			function modificarConvenio(convenioId) 
			{
				if ((document.gestionarConveniosForm.seleccionados.value == null) || (document.gestionarConveniosForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var convenioId = document.gestionarConveniosForm.seleccionados.value;
				window.location.href='<html:rewrite action="/instrumentos/modificarConvenio" />?convenioId=' + convenioId;
			}
	
			function eliminarConvenio(convenioId) 
			{
				if ((document.gestionarConveniosForm.seleccionados.value == null) || (document.gestionarConveniosForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var convenioId = document.gestionarConveniosForm.seleccionados.value;	
				var eliminar = confirm ('<vgcutil:message key="jsp.gestionarconvenio.eliminar.confirmar" />');		
				if (eliminar)
					window.location.href='<html:rewrite action="/instrumentos/eliminarConvenio"/>?convenioId=' + convenioId + '&ts=<%= (new Date()).getTime() %>';
			}
					
		</script>
		
		<%-- Representación de la Forma --%>
	<html:form action="/instrumentos/gestionarConvenios" styleClass="formaHtmlGestionar">
		
		<%-- Atributos de la Forma --%>
		<html:hidden property="pagina" />
		<html:hidden property="atributoOrden" />
		<html:hidden property="tipoOrden" />
		<html:hidden property="seleccionados" />
		
	
		<vgcinterfaz:contenedorForma idContenedor="body-convenio">
	
			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.gestionarinstrumentos.convenio.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>
			
			<%-- Botón Actualizar --%>
			<vgcinterfaz:contenedorFormaBotonActualizar>
				<html:rewrite action='/instrumentos/gestionarConvenios' />
			</vgcinterfaz:contenedorFormaBotonActualizar>
		
			<%-- Botón Regresar --%>
			<vgcinterfaz:contenedorFormaBotonRegresar>
				<html:rewrite action='/instrumentos/gestionarInstrumentos' />
			</vgcinterfaz:contenedorFormaBotonRegresar>
	
			<%-- Menú --%>
			<vgcinterfaz:contenedorFormaBarraMenus>
	
				<%-- Inicio del Menú --%>
				<vgcinterfaz:contenedorMenuHorizontal>
	
				
					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoConvenio();" permisoId="PORTAFOLIO_ADD" />
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarConvenio();" permisoId="PORTAFOLIO_EDIT" />
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarConvenio();" permisoId="PORTAFOLIO_DELETE" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
		
					<%-- Menú: Ayuda --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuAyudaIniciativas" key="menu.ayuda">
							<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
	
				</vgcinterfaz:contenedorMenuHorizontal>
	
			</vgcinterfaz:contenedorFormaBarraMenus>
	
			<%-- Barra Genérica --%>
			<vgcinterfaz:contenedorFormaBarraGenerica height="10px">
	
	
				<%-- Barra de Herramientas --%>
				<vgcinterfaz:barraHerramientas nombre="barraGestionarPortafolios">
	
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoConvenio();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_EDIT" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarConvenio()();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarConvenio();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					
								
	
				</vgcinterfaz:barraHerramientas>
	
			</vgcinterfaz:contenedorFormaBarraGenerica>
	
			<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaConvenios" width="100%" messageKeyNoElementos="jsp.gestionarvistasdatos.noregistros" nombre="visorConvenio" seleccionSimple="true" nombreForma="gestionarConveniosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
		
												
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="220px">
						<vgcutil:message key="jsp.pagina.convenio.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="720px">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>
					
		
					<vgcinterfaz:filasVisorLista nombreObjeto="tipoConvenio">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="tipoConvenio" property="tiposConvenioId" />
						</vgcinterfaz:visorListaFilaId>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="tipoConvenio" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="tipoConvenio" property="descripcion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>	
		
	
		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaConvenios" labelPage="inPagina" action="javascript:consultar(gestionarConveniosForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>
		
		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarConveniosForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaConvenios" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
			
			
		</vgcinterfaz:contenedorForma>
	
	</html:form>
	
	<script type="text/javascript">
	resizeAlto(document.getElementById('body-convenio'), 230);
	
	var visor = document.getElementById('visorConvenio');
	if (visor != null)
		visor.style.width = (parseInt(_myWidth) - 140) + "px";
	</script>
		
	</tiles:put>
</tiles:insert>
