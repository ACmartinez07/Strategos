<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (16/03/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarIndicadorForm" property="indicadorId" value="0">
			<logic:empty name="editarIndicadorForm" property="nombreIndicadorSingular">
				<vgcutil:message key="jsp.editarindicador.titulo.nuevo" />
			</logic:empty>
			<logic:notEmpty name="editarIndicadorForm" property="nombreIndicadorSingular">
				<bean:write name="editarIndicadorForm" property="nombreIndicadorSingular" />
			</logic:notEmpty>
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarIndicadorForm" property="indicadorId" value="0">
			<logic:empty name="editarIndicadorForm" property="nombreIndicadorSingular">
				<vgcutil:message key="jsp.editarindicador.titulo.modificar" />
			</logic:empty>
			<logic:notEmpty name="editarIndicadorForm" property="nombreIndicadorSingular">
				<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write name="editarIndicadorForm" property="nombreIndicadorSingular" />
			</logic:notEmpty>
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/editarIndicadorFormulaJs.jsp'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/editarIndicadorCualitativoJs.jsp'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarIndicadorForm" property="bloqueado">
				<bean:write name="editarIndicadorForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarIndicadorForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var _loaded = false;
			// Inicializar botones de los cuadros Numéricos  
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');		
	
			function validar(forma) 
			{
				if (validateEditarIndicadorForm(forma)) 
				{
					if ((document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoOrdinal" />)
						|| (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />)) 
					{
						if ((document.editarIndicadorForm.escalaCualitativa.value == null) || (document.editarIndicadorForm.escalaCualitativa.value == '')) 
						{
							showAlert('<vgcutil:message key="jsp.editarindicador.validacion.cualitativo.noescala" />', 120, 450, undefined, IMG_IMPORTANT);
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					} 
					else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
					{
						if ((document.editarIndicadorForm.indicadorSumatoriaId.value == null) || (document.editarIndicadorForm.indicadorSumatoriaId.value == '')) 
						{
							alert('<vgcutil:message key="jsp.editarindicador.validacion.sumatoria.nobase" />');
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					} 
					else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />) 
					{
						if ((document.editarIndicadorForm.indicadorPromedioId.value == null) || (document.editarIndicadorForm.indicadorPromedioId.value == '')) 
						{
							alert('<vgcutil:message key="jsp.editarindicador.validacion.promedio.nobase" />');
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					} 
					else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />) 
					{
						if ((document.editarIndicadorForm.indicadorIndiceId.value == null) || (document.editarIndicadorForm.indicadorIndiceId.value == '')) 
						{
							alert('<vgcutil:message key="jsp.editarindicador.validacion.indice.nobase" />');
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					} 
					else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaFormula" />) 
					{
						if (document.editarIndicadorForm.formula.value == '') 
						{
							alert('<vgcutil:message key="validation.editarindicador.formula.requerida" />');
							<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicion" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					}
					if ((document.editarIndicadorForm.frecuenciaOriginal.value != null) && (document.editarIndicadorForm.frecuenciaOriginal.value != '')) 
					{
						if (document.editarIndicadorForm.frecuenciaOriginal.value != document.editarIndicadorForm.frecuenciaOriginal.value) 
						{
							if ((document.editarIndicadorForm.tieneMediciones.value != null) && (document.editarIndicadorForm.tieneMediciones.value == 'true')) 
							{
								if (!confirm('<vgcutil:message key="jsp.editarindicador.validacion.confirmarcambiofrecuencia" />')) 
									return false;
							}
						}
					}
					if ((document.editarIndicadorForm.naturalezaOriginal.value != null) && (document.editarIndicadorForm.naturalezaOriginal.value != '')) 
					{
						if (document.editarIndicadorForm.naturalezaOriginal.value != document.editarIndicadorForm.naturaleza.value) 
						{
							if ((document.editarIndicadorForm.tieneMediciones.value != null) && (document.editarIndicadorForm.tieneMediciones.value == 'true')) 
							{
								if (!confirm('<vgcutil:message key="jsp.editarindicador.validacion.confirmarcambionaturaleza" />')) 
									return false;
							}
						}
					}
					window.document.editarIndicadorForm.action = '<html:rewrite action="/indicadores/guardarIndicador"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					establecerSeriesTiempo();
		
					return true;
				} 
				else 
					return false;
			}
			
			function guardar() 
			{
				document.editarIndicadorForm.nombreLargo.value = document.editarIndicadorForm.nombre.value;
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarIndicador"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarIndicadorForm)) 
					window.document.editarIndicadorForm.submit();
			}
			
			function cancelar() 
			{
				window.document.editarIndicadorForm.action = '<html:rewrite action="/indicadores/cancelarGuardarIndicador"/>';
				window.document.editarIndicadorForm.submit();
				this.close();
			}
			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
	
			function eventoOnClickSerie(serie) 
			{
			    var serieReal = '<bean:write name="editarIndicadorForm" property="serieReal" />';
				if (serie.value == serieReal) 
				{
					serie.checked = true;
					alert('<vgcutil:message key="editarindicador.nodesasociarseriereal" />');
				}
			}
	
			function asignarSeriesTiempo() 
			{
				len = document.editarIndicadorForm.elements.length;
			
				var index = 0;
			
				var checkeds = document.editarIndicadorForm.seriesIndicador.value;
				for(index = 0; index < len; index++) 
				{
					if(document.editarIndicadorForm.elements[index].name == 'serie') 
					{
						if (checkeds.indexOf('<bean:write name="editarIndicadorForm" property="separadorSeries"/>' 
												+ document.editarIndicadorForm.elements[index].value
												+ '<bean:write name="editarIndicadorForm" property="separadorSeries"/>') >= 0) 
							document.editarIndicadorForm.elements[index].checked = true;
					}
				}
			}
	
			function establecerSeriesTiempo() 
			{
				len = document.editarIndicadorForm.elements.length;
			
				var index = 0;
			
				document.editarIndicadorForm.seriesIndicador.value = '';
			
				for(index = 0; index < len; index++) 
				{
					if(document.editarIndicadorForm.elements[index].name == 'serie') 
					{
						if (document.editarIndicadorForm.elements[index].checked) 
						{
							document.editarIndicadorForm.seriesIndicador.value = 
								document.editarIndicadorForm.seriesIndicador.value
								+ '<bean:write name="editarIndicadorForm" property="separadorSeries"/>'
								+ document.editarIndicadorForm.elements[index].value
								+ '<bean:write name="editarIndicadorForm" property="separadorSeries"/>';
						}
					}
				}
			}
	
			var htmlParametroAcotamientoSuperior;
			var htmlParametroAcotamientoSuperiorFijo;
			var htmlParametroAcotamientoSuperiorIndicador;
			var htmlParametroAcotamientoInferior;
			var htmlParametroAcotamientoInferiorFijo;
			var htmlParametroAcotamientoInferiorIndicador;
			
			function eventoOnclickCaracteristica() 
			{
				if ((htmlParametroAcotamientoSuperior == '') && (htmlParametroAcotamientoInferior == '')) 
				{
					htmlParametroAcotamientoSuperior = parametroAcotamientoPrimario.innerHTML;
					htmlParametroAcotamientoInferior = parametroAcotamientoSecundario.innerHTML;
				} 
				else if (htmlParametroAcotamientoSuperior == '') 
					htmlParametroAcotamientoSuperior = parametroAcotamientoPrimario.innerHTML;
				else if (htmlParametroAcotamientoInferior == '') 
					htmlParametroAcotamientoInferior = parametroAcotamientoPrimario.innerHTML;
				definirParametrosAcotamiento();
			}
			
			function definirParametrosAcotamiento() 
			{
				var seleccionado = '';
				for (var index = 0; index < document.editarIndicadorForm.caracteristica.length; index++) 
				{
					if (document.editarIndicadorForm.caracteristica[index].checked) 
					{
						seleccionado = document.editarIndicadorForm.caracteristica[index].value;
						break;
					}
				}
				if (seleccionado == '<bean:write name="editarIndicadorForm" property="caracteristicaRetoAumento"/>') 
				{
					parametroAcotamientoPrimario.innerHTML = htmlParametroAcotamientoInferior;
					parametroAcotamientoSecundario.innerHTML = '';
					htmlParametroAcotamientoInferior = '';
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="caracteristicaRetoDisminucion"/>') 
				{
					parametroAcotamientoPrimario.innerHTML = htmlParametroAcotamientoSuperior;
					parametroAcotamientoSecundario.innerHTML = '';
					htmlParametroAcotamientoSuperior = '';
					if (_loaded)
						alert('<vgcutil:message key="jsp.editarindicador.ficha.retodisminucion.alert" />');
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMaximo"/>') 
				{
					parametroAcotamientoPrimario.innerHTML = htmlParametroAcotamientoSuperior;
					parametroAcotamientoSecundario.innerHTML = '';
					htmlParametroAcotamientoSuperior = '';
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="caracteristicaCondicionValorMinimo"/>') 
				{
					parametroAcotamientoPrimario.innerHTML = htmlParametroAcotamientoInferior;
					parametroAcotamientoSecundario.innerHTML = '';
					htmlParametroAcotamientoInferior = '';
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="caracteristicaCondicionBandas"/>') 
				{
					parametroAcotamientoPrimario.innerHTML = htmlParametroAcotamientoSuperior;
					parametroAcotamientoSecundario.innerHTML = htmlParametroAcotamientoInferior;
					htmlParametroAcotamientoSuperior = '';
					htmlParametroAcotamientoInferior = '';
				}
			}
			
			function inicializarParametrosAcotamiento() 
			{
				htmlParametroAcotamientoSuperiorFijo = findObjetoHtml('parametroAcotamientoSuperiorFijo').innerHTML;
				findObjetoHtml('parametroAcotamientoSuperiorFijo').innerHTML = '';
				htmlParametroAcotamientoSuperiorIndicador = findObjetoHtml('parametroAcotamientoSuperiorIndicador').innerHTML;
				findObjetoHtml('parametroAcotamientoSuperiorIndicador').innerHTML = '';
				htmlParametroAcotamientoInferiorFijo = findObjetoHtml('parametroAcotamientoInferiorFijo').innerHTML;
				findObjetoHtml('parametroAcotamientoInferiorFijo').innerHTML = '';
				htmlParametroAcotamientoInferiorIndicador = findObjetoHtml('parametroAcotamientoInferiorIndicador').innerHTML;
				findObjetoHtml('parametroAcotamientoInferiorIndicador').innerHTML = '';
				eventoOnclickParametroTipoAcotamientoSuperior();
				eventoOnclickParametroTipoAcotamientoInferior();
				var parametroAcotamientoPrimario = findObjetoHtml('parametroAcotamientoPrimario');
				var parametroAcotamientoSecundario = findObjetoHtml('parametroAcotamientoSecundario');
				htmlParametroAcotamientoSuperior = parametroAcotamientoPrimario.innerHTML;
				htmlParametroAcotamientoInferior = parametroAcotamientoSecundario.innerHTML;
				definirParametrosAcotamiento();
			}
			
			function eventoOnclickParametroTipoAcotamientoSuperior() 
			{
				var seleccionado = '';
				for (var index = 0; index < document.editarIndicadorForm.parametroTipoAcotamientoSuperior.length; index++) 
				{
					if (document.editarIndicadorForm.parametroTipoAcotamientoSuperior[index].checked) 
					{
						seleccionado = document.editarIndicadorForm.parametroTipoAcotamientoSuperior[index].value;
						break;
					}
				}
			
				var parametroAcotamientoSuperior = findObjetoHtml('parametroAcotamientoSuperior');
			
				if (seleccionado == '<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorFijo" />') 
				{
					if (htmlParametroAcotamientoSuperiorFijo != '') 
					{
						if (htmlParametroAcotamientoSuperiorIndicador == '') 
						{
							document.editarIndicadorForm.parametroSuperiorIndicadorId.value = '';
							document.editarIndicadorForm.parametroSuperiorIndicadorNombre.value = '';
							// se elimina del html el atributo value para que el valor no quede grabado
							var nuevoHtml = parametroAcotamientoSuperior.innerHTML;
							if (parametroAcotamientoSuperior.innerHTML.indexOf('value=') > -1) 
								nuevoHtml = parametroAcotamientoSuperior.innerHTML.substring(0, parametroAcotamientoSuperior.innerHTML.indexOf('value') - 1) + ' ' + parametroAcotamientoSuperior.innerHTML.substring(parametroAcotamientoSuperior.innerHTML.indexOf('readonly') - 1);
							htmlParametroAcotamientoSuperiorIndicador = nuevoHtml;
						}
						parametroAcotamientoSuperior.innerHTML = htmlParametroAcotamientoSuperiorFijo;
						htmlParametroAcotamientoSuperiorFijo = '';
					}
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorIndicador" />') 
				{
					if (htmlParametroAcotamientoSuperiorIndicador != '') 
					{
						if (htmlParametroAcotamientoSuperiorFijo == '') 
						{
							document.editarIndicadorForm.parametroSuperiorValorFijo.value = '';
							var nuevoHtml = parametroAcotamientoSuperior.innerHTML;
							if (parametroAcotamientoSuperior.innerHTML.indexOf('value=') > -1) 
								nuevoHtml = parametroAcotamientoSuperior.innerHTML.substring(0, parametroAcotamientoSuperior.innerHTML.indexOf('value') - 1) + ' ' + parametroAcotamientoSuperior.innerHTML.substring(parametroAcotamientoSuperior.innerHTML.indexOf('onkeyup') - 1);
	
							// se elimina del html el atributo value para que el valor no quede grabado
							htmlParametroAcotamientoSuperiorFijo = nuevoHtml;
						}
						parametroAcotamientoSuperior.innerHTML = htmlParametroAcotamientoSuperiorIndicador;
						htmlParametroAcotamientoSuperiorIndicador = '';
					}
				}
			}
			
			function eventoOnclickParametroTipoAcotamientoInferior() 
			{
				var seleccionado = '';
				for (var index = 0; index < document.editarIndicadorForm.parametroTipoAcotamientoInferior.length; index++) 
				{
					if (document.editarIndicadorForm.parametroTipoAcotamientoInferior[index].checked) 
					{
						seleccionado = document.editarIndicadorForm.parametroTipoAcotamientoInferior[index].value;
						break;
					}
				}
			
				var parametroAcotamientoInferior = findObjetoHtml('parametroAcotamientoInferior');
			
				if (seleccionado == '<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorFijo" />') 
				{
					if (htmlParametroAcotamientoInferiorFijo != '') 
					{
						if (htmlParametroAcotamientoInferiorIndicador == '') 
						{
							document.editarIndicadorForm.parametroInferiorIndicadorId.value = '';
							document.editarIndicadorForm.parametroInferiorIndicadorNombre.value = '';
							// se elimina del html el atributo value para que el valor no quede grabado
							var nuevoHtml = parametroAcotamientoInferior.innerHTML;
							if (parametroAcotamientoInferior.innerHTML.indexOf('value=') > -1) 
								nuevoHtml = parametroAcotamientoInferior.innerHTML.substring(0, parametroAcotamientoInferior.innerHTML.indexOf('value') - 1) + ' ' + parametroAcotamientoInferior.innerHTML.substring(parametroAcotamientoInferior.innerHTML.indexOf('readonly') - 1);
							htmlParametroAcotamientoInferiorIndicador = nuevoHtml;
						}
						parametroAcotamientoInferior.innerHTML = htmlParametroAcotamientoInferiorFijo;
						htmlParametroAcotamientoInferiorFijo = '';
					}
				} 
				else if (seleccionado == '<bean:write name="editarIndicadorForm" property="tipoAcotamientoValorIndicador" />') 
				{
					if (htmlParametroAcotamientoInferiorIndicador != '') 
					{
						if (htmlParametroAcotamientoInferiorFijo == '') 
						{
							document.editarIndicadorForm.parametroInferiorValorFijo.value = '';
							// se elimina del html el atributo value para que el valor no quede grabado
							var nuevoHtml = parametroAcotamientoInferior.innerHTML;
							if (parametroAcotamientoInferior.innerHTML.indexOf('value=') > -1) 
								nuevoHtml = parametroAcotamientoInferior.innerHTML.substring(0, parametroAcotamientoInferior.innerHTML.indexOf('value') - 1) + ' ' + parametroAcotamientoInferior.innerHTML.substring(parametroAcotamientoInferior.innerHTML.indexOf('onkeyup') - 1);
							htmlParametroAcotamientoInferiorFijo = nuevoHtml;
						}
						parametroAcotamientoInferior.innerHTML = htmlParametroAcotamientoInferiorIndicador;
						htmlParametroAcotamientoInferiorIndicador = '';
					}
				}
			}
			
			function seleccionarIndicadorParametroSuperior() 
			{
				var permitirCambiarClase = 'true';
				<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
					permitirCambiarClase = 'false';
				</logic:equal>
				abrirSelectorIndicadores('editarIndicadorForm', 'parametroSuperiorIndicadorNombre', 'parametroSuperiorIndicadorId', null, 'finalizarSeleccionIndicadorParametroSuperior()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=false&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=false&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
			
			function limpiarIndicadorParametroSuperior() 
			{
				document.editarIndicadorForm.parametroSuperiorIndicadorId.value = '';
				document.editarIndicadorForm.parametroSuperiorIndicadorNombre.value = '';
			}
			
			function finalizarSeleccionIndicadorParametroSuperior() 
			{
			}
			
			function seleccionarIndicadorParametroInferior() 
			{
				var permitirCambiarClase = 'true';
				<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
					permitirCambiarClase = 'false';
				</logic:equal>
				abrirSelectorIndicadores('editarIndicadorForm', 'parametroInferiorIndicadorNombre', 'parametroInferiorIndicadorId', null, 'finalizarSeleccionIndicadorParametroInferior()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=false&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=false&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
			
			function limpiarIndicadorParametroInferior() 
			{
				document.editarIndicadorForm.parametroInferiorIndicadorId.value = '';
				document.editarIndicadorForm.parametroInferiorIndicadorNombre.value = '';
			}
	
			function finalizarSeleccionIndicadorParametroInferior() 
			{
			}
	
			function establecerTipoAlerta(valorSeleccion, nombreCuadroTexto, nombreColumna) 
			{
				switch (parseInt(valorSeleccion)) 
			   	{
					case <bean:write name="editarIndicadorForm" property="tipoAlertaPorcentaje" />:
					   	colocarCuadroTexto('<vgcutil:message key="jsp.editarindicador.ficha.porcentaje" />', nombreCuadroTexto, nombreColumna, valorSeleccion);
						eval('document.forms[0].' + nombreCuadroTexto).size = 5;
					   	break;
				   	case <bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />:
					   	colocarCuadroTexto('<vgcutil:message key="jsp.editarindicador.ficha.magnitud" />', nombreCuadroTexto, nombreColumna, valorSeleccion);
				   		eval('document.forms[0].' + nombreCuadroTexto).size = 27;
					   	break;
				   	case <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />:
						colocarCuadroTexto('<vgcutil:message key="jsp.editarindicador.ficha.indicador" />', nombreCuadroTexto, nombreColumna, valorSeleccion);
				   		eval('document.forms[0].' + nombreCuadroTexto).size = 27;
					   	break;
				}
			}
	
			function seleccionarIndicadorZonaVerde()
			{
				abrirSelectorIndicadores('editarIndicadorForm', 'alertaMetaZonaVerde', 'alertaIndicadorIdZonaVerde', '', '', document.editarIndicadorForm.alertaIndicadorIdZonaVerde.value, 'nombreOrganizacion', 'organizacionId', 'nombreClase', 'claseId' , '&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=false&permitirCambiarClase=true&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&organizacionId=<bean:write name="organizacionId" scope="session"/>' + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
	
			function seleccionarIndicadorZonaAmarilla() 
			{
				abrirSelectorIndicadores('editarIndicadorForm', 'alertaMetaZonaAmarilla', 'alertaIndicadorIdZonaAmarilla', '', '', document.editarIndicadorForm.alertaIndicadorIdZonaAmarilla.value, 'nombreOrganizacion', 'organizacionId', 'nombreClase', 'claseId', '&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=false&permitirCambiarClase=true&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&organizacionId=<bean:write name="organizacionId" scope="session"/>' + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
	
			function limpiarIndicadorZonaVerde() 
			{
				document.editarIndicadorForm.alertaIndicadorIdZonaVerde.value = 0;				
				document.editarIndicadorForm.alertaMetaZonaVerde.value = "";
			}			
			
			function limpiarIndicadorZonaAmarilla() 
			{			
				document.editarIndicadorForm.alertaIndicadorIdZonaAmarilla.value = 0;				
				document.editarIndicadorForm.alertaMetaZonaAmarilla.value = "";
			}
	
			function colocarCuadroTexto(etiquetaCuadroTexto, nombreCuadroTexto, nombreColumna, valorSeleccion) 
			{
				var celdaZonaVerde = "celdaZonaVerde";
			  	var celdaZonaAmarilla = "celdaZonaAmarilla";
			  	var funcionSeleccionarIndicador = '';
			  	var funcionLimpiarIndicadorSeleccionado = '';
			
			 	if ((valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) && (nombreColumna == celdaZonaVerde)) 
			 	{
					funcionSeleccionarIndicador = 'seleccionarIndicadorZonaVerde()';
					funcionLimpiarIndicadorSeleccionado = 'limpiarIndicadorZonaVerde()';
				}
				if ((valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) && (nombreColumna == celdaZonaAmarilla)) 
				{
					funcionSeleccionarIndicador = 'seleccionarIndicadorZonaAmarilla()';
					funcionLimpiarIndicadorSeleccionado = 'limpiarIndicadorZonaAmarilla()';
				}
			
				var celda = document.getElementById(nombreColumna);
			
				var parametrosTexto = '';
				
				if (valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) 
					parametrosTexto = 'readonly';
				else if (valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaValorAbsolutoMagnitud" />) 
					parametrosTexto = 'onKeyUp="verificarNumero(this, false);"';

				var htmlTexto = etiquetaCuadroTexto + '&nbsp;&nbsp;&nbsp;<input class="cuadroTexto" type="text" ' + ((document.editarIndicadorForm.bloqueado.value == "true" || document.editarIndicadorForm.bloquearIndicadorIniciativa.value == "true") ? 'disabled="true"' : '') + ' name="' + nombreCuadroTexto + '" size="5" ' + parametrosTexto + ' />';
				if (valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaPorcentaje" />)
				{
					//parametrosTexto = 'onKeyUp="verificarNumero(this, false);"';
					parametrosTexto = 'onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)"'; 
					parametrosTexto = parametrosTexto + ' onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)"'; 
					parametrosTexto = parametrosTexto + ' onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)"';
					
					htmlTexto = etiquetaCuadroTexto + '&nbsp;&nbsp;&nbsp;<input class="cuadroTexto" type="text" ' + ((document.editarIndicadorForm.bloqueado.value == "true" || document.editarIndicadorForm.bloquearIndicadorIniciativa.value == "true") ? 'disabled="true"' : '') + ' name="' + nombreCuadroTexto + '" ' + parametrosTexto + ' >';
				}
			
				var srcImgSelector = "<html:rewrite page='/componentes/fichaDatos/selector.gif'/>";
				var srcImgEliminar = "<html:rewrite page='/componentes/calendario/eliminar.gif'/>";
			
				if (valorSeleccion == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) 
				{
					htmlTexto = htmlTexto
					+ '&nbsp;<img style="cursor: pointer" onclick="javascript:' + funcionSeleccionarIndicador + ';" src="' + srcImgSelector + '" border="0" width="10" height="10"'
					+ 'title="<vgcutil:message key="boton.seleccionarindicador.alt" /> ">&nbsp;<img style="cursor: pointer"'
					+ 'src="' + srcImgEliminar + '" onclick="javascript:' + funcionLimpiarIndicadorSeleccionado + ';" border="0" width="10" height="10"'
					+ 'title="<vgcutil:message key="boton.limpiar.alt" />">&nbsp;';
				}
				celda.innerHTML = htmlTexto;
			}
	
			function establecerValorZonaVerde(valorAlertaVerde, nombreIndicador, tipo) 
			{
				var valor = "";
	
				if (tipo == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) 
					valor = nombreIndicador;
				else 
					valor = valorAlertaVerde;
				document.editarIndicadorForm.alertaMetaZonaVerde.value = valor;
			}
	
			function establecerValorZonaAmarilla(valorAlertaAmarilla, nombreIndicador, tipo) 
			{
				var valor = "";
	
				if (tipo == <bean:write name="editarIndicadorForm" property="tipoAlertaZonaValorAbsolutoIndicador" />) 
					valor = nombreIndicador;
				else 
					valor = valorAlertaAmarilla;
	
				document.editarIndicadorForm.alertaMetaZonaAmarilla.value = valor;
			}
			
			function limpiarResponsableNotificacion() 
			{				
				document.editarIndicadorForm.responsableNoficiacionId.value = "";				
				document.editarIndicadorForm.responsableNotificacion.value = "";
			}
	
			function limpiarResponsableFijarMeta() 
			{				
				document.editarIndicadorForm.responsableFijarMetaId.value = "";				
				document.editarIndicadorForm.responsableFijarMeta.value = "";
			}
			
			function limpiarResponsableLograrMeta() 
			{				
				document.editarIndicadorForm.responsableLograrMetaId.value = "";				
				document.editarIndicadorForm.responsableLograrMeta.value = "";
			}
			
			function limpiarResponsableSeguimiento() 
			{				
				document.editarIndicadorForm.responsableSeguimientoId.value = "";				
				document.editarIndicadorForm.responsableSeguimiento.value = "";
			}
			
			function limpiarResponsableCargarMeta() 
			{				
				document.editarIndicadorForm.responsableCargarMetaId.value = "";				
				document.editarIndicadorForm.responsableCargarMeta.value = "";
			}
			
			function limpiarResponsableCargarEjecutado() 
			{				
				document.editarIndicadorForm.responsableCargarEjecutadoId.value = "";				
				document.editarIndicadorForm.responsableCargarEjecutado.value = "";
			}
			
			function seleccionarResponsableNotificacion() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableNotificacion', 'responsableNotificacionId', document.editarIndicadorForm.responsableNotificacionId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}			
			
			function seleccionarResponsableFijarMeta() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableFijarMeta', 'responsableFijarMetaId', document.editarIndicadorForm.responsableFijarMetaId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}
			
			function seleccionarResponsableLograrMeta() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableLograrMeta', 'responsableLograrMetaId', document.editarIndicadorForm.responsableLograrMetaId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}
			
			function seleccionarResponsableSeguimiento() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableSeguimiento', 'responsableSeguimientoId', document.editarIndicadorForm.responsableSeguimientoId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}
			
			function seleccionarResponsableCargarMeta() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableCargarMeta', 'responsableCargarMetaId', document.editarIndicadorForm.responsableCargarMetaId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}
			
			function seleccionarResponsableCargarEjecutado() 
			{
				abrirSelectorResponsables('editarIndicadorForm', 'responsableCargarEjecutado', 'responsableCargarEjecutadoId', document.editarIndicadorForm.responsableCargarEjecutadoId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}	
			
			function establecerVisibilidad(panel, nombreRadio) 
			{
				var porcentaje = eval('document.forms[0].alertaTipoZona' + nombreRadio + '[0]').checked;
				var elemento = document.getElementById(panel);
				
				if (porcentaje) 
					elemento.style.visibility='hidden';
				else 
					elemento.style.visibility='visible';
			}
			
			/** Funciones javaScript para las nuturaleza del Indicador*/
			var categorias = "";
			var separadorCategorias = '<bean:write name="editarIndicadorForm" property="separadorCategorias" />';
			var sepOrd = '<bean:write name="editarIndicadorForm" property="separadorOrden" />';
	
			var unidadDesactivada = false;
			<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
				unidadDesactivada = true;
			</logic:equal>
			var mascaraDecimalesDesactivada = false;
			var panelParametrosDesactivado = false;
			var panelAsociarDesactivado = false;
			var panelAlertasDesactivado = false;
			var panelResponsablesDesactivado = false;
	
			<logic:present name="editarIndicadorForm" property="planId" scope="session">
				panelAsociarDesactivado = true;		
			</logic:present>
	
			function eventoCambioNaturaleza() 
			{
				if ((document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoOrdinal" />)
					|| (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />)) 
				{
					panelParametrosDesactivado = true;
					panelAlertasDesactivado = true;
	
					var objetosOrden = document.getElementById('definicionCualitativoOrden');
					if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />) 
						objetosOrden.style.visibility = "hidden";
					else 
						objetosOrden.style.visibility = "visible";
					document.editarIndicadorForm.unidadId.selectedIndex = 0;
					document.editarIndicadorForm.unidadId.value = '0'; 
					document.editarIndicadorForm.unidadId.disabled = true;
					unidadDesactivada = true;
					document.editarIndicadorForm.numeroDecimales.value = '';
					document.editarIndicadorForm.numeroDecimales.disabled = true;
					mascaraDecimalesDesactivada = true;
				} 
				else 
				{
					if (document.editarIndicadorForm.bloquearIndicadorIniciativa.value != "true")
						unidadDesactivada = false;
					if (document.editarIndicadorForm.bloqueado.value != "true" && document.editarIndicadorForm.bloquearIndicadorIniciativa.value != "true")
						document.editarIndicadorForm.unidadId.disabled = unidadDesactivada;
					mascaraDecimalesDesactivada = false;
					if (document.editarIndicadorForm.bloqueado.value != "true")
						document.editarIndicadorForm.numeroDecimales.disabled = false;
	
					panelParametrosDesactivado = false;
					panelAlertasDesactivado = false;
				}
	
				if ((document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />)
					|| (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />)
					|| (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />)) 
					panelResponsablesDesactivado = true;
				else 
					panelResponsablesDesactivado = false;
				
				var trPeriodos = document.getElementById('trPeriodos');
				var trTipoMediciones = document.getElementById('trTipoMediciones');
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />)
				{
					trPeriodos.style.display = "none";
					trTipoMediciones.style.display = "";
				}
				else
				{
					trPeriodos.style.display = "";
					trTipoMediciones.style.display = "none";
				}
				
				setPanelDefinicion();
			}
	
			function eventoOnchangeFrecuencia() 
			{
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
				{
					if ((document.editarIndicadorForm.indicadorSumatoriaId.value != null) && (document.editarIndicadorForm.indicadorSumatoriaId.value != '')) 
					{
						if (document.editarIndicadorForm.frecuencia.value < document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value) 
						{
							limpiarIndicadorBase('sumatoria');
							alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
						} 
						else 
						{
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)
								&& (document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaBimensual" />)) 
							{
								limpiarIndicadorBase('sumatoria');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)
								&& (document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)) 
							{
								limpiarIndicadorBase('sumatoria');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaSemestral" />)
								&& (document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)) 
							{
								limpiarIndicadorBase('sumatoria');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
						}
					}
				} 
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />) 
				{
					if ((document.editarIndicadorForm.indicadorPromedioId.value != null) && (document.editarIndicadorForm.indicadorPromedioId.value != '')) 
					{
						if (document.editarIndicadorForm.frecuencia.value < document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value) 
						{
							limpiarIndicadorBase('promedio');
							alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
						} 
						else 
						{
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)
								&& (document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaBimensual" />)) 
							{
								limpiarIndicadorBase('promedio');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)
								&& (document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)) 
							{
								limpiarIndicadorBase('promedio');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaSemestral" />)
								&& (document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)) 
							{
								limpiarIndicadorBase('promedio');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
						}
					}
				} 
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />) 
				{
					if ((document.editarIndicadorForm.indicadorIndiceId.value != null) && (document.editarIndicadorForm.indicadorIndiceId.value != '')) 
					{
						if (document.editarIndicadorForm.frecuencia.value < document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value) 
						{
							limpiarIndicadorBase('indice');
							alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
						} 
						else 
						{
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)
								&& (document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaBimensual" />)) 
							{
								limpiarIndicadorBase('indice');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)
								&& (document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaTrimestral" />)) 
							{
								limpiarIndicadorBase('indice');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
							if ((document.editarIndicadorForm.frecuencia.value == <bean:write name="editarIndicadorForm" property="frecuenciaSemestral" />)
								&& (document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value == <bean:write name="editarIndicadorForm" property="frecuenciaCuatrimestral" />)) 
							{
								limpiarIndicadorBase('indice');
								alert('<vgcutil:message key="jsp.editarindicador.mensaje.indicadorbaselimpiadocompatibilidad" />');
							}
						}
					}
				}
			}
	
			function onclickUnidadId(objeto) 
			{
				if (unidadDesactivada) 
					objeto.blur();
			}
	
			function onmousedownMascaraDecimalesUp() 
			{
				if (!mascaraDecimalesDesactivada)
				{
					iniciarConteoContinuo('numeroDecimales', 9);
					aumentoConstante();
				}
			}
	
			function onmousedownMascaraDecimalesDown() 
			{
				if (!mascaraDecimalesDesactivada) 
				{
					iniciarConteoContinuo('numeroDecimales');
					decrementoConstante();
				}
			}
	
			function setPanelDefinicion() 
			{
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSimple" />) 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionSimple" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaFormula" />) 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionFormula" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoOrdinal" />) 
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionCualitativo" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaCualitativoNominal" />) 
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionCualitativo" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionSumatoria" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />) 
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionPromedio" nombreContenedor="editarIndicadorPanelDefinicion" />
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />) 
					<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="definicionIndice" nombreContenedor="editarIndicadorPanelDefinicion" />
				else 
					alert('<vgcutil:message key="jsp.editarindicador.mensaje.opcionnovalida" />');
			}
	
			function  mostrarPanelIndicador(nombrePanelIndicador) 
			{
				if (nombrePanelIndicador == 'parametros') 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="parametros" nombreContenedor="editarIndicador" />
				else if (nombrePanelIndicador == 'asociar') 
				{
					if (panelAsociarDesactivado) 
						alert('<vgcutil:message key="jsp.editarindicador.mensaje.funcionalidadnodisponible" />');
					else 
				        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="asociar" nombreContenedor="editarIndicador" />
				} 
				else if (nombrePanelIndicador == 'alertas') 
				{
					if (panelAlertasDesactivado) 
						alert('<vgcutil:message key="jsp.editarindicador.mensaje.noaplicapornaturaleza" />');
					else 
				        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="alertas" nombreContenedor="editarIndicador" />
				} 
				else if (nombrePanelIndicador == 'responsables') 
			        <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="responsables" nombreContenedor="editarIndicador" />
				else 
					alert('<vgcutil:message key="jsp.editarindicador.mensaje.opcionnovalida" />');
			}
	
			function seleccionarIndicadorBase(naturaleza) 
			{
				var permitirCambiarClase = 'true';
				var permitirIniciativas = 'true';
				<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
					permitirCambiarClase = 'false';
					permitirIniciativas = 'false';
				</logic:equal>
				if (naturaleza == 'sumatoria') 
					abrirSelectorIndicadores('editarIndicadorForm', 'indicadorSumatoria', 'indicadorSumatoriaId', 'indicadorSumatoriaFrecuenciaId', 'finalizarSeleccionIndicadorBase()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=' + permitirIniciativas + '&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuenciasContenidas=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
				else if (naturaleza == 'promedio') 
					abrirSelectorIndicadores('editarIndicadorForm', 'indicadorPromedio', 'indicadorPromedioId', 'indicadorPromedioFrecuenciaId', 'finalizarSeleccionIndicadorBase()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=' + permitirIniciativas + '&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuenciasContenidas=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
				else if (naturaleza == 'indice') 
					abrirSelectorIndicadores('editarIndicadorForm', 'indicadorIndice', 'indicadorIndiceId', 'indicadorIndiceFrecuenciaId', 'finalizarSeleccionIndicadorBase()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=' + permitirIniciativas + '&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
			
			function finalizarSeleccionIndicadorBase() 
			{
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/editarIndicadorFuncion" />?funcion=getInformacionIndicadorBase&indicadorId=' + document.editarIndicadorForm.indicadorSumatoriaId.value, document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId, 'finalizarSeleccionIndicadorBaseFinal()');
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />) 
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/editarIndicadorFuncion" />?funcion=getInformacionIndicadorBase&indicadorId=' + document.editarIndicadorForm.indicadorPromedioId.value, document.editarIndicadorForm.indicadorPromedioFrecuenciaId, 'finalizarSeleccionIndicadorBaseFinal()');
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />) 
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/editarIndicadorFuncion" />?funcion=getInformacionIndicadorBase&indicadorId=' + document.editarIndicadorForm.indicadorIndiceId.value, document.editarIndicadorForm.indicadorIndiceFrecuenciaId, 'finalizarSeleccionIndicadorBaseFinal()');
			}
			
			function finalizarSeleccionIndicadorBaseFinal() 
			{
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
					finalizarSeleccionIndicadorBaseFinalSumatoria();
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaPromedio" />) 
					finalizarSeleccionIndicadorBaseFinalPromedio();
				else if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaIndice" />) 
					finalizarSeleccionIndicadorBaseFinalIndice();
			}
			
			function finalizarSeleccionIndicadorBaseFinalSumatoria() 
			{
				var partesId = document.editarIndicadorForm.indicadorSumatoriaId.value.split('<bean:write name="editarIndicadorForm" property="separadorSeries" ignore="true"/>');
			
				document.editarIndicadorForm.indicadorSumatoriaId.value = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']'; 
			
				var informacion = document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value;
			
				document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value = '';
				document.editarIndicadorForm.indicadorSumatoriaFrecuencia.value = '';
				document.editarIndicadorForm.indicadorSumatoriaUnidad.value = '';
			
				var index1 = informacion.indexOf('[frecuenciaId:');
				index1 = index1 + '[frecuenciaId:'.length;
				var index2 = informacion.indexOf('][frecuenciaNombre:');
				document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value = informacion.substring(index1, index2);
				index1 = index2 + '][frecuenciaNombre:'.length;
				index2 = informacion.indexOf('][unidadId:');
				if (index2 < 0) 
					document.editarIndicadorForm.indicadorSumatoriaFrecuencia.value = informacion.substring(index1, informacion.length - 1);
				else 
				{
					document.editarIndicadorForm.indicadorSumatoriaFrecuencia.value = informacion.substring(index1, index2);
					index1 = informacion.indexOf('][unidadNombre:') + '][unidadNombre:'.length;
					document.editarIndicadorForm.indicadorSumatoriaUnidad.value = informacion.substring(index1, informacion.length - 1);
				}
			}
			
			function finalizarSeleccionIndicadorBaseFinalPromedio() {
				var partesId = document.editarIndicadorForm.indicadorPromedioId.value.split('<bean:write name="editarIndicadorForm" property="separadorSeries" ignore="true"/>');
			
				document.editarIndicadorForm.indicadorPromedioId.value = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']'; 
			
				var informacion = document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value;
			
				document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value = '';
				document.editarIndicadorForm.indicadorPromedioFrecuencia.value = '';
				document.editarIndicadorForm.indicadorPromedioUnidad.value = '';
			
				var index1 = informacion.indexOf('[frecuenciaId:');
				index1 = index1 + '[frecuenciaId:'.length;
				var index2 = informacion.indexOf('][frecuenciaNombre:');
				document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value = informacion.substring(index1, index2);
				index1 = index2 + '][frecuenciaNombre:'.length;
				index2 = informacion.indexOf('][unidadId:');
				if (index2 < 0) {
					document.editarIndicadorForm.indicadorPromedioFrecuencia.value = informacion.substring(index1, informacion.length - 1);
				} else {
					document.editarIndicadorForm.indicadorPromedioFrecuencia.value = informacion.substring(index1, index2);
					index1 = informacion.indexOf('][unidadNombre:') + '][unidadNombre:'.length;
					document.editarIndicadorForm.indicadorPromedioUnidad.value = informacion.substring(index1, informacion.length - 1);
				}
			}
			
			function finalizarSeleccionIndicadorBaseFinalIndice() 
			{
				var partesId = document.editarIndicadorForm.indicadorIndiceId.value.split('<bean:write name="editarIndicadorForm" property="separadorSeries" ignore="true"/>');
			
				document.editarIndicadorForm.indicadorIndiceId.value = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']'; 
			
				var informacion = document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value;
			
				document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value = '';
				document.editarIndicadorForm.indicadorIndiceFrecuencia.value = '';
				document.editarIndicadorForm.indicadorIndiceUnidad.value = '';
			
				var index1 = informacion.indexOf('[frecuenciaId:');
				index1 = index1 + '[frecuenciaId:'.length;
				var index2 = informacion.indexOf('][frecuenciaNombre:');
				document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value = informacion.substring(index1, index2);
				index1 = index2 + '][frecuenciaNombre:'.length;
				index2 = informacion.indexOf('][unidadId:');
				if (index2 < 0) {
					document.editarIndicadorForm.indicadorIndiceFrecuencia.value = informacion.substring(index1, informacion.length - 1);
				} else {
					document.editarIndicadorForm.indicadorIndiceFrecuencia.value = informacion.substring(index1, index2);
					index1 = informacion.indexOf('][unidadNombre:') + '][unidadNombre:'.length;
					document.editarIndicadorForm.indicadorIndiceUnidad.value = informacion.substring(index1, informacion.length - 1);
				}
			}
			
			function limpiarIndicadorBase(naturaleza) 
			{			
				if (naturaleza == 'sumatoria') {
					document.editarIndicadorForm.indicadorSumatoriaId.value = ''; 
					document.editarIndicadorForm.indicadorSumatoria.value = ''; 
					document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value = '';
					document.editarIndicadorForm.indicadorSumatoriaFrecuencia.value = '';
					document.editarIndicadorForm.indicadorSumatoriaUnidad.value = '';
				} else if (naturaleza == 'promedio') {
					document.editarIndicadorForm.indicadorPromedioId.value = '';
					document.editarIndicadorForm.indicadorPromedio.value = '';
					document.editarIndicadorForm.indicadorPromedioFrecuenciaId.value = '';
					document.editarIndicadorForm.indicadorPromedioFrecuencia.value = '';
					document.editarIndicadorForm.indicadorPromedioUnidad.value = '';
				} else if (naturaleza == 'indice') {
					document.editarIndicadorForm.indicadorIndiceId.value = '';
					document.editarIndicadorForm.indicadorIndice.value = '';
					document.editarIndicadorForm.indicadorIndiceFrecuenciaId.value = '';
					document.editarIndicadorForm.indicadorIndiceFrecuencia.value = '';
					document.editarIndicadorForm.indicadorIndiceUnidad.value = '';
				}
			}
			
			function eventoCambioTipoComparacionIndicadorIndice() 
			{
				if (document.editarIndicadorForm.indicadorIndiceTipoComparacion[0].checked) {
					document.editarIndicadorForm.tipoComparacionIndicadorIndiceFormula.value = '<bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoAnteriorIndicadorIndiceFormula"/>';
				} else if (document.editarIndicadorForm.indicadorIndiceTipoComparacion[1].checked) {
					document.editarIndicadorForm.tipoComparacionIndicadorIndiceFormula.value = '<bean:write name="editarIndicadorForm" property="tipoComparacionAnoAnteriorMismoPeriodoIndicadorIndiceFormula"/>';
				} else if (document.editarIndicadorForm.indicadorIndiceTipoComparacion[2].checked) {
					document.editarIndicadorForm.tipoComparacionIndicadorIndiceFormula.value = '<bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPrimerPeriodoIndicadorIndiceFormula"/>';
				} else if (document.editarIndicadorForm.indicadorIndiceTipoComparacion[3].checked) {
					document.editarIndicadorForm.tipoComparacionIndicadorIndiceFormula.value = '<bean:write name="editarIndicadorForm" property="tipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndiceFormula"/>';
				}
			}
			
			function seleccionarIndicadorAsociado() {
			
				if (document.editarIndicadorForm.indicadorAsociadoTipo != null && (document.editarIndicadorForm.indicadorAsociadoTipo.value == null || document.editarIndicadorForm.indicadorAsociadoTipo.value == '')) 
					return;
			
				var permitirCambiarClase = 'true';
				var permitirIniciativas = 'true';
				<logic:equal name="editarIndicadorForm" property="desdeIniciativasPlanes" scope="session" value="true">
					permitirCambiarClase = 'false';
					permitirIniciativas = 'false';
				</logic:equal>
				abrirSelectorIndicadores('editarIndicadorForm', 'indicadorAsociadoNombre', 'indicadorAsociadoId', 'indicadorAsociadoFrecuenciaId', 'finalizarSeleccionIndicadorAsociado()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=true&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=' + permitirIniciativas + '&claseId=<bean:write name="editarIndicadorForm" property="claseId" />&frecuenciasContenidas=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value);
			}
			
			function finalizarSeleccionIndicadorAsociado() {
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/editarIndicadorFuncion" />?funcion=getInformacionIndicadorBase&indicadorId=' + document.editarIndicadorForm.indicadorAsociadoId.value, document.editarIndicadorForm.indicadorAsociadoFrecuenciaId, 'finalizarSeleccionIndicadorAsociadoFinal()');
			}
			
			function finalizarSeleccionIndicadorAsociadoFinal() {
			
				var informacion = document.editarIndicadorForm.indicadorAsociadoFrecuenciaId.value;
			
				document.editarIndicadorForm.indicadorAsociadoFrecuenciaId.value = '';
				document.editarIndicadorForm.indicadorAsociadoFrecuencia.value = '';
				document.editarIndicadorForm.indicadorAsociadoUnidad.value = '';
			
				var index1 = informacion.indexOf('[frecuenciaId:');
				index1 = index1 + '[frecuenciaId:'.length;
				var index2 = informacion.indexOf('][frecuenciaNombre:');
				document.editarIndicadorForm.indicadorAsociadoFrecuenciaId.value = informacion.substring(index1, index2);
				index1 = index2 + '][frecuenciaNombre:'.length;
				index2 = informacion.indexOf('][unidadId:');
				if (index2 < 0) {
					document.editarIndicadorForm.indicadorAsociadoFrecuencia.value = informacion.substring(index1, informacion.length - 1);
				} else {
					document.editarIndicadorForm.indicadorAsociadoFrecuencia.value = informacion.substring(index1, index2);
					index1 = informacion.indexOf('][unidadNombre:') + '][unidadNombre:'.length;
					document.editarIndicadorForm.indicadorAsociadoUnidad.value = informacion.substring(index1, informacion.length - 1);
				}
			}
			
			function eventoOnClickIndicadorAsociadoTipo() 
			{
				if (document.editarIndicadorForm.indicadorAsociadoTipo != null && document.editarIndicadorForm.indicadorAsociadoTipo.value != null && document.editarIndicadorForm.indicadorAsociadoTipo.value == '<bean:write name="editarIndicadorForm" property="indicadorAsociadoTipoProgramado"/>') 
				{
					if (document.editarIndicadorForm.bloqueado.value != "true")
						document.editarIndicadorForm.indicadorAsociadoRevision.disabled = false;
					if ((document.editarIndicadorForm.indicadorAsociadoRevision.value == null) || (document.editarIndicadorForm.indicadorAsociadoRevision.value == '')) 
						document.editarIndicadorForm.indicadorAsociadoRevision.value = '0';
				} 
				else if (document.editarIndicadorForm.indicadorAsociadoRevision != null)
					document.editarIndicadorForm.indicadorAsociadoRevision.disabled = true;
			}
			
			function limpiarIndicadorAsociado(naturaleza) 
			{
				document.editarIndicadorForm.indicadorAsociadoId.value = '';
				document.editarIndicadorForm.indicadorSumatoria.value = '';
				document.editarIndicadorForm.indicadorSumatoriaFrecuenciaId.value = '';
				document.editarIndicadorForm.indicadorAsociadoFrecuencia.value = '';
				document.editarIndicadorForm.indicadorAsociadoUnidad.value = '';
			}
			
			function eventoOnClickCorte(formloaded) 
			{
				if (formloaded == undefined)
					formloaded = true;
				if (document.editarIndicadorForm.corte[1].checked) 
				{
					document.editarIndicadorForm.tipoCargaMedicion[0].disabled = true;
					document.editarIndicadorForm.tipoCargaMedicion[1].disabled = true;
				} 
				else 
				{
					if (document.editarIndicadorForm.bloquearIndicadorIniciativa.value == "false")
					{
						if (document.editarIndicadorForm.bloqueado.value != "true")
						{
							document.editarIndicadorForm.tipoCargaMedicion[0].disabled = false;
							document.editarIndicadorForm.tipoCargaMedicion[1].disabled = false;
						}
					}
					else
					{
						document.editarIndicadorForm.tipoCargaMedicion[0].disabled = true;
						document.editarIndicadorForm.tipoCargaMedicion[1].disabled = true;
					}
				}
				
				if (document.editarIndicadorForm.naturaleza.value == <bean:write name="editarIndicadorForm" property="naturalezaSumatoria" />) 
				{
					if (formloaded)
						document.editarIndicadorForm.tipoSumaMedicion[0].checked = true;
					if (document.editarIndicadorForm.corte[0].checked) 
					{
						document.editarIndicadorForm.tipoSumaMedicion[0].disabled = true;
						document.editarIndicadorForm.tipoSumaMedicion[1].disabled = true;
					} 
					else 
					{
						if (document.editarIndicadorForm.bloqueado.value != "true")
						{
							document.editarIndicadorForm.tipoSumaMedicion[0].disabled = false;
							document.editarIndicadorForm.tipoSumaMedicion[1].disabled = false;
						}
					}
				}
			}
			
			function getURL()
			{
				return '&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value + '&permitirIniciativas=true';
			}
			
			function seleccionarCodigoEnlace()
			{
				var valor = document.editarIndicadorForm.codigoEnlace.value;
				if (valor == '' || valor == null || typeof(valor) == "undefined")
					valor = undefined;
				
				abrirSelectorCodigoEnlace('editarIndicadorForm', 'codigoEnlace', null, document.editarIndicadorForm.codigoEnlace.value, 'funcionCierreSelectorCodigoEnlace()', valor);
			}

			function funcionCierreSelectorCodigoEnlace()
			{
			}
			
			function limpiarCodigoEnlace()
			{
				document.editarIndicadorForm.codigoEnlace.value = "";				
			}
			
			function abrirSelectorCodigoEnlace(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre, valorBusqueda) 
			{		
				var parametroFuncionCierre = '';
				if ((funcionCierre != null) && (funcionCierre != '')) 
					parametroFuncionCierre = '&funcionCierre=' + funcionCierre;

				var url = '?nombreForma=' + nombreForma;
				url = url + '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto;
				url = url + '&seleccionados=' + seleccionados + parametroFuncionCierre;
				if (typeof(valorBusqueda) != "undefined")
					url = url + '&valorBusqueda=' + valorBusqueda;
				
				abrirVentanaModal('<html:rewrite action="/codigoenlace/seleccionarCodigoEnlace" />' + url,'seleccionarUnidadesMedida', '600', '400');
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarIndicadorForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/indicadores/guardarIndicador" focus="nombre">

			<html:hidden property="indicadorId" />
			<html:hidden property="claseId" />
			<html:hidden property="responsableNotificacionId" />
			<html:hidden property="responsableFijarMetaId" />
			<html:hidden property="responsableLograrMetaId" />
			<html:hidden property="responsableSeguimientoId" />
			<html:hidden property="responsableCargarMetaId" />
			<html:hidden property="responsableCargarEjecutadoId" />
			<html:hidden property="alertaIndicadorIdZonaVerde" />
			<html:hidden property="alertaIndicadorIdZonaAmarilla" />
			<html:hidden property="frecuenciaOriginal" />
			<html:hidden property="naturalezaOriginal" />
			<html:hidden property="tieneMediciones" />
			<html:hidden property="parametroSuperiorIndicadorId" />
			<html:hidden property="parametroInferiorIndicadorId" />
			<html:hidden property="bloqueado" />
			<html:hidden property="bloquearIndicadorIniciativa" />

			<html:hidden property="iniciativaId" />
			<html:hidden property="planId" />
			<html:hidden property="perspectivaId" />
			<html:hidden property="nombreLargo" />

			<%-- Campos hidden para el manejo de la fórmula --%>
			<input type="hidden" name="indicadorInsumoSeleccionadoIds">
			<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
			<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">
			<input type="hidden" name="nombreIndicadorZonaVerde">
			<input type="hidden" name="nombreIndicadorZonaAmarilla">

			<%-- Campos hidden para el manejo de la naturaleza cualitativa --%>
			<html:hidden property="escalaCualitativa" />

			<%-- Campos hidden para el manejo de la naturaleza sumatoria --%>
			<html:hidden property="indicadorSumatoriaId" />
			<html:hidden property="indicadorSumatoriaFrecuenciaId" />

			<%-- Campos hidden para el manejo de la naturaleza promedio --%>
			<html:hidden property="indicadorPromedioId" />
			<html:hidden property="indicadorPromedioFrecuenciaId" />

			<%-- Campos hidden para el manejo de la naturaleza indice --%>
			<html:hidden property="indicadorIndiceId" />
			<html:hidden property="indicadorIndiceFrecuenciaId" />

			<input type="hidden" name="nombreIndicador">
			<html:hidden property="indicadoresPlan" />

			<html:hidden property="indicadorAsociadoId" />
			<input name="indicadorAsociadoFrecuenciaId" type="hidden" />

			<input id="categoriaSeleccionada" type="hidden" name="categoriaSeleccionada" />

			<bean:define scope="page" id="iniciativaDisabled" value="false"></bean:define>
			<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
				<bean:define scope="page" id="iniciativaDisabled" value="true"></bean:define>
			</logic:equal>

			<vgcinterfaz:contenedorForma width="820px" height="630px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20" marginTop="5px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarIndicadorForm" property="indicadorId" value="0">
						<logic:empty name="editarIndicadorForm" property="nombreIndicadorSingular">
							<vgcutil:message key="jsp.editarindicador.titulo.nuevo" />
						</logic:empty>
						<logic:notEmpty name="editarIndicadorForm" property="nombreIndicadorSingular">
							<bean:write name="editarIndicadorForm" property="nombreIndicadorSingular" />
						</logic:notEmpty>
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarIndicadorForm" property="indicadorId" value="0">
						<logic:empty name="editarIndicadorForm" property="nombreIndicadorSingular">
							<vgcutil:message key="jsp.editarindicador.titulo.modificar" />
						</logic:empty>
						<logic:notEmpty name="editarIndicadorForm" property="nombreIndicadorSingular">
							<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write name="editarIndicadorForm" property="nombreIndicadorSingular" />
						</logic:notEmpty>
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="520px" width="690px" nombre="editarIndicador">

					<%-- Datos Básicos --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="datosBasicos">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorDatosBasicos.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>
					
					<%-- Definición --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="definicion">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.definicion" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorDefinicion.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

					<%-- Asociar --%>
					<vgcinterfaz:panelContenedor anchoPestana="90px" nombre="asociar" onclick="mostrarPanelIndicador('asociar');">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.asociar" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorAsociar.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

					<%-- Parámetros --%>
					<vgcinterfaz:panelContenedor anchoPestana="90px" nombre="parametros" onclick="mostrarPanelIndicador('parametros');">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.parametros" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorParametros.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

					<%-- Alertas --%>
					<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="alertas" onclick="mostrarPanelIndicador('alertas');">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorAlertas.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

					<%-- Responsables --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="responsables" onclick="mostrarPanelIndicador('responsables');">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorResponsable.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

					<%-- Relaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="90" nombre="relaciones">
						<%-- Título de la Pestaña --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarindicador.pestana.relaciones" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/editar/editarIndicadorRelaciones.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
						<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
							<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
							<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
						</logic:notEqual>
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
				inicializarCheckboxSincronizadorCamposTexto(document.editarIndicadorForm.nombreLargo, document.editarIndicadorForm.nombre, 50, false);
				asignarSeriesTiempo();
				establecerVisibilidad('divAlertaValorAmarilla', 'Amarilla');
				establecerVisibilidad('divAlertaValorVerde', 'Verde');
				establecerTipoAlerta(<bean:write name="editarIndicadorForm" property="alertaTipoZonaVerde" />, 'alertaMetaZonaVerde', 'celdaZonaVerde');
				establecerTipoAlerta(<bean:write name="editarIndicadorForm" property="alertaTipoZonaAmarilla" />, 'alertaMetaZonaAmarilla', 'celdaZonaAmarilla');
				establecerValorZonaVerde('<bean:write name="editarIndicadorForm" property="alertaMetaZonaVerde" />', '<bean:write name="editarIndicadorForm" property="nombreIndicadorZonaVerde" />', <bean:write name="editarIndicadorForm" property="alertaTipoZonaVerde" />);
				establecerValorZonaAmarilla('<bean:write name="editarIndicadorForm" property="alertaMetaZonaAmarilla" />', '<bean:write name="editarIndicadorForm" property="nombreIndicadorZonaAmarilla" />', <bean:write name="editarIndicadorForm" property="alertaTipoZonaAmarilla" />);
				setPanelDefinicion();
				setListaInsumosFormula();
				setEscalaCualitativa();
				eventoCambioNaturaleza();
				eventoCambioTipoComparacionIndicadorIndice();
				eventoOnClickCorte(false);
				eventoOnClickIndicadorAsociadoTipo();
				inicializarParametrosAcotamiento();
				_loaded = true;
		</script>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarIndicadorForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
