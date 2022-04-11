<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asistente.consolidado.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesIndicadoresJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesMultiplesJs.jsp"></jsp:include>
		<script>
			var pasoActual = 1;
			var _setCloseParent = false;
			var _setAlerta = false;
			var _separadorClases = '<bean:write name="indicadorConsolidadoForm" property="separadorClases" ignore="true"/>';
			var _separadorCampos = '<bean:write name="indicadorConsolidadoForm" property="separadorCampos" ignore="true"/>';
			var _clasesId = new Array();
			var _clasesNombre = new Array();
			var _clasesRutaCompleta = new Array();
		
			function seleccionarClaseIndicadores() 
			{
				abrirSelectorClasesIndicadores('indicadorConsolidadoForm', 'claseConsolidacionNombre', 'claseConsolidacionId', document.indicadorConsolidadoForm.claseConsolidacionId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');
			}
			
			function limpiarClaseIndicadores() 
			{
				document.indicadorConsolidadoForm.claseConsolidacionId.value = "";
				document.indicadorConsolidadoForm.claseConsolidacionNombre.value = "";
			}

			function agregarClase() 
			{
				abrirSelectorClases('indicadorConsolidadoForm', 'claseInsumoSeleccionadoNombres', 'claseInsumoSeleccionadoIds', document.indicadorConsolidadoForm.claseConsolidacionId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>', 'agregarClasesInsumo()');
			}

			function agregarClasesInsumo() 
			{
				var seleccionadoNombres = document.indicadorConsolidadoForm.claseInsumoSeleccionadoNombres.value.split(_separadorClases);
				
				for (var index = 0; index < seleccionadoNombres.length; index++) 
				{
					if (seleccionadoNombres[index] != '')
					{
						var campos = seleccionadoNombres[index].split(_separadorCampos);
						if (campos.length > 0)
						{
							var idExiste = false;
							for (var i = 0; i < _clasesId.length; i++)
							{
								if (_clasesId[i] == campos[0])
								{
									idExiste = true;
									break;
								}
							}
							
							if (!idExiste)
							{
								_clasesId.push(campos[0]);
								_clasesNombre.push(campos[1]);
								_clasesRutaCompleta.push(campos[2]);
							}
						}
					}
				}
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');

				//Se borra la lista de insumos
			 	while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
			 		tablaListaInsumos.deleteRow(0);
				
				for (var i = 0; i < _clasesId.length; i++) 
				{
					if (_clasesId[i] != '' && _clasesId[i] != 'ELIMINADO') 
					{
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");
						td1.appendChild(document.createTextNode(i));
						td2.appendChild(document.createTextNode(_clasesNombre[i]));
						row.appendChild(td1);
						row.appendChild(td2);
						row.onclick = function() 
						{
							var selAnterior = document.getElementById('insumoSeleccionado').value;
							selectRowColors(this.rowIndex, 
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
							paintListaInsumosColumnaSerie(selAnterior, document.getElementById('tablaListaInsumos'), "blue");
							mostrarRutaCompletaInsumo();
						};
						tbody.appendChild(row);
						if (numFilas == 0) 
						{
							selectRowColors(0,
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
						}
					}
				}

				mostrarRutaCompletaInsumo();
			}
			
			function mostrarRutaCompletaInsumo() 
			{
				correlativo = parseInt(document.indicadorConsolidadoForm.insumoSeleccionado.value);
				if (_clasesRutaCompleta.length > 0 && _clasesRutaCompleta[correlativo] != '')
					setTablaRutaCompletaInsumo(_clasesRutaCompleta[correlativo]);
			}
			
			// Creado por: Kerwin Arias (01/07/2012)
			function setTablaRutaCompletaInsumo(rutaCompletaInsumo) 
			{
				var tablaRutaCompletaInsumo = document.getElementById('tablaRutaCompletaInsumo');

				// Se borra la lista de insumos
				while (tablaRutaCompletaInsumo.getElementsByTagName("tr").length > 0) 
					tablaRutaCompletaInsumo.deleteRow(0);

				if (rutaCompletaInsumo == '') 
					return;

				// Se recorre la lista de insumos
				var partesRuta = rutaCompletaInsumo.split(' / ');
				nroPartes = partesRuta.length;
				for (var i = 0; i < partesRuta.length; i++) 
				{
					var numFilas = tablaRutaCompletaInsumo.getElementsByTagName("tr").length;
					var tbody = tablaRutaCompletaInsumo.getElementsByTagName("TBODY")[0];
					var row = document.createElement("TR");
					for (var j = 0; j < i; j++) 
					{
						var tdIdent = document.createElement("TD");
						tdIdent.appendChild(document.createTextNode('-'));
						tdIdent.width = '5px';
						row.appendChild(tdIdent);
					}
					var tdValor = document.createElement("TD");
					tdValor.appendChild(document.createTextNode(partesRuta[i]));
					if (nroPartes > 1) 
						tdValor.colSpan=nroPartes;
					row.appendChild(tdValor);
					tbody.appendChild(row);
					nroPartes--;
				}
			}

			function paintListaInsumosColumnaSerie(indexRow, oTable, color) 
			{
				indexRow = indexRow / 1;
				oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(1).style.color = color;			
			}		
			
			function selectRowColors(indexRow, oTable, fieldRowSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var rowSelected = fieldRowSelected.value / 1;
				var i = 0;

				indexRow = indexRow / 1;

				if (oTable.getElementsByTagName("tr").item(rowSelected) != null) 
				{
					for (i = 0; i < oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length; i++) 
					{
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
					};
				}
			
				for (i = 0; i < oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").length; i++) 
				{
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
				}
			
				fieldRowSelected.value = indexRow;
			}

			function removerClase() 
			{
				var tabla = document.getElementById('tablaListaInsumos');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				
				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.asistente.grafico.insumo.noinsumos" />');
				else 
				{
					// Se modifica el string que contiene los insumos seleccionados
					var index = parseInt(document.indicadorConsolidadoForm.insumoSeleccionado.value);
					deleteRowColors(tabla, 
									document.indicadorConsolidadoForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
					_clasesId[index] = "ELIMINADO";
				}
			}
			
			function deleteRowColors(objTable, indexRow, objIndexSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var numFilas = objTable.getElementsByTagName("tr").length;
				objTable.deleteRow(indexRow);
				if (numFilas > 1) 
				{
					if (objIndexSelected.value > 0) 
					{
						objIndexSelected.value = objIndexSelected.value - 1;
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
					} else 
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
				};
			}
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (pasoActual) 
				{
					case 1:
						break;
					case 2:
						valido = validar(pasoActual);
						break;
					case 3:
						consolidar();
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
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelPresentacion" />
					break;
					case 2:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelRepositorio" />
					break;
					case 3:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelFin" />
					break;
				}
				
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = '..:: <vgcutil:message key="jsp.asistente.consolidado.titulo1" /> ' + pasoActual + ' <vgcutil:message key="jsp.asistente.consolidado.titulo2" />';
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;
			}
			
			function validar(pasoActual) 
			{
				if (pasoActual == 2 && document.indicadorConsolidadoForm.claseConsolidacionId.value == '')
				{
					alert('<vgcutil:message key="jsp.asistente.consolidado.clase.destino.alerta.vacio" /> ');
					return false;
				}

				if (pasoActual == 2 && (_clasesId == null || _clasesId.length == 0))
				{
					alert('<vgcutil:message key="jsp.asistente.consolidado.clase.fuente.alerta.vacio" /> ');
					return false;
				}
				
				return true;
			}
			
			function consolidar()
			{
				var clases = "";
				for (var i = 0; i < _clasesId.length; i++) 
				{
					if (_clasesId[i] != '' && _clasesId[i] != 'ELIMINADO')
						clases = clases + _clasesId[i] + _separadorClases;  
				}
				
				var parametros = "&claseId=" + document.indicadorConsolidadoForm.claseConsolidacionId.value;
				parametros = parametros + "&clases=" + clases;
				parametros = parametros + "&organizacionId=" + document.indicadorConsolidadoForm.organizacionId.value;
				
				document.indicadorConsolidadoForm.action = '<html:rewrite action="/indicadores/indicadorConsolidado"/>?funcion=Consolidar' + parametros;
				document.indicadorConsolidadoForm.submit();
			}
			
			function onConsolidar()
			{
				if (confirm('<vgcutil:message key="jsp.asistente.consolidado.fin.reporte.archivo.exito" />'))
					window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesConsolidacion','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
				refrescarPadre();
				cancelar();
			}
			
			function cancelar() 
			{
				this.close();			
			}
			
			function onClose()
			{
				if (_setCloseParent)
				{
					refrescarPadre();
					cancelar();
				}
			}
			
			function onAlerta()
			{
				if (_setAlerta)
					if (confirm('<vgcutil:message key="jsp.asistente.consolidado.fin.reporte.archivo.error.reporte" />'))
						window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesConsolidacion','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
			}
			
			function refrescarPadre()
			{
				window.opener.refrescarClase();
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/indicadorConsolidado" styleClass="formaHtml" enctype="multipart/form-data" method="POST">

			<%-- Id de las tablas --%>
			<html:hidden property="organizacionId" />
			<html:hidden property="claseConsolidacionId" />
			<html:hidden property="insumosClases" />
			
			<input type="hidden" name="claseInsumoSeleccionadoNombres">
			<input type="hidden" name="claseInsumoSeleccionadoIds">
			<input type="hidden" name="claseInsumoSeleccionadoRutasCompletas">
			<input type="hidden" name="insumoSeleccionado" id="insumoSeleccionado">

			<vgcinterfaz:contenedorForma width="590px" height="410px" bodyAlign="center" bodyValign="center" marginTop="5px">
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr height=315px>

						<%-- Imágen del asistente --%>
						<td width="315px" >
							<img src="<html:rewrite page='/paginas/strategos/indicadores/consolidados/imagenes/WZConsolidado.gif'/>" border="0" width="150px" height="310px">
						</td>

						<td><vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">

							<%-- Panel Presentacion --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td>
											<b><vgcutil:message key="jsp.asistente.consolidado.presentacion.importante" /></b><br><br> 
											<vgcutil:message key="jsp.asistente.consolidado.presentacion.1" /><br><br>
											<vgcutil:message key="jsp.asistente.consolidado.presentacion.2" /><br><br>
											<vgcutil:message key="jsp.asistente.consolidado.presentacion.3" />
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Nombre --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelRepositorio" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" style="border-width:0px;" border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td colspan="2">
											<vgcutil:message key="jsp.asistente.consolidado.clase.repositorio.seleccion" />
										</td>
									</tr>
									<%-- Campo: Clase de destino --%>
									<tr>
										<td>
											<html:text property="claseConsolidacionNombre" size="45" readonly="true" maxlength="50" styleClass="cuadroTexto" />
											&nbsp;
											<img 
												style="cursor: pointer" 
												onclick="seleccionarClaseIndicadores();" 
												src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" 
												border="0" 
												width="11" 
												height="11"
												title="<vgcutil:message key="jsp.editarplan.ficha.seleccionarplan" />">
											&nbsp;
											<img 
												style="cursor: pointer" 
												onclick="limpiarClaseIndicadores();"
												src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" 
												border="0" 
												width="10" 
												height="10" 
												title="<vgcutil:message key="boton.limpiar.alt" />">
										</td>
									</tr>
									<tr>
										<td>
											</br>
										</td>
									</tr>
									<tr>
										<td>
											<vgcutil:message key="jsp.asistente.consolidado.clase.fuente.seleccion" />
										</td>
										<td>
											</br>
											</br>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table class="contenedorBotonesSeleccion" style="border-width:0px; width:100%; border-spacing: 0; border-collapse: collapse; padding: 0px;">
												<tr>
													<td width="50%">
														<table class="contenedorBotonesSeleccion" style="border-width:0px; width:100%; border-spacing: 0; border-collapse: collapse; padding: 0px;">
															<tr>
																<td>
																	<input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.consolidado.clase.fuente.agregarclase" />" onclick="agregarClase();" style="cursor: pointer">
																</td>
																<td>
																	&nbsp;&nbsp;
																</td>
																<td>
																	<input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.consolidado.clase.fuente.eliminarclase" />" onclick="removerClase();" style="cursor: pointer">
																</td>
															</tr>
														</table>
													</td>
													<td width="40%">
													</td>
												</tr>
												<tr height="80px">
													<td colspan="2" height="215px">
														<vgcinterfaz:splitterHorizontal anchoPorDefecto="180px" splitterId="splitInsumos" overflowPanelDerecho="auto" overflowPanelIzquierdo="auto">
															<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitInsumos">
																<table id="tablaListaInsumos" style="border-width:0px; border-spacing: 0; border-collapse: collapse; padding: 0px;">
																	<tbody class="cuadroTexto" style="border-width:0px; border-spacing: 0; border-collapse: collapse; padding: 0px;">
																	</tbody>
																</table>
															</vgcinterfaz:splitterHorizontalPanelIzquierdo>
															<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitInsumos">
																<table id="tablaRutaCompletaInsumo" style="border-width:0px; border-spacing: 0; border-collapse: collapse; padding: 0px;">
																	<tbody class="cuadroTexto" style="border-width:0px; border-spacing: 0; border-collapse: collapse; padding: 0px;">
																	</tbody>
																</table>
															</vgcinterfaz:splitterHorizontalPanelDerecho>
														</vgcinterfaz:splitterHorizontal>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Finalizar --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelFin" mostrarBorde="false">
								<table class="tabla contenedorBotonesSeleccion">
									<tr>
										<td>
											<b><vgcutil:message key="jsp.asistente.consolidado.fin.mensaje1" /></b><br><br> 
											<vgcutil:message key="jsp.asistente.consolidado.fin.mensaje2" /><br><br>
										</td>
									</tr>
									<tr>
										<td>
											<br><br><br>
											<br><br><br>
											<br><br><br>
										</td>
									</tr>
									<tr>
										<td>
											<vgcutil:message key="jsp.asistente.consolidado.fin.reportar" />
										</td>
									</tr>
									<tr>
										<td>
											<table class="tabla contenedorBotonesSeleccion">
												<tr>
													<td colspan="2">
														<vgcutil:message key="jsp.asistente.consolidado.fin.reportar.titulo" />
													</td>
												</tr>
												<tr>
													<td>
														<html:checkbox property="logIndicadores" styleClass="botonSeleccionMultiple" >
															<vgcutil:message key="jsp.asistente.consolidado.fin.reportar.indicadores" />
														</html:checkbox>
													</td>
													<td>
														<html:checkbox property="logErrores" styleClass="botonSeleccionMultiple" >
															<vgcutil:message key="jsp.asistente.consolidado.fin.reportar.errores" />
														</html:checkbox>
													</td>
												</tr>
											</table>
										</td>
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
		<script>
			<logic:equal name="indicadorConsolidadoForm" property="status" value="0">
				mostrarTitulo();
				mostrarBotones(pasoActual);
			</logic:equal>
			
			<logic:equal name="indicadorConsolidadoForm" property="status" value="3">
				pasoActual=3;
				mostrarTitulo();
				mostrarBotones(pasoActual);
				onConsolidar();
			</logic:equal>
			
			<logic:equal name="indicadorConsolidadoForm" property="status" value="1">
				_setCloseParent = true;
			</logic:equal>
			
			<logic:equal name="indicadorConsolidadoForm" property="status" value="2">
				_setAlerta = true;
				pasoActual=3;
				mostrarTitulo();
				mostrarBotones(pasoActual);
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
