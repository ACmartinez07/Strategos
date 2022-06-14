<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/06/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarActividadForm" property="actividadId" value="0">
			<vgcutil:message key="jsp.editaractividad.titulo.nuevo" />
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarActividadForm" property="actividadId" value="0">
			<vgcutil:message key="jsp.editaractividad.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/unidadesmedida/unidadesMedidaJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/editarIndicadorFormulaJs.jsp'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/actividadesJs/actividadesJs.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarActividadForm" property="bloqueado">
				<bean:write name="editarActividadForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarActividadForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			// Inicializar botones de los cuadros Numéricos
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			var _fechaInicio = null;
			var _fechaFin = null;
			var _unidadId = null;
			var _tipoMedicion = null;
			var _setCloseParent = false;
			
			function calcularFechasActividad(campoModificado) 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planificacionseguimiento/actividades/editarActividadFuncion" />?funcion=calcularFechasActividad&comienzoPlan=' + document.editarActividadForm.comienzoPlan.value + '&finPlan=' + document.editarActividadForm.finPlan.value + '&duracion=' + document.editarActividadForm.duracionPlan.value + '&campoModificado=' + campoModificado, document.editarActividadForm.informacionFechas, 'finalizarCalculoFechasActividad()');
			}
			
			function calcularFechasActividadComienzoPlan() 
			{
				setTimeout("calcularFechasActividad('comienzoPlan')", 500);
			}
			
			function calcularFechasActividadFinPlan() 
			{
				setTimeout("calcularFechasActividad('finPlan')", 500);
			}
			
			function finalizarCalculoFechasActividad() 
			{
				var texto = document.editarActividadForm.informacionFechas.value;
				texto = texto.substring('comienzoPlan:'.length);
				document.editarActividadForm.comienzoPlan.value = texto.substring(0, texto.indexOf('comienzoPlanTexto:'));
				texto = texto.substring(texto.indexOf('comienzoPlanTexto:') + 'comienzoPlanTexto:'.length, texto.length);
				document.editarActividadForm.comienzoPlanTexto.value = texto.substring(0, texto.indexOf('finPlan:'));
				texto = texto.substring(texto.indexOf('finPlan:') + 'finPlan:'.length, texto.length);
				document.editarActividadForm.finPlan.value = texto.substring(0, texto.indexOf('finPlanTexto:'));
				texto = texto.substring(texto.indexOf('finPlanTexto:') + 'finPlanTexto:'.length, texto.length);
				document.editarActividadForm.finPlanTexto.value = texto.substring(0, texto.indexOf('duracion:'));
				document.editarActividadForm.duracionPlan.value = texto.substring(texto.indexOf('duracion:') + 'duracion:'.length);
			}
			
			var ultimaActualizacionDuracion = 0;
			
			function actualizarFechasActividadPorDuracion() 
			{
				var ahora = (new Date()).getTime();
				var diferencia = ahora - ultimaActualizacionDuracion;
				if (diferencia > 1500) 
					calcularFechasActividad('duracion');
			}
			
			function eventoCambioDuracion() 
			{
				ultimaActualizacionDuracion = (new Date()).getTime();
				setTimeout("actualizarFechasActividadPorDuracion()", 2000);
			}
			
			function validar(forma) 
			{
				if (validateEditarActividadForm(forma)) 
				{
					if (document.editarActividadForm.naturaleza.value == <bean:write name="editarActividadForm" property="naturalezaActividadFormula" />) 
					{
						if (document.editarActividadForm.formula.value == '') 
						{
							alert('<vgcutil:message key="validation.editarindicador.formula.requerida" />');
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarActividadPanelDefinicion"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					}
					if (document.editarActividadForm.actividadId.value != null && document.editarActividadForm.actividadId.value != 0 && _fechaInicio != null && _fechaInicio != document.editarActividadForm.comienzoPlanTexto.value)
					{
						if (!confirm('<vgcutil:message key="jsp.editaractividad.mensaje.comienzo.confirmar" />'))
							return false;
						else
							document.editarActividadForm.eliminarMediciones.value = true;
					}
					if (document.editarActividadForm.eliminarMediciones.value == "false" && document.editarActividadForm.actividadId.value != null && document.editarActividadForm.actividadId.value != 0 && _fechaFin != null && _fechaFin != document.editarActividadForm.finPlanTexto.value)
					{
						if (!confirm('<vgcutil:message key="jsp.editaractividad.mensaje.fin.confirmar" />'))
							return false;
						else
							document.editarActividadForm.eliminarMediciones.value = true;
					}
					if (document.editarActividadForm.eliminarMediciones.value == "false" && document.editarActividadForm.actividadId.value != null && document.editarActividadForm.actividadId.value != 0 && _unidadId != null && _unidadId != document.editarActividadForm.unidadId.value)
					{
						if (!confirm('<vgcutil:message key="jsp.editaractividad.mensaje.unidad.confirmar" />'))
							return false;
						else
							document.editarActividadForm.eliminarMediciones.value = true;
					}

					if (document.editarActividadForm.eliminarMediciones.value == "false" && document.editarActividadForm.actividadId.value != null && document.editarActividadForm.actividadId.value != 0)
					{
						if (_tipoMedicion == 0 && document.editarActividadForm.tipoMedicion[1].checked)
						{
							if (!confirm('<vgcutil:message key="jsp.editaractividad.mensaje.tipomedicion.confirmar" />'))
								return false;
							else
								document.editarActividadForm.eliminarMediciones.value = true;
						}
						else if (_tipoMedicion == 1 && document.editarActividadForm.tipoMedicion[0].checked)
						{
							if (!confirm('<vgcutil:message key="jsp.editaractividad.mensaje.tipomedicion.confirmar" />'))
								return false;
							else
								document.editarActividadForm.eliminarMediciones.value = true;
						}
					}
					
					if (document.editarActividadForm.porcentajeAmarillo.value != "")
						document.editarActividadForm.hayValorPorcentajeAmarillo.value = true;
					if (document.editarActividadForm.porcentajeVerde.value != "")
						document.editarActividadForm.hayValorPorcentajeVerde.value = true;
					
			 		if (!fechaValida(document.editarActividadForm.comienzoPlan))
			 			return;

			 		if (!fechaValida(document.editarActividadForm.finPlan))
			 			return;
					
			 		if (!fechasRangosValidos(document.editarActividadForm.comienzoPlan, document.editarActividadForm.finPlan))
			 			return;
			 		
					window.document.editarActividadForm.action = '<html:rewrite action="/planificacionseguimiento/actividades/guardarActividad"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}
			
			function guardar() 
			{
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="general" nombreContenedor="editarActividad"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarActividadForm))
				{
					activarBloqueoEspera();
					window.document.editarActividadForm.submit();
				}
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
			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
			function limpiarResponsable(campoId, campoNombre) 
			{
				campoId.value = "";
				campoNombre.value = "";
			}
			
			function seleccionarResponsableFijarMeta() 
			{
				abrirSelectorResponsables('editarActividadForm', 'responsableFijarMeta', 'responsableFijarMetaId', document.editarActividadForm.responsableFijarMetaId.value, '<bean:write name="editarActividadForm" property="organizacionId"/>');
			}
			
			function seleccionarResponsableLograrMeta() 
			{
				abrirSelectorResponsables('editarActividadForm', 'responsableLograrMeta', 'responsableLograrMetaId', document.editarActividadForm.responsableLograrMetaId.value, '<bean:write name="editarActividadForm" property="organizacionId"/>');
			}
			
			function seleccionarResponsableSeguimiento() 
			{
				abrirSelectorResponsables('editarActividadForm', 'responsableSeguimiento', 'responsableSeguimientoId', document.editarActividadForm.responsableSeguimientoId.value, '<bean:write name="editarActividadForm" property="organizacionId"/>');
			}
			
			function seleccionarResponsableCargarMeta() 
			{
				abrirSelectorResponsables('editarActividadForm', 'responsableCargarMeta', 'responsableCargarMetaId', document.editarActividadForm.responsableCargarMetaId.value, '<bean:write name="editarActividadForm" property="organizacionId"/>');
			}
			
			function seleccionarResponsableCargarEjecutado() 
			{
				abrirSelectorResponsables('editarActividadForm', 'responsableCargarEjecutado', 'responsableCargarEjecutadoId', document.editarActividadForm.responsableCargarEjecutadoId.value, '<bean:write name="editarActividadForm" property="organizacionId"/>');
			}
			
			function seleccionarFechaComienzoPlan() 
			{
				mostrarCalendarioConFuncionCierre('document.editarActividadForm.comienzoPlan' , document.editarActividadForm.comienzoPlan.value, '<vgcutil:message key="formato.fecha.corta" />', 'calcularFechasActividadComienzoPlan()', false);
			}
			
			function seleccionarFechaFinPlan() 
			{
				mostrarCalendarioConFuncionCierre('document.editarActividadForm.finPlan' , document.editarActividadForm.finPlan.value, '<vgcutil:message key="formato.fecha.corta" />', 'calcularFechasActividadFinPlan()', false);
			}
			
			var panelDefinicionDesactivado = true;
			
			function eventoCambioNaturaleza() 
			{
				if (document.editarActividadForm.naturaleza.value == '<bean:write name="editarActividadForm" property="naturalezaActividadSimple" />') 
					panelDefinicionDesactivado = true;
				else if (document.editarActividadForm.naturaleza.value == '<bean:write name="editarActividadForm" property="naturalezaActividadFormula" />') 
					panelDefinicionDesactivado = false;
				else if (document.editarActividadForm.naturaleza.value == '<bean:write name="editarActividadForm" property="naturalezaActividadAsociado" />') 
					panelDefinicionDesactivado = false;
				
				setPanelDefinicion();
			}

			function setPanelDefinicion() 
			{
				if (document.editarActividadForm.naturaleza.value == <bean:write name="editarActividadForm" property="naturalezaActividadSimple" />) 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionSimple" nombreContenedor="editarActividadPanelDefinicion" />
				else if (document.editarActividadForm.naturaleza.value == <bean:write name="editarActividadForm" property="naturalezaActividadFormula" />)
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionFormula" nombreContenedor="editarActividadPanelDefinicion" />
				else
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionSimple" nombreContenedor="editarActividadPanelDefinicion" />
			}
			
			function  mostrarPanelActividad(nombrePanelActividad) 
			{
				if (nombrePanelActividad == 'definicion') 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarActividad" />
				else 
					alert('<vgcutil:message key="jsp.editaractividad.mensaje.opcionnovalida" />');
			}

			function getURL()
			{
				return '&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.editarActividadForm.frecuencia.value  +'&excluirIds=';
			}

			// Formato de string de insumos:
			// [correlativo:##][indicadorId:###][serieId:###][indicadorNombre:###][serieNombre:###][rutaCompleta:###]; ....
			function agregarActividadIndicadoresInsumoFormula() 
			{
				seleccionadoIds = document.editarActividadForm.indicadorInsumoSeleccionadoIds.value.split('!;!');
				seleccionadoNombres = document.editarActividadForm.indicadorInsumoSeleccionadoNombres.value.split('!;!');
				seleccionadoRutasCompletas = document.editarActividadForm.indicadorInsumoSeleccionadoRutasCompletas.value.split('!;!');

				var listaInsumos = document.editarActividadForm.insumosFormula.value;
				var correlativo = 0;

				if (listaInsumos != null) 
				{
					if (listaInsumos == '') 
					{
						// no hay insumos en la lista
						correlativo = 1;
					} 
					else 
					{
						strBuscada = ']' + '!;!' + '[';
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
					partesId = seleccionadoIds[i].split('!@!');
					nombresIndicador = seleccionadoNombres[i].split('!@!');

					insumoBuscado = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']';

					if (listaInsumos.indexOf(insumoBuscado) < 0) 
					{
						var separadorIndicadores = '';
						if (listaInsumos != '') 
							separadorIndicadores = '!;!';
						if (seleccionadoRutasCompletas[i] != '!ELIMINADO!') 
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
				document.editarActividadForm.insumosFormula.value = listaInsumos;
				setListaInsumosFormula();
			}

			function setListaInsumosFormula() 
			{
				var tablaListaInsumos = document.getElementById('tablaListaInsumosFormula');

				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);

				// Se recorre la lista de insumos
				var insumos = document.editarActividadForm.insumosFormula.value.split('!;!');
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
						//serie = '[' + strTemp.substring(indice + 'serieNombre:'.length + 2, strTemp.length);
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
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
										document.getElementById('tablaListaInsumosFormula'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
							paintListaInsumosFormulaColumnaSerie(selAnterior, document.getElementById('tablaListaInsumosFormula'), "blue");
							mostrarRutaCompletaInsumoFormula();
						};
						tbody.appendChild(row);
						if (numFilas == 0) 
						{
							selectRowColors(0,
										document.getElementById('tablaListaInsumosFormula'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
						}
					}
				}
				mostrarRutaCompletaInsumoFormula();
			}

			function mostrarRutaCompletaInsumoFormula() 
			{
				var tabla = document.getElementById('tablaListaInsumosFormula');
				numFilas = tabla.getElementsByTagName("tr").length;
				var rutaCompletaInsumo = '';

				if (numFilas > 0) 
				{
					// Si existen indicadores fórmula
					// Se modifica el string que contiene los insumos seleccionados
					var posBuscada = parseInt(document.editarActividadForm.insumoSeleccionado.value);
					valorCorrelativoFormula = '';
					var insumosFormula = document.editarActividadForm.insumosFormula.value;
					if (posBuscada == 0) 
					{
						var strBuscada = '[rutaCompleta:';
						var index = insumosFormula.indexOf(strBuscada);
						insumosFormula = insumosFormula.substring(index + strBuscada.length, insumosFormula.length);
						var index2 = insumosFormula.indexOf(']' + '!;!');
						if (index2 < 0) // solo hay 1 indicador insumo
							rutaCompletaInsumo = insumosFormula.substring(0, insumosFormula.length - 1);
						else 
							rutaCompletaInsumo = insumosFormula.substring(0, index2);
					} 
					else if ((posBuscada + 1) == numFilas) 
					{
						var strBuscada = '[rutaCompleta:';
						var index = insumosFormula.lastIndexOf(strBuscada);
						rutaCompletaInsumo = insumosFormula.substring(index + strBuscada.length, insumosFormula.length - 1);
					} 
					else 
					{
						var strBuscada = ']' + '!;!' + '[';
						var index = 0;
						for (var i = 0; i < posBuscada; i++) 
						{
							index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
							if (index > -1) 
								index= index + strBuscada.length;
						}
						if (index > -1) 
						{
							insumosFormula = insumosFormula.substring(index, insumosFormula.length);
							strBuscada = '[rutaCompleta:';
							index = insumosFormula.indexOf(strBuscada) + strBuscada.length;
							strBuscada = ']' + '!;!' + '[';
							index2 = insumosFormula.indexOf(strBuscada);
							rutaCompletaInsumo = insumosFormula.substring(index, index2);
						}
					}
					setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo);
				}
			}

			function setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo) 
			{
				var tablaRutaCompletaInsumo = document.getElementById('tablaRutaCompletaInsumoFormula');

				// Se borra la lista de insumos
				while (tablaRutaCompletaInsumo.getElementsByTagName("tr").length > 0) 
					tablaRutaCompletaInsumo.deleteRow(0);

				if (rutaCompletaInsumo == '') 
					return;

				// Se recorre la lista de insumos
				var partesRuta = rutaCompletaInsumo.split('!#!');
				nroPartes = partesRuta.length;
				for (var i = 0; i < partesRuta.length; i++) 
				{
					var numFilas = tablaRutaCompletaInsumo.getElementsByTagName("tr").length;
					var tbody = tablaRutaCompletaInsumo.getElementsByTagName("TBODY")[0];
					var row = document.createElement("TR");
					for (var j = 0; j < i; j++) 
					{
						var tdIdent = document.createElement("TD");
						tdIdent.appendChild(document.createTextNode('-'));
						tdIdent.width = '5px';
						row.appendChild(tdIdent);
					}
					var tdValor = document.createElement("TD");
					tdValor.appendChild(document.createTextNode(partesRuta[i]));
					if (nroPartes > 1) 
						tdValor.colSpan=nroPartes;
					row.appendChild(tdValor);
					tbody.appendChild(row);
					nroPartes--;
				}

			}

			function insertarActividadInsumoEnFormula() 
			{
				var tabla = document.getElementById('tablaListaInsumosFormula');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				var insumosFormula = document.editarActividadForm.insumosFormula.value;

				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.editarindicador.noinsumosformula" />');
				else 
				{
					var posicionBuscada = parseInt(document.editarActividadForm.insumoSeleccionado.value);
					var valorInsertableFormula = '';
					if (posicionBuscada == 0) 
					{
						var index = insumosFormula.indexOf(']');
						valorInsertableFormula = insumosFormula.substring(0, index + 1);
					} 
					else if ((posicionBuscada + 1) == numFilas) 
					{
						var strBuscada = ']' + '!;!' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 1 + '!;!'.length;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						valorInsertableFormula = insumosFormula.substring(index, index2 + 1);
					} 
					else 
					{
						var strBuscada = ']' + '!;!' + '[';
						var index = 0;
						for (var i = 0; i < posicionBuscada; i++) 
						{
							index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
							if (index != -1) 
								index= index + 1 + '!;!'.length;
						}
						if (index != -1) 
						{
							index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
							valorInsertableFormula = insumosFormula.substring(index, index2 + 1);
						}
					}
					insertAtCursorPosition(document.editarActividadForm.formula, valorInsertableFormula);
				}
			}
			
			function removerActividadInsumoFormula() 
			{
				var tabla = document.getElementById('tablaListaInsumosFormula');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				var insumosFormula = document.editarActividadForm.insumosFormula.value;

				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.editarindicador.noinsumosformula" />');
				else 
				{
					// Se modifica el string que contiene los insumos seleccionados
					var posicionBuscada = parseInt(document.editarActividadForm.insumoSeleccionado.value);
					valorEliminableFormula = '';
					if (posicionBuscada == 0) 
					{
						var index = insumosFormula.indexOf(']');
						valorEliminableFormula = insumosFormula.substring(0, index + 1);
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
						var strBuscada = ']' + '!;!' + '[';
						var index = insumosFormula.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
						valorEliminableFormula = insumosFormula.substring(index, index2 + 1);
						insumosFormula = insumosFormula.substring(0, index - 1);
					} 
					else 
					{
						var strBuscada = ']' + '!;!' + '[';
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
							valorEliminableFormula = insumosFormula.substring(index, index2 + 1);
						}
						index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
						str2 = insumosFormula.substring(index + 2, insumosFormula.length);
						insumosFormula = str1 + str2;
					}
					formula = document.editarActividadForm.formula.value;
					index = formula.indexOf(valorEliminableFormula);
					while (index > -1) 
					{
						formula = formula.substring(0, index) + formula.substring(index + valorEliminableFormula.length, formula.length);
						index = formula.indexOf(valorEliminableFormula);
					}
					document.editarActividadForm.formula.value = formula;
					document.editarActividadForm.insumosFormula.value = insumosFormula;
					deleteRowColors(tabla,
									document.editarActividadForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
				}
			}

			function setListaActividadInsumosFormula() 
			{
				var tablaListaInsumos = document.getElementById('tablaListaInsumosFormula');

				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);

				// Se recorre la lista de insumos
				var insumos = document.editarActividadForm.insumosFormula.value.split('<bean:write name="editarActividadForm" property="separadorIndicadores" ignore="true"/>');
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
						//serie = '[' + strTemp.substring(indice + 'serieNombre:'.length + 2, strTemp.length);
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
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
										document.getElementById('tablaListaInsumosFormula'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
							paintListaInsumosFormulaColumnaSerie(selAnterior, document.getElementById('tablaListaInsumosFormula'), "blue");
							mostrarRutaCompletaInsumoFormula();
						};
						tbody.appendChild(row);
						if (numFilas == 0) 
						{
							selectRowColors(0,
										document.getElementById('tablaListaInsumosFormula'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
						}
					}
				}
				mostrarRutaCompletaInsumoFormula();
			}
			
			function refrescarPadre()
			{
				window.opener.actualizar();
			}
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarActividadForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planificacionseguimiento/actividades/guardarActividad" focus="nombre">

			<html:hidden property="actividadId" />
			<html:hidden property="responsableFijarMetaId" />
			<html:hidden property="responsableLograrMetaId" />
			<html:hidden property="responsableSeguimientoId" />
			<html:hidden property="responsableCargarMetaId" />
			<html:hidden property="responsableCargarEjecutadoId" />
			<html:hidden property="seleccionados" />
			<html:hidden property="frecuencia" />
			<html:hidden property="hayValorPorcentajeAmarillo" />
			<html:hidden property="hayValorPorcentajeVerde" />
			<html:hidden property="eliminarMediciones" />

			<%-- Campos hidden para el manejo de la fórmula --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">
			<input type="hidden" name="informacionFechas" />

			<vgcinterfaz:contenedorForma width="645px" height="405px" bodyAlign="center" bodyValign="middle" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarActividadForm" property="actividadId" value="0">
						<vgcutil:message key="jsp.editaractividad.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarActividadForm" property="actividadId" value="0">
						<vgcutil:message key="jsp.editaractividad.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>
				<bean:define id="esPadre" toScope="page">
					<logic:notEmpty name="editarActividadForm" property="esPadre">
						<bean:write name="editarActividadForm" property="esPadre" />
					</logic:notEmpty>
					<logic:empty name="editarActividadForm" property="esPadre">
						false
					</logic:empty>
				</bean:define>
				<bean:define id="esObjetoAsociado" toScope="page">
					<logic:notEmpty name="editarActividadForm" property="asociadaNombre">
						true
					</logic:notEmpty>
					<logic:empty name="editarActividadForm" property="asociadaNombre">
						false
					</logic:empty>
				</bean:define>

				<vgcinterfaz:contenedorPaneles height="315" width="610" nombre="editarActividad">
					<!-- Panel: General -->
					<vgcinterfaz:panelContenedor anchoPestana="85" nombre="general">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaractividad.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>
						<table style="width: 600px; border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
							<tr>
								<td>
								<table class="bordeFichaDatos">
									<!-- Campo Nombre -->
									<tr>
										<td align="right" valign="top"><vgcutil:message key="jsp.editaractividad.ficha.nombre" /></td>
										<td colspan="4"><html:text style="width: 500px;" property="nombre" size="65" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" maxlength="100" styleClass="cuadroTexto" /></td>
									</tr>
									<%-- Campo descripción --%>
									<tr>
										<td align="right"><vgcutil:message key="jsp.editaractividad.ficha.descripcion" /></td>
										<td colspan="4"><html:textarea style="width: 500px;" property="descripcion" cols="65" rows="5" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" /></td>
									</tr>

									<%-- Campo producto Esperado --%>
									<tr>
										<td align="right"><vgcutil:message key="jsp.editaractividad.ficha.productoesperado" /></td>
										<td><html:textarea style="width: 500px;" property="productoEsperado" cols="65" rows="5" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" /></td>
									</tr>
									<%-- Campo Unidad de Medida --%>
									<tr>
										<td style="vertical-align: center;"><vgcutil:message key="jsp.editaractividad.ficha.unidadmedida" />&nbsp;</td>
										<td>
											<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px;">
												<tr>
													<td>
														<html:select property="unidadId" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" styleClass="cuadroTexto" size="1" >
															<html:option value=""></html:option>
															<html:optionsCollection property="unidadesMedida" value="unidadId" label="nombre" />
														</html:select>
													</td>
													
													<td>&nbsp;&nbsp;&nbsp;</td>
													<%-- Campo Tipo de Medición --%>
													<td>
														<div>
															<table style="border-spacing: 0px; padding: 0px; width: 290px; border-style:solid; border-width:1px; border-color:#666666; font-family:Verdana; font-size:11; color:#666666; text-decoration:none;">
																<tr>
																	<td colspan="2"><b>&nbsp;<vgcutil:message key="jsp.editaractividad.ficha.titulo.mediciones" /></b></td>
																</tr>
																<tr>
																	<td><html:radio property="tipoMedicion" value="0" disabled="true"/><vgcutil:message key="jsp.editaractividad.ficha.tipomedicion.enperiodo" /></td>
																	<td><html:radio property="tipoMedicion" value="1" disabled="true"/><vgcutil:message key="jsp.editaractividad.ficha.tipomedicion.alperiodo" /></td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="right" valign="top"><vgcutil:message key="jsp.editaractividad.ficha.naturaleza" />&nbsp;</td>
										<td>
											<html:select property="naturaleza" styleClass="cuadroTexto" size="1" onchange="eventoCambioNaturaleza()" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>">
												<html:optionsCollection property="naturalezas" value="naturalezaId" label="nombre" />
											</html:select>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Responsables -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="responsables">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaractividad.pestana.responsable" />
						</vgcinterfaz:panelContenedorTitulo>

						<table style="width: 600px; border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
							<%-- Campo Responsables Fijar Meta --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editaractividad.ficha.responsablefijarmeta" /></td>
								<td>
									<html:text style="width: 350px;" property="responsableFijarMeta" onkeypress="ejecutarPorDefecto(event)" size="46" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
									<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
										<logic:empty name="editarActividadForm" property="asociadaNombre">
											<img style="cursor: pointer" onclick="seleccionarResponsableFijarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaractividad.ficha.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable(document.editarActividadForm.responsableFijarMetaId, document.editarActividadForm.responsableFijarMeta);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
										</logic:empty>
									</logic:notEqual>
								</td>
							</tr>

							<%-- Campo Responsables Lograr Meta --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editaractividad.ficha.responsablelograrmeta" /></td>
								<td>
									<logic:notEqual name="editarActividadForm" property="responsableLograrMeta" value="null">
										<html:text style="width: 350px;" property="responsableLograrMeta" onkeypress="ejecutarPorDefecto(event)" size="46" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
									</logic:notEqual>									
									<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
										<logic:empty name="editarActividadForm" property="asociadaNombre">
											<img style="cursor: pointer" onclick="seleccionarResponsableLograrMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaractividad.ficha.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable(document.editarActividadForm.responsableLograrMetaId, document.editarActividadForm.responsableLograrMeta);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
										</logic:empty>
									</logic:notEqual>
								</td>
							</tr>

							<%-- Campo Responsables Seguimiento --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editaractividad.ficha.responsableseguimiento" /></td>
								<td>
									<html:text style="width: 350px;" property="responsableSeguimiento" onkeypress="ejecutarPorDefecto(event)" size="46" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
									<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
										<logic:empty name="editarActividadForm" property="asociadaNombre">
											<img style="cursor: pointer" onclick="seleccionarResponsableSeguimiento();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaractividad.ficha.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable(document.editarActividadForm.responsableSeguimientoId, document.editarActividadForm.responsableSeguimiento);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
										</logic:empty>
									</logic:notEqual>
								</td>
							</tr>

							<%-- Campo Responsables Cargar Meta --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editaractividad.ficha.responsablecargarmeta" /></td>
								<td>
									<html:text style="width: 350px;" property="responsableCargarMeta" onkeypress="ejecutarPorDefecto(event)" size="46" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
									<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
										<logic:empty name="editarActividadForm" property="asociadaNombre">
											<img style="cursor: pointer" onclick="seleccionarResponsableCargarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaractividad.ficha.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable(document.editarActividadForm.responsableCargarMetaId, document.editarActividadForm.responsableCargarMeta);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
										</logic:empty>
									</logic:notEqual>
								</td>
							</tr>

							<%-- Campo Responsables Cargar Ejecutado --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editaractividad.ficha.responsablecargarejecutado" /></td>
								<td>
									<html:text style="width: 350px;" property="responsableCargarEjecutado" onkeypress="ejecutarPorDefecto(event)" size="46" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" />&nbsp;
									<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
										<logic:empty name="editarActividadForm" property="asociadaNombre">
											<img style="cursor: pointer" onclick="seleccionarResponsableCargarEjecutado();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaractividad.ficha.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable(document.editarActividadForm.responsableCargarEjecutadoId, document.editarActividadForm.responsableCargarEjecutado);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
										</logic:empty>
									</logic:notEqual>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Definicion -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="definicion" onclick="mostrarPanelActividad('definicion');">
					
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaractividad.pestana.definicion" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<vgcinterfaz:contenedorPaneles height="270" width="595" nombre="editarActividadPanelDefinicion" mostrarSelectoresPaneles="false">
						
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionSimple" mostrarBorde="false">
								<table class="panelContenedor" style="border-spacing: 0px; border-collapse: separate; padding: 3px;">
									<!-- Campo Codigo de Enlace-->
									<tr>
										<td style="text-align: left; width: 150px;"><vgcutil:message key="jsp.editarindicador.ficha.codigoenlace" /></td>
										<td><html:text property="codigoEnlace" size="55" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>" maxlength="100" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
									</tr>
									<!-- Campo Codigo de Enlace Parcial-->
									<tr>
										<td style="text-align: left; width: 150px;"><vgcutil:message key="jsp.editarindicador.ficha.codigoparcialenlace" /></td>
										<td><html:text property="enlaceParcial" size="55" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>" maxlength="20" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
									</tr>
									<tr height="210px">
										<td colspan="2">&nbsp;</td>
									</tr>
								</table>
								<%-- 
								<table style="border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
									<tr>
										<td align="left">
											<table style="border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
								--%>
												<%-- Campo Iniciativa --%>
								<%-- 
												<tr>
													<td width="90px" align="right">
														<vgcutil:message key="jsp.editaractividad.ficha.asociada.iniciativa" />
													</td>
													<td>
														<html:text property="asociadaNombre" size="80" readonly="true" disabled="false" styleClass="cuadroTexto" />
													</td>
												</tr>
									--%>							
												<%-- Campo Organizacion --%>
									<%-- 
												<tr>
													<td align="right">
														<vgcutil:message key="jsp.editaractividad.ficha.asociada.organizacion" />
													</td>
													<td>
														<html:text property="asociadaOrganizacion" size="80" readonly="true" disabled="false" styleClass="cuadroTexto" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr height="100%">
										<td colspan="2">&nbsp;</td>
									</tr>
								</table>
								--%>		
								
							</vgcinterfaz:panelContenedor>

							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="definicionFormula" mostrarBorde="false">
								<%-- Naturaleza Fórmula --%>
								<table style="width: 600px; border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">

									<!-- Indicadores -->
									<tr>
										<td align="center">
											<div>
												<table style="width: 100%; height: 130px; border-style:solid; border-width:1px; border-color:#666666; font-family:Verdana; font-size:11; color:#666666; text-decoration:none;">
													<tr>
														<td colspan="3"><b><vgcutil:message key="jsp.editarindicador.ficha.formula.indicadores" /></b></td>
													</tr>
													<tr>
														<td>
															<input id="insumoSeleccionado" type="hidden" name="insumoSeleccionado" /> 
															<html:hidden property="insumosFormula" /> 
															<input type="button" style="width:90%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.agregarinsumo" />" onclick="seleccionarIndicadoresInsumoFormula('editarActividadForm', 'agregarActividadIndicadoresInsumoFormula()', getURL());">
														</td>
														<td><input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertarenformula" />" onclick="insertarActividadInsumoEnFormula();"></td>
														<td><input type="button" style="width:100%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.removerinsumo" />" onclick="removerActividadInsumoFormula();"></td>
													</tr>
													<tr height="50%">
														<td colspan="3">
															<bean:define scope="page" id="anchoListadoIndicadoresInsumos" value="350"></bean:define>
															<logic:equal name="editarActividadForm" property="desdeIniciativasPlanes" scope="session" value="true">
																<bean:define scope="page" id="anchoListadoIndicadoresInsumos" value="100%"></bean:define>
															</logic:equal>
															<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoListadoIndicadoresInsumos %>" splitterId="splitInsumosFormula" overflowPanelDerecho="auto" overflowPanelIzquierdo="auto">
																<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitInsumosFormula">
																	<table id="tablaListaInsumosFormula">
																		<tbody class="cuadroTexto">
																		</tbody>
																	</table>
																</vgcinterfaz:splitterHorizontalPanelIzquierdo>
																<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitInsumosFormula">
																	<table id="tablaRutaCompletaInsumoFormula">
																		<tbody class="cuadroTexto">
																		</tbody>
																	</table>
																</vgcinterfaz:splitterHorizontalPanelDerecho>
															</vgcinterfaz:splitterHorizontal>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>

									<!-- Editor de Fórmula -->
									<tr>
										<td align="center">
											<table class="contenedorBotonesSeleccion" style="width: 100%;">
												<tr>
													<td colspan="4"><b><vgcutil:message key="jsp.editarindicador.ficha.formula.editorformula" /></b></td>
												</tr>
												<tr>
													<td colspan="4">
														<html:textarea property="formula" rows="4" style="width:99%" styleClass="cuadroTexto" onkeyup="validarTextoFormula()" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:textarea>
													</td>
												</tr>
												<tr>
													<td>
														<input id="insumoFormulaSeleccionado" type="hidden" name="insumoFormulaSeleccionado" />
														<table style="border-spacing: 0px; padding: 2px; boder: 0px">
															<tr>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="+" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '+')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="-" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '-')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="*" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '*')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="/" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '/')"></td>
															</tr>
															<tr>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="(" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '(')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="." onclick="insertAtCursorPosition(document.editarActividadForm.formula, '.')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value=")" onclick="insertAtCursorPosition(document.editarActividadForm.formula, ')')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="^" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '^')"></td>
															</tr>
														</table>
													</td>
													<td>
														<table style="border-spacing: 0px; padding: 2px; boder: 0px">
															<tr>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="0" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '0')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="1" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '1')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="2" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '2')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="3" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '3')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="4" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '4')"></td>
															</tr>
															<tr>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="5" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '5')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="6" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '6')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="7" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '7')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="8" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '8')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="9" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '9')"></td>
															</tr>
														</table>
													</td>
													<td>
														<table style="border-spacing: 0px; padding: 2px; boder: 0px">
															<tr>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value=":S" onclick="insertAtCursorPosition(document.editarActividadForm.formula, ':S')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value=":@" onclick="insertAtCursorPosition(document.editarActividadForm.formula, ':@')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value=":#" onclick="insertAtCursorPosition(document.editarActividadForm.formula, ':#')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value=":%" onclick="insertAtCursorPosition(document.editarActividadForm.formula, ':%')"></td>
																<td><input type="button" style="width:25px;height:20px" class="cuadroTexto" value="[P]" onclick="insertAtCursorPosition(document.editarActividadForm.formula, '[P]')"></td>
															</tr>
															<tr>
																<td colspan="5">
																	<input type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.limpiar" />" onclick="document.editarActividadForm.formula.value=''">
																</td>
															</tr>
														</table>
													</td>
													<td>
														<table style="border-spacing: 0px; padding: 2px; boder: 0px">
															<tr>
																<td>
																	<html:select property="funcion" styleClass="cuadroTexto">
																		<html:optionsCollection property="funcionesFormula" value="interfaz" label="nombre" />
																	</html:select>
																</td>
															</tr>
															<tr>
																<td colspan="5">
																	<input type="button" style="width:100%;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarindicador.ficha.formula.insertar" />" onclick="insertAtCursorPosition(document.editarActividadForm.formula, document.editarActividadForm.funcion.options[document.editarActividadForm.funcion.selectedIndex].value)">
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>
							
						</vgcinterfaz:contenedorPaneles>							

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Adicional -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="adicional">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaractividad.pestana.adicional" />
						</vgcinterfaz:panelContenedorTitulo>

						<table style="width: 600px; border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
							<tr>
								<td style="width: 50%;">
									<table class="panelContenedor" style="border-spacing: 0px; padding: 3px; height: 90px; width: 100%;">
										<tr>
											<td align="left"><b><vgcutil:message key="jsp.editaractividad.ficha.fecha" /></b></td>
											<td>&nbsp;</td>
										</tr>
	
										<%-- Campo Fecha de comienzo --%>
										<tr>
											<td align="right"><vgcutil:message key="jsp.editaractividad.ficha.fechacomienzo" /></td>
											<td>
												<html:text 
													property="comienzoPlan" 
													onkeypress="ejecutarPorDefecto(event)" 
													size="15" 
													maxlength="10"
													disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>"
													onblur="calcularFechasActividad('comienzoPlan')" 
													styleClass="cuadroTexto" />
													<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
														<logic:notEqual name="editarActividadForm" property="esPadre" value="true">
															<logic:empty name="editarActividadForm" property="asociadaNombre">
																<span style="padding:2px">
																	<img 
																		style="cursor: pointer" 
																		onclick="seleccionarFechaComienzoPlan();" 
																		src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																		border="0" 
																		width="10" 
																		height="10" 
																		title="<vgcutil:message 
																		key="boton.calendario.alt" />">
																</span>
															</logic:empty>
														</logic:notEqual>
													</logic:notEqual>
											</td>
										</tr>
	
										<%-- Campo Fecha de Fin --%>
										<tr>
											<td align="right"><vgcutil:message key="jsp.editaractividad.ficha.fechafin" /></td>
											<td>
												<html:text 
													property="finPlan" 
													onkeypress="ejecutarPorDefecto(event)" 
													size="15" 
													maxlength="10"
													disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>"
													onblur="calcularFechasActividad('finPlan')" 
													styleClass="cuadroTexto" />
													<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
														<logic:notEqual name="editarActividadForm" property="esPadre" value="true">
															<logic:empty name="editarActividadForm" property="asociadaNombre">
																<span style="padding:2px">
																	<img 
																		style="cursor: pointer" 
																		onclick="seleccionarFechaFinPlan();" 
																		src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
																		border="0" 
																		width="10" 
																		height="10" 
																		title="<vgcutil:message key="boton.calendario.alt" />">
																</span>
															</logic:empty>
														</logic:notEqual>
													</logic:notEqual>
											</td>
										</tr>
	
										<%-- Campo Duración --%>
										<tr>
											<td align="right">&nbsp;<vgcutil:message key="jsp.editaractividad.ficha.duracion" /></td>
											<td align="left">
											<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px; boder: 0px">
												<tr>
													<td valign="middle">
														<html:text property="duracionPlan" size="15" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esPadre) || Boolean.parseBoolean(esObjetoAsociado) %>" styleClass="cuadroTexto" />
													</td>
													<td valign="middle">
														<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
															<logic:notEqual name="editarActividadForm" property="esPadre" value="true">
																<logic:empty name="editarActividadForm" property="asociadaNombre">
																	<img id="botonDuracionPlan" name="botonDuracionPlan" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/actividadesJs/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" />
																</logic:empty>
															</logic:notEqual>
														</logic:notEqual>
													</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
								</td>

								<%-- Campo Períodos --%>
								<td style="width: 50%;">
									<table class="panelContenedor" style="border-spacing: 0px; padding: 3px; height: 90px; width: 100%;">
										<tr>
											<td><b><vgcutil:message key="jsp.editaractividad.ficha.periodo" /></b></td>
											<td>&nbsp;</td>
										</tr>
										<%-- Campo Período de fecha comienzo --%>
										<tr>
											<td><html:text property="comienzoPlanTexto" size="35" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" styleClass="cuadroTexto"></html:text></td>
										</tr>
										<%-- Campo Período de fecha fin --%>
										<tr>
											<td><html:text property="finPlanTexto" size="35" readonly="true" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" styleClass="cuadroTexto"></html:text></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" valign="top"><hr width="100%"></td>
							</tr>
							<tr>
								<%-- Campo recurso humano --%>
								<td style="width: 50%;">
									<table class="panelContenedor" style="border-spacing: 0px; padding: 3px; height: 120px; width: 100%;">
										<tr>
											<td align="left"><b><vgcutil:message key="jsp.editaractividad.ficha.recursohumano" /></b></td>
										</tr>
										<tr>
											<td><html:textarea style="width: 100%;" property="recursosHumanos" cols="38" rows="8" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" /></td>
										</tr>
									</table>
								</td>

								<%-- Campo recurso Material --%>
								<td style="width: 50%;">
									<table class="panelContenedor" style="border-spacing: 0px; padding: 3px; height: 120px; width: 100%;">
										<tr>
											<td align="left"><b><vgcutil:message key="jsp.editaractividad.ficha.recursomaterial" /></b></td>
										</tr>
										<tr>
											<td><html:textarea style="width: 100%;" property="recursosMateriales" cols="38" rows="8" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" /></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Imputaciones -->
					<vgcinterfaz:panelContenedor anchoPestana="160" nombre="imputaciones">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaractividad.pestana.alertas.imputaciones" />
						</vgcinterfaz:panelContenedorTitulo>
						<table style="border-spacing: 0px; padding: 3px; boder:0px; border-width:0px; font-weight:normal; font-size:11px; color:#666666; font-family:Verdana; text-decoration:none;">
							<tr>
								<td align="left">
									<div>
										<table class="contenedorBotonesSeleccion" style="width: 600px">
											<tr>
												<td align="left" colspan="4"><b>&nbsp;<vgcutil:message key="jsp.editaractividad.ficha.alertas" /></b></td>
											</tr>
											<%-- Porcentaje zona verde --%>
											<tr>
												<td align="left" width="200px">&nbsp;<vgcutil:message key="jsp.editaractividad.ficha.porcentaje.zonaverde" /></td>
												<td align="left" colspan="3">
													<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px; boder: 0px">
														<tr>
															<td valign="middle" align="left">
																<html:text size="5" property="porcentajeVerde" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
															</td>
															<td valign="middle" align="left">&nbsp;%</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</table>
												</td>
											</tr>
		
											<%-- Porcentaje zona amarilla --%>
											<tr>
												<td align="left" width="200px">&nbsp;<vgcutil:message key="jsp.editaractividad.ficha.porcentaje.zonaamarilla" /></td>
												<td align="left" colspan="3">
													<table class="bordeFichaDatos" style="border-spacing: 0px; padding: 0px; boder: 0px">
														<tr>
															<td valign="middle" align="left">
																<html:text size="5" property="porcentajeAmarillo" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) || Boolean.parseBoolean(esObjetoAsociado) %>" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
															</td>
															<td valign="middle" align="left">&nbsp;%</td>
															<td width="40px">&nbsp;</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>
						<map name="MapControlUpDown1" id="MapControlUpDown1">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('porcentajeVerde')" onmouseout="normalAction('porcentajeVerde')" onmousedown="iniciarConteoContinuo('porcentajeVerde');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('porcentajeVerde')" onmouseout="normalAction('porcentajeVerde')" onmousedown="iniciarConteoContinuo('porcentajeVerde');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown2" id="MapControlUpDown2">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZonaAmarilla')" onmouseout="normalAction('botonZonaAmarilla')" onmousedown="iniciarConteoContinuo('porcentajeAmarillo');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZonaAmarilla')" onmouseout="normalAction('botonZonaAmarilla')" onmousedown="iniciarConteoContinuo('porcentajeAmarillo');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown3" id="MapControlUpDown3">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonDuracionPlan')" onmouseout="normalAction('botonDuracionPlan')" onmousedown="iniciarConteoContinuo('duracionPlan');aumentoConstante()" onmouseup="finalizarConteoContinuo(); eventoCambioDuracion()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonDuracionPlan')" onmouseout="normalAction('botonDuracionPlan')" onmousedown="iniciarConteoContinuo('duracionPlan');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>

					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarActividadForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarActividadForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<script>
			eventoCambioNaturaleza();
			setListaActividadInsumosFormula();
			_fechaInicio = document.editarActividadForm.comienzoPlanTexto.value;
			_fechaFin = document.editarActividadForm.finPlanTexto.value;
			_unidadId = document.editarActividadForm.unidadId.value;
			if (document.editarActividadForm.tipoMedicion[0].checked)
				_tipoMedicion = 0;
			else if (document.editarActividadForm.tipoMedicion[1].checked)
				_tipoMedicion = 1;
		</script>
		<script>
			<logic:equal name="editarActividadForm" property="status" value="4">
				_setCloseParent = true;
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarActividadForm" property="status" value="0">
				_setCloseParent = false;
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarActividadForm" property="status" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.no.ok" />', 80, 300);
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
