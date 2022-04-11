<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (19/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var soloLecturaOriginal = false;

			function cerrarVentana() 
			{
				var soloLectura = window.document.editarIndicadorForm.soloLectura.checked;
				if (soloLecturaOriginal != soloLectura) 
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/guardarIndicadorSoloLectura" />?indicadorId=' + window.document.editarIndicadorForm.indicadorId.value + '&soloLectura=' + soloLectura , null, 'finalizarPropiedades()');
				else 
					this.close();
			}

			function finalizarPropiedades() 
			{
				this.close();
			}

			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					cerrarVentana();
			}
		</script>

		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/guardarIndicadorSoloLectura" styleClass="formaHtml">

			<html:hidden property="indicadorId" />
			<input type="hidden" name="mensaje" value="" />

			<vgcinterfaz:contenedorForma bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.propiedadesindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="320" width="380" nombre="propiedadesIndicador">

					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesindicador.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" style="border-spacing:0px;">

							<!-- Nombre Indicador -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/indicadores.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarIndicadorForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Naturaleza -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesindicador.pestana.general.naturaleza" />:</b> &nbsp;<bean:write scope="request" name="editarIndicadorForm" property="naturalezaNombre" /></td>
							</tr>

							<!-- Insumo -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesindicador.pestana.general.insumode" />:</b>&nbsp;<logic:equal scope="request" name="editarIndicadorForm" property="numeroUsosComoIndicadorInsumo" value="0">
									<vgcutil:message key="jsp.propiedadesindicador.pestana.general.noinsumo" />
								</logic:equal><logic:notEqual scope="request" name="editarIndicadorForm" property="numeroUsosComoIndicadorInsumo" value="0">
									<bean:write scope="request" name="editarIndicadorForm" property="numeroUsosComoIndicadorInsumo" />&nbsp;
								</logic:notEqual><logic:equal scope="request" name="editarIndicadorForm" property="numeroUsosComoIndicadorInsumo" value="1">
									<vgcutil:message key="jsp.propiedadesindicador.pestana.general.indicador" />
								</logic:equal><logic:greaterThan scope="request" name="editarIndicadorForm" property="numeroUsosComoIndicadorInsumo" value="1">
									<vgcutil:message key="jsp.propiedadesindicador.pestana.general.indicadores" />
								</logic:greaterThan></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<tr>
								<td><bean:define id="soloLecturaApagado" value="true"></bean:define><vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="INDICADOR_READONLY">
									<bean:define id="soloLecturaApagado" value="false"></bean:define>
								</vgcutil:tienePermiso><html:checkbox property="soloLectura" disabled="<%= (new Boolean(soloLecturaApagado)).booleanValue() %>" styleClass="botonSeleccionMultiple" onkeypress="ejecutarPorDefecto(event)" /><vgcutil:message key="jsp.propiedadesindicador.pestana.general.sololectura" /></td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script type="text/javascript">
			soloLecturaOriginal = window.document.editarIndicadorForm.soloLectura.checked;
		</script>

	</tiles:put>
</tiles:insert>
