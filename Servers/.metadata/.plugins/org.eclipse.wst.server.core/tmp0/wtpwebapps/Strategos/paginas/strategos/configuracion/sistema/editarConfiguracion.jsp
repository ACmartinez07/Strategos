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
		<vgcutil:message key="jsp.configuracion.sistema.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script>
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			var _setCloseParent = false;
			var _setAvance = null;
			var _setPresupuesto = null;
			var _setEficacia = null;
			var _setEficiencia = null;

			var _setAnteponerAvance = null;
			var _setAnteponerPresupuesto = null;
			var _setAnteponerEficacia = null;
			var _setAnteponerEficiencia = null;
			
			function cancelar() 
			{
				window.close();						
			}
			
			function salvar() 
			{
				if (!validar())
					return;
				
				activarBloqueoEspera();
	
				document.editarConfiguracionSistemaForm.action = '<html:rewrite action="/configuracion/sistema/editar"/>?funcion=salvar';
				document.editarConfiguracionSistemaForm.submit();
			}

			function validar()
			{
				if (document.editarConfiguracionSistemaForm.iniciativaNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.configuracion.sistema.iniciativas.mensaje.nombre.vacio" /> ');
					document.editarConfiguracionSistemaForm.iniciativaNombre.focus();
					return false;
				}
	
				if (_setAvance && _setAnteponerAvance && document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.configuracion.sistema.iniciativas.mensaje.avance.nombre.vacio" /> ');
					document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.focus();
					return false;
				}
				else
					document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.value = '<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.avance.nombre" />';
				
				if (_setPresupuesto && document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.configuracion.sistema.iniciativas.mensaje.presupuesto.nombre.vacio" /> ');
					document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.focus();
					return false;
				}
				else
					document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.value = '<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.presupuesto.nombre" />';
				
				if (_setEficacia && document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.configuracion.sistema.iniciativas.mensaje.eficacia.nombre.vacio" /> ');
					document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.focus();
					return false;
				}
				else
					document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.value = '<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.eficacia.nombre" />';
	
				if (_setEficiencia && document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.configuracion.sistema.iniciativas.mensaje.eficiencia.nombre.vacio" /> ');
					document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.focus();
					return false;
				}
				else
					document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.value = '<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.eficiencia.nombre" />';
				
				return true;
			}
		
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function mostrar_click(value, tipo)
			{
				if (tipo == 1)
				{
					_setAvance = value;
					if (value && _setAnteponerAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = false;
					else if (value && !_setAnteponerAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
					else if (!value && !_setAnteponerAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
					else if (!value)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
					
					if (!value)
					{
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceAnteponer[0].checked = true;
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceAnteponer[1].checked = false;
						anteponer_click(value, tipo);
					}
				}
				
				if (tipo == 2)
				{
					_setPresupuesto = value;
					if (value == true)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.disabled = false;
					else
						document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.disabled = true;
				}
				
				if (tipo == 3)
				{
					_setEficacia = value;
					if (value == true)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.disabled = false;
					else
						document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.disabled = true;
				}
				
				if (tipo == 4)
				{
					_setEficiencia = value;
					if (value == true)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.disabled = false;
					else
						document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.disabled = true;
				}
			}
			
			function anteponer_click(value, tipo)
			{
				if (tipo == 1)
				{
					_setAnteponerAvance = value;
					if (value && _setAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = false;
					else if (value && !_setAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
					else if (!value && !_setAvance)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
					else if (!value)
						document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
				}
			}
			
			function onmousedownMascaraNumeroUp(objeto, minimo, maximo) 
			{
				if (typeof (minimo) == "undefined")
					minimo = 1;
				if (typeof (maximo) == "undefined")
					maximo = null;
				
				iniciarConteoContinuo(objeto, maximo, minimo);
				aumentoConstante();
			}
	
			function onmousedownMascaraNumeroDown(objeto, minimo, maximo) 
			{
				if (typeof (minimo) == "undefined")
					minimo = 1;
				if (typeof (maximo) == "undefined")
					maximo = null;
				
				iniciarConteoContinuo(objeto, maximo, minimo);
				decrementoConstante();
			}
			
			
			function guardarServicio() 
			{
					 
				window.document.editarConfiguracionSistemaForm.action = '<html:rewrite action="/configuracion/salvarConfiguracionServicio"/>';
				window.document.editarConfiguracionSistemaForm.submit();
			}
			
			
			$(function() 
			{
				$('#txtRestriccionUsoHoraDesde').timepicker({'timeFormat': 'h:i A' });
			});
			
			
			
			
		</script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/configuracion/sistema/editar" styleClass="formaHtml" enctype="multipart/form-data" method="POST">		
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma height="550px" width="650px" bodyAlign="center" bodyValign="middle" marginTop="5px">

				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.configuracion.sistema.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<html:hidden property="hora" />

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="440px" width="600px" nombre="configuracionSistema">

					<%-- Panel: Indicadores --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="indicadores">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.indicadores" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Nivel del duppont de los indicadores formula-->
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.indicadores.formula" /></b></td>
							</tr>
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td valign="top" align="left"><vgcutil:message key="jsp.configuracion.sistema.indicadores.formula.nivel" /></td>
								<td valign="top" colspan="2">
									<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
										<tr>
											<td valign="middle" align="left">
												<html:text property="indicadorNivel" size="1" readonly="true" styleClass="cuadroTexto" />
											</td>
											<td valign="middle">
												<img id="botonNumerodeNiveles" name="botonNumerodeNiveles" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
											</td>
										</tr>
									</table>
								</td>	
							</tr>
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
						</table>
						<map name="MapControlUpDown1" id="MapControlUpDown1">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonNumerodeNiveles')" onmouseout="normalAction('botonNumerodeNiveles')" onmousedown="onmousedownMascaraNumeroUp('indicadorNivel')" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonNumerodeNiveles')" onmouseout="normalAction('botonNumerodeNiveles')" onmousedown="onmousedownMascaraNumeroDown('indicadorNivel')" onmouseup="finalizarConteoContinuo()" />
						</map>
					
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Iniciativas --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="iniciativas">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.iniciativas" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Nombre Iniciativa-->
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.nombre" /> : </td>
								<td valign="top" colspan="2"><html:text name="editarConfiguracionSistemaForm" property="iniciativaNombre" size="35" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							
							<!-- Indicador de Avance -->
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.avance" /></b></td>
							</tr>
							<!-- 
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceMostrar" value="false" onclick="mostrar_click(false, 1)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceMostrar" value="true" onclick="mostrar_click(true, 1)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.si" />
									</html:radio>
								</td>
							</tr>
							 -->
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.anteponer.nombre" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceAnteponer" value="false" onclick="anteponer_click(false, 1)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.anteponer.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceAnteponer" value="true" onclick="anteponer_click(true, 1)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.anteponer.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.nombre" /> : </td>
								<td valign="top" colspan="2"><html:text name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceNombre" size="35" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							
							<!-- Indicador de Presupuesto -->
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.presupuesto" /></b></td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorPresupuestoMostrar" value="false" onclick="mostrar_click(false, 2)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorPresupuestoMostrar" value="true" onclick="mostrar_click(true, 2)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.nombre" /> : </td>
								<td valign="top" colspan="2"><html:text name="editarConfiguracionSistemaForm" property="iniciativaIndicadorPresupuestoNombre" size="35" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Indicador de Eficacia -->
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.eficacia" /></b></td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficaciaMostrar" value="false" onclick="mostrar_click(false, 3)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficaciaMostrar" value="true" onclick="mostrar_click(true, 3)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.nombre" /> : </td>
								<td valign="top" colspan="2"><html:text name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficaciaNombre" size="35" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Indicador de Eficiencia -->
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.eficiencia" /></b></td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficienciaMostrar" value="false" onclick="mostrar_click(false, 4)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficienciaMostrar" value="true" onclick="mostrar_click(true, 4)">
										<vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.crear.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.iniciativas.indicador.nombre" /> : </td>
								<td valign="top" colspan="2"><html:text name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficienciaNombre" size="35" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							
						</table>
					</vgcinterfaz:panelContenedor>
					
					<%-- Panel: Plan --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="planes">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.planes" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Ocultar Logro Parcial o Anual -->
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.planes.logro" /></b></td>
							</tr>
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.planes.logro.anual.ocultar" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoLogroAnualMostrar" value="false">
										<vgcutil:message key="jsp.configuracion.sistema.planes.logro.anual.ocultar.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoLogroAnualMostrar" value="true">
										<vgcutil:message key="jsp.configuracion.sistema.planes.logro.anual.ocultar.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.planes.logro.parcial.ocultar" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoLogroParcialMostrar" value="false">
										<vgcutil:message key="jsp.configuracion.sistema.planes.logro.parcial.ocultar.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoLogroParcialMostrar" value="true">
										<vgcutil:message key="jsp.configuracion.sistema.planes.logro.parcial.ocultar.si" />
									</html:radio>
								</td>
							</tr>

							<!-- Ocultar Alerta Parcial o Anual -->
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.planes.alerta" /></b></td>
							</tr>
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.planes.alerta.anual.ocultar" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoAlertaAnualMostrar" value="false">
										<vgcutil:message key="jsp.configuracion.sistema.planes.alerta.anual.ocultar.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoAlertaAnualMostrar" value="true">
										<vgcutil:message key="jsp.configuracion.sistema.planes.alerta.anual.ocultar.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.planes.alerta.parcial.ocultar" /> : </td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoAlertaParcialMostrar" value="false">
										<vgcutil:message key="jsp.configuracion.sistema.planes.alerta.parcial.ocultar.no" />
									</html:radio>
								</td>
								<td valign="top">
									<html:radio name="editarConfiguracionSistemaForm" property="planObjetivoAlertaParcialMostrar" value="true">
										<vgcutil:message key="jsp.configuracion.sistema.planes.alerta.parcial.ocultar.si" />
									</html:radio>
								</td>
							</tr>
							<tr>
								<td colspan="3" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							<tr>
								<td align="left" colspan="3" valign="top">&nbsp;</td>
							</tr>
							
						</table>
						
					</vgcinterfaz:panelContenedor>
					
					<%-- Panel: Responsable --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="responsable">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.responsables" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td>
									<table class="panelContenedor panelContenedorTabla">
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.responsable.enviar" /></b></td>
										</tr>
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.seguimiento" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimiento" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimiento" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.fijarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMeta" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMeta" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.lograrmeta" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMeta" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMeta" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMeta" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMeta" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarejecutado" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutado" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutado" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.notificacion" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableNegativo" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableNegativo" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
					</vgcinterfaz:panelContenedor>
					
					
					
					<%-- Panel: Inventario --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="inventario">
					
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.inventario" />
						</vgcinterfaz:panelContenedorTitulo>
						
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td>
									<table class="panelContenedor panelContenedorTabla">
										
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.fecha.programado" /></b></td>
										</tr>
										
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.titulo" /> : </td>
											<td colspan="3" valign="top">
												
												<html:text name="editarConfiguracionSistemaForm" property="titulo" maxlength="70" size="35">
													
												</html:text>
											</td>
										</tr>
										
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.descripcion" /> : </td>
											<td colspan="3" valign="top">
												
																						
											   <html:textarea name="editarConfiguracionSistemaForm" property="texto" rows="3" cols="38" styleClass="cuadroTexto">
													
											   </html:textarea>
											</td>
										</tr>
																				
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										
										
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.responsable.enviar" /></b></td>
										</tr>
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.seguimiento" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.fijarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.lograrmeta" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarejecutado" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.notificacion" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableNegativoInv" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableNegativoInv" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
						
					</vgcinterfaz:panelContenedor>
					
					
					<%-- Panel: Correo iniciativa --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="correo_inventario">
					
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.configuracion.sistema.correo.iniciativa" />
						</vgcinterfaz:panelContenedorTitulo>
						
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td>
									<table class="panelContenedor panelContenedorTabla">
										
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.fecha.programado" /></b></td>
										</tr>
										
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.titulo" /> : </td>
											<td colspan="3" valign="top">
												
												<html:text name="editarConfiguracionSistemaForm" property="titulo1" maxlength="70" size="35">
													
												</html:text>
											</td>
										</tr>
										
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.descripcion" /> : </td>
											<td colspan="3" valign="top">
												
																						
											   <html:textarea name="editarConfiguracionSistemaForm" property="texto1" rows="3" cols="38" styleClass="cuadroTexto">
													
											   </html:textarea>
											</td>
										</tr>
										
										<tr>
											<td valign="top" align="left">
														<vgcutil:message key="jsp.config.sistema.dia.configurar" />
														</td>
											<td valign="top" align="left">
												<html:select name="editarConfiguracionSistemaForm" property="dia1" styleClass="cuadroTexto" >
													<html:option value="" />
													<html:option value="1" />
													<html:option value="2" />
													<html:option value="3" />
													<html:option value="4" />
													<html:option value="5" />
													<html:option value="6" />
													<html:option value="7"/>
													<html:option value="8" />
													<html:option value="9" />
													<html:option value="10" />
													<html:option value="11" />
													<html:option value="12" />
													<html:option value="13" />
													<html:option value="14" />
													<html:option value="15" />
													<html:option value="16" />
													<html:option value="17" />
													<html:option value="18" />
													<html:option value="19" />
													<html:option value="20" />
													<html:option value="21" />
													<html:option value="22" />
													<html:option value="23" />
													<html:option value="24" />
													<html:option value="25" />
													<html:option value="26" />
													<html:option value="27" />
													<html:option value="28" />
													<html:option value="29" />
													<html:option value="30" />
													<html:option value="31" />
												</html:select>
												
											
											</td>
										</tr>
										
										<tr>
											<td valign="top" align="left">
														<vgcutil:message key="jsp.config.sistema.hora.configurar" />
														</td>
											<td valign="top" align="left">
											
											
												<html:text name="editarConfiguracionSistemaForm" property="hora1" styleClass="cuadroTexto" maxlength="5" size="5">
												</html:text>
												<br>			
											</td>
										</tr>										
																				
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										
										
										<tr>
											<td align="left" colspan="3" valign="top"><b><vgcutil:message key="jsp.configuracion.sistema.responsable.enviar" /></b></td>
										</tr>
										<tr>
											<td colspan="3" valign="top"><hr width="100%"></td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.seguimiento" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv1" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableSeguimientoInv1" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.fijarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv1" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableFijarMetaInv1" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.lograrmeta" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv1" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableLograrMetaInv1" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarmeta" /> : </td>
											<td align="right"valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv1" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarMetaInv1" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
												</html:radio>
											</td>
										</tr>
										<tr>
											<td align="left" valign="top"><vgcutil:message key="jsp.configuracion.sistema.responsable.cargarejecutado" /> : </td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv1" value="false">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.no" />
												</html:radio>
											</td>
											<td align="right" valign="top">
												<html:radio name="editarConfiguracionSistemaForm" property="enviarResponsableCargarEjecutadoInv1" value="true">
													<vgcutil:message key="jsp.configuracion.sistema.responsable.enviar.si" />
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
			_setAvance = true;
			_setPresupuesto = false;
			_setEficacia = false;
			_setEficiencia = false;

			_setAnteponerAvance = false;
			_setAnteponerPresupuesto = true;
			_setAnteponerEficacia = true;
			_setAnteponerEficiencia = true;
			
			// Configuración salvada
			<logic:equal name="editarConfiguracionSistemaForm" property="status" value="2">
				_setCloseParent = true;
			</logic:equal>
			
			<logic:notEqual name="editarConfiguracionSistemaForm" property="status" value="2">
				<logic:equal name="editarConfiguracionSistemaForm" property="iniciativaIndicadorPresupuestoMostrar" value="true">
					_setPresupuesto = true;
				</logic:equal>
				
				<logic:equal name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficaciaMostrar" value="true">
					_setEficacia = true;
				</logic:equal>
	
				<logic:equal name="editarConfiguracionSistemaForm" property="iniciativaIndicadorEficienciaMostrar" value="true">
					_setEficiencia = true;
				</logic:equal>
			
				<logic:equal name="editarConfiguracionSistemaForm" property="iniciativaIndicadorAvanceAnteponer" value="true">
					_setAnteponerAvance = true;
				</logic:equal>
			</logic:notEqual>
			
			if (!_setAvance)
				document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
			else if (!_setAnteponerAvance)
				document.editarConfiguracionSistemaForm.iniciativaIndicadorAvanceNombre.disabled = true;
			
			if (!_setPresupuesto)
				document.editarConfiguracionSistemaForm.iniciativaIndicadorPresupuestoNombre.disabled = true;
			
			if (!_setEficacia)
				document.editarConfiguracionSistemaForm.iniciativaIndicadorEficaciaNombre.disabled = true;
			
			if (!_setEficiencia)
				document.editarConfiguracionSistemaForm.iniciativaIndicadorEficienciaNombre.disabled = true;
				
		</script>
	</tiles:put>
</tiles:insert>
