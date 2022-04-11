<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configuracion.email.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script>
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			var _setCloseParent = false;

			function cancelar() 
			{
				window.close();						
			}
			
			function salvar() 
			{
				if (!validar())
					return;
				
				activarBloqueoEspera();
	
				document.editarConfiguracionSistemaForm.action = '<html:rewrite action="/configuracion/sistema/email"/>?funcion=salvar&Pantalla=Email';
				document.editarConfiguracionSistemaForm.submit();
			}

			function validar()
			{
				return true;
			}
		
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/configuracion/sistema/editar" styleClass="formaHtmlCompleta" enctype="multipart/form-data" method="POST">		
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma height="180px" width="340px" scrolling="none">

				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.configuracion.email.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="120px" width="320px" nombre="configuracionEmail">

					<%-- Panel: Email --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="responsable" mostrarBorde="false">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.email.pestana" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td>
									<table class="panelContenedor panelContenedorTabla">
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.responsable.correo.defecto.titulo" /></b></td>
										</tr>
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<bean:define id="correoLocal">
												<bean:write name="editarConfiguracionSistemaForm" property="correoLocal" />
											</bean:define>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="tipoCorreoPorDefecto" value="<%=correoLocal%>">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.correo.defecto.local" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<bean:define id="correoGMail">
												<bean:write name="editarConfiguracionSistemaForm" property="correoGMail" />
											</bean:define>
											<td valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="tipoCorreoPorDefecto" value="<%=correoGMail%>">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.correo.defecto.gmail" />
												</html:radio>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:salvar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<%-- Cancelar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			// Configuración salvada
			<logic:equal name="editarConfiguracionSistemaForm" property="status" value="2">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.ok" />', 80, 260);
			</logic:equal>
			<logic:equal name="editarConfiguracionSistemaForm" property="status" value="3">
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.no.ok" />', 80, 300);
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
