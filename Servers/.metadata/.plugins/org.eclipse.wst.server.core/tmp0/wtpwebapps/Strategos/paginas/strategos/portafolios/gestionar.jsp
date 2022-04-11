<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>
<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	var _selectCondicionTypeIndex = 1;
	var _altoPrefijoListo = false;

	function nuevo() 
	{
		abrirVentanaModal('<html:rewrite action="/portafolios/crearPortafolio" />', "Portafolio", 660, 440);
	}

	function modificar() 
	{
		if ((document.gestionarPortafoliosForm.seleccionadoId.value == null) || (document.gestionarPortafoliosForm.seleccionadoId.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}
		var id = document.gestionarPortafoliosForm.seleccionadoId.value;
		abrirVentanaModal('<html:rewrite action="/portafolios/modificarPortafolio" />?id=' + id, "Portafolio", 660, 440);
	}

	function eliminar(categoriaId) 
	{
		if ((document.gestionarPortafoliosForm.seleccionadoId.value == null) || (document.gestionarPortafoliosForm.seleccionadoId.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}			
		var id = document.gestionarPortafoliosForm.seleccionadoId.value;	
		var eliminar = confirm ('<vgcutil:message key="jsp.gestionarportafolio.eliminar.confirmar" />');		
		if (eliminar)
		{
			activarBloqueoEspera();
			window.location.href='<html:rewrite action="/portafolios/eliminarPortafolio"/>?id=' + id;
		}
	}
						
	function reporte() 
	{			  
		var url = '';
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectCondicionType = document.getElementById('selectCondicionType');
		if (selectCondicionType != null)
			url = url + '&selectCondicionType=' + selectCondicionType.value;

		abrirReporte('<html:rewrite action="/portafolios/generarReportePortafolios.action"/>?atributoOrden=' + gestionarPortafoliosForm.atributoOrden.value + '&tipoOrden=' + gestionarPortafoliosForm.tipoOrden.value + url, 'reporte');
	}
	
	function limpiarFiltros() 
	{
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			filtroNombre.value = "";
		var selectCondicionType = document.getElementById('selectCondicionType');
		if (selectCondicionType != null)
			selectCondicionType.selectedIndex = _selectCondicionTypeIndex;
	}
	
	function refrescarPortafolio(iniciativaSeleccionadaId)
	{
		var url = '?portafolioId=' + document.gestionarPortafoliosForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectCondicionType = document.getElementById('selectCondicionType');
		if (selectCondicionType != null)
			url = url + '&selectCondicionType=' + selectCondicionType.value;
		if (typeof(iniciativaSeleccionadaId) != "undefined")
			url = url + '&iniciativaSeleccionadaId=' + iniciativaSeleccionadaId;

		window.location.href='<html:rewrite action="/portafolios/gestionarPortafolios" />' + url;
	}
	
	function eventoClickPortafolio(objetoFilaId) 
	{
		activarBloqueoEspera();
		refrescarPortafolio();
	}
	
	function mostrarVista() 
	{
		if ((document.gestionarPortafoliosForm.seleccionadoId.value == null) || (document.gestionarPortafoliosForm.seleccionadoId.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}
		var id = document.gestionarPortafoliosForm.seleccionadoId.value;
		if ((id != null) && (id != '')) 
			window.location.href='<html:rewrite action="/portafolios/mostrarVista" />?defaultLoader=true&id=' + id;
	}
	
	function calcularPortafolio()
	{
		if ((document.gestionarPortafoliosForm.seleccionadoId.value == null) || (document.gestionarPortafoliosForm.seleccionadoId.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}
		activarBloqueoEspera();
		window.location.href='<html:rewrite action="/portafolios/calcular"/>?id=' + document.gestionarPortafoliosForm.seleccionadoId.value;
	}
	
	function asignarPesos()
	{
		if ((document.gestionarPortafoliosForm.seleccionadoId.value == null) || (document.gestionarPortafoliosForm.seleccionadoId.value == "")) 
		{
			alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
			return;
		}

		var id = document.gestionarPortafoliosForm.seleccionadoId.value;
		abrirVentanaModal('<html:rewrite action="/portafolios/asignarPesos" />?id=' + id, "Portafolio", 730, 600);
	}
	
	function setAnchoPanel()
	{
		if (startHorizontal && splitPortafolioPosicionNueva != 0 && splitPortafolioPosicionActual != splitPortafolioPosicionNueva)
		{
			startHorizontal = false;
			var tipo = "Ancho";
			var panel = "Strategos.Panel.Portafolio";
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/setPanel" />?panel=' + panel + '&tipo=' + tipo + '&tamano=' + splitPortafolioPosicionNueva, document.gestionarPortafoliosForm.respuesta, 'onSetPanel()');
		}
	}
	
	function setAltoPanel()
	{
		if (startVertical && splitPortafolioVerticalPosicionNueva != 0 && splitPortafolioVerticalPosicionActual != splitPortafolioVerticalPosicionNueva)
		{
			startVertical = false;
			var tipo = "Alto";
			var panel = "Strategos.Panel.Portafolio";
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/setPanel" />?panel=' + panel + '&tipo=' + tipo + '&tamano=' + splitPortafolioVerticalPosicionNueva, document.gestionarPortafoliosForm.respuesta, 'onSetPanel()');
		}
	}
	
	function onSetPanel()
	{
	}
	
	function resizeAltoFormaDividida(altoPrefijo)
	{
		var altoFijo = "312";
		if (typeof(altoPrefijo) == "undefined")
			altoPrefijo = false;
		
		var formaIniciativas = document.getElementById('body-iniciativas');
		if (formaIniciativas != null)
		{
			var tdScreenSuperior = document.getElementById('tdScreenSuperior');
			var alto = 0; 
			if (tdScreenSuperior != null)
				alto = tdScreenSuperior.style.height.replace("px", "");
			else
				alto = "280";
			if (altoPrefijo && !_altoPrefijoListo)
			{
				_altoPrefijoListo = true;
				if (parseInt(alto) >= 840)
					alto = 615;
				else if (parseInt(alto) <= 200)
					alto = 70;
				else
					alto = parseInt(alto) - 170;
			}
			tdScreenSuperior.style.height = (parseInt(alto)) + "px";
			formaIniciativas.style.height = (parseInt(alto)) + "px";

			var hIndicadores = (parseInt(_myHeight) - (parseInt(alto)));
			var forma = document.getElementById('body-indicadores');
			var tamano = (parseInt(hIndicadores) - getAltoScreenDividida(parseInt(altoFijo)));
			if (forma != null)
				forma.style.height = tamano + "px";
		}
	}
	
	function configurarVisorPortafolios() 
	{
		configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorPortafolios', '<vgcutil:message key="jsp.gestionarportafolio.visor.titulo" />');
	}
	
	
	
	function reportePortafolioDetalle() 
	{	
		var url = '?portafolioId=' + document.gestionarPortafoliosForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectCondicionType = document.getElementById('selectCondicionType');
		if (selectCondicionType != null)
			url = url + '&selectCondicionType=' + selectCondicionType.value;
		
		if(document.gestionarPortafoliosForm.seleccionadoId.value == "" || document.gestionarPortafoliosForm.seleccionadoId.value == null){
			alert('<vgcutil:message key="jsp.iniciativas.reporte.detallado" />');
		}
		else{
			
			abrirVentanaModal('<html:rewrite action="/reportes/portafolios/detalle" />' + url, "reportePortafolioDetallado", 500, 490);
		}
	}
	
	function reportePortafolioResumido() 
	{
		var url = '?portafolioId=' + document.gestionarPortafoliosForm.seleccionadoId.value;
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var selectCondicionType = document.getElementById('selectCondicionType');
		if (selectCondicionType != null)
			url = url + '&selectCondicionType=' + selectCondicionType.value;

    	abrirVentanaModal('<html:rewrite action="/reportes/portafolios/resumida" />?' + url, "reportePortafolioResumido", 500, 490);
	}
	
</script>

<%-- Representación de la Forma --%>
<html:form action="/portafolios/gestionarPortafolios" styleClass="formaHtmlGestionar">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionadoId" />
	<html:hidden property="respuesta" />
	<html:hidden property="organizacionId" />

	<vgcinterfaz:contenedorForma idContenedor="body-portafolio">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarportafolio.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>

				<%-- Menú: Archivo --%>
				<%-- 
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="PORTAFOLIO_PRINT" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				--%>

				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="PORTAFOLIO_ADD" />
						<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="PORTAFOLIO_EDIT" />
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="PORTAFOLIO_DELETE" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
				
				<%-- Menú: Mediciones --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuMedicionesIndicadores" key="menu.mediciones">
						<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularPortafolio();" permisoId="PORTAFOLIO_CALCULAR" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.asignarpesos" onclick="asignarPesos();" permisoId="PORTAFOLIO_ASIGNARPESOS" aplicaOrganizacion="true" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ver --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuVerVista" key="menu.ver">
						<vgcinterfaz:botonMenu key="menu.ver.grafico" onclick="mostrarVista();" permisoId="PORTAFOLIO_VISTA" />
						<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.detallado" onclick="reportePortafolioDetalle();" permisoId="PORTAFOLIO_VISTA" />
						<vgcinterfaz:botonMenu key="jsp.gestionariniciativas.menu.reportes.resumido" onclick="reportePortafolioResumido();" permisoId="PORTAFOLIO_VISTA" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasPortafolios" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorPortafolios();" />
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

			<%-- Filtros --%>
			<table id="tblFiltro" class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px; width: 100%;">
				<tr class="barraFiltrosForma">
					<td style="width: 215px;">
						<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
							<%-- Nombre --%>
							<tr class="barraFiltrosForma">
								<td style="width: 105px;"><vgcutil:message key="jsp.gestionarportafolio.columna.nombre" /></td>
								<td style="width: 110px;">
									<logic:notEmpty name="gestionarPortafoliosForm" property="filtro.nombre">
										<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="<bean:write name="gestionarPortafoliosForm" property="filtro.nombre" />">
									</logic:notEmpty>
									<logic:empty name="gestionarPortafoliosForm" property="filtro.nombre">
										<input size="50" type="text" id="filtroNombre" name="filtroNombre" class="cuadroTexto" value="">									
									</logic:empty>
								</td>
							</tr>
				
							<%-- Condicion --%>
							<tr class="barraFiltrosForma" style="height: 20px;">
								<td style="width: 105px;"><vgcutil:message key="jsp.gestionarportafolio.columna.condicion" /></td>
								<td style="width: 110px;">
									<bean:define id="filtroCondicionValue">
										<logic:notEmpty name="gestionarPortafoliosForm" property="filtro.condicion">
											<bean:write name="gestionarPortafoliosForm" property="filtro.condicion" />
										</logic:notEmpty>
										<logic:empty name="gestionarPortafoliosForm" property="filtro.condicion">
											0
										</logic:empty>
									</bean:define>
									<select class="cuadroCombinado" name="selectCondicionType" id="selectCondicionType">
										<logic:iterate name="gestionarPortafoliosForm" property="filtro.tiposCondiciones" id="filtroCondicionType">
											<bean:define id="tipo" toScope="page" name='filtroCondicionType' property='filtroCondicionTypeId' type="Byte"></bean:define>
											<bean:define id="nombre" toScope="page"><bean:write name='filtroCondicionType' property='nombre' /></bean:define>
											<logic:equal name='filtroCondicionType' property='filtroCondicionTypeId' value='<%=filtroCondicionValue%>'>
												<option value="<%=tipo%>" selected><%=nombre%></option>
											</logic:equal>
											<logic:notEqual name='filtroCondicionType' property='filtroCondicionTypeId' value='<%=filtroCondicionValue.toString()%>'>
												<option value="<%=tipo%>"><%=nombre%></option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<%-- Botones --%>
					<td style="width: 50px;">
						<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
							<tr class="barraFiltrosForma" style="height: 20px;">
								<td colspan="2" style="width: 30px;">
									<a class="boton" title="<vgcutil:message key="boton.buscar.alt" />" onclick="refrescarPortafolio()"><vgcutil:message key="boton.buscar.alt" /></a>
								</td>
							</tr>
							<tr class="barraFiltrosForma" style="height: 20px;">
								<td colspan="2" style="width: 30px;">
									<a class="boton" title="<vgcutil:message key="boton.limpiar.alt" />" onclick="limpiarFiltros()"><vgcutil:message key="boton.limpiar.alt" /></a>
								</td>
							</tr>
						</table>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>

			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarPortafolios">

				<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_EDIT" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.modificar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_VISTA" nombreImagen="grafico" pathImagenes="/paginas/strategos/presentaciones/vistas/imagenes/barraHerramientas/" nombre="graficoVista" onclick="javascript:mostrarVista();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="boton.grafico.alt" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="PORTAFOLIO_CALCULAR" nombreImagen="calculo" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="calcularPortafolio" onclick="javascript:calcularPortafolio();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.mediciones.calcular" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
							

			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>

		<%-- Visor Tipo Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaPortafolios" nombre="visorPortafolios" seleccionSimple="true" nombreForma="gestionarPortafoliosForm" nombreCampoSeleccionados="seleccionadoId" messageKeyNoElementos="jsp.gestionarportafolio.noregistros"  nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultar(document.gestionarPortafoliosForm,'nombre', null)">
				<vgcutil:message key="jsp.gestionarportafolio.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="150px">
				<vgcutil:message key="jsp.gestionarportafolio.columna.porcentajecompletado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoCalculo" width="130px">
				<vgcutil:message key="jsp.gestionarportafolio.columna.ultimoperiodocalculo" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="condicion" width="100px">
				<vgcutil:message key="jsp.gestionarportafolio.columna.condicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estatus" width="160px">
				<vgcutil:message key="jsp.gestionarportafolio.columna.estatus" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="140px" onclick="javascript:consultar(document.gestionarPortafoliosForm, 'frecuencia', null)">
				<vgcutil:message key="jsp.gestionarportafolio.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="portafolio">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="portafolio" property="id" />
				</vgcinterfaz:visorListaFilaId>
				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickPortafolio('<bean:write name="portafolio" property="id" />');</vgcinterfaz:visorListaFilaEventoOnclick>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="portafolio" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center">
					<bean:write name="portafolio" property="porcentajeCompletadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoCalculo" align="center">
					<bean:write name="portafolio" property="fechaUltimoCalculo" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="condicion" align="center">
					<vgcst:imagenActivoPortafolio name="portafolio" property="activo" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
					<bean:write name="portafolio" property="estatus.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
					<bean:write name="portafolio" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaPortafolios" labelPage="inPagina" action="javascript:consultar(gestionarPortafoliosForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarPortafoliosForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaPortafolios" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-portafolio'), 272);
	
	var selectCondicionType = document.getElementById('selectCondicionType');
	if (selectCondicionType != null)
		_selectCondicionTypeIndex = selectCondicionType.selectedIndex;
</script>
