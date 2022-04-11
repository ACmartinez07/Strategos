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
		<vgcutil:message key="jsp.asistente.grafico.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript">
			var pasoActual = 1;
			var desdeMes = 1;
			var hastaMes =12;
			var desdeAno = '<bean:write name="graficoForm" property="anoInicial" ignore="true"/>';
			var hastaAno = '<bean:write name="graficoForm" property="anoFinal" ignore="true"/>';
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';
			
			function init()
			{
				var fecha = new Date (); 
				var anio = fecha.getYear(); 
				if ( anio < 1900 ) 
					anio = 1900 + fecha.getYear();
				desdeAno = anio;
				hastaAno = anio;
				
				var periodo = document.getElementById("tdPeriodoInicial");
				periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
				
				periodo = document.getElementById("tdPeriodoFinal");
				periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
				agregarPeriodos("3");
				SetFrecuenciasCompatibles("3");
			}
			
			function agregarPeriodos(frecuencia)
			{
				var select = document.getElementById("selectPeriodoInicial");
				var max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;

			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if (document.graficoForm.periodoInicial.value != "")
			    	select.selectedIndex = document.graficoForm.periodoInicial.value -1;
			    else
			    	select.selectedIndex = 0;
			    
				select = document.getElementById("selectPeriodoFinal");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);
			    
			    if (document.graficoForm.periodoFinal.value != "")
			    	select.selectedIndex = document.graficoForm.periodoFinal.value -1;
			    else
			    	select.selectedIndex = 0;
			    
			    hastaMes = numeroPeriodoMaximo(frecuencia);
			}
			
			function addElementToSelect(combo, texto, valor)
			{
			    var idxElemento = combo.options.length; //Numero de elementos de la combo si esta vacio es 0
			    
			    //Este indice será el del nuevo elemento
			    combo.options[idxElemento] = new Option();
			    combo.options[idxElemento].text = texto; //Este es el texto que verás en la combo
			    combo.options[idxElemento].value = valor; //Este es el valor que se enviará cuando hagas un submit del
		   	}			
			
			function numeroPeriodoMaximo(frecuencia)
			{
			    switch (frecuencia) 
			    {
		    		case "0":
		      			return 365;
		    		case "1":
		      			return 52;
	    			case "2":
		      			return 24;
				    case "3":
				      return 12;
				    case "4":
				      return 6;
				    case "5":
				      return 4;
				    case "6":
				      return 3;
				    case "7":
				      return 2;
				    case "8":
				      return 1;
		    	}
		    	return -1;
			}
			
			function siguiente() 
			{
				var valido = true; 

				switch (pasoActual) 
				{
					case 1:
					case 4:
						break;
					case 2:
					case 3:
					case 5:
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
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelInsumos" />
						break;
					case 4:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelRangoGrafico" />
						break;
					case 5:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelGrafico" />
						break;
					case 6:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelProperties" />
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
				var titulo = '..:: <vgcutil:message key="jsp.asistente.grafico.titulo1" /> ' + (pasoActual + ' <vgcutil:message key="jsp.asistente.grafico.titulo2" />');
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;

				celda = document.getElementById("mensajeFinal");
				celda.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.fin" />';
			}
			
			function validar() 
			{
				if (pasoActual == 2 && window.document.graficoForm.graficoSeleccion.value != "")
				{
					window.document.graficoForm.titulo.value = window.document.graficoForm.graficoSeleccion.value; 
					return true;
				}
				if (pasoActual == 3)
				{
					var tablaListaInsumos = document.getElementById('tablaListaInsumos');
					if (tablaListaInsumos.getElementsByTagName("tr").length == 0) 
					{
						alert('<vgcutil:message key="jsp.asistente.grafico.insumo.alerta.noinsumos" /> ');
						return false;
					}
				}
				if (pasoActual == 5 && document.graficoForm.tipo.value < 1)
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.tipografico.alerta.notipo" /> ');
					return false;
				}
				if (pasoActual == 6 && (window.document.graficoForm.titulo.value == ""))
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.titulosgraficos.alerta.titulos" /> ');
					return false;
				}
				document.graficoForm.graficoNombre.value = document.graficoForm.titulo.value;
				
				return true;
			}
			
			function cancelar() 
			{
				this.close();			
			}
			
			function getXml(codificar)
			{
				var writer = new XmlTextWriter();
				writer.Formatting = true;
	            writer.WriteStartDocument();
	            writer.WriteStartElement("grafico");
	            writer.WriteStartElement("properties");
	            writer.WriteElementString("tipo", document.graficoForm.tipo.value);
	            writer.WriteElementString("titulo", CodificarString(document.graficoForm.titulo.value, codificar));
	            writer.WriteElementString("tituloEjeY", CodificarString(document.graficoForm.tituloEjeY.value, codificar));
	            writer.WriteElementString("tituloEjeX", CodificarString(document.graficoForm.tituloEjeX.value, codificar));
	            writer.WriteElementString("anoInicial", document.graficoForm.anoInicial.value);
	            writer.WriteElementString("periodoInicial", document.graficoForm.periodoInicial.value);
	            writer.WriteElementString("anoFinal", document.graficoForm.anoFinal.value);
	            writer.WriteElementString("periodoFinal", document.graficoForm.periodoFinal.value);
	            writer.WriteElementString("frecuencia", document.graficoForm.frecuencia.value);
	            writer.WriteElementString("frecuenciaAgrupada", document.getElementById("selectFrecuenciasCompatibles").value);
	            writer.WriteElementString("nombre", CodificarString(document.graficoForm.titulo.value, codificar));
	            writer.WriteElementString("condicion", (document.graficoForm.condicion != null ? document.graficoForm.condicion.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("verTituloImprimir", "1");
	            writer.WriteElementString("ajustarEscala", "1");
	            writer.WriteStartElement("indicadores");
	            
				var planId = "";
				
				// Se recorre la lista de insumos
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				for (var i = 0; i < insumos.length; i++) 
				{
					if (insumos[i].length > 0) 
					{
						// correlativo
						var strTemp = insumos[i];
						var indice = strTemp.indexOf("][") + 1;
						var correlativo = strTemp.substring(0, indice);
						// indicadorId
						strTemp = strTemp.substring(indice, strTemp.length);
						indice = strTemp.indexOf("][");
						indicadorId = strTemp.substring('indicadorId:'.length + 1, indice);
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieId = strTemp.substring('serieId:'.length + 1, indice);
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring('planId:'.length + 1, indice);
						// nombreIndicador
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						nombreIndicador = strTemp.substring('indicadorNombre:'.length + 1, indice);
						// nombreSerie
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieNombre = strTemp.substring('serieNombre:'.length + 1, indice);
						
						var nombreLeyenda = nombreIndicador + " - " + serieNombre;
						var color = getRndColorRGB();
						var visible = "1";
						var tipoGrafico = "-1";

						writer.WriteStartElement("indicador");

						var objetosSeleccionados = indicadorId.split("&");
						if (planId == null || (planId != null && planId != "" && planId != "0" && objetosSeleccionados.length > 0))
						{
							writer.WriteElementString("id", objetosSeleccionados[0]);
							if (objetosSeleccionados.length > 1)
							{
								for (var j = 1; j < objetosSeleccionados.length; j++)
								{
									if (objetosSeleccionados[j].length > 0)
									{
										var selecciones = objetosSeleccionados[j].split("=");
										writer.WriteElementString(selecciones[0], selecciones[1]);
									}
								}
							}
							else
								writer.WriteElementString("planId", planId);
						}
						else
						{
							writer.WriteElementString("id", indicadorId);
							writer.WriteElementString("planId", planId);
						}
						writer.WriteElementString("serie", serieId);
						writer.WriteElementString("leyenda", CodificarString(nombreLeyenda, codificar));
						writer.WriteElementString("color", color.replace("#", "[[num]]"));
						writer.WriteElementString("visible", visible);
						writer.WriteElementString("tipoGrafico", tipoGrafico);
						
						writer.WriteEndElement();
					}
				}

	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndDocument();
				
	            return writer.unFormatted();
			}

			function CodificarString(value, codificar)
			{
				var valor = value;

				if (codificar)
					valor = valor.replace("%", "[[por]]");
				
				return valor;
			}
			
			function salvar() 
			{
				if (validar())
				{
					document.graficoForm.respuesta.value = "";

					var parametros = 'data=' + getXml(true);
					parametros = parametros + '&id=' + document.graficoForm.id.value;
					parametros = parametros + '&nombre=' + CodificarString(document.graficoForm.titulo.value, true);
					parametros = parametros + '&source=General';
					parametros = parametros + '&showPresentacion=' + (document.graficoForm.showPresentacion.checked ? 1 : 0);
					parametros = parametros + '&funcion=SalvarAsistente';
					
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/guardarAsistenteGrafico" />?' + parametros, document.graficoForm.respuesta, 'onSalvar()');
				} 
				else
				{
					pasoActual = 8;
					mostrarBotones(pasoActual);
					mostrarTitulo();
				}
			}
			
			function rgbToHex(r, g, b) 
			{
			    return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
			}
			
			function hexToRgb(hex) 
			{
			    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
			    return result ? 
				{
			        r: parseInt(result[1], 16),
			        g: parseInt(result[2], 16),
			        b: parseInt(result[3], 16)
			    } : null;
			}			
			
			function getRndColorRGB()
			{
				var valor = Math.floor(Math.random()*16777215).toString(16);
				if (valor.length < 6)
				{
					do 
					{
						valor+='0';
					} while(valor.length < 5);
				}
					
				return '#'+ valor;
			}
			
			function convertRGBDecimal(colorRGB)
			{
				return "PF" + hexToRgb(colorRGB).r + "PF" + hexToRgb(colorRGB).g + "PF" + hexToRgb(colorRGB).b + "PF";
			}
			
			function onMerge()
			{
				var respuesta = 'id=' + document.graficoForm.id.value;
				respuesta = respuesta + '&nombre=' + document.graficoForm.titulo.value;
				
				this.opener.document.<bean:write name="graficoForm" property="nombreForma" scope="request" />.<bean:write name="graficoForm" property="nombreCampoOculto" scope="request" />.value=respuesta;
				this.opener.<bean:write name="graficoForm" property="funcionCierre" scope="request" />;
				this.close();
			}
			
			function onSalvar()
			{
				var respuesta = document.graficoForm.respuesta.value.split("|");
				if (respuesta.length > 0 && respuesta[1] == 0)
				{
					document.graficoForm.id.value = respuesta[0]; 
					this.opener.document.<bean:write name="graficoForm" property="nombreForma" scope="request" />.<bean:write name="graficoForm" property="nombreCampoOculto" scope="request" />.value=document.graficoForm.id.value;
					this.opener.<bean:write name="graficoForm" property="funcionCierre" scope="request" />;
					this.close();
					return;
				}
				
				alert('<vgcutil:message key="jsp.asistente.grafico.alert.nombre.duplicado" /> ');
				pasoActual = 8;
				mostrarBotones(pasoActual);
				mostrarTitulo();
			}
			
			function eventoOnclickNo()
			{
				limpiarGrafico();
			}
			
			function seleccionarGraficos()
			{
				if (document.graficoForm.asistenteEditar[0].checked)
					document.graficoForm.asistenteEditar[1].checked = true;

				var nombreForma = '?nombreForma=' + 'graficoForm';
				var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
				var funcionCierre = '&funcionCierre=' + 'onListaGrafico()';

				abrirVentanaModal('<html:rewrite action="/graficos/listaGrafico" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarGraficos', '500', '575');
		    }
		    
		    function onListaGrafico()
		    {
		    	var respuesta = document.graficoForm.respuesta.value.split("|");
				if (respuesta.length > 0)
				{
					var campos;
					for (var i = 0; i < respuesta.length; i++)
					{
						campos = respuesta[i].split(',');
						if (eval('document.graficoForm.' + campos[0].replace("!", "")) != null)
						{
							if (campos[0] == "acumular" || campos[0].replace("!", "") == "compararVsRangoAnterior")
								eval('document.graficoForm.' + campos[0].replace("!", "") + '.checked=\"' + (campos[1].replace("!", "") == 1 ? true : "") + '\";');
							else
								eval('document.graficoForm.' + campos[0].replace("!", "") + '.value=\"' + campos[1].replace("!", "") + '\";');
						}
					}
				}
				
				// Se copia el nombre en el campo se seleccion
				document.graficoForm.graficoSeleccion.value = document.graficoForm.titulo.value;
				eventoCambioFrecuencia(false);
				
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
				// Se agregan los insumos de los indicadores
				agregarIndicadores();
				
				// Se selecciona el grafico
				setTipo(parseInt(document.graficoForm.tipo.value), false);
		    }
			
			function limpiarGrafico()
			{
				window.document.graficoForm.graficoSeleccion.value = '';
				window.document.graficoForm.graficoSeleccionId.value = '';
			}
			
			function seleccionarIndicadores() 
			{
				var forma = 'graficoForm';
				var funcionRetorno = 'agregarIndicadores()';
				var url = '&agregarSerieMeta=true&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true' + '&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.graficoForm.frecuencia.value + '&excluirIds=0';
		
				abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', funcionRetorno, null, null, null, null, null, url, 'indicadorInsumoSeleccionadoSeriePlanId');
			}
			
			function agregarIndicadores()
			{
				var seleccionadoIds = document.graficoForm.indicadorInsumoSeleccionadoIds.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoNombres = document.graficoForm.indicadorInsumoSeleccionadoNombres.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoRutasCompletas = document.graficoForm.indicadorInsumoSeleccionadoRutasCompletas.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoSeriePlanId = document.graficoForm.indicadorInsumoSeleccionadoSeriePlanId.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				
				var listaInsumos = document.graficoForm.insumosFormula.value;
				var correlativo = 0;
				
				if (listaInsumos != null) 
				{
					if (listaInsumos == '') // no hay insumos en la lista 
						correlativo = 1;
					else 
					{
						strBuscada = ']' + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>' + '[';
						indice1 = listaInsumos.lastIndexOf(strBuscada);
						if (indice1 > -1) 
						{
							// hay dos o más insumos en la lista
							indice1 = indice1 + strBuscada.length;
							indice2 = listaInsumos.substring(indice1, listaInsumos.length).indexOf(']');
							indice2 = indice1 + indice2;
							correlativo = parseInt(listaInsumos.substring(indice1 , indice2)) + 1;
						} else 
						{
							// hay un solo insumo en la lista
							indice = listaInsumos.indexOf(']');
							correlativo = parseInt(listaInsumos.substring(1, indice)) + 1;
						};
					};
				} 
				else 
				{
					listaInsumos = '';
					correlativo = 1;
				}
			
				for (var i = 0; i < seleccionadoIds.length; i++) 
				{
					partesId = seleccionadoIds[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
					nombresIndicador = seleccionadoNombres[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
					planesIds = seleccionadoSeriePlanId[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
			
					insumoBuscado = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + '][planId:' + planesIds[1] + ']';
			
					if (listaInsumos.indexOf(insumoBuscado) < 0) 
					{
						var separadorIndicadores = '';
						if (listaInsumos != '') 
							separadorIndicadores = '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>';
						
						if (seleccionadoRutasCompletas[i] != '<bean:write name="graficoForm" property="codigoIndicadorEliminado" ignore="true"/>') 
						{
							listaInsumos = listaInsumos 
										+ separadorIndicadores
										+ '[' + correlativo + ']' 
										+ insumoBuscado 
										+ '[indicadorNombre:' + nombresIndicador[0]  + ']'
										+ '[serieNombre:' + nombresIndicador[1] + ']'
										+ '[rutaCompleta:' + seleccionadoRutasCompletas[i] + ']';
							correlativo++;
						};
					};
				}
				
				document.graficoForm.insumosFormula.value = listaInsumos;
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
			
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
			
				// Se recorre la lista de insumos
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
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
						// planId
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
						};
						tbody.appendChild(row);
						if (numFilas == 0) 
						{
							selectRowColors(0,
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
						};
					};
				};
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
			
			function removerInsumo() 
			{
				var tabla = document.getElementById('tablaListaInsumos');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				var insumosFormula = document.graficoForm.insumosFormula.value;
			
				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.asistente.grafico.insumo.noinsumos" />');
				else 
				{
					// Se modifica el string que contiene los insumos seleccionados
					var posicionBuscada = parseInt(document.graficoForm.insumoSeleccionado.value);
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
					} else if ((posicionBuscada + 1) == numeroFilas) 
					{
						var strBuscada = ']' + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						insumosFormula = insumosFormula.substring(0, index - 1);
					} else 
					{
						var strBuscada = ']' + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = 0;
						for (var i = 0; i < posicionBuscada; i++) 
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
					document.graficoForm.insumosFormula.value = insumosFormula;
					deleteRowColors(tabla, 
									document.graficoForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
				};
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
			
			function eventoCambioFrecuencia(limpiar)
			{
				if (limpiar == undefined)
					limpiar = true;
				
				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				if (limpiar)
				{
					document.graficoForm.periodoInicial.value = "";
					document.graficoForm.periodoFinal.value = "";
	
					var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
					// Se borra la lista de insumos
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);
				}
				
				switch (document.graficoForm.frecuencia.value) 
				{
					case "0":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.dia" />';
		
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.dia" />';
						break;		
					case "1":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						break;		
					case "2":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						break;		
					case "3":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						break;		
					case "4":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						break;		
					case "5":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						break;		
					case "6":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						break;		
					case "7":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						break;		
					case "8":
						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';
						
						document.getElementById("trPeriodoInicial").style.display = "none";
						document.getElementById("trPeriodoFinal").style.display = "none";
						break;		
				}
				
				agregarPeriodos(document.graficoForm.frecuencia.value);
				
				SetFrecuenciasCompatibles(document.graficoForm.frecuencia.value);
			}
			
		  	function validarRango(desdeAnoObj, hastaAnoObj, desdeMesObj, hastaMesObj, errmsg)
		  	{
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.graficoForm.frecuencia.value == "0" ? "00" : "0") : "") + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.graficoForm.frecuencia.value == "0" ? "00" : "0") : "") + hastaMesObj.value);

				if (hasta<desde) 
				{
					alert(errmsg);
					hastaAnoObj.value = hastaAno;
					hastaMesObj.value = hastaMes;				
					desdeAnoObj.value = desdeAno;
					desdeMesObj.value = desdeMes;
				} else 
				{
					desdeAno = desdeAnoObj.value;
					hastaAno = hastaAnoObj.value;
					desdeMes = desdeMesObj.value;
					hastaMes = hastaMesObj.value;
				}
				
				document.graficoForm.periodoInicial.value = desdeMesObj.value;
				document.graficoForm.periodoFinal.value = hastaMesObj.value;
			}
		  	
		  	function setTipo(tipo, avanzar)
		  	{
		  		if (avanzar == undefined)
		  			avanzar = true;
		  		
		  		var imageUrl = 'url("<html:rewrite page='/paginas/strategos/graficos/imagenes/Seleccionado.png'/>")';
		  		
		  		document.getElementById("graficoLinea").style.backgroundImage = "";
		  		document.getElementById("graficoColumna").style.backgroundImage = "";
		  		document.getElementById("graficoBarra").style.backgroundImage = "";
		  		document.getElementById("graficoArea").style.backgroundImage = "";
		  		document.getElementById("graficoTorta").style.backgroundImage = "";
		  		document.getElementById("graficoPareto").style.backgroundImage = "";
		  		document.getElementById("graficoGauge").style.backgroundImage = "";
		  		document.getElementById("graficoCombinado").style.backgroundImage = "";
		  		document.getElementById("graficoCascada").style.backgroundImage = "";
		  		document.getElementById("graficoTorta3D").style.backgroundImage = "";
		  		document.getElementById("graficoBarra3D").style.backgroundImage = "";
		  		document.getElementById("graficoBarraApilada3D").style.backgroundImage = "";
		  		document.graficoForm.tipo.value = tipo;

		  		var tipografico = document.getElementById("tipoGraficoSeleccionado");
				switch (tipo) 
				{
					case 1:
					case 13:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.lineas" />';
						document.getElementById("graficoLinea").style.backgroundImage = imageUrl;
						break;
					case 2:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />';
						document.getElementById("graficoColumna").style.backgroundImage = imageUrl;
						break;
					case 3:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />';
						document.getElementById("graficoBarra").style.backgroundImage = imageUrl;
						break;
					case 4:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.area" />';
						document.getElementById("graficoArea").style.backgroundImage = imageUrl;
						break;
					case 5:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />';
						document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
						break;
					case 6:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.pareto" />';
						document.getElementById("graficoPareto").style.backgroundImage = imageUrl;
						break;
					case 7:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.multipletorta" />';
						document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
						break;
					case 8:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.combinado" />';
						document.getElementById("graficoCombinado").style.backgroundImage = imageUrl;
						break;
					case 9:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.cascada" />';
						document.getElementById("graficoCascada").style.backgroundImage = imageUrl;
						break;
					case 10:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />';
						document.getElementById("graficoTorta3D").style.backgroundImage = imageUrl;
						break;
					case 11:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />';
						document.getElementById("graficoBarra3D").style.backgroundImage = imageUrl;
						break;
					case 12:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barraapilada3d" />';
						document.getElementById("graficoBarraApilada3D").style.backgroundImage = imageUrl;
						break;
					case 14:
						tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.gauge" />';
						document.getElementById("graficoGauge").style.backgroundImage = imageUrl;
						break;
				}
		  	}
		  	
		  	function changeImages() 
		  	{
		  		var graficoLineaIn = new Image();
		  		graficoLineaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/LineaIn.png'/>";
		  		var graficoLineaOut = new Image();
		  		graficoLineaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Linea.png'/>";

		  		var graficoColumnaIn = new Image();
		  		graficoColumnaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/ColumnaIn.png'/>";
		  		var graficoColumnaOut = new Image();
		  		graficoColumnaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Columna.png'/>";

		  		var graficoBarraIn = new Image();
		  		graficoBarraIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/BarraIn.png'/>";
		  		var graficoBarraOut = new Image();
		  		graficoBarraOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra.png'/>";

		  		var graficoAreaIn = new Image();
		  		graficoAreaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/AreaIn.png'/>";
		  		var graficoAreaOut = new Image();
		  		graficoAreaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Area.png'/>";
		  		
		  		var graficoTortaIn = new Image();
		  		graficoTortaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/PieIn.png'/>";
		  		var graficoTortaOut = new Image();
		  		graficoTortaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie.png'/>";

		  		var graficoParetoIn = new Image();
		  		graficoParetoIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/ParetoIn.png'/>";
		  		var graficoParetoOut = new Image();
		  		graficoParetoOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pareto.png'/>";

		  		var graficoGaugeIn = new Image();
		  		graficoGaugeIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/GaugeIn.png'/>";
		  		var graficoGaugeOut = new Image();
		  		graficoGaugeOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Gauge.png'/>";

		  		var graficoCombinadoIn = new Image();
		  		graficoCombinadoIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/CombinadoIn.png'/>";
		  		var graficoCombinadoOut = new Image();
		  		graficoCombinadoOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Combinado.png'/>";

		  		var graficoCascadaIn = new Image();
		  		graficoCascadaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/CascadaIn.png'/>";
		  		var graficoCascadaOut = new Image();
		  		graficoCascadaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Cascada.png'/>";

		  		var graficoTorta3DIn = new Image();
		  		graficoTorta3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3DIn.png'/>";
		  		var graficoTorta3DOut = new Image();
		  		graficoTorta3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3D.png'/>";

		  		var graficoBarra3DIn = new Image();
		  		graficoBarra3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3DIn.png'/>";
		  		var graficoBarra3DOut = new Image();
		  		graficoBarra3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3D.png'/>";

		  		var graficoBarraApilada3DIn = new Image();
		  		graficoBarraApilada3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Apilada3DIn.png'/>";
		  		var graficoBarraApilada3DOut = new Image();
		  		graficoBarraApilada3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Apilada3D.png'/>";
		  		
		  		if (document.images) 
		  		{
		  			for (var i=0; i<changeImages.arguments.length; i+=2) 
		  				document[changeImages.arguments[i]].src = eval(changeImages.arguments[i+1] + ".src");
		  		}
		  	}

			function SetFrecuenciasCompatibles(frecuencia)
			{
				select = document.getElementById("selectFrecuenciasCompatibles");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			 	var frecuencias = frecuenciasCompatibles(frecuencia);
			 	var index = 0;
			    for (var i = 0; i < frecuencias.length; i++)
		    	{
			    	addElementToSelect(select, frecuencias[i].Nombre, frecuencias[i].Id);
			    	if (document.graficoForm.frecuenciaAgrupada.value == frecuencias[i].Id) 
			    		index = i; 
		    	}
			    
		    	select.selectedIndex = index;
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/graficos/asistenteGrafico">

			<%-- Campos hidden para el manejo de la insumos --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">
			<input type="hidden" name="indicadorInsumoSeleccionadoSerie">
			<input type="hidden" name="indicadorInsumoSeleccionadoSeriePlanId">
				
			<html:hidden property="tipo" />		
			<html:hidden property="id" />
			<html:hidden property="respuesta" />
			<html:hidden property="xmlAnterior" />
			<html:hidden property="periodoInicial" />
			<html:hidden property="periodoFinal" />
			<html:hidden property="condicion" />
			<html:hidden property="insumosSerie" />
			<html:hidden property="frecuenciaAgrupada" />
			<html:hidden property="graficoNombre" />

			<vgcinterfaz:contenedorForma width="580px" height="420px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
					<table class="bordeFichaDatostabla bordeFichaDatos">
						<tr height=315px>
	
							<%-- Imágen del asistente --%>
							<td width="315px">
								<img src="<html:rewrite page='/paginas/strategos/graficos/imagenes/WZGrafico.gif'/>" border="0" width="150px" height="310px">
							</td>
	
							<td>
								<vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">
	
									<%-- Panel Presentacion --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
										<table class="tablaSpacing0Padding0Width100Collapse">
											<tr>
												<td>
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td>
																<b><vgcutil:message key="jsp.asistente.grafico.presentacion.importante" /></b><br><br> 
																<vgcutil:message key="jsp.asistente.grafico.presentacion.1" /><br><br>
																<vgcutil:message key="jsp.asistente.grafico.presentacion.2" /><br><br>
																<vgcutil:message key="jsp.asistente.grafico.presentacion.3" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<br><br><br>
													<br><br><br>
													<br><br>
												</td>
											</tr>
											<tr>
												<td class="contenedorTextoSeleccion">
													<html:checkbox property="showPresentacion" styleClass="botonSeleccionMultiple">
														<vgcutil:message key="jsp.asistente.grafico.presentacion.mostrar" />
													</html:checkbox>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel seleccion de plantilla --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelEditarPlantilla" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.editar" /></td>
											</tr>
											<tr>
												<td>
													<html:radio property="asistenteEditar" value="0" onclick="eventoOnclickNo()">
														<vgcutil:message key="jsp.asistente.grafico.editar.no" />
													</html:radio>
												</td>
											</tr>
											<tr>
												<td>
													<html:radio property="asistenteEditar" value="1">
														<vgcutil:message key="jsp.asistente.grafico.editar.si" />
													</html:radio>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<html:text property="graficoSeleccion" size="44" readonly="true" disabled="false" maxlength="50" styleClass="cuadroTexto" />&nbsp;
													<img style="cursor: pointer" onclick="seleccionarGraficos();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.grafico.editar.seleccionar.plantilla" />">&nbsp;
													<img style="cursor: pointer" onclick="limpiarGrafico();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
													<html:hidden property="graficoSeleccionId" />
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<%-- Panel Insumos --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelInsumos" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<br>
												</td>
											</tr>
											<tr>
												<td align="left">
													<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
												</td>
												<td>
													<html:select property="frecuencia"
														styleClass="cuadroTexto" size="1"
														onchange="eventoCambioFrecuencia()">
														<html:optionsCollection property="frecuencias"
															value="frecuenciaId" label="nombre" />
													</html:select>
												</td>
											</tr>
											<tr>
												<td>
													<br>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<vgcutil:message key="jsp.asistente.grafico.insumo.titulo" />
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
											<tr height="200px" valign="top">
												<td colspan="2">
													<table class="tablainsumo contenedorBotonesSeleccion">
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
									
									<%-- Panel seleccion de rangos --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelRangoGrafico" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.rango" /></td>
											</tr>
											<tr>
												<td width="50%">
													<vgcutil:message key="jsp.asistente.grafico.inicial" />
												</td>
												<td width="50%">
													<vgcutil:message key="jsp.asistente.grafico.final" />
												</td>
											</tr>
											<tr>
												<bean:define id="maximoPeriodo" name="graficoForm" property="numeroMaximoPeriodos" type="java.lang.Integer" toScope="page" />
												<td width="50%">
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td>
																<vgcutil:message key="jsp.asistente.grafico.ano" />
															</td>
															<td>
																<bean:define id="anoCalculoInicial" toScope="page">
																	<bean:write name="graficoForm" property="anoInicial" />
																</bean:define>
																<html:select property="anoInicial" value="<%= anoCalculoInicial %>" onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)" styleClass="cuadroTexto">
																	<%
																	for (int i = 1900; i <= 2050; i++) {
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
														<tr id="trPeriodoInicial">
															<td>
																<span id="tdPeriodoInicial"></span>
															</td>
															<td>
																<select id="selectPeriodoInicial" onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)" class="cuadroTexto">
																</select>
															</td>
														</tr>
													</table>
												</td>
												<td width="50%">
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td>
																<vgcutil:message key="jsp.asistente.grafico.ano" />
															</td>
															<td>
																<bean:define id="anoCalculoFinal" toScope="page">
																	<bean:write name="graficoForm" property="anoFinal" />
																</bean:define>
																<html:select property="anoFinal" value="<%= anoCalculoFinal %>" onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)" styleClass="cuadroTexto">
																	<%
																	for (int i = 1900; i <= 2050; i++) {
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
														<tr id="trPeriodoFinal">
															<td>
																<span id="tdPeriodoFinal"></span>
															</td>
															<td>
																<select id="selectPeriodoFinal" onchange="validarRango(document.graficoForm.anoInicial, document.graficoForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)" class="cuadroTexto">
																</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td align="left"><vgcutil:message key="jsp.asistente.grafico.agruparFrecuencia" /></td>
												<td>
													<select id="selectFrecuenciasCompatibles" name="selectFrecuenciasCompatibles" class="cuadroTexto"></select>
												</td>
											</tr>
											<%-- 
											<tr>
												<td>
												</td>
												<td>
													<html:checkbox property="acumular" styleClass="botonSeleccionMultiple">
														<vgcutil:message key="jsp.asistente.grafico.acumular" />
													</html:checkbox><br><br>
												</td>
											</tr>
											<tr>
												<td align="left">
													<html:checkbox property="compararVsRangoAnterior" styleClass="botonSeleccionMultiple">
														<vgcutil:message key="jsp.asistente.grafico.compararvsrangoanterior" />
													</html:checkbox>
												</td>
											</tr>
											--%>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Tipo Grafico --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelGrafico" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.grafico.tipo" /> 
												</td>
											</tr>
											<tr>
												<td>
													<table class="tabla">
														<tr>
															<td>
																<table class="tablaImagenes">
																	<tr>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(1, true)" onmouseover="changeImages('graficoLinea', 'graficoLineaIn')" onmouseout="changeImages('graficoLinea', 'graficoLineaOut')">
																				<img id="graficoLinea" name="graficoLinea" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Linea.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.lineas" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(2, true)" onmouseover="changeImages('graficoColumna', 'graficoColumnaIn')" onmouseout="changeImages('graficoColumna', 'graficoColumnaOut')">
																				<img id="graficoColumna" name="graficoColumna" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Columna.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(3, true)" onmouseover="changeImages('graficoBarra', 'graficoBarraIn')" onmouseout="changeImages('graficoBarra', 'graficoBarraOut')">
																				<img id="graficoBarra" name="graficoBarra" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(4, true)" onmouseover="changeImages('graficoArea', 'graficoAreaIn')" onmouseout="changeImages('graficoArea', 'graficoAreaOut')">
																				<img id="graficoArea" name="graficoArea" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Area.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.area" />">
																			</a>
																		</td>
																	</tr>
																	<tr>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(5, true)" onmouseover="changeImages('graficoTorta', 'graficoTortaIn')" onmouseout="changeImages('graficoTorta', 'graficoTortaOut')">
																				<img id="graficoTorta" name="graficoTorta" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(6)" onmouseover="changeImages('graficoPareto', 'graficoParetoIn')" onmouseout="changeImages('graficoPareto', 'graficoParetoOut')">
																				<img id="graficoPareto" name="graficoPareto" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pareto.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.pareto" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(14, true)" onmouseover="changeImages('graficoGauge', 'graficoGaugeIn')" onmouseout="changeImages('graficoGauge', 'graficoGaugeOut')">
																				<img id=graficoGauge name="graficoGauge" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Gauge.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.gauge" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(8)" onmouseover="changeImages('graficoCombinado', 'graficoCombinadoIn')" onmouseout="changeImages('graficoCombinado', 'graficoCombinadoOut')">
																				<img id="graficoCombinado" name="graficoCombinado" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Combinado.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.combinado" />">
																			</a>
																		</td>
																	</tr>
																	<tr>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(9, true)" onmouseover="changeImages('graficoCascada', 'graficoCascadaIn')" onmouseout="changeImages('graficoCascada', 'graficoCascadaOut')">
																				<img id="graficoCascada" name="graficoCascada" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Cascada.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.cascada" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(10, true)" onmouseover="changeImages('graficoTorta3D', 'graficoTorta3DIn')" onmouseout="changeImages('graficoTorta3D', 'graficoTorta3DOut')">
																				<img id="graficoTorta3D" name="graficoTorta3D" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3D.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(11, true)" onmouseover="changeImages('graficoBarra3D', 'graficoBarra3DIn')" onmouseout="changeImages('graficoBarra3D', 'graficoBarra3DOut')">
																				<img id="graficoBarra3D" name="graficoBarra3D" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3D.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />">
																			</a>
																		</td>
																		<td bgcolor="#FFFFFF">
																			<a href="#" onclick="setTipo(12, true)" onmouseover="changeImages('graficoBarraApilada3D', 'graficoBarraApilada3DIn')" onmouseout="changeImages('graficoBarraApilada3D', 'graficoBarraApilada3DOut')">
																				<img id="graficoBarraApilada3D" name="graficoBarraApilada3D" src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Apilada3D.png'/>" border="0" width="72px" height="72px" alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barraapilada3d" />">
																			</a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.grafico.tipografico.tipo.seleccionado" />
													&nbsp;<span id="tipoGraficoSeleccionado"></span>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<%-- Panel Propiedades --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelProperties" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.titulosgraficos" /><br><br></td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.grafico.titulosgraficos.titulo" />
												</td>
											</tr>
											<tr>
												<td>
													<html:text property="titulo" size="50" disabled="false" maxlength="50" styleClass="cuadroTexto" />
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.grafico.titulosgraficos.ejey" />
												</td>
											</tr>
											<tr>
												<td>
													<html:text property="tituloEjeY" size="50" disabled="false" maxlength="50" styleClass="cuadroTexto" />
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.grafico.titulosgraficos.ejex" />
												</td>
											</tr>
											<tr>
												<td>
													<html:text property="tituloEjeX" size="50" disabled="false" maxlength="50" styleClass="cuadroTexto" /><br><br>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<%-- Panel Finalizar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelFin" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td><span id="mensajeFinal"></span></td>
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
			if (document.graficoForm.showPresentacion.checked)
				pasoActual = 2;
			mostrarTitulo();
			init();
			mostrarBotones(pasoActual);
			document.graficoForm.asistenteEditar[0].checked = true;
			if (document.graficoForm.respuesta.value != "")
			{
				if (document.graficoForm.respuesta.value == "Error")
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.multiplesindicadores.error" />');
				else
				{
					var respuesta = document.graficoForm.respuesta.value.split("|");
					if (respuesta.length > 0)
					{
						var campos;
						for (var i = 0; i < respuesta.length; i++)
						{
							if (respuesta[i].indexOf("indicadorInsumoSeleccionadoIds") != -1 || respuesta[i].indexOf("indicadorInsumoSeleccionadoNombres") != -1 || respuesta[i].indexOf("indicadorInsumoSeleccionadoSeriePlanId") != -1)
							{
								campos = respuesta[i].split('!,!');
								if (eval('document.graficoForm.' + campos[0]) != null)
									eval('document.graficoForm.' + campos[0] + '.value=\"' + campos[1] + '\";');
							}
						}
					}

					eventoCambioFrecuencia(false);
					
					// Se borra la lista de insumos
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);
					// Se agregan los insumos de los indicadores
					agregarIndicadores();
					
					document.graficoForm.respuesta.value = "";
				}
			}
		</script>
	</tiles:put>
</tiles:insert>
