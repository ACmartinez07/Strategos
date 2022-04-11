<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (29/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarplanes.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function seleccionar() 
			{
				if ((document.seleccionarPlanesForm.seleccionados.value == null) || (document.seleccionarPlanesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionarplanes.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarPlanesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPlanesForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarPlanesForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarPlanesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPlanesForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarPlanesForm.seleccionados.value;		
				<logic:notEmpty name="seleccionarPlanesForm" property="nombreCampoRutasCompletas" scope="session">
					getRutaCompletaPlanesSeleccionados();
				</logic:notEmpty>			
				<logic:empty name="seleccionarPlanesForm" property="nombreCampoRutasCompletas" scope="session">
					finalizarSeleccion();
				</logic:empty>
			}
			
			function finalizarSeleccion() 
			{
				if (document.seleccionarPlanesForm.rutaCompletaSeleccionados.value == '<bean:write name="seleccionarPlanesForm" property="codigoPlanEliminado" scope="session" />') {
					alert('<vgcutil:message key="jsp.seleccionarplanes.planeliminado" />');
				} else if (document.seleccionarPlanesForm.rutaCompletaSeleccionados.value.indexOf('<bean:write name="seleccionarPlanesForm" property="codigoPlanEliminado" scope="session" />') > -1) {
					alert('<vgcutil:message key="jsp.seleccionarplanes.planeseliminados" />');
				}
				<logic:notEmpty name="seleccionarPlanesForm" property="nombreCampoRutasCompletas" scope="session">
					this.opener.document.<bean:write name="seleccionarPlanesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPlanesForm" property="nombreCampoRutasCompletas" scope="session" />.value=document.seleccionarPlanesForm.rutaCompletaSeleccionados.value;					
				</logic:notEmpty>
				<logic:notEmpty name="seleccionarPlanesForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarPlanesForm" property="funcionCierre" scope="session" />
				</logic:notEmpty>
				this.close();
			}
			
			function getRutaCompletaPlanesSeleccionados() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/seleccionarPlanes" />?funcion=getRutaCompletaPlanesSeleccionados&seleccionados=' + document.seleccionarPlanesForm.seleccionados.value, document.seleccionarPlanesForm.rutaCompletaSeleccionados, 'finalizarSeleccion()');
			}
			
			function mostrarPanelOrganizaciones() {
				marcarBotonSeleccionado('organizacion');								
				document.seleccionarPlanesForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarPlanes" nombrePanel="panelOrganizaciones" />
			}

			function mostrarPanelPlanes() {
				marcarBotonSeleccionado('plan');				
				document.seleccionarPlanesForm.panelSeleccionado.value='panelPlanes';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarPlanes" nombrePanel="panelPlanes" />
			}	
			
			function marcarBotonSeleccionado(boton) {
				<logic:equal name="seleccionarPlanesForm" property="permitirCambiarOrganizacion" value="true">
					document.getElementById("barraSeleccionarPlanesBotonorganizacion").style.backgroundImage = "";
				</logic:equal>
				document.getElementById("barraSeleccionarPlanesBotonplan").style.backgroundImage = "";
				document.getElementById('barraSeleccionarPlanesBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';				
			}		
						
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/seleccionarPlanes" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />			
			<input type="hidden" name="rutaCompletaSeleccionados" value="" />
			<input type="hidden" name="actualizar" value="true" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="nombreCampoRutasCompletas" />
			<html:hidden property="panelSeleccionado" />

			<vgcinterfaz:contenedorForma width="715px" height="570px" esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()" marginTop="15px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarplanes.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarPlanesForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarplanes.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarPlanesForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>
						
					</table>
					
					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarPlanes">
						<logic:equal name="seleccionarPlanesForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarplanes.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>						
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelPlanes();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarplanes.panel.planes.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="700" height="430" nombre="panelesSeleccionarPlanes" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarplanes.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarPlanesForm" property="permitirCambiarOrganizacion" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planes/seleccionarPlanesOrganizaciones.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>
					
					<%-- Panel: Planes --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelPlanes" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarplanes.panel.planes.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/planes/seleccionarPlanesPlanes.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script type="text/javascript">
				
				<logic:equal name="seleccionarPlanesForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarPlanesForm" property="panelSeleccionado" value="panelPlanes">
					mostrarPanelPlanes();
				</logic:equal>	
				
				// Invoca las funciones que hacen el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarPlanesForm, 'tablaPlanes');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
