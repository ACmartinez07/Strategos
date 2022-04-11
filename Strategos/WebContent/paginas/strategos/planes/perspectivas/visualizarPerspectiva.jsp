<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<bean:write name="visualizarPerspectivaForm" property="nombreObjetoPerspectiva" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
		<script type="text/javascript">
			function verGraficoIndicador(indicadorId, perspectivaId, nivel) 
			{
				var grafico = new Grafico();
				grafico.url = '<html:rewrite action="/graficos/grafico"/>';
				grafico.ShowForm(true, indicadorId, 'Indicador', undefined, undefined, '<bean:write name="visualizarPerspectivaForm" property="planId" />');
			}
		
			function visualizarIndicador(indicadorId) 
			{
				abrirVentanaModal('<html:rewrite action="/indicadores/visualizarIndicador"/>?indicadorId=' + indicadorId, "Indicador", 640, 510);
			}
		
			function visualizarIniciativa(iniciativaId, perspectivaId, nivel) 
			{
				abrirVentanaModal('<html:rewrite action="/iniciativas/visualizarIniciativa"/>?planId=<bean:write name="visualizarPerspectivaForm" property="planId" />&perspectivaId=' + perspectivaId + '&iniciativaId=' + iniciativaId, "Iniciativa", 640, 410);
			}
			
			function verDupontIndicador(indicadorId) 
			{
				var duppont = new Duppont();
				duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
				duppont.ShowForm(true, indicadorId, undefined, undefined);
			}
		
			function gestionarAnexos(indicadorId) 
			{
				var explicacion = new Explicacion();
				explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
				explicacion.ShowList(true, indicadorId, 'Indicador', 0);
			}
		
			function editarMedicionesIndicadores() 
			{
				if (verificarSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados))
				{
			    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
					var funcionCierre = '&funcionCierre=' + 'onEditarMediciones()';
					var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
					var url = '?planId=<bean:write name="visualizarPerspectivaForm" property="planId" />&perspectivaId=<bean:write name="visualizarPerspectivaForm" property="perspectivaId" />&plantillaPlanesId=<bean:write name="visualizarPerspectivaForm" property="plantillaPlan.plantillaPlanesId" />&nivel=<bean:write name="visualizarPerspectivaForm" property="nivel" />' + "&source=1";
					var indicadoresIds = '<bean:write name="visualizarPerspectivaForm" property="indicadoresIds" ignore="true" />';
					if (indicadoresIds != '') 
						url = url + '&indicadorId=' + indicadoresIds;

					abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMediciones', '410', '510');
				}
			}
		
			function onEditarMediciones()
			{
				var url = '?planId=<bean:write name="visualizarPerspectivaForm" property="planId" />&perspectivaId=<bean:write name="visualizarPerspectivaForm" property="perspectivaId" />&plantillaPlanesId=<bean:write name="visualizarPerspectivaForm" property="plantillaPlan.plantillaPlanesId" />&nivel=<bean:write name="visualizarPerspectivaForm" property="nivel" />' + "&source=1" + "&funcion=Ejecutar";
				var indicadoresIds = '<bean:write name="visualizarPerspectivaForm" property="indicadoresIds" ignore="true" />';
				if (indicadoresIds != '') 
					url = url + '&indicadorId=' + indicadoresIds;
		    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + url;
			}
			
			function editarMetasIndicadores() 
			{
				if (verificarSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
				{
			    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
					var funcionCierre = '&funcionCierre=' + 'onEditarMetas()';
					var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
					var url = '?planId=<bean:write name="visualizarPerspectivaForm" property="planId" />&perspectivaId=<bean:write name="visualizarPerspectivaForm" property="perspectivaId" />';
					var queryString = '<bean:write name="visualizarPerspectivaForm" property="indicadoresIds" ignore="true" />';
					if (queryString != '') 
						url = url + '&indicadorId=' + queryString;
		
					abrirVentanaModal('<html:rewrite action="/planes/metas/configurarEdicionMetas" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMetas', '410', '510');
				}
			}
			
			function onEditarMetas()
			{
				
			}
			
			function cancelar()
			{
				if (document.visualizarPerspectivaForm.vinculoCausaEfecto.value)
					irAtras(2);
				else
					irAtras();
			}
			
		</script>

		<bean:define id="editarMediciones" value="false"></bean:define>
		<bean:define id="mostrarObjetosAsociados">
			<bean:write name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" />
		</bean:define>

		<bean:define id="visualizarPerspectiva_perspectivaId" toScope="request">
			<bean:write name="visualizarPerspectivaForm" property="perspectivaId" />
		</bean:define>
		<bean:define id="visualizarPerspectiva_nivel" toScope="request">
			<bean:write name="visualizarPerspectivaForm" property="nivel" />
		</bean:define>

		<logic:equal name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" value="true">
			<logic:notEmpty name="perspectiva" property="listaIndicadores">
				<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="listaIndicadores" value="0">
					<bean:define id="editarMediciones" value="true"></bean:define>
				</vgclogica:tamanoColeccionMayorQue>
			</logic:notEmpty>
			<logic:notEmpty name="perspectiva" property="listaIndicadoresGuia">
				<vgclogica:tamanoColeccionMayorQue name="perspectiva" property="listaIndicadoresGuia" value="0">
					<bean:define id="editarMediciones" value="true"></bean:define>
				</vgclogica:tamanoColeccionMayorQue>
			</logic:notEmpty>
		</logic:equal>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/perspectivas/visualizarPerspectiva">
			<html:hidden property="respuesta" />
			<html:hidden property="vinculoCausaEfecto" />
			<bean:define id="anchoVentana">
				<logic:notEmpty name="com.visiongc.app.web.navegadorinfo">
					<bean:write name="com.visiongc.app.web.navegadorinfo" property="pantallaAncho" />
				</logic:notEmpty>
				<logic:empty name="com.visiongc.app.web.navegadorinfo">
					100%
				</logic:empty>
			</bean:define>
			<bean:define id="altoVentana">
				<logic:notEmpty name="com.visiongc.app.web.navegadorinfo">
					<bean:write name="com.visiongc.app.web.navegadorinfo" property="pantallaAlto" />
				</logic:notEmpty>
				<logic:empty name="com.visiongc.app.web.navegadorinfo">
					100%
				</logic:empty>
			</bean:define>
			<%
				if (mostrarObjetosAsociados.equals("true"))
				{
					anchoVentana = "99%";
					altoVentana = "99%";
				}
			%>
			<vgcinterfaz:contenedorForma idContenedor="visualizar-Perspectiva" width="<%=anchoVentana%>" height="<%=altoVentana%>" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <bean:write name="visualizarPerspectivaForm" property="nombreObjetoPerspectiva" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">
					<tr>
						<td align="right" valign="top"><vgcutil:message key="jsp.visualizarperspectiva.organizacion" /></td>
						<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="organizacion" property="nombre" />"></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" valign="top"><vgcutil:message key="jsp.visualizarperspectiva.plan" /></td>
						<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="visualizarPerspectivaForm" property="plan.nombre" />"></td>
						<td>&nbsp;</td>
					</tr>
					<logic:equal name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" value="true">
						<tr>
							<td align="right" valign="top"><bean:write name="visualizarPerspectivaForm" property="nombreObjetoPerspectiva" /></td>
							<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="visualizarPerspectivaForm" property="nombre" />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" valign="top" align="left">
							<table class="contenedorBotonesSeleccion" width="420px">
								<tr>
									<td align="right"><img border="0" src="<html:rewrite page='/paginas/strategos/planes/perspectivas/imagenes/flecha.gif'/>"> <a href="<html:rewrite action="/planes/perspectivas/visualizarPerspectiva"/>?perspectivaId=<bean:write name="visualizarPerspectivaForm" property="perspectivaId" />&mostrarObjetosAsociados=false&nivel=<bean:write name="visualizarPerspectivaForm" property="nivel" />"><vgcutil:message key="boton.detalle.alt" /></a></td>
								</tr>
							</table>
							</td>
							<td>&nbsp;</td>
						</tr>
					</logic:equal>
					<logic:equal name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" value="false">
						<tr>
							<td align="right" valign="top"><bean:write name="visualizarPerspectivaForm" property="nombreObjetoPerspectiva" /></td>
							<td><input type="text" class="cuadroTexto" readonly="true" size="50" value="<bean:write name="visualizarPerspectivaForm" property="nombre" />"></td>
							<td>&nbsp;</td>
						</tr>
					</logic:equal>
					<logic:equal name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" value="false">
						<tr>
							<td align="right" valign="middle"><vgcutil:message key="jsp.visualizarperspectiva.descripcion" /></td>
							<td><textarea class="cuadroTexto" readonly="true" rows="5" cols="47"><bean:write name="visualizarPerspectivaForm" property="descripcion" /></textarea></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" valign="top"><vgcutil:message key="jsp.visualizarperspectiva.frecuenciaevaluacion" /></td>
							<td><input type="text" class="cuadroTexto" readonly="true" size="35" value="<bean:write name="visualizarPerspectivaForm" property="nombreFrecuencia" />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" valign="top"><vgcutil:message key="jsp.visualizarperspectiva.responsable" /></td>
							<td><input type="text" class="cuadroTexto" readonly="true" size="35" value="<bean:write name="visualizarPerspectivaForm" property="nombreResponsable" />"></td>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<td colspan="3">
							<hr width="100%">
							</td>
						</tr>

						<tr>
							<td colspan="3">
							<table class="bordeFichaDatos">
								<tr>
									<td><b>Estados</b></td>
								</tr>
								<tr>
									<td>
									<table class="listView" cellpadding="3" cellspacing="0" align="center" height="100%" border="1">
										<tr class="encabezadoListView" height="20px">
											<td align="center" width="200px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.visualizarperspectiva.anoevaluacion" /></td>
											<td align="center" width="200px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.visualizarperspectiva.periodoevaluacion" /></td>
											<td align="center" width="200px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.visualizarperspectiva.tipoestado" /></td>
											<td align="center" width="100px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.visualizarperspectiva.estado" /></td>
										</tr>
										<logic:iterate name="perspectivaEstados" id="perspectivaEstado">
											<tr class="mouseFueraCuerpoListView" valign="top">
												<td align="right"><bean:write name="perspectivaEstado" property="pk.ano" /></td>
												<td align="right"><bean:write name="perspectivaEstado" property="pk.periodo" /></td>
												<td><bean:write name="perspectivaEstado" property="pk.tipoNombre" /></td>
												<td align="right"><bean:write name="perspectivaEstado" property="estadoFormateado" />&nbsp;</td>
											</tr>
										</logic:iterate>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</logic:equal>
					<logic:equal name="visualizarPerspectivaForm" property="mostrarObjetosAsociados" value="true">
						<vgcutil:crearPaginaLista name="perspectiva" property="listaIndicadores" nombre="paginaIndicadores" toScope="request" />
						<tr>
							<td colspan="3"><b><bean:write name="visualizarPerspectivaForm" property="plantillaPlan.nombreIndicadorPlural" /></b></td>
						</tr>
						<tr>
							<td colspan="3"><logic:equal name="visualizarPerspectivaForm" property="tipoVista" value="1">
								<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlan.jsp"></jsp:include>
							</logic:equal><logic:equal name="visualizarPerspectivaForm" property="tipoVista" value="3">
								<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlanEjecutiva.jsp"></jsp:include>
							</logic:equal></td>
						</tr>
						<vgcutil:crearPaginaLista name="perspectiva" property="listaIndicadoresGuia" nombre="paginaIndicadores" toScope="request" />
						<tr>
							<td colspan="3"><b><bean:write name="visualizarPerspectivaForm" property="plantillaPlan.nombreIndicadorPlural" />&nbsp;<vgcutil:message key="jsp.visualizarperspectiva.guia" /></b></td>
						</tr>
						<tr>
							<td colspan="3"><logic:equal name="visualizarPerspectivaForm" property="tipoVista" value="1">
								<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlan.jsp"></jsp:include>
							</logic:equal><logic:equal name="visualizarPerspectivaForm" property="tipoVista" value="3">
								<jsp:include page="/paginas/strategos/planes/indicadores/visualizarIndicadoresPlanEjecutiva.jsp"></jsp:include>
							</logic:equal></td>
						</tr>
						<vgcutil:crearPaginaLista name="perspectiva" property="listaIniciativas" nombre="paginaIniciativas" toScope="request" />
						<tr>
							<td colspan="3" align="left"><jsp:include page="/paginas/strategos/planes/iniciativas/visualizarIniciativasPlan.jsp"></jsp:include></td>
						</tr>
						<logic:notEmpty name="perspectiva" property="listaHijos">
							<vgcutil:crearPaginaLista name="perspectiva" property="listaHijos" nombre="paginaPerspectivaHijas" toScope="request" />
							<tr>
								<td colspan="3" align="left">
									<jsp:include page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasHijas.jsp"></jsp:include>
								</td>
							</tr>
						</logic:notEmpty>
					</logic:equal>
				</table>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('visualizar-Perspectiva'), 80);
		</script>
	</tiles:put>
</tiles:insert>