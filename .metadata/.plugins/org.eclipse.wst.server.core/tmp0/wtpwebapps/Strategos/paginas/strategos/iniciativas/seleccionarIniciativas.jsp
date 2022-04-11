<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (01/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionariniciativas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<script type="text/javascript" src="<html:rewrite page="/paginas/strategos/iniciativas/Iniciativa.js" />"></script>
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var _botonAplicar = false;
		
			function cancelar() 
			{
				this.close();
			}
			
			function seleccionar(aplicar) 
			{
				if (aplicar == undefined)
					aplicar = false;
				_botonAplicar = aplicar;
				
				if ((document.seleccionarIniciativasForm.seleccionados.value == null) || (document.seleccionarIniciativasForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarIniciativasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIniciativasForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarIniciativasForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarIniciativasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIniciativasForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarIniciativasForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarIniciativasForm" property="nombreCampoRutasCompletas" scope="session">
					getRutaCompletaIniciativasSeleccionadas();
				</logic:notEmpty>
				<logic:notEmpty name="seleccionarIniciativasForm" property="nombreCampoPlanId" scope="session">
					this.opener.document.<bean:write name="seleccionarIniciativasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIniciativasForm" property="nombreCampoPlanId" scope="session" />.value=document.seleccionarIniciativasForm.planSeleccionadoId.value;
				</logic:notEmpty>
				<logic:empty name="seleccionarIniciativasForm" property="nombreCampoRutasCompletas" scope="session">
					finalizarSeleccion();
				</logic:empty>
			}
			
			function finalizarSeleccion() 
			{
				if (document.seleccionarIniciativasForm.rutaCompletaSeleccionados.value == '<bean:write name="seleccionarIniciativasForm" property="codigoIniciativaEliminada" scope="session" />') 
					alert('<vgcutil:message key="jsp.seleccionariniciativas.iniciativaeliminada" />');
				else if (document.seleccionarIniciativasForm.rutaCompletaSeleccionados.value.indexOf('<bean:write name="seleccionarIniciativasForm" property="codigoIniciativaEliminada" scope="session" />') > -1) 
					alert('<vgcutil:message key="jsp.seleccionariniciativas.iniciativaseliminadas" />');
				<logic:notEmpty name="seleccionarIniciativasForm" property="nombreCampoRutasCompletas" scope="session">
					this.opener.document.<bean:write name="seleccionarIniciativasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIniciativasForm" property="nombreCampoRutasCompletas" scope="session" />.value=document.seleccionarIniciativasForm.rutaCompletaSeleccionados.value;
				</logic:notEmpty>				
				<logic:notEmpty name="seleccionarIniciativasForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarIniciativasForm" property="funcionCierre" scope="session" />
				</logic:notEmpty>
				
				if (!_botonAplicar)
					cancelar();
			}
			
			function getRutaCompletaIniciativasSeleccionadas() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/iniciativas/seleccionarIniciativas" />?funcion=getRutaCompletaIniciativasSeleccionadas&seleccionados=' + document.seleccionarIniciativasForm.seleccionados.value, document.seleccionarIniciativasForm.rutaCompletaSeleccionados, 'finalizarSeleccion()');
			}
			
			function mostrarPanelOrganizaciones() 
			{
				marcarBotonSeleccionado('organizacion');
				document.seleccionarIniciativasForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIniciativas" nombrePanel="panelOrganizaciones" />
			}

			function mostrarPanelPlanes() 
			{
				marcarBotonSeleccionado('plan');
				document.seleccionarIniciativasForm.panelSeleccionado.value='panelPlanes';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIniciativas" nombrePanel="panelPlanes" />
			}

			function mostrarPanelIniciativas() 
			{
				marcarBotonSeleccionado('iniciativa');
				document.seleccionarIniciativasForm.panelSeleccionado.value='panelIniciativas';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIniciativas" nombrePanel="panelIniciativas" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
				<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarOrganizacion" value="true">
					document.getElementById("barraSeleccionarIniciativasBotonorganizacion").style.backgroundImage = "";
				</logic:equal>				
				<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="true">
					document.getElementById("barraSeleccionarIniciativasBotonplan").style.backgroundImage = "";
				</logic:equal>
				document.getElementById("barraSeleccionarIniciativasBotoniniciativa").style.backgroundImage = "";				
				document.getElementById('barraSeleccionarIniciativasBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';				
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/iniciativas/seleccionarIniciativas" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="atributoOrdenPlanes" />
			<html:hidden property="tipoOrdenPlanes" />
			<html:hidden property="planSeleccionadoId" />
			<input type="hidden" name="rutaCompletaSeleccionados" value="" />
			<input type="hidden" name="planIdIniciativasSeleccionadas" value="" />
			<input type="hidden" name="actualizar" value="true" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="nombreCampoRutasCompletas" />
			<html:hidden property="nombreCampoPlanId" />
			<html:hidden property="panelSeleccionado" />

			<vgcinterfaz:contenedorForma esSelector="true" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionariniciativas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarIniciativasForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionariniciativas.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarIniciativasForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>

						<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="true">
							<%-- Plan --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionariniciativas.barraherramientas.plan" /></b></td>
								<td><bean:write name="seleccionarIniciativasForm" property="nombrePlan" /></td>
							</tr>
						</logic:equal>

					</table>

					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIniciativas">
						<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionariniciativas.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelPlanes();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionariniciativas.panel.planes.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="iniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="iniciativa" onclick="javascript:mostrarPanelIniciativas();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionariniciativas.panel.iniciativas.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Valida el alto de los paneles --%>
				<bean:define id="altoPaneles" value="420" />
				<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="false">
					<bean:define id="altoPaneles" value="435" />
				</logic:equal>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="<%=altoPaneles%>" width="700" nombre="panelesSeleccionarIniciativas" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionariniciativas.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarOrganizacion" value="true">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/seleccionarIniciativasOrganizaciones.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel:  Planes --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelPlanes" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionariniciativas.panel.planes.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="true">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/seleccionarIniciativasPlanes.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Iniciativas --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelIniciativas" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionariniciativas.panel.iniciativas.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/iniciativas/seleccionarIniciativasIniciativas.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>
				
				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:equal name="seleccionarIniciativasForm" property="seleccionMultiple" value="true">
						<img src="<html:rewrite page='/componentes/formulario/acciones.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.aplicar.alt" />">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar(true);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aplicar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:equal>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.seleccionar.alt" />">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar(false);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.seleccionar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script type="text/javascript">

				<logic:equal name="seleccionarIniciativasForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarIniciativasForm" property="panelSeleccionado" value="panelPlanes">
					mostrarPanelPlanes();
				</logic:equal>
				<logic:equal name="seleccionarIniciativasForm" property="panelSeleccionado" value="panelIniciativas">
					mostrarPanelIniciativas();
				</logic:equal>
				
				// Invoca las funciones que hacen el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarIniciativasForm, 'tablaIniciativas');
				<logic:equal name="seleccionarIniciativasForm" property="permitirCambiarPlan" value="true">
					actualizarSeleccionadosV2(seleccionarIniciativasForm.planSeleccionadoId, 'tablaPlanes');
				</logic:equal>				

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
