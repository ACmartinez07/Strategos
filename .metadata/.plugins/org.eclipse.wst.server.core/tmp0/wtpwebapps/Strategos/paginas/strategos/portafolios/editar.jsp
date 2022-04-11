<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (08/04/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarPortafolioForm" property="id" value="0">
			<vgcutil:message key="jsp.editarportafolio.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarPortafolioForm" property="id" value="0">
			<vgcutil:message key="jsp.editarportafolio.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarPortafolioForm" property="bloqueado">
				<bean:write name="editarPortafolioForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarPortafolioForm" property="bloqueado">
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
					var url = '?id=' + document.editarPortafolioForm.id.value;
					url = url + '&activo=' + document.editarPortafolioForm.activo.value;
					
					window.document.editarPortafolioForm.action = '<html:rewrite action="/portafolios/guardarPortafolio"/>' + url;
					window.document.editarPortafolioForm.submit();
				}
			}
	
			function validar() 
			{
				if (document.editarPortafolioForm.nombre.value == null || document.editarPortafolioForm.nombre.value == "" || document.editarPortafolioForm.nombre.value.length == 0)
				{
					showAlert('<vgcutil:message key="jsp.editarportafolio.alerta.nombre.empty" />', 100, 340);
					return false;
				}
					
				return true;
			}
	
			function refrescarPadre()
			{
				window.opener.refrescarPortafolio();
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPortafolioForm.nombre" />
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/portafolios/guardarPortafolio" focus="nombre">

			<html:hidden property="id" />
			
			<vgcinterfaz:contenedorForma width="500px" height="330px" bodyAlign="center" bodyValign="middle" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarPortafolioForm" property="id" value="0">
						<vgcutil:message key="jsp.editarportafolio.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarPortafolioForm" property="id" value="0">
						<vgcutil:message key="jsp.editarportafolio.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos fichaDatostablaGeneral" cellpadding="4" cellspacing="10" border="0" >
					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left" width="120px">
							<vgcutil:message key="jsp.editarportafolio.ficha.nombre" />
						</td>
						<td align="left" colspan="3">
							<html:text property="nombre" size="44" maxlength="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" />
						</td>
					</tr>
					<tr>
						<td align="left" width="80px">&nbsp;</td>
						<td colspan="3">
							<div>
								<table style="border-spacing: 0px; padding: 0px; width: 324px; border-style:solid; border-width:1px; border-color:#666666; font-family:Verdana; font-size:11; color:#666666; text-decoration:none;">
									<tr>
										<td colspan="2"><b>&nbsp;<vgcutil:message key="jsp.editarportafolio.ficha.condicion" /></b></td>
									</tr>
									<tr>
										<td><html:radio property="activo" value="1" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/><vgcutil:message key="jsp.editarportafolio.ficha.condicion.activo" /></td>
										<td><html:radio property="activo" value="2" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/><vgcutil:message key="jsp.editarportafolio.ficha.condicion.inactivo" /></td>
									</tr>
								</table>
							</div>
						</td>
						<td align="right">&nbsp;</td>
						<td width="7%" align="left">&nbsp;</td>
					</tr>
					
					<!-- Ancho y Alto -->
					<tr>
						<td align="left" width="80px">&nbsp;</td>
						<td colspan="3">
							<div>
								<table style="border-spacing: 3px; padding: 6px; width: 324px; border-style:solid; border-width:1px; border-color:#666666; font-family:Verdana; font-size:11; color:#666666; text-decoration:none;">
									<tr>
										<td colspan="2">
											<b><vgcutil:message key="jsp.editarportafolio.ficha.celdas.tamano" /></b>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<hr style="width: 100%;">
										</td>
									</tr>
									<tr>
										<td><b>&nbsp;<vgcutil:message key="jsp.editarpaginas.ficha.ancho" /></b></td>
										<td><b>&nbsp;<vgcutil:message key="jsp.editarpaginas.ficha.alto" /></b></td>
									</tr>
									<tr>
										<td>
											<table class="bordeFichaDatos fichaDatostablaGeneral">
												<tr>
													<td style="text-align:left; width: 30px;">
														<html:text property="ancho" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
													</td>
													<logic:notEqual name="editarPortafolioForm" property="bloqueado" value="true">
														<td style="text-align:left;">
															<img id="boton2" name="boton2" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" />
														</td>
													</logic:notEqual>
													<logic:equal name="editarPortafolioForm" property="bloqueado" value="true">
														<td style="text-align:left;">&nbsp;</td>
													</logic:equal>
												</tr>
											</table>
											<map name="MapControlUpDown2" id="MapControlUpDown2">
												<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton2')" onmouseout="normalAction('boton2')" onmousedown="iniciarConteoContinuo('ancho', 999, 100);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
												<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton2')" onmouseout="normalAction('boton2')" onmousedown="iniciarConteoContinuo('ancho', 999, 100);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
											</map>
										</td>
										<td>
											<table class="bordeFichaDatos fichaDatostablaGeneral">
												<tr>
													<td valign="middle" align="left" width="30px">
														<html:text property="alto" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
													</td>
													<logic:notEqual name="editarPortafolioForm" property="bloqueado" value="true">
														<td style="text-align:left;">
															<img id="boton4" name="boton4" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown4" />
														</td>
													</logic:notEqual>
													<logic:equal name="editarPortafolioForm" property="bloqueado" value="true">
														<td align="left">&nbsp;</td>
													</logic:equal>
												</tr>
											</table>
											<map name="MapControlUpDown4" id="MapControlUpDown4">
												<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton4')" onmouseout="normalAction('boton4')" onmousedown="iniciarConteoContinuo('alto', 999, 100);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
												<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton4')" onmouseout="normalAction('boton4')" onmousedown="iniciarConteoContinuo('alto', 999, 100);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
											</map>
										</td>
									</tr>
								</table>
							</div>
						</td>
						<td align="right">&nbsp;</td>
						<td width="7%" align="left">&nbsp;</td>
					</tr>

					<!-- Campo frecuencia -->
					<tr>
						<td align="left" width="80px"><vgcutil:message key="jsp.editarportafolio.ficha.frecuencia" /></td>
						<td colspan="3" align="left">
							<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" size="1">
								<html:option value=""></html:option>
								<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
							</html:select>
						</td>
						<td align="right">&nbsp;</td>
						<td width="7%" align="left">&nbsp;</td>
					</tr>
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarPortafolioForm" property="bloqueado" value="true">
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
			<logic:equal name="editarPortafolioForm" property="status" value="4">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarPortafolioForm" property="status" value="0">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.nuevo.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarPortafolioForm" property="status" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.no.ok" />', 80, 300);
			</logic:equal>
			<logic:equal name="editarPortafolioForm" property="status" value="6">
				_setCloseParent = false;
				showAlert('<bean:write name="editarPortafolioForm" property="respuesta" />', 80, 300);
			</logic:equal>
		</script>

	</tiles:put>
</tiles:insert>
