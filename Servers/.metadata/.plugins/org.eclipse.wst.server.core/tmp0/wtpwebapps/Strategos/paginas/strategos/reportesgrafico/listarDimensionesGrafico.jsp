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
<script type="text/javascript">
  
		/** funciones javascript para el selector de dimensiones */
	    var tipoDimensionTiempo = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionTiempo" />';
	    var tipoDimensionVariable = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionVariable" />';
	    var tipoDimensionAtributo = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionAtributo" />';
	    var tipoDimensionIndicador = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionIndicador" />';
	    var tipoDimensionOrganizacion = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionOrganizacion" />';
	    var tipoDimensionPlan = '<bean:write name="configurarReporteGraficoForm" property="tipoDimensionPlan" />';
 	    
 	    function seleccionarDimensiones() 
 	    {               
 	        var parametros = 'seleccionMultiple=1'; 
		}
		
		var arregloDimensiones = new Array();
		var separadorElementos = '<bean:write name="configurarReporteGraficoForm" property="separadorElementos"  />';
		var separadorAtributos = '<bean:write name="configurarReporteGraficoForm" property="separadorAtributos"  />';
		
		function borrarDimensiones() 
		{
			arregloDimensiones = new Array();			
			dibujarTablaDimensiones();
		}
		
		function agregarDimensiones() 
		{
			//Declaración de las variables locales			
			var	nombreDimension = '';
			var	dimensionId = '';
			var existe = false;

			var arrNombreDimension = new Array(0);
			var arrDimensionId = new Array(0);
			if ((window.document.configurarReporteGraficoForm.dimensionId.value != null) && (window.document.configurarReporteGraficoForm.dimensionId.value != '')) 
			{			
			   arrNombreDimension = window.document.configurarReporteGraficoForm.nombreDimension.value.split(",");
			   arrDimensionId = window.document.configurarReporteGraficoForm.dimensionId.value.split(",");			
			}
			
			for (var k = 0 ; k < arrDimensionId.length ; k++) 
			{
			    nombreDimension = arrNombreDimension[k];
			    dimensionId = arrDimensionId[k];

				if ((dimensionId == null) || (dimensionId == '')) 
					return;

	
				//Valida que el elemento no esté repetido
				for (var i = 0; i < arregloDimensiones.length; i++) 
				{
					var arregloTabla = arregloDimensiones[i].split(separadorAtributos);
					if (arregloTabla[0] == dimensionId) 
					{
						existe = true;
						break;
					}
				}
				
			    //Se añade el Nivel en el arreglo global
			    if (!existe) 
			    	arregloDimensiones[(arregloDimensiones.length)] = dimensionId + separadorAtributos + nombreDimension;
	
				//Se dibuja la tabla
				dibujarTablaDimensiones();				
		    }		
		}
		
		function dibujarTablaDimensiones() 
		{
			//Declaración de las variables locales
			var tabla = document.getElementById("tablaListaDimensiones");			
		
			//Se eliminan todas las filas
			var numFilas = tabla.getElementsByTagName("tr").length;
			if (numFilas > 0) 
			{
				valor = numFilas - 1;
				for (var i = 1; i < numFilas; i++) 
				{
					tabla.deleteRow(valor);
					valor--;												
				}
			}
		
			//Se recorre el arreglo global
			for (var i = 0; i < arregloDimensiones.length; i++) 
			{
				//Se establecen los valores de la tabla
				var arregloRegistro = arregloDimensiones[i].split(separadorAtributos);
				tabla.insertRow(tabla.rows.length);
				var tr = tabla.rows[tabla.rows.length-1];
				tr.height = "25px";
				tr.className = "mouseFueraCuerpoListView";
				tr.id = arregloRegistro[0];
				tr.onmouseover = dimesionEventoMouseEncimaFilaSeleccionEvent;
				tr.onmouseout = dimesionEventoMouseFueraFilaSeleccionEvent;
				tr.onclick = dimesionEventoClickFilaSeleccionEvent;

				var td = document.createElement("td");
				td.align = "center";
				td.width = "20px";				
				var srcImgSeleccionar = "<html:rewrite page='/componentes/visorLista/transparente.gif'/>";
				td.innerHTML = ('<img style="cursor:pointer" id="img' + arregloRegistro[0] + '"  src="' + srcImgSeleccionar + '" border="0" width="10" height="10">');					
				tr.appendChild(td);	
				
				td = document.createElement("td");
				td.align = "left";
				td.width = "200px";
				td.id = 'valor' + arregloRegistro[0];
				td.innerHTML = (arregloRegistro[1]);									
				tr.appendChild(td);								
				
				td = document.createElement("td");
				td.align = "center";
				td.width = "100px";				
				var srcImgSelectores = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";
				var titleSelectores = "<vgcutil:message key="jsp.listardimensiones.selectores" />";
				var funcionSelectores = 'onClick="javascript:agregarDimensionSelectores(this, \'' + arregloRegistro[0] + '\', \'' + arregloRegistro[1] + '\');"';				
				td.innerHTML = ('<img style="cursor:pointer" id="imgSelectores" src="' + srcImgSelectores + '" ' + funcionSelectores + ' title="' + titleSelectores + '" border="0" width="20" height="20">');					
				tr.appendChild(td);	

				
		
					
			}				
		}

		function agregarDimensionSelectores(imagen, idDimension, nombreDimension) 
		{
			var srcImgSelectoresOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		   	var srcImgSelectoresOff = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";
		   
		   	var objFila = document.getElementById(idDimension);
		   	dimesionEventoClickFilaSeleccionEjecutar(objFila);
		   
		   	var srcImg = srcImgSelectoresOn;		   
		   	var valido = false;
		   
		   	if (imagen.src.indexOf(srcImgSelectoresOn) > -1) 
		   	{
		      	srcImg = srcImgSelectoresOff;
		      
		      	borrarSelector(idDimension);
		    	valido = true;
		   	} 
		   	else 
		      	valido = seleccionarSelectores();
		   
			if (valido) 
				imagen.src = srcImg;
			
			resizePaneles();
		}

		function agregarDimensionFilas(imagen, idDimension, nombreDimension) 
		{
			var srcImgFilasOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		   	var srcImgFilasOff = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";

		   	var objFila = document.getElementById(idDimension);
		   	dimesionEventoClickFilaSeleccionEjecutar(objFila);
		  
		   	var srcImg = srcImgFilasOn;
		   	var valido = false;
		   	var omitirLimpiarFila = variablesCombinadas(false); 
			
		   	if (imagen.src.indexOf(srcImgFilasOn) > -1) 
		   	{
				srcImg = srcImgFilasOff;

		      	limpiarFilas();
		      	valido = true;		      
		   	}
		   	else
				valido = seleccionarFilas();
		   
 		   	if (valido) 
 		   	{
 		   		if (!omitirLimpiarFila)
 		   			limpiarImgFilas();
		   	 	imagen.src = srcImg;
		   	}		
		}
		
		function agregarDimensionColumnas(imagen, idDimension, nombreDimension) 
		{
			var srcImgColumnasOn = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkon.gif'/>";
		   	var srcImgColumnasOff = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";		

		   	var objFila = document.getElementById(idDimension);
		   	dimesionEventoClickFilaSeleccionEjecutar(objFila);
		
		   	var srcImg = srcImgColumnasOn;
		   	var valido = false;
		   	var omitirLimpiarColumna = variablesCombinadas(true);
		   
		   	if (imagen.src.indexOf(srcImgColumnasOn) > -1) 
		   	{
		      	srcImg = srcImgColumnasOff;

		      	limpiarColumnas();
		      	valido = true;		      
		   	}
		   	else 
		      	valido = seleccionarColumnas();
		   
			if (valido) 
 		   	{
				if (!omitirLimpiarColumna)
					limpiarImgColumnas();
		   	 	imagen.src = srcImg;
		   	}
		}

		function limpiarImgFilas() 
		{
		   var srcImgFilasOff = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";
		   
		   for (var i = 0 ; i < document.configurarReporteGraficoForm.imgFilas.length ; i++) 
		   	  document.configurarReporteGraficoForm.imgFilas[i].src = srcImgFilasOff;	
		}

		function limpiarImgColumnas() 
		{
		   var srcImgColumnasOff = "<html:rewrite page='/paginas/strategos/vistasdatos/imagenes/checkoff.gif'/>";
		   
		   for (var i = 0 ; i < document.configurarReporteGraficoForm.imgColumnas.length ; i++) 
		   	  document.configurarReporteGraficoForm.imgColumnas[i].src = srcImgColumnasOff;	
		}
		
		function dimesionEventoMouseEncimaFilaSeleccionEvent(event) 
		{
		   dimesionEventoMouseEncimaFilaSeleccion(document.configurarReporteGraficoForm.seleccionadosDimensiones, this);
		}
		
		function dimesionEventoMouseFueraFilaSeleccionEvent(event) 
		{
		   dimesionEventoMouseFueraFilaSeleccion(document.configurarReporteGraficoForm.seleccionadosDimensiones, this);		   
		}		
		
		function dimesionEventoClickFilaSeleccionEvent(event) 
		{		
		   setTextoMiembrosDimension();
		   dimesionEventoClickFilaSeleccion(document.configurarReporteGraficoForm.seleccionadosDimensiones, document.configurarReporteGraficoForm.valoresSeleccionadosDimensiones, 'tablaListaDimensiones', this);												   
		   getTextoMiembrosDimension();
		}		

		function dimesionEventoClickFilaSeleccionEjecutar(objFila) 
		{		
		   setTextoMiembrosDimension();
		   dimesionEventoClickFilaSeleccion(document.configurarReporteGraficoForm.seleccionadosDimensiones, document.configurarReporteGraficoForm.valoresSeleccionadosDimensiones, 'tablaListaDimensiones', objFila);												   
		   getTextoMiembrosDimension();
		}	
			
		function setTextoMiembrosDimension() 
		{
		  var seleccionadosDimensiones = document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
		  var textoMiembros = asignarMiembros();
		  
		  if (seleccionadosDimensiones == tipoDimensionTiempo) 
		     document.configurarReporteGraficoForm.textoMiembrosTiempo.value = textoMiembros;
		  else if (seleccionadosDimensiones == tipoDimensionVariable) 
		  	document.configurarReporteGraficoForm.textoMiembrosVariable.value = textoMiembros;
		  else if (seleccionadosDimensiones == tipoDimensionAtributo) 
		    document.configurarReporteGraficoForm.textoMiembrosAtributo.value = textoMiembros;
		  else if (seleccionadosDimensiones == tipoDimensionOrganizacion) 
		    document.configurarReporteGraficoForm.textoMiembrosOrganizacion.value = textoMiembros;
		  else if (seleccionadosDimensiones == tipoDimensionPlan) 
		    document.configurarReporteGraficoForm.textoMiembrosPlan.value = textoMiembros;
		  else if (seleccionadosDimensiones == tipoDimensionIndicador) 
		    document.configurarReporteGraficoForm.textoMiembrosIndicador.value = textoMiembros;
		  
		  document.configurarReporteGraficoForm.textoMiembros.value='';
		}
		 
		function getTextoMiembrosDimension() 
		{
		  var seleccionadosDimensiones = document.configurarReporteGraficoForm.seleccionadosDimensiones.value;
		  var textoMiembros = document.configurarReporteGraficoForm.textoMiembros;

		  if (seleccionadosDimensiones == tipoDimensionTiempo) 
		    textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosTiempo.value;
		  else if (seleccionadosDimensiones == tipoDimensionVariable) 
		  	textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosVariable.value;
		  else if (seleccionadosDimensiones == tipoDimensionAtributo) 
		    textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosAtributo.value;
		  else if (seleccionadosDimensiones == tipoDimensionOrganizacion) 
		    textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosOrganizacion.value;
		  else if (seleccionadosDimensiones == tipoDimensionPlan) 
		    textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosPlan.value;
		  else if (seleccionadosDimensiones == tipoDimensionIndicador) 
		    textoMiembros.value = document.configurarReporteGraficoForm.textoMiembrosIndicador.value;
		  
		  limpiarMiembros();		  
		  cargarMiembrosExistentes();
		}		
	
		function borrarDimension(valor) 
		{
			//Declaración de las variables locales
			var arregloTemporal = new Array();
			var n = 0;
			var i = 0;
		
			//Se pasa el arreglo global a uno temporal
			for (i = 0; i < arregloDimensiones.length; i++) 
				arregloTemporal[i] = arregloDimensiones[i];
		
			//Se limpia el arreglo global
			arregloDimensiones = new Array();

			//Se evalua cada elemento con el valor que se desea eliminar
			for (i = 0; i < arregloTemporal.length; i++) 
			{
				var arregloRegistro = arregloTemporal[i].split(separadorAtributos);
	
				//Si es igual no se añade al arreglo global
				if (arregloRegistro[0] != valor) 
				{
					arregloDimensiones[n] = arregloTemporal[i];
					n++;
				}
			}
	
			//Se dibuja la tabla
			dibujarTablaDimensiones();		
		}
						
		function cargarDimensionesExistentes() 
		{
			if ((window.document.configurarReporteGraficoForm.textoDimensiones.value != null) && (window.document.configurarReporteGraficoForm.textoDimensiones.value != "")) 
			{
				arregloDimensiones = window.document.configurarReporteGraficoForm.textoDimensiones.value.split(separadorElementos);				
				dibujarTablaDimensiones();
			}
		}
		
		function asignarDimensiones() 
		{								
			var str = "";
			for (var i = 0; i < arregloDimensiones.length; i++) 
				str = str + arregloDimensiones[i] + separadorElementos;

			return str.substring(0, str.length - separadorElementos.length);
		}

		
		/** funciones de selección de filas */
		var seleccionMultipleDimensiones = '0';

        function dimesionEventoClickFilaSeleccion(idsSeleccionados, valoresSeleccionados, nombreTabla, fila) 
        {	
	       if (seleccionMultipleDimensiones == '1') 
	         eventoClickFilaSeleccionMultiple(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);										 
	       else 
	         eventoClickFilaV2(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);
	    }

		function dimesionEventoMouseFueraFilaSeleccion(idsSeleccionados, fila) 
		{	
	       if (seleccionMultipleDimensiones == '1') 
	   	      eventoMouseFueraFilaSeleccionMultiple(idsSeleccionados, fila);		      
	       else 
	          eventoMouseFueraFilaV2(idsSeleccionados, fila);
	    }           
          
		function dimesionEventoMouseEncimaFilaSeleccion(idsSeleccionados, fila) 
		{	
	       if (seleccionMultipleDimensiones == '1') 
	   	      eventoMouseEncimaFilaSeleccionMultiple(idsSeleccionados, fila);		      
	       else 
	          eventoMouseEncimaFilaV2(idsSeleccionados, fila);
	    }           		   
	   	
	   	function dimesionInicializarVisorListaSeleccion(idsSeleccionados, nombreTabla) 
	   	{
	   	   
	   	   if ((idsSeleccionados.value == null) || (idsSeleccionados.value == '')) 
	   	     return;
	   			
  		   if (seleccionMultipleDimensiones == '1') 
	   		  inicializarVisorListaSeleccionMultiple(idsSeleccionados, nombreTabla);			      
  		   else 
	          inicializarVisorListaSeleccionSimple(idsSeleccionados, nombreTabla);
	   	}	

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/reportesgrafico/vistasGraficoJs.jsp"></jsp:include>

