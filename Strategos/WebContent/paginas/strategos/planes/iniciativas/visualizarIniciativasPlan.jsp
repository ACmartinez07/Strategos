<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript">
	
	function gestionarAnexosIniciativa(inciativaId) 
	{
		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, inciativaId, 'Iniciativa', 0);
	}
	
	function gestionarPlanificacionSeguimiento(inciativaId) 
	{
		<logic:notEmpty name="visualizarPlanForm">
			window.location.href='<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>?iniciativaId=' + inciativaId + '&planId=<bean:write name="visualizarPlanForm" property="planId" />';
		</logic:notEmpty>
		<logic:notEmpty name="visualizarPerspectivaForm">
			window.location.href='<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>?iniciativaId=' + inciativaId + '&planId=<bean:write name="visualizarPerspectivaForm" property="planId" />';
		</logic:notEmpty>
	}
	
</script>

<bean:define id="nombreIniciativaPlural" value="INICIATIVA"></bean:define>
<logic:notEmpty name="visualizarPlanForm">
<bean:define id="nombreIniciativaPlural"><bean:write name="visualizarPlanForm" property="plantillaPlan.nombreIniciativaPlural" /></bean:define>
</logic:notEmpty>
<logic:notEmpty name="visualizarPerspectivaForm">
<bean:define id="nombreIniciativaPlural"><bean:write name="visualizarPerspectivaForm" property="plantillaPlan.nombreIniciativaPlural" /></bean:define>
</logic:notEmpty>

<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" width="100%">
	<tr>
		<td><b><bean:write name="nombreIniciativaPlural" /></b></td>
	</tr>
	<tr>
		<td><%-- Visor Lista --%> <vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" scopePaginaLista="request" nombre="visorIniciativasPlan" messageKeyNoElementos="jsp.visualizariniciativasplan.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados del Visor Lista --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="100px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.porcentajecompletado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeEsperado" width="70px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.porcentajeesperado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaUltimaMedicion" width="100px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.ultimoperiodoevaluacion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="ultimaMedicion" width="100px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.ultimaMedicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="planificacionyseguimiento" width="100px" align="center">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.planificacionyseguimiento" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="anexos" width="70px" align="center">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.explicaciones" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="100px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="condicion" width="40px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.condicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.estatus" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="anioformproy" width="100px" >
				<vgcutil:message key="jsp.gestionariniciativas.columna.anioFormProy" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="responsableSeguimiento" width="200px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.responsableseguimiento" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="200px">
				<vgcutil:message key="jsp.visualizariniciativasplan.columna.responsableLograrMeta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="responsableCargarEjecutado" width="200px">
				<vgcutil:message key="jsp.visualizarindicadoresplan.columna.responsable.cargarejecutado" />
			</vgcinterfaz:columnaVisorLista>

			<%-- Filas del Visor Lista --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="iniciativa" property="iniciativaId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIniciativa name="iniciativa" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<a href="javascript:visualizarIniciativa('<bean:write name="iniciativa" property="iniciativaId" />', '<bean:write name="visualizarPerspectiva_perspectivaId" scope="request" />', '<bean:write name="visualizarPerspectiva_nivel" scope="request" />')"> <bean:write name="iniciativa" property="nombre" /></a>
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center"><bean:write name="iniciativa" property="porcentajeCompletadoFormateado" /></vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeEsperado" align="center">
					<bean:write name="iniciativa" property="porcentajeEsperadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaUltimaMedicion" align="center">
					<bean:write name="iniciativa" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimaMedicion" align="center">
					<bean:write name="iniciativa" property="ultimaMedicionFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="planificacionyseguimiento" align="center">
					<img onclick="gestionarPlanificacionSeguimiento('<bean:write name="iniciativa" property="iniciativaId" />')" alt="<vgcutil:message key="jsp.visualizariniciativasplan.columna.planificacionyseguimiento" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/planificacion.gif" />" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="anexos" align="center">
					<img onclick="gestionarAnexosIniciativa('<bean:write name="iniciativa" property="iniciativaId" />')" alt="<vgcutil:message key="jsp.visualizariniciativasplan.columna.explicaciones" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/explicaciones.gif" />" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
					<bean:write name="iniciativa" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="condicion" align="center">
					<vgcst:imagenHistoricoIniciativa name="iniciativa" property="condicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
					<bean:write name="iniciativa" property="estatus.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>	
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="anioformproy" align="center">
					<bean:write name="iniciativa" property="anioFormulacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>		
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableSeguimiento" align="left">
					<logic:notEmpty name="iniciativa" property="responsableSeguimiento">
						<bean:write name="iniciativa" property="responsableSeguimiento.nombreCargo" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta" align="left">
					<logic:notEmpty name="iniciativa" property="responsableLograrMeta">
						<bean:write name="iniciativa" property="responsableLograrMeta.nombreCargo" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableCargarEjecutado" align="left">
					<logic:notEmpty name="iniciativa" property="responsableCargarEjecutado">
						<bean:write name="iniciativa" property="responsableCargarEjecutado.nombreCargo" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista></td>
	</tr>
</table>


