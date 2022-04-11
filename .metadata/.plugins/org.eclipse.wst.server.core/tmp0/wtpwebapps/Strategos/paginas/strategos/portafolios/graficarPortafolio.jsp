<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/09/2012) --%>

<tiles:insert definition="doc.modalLayoutHTML5" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.graficoindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones Style --%>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/window-modal/css/window-modal.css" />">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/paletaColor/css/colorPicker.css" />">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/style.css" />" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/grafico/css/print.css" />" media="print"/>

		<%-- Funciones JavaScript --%>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/graphicType.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/paletaColor/colorPicker.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/numeral.js'/>"></script>
		<script type="text/javascript">
			var _containerGraphic = 'div_grafico';
			var _separadorSeries = '<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>';
			var _fontSizeGraph = '14px';
			var _fontSizeGraphTitulo = '16px';
		</script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts-3d.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts-more.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/graphicExport.js" />"></script>
		
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/solid-gauge.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/exporting.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/export-csv.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/canvg.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/rgbcolor.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/modules/highcharts-export-clientside.js" />"></script>

		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/validateInput.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page="/componentes/comunes/jsComunes.jsp"/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>

		<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="graficoForm" property="bloqueado">
				<bean:write name="graficoForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="graficoForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var pasoActual = 1;
			var desdeMes = 1;
			var hastaMes =12;
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';
			var calendario = null;
			
			function seleccionarFecha() 
			{
				calendario = mostrarCalendario('document.graficoForm.fechaDesde' , document.graficoForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
		
			function cancelar() 
			{
				window.document.graficoForm.action = '<html:rewrite action="/iniciativa/grafico/crearGrafico"/>?cancelar=true';
				window.document.graficoForm.submit();			
			}
			
			function changeContent(tablecell)
			{
				if (tablecell.innerHTML.indexOf("INPUT") != -1) return;
			    tablecell.innerHTML = "<INPUT id=\"block\" type=\"text\" name=\"newname\" onBlur=\"javascript:submitNewName(this, '" + tablecell.id +"');\" value=\"" + tablecell.innerHTML + "\">";
			    tablecell.firstChild.focus();
			}

			function submitNewName(textfield, id)
			{
			    textfield.parentNode.innerHTML= textfield.value;
			    if (id.indexOf("_") != -1)
		    	{
			    	var serieId = id.replace(id.substring(0, id.indexOf("_") + 1), "");
			    	eval("window.document.getElementById('serie_name_' + " + serieId + ").value = '" + textfield.value + "';");
		    	}
			}
			
			function validar() 
			{
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				if (tablaListaInsumos != null && tablaListaInsumos.getElementsByTagName("tr").length == 0) 
				{
					alert('<vgcutil:message key="jsp.editar.grafico.portafolio.insumo.alerta.noinsumos" /> ');
					return false;
				}

				if (document.graficoForm.tipo.value < 1)
				{
					alert('<vgcutil:message key="jsp.editar.grafico.portafolio.tipografico.alerta.notipo" /> ');
					return false;
				}

				if (window.document.graficoForm.titulo.value == "")
				{
					alert('<vgcutil:message key="jsp.editar.grafico.portafolio.titulosgraficos.alerta.titulos" /> ');
					return false;
				}
				
				if (window.document.graficoForm.graficoNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.editar.grafico.portafolio.alert.nombre.invalido.vacio" /> ');
					return false;
				}
				
				return true;
			}
			
			function CodificarString(value, codificar)
			{
				var valor = value;

				if (codificar)
					valor = valor.replace("%", "[[por]]");
				
				return valor;
			}
			
			function getXml(codificar)
			{
				if (codificar == undefined)
					codificar = false;
				
				var writer = new XmlTextWriter();
				writer.Formatting = true;
	            writer.WriteStartDocument();
	            writer.WriteStartElement("grafico");
	            writer.WriteStartElement("properties");
	            writer.WriteElementString("tipo", document.graficoForm.tipo.value);
	            writer.WriteElementString("titulo", CodificarString(document.graficoForm.titulo.value, codificar));
	            writer.WriteElementString("tituloEjeY", CodificarString(document.graficoForm.tituloEjeY.value, codificar));
	            writer.WriteElementString("tituloEjeZ", CodificarString(document.graficoForm.tituloEjeZ.value, codificar));
	            writer.WriteElementString("tituloEjeX", CodificarString(document.graficoForm.tituloEjeX.value, codificar));
	            writer.WriteElementString("anoInicial", document.graficoForm.anoInicial.value);
	            writer.WriteElementString("periodoInicial", document.graficoForm.periodoInicial.value);
	            writer.WriteElementString("anoFinal", document.graficoForm.anoFinal.value);
	            writer.WriteElementString("periodoFinal", document.graficoForm.periodoFinal.value);
	            writer.WriteElementString("ano", document.graficoForm.ano.value);
	            writer.WriteElementString("periodo", document.graficoForm.periodo.value);
	            writer.WriteElementString("nombre", CodificarString(document.graficoForm.graficoNombre.value, codificar));
	            writer.WriteElementString("condicion", (document.graficoForm.condicion != null ? document.graficoForm.condicion.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("verTituloImprimir", (document.graficoForm.verTituloImprimir != null ? document.graficoForm.verTituloImprimir.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("ajustarEscala", (document.graficoForm.ajustarEscala != null ? document.graficoForm.ajustarEscala.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("impVsAnoAnterior", (document.graficoForm.impVsAnoAnterior != null ? document.graficoForm.impVsAnoAnterior.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("tipoGrafico", document.graficoForm.tipoGrafico.value);

				// Se recorre la lista de insumos
	            writer.WriteStartElement("iniciativas");
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var iniciativaId;
				for (var i = 0; i < insumos.length; i++) 
				{
					if (insumos[i].length > 0) 
					{
						// correlativo
						strTemp = insumos[i];
						indice = strTemp.indexOf("][") + 1;
						correlativo = strTemp.substring(0, indice);
						// iniciativaId
						strTemp = strTemp.substring(indice, strTemp.length);
						indice = strTemp.indexOf("][");
						iniciativaId = strTemp.substring(0, indice).replace("[iniciativaId:", "");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring(0, indice).replace("[planId:", "");
						
			            writer.WriteStartElement("iniciativa");
						writer.WriteElementString("id", iniciativaId);

						writer.WriteEndElement();
					};
				};

	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndDocument();
	            
	            return writer.unFormatted();
			}
			
			function imprimir(soloGrafico)
			{
				if (!validar())
					return;

				if (typeof(soloGrafico) == "undefined")
					soloGrafico = false;
				
		        $(function () {
					var grafico = $("#" + _containerGraphic).html();
					var tabla = null;
					if (!soloGrafico)
						tabla = $("#tblDatos").html();
					
	                var frame1 = $('<iframe />');
	                frame1[0].name = "frame1";
	                frame1.css({ "position": "absolute", "top": "-1000000px" });
	                $("body").append(frame1);
	                var frameDoc = frame1[0].contentWindow ? frame1[0].contentWindow : frame1[0].contentDocument.document ? frame1[0].contentDocument.document : frame1[0].contentDocument;
	                frameDoc.document.open();
	                //Create a new HTML document.
	                frameDoc.document.write('<html>');
	                frameDoc.document.write('<head>');
	                frameDoc.document.write('<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">');
	                frameDoc.document.write('<title>Graficos</title>');
	                frameDoc.document.write('<style type="text/css" media="print">');
	                frameDoc.document.write('	div.page');
               		frameDoc.document.write('	{');
               		frameDoc.document.write('		margin-left:10px;');
               		frameDoc.document.write('		margin-right:10px;');
               		//frameDoc.document.write('		margin-top:10px;');
               		//frameDoc.document.write('		margin-bottom:10px;');
               		frameDoc.document.write('		font-family:Verdana;');
               		frameDoc.document.write('		font-size:11;');
               		frameDoc.document.write('		color:#000;');
               		frameDoc.document.write('		text-decoration:none;');
               		frameDoc.document.write('		text-align: center;');
               		frameDoc.document.write('	}');
              		frameDoc.document.write('</style>');	                
	                frameDoc.document.write('</head><body>');
	                //Append the external CSS file.
	                frameDoc.document.write('<link href="<html:rewrite page="/paginas/comunes/jQuery/grafico/css/print.css" />" rel="stylesheet" type="text/css" />');
	                //Append the DIV contents.
	                var div = '<div class="page">' + getTitulo() + grafico + '</div>';
	                frameDoc.document.write(div);
	                if (!soloGrafico)
	                {
	                	var div = "<p style='page-break-after:always;'></p>";
	                	frameDoc.document.write(div);
	                	div = '<div class="page" style="width:700px;">' + tabla + '</div>';
	                	frameDoc.document.write(div);
	                }
	                frameDoc.document.write('</body></html>');
	                frameDoc.document.close();
	                setTimeout(function () {
	                    window.frames["frame1"].focus();
	                    window.frames["frame1"].print();
	                    frame1.remove();
	                }, 500);
		        });
			}
			
			function getTitulo()
			{
				return "<div style='font-family:Verdana; font-size:20; color:#000; text-decoration:none; text-align: center; width: 100%; height:30 pt;'>" + '<b><bean:message key="jsp.graficoindicador.organizacion" /></b>: <bean:write name="organizacion" scope="session" property="nombre" />' + '<br><br><div/>';
			}
			
			function aplicar()
			{
				if (!validar())
					return;

				document.graficoForm.respuesta.value = "";
				var parametros = 'data=' + getXml(true);
				parametros = parametros + '&funcion=aplicar';
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/iniciativa/grafico/preimprimir"/>?' + parametros, document.graficoForm.respuesta, 'onAplicar()');
			}
			
			function onAplicar()
			{
				var respuesta = document.graficoForm.respuesta.value;
				if (respuesta == "10000")
				{
					var parametros = "objetoId=" + document.graficoForm.objetoId.value;
					if (document.graficoForm.className.value == "Portafolio")
						parametros = parametros + "&portafolioId=" + document.graficoForm.portafolioId.value;
					parametros = parametros + "&objetoNombre=" + CodificarString(document.graficoForm.objetoNombre.value, true);
					parametros = parametros + "&source=" + document.graficoForm.source.value;
					parametros = parametros + "&ano=" + document.graficoForm.ano.value;
					parametros = parametros + "&periodo=" + document.graficoForm.periodo.value;
					parametros = parametros + "&tipoObjeto=" + document.graficoForm.tipoObjeto.value;
					parametros = parametros + "&tipoGrafico=" + document.graficoForm.tipoGrafico.value;
					parametros = parametros + "&organizacionId=" + document.graficoForm.organizacionId.value;
					parametros = parametros + "&tipo=" + document.graficoForm.tipo.value;
					parametros = parametros + "&titulo=" + CodificarString(document.graficoForm.titulo.value, true);
					parametros = parametros + "&tituloEjeY=" + CodificarString(document.graficoForm.tituloEjeY.value, true);
					parametros = parametros + "&tituloEjeX=" + CodificarString(document.graficoForm.tituloEjeX.value, true);
					parametros = parametros + "&nombre=" + CodificarString(document.graficoForm.graficoNombre.value, true);
					if (document.graficoForm.verTituloImprimir.value == "true")
						parametros = parametros + "&verTituloImprimir=" + "1";
					else
						parametros = parametros + "&verTituloImprimir=" + "0";
					if (document.graficoForm.ajustarEscala.value == "true")
						parametros = parametros + "&ajustarEscala=" + "1";
					else
						parametros = parametros + "&ajustarEscala=" + "0";
					parametros = parametros + "&fecha=" + document.graficoForm.fecha.value;
					if (document.graficoForm.className.value != "Portafolio")
						parametros = parametros + "&frecuencia=" + document.graficoForm.frecuencia.value;

					window.location.href='<html:rewrite action="/iniciativa/grafico/crearGrafico"/>?virtual=' + document.graficoForm.virtual.value + "&" + parametros;
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
			
		  	function setPeriodo()
		  	{
		  		var select = document.getElementById("selectPeriodo");
				document.graficoForm.periodo.value = select.value;
			}
		  	
			function eventoCambioFrecuencia(limpiar)
			{
				if (limpiar == undefined)
					limpiar = true;
				
				document.getElementById("trPeriodo").style.display = "";
				
				if (limpiar)
				{
					document.graficoForm.periodo.value = "";
	
					var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
					// Se borra la lista de insumos
					if (tablaListaInsumos != null)
					{
						while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
							tablaListaInsumos.deleteRow(0);
					}
				}
				
				var frecuencia = "3"; // Mensual
				if (document.graficoForm.frecuencia != null)
					frecuencia = document.graficoForm.frecuencia.value;
				
				switch (frecuencia) 
				{
					case "0":
						document.getElementById("trPeriodoDate").style.display = "";
						
						document.getElementById("trPeriodo").style.display = "none";
						document.getElementById("trAno").style.display = "none";
						break;		
					case "1":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						break;		
					case "2":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						break;		
					case "3":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						break;		
					case "4":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						break;		
					case "5":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						break;		
					case "6":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						break;		
					case "7":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						break;		
					case "8":
						document.getElementById("trPeriodoDate").style.display = "none";
						
						document.getElementById("trPeriodo").style.display = "";
						document.getElementById("trAno").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';

						document.getElementById("trPeriodo").style.display = "none";
						break;		
				}
				
				agregarPeriodos(frecuencia, true);
			}
			
			function agregarPeriodos(frecuencia, limpiar)
			{
				if (limpiar == undefined)
					limpiar = false;
				
				var select = document.getElementById("selectPeriodo");
				var max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;

			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if (document.graficoForm.periodo.value != "")
			    	select.selectedIndex = document.graficoForm.periodo.value -1;
			    else
			    	select.selectedIndex = 0;
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

			function init()
			{
				var fecha = new Date (); 
				var anio = fecha.getYear(); 
				if ( anio < 1900 ) 
					anio = 1900 + fecha.getYear();
				
				agregarPeriodos("3");
				onLoad();
				
				var objeto = document.getElementById(_containerGraphic);
				if (objeto != null)
				{
					objeto.style.width = (parseInt(_myWidth) - 28) + "px";
					if (document.graficoForm.tipo.value == 11 || document.graficoForm.tipo.value == 12)
						objeto.style.height = (parseInt(_myHeight) - 295) + "px";
					else
						objeto.style.height = (parseInt(_myHeight) - 195) + "px";
				}
			}

			function addElementToSelect(combo, texto, valor)
			{
			    var idxElemento = combo.options.length; //Numero de elementos de la combo si esta vacio es 0
			    
			    //Este indice será el del nuevo elemento
			    combo.options[idxElemento] = new Option();
			    combo.options[idxElemento].text = texto; //Este es el texto que verás en la combo
			    combo.options[idxElemento].value = valor; //Este es el valor que se enviará cuando hagas un submit del
		   	}
			
			function seleccionarInsumo() 
			{
				var url = '&permitirCambiarOrganizacion=false&permitirCambiarPlan=false&frecuencia=' + document.graficoForm.frecuencia.value + '&organizacionId=' + document.graficoForm.organizacionId.value + '&planId=' + document.graficoForm.objetoId.value;
		
				abrirSelectorIniciativas('graficoForm', 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', 'indicadorInsumoSeleccionadoSeriePlanId', 'agregarInsumos()', null, url);
			}
			
			function agregarInsumos()
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
						} 
						else 
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
				
				if (seleccionadoIds != "")
				{
					for (var i = 0; i < seleccionadoIds.length; i++) 
					{
						partesId = seleccionadoIds[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						nombresIniciativa = seleccionadoNombres[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						planesIds = seleccionadoSeriePlanId[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');

						insumoBuscado = '[iniciativaId:' + partesId[0] + '][planId:' + planesIds[0] + ']';
				
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
											+ '[iniciativaNombre:' + nombresIniciativa[0]  + ']'
											+ '[rutaCompleta:' + seleccionadoRutasCompletas[i] + ']';
								correlativo++;
							}
						}
					}
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
						// iniciativaId
						strTemp = strTemp.substring(indice, strTemp.length);
						indice = strTemp.indexOf("][");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						// iniciativaNombre
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						iniciativaNombre = '[' + strTemp.substring('iniciativaNombre:'.length + 1, indice + 1);
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");
						td1.appendChild(document.createTextNode(correlativo));
						td2.appendChild(document.createTextNode(iniciativaNombre));
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
					} 
					else if ((posicionBuscada + 1) == numeroFilas) 
					{
						var strBuscada = ']' + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						insumosFormula = insumosFormula.substring(0, index - 1);
					} 
					else 
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
					var index = document.graficoForm.insumoSeleccionado.value;
					deleteRowColors(tabla, 
									document.graficoForm.insumoSeleccionado.value,
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
					} else 
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
				};
			}
			
		  	function changeImages() 
		  	{
		  		var graficoColumnaIn = new Image();
		  		graficoColumnaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/ColumnaIn.png'/>";
		  		var graficoColumnaOut = new Image();
		  		graficoColumnaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Columna.png'/>";

		  		var graficoBarraIn = new Image();
		  		graficoBarraIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/BarraIn.png'/>";
		  		var graficoBarraOut = new Image();
		  		graficoBarraOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra.png'/>";
		  		
		  		var graficoTortaIn = new Image();
		  		graficoTortaIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/PieIn.png'/>";
		  		var graficoTortaOut = new Image();
		  		graficoTortaOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie.png'/>";

		  		var graficoTorta3DIn = new Image();
		  		graficoTorta3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3DIn.png'/>";
		  		var graficoTorta3DOut = new Image();
		  		graficoTorta3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3D.png'/>";

		  		var graficoBarra3DIn = new Image();
		  		graficoBarra3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3DIn.png'/>";
		  		var graficoBarra3DOut = new Image();
		  		graficoBarra3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3D.png'/>";
		  		
		  		if (document.images) 
		  		{
		  			for (var i=0; i<changeImages.arguments.length; i+=2) 
		  				document[changeImages.arguments[i]].src = eval(changeImages.arguments[i+1] + ".src");
		  		}
		  	} 		  	
		  	
		  	function setTipo(tipo)
		  	{
		  		<logic:notEqual name="graficoForm" property="bloqueado" value="true">
			  		var imageUrl = 'url("<html:rewrite page='/paginas/strategos/graficos/imagenes/Seleccionado.png'/>")';
			  		
			  		document.getElementById("graficoColumna").style.backgroundImage = "";
			  		document.getElementById("graficoBarra").style.backgroundImage = "";
			  		document.getElementById("graficoTorta").style.backgroundImage = "";
			  		document.getElementById("graficoTorta3D").style.backgroundImage = "";
			  		document.getElementById("graficoBarra3D").style.backgroundImage = "";
			  		document.graficoForm.tipo.value = tipo;
	
			  		var tipografico = document.getElementById("tipoGraficoSeleccionado");
					document.getElementById("trTitleEjez").style.display = "none";
					document.getElementById("trLabelEjez").style.display = "none";
			  		
					switch (tipo) 
					{
						case 2:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />';
							document.getElementById("graficoColumna").style.backgroundImage = imageUrl;
							break;
						case 3:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />';
							document.getElementById("graficoBarra").style.backgroundImage = imageUrl;
							break;
						case 5:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />';
							document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
							break;
						case 7:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.multipletorta" />';
							document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
							break;
						case 10:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />';
							document.getElementById("graficoTorta3D").style.backgroundImage = imageUrl;
							break;
						case 11:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />';
							document.getElementById("graficoBarra3D").style.backgroundImage = imageUrl;
							break;
					}
				</logic:notEqual>
		  	}
		  	
		    function onLoad()
		    {
		    	var respuesta = document.graficoForm.respuesta.value.split("|");
				if (respuesta.length > 0)
				{
					var campos;
					for (var i = 0; i < respuesta.length; i++)
					{
						campos = respuesta[i].split('!,!');
						if (eval('document.graficoForm.' + campos[0]) != null)
							eval('document.graficoForm.' + campos[0] + '.value=\"' + campos[1] + '\";');
					}
				}
				
				eventoCambioFrecuencia(false);

				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
				// Se borra la lista de insumos
				if (tablaListaInsumos != null)
				{
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);
					// Se agregan los insumos de los indicadores
					agregarInsumos();
				}
				
				// Se selecciona el grafico
				setTipo(parseInt(document.graficoForm.tipo.value), false);
				document.graficoForm.respuesta.value = "";
		    }
		    
		    function validarTipo(objeto)
		    {
		    	if (objeto.value != -1)
		    		setTipo(8);
		    }
		    
			function previo() 
			{        
				var grafico = new Grafico();
				grafico.url = '<html:rewrite action="/graficos/grafico"/>';
				grafico.ShowForm(true, '<bean:write name="graficoForm" property="previaCeldaId" />', document.graficoForm.source.value, undefined, undefined, undefined, '<bean:write name="graficoForm" property="vistaId" />', '<bean:write name="graficoForm" property="paginaId" />');
	        }
	
	        function siguiente() 
	        {        
				var grafico = new Grafico();
				grafico.url = '<html:rewrite action="/graficos/grafico"/>';
				grafico.ShowForm(true, '<bean:write name="graficoForm" property="previaCeldaId" />', document.graficoForm.source.value, undefined, undefined, undefined, '<bean:write name="graficoForm" property="vistaId" />', '<bean:write name="graficoForm" property="paginaId" />');
	        }		
		    
			function configuracion()
			{
				window.location.href = "#modal1";
			}

			function showTable()
			{
				window.location.href = "#modal2";
			}
			
			function closeModal(hasValidate)
			{
				if (hasValidate && !validar())
					return;
				
				if (calendario != null)
					calendario.close();
				window.location.href = "#close";
				
				if (hasValidate)
					aplicar();
			}
			
			function mostrarColores(objeto, txtRGBStr, txtHexStr)
			{
				var txtRGB = document.getElementById(txtRGBStr);
				var txtHex = document.getElementById(txtHexStr);
				paleta(objeto, txtRGB, txtHex);
			}
	        
			function preparedGraph()
			{
				_name = document.graficoForm.titulo.value;
				_subtitle = document.graficoForm.tituloEjeY.value; 
				if (document.graficoForm.tipo.value == 1)
					_typeGraphic = 'line';
				else if (document.graficoForm.tipo.value == 2 || document.graficoForm.tipo.value == 9 || document.graficoForm.tipo.value == 11 || document.graficoForm.tipo.value == 12)
					_typeGraphic = 'column';
				else if (document.graficoForm.tipo.value == 3)
					_typeGraphic = 'bar';
				else if (document.graficoForm.tipo.value == 4)
					_typeGraphic = 'area';
				else if (document.graficoForm.tipo.value == 5 || document.graficoForm.tipo.value == 10)
					_typeGraphic = 'pie';
				else if (document.graficoForm.tipo.value == 14)
					_typeGraphic = 'gauge';
				else if (document.graficoForm.tipo.value == 8 || document.graficoForm.tipo.value == 6)
					_typeGraphic = null;
				else
					_typeGraphic = 'line';
					
				_ejeYTitle = document.graficoForm.tituloEjeY.value;
				_ejeX = document.graficoForm.ejeX.value.split(",");
				_serie = [];
				_ajustarEscala = (document.graficoForm.ajustarEscala != null ? (document.graficoForm.ajustarEscala.checked ? true : false) : false);
				var series = document.graficoForm.serieName.value.split(_separadorSeries);
				var color = document.graficoForm.serieColor.value.split(_separadorSeries);
				var datas = document.graficoForm.serieData.value.split(_separadorSeries);
				if (document.graficoForm.tipo.value == 5 || document.graficoForm.tipo.value == 10)
				{
					if (document.graficoForm.tipo.value == 5)
					{
						_serie.push({
				            name: _ejeX,
				            colorByPoint: true,
				            data: []
				        });
					}
					else if (document.graficoForm.tipo.value == 10)
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
				
				getGrafico(document.graficoForm.tipo.value);
			}
			
			function save()
			{
				if (!validar())
					return;

				document.graficoForm.respuesta.value = "";
				var codificar = true;
				var data = getXml(codificar);
				
				var parametros = 'data=' + data;
				parametros = parametros + '&id=' + document.graficoForm.id.value;
				parametros = parametros + '&nombre=' + CodificarString(document.graficoForm.titulo.value, codificar); 
				parametros = parametros + '&source=' + document.graficoForm.source.value;
				parametros = parametros + '&objetoId=' + document.graficoForm.objetoId.value;
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/guardarAsistenteGrafico" />?' + parametros, document.graficoForm.respuesta, 'onSalvar()');
			}

			function onSalvar()
			{
				var respuesta = document.graficoForm.respuesta.value.split("|");
				if (respuesta.length > 0 && respuesta[1] == 0)
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.save.exito" /> ');
				else if (respuesta.length > 0 && respuesta[1] == 3)
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.nombre.duplicado" /> ');
			}
	        
		</script>

		<%-- Tooltip --%>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/screen.css" />" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/jquery-ui.css" />" />
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.dimensions.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.ui.widget.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.ui.tabs.js'/>"></script>

		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloBackground" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				if (estilos.getBackground() != null)
					estiloBackground = estilos.getBackground();
			}
			else
				hayEstilo = "false";
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloBackground" value="<%=estiloBackground%>"></bean:define>
		</logic:equal>
		<script type="text/javascript">
			var background = "<%=estiloBackground%>".toString().split(":");
		</script>
		<script type="text/javascript">
			$(function() 
			{
				$( "#tabs" ).tabs();
			});
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativa/grafico/crearGrafico" styleClass="formaHtmlCompleta">

			<%-- Campos hidden para el manejo de la insumos --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoSerie">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">
			<input type="hidden" name="indicadorInsumoSeleccionadoSeriePlanId">
			<input type="hidden" name="insumoSeleccionado" id="insumoSeleccionado">

			<html:hidden property="tipo" />
			<html:hidden property="id" />
			<html:hidden property="respuesta" />
			<html:hidden property="periodo" />
			<html:hidden property="xmlAnterior" />
			<html:hidden property="virtual" />
			<html:hidden property="frecuenciaAgrupada" />
			<html:hidden property="mostrarCondicion" />
			<html:hidden property="source" />
			<html:hidden property="objetoId" />
			<html:hidden property="objetosIds" />
			<html:hidden property="className" />
			<html:hidden property="insumosFormula" />
			<html:hidden property="paginaId" />
			<html:hidden property="vistaId" />
			<html:hidden property="previaCeldaId" />
			<html:hidden property="siguienteCeldaId" />
			<html:hidden property="claseId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="tipoGrafico" />
			<html:hidden property="objetoNombre" />
			<html:hidden property="tipoObjeto" />
			<html:hidden property="ejeX" />
			<html:hidden property="serieName" />
			<html:hidden property="serieData" />
			<html:hidden property="serieColor" />
			
			<html:hidden property="anoInicial" />
			<html:hidden property="periodoInicial" />
			<html:hidden property="anoFinal" />
			<html:hidden property="periodoFinal" />
			
			
			<html:hidden property="portafolioId" />

			<vgcinterfaz:contenedorForma width="100%" height="100%"
				mostrarBarraSuperior="true">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
				..::<vgcutil:message key="jsp.graficoindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100Collapse">
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.graficoindicador.organizacion" /></b></td>
							<td><bean:write name="organizacion" scope="session" property="nombre" />&nbsp;</td>
						</tr>
						<logic:notEmpty name="graficoForm" property="paginaId">
							<%-- Vista --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.vista" /></b></td>
								<td><bean:write name="graficoForm" property="vista.nombre" />&nbsp;</td>
							</tr>

							<%-- Página --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.pagina.nro" /></b></td>
								<td><bean:write name="graficoForm" property="pagina.numero" />&nbsp;&nbsp;|&nbsp;&nbsp;<bean:write name="graficoForm" property="pagina.descripcion" />&nbsp;</td>
							</tr>

							<%-- Celda --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.celda" /></b></td>
								<td><bean:write name="graficoForm" property="celda.indice" />&nbsp;&nbsp;|&nbsp;&nbsp;<bean:write name="graficoForm" property="graficoNombre" />&nbsp;</td>
							</tr>
						</logic:notEmpty>
						<logic:notEmpty property="className" name="graficoForm">
							<logic:equal name="graficoForm" property="className" value="Portafolio">
								<tr class="barraFiltrosForma">
									<td width="20px"><b><vgcutil:message key="jsp.vista.portafolio" />: </b></td>
									<td><bean:write name="graficoForm" property="portafolio.nombre" />&nbsp;</td>
								</tr>
							</logic:equal>
						</logic:notEmpty>
					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraGraficoIndicador">
						<logic:notEmpty name="graficoForm" property="paginaId">
							<vgcinterfaz:barraHerramientasBoton
								nombreImagen="flechaIzquierda"
								pathImagenes="/componentes/barraHerramientas/" nombre="previo"
								onclick="javascript:previo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.previo.alt" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="flechaDerecha"
								pathImagenes="/componentes/barraHerramientas/"
								nombre="siguiente" onclick="javascript:siguiente();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.siguiente.alt" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEmpty>
						<logic:notEqual name="graficoForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar"
								pathImagenes="/componentes/barraHerramientas/" nombre="refrescar"
								onclick="javascript:configuracion();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.ver.configuracion.grafico" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar"
							pathImagenes="/componentes/barraHerramientas/" nombre="guardar"
							onclick="javascript:save();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.guardar.configuracion" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir"
							pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
							onclick="javascript:imprimir();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.imprimir.configuracion" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="tabla" pathImagenes="/componentes/barraHerramientas/" nombre="tabla" onclick="javascript:showTable();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.ver.tabla" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<div id="modal1" class="modalmask">
					<div class="modalbox movedown" style="width:700px;padding: 0px;">
						<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px;border-collapse: collapse;">
							<tr height="30px">
								<td colspan="2">
									<a href="javascript:closeModal(true);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a>
								</td>
							</tr>
							<tr>
								<td class="tabtd">
									<div class="demo">
										<div id="tabs">
											<ul>
												<li><a href="#tabs-1"><vgcutil:message key="jsp.grafico.editor.tab.insumo" /></a></li>
												<li><a href="#tabs-2"><vgcutil:message key="jsp.grafico.editor.tab.periodo" /></a></li>
												<li><a href="#tabs-3"><vgcutil:message key="jsp.grafico.editor.tab.titulo" /></a></li>
												<li><a href="#tabs-4"><vgcutil:message key="jsp.grafico.editor.tab.tipo" /></a></li>
												<li><a href="#tabs-5"><vgcutil:message key="jsp.editar.grafico.portafolio.tab.serie" /></a></li>
												<li><a href="#tabs-6"><vgcutil:message key="jsp.grafico.editor.tab.otros" /></a></li>
											</ul>
											<div id="tabs-1">
												<table class="tabTablaPrincipal">
													<logic:notEmpty property="className" name="graficoForm">
														<logic:notEqual name="graficoForm" property="className" value="Portafolio">
															<tr>
																<td align="left">
																	<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
																</td>
																<td>
																	<logic:empty property="className" name="graficoForm">
																		<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			styleClass="cuadroTexto" size="1"
																			onchange="eventoCambioFrecuencia()">
																			<html:optionsCollection property="frecuencias"
																				value="frecuenciaId" label="nombre" />
																		</html:select>
																	</logic:empty>
																	<logic:notEmpty property="className" name="graficoForm">
																		<logic:equal name="graficoForm" property="className" value="Celda">
																			<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																				styleClass="cuadroTexto" size="1"
																				onchange="eventoCambioFrecuencia()">
																				<html:optionsCollection property="frecuencias"
																					value="frecuenciaId" label="nombre" />
																			</html:select>
																		</logic:equal>
																		<logic:notEqual name="graficoForm" property="className" value="Celda">
																			<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																				styleClass="cuadroTexto" size="1"
																				onchange="eventoCambioFrecuencia()">
																				<html:optionsCollection property="frecuencias"
																					value="frecuenciaId" label="nombre" />
																			</html:select>
																		</logic:notEqual>
																	</logic:notEmpty>
																</td>
															</tr>
														</logic:notEqual>
													</logic:notEmpty>
													<logic:empty property="className" name="graficoForm">
														<tr>
															<td align="left">
																<vgcutil:message key="jsp.grafico.editor.tab.frecuencia" />
															</td>
															<td>
																<logic:empty property="className" name="graficoForm">
																	<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																		styleClass="cuadroTexto" size="1"
																		onchange="eventoCambioFrecuencia()">
																		<html:optionsCollection property="frecuencias"
																			value="frecuenciaId" label="nombre" />
																	</html:select>
																</logic:empty>
																<logic:notEmpty property="className" name="graficoForm">
																	<logic:equal name="graficoForm" property="className" value="Celda">
																		<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			styleClass="cuadroTexto" size="1"
																			onchange="eventoCambioFrecuencia()">
																			<html:optionsCollection property="frecuencias"
																				value="frecuenciaId" label="nombre" />
																		</html:select>
																	</logic:equal>
																	<logic:notEqual name="graficoForm" property="className" value="Celda">
																		<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			styleClass="cuadroTexto" size="1"
																			onchange="eventoCambioFrecuencia()">
																			<html:optionsCollection property="frecuencias"
																				value="frecuenciaId" label="nombre" />
																		</html:select>
																	</logic:notEqual>
																</logic:notEmpty>
															</td>
														</tr>
													</logic:empty>
													<tr>
														<td>
															<br>
														</td>
													</tr>
													<tr>
														<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.insumo.titulo" /></td>
													</tr>
													<tr align="center">
														<logic:empty property="className" name="graficoForm">
															<logic:notEqual name="graficoForm" property="bloqueado" value="true">
																<td>
																	<input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																	onclick="seleccionarInsumo();">
																</td>
																<td><input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																	onclick="removerInsumo();">
																</td>
															</logic:notEqual>
															<logic:equal name="graficoForm" property="bloqueado" value="true">
																<td>
																	<input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																	onclick="seleccionarInsumo();" disabled>
																</td>
																<td><input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																	onclick="removerInsumo();" disabled>
																</td>
															</logic:equal>
														</logic:empty>
														<logic:notEmpty property="className" name="graficoForm">
															<logic:equal name="graficoForm" property="className" value="Celda">
																<logic:notEqual name="graficoForm" property="bloqueado" value="true">
																	<td>
																		<input type="button" style="width: 150px"
																		class="cuadroTexto"
																		value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																		onclick="seleccionarInsumo();">
																	</td>
																	<td>
																		<input type="button" style="width: 150px"
																		class="cuadroTexto"
																		value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																		onclick="removerInsumo();">
																	</td>
																</logic:notEqual>
																<logic:equal name="graficoForm" property="bloqueado" value="true">
																	<td>
																		<input type="button" style="width: 150px"
																		class="cuadroTexto"
																		value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																		onclick="seleccionarInsumo();" disabled>
																	</td>
																	<td>
																		<input type="button" style="width: 150px"
																		class="cuadroTexto"
																		value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																		onclick="removerInsumo();" disabled>
																	</td>
																</logic:equal>
															</logic:equal>
															<logic:equal name="graficoForm" property="className" value="Portafolio">
																<td>
																	<input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																	onclick="seleccionarInsumo();" disabled>
																</td>
																<td>
																	<input type="button" style="width: 150px"
																	class="cuadroTexto"
																	value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																	onclick="removerInsumo();" disabled>
																</td>
															</logic:equal>
															<logic:notEqual name="graficoForm" property="className" value="Celda">
																<logic:notEqual name="graficoForm" property="className" value="Portafolio">
																	<logic:empty property="objetosIds" name="graficoForm">
																		<td>
																			<input type="button" style="width: 150px"
																			class="cuadroTexto"
																			value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																			onclick="seleccionarInsumo();"
																			disabled>
																		</td>
																		<td>
																			<input type="button" style="width: 150px"
																			class="cuadroTexto"
																			value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																			onclick="removerInsumo();"
																			disabled>
																		</td>
																	</logic:empty>
																	<logic:notEmpty property="objetosIds" name="graficoForm">
																		<td>
																			<input type="button" style="width: 150px"
																			class="cuadroTexto"
																			value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />"
																			onclick="seleccionarInsumo();">
																		</td>
																		<td>
																			<input type="button" style="width: 150px"
																			class="cuadroTexto"
																			value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />"
																			onclick="removerInsumo();">
																		</td>
																	</logic:notEmpty>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEmpty>
													</tr>
													<tr height="128px" valign="top">
														<td colspan="2">
															<table class="tabtablainsumo">
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
											</div>
											<div id="tabs-2">
												<table class="tabTablaPrincipal">
													<tr>
														<td colspan="2"><vgcutil:message
																key="jsp.asistente.grafico.rango" /></td>
													</tr>
													<tr>
														<td colspan="2">
															<vgcutil:message key="jsp.asistente.grafico.inicial" />
														</td>
													</tr>
													<tr>
														<bean:define id="maximoPeriodo" name="graficoForm"
															property="numeroMaximoPeriodos" type="java.lang.Integer"
															toScope="page" />
														<td colspan="2">
															<table class="tabla contenedorBotonesSeleccion">
																<tr id="trAno">
																	<td><vgcutil:message
																			key="jsp.asistente.grafico.ano" /></td>
																	<td><bean:define id="anoCalculo"
																			toScope="page">
																			<bean:write name="graficoForm" property="ano" />
																		</bean:define> 
																		<html:select property="ano" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			value="<%=anoCalculo%>"
																			styleClass="cuadroTexto">
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
																<tr id="trPeriodo">
																	<td><span id="tdPeriodo"></span></td>
																	<td>
																		<logic:notEqual name="graficoForm" property="bloqueado" value="true">
																			<select id="selectPeriodo"
																				class="cuadroTexto" onchange="setPeriodo()">
																			</select>
																		</logic:notEqual>
																		<logic:equal name="graficoForm" property="bloqueado" value="true">
																			<select id="selectPeriodo" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																				class="cuadroTexto">
																			</select>
																		</logic:equal>
																	</td>
																</tr>
																<tr id="trPeriodoDate">
																	<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
																	<td>
																		<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fecha" size="10" maxlength="10" styleClass="cuadroTexto" />
																		<logic:notEqual name="graficoForm" property="bloqueado" value="true">
																			<img style="cursor: pointer" onclick="seleccionarFecha();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
																		</logic:notEqual>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<br>
														</td>
													</tr>
												</table>
											</div>
											<div id="tabs-3">
												<table class="tabTablaPrincipal">
													<tr>
														<td colspan="2"><vgcutil:message
																key="jsp.asistente.grafico.titulosgraficos" /><br>
															<br></td>
													</tr>
													<tr>
														<td><vgcutil:message
																key="jsp.asistente.grafico.titulosgraficos.titulo" />
														</td>
													</tr>
													<tr>
														<td>
															<table class="tabTablaPrincipal">
																<tr>
																	<td>
																		<html:text property="titulo" size="57" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td><vgcutil:message
																key="jsp.asistente.grafico.titulosgraficos.ejey" /></td>
													</tr>
													<tr>
														<td><html:text property="tituloEjeY" size="57"
																disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
																styleClass="cuadroTexto" /></td>
													</tr>
													<tr>
														<td><vgcutil:message
																key="jsp.asistente.grafico.titulosgraficos.ejex" /></td>
													</tr>
													<tr>
														<td><html:text property="tituloEjeX" size="57"
																disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
																styleClass="cuadroTexto" /></td>
													</tr>
													<tr id="trTitleEjez" style="display:none">
														<td><vgcutil:message
																key="jsp.asistente.grafico.titulosgraficos.ejez" /></td>
													</tr>
													<tr id="trLabelEjez" style="display:none">
														<td><html:text property="tituloEjeZ" size="57"
																disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50"
																styleClass="cuadroTexto" /></td>
													</tr>
												</table>
											</div>
											<div id="tabs-4">
												<table class="tabTablaPrincipal">
													<tr>
														<td>
															<table class="tabla">
																<tr>
																	<td>
																		<table class="tabtablaImagenes">
																			<tr>
																				<td bgcolor="#FFFFFF">
																					<img id="graficoColumna" name="graficoColumna"
																						src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Columna.png'/>"
																						border="0" width="72px" height="72px"
																						alt="<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />"
																						onclick="setTipo(2, true)"
																						onmouseover="changeImages('graficoColumna', 'graficoColumnaIn')"
																						onmouseout="changeImages('graficoColumna', 'graficoColumnaOut')"
																						style="cursor:pointer">
																				</td>
																				<td bgcolor="#FFFFFF">
																					<img id="graficoBarra" name="graficoBarra"
																						src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra.png'/>"
																						border="0" width="72px" height="72px"
																						alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />"
																						onclick="setTipo(3, true)"
																						onmouseover="changeImages('graficoBarra', 'graficoBarraIn')"
																						onmouseout="changeImages('graficoBarra', 'graficoBarraOut')"
																						style="cursor:pointer">
																				</td>
																				<td bgcolor="#FFFFFF">
																					<img id="graficoTorta" name="graficoTorta"
																						src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie.png'/>"
																						border="0" width="72px" height="72px"
																						alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />"
																						style="cursor:pointer"
																						onclick="setTipo(5, true)"
																						onmouseover="changeImages('graficoTorta', 'graficoTortaIn')"
																						onmouseout="changeImages('graficoTorta', 'graficoTortaOut')">
																				</td>
																				<td bgcolor="#FFFFFF">
																					<img id="graficoTorta3D" name="graficoTorta3D"
																						src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Pie3D.png'/>"
																						border="0" width="72px" height="72px"
																						alt="<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />"
																						style="cursor:pointer"
																						onclick="setTipo(10, true)"
																						onmouseover="changeImages('graficoTorta3D', 'graficoTorta3DIn')"
																						onmouseout="changeImages('graficoTorta3D', 'graficoTorta3DOut')">
																				</td>
																				<td bgcolor="#FFFFFF">
																					<img id="graficoBarra3D" name="graficoBarra3D"
																						src="<html:rewrite page='/paginas/strategos/graficos/imagenes/Barra3D.png'/>"
																						border="0" width="72px" height="72px"
																						alt="<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />"
																						onclick="setTipo(11, true)"
																						onmouseover="changeImages('graficoBarra3D', 'graficoBarra3DIn')"
																						onmouseout="changeImages('graficoBarra3D', 'graficoBarra3DOut')"
																						style="cursor:pointer">
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
															<vgcutil:message key="jsp.asistente.grafico.tipografico.tipo.seleccionado" /> &nbsp;<span id="tipoGraficoSeleccionado"></span>
														</td>
													</tr>
												</table>
											</div>
											<div id="tabs-5">
												<table class="tabTablaPrincipal">
													<tr>
														<td height="10px" style="padding-top: 10px;">
															<table class="cuadroTextoTitle">
																<tr valign="top">
																	<td align="left" style="padding-left: 10px; width:250px"><vgcutil:message key="jsp.grafico.editor.serie" /></td>
																	<td align="left" style="padding-left: 30px; width:40px"><vgcutil:message key="jsp.grafico.editor.color" /></td>
																</tr>
																<tr valign="top">
																	<td colspan="2">
																		<table id="tablaListaSeries" class="tabtablainsumoSerie" style="width:150px">
																			<tbody class="cuadroTexto">
																				<logic:iterate name="graficoForm" property="series" id="serie">
																					<logic:notEmpty property="planId" name="serie">
																						<tr id="row_<bean:write name='serie' property='id' />">
																							<logic:notEmpty property="id" name="serie">
																								<td align="left">
																									<input size="47" maxlength="100"
																									type="text"
																									id="serie_<bean:write name='serie' property='id' />"
																									name="serie_<bean:write name='serie' property='id' />"
																									value="<bean:write name='serie' property='nombreLeyenda' />">
																								</td>
																								<td align="left" style="width:40px">
																									<input type="text"
																									onclick="javascript:mostrarColores(this,'color_decimal_hidden_<bean:write name='serie' property='id' />', 'color_hidden_<bean:write name='serie' property='id' />');"
																									name="color_<bean:write name='serie' property='id' />"
																									size="3" readonly
																									style="background-color:<bean:write name='serie' property='color' />">
																									<input type="hidden"
																									value="<bean:write name='serie' property='color' />"
																									id="color_hidden_<bean:write name='serie' property='id' />"
																									name="color_hidden_<bean:write name='serie' property='id' />">
																									<input type="hidden"
																									value="<bean:write name='serie' property='colorDecimal' />"
																									id="color_decimal_hidden_<bean:write name='serie' property='id' />"
																									name="color_decimal_hidden_<bean:write name='serie' property='id' />">
																								</td>
																							</logic:notEmpty>
																						</tr>
																					</logic:notEmpty>
																				</logic:iterate>
																			</tbody>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td colspan="2">
																		<!--   inicio Paleta popUp  -->
																		<div id="The_colorPicker" style="width: 250px; position: absolute; z-index: 1; display: none;">
																			<div style="width: 140px; height: 220px; padding-right: 5px; padding-bottom: 5px; padding-left: 5px; position: relative; display: inline;">
																				<div style="left: 80px; width: 16px; height: 16px; position: relative; display: inline;">
																					<img style="float: right; display:block; height:16px; width:16px; cursor: pointer;" src="<html:rewrite page='/paginas/comunes/jQuery/paletaColor/css/cancel.png'/>" onclick="javascript:hideColorPicker();">
																				</div>
																				<div id="paletaColor"></div>
																			</div>
																		</div>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</div>
											<div id="tabs-6">
												<table class="tabTablaPrincipal">
													<tr>
														<td colspan="2"><vgcutil:message
																key="jsp.asistente.grafico.nombre" /></td>
													</tr>
													<tr>
														<td>
															<html:text property="graficoNombre" size="50"
																 maxlength="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																styleClass="cuadroTexto" />
														</td>
													</tr>
													<tr>
														<td>
															<html:checkbox property="verTituloImprimir" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="botonSeleccionMultiple">
																<vgcutil:message key="jsp.grafico.editor.vertituloimprimir" />
															</html:checkbox>
														</td>
													</tr>
													<logic:notEqual name="graficoForm" property="tipo" value="8">
														<tr>
															<td>
																<html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="ajustarEscala" styleClass="botonSeleccionMultiple">
																	<vgcutil:message key="jsp.grafico.editor.ajustarescala" />
																</html:checkbox>
															</td>
														</tr>
													</logic:notEqual>
												</table>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>

				<div id="modal2" class="modalmask">
					<div id="tblDatos" class="modalbox movedown" style="width:800px; height:600px; padding: 0px; overflow: auto">
						<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px; border-collapse: collapse;">
							<tr>
								<td colspan="2">
									<a href="javascript:closeModal(false);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a>
								</td>
							</tr>
							<tr align="center">
								<td>
									<table id="background-imagekerwin" style="background-color: #ffffff;">
										<colgroup>
											<col class="oce-firstKerwin" />
										</colgroup>
										<thead>
											<tr>
												<th scope="col" style="color: #666666; font-weight: bold;"><vgcutil:message key="jsp.asistente.grafico.tabla.iniciativa.estatus" /></th>
												<th scope="col" style="color: #666666; font-weight: bold;"><vgcutil:message key="jsp.asistente.grafico.tabla.iniciativa.nombre" /></th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<td colspan="100" class="rounded-foot-right">&nbsp;</td>
											</tr>
										</tfoot>
										<tbody class="tbody">
											<logic:iterate name="graficoForm" property="valores" id="valor">
												<tr>
													<td class="columna" width="50%">
														<bean:write name='valor' property='serieNombre' />
													</td>
													<td class="estatusPrincipal">
														<table>
															<logic:iterate name="valor" property="objetos" id="objeto">
																<tr><td class="estatus">.- <bean:write name="objeto" property="clave" /></td></tr>
															</logic:iterate>
														</table>
													</td>
												</tr>
											</logic:iterate>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>

				<%-- Cuerpo --%>
				<table class="tablaSpacing0Padding0Width100">
					<tr align="center">
						<%-- Se obtienen las variables de Form Bean --%>
						<td>
							<table class="bordeFichaDatos">
								<tr align="center">
									<td align="center">
										<div id="div_grafico" style="height: 300px; width : 370px; margin: 0 auto"></div>
										<logic:equal name="graficoForm" property="tipo" value="11">
											<div id="sliders">
												<table>
													<tr><td><vgcutil:message key="jsp.grafico.editor.angulo.alpha" /></td><td><input id="R0" type="range" min="0" max="45" value="15"/> <span id="R0-value" class="value"></span></td></tr>
												    <tr><td><vgcutil:message key="jsp.grafico.editor.angulo.beta" /></td><td><input id="R1" type="range" min="0" max="45" value="15"/> <span id="R1-value" class="value"></span></td></tr>
												</table>
											</div>
										</logic:equal>
										<logic:equal name="graficoForm" property="tipo" value="12">
											<div id="sliders">
												<table>
													<tr><td><vgcutil:message key="jsp.grafico.editor.angulo.alpha" /></td><td><input id="R0" type="range" min="0" max="45" value="15"/> <span id="R0-value" class="value"></span></td></tr>
												    <tr><td><vgcutil:message key="jsp.grafico.editor.angulo.beta" /></td><td><input id="R1" type="range" min="0" max="45" value="15"/> <span id="R1-value" class="value"></span></td></tr>
												</table>
											</div>
										</logic:equal>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
			init();
			preparedGraph();
		</script>
		<script type="text/javascript">
			var buttons = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
			buttons.push({
		    	separator: !0
			},
			{
		    	text: "Salvar Grafico como PNG",
		    	onclick: function() {
		            var chart = this;
		            var render_width = 1000;
		            var render_height = render_width * chart.chartHeight / chart.chartWidth
		
		            var svg = chart.getSVG({
		                exporting: 
		                {
		                    sourceWidth: chart.chartWidth,
		                    sourceHeight: chart.chartHeight
		                }
		            });
		
		            var canvas = document.createElement('canvas');
		            canvas.height = render_height;
		            canvas.width = render_width;
		
		            canvg(canvas, svg, {
		                scaleWidth: render_width,
		                scaleHeight: render_height,
		                ignoreDimensions: true
		            });
		
		            download(canvas, 'test.png', 'image/png');                    	
		    	}
			},
			{
		    	text: "Salvar Grafico como JPEG",
		    	onclick: function() {
		            var chart = this;
		            var render_width = 1000;
		            var render_height = render_width * chart.chartHeight / chart.chartWidth
		
		            var svg = chart.getSVG({
		                exporting: 
		                {
		                    sourceWidth: chart.chartWidth,
		                    sourceHeight: chart.chartHeight
		                }
		            });
		
		            var canvas = document.createElement('canvas');
		            canvas.height = render_height;
		            canvas.width = render_width;
		
		            canvg(canvas, svg, {
		                scaleWidth: render_width,
		                scaleHeight: render_height,
		                ignoreDimensions: true
		            });
		
		            download(canvas, 'test.jpg', 'image/jpeg');                    	
		        }
			},
			{
		    	text: "Salvar Grafico como PDF",
		    	onclick: function() {
		    		this.exportChart({
		    			type: 'application/pdf', 
		    			filename: 'grafico'
		    		});                    	
		        }
			},
			{
		    	text: "Salvar Grafico como SVG",
		    	onclick: function() {
		    		this.exportChart({
						type: 'image/svg+xml',
						filename: 'grafico'
					});                   	
		        }
			});
		</script>
	</tiles:put>
</tiles:insert>
