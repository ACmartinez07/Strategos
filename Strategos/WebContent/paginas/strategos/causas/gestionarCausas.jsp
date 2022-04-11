<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarcausas.titulo" />
	</tiles:put>

	<%-- Cuerpo vacio --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			var nodoSeleccionado = "";
			
			function seleccionarNodo(nodoId, marcadorAncla) {		
				window.location.href='<html:rewrite action="/causas/gestionarCausas"/>?selectedCausaId=' + nodoId + marcadorAncla;
			}                                                                      
	
			function openNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/causas/gestionarCausas"/>?openCausaId=' + nodoId + marcadorAncla;
			}
			
			function closeNodo(nodoId, marcadorAncla) {					
				window.location.href='<html:rewrite action="/causas/gestionarCausas"/>?closeCausaId=' + nodoId + marcadorAncla;
			}		
			
			function nuevo() {				
				abrirVentanaModal('<html:rewrite action="/causas/crearCausa"/>', "CausaAdd", 680, 370);
			}
	
			function modificar() {				
				abrirVentanaModal('<html:rewrite action="/causas/modificarCausa"/>?causaId=<bean:write name="causa" property="causaId" scope="session" />', "CausaEdit", 680, 370);
			}
	
			function eliminar() {
			    var nombreCausa='<bean:write name="causa" property="nombre"/>';
				var respuesta = confirm ('<vgcutil:message key="jsp.gestionarcausas.eliminarcausa.confirmar" arg0=' + nombreCausa + ' />')					
				if (respuesta){
					window.location.href='<html:rewrite action="/causas/eliminarCausa" />?causaId=<bean:write name="causa" property="causaId" />' + '&ts=<%= (new Date()).getTime() %>';														
				}
			}
				
			function reporte() {			
				abrirReporte('<html:rewrite action="/causas/generarReporteCausas.action"/>', 'reporte');
			}
				
		</script>
		
		<%-- Inclusión de los JavaScript externos a esta página --%>		
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<vgcinterfaz:contenedorForma idContenedor="body-causas">

			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.gestionarcausas.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>

			<%-- Botón Actualizar --%>
			<vgcinterfaz:contenedorFormaBotonActualizar>
				<html:rewrite action='/causas/gestionarCausas' />
			</vgcinterfaz:contenedorFormaBotonActualizar>

			<%-- Botón Regresar --%>
			<vgcinterfaz:contenedorFormaBotonRegresar>
				javascript:irAtras(2)
			</vgcinterfaz:contenedorFormaBotonRegresar>

			<%-- Menú --%>
			<vgcinterfaz:contenedorFormaBarraMenus>
			
				<%-- Inicio del Menú --%>
				<vgcinterfaz:contenedorMenuHorizontal>
				
					<%-- Menú: Archivo --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
							<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
							<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="CAUSA_PRINT" agregarSeparador="true" />			
							<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>				
					
					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">					
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="CAUSA_ADD" />					
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="CAUSA_EDIT" />
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="CAUSA_DELETE" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
					
					<%-- Menú: Herramientas --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>	
						<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
							<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" permisoId="SEGUIMIENTO_MENSAJE" agregarSeparador="true"/>
							<%-- 
							<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
							 --%>
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
					
					<%-- Menú: Ayuda --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
							<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
				
				</vgcinterfaz:contenedorMenuHorizontal>
					
			</vgcinterfaz:contenedorFormaBarraMenus>
			
			<%-- Barra Genérica --%>
			<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
			
				<%-- Barra de Herramientas --%>	
				<vgcinterfaz:barraHerramientas nombre="barraGestionarCausas">
					
					<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.modificar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.eliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />								
					<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.archivo.presentacionpreliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					
				</vgcinterfaz:barraHerramientas>
				
			</vgcinterfaz:contenedorFormaBarraGenerica>	

			<treeview:treeview useFrame="false" name="arbolCausas" scope="session" baseObject="causaRoot" isRoot="true" fieldName="Nombre" fieldId="causaId" fieldChildren="hijos" lblUrlObjectId="objId"
				styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="causaId"
				urlName="javascript:seleccionarNodo(objId, marcadorAncla);" urlConnectorOpen="javascript:openNodo(objId, marcadorAncla);" urlConnectorClose="javascript:closeNodo(objId, marcadorAncla);"
				lblUrlAnchor="marcadorAncla" />

			<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
			<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			</vgcinterfaz:contenedorFormaBarraInferior>
			
		</vgcinterfaz:contenedorForma>

		<script>		
		<%-- Arma la descripción al final de la lista --%> 
		    var variable = document.getElementById('barraInferior');
		    var nombreCausa = '<bean:write name="causa" property="nombre" />';		    
		    variable.innerHTML = "<b><vgcutil:message key='jsp.gestionararbol.nodoseleccionado' /></b>: [" + nombreCausa.toLowerCase() + "]";
	   </script>
	</tiles:put>
</tiles:insert>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-causas'), 250);	
</script>
