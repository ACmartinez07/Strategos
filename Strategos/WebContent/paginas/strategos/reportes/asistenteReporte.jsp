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

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asistente.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script>
		
			var pasoActual = 1;
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (pasoActual) 
				{
					case 1:
					case 3:
					case 4:
					case 5:
						break;
					case 2:
						if (!validar())
							valido = false;
						break;
					case 6:
						if (!validar())
							valido = false;
						break;
					case 7:
						salvar();
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
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelEditarPlantilla" />
					break;
					case 3:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelTipoReporte" />
					break;
					case 4:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelInsumos" />
					break;
					case 5:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelReporte" />
					break;
					case 6:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelNombre" />
					break;
					case 7:
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
				var titulo = '..:: <vgcutil:message key="jsp.asistente.titulo1" /> ' + pasoActual + ' <vgcutil:message key="jsp.asistente.titulo2" />';
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;

				celda = document.getElementById("mensajeFinal");
				celda.innerHTML = '<vgcutil:message key="jsp.asistente.fin" />';
			}
			
			function validar() 
			{
				if (pasoActual == 2 && window.document.reporteForm.reporteSeleccion.value != "")
				{
					window.document.reporteForm.reporteNombre.value = window.document.reporteForm.reporteSeleccion.value; 
					return true;
				}

				if (pasoActual == 6 && window.document.reporteForm.reporteNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.alert.nombre.invalido.vacio" /> ');
					return false;
				}
				
				return true;
			}
			
			function cancelar() 
			{
				window.document.reporteForm.action = '<html:rewrite action="/reportes/cancelarAsistenteReporte"/>?cancelar=true';
				window.document.reporteForm.submit();			
			}

			function salvar() 
			{
				if (validar())
				{
					document.reporteForm.action= '<html:rewrite action="/reportes/guardarAsistenteReporte" />' + '?ts=<%= (new Date()).getTime() %>';
					document.reporteForm.submit();
				}
				else
				{
					pasoActual = 7;
					mostrarBotones(pasoActual);
					mostrarTitulo();
				}
			}
			
			function eventoOnclickNo()
			{
				limpiarReporte();
			}
			
			function seleccionarReportes()
			{
				if (document.reporteForm.asistenteEditar[0].checked)
				{
					alert('<vgcutil:message key="jsp.asistente.editar.seleccionar" /> ');
					return;
				}

				var nombreForma = '?nombreForma=' + 'reporteForm';
				var nombreCampoOculto = '&nombreCampoOculto=' + 'reporteSeleccionId';
				var nombreCampoValor = '&nombreCampoValor=' + 'reporteSeleccion';

				abrirVentanaModal('<html:rewrite action="/reportes/listaReporte" />' + nombreForma + nombreCampoOculto + nombreCampoValor, 'seleccionarReportes', '500', '575');
			}
			
			function limpiarReporte()
			{
				window.document.reporteForm.reporteSeleccion.value = '';
				window.document.reporteForm.reporteSeleccionId.value = '';
			}
			
			function editarFormato()
			{
				abrirReporte('<html:rewrite action="/reportes/crearFormato"/>', 'formatoReporte');
				//window.document.reporteForm.action = '<html:rewrite action="/reportes/crearFormato"/>';
				//window.document.reporteForm.submit();
			}
			
			function editarFormato2()
			{
				//var parametros = '';
				//parametros = ', width=' + ((screen.width)-100);
				//parametros = parametros + ', height=' + ((screen.height)-150);
				//parametros = "toolbar=no, location=no, directories=no, status=yes, menubar=no, scrollbars=no, resizable =yes, left=50, top=50 " + parametros;	

				//var ventana = window.open();
				//ventana.location = "D:\\Desarrollo\\Eclipse\\Strategos\\PLUS\\Strategos\\WebContent\\temp\\filename4.xls";
				//window.open('data:application/vnd.ms-excel, "D:\\Desarrollo\\Eclipse\\Strategos\\PLUS\\Strategos\\WebContent\\temp\\filename4.xls"' );
				//ventana.focus();

				
				if (window.ActiveXObject)
				{  
					var objExcel;
        			objExcel = new ActiveXObject("Excel.Application");
        			objExcel.Visible = true;
        			objExcel.Workbooks.Open("D:\\Desarrollo\\Eclipse\\Strategos\\PLUS\\Strategos\\WebContent\\temp\\filename4.xls", false, false);
        		}
        		else
        			alert("no abre");
        					
				//var textWindow = window.open();
				//textWindow.document.open();
				//textWindow.document.write("application/vnd.ms-excel");
				//var ventana = window.open("D:\Desarrollo\Eclipse\Strategos\PLUS\Strategos\WebContent\temp\filename4.xls","test",parametros);
				
				//textWindow.document.close();				
			}
			
			function seleccionarIndicadores() 
			{
				var forma = 'reporteForm';
				var funcionRetorno = 'agregarIndicadores()';
				var url = '&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.reporteForm.frecuencia.value + '&excluirIds=0';
		
				abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', funcionRetorno, null, null, null, null, null, url);
			}
			
			function agregarIndicadores()
			{
				var seleccionadoIds = document.reporteForm.indicadorInsumoSeleccionadoIds.value.split('<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoNombres = document.reporteForm.indicadorInsumoSeleccionadoNombres.value.split('<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoRutasCompletas = document.reporteForm.indicadorInsumoSeleccionadoRutasCompletas.value.split('<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>');
			
				var listaInsumos = document.reporteForm.insumosFormula.value;
				var correlativo = 0;
			
				if (listaInsumos != null) 
				{
					if (listaInsumos == '') // no hay insumos en la lista 
						correlativo = 1;
					else 
					{
						strBuscada = ']' + '<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>' + '[';
						indice1 = listaInsumos.lastIndexOf(strBuscada);
						if (indice1 > -1) 
						{
							// hay dos o más insumos en la lista
							indice1 = indice1 + strBuscada.length;
							indice2 = listaInsumos.substring(indice1, listaInsumos.length).indexOf(']');
							indice2 = indice1 + indice2;
							correlativo = parseInt(listaInsumos.substring(indice1 , indice2)) + 1;
						} 
						else 
						{
							// hay un solo insumo en la lista
							indice = listaInsumos.indexOf(']');
							correlativo = parseInt(listaInsumos.substring(1, indice)) + 1;
						}
					}
				} 
				else 
				{
					listaInsumos = '';
					correlativo = 1;
				}
			
				for (var i = 0; i < seleccionadoIds.length; i++) 
				{
					partesId = seleccionadoIds[i].split('<bean:write name="reporteForm" property="separadorSeries" ignore="true"/>');
					nombresIndicador = seleccionadoNombres[i].split('<bean:write name="reporteForm" property="separadorSeries" ignore="true"/>');
			
					insumoBuscado = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']';
			
					if (listaInsumos.indexOf(insumoBuscado) < 0) 
					{
						var separadorIndicadores = '';
						if (listaInsumos != '') 
							separadorIndicadores = '<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>';
						
						if (seleccionadoRutasCompletas[i] != '<bean:write name="reporteForm" property="codigoIndicadorEliminado" ignore="true"/>') 
						{
							listaInsumos = listaInsumos 
										+ separadorIndicadores
										+ '[' + correlativo + ']' 
										+ insumoBuscado 
										+ '[indicadorNombre:' + nombresIndicador[0]  + ']'
										+ '[serieNombre:' + nombresIndicador[1] + ']'
										+ '[rutaCompleta:' + seleccionadoRutasCompletas[i] + ']';
							correlativo++;
						}
					}
				}
				
				document.reporteForm.insumosFormula.value = listaInsumos;
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
			
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
			
				// Se recorre la lista de insumos
				var insumos = document.reporteForm.insumosFormula.value.split('<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>');
				for (var i = 0; i < insumos.length; i++) 
				{
					if (insumos[i].length > 0) 
					{
						// correlativo
						strTemp = insumos[i];
						indice = strTemp.indexOf("][") + 1;
						correlativo = strTemp.substring(0, indice);
						// indicadorId
						strTemp = strTemp.substring(indice, strTemp.length);
						indice = strTemp.indexOf("][");
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						// nombreIndicador
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						nombreIndicador = '[' + strTemp.substring('indicadorNombre:'.length + 1, indice + 1);
						// nombreSerie
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serie = '[' + strTemp.substring('serieNombre:'.length + 1, indice + 1);
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");
						var td3 = document.createElement("TD");
						td1.appendChild(document.createTextNode(correlativo));
						td2.appendChild(document.createTextNode(serie));
						td2.style.color = "blue";
						td3.appendChild(document.createTextNode(nombreIndicador));
						row.appendChild(td1);
						row.appendChild(td2);
						row.appendChild(td3);
						row.onclick = function() 
						{
							var selAnterior = document.getElementById('insumoSeleccionado').value;
							selectRowColors(this.rowIndex, 
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
							paintListaInsumosColumnaSerie(selAnterior, document.getElementById('tablaListaInsumos'), "blue");
						}
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
			}
			
			function paintListaInsumosColumnaSerie(indexRow, oTable, color) 
			{
				indexRow = indexRow / 1;
				oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(1).style.color = color;			
			}		
			
			function selectRowColors(indexRow, oTable, fieldRowSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var rowSelected = fieldRowSelected.value / 1;
				var numColumnas = 0;
				var i = 0;

				indexRow = indexRow / 1;
				var numColumnas = 1;

				if (oTable.getElementsByTagName("tr").item(rowSelected) != null) 
				{
					numColumnas = oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length;
			
					for (i = 0; i < numColumnas; i++) 
					{
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
					}
				}
			
				numColumnas = oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").length;
			
				for (i = 0; i < numColumnas; i++) 
				{
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
				}
			
				fieldRowSelected.value = indexRow;
			}
			
			function removerInsumo() 
			{
				var tabla = document.getElementById('tablaListaInsumos');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				var insumosFormula = document.reporteForm.insumosFormula.value;
			
				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.asistente.insumo.noinsumos" />');
				else 
				{
					// Se modifica el string que contiene los insumos seleccionados
					var posicionBuscada = parseInt(document.reporteForm.insumoSeleccionado.value);
					if (posicionBuscada == 0) 
					{
						var index = insumosFormula.indexOf(']');
						// Se busca el último valor de cada indicador seleccionado
						index = insumosFormula.indexOf('[rutaCompleta:');
						insumosFormula = insumosFormula.substring(index + 2, insumosFormula.length);
						index = insumosFormula.indexOf(']');
						if (insumosFormula.length > (index + 1)) 
							index++;
						insumosFormula = insumosFormula.substring(index + 1, insumosFormula.length);
					} 
					else if ((posicionBuscada + 1) == numeroFilas) 
					{
						var strBuscada = ']' + '<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						insumosFormula = insumosFormula.substring(0, index - 1);
					} 
					else 
					{
						var strBuscada = ']' + '<bean:write name="reporteForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = 0;
						for (i = 0; i < posicionBuscada; i++) 
						{
							index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
							if (index != -1) 
								index= index + 2;
						}
						str1 = '';
						if (index != -1) 
						{
							str1 = insumosFormula.substring(0, index);
							index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						}
						index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
						str2 = insumosFormula.substring(index + 2, insumosFormula.length);
						insumosFormula = str1 + str2;
					}
					document.reporteForm.insumosFormula.value = insumosFormula;
					deleteRowColors(tabla, 
									document.reporteForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
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
					} 
					else 
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
				}
			}
			
			function eventoCambioFrecuencia()
			{
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
			
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/asistenteReporte">

			<%-- Campos hidden para el manejo de la insumos --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">			

			<vgcinterfaz:contenedorForma width="570px" height="385px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
					<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
						<tr height=315px>
	
							<%-- Imágen del asistente --%>
							<td width="315px">
								<img src="<html:rewrite page='/paginas/strategos/reportes/imagenes/WZReporte.gif'/>" border="0" width="150px" height="310px">
							</td>
	
							<td>
								<vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">
	
									<%-- Panel Presentacion --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td>
													<b><vgcutil:message key="jsp.asistente.presentacion.importante" /></b></br></br> 
													<vgcutil:message key="jsp.asistente.presentacion.1" /></br></br>
													<vgcutil:message key="jsp.asistente.presentacion.2" /></br></br>
													<vgcutil:message key="jsp.asistente.presentacion.3" />
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel seleccion de plantilla --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelEditarPlantilla" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.editar" /></td>
											</tr>
											<tr>
												<td>
													<html:radio property="asistenteEditar" value="0" onclick="eventoOnclickNo()">
														<vgcutil:message key="jsp.asistente.editar.NO" />
													</html:radio>
												</td>
											</tr>
											<tr>
												<td>
													<html:radio property="asistenteEditar" value="1">
														<vgcutil:message key="jsp.asistente.editar.SI" />
													</html:radio>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<html:text property="reporteSeleccion" size="50" readonly="true" disabled="false" maxlength="50" styleClass="cuadroTexto" />&nbsp;
													<img style="cursor: pointer" onclick="seleccionarReportes();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.editar.seleccionar.plantilla" />">&nbsp;
													<img style="cursor: pointer" onclick="limpiarReporte();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
													<html:hidden property="reporteSeleccionId" />
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel seleccion de tipo reporte --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelTipoReporte" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.tipo" /></td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="asistenteTipo" value="0" class="botonSeleccionSimple" checked="true" >
													<vgcutil:message key="jsp.asistente.tipo.transversal" />
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="asistenteTipo" value="1" class="botonSeleccionSimple" >
													<vgcutil:message key="jsp.asistente.tipo.longitudinal" />
												</td>
											</tr>
											<tr>
												<td align="right">
													<vgcutil:message key="jsp.asistente.tipo.frecuencia" />
												</td>
												<td>
													<html:select property="frecuencia" styleClass="cuadroTexto" size="1" onchange="eventoCambioFrecuencia()">
													<html:option value=""></html:option>
													<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
													</html:select>
												</td>									
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Insumos --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelInsumos" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td colspan="2">
													<vgcutil:message key="jsp.asistente.insumo.titulo" />
												</td>
											</tr>
											<tr align="center">
												<td>
													<input id="insumoSeleccionado" type="hidden" name="insumoSeleccionado" />
													<html:hidden property="insumosFormula" />
													<input type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />" onclick="seleccionarIndicadores();">
												</td>
												<td>
													<input type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />" onclick="removerInsumo();">
												</td>
											</tr>
											<tr height="230px" valign="top">
												<td colspan="2">
													<table class="contenedorBotonesSeleccion" cellpadding="0" cellspacing="0" width="100%" height="100%">
														<tr valign="top">
															<td>
																<table id="tablaListaInsumos">
																	<tbody class="cuadroTexto">
																	</tbody>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Formato --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelReporte" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.reporte.mensaje.1" /></br></br> 
													<vgcutil:message key="jsp.asistente.reporte.mensaje.2" />
												</td>
											</tr>
											<tr>
												<td align="right">
													<input type="button" style="width:100px" class="cuadroTexto" value="<vgcutil:message key="boton.formato" />" onclick="editarFormato();">
												</td>
											</tr>
											<tr>
												<td align="right">
													<input type="button" style="width:100px" class="cuadroTexto" value="<vgcutil:message key="boton.formato" />" onclick="editarFormato2();">
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Nombre --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelNombre" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.nombre" /></td>
											</tr>
											<tr>
												<td>
													<html:text property="reporteNombre" size="50" disabled="false" maxlength="50" styleClass="cuadroTexto" />
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Finalizar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelFin" mostrarBorde="false">
										<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
											<tr>
												<td><span id="mensajeFinal"></td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
	
								</vgcinterfaz:contenedorPaneles>
							</td>
						</tr>
					</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<script>
			mostrarTitulo();
			mostrarBotones(pasoActual);
			document.reporteForm.asistenteEditar[0].checked = true;
		</script>
	</tiles:put>
</tiles:insert>
