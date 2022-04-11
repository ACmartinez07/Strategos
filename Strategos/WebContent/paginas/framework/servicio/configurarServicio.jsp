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
		<vgcutil:message key="jsp.configurar.conexion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript para el wizzard --%>
		<script>
			var _setChangePassword = false;
			var _setCloseParent = false;
			
			function guardar()
			{
				if (validar())
				{
					window.document.gestionarServiciosForm.action = '<html:rewrite action="/framework/servicio/configurarServicio"/>' + '?accion=SAVE' + '&changePassword=' + _setChangePassword;
					window.document.gestionarServiciosForm.submit();
				}
			}
			
			function probar()
			{
				window.document.gestionarServiciosForm.action = '<html:rewrite action="/framework/servicio/configurarServicio"/>' + '?accion=TEST' + '&changePassword=' + _setChangePassword;
				window.document.gestionarServiciosForm.submit();
			}
			
			function validar() 
			{
				if (document.gestionarServiciosForm.stringConexion.value == '')
			  	{
			  		alert('<vgcutil:message key="jsp.configurar.conexion.url.validation.vacio" />');
			  		return false;
			  	}

				if (document.gestionarServiciosForm.driverConexion.value == '')
			  	{
			  		alert('<vgcutil:message key="jsp.configurar.conexion.driver.validation.vacio" />');
			  		return false;
			  	}

				if (document.gestionarServiciosForm.usuarioConexion.value == '')
			  	{
			  		alert('<vgcutil:message key="jsp.configurar.conexion.user.validation.vacio" />');
			  		return false;
			  	}

				if (document.gestionarServiciosForm.passwordConexion.value == '')
			  	{
			  		alert('<vgcutil:message key="jsp.configurar.conexion.password.validation.vacio" />');
			  		return false;
			  	}
				
				return true;
			}
			
			function cancelar() 
			{
				this.close();			
			}
			
			function setChangePassword()
			{
				_setChangePassword = true;
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/servicio/configurarServicio?accion=SAVE" styleClass="formaHtmlCompleta">
		
			<html:hidden property="respuesta" />
			
			<vgcinterfaz:contenedorForma width="600px" height="300px" bodyAlign="center" bodyValign="center" >

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.configurar.conexion.titulo" />
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
															<b><vgcutil:message key="jsp.configurar.conexion.texto" /></b></br></br> 
															<vgcutil:message key="jsp.configurar.conexion.presentacion.linea1" /></br></br>
															<vgcutil:message key="jsp.configurar.conexion.presentacion.linea2" />
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
														<td colspan="2"><b><vgcutil:message key="jsp.configurar.conexion.subtitulo" /></b></td>
													</tr>
													
													<!-- url -->
													<tr>
														<td align="right">
															<vgcutil:message key="jsp.configurar.conexion.string" />
														</td>
														<td>
															<html:text property="stringConexion" size="65" maxlength="150" styleClass="cuadroTexto" />
														</td>
													</tr>
													
													<!-- driver -->
													<tr>
														<td align="right">
															<vgcutil:message key="jsp.configurar.conexion.driver" />
														</td>
														<td>
															<html:text property="driverConexion" size="65" maxlength="150" styleClass="cuadroTexto" />
														</td>
													</tr>

													<!-- user -->
													<tr>
														<td align="right">
															<vgcutil:message key="jsp.configurar.conexion.user" />
														</td>
														<td>
															<html:text property="usuarioConexion" size="65" maxlength="150" styleClass="cuadroTexto" />
														</td>
													</tr>

													<!-- password -->
													<tr>
														<td align="right">
															<vgcutil:message key="jsp.configurar.conexion.password" />
														</td>
														<td>
															<html:password property="passwordConexion" size="65" maxlength="150" styleClass="cuadroTexto" onchange="setChangePassword()"/>
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
					<img src="<html:rewrite page='/componentes/formulario/probar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:probar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.probar" /></a>&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;
					&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			<logic:equal name="gestionarServiciosForm" property="status" value="0">
				_setCloseParent = true;
			</logic:equal>
			<logic:equal name="gestionarServiciosForm" property="status" value="6">
				_setChangePassword = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
