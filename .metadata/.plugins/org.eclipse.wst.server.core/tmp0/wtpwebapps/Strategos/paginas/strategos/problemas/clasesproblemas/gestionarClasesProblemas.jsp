<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	function seleccionarNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas"/>?selectedClaseProblemasId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitProblemas"/> + marcadorAncla;
	}

	function openNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas"/>?openClaseProblemasId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitProblemas"/> + marcadorAncla;
	}

	function closeNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas"/>?closeClaseProblemasId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitProblemas"/> + marcadorAncla;
	}

	function nuevoClaseProblemas() 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/crearClaseProblemas" />?tipo=<bean:write name="claseProblemas" property="tipo" scope="session" />';
	}

	function modificarClaseProblemas() 
	{
		<logic:equal scope="session" name="editarClase" value="true">
			window.location.href='<html:rewrite action="/problemas/clasesproblemas/modificarClaseProblemas" />?claseId=<bean:write name="claseProblemas" property="claseId" scope="session" />&tipo=<bean:write name="claseProblemas" property="tipo" scope="session" />';
		</logic:equal>
		<logic:notEqual scope="session" name="editarClase" value="true">
			<logic:equal scope="session" name="verClase" value="true">
				window.location.href='<html:rewrite action="/problemas/clasesproblemas/verClaseProblemas" />?claseId=<bean:write name="claseProblemas" property="claseId" scope="session" />&tipo=<bean:write name="claseProblemas" property="tipo" scope="session" />';
			</logic:equal>
		</logic:notEqual>
	}

	function eliminarClaseProblemas() 
	{	    
		var respuesta = confirm ('<vgcutil:message key="jsp.gestionarclasesproblemas.eliminarclaseproblema.confirmar" />');					
		if (respuesta)
			window.location.href='<html:rewrite action="/problemas/clasesproblemas/eliminarClaseProblemas" />?claseId=<bean:write name="claseProblemas" property="claseId" />' + '&ts=<%= (new Date()).getTime() %>';
	}

	function copiarClaseProblemas() 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/copiarClaseProblemas" />?claseId=<bean:write name="claseProblemas" property="claseId" scope="session" />&tipo=<bean:write name="claseProblemas" property="tipo" scope="session" />';
	}

	function propiedadesClaseProblemas() 
	{
	     abrirVentanaModal('<html:rewrite action="/problemas/clasesproblemas/propiedadesClaseProblemas" />?claseId=<bean:write name="claseProblemas" property="claseId" scope="session" />', "ClaseProblemas", 450, 470);
	}

	function reporteClasesProblemas() 
	{
		abrirReporte('<html:rewrite action="/problemas/clasesproblemas/generarReporteClasesProblemas.action"/>?tipo=<bean:write name="claseProblemas" property="tipo" scope="session" />', 'reporte');
	}

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

<vgcinterfaz:contenedorForma idContenedor="body-clase-problema">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
			..:: <vgcutil:message key="jsp.gestionarclasesproblemas.titulo" />
		</logic:equal>
		<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
			..:: <vgcutil:message key="jsp.gestionarclasesriesgos.titulo" />
		</logic:equal>
	</vgcinterfaz:contenedorFormaTitulo>
	
	<%-- Menú --%>
	<vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Inicio del Menú --%>
		<vgcinterfaz:contenedorMenuHorizontal>

			<%-- Menú: Archivo --%>
			<vgcinterfaz:contenedorMenuHorizontalItem>
				<vgcinterfaz:menuBotones id="menuArchivoClasesProblemas" key="menu.archivo">
					<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
					<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteClasesProblemas();" permisoId="CLASE_PROBLEMAS_PRINT" agregarSeparador="true" />
					<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
				</vgcinterfaz:menuBotones>
			</vgcinterfaz:contenedorMenuHorizontalItem>

			<%-- Menú: Edición --%>
			<vgcinterfaz:contenedorMenuHorizontalItem>
				<vgcinterfaz:menuBotones id="menuEdicionClasesProblemas" key="menu.edicion">
					<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoClaseProblemas();" permisoId="CLASE_PROBLEMA_ADD" />
					<logic:equal scope="session" name="editarClase" value="true">										
						<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarClaseProblemas();" />
					</logic:equal>
					<logic:notEqual scope="session" name="editarClase" value="true">
						<logic:equal scope="session" name="verClase" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarClaseProblemas();" />
						</logic:equal>
					</logic:notEqual>
					<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarClaseProblemas();" permisoId="CLASE_PROBLEMA_DELETE" agregarSeparador="true" />
					<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiarClaseProblemas();" permisoId="CLASE_PROBLEMA_EDIT" />
					<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesClaseProblemas();" permisoId="CLASE_PROBLEMA" />
				</vgcinterfaz:menuBotones>
			</vgcinterfaz:contenedorMenuHorizontalItem>

		</vgcinterfaz:contenedorMenuHorizontal>

	</vgcinterfaz:contenedorFormaBarraMenus>

	<%-- Barra Genérica --%>
	<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

		<%-- Barra de Herramientas --%>
		<vgcinterfaz:barraHerramientas nombre="barraGestionarClasesProblemas">

			<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_PROBLEMA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoClaseProblemas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.nuevo" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<logic:equal scope="session" name="editarClase" value="true">
				<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarClaseProblemas();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.modificar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
			</logic:equal>
			<logic:notEqual scope="session" name="editarClase" value="true">
				<logic:equal scope="session" name="verClase" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarClaseProblemas();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
			</logic:notEqual>
			<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_PROBLEMA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarClaseProblemas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.eliminar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasSeparador />
			<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesClaseProblemas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.propiedades" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasBoton permisoId="CLASE_PROBLEMA_EDIT" nombreImagen="copiar" pathImagenes="/componentes/barraHerramientas/" nombre="copiar" onclick="javascript:copiarClaseProblemas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.copiar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasSeparador />
			<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteClasesProblemas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.archivo.presentacionpreliminar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>

		</vgcinterfaz:barraHerramientas>

	</vgcinterfaz:contenedorFormaBarraGenerica>

	<treeview:treeview useFrame="false" name="arbolClasesProblemas" scope="session" baseObject="claseProblemasRoot" isRoot="true" fieldName="Nombre" fieldId="claseProblemasId" fieldChildren="hijos" lblUrlObjectId="objId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="claseProblemasId" urlName="javascript:seleccionarNodo(objId, marcadorAncla);" urlConnectorOpen="javascript:openNodo(objId, marcadorAncla);" urlConnectorClose="javascript:closeNodo(objId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

	<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
	<vgcinterfaz:contenedorFormaBarraInferior>
		<b><vgcutil:message key="jsp.gestionararbol.nodoseleccionado" /></b>: [<bean:write name="claseProblemas" property="nombre" />]
	</vgcinterfaz:contenedorFormaBarraInferior>

</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-clase-problema'), 225);
</script>
