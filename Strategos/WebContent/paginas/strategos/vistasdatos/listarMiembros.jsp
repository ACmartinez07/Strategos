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
	 
		function aceptar() 
		{
		    // evita que se pierdan los miembros de las dimensiones seleccionadas 
		    setTextoMiembrosDimension(); 	
		    document.configurarVistaDatosForm.textoSelectores.value = asignarSelectores(); 
		 
		    window.document.configurarVistaDatosForm.action = '<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
			window.document.configurarVistaDatosForm.submit();
		}
	
	     /** funciones javascript para el selector de miembros */
		function seleccionarMiembros() 
	    {
			var parametros = 'seleccionMultiple=1';  	        
			var frecuencia = document.configurarVistaDatosForm.frecuencia.value;
	 	    var url = '';
	 	        
	 	    parametros = parametros + '&frecuenciaBloqueada=1&frecuencia=' + frecuencia; 
	 	    if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionTiempo) 
				url = '<html:rewrite action="/vistasdatos/seleccionarTiempo" />';
			if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionAtributo) 
				url = '<html:rewrite action="/vistasdatos/seleccionarAtributo" />';
			if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionVariable) 
	 	    	url = '<html:rewrite action="/vistasdatos/seleccionarVariable" />';
			if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionOrganizacion) 
	 	    	url = '<html:rewrite action="/vistasdatos/seleccionarOrganizacion" />';
			if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionPlan) 
	 	    	url = '<html:rewrite action="/vistasdatos/seleccionarPlan" />';
			if (document.configurarVistaDatosForm.seleccionadosDimensiones.value == tipoDimensionIndicador)
			{
	 	    	//url = '<html:rewrite action="/vistasdatos/seleccionarIndicador" />';
				var forma = 'configurarVistaDatosForm';
				var	funcionRetorno = 'agregarIndicadores()';
				url = '&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + frecuencia + '&permitirIniciativas=true';

				abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', funcionRetorno, null, null, null, null, null, url, 'indicadorInsumoSeleccionadoSeriePlanId');
			}
			else if (url != '') 
				abrirSelectorMiembro(url, 'configurarVistaDatosForm', 'nombreMiembro', 'miembroId', null, 'agregarMiembros()', parametros);
		}

		// Formato de string de insumos:
		// [correlativo:##][indicadorId:###][serieId:###][indicadorNombre:###][serieNombre:###][rutaCompleta:###]; ....
		function agregarIndicadores() 
		{
			var seleccionadoIds = document.configurarVistaDatosForm.indicadorInsumoSeleccionadoIds.value.split('<bean:write name="configurarVistaDatosForm" property="separadorIndicadores" ignore="true"/>');
			var seleccionadoNombres = document.configurarVistaDatosForm.indicadorInsumoSeleccionadoNombres.value.split('<bean:write name="configurarVistaDatosForm" property="separadorIndicadores" ignore="true"/>');
			var seleccionadoSeriePlanId = document.configurarVistaDatosForm.indicadorInsumoSeleccionadoSeriePlanId.value.split('<bean:write name="configurarVistaDatosForm" property="separadorIndicadores" ignore="true"/>');
			var separadorSeries = '<bean:write name="configurarVistaDatosForm" property="separadorSeries" scope="session" />';
			for (var k = 0 ; k < seleccionadoIds.length ; k++)
			{
				var planId = '';
				var nombreMiembro = seleccionadoNombres[k].split(separadorSeries);
			    var miembroId = seleccionadoIds[k].split(separadorSeries);
			    if (seleccionadoSeriePlanId.length > 0 && seleccionadoSeriePlanId[k] != '')
			    	planId = seleccionadoSeriePlanId[k].split(separadorSeries);
			    
				agregarArreglos(nombreMiembro, miembroId, planId);
			}
		}
			
		var arregloMiembros = new Array();
		var separadorElementos = '<bean:write name="configurarVistaDatosForm" property="separadorElementos"  />';
		var separadorAtributos = '<bean:write name="configurarVistaDatosForm" property="separadorAtributos"  />';
		
		function limpiarMiembros() 
		{
			arregloMiembros = new Array();			
			dibujarTablaMiembros();
		}
		
		function agregarMiembros() 
		{
			var arrNombreMiembro = new Array(0);
			var arrMiembroId = new Array(0);
			
			if ((window.document.configurarVistaDatosForm.miembroId.value != null) && (window.document.configurarVistaDatosForm.miembroId.value != '')) 
			{			
			   arrNombreMiembro = window.document.configurarVistaDatosForm.nombreMiembro.value.split(",");
			   arrMiembroId = window.document.configurarVistaDatosForm.miembroId.value.split(",");			
			}

			agregarArreglos(arrNombreMiembro, arrMiembroId);
		}
		
		function agregarArreglos(arrNombreMiembro, arrMiembroId, arrMiembroPlanId)
		{
			//Declaración de las variables locales
			if (arrMiembroPlanId == undefined)
				arrMiembroPlanId = '';
			var	nombreMiembro = '';
			var	miembroId = '';
			var planId = '';
			var existe = false;

			for (var k = 0 ; k < arrMiembroId.length ; k++) 
			{
				planId = '';
				nombreMiembro = arrNombreMiembro[k];
			    miembroId = arrMiembroId[k];
			    if (arrMiembroPlanId.length > 1 && arrMiembroPlanId[k+1] != '')
			    	planId = arrMiembroPlanId[k+1];

				if ((miembroId == null) || (miembroId == '')) 
				  return;

	            existe = false;
				//Valida que el elemento no esté repetido
				for (var i = 0; i < arregloMiembros.length; i++) 
				{
					var arregloTabla = arregloMiembros[i].split(separadorAtributos);
					if (arregloTabla[0] == miembroId) 
					{
						existe = true;
						break;
					}
				}
				
			    //Se añade el Nivel en el arreglo global
			    if (!existe) 
			    	arregloMiembros[(arregloMiembros.length)] = miembroId + separadorAtributos + nombreMiembro + (planId != '' ? (separadorAtributos + planId) : '');
	
				//Se dibuja la tabla
				dibujarTablaMiembros();				
		    }
		}
		
		function dibujarTablaMiembros() 
		{
			//Declaración de las variables locales
			var tabla = document.getElementById("tablaListaMiembros");			
		
			//Se eliminan todas las filas
			var numFilas = tabla.getElementsByTagName("tr").length;
			if (numFilas > 0) 
			{
				valor = numFilas - 1;
				for (var i = 0; i < numFilas; i++) 
				{
					tabla.deleteRow(valor);
					valor--;												
				}
			}
		
			//Se recorre el arreglo global
			for (i = 0; i < arregloMiembros.length; i++) 
			{
				//Se establecen los valores de la tabla
				var arregloRegistro = arregloMiembros[i].split(separadorAtributos);
				tabla.insertRow(tabla.rows.length);
				var tr = tabla.rows[tabla.rows.length-1];
				tr.height = "20px";
				
				var td = document.createElement("td");
				td.align = "left";
				td.width = "100%";				
				var srcImgEliminar = "<html:rewrite page='/componentes/visorLista/eliminar.gif'/>";
				var titleEliminar = "<vgcutil:message key="boton.eliminar.alt" />";
				var funcion = 'onClick="javascript:borrarMiembro(\'' + arregloRegistro[0] + '\');"';
				var innerHTML = '<img style="cursor:pointer" src="' + srcImgEliminar + '" ' + funcion + ' title="' + titleEliminar + '" border="0" width="10" height="10">' + '&nbsp;&nbsp;' + arregloRegistro[1];
				td.innerHTML = (innerHTML);					
				tr.appendChild(td);

				// Plan Id
				td = document.createElement("td");
				innerHTML = "";
				if (arregloRegistro[2] != undefined && arregloRegistro[2] != "")
					innerHTML = "<input type='hidden' value='" + arregloRegistro[2] + "' id='plan_" + arregloRegistro[0] +"_" + arregloRegistro[2] + "' name='plan_" + arregloRegistro[0] +"_" + arregloRegistro[2] + "'>";
				td.innerHTML = (innerHTML);					
				tr.appendChild(td);							
			}				
		}
			
		function borrarMiembro(valor) 
		{
			//Declaración de las variables locales
			var arregloTemporal = new Array();
			var n = 0;
			var i = 0;
		
			//Se pasa el arreglo global a uno temporal
			for (i = 0; i < arregloMiembros.length; i++) 
				arregloTemporal[i] = arregloMiembros[i];
		
			//Se limpia el arreglo global
			arregloMiembros = new Array();

			//Se evalua cada elemento con el valor que se desea eliminar
			for (i = 0; i < arregloTemporal.length; i++) 
			{
				var arregloRegistro = arregloTemporal[i].split(separadorAtributos);
	
				//Si es igual no se añade al arreglo global
				if (arregloRegistro[0] != valor) 
				{
					arregloMiembros[n] = arregloTemporal[i];
					n++;
				}
			}
	
			//Se dibuja la tabla
			dibujarTablaMiembros();		
		}
						
		function cargarMiembrosExistentes() 
		{
			if ((window.document.configurarVistaDatosForm.textoMiembros.value != null) && (window.document.configurarVistaDatosForm.textoMiembros.value != "")) 
			{				
				arregloMiembros = window.document.configurarVistaDatosForm.textoMiembros.value.split(separadorElementos);				
				dibujarTablaMiembros();
			}
		}
		
		function asignarMiembros() 
		{								
			var str = "";
			for (var i = 0; i < arregloMiembros.length; i++) 
				str = str + arregloMiembros[i] + separadorElementos;

			return str.substring(0, str.length - separadorElementos.length);
		}

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/vistasdatos/vistasDatosJs.jsp"></jsp:include>
<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

