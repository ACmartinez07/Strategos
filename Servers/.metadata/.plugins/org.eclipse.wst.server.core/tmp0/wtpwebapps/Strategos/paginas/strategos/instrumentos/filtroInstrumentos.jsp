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
<table id="tblFiltroInst" class="tablaSpacing0Padding0Width100Collapse" style="display: none; padding: 1px; width: 420px;">
	<tr class="barraFiltrosForma">
		<td style="width: 185px;">
			<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
			
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 105px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.nombre" /></td>
					<td style="width: 80px;">
						<logic:notEmpty name="gestionarInstrumentosForm">
							<logic:notEmpty name="gestionarInstrumentosForm" property="nombreCorto">
								<input size="30" type="text" id="nombreCorto" name="nombreCorto" class="cuadroTexto" value="<bean:write name="gestionarInstrumentosForm" property="nombreCorto" />">
							</logic:notEmpty>
							<logic:empty name="gestionarInstrumentosForm" property="nombreCorto">
								<input size="30" type="text" id="nombreCorto" name="nombreCorto" class="cuadroTexto" value="">									
							</logic:empty>
						</logic:notEmpty>
						<logic:empty name="gestionarInstrumentosForm">
							<logic:notEmpty name="gestionarInstrumentosForm">
								<logic:notEmpty name="gestionarInstrumentosForm" property="nombreCorto">
									<input size="30" type="text" id="nombreCorto" name="nombreCorto" class="cuadroTexto" value="<bean:write name="gestionarInstrumentosForm" property="nombreCorto" />">
								</logic:notEmpty>
								<logic:empty name="gestionarInstrumentosForm" property="nombreCorto">
									<input size="30" type="text" id="nombreCorto" name="nombreCorto" class="cuadroTexto" value="">									
								</logic:empty>
							</logic:notEmpty>
						</logic:empty>
					</td>
				</tr>	
				
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 50px;"><vgcutil:message key="jsp.gestionariniciativas.filtro.anio" /></td>
					<td style="width: 80px;">
						<logic:notEmpty name="gestionarInstrumentosForm">
							<logic:notEmpty name="gestionarInstrumentosForm" property="anio">
								<input size="5" type="text" id="anio" name="anio" class="cuadroTexto" value="<bean:write name="gestionarInstrumentosForm" property="anio"/>">
							</logic:notEmpty>
							<logic:empty name="gestionarInstrumentosForm" property="anio">
								<input size="5" type="text" id="anio" name="anio" class="cuadroTexto" value="">									
							</logic:empty>
						</logic:notEmpty>
						<logic:empty name="gestionarInstrumentosForm">
							<logic:notEmpty name="gestionarInstrumentosForm">
								<logic:notEmpty name="gestionarInstrumentosForm" property="anio">
									<input size="5" type="text" id="anio" name="anio" class="cuadroTexto" value="<bean:write name="gestionarInstrumentosForm" property="anio"/>">
								</logic:notEmpty>
								<logic:empty name="gestionarInstrumentosForm" property="anio">
									<input size="5" type="text" id="anio" name="anio" class="cuadroTexto" value="">									
								</logic:empty>
							</logic:notEmpty>
						</logic:empty>
					</td>
				</tr>
				
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 150px;"><vgcutil:message key="jsp.gestionarinstrumentos.tipo.titulo" /></td>	
					
					<td>
						<select class="cuadroCombinado" name="selectTipoConvenio" id="selectTipoConvenio">
							<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
							<logic:iterate name="gestionarInstrumentosForm" property="convenios" id="con">
									
								<bean:define id="tipoConvenioId" toScope="page"><bean:write name='con' property='tiposConvenioId' /></bean:define>								
								<bean:define id="nombre" toScope="page"><bean:write name='con' property='nombre' /></bean:define>
									
								<logic:equal name='gestionarInstrumentosForm' property='tiposConvenioId' value='<%=tipoConvenioId.toString()%>'>
									<option value="<%=tipoConvenioId%>" selected><%=nombre%></option>
								</logic:equal>
								<logic:notEqual name='gestionarInstrumentosForm' property='tiposConvenioId' value='<%=tipoConvenioId.toString()%>'>
									<option value="<%=tipoConvenioId%>"><%=nombre%></option>
								</logic:notEqual>
							</logic:iterate>
						</select>
							
					</td>
					
							
				</tr>
				
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 150px;"><vgcutil:message key="jsp.gestionarinstrumentos.cooperante.titulo" /></td>
					<td>
							<select class="cuadroCombinado" name="selectCooperante" id="selectCooperante">
								<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
								<logic:iterate name="gestionarInstrumentosForm" property="cooperantes" id="cop">
									
									<bean:define id="cooperanteId" toScope="page"><bean:write name='cop' property='cooperanteId' /></bean:define>								
									<bean:define id="nombre" toScope="page"><bean:write name='cop' property='nombre' /></bean:define>
									
									<logic:equal name='gestionarInstrumentosForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
													<option value="<%=cooperanteId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='gestionarInstrumentosForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
													<option value="<%=cooperanteId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
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
						<a class="boton" title="<vgcutil:message key="boton.buscar.alt" />" onclick="refrescarInstrumento()"><vgcutil:message key="boton.buscar.alt" /></a>
					</td>
				</tr>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td colspan="2" style="width: 30px;">
						<a class="boton" title="<vgcutil:message key="boton.limpiar.alt" />" onclick="limpiarFiltrosInstrumento()"><vgcutil:message key="boton.limpiar.alt" /></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
