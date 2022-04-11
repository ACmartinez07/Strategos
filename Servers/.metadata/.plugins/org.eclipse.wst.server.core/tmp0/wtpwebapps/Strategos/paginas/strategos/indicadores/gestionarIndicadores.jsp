<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>
<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
<script type="text/javascript">

	function nuevoIndicador() 
	{		
		abrirVentanaModal('<html:rewrite action="/indicadores/crearIndicador"/>?inicializar=true', 'IndicadoresAdd', 900, 680);
	}

	function modificarIndicador(modificar) 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados))
		{
			if (modificar)
				
				abrirVentanaModal('<html:rewrite action="/indicadores/modificarIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value, 'IndicadoresAdd', 900, 680);
			else				
				abrirVentanaModal('<html:rewrite action="/indicadores/verIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value, 'IndicadoresAdd', 900, 680);	
		}
	}

	function propiedadesIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
			abrirVentanaModal('<html:rewrite action="/indicadores/propiedadesIndicador" />?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value, "Indicador", 460, 460);
	}

	function editarMediciones() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
		{
	    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
			var funcionCierre = '&funcionCierre=' + 'onEditarMediciones()';
			var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
			var url = '&indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />' + "&source=0&desdeClases=true";

			abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMediciones', '440', '520');
		}
	}
	
	function onEditarMediciones()
	{
		var url = 'indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />' + "&source=0&desdeClases=true" + "&funcion=Ejecutar";
    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + url;
	}
	
	function importarMediciones()
	{
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onImportarMediciones()';

		abrirVentanaModal('<html:rewrite action="/indicadores/importar" />' + nombreForma + nombreCampoOculto + funcionCierre, 'importarMediciones', '590', '410');
	}

    function onImportarMediciones()
    {
		var respuesta = document.gestionarIndicadoresForm.graficoSeleccionadoId.value.split("|");
    }

	function calcularIndicadores() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&claseId=<bean:write scope="session" name="claseId"/>&indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value;

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
    }

	function eliminarIndicadores() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
		{
			var respuesta = confirm ('<vgcutil:message key="jsp.gestionarindicadores.eliminarindicador.confirmar" />');
			if (respuesta)
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/eliminarIndicador" />?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + "&funcion=check" + '&ts=<%= (new java.util.Date()).getTime() %>', document.gestionarIndicadoresForm.respuesta, 'onEliminarIndicadores()');
		}
	}
	
	function onEliminarIndicadores() 
	{
		var respuesta = document.gestionarIndicadoresForm.respuesta.value.split("|");
		if (respuesta.length > 0)
		{
			if (respuesta[0] == "true")
			{
				var respuesta = confirm ('<vgcutil:message key="jsp.gestionarindicadores.eliminarindicador.insumo.confirmar" />');
				if (respuesta) 
					window.location.href='<html:rewrite action="/indicadores/eliminarIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
			}
			else
				window.location.href='<html:rewrite action="/indicadores/eliminarIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
		}
		else
			window.location.href='<html:rewrite action="/indicadores/eliminarIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
	}
	
	function reporteIndicadores() 
	{
		abrirReporte('<html:rewrite action="/indicadores/generarReporteIndicadores.action"/>?organizacionId=<bean:write scope="session" name="organizacionId"/>&claseId=<bean:write scope="session" name="claseId"/>' , 'reporte');
	}

	function limpiarFiltrosIndicadores() 
	{
	  	gestionarIndicadoresForm.filtroNombre.value = "";
	  	gestionarIndicadoresForm.submit();
	}

	function buscarIndicadores() 
	{
		gestionarIndicadoresForm.submit();
	}

	function gestionarAnexos() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados))
		{
			var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
			explicacion.ShowList(true, document.gestionarIndicadoresForm.seleccionados.value, 'Indicador', 0);
		}
	}

	function graficar() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados))
		{
			var arrObjetosId = getObjetos(document.gestionarIndicadoresForm.seleccionados);
			if (arrObjetosId.length > 0)
			{
				for (var j = 0; i < arrObjetosId.length; i++)
				{
					for (var i = 0; i < naturalezaIndicadores.length; i++) 
					{
						if (naturalezaIndicadores[i][0] == arrObjetosId[j]) 
						{
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresForm" property="naturalezaCualitativoOrdinal" />') 
							{
								alert('<vgcutil:message key="jsp.gestionarindicadores.nograficonaturaleza" />');
								return;
							}
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresForm" property="naturalezaCualitativoNominal" />') 
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
			grafico.ShowForm(true, document.gestionarIndicadoresForm.seleccionados.value, 'Indicador', '<bean:write name="claseIndicadores" property="claseId" scope="session" />');
		}
	}

	function verDupontIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
		{
			var i = 0;
			for (i = 0; i < naturalezaIndicadores.length; i++) 
			{
				if (naturalezaIndicadores[i][0] == document.gestionarIndicadoresForm.seleccionados.value) 
				{
					if (naturalezaIndicadores[i][1] != '<bean:write name="gestionarIndicadoresForm" property="naturalezaFormula" />') 
					{
						alert('<vgcutil:message key="jsp.gestionarindicadores.noarboldupont" />');
						return;
					}
					if (naturalezaIndicadores[i][2] != 'true')
					{
						alert('<vgcutil:message key="jsp.gestionarindicadores.arboldupont.mostrar.off" />');
						return;
					}
					break;
				}
			}
			
			var duppont = new Duppont();
			duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
			duppont.ShowForm(true, document.gestionarIndicadoresForm.seleccionados.value, undefined, undefined);
		}
    }

	function configurarVisorIndicadores() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIndicadores', '<vgcutil:message key="jsp.gestionarindicadores.titulo" />');
	}

    function copiar()
    {
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
	    	window.location.href='<html:rewrite action="/indicadores/copiarIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&funcion=Copiar';
    }

    function moverIndicador()
    {
		if (verificarSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados)) 
		{
			var respuesta = confirm ('<vgcutil:message key="jsp.gestionarindicadores.moverindicador.confirmar" />');
			if (respuesta)
				abrirVentanaModal('<html:rewrite action="/indicadores/moverIndicador"/>?indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + '&funcion=mover&accion=editar', 'moverIndicador', 510, 295);
		}
    }
    
    function listaReporte()
    {
    	return;
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'reporteSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaReporte()';

		abrirVentanaModal('<html:rewrite action="/reportes/listaReporte" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarReportes', '500', '575');
    }
    
    function onListaReporte()
    {
    	alert(document.gestionarIndicadoresForm.reporteSeleccionadoId.value);
    }

    function asistenteReporte()
    {
    	return;
    	window.location.href='<html:rewrite action="/reportes/asistenteReporte"/>';
    }

    function listaGrafico()
    {
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaGrafico()';

		abrirVentanaModal('<html:rewrite action="/graficos/listaGrafico" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarGraficos', '500', '575');
    }
    
    function onListaGrafico()
    {
		var respuesta = document.gestionarIndicadoresForm.graficoSeleccionadoId.value.split("|");
		if (respuesta.length > 0)
		{
			var campos = respuesta[0].split('!,!');
			document.gestionarIndicadoresForm.graficoSeleccionadoId.value = campos[1];
			
			var grafico = new Grafico();
			grafico.url = '<html:rewrite action="/graficos/grafico"/>';
			grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresForm.graficoSeleccionadoId.value);
		}
    }

    function asistenteGrafico()
    {
    	var xml = "";
    	if (document.gestionarIndicadoresForm.seleccionados.value != "") 
    	{
    		var seleccionados = confirm('<vgcutil:message key="jsp.asistente.grafico.alert.multiplesindicadores.confirmar" />');
    		if (seleccionados)
    			xml = getXml();
    	}

    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId' + '&xml=' + xml;
		var funcionCierre = '&funcionCierre=' + 'onAsistenteGrafico()';
		var xml = nombreForma + nombreCampoOculto + funcionCierre + '&funcion=Asistente';
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
		var insumos = document.gestionarIndicadoresForm.seleccionados.value.split(',');
		var indicadorId;
		for (var i = 0; i < insumos.length; i++) 
		{
			if (insumos[i].length > 0) 
			{
				// indicadorId
				indicadorId = insumos[i]; 
				
				writer.WriteStartElement("indicador");
				writer.WriteElementString("id", indicadorId);
				writer.WriteElementString("serie", "0");
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
		grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresForm.graficoSeleccionadoId.value); 
    }
    
	var naturalezaIndicadores = new Array(<bean:write name="paginaIndicadores" property="total"/>);
	<logic:iterate id="indicador" name="paginaIndicadores" property="lista" indexId="indexIndicadores">
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>] = new Array(3);
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][0] = '<bean:write name="indicador" property="indicadorId"/>';
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][1] = '<bean:write name="indicador" property="naturaleza"/>';
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][2] = '<bean:write name="indicador" property="mostrarEnArbol"/>';
	</logic:iterate>

	function abrirReporteComiteEjecutivo() 
	{
    	abrirVentanaModal('<html:rewrite action="/reportes/indicadores/reporteComiteEjecutivo" />', "Indicadores", 640, 480);
	}
	
	function consolidarIndicador()
	{
		var nombreForma = '&nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + '';
		var funcionCierre = '&funcionCierre=' + '';

		abrirVentanaModal('<html:rewrite action="/indicadores/indicadorConsolidado" />?organizacionId=<bean:write name="organizacion" property="organizacionId"/>' + nombreForma + nombreCampoOculto + funcionCierre, 'consolidarIndicadores', '630', '430');
	}
	
	function protegerLiberarMediciones(proteger)
	{
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
		var parametros = '&proteger=' + proteger;
		parametros = parametros + '&origen=2';
		parametros = parametros + '&indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value;
		parametros = parametros + '&claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />';
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
		abrirVentanaModal('<html:rewrite action="/mediciones/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarMediciones', '610', '660');
	}
    
    function onProtegerLiberarMediciones()
    {
    }
    
    function importarTransaccion(transaccionId)
    {
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var url = nombreForma + nombreCampoOculto + "&transaccionId=" + transaccionId;
		
		abrirVentanaModal('<html:rewrite action="/transacciones/importar" />' + url, 'importarTransaccion', '580', '395');
    }

    function reporteTransaccion(transaccionId)
    {
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var url = nombreForma + nombreCampoOculto + "&transaccionId=" + transaccionId + "&funcion=seleccionar";
		
		abrirVentanaModal('<html:rewrite action="/transacciones/reporteTransaccion" />' + url, 'parametrosTransaccion', '380', '210');
    }

    function onReporteTransaccion(url)
    {
    	window.location.href='<html:rewrite action="/transacciones/reporteEjecucionTransaccion" />?' + url;
    }

	function enviarEmail()
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresForm.seleccionados))
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/enviarEmailIndicador" />?objetoId=' + document.gestionarIndicadoresForm.seleccionados.value + "&tipoObjeto=Indicador", document.gestionarIndicadoresForm.respuesta, 'onEnviarEmail()');
	}

	function onEnviarEmail()
	{
		var respuesta = document.gestionarIndicadoresForm.respuesta.value.split("|");
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
		
		window.location.href = "<html:rewrite action='/indicadores/clasesindicadores/gestionarClasesIndicadores' />" + url;
	}
	
