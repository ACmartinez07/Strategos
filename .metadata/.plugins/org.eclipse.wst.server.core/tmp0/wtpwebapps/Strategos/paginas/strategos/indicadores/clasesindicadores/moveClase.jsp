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
		<vgcutil:message key="jsp.moverclase.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesIndicadoresJs.jsp"></jsp:include>
		<script>
			function guardar() 
			{
				if (window.document.editarClaseIndicadoresForm.claseSeleccionId.value == "")
				{
					alert('<vgcutil:message key="jsp.moverclase.validacion.alert.clase.vacio" /> ');
					return;
				}

				activarBloqueoEspera();
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/clasesindicadores/guardarMoverClase" />?claseId=' + window.document.editarClaseIndicadoresForm.claseId.value + "&clasePadreId=" + window.document.editarClaseIndicadoresForm.claseSeleccionId.value, document.editarClaseIndicadoresForm.status, 'onGuardar()');
			}
			
			function onGuardar()
			{
				desactivarBloqueoEspera();
				if (document.editarClaseIndicadoresForm.status.value == "0")
				{
					window.opener.refrescarClase(true);
					alert('<vgcutil:message key="jsp.moverclase.mover.ok" /> ');
					window.close();
				}
				else
					alert('<vgcutil:message key="jsp.moverclase.mover.noOk" /> ');
			}
	
			function cancelar() 
			{
				window.close();
			}
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Clases de Indicadores
			// ********************************************************************************			

			function limpiarClase()
			{
				window.document.editarClaseIndicadoresForm.claseSeleccion.value = '';
				window.document.editarClaseIndicadoresForm.claseSeleccionId.value = '';
			}
			
			function seleccionarClase()
			{
				abrirSelectorClasesIndicadores('editarClaseIndicadoresForm', 'claseSeleccion', 'claseSeleccionId', document.editarClaseIndicadoresForm.claseId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');
			}
			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Clases de Indicadores
			// ********************************************************************************
	
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/clasesindicadores/moverClase">

			<%-- Id de las tablas --%>
			<html:hidden property="claseId" />
			<html:hidden property="padreId" />
			<html:hidden property="status" />

			<vgcinterfaz:contenedorForma width="460px" height="265px" bodyAlign="center" bodyValign="center" marginTop="5px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.moverclase.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr height=170px>

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
															<b><vgcutil:message key="jsp.moverclase.texto1" /></b></br></br> 
															<vgcutil:message key="jsp.moverclase.presentacion" /></br></br>
															<vgcutil:message key="jsp.moverclase.presentacion1" />
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
														<td colspan="2"><b><vgcutil:message key="jsp.moverclase.seleccion" /></b></td>
													</tr>
													<tr>
														<td colspan="2">
															<html:text property="claseSeleccion" size="52" readonly="true" disabled="false" maxlength="50" styleClass="cuadroTexto" />&nbsp;
															<img style="cursor: pointer" onclick="seleccionarClase();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.editar.seleccionar.plantilla" />">&nbsp;
															<img style="cursor: pointer" onclick="limpiarClase();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
															<html:hidden property="claseSeleccionId" />
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
		<html:javascript formName="editarClaseIndicadoresForm" dynamicJavascript="true" staticJavascript="false" />
		<script>
			if (document.editarClaseIndicadoresForm.padreId.value == "" || document.editarClaseIndicadoresForm.padreId.value == "0")
			{
				alert('<vgcutil:message key="jsp.moverclase.validacion.nocopiar" /> ');
				cancelar();
			}
		</script>
	</tiles:put>
</tiles:insert>
