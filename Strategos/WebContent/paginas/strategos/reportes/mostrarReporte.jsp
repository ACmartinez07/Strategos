<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.mostrarvistadatos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">			
			
			var desdeMes = 1;
			var hastaMes = 12;
			var desdeAno = '<bean:write name="reporteForm" property="anoInicial" ignore="true"/>';
			var hastaAno = '<bean:write name="reporteForm" property="anoFinal" ignore="true"/>';
			var errMsRango = '<vgcutil:message key="jsp.reporteindicador.editor.alerta.invalido" />';
	
			function cancelar() 
			{
				 javascript:irAtras(1);
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
			
			function refrescar() 
			{
				if (!validar())
					return;

				window.document.reporteForm.action = '<html:rewrite action="/reportes/mostrarReporte"/>?funcion=refresh&reporteId=' + document.reporteForm.id.value;
			    window.document.reporteForm.submit();
			}
			
			function validar() 
			{
				if (!document.reporteForm.acumular.checked)
				{
				 	document.reporteForm.anoFinal.value = document.reporteForm.anoInicial.value;
				 	document.reporteForm.periodoFinal.value = document.reporteForm.periodoInicial.value;
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
			
			function getData()
			{
				var xml = "&anoInicial=" + document.reporteForm.anoInicial.value;
				xml = xml + "&anoFinal=" + document.reporteForm.anoFinal.value;
				xml = xml + "&periodoInicial=" + document.reporteForm.periodoInicial.value;
				xml = xml + "&periodoFinal=" + document.reporteForm.periodoFinal.value;
				xml = xml + "&acumular=" + document.reporteForm.acumular.checked;
				xml = xml + "&corte=2";
				
				return xml;
			}
			
			function imprimir(tipo) 
			{
				var url = '?reporteId=' + document.reporteForm.id.value;
				url = url + "&orientacion=H" + getData();
				if (typeof tipo != "undefined") 
					url = url + "&tipoPresentacion=" + tipo;
				
				abrirReporte('<html:rewrite action="/vistasdatos/imprimir"/>' + url, 'Reporte');
			}
			
			function exportarToXls()
			{
				var url = '?reporteId=' + document.reporteForm.id.value + getData();

				abrirReporte('<html:rewrite action="/vistasdatos/exportarXLS"/>' + url, 'Reporte');
			}
			
			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.reporteForm.fechaDesde' , document.reporteForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.reporteForm.fechaHasta' , document.reporteForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
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
		  	
		  	function acumular_click(checked)
		  	{
		  		if (checked)
	  			{
		  			document.getElementById("tdDesde").style.display = "";
		  			document.getElementById("tdHasta").style.display = "";
		  			document.getElementById("tdHastaPeriodo").style.display = "";
	  			}
		  		else
	  			{
		  			document.getElementById("tdDesde").style.display = "none";
		  			document.getElementById("tdHasta").style.display = "none";
		  			document.getElementById("tdHastaPeriodo").style.display = "none";
	  			}
		  	}
	 
		</script>

		<html:form action="/reportes/mostrarReporte" styleClass="formaHtml">
			<html:hidden property="id" />
			<html:hidden property="configuracion" />
			<html:hidden property="corte" />
			<html:hidden property="fechaDesde" />
			<html:hidden property="fechaHasta" />
			<html:hidden property="numeroMaximoPeriodos" />
			<html:hidden property="periodoInicial" />
			<html:hidden property="periodoFinal" />
			<html:hidden property="frecuencia" />
			<html:hidden property="reporteNombre" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.mostrarvistadatos.titulo" /> / <bean:write scope="session" name="organizacionNombre"/>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:refrescar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar();
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarDimensiones">
						
						<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar" pathImagenes="/componentes/barraHerramientas/" nombre="refrescar" onclick="javascript:refrescar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.refrescar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

						<logic:notEqual name="reporteForm" property="configuracion" value="">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir"
								pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
								onclick="javascript:imprimir();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.imprimir.vista.datos" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:exportarToXls();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.vista.datos.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="html"
								pathImagenes="/componentes/barraHerramientas/" nombre="html"
								onclick="javascript:imprimir('HTML');">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.vista.datos.html" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>

					</vgcinterfaz:barraHerramientas>

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td class="encabezadoListView" align="center" colspan="2" style="border: 1px black solid;width: 300px;">
								<vgcutil:message key="jsp.mostrarvistadatos.parametros" />
							</td>
							<td class="encabezadoListView" align="center" width="100%" valign="top" style="border: 1px black solid;">
								<vgcutil:message key="jsp.mostrarvistadatos.informacion.transversal" />
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td colspan="2" style="border: 1px black solid;width: 300px;">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" width="300px">
									<tr class="barraFiltrosForma">
										<td colspan="3">
											<table class="tabTablaPrincipal" width="300px">
												<tr>
													<td colspan="2"><vgcutil:message key="jsp.reporteindicador.editor.tab.rango" /></td>
												</tr>
												<tr>
													<td colspan="2"><html:checkbox property="acumular" styleClass="botonSeleccionMultiple" onclick="acumular_click(this.checked)"></html:checkbox><vgcutil:message key="jsp.reporteindicador.editor.tab.acumulado" /></td>
												</tr>
												<tr>
													<td width="50%" style="display:none" id="tdDesde"><vgcutil:message key="jsp.asistente.grafico.inicial" /></td>
													<td width="50%" style="display:none" id="tdHasta"><vgcutil:message key="jsp.asistente.grafico.final" /></td>
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
																	<select 
																		id="selectPeriodoInicial"
																		onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
													<td width="50%" id="tdHastaPeriodo" style="display:none">
														<table class="tabla contenedorBotonesSeleccion">
															<tr id="trAnoFinal">
																<td><vgcutil:message key="jsp.asistente.grafico.ano" /></td>
																<td>
																	<bean:define id="anoCalculoFinal" toScope="page"><bean:write name="reporteForm" property="anoFinal" /></bean:define>
																	<html:select property="anoFinal"
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
																	<select 
																		id="selectPeriodoFinal" 
																		onchange="validarRango(document.reporteForm.anoInicial, document.reporteForm.anoFinal, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)"
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
										</td>
									</tr>
								</table>
							</td>
							<td width="100%" valign="top" style="border: 1px black solid;">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" width="100%" height="100%">
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px"><vgcutil:message key="jsp.mostrarvistadatos.nombrereporte" /></td>
										<td colspan="2">
											<bean:write name="reporteForm" property="reporteNombre" />
										</td>
									</tr>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px"><vgcutil:message key="jsp.mostrarvistadatos.frecuencia" /></td>
										<td align="left" colspan="2"><bean:write name="reporteForm" property="frecuenciaNombre" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<table class="tabla">
					<tr>
						<td>
							<%-- Datos --%>
							<bean:define id="colorCelda" value="oscuro" />
							<bean:define id="estilo" value="#F4F4F4" />

							<%-- Datos --%>
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
													<td width="200px" class="encabezadoListView" align="center">
														<b><bean:write name="columna" property="valor" /></b>
													</td>
												</logic:equal>
												<logic:equal name="centrado" value="false">
													<td bgcolor="<%=estilo%>" width="200px"><b>
														<bean:write name="columna" property="valor" /></b>
													</td>
												</logic:equal>
											</logic:equal>
	
											<%-- Validaciones para determinar el color de la celda --%>
											<logic:equal name="esEncabezado" value="false">
												<td width="200px" onmouseover="establecerEstilo(this);"
													onmouseout="quitarEstilo(this);" align="center"
													bgcolor="<%=estilo%>">
													<bean:write name="columna" property="valor" />
												</td>
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
		<script>
			agregarPeriodos(document.reporteForm.frecuencia.value);
			eventoCambioFrecuencia(false);
			acumular_click(document.reporteForm.acumular.checked);
		</script>

	</tiles:put>

</tiles:insert>
