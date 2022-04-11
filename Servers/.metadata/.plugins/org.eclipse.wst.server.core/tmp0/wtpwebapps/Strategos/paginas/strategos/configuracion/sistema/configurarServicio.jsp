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



<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="menu.herramientas.configurar.servicios.ind" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		

		<%-- Funciones JavaScript para el wizzard --%>
		<script>

			

			function aplicar()
			{
				activarBloqueoEspera();
				document.editarConfiguracionSistemaForm.action = '<html:rewrite action="/configuracion/salvarConfiguracionServicio"/>';
				document.editarConfiguracionSistemaForm.submit();
				
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
			
			
			
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/configuracion/configuracionServicio" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
			
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="570px" height="280" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::
						<vgcutil:message key="menu.herramientas.configurar.servicios.ind" />
				</vgcinterfaz:contenedorFormaTitulo>
								
				
				<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td>
									<table class="panelContenedor panelContenedorTabla">
										
										<tr>
											<td align="left" colspan="3" valign="top">
											<b><vgcutil:message key="jsp.configuracion.sistema.fecha.programado" /></b>
											<br>
											<br>
											</td>
											
										</tr>
										
										
										<tr>
											<td valign="top" align="left">
														<vgcutil:message key="jsp.config.sistema.dia.configurar" />
														</td>
											<td valign="top" align="left">
												<html:select name="editarConfiguracionSistemaForm" property="dia" styleClass="cuadroTexto" >
													<html:option value="" />
													<html:option value="1" />
													<html:option value="2" />
													<html:option value="3" />
													<html:option value="4" />
													<html:option value="5" />
													<html:option value="6" />
													<html:option value="7"/>
													<html:option value="8" />
													<html:option value="9" />
													<html:option value="10" />
													<html:option value="11" />
													<html:option value="12" />
													<html:option value="13" />
													<html:option value="14" />
													<html:option value="15" />
													<html:option value="16" />
													<html:option value="17" />
													<html:option value="18" />
													<html:option value="19" />
													<html:option value="20" />
													<html:option value="21" />
													<html:option value="22" />
													<html:option value="23" />
													<html:option value="24" />
													<html:option value="25" />
													<html:option value="26" />
													<html:option value="27" />
													<html:option value="28" />
													<html:option value="29" />
													<html:option value="30" />
													<html:option value="31" />
												</html:select>
												<br>
												<br>
											
											</td>
										</tr>
										
										
										<tr>
											<td valign="top" align="left">
														<vgcutil:message key="jsp.config.sistema.hora.configurar" />
														</td>
											<td valign="top" align="left">
											
											
												<html:text name="editarConfiguracionSistemaForm" property="hora" styleClass="cuadroTexto" maxlength="5" size="5">
												</html:text>
												<br>			
											</td>
										</tr>
										
										
										
										<!--  
										
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.responsable.enviar" /></b></td>
										</tr>
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.seguimiento" /> : </td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.fijarmeta" /> : </td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.lograrmeta" /> : </td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarmeta" /> : </td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarejecutado" /> : </td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										
										-->
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
		
	</tiles:put>
</tiles:insert>
