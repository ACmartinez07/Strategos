<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/10/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarperspectivas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function seleccionar() 
			{
				if ((document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value == null) || (document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionarperspectivas.noseleccion" />');
					return;
				}
				var nombrePerspectiva; 
				<logic:notEmpty name="seleccionarPerspectivasArbolPerspectivasBean" property="nodoSeleccionado">
					nombrePerspectiva = '<bean:write name="seleccionarPerspectivasArbolPerspectivasBean" property="nodoSeleccionado.nombre" />'.replace("&lt;", "<").replace("&quot;", "'").replace("&gt;", ">");
				</logic:notEmpty>
				this.opener.document.<bean:write name="seleccionarPerspectivasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPerspectivasForm" property="nombreCampoValor" scope="session" />.value=nombrePerspectiva;
				this.opener.document.<bean:write name="seleccionarPerspectivasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPerspectivasForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value;				
				<logic:notEmpty name="seleccionarPerspectivasForm" property="nombreCampoRutaCompleta" scope="session">
					getRutaCompletaPerspectivaSeleccionada();
				</logic:notEmpty>
				<logic:empty name="seleccionarPerspectivasForm" property="nombreCampoRutaCompleta" scope="session">
					finalizarSeleccion();
				</logic:empty>				
			}
			
			function finalizarSeleccion() 
			{
				if (document.seleccionarPerspectivasForm.rutaCompletaPerspectivaSeleccionada.value == '<bean:write name="seleccionarPerspectivasForm" property="codigoPerspectivaEliminada" scope="session" />') 
					alert('<vgcutil:message key="jsp.seleccionarperspectivas.perspectivaeliminada" />');
				else if (document.seleccionarPerspectivasForm.rutaCompletaPerspectivaSeleccionada.value.indexOf('<bean:write name="seleccionarPerspectivasForm" property="codigoPerspectivaEliminada" scope="session" />') > -1) 
					alert('<vgcutil:message key="jsp.seleccionarperspectivas.perspectivaseliminadas" />');

				<logic:notEmpty name="seleccionarPerspectivasForm" property="nombreCampoRutaCompleta" scope="session">
					this.opener.document.<bean:write name="seleccionarPerspectivasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarPerspectivasForm" property="nombreCampoRutaCompleta" scope="session" />.value=document.seleccionarPerspectivasForm.rutaCompletaPerspectivaSeleccionada.value;
				</logic:notEmpty>				
				<logic:notEmpty name="seleccionarPerspectivasForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarPerspectivasForm" property="funcionCierre" scope="session" />
				</logic:notEmpty>
				this.close();
			}
			
			function getRutaCompletaPerspectivaSeleccionada() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planes/perspectivas/seleccionarPerspectivas" />?funcion=getRutaCompletaPerspectivaSeleccionada&seleccionados=' + document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value, document.seleccionarPerspectivasForm.rutaCompletaPerspectivaSeleccionada, 'finalizarSeleccion()');
			}
			
			function mostrarPanelOrganizaciones() 
			{
				marcarBotonSeleccionado('organizacion');				
				document.seleccionarPerspectivasForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarPerspectivas" nombrePanel="panelOrganizaciones" />
			}

			function mostrarPanelPlanes() 
			{
				marcarBotonSeleccionado('plan');			
				document.seleccionarPerspectivasForm.panelSeleccionado.value='panelPlanes';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarPerspectivas" nombrePanel="panelPlanes" />
			}

			function mostrarPanelPerspectivas() 
			{
				marcarBotonSeleccionado('perspectiva');				
				document.seleccionarPerspectivasForm.panelSeleccionado.value='panelPerspectivas';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarPerspectivas" nombrePanel="panelPerspectivas" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
				<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarOrganizacion" value="true">
					document.getElementById("barraSeleccionarPerspectivasBotonorganizacion").style.backgroundImage = "";
				</logic:equal>				
				<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarPlan" value="true">
					document.getElementById("barraSeleccionarPerspectivasBotonplan").style.backgroundImage = "";
				</logic:equal>
				document.getElementById("barraSeleccionarPerspectivasBotonperspectiva").style.backgroundImage = "";
				document.getElementById('barraSeleccionarPerspectivasBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';				
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/perspectivas/seleccionarPerspectivas" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>			
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="nombreCampoRutaCompleta" />
			<html:hidden property="panelSeleccionado" />
			<html:hidden property="atributoOrdenPlanes" />
			<html:hidden property="tipoOrdenPlanes" />
			<html:hidden property="planSeleccionadoId" />			
			<html:hidden property="perspectivaSeleccionadaId" />
			<input type="hidden" name="rutaCompletaPerspectivaSeleccionada" value="" />
			<input type="hidden" name="actualizar" value="true" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarperspectivas.titulo" />					
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarPerspectivasForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarperspectivas.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarPerspectivasForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>
						
						<%-- Plan --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarperspectivas.barraherramientas.plan" /></b></td>
							<td><bean:write name="seleccionarPerspectivasForm" property="nombrePlan" /></td>
						</tr>						

					</table>

					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarPerspectivas">
						<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarperspectivas.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarPlan" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelPlanes();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarperspectivas.panel.planes.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="perspectiva" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="perspectiva" onclick="javascript:mostrarPanelPerspectivas();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarperspectivas.panel.perspectivas.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="420" width="770" nombre="panelesSeleccionarPerspectivas" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarperspectivas.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarOrganizacion" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planes/perspectivas/seleccionarPerspectivasOrganizaciones.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel:  Planes --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelPlanes" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarperspectivas.panel.planes.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarPlan" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planes/perspectivas/seleccionarPerspectivasPlanes.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Perspectivas --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelPerspectivas" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarperspectivas.panel.perspectivas.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/planes/perspectivas/seleccionarPerspectivasPerspectivas.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">

				<logic:equal name="seleccionarPerspectivasForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarPerspectivasForm" property="panelSeleccionado" value="panelPlanes">
					mostrarPanelPlanes();
				</logic:equal>
				<logic:equal name="seleccionarPerspectivasForm" property="panelSeleccionado" value="panelPerspectivas">
					mostrarPanelPerspectivas();
				</logic:equal>

				// Invoca las funciones que hacen el ordenamiento de las columnas
				<logic:equal name="seleccionarPerspectivasForm" property="permitirCambiarPlan" value="true">
					actualizarSeleccionadosV2(seleccionarPerspectivasForm.planSeleccionadoId, 'tablaPlanes');
				</logic:equal>

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
