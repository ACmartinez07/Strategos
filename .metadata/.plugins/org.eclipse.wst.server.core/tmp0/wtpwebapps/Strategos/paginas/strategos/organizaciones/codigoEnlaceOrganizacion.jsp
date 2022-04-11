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
		<vgcutil:message key="jsp.codigoenlaceorganizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script>
		
		function guardar() 
		{
			activarBloqueoEspera();
			document.editarOrganizacionForm.action= '<html:rewrite action="/organizaciones/codigoEnlaceOrganizacion" />' + '?ts=<%= (new Date()).getTime() %>';
			document.editarOrganizacionForm.submit();
		}

		function cancelar() 
		{
			window.close();
		}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/codigoEnlaceOrganizacion">

			<%-- Id de las tablas --%>
			<html:hidden property="organizacionId" />

			<vgcinterfaz:contenedorForma width="610px" height="340px" bodyAlign="center" bodyValign="center" >

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.codigoenlaceorganizacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr height=240px>

						<td>
							<vgcinterfaz:contenedorPaneles height="100%" width="100%" nombre="contenedorPrincipal" mostrarSelectoresPaneles="false">
								<%-- Panel Presentacion --%>
								<vgcinterfaz:panelContenedor anchoPestana="100%" nombre="panelPresentacion" mostrarBorde="false">
									<table cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td>
												<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
													<tr>
														<td>
															<b><vgcutil:message key="jsp.codigoenlaceorganizacion.texto1" /></b></br></br> 
															<vgcutil:message key="jsp.codigoenlaceorganizacion.presentacion" /></br></br>
															<vgcutil:message key="jsp.codigoenlaceorganizacion.presentacion1" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td></br></td>
										</tr>
										<tr>
											<td>
												<table class="contenedorBotonesSeleccion" width="100%">
													<tr>
														<td colspan="2"><b><vgcutil:message key="jsp.codigoenlaceorganizacion.seleccion" /></b></td>
													</tr>
													<tr>
														<td>
															<html:radio property="seleccion" value="0" /> 
															<vgcutil:message key="jsp.codigoenlaceorganizacion.seleccion.solo" />
														</td>
													</tr>
													<tr>
														<td>
															<html:radio property="seleccion" value="1" />
															<vgcutil:message key="jsp.codigoenlaceorganizacion.seleccion.todas" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td></br></td>
										</tr>
										<tr>
											<td>
												<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td>
															<html:checkbox property="concatenarCodigos" styleClass="botonSeleccionMultiple">
																<vgcutil:message key="jsp.codigoenlaceorganizacion.concatenar" />
															</html:checkbox>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</vgcinterfaz:panelContenedor>
							</vgcinterfaz:contenedorPaneles>
						</td>
					</tr>
				</table>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;
					&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<html:javascript formName="editarOrganizacionForm" dynamicJavascript="true" staticJavascript="false" />
		<script>
			document.editarOrganizacionForm.seleccion[0].checked = true;
		</script>
	</tiles:put>
</tiles:insert>
