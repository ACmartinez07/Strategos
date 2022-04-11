<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<logic:equal name="editarSeguimientoForm" property="verSeguimiento" value="false">
			<%-- Para el caso de Responder --%>
			<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="false">
				<vgcutil:message key="jsp.editarseguimiento.titulo.responder" />
			</logic:equal>
			<%-- Para el caso de Aprobar --%>
			<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="true">
				<logic:empty name="editarSeguimientoForm" property="fechaAprobacion">
					<vgcutil:message key="jsp.editarseguimiento.titulo.aprobar" />
				</logic:empty>
			</logic:equal>
		</logic:equal>
		<%-- Para el caso de Ver --%>
		<logic:equal name="editarSeguimientoForm" property="verSeguimiento" value="true">
			<vgcutil:message key="jsp.editarseguimiento.titulo.ver" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">			
			
			function validar() {
				<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="false">
					if ((window.document.editarSeguimientoForm.memoResumen == null) || (window.document.editarSeguimientoForm.memoResumen.value == "")) {
						alert('<vgcutil:message key="jsp.editarseguimiento.validacion.resumen.requerido" />');
						return false;
					}
					if ((window.document.editarSeguimientoForm.estadoId == null) || (window.document.editarSeguimientoForm.estadoId.value == "")) {
						alert('<vgcutil:message key="jsp.editarseguimiento.validacion.estado.requerido" />');
						return false;
					}
				</logic:equal>
				<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="true">
					<logic:empty name="editarSeguimientoForm" property="fechaAprobacion">
						if ((window.document.editarSeguimientoForm.memoComentario == null) || (window.document.editarSeguimientoForm.memoComentario.value == "")) {
							alert('<vgcutil:message key="jsp.editarseguimiento.validacion.comentario.requerido" />');
							return false;
						}
						if ((window.document.editarSeguimientoForm.aprobado == null) || (window.document.editarSeguimientoForm.aprobado.value == "")) {
							alert('<vgcutil:message key="jsp.editarseguimiento.validacion.aprobacion.requerido" />');
							return false;
						}
					</logic:empty>
				</logic:equal>
				return true;
			}

			function guardar() {				
				if (validar()) {
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarSeguimientoForm"></vgcinterfaz:mostrarPanelContenedorJs>
					window.document.editarSeguimientoForm.action = '<html:rewrite action="/problemas/seguimientos/guardarSeguimiento"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';			
					window.document.editarSeguimientoForm.submit();
				}
			}

			function cancelar() {
				window.document.editarSeguimientoForm.action = '<html:rewrite action="/problemas/seguimientos/cancelarGuardarSeguimiento"/>';
				window.document.editarSeguimientoForm.submit();
			}
												
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/seguimientos/guardarSeguimiento" onsubmit="if (validar(document.editarSeguimientoForm)) return true; else return false;" styleClass="formatHtml">

			<html:hidden property="seguimientoId" />

			<vgcinterfaz:contenedorForma width="590px" height="405px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<logic:equal name="editarSeguimientoForm" property="verSeguimiento" value="false">
						<%-- Para el caso de Responder --%>
						<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="false">
							<vgcutil:message key="jsp.editarseguimiento.titulo.responder" />
						</logic:equal>
						<%-- Para el caso de Aprobar --%>
						<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="true">
							<logic:empty name="editarSeguimientoForm" property="fechaAprobacion">
								<vgcutil:message key="jsp.editarseguimiento.titulo.aprobar" />
							</logic:empty>
						</logic:equal>
					</logic:equal>
					<%-- Para el caso de Ver --%>
					<logic:equal name="editarSeguimientoForm" property="verSeguimiento" value="true">
						<vgcutil:message key="jsp.editarseguimiento.titulo.ver" />
					</logic:equal>
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="300px" width="530px" nombre="editarSeguimientoForm">

					<!-- Panel: Datos Básicos -->
					<vgcinterfaz:panelContenedor anchoPestana="105" nombre="datosBasicos">

						<!-- Título del Panel Datos Básicos -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0">

							<!-- Acción Correctiva -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.accion" /></td>
								<td><html:text property="nombreAccion" size="64" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Responsable -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.responsable" /></td>
								<td><html:text property="nombreResponsable" size="64" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Número de Reporte -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.numeroreporte" /></td>
								<td><html:text property="numeroReporte" size="2" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Campo Fecha de Emision -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.fechaemision" /></td>
								<td><html:text property="fechaEmision" size="10" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Campo fecha Recepción -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.fecharecepcion" /></td>
								<td><html:text property="fechaRecepcion" size="10" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Resumen -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.resumen" /></td>
								<td><html:textarea property="memoResumen" cols="63" rows="8" styleClass="cuadroTexto" disabled="true" /></td>
							</tr>

							<!-- Estado Acción -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.datosbasicos.estadoacciones" /></td>
								<td><html:select property="estadoId" styleClass="cuadroTexto" disabled="true">
									<html:option value=""></html:option>
									<html:options collection="paginaEstadoAcciones" property="estadoId" labelProperty="nombre" />
								</html:select></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Aprobación -->
					<vgcinterfaz:panelContenedor anchoPestana="105" nombre="aprobacion">

						<!-- Título del Panel Aprobación -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarseguimiento.ficha.pestana.aprobacion" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0">

							<!-- Supervisa -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.aprobacion.supervisa" /></td>
								<td><html:text property="nombreSupervisor" size="71" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Aprobado -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.aprobacion.aprobado" /></td>
								<td><html:select property="aprobado" styleClass="cuadroTexto" disabled="true">
									<html:option value="false">
										<vgcutil:message key="comunes.no" />
									</html:option>
									<html:option value="true">
										<vgcutil:message key="comunes.si" />
									</html:option>
								</html:select></td>
							</tr>

							<!-- Fecha -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.aprobacion.fecha" /></td>
								<td><html:text property="fechaAprobacion" size="10" styleClass="cuadroTexto" readonly="true" /></td>
							</tr>

							<!-- Comentario -->
							<tr>
								<td colspan="2"><vgcutil:message key="jsp.editarseguimiento.ficha.pestana.aprobacion.comentario" /><br>
								<html:textarea property="memoComentario" cols="80" rows="12" styleClass="cuadroTexto" disabled="true" /></td>
							</tr>

						</table>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">

					<!-- Boton Aceptar -->
					<logic:notEqual name="editarSeguimientoForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>

					<!-- Boton Cancelar -->
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;

				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			<logic:equal name="editarSeguimientoForm" property="verSeguimiento" value="false">
				<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="false">
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarSeguimientoForm"></vgcinterfaz:mostrarPanelContenedorJs>
					window.document.editarSeguimientoForm.memoResumen.disabled = false;
					window.document.editarSeguimientoForm.estadoId.disabled = false;
				</logic:equal>
				<logic:equal name="editarSeguimientoForm" property="recepcionEnviado" value="true">
					<logic:empty name="editarSeguimientoForm" property="fechaAprobacion">
						<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="aprobacion" nombreContenedor="editarSeguimientoForm"></vgcinterfaz:mostrarPanelContenedorJs>
						window.document.editarSeguimientoForm.memoComentario.disabled = false;
						window.document.editarSeguimientoForm.aprobado.disabled = false;
					</logic:empty>					
				</logic:equal>
			</logic:equal>

		</script>

	</tiles:put>
</tiles:insert>
