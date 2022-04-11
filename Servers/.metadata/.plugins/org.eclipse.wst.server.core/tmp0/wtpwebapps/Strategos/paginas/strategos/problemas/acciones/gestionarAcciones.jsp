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
		window.location.href='<html:rewrite action="/problemas/acciones/gestionarAcciones"/>?selectedAccionCorrectivaId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitSeguimientos"/> + marcadorAncla;
	}

	function openNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/problemas/acciones/gestionarAcciones"/>?openAccionCorrectivaId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitSeguimientos"/> + marcadorAncla;
	}

	function closeNodo(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/problemas/acciones/gestionarAcciones"/>?closeAccionCorrectivaId=' + nodoId + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitSeguimientos"/> + marcadorAncla;
	}

	function nuevoAccionCorrectiva() 
	{
		window.location.href='<html:rewrite action="/problemas/acciones/crearAccion" />';
	}

	function modificarAccionCorrectiva() 
	{
		var accionId = '<bean:write name="accionCorrectiva" property="accionId" scope="session" />';
		var accionIdRoot = '<bean:write name="accionCorrectivaRoot" property="accionId" scope="session" />';
		if (accionId == accionIdRoot)
		{
			<logic:equal scope="session" name="editarClaseProblema" value="true">
				window.location.href='<html:rewrite action="/problemas/modificarProblema" />?problemaId=<bean:write name="accionCorrectivaRoot" property="problemaId" scope="session" />';
			</logic:equal>
			<logic:notEqual scope="session" name="editarClaseProblema" value="true">
				<logic:equal scope="session" name="verClaseProblema" value="true">
					window.location.href='<html:rewrite action="/problemas/verProblema" />?problemaId=<bean:write name="accionCorrectivaRoot" property="problemaId" scope="session" />';
				</logic:equal>
			</logic:notEqual>
		}
		else
		{
			<logic:equal scope="session" name="editarClase" value="true">
				window.location.href='<html:rewrite action="/problemas/acciones/modificarAccion" />?accionId=' + accionId;
			</logic:equal>
			<logic:notEqual scope="session" name="editarClase" value="true">
				<logic:equal scope="session" name="verClase" value="true">
					window.location.href='<html:rewrite action="/problemas/acciones/verAccion" />?accionId=' + accionId;
				</logic:equal>
			</logic:notEqual>
		}
	}

	function propiedadesAccionCorrectiva() 
	{
		var accionId = '<bean:write name="accionCorrectiva" property="accionId" scope="session" />';
		var accionIdRoot = '<bean:write name="accionCorrectivaRoot" property="accionId" scope="session" />';
		if (accionId == accionIdRoot) 
			abrirVentanaModal('<html:rewrite action="/problemas/propiedadesProblema" />?problemaId=<bean:write name="accionCorrectivaRoot" property="problemaId" scope="session" />', "Problema", 450, 470);			
		else 
			abrirVentanaModal('<html:rewrite action="/problemas/acciones/propiedadesAccion" />?accionId=<bean:write name="accionCorrectiva" property="accionId" scope="session" />', "AccionCorrectiva", 450, 470);
	}

	function eliminarAccionCorrectiva() 
	{
		var respuesta = confirm ('<vgcutil:message key="jsp.gestionaraccionescorrectivas.eliminaraccioncorrectiva.confirmar" />');
		if (respuesta)
			window.location.href='<html:rewrite action="/problemas/acciones/eliminarAccion" />?accionId=<bean:write name="accionCorrectiva" property="accionId" />' + '&ts=<%= (new Date()).getTime() %>';
	}

	function reporteAccionesCorrectivas() 
	{		
		abrirReporte('<html:rewrite action="/problemas/acciones/generarReporteAcciones.action"/>', 'reporte');
	}

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

