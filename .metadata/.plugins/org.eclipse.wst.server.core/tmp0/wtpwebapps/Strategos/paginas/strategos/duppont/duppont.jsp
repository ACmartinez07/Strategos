<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (30/05/2012) --%>
<tiles:insert definition="doc.modalLayoutHTML5" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.dupontindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
		<bean:define toScope="page" id="estiloInferior" value=""></bean:define>
		<bean:define toScope="page" id="estiloBarraSuperior" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				estiloSuperior = estilos.getEstiloSuperior();
				estiloInferior = estilos.getEstiloInferior();
			}
			else
				hayEstilo = "false";
			
			estiloBarraSuperior = new com.visiongc.framework.web.struts.taglib.interfaz.ContenedorFormaTag().checkEstilo();
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
			<bean:define toScope="page" id="estiloInferior" value="<%=estiloInferior%>"></bean:define>
		</logic:equal>
	
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/dupont/css/duppont.css" />">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/window-modal/css/window-modal.css" />">

		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/dupont/d3.v3.min.js" />" charset="utf-8"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/dupont/duppontTree.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/highcharts.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/paginas/comunes/jQuery/grafico/graphic.js" />"></script>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript">
			var _width = _myWidth;
			var _height = _myHeight;
			var _separadorSeries = '<bean:write name="duppontForm" property="separadorSeries" ignore="true"/>';
			var _showGraph = false;
			var _node;
			var desdeMes = 1;
			var hastaMes = 12;
			var desdeAno = '<bean:write name="duppontForm" property="anoInicial" ignore="true"/>';
			var hastaAno = '<bean:write name="duppontForm" property="anoFinal" ignore="true"/>';
			var errMsRango = '<vgcutil:message key="jsp.reporteindicador.editor.alerta.invalido" />';
			var _nivel = null; 
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			var indicadores = null;
			_alertaInalterada = '<html:rewrite page="/paginas/strategos/indicadores/imagenes/alertaInalterada16.gif"/>';
			
			function Indicador()
			{
				this.Id = null;
				this.Desplegado = false;
			}  

			function refrescar(expandirId, desplegado) 
			{
				if (validar())
				{
					if (typeof (expandirId) != "undefined")
					{
						if (typeof (desplegado) == "undefined")
							desplegado = false;
						
						for (var index = 0; index < indicadores.length; index++) 
						{
							if (indicadores[index].Id == expandirId)
							{
								indicadores[index].Desplegado = desplegado;
								break;
							}
						}
					}
					_nivel = document.duppontForm.numerodeNiveles.value;
					
					var xml = 'indicadorId=' + document.duppontForm.indicadorId.value;
					xml = xml + "&planId=" + document.duppontForm.planId.value;
					xml = xml + "&nivel=" + _nivel;
					xml = xml + "&indicadores=" + getIndicadores();
					
					var forma = document.duppontForm;			   
				   	forma.action = '<html:rewrite action="/graficos/dupontIndicador"/>?' + xml;
				   	forma.submit();
				}
			}
			
			function getIndicadores()
			{
				var xml = "";
				var isFirst = true;
				for (var index = 0; index < indicadores.length; index++)
				{
					if (!isFirst)
						xml = xml + "-";
					xml = xml + indicadores[index].Id + ",";
					xml = xml + indicadores[index].Desplegado;
					isFirst = false;
				}
				
				return xml;
			}
			
			function guardar()
			{
				if (validar())
				{
					_nivel = document.duppontForm.numerodeNiveles.value;
					
					var forma = document.duppontForm;			   
				   	forma.action = '<html:rewrite action="/graficos/dupontIndicador"/>?funcion=salvar&indicadorId=' + document.duppontForm.indicadorId.value + "&planId=" + document.duppontForm.planId.value + "&nivel=" + _nivel;
				   	forma.submit();
				}
			}
			
			function validar()
			{
				if (document.duppontForm.frecuencia.value == 0)
		 		{
			 		document.duppontForm.fechaHasta.value = document.duppontForm.fechaDesde.value;

			 		if (!fechaValida(document.duppontForm.fechaDesde))
		 			{
			 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechadesde.invalido" /> ');
			 			return false;
		 			}

			 		if (!fechaValida(document.duppontForm.fechaHasta))
		 			{
			 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechahasta.invalido" /> ');
			 			return false;
		 			}
		 		}
				
			 	if (document.duppontForm.frecuencia.value == 0)
				{
					var fecha1 = document.duppontForm.fechaDesde.value.split("/");
					var fecha2 = document.duppontForm.fechaHasta.value.split("/");

					document.duppontForm.anoInicial.value = parseInt(fecha1[2]);
					document.duppontForm.anoFinal.value = parseInt(fecha2[2]);

					document.duppontForm.periodoInicial.value = getPeriodoDeFechaDiaria(document.duppontForm.fechaDesde.value);
					document.duppontForm.periodoFinal.value = getPeriodoDeFechaDiaria(document.duppontForm.fechaHasta.value);
				}

			 	desde = parseInt(document.duppontForm.anoInicial.value + "" + (document.duppontForm.periodoInicial.value.length == 1 ? "00" : (document.duppontForm.periodoInicial.value.length == 2 ? "0" : "")) + document.duppontForm.periodoInicial.value);
				hasta = parseInt(document.duppontForm.anoFinal.value + "" + (document.duppontForm.periodoFinal.value.length == 1 ? "00" : (document.duppontForm.periodoFinal.value.length == 2 ? "0" : "")) + document.duppontForm.periodoFinal.value);
				
				if (hasta<desde) 
				{
					if (document.duppontForm.frecuencia.value == 0)
						alert('<vgcutil:message key="jsp.reporteindicador.editor.alerta.rango.fechas.invalido" /> ');
					else
						alert(errMsRango);
					return false;
				} 
				
				return true;
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

			    if (document.duppontForm.periodoInicial.value != "")
			    	select.selectedIndex = document.duppontForm.periodoInicial.value -1;
			    else
			    	select.selectedIndex = 0;
			    
				select = document.getElementById("selectPeriodoFinal");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);
			    
			    if (document.duppontForm.periodoFinal.value != "")
			    	select.selectedIndex = document.duppontForm.periodoFinal.value -1;
			    else
			    	select.selectedIndex = select.options.length-1;
			    
			    hastaMes = numeroPeriodoMaximo(frecuencia);
			    
			    if (limpiar)
		    	{
					document.duppontForm.periodoInicial.value = document.getElementById("selectPeriodoInicial").value;
					document.duppontForm.periodoFinal.value = document.getElementById("selectPeriodoFinal").value;
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
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.duppontForm.frecuencia.value == "0" ? "00" : "0") : (desdeMesObj.value.length == 2 ? "0" : "")) + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.duppontForm.frecuencia.value == "0" ? "00" : "0") : (hastaMesObj.value.length == 2 ? "0" : "")) + hastaMesObj.value);

				desdeAno = desdeAnoObj.value;
				hastaAno = hastaAnoObj.value;
				desdeMes = desdeMesObj.value;
				hastaMes = hastaMesObj.value;
				
				document.duppontForm.periodoInicial.value = desdeMesObj.value;
				document.duppontForm.periodoFinal.value = hastaMesObj.value;
			}
			
			function eventoCambioFrecuencia(limpiar)
			{
				if (limpiar == undefined)
					limpiar = true;

				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				if (limpiar)
				{
					document.duppontForm.periodoInicial.value = "";
					document.duppontForm.periodoFinal.value = "";
		  		}
	
				switch (document.duppontForm.frecuencia.value) 
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
				
				agregarPeriodos(document.duppontForm.frecuencia.value, limpiar);
			}
			
			function getTooltipText(node)
			{
				var label = '<b>' + '<vgcutil:message key="jsp.graficoindicador.indicador" />' + ': </b>' + node.name;
				label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.unidad" />' + ': </b>' + ((node.unidad.length > 40) ? (node.unidad.substr(0, 40) + '...') : node.unidad);
				label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.organizacion" />' + ': </b>' + ((node.organizacion.length > 30) ? (node.organizacion.substr(0, 30) + '...') : node.organizacion);
				label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.claseindicador" />' + ': </b>' + ((node.clase.length > 40) ? (node.clase.substr(0, 40) + '...') : node.clase);
				var height = 80;
				if (node.insumo.length > 0)
				{
					label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.insumo.nombre" />' + ': </b>' + node.insumo;
					height = height + 15;
				}
				if (node.plan.length > 0)
				{
					label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.plan" />' + ': </b>' + ((node.plan.length > 40) ? (node.plan.substr(0, 40) + '...') : node.plan);
					height = height + 15;
				}
				if (node.pp.length > 0 && node.pp != "null")
				{
					label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.porcentaje.logro.parcial" />' + ': </b>' + node.pp + " %";
					height = height + 15;
				}
				if (node.pa.length > 0 && node.pp != "null")
				{
					label = label + '<br/><b>' + '<vgcutil:message key="jsp.graficoindicador.porcentaje.logro.anual" />' + ': </b>' + node.pa + " %";
					height = height + 15;
				}

				$('.tooltipHelper').css('height', (height + 'px'));
				
				return label; 
			}
			
			function closeModal()
			{
				if (validar())
					window.location.href = "#close";
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
			
			function cerrar()
			{
				javascript:irAtras(2);
			}
			
		</script>
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/graficos/dupontIndicador" styleClass="formaHtml">
			
			<input type="hidden" name="indicadorId">
			
			<html:hidden property="frecuencia" />
			<html:hidden property="fechaDesde" />
			<html:hidden property="fechaHasta" />
			<html:hidden property="periodoInicial" />
			<html:hidden property="periodoFinal" />
			<html:hidden property="planId" />
			<html:hidden property="source" />

			<div id="modal1" class="modalmask">
				<div class="modalbox movedown">
					<table cellpadding="0" cellspacing="0" width="320px">
						<tr height="30px">
							<td colspan="2">
								<a href="javascript:closeModal();" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a>
							</td>
						</tr>
						<tr class="barraFiltrosForma" style="background-color:#fff;">
							<td colspan="2"><vgcutil:message key="jsp.asistente.grafico.rango" /></td>
						</tr>
						<tr class="barraFiltrosForma" style="background-color:#fff;">
							<td width="50%"><vgcutil:message key="jsp.asistente.grafico.inicial" /></td>
							<td width="50%"><vgcutil:message key="jsp.asistente.grafico.final" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="50%">
								<table class="tabla contenedorBotonesSeleccion">
									<tr id="trAnoInicial">
										<td><vgcutil:message key="jsp.asistente.grafico.ano" /></td>
										<td>
											<bean:define id="anoCalculoInicial" toScope="page">
											<bean:write name="duppontForm" property="anoInicial" /></bean:define>
											<html:select 
												property="anoInicial" 
												value="<%=anoCalculoInicial%>"
												onchange="validarRango(document.duppontForm.anoInicial, document.duppontForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
											<select 
												id="selectPeriodoInicial"
												onchange="validarRango(document.duppontForm.anoInicial, document.duppontForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
												class="cuadroTexto">
											</select>
										</td>
									</tr>
									<tr id="trPeriodoInicialDate">
										<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
										<td>
											<html:text property="fechaDesde" size="10" maxlength="10" styleClass="cuadroTexto" />
											<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
										</td>
									</tr>
								</table>
							</td>
							<td width="50%" id="tdHastaPeriodo">
								<table class="tabla contenedorBotonesSeleccion">
									<tr id="trAnoFinal">
										<td><vgcutil:message key="jsp.asistente.grafico.ano" /></td>
										<td>
											<bean:define id="anoCalculoFinal" toScope="page"><bean:write name="duppontForm" property="anoFinal" /></bean:define>
											<html:select property="anoFinal"
												value="<%=anoCalculoFinal%>"
												onchange="validarRango(document.duppontForm.anoInicial, document.duppontForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
											<select 
												id="selectPeriodoFinal" 
												onchange="validarRango(document.duppontForm.anoInicial, document.duppontForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
												class="cuadroTexto">
											</select>
										</td>
									</tr>
									<tr id="trPeriodoFinalDate">
										<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
										<td>
											<html:text property="fechaHasta" size="10" maxlength="10" styleClass="cuadroTexto" />
											<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<table class="contenedorForma" style="border-spacing:2px; border-collapse: separate; padding: 2px; width: 100%; height: 100%;">
				<tr>
				    <td <%=estiloBarraSuperior%> >..:: <vgcutil:message key="jsp.dupontindicador.titulo" /></td>
				    <td <%=estiloBarraSuperior%> align="right" width="50%">
				    	<img src="/Strategos/componentes/formulario/regresar.gif" border="0" width="10" height="10" title="<vgcutil:message key="boton.regresar" />">&nbsp;<a onMouseOver="this.className='mouseEncimaBarraSuperiorForma'" onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:cerrar()" class="mouseFueraBarraSuperiorForma" <%=estiloBarraSuperior%>><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    </td>
			  	</tr>
			    <tr>
					<td colspan="2" height="20px" >
						<table width="100%" cellpadding="3" cellspacing="0">
							<tr class="barraFiltrosForma">
								<td width="20px"><b><bean:message key="jsp.graficoindicador.organizacion" /></b></td>
								<td><bean:write name="duppontForm" property="indicador.organizacion.nombre" /></td>
							</tr>
							<tr class="barraFiltrosForma">
								<td width="20px"><b><bean:message key="jsp.graficoindicador.claseindicador" /></b></td>
								<td><bean:write name="duppontForm" property="indicador.clase.nombre" /></td>
							</tr>
							<tr class="barraFiltrosForma">
								<td width="20px"><b><bean:message key="jsp.graficoindicador.indicador" /></b></td>
								<td><bean:write name="duppontForm" property="indicador.nombre" /></td>
							</tr>
						</table>
						
						<script language="Javascript" src="/Strategos/componentes/barraHerramientas/barraHerramientas.js"></script>
						<table width="100%" cellpadding="3" cellspacing="0"  >
							<tr class="barraFiltrosForma">
								<td colspan="2" width="100%">
									<table id="barraDupont" width="100%" cellpadding="3" cellspacing="0"  >
										<tr class="barraFiltrosForma">
											<td width="30px" id="barraDupontBotonSalvar">
												<img src="/Strategos/componentes/barraHerramientas/guardar_1.gif" name="barraHerramientasbarraDupontImagenBotonguardar" id="barraHerramientasbarraDupontImagenBotonguardar" border="0" width="25px" height="25px" onclick="javascript:guardar();" title="<vgcutil:message key="boton.guardar.configuracion" />" onmouseout="barraHerramientas_swapImgRestore()" onmouseover="barraHerramientas_swapImage('barraHerramientasbarraDupontImagenBotonguardar', '', '/Strategos/componentes/barraHerramientas/guardar_2.gif', 1)">
											</td>
											<td><img src="/Strategos/componentes/barraHerramientas/separador.gif" border="0" width="1px" height="25px"></td>
											<td width="30px" id="barraDupontBotonRefrescar">
												<img src="/Strategos/componentes/barraHerramientas/refrescar_1.gif" name="barraHerramientasbarraDupontImagenBotonrefrescar" id="barraHerramientasbarraDupontImagenBotonrefrescar" border="0" width="25px" height="25px" onclick="javascript:refrescar();" title="<vgcutil:message key="menu.ver.refrescar" />" onmouseout="barraHerramientas_swapImgRestore()" onmouseover="barraHerramientas_swapImage('barraHerramientasbarraDupontImagenBotonrefrescar', '', '/Strategos/componentes/barraHerramientas/refrescar_2.gif', 1)">
											</td>
											<td><img src="/Strategos/componentes/barraHerramientas/separador.gif" border="0" width="1px" height="25px"></td>
											<td width="30px" id="barraDupontBotonConfiguracion">
												<a href="#modal1"><img src="/Strategos/componentes/barraHerramientas/configuracion_1.gif" name="barraHerramientasbarraDupontImagenBotonrefrescar" id="barraHerramientasbarraDupontImagenBotonrefrescar" border="0" width="25px" height="25px" title="<vgcutil:message key="menu.ver.configuracion.periodo" />" onmouseout="barraHerramientas_swapImgRestore()" onmouseover="barraHerramientas_swapImage('barraHerramientasbarraDupontImagenBotonrefrescar', '', '/Strategos/componentes/barraHerramientas/configuracion_2.gif', 1)"></a>
											</td>
											<td><img src="/Strategos/componentes/barraHerramientas/separador.gif" border="0" width="1px" height="25px"></td>
											<td width="30px" id="barraDupontBotonConfiguracion" title="<vgcutil:message key="jsp.dupontindicador.niveles" />">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td valign="middle" align="left">
															<html:text property="numerodeNiveles" size="1" readonly="true" styleClass="cuadroTexto" />
														</td>
														<td valign="middle">
															<img id="botonNumerodeNiveles" name="botonNumerodeNiveles" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
														</td>
													</tr>
												</table>
												<map name="MapControlUpDown1" id="MapControlUpDown1">
													<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonNumerodeNiveles')" onmouseout="normalAction('botonNumerodeNiveles')" onmousedown="onmousedownMascaraNumeroUp('numerodeNiveles')" onmouseup="finalizarConteoContinuo()" />
													<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonNumerodeNiveles')" onmouseout="normalAction('botonNumerodeNiveles')" onmousedown="onmousedownMascaraNumeroDown('numerodeNiveles')" onmouseup="finalizarConteoContinuo()" />
												</map>
											</td>

											<td><img src="/Strategos/componentes/barraHerramientas/separador.gif" border="0" width="1px" height="25px"></td>
											<td width="30px" id="barraDupontBotonConfiguracion" title="<vgcutil:message key="jsp.dupontindicador.tamano.letra.nodo" />">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td valign="middle" align="left">
															<html:text property="tamanoLetra" size="1" readonly="true" styleClass="cuadroTexto" />
														</td>
														<td valign="middle">
															<img id="botonTamanoLetra" name="botonTamanoLetra" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" />
														</td>
													</tr>
												</table>
												<map name="MapControlUpDown2" id="MapControlUpDown2">
													<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonTamanoLetra')" onmouseout="normalAction('botonTamanoLetra')" onmousedown="onmousedownMascaraNumeroUp('tamanoLetra', 5, 14)" onmouseup="finalizarConteoContinuo()" />
													<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonTamanoLetra')" onmouseout="normalAction('botonTamanoLetra')" onmousedown="onmousedownMascaraNumeroDown('tamanoLetra', 5, 14)" onmouseup="finalizarConteoContinuo()" />
												</map>
											</td>
											
											<td width="100%" id="barraDupontBotonDummy">
												&nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
			        </td>
      			</tr>
      			<tr>
        			<td colspan="2">
        				<div class="contenedorForma">
							<table cellpadding="3" cellspacing="0" width="100%" height="100%">
								<tr valign="top">
									<td colspan="2" align="left" valign="top">
	
										<%-- Cuerpo --%>
										<div id="div-body" class="div-body" onscroll="hideGraph()"></div>
										<div id="tooltip-node" class="tooltip" style="display: none;">
											<span class="close" title="<vgcutil:message key="boton.cancelar.alt" />"></span>
											<span id="spanMaximize" class="maximize" title="<vgcutil:message key="boton.maximizar.alt" />"></span>
											<div id="container-tooltip" style="height: 300px; width : 370px; margin: 0 auto"></div>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			
		</html:form>
		<script type="text/javascript">
			document.duppontForm.indicadorId.value = '<bean:write name="duppontForm" property="indicador.indicadorId" />'; 
			var objeto = document.getElementById('div-body');
			if (objeto != null)
			{
				objeto.style.width = (parseInt(_width) - 35) + "px";
				objeto.style.height = (parseInt(_height) - 195) + "px";
			}
		</script>
		<script type="text/javascript">
			_typeGraphic = 'line';
			_containerGraphic = 'container-tooltip';
			_containerNodes = '#div-body';
			_ejeX = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
			_ejeYTitle = 'Temperature (C)';
			var _fontSizeGraph = '8px';
			var _fontSizeNode = document.duppontForm.tamanoLetra.value;
			var _graphShowed = false;
			_serie = [
				{
					name: 'Tokyo',
					data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
				}, 
				{
					name: 'New York',
					data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
				}, 
				{
					name: 'Berlin',
					data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
				}, 
				{
					name: 'London',
					data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
				}];
			
			var arbol = '<bean:write name="duppontForm" property="arbol" />';
			var treeData = 
			[
				JSON.parse(arbol.replace(new RegExp("&quot;", "gi"), "\""))
			];
			
			// ************** Generate the tree diagram	 *****************
			var objeto = document.getElementById('div-body');
			if (objeto != null)
			{
				_width = objeto.style.width.replace("px", "");
				_height = objeto.style.height.replace("px", "");
			}
			
			var margin = {top: 5, right: 120, bottom: 30, left: 170};
			_width = _width -(margin.right + margin.left);
			_height = _height -(margin.top + margin.bottom);
				
			var i = 0;
			var	duration = 750;
			var	root;
			
			var tree = d3.layout.tree().size([(_height + 90), (_width + 20)]);
	
			var diagonal = d3.svg.diagonal().projection(function(d) { return [d.y, d.x]; });
	
			var svg = d3.select(_containerNodes).append("svg")
				.attr("width", _width + margin.right + margin.left + 200)
				.attr("height", _height + margin.top + margin.bottom + 100)
				.append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
			root = treeData[0];
			root.x0 = _height / 2;
			root.y0 = 0;
			  
			update(root);
	
			d3.select(self.frameElement).style("height", "500px");
		</script>
		<script type="text/javascript">
			eventoCambioFrecuencia(false);
			_nivel = parseInt(document.duppontForm.numerodeNiveles.value);
		</script>
	</tiles:put>
</tiles:insert>
