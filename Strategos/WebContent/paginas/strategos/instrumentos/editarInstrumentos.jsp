<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (08/04/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		
		
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarInstrumentosForm" property="instrumentoId" value="0">
			<vgcutil:message key="jsp.editarinstrumento.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarInstrumentosForm" property="instrumentoId" value="0">
			<vgcutil:message key="jsp.editarinstrumento.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>
		
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<script type="text/javascript">
		
			function cancelar() {				
				window.document.editarInstrumentosForm.action = '<html:rewrite action="/instrumentos/cancelarGuardarInstrumento"/>';
				window.document.editarInstrumentosForm.submit();					
			}
		
			function guardar() {				
				if (validar(document.editarInstrumentosForm)) {
					console.log(document.editarInstrumentosForm.anio.value);
					window.document.editarInstrumentosForm.submit();					
				}	
			}

			function validar(forma) {
				if (validateEditarInstrumentosForm(forma)) {
					
					var url = '?ts=<%= (new java.util.Date()).getTime() %>';
					var selectCooperante = document.getElementById('selectCooperante');
					if (selectCooperante != null)
						url = url + '&selectCooperante=' + selectCooperante.value;
					
					var selectTipoConvenio = document.getElementById('selectTipoConvenio');
					if (selectTipoConvenio != null)
						url = url + '&selectTipoConvenio=' + selectTipoConvenio.value;			
					
					
					window.document.editarInstrumentosForm.action = '<html:rewrite action="/instrumentos/guardarInstrumento"/>' + url;
					return true;
				} else {
					return false;
				}
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}
			
			function seleccionarFechaInicio() 
			{
				mostrarCalendarioConFuncionCierre('document.editarInstrumentosForm.fechaInicio' , document.editarInstrumentosForm.fechaInicio.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
			function seleccionarFechaTerminacion() 
			{
				mostrarCalendarioConFuncionCierre('document.editarInstrumentosForm.fechaTerminacion' , document.editarInstrumentosForm.fechaTerminacion.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
			function seleccionarFechaProrroga() 
			{
				mostrarCalendarioConFuncionCierre('document.editarInstrumentosForm.fechaProrroga' , document.editarInstrumentosForm.fechaProrroga.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}

		</script>
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarInstrumentosForm.nombre" />
		
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/guardarInstrumento" focus="nombreCorto" >

			<html:hidden property="instrumentoId" />

			<vgcinterfaz:contenedorForma width="1100px" height="550px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarInstrumentosForm" property="instrumentoId" value="0">
						<vgcutil:message key="jsp.editarinstrumento.titulo.nuevo" />
					</logic:equal>
			
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarInstrumentosForm" property="instrumentoId" value="0">
						<vgcutil:message key="jsp.editarinstrumento.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>
				
				<br>
				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.nombre" /></td>
						<td><html:text property="nombreCorto" size="65" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.descripcion" /></td>
						<td><html:textarea property="nombreInstrumento" cols="45" rows="5" styleClass="cuadroTexto" /></td>
					
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.objetivo" /></td>
						<td><html:textarea property="objetivoInstrumento" cols="45" rows="5" styleClass="cuadroTexto" /></td>					
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.productos" /></td>						
						<td><html:textarea property="productos" cols="45" rows="5" styleClass="cuadroTexto" /></td>
						
						
					</tr>
					
					<!-- Campo tipo proyecto -->
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.tipo" /></td>
						<td>
							<select class="cuadroCombinado" name="selectTipoConvenio" id="selectTipoConvenio">
								<logic:iterate name="editarInstrumentosForm" property="convenios" id="con">
									
									<bean:define id="tipoConvenioId" toScope="page"><bean:write name='con' property='tiposConvenioId' /></bean:define>								
									<bean:define id="nombre" toScope="page"><bean:write name='con' property='nombre' /></bean:define>
									
									<logic:equal name='editarInstrumentosForm' property='tiposConvenioId' value='<%=tipoConvenioId.toString()%>'>
													<option value="<%=tipoConvenioId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='editarInstrumentosForm' property='tiposConvenioId' value='<%=tipoConvenioId.toString()%>'>
													<option value="<%=tipoConvenioId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</select>
							
						</td>
						
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.cooperante" /></td>
						<td>
							<select class="cuadroCombinado" name="selectCooperante" id="selectCooperante">
								<logic:iterate name="editarInstrumentosForm" property="cooperantes" id="cop">
									
									<bean:define id="cooperanteId" toScope="page"><bean:write name='cop' property='cooperanteId' /></bean:define>								
									<bean:define id="nombre" toScope="page"><bean:write name='cop' property='nombre' /></bean:define>
									
									<logic:equal name='editarInstrumentosForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
													<option value="<%=cooperanteId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='editarInstrumentosForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
													<option value="<%=cooperanteId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</select>
							
						</td>
					</tr>
					
					<!-- Campo año formulación -->
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.anio" /></td>
						<td colspan="3" >
							<html:text property="anio" size="5" maxlength="4" styleClass="cuadroTexto" />
						</td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.fecha" /></td>
						<td><html:text 
										property="fechaInicio" 
										onkeypress="ejecutarPorDefecto(event)" 
										size="15" 
										maxlength="10"
										styleClass="cuadroTexto" />
								
								<span style="padding:2px">
									<img 
										style="cursor: pointer" 
										onclick="seleccionarFechaInicio();" 
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
										border="0" 
										width="10" 
										height="10" 
										title="<vgcutil:message 
										key="boton.calendario.alt" />">
								</span>
								</td>
						
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.fecha.terminacion" /></td>
						
						<td><html:text 
										property="fechaTerminacion" 
										onkeypress="ejecutarPorDefecto(event)" 
										size="15" 
										maxlength="10"
										styleClass="cuadroTexto" />
								
								<span style="padding:2px">
									<img 
										style="cursor: pointer" 
										onclick="seleccionarFechaTerminacion();" 
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
										border="0" 
										width="10" 
										height="10" 
										title="<vgcutil:message 
										key="boton.calendario.alt" />">
								</span>
						</td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.fecha.prorroga" /></td>
						
						<td>
								<html:text 
										property="fechaProrroga" 
										onkeypress="ejecutarPorDefecto(event)" 
										size="15" 
										maxlength="10"
										styleClass="cuadroTexto" />
								
								<span style="padding:2px">
									<img 
										style="cursor: pointer" 
										onclick="seleccionarFechaProrroga();" 
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
										border="0" 
										width="10" 
										height="10" 
										title="<vgcutil:message 
										key="boton.calendario.alt" />">
								</span>
						</td>
																	
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.estatus" /></td>
						<td><html:select property="estatus" styleClass="cuadroTexto" size="1">
								
								<html:option value="1">
									<vgcutil:message key="jsp.pagina.instrumentos.estatus.sinIniciar" />
								</html:option>
								<html:option value="2">
									<vgcutil:message key="jsp.pagina.instrumentos.estatus.ejecucion" />
								</html:option>
								<html:option value="3">
									<vgcutil:message key="jsp.pagina.instrumentos.estatus.cancelado" />
								</html:option>
								<html:option value="4">
									<vgcutil:message key="jsp.pagina.instrumentos.estatus.suspendido" />
								</html:option>
								<html:option value="5">
									<vgcutil:message key="jsp.pagina.instrumentos.estatus.culminado" />
								</html:option>
								
							</html:select></td>
						
					</tr>
					
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.recursos.pesos" /></td>
						<td><html:text property="recursosPesos" size="20" maxlength="20" styleClass="cuadroTexto" /></td>
						
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.recursos.dolares" /></td>
						<td><html:text property="recursosDolares" size="20" maxlength="20" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.nombre.ejecutante" /></td>
						<td><html:text property="nombreEjecutante" size="45" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.responsable.pgn" /></td>
						<td><html:text property="nombreReposnsableAreas" size="45" maxlength="50" styleClass="cuadroTexto" /></td>
				
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.responsable.cgi" /></td>
						<td><html:text property="responsableCgi" size="45" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.unidad" /></td>
						<td><html:textarea property="areasCargo" cols="45" rows="5" styleClass="cuadroTexto" /></td>
						
						<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.observaciones" /></td>
						<td><html:textarea property="observaciones" cols="45" rows="5" styleClass="cuadroTexto" /></td>
					</tr>
				
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarInstrumentosForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<html:javascript formName="editarInstrumentosForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
	
	
</tiles:insert>
