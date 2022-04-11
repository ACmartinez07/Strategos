<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%-- Modificado por: Kerwin Arias (09/01/2012) --%>
<%-- Modificado por: Andres Caucali (13/10/2019) --%>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/componentes/estilos/botones.css" />" media="screen"/>
<script type="text/javascript">
	var NO_MARCADO = 1;
	var MARCADO = 2;
</script>
<%-- Filtro --%>
<table id="tblFiltro" class="tablaSpacing0Padding0Width100Collapse" style="display: none; padding: 1px; width: 420px;">
	<tr class="barraFiltrosForma">
		<td style="width: 185px;">
			<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
				<%-- Historico --%>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 105px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.condicion.titulo" /></td>
					<td style="width: 80px;">
						<bean:define id="filtroHistoricoValue">
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasForm" property="filtro.historico">
									<bean:write name="gestionarIniciativasForm" property="filtro.historico" />
								</logic:notEmpty>
								<logic:empty name="gestionarIniciativasForm" property="filtro.historico">
									0
								</logic:empty>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:notEmpty name="gestionarIniciativasPlanForm" property="filtro.historico">
										<bean:write name="gestionarIniciativasPlanForm" property="filtro.historico" />
									</logic:notEmpty>
									<logic:empty name="gestionarIniciativasPlanForm" property="filtro.historico">
										0
									</logic:empty>
								</logic:notEmpty>
							</logic:empty>
						</bean:define>
						<select class="cuadroCombinado" name="selectHitoricoType" id="selectHitoricoType">
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:equal name='gestionarIniciativasForm' property='filtro.incluirTodos' value='true'>
									<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
								</logic:equal>
								<logic:iterate name="gestionarIniciativasForm" property="filtro.tiposHistoricos" id="filtroHistoricoType">
									<bean:define id="tipo" toScope="page" name='filtroHistoricoType' property='filtroHistoricoTypeId' type="Byte"></bean:define>
									<bean:define id="nombre" toScope="page"><bean:write name='filtroHistoricoType' property='nombre' /></bean:define>
									<logic:equal name='filtroHistoricoType' property='filtroHistoricoTypeId' value='<%=filtroHistoricoValue%>'>
										<option value="<%=tipo%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='filtroHistoricoType' property='filtroHistoricoTypeId' value='<%=filtroHistoricoValue.toString()%>'>
										<option value="<%=tipo%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:equal name='gestionarIniciativasPlanForm' property='filtro.incluirTodos' value='true'>
										<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
									</logic:equal>
									<logic:iterate name="gestionarIniciativasPlanForm" property="filtro.tiposHistoricos" id="filtroHistoricoType">
										<bean:define id="tipo" toScope="page" name='filtroHistoricoType' property='filtroHistoricoTypeId' type="Byte"></bean:define>
										<bean:define id="nombre" toScope="page"><bean:write name='filtroHistoricoType' property='nombre' /></bean:define>
										<logic:equal name='filtroHistoricoType' property='filtroHistoricoTypeId' value='<%=filtroHistoricoValue%>'>
											<option value="<%=tipo%>" selected><%=nombre%></option>
										</logic:equal>
										<logic:notEqual name='filtroHistoricoType' property='filtroHistoricoTypeId' value='<%=filtroHistoricoValue.toString()%>'>
											<option value="<%=tipo%>"><%=nombre%></option>
										</logic:notEqual>
									</logic:iterate>
								</logic:notEmpty>
							</logic:empty>
						</select>
					</td>
				</tr>
				<%-- Status --%>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 105px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.estatus.titulo" /></td>
					<td style="width: 80px;">
						<bean:define id="filtroEstatusValue">
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasForm" property="estatus">
									<bean:write name="gestionarIniciativasForm" property="estatus" />
								</logic:notEmpty>
								<logic:empty name="gestionarIniciativasForm" property="estatus">
									0
								</logic:empty>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:notEmpty name="gestionarIniciativasPlanForm" property="estatus">
										<bean:write name="gestionarIniciativasPlanForm" property="estatus" />
									</logic:notEmpty>
									<logic:empty name="gestionarIniciativasPlanForm" property="estatus">
										0
									</logic:empty>
								</logic:notEmpty>
							</logic:empty>
						</bean:define>
						<select class="cuadroCombinado" name="selectEstatusType" id="selectEstatusType">
							<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:iterate name="gestionarIniciativasForm" property="tiposEstatus" id="filtroEstatusType">
									<bean:define id="id" toScope="page" name='filtroEstatusType' property='id' type="Long"></bean:define>
									<bean:define id="nombre" toScope="page"><bean:write name='filtroEstatusType' property='nombre' /></bean:define>
									<logic:equal name='filtroEstatusType' property='id' value='<%=filtroEstatusValue%>'>
										<option value="<%=id%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='filtroEstatusType' property='id' value='<%=filtroEstatusValue.toString()%>'>
										<option value="<%=id%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:iterate name="gestionarIniciativasPlanForm" property="tiposEstatus" id="filtroEstatusType">
										<bean:define id="id" toScope="page" name='filtroEstatusType' property='id' type="Long"></bean:define>
										<bean:define id="nombre" toScope="page"><bean:write name='filtroEstatusType' property='nombre' /></bean:define>
										<logic:equal name='filtroEstatusType' property='id' value='<%=filtroEstatusValue%>'>
											<option value="<%=id%>" selected><%=nombre%></option>
										</logic:equal>
										<logic:notEqual name='filtroEstatusType' property='id' value='<%=filtroEstatusValue.toString()%>'>
											<option value="<%=id%>"><%=nombre%></option>
										</logic:notEqual>
									</logic:iterate>
								</logic:notEmpty>
							</logic:empty>
						</select>
					</td>
				</tr>
				<%-- Nombre --%>
				
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 105px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.nombre" /></td>
					<td style="width: 80px;">
						<logic:notEmpty name="gestionarIniciativasForm">
							<logic:notEmpty name="gestionarIniciativasForm" property="filtro.nombre">
								<input size="30" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="gestionarIniciativasForm" property="filtro.nombre" />">
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm" property="filtro.nombre">
								<input size="30" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="">									
							</logic:empty>
						</logic:notEmpty>
						<logic:empty name="gestionarIniciativasForm">
							<logic:notEmpty name="gestionarIniciativasPlanForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm" property="filtro.nombre">
									<input size="30" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="gestionarIniciativasPlanForm" property="filtro.nombre" />">
								</logic:notEmpty>
								<logic:empty name="gestionarIniciativasPlanForm" property="filtro.nombre">
									<input size="30" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="">									
								</logic:empty>
							</logic:notEmpty>
						</logic:empty>
					</td>
				</tr>
								
		
		
	
				<%-- Año formulación --%>
		
		
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 50px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.anio" /></td>
					<td style="width: 80px;">
						<logic:notEmpty name="gestionarIniciativasForm">
							<logic:notEmpty name="gestionarIniciativasForm" property="filtro.anio">
								<input size="5" type="text" id="filtroAnio" name="filtroAnio" class="cuadroTexto" value="<bean:write name="gestionarIniciativasForm" property="filtro.anio"/>">
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm" property="filtro.anio">
								<input size="5" type="text" id="filtroAnio" name="filtroAnio" class="cuadroTexto" value="">									
							</logic:empty>
						</logic:notEmpty>
						<logic:empty name="gestionarIniciativasForm">
							<logic:notEmpty name="gestionarIniciativasPlanForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm" property="filtro.anio">
									<input size="5" type="text" id="filtroAnio" name="filtroAnio" class="cuadroTexto" value="<bean:write name="gestionarIniciativasPlanForm" property="filtro.anio"/>">
								</logic:notEmpty>
								<logic:empty name="gestionarIniciativasPlanForm" property="filtro.anio">
									<input size="5" type="text" id="filtroAnio" name="filtroAnio" class="cuadroTexto" value="">									
								</logic:empty>
							</logic:notEmpty>
						</logic:empty>
					</td>
				</tr>
				
				<%-- Tipo Proyecto --%>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 105px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.tipo.proyecto.titulo" /></td>
					<td style="width: 80px;">
						<bean:define id="filtroTiposValue">
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasForm" property="tipo">
									<bean:write name="gestionarIniciativasForm" property="tipo" />
								</logic:notEmpty>
								<logic:empty name="gestionarIniciativasForm" property="tipo">
									0
								</logic:empty>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:notEmpty name="gestionarIniciativasPlanForm" property="tipo">
										<bean:write name="gestionarIniciativasPlanForm" property="tipo" />
									</logic:notEmpty>
									<logic:empty name="gestionarIniciativasPlanForm" property="tipo">
										0
									</logic:empty>
								</logic:notEmpty>
							</logic:empty>
						</bean:define>
						<select class="cuadroCombinado" name="selectTipos" id="selectTipos">
							<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
							<logic:notEmpty name="gestionarIniciativasForm">
								<logic:iterate name="gestionarIniciativasForm" property="tipos" id="filtrosType">
									<bean:define id="tipoProyectoId" toScope="page" name='filtrosType' property='tipoProyectoId' type="Long"></bean:define>
									<bean:define id="nombre" toScope="page"><bean:write name='filtrosType' property='nombre' /></bean:define>
									<logic:equal name='filtrosType' property='tipoProyectoId' value='<%=filtroTiposValue%>'>
										<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='filtrosType' property='tipoProyectoId' value='<%=filtroTiposValue.toString()%>'>
										<option value="<%=tipoProyectoId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="gestionarIniciativasForm">
								<logic:notEmpty name="gestionarIniciativasPlanForm">
									<logic:iterate name="gestionarIniciativasPlanForm" property="tipos" id="filtrosType">
										<bean:define id="tipoProyectoId" toScope="page" name='filtrosType' property='tipoProyectoId' type="Long"></bean:define>
										<bean:define id="nombre" toScope="page"><bean:write name='filtrosType' property='nombre' /></bean:define>
										<logic:equal name='filtrosType' property='tipoProyectoId' value='<%=filtroTiposValue%>'>
											<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
										</logic:equal>
										<logic:notEqual name='filtrosType' property='tipoProyectoId' value='<%=filtroTiposValue.toString()%>'>
											<option value="<%=tipoProyectoId%>"><%=nombre%></option>
										</logic:notEqual>
									</logic:iterate>
								</logic:notEmpty>
							</logic:empty>
						</select>
					</td>
				</tr>
			
				
				
			</table>					
		</td>
		
	
		<%-- Botones --%>
		<td style="width: 50px;">
			<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td colspan="2" style="width: 30px;">
						<a class="boton" title="<vgcutil:message key="boton.buscar.alt" />" onclick="refrescar()"><vgcutil:message key="boton.buscar.alt" /></a>
					</td>
				</tr>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td colspan="2" style="width: 30px;">
						<a class="boton" title="<vgcutil:message key="boton.limpiar.alt" />" onclick="limpiarFiltros()"><vgcutil:message key="boton.limpiar.alt" /></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
