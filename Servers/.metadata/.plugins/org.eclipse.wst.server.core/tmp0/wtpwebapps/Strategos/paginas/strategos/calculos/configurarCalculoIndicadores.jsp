<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (05/03/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configurarcalculoindicadores.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/validateInput.js'/>"></script>

		<bean:define id="origenOrganizaciones">
			<bean:write name="calculoIndicadoresForm" property="origenOrganizaciones" />
		</bean:define>

		<bean:define id="origenIndicadores">
			<bean:write name="calculoIndicadoresForm" property="origenIndicadores" />
		</bean:define>

		<bean:define id="origenPlanes">
			<bean:write name="calculoIndicadoresForm" property="origenPlanes" />
		</bean:define>

		<bean:define id="origenIniciativas">
			<bean:write name="calculoIndicadoresForm" property="origenIniciativas" />
		</bean:define>

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript">
		
			<%-- Inicializar botones de los cuadros Numéricos  --%>
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');

			var pasoActual = 1;
			var _setCloseParent = false;
			var _selectFrecuencia = false;
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (pasoActual) 
				{
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						calcular();
						break;
				}
				if (valido) 
				{
					pasoActual = pasoActual + 1;
					mostrarBotones(pasoActual);
				}
				mostrarTitulo();
			}
			
			function previo() 
			{
				pasoActual = pasoActual - 1;
				mostrarBotones(pasoActual);
				mostrarTitulo();
			}
			
			function crearBoton(nombreBoton, accionBoton)
			{
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
					+ nombreBoton + '</a>';
			
				return boton;
			}
			
			function mostrarBotones(paso) 
			{
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.finalizar.alt" />';
				var accionBotonFinalizar = 'javascript:siguiente();';
			
				switch (paso) 
				{
					case 1:
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;  
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCalculo" nombrePanel="panelPresentacion" />
					break;
					case 2:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCalculo" nombrePanel="panelRango" />
					break;
					case 3:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCalculo" nombrePanel="panelAlcance" />
					break;
					case 4:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCalculo" nombrePanel="panelLog" />
					break;
				}
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = '..:: <vgcutil:message key="jsp.configurarcalculoindicadores.titulo1" /> ' + pasoActual + ' <vgcutil:message key="jsp.configurarcalculoindicadores.titulo2" />';
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;
			}
			
			function validar(forma) 
			{
				res = validateCalculoIndicadoresForm(forma);	
				if (res) 
					return true;
				else 
					return false;
			}
			
			function verDetalles(ocultarConfirmacion) 
			{
				if ((ocultarConfirmacion == null) || (!ocultarConfirmacion)) 
					verdetallescalculo = confirm ('<vgcutil:message key="calcularindicadores.verdetallescalculo.confirm" />');
				else 
					verdetallescalculo = true;
			
				if (verdetallescalculo)
					window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesCalculo','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
			}
			
			function eventoOnclickPorNombre() 
			{
				var forma = document.calculoIndicadoresForm;
			
				if (forma.porNombre.checked) 
				{
					forma.alcance[2].checked = true;
					forma.alcance[0].disabled = true;
				} 
				else 
					forma.alcance[0].disabled = false;
			}
			
			function eventoOnclickAlcance() 
			{
				<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIndicadores %>">
					var forma = document.calculoIndicadoresForm;
					if (forma.porNombre.checked) 
						forma.alcance[0].disabled = true;
					else 
						forma.alcance[0].disabled = false;
				</logic:equal>
			}
			
			function funcionCierreSelectorIndicadores() 
			{
				if (!document.calculoIndicadoresForm.porNombre.checked) 
					document.calculoIndicadoresForm.alcance[0].checked = true;
			}
			
			function seleccionarIndicador() 
			{
				var queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarClase=true';
				abrirSelectorIndicadores('calculoIndicadoresForm', 'nombreIndicador', 'indicadorId', 'indicadorRutasCompletas', 'funcionCierreSelectorIndicadores()', null, null, null, null, null, queryStringFiltros);
			}
			
			function deseleccionarIndicador() 
			{
				var forma = document.calculoIndicadoresForm;
				forma.indicadorId.value = '';
				forma.serieId.value = '';
				forma.nombreIndicador.value = '';
			}
			
			function cancelar() 
			{
				this.close();
			}

			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function calcular() 
			{
				var alcance = 0;
				for (var i = 0; i < document.calculoIndicadoresForm.alcance.length;i++) 
				{
					if (document.calculoIndicadoresForm.alcance[i].checked) 
					{
						alcance = document.calculoIndicadoresForm.alcance[i].value;
						break;
					}
				}
				var indicadorId = '';
				if ((document.calculoIndicadoresForm.indicadorId.value != null) && (document.calculoIndicadoresForm.indicadorId.value != '')) 
				{
					indicadorId = '&indicadorId=' + document.calculoIndicadoresForm.indicadorId.value;
				}
				var porNombre = '&porNombre=false';
				if ((document.calculoIndicadoresForm.porNombre != null) && (document.calculoIndicadoresForm.porNombre.checked)) 
					porNombre = '&porNombre=true&nombreIndicador=' + document.calculoIndicadoresForm.nombreIndicador.value;
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />?funcion=getNumeroIndicadoresPorAlcance&alcance=' + alcance + indicadorId + porNombre, document.calculoIndicadoresForm.numeroIndicadoresAlcance, 'iniciarCalculo()');
			}
			
			function iniciarCalculo() 
			{
				activarBloqueoEspera();
				barraprogNumeroIndicadores = document.calculoIndicadoresForm.numeroIndicadoresAlcance.value;
				if (!_selectFrecuencia)
					document.calculoIndicadoresForm.frecuencia.value = null;
				
				var xml = '?showPresentacion=' + (document.calculoIndicadoresForm.showPresentacion.checked ? 1 : 0);
				document.calculoIndicadoresForm.action = '<html:rewrite action="/calculoindicadores/calcularIndicadores"/>' + xml;
				document.calculoIndicadoresForm.submit();
			}
			
			var barraprogBarraNumero = 0;
			var barraprogNumeroIndicadores = 0;
			var barraprogValorMax = parseInt(<bean:write name="calculoIndicadoresForm" property="mesFinal" />);
			var barraprogValorMin = parseInt(<bean:write name="calculoIndicadoresForm" property="mesInicial" />);
			var barraprogNumeroIteraciones = (barraprogValorMax - barraprogValorMin + 1 );
			var barraprogTiempoEjecucion = parseInt(150 + parseInt(40 * barraprogNumeroIteraciones) );
			var barraprogTiempoRespuesta = parseInt( (barraprogTiempoEjecucion * barraprogNumeroIndicadores * barraprogNumeroIteraciones )/ 32);
			
			function calcularTiemposBarraProgreso() 
			{
				var forma = document.calculoIndicadoresForm;
			
				barraprogNumeroIteraciones = (parseInt( barraprogValorMax ) - parseInt( barraprogValorMin ) + 1 );
				barraprogTiempoEjecucion = parseInt(200 + (20 * barraprogNumeroIteraciones) );
			
				barraprogTiempoRespuesta = parseInt( (barraprogTiempoEjecucion * barraprogNumeroIndicadores )/ 32);
			}
			
			function setValorMaxMinBarraProg(valorMax, valorMin) 
			{
				barraprogValorMax = valorMax;
				barraprogValorMin = valorMin;
			}
			
			function eventoCambioFrecuencia()
			{
				_selectFrecuencia = true;
			}
			
			<%-- Mensaje de error de verificación de rango --%>
			var errmsgrango = '<vgcutil:message key="jsp.configurarcalculoindicadores.errorrango"/>';
		</script>

		<bean:define id="alcanceIndicador" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceIndicador" />
		</bean:define>

		<bean:define id="alcanceClase" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceClase" />
		</bean:define>

		<bean:define id="alcanceOrganizacion" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceOrganizacion" />
		</bean:define>

		<bean:define id="alcanceOrganizacionSeleccionada" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceOrganizacionSeleccionada" />
		</bean:define>

		<bean:define id="alcanceOrganizacionTodas" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceOrganizacionTodas" />
		</bean:define>

		<bean:define id="alcancePlan" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcancePlan" />
		</bean:define>

		<bean:define id="alcancePerspectiva" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcancePerspectiva" />
		</bean:define>

		<bean:define id="alcanceIniciativa" toScope="page">
			<bean:write name="calculoIndicadoresForm" property="alcanceIniciativa" />
		</bean:define>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript externas --%>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/calculoindicadores/calcularIndicadores">

			<%-- Id de las tablas --%>
			<html:hidden property="indicadorId" />
			<html:hidden property="serieId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="claseId" />
			<html:hidden property="puntoEdicion" />
			<input type="hidden" name="indicadorRutasCompletas">
			<input type="hidden" name="mesInicialAnt" value="1" />
			<input type="hidden" name="mesFinalAnt" value="1" />
			<input type="hidden" name="calculado" />
			<input type="hidden" name="numeroIndicadoresAlcance" />

			<vgcinterfaz:contenedorForma width="610px" height="410px" bodyAlign="center" bodyValign="center">
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatos bordeFichaDatostabla">
					<tr height=315px>

						<%-- Imágen del asistente --%>
						<td width="315px"><img src="<html:rewrite page='/paginas/strategos/calculos/imagenes/wzdcalculo.gif'/>" border="0" width="150px" height="310px"></td>

						<td><vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesCalculo" mostrarSelectoresPaneles="false">

							<%-- Panel Presentacion --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
								<table class="tablaSpacing0Padding0Width100Collapse">							
									<tr>
										<td>
											<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
												<tr>
													<td><b><vgcutil:message key="jsp.configurarcalculoindicadores.paso1.texto1" /></b> <vgcutil:message key="jsp.configurarcalculoindicadores.presentacion" /></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<br><br><br>
											<br><br><br>
											<br><br><br>
											<br><br><br>
										</td>
									</tr>
									<tr>
										<td class="contenedorTextoSeleccion">
											<html:checkbox property="showPresentacion" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.configurarcalculoindicadores.presentacion.mostrar" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Rango --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelRango" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td colspan="2"><b><vgcutil:message key="jsp.configurarcalculoindicadores.rango" /></b></td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.configurarcalculoindicadores.ano" /></td>
										<td>
											<bean:define id="anoCalculo" toScope="page"><bean:write name="calculoIndicadoresForm" property="ano" /></bean:define>
											<bean:define id="anoInicial" toScope="page"><bean:write name="calculoIndicadoresForm" property="anoInicio" /></bean:define>
											<bean:define id="anoFin" toScope="page"><bean:write name="calculoIndicadoresForm" property="anoFin" /></bean:define>
											<html:select property="ano" value="<%= anoCalculo %>" styleClass="cuadroTexto">
												<%
												for (int i = Integer.parseInt(anoInicial); i <= Integer.parseInt(anoFin); i++) 
												{
												%>
												<html:option value="<%=String.valueOf(i)%>">
													<%=i%>
												</html:option>
												<%
												}
												%>
											</html:select>
										</td>
									</tr>
									<tr>
										<bean:define id="maximoPeriodo" name="calculoIndicadoresForm" property="numMaximoPeriodos" type="java.lang.Integer" toScope="page" />

										<td align="right"><vgcutil:message key="jsp.configurarcalculoindicadores.mesinicial" /></td>
										<td><bean:define id="valor" toScope="page">
											<bean:write name="calculoIndicadoresForm" property="mesInicial" />
										</bean:define> <html:select property="mesInicial" value="<%= valor %>" onchange="validarRango(document.calculoIndicadoresForm.mesInicial,document.calculoIndicadoresForm.mesFinal,document.calculoIndicadoresForm.mesInicialAnt,document.calculoIndicadoresForm.mesFinalAnt,errmsgrango); setValorMaxMinBarraProg(document.calculoIndicadoresForm.mesFinal.value, document.calculoIndicadoresForm.mesInicial.value); calcularTiemposBarraProgreso()" styleClass="cuadroTexto">
											<%
											for (int i = 1; i <= maximoPeriodo.intValue(); i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select> <vgcutil:message key="jsp.configurarcalculoindicadores.mesfinal" /> <bean:define id="valor" toScope="page">
											<bean:write name="calculoIndicadoresForm" property="mesFinal" />
										</bean:define> <html:select property="mesFinal" value="<%= valor %>" onchange="validarRango(document.calculoIndicadoresForm.mesInicial,document.calculoIndicadoresForm.mesFinal,document.calculoIndicadoresForm.mesInicialAnt,document.calculoIndicadoresForm.mesFinalAnt,errmsgrango); setValorMaxMinBarraProg(document.calculoIndicadoresForm.mesFinal.value, document.calculoIndicadoresForm.mesInicial.value); calcularTiemposBarraProgreso()" styleClass="cuadroTexto">
											<%
											for (int i = 1; i <= maximoPeriodo.intValue(); i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select></td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Alcance --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelAlcance" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
									<tr>
										<td>
											<b>
												<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenOrganizaciones %>">
													<vgcutil:message key="jsp.configurarcalculoindicadores.alcancecalculo" />
												</logic:equal>
												<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIndicadores %>">
													<vgcutil:message key="jsp.configurarcalculoindicadores.alcancecalculo" />
												</logic:equal>
												<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenPlanes %>">
													<vgcutil:message key="jsp.configurarcalculoindicadores.alcancenivelcalculo" />
												</logic:equal>
												<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIniciativas %>">
													<vgcutil:message key="jsp.configurarcalculoindicadores.alcancecalculo" />
												</logic:equal>
											</b>
										</td>
									</tr>

									<tr>
										<td height="10">&nbsp;</td>
									</tr>

									<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIndicadores %>">
										<tr>
											<td>
												<html:radio property="alcance" value="<%= alcanceIndicador %>" onclick="eventoOnclickAlcance()">
													<vgcutil:message key="jsp.configurarcalculoindicadores.indicadorsolo" />
												</html:radio> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:checkbox property="porNombre" onclick="eventoOnclickPorNombre()" styleClass="botonSeleccionMultiple">&nbsp;
														<vgcutil:message key="jsp.configurarcalculoindicadores.indicadorpornombre" />
												</html:checkbox>
											</td>
										</tr>

										<tr>
											<td><html:text property="nombreIndicador" size="40" readonly="true" styleClass="cuadroTexto" /> <input value="..." class="cuadroTexto" type="button" onclick="seleccionarIndicador();"> <input value="X" class="cuadroTexto" type="button" onclick="deseleccionarIndicador();"></td>
										</tr>
										<tr>
											<td><html:radio property="alcance" value="<%= alcanceClase %>" onclick="eventoOnclickAlcance()">
												<vgcutil:message key="jsp.configurarcalculoindicadores.porclase" />
											</html:radio></td>
										</tr>
									</logic:equal>

									<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenPlanes %>">
										<tr>
											<td><html:radio property="alcance" value="<%= alcancePerspectiva %>" onclick="eventoOnclickAlcance()">
												<vgcutil:message key="jsp.configurarcalculoindicadores.porperspectiva" />
											</html:radio></td>
										</tr>
										<tr>
											<td><html:radio property="alcance" value="<%= alcancePlan %>" onclick="eventoOnclickAlcance()">
												<vgcutil:message key="jsp.configurarcalculoindicadores.porplan" />
											</html:radio></td>
										</tr>
									</logic:equal>

									<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIniciativas %>">
										<logic:notEmpty name="calculoIndicadoresForm" property="nombreIndicador">
											<tr>
												<td>
													<html:radio property="alcance" value="<%= alcanceIndicador %>" onclick="eventoOnclickAlcance()">
														<vgcutil:message key="jsp.configurarcalculoindicadores.indicadorsolo" />
													</html:radio> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<html:checkbox property="porNombre" onclick="eventoOnclickPorNombre()" styleClass="botonSeleccionMultiple">&nbsp;
														<vgcutil:message key="jsp.configurarcalculoindicadores.indicadorpornombre" />
													</html:checkbox>
												</td>
											</tr>
											<tr>
												<td><html:text property="nombreIndicador" size="40" readonly="true" styleClass="cuadroTexto" /> <input value="..." class="cuadroTexto" type="button" onclick="seleccionarIndicador();"> <input value="X" class="cuadroTexto" type="button" onclick="deseleccionarIndicador();"></td>
											</tr>
										</logic:notEmpty>

										<tr>
											<td><html:radio property="alcance" value="<%= alcanceIniciativa %>" onclick="eventoOnclickAlcance()">
												<vgcutil:message key="jsp.configurarcalculoindicadores.poriniciativa" />
											</html:radio></td>
										</tr>
									</logic:equal>

									<tr>
										<td><html:radio property="alcance" value="<%= alcanceOrganizacion %>" onclick="eventoOnclickAlcance()">
											<vgcutil:message key="jsp.configurarcalculoindicadores.pororganizacion" />
										</html:radio></td>
									</tr>

									<tr>
										<td><html:radio property="alcance" value="<%= alcanceOrganizacionSeleccionada %>" onclick="eventoOnclickAlcance()">
											<vgcutil:message key="jsp.configurarcalculoindicadores.pororganizacionseleccionada" />
										</html:radio></td>
									</tr>

									<tr>
										<td><html:radio property="alcance" value="<%= alcanceOrganizacionTodas %>" onclick="eventoOnclickAlcance()">
											<vgcutil:message key="jsp.configurarcalculoindicadores.pororganizaciontodas" />
										</html:radio></td>
									</tr>

									<tr>
										<td>
											<table style="font-family:Verdana; font-size:11; color:#666666; text-decoration:none;">
												<tr>
													<td align="left">
														<vgcutil:message key="jsp.configurarcalculoindicadores.frecuencia" />
													</td>
													<td>
														<html:select property="frecuencia"
															styleClass="cuadroTexto" size="1"
															onchange="eventoCambioFrecuencia()">
															<html:option value=""></html:option>
															<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
														</html:select>
													</td>
												</tr>
											</table>
										</td>
									
										<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenOrganizaciones %>">
											<td height="120">&nbsp;</td>
										</logic:equal>

										<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenIndicadores %>">
											<td height="40">&nbsp;</td>
										</logic:equal>

										<logic:equal property="origen" name="calculoIndicadoresForm" value="<%= origenPlanes %>">
											<td height="70">&nbsp;</td>
										</logic:equal>
									</tr>

									<tr>
										<td>
											<html:checkbox property="periodosCero" styleClass="botonSeleccionMultiple">&nbsp;
												<vgcutil:message key="jsp.configurarcalculoindicadores.periodossinmedicionvalorcero" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Log --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelLog" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td><b><vgcutil:message key="jsp.configurarcalculoindicadores.reportar" /></b></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td><html:checkbox property="reportarIndicadores" styleClass="botonSeleccionMultiple">&nbsp;
												<vgcutil:message key="jsp.configurarcalculoindicadores.reportarindicadores" />
										</html:checkbox> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:checkbox property="reportarErrores" styleClass="botonSeleccionMultiple">&nbsp;
												<vgcutil:message key="jsp.configurarcalculoindicadores.reportarerrores" />
										</html:checkbox></td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

						</vgcinterfaz:contenedorPaneles></td>
					</tr>
				</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<html:javascript formName="calculoIndicadoresForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/calculos/calculosJs/staticJavascript.jsp'/>"></script>

		<script>
			<logic:equal name="calculoIndicadoresForm" property="status" value="0">
				if (document.calculoIndicadoresForm.showPresentacion.checked)
					pasoActual = 2;
				mostrarTitulo();
				mostrarBotones(pasoActual);
			</logic:equal>
			
			<logic:equal name="calculoIndicadoresForm" property="status" value="1">
				if (document.calculoIndicadoresForm.showPresentacion.checked)
					pasoActual = 2;
				mostrarTitulo();
				mostrarBotones(pasoActual);
			</logic:equal>
			
			<logic:equal name="calculoIndicadoresForm" property="status" value="2">
				alert('<vgcutil:message key="calcularindicadores.actualizar.alerta.fin" />');
				cancelar();
			</logic:equal>
			
			<logic:equal name="calculoIndicadoresForm" property="status" value="3">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>

</tiles:insert>
