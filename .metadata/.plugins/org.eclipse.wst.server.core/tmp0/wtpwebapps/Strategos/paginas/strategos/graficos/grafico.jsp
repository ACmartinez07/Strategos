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
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/paletaColor/css/colorPicker.css" />">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/style.css" />" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/grafico/css/print.css" />" media="print"/>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/menu/css/helper.css" />" media="screen"/>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/menu/css/dropdown/dropdown.css" />" media="screen"/>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/menu/css/dropdown/dropdown.vertical.rtl.css" />" media="screen"/>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/menu/css/dropdown/themes/default/default.advanced.css" />" media="screen"/>

		<%-- Funciones JavaScript --%>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/graphicType.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/paletaColor/colorPicker.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/numeral.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery-1.7.1.min.js'/>"></script>
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
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>

		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
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
			var desdeAno = '<bean:write name="graficoForm" property="anoInicial" ignore="true"/>';
			var hastaAno = '<bean:write name="graficoForm" property="anoFinal" ignore="true"/>';
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';
			var tipoGraficos = new Array(4, 2);
			tipoGraficos[0] = new Array("-1",'<vgcutil:message key="graficoindicador.tiposserie.nograficar" />');
			tipoGraficos[1] = new Array("0",'<vgcutil:message key="graficoindicador.tiposserie.linea" />');
			tipoGraficos[2] = new Array("1",'<vgcutil:message key="graficoindicador.tiposserie.barra" />');
			var _onload = true;
			var _calendario = null;
			
			function seleccionarFechaDesde() 
			{
				_calendario = mostrarCalendario('document.graficoForm.fechaDesde' , document.graficoForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
		
			function seleccionarFechaHasta() 
			{
				_calendario = mostrarCalendario('document.graficoForm.fechaHasta' , document.graficoForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}

			function cancelar() 
			{
				window.document.graficoForm.action = '<html:rewrite action="/graficos/grafico"/>?cancelar=true';
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
				if (document.graficoForm.objetosIds.value != null && document.graficoForm.objetosIds.value != "")
				{
					parametros = parametros + '&source=' + "General";
					parametros = parametros + '&objetoId=' + "";
				}
				else
				{
					parametros = parametros + '&source=' + document.graficoForm.source.value;
					parametros = parametros + '&objetoId=' + document.graficoForm.objetoId.value;
				}
				
				if(document.graficoForm.esReporteGrafico.value){
					parametros = parametros + '&reporteId=' + document.graficoForm.reporteId.value;
				}
				
				parametros = parametros + '&claseId=' + document.graficoForm.claseId.value;
				parametros = parametros + '&planId=' + document.graficoForm.planId.value;
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/guardarAsistenteGrafico" />?' + parametros, document.graficoForm.respuesta, 'onSalvar()');
			}

			function onSalvar()
			{
				var respuesta = document.graficoForm.respuesta.value.split("|");
				if (respuesta.length > 0 && respuesta[1] == 0)
				{
					<logic:empty name="graficoForm" property="paginaId">
						cancelar();
					</logic:empty>
					<logic:notEmpty name="graficoForm" property="paginaId">
						<logic:notEqual name="graficoForm" property="paginaId" value="0">
							alert('<vgcutil:message key="jsp.asistente.grafico.alert.save.exito" /> ');
						</logic:notEqual>
						<logic:equal name="graficoForm" property="paginaId" value="0">
							cancelar();
						</logic:equal>
					</logic:notEmpty>
					return;
				}
				else if (respuesta.length > 0 && respuesta[1] == 3)
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.nombre.duplicado" /> ');
			}
			
			function validar(permitirRetorno) 
			{
				if (typeof(permitirRetorno) == "undefined")
					permitirRetorno = true;
					
			 	if (document.getElementById("selectFrecuenciasCompatibles").value == 0)
		 		{
			 		if (!fechaValida(document.graficoForm.fechaDesde))
		 			{
			 			alert('<vgcutil:message key="jsp.asistente.grafico.alert.fechadesde.invalido" /> ');
			 			return;
		 			}

			 		if (!fechaValida(document.graficoForm.fechaHasta))
		 			{
			 			alert('<vgcutil:message key="jsp.asistente.grafico.alert.fechahasta.invalido" /> ');
			 			return;
		 			}
		 		}
				
				if (document.getElementById("selectFrecuenciasCompatibles").value == 0)
				{
					var fecha1 = document.graficoForm.fechaDesde.value.split("/");
					var fecha2 = document.graficoForm.fechaHasta.value.split("/");

					document.graficoForm.anoInicial.value = parseInt(fecha1[2]);
					document.graficoForm.anoFinal.value = parseInt(fecha2[2]);

					document.graficoForm.periodoInicial.value = getPeriodoDeFechaDiaria(document.graficoForm.fechaDesde.value);
					document.graficoForm.periodoFinal.value = getPeriodoDeFechaDiaria(document.graficoForm.fechaHasta.value);
				}

			 	desde = parseInt(document.graficoForm.anoInicial.value + "" + (document.graficoForm.periodoInicial.value.length == 1 ? "00" : (document.graficoForm.periodoInicial.value.length == 2 ? "0" : "")) + document.graficoForm.periodoInicial.value);
				hasta = parseInt(document.graficoForm.anoFinal.value + "" + (document.graficoForm.periodoFinal.value.length == 1 ? "00" : (document.graficoForm.periodoFinal.value.length == 2 ? "0" : "")) + document.graficoForm.periodoFinal.value);
				if (hasta<desde) 
				{
					if (document.getElementById("selectFrecuenciasCompatibles").value == 0)
						alert('<vgcutil:message key="jsp.asistente.grafico.alerta.rango.fechas.invalido" /> ');
					else
						alert(errMsRango);
					return false;
				} 
				
				if (document.graficoForm.tipo.value == 5 || document.graficoForm.tipo.value == 10 || document.graficoForm.tipo.value == 14)
				{
					if (hasta != desde)
					{
						alert('<vgcutil:message key="jsp.asistente.grafico.tipografico.torta.alerta.varios.periodos" /> ');
						return false;
					}
				}
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				if (tablaListaInsumos != null && tablaListaInsumos.getElementsByTagName("tr").length == 0) 
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.insumo.alerta.noinsumos" /> ');
		 			if (permitirRetorno)
		 				return false;
		 			else
	 				{
		 				var respuesta = confirm ('<vgcutil:message key="jsp.asistente.grafico.cerrar.configuracion" />');
		 				if (respuesta)
		 					return true;
		 				else
		 					return false;
	 				}
				}

				if (document.graficoForm.tipo.value < 1)
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.tipografico.alerta.notipo" /> ');
					return false;
				}

				if (window.document.graficoForm.titulo.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.titulosgraficos.alerta.titulos" /> ');
					return false;
				}
				
				if (document.graficoForm.titulo.value.length > 100)
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.titulo.lenght" /> ');
					return;
				}

				if (document.graficoForm.tipo.value == 8 || document.graficoForm.tipo.value == 6)
				{
					var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
					var indicadorId;
					var serieId;
					var planId;
					var objeto;
					var visible;
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
							indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", ""); 
							// serieId
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.indexOf("][");
							serieId = strTemp.substring(0, indice).replace("[serieId:", "");
							// planId
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.indexOf("][");
							planId = strTemp.substring(0, indice).replace("[planId:", "");
							
							objeto = document.getElementById("tipo_" + indicadorId + "_" + serieId + "_" + planId);
							visible = document.getElementById("visible_" + indicadorId + "_" + serieId + "_" + planId);
							if (objeto != null && objeto.value == -1 && visible != null && visible.checked)
							{
								alert('<vgcutil:message key="jsp.asistente.grafico.alert.tipoGrafico.invalido" /> ');
								return false;
							}
						}
					}
				}

				if (document.graficoForm.tipo.value == 14 || document.graficoForm.tipo.value == 6)
				{
					var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
					var indicadorId;
					var serieId;
					var planId;
					var objeto;
					var serie = new Array();
					var serieIdControl = -1;
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
							indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", ""); 
							// serieId
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.indexOf("][");
							serieId = strTemp.substring(0, indice).replace("[serieId:", "");
							// planId
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.indexOf("][");
							planId = strTemp.substring(0, indice).replace("[planId:", "");
							
							objeto = document.getElementById("visible_" + indicadorId + "_" + serieId + "_" + planId);
							var id = indicadorId + "_" + serieId + "_" + planId;
							if (objeto != null && objeto.checked && serieIdControl != id)
							{
								serieIdControl = serieId;
								serie.push(serieIdControl);
							}
						}
					}
					
					if (serie.length != 2)
					{
						alert('<vgcutil:message key="jsp.asistente.grafico.alert.tipoGrafico.invalido.serie" /> ');
						return false;
					}
				}
				
				var visible = false;
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var indicadorId;
				var serieId;
				var objeto;
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
						indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", ""); 
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieId = strTemp.substring(0, indice).replace("[serieId:", "");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring(0, indice).replace("[planId:", "");
						
						objeto = document.getElementById("visible_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null && objeto.checked)
						{
							visible = true;
							break;
						}
					}
				}
				
				if (!visible)
				{
					alert('<vgcutil:message key="jsp.asistente.grafico.alert.visible.invalido" /> ');
					if (permitirRetorno)
						return false;
		 			else
	 				{
		 				var respuesta = confirm ('<vgcutil:message key="jsp.asistente.grafico.cerrar.configuracion" />');
		 				if (respuesta)
		 					return true;
		 				else
		 					return false;
	 				}
				}
				document.graficoForm.graficoNombre.value = document.graficoForm.titulo.value;

				return true;
			}
			
			function setTipoGraficoDefault(bloquear)
			{
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var indicadorId;
				var serieId;
				var objeto;
				if (typeof(bloquear) == "undefined")
					bloquear = false;
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
						indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", ""); 
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieId = strTemp.substring(0, indice).replace("[serieId:", "");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring(0, indice).replace("[planId:", "");
						
						objeto = document.getElementById("tipo_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
						{
							objeto.selectedIndex = 0;
							objeto.disabled = bloquear; 
						}
					}
				}
			}
			
			function CodificarString(value, codificar)
			{
				var valor = value;

				if (codificar)
				{
					valor = valor.replace("%", "[[por]]");
					valor = valor.replace("#", "[[num]]");
				}
				
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
	            writer.WriteElementString("frecuencia", document.graficoForm.frecuencia.value);
	            writer.WriteElementString("frecuenciaAgrupada", document.getElementById("selectFrecuenciasCompatibles").value);
	            writer.WriteElementString("nombre", CodificarString(document.graficoForm.titulo.value, codificar));
	            writer.WriteElementString("condicion", (document.graficoForm.condicion != null ? document.graficoForm.condicion.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("verTituloImprimir", (document.graficoForm.verTituloImprimir != null ? document.graficoForm.verTituloImprimir.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("ajustarEscala", (document.graficoForm.ajustarEscala != null ? document.graficoForm.ajustarEscala.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("acumular", (document.graficoForm.acumular != null ? document.graficoForm.acumular.checked ? "1" : "0" : "0"));
	            writer.WriteElementString("impVsAnoAnterior", (document.graficoForm.impVsAnoAnterior != null ? document.graficoForm.impVsAnoAnterior.checked ? "1" : "0" : "0"));
	            writer.WriteStartElement("indicadores");
	            
				// Se recorre la lista de insumos
				var insumos = document.graficoForm.insumosFormula.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var indicadorId;
				var serieId;
				var objeto;
				var planId;
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
						indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", "");
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieId = strTemp.substring(0, indice).replace("[serieId:", "");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring(0, indice).replace("[planId:", "");
						
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
						
						objeto = document.getElementById("serie_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("leyenda", CodificarString(objeto.value, codificar));
						
						objeto = document.getElementById("color_hidden_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("color", objeto.value.replace("#", "[[num]]"));

						objeto = document.getElementById("tipo_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("tipoGrafico", objeto.value);

						objeto = document.getElementById("visible_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("visible", objeto.checked ? "1" : "0");

						objeto = document.getElementById("showOrganizacion_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("showOrganizacion", objeto.checked ? "1" : "0");

						objeto = document.getElementById("nivelClase_" + indicadorId + "_" + serieId + "_" + planId);
						if (objeto != null)
							writer.WriteElementString("nivelClase", CodificarString(objeto.value, codificar));
						
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
		        	//var chartPrint = $("#" + _containerGraphic).highcharts().setSize(800, 700, doAnimation = true);
		        	
		        	//var chart2 = new Highcharts.Chart({
		        	    //chart: {
		        	       // renderTo: 'div_grafico_print'
		        	    //}
		        	//}).highcharts().setSize(800, 700, doAnimation = true);
		        	
		        	
		        	//var grafico = $("#div_grafico_print").html();
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
				var data = getXml(true);
				var parametros = 'data=' + data;
				parametros = parametros + '&onFuncion=onAplicar';
				parametros = parametros + '&id=' + document.graficoForm.id.value;
				parametros = parametros + '&nombre=' + document.graficoForm.titulo.value; 
				parametros = parametros + '&source=' + document.graficoForm.source.value;
				parametros = parametros + '&portafolioId=' + document.graficoForm.portafolioId.value;
				if (document.graficoForm.objetosIds.value != null && document.graficoForm.objetosIds.value != "")
					parametros = parametros + '&objetoId=' + document.graficoForm.objetosIds.value;
				else
					parametros = parametros + '&objetoId=' + document.graficoForm.objetoId.value;
				parametros = parametros + '&claseId=' + document.graficoForm.claseId.value;
				parametros = parametros + '&planId=' + document.graficoForm.planId.value;
				<logic:notEmpty name="graficoForm" property="paginaId">
					<logic:notEqual name="graficoForm" property="paginaId" value="0">
						parametros = parametros + '&vistaId=<bean:write name="graficoForm" property="vistaId" />&paginaId=<bean:write name="graficoForm" property="paginaId" />';
					</logic:notEqual>
				</logic:notEmpty>
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/grafico"/>?virtual=' + document.graficoForm.virtual.value + "&" + parametros, document.graficoForm.respuesta, 'onAplicar()');
			}
			
			function onAplicar()
			{
				var respuesta = document.graficoForm.respuesta.value;
				if (respuesta == "10000")
				{
					var parametros = 'id=' + document.graficoForm.id.value;
					parametros = parametros + '&nombre=' + document.graficoForm.titulo.value; 
					parametros = parametros + '&source=' + document.graficoForm.source.value;
					parametros = parametros + '&portafolioId=' + document.graficoForm.portafolioId.value;
					if (document.graficoForm.objetosIds.value != null && document.graficoForm.objetosIds.value != "")
						parametros = parametros + '&objetoId=' + document.graficoForm.objetosIds.value;
					else
						parametros = parametros + '&objetoId=' + document.graficoForm.objetoId.value;
					parametros = parametros + '&claseId=' + document.graficoForm.claseId.value;
					parametros = parametros + '&planId=' + document.graficoForm.planId.value;
					<logic:notEmpty name="graficoForm" property="paginaId">
						<logic:notEqual name="graficoForm" property="paginaId" value="0">
							parametros = parametros + '&vistaId=<bean:write name="graficoForm" property="vistaId" />&paginaId=<bean:write name="graficoForm" property="paginaId" />';
						</logic:notEqual>
					</logic:notEmpty>
					
					window.location.href='<html:rewrite action="/graficos/grafico"/>?virtual=' + document.graficoForm.virtual.value + "&" + parametros;
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
			
		  	function validarRango(desdeAnoObj, hastaAnoObj, desdeMesObj, hastaMesObj, errmsg)
		  	{
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.graficoForm.frecuencia.value == "0" ? "00" : "0") : (desdeMesObj.value.length == 2 ? "0" : "")) + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.graficoForm.frecuencia.value == "0" ? "00" : "0") : (hastaMesObj.value.length == 2 ? "0" : "")) + hastaMesObj.value);

				desdeAno = desdeAnoObj.value;
				hastaAno = hastaAnoObj.value;
				desdeMes = desdeMesObj.value;
				hastaMes = hastaMesObj.value;
				
				document.graficoForm.periodoInicial.value = desdeMesObj.value;
				document.graficoForm.periodoFinal.value = hastaMesObj.value;
			}
		  	
		  	function eventoCambioFrecuenciasCompatibles(limpiar)
		  	{
				if (limpiar == undefined)
					limpiar = true;

				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				if (limpiar)
				{
					document.graficoForm.periodoInicial.value = "";
					document.graficoForm.periodoFinal.value = "";
		  		}
	
				switch (document.getElementById("selectFrecuenciasCompatibles").value) 
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
				
				agregarPeriodos(document.getElementById("selectFrecuenciasCompatibles").value, limpiar);
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
					if (tablaListaInsumos != null)
					{
						while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
							tablaListaInsumos.deleteRow(0);
					}
				}
				
				switch (document.graficoForm.frecuencia.value) 
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
				
				agregarPeriodos(document.graficoForm.frecuencia.value, true);
				if (!_onload)
					document.graficoForm.frecuenciaAgrupada.value = document.graficoForm.frecuencia.value;
				SetFrecuenciasCompatibles(document.graficoForm.frecuencia.value);
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
			    	select.selectedIndex = select.options.length-1;
			    
			    hastaMes = numeroPeriodoMaximo(frecuencia);
			    
			    if (limpiar)
		    	{
					document.graficoForm.periodoInicial.value = document.getElementById("selectPeriodoInicial").value;
					document.graficoForm.periodoFinal.value = document.getElementById("selectPeriodoFinal").value;
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

			function init()
			{
				var fecha = new Date (); 
				var anio = fecha.getYear(); 
				if ( anio < 1900 ) 
					anio = 1900 + fecha.getYear();
				desdeAno = anio;
				hastaAno = anio;
				
				onLoad();
				eventoCambioFrecuenciasCompatibles(false);
				_onload = false;
				
				var objeto = document.getElementById(_containerGraphic);
				if (objeto != null)
				{
					objeto.style.width = (parseInt(_myWidth) - 60) + "px";
					if (document.graficoForm.tipo.value == 11 || document.graficoForm.tipo.value == 12)
						objeto.style.height = (parseInt(_myHeight) - 350) + "px";
					else
						objeto.style.height = (parseInt(_myHeight) - 250) + "px";
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
			
			function seleccionarIndicadores() 
			{
				var forma = 'graficoForm';
				var funcionRetorno = 'agregarIndicadores()';
				var url = '';
				if (document.graficoForm.className.value == "Celda" || document.graficoForm.className.value == "")
					url = '&agregarSerieMeta=true&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true' + '&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.graficoForm.frecuencia.value + '&excluirIds=0';
				else
					url = '&agregarSerieMeta=true&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=false&permitirCambiarClase=false&seleccionMultiple=true&permitirIniciativas=false&permitirPlanes=false&frecuencia=' + document.graficoForm.frecuencia.value + '&excluirIds=0' + '&indicadorId=' + document.graficoForm.objetoId.value + '&claseId=' + document.graficoForm.claseId.value;
		
				abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', funcionRetorno, null, null, null, null, null, url, 'indicadorInsumoSeleccionadoSeriePlanId');
			}
			
			function agregarIndicadores()
			{
				var seleccionadoIds = document.graficoForm.indicadorInsumoSeleccionadoIds.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoNombres = document.graficoForm.indicadorInsumoSeleccionadoNombres.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoRutasCompletas = document.graficoForm.indicadorInsumoSeleccionadoRutasCompletas.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var seleccionadoSeriePlanId = document.graficoForm.indicadorInsumoSeleccionadoSeriePlanId.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var nombreIndicador = "";
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
						nombresIndicador = seleccionadoNombres[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						planesIds = seleccionadoSeriePlanId[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						nombreIndicador = nombresIndicador[0];
						
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
						}
					}
				}

				var seleccionadoSeries = null;
				if (document.graficoForm.indicadorInsumoSeleccionadoSerie.value != "")
					seleccionadoSeries = document.graficoForm.indicadorInsumoSeleccionadoSerie.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');				
				var listaInsumosSerie = document.graficoForm.insumosSerie.value;
				var indicadorId;
				var serieId;
				var index = 0;
				
				if (listaInsumosSerie == null) 
				{
					listaInsumosSerie = "";
					var tabla = document.getElementById('tablaListaSeries');
					var row;
					var valores;
					var insumos;
					// Se borra la lista de insumos
					for (i = 0; i < tabla.getElementsByTagName("tr").length; i++)
					{
						row = tabla.getElementsByTagName("tr").item(i);
						valores = row.replace("row_", "").split("_");
						indicadorId = valores[0];
						serieId = valores[1];
						planId = valores[1];
						index = i;
						if (index != 0)
							listaInsumosSerie = listaInsumosSerie + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>'; 
						
						insumos = "[row:" + index + "]";
						insumos = insumos + "[indicadorId:" + indicadorId + "]";
						insumos = insumos + "[serieId:" + serieId + "]";
						insumos = insumos + "[serie:" + document.getElementById("serie_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						insumos = insumos + "[color:" + document.getElementById("color_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						insumos = insumos + "[color_hidden:" + document.getElementById("color_hidden_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						insumos = insumos + "[color_decimal_hidden:" + document.getElementById("color_decimal_hidden_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						insumos = insumos + "[visible:" + (document.getElementById("visible_" + indicadorId + "_" + serieId + "_" + planId).checked ? "1" : "0") + "]";
						insumos = insumos + "[tipoGrafico:" + document.getElementById("tipo_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						insumos = insumos + "[showOrganizacion:" + "true" + "]";
						insumos = insumos + "[nivelClase:" + document.getElementById("nivelClase_" + indicadorId + "_" + serieId + "_" + planId).value + "]";
						
						listaInsumosSerie = listaInsumosSerie + insumos; 
					}
				}
				
				if (seleccionadoIds != "")
				{
					var unSoloIndicador = true;
					var indId = null;
					for (var i = 0; i < seleccionadoIds.length; i++) 
					{
						partesId = seleccionadoIds[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						nombresIndicador = seleccionadoNombres[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						planesIds = seleccionadoSeriePlanId[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
						nombreIndicador = nombresIndicador[0];
						
						valorSeries = null;
						if (seleccionadoSeries != null)
							valorSeries = seleccionadoSeries[i].split('<bean:write name="graficoForm" property="separadorSeries" ignore="true"/>');
				
						insumoBuscado = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + '][planId:' + planesIds[1] + ']';
						indicadorId = partesId[0];
						if (indId != null && indId != indicadorId)
							unSoloIndicador = false;
						indId = indicadorId;
						serieId = partesId[1];
						planId = planesIds[1];
						
						if (listaInsumosSerie.indexOf(insumoBuscado) < 0) 
						{
							var separadorIndicadores = '';
							if (listaInsumosSerie != '') 
								separadorIndicadores = '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>';
							
							if (seleccionadoRutasCompletas[i] != '<bean:write name="graficoForm" property="codigoIndicadorEliminado" ignore="true"/>') 
							{
								if (index != 0)
									index++;
								
								var nombreLeyenda = valorSeries != null ? (valorSeries.length != 0 ? valorSeries[2] : (nombresIndicador[0] + " - " + nombresIndicador[1])) : (nombresIndicador[0] + " - " + nombresIndicador[1]);
								var color = valorSeries != null ? (valorSeries.length != 0 ? valorSeries[3] : getRndColorRGB()) : getRndColorRGB();
								var visible = valorSeries != null ? (valorSeries.length != 0 ? valorSeries[4] : "1") : "1";
								var tipoGrafico = valorSeries != null ? (valorSeries.length != 0 ? valorSeries[5] : "-1") : "-1";
								var showOrganizacion = valorSeries != null ? (valorSeries.length != 0 && valorSeries.length > 6 ? valorSeries[6] : "1") : "1";
								var nivelClase = valorSeries != null ? (valorSeries.length != 0 && valorSeries.length > 7 ? valorSeries[7] : "") : "";
		
								insumos = separadorIndicadores;
								insumos = insumos + "[row:" + index + "]";
								insumos = insumos + "[indicadorId:" + indicadorId + "]";
								insumos = insumos + "[serieId:" + serieId + "]";
								insumos = insumos + "[planId:" + planId + "]";
								insumos = insumos + "[serie:" + nombreLeyenda + "]";
								insumos = insumos + "[color:" + convertRGBDecimal(color) + "]";
								insumos = insumos + "[color_hidden:" + color + "]";
								insumos = insumos + "[color_decimal_hidden:" + convertRGBDecimal(color) + "]";
								insumos = insumos + "[visible:" + visible + "]";
								insumos = insumos + "[tipoGrafico:" + tipoGrafico + "]";
								insumos = insumos + "[showOrganizacion:" + showOrganizacion + "]";
								insumos = insumos + "[nivelClase:" + nivelClase + "]";
								
								listaInsumosSerie = listaInsumosSerie + insumos;
							}
						}
					}
				}
				
				document.graficoForm.insumosSerie.value = listaInsumosSerie;
				
				var tabla = document.getElementById('tablaListaSeries');
			
				// Se borra la lista de insumos
				while (tabla.getElementsByTagName("tr").length > 0) 
					tabla.deleteRow(0);
				
				var insumos = document.graficoForm.insumosSerie.value.split('<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>');
				var indicadores = "";
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
						indicadorId = strTemp.substring(0, indice).replace("[indicadorId:", "");
						if (indicadores.indexOf(indicadorId) == -1)
							indicadores = indicadores + indicadorId + ",";
						// serieId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						serieId = strTemp.substring(0, indice).replace("[serieId:", "");
						// planId
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						planId = strTemp.substring(0, indice).replace("[planId:", "");
						// nombreLeyenda
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						nombreLeyenda = strTemp.substring(0, indice).replace("[serie:", ""); 
						// color
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						color = strTemp.substring(0, indice).replace("[color:", "");
						// color_hicen
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						colorHiden = strTemp.substring(0, indice).replace("[color_hidden:", "");
						// color_decimal
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						colorDecimal = strTemp.substring(0, indice).replace("[color_decimal_hidden:", "");
						// visible
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						visible = strTemp.substring(0, indice).replace("[visible:", "");
						if (visible == "true")
							visible = "1";
						else if (visible == "false")
							visible = "0";
						// tipoGrafico
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						if (strTemp.indexOf("showOrganizacion") > 0)
							indice = strTemp.indexOf("][");
						else
							indice = strTemp.length - 1;
						tipoGrafico = strTemp.substring(0, indice).replace("[tipoGrafico:", "");
						// Organizacion
						showOrganizacion = "0";
						if (strTemp.indexOf("showOrganizacion") > 0)
						{
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.indexOf("][");
							showOrganizacion = strTemp.substring(0, indice).replace("[showOrganizacion:", "");
						}
						if (showOrganizacion == "true")
							showOrganizacion = "1";
						else if (showOrganizacion == "false")
							showOrganizacion = "0";
						// nivelClase
						nivelClase = "";
						if (strTemp.indexOf("nivelClase") > 0)
						{
							strTemp = strTemp.substring(indice + 1, strTemp.length);
							indice = strTemp.length - 1;
							nivelClase = strTemp.substring(0, indice).replace("[nivelClase:", "");
						}

						var tbody = tabla.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");

						row.valign = "top";
						row.id = "row_" + indicadorId + "_" + serieId + "_" + planId;
						row.name = "row_" + indicadorId + "_" + serieId + "_" + planId;
						
						var elementoTd1 = "";
						var elementoTd2 = "";
						var elementoTd3 = "";
						var elementoTd4 = "";
						var elementoTd5 = "";
						var elementoTd6 = "";
						<logic:notEqual name="graficoForm" property="bloqueado" value="true">
							elementoTd1 = "<input type='checkbox' name='visible_" + indicadorId + "_" + serieId + "_" + planId + "' id='visible_" + indicadorId +"_" + serieId + "_" + planId + "' " + (visible == "1" ? "checked" : "") + ">";
							elementoTd2 = "<input style='width:285px' size='47' maxlength='100' type='text' id='serie_" + indicadorId + "_" + serieId + "_" + planId + "' name='serie_" + indicadorId + "_" + serieId + "_" + planId + "' value='" + nombreLeyenda + "'>";
	
							elementoTd3 = "<input type='text'";
							elementoTd3 = elementoTd3 + " onclick='javascript:mostrarColores(this, \"color_decimal_hidden_" + indicadorId + "_" + serieId + "_" + planId + "\", \"color_hidden_" + indicadorId + "_" + serieId + "_" + planId + "\");'"; 
							elementoTd3 = elementoTd3 + " name='color_" + indicadorId +"_" + serieId + "_" + planId + "'";
							elementoTd3 = elementoTd3 + " size='3'";
							elementoTd3 = elementoTd3 + " readonly='true'";
							elementoTd3 = elementoTd3 + " style='background-color: " + colorHiden + "'>";
							elementoTd3 = elementoTd3 + "<input type='hidden' value='" + colorHiden + "' id='color_hidden_" + indicadorId +"_" + serieId + "_" + planId + "' name='color_hidden_" + indicadorId +"_" + serieId + "_" + planId + "'>";
							elementoTd3 = elementoTd3 + "<input type='hidden' value='" + colorDecimal + "' id='color_decimal_hidden_" + indicadorId +"_" + serieId + "_" + planId + "' name='color_decimal_hidden_" + indicadorId +"_" + serieId + "_" + planId + "'>";
	
							elementoTd4 = "<select class='cuadroCombinado' id='tipo_" + indicadorId +"_" + serieId + "_" + planId + "' name='tipo_" + indicadorId +"_" + serieId + "_" + planId + "' value='" + tipoGrafico + "' onchange='validarTipo(document.graficoForm.tipo_" + indicadorId +"_" + serieId + "_" + planId + ");'>";
							for (var j = 0; j < tipoGraficos.length; j++) 
							{
								if (tipoGraficos[j][0] == tipoGrafico) 
									elementoTd4 = elementoTd4 + "<option value='" + tipoGraficos[j][0] + "' selected>" + tipoGraficos[j][1] + "</option>";
								else
									elementoTd4 = elementoTd4 + "<option value='" + tipoGraficos[j][0] + "'>" + tipoGraficos[j][1] + "</option>";
							}
							elementoTd4 = elementoTd4 + "</select>";
							elementoTd5 = "<input style='width:85px' type='checkbox' name='showOrganizacion_" + indicadorId + "_" + serieId + "_" + planId + "' id='showOrganizacion_" + indicadorId +"_" + serieId + "_" + planId + "' " + (showOrganizacion == "1" ? "checked" : "") + ">";
							elementoTd6 = "<input size='2' maxlength='1' type='text' id='nivelClase_" + indicadorId + "_" + serieId + "_" + planId + "' name='nivelClase_" + indicadorId + "_" + serieId + "_" + planId + "' value='" + nivelClase + "' onkeyup='javascript:verificarNumero(this, false);'>";
						</logic:notEqual>
						<logic:equal name="graficoForm" property="bloqueado" value="true">
							elementoTd1 = "<input type='checkbox' disabled='<%= Boolean.parseBoolean(bloquearForma) %>' name='visible_" + indicadorId + "_" + serieId + "_" + planId + "' id='visible_" + indicadorId +"_" + serieId + "_" + planId + "' " + (visible == "1" ? "checked" : "") + ">";
							elementoTd2 = "<input style='width:285px' disabled='<%= Boolean.parseBoolean(bloquearForma) %>' size='47' maxlength='100' type='text' id='serie_" + indicadorId + "_" + serieId + "_" + planId + "' name='serie_" + indicadorId + "_" + serieId + "_" + planId + "' value='" + nombreLeyenda + "'>";
	
							elementoTd3 = "<input type='text' disabled='<%= Boolean.parseBoolean(bloquearForma) %>'";
							elementoTd3 = elementoTd3 + " onclick='javascript:mostrarColores(this, \"color_decimal_hidden_" + indicadorId + "_" + serieId + "_" + planId + "\", \"color_hidden_" + indicadorId + "_" + serieId + "_" + planId + "\");'"; 
							elementoTd3 = elementoTd3 + " name='color_" + indicadorId +"_" + serieId + "_" + planId + "'";
							elementoTd3 = elementoTd3 + " size='3'";
							elementoTd3 = elementoTd3 + " readonly='true'";
							elementoTd3 = elementoTd3 + " style='background-color: " + colorHiden + "'>";
							elementoTd3 = elementoTd3 + "<input type='hidden' value='" + colorHiden + "' id='color_hidden_" + indicadorId +"_" + serieId + "_" + planId + "' name='color_hidden_" + indicadorId +"_" + serieId + "_" + planId + "'>";
							elementoTd3 = elementoTd3 + "<input type='hidden' value='" + colorDecimal + "' id='color_decimal_hidden_" + indicadorId +"_" + serieId + "_" + planId + "' name='color_decimal_hidden_" + indicadorId +"_" + serieId + "_" + planId + "'>";
	
							elementoTd4 = "<select disabled='<%= Boolean.parseBoolean(bloquearForma) %>' class='cuadroCombinado' id='tipo_" + indicadorId +"_" + serieId + "_" + planId + "' name='tipo_" + indicadorId +"_" + serieId + "_" + planId + "' value='" + tipoGrafico + "' onchange='validarTipo(document.graficoForm.tipo_" + indicadorId +"_" + serieId + "_" + planId + ");'>";
							for (var j = 0; j < tipoGraficos.length; j++) 
							{
								if (tipoGraficos[j][0] == tipoGrafico) 
									elementoTd4 = elementoTd4 + "<option value='" + tipoGraficos[j][0] + "' selected>" + tipoGraficos[j][1] + "</option>";
								else
									elementoTd4 = elementoTd4 + "<option value='" + tipoGraficos[j][0] + "'>" + tipoGraficos[j][1] + "</option>";
							}
							elementoTd4 = elementoTd4 + "</select>";
							elementoTd5 = "<input style='width:85px' type='checkbox' disabled='<%= Boolean.parseBoolean(bloquearForma) %>' name='showOrganizacion_" + indicadorId + "_" + serieId + "_" + planId + "' id='showOrganizacion_" + indicadorId +"_" + serieId + "_" + planId + "' " + (showOrganizacion == "1" ? "checked" : "") + ">";
							elementoTd6 = "<input disabled='<%= Boolean.parseBoolean(bloquearForma) %>' size='2' maxlength='1' type='text' id='nivelClase_" + indicadorId + "_" + serieId + "_" + planId + "' name='nivelClase_" + indicadorId + "_" + serieId + "_" + planId + "' value='" + nivelClase + "' onkeyup='javascript:verificarNumero(this, false);'>";
						</logic:equal>
						
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");
						var td3 = document.createElement("TD");
						var td4 = document.createElement("TD");
						var td5 = document.createElement("TD");
						var td6 = document.createElement("TD");

						td1.innerHTML = elementoTd1;
						td2.innerHTML = elementoTd2;
						td3.innerHTML = elementoTd3;
						td4.innerHTML = elementoTd4;
						td5.innerHTML = elementoTd5;
						td6.innerHTML = elementoTd6;
						
						row.appendChild(td1);
						row.appendChild(td2);
						row.appendChild(td3);
						row.appendChild(td4);
						row.appendChild(td5);
						row.appendChild(td6);
						tbody.appendChild(row);
					}
				}
				
				document.graficoForm.indicadorInsumoSeleccionadoSerie.value = "";
				if (window.document.graficoForm.titulo.value == "" && typeof(nombreIndicador) != "undefined" && unSoloIndicador)
					window.document.graficoForm.titulo.value = nombreIndicador;
				window.document.graficoForm.graficoNombre.value = window.document.graficoForm.titulo.value;
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
				var tablaSerie = document.getElementById('tablaListaSeries');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				numeroSerieFilas = tablaSerie.getElementsByTagName("tr").length;
				var insumosFormula = document.graficoForm.insumosFormula.value;
				var insumosSerie = document.graficoForm.insumosSerie.value;
				
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
						
						index = insumosSerie.indexOf(']');
						index = insumosSerie.indexOf('[tipoGrafico:');
						insumosSerie = insumosSerie.substring(index + 1, insumosSerie.length);
						index = insumosSerie.indexOf(']');
						// Se busca el último valor de cada indicador seleccionado
						if (insumosSerie.length > (index + 1)) 
							index++;
						insumosSerie = insumosSerie.substring(index + 3, insumosSerie.length);						
					} 
					else if ((posicionBuscada + 1) == numeroFilas) 
					{
						var strBuscada = ']' + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						insumosFormula = insumosFormula.substring(0, index - 1);
						
						index = insumosSerie.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosSerie.substring(index, insumosSerie.length).indexOf(']');
						insumosSerie = insumosSerie.substring(0, index - 1);
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
						
						index = 0;
						for (var i = 0; i < posicionBuscada; i++) 
						{
							index = index + insumosSerie.substring(index, insumosSerie.length).indexOf(strBuscada);
							if (index != -1) 
								index= index + 2;
						}
						str1 = '';
						if (index != -1) 
						{
							str1 = insumosSerie.substring(0, index);
							index2 = index + insumosSerie.substring(index, insumosSerie.length).indexOf(']');
						}
						index = index + insumosSerie.substring(index, insumosSerie.length).indexOf(strBuscada);
						str2 = insumosSerie.substring(index + 2, insumosSerie.length);
						insumosSerie = str1 + str2;						
					}
					document.graficoForm.insumosFormula.value = insumosFormula;
					document.graficoForm.insumosSerie.value = insumosSerie;
					var index = document.graficoForm.insumoSeleccionado.value;
					deleteRowColors(tabla, 
									document.graficoForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
					
					if (tablaSerie.getElementsByTagName("tr").length > 0)
						tablaSerie.deleteRow(index);
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

		  		var graficoLinea3DIn = new Image();
		  		graficoLinea3DIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Linea3DIn.png'/>";
		  		var graficoLinea3DOut = new Image();
		  		graficoLinea3DOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Linea3D.png'/>";

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

		  		var graficoGaugeIn = new Image();
		  		graficoGaugeIn.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/GaugeIn.png'/>";
		  		var graficoGaugeOut = new Image();
		  		graficoGaugeOut.src = "<html:rewrite page='/paginas/strategos/graficos/imagenes/Gauge.png'/>";
		  		
		  		if (document.images) 
		  		{
		  			for (var i=0; i<changeImages.arguments.length; i+=2) 
		  				document[changeImages.arguments[i]].src = eval(changeImages.arguments[i+1] + ".src");
		  		}
		  	} 		  	
		  	
		  	function setTipo(tipo, recall)
		  	{
		  		<logic:notEqual name="graficoForm" property="bloqueado" value="true">
			  		var imageUrl = 'url("<html:rewrite page='/paginas/strategos/graficos/imagenes/Seleccionado.png'/>")';
			  		
			  		document.getElementById("graficoLinea").style.backgroundImage = "";
			  		document.getElementById("graficoColumna").style.backgroundImage = "";
			  		document.getElementById("graficoBarra").style.backgroundImage = "";
			  		document.getElementById("graficoArea").style.backgroundImage = "";
			  		document.getElementById("graficoTorta").style.backgroundImage = "";
			  		document.getElementById("graficoPareto").style.backgroundImage = "";
			  		document.getElementById("graficoCombinado").style.backgroundImage = "";
			  		document.getElementById("graficoCascada").style.backgroundImage = "";
			  		document.getElementById("graficoTorta3D").style.backgroundImage = "";
			  		document.getElementById("graficoBarra3D").style.backgroundImage = "";
			  		document.getElementById("graficoBarraApilada3D").style.backgroundImage = "";
			  		document.getElementById("graficoGauge").style.backgroundImage = "";
			  		document.graficoForm.tipo.value = tipo;
	
			  		var tipografico = document.getElementById("tipoGraficoSeleccionado");
					document.getElementById("trTitleEjez").style.display = "none";
					document.getElementById("trLabelEjez").style.display = "none";
					switch (tipo) 
					{
						case 1:
						case 13:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.lineas" />';
							document.getElementById("graficoLinea").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 2:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />';
							document.getElementById("graficoColumna").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 3:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />';
							document.getElementById("graficoBarra").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 4:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.area" />';
							document.getElementById("graficoArea").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 5:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />';
							document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
							var ul = document.graficoForm.ultimoPeriodo.value;
							if (ul == undefined || ul == "")
								ul = document.graficoForm.anoFinal.value + "-" + document.graficoForm.periodoFinal.value;
							var p = ul.split("-");
						 	document.graficoForm.anoFinal.value = p[0]; 
						 	document.graficoForm.anoInicial.value = document.graficoForm.anoFinal.value; 
						 	document.graficoForm.periodoFinal.value = p[1];
						 	document.graficoForm.periodoInicial.value = document.graficoForm.periodoFinal.value;
							var select = document.getElementById("selectPeriodoInicial");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoInicial.value) - 1);
							select = document.getElementById("selectPeriodoFinal");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoFinal.value) -1);
							setTipoGraficoDefault();
							break;
						case 6:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.pareto" />';
							document.getElementById("graficoPareto").style.backgroundImage = imageUrl;
							document.getElementById("trTitleEjez").style.display = "";
							document.getElementById("trLabelEjez").style.display = "";
							break;
						case 7:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.multipletorta" />';
							document.getElementById("graficoTorta").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 8:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.combinado" />';
							document.getElementById("graficoCombinado").style.backgroundImage = imageUrl;
							break;
						case 9:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.cascada" />';
							document.getElementById("graficoCascada").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 10:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />';
							document.getElementById("graficoTorta3D").style.backgroundImage = imageUrl;
							var ul = document.graficoForm.ultimoPeriodo.value;
							if (ul == undefined || ul == "")
								ul = document.graficoForm.anoFinal.value + "-" + document.graficoForm.periodoFinal.value;
							var p = ul.split("-");
						 	document.graficoForm.anoFinal.value = p[0]; 
						 	document.graficoForm.anoInicial.value = document.graficoForm.anoFinal.value; 
						 	document.graficoForm.periodoFinal.value = p[1];
						 	document.graficoForm.periodoInicial.value = document.graficoForm.periodoFinal.value;
							var select = document.getElementById("selectPeriodoInicial");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoInicial.value) - 1);
							select = document.getElementById("selectPeriodoFinal");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoFinal.value) -1);
							setTipoGraficoDefault();
							break;
						case 11:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />';
							document.getElementById("graficoBarra3D").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 12:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.barraapilada3d" />';
							document.getElementById("graficoBarraApilada3D").style.backgroundImage = imageUrl;
							setTipoGraficoDefault();
							break;
						case 14:
							tipografico.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.tipo.gauge" />';
							document.getElementById("graficoGauge").style.backgroundImage = imageUrl;
							var ul = document.graficoForm.ultimoPeriodo.value;
							if (ul == undefined || ul == "")
								ul = document.graficoForm.anoFinal.value + "-" + document.graficoForm.periodoFinal.value;
							var p = ul.split("-");
						 	document.graficoForm.anoFinal.value = p[0]; 
						 	document.graficoForm.anoInicial.value = document.graficoForm.anoFinal.value; 
						 	document.graficoForm.periodoFinal.value = p[1];
						 	document.graficoForm.periodoInicial.value = document.graficoForm.periodoFinal.value;
							var select = document.getElementById("selectPeriodoInicial");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoInicial.value) - 1);
							select = document.getElementById("selectPeriodoFinal");
					    	select.selectedIndex = (parseInt(document.graficoForm.periodoFinal.value) -1);
							setTipoGraficoDefault();
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

				// llenar series
				var insumos;
				var index = 0;
				var listaInsumosSerie= "";
				// Se borra la lista de insumos
				<logic:iterate name="graficoForm" property="series" id="serie">
					<logic:equal name="serie" property="serieAnoAnterior" value="false">
						if (index != 0)
							listaInsumosSerie = listaInsumosSerie + '<bean:write name="graficoForm" property="separadorIndicadores" ignore="true"/>'; 
						
						var indicadorId = 0;
						var serieId = 0;
						var planId = 0;
						<logic:notEmpty property="indicador" name="serie">
							indicadorId = '<bean:write name="serie" property="indicador.indicadorId" />';
							serieId = '<bean:write name="serie" property="serieIndicador.pk.serieId" />';
							planId = '<bean:write name="serie" property="planId" />';
							
						</logic:notEmpty>
							
						insumos = "[row:" + index + "]";
						insumos = insumos + "[indicadorId:" + indicadorId + "]";
						insumos = insumos + "[serieId:" + serieId + "]";
						insumos = insumos + "[planId:" + planId + "]";
						
						insumos = insumos + "[serie:" + '<bean:write name="serie" property="nombreLeyenda" />'+"]";
						insumos = insumos + "[color:" + '<bean:write name="serie" property="color" />' + "]";
						insumos = insumos + "[color_hidden:" + '<bean:write name="serie" property="color" />' + "]";
						insumos = insumos + "[color_decimal_hidden:" + '<bean:write name="serie" property="colorDecimal"/>' + "]";
						insumos = insumos + "[visible:" + '<bean:write name="serie" property="visualizar" />' + "]";
						insumos = insumos + "[tipoGrafico:" + '<bean:write name="serie" property="tipoSerie" />' + "]";
						insumos = insumos + "[showOrganizacion:" + "true" + "]";
						insumos = insumos + "[nivelClase:" + '<bean:write name="serie" property="nivelClase" />' + "]";
						
						listaInsumosSerie = listaInsumosSerie + insumos;
						index++;
					</logic:equal>
				</logic:iterate>

				// LLenar insumos
				document.graficoForm.insumosSerie.value = listaInsumosSerie;
				
				<logic:empty property="indicador" name="serie">
					document.graficoForm.indicadorInsumoSeleccionadoIds.value = "";
					document.graficoForm.indicadorInsumoSeleccionadoNombres.value = "";
					document.graficoForm.indicadorInsumoSeleccionadoRutasCompletas.value = "";
					document.graficoForm.indicadorInsumoSeleccionadoSeriePlanId.value = "";
					document.graficoForm.insumosSerie.value = "";
					document.graficoForm.insumosFormula.value = "";
				</logic:empty>

				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
				// Se borra la lista de insumos
				if (tablaListaInsumos != null)
				{
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);
					// Se agregan los insumos de los indicadores
					agregarIndicadores();
				}
				
				// Se selecciona el grafico
				setTipo(parseInt(document.graficoForm.tipo.value), false);
				document.graficoForm.respuesta.value = "";
		    }
		    
		    function validarTipo(objeto)
		    {
		    	if (objeto.value != -1)
	    		{
		    		if (document.graficoForm.tipo.value == 6)
		    			setTipo(6);
		    		else
		    			setTipo(8);
	    		}
		    }
		    
			function previo() 
			{        
	           window.location.href='<html:rewrite action="/graficos/grafico"/>?vistaId=<bean:write name="graficoForm" property="vistaId" />&paginaId=<bean:write name="graficoForm" property="paginaId" />&objetoId=<bean:write name="graficoForm" property="previaCeldaId" />' + "&source=" + document.graficoForm.source.value;
	        }
	
	        function siguiente() 
	        {        
	           window.location.href='<html:rewrite action="/graficos/grafico"/>?vistaId=<bean:write name="graficoForm" property="vistaId" />&paginaId=<bean:write name="graficoForm" property="paginaId" />&objetoId=<bean:write name="graficoForm" property="siguienteCeldaId" />' + "&source=" + document.graficoForm.source.value;
	        }		
	        
	        function reiniciar()
	        {
				var respuesta = confirm ('<vgcutil:message key="jsp.asistente.grafico.alerta.reiniciar" />');
				if (respuesta)
				{
					var parametros = 'onFuncion=onUndo';
					parametros = parametros + '&source=' + document.graficoForm.source.value;
					if (document.graficoForm.objetosIds.value != null && document.graficoForm.objetosIds.value != "")
						parametros = parametros + '&objetoId=' + document.graficoForm.objetosIds.value;
					else
						parametros = parametros + '&objetoId=' + document.graficoForm.objetoId.value;
					parametros = parametros + '&claseId=' + document.graficoForm.claseId.value;
					parametros = parametros + '&graficoUndoId=' + document.graficoForm.id.value;
					parametros = parametros + '&planId=' + document.graficoForm.planId.value;
					parametros = parametros + '&vistaId=<bean:write name="graficoForm" property="vistaId" />';
					parametros = parametros + '&paginaId=<bean:write name="graficoForm" property="paginaId" />';
		        	
		        	window.location.href='<html:rewrite action="/graficos/grafico"/>?' + parametros;
				}
	        }
		    
	    	function verDupontIndicador() 
	    	{
				var duppont = new Duppont();
				duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
				duppont.ShowForm(true, document.graficoForm.indicadorId.value, 2, document.graficoForm.planId.value);
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
				if (hasValidate && !validar(false))
					return;
				
				if (_calendario != null)
					_calendario.close();
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
				if (document.graficoForm.showImage.value == "true")
				{
					_name = document.graficoForm.titulo.value;
					_subtitle = '';
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
					var tipos = document.graficoForm.serieTipo.value.split(_separadorSeries);
					var rangoAlertas = document.graficoForm.rangoAlertas.value.split(_separadorSeries);
					if (document.graficoForm.tipo.value == 5 || document.graficoForm.tipo.value == 10)
					{
						_showLeyenda = false;
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
					else if (document.graficoForm.tipo.value == 14)
					{
						_plotBands = [];
						if (document.graficoForm.isAscendente.value == "true")
						{
							_red = rangoAlertas[0].split(",");
							_yellow = rangoAlertas[1].split(",");
							_green = rangoAlertas[2].split(",");

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
							_green = rangoAlertas[0].split(",");
							_yellow = rangoAlertas[1].split(",");
							_red = rangoAlertas[2].split(",");
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
					else if (document.graficoForm.tipo.value == 8)
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
					else if (document.graficoForm.tipo.value == 6)
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
					
					getGrafico(document.graficoForm.tipo.value);
				}
			}
			
			function gestionarAnexos(objetoId, objetoKey) 
			{
				var explicacion = new Explicacion();
				explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
				explicacion.ShowList(true, objetoId, objetoKey, 0);
			}
	        
		</script>

		<%-- Tooltip --%>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/screen.css" />" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/js/css/jquery-ui.css" />" />
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
				$("#tabs").tabs();
			});
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/graficos/grafico" styleClass="formaHtmlCompleta">

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
			<html:hidden property="periodoInicial" />
			<html:hidden property="periodoFinal" />
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
			<html:hidden property="indicadorId" />
			<html:hidden property="ejeX" />
			<html:hidden property="serieName" />
			<html:hidden property="serieData" />
			<html:hidden property="serieColor" />
			<html:hidden property="serieTipo" />
			<html:hidden property="rangoAlertas" />
			<html:hidden property="isAscendente" />
			<html:hidden property="showImage" />
			<html:hidden property="planId" />
			<html:hidden property="url" />
			<html:hidden property="ultimoPeriodo" />
			<html:hidden property="graficoNombre" />
			<html:hidden property="portafolioId" />
			<html:hidden property="esReporteGrafico" />
			<html:hidden property="reporteId" />
			
			
			<bean:define id="listaTiposSerie" toScope="page" name="graficoForm" property="tiposSerie" />
			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
				..::<vgcutil:message key="jsp.graficoindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<logic:notEqual name="graficoForm" property="esReporteGrafico" value="true">
				
					<%-- Botón Regresar --%>
					<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
					</vgcinterfaz:contenedorFormaBotonRegresar>
				
				</logic:notEqual>
				

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100Collapse">
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.graficoindicador.organizacion" /></b></td>
							<td><bean:write name="organizacion" scope="session" property="nombre" />&nbsp;</td>
						</tr>
						<logic:empty property="portafolioId" name="graficoForm">
							<logic:notEmpty name="graficoForm" property="paginaId">
								<logic:notEqual name="graficoForm" property="paginaId" value="0">
									<%-- Vista --%>
									<tr class="barraFiltrosForma">
										<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.vista" /></b></td>
										<logic:notEmpty name="graficoForm" property="vista">
											<td><bean:write name="graficoForm" property="vista.nombre" />&nbsp;</td>
										</logic:notEmpty>
										<logic:empty name="graficoForm" property="vista">
											<logic:notEmpty name="graficoForm" property="portafolio">
												<td><bean:write name="graficoForm" property="portafolio.nombre" />&nbsp;</td>
											</logic:notEmpty>
										</logic:empty>							
									</tr>
		
									<%-- Página --%>
									<tr class="barraFiltrosForma">
										<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.pagina.nro" /></b></td>
										<td><bean:write name="graficoForm" property="pagina.numero" />&nbsp;&nbsp;|&nbsp;&nbsp;<bean:write name="graficoForm" property="pagina.descripcion" />&nbsp;</td>
									</tr>
		
									<%-- Celda --%>
									<tr class="barraFiltrosForma">
										<td align="right" width="20px"><b><vgcutil:message key="jsp.grafico.celda.celda" /></b></td>
										<td><bean:write name="graficoForm" property="celda.indice" />&nbsp;&nbsp;|&nbsp;&nbsp;<bean:write name="graficoForm" property="titulo" />&nbsp;</td>
									</tr>
								</logic:notEqual>
							</logic:notEmpty>
						</logic:empty>
						<logic:notEmpty name="graficoForm" property="portafolio">
							<logic:notEmpty property="portafolioId" name="graficoForm">
								<tr class="barraFiltrosForma">
									<td width="20px"><b><vgcutil:message key="jsp.vista.portafolio" />: </b></td>
									<td><bean:write name="graficoForm" property="portafolio.nombre" />&nbsp;</td>
								</tr>
							</logic:notEmpty>
						</logic:notEmpty>
					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraGraficoIndicador">
						<logic:empty property="portafolioId" name="graficoForm">
							<logic:notEmpty name="graficoForm" property="paginaId">
								<logic:notEqual name="graficoForm" property="paginaId" value="0">
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
								</logic:notEqual>
							</logic:notEmpty>
						</logic:empty>
						<logic:notEqual name="graficoForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar"
								pathImagenes="/componentes/barraHerramientas/" nombre="refrescar"
								onclick="javascript:configuracion();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.ver.configuracion.grafico" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
														
							<vgcinterfaz:barraHerramientasSeparador />
							<logic:empty property="portafolioId" name="graficoForm">
								<logic:empty property="objetosIds" name="graficoForm">
									<vgcinterfaz:barraHerramientasBoton nombreImagen="undo"
										pathImagenes="/componentes/barraHerramientas/" nombre="undo"
										onclick="javascript:reiniciar();">
										<vgcinterfaz:barraHerramientasBotonTitulo>
											<vgcutil:message key="boton.undo.configuracion" />
										</vgcinterfaz:barraHerramientasBotonTitulo>
									</vgcinterfaz:barraHerramientasBoton>
									<vgcinterfaz:barraHerramientasSeparador />
								</logic:empty>
							</logic:empty>
							
							
								<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar"
								pathImagenes="/componentes/barraHerramientas/" nombre="guardar"
								onclick="javascript:save();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="boton.guardar.configuracion" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
									</vgcinterfaz:barraHerramientasBoton>
								<vgcinterfaz:barraHerramientasSeparador />
			
							
						</logic:notEqual>
						
						<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir"
							pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
							onclick="javascript:imprimir();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.imprimir.configuracion" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="graficoForm" property="showDuppont" value="true">
							<logic:notEmpty name="graficoForm" property="indicadorId">
								<vgcinterfaz:barraHerramientasSeparador />
								<vgcinterfaz:barraHerramientasBoton nombreImagen="dupont"
									pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="dupont"
									onclick="javascript:verDupontIndicador();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.arboldupont" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
							</logic:notEmpty>
						</logic:equal>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="tabla" pathImagenes="/componentes/barraHerramientas/" nombre="tabla" onclick="javascript:showTable();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.ver.tabla" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:notEmpty name="graficoForm" property="celda" >
							<logic:notEmpty name="graficoForm" property="indicadores" >
								<vgcinterfaz:barraHerramientasSeparador />
								<td width="30px" id="tdBarraGraficoAnexos">
									<ul id="nav" class="dropdown dropdown-horizontal">
										<li class="dir"><vgcutil:message key="jsp.gestionarindicadores.barraherramientas.explicaciones" />
											<ul>
												<li><a href="javascript:gestionarAnexos('<bean:write name="graficoForm" property="celda.celdaId" />', 'Celda');"><vgcutil:message key="jsp.grafico.celda.celda" /></a></li>
												<li class="dir"><vgcutil:message key="jsp.reportes.plan.explicacion.objeto" />
													<ul>
														<logic:iterate name="graficoForm" property="indicadores" id="indicador">
															<li><a href="javascript:gestionarAnexos('<bean:write name="indicador" property="indicadorId" />', 'Indicador');"><bean:write name="indicador" property="nombre" /></a></li>
														</logic:iterate>
													</ul>
												</li>											
											</ul>
										</li>
									</ul>
								</td>
							</logic:notEmpty>
							<logic:empty name="graficoForm" property="indicadores" >
								<logic:notEmpty name="graficoForm" property="indicadorId">
									<logic:notEqual name="graficoForm" property="indicadorId" value="0">
										<vgcinterfaz:barraHerramientasSeparador />
										<td width="30px" id="tdBarraGraficoAnexos">
											<img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/barraHerramientas/explicaciones_1.gif'/>" name="imgBarraGraficoAnexos" id="imgBarraGraficoAnexos" border="0" width="25px" height="25px" onclick="javascript:gestionarAnexos('<bean:write name="graficoForm" property="indicadorId" />', 'Indicador');" title='<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.explicaciones" />' onmouseout="barraHerramientas_swapImgRestore()" onmouseover="barraHerramientas_swapImage('imgBarraGraficoAnexos', '', '/Strategos/paginas/strategos/indicadores/imagenes/barraHerramientas/explicaciones_2.gif', 1)">
										</td>
									</logic:notEqual>
								</logic:notEmpty>
							</logic:empty>
						</logic:notEmpty>
						<logic:empty name="graficoForm" property="celda" >
							<logic:notEmpty name="graficoForm" property="indicadorId">
								<logic:notEqual name="graficoForm" property="indicadorId" value="0">
									<vgcinterfaz:barraHerramientasSeparador />
									<td width="30px" id="tdBarraGraficoAnexos">
										<img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/barraHerramientas/explicaciones_1.gif'/>" name="imgBarraGraficoAnexos" id="imgBarraGraficoAnexos" border="0" width="25px" height="25px" onclick="javascript:gestionarAnexos('<bean:write name="graficoForm" property="indicadorId" />', 'Indicador');" title='<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.explicaciones" />' onmouseout="barraHerramientas_swapImgRestore()" onmouseover="barraHerramientas_swapImage('imgBarraGraficoAnexos', '', '/Strategos/paginas/strategos/indicadores/imagenes/barraHerramientas/explicaciones_2.gif', 1)">
									</td>
								</logic:notEqual>
							</logic:notEmpty>
						</logic:empty>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>
				
				<jsp:include flush="true" page="/paginas/strategos/graficos/configuracion.jsp"></jsp:include>
				<jsp:include flush="true" page="/paginas/strategos/graficos/tabla.jsp"></jsp:include>

				<%-- Cuerpo --%>
				<table class="tablaSpacing0Padding0Width100">
					<tr align="center">
						<%-- Se obtienen las variables de Form Bean --%>
						<td>
							<table class="bordeFichaDatos">
								<logic:equal name="graficoForm" property="showPath" value="true">
									<tr align="center">
										<td align="center" style="font-family:Verdana; font-size:19; color:#000000;border-spacing: 100px;">
											<b>
												<logic:notEmpty name="graficoForm" property="ubicacionOrganizacion">
													<bean:write name="graficoForm" property="ubicacionOrganizacion" />
												</logic:notEmpty>
												<logic:notEmpty name="graficoForm" property="ubicacionClase">
													<br><bean:write name="graficoForm" property="ubicacionClase" />
												</logic:notEmpty>
											</b>
										</td>
									</tr>
								</logic:equal>
								<tr align="center">
									<td align="center">
										<logic:equal name="graficoForm" property="showImage" value="true">
											<div id="div_grafico" style="height: 300px; width : 370px; margin: 0 auto"></div>
											<div id="div_grafico_print"></div>
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
										</logic:equal>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			init();
			preparedGraph();
			
			var	$background = $("#headerCausa");  
			$background.css('background', background[1]);
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
