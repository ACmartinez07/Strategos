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
			<bean:write name="protegerLiberarMedicionesForm" property="origenOrganizaciones" />
		</bean:define>
		<bean:define id="origenIndicadores">
			<bean:write name="protegerLiberarMedicionesForm" property="origenIndicadores" />
		</bean:define>
		<bean:define id="origenPlanes">
			<bean:write name="protegerLiberarMedicionesForm" property="origenPlanes" />
		</bean:define>
		<bean:define id="origenIniciativas">
			<bean:write name="protegerLiberarMedicionesForm" property="origenIniciativas" />
		</bean:define>
		<bean:define id="origenActividad">
			<bean:write name="protegerLiberarMedicionesForm" property="origenActividades" />
		</bean:define>
		
		<bean:define id="seleccionIndicador" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionIndicador" />
		</bean:define>
		<bean:define id="seleccionIndicadoresSeleccionados" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionIndicadoresSeleccionados" />
		</bean:define>
		<bean:define id="seleccionClase" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionClase" />
		</bean:define>
		<bean:define id="seleccionSubClase" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionSubClase" />
		</bean:define>
		<bean:define id="seleccionOrganizacion" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionOrganizacion" />
		</bean:define>
		<bean:define id="seleccionSubOrganizacion" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionSubOrganizacion" />
		</bean:define>
		<bean:define id="seleccionOrganizacionTodas" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionOrganizacionTodas" />
		</bean:define>
		<bean:define id="seleccionPlan" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionPlan" />
		</bean:define>
		<bean:define id="seleccionPerspectiva" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionPerspectiva" />
		</bean:define>
		<bean:define id="seleccionIniciativa" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="seleccionIniciativa" />
		</bean:define>
		<bean:define id="accionProteger" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="accionProteger" />
		</bean:define>
		<bean:define id="accionLiberar" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="accionLiberar" />
		</bean:define>
		<bean:define id="altoForma" toScope="page">
			<bean:write name="protegerLiberarMedicionesForm" property="altoForma" />
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
				if(document.protegerLiberarMedicionesForm.accion.value ==1){
					accion="proteger";
				}else if(document.protegerLiberarMedicionesForm.accion.value ==2){
					accion="liberar";
				}
				console.log(document.protegerLiberarMedicionesForm);
				
				
				
					var xml = '?funcion=importar';	
					xml= xml+'&accion='+accion;
					
				
				
				activarBloqueoEspera();
				document.protegerLiberarMedicionesForm.action = '<html:rewrite action="/planes/protegerLiberarSalvar"/>'+xml;
				
				document.protegerLiberarMedicionesForm.submit();
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
				
				if (document.protegerLiberarMedicionesForm.origen.value != "5")
				{
					var seleccionado = false;
					for (var i = 0; i < document.protegerLiberarMedicionesForm.tipoSeleccion.length; i++)
					{
						if (document.protegerLiberarMedicionesForm.tipoSeleccion[i].checked)
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
		<html:form action="/planes/protegerLiberar" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="respuesta" />
			<html:hidden property="status" />
			<html:hidden property="origen" />
			<html:hidden property="accion" />
			<html:hidden property="organizacionId" />
			<html:hidden property="planId"/>
			<html:hidden property="indicadorId"/>
			
			
			<input type="hidden" name="mesInicialAnt" value="1" />
			<input type="hidden" name="mesFinalAnt" value="12" />
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="660px" height="400px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::
					<vgcutil:message key="jsp.protegerliberar.metas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatostabla bordeFichaDatos">
					<tr>
						<%-- Rango --%>
						<td>
							<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
								<tr>
									<td colspan="2">
										<b><vgcutil:message key="jsp.protegerliberar.rango.ano" /></b>
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
											<bean:write name="protegerLiberarMedicionesForm" property="ano" />
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
								
							</table>
						</td>
					</tr>
					<logic:notEqual property="origen" name="protegerLiberarMedicionesForm" value="<%= origenActividad %>">
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
										<td height="10">&nbsp;</td>
									</tr>
									<logic:equal property="origen" name="protegerLiberarMedicionesForm" value="<%= origenIndicadores %>">
										<logic:notEmpty property="indicadorId" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicador %>">
														<vgcutil:message key="jsp.protegerliberar.porindicador" />
													</html:radio>
												</td>
											</tr>
										</logic:notEmpty>
										<logic:empty property="indicadorId" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicador %>" disabled="true">
														<vgcutil:message key="jsp.protegerliberar.porindicador" />
													</html:radio>
												</td>
											</tr>
										</logic:empty>
										<logic:notEmpty property="indicadores" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicadoresSeleccionados %>">
														<vgcutil:message key="jsp.protegerliberar.porindicadores" />
													</html:radio>
												</td>
											</tr>
										</logic:notEmpty>
										<logic:empty property="indicadores" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicadoresSeleccionados %>" disabled="true">
														<vgcutil:message key="jsp.protegerliberar.porindicadores" />
													</html:radio>
												</td>
											</tr>
										</logic:empty>
	
										<tr>
											<td>
												<html:radio property="tipoSeleccion" value="<%= seleccionClase %>">
													<vgcutil:message key="jsp.protegerliberar.porclase" />
												</html:radio>
											</td>
										</tr>
										
										<tr>
											<td>
												<html:radio property="tipoSeleccion" value="<%= seleccionSubClase %>">
													<vgcutil:message key="jsp.protegerliberar.porsubclase" />
												</html:radio>
											</td>
										</tr>
									</logic:equal>
	
									<logic:equal property="origen" name="protegerLiberarMedicionesForm" value="<%= origenPlanes %>">
										<logic:notEmpty property="indicadorId" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicador %>">
														<vgcutil:message key="jsp.protegerliberar.porindicador" />
													</html:radio>
												</td>
											</tr>
										</logic:notEmpty>
										<logic:empty property="indicadorId" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicador %>" disabled="true">
														<vgcutil:message key="jsp.protegerliberar.porindicador" />
													</html:radio>
												</td>
											</tr>
										</logic:empty>
										<logic:notEmpty property="indicadores" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicadoresSeleccionados %>">
														<vgcutil:message key="jsp.protegerliberar.porindicadores" />
													</html:radio>
												</td>
											</tr>
										</logic:notEmpty>
										<logic:empty property="indicadores" name="protegerLiberarMedicionesForm">
											<tr>
												<td>
													<html:radio property="tipoSeleccion" value="<%= seleccionIndicadoresSeleccionados %>" disabled="true">
														<vgcutil:message key="jsp.protegerliberar.porindicadores" />
													</html:radio>
												</td>
											</tr>
										</logic:empty>
										
										<tr>
											<td>
												<html:radio property="tipoSeleccion" value="<%= seleccionPlan %>">
													<vgcutil:message key="jsp.protegerliberar.porplan" />
												</html:radio>
											</td>
										</tr>
									</logic:equal>
	
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
					</logic:notEqual>
					
					
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
			<logic:equal name="protegerLiberarMedicionesForm" property="status" value="0">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
