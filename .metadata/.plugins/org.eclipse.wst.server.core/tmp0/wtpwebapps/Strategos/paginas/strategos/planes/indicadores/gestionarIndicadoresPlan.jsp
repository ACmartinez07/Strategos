<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
<script type="text/javascript">

	function nuevoIndicador() 
	{		
		abrirVentanaModal('<html:rewrite action="/indicadores/crearIndicador" />?inicializar=true&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />',"IndicadorAdd", 880, 670);
	}

	function modificarIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados))
		{
			<logic:equal name="gestionarIndicadoresPlanForm" property="editarForma" value="true">				
				abrirVentanaModal('<html:rewrite action="/indicadores/modificarIndicador"/>?planId=<bean:write name="gestionarPlanForm" property="planId" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value, "IndicadorEdit", 880, 670);
			</logic:equal>
			<logic:notEqual name="gestionarIndicadoresPlanForm" property="editarForma" value="true">
				<logic:equal name="gestionarIndicadoresPlanForm" property="verForma" value="true">					
					abrirVentanaModal('<html:rewrite action="/indicadores/verIndicador"/>?planId=<bean:write name="gestionarPlanForm" property="planId" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value, "IndicadorEdit", 880, 670);
				</logic:equal>
			</logic:notEqual>
		}
	}

	function eliminarIndicadores() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
		{
			var eliminar = confirm('<vgcutil:message key="jsp.gestionarindicadores.eliminarindicador.confirmar" />');
			if (eliminar) 
				window.location.href='<html:rewrite action="/indicadores/eliminarIndicador"/>?indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value + '&ts=<%=(new java.util.Date()).getTime()%>';
		}
	}

	function propiedadesIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
			abrirVentanaModal('<html:rewrite action="/indicadores/propiedadesIndicador" />?indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value, "Indicador", 460, 460);
	}

	function reporteIndicadores() 
	{
		abrirReporte('<html:rewrite action="/indicadores/generarReporteIndicadores.action"/>', 'reporte');
	}

	function editarMedicionesIndicadores() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
		{
	    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
			var funcionCierre = '&funcionCierre=' + 'onEditarMediciones()';
			var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
			var url = '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&plantillaPlanesId=<bean:write name="gestionarPlanForm" property="plantillaPlanes.plantillaPlanesId" />&nivel=<bean:write name="gestionarPerspectivasForm" property="nivelSeleccionadoArbol" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value + "&source=1";

			abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMediciones', '440', '520');
		}
	}
	
	function onEditarMediciones()
	{
		var url = '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&plantillaPlanesId=<bean:write name="gestionarPlanForm" property="plantillaPlanes.plantillaPlanesId" />&nivel=<bean:write name="gestionarPerspectivasForm" property="nivelSeleccionadoArbol" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value + "&source=1" + "&funcion=Ejecutar";
    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + url;
	}

	function editarMetas() 
	{
		window.location.href = '<html:rewrite action="/planes/metas/configurarEdicionMetas" />?planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&verIndicadoresLogroPlan=<bean:write name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value;
	}

	function editarValoresIniciales() 
	{
		window.location.href = '<html:rewrite action="/planes/valoresiniciales/configurarEdicionValoresIniciales" />?planId=<bean:write name="gestionarPlanForm" property="planId" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value;
	}

	function gestionarAnexos() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
		{
			var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
			explicacion.ShowList(true, document.gestionarIndicadoresPlanForm.seleccionados.value, 'Indicador', 0);
		}
	}	

	function verGraficoIndicador() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados))
		{
			var arrObjetosId = getObjetos(document.gestionarIndicadoresPlanForm.seleccionados);
			if (arrObjetosId.length > 0)
			{
				for (var j = 0; i < arrObjetosId.length; i++)
				{
					for (var i = 0; i < naturalezaIndicadores.length; i++) 
					{
						if (naturalezaIndicadores[i][0] == arrObjetosId[j]) 
						{
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresPlanForm" property="naturalezaCualitativoOrdinal" />') 
							{
								alert('<vgcutil:message key="jsp.gestionarindicadores.nograficonaturaleza" />');
								return;
							}
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresPlanForm" property="naturalezaCualitativoNominal" />') 
							{
								alert('<vgcutil:message key="jsp.gestionarindicadores.nograficonaturaleza" />');
								return;
							}
							break;
						}
					}
				}
			}
			
			var grafico = new Grafico();
			grafico.url = '<html:rewrite action="/graficos/grafico"/>';
			grafico.ShowForm(true, document.gestionarIndicadoresPlanForm.seleccionados.value, 'Indicador', undefined, undefined, '<bean:write name="gestionarPlanForm" property="planId" />');
		}
	}

	function verDupontIndicador(indicadorId) 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
		{
			var i = 0;
			for (i = 0; i < naturalezaIndicadores.length; i++) 
			{
				if (naturalezaIndicadores[i][0] == document.gestionarIndicadoresPlanForm.seleccionados.value) 
				{
					if (naturalezaIndicadores[i][1] != '<bean:write name="gestionarIndicadoresPlanForm" property="naturalezaFormula" />') 
					{
						alert('No se puede construir un Arbol de Dupont para el indicador debido a su Naturaleza');
						return;
					}
					break;
				}
			}
			
			var duppont = new Duppont();
			duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
			duppont.ShowForm(true, document.gestionarIndicadoresPlanForm.seleccionados.value, undefined, '<bean:write name="gestionarPlanForm" property="planId" />');
		}
    }

	function configurarVisorIndicadoresPlan() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIndicadoresPlan', '<vgcutil:message key="jsp.gestionarindicadores.titulo" />');
	}

	function asociarIndicador() 
	{
		document.gestionarIndicadoresPlanForm.respuesta.value = "";
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />'; 
		var perspectivaId = '<bean:write name="gestionarPlanForm" property="perspectivaId" />'; 
		var indicadorId = document.gestionarIndicadoresPlanForm.indicadorId.value;
		
		var parametros = 'planId=' + planId;
		parametros = parametros + '&perspectivaId=' + perspectivaId;
		parametros = parametros + '&indicadorId=' + indicadorId;
		parametros = parametros + '&funcion=chequear';
		
		ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/indicadores/asociarIndicadorPlan" />?' + parametros, document.gestionarIndicadoresPlanForm.respuesta, 'onAsociarIndicador()');
	}
	
	function onAsociarIndicador()
	{
		var importarMetas = false;
		var respuesta = document.gestionarIndicadoresPlanForm.respuesta.value.split("|");
		
		if (respuesta[4] == "false")
		{
			if (respuesta[0] == "true")
			{
				importarMetas = true;
						
			}
			
			var indicadorId = respuesta[1];
			var planId = respuesta[2]; 
			var perspectivaId = respuesta[3]; 
			
			var parametros = 'planId=' + planId;
			parametros = parametros + '&perspectivaId=' + perspectivaId;
			parametros = parametros + '&indicadorId=' + indicadorId;
			parametros = parametros + '&importarMetas=' + importarMetas;
			parametros = parametros + '&actualizarForma=' + true;
			
			window.location.href='<html:rewrite action="/planes/indicadores/asociarIndicadorPlan" />?' + parametros + '&ts=<%=(new java.util.Date()).getTime()%>';
		}
	}

	function seleccionarIndicador() 
	{
		abrirSelectorIndicadores('gestionarIndicadoresPlanForm', 'nombreIndicador', 'indicadorId', null, 'asociarIndicador()', null, null, null, null, null, '&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&mostrarSeriesTiempo=false&permitirIniciativas=true');
	}

	function desasociarIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados)) 
		{
			var desasociar = confirm('<vgcutil:message key="jsp.gestionarindicadoresplan.desasociarindicadorplan.confirmar" />');
			if (desasociar) 
			{
				var perspectivaId = '<bean:write name="gestionarPlanForm" property="perspectivaId" />';
				var planId = '<bean:write name="gestionarPlanForm" property="planId" />';
				window.location.href='<html:rewrite action="/planes/indicadores/desasociarIndicadorPlan" />?indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value + '&perspectivaId=' + perspectivaId + '&planId=' + planId + '&ts=<%=(new java.util.Date()).getTime()%>';
			}
		}
	}
	
	function importarMediciones()
	{
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onImportarMediciones()';

		abrirVentanaModal('<html:rewrite action="/indicadores/importar" />' + nombreForma + nombreCampoOculto + funcionCierre, 'importarMediciones', '590', '410');
	}

    function onImportarMediciones()
    {
		var respuesta = document.gestionarIndicadoresPlanForm.respuesta.value.split("|");
    }

	function calcularIndicadores() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value;

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
    }
    
	var naturalezaIndicadores = new Array(<bean:write name="paginaIndicadores" property="total"/>);
	<logic:iterate id="indicador" name="paginaIndicadores" property="lista" indexId="indexIndicadores">
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>] = new Array(2);
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][0] = '<bean:write name="indicador" property="indicadorId"/>';
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][1] = '<bean:write name="indicador" property="naturaleza"/>';
	</logic:iterate>

    function listaGrafico()
    {
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaGrafico()';
		var parametros = '';

		abrirVentanaModal('<html:rewrite action="/graficos/listaGrafico" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'seleccionarGraficos', '500', '575');
    }
    
    function onListaGrafico()
    {
		var respuesta = document.gestionarIndicadoresPlanForm.graficoSeleccionadoId.value.split("|");
		if (respuesta.length > 0 && respuesta != "10000")
		{
			var campos = respuesta[0].split('!,!');
			document.gestionarIndicadoresPlanForm.graficoSeleccionadoId.value = campos[1];
			
			var grafico = new Grafico();
			grafico.url = '<html:rewrite action="/graficos/grafico"/>';
			grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresPlanForm.graficoSeleccionadoId.value);
		}
    }

    function asistenteGrafico()
    {
    	var xml = "";
    	if (document.gestionarIndicadoresPlanForm.seleccionados.value != "") 
    	{
    		var seleccionados = confirm('<vgcutil:message key="jsp.asistente.grafico.alert.multiplesindicadores.confirmar" />');
    		if (seleccionados)
    		{
    			xml = getXml();
    		}
    	}

    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId' + '&xml=' + xml;
		var funcionCierre = '&funcionCierre=' + 'onAsistenteGrafico()';
		var parametros = '';
		var xml = nombreForma + nombreCampoOculto + funcionCierre + parametros + '&funcion=Asistente';
		abrirVentanaModal('<html:rewrite action="/graficos/asistenteGrafico" />' + xml, 'asistenteGraficos', '620', '440');
    }
    
	function getXml()
	{
		var writer = new XmlTextWriter();
		writer.Formatting = true;
        writer.WriteStartDocument();
        writer.WriteStartElement("grafico");
        writer.WriteStartElement("properties");
        writer.WriteElementString("tipo", "1");
        writer.WriteElementString("titulo", "");
        writer.WriteElementString("tituloEjeY", "");
        writer.WriteElementString("tituloEjeX", "");
        writer.WriteElementString("anoInicial", "");
        writer.WriteElementString("periodoInicial", "");
        writer.WriteElementString("anoFinal", "");
        writer.WriteElementString("periodoFinal", "");
        writer.WriteElementString("frecuencia", "");
        writer.WriteElementString("frecuenciaAgrupada", "");
        writer.WriteElementString("nombre", "");
        writer.WriteElementString("condicion", "0");
        writer.WriteStartElement("indicadores");
        
		// Se recorre la lista de insumos
		var insumos = document.gestionarIndicadoresPlanForm.seleccionados.value.split(',');
		var indicadorId;
		for (var i = 0; i < insumos.length; i++) 
		{
			if (insumos[i].length > 0) 
			{
				// indicadorId
				indicadorId = insumos[i]; 
				
				writer.WriteStartElement("indicador");
				writer.WriteElementString("id", indicadorId);
				writer.WriteElementString("planId", '<bean:write name="gestionarPlanForm" property="planId" />');
				writer.WriteElementString("serie", "0");
				writer.WriteElementString("leyenda", "");
				writer.WriteElementString("color", "");
				writer.WriteElementString("tipoGrafico", "");
				writer.WriteElementString("visible", "1");
				writer.WriteEndElement();
				
				writer.WriteStartElement("indicador");
				writer.WriteElementString("id", indicadorId);
				writer.WriteElementString("planId", '<bean:write name="gestionarPlanForm" property="planId" />');
				writer.WriteElementString("serie", "7");
				writer.WriteElementString("leyenda", "");
				writer.WriteElementString("color", "");
				writer.WriteElementString("tipoGrafico", "");
				writer.WriteElementString("visible", "1");
				writer.WriteEndElement();
			};
		};

        writer.WriteEndElement();
        writer.WriteEndElement();
        writer.WriteEndElement();
        writer.WriteEndDocument();
        
        return writer.unFormatted();
	}
    
    function onAsistenteGrafico()
    {
		var grafico = new Grafico();
		grafico.url = '<html:rewrite action="/graficos/grafico"/>';
		grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresPlanForm.graficoSeleccionadoId.value);
    }
    
    function listaReporte()
    {
    	return;
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'reporteSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaReporte()';

		abrirVentanaModal('<html:rewrite action="/reportes/listaReporte" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarReportes', '500', '575');
    }
    
    function onListaReporte()
    {
    	alert(document.gestionarIndicadoresPlanForm.reporteSeleccionadoId.value);
    }

    function asistenteReporte()
    {
    	return;
    	window.location.href='<html:rewrite action="/reportes/asistenteReporte"/>';
    }
    
	function configurarVisorIndicadores() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIndicadoresPlan', '<vgcutil:message key="jsp.gestionarindicadores.titulo" />');
	}
	
	function protegerLiberarMediciones(proteger)
	{
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
		var parametros = '&proteger=' + proteger;
		parametros = parametros + '&origen=3';
		parametros = parametros + '&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value;
		parametros = parametros + '&planId=<bean:write name="gestionarPlanForm" property="planId" />';
		parametros = parametros + '&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
		abrirVentanaModal('<html:rewrite action="/mediciones/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarMediciones', '610', '660');
	}
    
	function protegerLiberarMetas(){
		var proteger;
		
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresPlanForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
		var parametros = '&proteger=' + proteger;
		parametros = parametros + '&origen=3';
		parametros = parametros + '&indicadorId=' + document.gestionarIndicadoresPlanForm.seleccionados.value;
		parametros = parametros + '&planId=<bean:write name="gestionarPlanForm" property="planId" />';
		parametros = parametros + '&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
		abrirVentanaModal('<html:rewrite action="/planes/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerPlan', '700', '410');
	}
	
	
    function onProtegerLiberarMediciones()
    {
    }
    
    function importarProgramado()
    {
    	if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados))
   		{

    		document.gestionarIndicadoresPlanForm.respuesta.value = "";
    		var planId = '<bean:write name="gestionarPlanForm" property="planId" />'; 
    		var perspectivaId = '<bean:write name="gestionarPlanForm" property="perspectivaId" />'; 
    		var indicadorId = document.gestionarIndicadoresPlanForm.seleccionados.value;

    		var parametros = '&planId=' + planId;
    		parametros = parametros + '&perspectivaId=' + perspectivaId;
    		parametros = parametros + '&indicadorId=' + indicadorId;
    		parametros = parametros + '&funcion=chequearProgramado';

    		ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/indicadores/asociarIndicadorPlan" />?' + parametros, document.gestionarIndicadoresPlanForm.respuesta, 'onImportarProgramado()');
   		}
	}
	
	function onImportarProgramado()
	{
		var respuesta = document.gestionarIndicadoresPlanForm.respuesta.value.split("|");
		if (respuesta[0] == "true")
		{
			document.gestionarIndicadoresPlanForm.respuesta.value = "";
			var indicadorId = respuesta[1];
			var planId = respuesta[2]; 
			var perspectivaId = respuesta[3]; 
			
			var parametros = '?planId=' + planId;
			parametros = parametros + '&perspectivaId=' + perspectivaId;
			parametros = parametros + '&indicadorId=' + indicadorId;
			parametros = parametros + '&funcion=getAnos';

			abrirVentanaModal('<html:rewrite action="/planes/indicadores/importarProgramado" />' + parametros, 'ImportarProgramado', '240', '230');
		}
		else
			alert('<vgcutil:message key="jsp.importarprogramado.mediciones.noposee.programado" />');
	}
	
	function onChildImportarProgramado(ano)
	{
		document.gestionarIndicadoresPlanForm.respuesta.value = "";
		var planId = '<bean:write name="gestionarPlanForm" property="planId" />'; 
		var perspectivaId = '<bean:write name="gestionarPlanForm" property="perspectivaId" />'; 
		var indicadorId = document.gestionarIndicadoresPlanForm.seleccionados.value;

		var parametros = '?planId=' + planId;
		parametros = parametros + '&perspectivaId=' + perspectivaId;
		parametros = parametros + '&indicadorId=' + indicadorId;
		parametros = parametros + '&ano=' + ano;
		parametros = parametros + '&funcion=importarAno';
		parametros = parametros + '&ts=<%=(new java.util.Date()).getTime()%>';

		window.location.href='<html:rewrite action="/planes/indicadores/asociarIndicadorPlan" />' + parametros + '&ts=<%=(new java.util.Date()).getTime()%>';
	}
	
	function enviarEmail()
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados))
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/enviarEmailIndicador" />?objetoId=' + document.gestionarIndicadoresPlanForm.seleccionados.value + "&tipoObjeto=Indicador", document.gestionarIndicadoresPlanForm.respuesta, 'onEnviarEmail()');
	}

	function onEnviarEmail()
	{
		var respuesta = document.gestionarIndicadoresPlanForm.respuesta.value.split("|");
		if (respuesta.length > 0)
		{
			if (respuesta[0] == "true")
			{
				if (respuesta[1] == "true")
				{
					var to = respuesta[2]; 
					var cc = respuesta[3];
					var asunto = respuesta[4];
					
					SendEmail(to, cc, undefined, asunto, undefined);
				}
				else
					alert('<vgcutil:message key="jsp.email.reponsable.correo.empty" />');
			}
			else
				alert('<vgcutil:message key="jsp.email.objeto.reponsable.empty" />');
		}
		else
			alert('<vgcutil:message key="jsp.email.objeto.invalido" />');
	}
	
	function actualizar(actualizarForma)
	{
		var url = "";
		if (typeof actualizarForma != "undefined")
			url = url + "?actualizarForma=" + actualizarForma;
		
		window.location.href= "<html:rewrite action='/planes/perspectivas/gestionarPerspectivas' />" + url;
	}
	
