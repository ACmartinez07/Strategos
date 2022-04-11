<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/09/2012) --%>

<bean:define id="valorNaturalezaFormula" value="0" ></bean:define>
<logic:notEmpty name="visualizarPerspectivaForm">
	<bean:define id="valorNaturalezaFormula">
		<bean:write name="visualizarPerspectivaForm" property="indicadorNaturalezaFormula" />
	</bean:define>
</logic:notEmpty>
<logic:notEmpty name="visualizarPerspectivasForm">
	<bean:define id="valorNaturalezaFormula">
		<bean:write name="visualizarPerspectivasForm" property="indicadorNaturalezaFormula" />
	</bean:define>
</logic:notEmpty>
<%-- 
<logic:notEmpty name="visualizarPlanForm">
	<bean:define id="valorNaturalezaFormula">
		<bean:write name="visualizarPlanForm" property="indicadorNaturalezaFormula" />
	</bean:define>
</logic:notEmpty>
--%>

<%-- Visor Lista --%>
<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" nombre="visorIndicadoresPlan" messageKeyNoElementos="jsp.visualizarindicadoresplan.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

	<%-- Encabezados del Visor Lista --%>
	<vgcinterfaz:columnaVisorLista nombre="alerta" width="50px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.alerta" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="grafico" width="50px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.grafico" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="arbol" width="50px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.arbol" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="anexos" width="50px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.explicaciones" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="nombre" width="450px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.nombre" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="real" width="120px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.real" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" width="160px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.ultimoperiodomedicion" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="metaAnual" width="120px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.metaanual" />
	</vgcinterfaz:columnaVisorLista>
	<logic:notEmpty scope="session" name="configuracionPlan">
		<logic:equal scope="session" name="configuracionPlan" property="planObjetivoLogroAnualMostrar" value="true">
			<vgcinterfaz:columnaVisorLista nombre="estadoAnual" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.estadoanual" />
			</vgcinterfaz:columnaVisorLista>
		</logic:equal>
	</logic:notEmpty>
	<vgcinterfaz:columnaVisorLista nombre="metaParcial" width="120px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.metaparcial" />
	</vgcinterfaz:columnaVisorLista>
	<logic:notEmpty scope="session" name="configuracionPlan">
		<logic:equal scope="session" name="configuracionPlan" property="planObjetivoLogroParcialMostrar" value="true">
			<vgcinterfaz:columnaVisorLista nombre="estadoParcial" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.estadoparcial" />
			</vgcinterfaz:columnaVisorLista>
		</logic:equal>
	</logic:notEmpty>
	<vgcinterfaz:columnaVisorLista nombre="unidad" width="120px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.unidad" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="100px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.frecuencia" />
	</vgcinterfaz:columnaVisorLista>
	<vgcinterfaz:columnaVisorLista nombre="naturaleza" width="100px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.naturaleza" />
	</vgcinterfaz:columnaVisorLista>
	<%-- 
	<vgcinterfaz:columnaVisorLista nombre="responsableEjecutado" width="200px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.responsable.cargarejecutado" />
	</vgcinterfaz:columnaVisorLista>
	--%>
	<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="200px">
		<vgcutil:message key="jsp.visualizarindicadoresplan.columna.responsable.lograrmeta" />
	</vgcinterfaz:columnaVisorLista>

	<%-- Filas del Visor Lista --%>
	<vgcinterfaz:filasVisorLista nombreObjeto="indicador">

		<vgcinterfaz:visorListaFilaId>
			<bean:write name="indicador" property="indicadorId" />
		</vgcinterfaz:visorListaFilaId>

		<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
			<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="grafico" align="center">
			<img onclick="verGraficoIndicador('<bean:write name="indicador" property="indicadorId" />', '<bean:write name="visualizarPerspectiva_perspectivaId" scope="request" />', '<bean:write name="visualizarPerspectiva_nivel" scope="request" />')" alt="<vgcutil:message key="jsp.visualizarindicadoresplan.columna.grafico" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/grafico.gif" />" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="arbol" align="center">
			<logic:equal name="indicador" property="naturaleza" value="<%= valorNaturalezaFormula %>">
				<img onclick="verDupontIndicador('<bean:write name="indicador" property="indicadorId" />')" alt="<vgcutil:message key="jsp.visualizarindicadoresplan.columna.arbol" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/dupont.gif" />" />
			</logic:equal>
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="anexos" align="center">
			<img onclick="gestionarAnexos('<bean:write name="indicador" property="indicadorId" />')" alt="<vgcutil:message key="jsp.visualizarindicadoresplan.columna.explicaciones" />" src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/explicaciones.gif" />" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
			<a href="javascript:visualizarIndicador('<bean:write name="indicador" property="indicadorId" />')"><bean:write name="indicador" property="nombre" /></a>
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="real" align="right">
			<bean:write name="indicador" property="ultimaMedicionFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="center">
			<bean:write name="indicador" property="fechaUltimaMedicion" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaAnual" align="right">
			<bean:write name="indicador" property="metaAnualFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="estadoAnual" align="right">
			<bean:write name="indicador" property="estadoAnualFormateado" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaParcial" align="right">
			<bean:write name="indicador" property="metaParcialFormateada" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="estadoParcial" align="right">
			<bean:write name="indicador" property="estadoParcialFormateado" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
			<logic:notEmpty name="indicador" property="unidad">
				<bean:write name="indicador" property="unidad.nombre" />
			</logic:notEmpty>
			<logic:empty name="indicador" property="unidad">&nbsp;
					</logic:empty>
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
			<bean:write name="indicador" property="frecuenciaNombre" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
			<bean:write name="indicador" property="naturalezaNombre" />
		</vgcinterfaz:valorFilaColumnaVisorLista>
		<%--
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableEjecutado" align="left">
			<logic:notEmpty name="indicador" property="responsableCargarEjecutado">
				<bean:write name="indicador" property="responsableCargarEjecutado.cargo" />
			</logic:notEmpty>&nbsp;
		</vgcinterfaz:valorFilaColumnaVisorLista>
		--%>
		<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta" align="left">
			<logic:notEmpty name="indicador" property="responsableLograrMeta">
				<bean:write name="indicador" property="responsableLograrMeta.cargo" />
			</logic:notEmpty>&nbsp;
		</vgcinterfaz:valorFilaColumnaVisorLista>

	</vgcinterfaz:filasVisorLista>
</vgcinterfaz:visorLista>
