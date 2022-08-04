<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/01/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	var visible = null;
	<logic:equal scope="session" name="claseVisible" value="true">
		visible = true;
	</logic:equal>
	<logic:equal scope="session" name="claseVisible" value="false">
		visible = false;
	</logic:equal>

	function refrescarClase(refrescar)
	{
		var xml = "";
		if (typeof(refrescar) != "undefined" && refrescar)
			xml = "&accion=refrescar";
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores"/>?visible=' + visible + xml;
	}
	
	function mostrarClases()
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores"/>?cambiarEstadoVisible=' + !visible;
	}

	function seleccionarNodoClaseIndicadores(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores"/>?selectedClaseIndicadoresId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIndicadores"/> + marcadorAncla + '&visible=' + visible;
	}

	function openNodoClaseIndicadores(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores"/>?openClaseIndicadoresId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIndicadores"/> + marcadorAncla + '&visible=' + visible;
	}

	function closeNodoClaseIndicadores(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores"/>?closeClaseIndicadoresId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitIndicadores"/> + marcadorAncla + '&visible=' + visible;
	}

	function nuevoClaseIndicadores() 
	{		
		//abrirVentanaModal('<html:rewrite action="/indicadores/clasesindicadores/crearClaseIndicadores"/>', 'ClaseIndicadoresAdd', 680, 420);
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/crearClaseIndicadores" />';		
	}

	function modificarClaseIndicadores() 
	{
		<logic:equal scope="session" name="editarClase" value="true">
			//abrirVentanaModal('<html:rewrite action="/indicadores/clasesindicadores/modificarClaseIndicadores"/>?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />', 'ClaseIndicadoresAdd', 680, 420);
			window.location.href='<html:rewrite action="/indicadores/clasesindicadores/modificarClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />';
		</logic:equal>
		<logic:notEqual scope="session" name="editarClase" value="true">
			<logic:equal scope="session" name="verClase" value="true">
				//abrirVentanaModal('<html:rewrite action="/indicadores/clasesindicadores/verClaseIndicadores"/>?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />', 'ClaseIndicadoresAdd', 680, 420);
				window.location.href='<html:rewrite action="/indicadores/clasesindicadores/verClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />';
			</logic:equal>
		</logic:notEqual>
	}

	function propiedadesClaseIndicadores() 
	{
	     abrirVentanaModal('<html:rewrite action="/indicadores/clasesindicadores/propiedadesClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />', "Clase", 565, 490);			
	}

	function eliminarClaseIndicadores() 
	{
	    var nombre = '<bean:write name="claseIndicadores" property="nombre" scope="session" />';
	    var hijos = '<bean:write scope="session" name="hijos"/>';
	    var respuesta = "";
	    if (hijos == 'true')
			respuesta = confirm('<vgcutil:message key="jsp.gestionarclasesindicadores.eliminarclaseindicadores.hijos.confirmar" arg0="' + nombre + '" />');
	    else
	    	respuesta = confirm('<vgcutil:message key="jsp.gestionarclasesindicadores.eliminarclaseindicadores.confirmar" arg0="' + nombre + '" />');
		if (respuesta)
		{
			activarBloqueoEspera();
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/clasesindicadores/eliminarClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" />&funcion=check' + '&ts=<%= (new Date()).getTime() %>', document.gestionarClaseIndicadoresForm.respuesta, 'onEliminarClaseIndicadores()');
		}
	}
	
	function onEliminarClaseIndicadores()
	{
		desactivarBloqueoEspera();
		var respuesta = document.gestionarClaseIndicadoresForm.respuesta.value.split("|");
		if (respuesta.length > 0)
		{
			if (respuesta[0] == "true")
			{
				var respuesta = confirm ('<vgcutil:message key="jsp.gestionarclasesindicadores.eliminarclaseindicadores.insumo.confirmar" />');
				if (respuesta) 
				{
					activarBloqueoEspera();
					window.location.href='<html:rewrite action="/indicadores/clasesindicadores/eliminarClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" />' + '&ts=<%= (new Date()).getTime() %>';
				}
			}
			else
			{
				activarBloqueoEspera();
				window.location.href='<html:rewrite action="/indicadores/clasesindicadores/eliminarClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" />' + '&ts=<%= (new Date()).getTime() %>';
			}
		}
		else
		{
			activarBloqueoEspera();
			window.location.href='<html:rewrite action="/indicadores/clasesindicadores/eliminarClaseIndicadores" />?claseId=<bean:write name="claseIndicadores" property="claseId" />' + '&ts=<%= (new Date()).getTime() %>';
		}
	}

	function reporteClasesIndicadores() 
	{
		abrirReporte('<html:rewrite action="/indicadores/clasesindicadores/generarReporteClasesIndicadores"/>?organizacionId=<bean:write scope="session" name="organizacionId"/>' + '&visible=' + visible, 'reporte');
	}

	function calcular() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&claseId=<bean:write scope="session" name="claseId"/>';

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
    }

    function copiarClase()
    {
    	window.location.href='<html:rewrite action="/indicadores/clasesindicadores/copiarClase"/>?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />&funcion=Copiar';
    }

    function crearIndicadoresConsolidadosClases()
    {
    	window.location.href='<html:rewrite action="/indicadores/leerIndicadorConsolidado"/>?organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
	}
    
	function mover()
	{
		abrirVentanaModal('<html:rewrite action="/indicadores/clasesindicadores/moverClase"/>?claseId=<bean:write name="claseIndicadores" property="claseId" scope="session" />', 'moverClase', 500, 285);
	}

