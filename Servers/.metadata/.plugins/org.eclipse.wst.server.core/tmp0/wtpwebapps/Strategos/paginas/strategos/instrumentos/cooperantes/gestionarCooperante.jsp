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
		..:: <vgcutil:message key="jsp.gestionarinstrumentos.cooperantes.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<script type="text/javascript">
			var _selectCondicionTypeIndex = 1;
			var _altoPrefijoListo = false;
		
			function nuevoCooperante(){				
				abrirVentanaModal('<html:rewrite action="/instrumentos/crearCooperante" />',"CooperanteAdd", 530, 380);				
			}
			
			function modificarCooperante(cooperanteId) 
			{
				if ((document.gestionarCooperantesForm.seleccionados.value == null) || (document.gestionarCooperantesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var cooperanteId = document.gestionarCooperantesForm.seleccionados.value;
				abrirVentanaModal('<html:rewrite action="/instrumentos/modificarCooperante" />?cooperanteId=' + cooperanteId , "CooperanteEdit", 530, 380);
				
			}
	
			function eliminarCooperante(cooperanteId) 
			{
				if ((document.gestionarCooperantesForm.seleccionados.value == null) || (document.gestionarCooperantesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var cooperanteId = document.gestionarCooperantesForm.seleccionados.value;	
				var eliminar = confirm ('<vgcutil:message key="jsp.gestionarcooperantes.eliminar.confirmar" />');		
				if (eliminar)
					window.location.href='<html:rewrite action="/instrumentos/eliminarCooperante"/>?cooperanteId=' + cooperanteId + '&ts=<%= (new Date()).getTime() %>';
			}
					
		</script>
		
		<%-- Representación de la Forma --%>
	<html:form action="/instrumentos/gestionarCooperantes" styleClass="formaHtmlGestionar">
		
		<%-- Atributos de la Forma --%>
		<html:hidden property="pagina" />
		<html:hidden property="atributoOrden" />
		<html:hidden property="tipoOrden" />
		<html:hidden property="seleccionados" />
		
	
		<vgcinterfaz:contenedorForma idContenedor="body-cooperantes">
	
			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.gestionarinstrumentos.cooperantes.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>
			
			<%-- Botón Actualizar --%>
			<vgcinterfaz:contenedorFormaBotonActualizar>
				<html:rewrite action='/instrumentos/gestionarCooperantes' />
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
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoCooperante();" permisoId="PORTAFOLIO_ADD" />
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarCooperante();" permisoId="PORTAFOLIO_EDIT" />
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarCooperante();" permisoId="PORTAFOLIO_DELETE" />
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
	
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoCooperante();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_EDIT" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarCooperante();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarCooperante();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					
								
	
				</vgcinterfaz:barraHerramientas>
	
			</vgcinterfaz:contenedorFormaBarraGenerica>
	
			<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaCooperantes" width="100%" messageKeyNoElementos="jsp.gestionarvistasdatos.noregistros" nombre="visorCooperantes" seleccionSimple="true" nombreForma="gestionarCooperantesForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
		
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" >
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
									
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="720px">
						<vgcutil:message key="jsp.gestionarvistasdatos.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="pais" width="300px" >
						<vgcutil:message key="jsp.pagina.cooperantes.pais" />
					</vgcinterfaz:columnaVisorLista>			
		
					<vgcinterfaz:filasVisorLista nombreObjeto="cooperante">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="cooperante" property="cooperanteId" />
						</vgcinterfaz:visorListaFilaId>
		
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="cooperante" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="cooperante" property="descripcion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="pais">
							<bean:write name="cooperante" property="pais" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
		
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>	
		
	
		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaCooperantes" labelPage="inPagina" action="javascript:consultar(gestionarCooperantesForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>
		
		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarCooperantesForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaCooperantes" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
			
			
		</vgcinterfaz:contenedorForma>
	
	</html:form>
	
	<script type="text/javascript">
	resizeAlto(document.getElementById('body-cooperantes'), 230);
	
	var visor = document.getElementById('visorCooperantes');
	if (visor != null)
		visor.style.width = (parseInt(_myWidth) - 140) + "px";
	</script>
		
	</tiles:put>
</tiles:insert>
