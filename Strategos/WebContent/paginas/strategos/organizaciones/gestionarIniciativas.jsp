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

<%-- Modificado por: Kerwin Arias (09/01/2012) --%>
<%-- Modificado por: Andres Caucali (19/09/2018) --%>
<%-- Funciones JavaScript locales de la pï¿½gina Jsp --%>
<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/Actividad.js'/>"></script>
<script type="text/javascript">
	var _tituloIniciativa = '<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />';
	var _showFiltro = false;
	var _selectHitoricoTypeIndex = 1;

	

	function planificacionSeguimiento() 
	{
		var actividad = new Actividad();
		actividad.url = '<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>';
		actividad.ShowList(true, document.gestionarIniciativasForm.seleccionadoId.value, 'Inicio');
	}

	function eventoClickIniciativa(objetoFilaId) 
	{
		activarBloqueoEspera();
		if (document.gestionarIniciativasForm.source.value == "Plan")
			document.gestionarIniciativasForm.action = getActionSubmitIniciativasGestionFromPlan();
		else if (document.gestionarIniciativasForm.source.value == "portafolio")
		{
			refrescarPortafolio(document.gestionarIniciativasForm.seleccionadoId.value);
			return;
		}
		else
			document.gestionarIniciativasForm.action = getActionSubmitIniciativasGestion();			
		document.gestionarIniciativasForm.submit();
	}

	
	function getActionSubmitIniciativas() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativas"/>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIniciativas" primerParametro="true" /> + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	function getActionSubmitIniciativasGestion() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativas" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	function getActionSubmitIniciativasGestionFromPlan() 
	{
		return '<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />?iniciativaId=' + document.gestionarIniciativasForm.seleccionadoId.value + '&source=' + document.gestionarIniciativasForm.source.value + '&planId' + document.gestionarIniciativasForm.planId.value;
	}

	
	function resizeAltoForma(alto)
	{
		if (typeof(alto) == "undefined")
			alto = 500;
			
		resizeAlto(document.getElementById('body-iniciativas'), alto);
	}
	
	
	function configurarVisorIniciativas() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIniciativas', _tituloIniciativa);
	}
	
	
	
