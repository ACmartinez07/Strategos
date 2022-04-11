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
		<vgcutil:message key="jsp.moverorganizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript">
			function guardar() 
			{
				if (window.document.editarOrganizacionForm.organizacionSeleccionId.value == "")
				{
					alert('<vgcutil:message key="jsp.moverorganizacion.validacion.alert.organizacion.vacio" /> ');
					return;
				}
				 
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/organizaciones/guardarMoverOrganizacion" />?organizacionId=' + window.document.editarOrganizacionForm.organizacionId.value + "&organizacionPadreId=" + window.document.editarOrganizacionForm.organizacionSeleccionId.value, document.editarOrganizacionForm.status, 'onGuardar()');
			}
			
			function onGuardar()
			{
				if (document.editarOrganizacionForm.status.value == "0")
				{
					alert('<vgcutil:message key="jsp.moverorganizacion.mover.ok" /> ');
					window.opener.regrescarOrganizacion(window.document.editarOrganizacionForm.rootId.value);
					window.close();
				}
				else
					alert('<vgcutil:message key="jsp.moverorganizacion.mover.noOk" /> ');
			}
	
			function cancelar() 
			{
				window.close();
			}
			
			function seleccionarOrganizacion()
			{
				var nombreForma = '?nombreForma=' + 'editarOrganizacionForm';
				var nombreCampoOculto = '&nombreCampoOculto=' + 'organizacionSeleccionId';
				var nombreCampoValor = '&nombreCampoValor=' + 'organizacionSeleccion';
	
				abrirVentanaModal('<html:rewrite action="/organizaciones/seleccionarOrganizaciones" />' + nombreForma + nombreCampoOculto + nombreCampoValor, 'seleccionarOrganizacion', '600', '400');
			}
	
			function limpiarOrganizacion()
			{
				window.document.editarOrganizacionForm.organizacionSeleccion.value = '';
				window.document.editarOrganizacionForm.organizacionSeleccionId.value = '';
			}
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/moverOrganizacion">

			<%-- Id de las tablas --%>
			<html:hidden property="organizacionId" />
			<html:hidden property="padreId" />
			<html:hidden property="rootId" />
			<html:hidden property="status" />

			<vgcinterfaz:contenedorForma width="460px" height="260px" bodyAlign="center" bodyValign="center" >

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.moverorganizacion.titulo" />
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
															<b><vgcutil:message key="jsp.moverorganizacion.texto1" /></b></br></br> 
															<vgcutil:message key="jsp.moverorganizacion.presentacion" /></br></br>
															<vgcutil:message key="jsp.moverorganizacion.presentacion1" />
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
														<td colspan="2"><b><vgcutil:message key="jsp.moverorganizacion.seleccion" /></b></td>
													</tr>
													<tr>
														<td colspan="2">
															<html:text property="organizacionSeleccion" size="50" readonly="true" disabled="false" maxlength="50" styleClass="cuadroTexto" />&nbsp;
															<img style="cursor: pointer" onclick="seleccionarOrganizacion();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.editar.seleccionar.plantilla" />">&nbsp;
															<img style="cursor: pointer" onclick="limpiarOrganizacion();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
															<html:hidden property="organizacionSeleccionId" />
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
			if (document.editarOrganizacionForm.padreId.value == "")
			{
				alert('<vgcutil:message key="jsp.moverorganizacion.validacion.nocopiar" /> ');
				cancelar();
			}
		</script>
	</tiles:put>
</tiles:insert>
