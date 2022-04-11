<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/09/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..::  <vgcutil:message key="jsp.visualizarplan.titulo" /> [<bean:write name="visualizarPlanForm" scope="session" property="nombrePlan" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Representación de la Forma --%>
		<html:form action="/planes/visualizarPlan" styleClass="formaHtml">

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.visualizarplan.titulo" /> [<bean:write name="visualizarPlanForm" scope="session" property="nombrePlan" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]]</vgcinterfaz:contenedorFormaTitulo>
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/planes/visualizarPlan' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

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

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="200px"><vgcutil:message key="jsp.visualizarplan.organizacion" /></td>
							<td><b><bean:write name="organizacion" property="nombre" /></b></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.visualizarplan.plan" /></td>
							<td><b><bean:write name="visualizarPlanForm" property="nombrePlan" /></b></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.visualizarplan.tipovista" /></td>
							<td><html:select property="tipoVista" styleClass="cuadroTexto" onchange="document.visualizarPlanForm.submit();">
								<logic:equal name="visualizarPlanForm" property="mostrarTipoVistaDetallada" value="true">
									<html:option value="<%=tipoVistaDetallada%>">
										<bean:write name="visualizarPlanForm" property="nombreTipoVistaDetallada" />
									</html:option>
								</logic:equal>
								<logic:equal name="visualizarPlanForm" property="mostrarTipoVistaResumen" value="true">
									<html:option value="<%=tipoVistaResumen%>">
										<bean:write name="visualizarPlanForm" property="nombreTipoVistaResumen" />
									</html:option>
								</logic:equal>
								<%-- 
								<logic:equal name="visualizarPlanForm" property="mostrarTipoVistaEjecutiva" value="true">
									<html:option value="<%=tipoVistaEjecutiva%>">
										<bean:write name="visualizarPlanForm" property="nombreTipoVistaEjecutiva" />
									</html:option>
								</logic:equal>
								--%>
								<logic:equal name="visualizarPlanForm" property="mostrarTipoVistaArbol" value="true">
									<html:option value="<%=tipoVistaArbol%>">
										<bean:write name="visualizarPlanForm" property="nombreTipoVistaArbol" />
									</html:option>
								</logic:equal>
							</html:select></td>
							<td>&nbsp;</td>
						</tr>
						<logic:notEmpty name="visualizarPlanForm" property="anos">
							<tr class="barraFiltrosForma">
								<td><vgcutil:message key="jsp.visualizarplan.anoplan" /></td>
								<td><html:select property="ano" styleClass="cuadroTexto" onchange="document.visualizarPlanForm.submit();">
									<html:option value="0">------</html:option>
									<html:optionsCollection property="anos" value="clave" label="valor" />
								</html:select></td>
								<td>&nbsp;</td>
							</tr>
						</logic:notEmpty>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<logic:equal name="visualizarPlanForm" property="tipoVista" value="<%=tipoVistaDetallada%>">
					<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="0" border="0">
						<tr>
							<td><jsp:include page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasVistaResumenDetalladaEjecutiva.jsp"></jsp:include></td>
						</tr>
					</table>
				</logic:equal>
				<logic:equal name="visualizarPlanForm" property="tipoVista" value="<%=tipoVistaResumen%>">
					<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="0" border="0">
						<tr>
							<td><jsp:include page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasVistaResumenDetalladaEjecutiva.jsp"></jsp:include></td>
						</tr>
					</table>
				</logic:equal>
				<%-- 
				<logic:equal name="visualizarPlanForm" property="tipoVista" value="<%=tipoVistaEjecutiva%>">
					<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="0" border="0">
						<tr>
							<td><jsp:include page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasVistaResumenDetalladaEjecutiva.jsp"></jsp:include></td>
						</tr>
					</table>
				</logic:equal>
				 --%>
				<logic:equal name="visualizarPlanForm" property="tipoVista" value="<%=tipoVistaArbol%>">
					<table cellpadding="3" cellspacing="0" border="0">
						<tr>
							<td><jsp:include page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasVistaArbol.jsp"></jsp:include></td>
						</tr>
					</table>
				</logic:equal>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>