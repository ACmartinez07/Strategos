<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/Actividad.js'/>"></script>
<script type="text/javascript">	
	var _tituloIniciativa = '<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />';
	var _showFiltro = false;
	var _selectHitoricoTypeIndex = 1;

	function nuevaIniciativa() 
	{		
		abrirVentanaModal('<html:rewrite action="/iniciativas/crearIniciativa"/>?planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />', "IniciativaAdd", 1080, 800);
		window.location.href = '<html:rewrite action="/iniciativas/crearIniciativa"/>?planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
	}

	function modificarIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
		{
			<logic:equal name="gestionarIniciativasPlanForm" property="editarForma" value="true">				
				//abrirVentanaModal('<html:rewrite action="/iniciativas/modificarIniciativa"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />', "IniciativaEdit", 1080, 800);
				window.location.href = '<html:rewrite action="/iniciativas/modificarIniciativa"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
			</logic:equal>
			<logic:notEqual name="gestionarIniciativasPlanForm" property="editarForma" value="true">
				<logic:equal name="gestionarIniciativasPlanForm" property="verForma" value="true">					
					//abrirVentanaModal('<html:rewrite action="/iniciativas/verIniciativa"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />', "IniciativaEdit", 1080, 800);
					window.location.href = '<html:rewrite action="/iniciativas/verIniciativa"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&planId=<bean:write name="gestionarPlanForm" property="planId" />&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
				</logic:equal>
			</logic:notEqual>
		}
	}

	function eliminarIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
		{
			var eliminar = confirm('<vgcutil:message key="jsp.gestionariniciativas.eliminariniciativas.confirmar" arg0="' + _tituloIniciativa + '" />');
			if (eliminar)
				window.location.href='<html:rewrite action="/iniciativas/eliminarIniciativa"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&ts=<%= (new Date()).getTime() %>';
		}
	}
	
	function propiedadesIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados)) 
			abrirVentanaModal('<html:rewrite action="/iniciativas/propiedadesIniciativa" />?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value , "iniciativa", 450, 480);
	}

	function seleccionarIniciativaAsociar() 
	{
		var queryStringFiltros = '&permitirCambiarOrganizacion=false&permitirCambiarPlan=false';	
		abrirSelectorIniciativas('gestionarIniciativasPlanForm', 'nombreIniciativa', 'iniciativaId', null, null, 'finalizarAsociacionIniciativa()', null, queryStringFiltros);		
	}

	function finalizarAsociacionIniciativa() 
	{
		window.location.href = '<html:rewrite action="/planes/iniciativas/asociarIniciativaPlan"/>?perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&planId=<bean:write name="gestionarPlanForm" property="planId" />&iniciativaId=' + document.gestionarIniciativasPlanForm.iniciativaId.value + '&ts=<%= (new java.util.Date()).getTime() %>';
	}

	function desasociarIniciativa() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados)) 
		{
			var desasociar = confirm('Está seguro que desea desasociar la iniciativa seleccionada?');
			if (desasociar) 
				window.location.href='<html:rewrite action="/planes/iniciativas/desasociarIniciativaPlan"/>?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&ts=<%= (new java.util.Date()).getTime() %>' + '&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&planId=<bean:write name="gestionarPlanForm" property="planId" />';
		}
	}

	function seleccionarIniciativaVincular() 
	{
		var queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&seleccionMultiple=true';
		abrirSelectorIniciativas('gestionarIniciativasPlanForm', 'nombreIniciativa', 'iniciativaId', null, null, 'finalizarVinculacionIniciativa()', null, queryStringFiltros);
	}

	function finalizarVinculacionIniciativa() 
	{
		window.location.href = '<html:rewrite action="/planes/iniciativas/vincularIniciativaPlan"/>?perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />&planId=<bean:write name="gestionarPlanForm" property="planId" />&iniciativaId=' + document.gestionarIniciativasPlanForm.iniciativaId.value + '&organizacionId=<bean:write name="organizacionId" />&ts=<%= (new java.util.Date()).getTime() %>';
	}

	function gestionarPlanificacionSeguimiento() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
		{
			var actividad = new Actividad();
			actividad.url = '<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>';
			actividad.ShowList(true, document.gestionarIniciativasPlanForm.seleccionados.value, undefined, '<bean:write name="gestionarPlanForm" property="planId" />');
		}
	}

	function gestionarProductos() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados)) 
			window.location.href='<html:rewrite action="/planificacionseguimiento/productos/gestionarProductosAutonomo" />?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value;
	}

	function gestionarIniciativaDePlan() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados)) 
			window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativasDePlan" />?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&source=Plan';
	}
	
	function obtenerLista(inOrden)
	{
		if (inOrden != null) 
		{
			var cambioOrden = (document.gestionarIniciativasPlanForm.atributoOrden.value != inOrden);
			document.gestionarIniciativasPlanForm.atributoOrden.value = inOrden;
			if (cambioOrden) 
				document.gestionarIniciativasPlanForm.tipoOrden.value = 'ASC';			
			else 
			{
				if (document.gestionarIniciativasPlanForm.tipoOrden.value != null) 
				{
					if (document.gestionarIniciativasPlanForm.tipoOrden.value == 'ASC') 
						document.gestionarIniciativasPlanForm.tipoOrden.value = 'DESC';					
					else 
						document.gestionarIniciativasPlanForm.tipoOrden.value = 'ASC';					
				} 
				else 
					document.gestionarIniciativasPlanForm.tipoOrden.value = 'ASC';				
			}
		}

		refrescar();
	}

	var tipoCalculoIniciativas = new Array(<bean:write name="paginaIniciativasPlan" property="total"/>);
	<logic:iterate id="iniciativa" name="paginaIniciativasPlan" property="lista" indexId="indexIniciativas">
		tipoCalculoIniciativas[<bean:write name="indexIniciativas"/>] = new Array(2);
		tipoCalculoIniciativas[<bean:write name="indexIniciativas"/>][0] = '<bean:write name="iniciativa" property="iniciativaId"/>';
		tipoCalculoIniciativas[<bean:write name="indexIniciativas"/>][1] = '<bean:write name="iniciativa" property="tipoAlerta"/>';
	</logic:iterate>
	
	function eventoClickFilaIniciativaPlan(fila) 
	{
		eventoClickFilaV2(document.gestionarIniciativasPlanForm.seleccionados, null, 'visorIniciativasPlan', fila);
		for (var i = 0; i < tipoCalculoIniciativas.length; i++) 
		{
			if (tipoCalculoIniciativas[i][0] == document.gestionarIniciativasPlanForm.seleccionados.value) 
			{
				if (tipoCalculoIniciativas[i][1] == '0') 
				{
					<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarPlanificacionSeguimiento" nombreBarra="barraGestionarIniciativasPlan" display="block" />
					<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarProductos" nombreBarra="barraGestionarIniciativasPlan" display="none" />
				} 
				else 
				{
					<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarPlanificacionSeguimiento" nombreBarra="barraGestionarIniciativasPlan" display="none" />
					<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarProductos" nombreBarra="barraGestionarIniciativasPlan" display="block" />
				}
				
				return;
			}
		}
	}
	
	function gestionarAnexosIniciativa(inciativaId) 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
		{
			var explicacion = new Explicacion();
			explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
			explicacion.ShowList(true, document.gestionarIniciativasPlanForm.seleccionados.value, 'Iniciativa', 0);
		}
	}

	function enviarEmailIniciativa()
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/iniciativas/enviarEmail" />?objetoId=' + document.gestionarIniciativasPlanForm.seleccionados.value + "&tipoObjeto=Iniciativa", document.gestionarIniciativasPlanForm.respuesta, 'onEnviarEmailIniciativa()');
	}

	function onEnviarEmailIniciativa()
	{
		var respuesta = document.gestionarIniciativasPlanForm.respuesta.value.split("|");
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
	
	function visualizarIniciativasRelacionadas() 
	{
		if (verificarElementoUnicoSeleccionMultiple(document.gestionarIniciativasPlanForm.seleccionados))
		{
			var url = '?iniciativaId=' + document.gestionarIniciativasPlanForm.seleccionados.value + '&accion=refrescar';
			window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas" />' + url;
		}
	}
	
	function resizePanelIniciativa(altoPrefijo)
	{
		if (typeof(altoPrefijo) == "undefined")
			altoPrefijo = 0;
		var tamanoToolbarMenu = 0;
		<logic:empty name="perspectiva" property="padreId" scope="session">
			tamanoToolbarMenu = 63;
		</logic:empty>
		var height = (parseInt(_myHeight) - (182)) + tamanoToolbarMenu;
		var spliter = document.getElementById('splitPlanVerticalPanelSuperior');
		if (spliter != null)
			height = height - parseInt(spliter.style.height.replace("px", ""));
		else
			height = (parseInt(_myHeight) - 480);

		height = (parseInt(height) - altoPrefijo);
		var objeto = document.getElementById('body-iniciativas-plan');
		if (objeto != null)
			objeto.style.height = height + "px";
	}
	
	function showFiltro()
	{
		var tblFiltro = document.getElementById('tblFiltro');
		var trFilterTop = document.getElementById('trFilterTop');
		var trFilterBottom = document.getElementById('trFilterBottom');
		if (tblFiltro != null)
		{
			if (_showFiltro)
			{
				_showFiltro = false;
				tblFiltro.style.display = "none";
				resizePanelIniciativa(0);
			}
			else
			{
				_showFiltro = true;
				tblFiltro.style.display = "";
				resizePanelIniciativa(102);
			}
			if (trFilterTop != null)
				trFilterTop.style.display = tblFiltro.style.display;
			if (trFilterBottom != null)
				trFilterBottom.style.display = tblFiltro.style.display;
		}
	}
	
	function limpiarFiltros()
	{
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			filtroNombre.value = "";
		if (filtroAnio != null)
			filtroAnio.value = "";
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			selectHitoricoType.selectedIndex = _selectHitoricoTypeIndex;
	}
	
	function refrescar()
	{
		var url = '?planId=<bean:write name="gestionarPlanForm" property="planId" />';
		url = url + '&perspectivaId=<bean:write name="gestionarPlanForm" property="perspectivaId" />';
		var filtroNombre = document.getElementById('filtroNombre');
		if (filtroNombre != null)
			url = url + '&filtroNombre=' + filtroNombre.value;
		var filtroAnio = document.getElementById('filtroAnio');
		if (filtroAnio != null)
			url = url + '&filtroAnio=' + filtroAnio.value;
		var selectHitoricoType = document.getElementById('selectHitoricoType');
		if (selectHitoricoType != null)
			url = url + '&selectHitoricoType=' + selectHitoricoType.value;
		var selectEstatusType = document.getElementById('selectEstatusType');
		if (selectEstatusType != null)
			url = url + '&selectEstatusType=' + selectEstatusType.value;
		
		window.document.gestionarIniciativasPlanForm.action = '<html:rewrite action="/planes/indicadores/gestionarIndicadoresPlan"/>';
		window.document.gestionarIniciativasPlanForm.submit();
	}
	
</script>

<%-- Representación de la Forma --%>
<html:form action="/planes/iniciativas/gestionarIniciativasPlan" styleClass="formaHtmlCompleta">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="respuesta" />
	
	<input type="hidden" name="nombreIniciativa">
	<input type="hidden" name="iniciativaId">

	<bean:define id="mostrarBarraSuperior" type="String" value="false"></bean:define>
	<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.iniciativas.gestionariniciativasplan.barrasuperior.mostrar" valor="true">
		<bean:define id="mostrarBarraSuperior" type="String" value="true"></bean:define>
	</vgcutil:valorPropertyIgual>
	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<vgcinterfaz:contenedorForma idContenedor="body-iniciativas-plan" mostrarBarraSuperior="<%= mostrarBarraSuperior %>" idHtml="gestionarIniciativasPlan">

		<%-- Título  --%>
		<vgcinterfaz:contenedorFormaTitulo>..::	<bean:write name="gestionarIniciativasPlanForm" property="nombreIniciativaPlural" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Validación: Solo se dibuja el Menú cuando no está seleccionada la Raiz de las Perspectivas --%>
		<logic:notEmpty name="perspectiva" property="padreId" scope="session">

			<%-- Menú --%>
			<vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Inicio del Menú --%>
				<vgcinterfaz:contenedorMenuHorizontal>

					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>

						<vgcinterfaz:menuBotones id="menuEdicionIniciativasPlan" key="menu.edicion">
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevaIniciativa();" permisoId="INICIATIVA_ADD" agregarSeparador="true" />
							<logic:equal name="gestionarIniciativasPlanForm" property="editarForma" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIniciativa();" aplicaOrganizacion="true" />
							</logic:equal>
							<logic:notEqual name="gestionarIniciativasPlanForm" property="editarForma" value="true">
								<logic:equal name="gestionarIniciativasPlanForm" property="verForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarIniciativa();" aplicaOrganizacion="true" />
								</logic:equal>
							</logic:notEqual>
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarIniciativa();" permisoId="INICIATIVA_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesIniciativa();" permisoId="INICIATIVA" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.vincular" onclick="seleccionarIniciativaVincular()" permisoId="INICIATIVA_VINCULAR" aplicaOrganizacion="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.desvincular" onclick="desasociarIniciativa()" permisoId="INICIATIVA_DESVINCULAR" aplicaOrganizacion="true" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="INICIATIVA_EMAIL" onclick="enviarEmailIniciativa();" />
						</vgcinterfaz:menuBotones>

					</vgcinterfaz:contenedorMenuHorizontalItem>

				</vgcinterfaz:contenedorMenuHorizontal>

			</vgcinterfaz:contenedorFormaBarraMenus>

			<%-- Barra Genérica --%>
			<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

				<%-- Barra de Herramientas --%>
				<vgcinterfaz:barraHerramientas nombre="barraGestionarIniciativasPlan">
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevaIniciativa" onclick="javascript:nuevaIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<logic:equal name="gestionarIniciativasPlanForm" property="editarForma" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarIniciativa" onclick="javascript:modificarIniciativa();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
					<logic:notEqual name="gestionarIniciativasPlanForm" property="editarForma" value="true">
						<logic:equal name="gestionarIniciativasPlanForm" property="verForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificarIniciativa" onclick="javascript:modificarIniciativa();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</logic:notEqual>
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminarIniciativa" onclick="javascript:eliminarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_VINCULAR" nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="vincularIniciativa" onclick="javascript:seleccionarIniciativaVincular();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.vincular" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_DESVINCULAR" nombreImagen="desasociar" pathImagenes="/componentes/barraHerramientas/" nombre="desvincularIniciativa" onclick="javascript:desasociarIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.desvincular" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_SEGUIMIENTO" nombreImagen="planificacion" pathImagenes="/paginas/strategos/iniciativas/imagenes/" nombre="gestionarPlanificacionSeguimiento" onclick="javascript:gestionarPlanificacionSeguimiento();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.gestionariniciativas.planificacionseguimiento" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="PRODUCTO" nombreImagen="productos" pathImagenes="/paginas/strategos/barraHerramientas/" nombre="gestionarProductos" onclick="javascript:gestionarProductos();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.productos" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_GESTION" nombreImagen="gestion" pathImagenes="/paginas/strategos/barraHerramientas/" nombre="gestionIniciativa" onclick="javascript:gestionarIniciativaDePlan();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="iniciativas.gestionariniciativas" arg0="<%= tituloIniciativa %>"/>
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA" nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedadesIniciativa" onclick="javascript:propiedadesIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.propiedades" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton permisoId="EXPLICACION" nombreImagen="explicaciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="gestionarAnexos" onclick="javascript:gestionarAnexosIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="jsp.visualizariniciativasplan.columna.explicaciones" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_EMAIL" nombreImagen="email" pathImagenes="/componentes/barraHerramientas/" nombre="email" onclick="javascript:enviarEmailIniciativa();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.email" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA_RELACION" nombreImagen="relacionIniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="relacionIniciativa" onclick="javascript:visualizarIniciativasRelacionadas();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.iniciativas.relacionadas" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:showFiltro();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.ver.filtro" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>

				</vgcinterfaz:barraHerramientas>

				<%-- Filtro --%>
				<table class="tablaSpacing0Padding0Width100Collapse">
					<tr id="trFilterTop" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
					<tr class="barraFiltrosForma">
						<td style="width: 420px;">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/filtroIniciativas.jsp"></jsp:include>
						</td>
					</tr>
					<tr id="trFilterBottom" style="display:none;"><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
				</table>

			</vgcinterfaz:contenedorFormaBarraGenerica>

		</logic:notEmpty>

		<%-- Visor Lista --%>
		<bean:define id="listavacia">
			<vgcutil:message key="jsp.gestionariniciativasplan.noregistros" arg0="<%= tituloIniciativa %>"/>
		</bean:define>
		<vgcinterfaz:visorLista namePaginaLista="paginaIniciativasPlan" nombre="visorIniciativasPlan" messageKeyNoElementos="<%= listavacia %>" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados del Visor Lista --%>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="40px">
				<vgcutil:message key="jsp.gestionariniciativasplan.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:obtenerLista('nombre')">
				<bean:write name="gestionarIniciativasPlanForm" property="nombreIniciativaPlural" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="70px">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.porcentajecompletado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="porcentajeEsperado" width="70px">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.porcentajeesperado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaUltimaMedicion" width="100px">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.fechaUltimaMedicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="100px" onclick="javascript:obtenerLista('frecuencia')">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="condicion" width="40px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.condicion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px">
				<vgcutil:message key="jsp.gestionariniciativas.columna.estatus" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="anioformproy" width="100px" >
				<vgcutil:message key="jsp.gestionariniciativas.columna.anioFormProy" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="responsableSeguimiento" width="200px" onclick="javascript:obtenerLista('responsableSeguimientoId');">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.responsableseguimiento" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="200px" onclick="javascript:obtenerLista('responsableLograrMetaId');">
				<vgcutil:message key="jsp.gestionariniciativasplan.columna.responsableLograrMeta" />
			</vgcinterfaz:columnaVisorLista>

			<%-- Filas del Visor Lista --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="iniciativa" property="iniciativaId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaV2(document.gestionarIniciativasPlanForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
				<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaV2(document.gestionarIniciativasPlanForm.seleccionados, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
				<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickFilaIniciativaPlan(this)</vgcinterfaz:visorListaFilaEventoOnclick>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIniciativa name="iniciativa" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="iniciativa" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center">
					<bean:write name="iniciativa" property="porcentajeCompletadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeEsperado" align="center">
					<bean:write name="iniciativa" property="porcentajeEsperadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaUltimaMedicion" align="center">
					<bean:write name="iniciativa" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia" align="center">
					<bean:write name="iniciativa" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="condicion" align="center">
					<vgcst:imagenHistoricoIniciativa name="iniciativa" property="condicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
					<bean:write name="iniciativa" property="estatus.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="anioformproy" align="center">
					<bean:write name="iniciativa" property="anioFormulacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableSeguimiento" align="left">
					<logic:notEmpty name="iniciativa" property="responsableSeguimiento">
						<bean:write name="iniciativa" property="responsableSeguimiento.nombreCargo" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta" align="left">
					<logic:notEmpty name="iniciativa" property="responsableLograrMeta">
						<bean:write name="iniciativa" property="responsableLograrMeta.nombreCargo" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<vgcutil:valorPropertyIgual nombre="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration" property="jsp.planes.iniciativas.gestionariniciativasplan.barrainferior.mostrar" valor="true">
			<%-- Barra Inferior --%>
			<vgcinterfaz:contenedorFormaBarraInferior>
				<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIniciativasPlanForm" property="atributoOrden" />  [<bean:write name="gestionarIniciativasPlanForm" property="tipoOrden" />]
			</vgcinterfaz:contenedorFormaBarraInferior>
		</vgcutil:valorPropertyIgual>

	</vgcinterfaz:contenedorForma>

	<script>
		inicializarVisorListaSeleccionSimple(gestionarIniciativasPlanForm.seleccionados, 'visorIniciativasPlan');
		<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarPlanificacionSeguimiento" nombreBarra="barraGestionarIniciativasPlan" display="block" />
		<vgcinterfaz:barraHerramientasBotonVisibilidadJs nombreBoton="gestionarProductos" nombreBarra="barraGestionarIniciativasPlan" display="none" />
	</script>

</html:form>
<script type="text/javascript">
	resizePanelIniciativa();
</script>