</script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<jsp:include flush="true" page="/paginas/strategos/menu/menuVerJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

<%-- Representación de la Forma --%>
<html:form action="/indicadores/gestionarIndicadores" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="reporteSeleccionadoId" />
	<html:hidden property="graficoSeleccionadoId" />
	<html:hidden property="respuesta" />
	<html:hidden property="editarForma" />
	<html:hidden property="verForma" />

	<vgcinterfaz:contenedorForma idContenedor="body-indicadores">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarindicadores.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			javascript:actualizar(true)			
		</vgcinterfaz:contenedorFormaBotonActualizar>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

				<%-- Menú: Archivo --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivoIndicadores" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteIndicadores();" permisoId="INDICADOR_PRINT" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionIndicadores" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoIndicador();" permisoId="INDICADOR_ADD" aplicaOrganizacion="true" />
						<logic:equal name="gestionarIndicadoresForm" property="editarForma" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIndicador(true);" />
						</logic:equal>
						<logic:notEqual name="gestionarIndicadoresForm" property="editarForma" value="true">
							<logic:equal name="gestionarIndicadoresForm" property="verForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIndicador(false);" />							
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.copiar" permisoId="INDICADOR_ADD" onclick="copiar();" />
						<vgcinterfaz:botonMenu key="menu.edicion.move" permisoId="INDICADOR_ADD" onclick="moverIndicador();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarIndicadores();" permisoId="INDICADOR_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesIndicador();" permisoId="INDICADOR" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.indicadoresconsolidados" permisoId="INDICADOR_CONSOLIDADO" onclick="consolidarIndicador();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="INDICADOR_EMAIL" onclick="enviarEmail();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVerIndicadores" key="menu.ver">
						<vgcinterfaz:botonMenu key="menu.ver.unidadesmedida" onclick="gestionarUnidadesMedida();" permisoId="UNIDAD" />
						<vgcinterfaz:botonMenu key="menu.ver.categoriasmedicion" onclick="gestionarCategoriasMedicion();" permisoId="CATEGORIA" />
						<%-- 
						<vgcinterfaz:botonMenu key="menu.ver.indicadoresrelacionados" onclick="alert('Funcionalidad No Disponible');" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ver.indicadoresasociadosproblemas" onclick="alert('Funcionalidad No Disponible');" />
						 --%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Mediciones --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuMedicionesIndicadores" key="menu.mediciones">
						<vgcinterfaz:botonMenu key="menu.mediciones.ejecutado" onclick="editarMediciones();" permisoId="INDICADOR_MEDICION" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.mediciones.importar" onclick="importarMediciones();" permisoId="INDICADOR_MEDICION_IMPORTAR" aplicaOrganizacion="true"/>
						<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularIndicadores();" permisoId="INDICADOR_MEDICION_CALCULAR" aplicaOrganizacion="true" agregarSeparador="true" />
						<logic:equal name="gestionarIndicadoresForm" property="hayTransacciones" value="true">
							<vgcinterfaz:menuAnidado key="menu.mediciones.transacciones" agregarSeparador="true">
								<logic:iterate name="gestionarIndicadoresForm" property="transacciones" id="transaccion">
									<bean:define id="nombreTransaccion">
										<bean:write name="transaccion" property="nombre" />
									</bean:define>
									<bean:define id="idImportacionTransaccion">
										javascript:importarTransaccion(<bean:write name="transaccion" property="id" />)
									</bean:define>
									<bean:define id="idReporteTransaccion">
										javascript:reporteTransaccion(<bean:write name="transaccion" property="id" />)
									</bean:define>
									<vgcinterfaz:botonMenu key="menu.mediciones.transacciones.importar" arg0="<%= nombreTransaccion %>" onclick="<%= idImportacionTransaccion %>" permisoId="INDICADOR_MEDICION_TRANSACCION_IMPORTAR" aplicaOrganizacion="true" />
									<vgcinterfaz:botonMenu key="menu.mediciones.transacciones.reporte" arg0="<%= nombreTransaccion %>" onclick="<%= idReporteTransaccion %>" permisoId="INDICADOR_MEDICION_TRANSACCION_REPORTE" aplicaOrganizacion="true" />
								</logic:iterate>
							</vgcinterfaz:menuAnidado>
						</logic:equal>
						<vgcinterfaz:menuAnidado key="menu.mediciones.proteccion">
							<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.liberar" onclick="protegerLiberarMediciones(false);" permisoId="INDICADOR_MEDICION_DESPROTECCION" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.bloquear" onclick="protegerLiberarMediciones(true)" permisoId="INDICADOR_MEDICION_PROTECCION" aplicaOrganizacion="true" />
						</vgcinterfaz:menuAnidado>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Evaluación --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEvaluacionIndicadores" key="menu.evaluacion">
						<vgcinterfaz:menuAnidado key="menu.evaluacion.graficos" agregarSeparador="true">
							<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.graficar" permisoId="INDICADOR_EVALUAR_GRAFICO_GRAFICO" aplicaOrganizacion="true" onclick="graficar();" />
							<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.plantillas" permisoId="INDICADOR_EVALUAR_GRAFICO_PLANTILLA" aplicaOrganizacion="true" onclick="listaGrafico();" />
							<vgcinterfaz:botonMenu key="menu.evaluacion.graficos.asistente" permisoId="INDICADOR_EVALUAR_GRAFICO_ASISTENTE" aplicaOrganizacion="true" onclick="asistenteGrafico();" />
						</vgcinterfaz:menuAnidado>
						<logic:equal name="gestionarIndicadoresForm" property="reporte" value="true">
							<vgcinterfaz:menuAnidado key="menu.evaluacion.reportes" agregarSeparador="true">
								<%--
								<vgcinterfaz:botonMenu key="menu.evaluacion.reportes.plantillas" permisoId="INDICADOR_EVALUAR_REPORTE_PLANTILLA" aplicaOrganizacion="true" onclick="listaReporte();" />
								<vgcinterfaz:botonMenu key="menu.evaluacion.reportes.asistente" permisoId="INDICADOR_EVALUAR_REPORTE_ASISTENTE" aplicaOrganizacion="true" onclick="asistenteReporte();" agregarSeparador="true" />
								 --%>
								<logic:equal name="gestionarIndicadoresForm" property="reporteComiteEjecutivo" value="true">
									<vgcinterfaz:botonMenu key="menu.evaluacion.reportecomiteejecutivo" permisoId="INDICADOR_EVALUAR_REPORTE_COMITE" aplicaOrganizacion="true" onclick="abrirReporteComiteEjecutivo()" />
							 	</logic:equal>
							</vgcinterfaz:menuAnidado>
						</logic:equal>
						<%--
						<vgcinterfaz:botonMenu key="menu.evaluacion.analisissensibilidad" onclick="alert('Funcionalidad no disponible');" />
						--%>
						<vgcinterfaz:botonMenu key="menu.evaluacion.arbol" permisoId="INDICADOR_EVALUAR_ARBOL" aplicaOrganizacion="true" onclick="javascript:verDupontIndicador();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasIndicadores" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorIndicadores();" agregarSeparador="true"/>
						<%-- 
						<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
						--%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Barra de Herramientas --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<vgcinterfaz:barraHerramientas nombre="barraGestionarIndicadores">

				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_ADD" aplicaOrganizacion="true" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoIndicador();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<logic:equal name="gestionarIndicadoresForm" property="editarForma" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarIndicador();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:notEqual name="gestionarIndicadoresForm" property="editarForma" value="true">
					<logic:equal name="gestionarIndicadoresForm" property="verForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarIndicador();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_DELETE" aplicaOrganizacion="true" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR" nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesIndicador();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_GRAFICO_GRAFICO" nombreImagen="grafico" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoIndicador" onclick="javascript:graficar();">
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
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION" aplicaOrganizacion="true" nombreImagen="mediciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="medicionesIndicadores" onclick="javascript:editarMediciones();">
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

		<bean:define id="valorNaturalezaFormula">
			<bean:write name="gestionarIndicadoresForm" property="naturalezaFormula" />
		</bean:define>

		<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" nombre="visorIndicadores" seleccionMultiple="true" nombreForma="gestionarIndicadoresForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="30px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="350px" onclick="javascript:consultar(gestionarIndicadoresForm, 'nombre', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="unidad" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.unidad" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="80px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="naturaleza" width="100px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.naturaleza" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="orden" width="60px" onclick="javascript:consultar(gestionarIndicadoresForm, 'orden', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.orden" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="codigoEnlace" width="90px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.codigoenlace" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" width="130px" onclick="javascript:consultar(gestionarIndicadoresForm, 'fechaUltimaMedicion', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.ultimoperiodomedicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="real" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.real" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="programado" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.programado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="cumplimientoParcial" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.cumplimiento.parcial" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="cumplimientoAnual" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.cumplimiento.anual" />
			</vgcinterfaz:columnaVisorLista>

			<%-- Filas --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="indicador">
				<vgcinterfaz:visorListaFilaId>
					<bean:write name="indicador" property="indicadorId" />
				</vgcinterfaz:visorListaFilaId>

				<%-- Columnas --%>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="indicador" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
					<logic:notEmpty name="indicador" property="unidad">
						<bean:write name="indicador" property="unidad.nombre" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
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
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="right">
					<bean:write name="indicador" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="real" align="right">
					<bean:write name="indicador" property="ultimaMedicionFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="programado" align="right">
					<bean:write name="indicador" property="ultimoProgramadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="cumplimientoParcial" align="center">
					<bean:write name="indicador" property="porcentajeCumplimientoParcialFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="cumplimientoAnual" align="center">
					<bean:write name="indicador" property="porcentajeCumplimientoAnualFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaIndicadores" labelPage="inPagina" action="javascript:consultar(gestionarIndicadoresForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior>
			<logic:notEmpty name="gestionarIndicadoresForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIndicadoresForm" property="atributoOrden" />  [<bean:write name="gestionarIndicadoresForm" property="tipoOrden" />]
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-indicadores'), 225);
</script>
