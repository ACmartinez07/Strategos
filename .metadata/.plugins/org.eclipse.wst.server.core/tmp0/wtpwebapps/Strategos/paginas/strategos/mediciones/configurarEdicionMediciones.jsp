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
		<vgcutil:message key="jsp.configuraredicionmediciones.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<script type="text/javascript">

			var _setCloseParent = false;
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';
		
			
			var desdeAno = '<bean:write name="editarMedicionesForm" property="anoDesde" ignore="true"/>';
			var hastaAno = '<bean:write name="editarMedicionesForm" property="anoHasta" ignore="true"/>';
			var desdeMes = 1;
			var hastaMes =12;

			function init()
			{
				eventoCambioFrecuencia();
				inicializarSeriesTiempo();
				<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
					document.getElementById('selectPeriodoInicial').value = document.editarMedicionesForm.periodoDesde.value;
					document.getElementById('selectPeriodoFinal').value = document.editarMedicionesForm.periodoHasta.value;
					document.getElementById('selectDefecto').value= document.editarMedicionesForm.periodoHasta.value;
					document.getElementById('selectPeriodoInicial').disabled = true;
					validarRango(document.editarMedicionesForm.anoDesde, document.editarMedicionesForm.anoHasta, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango)
				</logic:equal>	
			}

			function agregarPeriodos(frecuencia, limpiar)
			{
				if (limpiar == undefined)
					limpiar = false;
				
				if(frecuencia !=0 && frecuencia !=8){
				 var index = obtenerPeriodoActual(frecuencia);
				}	
				var select = document.getElementById("selectPeriodoInicial");
				var max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;

			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if(frecuencia !=0 && frecuencia !=8){
			    	select.selectedIndex = index;
			    }else{
			    	select.selectedIndex = 0;
			    }
				select = document.getElementById("selectPeriodoFinal");
				max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;
			    
			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);
			    
				if(frecuencia !=0 && frecuencia !=8){
					select.selectedIndex = index;
			    }else{
		    		select.selectedIndex = select.options.length-1;
			    }
			}
			
			
			
			
			function obtenerPeriodoActual(frecuencia){
				var fechaHoy= new Date();
				var mes=fechaHoy.getMonth() + 1;
				var dia=fechaHoy.getDate(); 
				var año=fechaHoy.getFullYear();
				
				var fecha1 =año+"-"+"01"+"-"+"01"; 
				var fecha2 =año+"-"+mes+"-"+dia;
				
				var fechaInicio = new Date(fecha1).getTime();
				var fechaFin    = new Date(fecha2).getTime();
				
				var mil= 86400000;
				var dif = fechaFin - fechaInicio;
				var dias = dif / mil;
				
				
				
				
				switch (frecuencia) 
			    {
		    		case "0":
		      			return 365;
		      			//diaria
		    		case "1":
		    			var sem= dias/7;
		    			//semanal
		    			return Math.round(sem)-1;
	    			case "2":
	    				if(mes > 1){
	    					var semanas = (mes-1)*2;
	    					if(dia >15){
	    						semanas +=2;
	    					}else if(dia <=15){
	    						semanas +=1;
	    					}	
	    					
	    					return semanas -1;
	    					
	    				}else{
	    					
	    					if(dia >15){
	    						return 1;
	    					}else if(dia <=15){
	    						return 0;
	    					}
	    				}	    				
	    				//quincenal

				    case "3":
				    	//mensual
				      return mes -1;
				    case "4":
				    	//bimestral
				    	if(mes <=2){
				    		return 0;
				    	}else if(mes >2 && mes <=4){
				    		return 1;
				    	}else if(mes >4 && mes <=6){
				    		return 2;
				    	}else if(mes >6 && mes <=8){
				    		return 3;
				    	}else if(mes >8 && mes <=10){
				    		return 4;
				    	}else if(mes >10){
				    		return 5;
				    	}
				 
				    case "5":
				    	
				    	if(mes <=3){
				    		return 0;
				    	}else if(mes >3 && mes <=6){
				    		return 1;
				    	}else if(mes >6 && mes <=9){
				    		return 2;
				    	}else if(mes >9){
				    		return 3;
				    	}
				    	//trimestral
				      
				    case "6":
				    	
				    	if(mes <=4){
				    		return 0;
				    	}else if(mes >4 && mes <=8){
				    		return 1;
				    	}else if(mes >8){
				    		return 2;
				    	}
				    	
				    	//cuatrimestral
			
				    case "7":
				    	if(mes<=6){
				    		return 0;
				    	}else if(mes>6){
				    		return 1;
				    	}
				    	//semestral
				    
				    case "8":
				    	//anual
				      return 1;
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
		  		desde = parseInt(desdeAnoObj.value + "" + (desdeMesObj.value.length == 1 ? (document.editarMedicionesForm.frecuencia.value == "0" ? "00" : "0") : "") + desdeMesObj.value);
				hasta = parseInt(hastaAnoObj.value + "" + (hastaMesObj.value.length == 1 ? (document.editarMedicionesForm.frecuencia.value == "0" ? "00" : "0") : "") + hastaMesObj.value);

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
					var fecha = new Date(); 
					var ano = fecha.getFullYear();
					var mes = obtenerPeriodoActual(document.editarMedicionesForm.frecuencia.value) + 1;
					
					var desdeIniciativa = document.editarMedicionesForm.desdePlanificacion.value;
						
					
					desdeAno = desdeAnoObj.value;
					hastaAno = hastaAnoObj.value;
					desdeMes = desdeMesObj.value;
					hastaMes = hastaMesObj.value;
					
					
					<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
						<logic:equal name="editarMedicionesForm" property="desdeReal" value="true">
							<logic:notEqual name="editarMedicionesForm" property="esAdmin" value="true">
						
								if(hastaAno == ano){
									
									if(hastaMes > mes){
										
										alert('<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido.superior" />');
										hastaAnoObj.value = hastaAno;
										hastaMesObj.value = hastaMes;				
										desdeAnoObj.value = desdeAno;
										desdeMesObj.value = desdeMes;
										return false;
									}
									
								}
								if(hastaAno > ano){
									alert('<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido.superior" />');
									hastaAnoObj.value = hastaAno;
									hastaMesObj.value = hastaMes;				
									desdeAnoObj.value = desdeAno;
									desdeMesObj.value = desdeMes;
									return false;
								}
							</logic:notEqual>
						</logic:equal>
					</logic:equal>
					
					
				}
				
				document.editarMedicionesForm.periodoDesde.value = desdeMesObj.value;
				document.editarMedicionesForm.periodoHasta.value = hastaMesObj.value;
				return true;
			}

			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.editarMedicionesForm.fechaDesde' , document.editarMedicionesForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.editarMedicionesForm.fechaHasta' , document.editarMedicionesForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function eventoCambioFrecuencia() 
			{
				document.getElementById("trPeriodoInicial").style.display = "";
				document.getElementById("trPeriodoFinal").style.display = "";
				
				switch (document.editarMedicionesForm.frecuencia.value) 
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
				
				agregarPeriodos(document.editarMedicionesForm.frecuencia.value);
			}
		
			function editarMediciones() 
			{
				document.editarMedicionesForm.respuesta.value = "";
				document.editarMedicionesForm.periodoDesde.value = document.getElementById('selectPeriodoInicial').value;
				document.editarMedicionesForm.periodoHasta.value = document.getElementById('selectPeriodoFinal').value;
				
				var haySeriesSeleccionadas = false;
				<logic:notEqual name="editarMedicionesForm" property="desdePlanificacion" value="true">
					for (var i = 0; i < document.editarMedicionesForm.elements.length; i++) 
					{
						if (document.editarMedicionesForm.elements[i].name == 'serieId') 
						{
							if (document.editarMedicionesForm.elements[i].checked) 
								haySeriesSeleccionadas = true;
						}
					}
				 	if (!haySeriesSeleccionadas) 
				 	{
				 		alert('<vgcutil:message key="jsp.configuraredicionmediciones.mensaje.noseries" />');
				 		return;
				 	}
				</logic:notEqual>

				if (document.editarMedicionesForm.anoDesde != null) 
				{
				 	var desde = parseInt(document.editarMedicionesForm.anoDesde.value + "" + (document.editarMedicionesForm.periodoDesde.value.length == 1 ? "00" : (document.editarMedicionesForm.periodoDesde.value.length == 2 ? "0" : "")) + document.editarMedicionesForm.periodoDesde.value);
					var hasta = parseInt(document.editarMedicionesForm.anoHasta.value + "" + (document.editarMedicionesForm.periodoHasta.value.length == 1 ? "00" : (document.editarMedicionesForm.periodoHasta.value.length == 2 ? "0" : "")) + document.editarMedicionesForm.periodoHasta.value);

					// Esta condición no se valida en los indicadores diarios
					if (document.editarMedicionesForm.anoDesde.value > document.editarMedicionesForm.anoHasta.value) 
					{
				 		alert('<vgcutil:message key="jsp.configuraredicionmediciones.mensaje.errorano" />');
				 		return;
					}
					
					if (hasta<desde)
					{
				 		alert('<vgcutil:message key="jsp.configuraredicionmediciones.mensaje.errorperiodo" />');
				 		return;
					}
				}
		
			 	if (document.editarMedicionesForm.frecuencia.value == 0)
		 		{
			 		if (!fechaValida(document.editarMedicionesForm.fechaDesde))
			 			return;

			 		if (!fechaValida(document.editarMedicionesForm.fechaHasta))
			 			return;
		 		}
			 	
			 	if (!validarRango(document.editarMedicionesForm.anoDesde, document.editarMedicionesForm.anoHasta, document.getElementById('selectPeriodoInicial'), document.getElementById('selectPeriodoFinal'), errMsRango))
			 		return;
			 		
			 	document.editarMedicionesForm.action = '<html:rewrite action="/mediciones/editarMediciones"/>' + "?source=" + document.editarMedicionesForm.sourceScreen.value + "&funcion=Validar&tipo=0";
				document.editarMedicionesForm.submit();
			}

			function onAceptar()
			{
				this.opener.document.<bean:write name="editarMedicionesForm" property="nombreForma" scope="session" />.<bean:write name="editarMedicionesForm" property="nombreCampoOculto" scope="session" />.value="Sucess";
				this.opener.<bean:write name="editarMedicionesForm" property="funcionCierre" scope="session" />;
				cancelar();
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

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<html:form action="mediciones/configurarEdicionMediciones" focus="frecuencia" styleClass="formaHtml" style="overflow:hidden;" enctype="multipart/form-data" method="POST">

			<html:hidden property="numeroMaximoPeriodos" />
			<html:hidden property="iniciativaId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="desdePlanificacion" />
			<html:hidden property="sourceScreen" />
			<html:hidden property="periodoDesde" />
			<html:hidden property="periodoHasta" />
			<html:hidden property="respuesta" />
			<html:hidden property="status" />
			<html:hidden property="desdeReal" />
			<html:hidden property="esAdmin" />
			
			<input type="hidden" name="periodoDesdeAnt" value="1" />
			<input type="hidden" name="periodoHastaAnt" value="1" />

			<bean:define id="mostrarSeleccion" value="false"></bean:define>
			<logic:notEmpty name="editarMedicionesForm" property="claseId">
				<logic:equal name="editarMedicionesForm" property="desdeIndicador" value="true">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:equal>
			</logic:notEmpty>
			<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
				<logic:equal name="editarMedicionesForm" property="desdeIndicador" value="true">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:equal>
			</logic:notEmpty>
			<bean:define id="altoContenedor" value="400px"></bean:define>
			<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
				<bean:define id="mostrarSeleccion" value="false"></bean:define>
				<bean:define id="altoContenedor" value="260px"></bean:define>
			</logic:equal>
			<logic:notEqual name="editarMedicionesForm" property="desdePlanificacion" value="true">
				<logic:equal name="mostrarSeleccion" value="true">
					<bean:define id="altoContenedor" value="500px"></bean:define>
				</logic:equal>
			</logic:notEqual>
			<bean:define id="bloquearPeriodosIniciales" toScope="page">
				<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
					true
				</logic:equal>
				<logic:notEqual name="editarMedicionesForm" property="desdePlanificacion" value="true">
					false
				</logic:notEqual>
			</bean:define>

			<vgcinterfaz:contenedorForma width="400px" height="<%=altoContenedor %>" bodyAlign="center" bodyValign="middle" marginTop="10px" scrolling="hidden">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
						<vgcutil:message key="jsp.configuraredicionmediciones.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos">

					<%-- Organización --%>
					<tr>
						<td align="right" valign="top"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.organizacion" /></b></td>
						<td colspan="3"><bean:write name="editarMedicionesForm" property="organizacion" />&nbsp;</td>
					</tr>
					<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
						<%-- Plan --%>
						<tr>
							<td align="right" valign="top"><b><bean:write name="editarMedicionesForm" property="nombreObjetoPerspectiva" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm" property="perspectivaNombre" />&nbsp;</td>
						</tr>
					</logic:notEmpty>
					<logic:equal name="editarMedicionesForm" property="desdeIndicador" value="true">
						<%-- Clase de Indicadores --%>
						<tr>
							<td align="right"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.clase" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm" property="clase" />&nbsp;</td>
						</tr>
					</logic:equal>
					<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
						<%-- Actividades --%>
						<tr>
							<td align="right"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.iniciativa" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm" property="iniciativa" />&nbsp;</td>
						</tr>
					</logic:equal>

					<%-- Frecuencia --%>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.frecuencia" /></b></td>
						<td align="left">
							<html:select property="frecuencia" styleClass="cuadroTexto" onchange="eventoCambioFrecuencia();" disabled="<%= Boolean.parseBoolean(bloquearPeriodosIniciales) %>">
								<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
							</html:select>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="50%" colspan="2"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.desde" /></b></td>
						<td width="50%" colspan="2"><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.hasta" /></b></td>
					</tr>
					<tr>
						<bean:define id="anoInicial" toScope="page"> <bean:write name="editarMedicionesForm" property="anoInicial" /></bean:define>
						<bean:define id="anoFin" toScope="page"> <bean:write name="editarMedicionesForm" property="anoFinal" /></bean:define>
						<td width="50%" colspan="2">
							<table class="tabla contenedorBotonesSeleccion">
								<tr id="trAnoInicial">
									<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.ano" /></td>
									<td>
										<bean:define id="anoCalculoInicial" toScope="page"> <bean:write name="editarMedicionesForm" property="anoDesde" /></bean:define>
										<html:select property="anoDesde" value="<%=anoCalculoInicial%>"
												disabled="<%= Boolean.parseBoolean(bloquearPeriodosIniciales) %>"
												styleClass="cuadroTexto">
											<%
												for (int i = Integer.parseInt(anoInicial); i <= Integer.parseInt(anoFin); i++) {
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
											<option selected id="selectDefecto"></option>
										</select>
									</td>
								</tr>
								<tr id="trPeriodoInicialDate">
									<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
									<td>
										<html:text property="fechaDesde" size="12" maxlength="10" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearPeriodosIniciales) %>" />
										<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									</td>
								</tr>
							</table>
						</td>
						<td width="50%" colspan="2">
							<table class="tabla contenedorBotonesSeleccion">
								<tr id="trAnoFinal">
									<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.ano" /></td>
									<td>
										<bean:define id="anoCalculoFinal" toScope="page"><bean:write name="editarMedicionesForm" property="anoHasta" /></bean:define>
										<html:select property="anoHasta" value="<%=anoCalculoFinal%>"
												styleClass="cuadroTexto">
											<%
												for (int i = Integer.parseInt(anoInicial); i <= Integer.parseInt(anoFin); i++) {
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
									<td><vgcutil:message key="jsp.configuraredicionmediciones.ficha.dia" /></td>
									<td>
										<html:text property="fechaHasta" size="12" maxlength="10" styleClass="cuadroTexto" />
										<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="4"><b>&nbsp;</b></td>
					</tr>
					<logic:notEqual name="editarMedicionesForm" property="desdePlanificacion" value="true">
						<tr>
							<td colspan="4">
								<bean:define id="paginaSeries" name="editarMedicionesForm" property="paginaSeriesTiempo" scope="session" toScope="request"></bean:define>
								<div style="position: relative; overflow: auto; height: 100px; border-style: solid; border-width: 1px; border-color: #666666;">
									<vgcinterfaz:visorLista nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" namePaginaLista="paginaSeries" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombre="visorSeriesTiempo" scopePaginaLista="request">
								
										<%-- Selección múltiple --%>
										<vgcinterfaz:visorListaColumnaSeleccion nombreCampoObjetoId="serieId" nombreFormaHtml="editarMedicionesForm" />
	
										<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%">
											<vgcutil:message key="jsp.configuraredicionmediciones.ficha.serietiempo" />
										</vgcinterfaz:columnaVisorLista>
	
										<%-- Filas --%>
										<vgcinterfaz:filasVisorLista nombreObjeto="serie">
			
											<%-- Selección múltiple --%>
											<vgcinterfaz:visorListaValorSeleccion>
												<bean:write name="serie" property="serieId" />
											</vgcinterfaz:visorListaValorSeleccion>
			
											<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" align="left">
												<bean:write name="serie" property="nombre" />
											</vgcinterfaz:valorFilaColumnaVisorLista>
										</vgcinterfaz:filasVisorLista>
	
									</vgcinterfaz:visorLista>
								</div>
							</td>
						</tr>
					</logic:notEqual>

					<logic:equal name="mostrarSeleccion" value="true">
						<%-- Espacio en Blanco --%>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<!-- Campo: Solo Seleccionados / Todos los Seleccionados -->
						<tr>
							<td colspan="4">
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
								<tr>
									<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center">
										<logic:equal name="editarMedicionesForm" property="soloSeleccionados" value="true">
											<input type="radio" name="soloSeleccionados" value="true" class="botonSeleccionSimple" checked>
										</logic:equal>
										<logic:notEqual name="editarMedicionesForm" property="soloSeleccionados" value="true">
											<input type="radio" name="soloSeleccionados" value="true" class="botonSeleccionSimple">
										</logic:notEqual>
									</td>
									<td><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.seleccion.soloseleccionados" /></b></td>
								</tr>
								<tr>
									<logic:equal name="editarMedicionesForm" property="desdeIndicadorOrg" value="false">
										<td width="20px" align="center">
											<logic:equal name="editarMedicionesForm" property="soloSeleccionados" value="true">
												<input type="radio" name="soloSeleccionados" value="false" class="botonSeleccionSimple">
											</logic:equal>
											<logic:notEqual name="editarMedicionesForm" property="soloSeleccionados" value="true">
												<input type="radio" name="soloSeleccionados" value="false" class="botonSeleccionSimple" checked>
											</logic:notEqual>
										</td>
										<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
											<td><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.seleccion.todosperspectiva" /></b></td>
										</logic:notEmpty>
										
											<logic:equal name="editarMedicionesForm" property="desdeIndicador" value="true">
												<logic:empty name="editarMedicionesForm" property="perspectivaId">
													<td><b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.seleccion.todosclase" /></b></td>
												</logic:empty>
											</logic:equal>
										
										<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">
											<td>
												<b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.seleccion.todos.iniciativas" /></b>
											</td>
										</logic:equal>
									</logic:equal>
									
								</tr>
							</table>
							</td>
						</tr>
					</logic:equal>

					<logic:empty name="editarMedicionesForm" property="indicadores" scope="session">
						<tr>
							<td colspan="4"><b>&nbsp;</b></td>
						</tr>
						<tr>
							<td colspan="4"><html:checkbox property="visualizarIndicadoresCompuestos"></html:checkbox>&nbsp;<vgcutil:message key="jsp.configuraredicionmediciones.visualizarindicadorescompuestos" /></td>
						</tr>
					</logic:empty>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:editarMediciones();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
						
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
			function inicializarSeriesTiempo() 
			{
				<logic:notEqual name="editarMedicionesForm" property="desdePlanificacion" value="true">
					for (var i = 0; i < document.editarMedicionesForm.elements.length; i++) 
					{
						if (document.editarMedicionesForm.elements[i].name == 'serieId') 
						{
							<logic:iterate name="paginaSeries" property="lista" id="serie">
								if (document.editarMedicionesForm.elements[i].value == '<bean:write name="serie" property="serieId"/>') 
									document.editarMedicionesForm.elements[i].checked = <bean:write name="serie" property="preseleccionada"/>;
							</logic:iterate>
						}
					}	
				</logic:notEqual>	
			}

			<logic:equal name="editarMedicionesForm" property="status" value="0">
				init();
			</logic:equal>

			<logic:equal name="editarMedicionesForm" property="status" value="1">
				onAceptar();
			</logic:equal>

			<logic:equal name="editarMedicionesForm" property="status" value="2">
				alert('<bean:message key="action.editarmediciones.mensaje.noindicadores" />');
				init();
			</logic:equal>
			
			<logic:equal name="editarMedicionesForm" property="status" value="3">
				if (confirm('<bean:message key="jsp.configuraredicionmediciones.mensaje.nometas" />')) 
					onAceptar();
				else
					init();
			</logic:equal>
			
		</script>
	</tiles:put>
</tiles:insert>
