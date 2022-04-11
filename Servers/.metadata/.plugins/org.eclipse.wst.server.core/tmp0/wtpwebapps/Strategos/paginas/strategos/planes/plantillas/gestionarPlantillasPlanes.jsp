<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (20/01/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarplantillasplanes.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{
				window.location.href='<html:rewrite action="/planes/plantillas/crearPlantillaPlanes" />';
			}

			function modificar() 
			{
				if ((document.gestionarPlantillasPlanesForm.seleccionados.value == null) || (document.gestionarPlantillasPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var plantillaPlanesId = document.gestionarPlantillasPlanesForm.seleccionados.value;
				var nombre = document.gestionarPlantillasPlanesForm.valoresSeleccionados.value;
				if (nombre == "Balanced ScoreCard") 
					alert('<vgcutil:message key="jsp.gestionarplantillasplanes.plantillaplanes.modificar.balancedscorecard" />');	
				else
				{
					<logic:equal name="gestionarPlantillasPlanesForm" property="editarForma" value="true">
						window.location.href='<html:rewrite action="/planes/plantillas/modificarPlantillaPlanes" />?plantillaPlanesId=' + plantillaPlanesId;
					</logic:equal>
					<logic:notEqual name="gestionarPlantillasPlanesForm" property="editarForma" value="true">
						<logic:equal name="gestionarPlantillasPlanesForm" property="verForma" value="true">
							window.location.href='<html:rewrite action="/planes/plantillas/verPlantillaPlanes" />?plantillaPlanesId=' + plantillaPlanesId;
						</logic:equal>
					</logic:notEqual>
				}
			}
	
			function eliminar() 
			{
				if ((document.gestionarPlantillasPlanesForm.seleccionados.value == null) || (document.gestionarPlantillasPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var plantillaPlanesId = document.gestionarPlantillasPlanesForm.seleccionados.value;
				var nombre = document.gestionarPlantillasPlanesForm.valoresSeleccionados.value;
				if (nombre == "Balanced ScoreCard") 
				{
					alert('<vgcutil:message key="jsp.gestionarplantillasplanes.plantillaplanes.eliminar.balancedscorecard" />');	
				} 
				else 
				{			
					var eliminar = confirm('<vgcutil:message key="jsp.gestionarplantillasplanes.eliminarplantillaplanes.confirmar" />');
					if (eliminar) 
						window.location.href='<html:rewrite action="/planes/plantillas/eliminarPlantillaPlanes"/>?plantillaPlanesId=' + plantillaPlanesId + '&ts=<%= (new Date()).getTime() %>';
			  	}
			}

			function reporte() 
			{				
				abrirReporte('<html:rewrite action="/planes/plantillas/generarReportePlantillasPlanes"/>?atributoOrden=' + gestionarPlantillasPlanesForm.atributoOrden.value + '&tipoOrden=' + gestionarPlantillasPlanesForm.tipoOrden.value, 'reporte');
			}
	
			function limpiarFiltros() 
			{
				document.gestionarPlantillasPlanesForm.filtroNombre.value = "";
				document.gestionarPlantillasPlanesForm.submit();
			}

			function buscar() 
			{
				document.gestionarPlantillasPlanesForm.submit();
			}

		</script>

		<%-- Se pasan los valores de los Objetos que estan en "session" o "request" a las propiedades locales --%>
		<bean:define id="totalElementos" name="paginaPlantillasPlanes" property="total" scope="request" toScope="page" />
		<bean:define id="tamanoPagina" name="paginaPlantillasPlanes" property="tamanoPagina" scope="request" toScope="page" />
		<bean:define id="tamanoSetPaginas" name="paginaPlantillasPlanes" property="tamanoSetPaginas" scope="request" toScope="page" />
		<bean:define id="nroPagina" name="paginaPlantillasPlanes" property="nroPagina" scope="request" toScope="page" />

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/plantillas/gestionarPlantillasPlanes" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-plantillas-planes">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarplantillasplanes.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/planes/plantillas/gestionarPlantillasPlanes' />
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
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="METODOLOGIA_PRINT" agregarSeparador="true" />			
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="METODOLOGIA_ADD" />
								<logic:equal name="gestionarPlantillasPlanesForm" property="editarForma" value="true">						
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
								</logic:equal>
								<logic:notEqual name="gestionarPlantillasPlanesForm" property="editarForma" value="true">
									<logic:equal name="gestionarPlantillasPlanesForm" property="verForma" value="true">
										<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
									</logic:equal>
								</logic:notEqual>
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="METODOLOGIA_DELETE" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>						
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" />
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" permisoId="SEGUIMIENTO_MENSAJE" agregarSeparador="true"/>
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
							<td width="10px"><vgcutil:message key="jsp.gestionarplantillasplanes.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10"
								title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;"
								width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>
					
					<%-- Barra de Herramientas --%>	
					<vgcinterfaz:barraHerramientas nombre="barraGestionarPlantillaPlanes">						
						<vgcinterfaz:barraHerramientasBoton permisoId="METODOLOGIA_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.nuevo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="gestionarPlantillasPlanesForm" property="editarForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:notEqual name="gestionarPlantillasPlanesForm" property="editarForma" value="true">
							<logic:equal name="gestionarPlantillasPlanesForm" property="verForma" value="true">
								<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.modificar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasBoton permisoId="METODOLOGIA_DELETE" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
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

				<%-- Visor Tipo Lista --%>
				<table class="listView" width="100%" cellpadding="5" cellspacing="1" id="tablaPlantillaPlanes">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">						

						<td align="center" width="26%" onClick="javascript:consultar(gestionarPlantillasPlanesForm, 'nombre', null);" style="cursor: pointer"
							onMouseOver="this.className='mouseEncimaEncabezadoListView'" onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message
							key="jsp.gestionarplantillasplanes.columna.nombre" /><img name="nombre" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"
							title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
							
						<td align="center" width="70%" onClick="javascript:consultar(gestionarPlantillasPlanesForm, 'descripcion', null);" style="cursor: pointer"
							onMouseOver="this.className='mouseEncimaEncabezadoListView'" onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message
							key="jsp.gestionarplantillasplanes.columna.descripcion" /><img name="descripcion" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"
							title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="paginaPlantillasPlanes" property="lista" scope="request" id="plantillaPlanes">
						
						<tr onclick="eventoClickFilaV2(document.gestionarPlantillasPlanesForm.seleccionados, document.gestionarPlantillasPlanesForm.valoresSeleccionados, 'tablaPlantillaPlanes', this)" id="<bean:write name="plantillaPlanes" property="plantillaPlanesId" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaV2(document.gestionarPlantillasPlanesForm.seleccionados, this)" onMouseOut="eventoMouseFueraFilaV2(document.gestionarPlantillasPlanesForm.seleccionados, this)" height="20px">
							
							<td id="valor<bean:write name="plantillaPlanes" property="plantillaPlanesId" />"><bean:write name="plantillaPlanes" property="nombre" /></td>
							
							<td><bean:write name="plantillaPlanes" property="descripcion" /></td>

						</tr>

					</logic:iterate>

					<%-- Validación cuando no hay registros --%>
					<logic:equal name="paginaPlantillasPlanes" property="total" value="0" scope="request">
						<tr class="mouseFueraCuerpoListView" id="0" height="20px">
							<td valign="middle" align="center" colspan="2"><vgcutil:message key="jsp.gestionarplantillasplanes.noregistros" /></td>
						</tr>
					</logic:equal>

				</table>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager resultSize="<%=totalElementos%>" pageSize="<%=tamanoPagina%>" page="<%=nroPagina%>" labelPage="inPagina" action="javascript:consultar(gestionarPlantillasPlanesForm, null, inPagina)" styleClass="paginador" pagesSetSize="<%=tamanoSetPaginas%>" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">									
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>

			<script>

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionadosV2(gestionarPlantillasPlanesForm.seleccionados, 'tablaPlantillaPlanes');
				
				//Invoca la función que hace el ordenamiento de las columnas
				cambioImagenOrden(gestionarPlantillasPlanesForm);

				// Arma la descripción al final de la lista
				var barraInferior = document.getElementById('barraInferior');
				barraInferior.innerHTML = "<b><vgcutil:message key='jsp.gestionarlista.ordenado' /></b>: " + gestionarPlantillasPlanesForm.atributoOrden.value + " [" + document.gestionarPlantillasPlanesForm.tipoOrden.value.toLowerCase() + "]";

			</script>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-plantillas-planes'), 270);	

			var visor = document.getElementById('tablaPlantillaPlanes');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
