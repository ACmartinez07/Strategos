<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Creado por: Kerwin Arias (05/03/2012) -->
<!-- Modificado por: Kerwin Arias (17/09/2012) -->

//<script type="text/javascript">

	function consultar(forma, inOrden, inPagina) 
	{
		if (typeof(forma.seleccionados) != "undefined")
			forma.seleccionados.value = "";
	
		if (inPagina != null) 
		{
			forma.pagina.value=inPagina;
			forma.submit();
		}	
	
		if (inOrden != null) 
		{
			var cambioOrden = (forma.atributoOrden.value != inOrden);
			forma.atributoOrden.value=inOrden;
			if (cambioOrden) 
				forma.tipoOrden.value = 'ASC';			
			else 
			{
				if (forma.tipoOrden.value != null) 
				{
					if (forma.tipoOrden.value == 'ASC') 
						forma.tipoOrden.value = 'DESC';					
					else 
						forma.tipoOrden.value = 'ASC';					
				} 
				else 
					forma.tipoOrden.value = 'ASC';				
			}
		}
	
		forma.submit();
	}
	
	// Modificado por: Kerwin Arias (27/08/2012)
	function cambioImagenOrden(forma) 
	{
		if ((forma.atributoOrden.value != null) && (forma.atributoOrden.value != '')) 
		{
			url = "<html:rewrite page='/'/>";
			// Hace las validaciones correspondientes y dibuja las imagen
			if (forma.tipoOrden.value == "ASC") 
				document.images[forma.atributoOrden.value].src = url + "componentes/visorLista/arriba.gif";
			else if (forma.tipoOrden.value == "DESC") 
				document.images[forma.atributoOrden.value].src = url + "componentes/visorLista/abajo.gif";							
		}
	}
	
	function verificarObjetoSeleccionado(my_form, form_field, errorMsg, my_action, confirmMsg, multiSelect, errorSimpleSelect) 
	{
		var length = my_form.elements.length;
		var index = 0;
		var checked = 0;
		var totalSelected = 0;
		for( index=0; index < length; index++ ) 
		{
			if( my_form.elements[index].name == form_field && my_form.elements[index].checked  ) 
			{
				checked = 1;
				totalSelected++;
			}
		}
		if ( !checked ) 
		  	alert(errorMsg);
		else 
		{
			if ((!multiSelect) && (totalSelected>1)) 
			{
				alert(errorSimpleSelect);
				return;
			}
			res = false;
			if (confirmMsg.length > 0) 
				res = confirm(confirmMsg);
			else 
				res = true;
			if (res) 
			{
				my_form.action = my_action;
				my_form.submit();
			}
		}
	}
	
	// Modificado por: Kerwin Arias (27/04/2012)
	function eventoMouseFueraFila(forma, fila) 
	{
		if (forma.seleccionados.value == fila.id) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else 
			fila.className='mouseFueraCuerpoListView';
	}
	
	// Modificado por: Kerwin Arias (27/04/2012)
	function eventoMouseEncimaFila(forma, fila) 
	{
		if (forma.seleccionados.value == fila.id) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else 
			fila.className='mouseEncimaCuerpoListView';
	}
	
	// Modificado por: Kerwin Arias (28/07/2012)
	function eventoClickFila(forma, idTabla, fila) 
	{
		var seleccionadoAnterior = forma.seleccionados.value;
		if (forma.seleccionados.value != fila.id) 
		{
			forma.seleccionados.value = fila.id;
			var objeto = document.getElementById('valor' + fila.id);
			if (objeto != null) 
			{
				var valor = objeto.innerHTML;
				if (forma.valoresSeleccionados != null) 
					forma.valoresSeleccionados.value = valor;
			}
			eventoMouseFueraFila(forma, fila);
			setImagenSeleccionadoFila(forma, fila);		
		} 
		else 
		{
			if (forma.valoresSeleccionados != null) 
				forma.seleccionados.value = '';
		}
		if ((seleccionadoAnterior != null) && (seleccionadoAnterior != '')) 
		{
			objetoFilaAnterior = document.getElementById(seleccionadoAnterior);
			if (objetoFilaAnterior != null) 
			{
				eventoMouseFueraFila(forma, objetoFilaAnterior);
				setImagenSeleccionadoFila(forma, objetoFilaAnterior);		
			}
		}
	}
	
	// Modificado por: Kerwin Arias (28/07/2012)
	function actualizarSeleccionados(forma, idTabla) 
	{
		if ((forma.seleccionados.value != null) && (forma.seleccionados.value != '')) 
		{
			var fila = document.getElementById(forma.seleccionados.value);
			if (fila != null) 
			{
				eventoMouseFueraFila(forma, fila);
				setImagenSeleccionadoFila(forma, fila);
			}
		}
		else
		{
			var tabla = document.getElementById(idTabla);
			var i = 0;
			for (i = 1; i < tabla.rows.length; i++) 
			{
				var fila = tabla.rows[i];
				eventoMouseEncimaFila(forma, fila);
				eventoMouseFueraFila(forma, fila);
				setImagenSeleccionadoFila(forma, fila);
			}
		}
	}
	
	// Modificado por: Kerwin Arias (28/07/2012)
	function setImagenSeleccionadoFila(forma, fila) 
	{
		// Hace las validaciones correspondientes y dibuja las imagen
		url = "<html:rewrite page='/'/>";
		var celdas = fila.cells;
		var celdaSelector = celdas[0];
		if (celdaSelector.id != null) 
		{
			if (celdaSelector.id.indexOf('selector') == 0) 
			{
				if (forma.seleccionados.value == fila.id) 
					celdaSelector.innerHTML = '<img src="' + url + 'componentes/visorLista/seleccionado.gif" border="0" width="10" height="10">';
				else 
					celdaSelector.innerHTML = '&nbsp;';
			}
		}
	}
	
	// Funcion que selecciona/deselecciona todos los elementos de un
	// visor de lista
	// Creado por: Kerwin Arias (06/06/2012)
	function selectUnselectAllObject(forma, campoControl, nombreCampo) 
	{
		var flag;
		if (campoControl.checked) 
			flag = true;
		else 
			flag = false;
		len=forma.elements.length;
		var index=0;
		for( index=0; index < len; index++ ) 
		{
			if(forma.elements[index].name == nombreCampo) 
				forma.elements[index].checked=flag;
		}
	}
	
	// Creado por: Kerwin Arias (10/07/2012)
	function consultarConfigurable(forma, action, campoPagina, campoAtributoOrden, campoTipoOrden, inOrden, inPagina) 
	{
		if (action != null) 
			forma.action = action;
	
		if (inPagina != null) 
		{
			campoPagina.value = inPagina;
			forma.submit();
		}
	
		if (inOrden != null) 
		{
			var cambioOrden = (campoAtributoOrden.value != inOrden);
			campoAtributoOrden.value=inOrden;
			if (cambioOrden) 
				campoTipoOrden.value = 'ASC';			
			else 
			{
				if (campoTipoOrden.value != null) 
				{
					if (campoTipoOrden.value == 'ASC') 
						campoTipoOrden.value = 'DESC';					
					else 
						campoTipoOrden.value = 'ASC';					
				} 
				else 
					campoTipoOrden.value = 'ASC';				
			}
		}
	
		forma.submit();
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function consultarV2(forma, campoPagina, campoAtributoOrden, campoTipoOrden, inOrden, inPagina) 
	{
		if (typeof(forma.seleccionados) != "undefined")
			forma.seleccionados.value = "";

		if (inPagina != null) 
		{
			campoPagina.value = inPagina;
			forma.submit();
		}
	
		if (inOrden != null) 
		{
			var cambioOrden = (campoAtributoOrden.value != inOrden);
			campoAtributoOrden.value=inOrden;
			if (cambioOrden) 
				campoTipoOrden.value = 'ASC';			
			else 
			{
				if (campoTipoOrden.value != null) 
				{
					if (campoTipoOrden.value == 'ASC') 
						campoTipoOrden.value = 'DESC';					
					else 
						campoTipoOrden.value = 'ASC';					
				} 
				else 
					campoTipoOrden.value = 'ASC';				
			}
		}
	
		forma.submit();
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function cambioImagenOrdenV2(campoAtributoOrden, campoTipoOrden) 
	{
		url = "<html:rewrite page='/'/>";
		// Hace las validaciones correspondientes y dibuja las imagen
		if (campoTipoOrden.value == "ASC") 
			document.images[campoAtributoOrden.value].src = url + "componentes/visorLista/arriba.gif";
		else if (campoTipoOrden.value == "DESC") 
			document.images[campoAtributoOrden.value].src = url + "componentes/visorLista/abajo.gif";							
	}
	
	// Creado por: Kerwin Arias (28/07/2012)
	function eventoClickFilaV2(campoIdSeleccionado, campoValorSeleccionado, idTabla, fila) 
	{
		var seleccionadoAnterior = campoIdSeleccionado.value;
		if (campoIdSeleccionado.value != fila.id) 
		{
			campoIdSeleccionado.value = fila.id;
			var objeto = document.getElementById('valor' + fila.id);
			if (objeto != null) 
			{
				var valor = objeto.innerHTML;
				if (campoValorSeleccionado != null) 
					campoValorSeleccionado.value = valor;
			}
			eventoMouseFueraFilaV2(campoIdSeleccionado, fila);
			setImagenSeleccionadoFilaV2(campoIdSeleccionado, fila);		
		} 
		else 
		{
			if (campoValorSeleccionado != null) 
				campoIdSeleccionado.value = '';
		}
		if ((seleccionadoAnterior != null) && (seleccionadoAnterior != '')) 
		{
			objetoFilaAnterior = document.getElementById(seleccionadoAnterior);
			if (objetoFilaAnterior != null) 
			{
				eventoMouseFueraFilaV2(campoIdSeleccionado, objetoFilaAnterior);
				setImagenSeleccionadoFilaV2(campoIdSeleccionado, objetoFilaAnterior);		
			}
		}
	}
	
	// Modificado por: Kerwin Arias (28/07/2012)
	function actualizarSeleccionadosV2(campoIdSeleccionado, idTabla) 
	{
		if ((campoIdSeleccionado.value != null) && (campoIdSeleccionado.value != '')) 
		{
			var fila = document.getElementById(campoIdSeleccionado.value);
			if (fila != null) 
			{
				eventoMouseFueraFilaV2(campoIdSeleccionado, fila);
				setImagenSeleccionadoFilaV2(campoIdSeleccionado, fila);
			}
		}
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function eventoMouseFueraFilaV2(campoIdSeleccionado, fila) 
	{
		if (campoIdSeleccionado.value != fila.id) 
			fila.className='mouseFueraCuerpoListView';
		else 
			fila.className='cuerpoListViewFilaSeleccionada';
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function eventoMouseEncimaFilaV2(campoIdSeleccionado, fila) 
	{
		if (campoIdSeleccionado.value != fila.id) 
			fila.className='mouseEncimaCuerpoListView';
		else 
			fila.className='cuerpoListViewFilaSeleccionada';
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function setImagenSeleccionadoFilaV2(campoIdSeleccionado, fila) 
	{
		// Hace las validaciones correspondientes y dibuja las imagen
		url = "<html:rewrite page='/'/>";
		var celdas = fila.cells;
		var celdaSelector = celdas[0];
		if (celdaSelector.id != null) 
		{
			if (celdaSelector.id.indexOf('selector') == 0) 
			{
				if (campoIdSeleccionado.value == fila.id) 
					celdaSelector.innerHTML = '<img src="' + url + 'componentes/visorLista/seleccionado.gif" border="0" width="10" height="10">';
				else 
					celdaSelector.innerHTML = '&nbsp;';
			}
		}
	}
	
	function inicializarVisorListaSeleccionSimple(campoIdSeleccionado, idTabla) 
	{
		actualizarSeleccionadosV2(campoIdSeleccionado, idTabla);
	}
	
	// ******************************************************
	// *** Funciones de Visor Lista de Seleccion Multiple ***
	// ******************************************************
	
	// Creado por: Kerwin Arias (27/08/2012)
	function getValorFilaSeleccionMultiple(filaId) 
	{
	    var objeto = document.getElementById('valor' + filaId);
		if (objeto != null) {
		  var valor = objeto.innerHTML;
		  if (valor != null) 
			 return valor;
		  else 
		     return ''; 
		} 
		else 
			return ''; 
	}
	
	
	// Creado por: Kerwin Arias (27/08/2012)
	function eventoClickFilaSeleccionMultiple(campoIdsSeleccionados, campoValoresSeleccionados, idTabla, fila) 
	{
		if ((campoIdsSeleccionados.value == null) || (campoIdsSeleccionados.value == '') || (campoIdsSeleccionados.value == 'null')) 
			campoIdsSeleccionados.value = ",";
		else 
			campoIdsSeleccionados.value = "," + campoIdsSeleccionados.value + ",";
	
		if (campoValoresSeleccionados != null) 
		{
			if ((campoValoresSeleccionados.value == null) || (campoValoresSeleccionados.value == '') || (campoValoresSeleccionados.value == 'null')) 
				campoValoresSeleccionados.value = ",";
			else 
				campoValoresSeleccionados.value = "," + campoValoresSeleccionados.value + ",";
		}
			
		var index = campoIdsSeleccionados.value.indexOf(',' + fila.id + ',');
		if (index < 0) 
		{
			campoIdsSeleccionados.value = campoIdsSeleccionados.value + fila.id + ',';
			if (campoValoresSeleccionados != null) 
				campoValoresSeleccionados.value = campoValoresSeleccionados.value + getValorFilaSeleccionMultiple(fila.id) + ",";
		} 
		else 
		{
			campoIdsSeleccionados.value = campoIdsSeleccionados.value.replace(',' + fila.id + ',',',');		
			if (campoValoresSeleccionados != null) 
				campoValoresSeleccionados.value = campoValoresSeleccionados.value.replace(',' + getValorFilaSeleccionMultiple(fila.id) + ',',',');		
		} 
	
	    campoIdsSeleccionados.value = campoIdsSeleccionados.value.substring(1, campoIdsSeleccionados.value.length);
		if (campoValoresSeleccionados != null) 
			campoValoresSeleccionados.value = campoValoresSeleccionados.value.substring(1, campoValoresSeleccionados.value.length);
	
		if (campoIdsSeleccionados.value != '') 
		{
			campoIdsSeleccionados.value = campoIdsSeleccionados.value.substring(0, campoIdsSeleccionados.value.length-1);
			if (campoValoresSeleccionados != null) 
				campoValoresSeleccionados.value = campoValoresSeleccionados.value.substring(0, campoValoresSeleccionados.value.length-1);
		}
		
		actualizarSeleccionadosSeleccionMultiple(campoIdsSeleccionados, idTabla);
	}
	
	// Creado por: Kerwin Arias (19/01/2012)
	function eventoMouseFueraFilaSeleccionMultiple(campoIdsSeleccionados, fila) 
	{
		if (fila.id == campoIdsSeleccionados.value) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else if (campoIdsSeleccionados.value.indexOf(fila.id + ',') == 0) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else if (campoIdsSeleccionados.value.indexOf(',' + fila.id + ',') > -1) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else 
		{
			var index = campoIdsSeleccionados.value.length - fila.id.length - ','.length;
			if ((campoIdsSeleccionados.value.indexOf(',' + fila.id) == index) && (index > -1)) 
				fila.className='cuerpoListViewFilaSeleccionada';
			else 
				fila.className='mouseFueraCuerpoListView';
		}
	}
	
	// Creado por: Kerwin Arias (19/01/2012)
	function eventoMouseEncimaFilaSeleccionMultiple(campoIdsSeleccionados, fila) 
	{
		if (fila.id == campoIdsSeleccionados.value) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else if (campoIdsSeleccionados.value.indexOf(fila.id + ',') == 0) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else if (campoIdsSeleccionados.value.indexOf(',' + fila.id + ',') > -1) 
			fila.className='cuerpoListViewFilaSeleccionada';
		else 
		{
			var index = campoIdsSeleccionados.value.length - fila.id.length - ','.length;
	
			if ((campoIdsSeleccionados.value.indexOf(',' + fila.id) == index) && (index > -1)) 
				fila.className='cuerpoListViewFilaSeleccionada';
			else 
				fila.className='mouseEncimaCuerpoListView';
		}
	}
	
	function setImagenSeleccionadoFilaSeleccionMultiple(campoIdsSeleccionados, fila) 
	{
		// Hace las validaciones correspondientes y dibuja las imagen
		url = "<html:rewrite page='/'/>";
		if (document.images['img' + fila.id] != null) 
		{
			if (fila.id == campoIdsSeleccionados.value) 
			{
				document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
				document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';
			} 
			else if (campoIdsSeleccionados.value.indexOf(fila.id + ',') == 0) 
			{			
				document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
				document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';			
			} 
			else if (campoIdsSeleccionados.value.indexOf(',' + fila.id + ',') > -1) 
			{
				document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
				document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';
			} 
			else 
			{
				var index = campoIdsSeleccionados.value.length - fila.id.length - ','.length;
		
				if ((campoIdsSeleccionados.value.indexOf(',' + fila.id) == index) && (index > -1)) 
				{
					document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
					document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';
	
				} 
				else 
					document.images['img' + fila.id].src = url + "componentes/visorLista/transparente.gif";
			}	
		}
	}
	
	// Creado por: Kerwin Arias (10/07/2012)
	function actualizarSeleccionadosSeleccionMultiple(campoIdsSeleccionados, idTabla) 
	{
		var tabla = document.getElementById(idTabla);
		var total = tabla.rows.length;
		for (i = 1; i < total; i++) 
		{
			var fila = tabla.rows[i];
			eventoMouseEncimaFilaSeleccionMultiple(campoIdsSeleccionados, fila);
			eventoMouseFueraFilaSeleccionMultiple(campoIdsSeleccionados, fila);
			setImagenSeleccionadoFilaSeleccionMultiple(campoIdsSeleccionados, fila);
		}
	}
	
	// Creado por: Kerwin Arias (16/01/2012)
	function inicializarVisorListaSeleccionMultiple(campoIdsSeleccionados, idTabla) 
	{
		actualizarSeleccionadosSeleccionMultiple(campoIdsSeleccionados, idTabla);
	}
	
	function verificarElementoUnicoSeleccionMultiple(campoIdsSeleccionados) 
	{
		if ((campoIdsSeleccionados.value == null) || (campoIdsSeleccionados.value == '')) 
		{
			alert('Debe seleccionar un elemento para poder ejecutar la operación seleccionada');
			return false;
		} 
		else if (campoIdsSeleccionados.value.indexOf(',') > -1) 
		{
			alert('Debe seleccionar un solo elemento para poder ejecutar la operación seleccionada');
			return false;
		} 
		else 
			return true;
	}
	
	function verificarSeleccionMultiple(campoIdsSeleccionados) 
	{
		if ((campoIdsSeleccionados.value == null) || (campoIdsSeleccionados.value == '')) 
		{
			alert('Debe seleccionar un elemento para poder ejecutar la operación seleccionada');
			return false;
		} 
		else 
			return true;
	}
	
	function getObjetos(campoIdsSeleccionados)
	{
		var arrObjetosId = new Array(0);
		
		if ((campoIdsSeleccionados.value != null) || (campoIdsSeleccionados.value =! '')) 
			arrObjetosId = campoIdsSeleccionados.value.split(",");
	
		return arrObjetosId;
	}

//</script>
