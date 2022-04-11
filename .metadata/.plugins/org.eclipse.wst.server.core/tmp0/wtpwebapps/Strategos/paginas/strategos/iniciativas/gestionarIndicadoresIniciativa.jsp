<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%-- Creado por: Kerwin Arias (02/06/2012) --%>

<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>
<script type="text/javascript">

	function nuevoIndicadorIniciativa() 
	{		
		abrirVentanaModal('<html:rewrite action="/indicadores/crearIndicador"/>?inicializar=true&claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>&iniciativaId=<bean:write name="gestionarIndicadoresIniciativaForm" property="iniciativaId"/>' , "IndicadoresAdd", 880, 670);
	}

	function modificarIndicadorIniciativa() 
	{
		if ( verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados)) 			
			abrirVentanaModal('<html:rewrite action="/indicadores/modificarIndicador"/>?claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>&iniciativaId=<bean:write name="gestionarIndicadoresIniciativaForm" property="iniciativaId"/>&indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value , "IndicadoresEdit", 880, 670);
	}

	function eliminarIndicadoresIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados)) 
		{
			if (document.gestionarIndicadoresIniciativaForm.seleccionados.value == '<bean:write name="gestionarIndicadoresIniciativaForm" property="indicadorId" />') 
			{
				alert('El Indicador no puede ser eliminado porque es el Indicador de Avance de la Iniciativa');
				return;
			}
			var eliminar = confirm('<vgcutil:message key="jsp.gestionarindicadores.eliminarindicador.confirmar" />');
			if (eliminar) 
				window.location.href='<html:rewrite action="/indicadores/eliminarIndicador"/>?indicadorIniciativaId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>';
		}
	}
	
	function onEditarMediciones()
	{
		var url = 'indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value + "&source=2" + "&funcion=Ejecutar";
    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + url;
	}

	function propiedadesIndicadorIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados)) 
			abrirVentanaModal('<html:rewrite action="/indicadores/propiedadesIndicador" />?indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value, "Indicador", 460, 460);
	}

	function gestionarAnexosIndicadorIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados))
		{
			var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
			explicacion.ShowList(true, document.gestionarIndicadoresIniciativaForm.seleccionados.value, 'Indicador', 0);
		}
	}

	function calcularIndicadoresIniciativa() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&desdeIniciativa=true&claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>&iniciativaId=<bean:write name="gestionarIndicadoresIniciativaForm" property="iniciativaId"/>&indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value;

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
    }
	
	function verGraficoIndicador() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados))
		{
			var arrObjetosId = getObjetos(document.gestionarIndicadoresIniciativaForm.seleccionados);
			if (arrObjetosId.length > 0)
			{
				for (var j = 0; i < arrObjetosId.length; i++)
				{
					for (var i = 0; i < naturalezaIndicadores.length; i++) 
					{
						if (naturalezaIndicadores[i][0] == arrObjetosId[j]) 
						{
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresIniciativaForm" property="naturalezaCualitativoOrdinal" />') 
							{
								alert('<vgcutil:message key="jsp.gestionarindicadores.nograficonaturaleza" />');
								return;
							}
							if (naturalezaIndicadores[i][1] == '<bean:write name="gestionarIndicadoresIniciativaForm" property="naturalezaCualitativoNominal" />') 
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
			grafico.ShowForm(true, document.gestionarIndicadoresIniciativaForm.seleccionados.value, 'Indicador', '<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>');
		}
	}

	function verDupontIndicador() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados)) 
		{
			var i = 0;
			for (i = 0; i < naturalezaIndicadores.length; i++) 
			{
				if (naturalezaIndicadores[i][0] == document.gestionarIndicadoresIniciativaForm.seleccionados.value) 
				{
					if (naturalezaIndicadores[i][1] != '<bean:write name="gestionarIndicadoresIniciativaForm" property="naturalezaFormula" />') 
					{
						alert('<vgcutil:message key="jsp.gestionarindicadores.noarboldupont" />');
						return;
					}
					break;
				}
			}
			
			var duppont = new Duppont();
			duppont.url = '<html:rewrite action="/graficos/dupontIndicador"/>';
			duppont.ShowForm(true, document.gestionarIndicadoresIniciativaForm.seleccionados.value, undefined, undefined);
		}
    }
	
	
	function editarMedicionesIndicadores() 
	{
		if (verificarSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados)) 
		{
	    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresIniciativaForm';
			var funcionCierre = '&funcionCierre=' + 'onEditarMedicionesIndicadores()';
			var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
			var url = '&indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value + '&claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>' + "&source=0";

			abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMediciones', '440', '520');
		}
	}
	
	function onEditarMedicionesIndicadores()
	{
		var url = '?iniciativaId=<bean:write name="gestionarIndicadoresIniciativaForm" property="iniciativaId"/>&indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value + '&claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>' + "&source=0" + "&funcion=Ejecutar";
    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>' + url;
	}
	
	
	function protegerLiberarMediciones(proteger)
	{
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresIniciativaForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
		var parametros = '&proteger=' + proteger; 
		parametros = parametros + '&origen=2';
		parametros = parametros + '&indicadorId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value;
		parametros = parametros + '&claseId=<bean:write name="gestionarIndicadoresIniciativaForm" property="claseId"/>';
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
		abrirVentanaModal('<html:rewrite action="/mediciones/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarMediciones', '610', '660');
	}
    
    function onProtegerLiberarMediciones()
    {
    }
    
	function configurarVisorIndicadores() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorIndicadores', '<vgcutil:message key="jsp.gestionarindicadores.titulo" />');
	}
	
	function editarClaves()
	{
		window.location.href = '<html:rewrite action="/framework/usuarios/cambiarClaveUsuario" />';
	}

	function enviarEmail()
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIndicadoresIniciativaForm.seleccionados))
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/enviarEmailIndicador" />?objetoId=' + document.gestionarIndicadoresIniciativaForm.seleccionados.value + "&tipoObjeto=Indicador", document.gestionarIndicadoresIniciativaForm.respuesta, 'onEnviarEmail()');
	}

	function onEnviarEmail()
	{
		var respuesta = document.gestionarIndicadoresIniciativaForm.respuesta.value.split("|");
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
	
	var naturalezaIndicadores = new Array(<bean:write name="gestionarIndicadoresIniciativaPaginaIndicadores" property="total"/>);
	<logic:iterate id="indicador" name="gestionarIndicadoresIniciativaPaginaIndicadores" property="lista" indexId="indexIndicadores">
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>] = new Array(2);
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][0] = '<bean:write name="indicador" property="indicadorId"/>';
		naturalezaIndicadores[<bean:write name="indexIndicadores"/>][1] = '<bean:write name="indicador" property="naturaleza"/>';
	</logic:iterate>
	
    function asistenteGrafico()
    {
    	var xml = "";
    	if (document.gestionarIndicadoresIniciativaForm.seleccionados.value != "") 
    	{
    		var seleccionados = confirm('<vgcutil:message key="jsp.asistente.grafico.alert.multiplesindicadores.confirmar" />');
    		if (seleccionados)
    			xml = getXml();
    	}

    	var nombreForma = '?nombreForma=' + 'gestionarIndicadoresIniciativaForm';
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
		var insumos = document.gestionarIndicadoresIniciativaForm.seleccionados.value.split(',');
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
		grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresIniciativaForm.graficoSeleccionadoId.value);
    }
    
    function listaGrafico()
    {
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresIniciativaForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaGrafico()';

		abrirVentanaModal('<html:rewrite action="/graficos/listaGrafico" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarGraficos', '500', '575');
    }
    
    function onListaGrafico()
    {
		var respuesta = document.gestionarIndicadoresIniciativaForm.graficoSeleccionadoId.value.split("|");
		if (respuesta.length > 0)
		{
			var campos = respuesta[0].split('!,!');
			document.gestionarIndicadoresIniciativaForm.graficoSeleccionadoId.value = campos[1];

			var grafico = new Grafico();
			grafico.url = '<html:rewrite action="/graficos/grafico"/>';
			grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarIndicadoresIniciativaForm.graficoSeleccionadoId.value);
		}
    }
    
	function importarMediciones()
	{
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresIniciativaForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onImportarMediciones()';

		abrirVentanaModal('<html:rewrite action="/indicadores/importar" />' + nombreForma + nombreCampoOculto + funcionCierre, 'importarMediciones', '590', '410');
	}

    function onImportarMediciones()
    {
		var respuesta = document.gestionarIndicadoresIniciativaForm.graficoSeleccionadoId.value.split("|");
    }
    
