<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">
	<tiles:put name="title" type="String">
		<bean:message key="jsp.editarmediciones.titulo" />
	</tiles:put>
	<tiles:put name="body" type="String">
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarMedicionesForm" property="bloqueado">
				<bean:write name="editarMedicionesForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarMedicionesForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery-1.7.1.min.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/tableExport.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/jquery.base64.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/png/html2canvas.js'/>"></script>

		<script type="text/javascript">

			var _formaEditada = null;
			function confirmar() 
			{
				if (!validarTotal())
					return;
					
				var confirmado = confirm('<bean:message key="jsp.editarmediciones.mensaje.confirmarguardar" />');
				if (confirmado) 
					return true;
				else 
					return false;
			}
			
			function validarEdicion()
			{
				_formaEditada = true;
			}
			
			function guardar() 
			{
				if (confirmar()) 
				{
					if(!validarNulo()){
					_formaEditada = false;
					activarBloqueoEspera();
					window.document.editarMedicionesForm.action = '<html:rewrite action="/mediciones/guardarMediciones"/>' + '?ts=<%=(new java.util.Date()).getTime()%>';
					window.document.editarMedicionesForm.submit();
					}
				}	
			}
			
			function cancelar() 
			{
				var continuar = true;
				if (_formaEditada != null && _formaEditada)
					continuar = confirm('<bean:message key="jsp.editarmediciones.mensaje.salir.singrabar" />');
				if (continuar)
				{
					window.document.editarMedicionesForm.action = '<html:rewrite action="/mediciones/cancelarGuardarMediciones"/>';
					window.document.editarMedicionesForm.submit();
				}
			}
			
			function validarNulo(){
				var total = null;
				var campos = document.editarMedicionesForm.indicadoresSeries.value.split("|");
				var tiposCampos;
				var indicadorId = null;
				var serie;
				var hayDato = false;
				var hayDatoMayoraCien = false;
				var tipoCarga;
				for (var i = 0; i < campos.length; i++)
				{
					tiposCampos = campos[i].split(";");
					
					indicadorId = tiposCampos[0];
																
					serie = "0"; //Ejecutado
						if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 0)
						{
							for (var index = 0; index < window.document.editarMedicionesForm.elements.length; index++) 
							{
								if (window.document.editarMedicionesForm.elements[index].name.indexOf('valorIndId' + indicadorId + 'serId' + serie) > -1 && window.document.editarMedicionesForm.elements[index].name.indexOf('anteriorvalorIndId' + indicadorId + 'serId' + serie) == -1) 
								{
									if ((window.document.editarMedicionesForm.elements[index].value == null) || (window.document.editarMedicionesForm.elements[index].value == '')) 
									{
										if((window.document.editarMedicionesForm.elements[index].className == "cuadroTexto")){
											alert('Existen mediciones nulas, debe ingresar mediciones en los campos vacios');
											return true;
										}
									}
								}
							}
						}
					
				
				}
				return false;
			}
			
			function validarPeriodo()
			{
				var campos = document.editarMedicionesForm.indicadoresSeries.value.split("|");
				var tiposCampos;
				var indicadorId;
				var serie;
				var ano;
				var periodo;
				var valor;
				var celda;
				var indice;
				var frecuencia = document.editarMedicionesForm.frecuencia.value;

				for (var i = 0; i < campos.length; i++)
				{
					tiposCampos = campos[i].split(";");
					indicadorId = tiposCampos[0];
					serie = "0";
					for (var index = 0; index < window.document.editarMedicionesForm.elements.length; index++) 
					{
						if (window.document.editarMedicionesForm.elements[index].name.indexOf('valorIndId' + indicadorId + 'serId' + serie) > -1 && window.document.editarMedicionesForm.elements[index].name.indexOf('anteriorvalorIndId' + indicadorId + 'serId' + serie) == -1) 
						{
							if ((window.document.editarMedicionesForm.elements[index].value != null) && (window.document.editarMedicionesForm.elements[index].value != '')) 
							{
								celda = window.document.editarMedicionesForm.elements[index].name;
								valor = window.document.editarMedicionesForm.elements[index].value;
								// Periodo
								indice = celda.indexOf("periodo") + 7;
								periodo = celda.substring(indice, (celda.indexOf("ano")));
								// Ano
								indice = celda.indexOf("ano") + 3;
								ano = celda.substring(indice, celda.length);
								
								var fechaInicio = document.getElementById("fechaRealInicioIndId_" + indicadorId);
								var fechaFin = document.getElementById("fechaRealFinIndId_" + indicadorId);
								if (fechaInicio != null && !fechaValida(fechaInicio))
								{
						 			var td = document.getElementById("tdFechaRealInicioIndId_" + indicadorId);
						 			if (td != null)
						 				td.className = "cuerpoListViewCeldaSeleccionadaError"; 
									return true;
								}

						 		if (fechaFin != null && !fechaValida(fechaFin))
					 			{
						 			var td = document.getElementById("tdFechaRealFinIndId_" + indicadorId);
						 			if (td != null)
						 				td.className = "cuerpoListViewCeldaSeleccionadaError"; 
									return true;
					 			}
						 		
						 		if (fechaInicio != null && fechaFin != null && !fechasRangosValidos(fechaInicio, fechaFin))
					 			{
						 			var td = document.getElementById("tdFechaRealInicioIndId_" + indicadorId);
						 			if (td != null)
						 				td.className = "cuerpoListViewCeldaSeleccionadaError"; 
						 			td = document.getElementById("tdFechaRealFinIndId_" + indicadorId);
						 			if (td != null)
						 				td.className = "cuerpoListViewCeldaSeleccionadaError"; 
									return true;
					 			}
								
								if (fechaFin != null)
								{
									var matrizFecha = fechaFin.value.replace("-", "/").replace("-", "/").split("/");
									var fechaRealFin = new Date(matrizFecha[2], (matrizFecha[1] - 1), matrizFecha[0]);

									var fecha = getDateByPeriodo(parseInt(frecuencia), parseInt(ano), parseInt(periodo));
									if (fechaRealFin.getTime() < fecha.getTime())
									{
										if (confirm('<vgcutil:message key="jsp.editarmediciones.mensaje.confirmarfechafin" />'))
										{
											var dia = ultimoDiaDelMes(fecha.getMonth(), fecha.getFullYear());
											var mes = (fecha.getMonth() + 1);
											mes = (mes.toString().length == 1 ? "0" + mes : mes);
											fechaFin.value = dia + "/" + mes + "/" + fecha.getFullYear();
										}
									}
								}
							}
						}
					}
				}
				
				return false;
			}
			
			function getDateByPeriodo(frecuencia, ano, periodo)
			{
				if (frecuencia == 0) 
			  	{
					var mes = 1;
					var dia = periodo;
				}
				else if (frecuencia == 1) 
			  	{
					var mes = 1;
					var dia = ((periodo * 7) - 6);
				}
				else if (frecuencia == 2) 
			  	{
					var mes = 1;
					var dia = ((periodo * 15) - 14);
				}
				else if (frecuencia == 3) 
			  	{
					var mes = periodo;
					var dia = 1;
				}
				else if (frecuencia == 4) 
			  	{
					var mes = ((periodo * 2) - 1);
					var dia = 1;
				}
				else if (frecuencia == 5) 
			  	{
					var mes = ((periodo * 3) - 2);
					var dia = 1;
				}
				else if (frecuencia == 6) 
			  	{
					var mes = ((periodo * 4) - 3);
					var dia = 1;
				}
				else if (frecuencia == 7) 
			  	{
					var mes = ((periodo * 6) - 5);
					var dia = 1;
				}
				else if (frecuencia == 8) 
			  	{
					var mes = 1;
					var dia = 1;
				}

				return new Date(ano, (mes-1), dia);
			}
			
			function validarTotal()
			{
				if (!eval(document.editarMedicionesForm.desdePlanificacion.value))
					return true;

				var total = null;
				var campos = document.editarMedicionesForm.indicadoresSeries.value.split("|");
				var tiposCampos;
				var indicadorId = null;
				var serie;
				var hayDato = false;
				var hayDatoMayoraCien = false;
				var tipoCarga;
				for (var i = 0; i < campos.length; i++)
				{
					tiposCampos = campos[i].split(";");
					if (indicadorId != tiposCampos[0])
						total = null;
					indicadorId = tiposCampos[0];
					var isPocentaje = document.getElementById('indIsPocentaje_' + indicadorId);
					tipoCarga = document.getElementById('indTipoCarga_' + indicadorId);
					if (isPocentaje.value == "true")
					{
						serie = "1"; //Programado
						if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 1)
						{
							for (var index = 0; index < window.document.editarMedicionesForm.elements.length; index++) 
							{
								if (window.document.editarMedicionesForm.elements[index].name.indexOf('valorIndId' + indicadorId + 'serId' + serie) > -1 && window.document.editarMedicionesForm.elements[index].name.indexOf('anteriorvalorIndId' + indicadorId + 'serId' + serie) == -1) 
								{
									if ((window.document.editarMedicionesForm.elements[index].value != null) && (window.document.editarMedicionesForm.elements[index].value != '')) 
									{
										if (convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false) > convertirNumeroFormateadoToNumero("100", false))
										{
											hayDatoMayoraCien = true;
											break;
										}
										hayDato = true;
										if (total == null)
											total = 0;
										
										if (tipoCarga.value == 0)
											total = total + convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false);
										else
											total = convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false);
									}
								}
							}
						}

						if ((document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 1 && hayDato && total != null && total != convertirNumeroFormateadoToNumero("100", false)) || hayDatoMayoraCien)
							break;
						
						serie = "0"; //Ejecutado
						if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 0)
						{
							for (var index = 0; index < window.document.editarMedicionesForm.elements.length; index++) 
							{
								if (window.document.editarMedicionesForm.elements[index].name.indexOf('valorIndId' + indicadorId + 'serId' + serie) > -1 && window.document.editarMedicionesForm.elements[index].name.indexOf('anteriorvalorIndId' + indicadorId + 'serId' + serie) == -1) 
								{
									if ((window.document.editarMedicionesForm.elements[index].value != null) && (window.document.editarMedicionesForm.elements[index].value != '')) 
									{
										if (convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false) > convertirNumeroFormateadoToNumero("100", false))
										{
											hayDatoMayoraCien = true;
											break;
										}
										hayDato = true;
										if (total == null)
											total = 0;

										if (tipoCarga.value == 0)
											total = total + convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false);
										else
											total = convertirNumeroFormateadoToNumero(window.document.editarMedicionesForm.elements[index].value, false);
									}
								}
							}
						}
					}
					
					if ((hayDato && total != null && total != convertirNumeroFormateadoToNumero("100", false)) || hayDatoMayoraCien)
						break;
				}

				if (hayDatoMayoraCien)
				{
					cambiarEstiloFila(indicadorId, serie, "celdaMedicionesError");
					alert('<vgcutil:message key="action.guardarmediciones.mensaje.guardarmediciones.porcentajemayoracien" />');
					return false;
				}

				if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 1 && hayDato && total != null && total != convertirNumeroFormateadoToNumero("100", false))
				{
					cambiarEstiloFila(indicadorId, serie, "celdaMedicionesError");
					if (tipoCarga.value == 0)
						alert('<vgcutil:message key="action.guardarmediciones.mensaje.guardarmediciones.porcentajediferentedecien" />');
					else
						alert('<vgcutil:message key="action.guardarmediciones.mensaje.guardarmediciones.porcentajediferentedecien.alperiodo" />');
					return false;
				}

				if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 0 && hayDato && total != null && total > convertirNumeroFormateadoToNumero("100", false))
				{
					cambiarEstiloFila(indicadorId, serie, "celdaMedicionesError");
					if (tipoCarga.value == 0)
						alert('<vgcutil:message key="action.guardarmediciones.mensaje.guardarmediciones.ejecutado.porcentajediferentedecien" />');
					else
						alert('<vgcutil:message key="action.guardarmediciones.mensaje.guardarmediciones.ejecutado.porcentajediferentedecien.alperiodo" />');
					return false;
				}
				
				if (document.editarMedicionesForm.tipoCargaDesdePlanificacion.value == 0 && validarPeriodo())
					return false;
				
				return true;
			}
			
			function cambiarEstiloFila(indicadorId, serieId, estilo)
			{
				for (var index = 0; index < window.document.editarMedicionesForm.elements.length; index++) 
				{
					if (window.document.editarMedicionesForm.elements[index].name.indexOf('valorIndId' + indicadorId + 'serId' + serieId) > -1 && window.document.editarMedicionesForm.elements[index].name.indexOf('anteriorvalorIndId' + indicadorId + 'serId' + serieId) == -1) 
					{
						var celda = window.document.editarMedicionesForm.elements[index].name;
						// Periodo
						var indice = celda.indexOf("periodo") + 7;
						var periodo = celda.substring(indice, (celda.indexOf("ano")));
						// Ano
						indice = celda.indexOf("ano") + 3;
						var ano = celda.substring(indice, celda.length);
						
						var td = document.getElementById("tdFilaMedicion_" + indicadorId + "_" + serieId + "_" + periodo + "_" + ano); 
			 			if (td != null)
			 				td.className = estilo; 
					}
				}
			}
			
			function seleccionarFecha(campo) 
			{
				var indicadorId = campo.split("_");
				if (indicadorId.length > 1)
				{
					var td = document.getElementById("tdFechaRealInicioIndId_" + indicadorId[1]);
		 			if (td != null)
		 				td.className = "celdaMediciones"; 
			 		td = document.getElementById("tdFechaRealFinIndId_" + indicadorId[1]);
		 			if (td != null)
		 				td.className = "celdaMediciones"; 
				}
				
				var fecha = document.getElementById(campo);
				if (fecha != null)
					mostrarCalendario(campo, fecha.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function configurarVisorEdicion()
			{
				configurarVisorLista('Strategos.Forma.Configuracion.Columnas', 'Medicion', '<vgcutil:message key="jsp.editarmediciones.titulo" />', true, 540, 280);
			}
			
			function getUrl(adicionalUrl, agregarPeriodos)
			{
				if (typeof agregarPeriodos == "undefined")
					agregarPeriodos = true;
				
				var url = 'claseId=' + document.editarMedicionesForm.claseId.value;
				url = url + '&source=' + document.editarMedicionesForm.sourceScreen.value;
				url = url + '&desdeClases=' + document.editarMedicionesForm.desdeClase.value;
				url = url + '&tipo=' + document.editarMedicionesForm.tipoCargaDesdePlanificacion.value;
				url = url + '&funcion=Ejecutar';
				
				if (document.editarMedicionesForm.desdePlanificacion.value == "true")
				{
					url = url + '&iniciativaId='  + document.editarMedicionesForm.iniciativaId.value;
					url = url + '&organizacionId='  + document.editarMedicionesForm.organizacionId.value;
				}
				
				if (adicionalUrl)
				{
					url = url + '&indicadores=' + getIndicadores();
					if (agregarPeriodos)
					{
						url = url + '&anoDesde=' + document.editarMedicionesForm.anoDesde.value;
						url = url + '&anoHasta=' + document.editarMedicionesForm.anoHasta.value;
						url = url + '&periodoDesde=' + document.editarMedicionesForm.periodoDesde.value;
						url = url + '&periodoHasta=' + document.editarMedicionesForm.periodoHasta.value;
					}
				}

				return url;
			}
			
			function onConfigurarVisorEdicion()
			{
				window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + getUrl(false, false);
			}
			
			function imprimir(tipo) 
			{
				var url = getUrl(true, true);
				url = url + "&orientacion=H&corte=1";
				if (typeof tipo != "undefined")
				{
					url = url + "&tipoPresentacion=" + tipo;
					
					abrirReporte('<html:rewrite action="/mediciones/imprimirMediciones"/>?' + url, 'Reporte');
				}
				else
				{
					var nombreForma = '?nombreForma=' + 'editarMedicionesForm';
					var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
					var funcionCierre = '&funcionCierre=onImprimir&funcion=parametros&frecuencia=' + document.editarMedicionesForm.frecuencia.value;

					abrirVentanaModal('<html:rewrite action="/reportes/parametrosReporte" />' + nombreForma + nombreCampoOculto + funcionCierre, 'parametros', '360', '190');
				}
			}
			
			function onImprimir(anoDesde, anoHasta, periodoDesde, periodoHasta) 
			{
				var url = getUrl(true, false);
				url = url + "&orientacion=H&corte=1";
				url = url + "&anoDesde=" + anoDesde;
				url = url + "&anoHasta=" + anoHasta;
				url = url + "&periodoDesde=" + periodoDesde;
				url = url + "&periodoHasta=" + periodoHasta; 

				abrirReporte('<html:rewrite action="/mediciones/imprimirMediciones"/>?' + url, 'Reporte');
			}

			function exportarToXls()
			{
				//abrirReporte('<html:rewrite action="/mediciones/exportarXLSMediciones"/>?' + getUrl(true, true), 'Reporte');
				$('#tblDatos').tableExport({type:'excel',escape:'false'});
			}
			
			function getIndicadores()
			{
				var indicadores = "";
				var esPrimero = true;
				<logic:notEmpty name="seriesIndicadores" scope="request">
					<logic:iterate name="seriesIndicadores" id="serieIndicador" scope="request">
						var indicadorId = '<bean:write name="serieIndicador" property="indicador.indicadorId" />';
						var serieId = '<bean:write name="serieIndicador" property="pk.serieId" />';
						if (!esPrimero)
							indicadores = indicadores + "!;!";
						indicadores = indicadores + indicadorId + "!:!" + serieId;
						esPrimero = false;
					</logic:iterate>
				</logic:notEmpty>
					
				return indicadores;
			}
		</script>

		<bean:define id="naturalezaFormula" name="editarMedicionesForm" property="naturalezaFormula" toScope="page"></bean:define>
		<bean:define id="naturalezaSumatoria" name="editarMedicionesForm" property="naturalezaSumatoria" toScope="page"></bean:define>
		<bean:define id="naturalezaPromedio" name="editarMedicionesForm" property="naturalezaPromedio" toScope="page"></bean:define>
		<bean:define id="naturalezaIndice" name="editarMedicionesForm" property="naturalezaIndice" toScope="page"></bean:define>
		<bean:define id="naturalezaCualitativoOrdinal" name="editarMedicionesForm" property="naturalezaCualitativoOrdinal" toScope="page"></bean:define>
		<bean:define id="naturalezaCualitativoNominal" name="editarMedicionesForm" property="naturalezaCualitativoNominal" toScope="page"></bean:define>

		<html:form action="mediciones/guardarMediciones" styleClass="formaHtml">
			<html:hidden property="iniciativaId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="indicadoresSeries" />
			<html:hidden property="desdePlanificacion" />
			<html:hidden property="frecuencia" />
			<html:hidden property="claseId" />
			<html:hidden property="sourceScreen" />
			<html:hidden property="desdeClase" />
			<html:hidden property="serieId" />
			<html:hidden property="anoDesde" />
			<html:hidden property="anoHasta" />
			<html:hidden property="periodoDesde" />
			<html:hidden property="periodoHasta" />
			<html:hidden property="tipoCargaDesdePlanificacion" />
			
			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true" bodyAlign="left" marginTop="15px">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.editarmediciones.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table width="100%" cellpadding="3" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="20px">
								<b><bean:message key="jsp.editarmediciones.ficha.organizacion" /></b>
							</td>
							<td>
								<bean:write name="editarMedicionesForm" property="organizacion" />&nbsp;
							</td>
						</tr>
						<logic:equal name="editarMedicionesForm" property="sourceScreen" value="0">
							<tr class="barraFiltrosForma">
								<td width="20px">
									<b><bean:message key="jsp.editarmediciones.ficha.clase" /></b>
								</td>
								<td>
									<bean:write name="editarMedicionesForm" property="clase" />&nbsp;
								</td>
							</tr>
						</logic:equal>
						<logic:equal name="editarMedicionesForm" property="sourceScreen" value="1">
							<tr class="barraFiltrosForma">
								<td width="20px">
									<b><bean:write name="editarMedicionesForm" property="nombreObjetoPerspectiva" /></b>
								</td>
								<td>
									<bean:write name="editarMedicionesForm" property="perspectivaNombre" />&nbsp;
								</td>
							</tr>
						</logic:equal>
						<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
							<%-- Actividades --%>
							<tr class="barraFiltrosForma">
								<td width="20px">
									<b><vgcutil:message key="jsp.configuraredicionmediciones.ficha.iniciativa" /></b>
								</td>
								<td>
									<bean:write name="editarMedicionesForm" property="iniciativa" />&nbsp;
								</td>
							</tr>
						</logic:equal>
					</table>

					<vgcinterfaz:barraHerramientas nombre="barraMediciones">
						<logic:notEqual name="editarMedicionesForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION_CARGAR" nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasSeparador />
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="tool" pathImagenes="/componentes/barraHerramientas/" nombre="tool" onclick="javascript:configurarVisorEdicion();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.tool.edicion" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir" pathImagenes="/componentes/barraHerramientas/" nombre="imprimir" onclick="javascript:imprimir();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.imprimir" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar" pathImagenes="/componentes/barraHerramientas/" nombre="exportar" onclick="javascript:exportarToXls();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.excel" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="html" pathImagenes="/componentes/barraHerramientas/" nombre="html" onclick="javascript:imprimir('HTML');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.html" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<bean:define id="tamanoTabla" scope="page"><bean:write name="editarMedicionesForm" property="anchoMatriz"/></bean:define>
				<table id="tblDatos" style="border-spacing:1px; border-collapse: separate; padding: 3px; width: <%= tamanoTabla.toString() %>; table-layout: fixed;">
					<bean:define id="indicadorId" scope="page">0</bean:define>
					<bean:define id="agregarEncabezado" scope="page">true</bean:define>
					<bean:define id="agregarPeriodos" scope="page">true</bean:define>
					<logic:notEmpty name="seriesIndicadores" scope="request">
						<logic:iterate name="seriesIndicadores" id="serieIndicador" scope="request">
								<bean:define id="indicador" name="serieIndicador" property="indicador" toScope="page"></bean:define>
								<bean:define id="indicadorCompuesto" value="false" toScope="page"></bean:define>
								<bean:define id="indicadorCualitativo" value="false" toScope="page"></bean:define>
							
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaFormula.toString() %>">
									<bean:define id="indicadorCompuesto" value="true" toScope="page"></bean:define>
								</logic:equal>
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaSumatoria.toString() %>">
									<bean:define id="indicadorCompuesto" value="true" toScope="page"></bean:define>
								</logic:equal>
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaPromedio.toString() %>">
									<bean:define id="indicadorCompuesto" value="true" toScope="page"></bean:define>
								</logic:equal>
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaIndice.toString() %>">
									<bean:define id="indicadorCompuesto" value="true" toScope="page"></bean:define>
								</logic:equal>
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaCualitativoOrdinal.toString() %>">
									<bean:define id="indicadorCualitativo" value="true" toScope="page"></bean:define>
								</logic:equal>
								<logic:equal scope="page" name="indicador" property="naturaleza" value="<%= naturalezaCualitativoNominal.toString() %>">
									<bean:define id="indicadorCualitativo" value="true" toScope="page"></bean:define>
								</logic:equal>
		
								<bean:define id="numeroDecimales" name="indicador" property="numeroDecimales" type="java.lang.Byte" />
								
								<!-- Encabezado -->
								<bean:define id="tamanoPeriodos" scope="page">150</bean:define>
								<logic:equal name="agregarEncabezado" value="true">
									<tr class="encabezadoListView" height="20px">
										<logic:iterate name="editarMedicionesForm" property="columnas" id="columna">
											<logic:notEqual name="columna" property="orden" value="4">
												<logic:equal name="columna" property="mostrar" value="true">
													<bean:define id="tamano" name="columna" property="tamano" type="java.lang.String"></bean:define>
													<bean:define id="nombre" name="columna" property="nombre" type="java.lang.String"></bean:define>
													<td style="text-align: center; width:<%= tamano.toString() %>px; overflow: hidden;"><%= nombre %></td>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="columna" property="orden" value="4">
												<logic:notEqual name="columna" property="mostrar" value="true">
													<bean:define id="agregarPeriodos" scope="page">false</bean:define>
												</logic:notEqual>
												<logic:equal name="columna" property="mostrar" value="true">
													<bean:define id="tamanoPeriodos" name="columna" property="tamano" type="java.lang.String"></bean:define>
												</logic:equal>
											</logic:equal>
										</logic:iterate>
										
										<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
											<logic:equal name="editarMedicionesForm" property="tipoCargaDesdePlanificacion" value="0">
												<td style="text-align: center; width: 100px; overflow: hidden;"><vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.inicio" /></td>
												<td style="text-align: center; width: 100px; overflow: hidden;"><vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.culminacion" /></td>
											</logic:equal>
										</logic:equal>
										
										<logic:equal name="agregarPeriodos" value="true">
											<bean:define id="frecuencia" type="java.lang.Byte" name="editarMedicionesForm" property="frecuencia"></bean:define>
											<logic:iterate name="serieIndicador" property="mediciones" id="medicion">
												<td style="text-align: center; width: <%= tamanoPeriodos.toString() %>px; overflow: hidden;">
													<vgcst:nombrePeriodoMedicion name="medicion" frecuencia="<%= ((Byte) frecuencia).toString() %>" />
												</td>
											</logic:iterate>
										</logic:equal>
									</tr>
									<bean:define id="agregarEncabezado" scope="page">false</bean:define>
								</logic:equal>
		
								<!-- Cuerpo -->	
								<tr class="mouseFueraCuerpoListView" height="20px">
									<logic:iterate name="editarMedicionesForm" property="columnas" id="columna">
										<bean:define id="tamano" name="columna" property="tamano" type="java.lang.String"></bean:define>
										<logic:equal name="indicador" property="indicadorId" value="<%= indicadorId %>">
											<!-- Nombre del Indicador -->
											<logic:equal name="columna" property="mostrar" value="true">
												<logic:equal name="columna" property="orden" value="1">
													<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMediciones">&nbsp;</td>
													</logic:notEqual>
											
													<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMedicionesProgramado">&nbsp;</td>
													</logic:equal>
													
												</logic:equal>
												<logic:equal name="columna" property="orden" value="2">
													<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMediciones">&nbsp;</td>
													</logic:notEqual>
											
													<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMedicionesProgramado">&nbsp;</td>
													</logic:equal>
													
												</logic:equal>
											</logic:equal>
										</logic:equal>
										<logic:notEqual name="indicador" property="indicadorId" value="<%= indicadorId %>">
											<logic:equal name="columna" property="mostrar" value="true">
												<logic:equal name="columna" property="orden" value="1">
												
													<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
														<!-- Nombre del Indicador -->
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMediciones"><bean:write name="indicador" property="nombre" />&nbsp;&nbsp;
															<logic:equal name="indicador" property="estaBloqueado" value="true">
																(<bean:message key="jsp.editarmediciones.ficha.indicador.bloqueado" />)
															</logic:equal>
															<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
																<input type="hidden" 
																	name="indIsPocentaje_<bean:write name="indicador" property="indicadorId" />" 
																	id="indIsPocentaje_<bean:write name="indicador" property="indicadorId" />"
																	value="<bean:write name="indicador" property="isPocentaje" />">
																<input type="hidden" 
																	name="indTipoCarga_<bean:write name="indicador" property="indicadorId" />" 
																	id="indTipoCarga_<bean:write name="indicador" property="indicadorId" />"
																	value="<bean:write name="indicador" property="tipoCargaMedicion" />">
															</logic:equal>
														</td>
													</logic:notEqual>
											
													<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
														<!-- Nombre del Indicador -->
														<td style="vertical-align: top; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMedicionesProgramado"><bean:write name="indicador" property="nombre" />&nbsp;&nbsp;
															<logic:equal name="indicador" property="estaBloqueado" value="true">
																(<bean:message key="jsp.editarmediciones.ficha.indicador.bloqueado" />)
															</logic:equal>
															<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
																<input type="hidden" 
																	name="indIsPocentaje_<bean:write name="indicador" property="indicadorId" />" 
																	id="indIsPocentaje_<bean:write name="indicador" property="indicadorId" />"
																	value="<bean:write name="indicador" property="isPocentaje" />">
																<input type="hidden" 
																	name="indTipoCarga_<bean:write name="indicador" property="indicadorId" />" 
																	id="indTipoCarga_<bean:write name="indicador" property="indicadorId" />"
																	value="<bean:write name="indicador" property="tipoCargaMedicion" />">
															</logic:equal>
														</td>
													</logic:equal>
												
												
													
												</logic:equal>
												<logic:equal name="columna" property="orden" value="2">
												
													<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
														<!-- Unidad del Indicador -->
														<td style="vertical-align: top; text-align: center; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMediciones">
															<logic:notEmpty name="indicador" property="unidad">
																<bean:write name="indicador" property="unidad.nombre" />
															</logic:notEmpty>&nbsp;
														</td>
													</logic:notEqual>
											
													<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
														<!-- Unidad del Indicador -->
														<td style="vertical-align: top; text-align: center; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMedicionesProgramado">
															<logic:notEmpty name="indicador" property="unidad">
																<bean:write name="indicador" property="unidad.nombre" />
															</logic:notEmpty>&nbsp;
														</td>
													</logic:equal>
												
													
												</logic:equal>
											</logic:equal>
										</logic:notEqual>
										<logic:equal name="columna" property="orden" value="3">
											<logic:equal name="columna" property="mostrar" value="true">
												<!-- Nombre de la Serie -->
												
												<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
													<td style="vertical-align: top; text-align: center; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMediciones">
														<bean:write name="serieIndicador" property="serieTiempo.nombre" />
													</td>
												</logic:notEqual>
										
												<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
													<td style="vertical-align: top; text-align: center; width:<%= tamano.toString() %>px; overflow: hidden;" class="celdaMedicionesProgramado">
														<bean:write name="serieIndicador" property="serieTiempo.nombre" />
													</td>
												</logic:equal>
												
											</logic:equal>
										</logic:equal>
										
										
										
									</logic:iterate>
									
									<logic:notEqual name="indicador" property="indicadorId" value="<%= indicadorId %>">
										<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
											<logic:equal name="editarMedicionesForm" property="tipoCargaDesdePlanificacion" value="0">
												
												<logic:notEqual name="serieIndicador" property="serieTiempo.serieId" value="1">
													<td id="tdFechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" style="vertical-align: top; width:100px; text-align: center; overflow: hidden;" class="celdaMediciones">
														<logic:notEqual name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="cuadroTexto" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9"
																name="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaInicial" />">
															<span style="padding:2px">
																<img 
																	style="cursor: pointer" 
																	onclick="seleccionarFecha('fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />');" 
																	src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																	border="0" 
																	width="10" 
																	height="10"
																	title="<vgcutil:message key="boton.calendario.alt" />">
															</span>
														</logic:notEqual>
														<logic:equal name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="medicionProtegida"
																style="text-align: right; width:80px; overflow: hidden;"
																size="9" readonly
																name="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaInicial" />">
														</logic:equal>
													</td>
													<td id="tdFechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" style="vertical-align: top; text-align: center; width:100px; overflow: hidden;" class="celdaMediciones">
														<logic:notEqual name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="cuadroTexto" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9"
																name="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaFinal" />">
															<span style="padding:2px">
																<img 
																	style="cursor: pointer" 
																	onclick="seleccionarFecha('fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />');" 
																	src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																	border="0" 
																	width="10" 
																	height="10"
																	title="<vgcutil:message key="boton.calendario.alt" />">
															</span>
														</logic:notEqual>
														<logic:equal name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="medicionProtegida" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9" readonly
																name="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaFinal" />">
														</logic:equal>
													</td>
												</logic:notEqual>
										
												<logic:equal name="serieIndicador" property="serieTiempo.serieId" value="1">
													<td id="tdFechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" style="vertical-align: top; width:100px; text-align: center; overflow: hidden;" class="celdaMedicionesProgramado">
														<logic:notEqual name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="cuadroTexto" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9"
																name="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaInicial" />">
															<span style="padding:2px">
																<img 
																	style="cursor: pointer" 
																	onclick="seleccionarFecha('fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />');" 
																	src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																	border="0" 
																	width="10" 
																	height="10"
																	title="<vgcutil:message key="boton.calendario.alt" />">
															</span>
														</logic:notEqual>
														<logic:equal name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="medicionProtegida"
																style="text-align: right; width:80px; overflow: hidden;"
																size="9" readonly
																name="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealInicioIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaInicial" />">
														</logic:equal>
													</td>
													<td id="tdFechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" style="vertical-align: top; text-align: center; width:100px; overflow: hidden;" class="celdaMedicionesProgramado">
														<logic:notEqual name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="cuadroTexto" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9"
																name="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaFinal" />">
															<span style="padding:2px">
																<img 
																	style="cursor: pointer" 
																	onclick="seleccionarFecha('fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />');" 
																	src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																	border="0" 
																	width="10" 
																	height="10"
																	title="<vgcutil:message key="boton.calendario.alt" />">
															</span>
														</logic:notEqual>
														<logic:equal name="editarMedicionesForm" property="bloqueado" value="true">
															<input type="text" 
																class="medicionProtegida" 
																style="text-align: right; width:80px; overflow: hidden;"
																size="9" readonly
																name="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />" 
																id="fechaRealFinIndId_<bean:write name="indicador" property="indicadorId" />"
																value="<bean:write name="indicador" property="fechaFinal" />">
														</logic:equal>
													</td>
												</logic:equal>
												
											</logic:equal>
										</logic:equal>
									</logic:notEqual>
									<logic:equal name="indicador" property="indicadorId" value="<%= indicadorId %>">
										<logic:equal name="editarMedicionesForm" property="sourceScreen" value="3">
											<logic:equal name="editarMedicionesForm" property="tipoCargaDesdePlanificacion" value="0">
												<td style="vertical-align: top; width:100px; text-align: center; overflow: hidden;" class="celdaMediciones">&nbsp;</td>
												<td style="vertical-align: top; width:100px; text-align: center; overflow: hidden;" class="celdaMediciones">&nbsp;</td>
											</logic:equal>
										</logic:equal>
									</logic:equal>
									
									<bean:define id="indicadorId">
										<bean:write name="indicador" property="indicadorId" />
									</bean:define>
									
									<!-- agregar periodos -->
										<logic:equal name="agregarPeriodos" value="true">
											<logic:iterate name="serieIndicador" property="mediciones" id="medicion">
												<bean:define id="medicionSoloLectura" scope="page" value=""></bean:define>
												<bean:define id="claseEstiloCelda" scope="page" value="cuadroTexto"></bean:define>
												<logic:equal name="indicador" property="estaBloqueado" value="true">
													<bean:define id="medicionSoloLectura" scope="page" value="this.blur();"></bean:define>
													<bean:define id="claseEstiloCelda" scope="page" value="medicionProtegida"></bean:define>
												</logic:equal>
												<logic:equal name="medicion" property="protegido" value="true">
													<bean:define id="medicionSoloLectura" scope="page" value="this.blur();"></bean:define>
													<bean:define id="claseEstiloCelda" scope="page" value="medicionProtegida"></bean:define>
												</logic:equal>
												<logic:equal name="indicadorCompuesto" scope="page" value="true">
													<logic:equal name="medicion" property="medicionId.serieId" value="0">
														<bean:define id="medicionSoloLectura" scope="page" value="this.blur();"></bean:define>
														<bean:define id="claseEstiloCelda" scope="page" value="medicionProtegida"></bean:define>
													</logic:equal>
												</logic:equal>
												<td id="tdFilaMedicion_<bean:write name="medicion" 
																		property="medicionId.indicadorId" />_<bean:write name="medicion" 
																		property="medicionId.serieId" />_<bean:write name="medicion" 
																		property="medicionId.periodo" />_<bean:write name="medicion" 
																		property="medicionId.ano" />" class="celdaMediciones" style="vertical-align: top; width:<%= tamanoPeriodos.toString() %>px; text-align: center; overflow: hidden;">
													<logic:notEqual scope="page" name="indicadorCualitativo" value="true">
														<input 
															type="text" 
															onfocus="<bean:write name="medicionSoloLectura" scope="page"/>" 
															onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, <bean:write name="indicador" property="numeroDecimales"/>, true);" 
															onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, <bean:write name="indicador" property="numeroDecimales"/>, true);" 
															onblur="validarEntradaNumeroEventoOnBlur(this, event, <bean:write name="indicador" property="numeroDecimales" />, true);"
															onchange="validarEdicion();" 
															class="<bean:write name="claseEstiloCelda" scope="page"/>" 
															style="text-align: right; width:100%;"
															name="valorIndId<bean:write 
																				name="medicion" 
																				property="medicionId.indicadorId" />serId<bean:write 
																				name="medicion" 
																				property="medicionId.serieId" />periodo<bean:write 
																				name="medicion" 
																				property="medicionId.periodo" />ano<bean:write 
																				name="medicion" 
																				property="medicionId.ano" />" 
															id="valorIndId<bean:write 
																				name="medicion" 
																				property="medicionId.indicadorId" />serId<bean:write 
																				name="medicion" 
																				property="medicionId.serieId" />periodo<bean:write 
																				name="medicion" 
																				property="medicionId.periodo" />ano<bean:write 
																				name="medicion" 
																				property="medicionId.ano" />" 
															value="<bean:write name="medicion" property="valorString" />">
														<input 
															type="hidden" 
															name="anteriorvalorIndId<bean:write 
																				name="medicion" 
																				property="medicionId.indicadorId" />serId<bean:write 
																				name="medicion" 
																				property="medicionId.serieId" />periodo<bean:write 
																				name="medicion" 
																				property="medicionId.periodo" />ano<bean:write 
																				name="medicion" 
																				property="medicionId.ano" />"
															id="anteriorvalorIndId<bean:write 
																				name="medicion" 
																				property="medicionId.indicadorId" />serId<bean:write 
																				name="medicion" 
																				property="medicionId.serieId" />periodo<bean:write 
																				name="medicion" 
																				property="medicionId.periodo" />ano<bean:write 
																				name="medicion" 
																				property="medicionId.ano" />" 
															value="<bean:write name="medicion" property="valorString" />">
													</logic:notEqual>
													<logic:equal scope="page" name="indicadorCualitativo" value="true"  >
														<bean:define id="medicionCategoriaId" toScope="page" value=""></bean:define>
														<logic:notEmpty name="medicion" property="valor">
															<bean:define id="medicionCategoriaId" toScope="page">
																<bean:write name="medicion" property="valor" format="###0" />
															</bean:define>
														</logic:notEmpty>
														<select size="1" style="width: 130px;" onfocus="<bean:write name="medicionSoloLectura" scope="page"/>" 
															name="valorIndId<bean:write name="medicion" property="medicionId.indicadorId" />
																  			serId<bean:write name="medicion" property="medicionId.serieId" />
																  			periodo<bean:write name="medicion" property="medicionId.periodo" />
																  			ano<bean:write name="medicion" property="medicionId.ano" />"
															id="valorIndId<bean:write name="medicion" property="medicionId.indicadorId" />
																  			serId<bean:write name="medicion" property="medicionId.serieId" />
																  			periodo<bean:write name="medicion" property="medicionId.periodo" />
																  			ano<bean:write name="medicion" property="medicionId.ano" />" 
															value="<bean:write name="medicion" property="valor" format="###0" />" 
															class="cuadroTexto">
															<option value=""></option>
															<logic:iterate scope="page" name="indicador" property="escalaCualitativa" id="categoriaIndicador">
																<bean:define id="categoria" toScope="page" name="categoriaIndicador" property="categoriaMedicion" scope="page"></bean:define>
																<bean:define id="categoriaSeleccionada" toScope="page" value=""></bean:define>
																<logic:equal name="categoria" property="categoriaId" value="<%= medicionCategoriaId %>">
																	<bean:define id="categoriaSeleccionada" toScope="page" value="selected"></bean:define>
																</logic:equal>
																<option <bean:write name="categoriaSeleccionada" scope="page" /> value="<bean:write name="categoria" property="categoriaId"/>"><bean:write name="categoria" property="nombre" /></option>
															</logic:iterate>
														</select>
														<input 
															type="hidden" 
															name="anteriorvalorIndId<bean:write name="medicion" property="medicionId.indicadorId" />
																  			serId<bean:write name="medicion" property="medicionId.serieId" />
																  			periodo<bean:write name="medicion" property="medicionId.periodo" />
																  			ano<bean:write name="medicion" property="medicionId.ano" />" 
															id="anteriorvalorIndId<bean:write name="medicion" property="medicionId.indicadorId" />
																  			serId<bean:write name="medicion" property="medicionId.serieId" />
																  			periodo<bean:write name="medicion" property="medicionId.periodo" />
																  			ano<bean:write name="medicion" property="medicionId.ano" />" 
															value="<bean:write name="medicion" property="valor" format="###0" />">
													</logic:equal>
												</td>
											</logic:iterate>
										</logic:equal>
								</tr>
						</logic:iterate>
					</logic:notEmpty>
				</table>
			</vgcinterfaz:contenedorForma>
		</html:form>
	</tiles:put>
</tiles:insert>
