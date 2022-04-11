<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.copiarplan.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/paginas/strategos/planes/plantillas/plantillasPlanesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesIndicadoresJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<script language="Javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			// Inicializar botones de los cuadros Numéricos  
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');           
			var _setCloseParent = false;
			
			function validar(forma) 
			{
				if (validateEditarPlanForm(forma)) {
					window.document.editarPlanForm.action = '<html:rewrite action="/planes/guardarCopiaPlan"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
			
					// Validación de Clase
					if (document.editarPlanForm.crearClaseAutomaticamente[1].checked)
					{
						if ((document.editarPlanForm.claseId.value == null) || (document.editarPlanForm.claseId.value == '') || ((document.editarPlanForm.claseId.value == 0))) 
						{
							alert('<vgcutil:message key="jsp.editarplan.validacion.clase.requerida" />');
							return false;
						}
					}
			
					// Validación de la Plantilla
					if ((document.editarPlanForm.metodologiaId.value == null) || (document.editarPlanForm.metodologiaId.value == '') || (document.editarPlanForm.metodologiaId.value == '0')) 
					{
						alert('<vgcutil:message key="jsp.editarplan.validacion.metodologia.requerida" />');
						return false;
					} 
					else 
						return true;
				} 
				else 
					return false;
			}
			
			function guardar() 
			{
				if (validar(document.editarPlanForm)) 
				{
					activarBloqueoEspera();
					
					window.document.editarPlanForm.action = '<html:rewrite action="/planes/guardarCopiaPlan"/>?copiar=true';
					window.document.editarPlanForm.submit();
				}
			}
			
			function cancelar() 
			{
				this.close();
			}
			
			function ejecutarPorDefecto(e) 
			{
				if (e.keyCode == 13) 
					guardar();
			}
			
			function seleccionarClaseIndicadores() 
			{
				if (document.editarPlanForm.crearClaseAutomaticamente[1].checked)
					abrirSelectorClasesIndicadores('editarPlanForm', 'claseIndicadoresNombre', 'claseId', document.editarPlanForm.claseId.value, document.editarPlanForm.organizacionDestinoId.value);
			}
			
			function limpiarClaseIndicadores() 
			{
				if (document.editarPlanForm.crearClaseAutomaticamente[1].checked)
				{
					document.editarPlanForm.claseId.value = "";
					document.editarPlanForm.claseIndicadoresNombre.value = "";
				}
			}
			
			function limpiarPlantillaBase() 
			{				
				document.editarPlanForm.metodologiaId.value = "";
				document.editarPlanForm.metodologiaNombre.value = "";
				limpiarPlanAsociado();
			}
			
			function seleccionarPlantillaBase() 
			{							
				abrirSelectorPlantillasPlanes('editarPlanForm', 'metodologiaNombre', 'metodologiaId', document.editarPlanForm.metodologiaId.value);
				limpiarPlanAsociado();
			}
			
			function limpiarPlanAsociado() 
			{				
				document.editarPlanForm.planImpactaId.value = "";
				document.editarPlanForm.planImpactaNombre.value = "";
			}
						
			function seleccionarPlanAsociado() 
			{
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true';
				abrirSelectorPlanes('editarPlanForm', 'planImpactaNombre', 'planImpactaId', 'planesRutasCompletas', 'getMetodologiaPlanImpacta()', null, queryStringFiltros);
			}
			
			function getMetodologiaPlanImpacta() 
			{			
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/crearPlan" />?funcion=getMetodologiaPlanImpacta&planImpactaId=' + document.editarPlanForm.planImpactaId.value, document.editarPlanForm.metodologiaPlanImpacta, 'establecerMetodologiaNuevoPlan()');
			}
			
			function establecerMetodologiaNuevoPlan() 
			{
				if ((document.editarPlanForm.planesRutasCompletas != null) && (document.editarPlanForm.planesRutasCompletas.value != "")) 
					document.editarPlanForm.planImpactaNombre.value = document.editarPlanForm.planesRutasCompletas.value + " / " + document.editarPlanForm.planImpactaNombre.value;
				var arreglo = document.editarPlanForm.metodologiaPlanImpacta.value.split('<bean:write name="editarPlanForm" property="separadorValores" scope="request" />');
				document.editarPlanForm.metodologiaId.value = arreglo[0];
				document.editarPlanForm.metodologiaNombre.value = arreglo[1];
			}
			
			function SeleccionarIndicares()
			{
			}
			
			function eventoOnclickClase() 
			{
				var control = document.editarPlanForm.crearClaseAutomaticamente[0];
			
				if (control.checked) 
					document.editarPlanForm.claseIndicadoresNombre.disabled = true;
				else
					document.editarPlanForm.claseIndicadoresNombre.disabled = false;
			}
			
			function eventoOnclickIndicador()
			{
				var control = document.editarPlanForm.asociarIndicador;
				
				if (control.checked)
				{
					document.editarPlanForm.asociar[1].disabled = false;
					document.editarPlanForm.asociar[0].disabled = false;
				}
				else
				{
					document.editarPlanForm.asociar[1].disabled = true;
					document.editarPlanForm.asociar[0].disabled = true;
				}
			}
			
			function seleccionarOrganizacion() 
			{
				abrirSelectorOrganizaciones('editarPlanForm', 'organizacionDestinoNombre', 'organizacionDestinoId', document.editarPlanForm.organizacionDestinoId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');
			}
			
			function limpiarOrganizacion() 
			{
				document.editarPlanForm.organizacionDestinoId.value = "";
				document.editarPlanForm.organizacionDestinoNombre.value = "";
			}

			function onClose()
			{
				if (_setCloseParent)
				{
					cancelar();
					window.opener.actualizar();
				}
			}

		</script>
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPlanForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/guardarCopiaPlan" focus="nombre">		
		
			<html:hidden property="tipo" />
			<html:hidden property="planId" />
			<html:hidden property="metodologiaId" />
			<html:hidden property="claseId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="organizacionDestinoId" />
			<html:hidden property="planImpactaId" />
			<html:hidden property="activo" />
			<html:hidden property="originalPlanId" />
			<input type="hidden" name="metodologiaPlanImpacta" value="">			
			<input type="hidden" name="planesRutasCompletas" >

			<vgcinterfaz:contenedorForma width="490px" height="290px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.copiarplan.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos: Nombre --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarplan.ficha.nombre" /></td>
						<td colspan="3"><html:text property="nombre" size="43" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
					
					<%-- Campo: Año Inicial / Año Final --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarplan.ficha.anoinicial" /></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="left"><html:text property="anoInicial" size="4" readonly="true" styleClass="cuadroTexto" /></td>
									<td valign="middle"><img id="botonAnoInicial" name="botonAnoInicial" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" /></td>
								</tr>
							</table>
						</td>
						<td align="right"><vgcutil:message key="jsp.copiarplan.ficha.anofinal" /></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="center"><html:text property="anoFinal" size="4" readonly="true" styleClass="cuadroTexto" /></td>
									<td valign="middle"><img id="botonAnoFinal" name="botonAnoFinal" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" /></td>	
								</tr>
							</table>
						</td>
					</tr>

					<%-- Campo: Revisión --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarplan.ficha.revision" /></td>
						<td colspan="3">
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="center"><html:text property="revision" size="2" readonly="true" styleClass="cuadroTexto" /></td>
									<td valign="middle"><img id="botonRevision" name="botonRevision" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" /></td>	
								</tr>
							</table>
						</td>
					</tr>
					
					<%-- Campo: Organización Destino --%>
					<logic:equal name="editarPlanForm" property="copiar" value="true">
						<tr>
							<td align="right">
								<vgcutil:message key="jsp.copiarplan.ficha.organizacion.destino" />
							</td>
							<td colspan="3">
								<html:text property="organizacionDestinoNombre" size="37" readonly="true" maxlength="50" styleClass="cuadroTexto" />
								&nbsp;<img style="cursor: pointer" onclick="seleccionarOrganizacion();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.copiarplan.ficha.seleccionar.organizacion" />">
								&nbsp;<img style="cursor: pointer" onclick="limpiarOrganizacion();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>
						</tr>
					</logic:equal>

					<%-- Campo: Clase por defecto del plan --%>
					<tr>
						<td align="left" valign="top"><vgcutil:message key="jsp.copiarplan.ficha.claseindicador" /></td>
						<td colspan="3" align="left" width="350px">
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="top" colspan="4" align="left">
										<html:radio property="crearClaseAutomaticamente" value="true" onclick="eventoOnclickClase()">
											<vgcutil:message key="jsp.copiarplan.ficha.claseindicador.si" />
										</html:radio>
									</td>
								</tr>
								<tr>
									<td valign="top" align="left">
										<html:radio property="crearClaseAutomaticamente" value="false" onclick="eventoOnclickClase()">
											<vgcutil:message key="jsp.copiarplan.ficha.claseindicador.no" />
										</html:radio>
									</td>
									<td colspan="3">
										&nbsp;<html:text property="claseIndicadoresNombre" size="31" readonly="true" disabled="true" maxlength="50" styleClass="cuadroTexto" />
										&nbsp;<img style="cursor: pointer" onclick="seleccionarClaseIndicadores();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.copiarplan.ficha.seleccionarplan" />">
										&nbsp;<img style="cursor: pointer" onclick="limpiarClaseIndicadores();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<%-- Campo: Plantilla Base --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.copiarplan.ficha.plantillaplan" /></td>
						<td colspan="3">
							<html:text property="metodologiaNombre" size="37" readonly="true" maxlength="50" styleClass="cuadroTexto" />
							&nbsp;<img style="cursor: pointer" onclick="seleccionarPlantillaBase();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.copiarplan.ficha.seleccionarplantillabase" />">
							&nbsp;<img style="cursor: pointer" onclick="limpiarPlantillaBase();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
					</tr>

					<tr>
						<td align="right">
						</td>
						<td colspan="3">
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td>
										<html:checkbox property="asociarIndicador" styleClass="botonSeleccionMultiple" onclick="eventoOnclickIndicador()">
											<vgcutil:message key="jsp.copiarorganizacion.copiarindicadores" />
										</html:checkbox>
										&nbsp;
									</td>
									<td>
										<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
											<tr>
												<td align="left">
													<html:radio property="asociar" value="true" disabled="true">
														<vgcutil:message key="jsp.copiarplan.ficha.claseindicador.asociar" />
													</html:radio>
												</td>
												<td align="left">
													<html:radio property="asociar" value="false" disabled="true">
														<vgcutil:message key="jsp.copiarplan.ficha.claseindicador.copiar" />
													</html:radio>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<html:checkbox property="asociarIniciativa" styleClass="botonSeleccionMultiple">
											<vgcutil:message key="jsp.copiarorganizacion.copiariniciativas" />
										</html:checkbox>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
				</table>

				<%-- Cuadros numéricos --%>
				<map name="MapControlUpDown1" id="MapControlUpDown1">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonAnoInicial')" onmouseout="normalAction('botonAnoInicial')" onmousedown="iniciarConteoContinuo('anoInicial',9999);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonAnoInicial')" onmouseout="normalAction('botonAnoInicial')" onmousedown="iniciarConteoContinuo('anoInicial');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>
				<map name="MapControlUpDown2" id="MapControlUpDown2">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonAnoFinal')" onmouseout="normalAction('botonAnoFinal')" onmousedown="iniciarConteoContinuo('anoFinal',9999);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonAnoFinal')" onmouseout="normalAction('botonAnoFinal')" onmousedown="iniciarConteoContinuo('anoFinal');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>
				<map name="MapControlUpDown3" id="MapControlUpDown3">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonRevision')" onmouseout="normalAction('botonRevision')" onmousedown="iniciarConteoContinuo('revision',99);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonRevision')" onmouseout="normalAction('botonRevision')" onmousedown="iniciarConteoContinuo('revision');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">					
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
					key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarPlanForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<script>
			// Configuración salvada
			<logic:equal name="editarPlanForm" property="status" value="2">
				_setCloseParent = true;
			</logic:equal>
		</script>

	</tiles:put>
</tiles:insert>