<vgcinterfaz:contenedorForma idContenedor="body-acciones">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
			..:: <vgcutil:message key="jsp.gestionaraccionescorrectivas.titulo" />
		</logic:equal>
		<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
			..:: <vgcutil:message key="jsp.gestionarproblemas.boton.accionespreventivas" />
		</logic:equal>
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Menú --%>
	<vgcinterfaz:contenedorFormaBarraMenus>

		<%-- Inicio del Menú --%>
		<vgcinterfaz:contenedorMenuHorizontal>

			<%-- Menú: Archivo --%>
			<vgcinterfaz:contenedorMenuHorizontalItem>
				<vgcinterfaz:menuBotones id="menuArchivoAccionesCorrectivas" key="menu.archivo">
					<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
					<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteAccionesCorrectivas();" permisoId="ACCIONES_PRINT" agregarSeparador="true" />
					<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
				</vgcinterfaz:menuBotones>
			</vgcinterfaz:contenedorMenuHorizontalItem>

			<%-- Menú: Edición --%>
			<vgcinterfaz:contenedorMenuHorizontalItem>
				<vgcinterfaz:menuBotones id="menuEdicionAccionesCorrectivas" key="menu.edicion">
					<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevoAccionCorrectiva();" permisoId="ACCION_ADD" />
					<logic:equal scope="session" name="editarClase" value="true">
						<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarAccionCorrectiva();" />
					</logic:equal>
					<logic:notEqual scope="session" name="editarClase" value="true">
						<logic:equal scope="session" name="verClase" value="true">
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificarAccionCorrectiva();" />
						</logic:equal>
					</logic:notEqual>
					<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminarAccionCorrectiva();" permisoId="ACCION_DELETE" agregarSeparador="true" />
					<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesAccionCorrectiva();" permisoId="ACCION" />
				</vgcinterfaz:menuBotones>
			</vgcinterfaz:contenedorMenuHorizontalItem>

		</vgcinterfaz:contenedorMenuHorizontal>

	</vgcinterfaz:contenedorFormaBarraMenus>

	<%-- Barra Genérica --%>
	<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

		<%-- Barra de Herramientas --%>
		<vgcinterfaz:barraHerramientas nombre="barraGestionarAccionesCorrectivas">

			<vgcinterfaz:barraHerramientasBoton permisoId="ACCION_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevoAccionCorrectiva();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.nuevo" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<logic:equal scope="session" name="editarClase" value="true">
				<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarAccionCorrectiva();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="menu.edicion.modificar" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>
			</logic:equal>
			<logic:notEqual scope="session" name="editarClase" value="true">
				<logic:equal scope="session" name="verClase" value="true">
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificarAccionCorrectiva();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</logic:equal>
			</logic:notEqual>
			<vgcinterfaz:barraHerramientasBoton permisoId="ACCION_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminarAccionCorrectiva();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.eliminar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasSeparador />
			<vgcinterfaz:barraHerramientasBoton permisoId="ACCION" nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesAccionCorrectiva();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.edicion.propiedades" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>
			<vgcinterfaz:barraHerramientasSeparador />
			<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteAccionesCorrectivas();">
				<vgcinterfaz:barraHerramientasBotonTitulo>
					<vgcutil:message key="menu.archivo.presentacionpreliminar" />
				</vgcinterfaz:barraHerramientasBotonTitulo>
			</vgcinterfaz:barraHerramientasBoton>

		</vgcinterfaz:barraHerramientas>

	</vgcinterfaz:contenedorFormaBarraGenerica>

	<treeview:treeview useFrame="false" name="arbolAccionesCorrectivas" scope="session" baseObject="accionCorrectivaRoot" isRoot="true" fieldName="Nombre" fieldId="accionCorrectivaId" fieldChildren="hijos" lblUrlObjectId="objId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="accionCorrectivaId" urlName="javascript:seleccionarNodo(objId, marcadorAncla);" urlConnectorOpen="javascript:openNodo(objId, marcadorAncla);" urlConnectorClose="javascript:closeNodo(objId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

	<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
	<vgcinterfaz:contenedorFormaBarraInferior>
		<b><vgcutil:message key="jsp.gestionararbol.nodoseleccionado" /></b>: [<bean:write name="accionCorrectiva" property="nombre" />]
	</vgcinterfaz:contenedorFormaBarraInferior>

</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-acciones'), 225);	
</script>
