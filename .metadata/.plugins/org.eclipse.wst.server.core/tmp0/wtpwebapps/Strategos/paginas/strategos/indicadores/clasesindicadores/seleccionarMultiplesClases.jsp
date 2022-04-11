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
		<script type="text/javascript">

			var _indiceClases;
			var _clasesSeleccionadas;
			var _separadorClases = '<bean:write name="seleccionarMultiplesClasesForm" property="separadorClases" ignore="true"/>';
			var _separadorCampos = '<bean:write name="seleccionarMultiplesClasesForm" property="separadorCampos" ignore="true"/>';
			var _cerrarForma = false;

			function loadIndicesObjeto(myForm, nombreObjeto) 
			{
				var indices = new Array();
				var len = myForm.elements.length;
				for (var index = 0; index < len; index++) 
				{
					if (myForm.elements[index].name == nombreObjeto) 
						indices.push(index);
				}

				return indices;
			}
			
			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?seleccionarClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}

			function abrirNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?abrirClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}

			function cerrarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?cerrarClaseId=' + nodoId + '&llamadaDesde=Clases' + marcadorAncla;
			}
			
			function seleccionarOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?seleccionarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}
			
			function abrirOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?abrirOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}
			
			function cerrarOrganizacion(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/indicadores/seleccionarMultiplesClases"/>?cerrarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
			}
			
			function agregar()
			{
				var myForm = self.document.seleccionarMultiplesClasesForm;
				
				_clasesSeleccionadas = "";
				for (var index = 0; index < _indiceClases.length; index++) 
				{
					if (myForm.elements[_indiceClases[index]].checked)
					{
						_clasesSeleccionadas = _clasesSeleccionadas + myForm.elements[_indiceClases[index]].value + _separadorClases;
						myForm.elements[_indiceClases[index]].checked = false;
					}
				}
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/seleccionarMultiplesClases" />?llamadaDesde=Agregar&clases=' + _clasesSeleccionadas, document.seleccionarMultiplesClasesForm.respuesta, 'onAgregar()');
			}
			
			function onAgregar()
			{
				var respuesta = document.seleccionarMultiplesClasesForm.respuesta.value.split(_separadorClases);
				if (respuesta.length > 0)
				{
					this.opener.document.<bean:write name="seleccionarMultiplesClasesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarMultiplesClasesForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarMultiplesClasesForm.respuesta.value;
					this.opener.document.<bean:write name="seleccionarMultiplesClasesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarMultiplesClasesForm" property="nombreCampoOculto" scope="session" />.value=_clasesSeleccionadas;
					<logic:notEmpty name="seleccionarMultiplesClasesForm" property="funcionCierre" scope="session">
						this.opener.<bean:write name="seleccionarMultiplesClasesForm" property="funcionCierre" scope="session" />;
					</logic:notEmpty>
				}
				if (_cerrarForma)
					this.close();
			}
			
			function seleccionar() 
			{
				_cerrarForma = true;
				agregar();
			}
			
			function mostrarPaneles(objeto) 
			{
				marcarBotonSeleccionado(objeto);
				if (objeto == 'organizacion')
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionar" nombrePanel="panelOrganizaciones" />
				else
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionar" nombrePanel="panelClases" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
				document.getElementById("barraSeleccionarIndicadoresBotonorganizacion").style.backgroundImage = "";
				document.getElementById("barraSeleccionarIndicadoresBotonclase").style.backgroundImage = "";
				document.getElementById("barraSeleccionarIndicadoresBotonapply").style.backgroundImage = "";
				document.getElementById("barraSeleccionarIndicadoresBotonselect").style.backgroundImage = "";
				if (document.getElementById('barraSeleccionarIndicadoresBoton' + boton) != null)
					document.getElementById('barraSeleccionarIndicadoresBoton' + boton).style.backgroundImage = 'url("<html:rewrite page='/paginas/strategos/imagenes/barraHerramientas/botonSeleccionado.gif'/>")';
				
				if (boton == 'organizacion')
				{
					document.getElementById("barraSeleccionarIndicadoresBotonapply").style.display = "none";
					document.getElementById("barraSeleccionarIndicadoresBotonselect").style.display = "none";
				}
				else
				{
					document.getElementById("barraSeleccionarIndicadoresBotonapply").style.display = "";
					document.getElementById("barraSeleccionarIndicadoresBotonselect").style.display = "";
				}
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/indicadores/seleccionarMultiplesClases" styleClass="formaHtmlCompleta">

			<html:hidden property="respuesta" />
			
			<%-- Atributos de la Forma --%>
			<vgcinterfaz:contenedorForma esSelector="true" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarclasesindicadores.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/indicadores/seleccionarMultiplesClases' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table class="tablaSpacing0Padding0Width100Collapse">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px">
								<b><vgcutil:message key="jsp.seleccionarindicadores.barraherramientas.organizacion" /></b>
							</td>
							<td>
								<bean:write name="seleccionarMultiplesClasesForm" property="rutaCompletaOrganizacion" />
							</td>
						</tr>
					</table>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIndicadores">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPaneles('organizacion');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="indicador" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="clase" onclick="javascript:mostrarPaneles('clase');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="apply" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="apply" onclick="javascript:agregar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionar.multiples.clases.panel.aplicar.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="select" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="select" onclick="javascript:seleccionar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionar.multiples.clases.panel.seleccionar.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:contenedorPaneles height="200" width="300" nombre="panelesSeleccionar" mostrarSelectoresPaneles="false">

					<%-- Panel: Organizaciones --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelOrganizaciones" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<treeview:treeview 
							useFrame="false" 
							arbolBean="seleccionarOrganizacionesArbolBean" 
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
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Indicadores (Por clases, iniciativas o planes) --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelClases" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<treeview:treeview 
							useFrame="false" 
							arbolBean="seleccionarClasesArbolBean" 
							scope="session" 
							isRoot="true" 
							fieldName="nombre" 
							fieldId="claseId" 
							fieldChildren="hijos" 
							lblUrlObjectId="claseId" 
							styleClass="treeview" 
							checkbox="true" 
							checkboxName="checkBoxClase"
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
			<script type="text/javascript">
				<%-- checkboxOnClick="javascript:loadClaseSeleccionada(self.document.seleccionarMultiplesClasesForm, this);" --%>
				_indiceClases = loadIndicesObjeto(self.document.seleccionarMultiplesClasesForm, 'checkBoxClase');
				<logic:equal name="seleccionarMultiplesClasesForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPaneles('organizacion');
				</logic:equal>
				<logic:equal name="seleccionarMultiplesClasesForm" property="panelSeleccionado" value="panelClases">
					mostrarPaneles('clase');
				</logic:equal>
			</script>
		</html:form>
	</tiles:put>

</tiles:insert>