</script>
<%-- Representaciï¿½n de la Forma --%>
<html:form action="/iniciativas/gestionarIniciativas" styleClass="formaHtmlGestionar" >

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionadoId" />
	<html:hidden property="tipoAlerta" />
	<html:hidden property="respuesta" />
	<html:hidden property="source" />
	<html:hidden property="planId" />
	<html:hidden property="portafolioId" />

	<input type="hidden" name="nombreIniciativa" value="">
	<input type="hidden" name="seleccionadoIniciativaId" value="">

	<bean:define id="tipoCalculoEstadoIniciativaPorActividades">
		<bean:write name="gestionarIniciativasForm" property="tipoCalculoEstadoIniciativaPorActividades" />
	</bean:define>
	<bean:define id="tipoCalculoEstadoIniciativaPorSeguimientos">
		<bean:write name="gestionarIniciativasForm" property="tipoCalculoEstadoIniciativaPorSeguimientos" />
	</bean:define>

	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<vgcinterfaz:contenedorForma idContenedor="body-iniciativas">

		<%-- Tï¿½tulo --%>
		<vgcinterfaz:contenedorFormaTitulo>
			<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">..:: <vgcutil:message key="jsp.gestionariniciativas.titulo" arg0="<%= tituloIniciativa %>" /></logic:notEqual>
			<logic:equal name="gestionarIniciativasForm" property="source" value="portafolio">..:: <vgcutil:message key="jsp.gestionarportafolios.titulo" arg0="<%= tituloIniciativa %>" /></logic:equal>
		</vgcinterfaz:contenedorFormaTitulo>
		
		<%-- Botón Regresar --%>
		<vgcinterfaz:contenedorFormaBotonRegresar>
		    javascript:irAtras(2)
		</vgcinterfaz:contenedorFormaBotonRegresar>
	
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarIniciativas">
				
				<%--<logic:greaterThan name="gestionarIniciativasPaginaPeriodos" property="total" value="0"> --%>
				<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="INICIATIVA_SEGUIMIENTO">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="planificacion" pathImagenes="/paginas/strategos/iniciativas/imagenes/" nombre="planificacionSeguimiento" onclick="javascript:planificacionSeguimiento();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionariniciativas.planificacionseguimiento" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcutil:tienePermiso>
																
			</vgcinterfaz:barraHerramientas>
			
			
		</vgcinterfaz:contenedorFormaBarraGenerica>
		
		<bean:define id="listavacia">
			<vgcutil:message key="jsp.gestionariniciativas.noregistros" arg0="<%= tituloIniciativa %>"/>
		</bean:define>
		
		<vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" nombre="visorIniciativas" seleccionSimple="true" nombreForma="gestionarIniciativasForm" nombreCampoSeleccionados="seleccionadoId" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" messageKeyNoElementos="<%= listavacia %>" >
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
				<vgcutil:message key="jsp.gestionariniciativasplan.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'nombre', null)">
				<vgcutil:message key="jsp.gestionariniciativas.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeEjecutado" width="70px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="70px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.porcentaje.programado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.estatus" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaActualizacion" width="100px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.fecha.actualizacion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaActualizacionEsperada" width="100px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="dias" width="70px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.dias" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="observacion" width="100px" >
				<vgcutil:message key="action.reporte.estatus.iniciativa.observacion" />
			</vgcinterfaz:columnaVisorLista>		
			<vgcinterfaz:columnaVisorLista nombre="anioformproy" width="75px" >
				<vgcutil:message key="jsp.gestionariniciativas.columna.anioFormProy" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="80px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'frecuencia', null)">
				<vgcutil:message key="jsp.gestionariniciativas.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="organizacion" width="150px" >
				<vgcutil:message key="action.reporteresponsables.organizacion" />
			</vgcinterfaz:columnaVisorLista>
			

			<%-- Filas --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">
				<vgcinterfaz:visorListaFilaId>
					<bean:write name="iniciativa" property="iniciativaId" />
				</vgcinterfaz:visorListaFilaId>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIniciativa name="iniciativa" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="iniciativa" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeEjecutado" align="center">
					<bean:write name="iniciativa" property="porcentajeCompletadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center">
					<bean:write name="iniciativa" property="porcentajeEsperadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
					<bean:write name="iniciativa" property="estatus.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaActualizacion" align="center">
					<bean:write name="iniciativa" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaActualizacionEsperada" align="center">
					<bean:write name="iniciativa" property="fechaesperado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="dias">
					<bean:write name="iniciativa" property="dias" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="observacion">
					<bean:write name="iniciativa" property="observacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>		
								
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="anioformproy" align="center">
					<bean:write name="iniciativa" property="anioFormulacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
					<bean:write name="iniciativa" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacion">
					<bean:write name="iniciativa" property="organizacionNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				
			</vgcinterfaz:filasVisorLista>

		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaIniciativas" labelPage="inPagina" action="javascript:consultarV2(gestionarIniciativasForm, gestionarIniciativasForm.pagina, gestionarIniciativasForm.atributoOrden, gestionarIniciativasForm.tipoOrden, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<logic:notEqual name="gestionarIniciativasForm" property="source" value="portafolio">
			<vgcinterfaz:contenedorFormaBarraInferior>
				<logic:notEmpty name="gestionarIniciativasForm" property="atributoOrden">
					<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIniciativasForm" property="atributoOrden" />  [<bean:write name="gestionarIniciativasForm" property="tipoOrden" />]
				</logic:notEmpty>
			</vgcinterfaz:contenedorFormaBarraInferior>
		</logic:notEqual>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	if (gestionarIniciativasForm.source.value != "portafolio")
		resizeAltoForma(110);
	else
		resizeAltoFormaDividida(true);
	var selectHitoricoType = document.getElementById('selectHitoricoType');
	if (selectHitoricoType != null)
		_selectHitoricoTypeIndex = selectHitoricoType.selectedIndex;
</script>