</script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<html:form action="/indicadores/clasesindicadores/gestionarClasesIndicadores" styleClass="formaHtmlGestionar">
	
	<%-- Atributos de la Forma --%>
	<html:hidden property="respuesta" />
	
	<vgcinterfaz:contenedorForma idContenedor="body-clases">
	
		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.gestionarclasesindicadores.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>
	
		<%-- Menú --%>
		<vgcinterfaz:contenedorFormaBarraMenus>
	
			<%-- Inicio del Menú --%>
			<vgcinterfaz:contenedorMenuHorizontal>
	
				<%-- Menú: Archivo --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuArchivoClasesIndicadores" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
						<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteClasesIndicadores();" permisoId="CLASE_PRINT" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
	
				<%-- Menú: Edición --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuEdicionClasesIndicadores" key="menu.edicion">
						<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoClaseIndicadores();" permisoId="CLASE_ADD" aplicaOrganizacion="true" />
						<logic:equal scope="session" name="editarClase" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarClaseIndicadores();" />
						</logic:equal>
						<logic:notEqual scope="session" name="editarClase" value="true">
							<logic:equal scope="session" name="verClase" value="true">
								<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarClaseIndicadores();" />
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarClaseIndicadores();" permisoId="CLASE_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiarClase();" permisoId="CLASE_COPY" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.edicion.move" onclick="mover();" permisoId="CLASE_MOVE" agregarSeparador="true" />
						<%-- <vgcinterfaz:botonMenu key="menu.edicion.indicadoresconsolidados" onclick="crearIndicadoresConsolidadosClases();" agregarSeparador="true" /> --%>
						<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesClaseIndicadores();" permisoId="CLASE" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>
	
			</vgcinterfaz:contenedorMenuHorizontal>
		</vgcinterfaz:contenedorFormaBarraMenus>
	
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
	
			<%-- Barra de Herramientas --%>
			<vgcinterfaz:barraHerramientas nombre="barraGestionarClasesIndicadoress">
				<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_ADD" aplicaOrganizacion="true" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoClaseIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.nuevo" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<logic:equal scope="session" name="editarClase" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarClaseIndicadores();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
				<logic:notEqual scope="session" name="editarClase" value="true">
					<logic:equal scope="session" name="verClase" value="true">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarClaseIndicadores();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:equal>
				</logic:notEqual>
				<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_DELETE" aplicaOrganizacion="true" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarClaseIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.eliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="CLASE" nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesClaseIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.propiedades" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_VISTA" nombreImagen="visible" pathImagenes="/componentes/barraHerramientas/" nombre="visible" onclick="javascript:mostrarClases();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<logic:equal scope="session" name="claseVisible" value="true">
							<vgcutil:message key="menu.archivo.visible" />
						</logic:equal>
						<logic:equal scope="session" name="claseVisible" value="false">
							<vgcutil:message key="menu.archivo.ocultar" />
						</logic:equal>
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
				<vgcinterfaz:barraHerramientasSeparador />
				<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteClasesIndicadores();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.archivo.presentacionpreliminar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
	
			</vgcinterfaz:barraHerramientas>
	
		</vgcinterfaz:contenedorFormaBarraGenerica>
		<treeview:treeview 
			useFrame="false" 
			arbolBean="gestionarClasesIndicadoresArbolBean" 
			scope="session" 
			isRoot="true" 
			fieldName="nodoArbolNombre" 
			fieldId="nodoArbolId" 
			fieldChildren="nodoArbolHijos" 
			lblUrlObjectId="objId" 
			styleClass="treeview" 
			checkbox="false" 
			pathImages="/componentes/visorArbol/" 
			urlJs="/componentes/visorArbol/visorArbol.js" 
			nameSelectedId="claseId" 
			urlName="javascript:seleccionarNodoClaseIndicadores(objId, marcadorAncla);" 
			urlConnectorOpen="javascript:openNodoClaseIndicadores(objId, marcadorAncla);" 
			urlConnectorClose="javascript:closeNodoClaseIndicadores(objId, marcadorAncla);" 
			lblUrlAnchor="marcadorAncla" />
		<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
		<vgcinterfaz:contenedorFormaBarraInferior>
			<logic:notEmpty name="claseIndicadores" property="nombre">
				<b><vgcutil:message key="jsp.gestionararbol.nodoseleccionado" /></b>: [<bean:write name="claseIndicadores" property="nombre" />]
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
	
	</vgcinterfaz:contenedorForma>
</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-clases'), 225);
</script>

