<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (16/09/2012) --%>

<tiles:insert definition="doc.modalLayoutHTML5" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.mostrarvista.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript --%>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/graphicType.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/paletaColor/colorPicker.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/numeral.js'/>"></script>
		<script type="text/javascript">
			var _containerGraphic = '';
			var _separadorSeries = '<bean:write name="showVistaForm" property="separadorSeries" ignore="true"/>';
			var _fontSizeGraph = '8px';
			var _fontSizeGraphTitulo = '10px';
		</script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts-3d.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts-more.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/solid-gauge.js" />"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/foros/Foro.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			function validar(forma) 
			{		
			    var res = true;			
				return res;
			}
	
			function siguiente() 
			{	
				document.location.href='<html:rewrite action="/presentaciones/vistas/mostrarVista" />?vistaId=<bean:write name="showVistaForm" property="vista.vistaId" />&paginaId=<bean:write name="showVistaForm" property="paginaSiguienteId" />';
			}
	
			function previa() 
			{
				document.location.href='<html:rewrite action="/presentaciones/vistas/mostrarVista" />?vistaId=<bean:write name="showVistaForm" property="vista.vistaId" />&paginaId=<bean:write name="showVistaForm" property="paginaPreviaId" />';
			}
	
	    	function mostrarDetalle(celdaId) 
	    	{
				var celda = document.getElementById(celdaId);
				if (celda != null)
		    		mostrarGrafico(celda.value);
			}
		
	    	function mostrarGrafico(celdaId) 
	    	{ 	
				var grafico = new Grafico();
				grafico.url = '<html:rewrite action="/graficos/grafico"/>';
				grafico.ShowForm(true, celdaId, 'Celda', undefined, undefined, undefined, '<bean:write name="showVistaForm" property="vista.vistaId" />', '<bean:write name="showVistaForm" property="pagina.paginaId" />');
			}

			function gestionarForos(celdaId) 
			{
				var foro = new Foro();
				foro.url = '<html:rewrite action="/foros/gestionarForos" />';
				foro.ShowList(true, celdaId, 'Celda');
			}
		
			function gestionarExplicaciones(celdaId) 
			{
				var celda = document.getElementById(celdaId);
				if (celda != null)
				{
					var explicacion = new Explicacion();
					explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
					explicacion.ShowList(true, celda.value, 'Celda', 0);
				}
			}
			
			function verDupontIndicador(celdaId) 
			{
				var celda = document.getElementById(celdaId);
				if (celda != null)
				{
					var duppont = new Duppont();
					duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
					duppont.ShowForm(true, celda.value, undefined, undefined);
				}
		    }
			
			function preparedGraph(div, tipo, ejeX, serieName, serieData, titulo, serieColor, serieTipo, rangoAlertas, isAscendente)
			{
				_name = titulo;
				_subtitle = ''; 
				_negrilla = true;
				if (tipo == 1)
					_typeGraphic = 'line';
				else if (tipo == 2 || tipo == 9 || tipo == 11 || tipo == 12)
					_typeGraphic = 'column';
				else if (tipo == 3)
					_typeGraphic = 'bar';
				else if (tipo == 4)
					_typeGraphic = 'area';
				else if (tipo == 5 || tipo == 10)
					_typeGraphic = 'pie';
				else if (tipo == 14)
					_typeGraphic = 'gauge';
				else if (tipo == 8 || tipo == 6)
					_typeGraphic = null;
				else
					_typeGraphic = 'line';
				
				_ejeYTitle = '';
				_ejeX = ejeX.split(",");
				_serie = [];

				var series = serieName.split(_separadorSeries);
				var datas = serieData.split(_separadorSeries);
				var color = serieColor.split(_separadorSeries);
				var tipos = serieTipo.split(_separadorSeries);
				var rangoAlert = rangoAlertas.split(_separadorSeries);
				
				if (tipo == 5 || tipo == 10)
				{
					_showLeyenda = true;
					if (tipo == 5)
					{
						_serie.push({
				            name: _ejeX,
				            colorByPoint: true,
				            data: []
				        });
					}
					else if (tipo == 10)
					{
						_serie.push({
							type: _typeGraphic,
				            name: _ejeX,
				            data: []
				        });
					}
					for (var i = 0; i < series.length; i++)
					{
						if (!isNaN(parseFloat(datas[i])))
							_serie[0].data.push({ name: series[i], color: color[i], y: parseFloat(datas[i])});
						else
							_serie[0].data.push({ name: series[i], color: color[i], y: null});
					}
				}
				else if (tipo == 14)
				{
					_plotBands = [];
					if (isAscendente  == "true")
					{
						_red = rangoAlert[0].split(",");
						_yellow = rangoAlert[1].split(",");
						_green = rangoAlert[2].split(",");

						_plotBands.push({
				            from: (!isNaN(parseFloat(_red[0])) ? parseFloat(_red[0]) : 0),
				            to: (!isNaN(parseFloat(_red[1])) ? parseFloat(_red[1]) : 120),
				            color : '#DF5353' // red
				        });

						_plotBands.push({
				            from: (!isNaN(parseFloat(_yellow[0])) ? parseFloat(_yellow[0]) : 120),
				            to: (!isNaN(parseFloat(_yellow[1])) ? parseFloat(_yellow[1]) : 160),
				            color : '#DDDF0D' // yellow
				        });
						
						_plotBands.push({
							from: (!isNaN(parseFloat(_green[0])) ? parseFloat(_green[0]) : 160),
				            to: (!isNaN(parseFloat(_green[1])) ? parseFloat(_green[1]) : 200),
				            color : '#55BF3B' // green
				        });
					}
					else
					{
						_green = rangoAlert[0].split(",");
						_yellow = rangoAlert[1].split(",");
						_red = rangoAlert[2].split(",");
						datas[1] = (!isNaN(parseFloat(datas[1])) ? parseFloat(datas[1]) : 0) + (!isNaN(parseFloat(_green[1])) ? parseFloat(_green[1]) : 0) + (!isNaN(parseFloat(_yellow[1])) ? parseFloat(_yellow[1]) : 0);

						_plotBands.push({
							from: (!isNaN(parseFloat(_green[0])) ? parseFloat(_green[0]) : 200),
				            to: (!isNaN(parseFloat(_green[1])) ? parseFloat(_green[1]) : 160),
				            color : '#DF5353' // red
				        });

						_plotBands.push({
				            from: (!isNaN(parseFloat(_yellow[0])) ? parseFloat(_yellow[0]) : 160),
				            to: (!isNaN(parseFloat(_yellow[1])) ? parseFloat(_yellow[1]) : 120),
				            color : '#DDDF0D' // yellow
				        });
						
						_plotBands.push({
				            from: (!isNaN(parseFloat(_red[0])) ? parseFloat(_red[0]) : 120),
				            to: (!isNaN(parseFloat(_red[1])) ? parseFloat(_red[1]) : 0),
				            color : '#55BF3B' // green
				        });
					}

					_meta = datas[1];
					_serie.push({
			            name: _name,
			            data: [],
			            tooltip: {
			                valueSuffix: ' ' + _ejeYTitle
			            }
			        });
					if (!isNaN(parseFloat(datas[0])))
						_serie[0].data.push(parseFloat(datas[0]));
					else
						_serie[0].data.push(null);
				}
				else if (tipo == 8)
				{
					for (var i = 0; i < series.length; i++)
					{
						_serie.push({
				            type: tipos[i] == 1 ? 'column' : 'line',
				            name: series[i],
				            color: color[i],
				            data: []
				        });
						var data = datas[i].split(",");
						for (var k = 0; k < data.length; k++)
						{
							if (!isNaN(parseFloat(data[k])))
								_serie[i].data.push(parseFloat(data[k]));
							else
								_serie[i].data.push(null);
						}
					}
				}
				else if (tipo == 6)
				{
					_yAxis = [];
					for (var i = 0; i < series.length; i++)
					{
						if (i == 0)
						{
							_yAxis.push({
								labels: {
					                format: '{value}',
					                style: {
					                    color: Highcharts.getOptions().colors[i]
					                }
					            },
								title : {
									text : series[i],
									style:  {
										color: Highcharts.getOptions().colors[i]
									}
								}
							});
							
							_serie.push({
					            type: tipos[i] == 1 ? 'column' : 'line',
					            name: series[i],
					            color: color[i],
					            data: []
					        });
						}
						else
						{
							_yAxis.push({
								labels: {
					                format: '{value}',
					                style: {
					                    color: Highcharts.getOptions().colors[i]
					                }
					            },
								title : {
									text : series[i],
									style:  {
										color: Highcharts.getOptions().colors[i]
									}
								},
								opposite: true
							});
							
							_serie.push({
					            type: tipos[i] == 1 ? 'column' : 'line',
					            name: series[i],
					            color: color[i],
					            yAxis: 1,
					            data: []
					        });
						}
						var data = datas[i].split(",");
						for (var k = 0; k < data.length; k++)
						{
							if (!isNaN(parseFloat(data[k])))
								_serie[i].data.push(parseFloat(data[k]));
							else
								_serie[i].data.push(null);
						}
					}
				}
				else
				{
					for (var i = 0; i < series.length; i++)
					{
						_serie.push({
				            name: series[i],
				            color: color[i],
				            data: []
				        });
						var data = datas[i].split(",");
						for (var k = 0; k < data.length; k++)
						{
							if (!isNaN(parseFloat(data[k])))
								_serie[i].data.push(parseFloat(data[k]));
							else
								_serie[i].data.push(null);
						}
					}
				}
				
				_containerGraphic = div;
				getGrafico(tipo);				
			}
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/graficos/grafico" styleClass="formaHtmlCompleta">
			<html:hidden property="respuesta" />

			<bean:define id="altoCelda" name="showVistaForm" property="altoCelda" />
			<bean:define id="anchoCelda" name="showVistaForm" property="anchoCelda" />
			<bean:define id="paginaActual" name="showVistaForm" property="pagina.numero" />
			<bean:define id="columnas" name="showVistaForm" property="pagina.columnas" />
			<bean:define id="paginas" name="showVistaForm" property="paginas" />
			
			<html:hidden property="paginaId" />
			<html:hidden property="vistaId" />

			<vgcinterfaz:contenedorForma width="100%" height="100%">
	
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.mostrarvista.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
	
				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>
	
				<%-- Cuerpo --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table width="100%" cellpadding="3" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="20px"><b><vgcutil:message key="jsp.mostrarvista.organizacion" /></b></td>
							<td><bean:write name="organizacion" scope="session" property="nombre" />&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="20px"><b><vgcutil:message key="jsp.mostrarvista.vista" /></b></td>
							<td><bean:write name="showVistaForm" property="vista.nombre" />&nbsp;</td>
						</tr>
					</table>
	
					<vgcinterfaz:barraHerramientas nombre="barraVista">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="flechaIzquierda" pathImagenes="/componentes/barraHerramientas/" nombre="previa" onclick="javascript:previa();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.previo.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="flechaDerecha" pathImagenes="/componentes/barraHerramientas/" nombre="siguiente" onclick="javascript:siguiente();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.siguiente.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasEspacio ancho="100%">
							<vgcutil:message key="jsp.mostrarvista.pagina.nro" />&nbsp;<bean:write name="showVistaForm" property="pagina.numero" />&nbsp;&nbsp;|&nbsp;&nbsp;<b><bean:write name="showVistaForm" property="pagina.descripcion" /></b>
						</vgcinterfaz:barraHerramientasEspacio>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<script type="text/javascript">	
					var columnas = <%=columnas%>;
					var anchoCelda = <%=anchoCelda%>;
					var paginas = <%=paginas%>;
				</script>
				<table id="tblBody">
					<tr>
						<td>
							<logic:notEmpty name="showVistaForm" property="vista.paginas">
								<table id="tblPaginaTop" class="bordeFichaDatos" style="border:1px solid #000000; position: relative;">
									<tr>
										<td>
											<vgcutil:message key="jsp.mostrarvista.irpagnro" />&nbsp;:&nbsp;&nbsp;
										</td>
										<%for (Integer i = 1; i <= new Integer(paginas.toString()).intValue(); i++) {%>
											<logic:iterate name="showVistaForm" property="vista.paginas" id="objPagina">
												<logic:equal name="objPagina" property="numero" value="<%= i.toString() %>">
													<td>
														<logic:equal name="objPagina" property="numero" value="<%= paginaActual.toString() %>">
															<bean:write name="objPagina" property="numero" />
														</logic:equal>
														<logic:notEqual name="objPagina" property="numero" value="<%= paginaActual.toString()%>">
															<a href="<html:rewrite action="/presentaciones/vistas/mostrarVista" />?vistaId=<bean:write name="vista" property="vistaId" />&paginaId=<bean:write name="objPagina" property="paginaId" />"><bean:write name="objPagina" property="numero" /></a>
														</logic:notEqual>
													</td>
												</logic:equal>
											</logic:iterate>
										<%}%>
									</tr>
								</table>
							</logic:notEmpty>
							<br>
							<table class="bordeFichaDatos" style="width: <bean:write name='showVistaForm' property='anchoMarco' />;">
								<tr>
									<td>
										<table id="tblPaginaBody" style="width: <bean:write name='showVistaForm' property='anchoPagina' />; height: <bean:write name='showVistaForm' property='altoPagina' />;">
											<bean:define id="strFrecuenciaAnual" toScope="page">
												<bean:write name='showVistaForm' property="frecuencia" />
											</bean:define>
											<bean:define id="fila" toScope="page" value="0" />
											<bean:define id="columna" toScope="page" value="1" />
											<bean:define id="addTR" toScope="page" value="false" />
											<logic:iterate name="showVistaForm" property="celdas" id="celda">
												<logic:notEqual name="celda" property="fila" value="<%=fila%>">
													<bean:define id="columna" toScope="page" value="1" />
													<bean:define id="fila" toScope="page">
														<bean:write name="celda" property="fila" />
													</bean:define>
													<logic:equal scope="page" name="addTR" value="true">
														</tr>
													</logic:equal>
													<tr>
													<bean:define id="addTR" toScope="page" value="true" />
												</logic:notEqual>
												<logic:equal name="celda" property="fila" value="<%=fila%>">
													<logic:equal name="celda" property="columna" value="<%=columna%>">
														<td>
															<table class="bordeFichaDatos">
																<tr>
																	<td style="border:1px solid #000000;">
																		<a 
																			href="javascript:mostrarDetalle('celda_<bean:write name="celda" property="fila" />_<%=columna%>');">
																			<img 
																				src="<html:rewrite page='/componentes/formulario/zoom_ampliar.gif'/>" 
																				border="0" 
																				width="10" 
																				height="10" 
																				alt="<vgcutil:message key="boton.detalle.alt" />">
																		</a>|
																		<logic:equal name="celda" property="showDuppont" value="true">
																			<logic:notEmpty name="celda" property="indicadorId">
																				<img
																					id="imgDuppontCelda_<bean:write name="celda" property="fila" />_<%=columna%>"
																					style="height:15px; width:15px;cursor: pointer" 
																					src="<html:rewrite page="/paginas/strategos/indicadores/imagenes/barraHerramientas/dupont_15.gif"/>" 
																					border="0" 
																					alt="<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.arboldupont" />"
																					onclick="javascript:verDupontIndicador('indicadorCelda_<bean:write name="celda" property="fila" />_<%=columna%>');">
																				<input type="hidden" id="indicadorCelda_<bean:write name="celda" property="fila" />_<%=columna%>" name="indicadorCelda_<bean:write name="celda" property="fila" />_<%=columna%>" value="<bean:write name="celda" property="indicadorId" />" />
																			</logic:notEmpty>
																		</logic:equal>
																		<logic:equal name="celda" property="showAlerta" value="true">
																			<logic:notEmpty name="celda" property="alerta">
																				<img
																					id="imgAlertaCelda_<bean:write name="celda" property="fila" />_<%=columna%>"
																					style="height:15px; width:15px; float:right;display:block" 
																					src="<bean:write name="celda" property="alerta" />" 
																					border="0"
																					alt="<vgcutil:message key="boton.alerta.alt" />">
																			</logic:notEmpty>
																		</logic:equal>
																	</td>
																</tr>
																<tr>
																	<td>
																	</td>
																</tr>
																<tr>
																	<td style='vertical-align: text-top; height: <%= altoCelda.toString().replace("px", "") %>px; width : <%= anchoCelda.toString().replace("px", "") %>px; border:1px solid #000000'>
																		<logic:notEmpty name="celda" property="celdaId">
																			<logic:equal name="celda" property="showImage" value="true">
																				<div id='div_grafico_celda_<bean:write name="celda" property="fila" />_<%=columna%>' style='height: 100%; width : 100%; margin: 0 auto'></div>
																				<script type="text/javascript">
																					preparedGraph('div_grafico_celda_<bean:write name="celda" property="fila" />_<%=columna%>', '<bean:write name="celda" property="tipo" />', '<bean:write name="celda" property="ejeX" />', '<bean:write name="celda" property="serieName" />', '<bean:write name="celda" property="serieData" />', '<bean:write name="celda" property="titulo" />', '<bean:write name="celda" property="serieColor" />', '<bean:write name="celda" property="serieTipo" />', '<bean:write name="celda" property="rangoAlertas" />', '<bean:write name="celda" property="isAscendente" />');
																				</script>
																			</logic:equal>
																			<input type='hidden' id='celda_<bean:write name="celda" property="fila" />_<%=columna%>' name='celda_<bean:write name="celda" property="fila" />_<%=columna%>' value='<bean:write name="celda" property="celdaId" />' />
																		</logic:notEmpty>
																		<logic:empty name="celda" property="celdaId">&nbsp;
																			<input type='hidden' id='celda_<bean:write name="celda" property="fila" />_<%=columna%>' name='celda_<bean:write name="celda" property="fila" />_<%=columna%>' value='<bean:write name="celda" property="celdaId" />' />
																		</logic:empty>
																	</td>
																</tr>
																<tr>
																	<td>
																	</td>
																</tr>
															</table>
														</td>
													</logic:equal>
													<%columna = new Integer(new Integer(columna.toString()) + 1) <= new Integer(columnas.toString()) ? new Integer(new Integer(columna.toString()) + 1).toString() : "1";%>
												</logic:equal>
											</logic:iterate>
											<logic:equal scope="page" name="addTR" value="true">
												</tr>
											</logic:equal>
										</table>
									</td>
								</tr>
							</table>
							<br>
							<logic:notEmpty name="showVistaForm" property="vista.paginas">
								<table id="tblPaginaBottom" class="bordeFichaDatos" style="border:1px solid #000000; position: relative;">
									<tr>
										<td>
											<vgcutil:message key="jsp.mostrarvista.irpagnro" />&nbsp;:&nbsp;&nbsp;
										</td>
										<%for (Integer i = 1; i <= new Integer(paginas.toString()).intValue(); i++) {%>
											<logic:iterate name="showVistaForm" property="vista.paginas" id="objPagina">
												<logic:equal name="objPagina" property="numero" value="<%= i.toString() %>">
													<td>
														<logic:equal name="objPagina" property="numero" value="<%= paginaActual.toString() %>">
															<bean:write name="objPagina" property="numero" />
														</logic:equal>
														<logic:notEqual name="objPagina" property="numero" value="<%= paginaActual.toString()%>">
															<a href="<html:rewrite action="/presentaciones/vistas/mostrarVista" />?vistaId=<bean:write name="vista" property="vistaId" />&paginaId=<bean:write name="objPagina" property="paginaId" />"><bean:write name="objPagina" property="numero" /></a>
														</logic:notEqual>
													</td>
												</logic:equal>
											</logic:iterate>
										<%}%>
									</tr>
								</table>
							</logic:notEmpty>
						</td>
					</tr>
				</table>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			var objeto = document.getElementById("tblBody");
			var anchoBody = (parseInt(_myWidth) - 30);
			if (objeto != null)
			{
				objeto.style.width = anchoBody + "px";
				objeto.style.height = (parseInt(_myHeight) - 185) + "px";
			}
			objeto = document.getElementById("tblPaginaTop");
			var width = (anchoBody / 2) - 80 + (paginas * 3);
			if (objeto != null)
				objeto.style.left = width + "px"; 
			objeto = document.getElementById("tblPaginaBottom");
			if (objeto != null)
				objeto.style.left = width + "px";
			objeto = document.getElementById("tblPaginaBody");
			if (objeto != null)
			{
				objeto.style.position = "relative";
				var ancho = (anchoBody / 2) - ((anchoCelda * columnas) / 2);
				if (ancho <= 0)
					ancho = 10;
				objeto.style.left = ancho + "px";
			}
		</script>
	</tiles:put>
</tiles:insert>
