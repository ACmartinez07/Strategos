<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (03/08/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarcuentas.titulo" />
	</tiles:put>
	<%-- Cuerpo--%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var nodoSeleccionado = "";

			function seleccionarNodo(cuentaId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/gestionarCuentas"/>?selectedCuentaId=' + cuentaId + marcadorAncla;
			}

			function openNodo(cuentaId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/gestionarCuentas"/>?openCuentaId=' + cuentaId + marcadorAncla;
			}

			function closeNodo(cuentaId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/gestionarCuentas"/>?closeCuentaId=' + cuentaId + marcadorAncla;
			}

			function nuevo() {
				window.location.href='<html:rewrite action="/plancuentas/crearCuenta"/>';				
			}
	
			function modificar() {
				window.location.href='<html:rewrite action="/plancuentas/modificarCuenta" />?cuentaId=' + '<bean:write name="cuenta" property="cuentaId" scope="session" />';
			}
			
			function eliminar(){
				var respuesta = confirm ('<vgcutil:message key="jsp.gestionarcuentas.eliminarcuenta.confirmar" />');				
				if(respuesta){
					window.location.href='<html:rewrite action="/plancuentas/eliminarCuenta"/>?cuentaId=<bean:write name="cuenta" property="cuentaId"/>' + '&ts=<%= (new Date()).getTime() %>';
				}
			}
			
			function definirMascara() {
				window.location.href='<html:rewrite action="/plancuentas/definirMascaraCuentas" />';
			}
			
			function reporte() {
				abrirReporte('<html:rewrite action="/plancuentas/generarReporteCuentas.action"/>', 'reporte');
			}
		
		</script>

		<vgcinterfaz:contenedorForma idContenedor="body-cuentas">

			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.gestionarcuentas.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>

			<%-- Botón Actualizar --%>
			<vgcinterfaz:contenedorFormaBotonActualizar>
				<html:rewrite action='/plancuentas/gestionarCuentas' />
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
							<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
							<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="IMPUTACION_PRINT" agregarSeparador="true" />
							<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>

					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
							<logic:notEmpty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo">
								<vgcinterfaz:botonMenu onclick="nuevo();" permisoId="IMPUTACION_ADD">
									<vgcutil:message key="menu.edicion.nuevo" />: <bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo.nombre" />
								</vgcinterfaz:botonMenu>
							</logic:notEmpty>
							<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" permisoId="IMPUTACION_EDIT" />
							<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" agregarSeparador="true" permisoId="IMPUTACION_DELETE" />
							<vgcinterfaz:botonMenu key="menu.edicion.nuevamascara" onclick="definirMascara();" permisoId="IMPUTACION_MASK" />
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
				<vgcinterfaz:barraHerramientas nombre="barraGestionarCuentas">

					<logic:notEmpty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</logic:notEmpty>
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
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="mascara" pathImagenes="/paginas/strategos/plancuentas/imagenes/" nombre="mascara" onclick="javascript:definirMascara();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevamascara" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcinterfaz:barraHerramientas>

			</vgcinterfaz:contenedorFormaBarraGenerica>

			<%-- Este es el "Visor Tipo Arbol" --%>
			<treeview:treeview useFrame="false" name="arbolCuentas" scope="session" baseObject="cuentaRoot" isRoot="true" fieldName="nombreFormateadoArbol" fieldId="cuentaId" fieldChildren="hijos" lblUrlObjectId="cuentaId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="cuentaId" urlName="javascript:seleccionarNodo(cuentaId, marcadorAncla);" urlConnectorOpen="javascript:openNodo(cuentaId, marcadorAncla);" urlConnectorClose="javascript:closeNodo(cuentaId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

			<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
			<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="descripcionArbol">
			</vgcinterfaz:contenedorFormaBarraInferior>

		</vgcinterfaz:contenedorForma>

		<script>
			// Arma la descripción al final del árbol
			var variable = document.getElementById('descripcionArbol');
			var nombreCuenta = '<bean:write name="cuenta" property="descripcion" />';
		    variable.innerHTML = "<b><vgcutil:message key='jsp.gestionararbol.nodoseleccionado' /></b>: [" + nombreCuenta.toLowerCase() + "]";
		</script>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-cuentas'), 225);	
		</script>

	</tiles:put>

</tiles:insert>