<%-- Atributos de la Forma --%>
<html:hidden property="paginaDimensiones" />
<html:hidden property="atributoOrdenDimensiones" />
<html:hidden property="tipoOrdenDimensiones" />
<html:hidden property="valoresSeleccionadosDimensiones" />
<html:hidden property="seleccionadosDimensiones" />
<html:hidden property="textoDimensiones" />
<html:hidden property="nombreDimension" />
<html:hidden property="dimensionId" />

<%-- Representación de la Forma --%>
<vgcinterfaz:contenedorForma>

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.listardimensiones.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Lista Dimensiones --%>
	<table id="panel-derecho-superior-tabla" class="listView" cellpadding="1" cellspacing="0" align="center" width="100%">

		<tr valign="top">
			<td>
			<div style="width: 100%; height: 100%; overflow: auto;">			
				<table id="tablaListaDimensiones" width="100%" class="listView" cellpadding="0" cellspacing="0" >				
					<tr class="encabezadoListView" height="0px">
								
					</tr>
					<colgroup class="cuadroTexto">
						
						
					</colgroup>
				</table>
			</div>
			</td>
		</tr>
	</table>

</vgcinterfaz:contenedorForma>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script>

	// cargar dimensiones
	cargarDimensionesExistentes();
	
	// inicializar seleccionados
	dimesionInicializarVisorListaSeleccion(document.configurarReporteGraficoForm.seleccionadosDimensiones, 'tablaListaDimensiones');
</script>