</script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<%-- Representación de la Forma --%>
<html:form action="/iniciativas/gestionarIndicadoresIniciativa" styleClass="formaHtmlCompleta">

	<html:hidden property="seleccionados" />
	<html:hidden property="graficoSeleccionadoId" />
	<html:hidden property="respuesta" />
	<html:hidden property="source" />

	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<vgcinterfaz:contenedorForma idContenedor="body-indicadores">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.gestionarindicadoresiniciativa.titulo" arg0="<%= tituloIniciativa %>" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>
				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionIndicadoresIniciativa" key="menu.edicion">
						<logic:notEqual name="gestionarIndicadoresIniciativaForm" property="source" value="portafolio">
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoIndicadorIniciativa();" permisoId="INDICADOR_ADD" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIndicadorIniciativa();" permisoId="INDICADOR_EDIT" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarIndicadoresIniciativa();" permisoId="INDICADOR_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesIndicadorIniciativa();" permisoId="INDICADOR" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="INDICADOR_EMAIL" onclick="enviarEmail();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Mediciones --%>
				<logic:notEqual name="gestionarIndicadoresIniciativaForm" property="source" value="portafolio">
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuMedicionesIndicadoresIniciativa" key="menu.mediciones">
							
							<vgcinterfaz:botonMenu key="menu.mediciones.importar" onclick="importarMediciones();" permisoId="INDICADOR_MEDICION_IMPORTAR" aplicaOrganizacion="true"/>
							<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularIndicadoresIniciativa();" permisoId="INDICADOR_MEDICION_CALCULAR" aplicaOrganizacion="true" agregarSeparador="true" />
							<vgcinterfaz:menuAnidado key="menu.mediciones.proteccion">
								<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.liberar" onclick="protegerLiberarMediciones(false);" permisoId="INDICADOR_MEDICION_DESPROTECCION" aplicaOrganizacion="true" />
								<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.bloquear" onclick="protegerLiberarMediciones(true)" permisoId="INDICADOR_MEDICION_PROTECCION" aplicaOrganizacion="true" />
							</vgcinterfaz:menuAnidado>
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
				</logic:notEqual>

				<%-- Menú: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasIndicadores" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClaves();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorIndicadores();"/>
						<%-- 
						<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
						--%>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaIniciativas" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>
		</vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Barra Genérica --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarIndicadoresPlan">

				<logic:notEqual name="gestionarIndicadoresIniciativaForm" property="source" value="portafolio">
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" permisoId="INDICADOR_ADD" pathImagenes="/componentes/barraHerramientas/" nombre="nuevoIndicadorIniciativa" onclick="javascript:nuevoIndicadorIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EDIT" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarIndicadorIniciativa" onclick="javascript:modificarIndicadorIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarIndicadorIniciativa" onclick="javascript:eliminarIndicadoresIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION_CALCULAR" nombreImagen="calculo" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="calcularIndicadores" onclick="javascript:calcularIndicadoresIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.calcular" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_GRAFICO_GRAFICO" nombreImagen="grafico" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="graficoIndicadorIniciativa" onclick="javascript:verGraficoIndicador();">
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
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_EVALUAR_ARBOL" nombreImagen="dupont" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="dupontIndicadorIniciativa" onclick="javascript:verDupontIndicador();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.arboldupont" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR" nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedadesIndicadorIniciativa" onclick="javascript:propiedadesIndicadorIniciativa();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				 
				<logic:notEqual name="gestionarIndicadoresIniciativaForm" property="source" value="portafolio">
					<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION" nombreImagen="mediciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="medicionesIndicadores" onclick="javascript:editarMedicionesIndicadores();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.mediciones" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:notEqual>
				
				<vgcinterfaz:barraHerramientasBoton permisoId="EXPLICACION" nombreImagen="explicaciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="gestionarAnexosIndicadorIniciativa" onclick="javascript:gestionarAnexosIndicadorIniciativa();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.explicaciones" />
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

		<%-- Visor Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="gestionarIndicadoresIniciativaPaginaIndicadores" nombre="visorIndicadores" seleccionMultiple="true" nombreForma="gestionarIndicadoresIniciativaForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="jsp.gestionarindicadoresiniciativa.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">
			<%-- Encabezados --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="30px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="350px" onclick="javascript:consultar(gestionarIndicadoresIniciativaForm, 'nombre', null);">
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
			<vgcinterfaz:columnaVisorLista nombre="orden" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.orden" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="codigoEnlace" width="90px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.codigoenlace" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" onclick="javascript:consultar(gestionarIndicadoresIniciativaForm, 'fechaUltimaMedicion', null);" width="130px">
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

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	if (gestionarIndicadoresIniciativaForm.source.value == "portafolio")
		resizeAltoFormaDividida(true);
</script>
