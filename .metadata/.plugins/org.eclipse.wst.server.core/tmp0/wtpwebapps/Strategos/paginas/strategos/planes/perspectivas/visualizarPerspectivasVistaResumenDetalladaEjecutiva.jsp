<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/09/2012) --%>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
<script type="text/javascript">
	function verGraficoIndicador(indicadorId, perspectivaId, nivel) 
	{
		var grafico = new Grafico();
		grafico.url = '<html:rewrite action="/graficos/grafico"/>';
		grafico.ShowForm(true, indicadorId, 'Indicador', undefined, undefined, '<bean:write name="visualizarPlanForm" property="planId" />');
	}

	function verDupontIndicador(indicadorId) 
	{
		var duppont = new Duppont();
		duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
		duppont.ShowForm(true, indicadorId, undefined, undefined);
	}

	function visualizarIndicador(indicadorId) 
	{
		abrirVentanaModal('<html:rewrite action="/indicadores/visualizarIndicador"/>?indicadorId=' + indicadorId, "Indicador", 640, 510);
	}

	function visualizarIniciativa(iniciativaId, perspectivaId, nivel) 
	{
		abrirVentanaModal('<html:rewrite action="/iniciativas/visualizarIniciativa"/>?planId=<bean:write name="visualizarPlanForm" property="planId" />&perspectivaId=' + perspectivaId + '&iniciativaId=' + iniciativaId, "Iniciativa", 640, 410);
	}

	function gestionarAnexos(indicadorId) 
	{
		var explicacion = new Explicacion();
		explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
		explicacion.ShowList(true, indicadorId, 'Indicador', 0);
	}
</script>

<bean:define id="tipoVistaDetallada">
	<bean:write name="visualizarPlanForm" property="tipoVistaDetallada" />
</bean:define>
<bean:define id="tipoVistaResumen">
	<bean:write name="visualizarPlanForm" property="tipoVistaResumen" />
</bean:define>
<bean:define id="tipoVistaEjecutiva">
	<bean:write name="visualizarPlanForm" property="tipoVistaEjecutiva" />
</bean:define>
<bean:define id="tipoVistaArbol">
	<bean:write name="visualizarPlanForm" property="tipoVistaArbol" />
