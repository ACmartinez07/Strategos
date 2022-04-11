<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript"> 

	/** funciones javascript para el selector de selectores */ 	    
	function seleccionarSelectores() 
	{               
		window.document.configurarReporteGraficoForm.selectorId.value = window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
		window.document.configurarReporteGraficoForm.nombreSelector.value = window.document.configurarReporteGraficoForm.valoresSeleccionadosDimensiones.value;
 	    return agregarSelectores();
	}
		
	var arregloSelectores = new Array();
	var separadorElementos = '<bean:write name="configurarReporteGraficoForm" property="separadorElementos" />';
	var separadorAtributos = '<bean:write name="configurarReporteGraficoForm" property="separadorAtributos" />';
	var separadorVariables = '<bean:write name="configurarReporteGraficoForm" property="separadorVariables" />';
		
	function limpiarValoresSelectores() 
	{
		document.configurarReporteGraficoForm.valorSelector1.value = '';
		document.configurarReporteGraficoForm.valorSelector2.value = '';
		document.configurarReporteGraficoForm.valorSelector3.value = '';
		document.configurarReporteGraficoForm.valorSelector4.value = '';
	}
		
	function limpiarSelectores() 
	{
		arregloSelectores = new Array();			
		dibujarTablaSelectores();
		limpiarValoresSelectores();
	}
		
	function validarSelectores(seleccionadosSelectoresId) 
	{
		var existe = false;	
       
	    //Valida que el elemento no esté en las filas
		existe = (window.document.configurarReporteGraficoForm.filasId.value == seleccionadosSelectoresId);
		  
		//Valida que el elemento no esté en las columnas 
		if (!existe) 
			existe = (window.document.configurarReporteGraficoForm.columnasId.value == seleccionadosSelectoresId);
          
		return !existe;
	}
		
	function agregarSelectores() 
	{
		//Declaración de las variables locales			
		var	nombreSelector = '';
		var	selectorId = '';
		var existe = false;

		var arrNombreSelector = new Array(0);
		var arrSelectorId = new Array(0);
		
		if ((window.document.configurarReporteGraficoForm.selectorId.value != null) && (window.document.configurarReporteGraficoForm.selectorId.value != '')) 
		{			
		   arrNombreSelector = window.document.configurarReporteGraficoForm.nombreSelector.value.split(",");
		   arrSelectorId = window.document.configurarReporteGraficoForm.selectorId.value.split(",");			
		}
		
		for (var k = 0 ; k < arrSelectorId.length ; k++) 
		{
		    nombreSelector = arrNombreSelector[k];
		    selectorId = arrSelectorId[k];

			if ((selectorId == null) || (selectorId == '') || (!validarSelectores(selectorId))) 
				return false;

			//Valida que el elemento no esté repetido
			for (var i = 0; i < arregloSelectores.length; i++) 
			{
				var arregloTabla = arregloSelectores[i].split(separadorAtributos);
				if (arregloTabla[0] == selectorId) 
				{
					existe = true;
					break;
				}
			}
			
		    //Se añade el Nivel en el arreglo global
		    if (!existe) 
		    	arregloSelectores[(arregloSelectores.length)] = selectorId + separadorAtributos + nombreSelector;

			//Se dibuja la tabla
			dibujarTablaSelectores();				
	    }
		
		resizePaneles();
	    
	    return true;	
	}
		
	function dibujarTablaSelectores() 
	{
		//Declaración de las variables locales
		var tabla = document.getElementById("tablaListaSelectores");			
	
		//Se eliminan todas las filas
		var numFilas = tabla.getElementsByTagName("tr").length;
		if (numFilas > 0) 
		{
			valor = numFilas - 1;
			for (i = 0; i < numFilas; i++) 
			{
				tabla.deleteRow(valor);
				valor--;												
			}
		}
	
		//Se recorre el arreglo global
		for (var i = 0; i < arregloSelectores.length; i++) 
		{
			//Se establecen los valores de la tabla
			var arregloRegistro = arregloSelectores[i].split(separadorAtributos);
			tabla.insertRow(tabla.rows.length);
			var tr = tabla.rows[tabla.rows.length-1];
			tr.height = "15px";
			
			var td = document.createElement("td");
			td.align = "left";
			td.width = "100%";				
			var funcionEjemplo = 'onClick="javascript:borrarSelector(\'' + arregloRegistro[0] + '\');"';
			var srcImg = "<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>";
			var title = "";				
			var funcion = '';
			td.innerHTML = ('<img style="cursor:pointer" src="' + srcImg + '" ' + funcion + ' title="' + title + '" border="0" width="10" height="10">' + '&nbsp;&nbsp;' + arregloRegistro[1]);					
			tr.appendChild(td);								
		}	
	}
			
	function borrarSelector(valor) 
	{
		//Declaración de las variables locales
		var arregloTemporal = new Array();
		var n = 0;
		var i = 0;

		//Se pasa el arreglo global a uno temporal
		for (i = 0; i < arregloSelectores.length; i++) 
			arregloTemporal[i] = arregloSelectores[i];
	
		//Se limpia el arreglo global
		arregloSelectores = new Array();

		//Se evalua cada elemento con el valor que se desea eliminar
		for (i = 0; i < arregloTemporal.length; i++) 
		{
			var arregloRegistro = arregloTemporal[i].split(separadorAtributos);

			//Si es igual no se añade al arreglo global
			if (arregloRegistro[0] != valor) 
			{
				arregloSelectores[n] = arregloTemporal[i];
				n++;
			}
		}

		//Se dibuja la tabla
		dibujarTablaSelectores();	
		limpiarValoresSelectores();	
	}
						
	function cargarSelectoresExistentes() 
	{
		if ((window.document.configurarReporteGraficoForm.textoSelectores.value != null) && (window.document.configurarReporteGraficoForm.textoSelectores.value != "")) 
		{				
			arregloSelectores = window.document.configurarReporteGraficoForm.textoSelectores.value.split(separadorElementos);				
			dibujarTablaSelectores();
		}
	}
	
	function asignarSelectores() 
	{								
		var str = "";
		for (var i = 0; i < arregloSelectores.length; i++) 
			str = str + arregloSelectores[i] + separadorElementos;

		return str.substring(0, str.length - separadorElementos.length);
	}
      
	/** funciones javascript para el selector de filas */
	function seleccionarFilas() 
    {
		if (validarFilas(window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value)) 
        {
			var hayVariables = variablesCombinadas(false);
       		if (hayVariables && window.document.configurarReporteGraficoForm.filasId.value != '')
      				window.document.configurarReporteGraficoForm.filasId.value = window.document.configurarReporteGraficoForm.filasId.value + separadorVariables + window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
			else
				window.document.configurarReporteGraficoForm.filasId.value = window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
			
			if (!hayVariables)
			{
			    window.document.configurarReporteGraficoForm.nombreFilas.value = window.document.configurarReporteGraficoForm.valoresSeleccionadosDimensiones.value;       
		    	document.getElementById("dimensionFilas").innerHTML=window.document.configurarReporteGraficoForm.nombreFilas.value;
			}
	    	
			return true;
      	} 
        else 
        	return false;
	}
		
	function variablesCombinadas(fromColumnas)
	{
		var hayIndicador = false;
		var variables = "";
		if (fromColumnas == undefined)
			fromColumnas = false;
		
		if (fromColumnas)
			variables = window.document.configurarReporteGraficoForm.columnasId.value.split(separadorVariables);
		else
			variables = window.document.configurarReporteGraficoForm.filasId.value.split(separadorVariables);
		
		for (var i = 0; i < variables.length; i++)
		{
			if (variables[0] == 4)
				hayIndicador = true;
		}
		
		return (hayIndicador && (window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value == 1 || window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value == 2));;
	}
      
	function limpiarFilas() 
	{
		window.document.configurarReporteGraficoForm.filasId.value = '';
  		window.document.configurarReporteGraficoForm.nombreFilas.value = '';  
  		document.getElementById("dimensionFilas").innerHTML = '&nbsp;';            
	}       
       
	function validarFilas(seleccionadosFilasId) 
    {
    	var existe = false;	
       
		//Valida que el elemento no esté en los selectores
	  	for (var i = 0; i < arregloSelectores.length; i++) 
	  	{
			var arregloTabla = arregloSelectores[i].split(separadorAtributos);
			if (arregloTabla[0] == seleccionadosFilasId) 
			{
				existe = true;
				break;
			}
	  	}

		//Valida que el elemento no esté en las columnas			 
		if (!existe) 
			existe = (window.document.configurarReporteGraficoForm.columnasId.value == seleccionadosFilasId);
          
		return !existe;
	}
       
    /** funciones javascript para el selector de columnas */
	function seleccionarColumnas() 
    {
    	if (validarColumnas(window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value)) 
        {
			var hayVariables = variablesCombinadas(true);
    		if (hayVariables && window.document.configurarReporteGraficoForm.columnasId.value != '')
   				window.document.configurarReporteGraficoForm.columnasId.value = window.document.configurarReporteGraficoForm.columnasId.value + separadorVariables + window.document.configurarReporteGraficoFormm.seleccionadosDimensiones.value;
			else
				window.document.configurarReporteGraficoForm.columnasId.value = window.document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
			
			if (!hayVariables)
			{
				window.document.configurarReporteGraficoForm.nombreColumnas.value = window.document.configurarReporteGraficoForm.valoresSeleccionadosDimensiones.value;       
			    document.getElementById("dimensionColumnas").innerHTML=window.document.configurarReporteGraficoForm.nombreColumnas.value;
			}
		    
		    return true;
		} 
        else 
        	return false;
	}
       
    function limpiarColumnas() 
    {
    	window.document.configurarReporteGraficoForm.columnasId.value = '';
		window.document.configurarReporteGraficoForm.nombreColumnas.value = '';  
		document.getElementById("dimensionColumnas").innerHTML = '&nbsp;';
	}     
       
    function validarColumnas(seleccionadosColumnasId) 
    {
		var existe = false;	
       
	    //Valida que el elemento no esté en los selectores
		for (var i = 0; i < arregloSelectores.length; i++) 
		{
			var arregloTabla = arregloSelectores[i].split(separadorAtributos);
			if (arregloTabla[0] == seleccionadosColumnasId) 
			{
				existe = true;
				break;
			}
		}
		 
		//Valida que el elemento no esté en las columnas 
		if (!existe) 
			existe = (window.document.configurarReporteGraficoForm.filasId.value == seleccionadosColumnasId);
          
		return !existe;
	}         
 
 	// -------------------------------------------------		
	// código para refrescar selección de dimensiones
	// -------------------------------------------------		
	function refrescarDimensionesMarcadas() 
 	{
		// refrescar dimensiones marcadas selectores
		for (var i = 0; i < arregloSelectores.length; i++) 
		{
			var arregloRegistro = arregloSelectores[i].split(separadorAtributos);
			marcarSelectoresSeleccionadas(arregloRegistro[0]);
		}
		    
		if ((document.configurarReporteGraficoForm.filasId.value != null) && (document.configurarReporteGraficoForm.filasId.value != '') && (document.configurarReporteGraficoForm.filasId.value != '0')) 
		{		
			marcarFilasSeleccionadas(document.configurarReporteGraficoForm.filasId.value);
	    	document.getElementById("dimensionFilas").innerHTML=window.document.configurarReporteGraficoForm.nombreFilas.value;
		}
		    
		if ((document.configurarReporteGraficoForm.columnasId.value != null) && (document.configurarReporteGraficoForm.columnasId.value != '')  && (document.configurarReporteGraficoForm.columnasId.value != '0')) 
		{		    		         
			marcarColumnasSeleccionadas(document.configurarReporteGraficoForm.columnasId.value);
	    	document.getElementById("dimensionColumnas").innerHTML=window.document.configurarReporteGraficoForm.nombreColumnas.value;
		}		    
	}

	function marcarSelectoresSeleccionadas(dimensionId) 
	{
		var srcImgSelectoresOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		var indexDimension = getIndexDimension(dimensionId);
		   
		for (var i = 0 ; i < document.configurarReporteGraficoForm.imgSelectores.length ; i++) 
		{
			if (indexDimension == i) 
		   	{
				document.configurarReporteGraficoForm.imgSelectores[i].src = srcImgSelectoresOn;	
		   	    break;
			}			   	  
		}		
	}

	function marcarFilasSeleccionadas(dimensionId) 
	{
		var srcImgFilasOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		var dimensiones = dimensionId.split(separadorVariables);
		for (var j = 0 ; j < dimensiones.length ; j++)
		{
			var indexDimension = getIndexDimension(dimensiones[j]);
		   	for (var i = 0 ; i < document.configurarReporteGraficoForm.imgFilas.length ; i++) 
		   	{
		   		if (indexDimension == i) 
		   	  	{
		   	    	document.configurarReporteGraficoForm.imgFilas[i].src = srcImgFilasOn;	
		   	    	break;
				}			   	  
			}
		}
	}
		
	function marcarColumnasSeleccionadas(dimensionId) 
	{
		var srcImgColumnasOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		var dimensiones = dimensionId.split(separadorVariables);
		for (var j = 0 ; j < dimensiones.length ; j++)
		{
			var indexDimension = getIndexDimension(dimensiones[j]);
			for (var i = 0 ; i < document.configurarReporteGraficoForm.imgColumnas.length ; i++) 
			{
				if (indexDimension == i) 
				{
			   	    document.configurarReporteGraficoForm.imgColumnas[i].src = srcImgColumnasOn;	
			   	    break;
				}			   	  
			}		
		}
	}
		
	function getIndexDimension(dimensionId) 
	{
		var indexDimension = 0;
			
		for (var i = 0; i < arregloDimensiones.length; i++) 
		{
			var arregloRegistro = arregloDimensiones[i].split(separadorAtributos);
            
			if (arregloRegistro[0] == dimensionId) 
			{
				indexDimension = i;
				break;
			}
		}
			
		return indexDimension; 		
	}
		
</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/reportesgrafico/vistasGraficoJs.jsp"></jsp:include>

<%-- Atributos de la Forma --%>
<html:hidden property="paginaSelectores" />
<html:hidden property="atributoOrdenSelectores" />
<html:hidden property="tipoOrdenSelectores" />
<html:hidden property="seleccionadosSelectores" />
<html:hidden property="textoSelectores" />
<html:hidden property="nombreSelector" />
<html:hidden property="selectorId" />
<html:hidden property="filasId" />
<html:hidden property="nombreFilas" />
<html:hidden property="columnasId" />
<html:hidden property="nombreColumnas" />
<html:hidden property="valorSelector1" />
<html:hidden property="valorSelector2" />
<html:hidden property="valorSelector3" />
<html:hidden property="valorSelector4" />



<%-- Funciones JavaScript locales de la página Jsp --%>
<script>

	// cargar selectores
	cargarSelectoresExistentes();	
	
	refrescarDimensionesMarcadas();
	
</script>