</script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/menu/menuVerJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

<%-- Representación de la Forma --%>
<html:form action="/planes/indicadores/gestionarIndicadoresPlan" styleClass="formaHtmlCompleta">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="respuesta" />
	<html:hidden property="reporteSeleccionadoId" />
	<html:hidden property="graficoSeleccionadoId" />
	
	<input type="hidden" name="indicadoresPlan">
	<input type="hidden" name="nombreIndicador" value="">
	<input type="hidden" name="indicadorId" value="">
	<bean:define id="valorNaturalezaFormula">
		<bean:write name="gestionarIndicadoresPlanForm" property="naturalezaFormula" />
	</bean:define>

	<%-- Variable para controlar el TipoCalculo de la Perspectiva--%>
	<bean:define id="tipoCalculoPerspectiva">
		<bean:write name="perspectiva" property="tipoCalculo" />
	</bean:define>

	<bean:define id="tipoCalculoPerspectivaAutomatico">
		<bean:write name="gestionarPerspectivasForm" property="tipoCalculoPerspectivaAutomatico" />
	</bean:define>
	<bean:define id="tipoCalculoPerspectivaManual">
		<bean:write name="gestionarPerspectivasForm" property="tipoCalculoPerspectivaManual" />
	</bean:define>

	<bean:define id="mostrarBarraSuperior" type="String" value="false"></bean:define>
	<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.indicadores.gestionarindicadoresplan.barrasuperior.mostrar" valor="true">
		<bean:define id="mostrarBarraSuperior" type="String" value="true"></bean:define>
	</vgcutil:valorPropertyIgual>

	<vgcinterfaz:contenedorForma mostrarBarraSuperior="<%= mostrarBarraSuperior %>" idHtml="gestionarIndicadoresPlan">

		<%-- Título  --%>
		<vgcinterfaz:contenedorFormaTitulo>
			<logic:notEmpty name="perspectiva" property="padreId" scope="session">
				..:: <bean:write name="gestionarPlanForm" property="plantillaPlanes.nombreIndicadorPlural" />
				<vgcutil:message key="jsp.exploradorplan.asociados" />
				<bean:write name="perspectiva" property="nombre" />
			</logic:notEmpty>
			<logic:empty name="perspectiva" property="padreId" scope="session">
				<%-- Validación: Se muestran los Indicadores de Logro del Plan --%>
				<logic:equal name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
					..:: <vgcutil:message key="jsp.exploradorplan.indicadoresplan" />
				</logic:equal>
				<%-- Validación: Se muestran los Indicadores de Logro de los Hijos del Plan --%>
				<logic:notEqual name="gestionarPerspectivasForm" property="verIndicadoresLogroPlan" value="true">
					..:: <bean:write name="gestionarIndicadoresPlanForm" property="nombreIndicadorPlural" />
					<vgcutil:message key="jsp.exploradorplan.asociados" />
					<bean:write name="perspectiva" property="nombre" />
				</logic:notEqual>
			</logic:empty>
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			javascript:actualizar(true)
		</vgcinterfaz:contenedorFormaBotonActualizar>

		<%-- Botón Regresar --%>
		<vgcinterfaz:contenedorFormaBotonRegresar>
			javascript:irAtras(2)
		</vgcinterfaz:contenedorFormaBotonRegresar>


		<%-- Validación: Solo se dibuja el Menú cuando no está seleccionada la Raiz de las Perspectivas --%>
		<%-- <logic:notEmpty name="perspectiva" property="padreId" scope="session">--%>

			<%-- Menú --%>
			<vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Inicio del Menú --%>
				<vgcinterfaz:contenedorMenuHorizontal>

					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEdicionIndicadoresPlan" key="menu.edicion">
							<%-- Validación: si el tipoCalculo = false entonces si se puede Asociar/Desasociar Indicadores --%>
							<logic:notEmpty name="perspectiva" property="padreId" scope="session">
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.edicion.asociar" onclick="seleccionarIndicador()" permisoId="INDICADOR_ASOCIAR" agregarSeparador="true" />
								</logic:equal>
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.edicion.desasociar" onclick="desasociarIndicador()" permisoId="INDICADOR_DESASOCIAR" agregarSeparador="true" />
								</logic:equal>
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoIndicador();" permisoId="INDICADOR_ADD" aplicaOrganizacion="true" />
								</logic:equal>
							</logic:notEmpty>
							<logic:equal name="gestionarIndicadoresPlanForm" property="editarForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIndicador();" aplicaOrganizacion="true" />
							</logic:equal>
							<logic:notEqual name="gestionarIndicadoresPlanForm" property="editarForma" value="true">
								<logic:equal name="gestionarIndicadoresPlanForm" property="verForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIndicador();" aplicaOrganizacion="true" />
								</logic:equal>
							</logic:notEqual>
							<logic:notEmpty name="perspectiva" property="padreId" scope="session">
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarIndicadores();" permisoId="INDICADOR_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
								</logic:equal>
							</logic:notEmpty>
							<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesIndicador();" permisoId="INDICADOR" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="INDICADOR_EMAIL" onclick="enviarEmail();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>

					<%-- Menú: Ver --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuVerIndicadores" key="menu.ver">
							<vgcinterfaz:botonMenu key="menu.ver.unidadesmedida" onclick="gestionarUnidadesMedida();" permisoId="UNIDAD" />
							<vgcinterfaz:botonMenu key="menu.ver.categoriasmedicion" onclick="gestionarCategoriasMedicion();" permisoId="CATEGORIA" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>

					<%-- Menú: Mediciones --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuMedicionesIndicadores" key="menu.mediciones">
							<logic:greaterThan name="paginaIndicadores" property="total" value="0">
								<vgcinterfaz:botonMenu key="menu.mediciones.ejecutado" onclick="editarMedicionesIndicadores();" permisoId="INDICADOR_MEDICION" aplicaOrganizacion="true" />
								<vgcinterfaz:botonMenu key="menu.mediciones.meta" onclick="editarMetas()" permisoId="INDICADOR_VALOR_META" />
								<logic:notEqual name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.mediciones.valoresiniciales" onclick="editarValoresIniciales()" permisoId="INDICADOR_VALOR_INICIAL" />
								</logic:notEqual>
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.mediciones.importar.programado" onclick="importarProgramado()" permisoId="INDICADOR_VALOR_META" />
								</logic:equal>
								<logic:equal name="tipoCalculoPerspectiva" value="<%= tipoCalculoPerspectivaManual %>">
									<vgcinterfaz:botonMenu key="menu.mediciones.valoresiniciales" onclick="editarValoresIniciales()" permisoId="INDICADOR_VALOR_INICIAL" agregarSeparador="true" />
									<vgcinterfaz:botonMenu key="menu.mediciones.importar" onclick="importarMediciones();" permisoId="INDICADOR_MEDICION_IMPORTAR" aplicaOrganizacion="true"/>
									<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularIndicadores();" permisoId="INDICADOR_MEDICION_CALCULAR" aplicaOrganizacion="true" agregarSeparador="true" />
									<vgcinterfaz:botonMenu key="menu.mediciones.metas" onclick="protegerLiberarMetas();" permisoId="PROTEGER_LIBERAR_META" aplicaOrganizacion="true" agregarSeparador="true" />
									<vgcinterfaz:menuAnidado key="menu.mediciones.proteccion">
										<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.liberar" onclick="protegerLiberarMediciones(false);" permisoId="INDICADOR_MEDICION_DESPROTECCION" aplicaOrganizacion="true" />
										<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.bloquear" onclick="protegerLiberarMediciones(true)" permisoId="INDICADOR_MEDICION_PROTECCION" aplicaOrganizacion="true" />
									</vgcinterfaz:menuAnidado>
									
								</logic:equal>
							</logic:greaterThan>
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>

					<%-- Menú: Evaluación --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEvaluacionIndicadores" key="menu.evaluacion">
							<vgcinterfaz:menuAnidado key="menu.evaluacion.graficos" agregarSeparador="true">
								<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.graficar" permisoId="INDICADOR_EVALUAR_GRAFICO_GRAFICO" aplicaOrganizacion="true" onclick="verGraficoIndicador();" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.plantillas" permisoId="INDICADOR_EVALUAR_GRAFICO_PLANTILLA" aplicaOrganizacion="true" onclick="listaGrafico();" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.asistente" permisoId="INDICADOR_EVALUAR_GRAFICO_ASISTENTE" aplicaOrganizacion="true" onclick="asistenteGrafico();" />
							</vgcinterfaz:menuAnidado>
							<%-- 
							<vgcinterfaz:menuAnidado key="menu.evaluacion.reportes" agregarSeparador="true">
								<vgcinterfaz:botonMenu key="menu.evaluacion.reportes.plantillas" permisoId="INDICADOR_EVALUAR_REPORTE_PLANTILLA" aplicaOrganizacion="true" onclick="listaReporte();" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.reportes.asistente" permisoId="INDICADOR_EVALUAR_REPORTE_ASISTENTE" aplicaOrganizacion="true" onclick="asistenteReporte();" />
							</vgcinterfaz:menuAnidado>
							<vgcinterfaz:botonMenu key="menu.evaluacion.analisissensibilidad" onclick="alert('Funcionalidad no disponible');" />
							 --%>
							<vgcinterfaz:botonMenu key="menu.evaluacion.arbol" permisoId="INDICADOR_EVALUAR_ARBOL" aplicaOrganizacion="true" onclick="javascript:verDupontIndicador();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
	
					<%-- Menú: Herramientas --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuHerramientasIndicadores" key="menu.herramientas">
							<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorIndicadores();"/>
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
	
				</vgcinterfaz:contenedorMenuHorizontal>

			</vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Barra Genérica --%>
			<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

				<%-- Barra de Herramientas --%>
				<vgcinterfaz:barraHerramientas nombre="barraGestionarIndicadoresPlan">

					<logic:notEmpty name="perspectiva" property="padreId" scope="session">
						<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoIndicador" onclick="javascript:nuevoIndicador();">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:notEmpty>
					<logic:equal name="gestionarIndicadoresPlanForm" property="editarForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarIndicador" onclick="javascript:modificarIndicador();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
					<logic:notEqual name="gestionarIndicadoresPlanForm" property="editarForma" value="true">
						<logic:equal name="gestionarIndicadoresPlanForm" property="verForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarIndicador" onclick="javascript:modificarIndicador();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</logic:notEqual>
					<logic:notEmpty name="perspectiva" property="padreId" scope="session">
						<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarIndicador" onclick="javascript:eliminarIndicadores();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.eliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_ADD" nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="asociarIndicador" onclick="javascript:seleccionarIndicador();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.asociar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_DESASOCIAR" nombreImagen="desasociar" pathImagenes="/componentes/barraHerramientas/" nombre="desasociarIndicador" onclick="javascript:desasociarIndicador();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.desasociar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:notEmpty>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesIndicador();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.propiedades" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_GRAFICO_GRAFICO" nombreImagen="grafico" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoIndicador" onclick="javascript:verGraficoIndicador();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.grafico" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_GRAFICO_ASISTENTE" nombreImagen="graficoasistente" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoAsistenteIndicador" onclick="javascript:asistenteGrafico();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.grafico.asisente" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_GRAFICO_PLANTILLA" nombreImagen="graficoplantillas" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoPlantillasIndicador" onclick="javascript:listaGrafico();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.grafico.plantillas" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_ARBOL" nombreImagen="dupont" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="dupontIndicador" onclick="javascript:verDupontIndicador();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.arboldupont" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION" nombreImagen="mediciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="medicionesIndicadores" onclick="javascript:editarMedicionesIndicadores();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.mediciones" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="EXPLICACION" nombreImagen="explicaciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="gestionarAnexos" onclick="javascript:gestionarAnexos();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.explicaciones" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION_CALCULAR" nombreImagen="calculo" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="calcularIndicadores" onclick="javascript:calcularIndicadores();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.calcular" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EMAIL" nombreImagen="email" pathImagenes="/componentes/barraHerramientas/" nombre="email" onclick="javascript:enviarEmail();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.email" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>

				</vgcinterfaz:barraHerramientas>

			</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- </logic:notEmpty> --%>

		<%-- Visor Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" nombre="visorIndicadoresPlan" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados del Visor Lista --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="450px" onclick="javascript:consultar(gestionarIndicadoresPlanForm, 'nombre', null);">
				<bean:write name="gestionarIndicadoresPlanForm" property="nombreIndicadorPlural" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="unidad" width="120px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.unidad" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="real" width="100px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.real" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="metaParcial" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.metaparcial" />
			</vgcinterfaz:columnaVisorLista>
			<logic:equal name="gestionarIndicadoresPlanForm" property="configuracionPlan.planObjetivoLogroParcialMostrar" value="true">
				<vgcinterfaz:columnaVisorLista nombre="estadoParcial" width="100px">
					<vgcutil:message key="jsp.gestionarindicadoresplan.columna.estadoparcial" />
				</vgcinterfaz:columnaVisorLista>
			</logic:equal>
			<vgcinterfaz:columnaVisorLista nombre="metaAnual" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.metaanual" />
			</vgcinterfaz:columnaVisorLista>
			<logic:equal name="gestionarIndicadoresPlanForm" property="configuracionPlan.planObjetivoLogroAnualMostrar" value="true">
				<vgcinterfaz:columnaVisorLista nombre="estadoAnual" width="100px">
					<vgcutil:message key="jsp.gestionarindicadoresplan.columna.estadoanual" />
				</vgcinterfaz:columnaVisorLista>
			</logic:equal>
			<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" width="130px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.ultimoperiodomedicion" />
			</vgcinterfaz:columnaVisorLista>			
			<vgcinterfaz:columnaVisorLista nombre="peso" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.peso" />
			</vgcinterfaz:columnaVisorLista>															
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="100px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="naturaleza" width="100px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.naturaleza" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="orden" width="60px" onclick="javascript:consultar(gestionarIndicadoresPlanForm, 'orden', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.orden" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="codigoEnlace" width="150px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.codigoenlace" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="tipo" width="100px">
				<vgcutil:message key="jsp.gestionarindicadoresplan.columna.tipo" />
			</vgcinterfaz:columnaVisorLista>

			<%-- Filas del Visor Lista --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="indicador">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="indicador" property="indicadorId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
				<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickFilaSeleccionMultiple(document.gestionarIndicadoresPlanForm.seleccionados, null, 'visorIndicadoresPlan', this)</vgcinterfaz:visorListaFilaEventoOnclick>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="indicador" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
					<logic:notEmpty name="indicador" property="unidad">
						<bean:write name="indicador" property="unidad.nombre" />
					</logic:notEmpty>
					<logic:empty name="indicador" property="unidad">&nbsp;
					</logic:empty>
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="real" align="right">
					<bean:write name="indicador" property="ultimaMedicionFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaParcial" align="right">
					<bean:write name="indicador" property="metaParcialFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<logic:equal name="gestionarIndicadoresPlanForm" property="configuracionPlan.planObjetivoLogroParcialMostrar" value="true">
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="estadoParcial" align="right">
						<bean:write name="indicador" property="estadoParcialFormateado" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
				</logic:equal>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="metaAnual" align="right">
					<bean:write name="indicador" property="metaAnualFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<logic:equal name="gestionarIndicadoresPlanForm" property="configuracionPlan.planObjetivoLogroAnualMostrar" value="true">
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="estadoAnual" align="right">
						<bean:write name="indicador" property="estadoAnualFormateado" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
				</logic:equal>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="center">
					<bean:write name="indicador" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="peso" align="right">
					<bean:write name="indicador" property="pesoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>								
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
					<bean:write name="indicador" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
					<bean:write name="indicador" property="naturalezaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="orden">
					<bean:write name="indicador" property="orden" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="codigoEnlace">
					<bean:write name="indicador" property="codigoEnlace" />
				</vgcinterfaz:valorFilaColumnaVisorLista>				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo" align="right">
					<logic:equal name="indicador" property="guia" value="true">
						<bean:write name="gestionarIndicadoresPlanForm" property="nombreIndicadorTipoGuia" />
					</logic:equal>
					<logic:equal name="indicador" property="guia" value="false">
						<bean:write name="gestionarIndicadoresPlanForm" property="nombreIndicadorTipoResultado" />
					</logic:equal>
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.indicadores.gestionarindicadoresplan.barrainferior.mostrar" valor="true">
			<%-- Barra Inferior --%>
			<vgcinterfaz:contenedorFormaBarraInferior>
				<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIndicadoresPlanForm" property="atributoOrden" />  [<bean:write name="gestionarIndicadoresPlanForm" property="tipoOrden" />]			
			</vgcinterfaz:contenedorFormaBarraInferior>
		</vgcutil:valorPropertyIgual>

	</vgcinterfaz:contenedorForma>

	<script>
		inicializarVisorListaSeleccionMultiple(gestionarIndicadoresPlanForm.seleccionados, 'visorIndicadoresPlan');
	</script>

</html:form>
