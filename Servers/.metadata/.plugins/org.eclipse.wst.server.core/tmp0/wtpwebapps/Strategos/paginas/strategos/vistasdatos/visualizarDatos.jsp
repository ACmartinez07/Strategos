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
		window.document.configurarVistaDatosForm.selectorId.value = window.document.configurarVistaDatosForm.seleccionadosDimensiones.value;
		window.document.configurarVistaDatosForm.nombreSelector.value = window.document.configurarVistaDatosForm.valoresSeleccionadosDimensiones.value;
 	    return agregarSelectores();
	}
		
	var arregloSelectores = new Array();
	var separadorElementos = '<bean:write name="configurarVistaDatosForm" property="separadorElementos" />';
	var separadorAtributos = '<bean:write name="configurarVistaDatosForm" property="separadorAtributos" />';
	var separadorVariables = '<bean:write name="configurarVistaDatosForm" property="separadorVariables" />';
		
	function limpiarValoresSelectores() 
	{
		document.configurarVistaDatosForm.valorSelector1.value = '';
		document.configurarVistaDatosForm.valorSelector2.value = '';
		document.configurarVistaDatosForm.valorSelector3.value = '';
		document.configurarVistaDatosForm.valorSelector4.value = '';
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
		existe = (window.document.configurarVistaDatosForm.filasId.value == seleccionadosSelectoresId);
		  
		//Valida que el elemento no esté en las columnas 
		if (!existe) 
			existe = (window.document.configurarVistaDatosForm.columnasId.value == seleccionadosSelectoresId);
          
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
		
		if ((window.document.configurarVistaDatosForm.selectorId.value != null) && (window.document.configurarVistaDatosForm.selectorId.value != '')) 
		{			
		   arrNombreSelector = window.document.configurarVistaDatosForm.nombreSelector.value.split(",");
		   arrSelectorId = window.document.configurarVistaDatosForm.selectorId.value.split(",");			
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
		if ((window.document.configurarVistaDatosForm.textoSelectores.value != null) && (window.document.configurarVistaDatosForm.textoSelectores.value != "")) 
		{				
			arregloSelectores = window.document.configurarVistaDatosForm.textoSelectores.value.split(separadorElementos);				
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
		if (validarFilas(window.document.configurarVistaDatosForm.seleccionadosDimensiones.value)) 
        {
			var hayVariables = variablesCombinadas(false);
       		if (hayVariables && window.document.configurarVistaDatosForm.filasId.value != '')
      				window.document.configurarVistaDatosForm.filasId.value = window.document.configurarVistaDatosForm.filasId.value + separadorVariables + window.document.configurarVistaDatosForm.seleccionadosDimensiones.value;
			else
				window.document.configurarVistaDatosForm.filasId.value = window.document.configurarVistaDatosForm.seleccionadosDimensiones.value;
			
			if (!hayVariables)
			{
			    window.document.configurarVistaDatosForm.nombreFilas.value = window.document.configurarVistaDatosForm.valoresSeleccionadosDimensiones.value;       
		    	document.getElementById("dimensionFilas").innerHTML=window.document.configurarVistaDatosForm.nombreFilas.value;
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
			variables = window.document.configurarVistaDatosForm.columnasId.value.split(separadorVariables);
		else
			variables = window.document.configurarVistaDatosForm.filasId.value.split(separadorVariables);
		
		for (var i = 0; i < variables.length; i++)
		{
			if (variables[0] == 4)
				hayIndicador = true;
		}
		
		return (hayIndicador && (window.document.configurarVistaDatosForm.seleccionadosDimensiones.value == 1 || window.document.configurarVistaDatosForm.seleccionadosDimensiones.value == 2));;
	}
      
	function limpiarFilas() 
	{
		window.document.configurarVistaDatosForm.filasId.value = '';
  		window.document.configurarVistaDatosForm.nombreFilas.value = '';  
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
			existe = (window.document.configurarVistaDatosForm.columnasId.value == seleccionadosFilasId);
          
		return !existe;
	}
       
    /** funciones javascript para el selector de columnas */
	function seleccionarColumnas() 
    {
    	if (validarColumnas(window.document.configurarVistaDatosForm.seleccionadosDimensiones.value)) 
        {
			var hayVariables = variablesCombinadas(true);
    		if (hayVariables && window.document.configurarVistaDatosForm.columnasId.value != '')
   				window.document.configurarVistaDatosForm.columnasId.value = window.document.configurarVistaDatosForm.columnasId.value + separadorVariables + window.document.configurarVistaDatosForm.seleccionadosDimensiones.value;
			else
				window.document.configurarVistaDatosForm.columnasId.value = window.document.configurarVistaDatosForm.seleccionadosDimensiones.value;
			
			if (!hayVariables)
			{
				window.document.configurarVistaDatosForm.nombreColumnas.value = window.document.configurarVistaDatosForm.valoresSeleccionadosDimensiones.value;       
			    document.getElementById("dimensionColumnas").innerHTML=window.document.configurarVistaDatosForm.nombreColumnas.value;
			}
		    
		    return true;
		} 
        else 
        	return false;
	}
       
    function limpiarColumnas() 
    {
    	window.document.configurarVistaDatosForm.columnasId.value = '';
		window.document.configurarVistaDatosForm.nombreColumnas.value = '';  
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
			existe = (window.document.configurarVistaDatosForm.filasId.value == seleccionadosColumnasId);
          
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
		    
		if ((document.configurarVistaDatosForm.filasId.value != null) && (document.configurarVistaDatosForm.filasId.value != '') && (document.configurarVistaDatosForm.filasId.value != '0')) 
		{		
			marcarFilasSeleccionadas(document.configurarVistaDatosForm.filasId.value);
	    	document.getElementById("dimensionFilas").innerHTML=window.document.configurarVistaDatosForm.nombreFilas.value;
		}
		    
		if ((document.configurarVistaDatosForm.columnasId.value != null) && (document.configurarVistaDatosForm.columnasId.value != '')  && (document.configurarVistaDatosForm.columnasId.value != '0')) 
		{		    		         
			marcarColumnasSeleccionadas(document.configurarVistaDatosForm.columnasId.value);
	    	document.getElementById("dimensionColumnas").innerHTML=window.document.configurarVistaDatosForm.nombreColumnas.value;
		}		    
	}

	function marcarSelectoresSeleccionadas(dimensionId) 
	{
		var srcImgSelectoresOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		var indexDimension = getIndexDimension(dimensionId);
		   
		for (var i = 0 ; i < document.configurarVistaDatosForm.imgSelectores.length ; i++) 
		{
			if (indexDimension == i) 
		   	{
				document.configurarVistaDatosForm.imgSelectores[i].src = srcImgSelectoresOn;	
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
		   	for (var i = 0 ; i < document.configurarVistaDatosForm.imgFilas.length ; i++) 
		   	{
		   		if (indexDimension == i) 
		   	  	{
		   	    	document.configurarVistaDatosForm.imgFilas[i].src = srcImgFilasOn;	
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
			for (var i = 0 ; i < document.configurarVistaDatosForm.imgColumnas.length ; i++) 
			{
				if (indexDimension == i) 
				{
			   	    document.configurarVistaDatosForm.imgColumnas[i].src = srcImgColumnasOn;	
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
<jsp:include flush="true" page="/paginas/strategos/vistasdatos/vistasDatosJs.jsp"></jsp:include>

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

<%-- Representación de la Forma --%>
<vgcinterfaz:contenedorForma>

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.visualizardatos.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<table id="panel-izquierdo-visualizar" style="height: 100%; width :100%; padding: 3px; border-spacing:0; border-collapse:collapse; margin-left: auto; margin-right: auto;" >
		<tr>
			<td valign="top">

				<%-- Selectores --%>
				<table style="width :100%; padding: 3px; border-spacing:0; border-collapse:collapse;" class="contenedorBotonesSeleccion" >				
					<tr class="barraFiltrosForma">
						<td width="10px"><img src="<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/flecha.gif'/>" border="0" width="10" height="10"></td>
						<td><b><vgcutil:message key="jsp.listardimensiones.selectores" /></b></td>				
						<td>&nbsp;</td>
					</tr>	
					<tr class="barraFiltrosForma">
						<td width="10px"></td>
						<td colspan="2" >
							<div style="width: 100%; height: 100%; overflow: auto; border: #666666 0px solid">
								<table id="tablaListaSelectores" class="listView" width="100%" height="100%" border="0" >
									<colgroup>
										<col style="width: 100%">
									</colgroup>
									<tbody class="cuadroTexto">
									</tbody>
								</table>
							</div>					
						</td>					
					</tr>				
				</table>

			</td>
		</tr>		
		<tr height="100%" >
			<td>			
				<%-- Vista Previa --%>
				<table style="width: 100%; height: 100%; padding: 3px; border-spacing:0; border-collapse:collapse; margin-left: auto; margin-right: auto;" class="contenedorBotonesSeleccion" >
					<tr height="60px">
						<td rowspan="2" width="80px"  id="dimensionFilas" align="center" valign="middle" bgcolor="#FFFFCC">&nbsp;</td>
						<td id="dimensionColumnas" align="center" bgcolor="#FFFFCC">&nbsp;</td>
					</tr>
					<tr height="150px">
						<td align="center" bgcolor="#999999">
						<table style="width: 100%; height: 100%; padding: 0px; border-spacing:0; border-collapse:collapse; margin-left: auto; margin-right: auto;">
							<tr>
								<td bgcolor="#F4F4F4">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#FFFFF2">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#F4F4F4">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#FFFFF2">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#F4F4F4">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#FFFFF2">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#F4F4F4">&nbsp;</td>
							</tr>
							<tr>
								<td bgcolor="#FFFFF2">&nbsp;</td>
							</tr>

						</table>				
						</td>
					</tr>
				</table>
			</td>
		</tr>

	</table>

</vgcinterfaz:contenedorForma>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script>

	// cargar selectores
	cargarSelectoresExistentes();	
	
	refrescarDimensionesMarcadas();
	
</script>