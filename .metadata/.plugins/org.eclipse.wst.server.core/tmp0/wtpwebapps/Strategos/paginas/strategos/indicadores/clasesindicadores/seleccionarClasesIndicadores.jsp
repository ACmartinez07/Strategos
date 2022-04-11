<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>


<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (17/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarclasesindicadores.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/clasesindicadores/seleccionarClasesIndicadores"/>?seleccionarClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}

			function abrirNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/clasesindicadores/seleccionarClasesIndicadores"/>?abrirClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}

			function cerrarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/clasesindicadores/seleccionarClasesIndicadores"/>?cerrarClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}
			
			function seleccionarOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarClasesOrganizaciones"/>?seleccionarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}
			
			function abrirOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarClasesOrganizaciones"/>?abrirOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}
			
			function cerrarOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarClasesOrganizaciones"/>?cerrarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}

			function seleccionar() 
			{
				this.opener.document.<bean:write name="seleccionarClasesIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarClasesIndicadoresForm" property="nombreCampoValor" scope="session" />.value='<bean:write name="seleccionarClasesIndicadoresArbolBean" scope="session" property="nodoSeleccionado.nombre"/>';
				this.opener.document.<bean:write name="seleccionarClasesIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarClasesIndicadoresForm" property="nombreCampoOculto" scope="session" />.value='<bean:write name="seleccionarClasesIndicadoresArbolBean" scope="session" property="nodoSeleccionado.claseId"/>';
				<logic:notEmpty name="seleccionarClasesIndicadoresForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarClasesIndicadoresForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}
			
			function mostrarPanelOrganizaciones() 
			{
				marcarBotonSeleccionado('organizacion');
				document.seleccionarClasesIndicadoresForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionar" nombrePanel="panelOrganizaciones" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
				<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
					document.getElementById("barraSeleccionarIndicadoresBotonorganizacion").style.backgroundImage = "";
				</logic:equal>
				<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarClase" value="true">
					document.getElementById("barraSeleccionarIndicadoresBotonclase").style.backgroundImage = "";
				</logic:equal>
				if (document.getElementById('barraSeleccionarIndicadoresBoton' + boton) != null)
					document.getElementById('barraSeleccionarIndicadoresBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';
			}
			
			function mostrarPanelClases(porObjeto) 
			{
				document.seleccionarClasesIndicadoresForm.panelClases.value = porObjeto;
				marcarBotonSeleccionado('clase');
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionar" nombrePanel="panelClases" />
			}
		
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/indicadores/clasesindicadores/seleccionarClasesIndicadoresInterno" styleClass="formaHtmlCompleta">
			<html:hidden property="panelClases" />
			<html:hidden property="panelSeleccionado" />

			<%-- Atributos de la Forma --%>
			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarclasesindicadores.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/indicadores/clasesindicadores/seleccionarClasesIndicadoresInterno' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px">
								<b><vgcutil:message key="jsp.seleccionarindicadores.barraherramientas.organizacion" /></b>
							</td>
							<td>
								<bean:write name="seleccionarClasesIndicadoresForm" property="rutaCompletaOrganizacion" />
							</td>
						</tr>

						<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarClase" value="true">
							<%-- Clase de Indicadores --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarindicadores.barraherramientas.clase" /></b></td>
								<td><bean:write name="seleccionarClasesIndicadoresForm" property="rutaCompletaClaseIndicadores" /></td>
							</tr>
						</logic:equal>

					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIndicadores">
						<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarClase" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="indicador" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="clase" onclick="javascript:mostrarPanelClases('clases');">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:contenedorPaneles height="200" width="300" nombre="panelesSeleccionar" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarClasesIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
							<treeview:treeview 
								useFrame="false" 
								arbolBean="seleccionarIndicadoresArbolOrganizacionesBean" 
								scope="session" 
								isRoot="true" 
								fieldName="nombre" 
								fieldId="organizacionId" 
								fieldChildren="hijos"
								lblUrlObjectId="organizacionId" 
								styleClass="treeview" 
								checkbox="false" 
								pathImages="/componentes/visorArbol/" 
								urlJs="/componentes/visorArbol/visorArbol.js"
								urlName="javascript:seleccionarOrganizacion(organizacionId, marcadorAncla);" 
								urlConnectorOpen="javascript:abrirOrganizacion(organizacionId, marcadorAncla);"
								urlConnectorClose="javascript:cerrarOrganizacion(organizacionId, marcadorAncla);" 
								lblUrlAnchor="marcadorAncla" />
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Indicadores (Por clases, iniciativas o planes) --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelClases" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<treeview:treeview 
							useFrame="false" 
							arbolBean="seleccionarClasesIndicadoresArbolBean" 
							scope="session" 
							isRoot="true" 
							fieldName="nombre" 
							fieldId="claseId" 
							fieldChildren="hijos" 
							lblUrlObjectId="claseId" 
							styleClass="treeview" 
							checkbox="false" 
							pathImages="/componentes/visorArbol/" 
							urlJs="/componentes/visorArbol/visorArbol.js" 
							nameSelectedId="claseId" 
							urlName="javascript:seleccionarNodo(claseId, marcadorAncla);" 
							urlConnectorOpen="javascript:abrirNodo(claseId, marcadorAncla);" 
							urlConnectorClose="javascript:cerrarNodo(claseId, marcadorAncla);" 
							lblUrlAnchor="marcadorAncla" />
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">
				<logic:equal name="seleccionarClasesIndicadoresForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarClasesIndicadoresForm" property="panelSeleccionado" value="panelClases">
					mostrarPanelClases('Clases');
				</logic:equal>
			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
