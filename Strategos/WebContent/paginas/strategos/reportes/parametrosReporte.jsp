<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.reporte.parametros.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript">

			var _setCloseParent = false;
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';
			var desdeAno = '<bean:write name="parametroReporteForm" property="anoDesde" ignore="true"/>';
			var hastaAno = '<bean:write name="parametroReporteForm" property="anoHasta" ignore="true"/>';
			var desdeMes = 1;
			var hastaMes =12;
			var limitePeriodo = '<bean:write name="parametroReporteForm" property="limitePeriodo" ignore="true"/>';
	
			function init()
			{
				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				switch (document.parametroReporteForm.frecuencia.value) 
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
				
				agregarPeriodos(document.parametroReporteForm.frecuencia.value);
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

			    if (document.parametroReporteForm.periodoDesde.value != "")
			    	select.selectedIndex = document.parametroReporteForm.periodoDesde.value -1;
			    else
			    	select.selectedIndex = 0;
			    
				select = document.getElementById("selectPeriodoFinal");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if (document.parametroReporteForm.periodoHasta.value != "")
			    	select.selectedIndex = document.parametroReporteForm.periodoHasta.value -1;
			    else
			    	select.selectedIndex = select.options.length-1;
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

		  	function validarRango(desdeAnoObj, hastaAnoObj, desdeMesObj, hastaMesObj, errmsg)
		  	{
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.parametroReporteForm.frecuencia.value == "0" ? "00" : "0") : "") + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.parametroReporteForm.frecuencia.value == "0" ? "00" : "0") : "") + hastaMesObj.value);

				if (hasta<desde) 
				{
					alert(errmsg);
					hastaAnoObj.value = hastaAno;
					hastaMesObj.value = hastaMes;				
					desdeAnoObj.value = desdeAno;
					desdeMesObj.value = desdeMes;
					return false;
				} 
				else 
				{
					desdeAno = desdeAnoObj.value;
					hastaAno = hastaAnoObj.value;
					desdeMes = desdeMesObj.value;
					hastaMes = hastaMesObj.value;
				}
				
				document.parametroReporteForm.periodoDesde.value = desdeMesObj.value;
				document.parametroReporteForm.periodoHasta.value = hastaMesObj.value;
				return true;
			}

			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.parametroReporteForm.fechaDesde' , document.parametroReporteForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.parametroReporteForm.fechaHasta' , document.parametroReporteForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
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
			
			function validar()
			{
				document.parametroReporteForm.periodoDesde.value = document.getElementById('selectPeriodoInicial').value;
				document.parametroReporteForm.periodoHasta.value = document.getElementById('selectPeriodoFinal').value;
				
				if (document.parametroReporteForm.anoDesde != null) 
				{
				 	var desde = parseInt(document.parametroReporteForm.anoDesde.value + "" + (document.parametroReporteForm.periodoDesde.value.length == 1 ? "00" : (document.parametroReporteForm.periodoDesde.value.length == 2 ? "0" : "")) + document.parametroReporteForm.periodoDesde.value);
					var hasta = parseInt(document.parametroReporteForm.anoHasta.value + "" + (document.parametroReporteForm.periodoHasta.value.length == 1 ? "00" : (document.parametroReporteForm.periodoHasta.value.length == 2 ? "0" : "")) + document.parametroReporteForm.periodoHasta.value);

					// Esta condición no se valida en los indicadores diarios
					if (document.parametroReporteForm.anoDesde.value > document.parametroReporteForm.anoHasta.value) 
					{
				 		alert('<vgcutil:message key="jsp.configuraredicionmediciones.mensaje.errorano" />');
				 		return false;
					}
					
					if (hasta<desde)
					{
				 		alert('<vgcutil:message key="jsp.configuraredicionmediciones.mensaje.errorperiodo" />');
				 		return false;
					}
				}
		
			 	if (document.parametroReporteForm.frecuencia.value == 0)
		 		{
			 		if (!fechaValida(document.parametroReporteForm.fechaDesde))
			 			return false;

			 		if (!fechaValida(document.parametroReporteForm.fechaHasta))
			 			return false;
		 		}
			 	
			 	if (!validarRango(document.parametroReporteForm.anoDesde, document.parametroReporteForm.anoHasta, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango))
			 		return false;
			 	
			 	var periodoDesde = parseInt(document.parametroReporteForm.anoDesde.value + "" + (document.parametroReporteForm.periodoDesde.value.length == 1 ? "00" : (document.parametroReporteForm.periodoDesde.value.length == 2 ? "0" : "")) + document.parametroReporteForm.periodoDesde.value);
				var periodoHasta = parseInt(document.parametroReporteForm.anoHasta.value + "" + (document.parametroReporteForm.periodoHasta.value.length == 1 ? "00" : (document.parametroReporteForm.periodoHasta.value.length == 2 ? "0" : "")) + document.parametroReporteForm.periodoHasta.value);
				var limitePeriodo = parseInt(document.parametroReporteForm.limitePeriodo.value);
			 	if ((periodoHasta - periodoDesde) > limitePeriodo)
				{
			 		alert('<vgcutil:message key="jsp.reporte.parametros.mensaje.limite.fuera" arg0="' + limitePeriodo + '" />');
			 		return false;
				}
			 	
				return true;
			}
			
			function aceptar()
			{
				if (validar())
				{
					var anoDesde = document.parametroReporteForm.anoDesde.value;
					var anoHasta = document.parametroReporteForm.anoHasta.value;
					var periodoDesde = document.parametroReporteForm.periodoDesde.value;
					var periodoHasta = document.parametroReporteForm.periodoHasta.value;
					
					window.opener.onImprimir(anoDesde, anoHasta, periodoDesde, periodoHasta);
					cancelar();
				}
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<html:form action="/reportes/parametrosReporte" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
			<html:hidden property="limitePeriodo" />
			<html:hidden property="frecuencia" />
			<html:hidden property="periodoDesde" />
			<html:hidden property="periodoHasta" />
		
			<vgcinterfaz:contenedorForma width="320px" height="170px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reporte.parametros.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table style="width: 100%; height: 100%; border-spacing:0px; border-collapse: separate; padding: 3px;" class="bordeFichaDatos">
					<!-- Periodos a Imprimir -->
					<tr>
						<td width="50%" colspan="2"><b><vgcutil:message key="jsp.reporte.parametros.desde" /></b></td>
						<td width="50%" colspan="2"><b><vgcutil:message key="jsp.reporte.parametros.hasta" /></b></td>
					</tr>
					<tr>
						<td width="50%" colspan="2">
							<table class="tabla contenedorBotonesSeleccion">
								<tr id="trAnoInicial">
									<td><vgcutil:message key="jsp.reporte.parametros.ano" /></td>
									<td>
										<bean:define id="anoCalculoInicial" toScope="page"> <bean:write name="parametroReporteForm" property="anoDesde" /></bean:define>
										<html:select property="anoDesde" value="<%=anoCalculoInicial%>" styleClass="cuadroTexto">
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
										<select id="selectPeriodoInicial"
											class="cuadroTexto">
										</select>
									</td>
								</tr>
								<tr id="trPeriodoInicialDate">
									<td><vgcutil:message key="jsp.reporte.parametros.dia" /></td>
									<td>
										<html:text property="fechaDesde" size="12" maxlength="10" styleClass="cuadroTexto" />
										<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									</td>
								</tr>
							</table>
						</td>
						<td width="50%" colspan="2">
							<table class="tabla contenedorBotonesSeleccion">
								<tr id="trAnoFinal">
									<td><vgcutil:message key="jsp.reporte.parametros.ano" /></td>
									<td>
										<bean:define id="anoCalculoFinal" toScope="page"><bean:write name="parametroReporteForm" property="anoHasta" /></bean:define>
										<html:select property="anoHasta" value="<%=anoCalculoFinal%>"
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
										<select id="selectPeriodoFinal"
											class="cuadroTexto">
										</select>
									</td>
								</tr>
								<tr id="trPeriodoFinalDate">
									<td><vgcutil:message key="jsp.reporte.parametros.dia" /></td>
									<td>
										<html:text property="fechaHasta" size="12" maxlength="10" styleClass="cuadroTexto" />
										<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:aceptar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
						
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
			init();
			var objeto = document.getElementById("bodyCuerpo");
			if (objeto != null)
			{
				objeto.style.width = "120px";
				objeto.style.height = "40px";
			}
			
		</script>
	</tiles:put>
</tiles:insert>
