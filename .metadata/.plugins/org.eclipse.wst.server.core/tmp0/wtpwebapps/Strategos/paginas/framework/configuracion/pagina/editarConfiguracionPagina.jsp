<%@ page import="com.visiongc.app.strategos.web.struts.configuracion.actions.ValidarConfiguracionPaginaAction"%>

<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.editarconfiguracionpagina.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script>

			function guardar() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/configuracion/validarConfiguracionPagina" />' + 
						'?imagenSupIzq=' + window.document.editarConfiguracionPaginaForm.imagenSupIzq.value +
						'&imagenSupCen=' + window.document.editarConfiguracionPaginaForm.imagenSupCen.value +
						'&imagenSupDer=' + window.document.editarConfiguracionPaginaForm.imagenSupDer.value +
						'&imagenInfIzq=' + window.document.editarConfiguracionPaginaForm.imagenInfIzq.value +
						'&imagenInfCen=' + window.document.editarConfiguracionPaginaForm.imagenInfCen.value +
						'&imagenInfDer=' + window.document.editarConfiguracionPaginaForm.imagenInfDer.value +
						'&fromValidar=' + true, document.editarConfiguracionPaginaForm.respuesta, 'onValidar()');
			}

			function onValidar()
			{
				if (document.editarConfiguracionPaginaForm.respuesta.value == "Success")
				{
					document.editarConfiguracionPaginaForm.action= '<html:rewrite action="/framework/configuracion/guardarConfiguracionPagina" />' + '?tsValidar=false&ts=<%= (new java.util.Date()).getTime() %>';
					document.editarConfiguracionPaginaForm.submit();
				}
				else
				{
					var respuesta = document.editarConfiguracionPaginaForm.respuesta.value.split(",");
					var mensaje = ""; 
					switch (respuesta[0])
					{
						case "imagenInvalida":
							mensaje = '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenInvalida" />';
							break;
						case "tamanoInvalido":
							mensaje = '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.tamanoInvalido" />';
							break;
						case "dimensionInvalida":
							mensaje = '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.dimensionInvalida" />';
							break;
						case "direcciondesconocida":
							mensaje = '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.direcciondesconocida" />';
							break;
					}	

					mensaje = mensaje + " ";
					switch (respuesta[1])
					{
						case "imagenSupIzq":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenSupIzq" />';
							break;
						case "imagenSupCen":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenSupCen" />';
							break;
						case "imagenSupDer":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenSupDer" />';
							break;
						case "imagenInfIzq":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenInfIzq" />';
							break;
						case "imagenInfCen":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenInfCen" />';
							break;
						case "imagenInfDer":
							mensaje = mensaje + '<vgcutil:message key="jsp.editarconfiguracionpagina.validacion.imagenInfDer" />';
							break;
					}	
					
					alert(mensaje);
				}
			} 

			function cancelar() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/configuracion/cancelarGuardarConfiguracionPagina" />', null, 'cerrarConfiguracionPagina()');
			}

			function cerrarConfiguracionPagina() 
			{
				window.close();
			}
			
			function insertAtCursorPosition(myField, myValue) 
			{
				if (document.selection) 
					insertStringExplorer(myValue, myField);
				else if (myField.selectionStart || myField.selectionStart == '0') 
				{
					var startPos = myField.selectionStart;
					var endPos = myField.selectionEnd;
					myField.value = myField.value.substring(0, startPos) + myValue
						+ myField.value.substring(endPos, myField.value.length);
					myField.selectionStart = startPos + myValue.length;
					myField.selectionEnd = startPos + myValue.length;
				} 
				else 
				{
					myField.value += myValue;
					myField.selectionStart = startPos + myValue.length;
					myField.selectionEnd = startPos + myValue.length;
				}
			}
			
			var globalPosicionCursor = 0;			
			
			function setPosicionCursor(campoText) 
			{
				globalPosicionCursor = getPosicionCursorPagina(campoText);
			}
			
			function getPosicionCursorPagina(textElement) 
			{
				var sOldText = textElement.value;
				var objRange = document.selection.createRange();
				var sOldRange = objRange.text;
				var sWeirdString = '#%~';
				objRange.text = sOldRange + sWeirdString; objRange.moveStart('character', (0 - sOldRange.length - sWeirdString.length));
				var sNewText = textElement.value;
				objRange.text = sOldRange;
				for (i=0; i <= sNewText.length; i++) 
				{
					var sTemp = sNewText.substring(i, i + sWeirdString.length);
					if (sTemp == sWeirdString) 
					{
						var cursorPos = (i - sOldRange.length);
						return cursorPos;
					}
				}
			}
			
			function insertStringExplorer(stringToInsert, campoTexto) 
			{
				var firstPart = campoTexto.value.substring(0, globalPosicionCursor);
				var secondPart = campoTexto.value.substring(globalPosicionCursor, campoTexto.value.length);
				campoTexto.value = firstPart + stringToInsert + secondPart;
				globalPosicionCursor = globalPosicionCursor + stringToInsert.length;
			}
			
			var campoEnfocado = '';
			
			function insertarMacro(macro) 
			{
				var campo = null;

				if (campoEnfocado == 'encabezadoIzquierdo') 
					campo = document.editarConfiguracionPaginaForm.encabezadoIzquierdo;
				else if (campoEnfocado == 'encabezadoCentro') 
					campo = document.editarConfiguracionPaginaForm.encabezadoCentro;
				else if (campoEnfocado == 'encabezadoDerecho') 
					campo = document.editarConfiguracionPaginaForm.encabezadoDerecho;
				else if (campoEnfocado == 'piePaginaIzquierdo') 
					campo = document.editarConfiguracionPaginaForm.piePaginaIzquierdo;
				else if (campoEnfocado == 'piePaginaCentro') 
					campo = document.editarConfiguracionPaginaForm.piePaginaCentro;
				else if (campoEnfocado == 'piePaginaDerecho') 
					campo = document.editarConfiguracionPaginaForm.piePaginaDerecho;

				if (campo != null) 
					insertAtCursorPosition(campo, macro);
			}
			
			function eliminarImagen(numero) 
			{
				if (numero == '1') 
					document.editarConfiguracionPaginaForm.nombreImagenSupIzq.value = '';
				if (numero == '2') 
					document.editarConfiguracionPaginaForm.nombreImagenSupCen.value = '';
				if (numero == '3') 
					document.editarConfiguracionPaginaForm.nombreImagenSupDer.value = '';
				if (numero == '4') 
					document.editarConfiguracionPaginaForm.nombreImagenInfIzq.value = '';
				if (numero == '5') 
					document.editarConfiguracionPaginaForm.nombreImagenInfCen.value = '';
				if (numero == '6') 
					document.editarConfiguracionPaginaForm.nombreImagenInfDer.value = '';
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/configuracion/guardarConfiguracionPagina" styleClass="formaHtml" enctype="multipart/form-data" method="POST">

			<html:hidden property="configuracionBase" />
			<html:hidden property="respuesta" />

			<vgcinterfaz:contenedorForma width="430px" height="445px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.framework.editarconfiguracionpagina.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="350" nombre="configuracionPagina">

					<!-- Panel: Margenes -->
					<vgcinterfaz:panelContenedor anchoPestana="80px" nombre="margenes">

						<!-- Título del Panel Margenes -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.margenes" />
						</vgcinterfaz:panelContenedorTitulo>

						<table cellspacing="8">
							<tr>
								<td>
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%" align="center" height="100%">
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.margenes.superior" /></td>
										<td><html:text property="tamanoMargenSuperior" styleClass="cuadroTexto" size="46" onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 1, false)" onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 1, false);" onblur="validarEntradaNumeroEventoOnBlur(this, event, 1, false)" ></html:text></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.margenes.inferior" /></td>
										<td><html:text property="tamanoMargenInferior" styleClass="cuadroTexto" size="46" onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 1, false)" onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 1, false);" onblur="validarEntradaNumeroEventoOnBlur(this, event, 1, false)"></html:text></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.margenes.izquierdo" /></td>
										<td><html:text property="tamanoMargenIzquierdo" styleClass="cuadroTexto" size="46" onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 1, false)" onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 1, false);" onblur="validarEntradaNumeroEventoOnBlur(this, event, 1, false)"></html:text></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.margenes.derecho" /></td>
										<td><html:text property="tamanoMargenDerecho" styleClass="cuadroTexto" size="46" onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 1, false)" onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 1, false);" onblur="validarEntradaNumeroEventoOnBlur(this, event, 1, false)"></html:text></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Imagenes -->
					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="imagenes">

						<!-- Título del Panel Imagenes -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes" />
						</vgcinterfaz:panelContenedorTitulo>

						<table cellspacing="8">
							<tr>
								<td><%-- Contenedor de Paneles --%> <vgcinterfaz:contenedorPaneles width="350" height="250" nombre="configuracionPaginaImagenes">

									<!-- Panel: Superior -->
									<vgcinterfaz:panelContenedor anchoPestana="80px" nombre="superior">
										<!-- Título del Panel Imagenes Superiores -->
										<vgcinterfaz:panelContenedorTitulo>Superior</vgcinterfaz:panelContenedorTitulo>
										<table width="100%" cellpadding="3" cellspacing="3">
											<tr>
												<td>
												<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imagensupizq" /></td>
													</tr>
													<tr>
														<td>
															<html:text property="nombreImagenSupIzq" readonly="true" styleClass="cuadroTexto" size="48"></html:text>
															&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('1');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
														</td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenSupIzq" class="cuadroTexto"></td>
													</tr>
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imagensupcen" /></td>
													</tr>
													<tr>
														<td><html:text property="nombreImagenSupCen" readonly="true" styleClass="cuadroTexto" size="48"></html:text>&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('2');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenSupCen" class="cuadroTexto"></td>
													</tr>
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imagensupder" /></td>
													</tr>
													<tr>
														<td><html:text property="nombreImagenSupDer" readonly="true" styleClass="cuadroTexto" size="48"></html:text>&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('3');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenSupDer" class="cuadroTexto"></td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<!-- Panel: Inferior -->
									<vgcinterfaz:panelContenedor anchoPestana="80px" nombre="inferior">
										<!-- Título del Panel Imagenes Superiores -->
										<vgcinterfaz:panelContenedorTitulo>Inferior</vgcinterfaz:panelContenedorTitulo>
										<table width="100%" cellpadding="3" cellspacing="3">
											<tr>
												<td>
												<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imageninfizq" /></td>
													</tr>
													<tr>
														<td><html:text property="nombreImagenInfIzq" readonly="true" styleClass="cuadroTexto" size="48"></html:text>&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('4');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenInfIzq" class="cuadroTexto"></td>
													</tr>
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imageninfcen" /></td>
													</tr>
													<tr>
														<td><html:text property="nombreImagenInfCen" readonly="true" styleClass="cuadroTexto" size="48"></html:text>&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('5');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenInfCen" class="cuadroTexto"></td>
													</tr>
													<tr>
														<td><vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.imagenes.imageninfder" /></td>
													</tr>
													<tr>
														<td><html:text property="nombreImagenInfDer" readonly="true" styleClass="cuadroTexto" size="48"></html:text>&nbsp;<img style="cursor: pointer" onclick="eliminarImagen('6');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
													</tr>
													<tr>
														<td><input size="38" type="file" name="imagenInfDer" class="cuadroTexto"></td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

								</vgcinterfaz:contenedorPaneles></td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Encabezados -->
					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="encabezados">

						<!-- Título del Panel Encabezados -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.encabezados" />
						</vgcinterfaz:panelContenedorTitulo>

						<table cellspacing="8">
							<tr>
								<td>
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
									<tr>
										<td>
										<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
											<tr>
												<td colspan="2"><b><vgcutil:message key="jsp.framework.editarconfiguracionpagina.encabezado" /></b></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.encabezado.izquierdo" /></td>
												<td><html:text property="encabezadoIzquierdo" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='encabezadoIzquierdo';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.encabezado.centro" /></td>
												<td><html:text property="encabezadoCentro" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='encabezadoCentro';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.encabezado.derecho" /></td>
												<td><html:text property="encabezadoDerecho" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='encabezadoDerecho';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td>
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
									<tr>
										<td>
										<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
											<tr>
												<td colspan="2"><b><vgcutil:message key="jsp.framework.editarconfiguracionpagina.piedepagina" /></b></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.piedepagina.izquierdo" /></td>
												<td><html:text property="piePaginaIzquierdo" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='piePaginaIzquierdo';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.piedepagina.centro" /></td>
												<td><html:text property="piePaginaCentro" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='piePaginaCentro';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
											<tr>
												<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.piedepagina.derecho" /></td>
												<td><html:text property="piePaginaDerecho" styleClass="cuadroTexto" size="44" onfocus="campoEnfocado='piePaginaDerecho';" onchange="setPosicionCursor(this)" onclick="setPosicionCursor(this)"></html:text></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td>
								<table cellpadding="4">
									<tr>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macropagina" />" onclick="insertarMacro('&P')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macropagina.alt" />"></td>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrofecha.corta" />" onclick="insertarMacro('&D')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrofecha.corta.alt" />"></td>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrofecha.larga" />" onclick="insertarMacro('&L')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrofecha.larga.alt" />"></td>
									</tr>
									<tr>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrohora" />" onclick="insertarMacro('&T')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macrohora.alt" />"></td>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macroano" />" onclick="insertarMacro('&Y')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macroano.alt" />"></td>
										<td><input type="button" style="width: 110px; height: 20px; cursor: pointer;" class="cuadroTexto" value="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macromes" />" onclick="insertarMacro('&M')" title="<vgcutil:message key="jsp.framework.editarconfiguracionpagina.macromes.alt" />"></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Fuente -->
					<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="fuente">

						<!-- Título del Panel Fuente -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.framework.editarconfiguracionpagina.panel.fuente" />
						</vgcinterfaz:panelContenedorTitulo>

						<table cellspacing="8">
							<tr>
								<td>
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%" align="center" height="100%">
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.fuente.nombre" /></td>
										<td><html:select property="nombreFuente" styleClass="cuadroTexto">
											<html:optionsCollection property="fuentes" value="clave" label="valor" />
										</html:select></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.fuente.estilo" /></td>
										<td><html:select property="estiloFuente" styleClass="cuadroTexto">
											<html:optionsCollection property="estilos" value="clave" label="valor" />
										</html:select></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.framework.editarconfiguracionpagina.fuente.tamano" /></td>
										<td><html:select property="tamanoFuente" styleClass="cuadroTexto">
											<html:optionsCollection property="tamanosFuente" value="clave" label="valor" />
										</html:select></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;
					&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
