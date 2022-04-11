<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarIniciativaEstatusForm" property="id" value="0">
			<vgcutil:message key="jsp.editar.estatus.iniciativa.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarIniciativaEstatusForm" property="id" value="0">
			<vgcutil:message key="jsp.editar.estatus.iniciativa.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarIniciativaEstatusForm" property="bloqueado">
				<bean:write name="editarIniciativaEstatusForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarIniciativaEstatusForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var _setCloseParent = false;
		
			function cancelar() 
			{
				this.close();
			}

			function guardar() 
			{
				if (validar())
				{
					var url = '?id=' + document.editarIniciativaEstatusForm.id.value;
					url = url + '&porcentajeInicial=' + document.editarIniciativaEstatusForm.porcentajeInicial.value;
					url = url + '&porcentajeFinal=' + document.editarIniciativaEstatusForm.porcentajeFinal.value;
					
					window.document.editarIniciativaEstatusForm.action = '<html:rewrite action="/iniciativas/estatus/guardar"/>' + url;
					window.document.editarIniciativaEstatusForm.submit();
				}
			}

			function validar() 
			{
				if (document.editarIniciativaEstatusForm.nombre.value == null || document.editarIniciativaEstatusForm.nombre.value == "" || document.editarIniciativaEstatusForm.nombre.value.length == 0)
				{
					showAlert('<vgcutil:message key="jsp.editar.estatus.iniciativa.alerta.nombre.empty" />', 100, 340);
					return false;
				}
				if (document.editarIniciativaEstatusForm.sistema.value == "false")
				{
					if (document.editarIniciativaEstatusForm.porcentajeInicial.value == null || document.editarIniciativaEstatusForm.porcentajeInicial.value == "" || document.editarIniciativaEstatusForm.porcentajeInicial.value.length == 0)
					{
						showAlert('<vgcutil:message key="jsp.editar.estatus.iniciativa.alerta.porcentaje.inicial.empty" />', 100, 340);
						return false;
					}
					else if (convertirNumeroFormateadoToNumero(document.editarIniciativaEstatusForm.porcentajeInicial.value) < 0)
					{
						showAlert('<vgcutil:message key="jsp.editar.estatus.iniciativa.alerta.porcentaje.inicial.invalido" />', 100, 340);
						return false;
					}

					if (document.editarIniciativaEstatusForm.porcentajeFinal.value == null || document.editarIniciativaEstatusForm.porcentajeFinal.value == "" || document.editarIniciativaEstatusForm.porcentajeFinal.value.length == 0)
					{
						showAlert('<vgcutil:message key="jsp.editar.estatus.iniciativa.alerta.porcentaje.final.empty" />', 100, 340);
						return false;
					}
					else if (convertirNumeroFormateadoToNumero(document.editarIniciativaEstatusForm.porcentajeFinal.value) > 100)
					{
						showAlert('<vgcutil:message key="jsp.editar.estatus.iniciativa.alerta.porcentaje.final.invalido" />', 100, 340);
						return false;
					}
				}
					
				return true;
			}

			function refrescarPadre()
			{
				window.opener.actualizar();
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarIniciativaEstatusForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/estatus/guardar" focus="nombre">

			<html:hidden property="id" />
			<html:hidden property="sistema" />
			
			<bean:define id="esSistema" toScope="page">
				<logic:notEmpty name="editarIniciativaEstatusForm" property="sistema">
					<bean:write name="editarIniciativaEstatusForm" property="sistema" />
				</logic:notEmpty>
				<logic:empty name="editarIniciativaEstatusForm" property="sistema">
					false
				</logic:empty>
			</bean:define>

			<vgcinterfaz:contenedorForma width="420px" height="200px" bodyAlign="center" bodyValign="middle" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarIniciativaEstatusForm" property="id" value="0">
						<vgcutil:message key="jsp.editar.estatus.iniciativa.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarIniciativaEstatusForm" property="id" value="0">
						<vgcutil:message key="jsp.editar.estatus.iniciativa.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left" width="180px">
							<vgcutil:message key="jsp.editar.estatus.iniciativa.ficha.nombre" />
						</td>
						<td colspan="3">
							<html:text property="nombre" size="36" maxlength="15" styleClass="cuadroTexto" />
						</td>
					</tr>
					<%-- Porcentaje zona verde --%>
					<tr>
						<td align="left" width="180px"><vgcutil:message key="jsp.editar.estatus.iniciativa.ficha.porcentaje.inicial" /></td>
						<td align="left" colspan="3">
							<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px; boder: 0px">
								<tr>
									<td valign="middle" align="left">
										<html:text size="5" property="porcentajeInicial" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esSistema) %>" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
									</td>
									<td valign="middle" align="left">&nbsp;%</td>
									<td width="40px">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
		
					<%-- Porcentaje zona amarilla --%>
					<tr>
						<td align="left" width="180px"><vgcutil:message key="jsp.editar.estatus.iniciativa.ficha.porcentaje.final" /></td>
						<td align="left" colspan="3">
							<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px; boder: 0px">
								<tr>
									<td valign="middle" align="left">
										<html:text size="5" property="porcentajeFinal" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esSistema) %>" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
									</td>
									<td valign="middle" align="left">&nbsp;%</td>
									<td width="40px">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<!-- Bloquear Medicion -->
					<tr>
						<td align="left" width="180px"><vgcutil:message key="jsp.editar.estatus.iniciativa.ficha.bloquear.medicion" /></td>
						<td align="left" colspan="3"><html:checkbox property="bloquearMedicion" styleClass="botonSeleccionMultiple" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"></html:checkbox></td>
					</tr>
					
					<!-- Bloquear Indicadores -->
					<tr>
						<td align="left" width="180px"><vgcutil:message key="jsp.editar.estatus.iniciativa.ficha.bloquear.indicadores" /></td>
						<td align="left" colspan="3"><html:checkbox property="bloquearIndicadores" styleClass="botonSeleccionMultiple" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"></html:checkbox></td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarIniciativaEstatusForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script>
			<logic:equal name="editarIniciativaEstatusForm" property="status" value="4">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarIniciativaEstatusForm" property="status" value="0">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.nuevo.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarIniciativaEstatusForm" property="status" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.no.ok" />', 80, 300);
			</logic:equal>
			<logic:equal name="editarIniciativaEstatusForm" property="status" value="6">
				_setCloseParent = false;
				showAlert('<bean:write name="editarIniciativaEstatusForm" property="respuesta" />', 80, 300);
			</logic:equal>
		</script>

	</tiles:put>
</tiles:insert>