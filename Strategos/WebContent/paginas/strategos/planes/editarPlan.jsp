<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (04/11/2012) --%>

<bean:define id="tipoPlanEstrategico">
	<bean:write name="editarPlanForm" property="tipoPlanEstrategico" />
</bean:define>
<bean:define id="tipoPlanOperativo">
	<bean:write name="editarPlanForm" property="tipoPlanOperativo" />
</bean:define>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarPlanForm" property="planId" value="0">
			<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanEstrategico %>">
				<vgcutil:message key="jsp.editarplan.titulo.nuevo" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.estrategico" />
			</logic:equal>
			<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanOperativo %>">
				<vgcutil:message key="jsp.editarplan.titulo.nuevo" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.operativo" />
			</logic:equal>
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarPlanForm" property="planId" value="0">
			<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanEstrategico %>">
				<vgcutil:message key="jsp.editarplan.titulo.modificar" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.estrategico" />
			</logic:equal>
			<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanOperativo %>">
				<vgcutil:message key="jsp.editarplan.titulo.modificar" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.operativo" />
			</logic:equal>
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/paginas/strategos/planes/plantillas/plantillasPlanesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesIndicadoresJs.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarPlanForm" property="bloqueado">
				<bean:write name="editarPlanForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarPlanForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			// Inicializar botones de los cuadros Numéricos  
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');

			function validar(forma) 
			{
				if (validateEditarPlanForm(forma)) 
				{
					window.document.editarPlanForm.action = '<html:rewrite action="/planes/guardarPlan"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
			
					// Validación de Clase
					if ((document.editarPlanForm.claseId.value == null) || (document.editarPlanForm.claseId.value == '') || ((document.editarPlanForm.claseId.value == 0))) 
					{
						alert('<vgcutil:message key="jsp.editarplan.validacion.clase.requerida" />');
						return false;
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
					window.document.editarPlanForm.submit();
				}
			}
			
			function cancelar() 
			{
				window.document.editarPlanForm.action = '<html:rewrite action="/planes/cancelarGuardarPlan"/>';
				window.document.editarPlanForm.submit();
				this.close();
			}
			
			function ejecutarPorDefecto(e) 
			{
				if (e.keyCode == 13) 
					guardar();
			}
			
			function seleccionarClaseIndicadores() 
			{
				abrirSelectorClasesIndicadores('editarPlanForm', 'claseIndicadoresNombre', 'claseId', document.editarPlanForm.claseId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');
			}
			
			function limpiarClaseIndicadores() 
			{
				document.editarPlanForm.claseId.value = "";
				document.editarPlanForm.claseIndicadoresNombre.value = "";
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
			
			function establecerCamposEditables() 
			{
				var tienePerspectivas = '<bean:write name="editarPlanForm" property="tienePerspectivas"/>';
				var imagen;
				if (tienePerspectivas == 'true') 
				{
					window.document.editarPlanForm.metodologiaNombre.size = 45;
					window.document.editarPlanForm.planImpactaNombre.size = 45;
					<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
						imagen = document.getElementById("botonSeleccionarPlantilla");
						imagen.width = 0; imagen.height = 0;					
						imagen = document.getElementById("botonLimpiarPlantilla");
						imagen.width = 0; imagen.height = 0;					
						imagen = document.getElementById("botonSeleccionarPlanAsociado");
						imagen.width = 0; imagen.height = 0;					
						imagen = document.getElementById("botonLimpíarPlanAsociado");
						imagen.width = 0; imagen.height = 0;
					</logic:notEqual>
				}
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPlanForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/guardarPlan" focus="nombre">

			<html:hidden property="tipo" />
			<html:hidden property="planId" />
			<html:hidden property="metodologiaId" />
			<html:hidden property="claseId" />
			<html:hidden property="planImpactaId" />
			<html:hidden property="activo" />
			<input type="hidden" name="metodologiaPlanImpacta" value="">
			<input type="hidden" name="planesRutasCompletas">

			<vgcinterfaz:contenedorForma width="430px" height="225px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarPlanForm" property="planId" value="0">
						<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanEstrategico %>">
							<vgcutil:message key="jsp.editarplan.titulo.nuevo" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.estrategico" />
						</logic:equal>
						<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanOperativo %>">
							<vgcutil:message key="jsp.editarplan.titulo.nuevo" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.operativo" />
						</logic:equal>
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarPlanForm" property="planId" value="0">
						<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanEstrategico %>">
							<vgcutil:message key="jsp.editarplan.titulo.modificar" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.estrategico" />
						</logic:equal>
						<logic:equal name="editarPlanForm" property="tipo" value="<%= tipoPlanOperativo %>">
							<vgcutil:message key="jsp.editarplan.titulo.modificar" />&nbsp;<vgcutil:message key="jsp.editarplan.titulo.operativo" />
						</logic:equal>
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos: Nombre --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.nombre" /></td>
						<td colspan="3">
							<html:text style="width: 300px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="45" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" />
						</td>
					</tr>

					<%-- Campo: Año Inicial / Año Final --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.anoinicial" /></td>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
							<tr>
								<td valign="middle" align="center">
									<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="anoInicial" size="4" readonly="true" styleClass="cuadroTexto" />
								</td>
								<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
									<td valign="middle">
										<img id="botonAnoInicial" name="botonAnoInicial" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
									</td>
								</logic:notEqual>
							</tr>
						</table>
						</td>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.anofinal" /></td>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
							<tr>
								<td valign="middle" align="center"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="anoFinal" size="4" readonly="true" styleClass="cuadroTexto" /></td>
								<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
									<td valign="middle"><img id="botonAnoFinal" name="botonAnoFinal" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" /></td>
								</logic:notEqual>
							</tr>
						</table>
						</td>

					</tr>

					<%-- Campo: Revisión --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.revision" /></td>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
							<tr>
								<td valign="middle" align="center"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="revision" size="2" readonly="true" styleClass="cuadroTexto" /></td>
								<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
									<td valign="middle"><img id="botonRevision" name="botonRevision" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" /></td>
								</logic:notEqual>
							</tr>
						</table>
						</td>
					</tr>

					<%-- Campo: Clase por defecto del plan --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.claseindicador" /></td>
						<td colspan="3">
							<html:text style="width: 280px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="claseIndicadoresNombre" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
							<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
								&nbsp;<img style="cursor: pointer" onclick="seleccionarClaseIndicadores();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editarplan.ficha.seleccionarplan" />">
								&nbsp;<img style="cursor: pointer" onclick="limpiarClaseIndicadores();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</logic:notEqual>
						</td>
					</tr>

					<%-- Campo: Plantilla Base --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.plantillaplan" /></td>
						<td colspan="3">
							<html:text style="width: 280px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="metodologiaNombre" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
							<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
								&nbsp;<img style="cursor: pointer" onclick="seleccionarPlantillaBase();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" id="botonSeleccionarPlantilla" title="<vgcutil:message key="jsp.editarplan.ficha.seleccionarplantillabase" />">
								&nbsp;<img style="cursor: pointer" onclick="limpiarPlantillaBase();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" id="botonLimpiarPlantilla" title="<vgcutil:message key="boton.limpiar.alt" />">
							</logic:notEqual>
						</td>
					</tr>

					<%-- Campo: Plan Asociado --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarplan.ficha.planasociado" /></td>
						<td colspan="3">
							<html:text style="width: 280px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="planImpactaNombre" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
							<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
								&nbsp;<img style="cursor: pointer" onclick="seleccionarPlanAsociado();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" id="botonSeleccionarPlanAsociado" title="<vgcutil:message key="jsp.editarplan.ficha.seleccionarplan.asociado" />">
								&nbsp;<img style="cursor: pointer" onclick="limpiarPlanAsociado();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" id="botonLimpíarPlanAsociado" title="<vgcutil:message key="boton.limpiar.alt" />">
							</logic:notEqual>
						</td>
					</tr>

				</table>

				<%-- Cuadros numéricos --%>
				<map name="MapControlUpDown1" id="MapControlUpDown1">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonAnoInicial')" onmouseout="normalAction('botonAnoInicial')"
						onmousedown="iniciarConteoContinuo('anoInicial',9999);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonAnoInicial')" onmouseout="normalAction('botonAnoInicial')"
						onmousedown="iniciarConteoContinuo('anoInicial');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>
				<map name="MapControlUpDown2" id="MapControlUpDown2">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonAnoFinal')" onmouseout="normalAction('botonAnoFinal')"
						onmousedown="iniciarConteoContinuo('anoFinal',9999);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonAnoFinal')" onmouseout="normalAction('botonAnoFinal')"
						onmousedown="iniciarConteoContinuo('anoFinal');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>
				<map name="MapControlUpDown3" id="MapControlUpDown3">
					<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonRevision')" onmouseout="normalAction('botonRevision')"
						onmousedown="iniciarConteoContinuo('revision',99);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
					<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonRevision')" onmouseout="normalAction('botonRevision')"
						onmousedown="iniciarConteoContinuo('revision');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
				</map>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarPlanForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarPlanForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">		
			establecerCamposEditables();
		</script>

	</tiles:put>
</tiles:insert>
