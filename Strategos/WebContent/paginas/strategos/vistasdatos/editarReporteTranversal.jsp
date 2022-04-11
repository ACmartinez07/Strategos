<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.reporteindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/validateInput.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/componentes/paleta/paleta.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/componentes/paleta/colorwheel.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page="/componentes/comunes/jsComunes.jsp"/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>

		<%-- Tabla --%>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/style.css" />" />

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="reporteForm" property="bloqueado">
				<bean:write name="reporteForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="reporteForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var pasoActual = 1;
			var desdeMes = 1;
			var hastaMes = 12;
			var desdeAno = '<bean:write name="reporteForm" property="anoInicial" ignore="true"/>';
			var hastaAno = '<bean:write name="reporteForm" property="anoFinal" ignore="true"/>';
			var errMsRango = '<vgcutil:message key="jsp.reporteindicador.editor.alerta.invalido" />';
			var _selectedId;
            var _filas;
            var _columnas;
            var _setCloseParent = false;
			
			// Inicializar botones de los cuadros Numéricos  
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');

			function save()
			{
				if (!validar())
					return;
				document.reporteForm.respuesta.value = "";
				var data = getXml(true);
				document.reporteForm.configuracion.value = data;
				var parametros = '&id=' + document.reporteForm.id.value + '&data=' + data;

				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/vistasdatos/editarReporteTranversal"/>?funcion=salvar' + parametros, document.reporteForm.respuesta, 'onSalvar()');
			}

			function onSalvar()
			{
				var respuesta = document.reporteForm.respuesta.value;
				if (respuesta == "10000")
				{
					alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.save.exito" /> ');
					cancelar();
				}
				else if (respuesta == "10003")
					alert('<vgcutil:message key="action.guardarregistro.duplicado" /> ');
			}
			
			function validar() 
			{
				if (window.document.reporteForm.reporteNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.nombre.invalido.vacio" /> ');
					return false;
				}

				if (window.document.reporteForm.filas.value == "")
				{
					alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.filas.invalido.vacio" /> ');
					return false;
				}

				if (window.document.reporteForm.columnas.value == "")
				{
					alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.columnas.invalido.vacio" /> ');
					return false;
				}
				
			 	if (document.reporteForm.frecuencia.value == 0)
		 		{
			 		document.reporteForm.fechaHasta.value = document.reporteForm.fechaDesde.value;
			 		if (!fechaValida(document.reporteForm.fechaDesde))
		 			{
			 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechadesde.invalido" /> ');
			 			return;
		 			}

			 		if (!fechaValida(document.reporteForm.fechaHasta))
		 			{
			 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechahasta.invalido" /> ');
			 			return;
		 			}
		 		}
				
			 	document.reporteForm.anoFinal.value = document.reporteForm.anoInicial.value;
			 	document.reporteForm.periodoFinal.value = document.reporteForm.periodoInicial.value;
			 	
			 	if (document.reporteForm.frecuencia.value == 0)
				{
					var fecha1 = document.reporteForm.fechaDesde.value.split("/");
					var fecha2 = document.reporteForm.fechaHasta.value.split("/");

					document.reporteForm.anoInicial.value = parseInt(fecha1[2]);
					document.reporteForm.anoFinal.value = parseInt(fecha2[2]);

					document.reporteForm.periodoInicial.value = getPeriodoDeFechaDiaria(document.reporteForm.fechaDesde.value);
					document.reporteForm.periodoFinal.value = getPeriodoDeFechaDiaria(document.reporteForm.fechaHasta.value);
				}

			 	desde = parseInt(document.reporteForm.anoInicial.value + "" + (document.reporteForm.periodoInicial.value.length == 1 ? "00" : (document.reporteForm.periodoInicial.value.length == 2 ? "0" : "")) + document.reporteForm.periodoInicial.value);
				hasta = parseInt(document.reporteForm.anoFinal.value + "" + (document.reporteForm.periodoFinal.value.length == 1 ? "00" : (document.reporteForm.periodoFinal.value.length == 2 ? "0" : "")) + document.reporteForm.periodoFinal.value);
				if (hasta<desde) 
				{
					if (document.reporteForm.frecuencia.value == 0)
						alert('<vgcutil:message key="jsp.reporteindicador.editor.alerta.rango.fechas.invalido" /> ');
					else
						alert(errMsRango);
					return false;
				} 
				
				return true;
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function imprimir()
			{
			}
			
			function onImprimir()
			{
			}
			
			function aplicar()
			{
				if (!validar())
					return;
				
				var data = getXml(true);
				var parametros = '&data=' + data;
				
				//window.location.href='<html:rewrite action="/vistasdatos/editarReporteTranversal"/>?funcion=diseno' + parametros;
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/vistasdatos/editarReporteTranversal"/>?funcion=preDiseno' + parametros, document.reporteForm.respuesta, 'onAplicar()');
			}

			function onAplicar()
			{
				var parametros = '&id=' + document.reporteForm.id.value;

				window.location.href='<html:rewrite action="/vistasdatos/editarReporteTranversal"/>?funcion=diseno' + parametros;
			}
			
			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.reporteForm.fechaDesde' , document.reporteForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.reporteForm.fechaHasta' , document.reporteForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

			function cancelar() 
			{
				window.document.reporteForm.action = '<html:rewrite action="/vistasdatos/editarReporteTranversal"/>?cancelar=true';
				window.document.reporteForm.submit();			
			}
			
			function eventoCambioFrecuencia(limpiar)
			{
				if (limpiar == undefined)
					limpiar = true;

				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				if (limpiar)
				{
					document.reporteForm.periodoInicial.value = "";
					document.reporteForm.periodoFinal.value = "";
		  		}
	
				switch (document.reporteForm.frecuencia.value) 
				{
					case "0":
						document.getElementById("trPeriodoInicialDate").style.display = "";
						document.getElementById("trPeriodoFinalDate").style.display = "";
						
						document.getElementById("trPeriodoInicial").style.display = "none";
						document.getElementById("trPeriodoFinal").style.display = "none";
						document.getElementById("trAnoInicial").style.display = "none";
						document.getElementById("trAnoFinal").style.display = "none";
						break;		
					case "1":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						break;		
					case "2":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						break;		
					case "3":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						break;		
					case "4":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						break;		
					case "5":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						break;		
					case "6":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						break;		
					case "7":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						break;		
					case "8":
						document.getElementById("trPeriodoInicialDate").style.display = "none";
						document.getElementById("trPeriodoFinalDate").style.display = "none";
						
						document.getElementById("trPeriodoInicial").style.display = "";
						document.getElementById("trPeriodoFinal").style.display = "";
						document.getElementById("trAnoInicial").style.display = "";
						document.getElementById("trAnoFinal").style.display = "";

						var periodo = document.getElementById("tdPeriodoInicial");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';
						
						periodo = document.getElementById("tdPeriodoFinal");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';
						
						document.getElementById("trPeriodoInicial").style.display = "none";
						document.getElementById("trPeriodoFinal").style.display = "none";
						break;		
				}
				
				agregarPeriodos(document.reporteForm.frecuencia.value, limpiar);
			}

			function agregarPeriodos(frecuencia, limpiar)
			{
				if (limpiar == undefined)
					limpiar = false;
				
				var select = document.getElementById("selectPeriodoInicial");
				var max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;

			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if (document.reporteForm.periodoInicial.value != "")
			    	select.selectedIndex = document.reporteForm.periodoInicial.value -1;
			    else
			    	select.selectedIndex = 0;
			    
				select = document.getElementById("selectPeriodoFinal");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);
			    
			    if (document.reporteForm.periodoFinal.value != "")
			    	select.selectedIndex = document.reporteForm.periodoFinal.value -1;
			    else
			    	select.selectedIndex = select.options.length-1;
			    
			    hastaMes = numeroPeriodoMaximo(frecuencia);
			    
			    if (limpiar)
		    	{
					document.reporteForm.periodoInicial.value = document.getElementById("selectPeriodoInicial").value;
					document.reporteForm.periodoFinal.value = document.getElementById("selectPeriodoFinal").value;
		    	}
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
			
			function addElementToSelect(combo, texto, valor)
			{
			    var idxElemento = combo.options.length; //Numero de elementos de la combo si esta vacio es 0
			    
			    //Este indice será el del nuevo elemento
			    combo.options[idxElemento] = new Option();
			    combo.options[idxElemento].text = texto; //Este es el texto que verás en la combo
			    combo.options[idxElemento].value = valor; //Este es el valor que se enviará cuando hagas un submit del
		   	}
			
		  	function validarRango(desdeAnoObj, hastaAnoObj, desdeMesObj, hastaMesObj, errmsg)
		  	{
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.reporteForm.frecuencia.value == "0" ? "00" : "0") : (desdeMesObj.value.length == 2 ? "0" : "")) + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.reporteForm.frecuencia.value == "0" ? "00" : "0") : (hastaMesObj.value.length == 2 ? "0" : "")) + hastaMesObj.value);

				desdeAno = desdeAnoObj.value;
				hastaAno = hastaAnoObj.value;
				desdeMes = desdeMesObj.value;
				hastaMes = hastaMesObj.value;
				
				document.reporteForm.periodoInicial.value = desdeMesObj.value;
				document.reporteForm.periodoFinal.value = hastaMesObj.value;
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
	            writer.WriteStartElement("reporte");
	            writer.WriteStartElement("properties");
	            writer.WriteElementString("nombre", CodificarString(document.reporteForm.reporteNombre.value, codificar));
	            writer.WriteElementString("descripcion", CodificarString(document.reporteForm.descripcion.value, codificar));
	            writer.WriteElementString("publico", (document.reporteForm.publico != null ? document.reporteForm.publico.checked ? "true" : "false" : "false"));
	            writer.WriteElementString("filas", document.reporteForm.filas.value);
	            writer.WriteElementString("columnas", document.reporteForm.columnas.value);
	            writer.WriteElementString("frecuencia", document.reporteForm.frecuencia.value);
	            writer.WriteElementString("anoInicial", document.reporteForm.anoInicial.value);
	            writer.WriteElementString("periodoInicial", document.reporteForm.periodoInicial.value);
	            writer.WriteElementString("anoFinal", document.reporteForm.anoFinal.value);
	            writer.WriteElementString("periodoFinal", document.reporteForm.periodoFinal.value);

	            writer.WriteStartElement("configuracion");
	            var objeto;
				for (var i = 0; i < _filas; i++)
				{
					for (var j = 0; j < _columnas; j++)
					{
						writer.WriteStartElement("property");
						writer.WriteElementString("fila", "" + i);
						writer.WriteElementString("columna", "" + j);

						objeto = document.getElementById("campoHiddenEsEncabezado_" + i + "_" + j).value;
						if (objeto != null && objeto != undefined)
							writer.WriteElementString("esEncabezado", objeto);
						else
							writer.WriteElementString("esEncabezado", "false");
						if (objeto == 'true')
						{
							objeto = document.getElementById("campoHiddenNombre_" + i + "_" + j).value;
							if (objeto != null && objeto != undefined)
								writer.WriteElementString("nombreCelda", CodificarString(objeto, codificar));
							else
								writer.WriteElementString("nombreCelda", "");
						}
						else
						{
							objeto = document.getElementById("campoHiddenIndicadorId_" + i + "_" + j).value;
							if (objeto != null && objeto != undefined)
							{
								var indicador = objeto.split('&'); 
								writer.WriteElementString("indicadorId", indicador[0]);
								if (indicador.length > 1 && indicador[1].indexOf("planId") != -1)
								{
									var campos = indicador[1].split('=');
									writer.WriteElementString("planId", campos[1]);
								}
							}
						}
						writer.WriteEndElement();
					}
				}

				writer.WriteEndElement();

	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndDocument();
	            
	            return writer.unFormatted();
			}

			function init()
			{
	            _filas = document.reporteForm.filas.value;
	            _columnas = document.reporteForm.columnas.value;
	            var fecha = new Date (); 
				var anio = fecha.getYear(); 
				if ( anio < 1900 ) 
					anio = 1900 + fecha.getYear();
				desdeAno = anio;
				hastaAno = anio;
				
				agregarPeriodos("3");
				onLoad();
			}

		    function onLoad()
		    {
		    	eventoCambioFrecuencia(false);
		    }
		    
			function establecerEstilo(objeto) 
			{
			 	objeto.style.borderLeft="solid 1px #CCCC00";
			 	objeto.style.borderRight="solid 1px #CCCC00";
			 	objeto.style.borderTop="solid 1px #CCCC00";
			 	objeto.style.borderBottom="solid 1px #CCCC00";			 	
			}
			 
			function quitarEstilo(objeto) 
			{
			 	objeto.style.borderLeft="none";
			 	objeto.style.borderRight="none";
			 	objeto.style.borderTop="none";
			 	objeto.style.borderBottom="none";
			}
		    
			function changeContent(tablecell)
			{
				if (tablecell.innerHTML.indexOf("INPUT") != -1) return;
				_selectedId = tablecell.id;
				var valores = _selectedId.split("_");
				var nombre = "";
				if (window.document.getElementById("campoId_" + valores[1] + "_" + valores[2]) != null && window.document.getElementById("campoId_" + valores[1] + "_" + valores[2]).nombre != undefined)
					nombre = window.document.getElementById("campoId_" + valores[1] + "_" + valores[2]).nombre;
				
			    tablecell.innerHTML = "<INPUT id=\"block\" type=\"text\" name=\"newname\" onBlur=\"javascript:submitNewName(this, '" + tablecell.id +"');\" value=\"" + nombre + "\">";
			    tablecell.firstChild.focus();
			}

			function submitNewName(textfield, id)
			{
				var valores = _selectedId.split("_");
				var tablecell = document.getElementById("celdaId_" + valores[1] + "_" + valores[2]);

				var html = "<input type='hidden' id='campoHiddenId_" + valores[1] + "_" + valores[2] + "' name='campoHiddenId_" + valores[1] + "_" + valores[2] + "' value='" + valores[1] + "_" + valores[2] + "'>";
				html = html + "<input type='hidden' id='campoHiddenEsEncabezado_" + valores[1] + "_" + valores[2] + "' name='campoHiddenEsEncabezado_" + valores[1] + "_" + valores[2] + "' value='true'>";
				html = html + "<input type='hidden' id='campoHiddenNombre_" + valores[1] + "_" + valores[2] + "' name='campoHiddenNombre_" + valores[1] + "_" + valores[2] + "' value='" + textfield.value + "'>";
				html = html + "<span id='campoId_" + valores[1] + "_" + valores[2] + "' nombre='" + textfield.value + "' esEncabezado='true'><b>" + textfield.value + "</b></span>";
				
			    tablecell.innerHTML = html;				
			    _selectedId = null;
			}
			
			function cargarIndicador(tablecell)
			{
				_selectedId = tablecell.id;
				
				var valores = _selectedId.split("_");
				
				var indicadorId = document.getElementsByName("campoHiddenIndicadorId_" + valores[1] + "_" + valores[2]);
				var indicadorNombre = document.getElementsByName("campoHiddenIndicadorNombre_" + valores[1] + "_" + valores[2]);
				if (indicadorId != null && indicadorId != undefined && typeof indicadorId.value != "undefined")
					document.reporteForm.indicadorInsumoSeleccionadoId.value = indicadorId.value;
				else
					document.reporteForm.indicadorInsumoSeleccionadoId.value = "";
				if (indicadorNombre != null && indicadorNombre != undefined && typeof indicadorNombre.value != "undefined")
					document.reporteForm.indicadorInsumoSeleccionadoNombre.value = indicadorNombre.value;
				else
					document.reporteForm.indicadorInsumoSeleccionadoNombre.value = "";
				
				seleccionarIndicadoresInsumo('reporteForm', 'agregarIndicadoresInsumo()', getURL());
			}
			
			function seleccionarIndicadoresInsumo(forma, funcionRetorno, url) 
			{
				if (forma == undefined)
					forma = 'reporteForm';
				if (funcionRetorno == undefined)
					funcionRetorno = 'agregarIndicadoresInsumo()';
				if (url == undefined)
					url = '&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=false&frecuencia=' + document.reporteForm.frecuencia.value + '&excluirIds=' + document.reporteForm.indicadorInsumoSeleccionadoId.value + '&permitirIniciativas=true';

				abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombre', 'indicadorInsumoSeleccionadoId', null, funcionRetorno, null, null, null, null, null, url);
			}
			
			function getURL()
			{
				return '&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=false&frecuencia=' + document.reporteForm.frecuencia.value + '&excluirIds=' + document.reporteForm.indicadorInsumoSeleccionadoId.value + '&permitirIniciativas=true';
			}
			
			function agregarIndicadoresInsumo()
			{
				var valores = _selectedId.split("_");
				var tablecell = document.getElementById("celdaId_" + valores[1] + "_" + valores[2]);
				var indicadorId = document.getElementsByName("campoHiddenIndicadorId_" + valores[1] + "_" + valores[2]);
				var indicadorNombre = document.getElementsByName("campoHiddenIndicadorNombre_" + valores[1] + "_" + valores[2]);
				if (indicadorId != null && indicadorId != undefined)
					indicadorId.value = document.reporteForm.indicadorInsumoSeleccionadoId.value;
				if (indicadorNombre != null && indicadorNombre != undefined)
					indicadorNombre.value = document.reporteForm.indicadorInsumoSeleccionadoNombre.value;

				var html = "<input type='hidden' id='campoHiddenId_" + valores[1] + "_" + valores[2] + "' name='campoHiddenId_" + valores[1] + "_" + valores[2] + "' value='" + valores[1] + "_" + valores[2] + "'>";
				html = html + "<input type='hidden' id='campoHiddenEsEncabezado_" + valores[1] + "_" + valores[2] + "' name='campoHiddenEsEncabezado_" + valores[1] + "_" + valores[2] + "' value='false'>";
				html = html + "<input type='hidden' id='campoHiddenIndicadorId_" + valores[1] + "_" + valores[2] + "' name='campoHiddenIndicadorId_" + valores[1] + "_" + valores[2] + "' value='" + document.reporteForm.indicadorInsumoSeleccionadoId.value + "'>";
				html = html + "<input type='hidden' id='campoHiddenIndicadorNombre_" + valores[1] + "_" + valores[2] + "' name='campoHiddenIndicadorNombre_" + valores[1] + "_" + valores[2] + "' value='" + document.reporteForm.indicadorInsumoSeleccionadoNombre.value + "'>";
				html = html + "<span esEncabezado='false' id='campoId_" + valores[1] + "_" + valores[2] + "' indicadorId='" + document.reporteForm.indicadorInsumoSeleccionadoId.value + "' title='" + document.reporteForm.indicadorInsumoSeleccionadoNombre.value + "' indicadorNombre='" + document.reporteForm.indicadorInsumoSeleccionadoNombre.value + "'>:0</span>";
				
				tablecell.innerHTML = html; 				
			    
			    _selectedId = null;
			    document.reporteForm.indicadorInsumoSeleccionadoNombre.value = "";
			    document.reporteForm.indicadorInsumoSeleccionadoId.value = "";
			}
			
		</script>

		<%-- Tooltip --%>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/screen.css" />" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/jquery-ui.css" />" />
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.dimensions.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.ui.widget.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.ui.tabs.js'/>"></script>
		<script type="text/javascript">
			$(function() 
			{
				$( "#tabs" ).tabs();
			});
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/vistasdatos/editarReporteTranversal" styleClass="formaHtmlCompleta">
		
			<html:hidden property="respuesta" />
			<html:hidden property="periodoInicial" />
			<html:hidden property="periodoFinal" />
			<html:hidden property="virtual" />
			<html:hidden property="id" />
			<html:hidden property="configuracion" />

			<input type="hidden" name="indicadorInsumoSeleccionadoId">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombre">

			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma idContenedor="body-reporte-transversal" width="100%" height="100%" mostrarBarraSuperior="true">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
				..::<vgcutil:message key="jsp.reporteindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
				javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100Collapse">
						<tr class="barraFiltrosForma">
							<td width="20px">
								<b><bean:message key="jsp.reporteindicador.organizacion" /></b>
							</td>
							<td>
								<bean:write name="organizacion" scope="session" property="nombre" />&nbsp;
							</td>
						</tr>
					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraGraficoIndicador">
						<logic:notEqual name="reporteForm" property="bloqueado" value="true">							
							<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar" pathImagenes="/componentes/barraHerramientas/" nombre="refrescar" onclick="javascript:aplicar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.aplicar.configuracion" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:save();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar.configuracion" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasSeparador />
					</vgcinterfaz:barraHerramientas>

					<%-- Tabs --%>
					<table class="tabtable tabFichaDatostabla bordeFichaDatos">
						<tr>
							<td class="tabtd">
								<div class="demo">
									<div id="tabs">
										<ul>
											<li><a href="#tabs-1"><vgcutil:message key="jsp.reporteindicador.editor.tab.configuracion" /></a></li>
											<li><a href="#tabs-2"><vgcutil:message key="jsp.reporteindicador.editor.tab.insumo" /></a></li>
										</ul>
										<div id="tabs-1">
											<table class="tabTablaPrincipal">
												<tr>
													<td><vgcutil:message key="jsp.reporteindicador.editor.tab.nombre" /></td>
													<td><html:text property="reporteNombre" size="50" maxlength="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" /></td>
												</tr>
												<tr>
													<td width="75px" valign="top"><vgcutil:message key="jsp.reporteindicador.editor.tab.descripcion" /></td>
													<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="5" cols="50" property="descripcion" styleClass="cuadroTexto" /></td>
												</tr>
												<tr>
													<td width="65px"><vgcutil:message key="jsp.reporteindicador.editor.tab.publico" /></td>
													<td><html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="publico" styleClass="botonSeleccionMultiple"></html:checkbox></td>
												</tr>
												<tr>
													<td>
														<table class="tabTablaPrincipal">
															<tr>
																<td><vgcutil:message key="jsp.reporteindicador.editor.tab.filas" /></td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
																		<tr>
																			<td valign="middle" align="left">
																				<html:text property="filas" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
																			</td>
																			<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																				<td valign="middle">
																					<img id="boton1" name="boton1" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
																				</td>
																			</logic:notEqual>
																		</tr>
																	</table>
																	<map name="MapControlUpDown1" id="MapControlUpDown1">
																		<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton1')" onmouseout="normalAction('boton1')" onmousedown="iniciarConteoContinuo('filas', 99, 1);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
																		<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton1')" onmouseout="normalAction('boton1')" onmousedown="iniciarConteoContinuo('filas', 99, 1);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
																	</map>
																</td>
															</tr>
														</table>
													</td>
													<td>
														<table class="tabTablaPrincipal">
															<tr>
																<td><vgcutil:message key="jsp.reporteindicador.editor.tab.columnas" /></td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
																		<tr>
																			<td valign="middle" align="left">
																				<html:text property="columnas" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
																			</td>
																			<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																				<td valign="middle"><img id="boton3" name="boton3" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" /></td>
																			</logic:notEqual>
																		</tr>
																	</table>
																	<map name="MapControlUpDown3" id="MapControlUpDown3">
																		<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton3')" onmouseout="normalAction('boton3')" onmousedown="iniciarConteoContinuo('columnas', 99,1);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
																		<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton3')" onmouseout="normalAction('boton3')" onmousedown="iniciarConteoContinuo('columnas', 99, 1);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
																	</map>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div id="tabs-2">
											<table class="tabTablaPrincipal" width="300px">
												<tr>
													<td align="left">
														<vgcutil:message key="jsp.reporteindicador.editor.tab.frecuencia" />
													</td>
													<td>
														<logic:empty property="className" name="reporteForm">
															<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																styleClass="cuadroTexto" size="1"
																onchange="eventoCambioFrecuencia()">
																<html:optionsCollection property="frecuencias"
																	value="frecuenciaId" label="nombre" />
															</html:select>
														</logic:empty>
														<logic:notEmpty property="className" name="reporteForm">
															<logic:equal name="reporteForm" property="className" value="Celda">
																<html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																	styleClass="cuadroTexto" size="1"
																	onchange="eventoCambioFrecuencia()">
																	<html:optionsCollection property="frecuencias"
																		value="frecuenciaId" label="nombre" />
																</html:select>
															</logic:equal>
															<logic:notEqual name="reporteForm" property="className" value="Celda">
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
												<tr>
													<td>
														<br>
													</td>
												</tr>
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.reporteindicador.editor.tab.rango" /></td>
												</tr>
												<tr>
													<td width="50%" style="display:none"><vgcutil:message key="jsp.asistente.grafico.inicial" /></td>
													<td width="50%" style="display:none"><vgcutil:message key="jsp.asistente.grafico.final" /></td>
												</tr>
												<tr>
													<bean:define 
														id="maximoPeriodo" 
														name="reporteForm"
														property="numeroMaximoPeriodos" 
														type="java.lang.Integer"
														toScope="page" />
													<td width="50%">
														<table class="tabla contenedorBotonesSeleccion">
															<tr id="trAnoInicial">
																<td><vgcutil:message key="jsp.asistente.grafico.ano" /></td>
																<td>
																	<bean:define id="anoCalculoInicial" toScope="page">
																	<bean:write name="reporteForm" property="anoInicial" /></bean:define>
																	<html:select 
																		property="anoInicial" 
																		disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																		value="<%=anoCalculoInicial%>"
																		onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
															<tr id="trPeriodoInicial">
																<td><span id="tdPeriodoInicial"></span></td>
																<td>
																	<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																		<select 
																			id="selectPeriodoInicial"
																			onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																			class="cuadroTexto">
																		</select>
																	</logic:notEqual>
																	<logic:equal name="reporteForm" property="bloqueado" value="true">
																		<select 
																			id="selectPeriodoInicial" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																			class="cuadroTexto">
																		</select>
																	</logic:equal>
																</td>
															</tr>
															<tr id="trPeriodoInicialDate">
																<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
																<td>
																	<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaDesde" size="10" maxlength="10" styleClass="cuadroTexto" />
																	<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																		<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
																	</logic:notEqual>
																</td>
															</tr>
														</table>
													</td>
													<td width="50%" style="display:none">
														<table class="tabla contenedorBotonesSeleccion">
															<tr id="trAnoFinal">
																<td><vgcutil:message key="jsp.asistente.grafico.ano" /></td>
																<td>
																	<bean:define id="anoCalculoFinal" toScope="page"><bean:write name="reporteForm" property="anoFinal" /></bean:define>
																	<html:select property="anoFinal" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																		value="<%=anoCalculoFinal%>"
																		onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
															<tr id="trPeriodoFinal">
																<td><span id="tdPeriodoFinal"></span></td>
																<td>
																	<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																		<select 
																			id="selectPeriodoFinal" 
																			onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																			class="cuadroTexto">
																		</select>
																	</logic:notEqual>
																	<logic:equal name="reporteForm" property="bloqueado" value="true">
																		<select 
																			id="selectPeriodoFinal" 
																			disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
																			onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
																			class="cuadroTexto">
																		</select>
																	</logic:equal>
																</td>
															</tr>
															<tr id="trPeriodoFinalDate">
																<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
																<td>
																	<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaHasta" size="10" maxlength="10" styleClass="cuadroTexto" />
																	<logic:notEqual name="reporteForm" property="bloqueado" value="true">
																		<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
																	</logic:notEqual>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<br>
							</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Cuerpo --%>
				<table class="tabla">
					<tr>
						<td>
							<%-- Datos --%>
							<bean:define id="colorCelda" value="oscuro" />
							<bean:define id="estilo" value="#F4F4F4" />
							<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" id="datos" width="<bean:write name="reporteForm" property="anchoTablaDatos" />">
								<logic:iterate name="reporteForm" property="matrizDatos" scope="session" id="fila" indexId="filaIndex">
	
									<%-- Validaciones para determinar el color de la celda --%>
									<logic:equal name="colorCelda" value="oscuro">
										<bean:define id="estilo" value="#FFFFF2" />
									</logic:equal>
									<logic:equal name="colorCelda" value="claro">
										<bean:define id="estilo" value="#F4F4F4" />
									</logic:equal>
	
									<tr height="30px">
										<logic:iterate name="fila" id="columna" indexId="columnaIndex">
	
											<%-- Validaciones de la tabla --%>
											<bean:define id="esEncabezado" value="false" />
											<bean:define id="centrado" value="false" />
											<logic:equal name="columnaIndex" value="0">
												<bean:define id="esEncabezado" value="true" />
											</logic:equal>
											<logic:equal name="filaIndex" value="0">
												<bean:define id="esEncabezado" value="true" />
												<bean:define id="centrado" value="true" />
											</logic:equal>
	
											<%-- Se escribe la celda correspondiente de la tabla --%>
											<logic:equal name="esEncabezado" value="true">
												<logic:equal name="centrado" value="true">
													<logic:notEqual name="reporteForm" property="bloqueado" value="true">
														<td width="200px" class="encabezadoListView" align="center" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>" ondblclick="changeContent(this)">
															<logic:notEmpty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre='<bean:write name="columna" property="valor" />'><b><bean:write name="columna" property="valor" /></b></span>
															</logic:notEmpty>
															<logic:empty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre=''></span>
															</logic:empty>
														</td>
													</logic:notEqual>
													<logic:equal name="reporteForm" property="bloqueado" value="true">
														<td width="200px" class="encabezadoListView" align="center" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>">
															<logic:notEmpty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre='<bean:write name="columna" property="valor" />'><b><bean:write name="columna" property="valor" /></b></span>
															</logic:notEmpty>
															<logic:empty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre=''></span>
															</logic:empty>
														</td>
													</logic:equal>
												</logic:equal>
												<logic:equal name="centrado" value="false">
													<logic:notEqual name="reporteForm" property="bloqueado" value="true">
														<td bgcolor="<%=estilo%>" width="200px" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>" ondblclick="changeContent(this)">
															<logic:notEmpty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre='<bean:write name="columna" property="valor" />'><b><bean:write name="columna" property="valor" /></b></span>
															</logic:notEmpty>
															<logic:empty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre=''></span>
															</logic:empty>
														</td>
													</logic:notEqual>
													<logic:equal name="reporteForm" property="bloqueado" value="true">
														<td bgcolor="<%=estilo%>" width="200px" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>">
															<logic:notEmpty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre='<bean:write name="columna" property="valor" />'><b><bean:write name="columna" property="valor" /></b></span>
															</logic:notEmpty>
															<logic:empty name="columna" property="valor">
																<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
																<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='true'>
																<input type='hidden' id='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
																<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='true' nombre=''></span>
															</logic:empty>
														</td>
													</logic:equal>
												</logic:equal>
											</logic:equal>
	
											<%-- Validaciones para determinar el color de la celda --%>
											<logic:equal name="esEncabezado" value="false">
												<logic:notEqual name="reporteForm" property="bloqueado" value="true">
													<td width="200px" onmouseover="establecerEstilo(this);" onmouseout="quitarEstilo(this);" align="center" bgcolor="<%=estilo%>" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>" ondblclick="cargarIndicador(this)">
														<logic:notEmpty name="columna" property="valor">
															<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
															<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='false'>
															<input type='hidden' id='campoHiddenIndicadorId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
															<input type='hidden' id='campoHiddenIndicadorNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="nombre" />'>
															<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='false' indicadorId='<bean:write name="columna" property="valor" />' title='<bean:write name="columna" property="nombre" />' indicadorNombre='<bean:write name="columna" property="nombre" />'>:0</span>
														</logic:notEmpty>
														<logic:empty name="columna" property="valor">
															<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
															<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='false'>
															<input type='hidden' id='campoHiddenIndicadorId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
															<input type='hidden' id='campoHiddenIndicadorNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
															<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='false' indicadorId='' title='' indicadorNombre=''></span>
														</logic:empty>
													</td>
												</logic:notEqual>
												<logic:equal name="reporteForm" property="bloqueado" value="true">
													<td width="200px" onmouseover="establecerEstilo(this);" onmouseout="quitarEstilo(this);" align="center" bgcolor="<%=estilo%>" id="celdaId_<%=filaIndex%>_<%=columnaIndex%>" name="celdaId_<%=filaIndex%>_<%=columnaIndex%>">
														<logic:notEmpty name="columna" property="valor">
															<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
															<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='false'>
															<input type='hidden' id='campoHiddenIndicadorId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="valor" />'>
															<input type='hidden' id='campoHiddenIndicadorNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value='<bean:write name="columna" property="nombre" />'>
															<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='false' indicadorId='<bean:write name="columna" property="valor" />' title='<bean:write name="columna" property="nombre" />' indicadorNombre='<bean:write name="columna" property="nombre" />'>:0</span>
														</logic:notEmpty>
														<logic:empty name="columna" property="valor">
															<input type='hidden' id='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenId_<%=filaIndex%>_<%=columnaIndex%>' value='<%=filaIndex%>_<%=columnaIndex%>'>
															<input type='hidden' id='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenEsEncabezado_<%=filaIndex%>_<%=columnaIndex%>' value='false'>
															<input type='hidden' id='campoHiddenIndicadorId_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
															<input type='hidden' id='campoHiddenIndicadorNombre_<%=filaIndex%>_<%=columnaIndex%>' name='campoHiddenNombre_<%=filaIndex%>_<%=columnaIndex%>' value=''>
															<span id='campoId_<%=filaIndex%>_<%=columnaIndex%>' esEncabezado='false' indicadorId='' title='' indicadorNombre=''></span>
														</logic:empty>
													</td>
												</logic:equal>
											</logic:equal>
										</logic:iterate>
									</tr>
	
									<%-- Validaciones de la tabla --%>
									<logic:equal name="estilo" value="#FFFFF2">
										<bean:define id="colorCelda" value="claro" />
									</logic:equal>
									<logic:equal name="estilo" value="#F4F4F4">
										<bean:define id="colorCelda" value="oscuro" />
									</logic:equal>
	
								</logic:iterate>
							</table>
						</td>
					</tr>
				</table>
			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-reporte-transversal'), 370);
		</script>
		<script>
			init();
			<logic:equal name="reporteForm" property="estatusSave" value="1">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