<%-- Atributos de la Forma --%>
<html:hidden property="paginaMiembros" />
<html:hidden property="atributoOrdenMiembros" />
<html:hidden property="tipoOrdenMiembros" />
<html:hidden property="seleccionadosMiembros" />
<html:hidden property="textoMiembros" />
<html:hidden property="nombreMiembro" />
<html:hidden property="miembroId" />
<html:hidden property="textoMiembrosTiempo" />
<html:hidden property="textoMiembrosVariable" />
<html:hidden property="textoMiembrosAtributo" />
<html:hidden property="textoMiembrosOrganizacion" />
<html:hidden property="textoMiembrosPlan" />
<html:hidden property="textoMiembrosIndicador" />

<input type="hidden" name="indicadorInsumoSeleccionadoIds">
<input type="hidden" name="indicadorInsumoSeleccionadoNombres">
<input type="hidden" name="indicadorInsumoSeleccionadoRutasCompletas">
<input type="hidden" name="indicadorInsumoSeleccionadoSeriePlanId">

<%-- Representación de la Forma --%>
<vgcinterfaz:contenedorForma>

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
			  ..:: <vgcutil:message key="jsp.listarmiembros.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Barra Genérica --%>
	<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

		<%-- Barra de Herramientas --%>
		<vgcinterfaz:barraHerramientas nombre="barraListarMiembros">
			<vgcinterfaz:barraHerramientasBoton nombreImagen="vincular" pathImagenes="/componentes/barraHerramientas/" nombre="vincular" onclick="javascript:seleccionarMiembros();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.seleccionar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:limpiarMiembros();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.limpiar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
		</vgcinterfaz:barraHerramientas>

	</vgcinterfaz:contenedorFormaBarraGenerica>

	<%-- Lista Miembros --%>

	<table id="panel-derecho-inferior-tabla" class="listView" cellpadding="3" cellspacing="0" align="center" width="100%">
		<tr class="encabezadoListView" height="20px">
			<td class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.listarmiembros.nombre" /></td>
		</tr>

		<tr valign="top">
			<td>
			<div style="width: 100%; height: 100%; overflow: auto;">
			<table id="tablaListaMiembros" width="100%">
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

</vgcinterfaz:contenedorForma>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script>
	// seleccionar dimension
	getTextoMiembrosDimension();

	// cargar miembros
	cargarMiembrosExistentes();
</script>
