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
		<vgcutil:message key="jsp.seleccionaractividades.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function seleccionar() 
			{
				if ((document.seleccionarActividadForm.seleccionados.value == null) || (document.seleccionarActividadForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				
				this.opener.document.<bean:write name="seleccionarActividadForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarActividadForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarActividadForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarActividadForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarActividadForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarActividadForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarActividadForm" property="nombreCampoPlanId" scope="session">
					this.opener.document.<bean:write name="seleccionarActividadForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarActividadForm" property="nombreCampoPlanId" scope="session" />.value=document.seleccionarActividadForm.planSeleccionadoId.value;
				</logic:notEmpty>
				finalizarSeleccion();
			}
			
			function finalizarSeleccion() 
			{
				<logic:notEmpty name="seleccionarActividadForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarActividadForm" property="funcionCierre" scope="session" />
				</logic:notEmpty>
				this.close();
			}
			
			function mostrarPanelOrganizaciones() 
			{
				marcarBotonSeleccionado('organizacion');
				document.seleccionarActividadForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarActividades" nombrePanel="panelOrganizaciones" />
			}

			function mostrarPanelPlanes() {
				marcarBotonSeleccionado('plan');
				document.seleccionarActividadForm.panelSeleccionado.value='panelPlanes';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarActividades" nombrePanel="panelPlanes" />
			}

			function mostrarPanelIniciativas() 
			{
				marcarBotonSeleccionado('iniciativa');
				document.seleccionarActividadForm.panelSeleccionado.value='panelIniciativas';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarActividades" nombrePanel="panelIniciativas" />
			}

			function mostrarPanelActividades() 
			{
				marcarBotonSeleccionado('actividad');
				document.seleccionarActividadForm.panelSeleccionado.value='panelActividades';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarActividades" nombrePanel="panelActividades" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
				<logic:equal name="seleccionarActividadForm" property="permitirCambiarOrganizacion" value="true">
					document.getElementById("barraSeleccionarActividadesBotonorganizacion").style.backgroundImage = "";
				</logic:equal>				
				<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="true">
					document.getElementById("barraSeleccionarActividadesBotonplan").style.backgroundImage = "";
				</logic:equal>
				<logic:equal name="seleccionarActividadForm" property="permitirCambiarIniciativa" value="true">
					document.getElementById("barraSeleccionarActividadesBotoniniciativa").style.backgroundImage = "";
				</logic:equal>
				document.getElementById("barraSeleccionarActividadesBotonactividad").style.backgroundImage = "";				
				document.getElementById('barraSeleccionarActividadesBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';				
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/actividades/selector/seleccionarActividad" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="atributoOrdenPlanes" />
			<html:hidden property="tipoOrdenPlanes" />
			<html:hidden property="planSeleccionadoId" />
			<html:hidden property="iniciativaSeleccionadaId" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="nombreCampoRutasCompletas" />
			<html:hidden property="nombreCampoPlanId" />
			<html:hidden property="panelSeleccionado" />
			<input type="hidden" name="rutaCompletaSeleccionados" value="" />
			<input type="hidden" name="planIdActividadesSeleccionadas" value="" />
			<input type="hidden" name="actualizar" value="true" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionaractividades.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarActividadForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionaractividades.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarActividadForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>

						<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="true">
							<%-- Plan --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionaractividades.barraherramientas.plan" /></b></td>
								<td><bean:write name="seleccionarActividadForm" property="nombrePlan" /></td>
							</tr>
						</logic:equal>

						<logic:equal name="seleccionarActividadForm" property="permitirCambiarIniciativa" value="true">
							<%-- Plan --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionaractividades.barraherramientas.iniciativa" /></b></td>
								<td><bean:write name="seleccionarActividadForm" property="nombreIniciativa" /></td>
							</tr>
						</logic:equal>

					</table>

					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarActividades">
						<logic:equal name="seleccionarActividadForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionaractividades.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelPlanes();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionaractividades.panel.planes.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="iniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="iniciativa" onclick="javascript:mostrarPanelIniciativas();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionaractividades.panel.iniciativas.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="actividad" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="actividad" onclick="javascript:mostrarPanelActividades();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionaractividades.panel.actividades.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Valida el alto de los paneles --%>
				<bean:define id="altoPaneles" value="420" />
				<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="false">
					<bean:define id="altoPaneles" value="435" />
				</logic:equal>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="<%=altoPaneles%>" width="700" nombre="panelesSeleccionarActividades" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionaractividades.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarActividadForm" property="permitirCambiarOrganizacion" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/selector/seleccionarActividadOrganizaciones.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel:  Planes --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelPlanes" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionaractividades.panel.planes.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/selector/seleccionarActividadPlanes.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Iniciativas --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelIniciativas" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionaractividades.panel.iniciativas.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarActividadForm" property="permitirCambiarIniciativa" value="true">
							<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/selector/seleccionarActividadIniciativas.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Actividades --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelActividades" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionaractividades.panel.actividades.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/selector/seleccionarActividadActividades.jsp"></jsp:include>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">

				<logic:equal name="seleccionarActividadForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarActividadForm" property="panelSeleccionado" value="panelPlanes">
					mostrarPanelPlanes();
				</logic:equal>
				<logic:equal name="seleccionarActividadForm" property="panelSeleccionado" value="panelIniciativas">
					mostrarPanelIniciativas();
				</logic:equal>
				<logic:equal name="seleccionarActividadForm" property="panelSeleccionado" value="panelActividades">
					mostrarPanelActividades();
				</logic:equal>
				
				// Invoca las funciones que hacen el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarActividadForm, 'tablaActividades');
				<logic:equal name="seleccionarActividadForm" property="permitirCambiarPlan" value="true">
					actualizarSeleccionadosV2(seleccionarActividadForm.planSeleccionadoId, 'tablaPlanes');
				</logic:equal>				

			</script>
		</html:form>
	</tiles:put>
</tiles:insert>
