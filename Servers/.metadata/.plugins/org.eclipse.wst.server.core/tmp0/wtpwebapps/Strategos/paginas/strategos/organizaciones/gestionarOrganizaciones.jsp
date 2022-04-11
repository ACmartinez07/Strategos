<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/11/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarorganizaciones.titulo" /> [<bean:write name="organizacion" property="nombre" scope="session" />]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var nodoSeleccionado = "";
			var altoRestante = 224;
			
			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones"/>?mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />' + '&selectedOrgId=' + nodoId + marcadorAncla;
			}
			
			function openNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones"/>?mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />' + '&openOrgId=' + nodoId + marcadorAncla;
			}
			
			function closeNodo(nodoId, marcadorAncla) 
			{					
				window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones"/>?mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />' + '&closeOrgId=' + nodoId + marcadorAncla;
			}
			
			function nuevo() 
			{				
				abrirVentanaModal('<html:rewrite action="/organizaciones/crearOrganizacion"/>', "OrganizacionAdd", 950, 600);
			}
			
			function modificar() 
			{
				<logic:equal name="gestionarOrganizacionesForm" property="editarForma" value="true">					
					abrirVentanaModal('<html:rewrite action="/organizaciones/modificarOrganizacion"/>?organizacionId=<bean:write name="organizacion" property="organizacionId" scope="session" />', "OrganizacionEdit", 950, 600);
				</logic:equal>
				<logic:notEqual name="gestionarOrganizacionesForm" property="editarForma" value="true">
					<logic:equal name="gestionarOrganizacionesForm" property="verForma" value="true">						
						abrirVentanaModal('<html:rewrite action="/organizaciones/verOrganizacion"/>?organizacionId=<bean:write name="organizacion" property="organizacionId" scope="session" />', "OrganizacionEdit", 950, 600);
					</logic:equal>
				</logic:notEqual>
			}
			
			function eliminar() 
			{
			    var nombre = '<bean:write name="organizacion" property="nombre" scope="session" />';
			    var hijos = '<bean:write scope="session" name="hijos"/>';
			    var respuesta = "";
			    if (hijos == 'true')
					respuesta = confirm('<vgcutil:message key="jsp.gestionarorganizaciones.eliminarorganizacion.hijos.confirmar" arg0="' + nombre + '" />');
			    else
			    	respuesta = confirm('<vgcutil:message key="jsp.gestionarorganizaciones.eliminarorganizacion.confirmar" arg0="' + nombre + '" />');
				if (respuesta)
				{
					activarBloqueoEspera();
					window.location.href='<html:rewrite action="/organizaciones/eliminarOrganizacion" />?organizacionId=<bean:write name="organizacion" property="organizacionId"/>' + '&ts=<%= (new Date()).getTime() %>';
				}
			}

			function reporte() 
			{
				abrirReporte('<html:rewrite action="/organizaciones/generarReporteOrganizaciones.action"/>', 'reporte');
			}
			
			function propiedadesOrganizacion() 
			{
				abrirVentanaModal('<html:rewrite action="/organizaciones/propiedadesOrganizacion" />?organizacionId=<bean:write name="organizacion" property="organizacionId" scope="session" />', "Organizacion", 490, 500);
			}
			
			function gestionarIndicadoresResponsable() 
			{
				window.location.href='<html:rewrite action="/organizaciones/gestionarIndicadores" />?organizacionId=<bean:write name="organizacion" property="organizacionId" scope="session" />';
			}
			
			function gestionarIniciativasResponsable() 
			{
				window.location.href='<html:rewrite action="/organizaciones/gestionarIniciativas" />?organizacionId=<bean:write name="organizacion" property="organizacionId" scope="session" />';
			}
			
			function regrescarOrganizacion(padreId)
			{
				window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones"/>?mostrarMisionVision=' + '<bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />' + '&selectedOrgId=' + padreId;
			}
			
			function setAnchoPanel()
			{
				if (startHorizontal)
					setAncho();
			}
			
			function setAncho(anchoForma)
			{
				if (typeof(anchoForma) == 'undefined')
					anchoForma = (_myWidth - _anchoAreBar);
				var mostrarMisionVision = <bean:write name="gestionarOrganizacionesForm" property="mostrarMisionVision" />;
				if (mostrarMisionVision)
				{
					resizeAlto(document.getElementById('body-contenedorMisionVision'), (altoRestante / 2));
					var split = document.getElementById('splitOtganizacionPanelIzquierdo');
					var anchoContenedorMisionVision = 450;
					if (split != null)
					{
						var width;
						if (typeof(split.style.width) != 'undefined' && split.style.width.indexOf("%") == -1)
						{
							anchoContenedorMisionVision = split.style.width.replace("px", "");
							var ancho = parseInt(anchoForma) - parseInt(anchoContenedorMisionVision);
							if (ancho < 80)
							{
								anchoForma = (_myWidth - _anchoAreBar);
								anchoContenedorMisionVision = (anchoForma - 80);
								split.style.width = anchoContenedorMisionVision + "px";
								ancho = parseInt(anchoForma) - parseInt(anchoContenedorMisionVision);
							}
							document.getElementById('body-contenedorMisionVision').style.width = ancho + "px";
							width = (anchoContenedorMisionVision);
						}
						else
						{
							document.getElementById('body-contenedorMisionVision').style.width = anchoContenedorMisionVision + "px";
							width = (anchoForma - anchoContenedorMisionVision);
							split.style.width = width + "px";
						}
						anchoForma = width - 10;
					}
				}
				
				document.getElementById('body-organizaciones').style.width = anchoForma + "px";
			}

		</script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuVerJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/gestionarOrganizaciones" styleClass="formaHtmlCompleta">
			<html:hidden property="graficoSeleccionadoId" />
			<html:hidden property="respuesta" />
			<html:hidden property="editarForma" />
			<html:hidden property="verForma" />
			
			<%-- Se determina el ancho del split --%>
			<bean:define id="anchoSplit" value="70%" />
			<logic:equal name="gestionarOrganizacionesForm" property="mostrarMisionVision" value="true">
				<bean:define id="anchoSplit" value="70%" />
			</logic:equal>
			
			<%-- Split --%>
			<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%=anchoSplit%>" splitterId="splitOtganizacion" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

				<%-- Panel Izquierdo: Gestionar Organizaciones --%>
				<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitOtganizacion">

					<vgcinterfaz:contenedorForma idContenedor="body-organizaciones" idHtml="contenedorOrganizaciones" >

						<%-- Título --%>
						<vgcinterfaz:contenedorFormaTitulo>
							<vgcutil:message key="jsp.gestionarorganizaciones.titulo" />
						</vgcinterfaz:contenedorFormaTitulo>

						<%-- Menú --%>
						<vgcinterfaz:contenedorFormaBarraMenus>
							<jsp:include flush="true" page="/paginas/strategos/organizaciones/menuOrganizaciones.jsp"></jsp:include>
						</vgcinterfaz:contenedorFormaBarraMenus>

						<%-- Barra Genérica --%>
						<vgcinterfaz:contenedorFormaBarraGenerica height="20px" >

							<%-- Barra de Herramientas --%>
							<vgcinterfaz:barraHerramientas nombre="barraGestionarOrganizaciones">

								<vgcinterfaz:barraHerramientasBoton permisoId="ORGANIZACION_ADD" aplicaOrganizacion="true" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.nuevo" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
								<logic:equal name="gestionarOrganizacionesForm" property="editarForma" value="true">
									<vgcinterfaz:barraHerramientasBoton aplicaOrganizacion="true" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
										<vgcinterfaz:barraHerramientasBotonTitulo>
											<vgcutil:message key="menu.edicion.modificar" />
										</vgcinterfaz:barraHerramientasBotonTitulo>
									</vgcinterfaz:barraHerramientasBoton>
								</logic:equal>
								<logic:notEqual name="gestionarOrganizacionesForm" property="editarForma" value="true">
									<logic:equal name="gestionarOrganizacionesForm" property="verForma" value="true">
										<vgcinterfaz:barraHerramientasBoton aplicaOrganizacion="true" nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
											<vgcinterfaz:barraHerramientasBotonTitulo>
												<vgcutil:message key="menu.edicion.modificar" />
											</vgcinterfaz:barraHerramientasBotonTitulo>
										</vgcinterfaz:barraHerramientasBoton>
									</logic:equal>
								</logic:notEqual>
								<vgcinterfaz:barraHerramientasBoton permisoId="ORGANIZACION_DELETE" aplicaOrganizacion="true" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.eliminar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
								<vgcinterfaz:barraHerramientasSeparador />
								<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION_CALCULAR" nombreImagen="calculo" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="calcular" onclick="javascript:calcular();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.calcular" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
								<vgcinterfaz:barraHerramientasSeparador />
								<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedadesOrganizacion();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.propiedades" />
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
						
											
						<%-- Este es el "Visor Tipo Arbol" --%>
						
						<treeview:treeview 
										useFrame="false" 
										name="arbolOrganizaciones" 
										scope="session" 
										baseObject="organizacionRoot" 
										isRoot="true" 
										fieldName="Nombre" 
										fieldId="organizacionId" 
										fieldChildren="hijos" 
										lblUrlObjectId="orgId" 
										styleClass="treeview" 
										checkbox="false" 
										pathImages="/componentes/visorArbol/" 
										urlJs="/componentes/visorArbol/visorArbol.js" 
										nameSelectedId="organizacionId" 
										urlName="javascript:seleccionarNodo(orgId, marcadorAncla);" 
										urlConnectorOpen="javascript:openNodo(orgId, marcadorAncla);" 
										urlConnectorClose="javascript:closeNodo(orgId, marcadorAncla);" 
										lblUrlAnchor="marcadorAncla" />	
						
						<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
						<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
						</vgcinterfaz:contenedorFormaBarraInferior>
					</vgcinterfaz:contenedorForma>
				</vgcinterfaz:splitterHorizontalPanelIzquierdo>

				<%-- Panel Derecho:  --%>
				<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitOtganizacion">
				
				
					<%-- Representación de la Forma --%>
					<vgcinterfaz:contenedorForma idContenedor="body-contenedor" height="200px" mostrarBarraSuperior="false" idHtml="contenedor">
						
						
						<%-- Menú --%>
						<vgcinterfaz:contenedorFormaBarraMenus>
							
						</vgcinterfaz:contenedorFormaBarraMenus>
						
						
						<table class="panelContenedor" cellspacing="0">

							<tr>
								<td>&nbsp;</td>
								<td><b><vgcutil:message key="organizaciones.gestion.responsabilidad" /></b></td>
							</tr>
									
							<tr>
								<td>&nbsp;</td>
							</tr>							
								
							<tr>
								<td>&nbsp;</td>
							</tr>	
							
							<tr>								
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/indicadores.gif'/>" border="0" width="25" height="25" onclick="javascript:gestionarIndicadoresResponsable();" style="cursor:pointer">&nbsp;&nbsp;<b></b></td>
								<td><vgcutil:message key="barraareas.strategos.indicadores" /></td>
							</tr>
							 
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/iniciativas.gif'/>" border="0" width="25" height="25" onclick="javascript:gestionarIniciativasResponsable();" style="cursor:pointer">&nbsp;&nbsp;<b></b></td>
								<td><vgcutil:message key="iniciativas.gestionariniciativas.fijo.responsabilidad" /></td>
							</tr>
							
						</table>
						
						
					</vgcinterfaz:contenedorForma>
					
					
					<logic:equal name="gestionarOrganizacionesForm" property="mostrarMisionVision" value="true">
					
				
					
					<%-- Representación de la Forma --%>
					<vgcinterfaz:contenedorForma idContenedor="body-contenedorMisionVision" mostrarBarraSuperior="false" idHtml="contenedorMisionVision">
						
										
						
						<table class="bordeFichaDatos" id="table-contenedorMisionVision" style="padding: 3; border-collapse: collapse; text-align:center; height: 100%;">
														
							<tr height="15px">
								<td>&nbsp;</td>
							</tr>
							<tr height="100%">
								<td><b><vgcutil:message key="jsp.gestionarorganizaciones.vision" /></b></td>
							</tr>
							<tr height="100%">
								<td><html:textarea property="vision" rows="14" cols="60" styleClass="cuadroTexto" readonly="true" style="width: 440px" /></td>
							</tr>
							<tr height="15px">
								<td>&nbsp;</td>
							</tr>
							<tr height="100%">
								<td><b><vgcutil:message key="jsp.gestionarorganizaciones.mision" /></b></td>
							</tr>
							<tr height="100%">
								<td><html:textarea property="mision" rows="14" cols="60" styleClass="cuadroTexto" readonly="true" style="width: 440px"/></td>
							</tr>
							<tr height="15px">
								<td>&nbsp;</td>
							</tr>
							<tr height="100%">
								<td><b><vgcutil:message key="jsp.gestionarorganizaciones.lineamientosestrategicos" /></b></td>
							</tr>
							<tr height="100%">
								<td><html:textarea property="lineamientosEstrategicos" rows="14" cols="60" styleClass="cuadroTexto" readonly="true" style="width: 440px"/></td>
							</tr>
						</table>
						
						<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
						</vgcinterfaz:contenedorFormaBarraInferior>
						
					</vgcinterfaz:contenedorForma>
					
					</logic:equal>
					
				</vgcinterfaz:splitterHorizontalPanelDerecho>

			</vgcinterfaz:splitterHorizontal>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-organizaciones'), altoRestante);
			setAncho((_myWidth - _anchoAreBar));
		</script>
		<script type="text/javascript">
			$(document).ready(function() 
			{
				<logic:notEmpty scope="session" name="enviroment">
					<logic:notEmpty scope="session" name="enviroment" property="pageLoad">
						<logic:equal scope="session" name="enviroment" property="pageLoad" value="true">
							timeLoad();
						    var variable = document.getElementById('barraInferior');
						    variable.innerHTML = variable.innerHTML + " <vgcutil:message key='jsp.pagina.load' />" + " " + _totalLoadPage;
						</logic:equal>
					</logic:notEmpty>
				</logic:notEmpty>
			});
		</script>
		<script>				
			<%-- Arma la descripción al final de la lista --%> 
		    var variable = document.getElementById('barraInferior');
		    var nombreOrganizacion = '<bean:write name="organizacion" property="nombre" />';		    
		    variable.innerHTML = "<b><vgcutil:message key='jsp.gestionararbol.nodoseleccionado' /></b>: [" + nombreOrganizacion.toLowerCase() + "]";
		    <logic:notEmpty scope="session" name="activarInformeAlerta">
		    	<logic:equal scope="session" name="activarInformeAlerta" property="hayAlertas" value="true">
			    	var maskHeight = _myHeight;  
					var maskWidth = _myWidth;
					_dialogOpen = true;
					
					// calculate the values for center alignment
					var dialogTop =  maskHeight / 2;  
					var dialogLeft = screen.width / 2; 
					
					// assign values to the overlay and dialog box
					$('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
					$('#dialog-box').css({top:(dialogTop - 200), left:(dialogLeft - 150), height:300, width:350}).show();
					
					// display the message
					//$('#dialog-bottom').css({visibility: "hidden"});
					$('#dialog-titulo').html(nombreOrganizacion + '<br>' + '<vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.noticia" />');
					
					var message = "<div style='border:1px solid; height:250; width:335;'>" + '<bean:write name="gestionarOrganizacionesForm" property="alerta" />';
					var descripcion = '<bean:write name="gestionarOrganizacionesForm" property="descripcion" />';
					if (descripcion != "")
						message = message + "<br><br><div style='color:blue;'>" + descripcion + "</div>";
					message = message + "</div>";
					
					$('#dialog-message').html(message);
				
		    	</logic:equal>
		    </logic:notEmpty>
		</script>
	</tiles:put>

</tiles:insert>
