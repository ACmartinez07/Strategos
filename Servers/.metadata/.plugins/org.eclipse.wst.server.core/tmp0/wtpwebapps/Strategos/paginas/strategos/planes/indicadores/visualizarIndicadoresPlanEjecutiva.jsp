<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (25/09/2012) --%>

<%-- Visor Lista --%>
<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" nombre="visorIndicadoresPlan" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

	<%-- Encabezados del Visor Lista --%>
	<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.alerta" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="nombre" width="450px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.nombre" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="unidad" width="120px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.unidad" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" width="130px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.ultimoperiodomedicion" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="real" width="100px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.real" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="metaParcial" width="100px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.metaparcial" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="200px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.responsable.lograrmeta" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="responsableEjecutado" width="200px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.responsable.cargarejecutado" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="anexos" width="80px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.explicaciones" />
	</vgcinterfaz:columnaVisorLista>
	

	<%-- Filas del Visor Lista --%>
	<vgcinterfaz:filasVisorLista nombreObjeto="indicador">

		<vgcinterfaz:visorListaFilaId>
			<bean:write name="indicador" property="indicadorId" />
		</vgcinterfaz:visorListaFilaId>

		<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
			<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
			<a href="javascript:visualizarIndicador('<bean:write name="indicador" property="indicadorId" />')"><bean:write name="indicador" property="nombre" /></a>
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
			<logic:notEmpty name="indicador" property="unidad">
				<bean:write name="indicador" property="unidad.nombre" />
			</logic:notEmpty>&nbsp;
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
			<bean:write name="indicador" property="frecuenciaNombre" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
			<bean:write name="indicador" property="naturalezaNombre" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="center">
			<bean:write name="indicador" property="fechaUltimaMedicion" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="real" align="right">
			<bean:write name="indicador" property="ultimaMedicionFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaAnual" align="right">
			<bean:write name="indicador" property="metaAnualFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaParcial" align="right">
			<bean:write name="indicador" property="metaParcialFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta" align="left">
			<logic:notEmpty name="indicador" property="responsableLograrMeta">
				<bean:write name="indicador" property="responsableLograrMeta.cargo" />
			</logic:notEmpty>&nbsp;
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableEjecutado" align="left">
			<logic:notEmpty name="indicador" property="responsableCargarEjecutado">
				<bean:write name="indicador" property="responsableCargarEjecutado.cargo" />
			</logic:notEmpty>&nbsp;
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="anexos" align="center">
			<img onclick="gestionarAnexos('<bean:write name="indicador" property="indicadorId" />')" alt="<vgcutil:message key="jsp.visualizarindicadoresplan.columna.explicaciones" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/explicaciones.gif" />" />
		</vgcinterfaz:valorFilaColumnaVisorLista>

	</vgcinterfaz:filasVisorLista>
</vgcinterfaz:visorLista>