</bean:define>
<table id="tblPrincipal" name="tblPrincipal" border="0" class="bordeFichaDatos">
	<logic:iterate name="listaPerspectivas" id="perspectiva">
		<tr>
			<bean:define id="maximoNivel">
				<bean:write name="maximoNivelPerspectiva" />
			</bean:define>
			<bean:define id="nivel">
				<bean:write name="perspectiva" property="nivel" />
			</bean:define>
			<%
				int mn = Integer.parseInt(maximoNivel);
					int n = Integer.parseInt(nivel);
					String identacion = "";
					pageContext.setAttribute("colspanPerspectiva", new Integer((mn - n) + 1));
					for (int i = 1; i < n; i++) 
						identacion = identacion + "<td width=\"50\">&nbsp</td>";
			%>
			<%=identacion%>
			<td colspan="<bean:write name="colspanPerspectiva"/>">
				<logic:notEmpty name="perspectiva" property="configuracionPlan">
					<logic:equal name="perspectiva" property="configuracionPlan.planObjetivoAlertaParcialMostrar" value="true">
						<img src="<html:rewrite page="/paginas/strategos/planes/imagenes/gestionarPerspectivas"/>/imgNodo<bean:write name="perspectiva" property="imagenAlertaParcial" />.gif" title="<bean:write name="perspectiva" property="toolTipImagenAlertaParcial" />">
					</logic:equal>
					<logic:equal name="perspectiva" property="configuracionPlan.planObjetivoAlertaAnualMostrar" value="true">
						<img src="<html:rewrite page="/paginas/strategos/planes/imagenes/gestionarPerspectivas"/>/imgNodo<bean:write name="perspectiva" property="imagenAlertaAnual" />.gif" title="<bean:write name="perspectiva" property="toolTipImagenAlertaAnual" />">
					</logic:equal>
				</logic:notEmpty>
				<logic:empty name="perspectiva" property="configuracionPlan">
					<img src="<html:rewrite page="/paginas/strategos/planes/imagenes/gestionarPerspectivas"/>/imgNodo<bean:write name="perspectiva" property="imagenAlertaParcial" />.gif" title="<bean:write name="perspectiva" property="toolTipImagenAlertaParcial" />">
				</logic:empty>
				&nbsp;&nbsp;
				<logic:greaterThan name="perspectiva" property="nivel" value="1">
					<a href="<html:rewrite action="/planes/perspectivas/visualizarPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" />&mostrarObjetosAsociados=true&nivel=<bean:write name="perspectiva" property="nivel" />"><bean:write name="perspectiva" property="nombre" /></a>
				</logic:greaterThan>
				<logic:equal name="perspectiva" property="nivel" value="1">
					<bean:write name="perspectiva" property="nombre" />
				</logic:equal>
			</td>
		</tr>

		<bean:define id="visualizarPerspectiva_perspectivaId" toScope="request">
			<bean:write name="perspectiva" property="perspectivaId" />
		</bean:define>
		<bean:define id="visualizarPerspectiva_nivel" toScope="request">
			<bean:write name="perspectiva" property="nivel" />
		</bean:define>
		<logic:notEmpty name="perspectiva" property="listaIndicadores">
			<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="listaIndicadores" value="0">
				<tr>
					<vgcutil:crearPaginaLista name="perspectiva" property="listaIndicadores" nombre="paginaIndicadores" toScope="request" />
					<%=identacion%>
					<td colspan="<bean:write name="colspanPerspectiva"/>">
					<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">
						<tr>
							<td><b><bean:write name="visualizarPlanForm" property="plantillaPlan.nombreIndicadorPlural" /></b></td>
						</tr>
						<tr>
							<td>
								<logic:equal name="visualizarPlanForm" property="tipoVista" value="1">
									<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlan.jsp"></jsp:include>
								</logic:equal>
								<logic:equal name="visualizarPlanForm" property="tipoVista" value="3">
									<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlanEjecutiva.jsp"></jsp:include>
								</logic:equal>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</vgclogica:tamanoColeccionMayorQue>
		</logic:notEmpty>
		<logic:notEmpty name="perspectiva" property="listaIndicadoresGuia">
			<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="listaIndicadoresGuia" value="0">
				<tr>
					<vgcutil:crearPaginaLista name="perspectiva" property="listaIndicadoresGuia" nombre="paginaIndicadores" toScope="request" />
					<%=identacion%>
					<td colspan="<bean:write name="colspanPerspectiva"/>">
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">
							<tr>
								<td><b><bean:write name="visualizarPlanForm" property="plantillaPlan.nombreIndicadorPlural" />&nbsp;<vgcutil:message key="jsp.visualizarindicadoresplan.guia" /></b></td>
							</tr>
							<tr>
								<td>
									<logic:equal name="visualizarPlanForm" property="tipoVista" value="1">
										<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlan.jsp"></jsp:include>
									</logic:equal>
									<logic:equal name="visualizarPlanForm" property="tipoVista" value="3">
										<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlanEjecutiva.jsp"></jsp:include>
									</logic:equal>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</vgclogica:tamanoColeccionMayorQue>
		</logic:notEmpty>
		<logic:notEmpty name="perspectiva" property="listaIniciativas">
			<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="listaIniciativas" value="0">
				<vgcutil:crearPaginaLista name="perspectiva" property="listaIniciativas" nombre="paginaIniciativas" toScope="request" />
				<tr>
					<%=identacion%>
					<td colspan="<bean:write name="colspanPerspectiva"/>"><jsp:include page="/paginas/strategos/planes/iniciativas/visualizarIniciativasPlan.jsp"></jsp:include></td>
				</tr>
			</vgclogica:tamanoColeccionMayorQue>
		</logic:notEmpty>
	</logic:iterate>
</table>
<script type="text/javascript">
	var objeto = document.getElementById('tblPrincipal');
	if (objeto != null)
	{
		objeto.width = (parseInt(_myWidth) - 145) + "px";
		objeto.height = (parseInt(_myHeight) - 240) + "px";
	}
</script>
