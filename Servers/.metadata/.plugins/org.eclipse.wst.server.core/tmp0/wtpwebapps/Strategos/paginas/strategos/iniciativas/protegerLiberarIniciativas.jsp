<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.protegerliberar.metas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<bean:define id="origenOrganizaciones">
			<bean:write name="protegerLiberarIniciativasForm" property="origenOrganizaciones" />
		</bean:define>
		<bean:define id="origenIndicadores">
			<bean:write name="protegerLiberarIniciativasForm" property="origenIndicadores" />
		</bean:define>
		<bean:define id="origenPlanes">
			<bean:write name="protegerLiberarIniciativasForm" property="origenPlanes" />
		</bean:define>
		<bean:define id="origenIniciativas">
			<bean:write name="protegerLiberarIniciativasForm" property="origenIniciativas" />
		</bean:define>
		<bean:define id="origenActividad">
			<bean:write name="protegerLiberarIniciativasForm" property="origenActividades" />
		</bean:define>
		
		<bean:define id="seleccionOrganizacion" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="seleccionOrganizacion" />
		</bean:define>
		<bean:define id="seleccionSubOrganizacion" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="seleccionSubOrganizacion" />
		</bean:define>
		<bean:define id="seleccionOrganizacionTodas" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="seleccionOrganizacionTodas" />
		</bean:define>
		<bean:define id="seleccionPlan" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="seleccionPlan" />
		</bean:define>
		
		<bean:define id="seleccionIniciativa" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="seleccionIniciativa" />
		</bean:define>
		<bean:define id="accionProteger" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="accionProteger" />
		</bean:define>
		<bean:define id="accionLiberar" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="accionLiberar" />
		</bean:define>
		<bean:define id="altoForma" toScope="page">
			<bean:write name="protegerLiberarIniciativasForm" property="altoForma" />
		</bean:define>

		<%-- Funciones JavaScript para el wizzard --%>
		<script>

			var _setCloseParent = false;
			var errmsgrango = '<vgcutil:message key="jsp.protegerliberar.errorrango"/>';
			
			function validarRango(desdeobj, hastaobj, desdeAntobj, hastaAntobj, errmsg)
		  	{
		  		desde = parseInt(desdeobj.value);
				hasta = parseInt(hastaobj.value);
				if (hasta<desde) 
				{
					alert(errmsg);
					desdeobj.value = desdeAntobj.value;
					hastaobj.value = hastaAntobj.value;				
				} 
				else 
				{
					desdeAntobj.value = desde;
					hastaAntobj.value = hasta;				
				}
			}

			function aplicar()
			{
				
				var accion=null;
				if (!validar())
					return;
				if(document.protegerLiberarIniciativasForm.accion.value ==1){
					accion="proteger";
				}else if(document.protegerLiberarIniciativasForm.accion.value ==2){
					accion="liberar";
				}
				console.log(document.protegerLiberarIniciativasForm);
				
				var xml = '?funcion=importar';	
				xml= xml+'&accion='+accion;	
				
				var selectTipoProyecto = document.getElementById('selectTipoProyecto');
				if (selectTipoProyecto != null)
					xml = xml + '&selectTipoProyecto=' + selectTipoProyecto.value;
								
				activarBloqueoEspera();
				document.protegerLiberarIniciativasForm.action = '<html:rewrite action="/iniciativas/protegerLiberarSalvar"/>'+xml;
				
				document.protegerLiberarIniciativasForm.submit();
			}
			
			function cancelar() 
			{
				this.close();			
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function validar()
			{
				
				if (document.protegerLiberarIniciativasForm.origen.value != "5")
				{
					var seleccionado = false;
					for (var i = 0; i < document.protegerLiberarIniciativasForm.tipoSeleccion.length; i++)
					{
						if (document.protegerLiberarIniciativasForm.tipoSeleccion[i].checked)
						{
							seleccionado = true;
							break;						
						}
					}
					
					
					
					if (!seleccionado)
					{
						alert('<vgcutil:message key="jsp.protegerliberar.alerta.seleccion.empty" /> ');
						return false;					
					}
				}
				
				return true;
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/protegerLiberar" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="respuesta" />
			<html:hidden property="status" />
			<html:hidden property="origen" />
			<html:hidden property="accion" />
			<html:hidden property="iniciativaId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="planId"/>
			
			
			
			
			<input type="hidden" name="mesInicialAnt" value="1" />
			<input type="hidden" name="mesFinalAnt" value="12" />
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="700px" height="550px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::
					<vgcutil:message key="jsp.protegerliberar.iniciativas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatostabla bordeFichaDatos">
					<tr>
						<%-- Rango --%>
						<td>
							<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
								<tr>
									<td colspan="2">
										<b><vgcutil:message key="jsp.protegerliberar.rango" /></b>
									</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td align="right">
										<vgcutil:message key="jsp.protegerliberar.ano" />
									</td>
									<td>
										<bean:define id="anoCalculo" toScope="page">
											<bean:write name="protegerLiberarIniciativasForm" property="ano" />
										</bean:define>
										<html:select property="ano" value="<%= anoCalculo %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select>
									</td>
								</tr>
								<tr>
									<td align="right">
										<vgcutil:message key="jsp.protegerliberar.mesinicial" />
									</td>
									<td>
										<table class="contenedorTextoSeleccion" cellpadding="3" cellspacing="3">
											<tr>
												<td>
													<bean:define id="valor" toScope="page">
														<bean:write name="protegerLiberarIniciativasForm" property="mesInicial" />
													</bean:define>
													<html:select property="mesInicial" value="<%= valor %>" onchange="validarRango(document.protegerLiberarIniciativasForm.mesInicial, document.protegerLiberarIniciativasForm.mesFinal, document.protegerLiberarIniciativasForm.mesInicialAnt, document.protegerLiberarIniciativasForm.mesFinalAnt, errmsgrango);" styleClass="cuadroTexto">
														<%
														for (int i = 1; i <= 12; i++) 
														{
														%>
														<html:option value="<%=String.valueOf(i)%>">
															<%=i%>
														</html:option>
														<%
														}
														%>
													</html:select>
												</td>
												<td align="left">
													<table class="contenedorTextoSeleccion" cellpadding="3" cellspacing="3">
														<tr>
															<td>
																<vgcutil:message key="jsp.protegerliberar.mesfinal" />
															</td>
															<td>
																<bean:define id="valor" toScope="page">
																	<bean:write name="protegerLiberarIniciativasForm" property="mesFinal" />
																</bean:define>
																<html:select property="mesFinal" value="<%= valor %>" onchange="validarRango(document.protegerLiberarIniciativasForm.mesInicial, document.protegerLiberarIniciativasForm.mesFinal, document.protegerLiberarIniciativasForm.mesInicialAnt, document.protegerLiberarIniciativasForm.mesFinalAnt, errmsgrango);" styleClass="cuadroTexto">
																	<%
																	for (int i = 1; i <= 12; i++) 
																	{
																	%>
																	<html:option value="<%=String.valueOf(i)%>">
																		<%=i%>
																	</html:option>
																	<%
																	}
																	%>
																</html:select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
						<tr>
							<td>
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
										<tr>
											<td>
												<b>
													<vgcutil:message key="jsp.protegerliberar.selector" />
												</b>
											</td>
										</tr>
									
									<tr>
										<td><html:radio property="tipoSeleccion" value="<%= seleccionIniciativa %>">
											<vgcutil:message key="jsp.protegerliberar.iniciativa" />
										</html:radio></td>
									</tr>
									
									<logic:notEqual property="planId" name="protegerLiberarIniciativasForm" value="0">
										<tr>
											<td>
												<html:radio property="tipoSeleccion" value="<%= seleccionPlan %>">
													<vgcutil:message key="jsp.protegerliberar.porplan" />
												</html:radio>
											</td>
											
										</tr>
									</logic:notEqual>
																			
									<tr>
										<td><html:radio property="tipoSeleccion" value="<%= seleccionOrganizacion %>">
											<vgcutil:message key="jsp.protegerliberar.pororganizacionseleccionada" />
										</html:radio></td>
									</tr>
	
									<tr>
										<td>
											<html:radio property="tipoSeleccion" value="<%= seleccionOrganizacionTodas %>">
												<vgcutil:message key="jsp.protegerliberar.pororganizaciontodas" />
											</html:radio>
										</td>
									</tr>
	
								</table>	
							</td>
						</tr>
					
					 
					
					<tr>
						<td>
						<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
							
							<tr>
							<td colspan="2">
								<b>
								<vgcutil:message key="jsp.protegerliberar.accion" />
								</b>
							</td>	
							</tr>
							<tr>
								<td colspan="3">
									<html:radio property="accion" value="<%= accionProteger %>" /><vgcutil:message key="jsp.protegerliberar.proteger" />&nbsp;&nbsp;&nbsp;
									<html:radio property="accion" value="<%= accionLiberar %>" /><vgcutil:message key="jsp.protegerliberar.liberar" />
								</td>
							</tr>
							
						</table>
						</td>
					</tr>
					
					<tr>
						<td>
						<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
							
							<tr>
							<td colspan="2">
								<b>
								<vgcutil:message key="jsp.gestionariniciativas.columna.tipoProyecto" />
								</b>
							</td>	
							</tr>
							<tr>
								<td colspan="3">
									<select class="cuadroCombinado" name="selectTipoProyecto" id="selectTipoProyecto" >
											<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
											<logic:iterate name="protegerLiberarIniciativasForm" property="tipos" id="tip">
												<bean:define id="tipoProyectoId" toScope="page"><bean:write name='tip' property='tipoProyectoId' /></bean:define>
												<bean:define id="nombre" toScope="page"><bean:write name='tip' property='nombre' /></bean:define>
												<logic:equal name='protegerLiberarIniciativasForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='protegerLiberarIniciativasForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
									</select>	
								</td>
							</tr>
							
						</table>
						</td>
					</tr>
					
					<tr>
							<td>
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
									<tr>
										<td>
											<b>
												<vgcutil:message key="jsp.protegerliberar.serie.titulo" />
											</b>
										</td>
									</tr>	
									<tr>
										<td>
											<html:radio property="serieId" value="0" >
												<vgcutil:message key="jsp.protegerliberar.serie.real" />
											</html:radio>
										</td>
									</tr>
									<tr>
										<td>
											<html:radio property="serieId" value="1" >
												<vgcutil:message key="jsp.protegerliberar.serie.programado" />
											</html:radio>
										</td>
									</tr>
									
									<tr>
										<td>
											<html:radio property="serieId" value="7" >
												<vgcutil:message key="jsp.protegerliberar.serie.todos" />
											</html:radio>
										</td>
									</tr>							
								</table>
							</td>
						</tr>
				</table>
							
				
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:aplicar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			<logic:equal name="protegerLiberarIniciativasForm" property="status" value="0">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
