<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Creado por: Kerwin Arias (05/03/2012) -->
<!-- Modificado por: Kerwin Arias (27/08/2012) -->

<script type="text/javascript">

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
	
	// Creado por: Kerwin Arias (05/03/2012)
	function eventoMouseFueraFila(forma, fila) 
	{
		if (forma.seleccionados.value != fila.id) 
			fila.className='mouseFueraCuerpoListView';
		else 
			fila.className='cuerpoListViewFilaSeleccionada';
	}
	
	// Creado por: Kerwin Arias (05/03/2012)
	function eventoMouseEncimaFila(forma, fila) 
	{
		if (forma.seleccionados.value != fila.id) 
			fila.className='mouseEncimaCuerpoListView';
		else 
			fila.className='cuerpoListViewFilaSeleccionada';
	}
	
	// Creado por: Kerwin Arias (05/03/2012)
	function eventoClickFila(forma, idTabla, fila) 
	{
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
		} 
		else 
		{
			if (forma.valoresSeleccionados != null) 
				forma.seleccionados.value = '';
		}
		actualizarSeleccionados(forma, idTabla);
	}
	
	function actualizarSeleccionados(forma, idTabla) 
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
	
	function setImagenSeleccionadoFila(forma, fila) 
	{
		// Hace las validaciones correspondientes y dibuja las imagen
		url = "<html:rewrite page='/'/>";
		if (document.images['img' + fila.id] != null) 
		{
			if (forma.seleccionados.value.indexOf(fila.id) == -1) 
				document.images['img' + fila.id].src = url + "componentes/visorLista/transparente.gif";
			else 
			{
				document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
				document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';
			}
		}
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
	
	// Creado por: Kerwin Arias (27/08/2012)
	function cambioImagenOrdenV2(campoAtributoOrden, campoTipoOrden) 
	{
		if ((campoAtributoOrden.value != null) && (campoAtributoOrden.value != '')) 
		{
			url = "<html:rewrite page='/'/>";
			// Hace las validaciones correspondientes y dibuja las imagen
			if (campoTipoOrden.value == "ASC") 
				document.images[campoAtributoOrden.value].src = url + "componentes/visorLista/arriba.gif";
			else if (campoTipoOrden.value == "DESC") 
				document.images[campoAtributoOrden.value].src = url + "componentes/visorLista/abajo.gif";							
		}
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function eventoClickFilaV2(campoIdSeleccionado, campoValorSeleccionado, idTabla, fila) 
	{
		var buscado = fila.id;
		if (campoIdSeleccionado.value != buscado) 
		{
			campoIdSeleccionado.value = buscado;
			var objeto = document.getElementById('valor' + buscado);
			if (objeto != null) 
			{
				var valor = objeto.innerHTML;
				if (campoValorSeleccionado != null) 
					campoValorSeleccionado.value = valor;
			}
		}
		
		actualizarSeleccionadosV2(campoIdSeleccionado, idTabla);
	}
	
	// Creado por: Kerwin Arias (18/06/2012)
	function actualizarSeleccionadosV2(campoIdSeleccionado, idTabla) 
	{
		var tabla = document.getElementById(idTabla);
		var i = 0;
		for (i = 1; i < tabla.rows.length; i++) 
		{
			var fila = tabla.rows[i];
			eventoMouseEncimaFilaV2(campoIdSeleccionado, fila);
			eventoMouseFueraFilaV2(campoIdSeleccionado, fila);
			setImagenSeleccionadoFilaV2(campoIdSeleccionado, fila);
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
		if (document.images['img' + fila.id] != null) 
		{
			if (campoIdSeleccionado.value != fila.id) 
				document.images['img' + fila.id].src = url + "componentes/visorLista/transparente.gif";
			else 
			{
				document.images['img' + fila.id].src = url + "componentes/visorLista/seleccionado.gif";
				document.images['img' + fila.id].title = '<vgcutil:message key="commons.seleccionado" />';
			}
		}
	}
	
	function inicializarVisorListaSeleccionSimple(campoIdSeleccionado, idTabla) 
	{
		actualizarSeleccionadosV2(campoIdSeleccionado, idTabla);
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
	
	// Creado por: Kerwin Arias (27/08/2012)
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
	
	// Creado por: Kerwin Arias (08/09/2012)
	function cambioImagenOrdenV3(campoAtributoOrden, campoTipoOrden, nombreVisorLista) 
	{
		if ((campoAtributoOrden.value != null) && (campoAtributoOrden.value != '')) 
		{
			url = "<html:rewrite page='/'/>";
			// Hace las validaciones correspondientes y dibuja las imagen
			if (campoTipoOrden.value == "ASC") 
				document.images[nombreVisorLista + campoAtributoOrden.value].src = url + "componentes/visorLista/arriba.gif";
			else if (campoTipoOrden.value == "DESC") 
				document.images[nombreVisorLista + campoAtributoOrden.value].src = url + "componentes/visorLista/abajo.gif";							
		}
	}

</script>
