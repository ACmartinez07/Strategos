<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.asignarpesosindicadoresplan.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function guardar() 
			{
				if (validar(document.asignarPesosIndicadoresPlanForm)) 
				{
					window.document.asignarPesosIndicadoresPlanForm.action='<html:rewrite action="/planes/indicadores/asignarPesosIndicadoresPlan" />?funcion=guardar';
					window.document.asignarPesosIndicadoresPlanForm.submit();
				}
			}
			
			function actualizar() 
			{
				window.document.asignarPesosIndicadoresPlanForm.submit();
			}
			
			function cancelar() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/indicadores/asignarPesosIndicadoresPlan" />?funcion=cancelar', null, 'finalizarCancelar()');
			}
			
			function finalizarCancelar() 
			{
				window.close();
			}
			
			function validar(forma) 
			{
				if (!actualizarPesoTotal(true)) 
					return false;	
				if (forma.pesoTotalIndicadores.value != '') 
				{
					if (parseInt(forma.pesoTotalIndicadores.value) == 100) 
						return true;
					else 
					{
						alert('<vgcutil:message key="jsp.asignarpesosindicadoresplan.mensaje.sumapesocien" />');
						return false;
					}
				} 
				else  
					return true;
			}
			
			function actualizarPesoTotal(validar) 
			{
				var total = 0;
				var hayPesos = false;
				var totalIndicadores = 0;
				var totalConPeso = 0;
				for (index = 0; index < window.document.asignarPesosIndicadoresPlanForm.elements.length; index++) 
				{
					if (window.document.asignarPesosIndicadoresPlanForm.elements[index].name.indexOf('pesoIndicador') > -1) 
					{
						totalIndicadores++;
						if ((window.document.asignarPesosIndicadoresPlanForm.elements[index].value != null) && (window.document.asignarPesosIndicadoresPlanForm.elements[index].value != '')) 
						{
							hayPesos = true;
							total = total + convertirNumeroFormateadoToNumero(window.document.asignarPesosIndicadoresPlanForm.elements[index].value, false);
							totalConPeso++;
						}
					}
				}
			
				if (hayPesos) 
					window.document.asignarPesosIndicadoresPlanForm.pesoTotalIndicadores.value = formatearNumero(total, false, 2);
				else 
					window.document.asignarPesosIndicadoresPlanForm.pesoTotalIndicadores.value = '';
				
				if ((validar != null) && (validar == true)) 
				{
					if ((totalConPeso > 0) && (totalConPeso != totalIndicadores)) 
					{
						alert('<vgcutil:message key="jsp.asignarpesosindicadoresplan.mensaje.noindicadorsinpeso" />');
						return false;
					} 
					else 
						return true;
				}
			}
			
			function limpiarPesos() 
			{
				if (!confirm('<vgcutil:message key="jsp.asignarpesosindicadoresplan.mensaje.confirmarlimpiarpesos" />')) 
					return;
			
				for (index = 0; index < window.document.asignarPesosIndicadoresPlanForm.elements.length; index++) 
				{
					if (window.document.asignarPesosIndicadoresPlanForm.elements[index].name.indexOf('pesoIndicador') > -1) 
						window.document.asignarPesosIndicadoresPlanForm.elements[index].value = '';
				}
				actualizarPesoTotal(false);
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/indicadores/asignarPesosIndicadoresPlan" styleClass="formaHtml" onsubmit="if (validar(document.asignarPesosIndicadoresPlanForm)) return true; else return false;">

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.asignarpesosindicadoresplan.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.asignarpesosindicadoresplan.organizacion" /></b></td>
							<td><logic:notEmpty scope="session" name="asignarPesosIndicadoresPlanForm" property="organizacionNombre">
								<bean:write scope="session" name="asignarPesosIndicadoresPlanForm" property="organizacionNombre" />
							</logic:notEmpty><logic:empty scope="session" name="asignarPesosIndicadoresPlanForm" property="organizacionNombre">
								<vgcutil:message key="jsp.asignarpesosindicadoresplan.noaplica" />
							</logic:empty></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.asignarpesosindicadoresplan.plan" /></b></td>
							<td><logic:notEmpty scope="session" name="asignarPesosIndicadoresPlanForm" property="planNombre">
								<bean:write scope="session" name="asignarPesosIndicadoresPlanForm" property="planNombre" />
							</logic:notEmpty><logic:empty scope="session" name="asignarPesosIndicadoresPlanForm" property="planNombre">
								<vgcutil:message key="jsp.asignarpesosindicadoresplan.noaplica" />
							</logic:empty></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.asignarpesosindicadoresplan.perspectiva" /></b></td>
							<td><logic:notEmpty scope="session" name="asignarPesosIndicadoresPlanForm" property="perspectivaNombre">
								<bean:write scope="session" name="asignarPesosIndicadoresPlanForm" property="perspectivaNombre" />
							</logic:notEmpty><logic:empty scope="session" name="asignarPesosIndicadoresPlanForm" property="perspectivaNombre">
								<vgcutil:message key="jsp.asignarpesosindicadoresplan.noaplica" />
							</logic:empty></td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Encabezados --%>
				<div style="height: 390px; overflow: auto;"><vgcinterfaz:visorLista namePaginaLista="asignarPesosIndicadoresPlan.paginaIndicadores" nombre="visorIndicadores" messageKeyNoElementos="jsp.asignarpesosindicadoresplan.noindicadores" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

					<%-- Encabezados --%>
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="580px">
						<vgcutil:message key="jsp.asignarpesosindicadoresplan.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="peso" width="100px">
						<vgcutil:message key="jsp.asignarpesosindicadoresplan.columna.peso" />
					</vgcinterfaz:columnaVisorLista>

					<%-- Filas --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="indicador">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="indicador" property="indicadorId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="indicador" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="peso" align="center">
							<input style="text-align: right" onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 2, false);" onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 3, false);actualizarPesoTotal();" onblur="validarEntradaNumeroEventoOnBlur(this, event, 3, false)" type="text" name="pesoIndicador<bean:write name="indicador" property="indicadorId" />" value="<bean:write name="indicador" property="peso" />" class="cuadroTexto" size="10" maxlength="10" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista></div>
				<table width="100%">
					<tr>
						<td>
						<hr width="100%">
						</td>
					</tr>
					<tr class="mouseFueraCuerpoListView">
						<td align="right"><b><vgcutil:message key="jsp.asignarpesosindicadoresplan.pesototal" />:</b>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="pesoTotalIndicadores" class="cuadroTexto" size="10" maxlength="10" disabled="disabled" style="text-align: right; color: black;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<table width="100%">
						<tr>
							<td align="right"><img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:limpiarPesos();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="jsp.asignarpesosindicadoresplan.limpiarpesos" /></a>&nbsp;&nbsp;<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10"> <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<img
								src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script language="JavaScript" type="text/javascript">

			for (var index = 0; index < window.document.asignarPesosIndicadoresPlanForm.elements.length; index++) 
			{
				if (window.document.asignarPesosIndicadoresPlanForm.elements[index].name.indexOf('pesoIndicador') > -1) 
				{
					var numero = window.document.asignarPesosIndicadoresPlanForm.elements[index].value;
					var numeroFormateado = formatearNumero(numero, false, 2);
					window.document.asignarPesosIndicadoresPlanForm.elements[index].value = numeroFormateado;
				}
			}

			actualizarPesoTotal(true);

			<logic:notEmpty scope="request" name="cerrarAsignarPesosIndicadoresPlan">
				<logic:notEmpty name="asignarPesosIndicadoresPlanForm" property="funcionCierre">
					window.opener.<bean:write name="asignarPesosIndicadoresPlanForm" property="funcionCierre" />;
				</logic:notEmpty>
				window.close();
			</logic:notEmpty>

		</script>

	</tiles:put>

</tiles:insert>
