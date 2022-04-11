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
		<vgcutil:message key="jsp.seleccionarindicadores.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">	

			var arregloIndicadores = new Array();

			function chequearSerie(obj) {
				arregloIndicadores = new Array();
				n = 0;
				for (i = 0; i < document.seleccionarIndicadoresForm.elements.length; i++) {
					if ((document.seleccionarIndicadoresForm.elements[i].type=="checkbox") && (document.seleccionarIndicadoresForm.elements[i].checked))  {
						arregloIndicadores[n] = document.seleccionarIndicadoresForm.elements[i].value;
						n++;
					}
				}
				<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
					seleccionar();
				</logic:equal>
			}
			
			function seleccionar() {
				// Cuando se muestran las series de tiempo se devuelven los indicadores con sus series de tiempo
				// <logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
					var valorCampoIds = "";
					var valorCampoValores = "";
					for (i=0; i < arregloIndicadores.length; i++) {
						var texto = arregloIndicadores[i];
						var partes = texto.split('<bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" />');
						var planId = '';
						// <logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
						planId = 'planId<bean:write name="seleccionarIndicadoresForm" property="planId" scope="session" />';
						// </logic:equal>
						valorCampoIds = valorCampoIds + partes[0] + planId + '<bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" />' + partes[1] + '<bean:write name="seleccionarIndicadoresForm" property="separadorIndicadores" scope="session" />';
						valorCampoValores = valorCampoValores + partes[2] + '<bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" />' + partes[3]  + '<bean:write name="seleccionarIndicadoresForm" property="separadorIndicadores" scope="session" />';
					}
					valorCampoIds = valorCampoIds.substring(0, valorCampoIds.length - '<bean:write name="seleccionarIndicadoresForm" property="separadorIndicadores" scope="session" />'.length);
					valorCampoValores = valorCampoValores.substring(0, valorCampoValores.length - '<bean:write name="seleccionarIndicadoresForm" property="separadorIndicadores" scope="session" />'.length);
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session" />.value=valorCampoValores;
					document.seleccionarIndicadoresForm.valoresSeleccionados.value=valorCampoValores;
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session" />.value = valorCampoIds;
					document.seleccionarIndicadoresForm.seleccionados.value=valorCampoIds;
				// </logic:equal>
				if ((document.seleccionarIndicadoresForm.seleccionados.value == null) || (document.seleccionarIndicadoresForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionarindicadores.noseleccion" />');
					return;
				}
				// Cuando no se muestran las series de tiempo solo se devuelve 'nombreIndicador' y 'indicadorId'
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
					// <logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
					document.seleccionarIndicadoresForm.seleccionados.value = document.seleccionarIndicadoresForm.seleccionados.value + 'planId<bean:write name="seleccionarIndicadoresForm" property="planId" scope="session" />';
					// </logic:equal>
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarIndicadoresForm.valoresSeleccionados.value;
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarIndicadoresForm.seleccionados.value;
				</logic:equal>
				// Se llama a la función que obtiene la ruta completa de los indicadores seleccionados
				<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					getRutaCompletaIndicadoresSeleccionados();
				</logic:notEmpty>			
				<logic:empty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					finalizarSeleccion();
				</logic:empty>
			}			

			function finalizarSeleccion() {
				if (document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value == '<bean:write name="seleccionarIndicadoresForm" property="codigoIndicadorEliminado" scope="session" />') {
					alert('<vgcutil:message key="jsp.seleccionarindicadores.indicadoreliminado" />');
				} else if (document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value.indexOf('<bean:write name="seleccionarIndicadoresForm" property="codigoIndicadorEliminado" scope="session" />') > -1) {
					alert('<vgcutil:message key="jsp.seleccionarindicadores.indicadoreseliminados" />');
				}				
				<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session" />.value=document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value;
				</logic:notEmpty>								
				<logic:notEmpty name="seleccionarIndicadoresForm" property="funcionCierre" scope="session">					
					this.opener.<bean:write name="seleccionarIndicadoresForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}
			
			function getRutaCompletaIndicadoresSeleccionados() {
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/seleccionarIndicadores" />?funcion=getRutaCompletaIndicadoresSeleccionados&seleccionados=' + document.seleccionarIndicadoresForm.seleccionados.value, document.seleccionarIndicadoresForm.rutaCompletaSeleccionados, 'finalizarSeleccion()');
			}

			function mostrarPanelOrganizaciones() {
				marcarBotonSeleccionado('organizacion');
				document.seleccionarIndicadoresForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIndicadores" nombrePanel="panelOrganizaciones" />
			}

			function mostrarPanelIndicadoresClases(porObjeto) {
				if (document.seleccionarIndicadoresForm.panelIndicadores.value != porObjeto) {
					document.seleccionarIndicadoresForm.panelIndicadores.value = porObjeto;
					if (porObjeto == 'iniciativas') {
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresIniciativas"/>?panelIndicadores=iniciativas&panelSeleccionado=panelIndicadores';
					} else if (porObjeto == 'clases') {
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?panelIndicadores=clases&panelSeleccionado=panelIndicadores';
					} else if (porObjeto == 'planes') {
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresPlanes"/>?panelIndicadores=planes&panelSeleccionado=panelIndicadores';
					} else {
						alert('Opción No Válida');
					}
					return;
				}
				if (porObjeto == 'clases') {
					marcarBotonSeleccionado('clase');
				} else if (porObjeto == 'iniciativas') {
					marcarBotonSeleccionado('iniciativa');
				} else if (porObjeto == 'planes') {
					marcarBotonSeleccionado('plan');
				} else {
					alert('Opción No Válida');
				}
				document.seleccionarIndicadoresForm.panelSeleccionado.value='panelIndicadores';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIndicadores" nombrePanel="panelIndicadores" />
			}

			function marcarBotonSeleccionado(boton) {
			<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
				document.getElementById("barraSeleccionarIndicadoresBotonorganizacion").style.backgroundImage = "";
			</logic:equal>
				document.getElementById("barraSeleccionarIndicadoresBotonclase").style.backgroundImage = "";
			<logic:equal name="seleccionarIndicadoresForm" property="permitirIniciativas" value="true">
				document.getElementById("barraSeleccionarIndicadoresBotoniniciativa").style.backgroundImage = "";
				document.getElementById("barraSeleccionarIndicadoresBotonplan").style.backgroundImage = "";
			</logic:equal>			
				document.getElementById('barraSeleccionarIndicadoresBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/indicadores/seleccionarIndicadores" styleClass="formaHtml">

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
			<html:hidden property="panelIndicadores" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarindicadores.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarIndicadoresForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarindicadores.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarIndicadoresForm" property="rutaCompletaOrganizacion" /></td>
						</tr>

						<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarClase" value="true">
							<%-- Clase de Indicadores --%>
							<tr class="barraFiltrosForma">
								<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarindicadores.barraherramientas.clase" /></b></td>
								<td><bean:write name="seleccionarIndicadoresForm" property="rutaCompletaClaseIndicadores" /></td>
							</tr>
						</logic:equal>

					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIndicadores">
						<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="indicador" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="clase" onclick="javascript:mostrarPanelIndicadoresClases('clases');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="seleccionarIndicadoresForm" property="permitirIniciativas" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="iniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="iniciativa" onclick="javascript:mostrarPanelIndicadoresClases('iniciativas');">
								<vgcinterfaz:barraHerramientasBotonTitulo>Iniciativas</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelIndicadoresClases('planes');">
							<vgcinterfaz:barraHerramientasBotonTitulo>Planes</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="420" width="770" nombre="panelesSeleccionarIndicadores" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
							<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresOrganizaciones.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Indicadores (Por clases, iniciativas o planes) --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelIndicadores" mostrarBorde="false">
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="clases">
							<vgcinterfaz:panelContenedorTitulo>
								<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
							</vgcinterfaz:panelContenedorTitulo>
							<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorClasesIndicadores.jsp"></jsp:include>
						</logic:equal>
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="iniciativas">
							<vgcinterfaz:panelContenedorTitulo>Iniciativas</vgcinterfaz:panelContenedorTitulo>
							<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorIniciativas.jsp"></jsp:include>
						</logic:equal>
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
							<vgcinterfaz:panelContenedorTitulo>Planes</vgcinterfaz:panelContenedorTitulo>
							<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorPlanes.jsp"></jsp:include>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">
				<logic:equal name="seleccionarIndicadoresForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarIndicadoresForm" property="panelSeleccionado" value="panelIndicadores">
				mostrarPanelIndicadoresClases(document.seleccionarIndicadoresForm.panelIndicadores.value);
				</logic:equal>

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarIndicadoresForm, 'tablaIndicadores');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
