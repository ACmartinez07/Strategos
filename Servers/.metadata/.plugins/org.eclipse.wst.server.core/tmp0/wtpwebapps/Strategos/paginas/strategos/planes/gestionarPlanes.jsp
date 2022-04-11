<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (03/11/2012) --%>

<bean:define id="tipoPlanOperativo">
	<bean:write scope="session" name="gestionarPlanesForm" property="tipoPlanOperativo" />
</bean:define>
<bean:define id="tipoPlanEstrategico">
	<bean:write scope="session" name="gestionarPlanesForm" property="tipoPlanEstrategico" />
</bean:define>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanEstrategico %>">
			<vgcutil:message key="jsp.gestionarplanes.titulo.estrategico" />
		</logic:equal>
		<logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanOperativo %>">
			<vgcutil:message key="jsp.gestionarplanes.titulo.operativo" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{
				window.location.href='<html:rewrite action="/planes/crearPlan" />';
			}
			
			function modificar() 
			{
				if ((document.gestionarPlanesForm.seleccionados.value == null) || (document.gestionarPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var planId = document.gestionarPlanesForm.seleccionados.value;

				<logic:equal name="gestionarPlanesForm" property="editarForma" value="true">
					window.location.href='<html:rewrite action="/planes/modificarPlan" />?planId=' + planId;
				</logic:equal>
				<logic:notEqual name="gestionarPlanesForm" property="editarForma" value="true">
					<logic:equal name="gestionarPlanesForm" property="verForma" value="true">
						window.location.href='<html:rewrite action="/planes/verPlan" />?planId=' + planId;
					</logic:equal>
				</logic:notEqual>
			}
			
			function eliminar() 
			{
				if ((document.gestionarPlanesForm.seleccionados.value == null) || (document.gestionarPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var planId = document.gestionarPlanesForm.seleccionados.value;
				var eliminar = confirm('<vgcutil:message key="jsp.gestionarplanes.eliminarplan.confirmar" />');
				if (eliminar)
				{
					activarBloqueoEspera();
					window.location.href='<html:rewrite action="/planes/eliminarPlan"/>?planId=' + planId + '&ts=<%= (new Date()).getTime() %>';
				}
			}
			
			function gestionarPlan()
			{
				if ((document.gestionarPlanesForm.seleccionados.value == null) || (document.gestionarPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var planId = document.gestionarPlanesForm.seleccionados.value;
				window.location.href='<html:rewrite action="/planes/gestionarPlan" />?defaultLoader=true&planId=' + planId;
			}
			
			function activarDesactivarPlan(planId)
			{
				window.location.href='<html:rewrite action="/planes/activarDesactivarPlan" />?planId=' + planId;
			}
			
			function gestionarIniciativasPlan(planId)
			{
			     window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativas" />?defaultLoader=true&planId=' + planId;
			}
			
			function copiarPlan()
			{
				if ((document.gestionarPlanesForm.seleccionados.value == null) || (document.gestionarPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var planId = document.gestionarPlanesForm.seleccionados.value;
			    
			    abrirVentanaModal('<html:rewrite action="/planes/copiarPlan" />?planId=' + planId, 'copiarPlan', '510', '330');
			}				
			
			function reporte() 
			{			
				abrirReporte('<html:rewrite action="/planes/generarReportePlanes.action"/>?atributoOrden=' + gestionarPlanesForm.atributoOrden.value + '&tipoOrden=' + gestionarPlanesForm.tipoOrden.value + '&tipo=' + '<bean:write scope="session" name="gestionarPlanesForm" property="tipoPlanes" />' , 'reporte');
			}								 									
									
			function limpiarFiltros() 
			{
			  gestionarPlanesForm.filtroNombre.value = "";
			  gestionarPlanesForm.submit();
			}
			
			function actualizar() 
			{
				gestionarPlanesForm.submit();
			}
		
		</script>

		<%-- Se pasan los valores de los Objetos que estan en "session" o "request" a las propiedades locales --%>
		<bean:define id="totalElementos" name="paginaPlanes" property="total" scope="request" toScope="page" />
		<bean:define id="tamanoPagina" name="paginaPlanes" property="tamanoPagina" scope="request" toScope="page" />
		<bean:define id="tamanoSetPaginas" name="paginaPlanes" property="tamanoSetPaginas" scope="request" toScope="page" />
		<bean:define id="nroPagina" name="paginaPlanes" property="nroPagina" scope="request" toScope="page" />

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/gestionarPlanes" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-planes">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					<logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanEstrategico %>">
	                     ..::<vgcutil:message key="jsp.gestionarplanes.titulo.estrategico" />
					</logic:equal>

					<logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanOperativo %>">
	                     ..::<vgcutil:message key="jsp.gestionarplanes.titulo.operativo" />
					</logic:equal>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
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
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" />			
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="PLAN_ADD" />
								<logic:equal name="gestionarPlanesForm" property="editarForma" value="true">						
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
								</logic:equal>
								<logic:notEqual name="gestionarPlanesForm" property="editarForma" value="true">
									<logic:equal name="gestionarPlanesForm" property="verForma" value="true">
										<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
									</logic:equal>
								</logic:notEqual>
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="PLAN_DELETE" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="jsp.gestionarplanes.copiarplan" onclick="copiarPlan();" permisoId="PLAN_COPY" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="jsp.gestionarplanes.verplan" onclick="gestionarPlan();" permisoId="PLAN_EJECUTIVO" />
								<%-- <vgcinterfaz:botonMenu key="jsp.gestionarplanes.veriniciativas" onclick="gestionarIniciativasPlan();" permisoId="INICIATIVA" />--%>								
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" agregarSeparador="true"/>
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
					
					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarplanes.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="actualizar()" style="cursor: pointer;" width="10" height="10"
								title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;"
								width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					
					<%-- Barra de Herramientas --%>	
					<vgcinterfaz:barraHerramientas nombre="barraGestionarPlanes">						
						<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.eliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_COPY" nombreImagen="copiar" pathImagenes="/componentes/barraHerramientas/" nombre="copiar" onclick="javascript:copiarPlan();">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarplanes.copiarplan" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton permisoId="PLAN_EJECUTIVO" nombreImagen="plan" pathImagenes="/paginas/strategos/planes/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:gestionarPlan();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarplanes.verplan" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<%-- 
						<vgcinterfaz:barraHerramientasBoton permisoId="INICIATIVA" nombreImagen="iniciativas" pathImagenes="/paginas/strategos/planes/imagenes/barraHerramientas/" nombre="iniciativas" onclick="javascript:gestionarIniciativasPlan();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.gestionarplanes.veriniciativas" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						--%>		
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
							<vgcinterfaz:barraHerramientasSeparador />						
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
					
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<table class="listView" width="100%" cellpadding="5" cellspacing="1" id="tablaPlanes">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">						

						<td align="center" width="5%">&nbsp;</td>						
													
						<td align="center" width="55%" onClick="javascript:consultar(gestionarPlanesForm, 'nombre', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarplanes.columna.nombre" /><img name="nombre"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" onClick="javascript:consultar(gestionarPlanesForm, 'revision', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarplanes.columna.revision" /><img name="revision"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" onClick="javascript:consultar(gestionarPlanesForm, 'activo', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarplanes.columna.activo" /><img name="activo"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" onClick="javascript:consultar(gestionarPlanesForm, 'anoInicial', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarplanes.columna.fechainicio" /><img name="anoInicial"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" onClick="javascript:consultar(gestionarPlanesForm, 'anoFinal', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarplanes.columna.fechafinal" /><img name="anoFinal"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="paginaPlanes" property="lista" scope="request" id="plan">
						
						<tr onclick="eventoClickFilaV2(document.gestionarPlanesForm.seleccionados, document.gestionarPlanesForm.valoresSeleccionados, 'tablaPlanes', this)" id="<bean:write name="plan" property="planId" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaV2(document.gestionarPlanesForm.seleccionados, this)" onMouseOut="eventoMouseFueraFilaV2(document.gestionarPlanesForm.seleccionados, this)" height="20px">

							<td align="center">
								<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PLAN_ACTIVO">
									<%-- Activo --%>
									<logic:equal name="plan" property="activo" value="true">
										<img onClick="javascript:activarDesactivarPlan('<bean:write name="plan" property="planId" />');" style="cursor: pointer"
											src="<html:rewrite page='/paginas/strategos/planes/imagenes/activo.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarplanes.columna.desactivar" />">
									</logic:equal>
									<%-- Inactivo --%>
									<logic:equal name="plan" property="activo" value="false">
										<img onClick="javascript:activarDesactivarPlan('<bean:write name="plan" property="planId" />');" style="cursor: pointer"
											src="<html:rewrite page='/paginas/strategos/planes/imagenes/inactivo.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarplanes.columna.activar" />">
									</logic:equal>
								</vgcutil:tienePermiso>
							</td>
							
							<td id="valor<bean:write name="plan" property="planId" />"><bean:write name="plan" property="nombre" /></td>

							<td align="center">[<bean:write name="plan" property="revisionNombre" />]</td>

							<td align="center"><%-- Si --%> <logic:equal name="plan" property="activo" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal> <%-- No --%> <logic:equal name="plan" property="activo" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal></td>

							<td align="center"><bean:write name="plan" property="anoInicial" /></td>

							<td align="center"><bean:write name="plan" property="anoFinal" /></td>

						</tr>

					</logic:iterate>

					<%-- Validación cuando no hay registros --%>
					<logic:equal name="paginaPlanes" property="total" value="0" scope="request">
						<tr class="mouseFueraCuerpoListView" id="0" height="20px">
							<td valign="middle" align="center" colspan="6"><logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanEstrategico %>">
								<vgcutil:message key="jsp.gestionarplanes.estrategicos.noregistros" />
							</logic:equal> <logic:equal name="gestionarPlanesForm" property="tipoPlanes" value="<%= tipoPlanOperativo %>">
								<vgcutil:message key="jsp.gestionarplanes.operativos.noregistros" />
							</logic:equal></td>
						</tr>
					</logic:equal>

				</table>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager resultSize="<%=totalElementos%>" pageSize="<%=tamanoPagina%>" page="<%=nroPagina%>" labelPage="inPagina" action="javascript:consultar(gestionarPlanesForm, null, inPagina)"
						styleClass="paginador" pagesSetSize="<%=tamanoSetPaginas%>" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

			<script>

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionadosV2(gestionarPlanesForm.seleccionados, 'tablaPlanes');
				
				//Invoca la función que hace el ordenamiento de las columnas
				cambioImagenOrden(gestionarPlanesForm);

				// Arma la descripción al final de la lista
				var variable = document.getElementById('barraInferior');
				var atributoOrden = gestionarPlanesForm.atributoOrden.value;
				if (atributoOrden == "nombre") atributoOrden  = '<vgcutil:message key="jsp.gestionarplanes.columna.nombre" />';	
				if (atributoOrden == "revision") atributoOrden  = '<vgcutil:message key="jsp.gestionarplanes.columna.revision" />';
				if (atributoOrden == "activo") atributoOrden  = '<vgcutil:message key="jsp.gestionarplanes.columna.activo" />';
				if (atributoOrden == "anoInicial") atributoOrden  = '<vgcutil:message key="jsp.gestionarplanes.columna.fechainicio" />';
				if (atributoOrden == "anoFinal") atributoOrden  = '<vgcutil:message key="jsp.gestionarplanes.columna.fechafinal" />';
				variable.innerHTML = "<b><vgcutil:message key='jsp.gestionarlista.ordenado' /></b>: " + atributoOrden + " [" + gestionarPlanesForm.tipoOrden.value.toLowerCase() + "]";
                
			</script>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-planes'), 262);	

			var visor = document.getElementById('tablaPlanes');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
