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
			var _botonAplicar = false; 
			var _aplicar = false;
			
			function mostrarPanelIndicadores(porObjeto) 
			{
				if (document.seleccionarIndicadoresForm.panelIndicadores.value != porObjeto) 
				{
					document.seleccionarIndicadoresForm.panelIndicadores.value = porObjeto;
					if (porObjeto == 'iniciativas') 
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresIniciativas"/>?panelIndicadores=iniciativas&panelSeleccionado=panelIndicadores';
					else if (porObjeto == 'clases') 
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?panelIndicadores=clases&panelSeleccionado=panelIndicadores';
					else if (porObjeto == 'planes') 
						window.location.href = '<html:rewrite action="/indicadores/seleccionarIndicadoresPlanes"/>?panelIndicadores=planes&panelSeleccionado=panelIndicadores';
					else 
						alert('Opción No Válida');
					return;
				}
				if (porObjeto == 'clases') 
					marcarBotonSeleccionado('clase');
				else if (porObjeto == 'iniciativas') 
					marcarBotonSeleccionado('iniciativa');
				else if (porObjeto == 'planes') 
					marcarBotonSeleccionado('plan');
				else 
					alert('Opción No Válida');
				document.seleccionarIndicadoresForm.panelSeleccionado.value='panelIndicadores';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIndicadores" nombrePanel="panelIndicadores" />
			}
			
			function marcarBotonSeleccionado(boton) 
			{
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
			
			function mostrarPanelOrganizaciones() 
			{
				marcarBotonSeleccionado('organizacion');
				document.seleccionarIndicadoresForm.panelSeleccionado.value='panelOrganizaciones';
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesSeleccionarIndicadores" nombrePanel="panelOrganizaciones" />
			}
			
			function quitarChequearIndicador() 
			{
				for (var i = 0; i < document.seleccionarIndicadoresForm.elements.length; i++) 
				{
					if ((document.seleccionarIndicadoresForm.elements[i].type=="checkbox") && (document.seleccionarIndicadoresForm.elements[i].checked))  
						document.seleccionarIndicadoresForm.elements[i].checked = false;
				}
				arregloIndicadores = new Array();
			}
			
			function getRutaCompletaIndicadoresSeleccionados() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/seleccionarIndicadores" />?funcion=getRutaCompletaIndicadoresSeleccionados&seleccionados=' + document.seleccionarIndicadoresForm.seleccionados.value, document.seleccionarIndicadoresForm.rutaCompletaSeleccionados, 'finalizarSeleccion()');
			}

			function chequearIndicador(obj) 
			{
				arregloIndicadores = new Array();
				var n = 0;
				for (var i = 0; i < document.seleccionarIndicadoresForm.elements.length; i++) 
				{
					if ((document.seleccionarIndicadoresForm.elements[i].type=="checkbox") && (document.seleccionarIndicadoresForm.elements[i].checked))  
					{
						arregloIndicadores[n] = document.seleccionarIndicadoresForm.elements[i].value;
						n++;
					}
				}
			}
			
			function chequearSerie(obj) 
			{
				arregloIndicadores = new Array();
				var n = 0;
				for (var i = 0; i < document.seleccionarIndicadoresForm.elements.length; i++) 
				{
					if ((document.seleccionarIndicadoresForm.elements[i].type=="checkbox") && (document.seleccionarIndicadoresForm.elements[i].checked))  
					{
						arregloIndicadores[n] = document.seleccionarIndicadoresForm.elements[i].value;
						n++;
					}
				}
				<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
					seleccionar();
				</logic:equal>
			}
			
			function finalizarSeleccion() 
			{
				if (document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value == '<bean:write name="seleccionarIndicadoresForm" property="codigoIndicadorEliminado" scope="session" />') 
					alert('<vgcutil:message key="jsp.seleccionarindicadores.indicadoreliminado" />');
				else if (document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value.indexOf('<bean:write name="seleccionarIndicadoresForm" property="codigoIndicadorEliminado" scope="session" />') > -1) 
					alert('<vgcutil:message key="jsp.seleccionarindicadores.indicadoreseliminados" />');
				<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session" />.value=document.seleccionarIndicadoresForm.rutaCompletaSeleccionados.value;
				</logic:notEmpty>								
				<logic:notEmpty name="seleccionarIndicadoresForm" property="funcionCierre" scope="session">					
					this.opener.<bean:write name="seleccionarIndicadoresForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				if (!_botonAplicar){
					if(!_aplicar)
						this.close();
					else
						
						quitarChequearIndicador();
				}
			}
			
			function seleccionar(aplicar, cerrar) 
			{
				if (aplicar == undefined)
					aplicar = false;
				if (cerrar == undefined)
					cerrar = false;
				
				_botonAplicar = aplicar;
				_aplicar = cerrar;
				var separadorSeries = '<bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" />';
				var separadorIndicadores = '<bean:write name="seleccionarIndicadoresForm" property="separadorIndicadores" scope="session" />';

				// Cuando se muestran las series de tiempo se devuelven los indicadores con sus series de tiempo
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
					var valorCampoIds = "";
					var valorCampoValores = "";
					var valorPlanIds = "";
					for (var i=0; i < arregloIndicadores.length; i++) 
					{
						var texto = arregloIndicadores[i];
						var partes = texto.split(separadorSeries);
						var planId = '';
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
							planId = '<bean:write name="seleccionarIndicadoresForm" property="planId" scope="session" />';
						</logic:equal>
						valorCampoIds = valorCampoIds + partes[0] + separadorSeries + partes[1] + separadorIndicadores;
						valorCampoValores = valorCampoValores + partes[2] + separadorSeries + partes[3]  + separadorIndicadores;
						valorPlanIds = valorPlanIds + partes[0] + separadorSeries + planId + separadorIndicadores; 
					}
					valorCampoIds = valorCampoIds.substring(0, valorCampoIds.length - separadorIndicadores.length);
					valorCampoValores = valorCampoValores.substring(0, valorCampoValores.length - separadorIndicadores.length);
					valorPlanIds = valorPlanIds.substring(0, valorPlanIds.length - separadorIndicadores.length);
					<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session">
						this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session" />.value=valorCampoValores;
					</logic:notEmpty>
					document.seleccionarIndicadoresForm.valoresSeleccionados.value=valorCampoValores;
					<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session">
						this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session" />.value = valorCampoIds;
					</logic:notEmpty>
					document.seleccionarIndicadoresForm.seleccionados.value=valorCampoIds;
					<logic:notEmpty name="seleccionarIndicadoresForm" property="seleccionadosPlanId" scope="session">
						this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="seleccionadosPlanId" scope="session" />.value = valorPlanIds;
						document.seleccionarIndicadoresForm.seleccionadosPlanId.value=valorPlanIds;
					</logic:notEmpty>
				</logic:equal>

				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="true">
						var valorCampoIds = "";
						var valorCampoValores = "";
						var valorPlanIds = "";
						for (var i=0; i < arregloIndicadores.length; i++) 
						{
							var texto = arregloIndicadores[i];
							var partes = texto.split(separadorSeries);
							var planId = '';
							<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
								planId = '<bean:write name="seleccionarIndicadoresForm" property="planId" scope="session" />';
							</logic:equal>
							valorCampoIds = valorCampoIds + partes[0] + separadorIndicadores;
							valorCampoValores = valorCampoValores + partes[1] + separadorIndicadores;
							valorPlanIds = valorPlanIds + partes[0] + separadorSeries + planId + separadorIndicadores; 
						}
						valorCampoIds = valorCampoIds.substring(0, valorCampoIds.length - separadorIndicadores.length);
						valorCampoValores = valorCampoValores.substring(0, valorCampoValores.length - separadorIndicadores.length);
						valorPlanIds = valorPlanIds.substring(0, valorPlanIds.length - separadorIndicadores.length);
						<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session">
							this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session" />.value=valorCampoValores;
						</logic:notEmpty>
						document.seleccionarIndicadoresForm.valoresSeleccionados.value=valorCampoValores;
						<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session">
							this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session" />.value = valorCampoIds;
						</logic:notEmpty>
						document.seleccionarIndicadoresForm.seleccionados.value=valorCampoIds;
						<logic:notEmpty name="seleccionarIndicadoresForm" property="seleccionadosPlanId" scope="session">
							this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="seleccionadosPlanId" scope="session" />.value = valorPlanIds;
							document.seleccionarIndicadoresForm.seleccionadosPlanId.value=valorPlanIds;
						</logic:notEmpty>
					</logic:equal>
				</logic:equal>
				
				if ((document.seleccionarIndicadoresForm.seleccionados.value == null) || (document.seleccionarIndicadoresForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionarindicadores.noseleccion" />');
					return;
				}
				
				// Cuando no se muestran las series de tiempo solo se devuelve 'nombreIndicador' y 'indicadorId'
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
							document.seleccionarIndicadoresForm.seleccionados.value = document.seleccionarIndicadoresForm.seleccionados.value + '&planId=<bean:write name="seleccionarIndicadoresForm" property="planId" scope="session" />';
						</logic:equal>
						<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session">
							this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarIndicadoresForm.valoresSeleccionados.value;
						</logic:notEmpty>
						<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session">
							this.opener.document.<bean:write name="seleccionarIndicadoresForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadoresForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarIndicadoresForm.seleccionados.value;
						</logic:notEmpty>
					</logic:equal>
				</logic:equal>
				// Se llama a la función que obtiene la ruta completa de los indicadores seleccionados
				<logic:notEmpty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					getRutaCompletaIndicadoresSeleccionados();
				</logic:notEmpty>
				<logic:empty name="seleccionarIndicadoresForm" property="nombreCampoRutasCompletas" scope="session">
					finalizarSeleccion();
				</logic:empty>
			}			
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/indicadores/seleccionarIndicadores" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<input type="hidden" name="rutaCompletaSeleccionados" value="" />
			<input type="hidden" name="actualizar" value="true" />

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="nombreCampoRutasCompletas" />
			<html:hidden property="panelSeleccionado" />
			<html:hidden property="panelIndicadores" />
			<html:hidden property="seleccionadosPlanId" />

			<vgcinterfaz:contenedorForma esSelector="true" scrolling="none">

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

				 	<div style="width:100%; height:40px; overflow:auto;">
						<table class="bordeFichaDatostabla" style="border-collapse: collapse; height: 40px">
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
					</div>

					<%-- Tool Bar --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIndicadores">
						<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarOrganizacion" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="organizacion" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="organizacion" onclick="javascript:mostrarPanelOrganizaciones();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.seleccionarindicadores.panel.organizaciones.titulo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="indicador" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="clase" onclick="javascript:mostrarPanelIndicadores('clases');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:equal name="seleccionarIndicadoresForm" property="permitirIniciativas" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="iniciativa" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="iniciativa" onclick="javascript:mostrarPanelIndicadores('iniciativas');">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<logic:empty scope="session" name="activarIniciativa">
										<vgcutil:message key="barraareas.strategos.iniciativas" />
									</logic:empty>
									<logic:notEmpty scope="session" name="activarIniciativa">
										<logic:notEmpty scope="session" name="activarIniciativa" property="objeto">
											<logic:equal scope="session" name="activarIniciativa" property="objeto.activo" value="true">
												<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
											</logic:equal>
										</logic:notEmpty>
									</logic:notEmpty>
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="seleccionarIndicadoresForm" property="permitirPlanes" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="plan" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="plan" onclick="javascript:mostrarPanelIndicadores('planes');">
								<vgcinterfaz:barraHerramientasBotonTitulo><vgcutil:message key="jsp.seleccionariniciativas.panel.planes.titulo" /></vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="452" width="774" nombre="panelesSeleccionarIndicadores" mostrarSelectoresPaneles="false">

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
							<vgcinterfaz:barraHerramientas nombre="barraSeleccionarClasesIndicadoress">
								<vgcinterfaz:barraHerramientasBoton nombreImagen="visible" pathImagenes="/componentes/barraHerramientas/" nombre="visible" onclick="javascript:mostrarClases();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<logic:equal scope="session" name="claseVisible" value="true">
											<vgcutil:message key="menu.archivo.visible" />
										</logic:equal>
										<logic:equal scope="session" name="claseVisible" value="false">
											<vgcutil:message key="menu.archivo.ocultar" />
										</logic:equal>
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
								<vgcinterfaz:barraHerramientasSeparador />
							</vgcinterfaz:barraHerramientas>
							<div style="width:100%; height:412px" id="panelContenedorClases">
								<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorClasesIndicadores.jsp"></jsp:include>
							</div>
						</logic:equal>
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="iniciativas">
							<div id="panelContenedorBarraIniciativas" class="barraFiltrosForma" style="width:100%; height:30px">
								&nbsp;
							</div>
							<div style="width:100%; height:412px" id="panelContenedorIniciativas">
								<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorIniciativas.jsp"></jsp:include>
							</div>
						</logic:equal>
						<logic:equal name="seleccionarIndicadoresForm" property="panelIndicadores" value="planes">
							<div id="panelContenedorBarraPlanes" class="barraFiltrosForma" style="width:100%; height:30px">
								&nbsp;
							</div>
							<div style="width:100%; height:412px" id="panelContenedorPlanes">
								<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresExploradorPlanes.jsp"></jsp:include>
							</div>
						</logic:equal>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>
				
				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="true">
						<img src="<html:rewrite page='/componentes/formulario/acciones.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.aplicar.alt" />">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar(false, true);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aplicar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:equal>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.seleccionar.alt" />">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar(false, false);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.seleccionar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script type="text/javascript">
				<logic:equal name="seleccionarIndicadoresForm" property="panelSeleccionado" value="panelOrganizaciones">
					mostrarPanelOrganizaciones();
				</logic:equal>
				<logic:equal name="seleccionarIndicadoresForm" property="panelSeleccionado" value="panelIndicadores">
					mostrarPanelIndicadores(document.seleccionarIndicadoresForm.panelIndicadores.value);
				</logic:equal>

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarIndicadoresForm, 'tablaIndicadores');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
