<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
			<vgcutil:message key="jsp.editarplantillaplanes.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
			<vgcutil:message key="jsp.editarplantillaplanes.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarPlantillaPlanesForm" property="bloqueado">
				<bean:write name="editarPlantillaPlanesForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarPlantillaPlanesForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">				
				
			//Declaración de las variables globales
			var arreglo = new Array();
			var separadorPerspectivas = '<bean:write name="editarPlantillaPlanesForm" property="separadorPerspectivas" scope="request" />';
			var separadorAtributosPerspectivas = '<bean:write name="editarPlantillaPlanesForm" property="separadorAtributosPerspectivas" scope="request" />';
			var cargarValoresExistentes = false;
			var accionNiveles = "";

			function abrirNiveles() 
			{
				//Declaración de las variables locales
				var valoresExistentes = "";

				//Se pasan todos los nombres del arreglo a la cadana de validación
				for (var i = 0; i < arreglo.length; i++) 
				{
					//Variable local
					var nombreNivel = arreglo[i].split(separadorAtributosPerspectivas);
					valoresExistentes = valoresExistentes + nombreNivel[2] + separadorPerspectivas;
				}

				//Elimina el último separador
				valoresExistentes = valoresExistentes.substring(0, valoresExistentes.length - separadorPerspectivas.length);
				
				//Se lanza el selector de Niveles de Plantillas de Planes
				var width = 440;
				var height = 140;
				if (accionNiveles == "0")
					height = 160;
				abrirVentanaModal('<html:rewrite page="/paginas/strategos/planes/plantillas/editarNivelesPlantillaPlanes.jsp" />?accionNiveles="' + accionNiveles + '"&valoresExistentes="' + valoresExistentes + '"&separadorPerspectivas="' + separadorPerspectivas + '"', 'niveles', width, height);
			}

			function borrarTodo() 
			{
				arreglo = new Array();
				accionNiveles = "";
				dibujarTablaPerspectivas();
			}

			function cargarNivelesExistentes() 
			{
				if ((window.document.editarPlantillaPlanesForm.textoNiveles.value != null) && (window.document.editarPlantillaPlanesForm.textoNiveles.value != "")) 
				{
					//Se toma el valor que viene del Form Bean
					arreglo = window.document.editarPlantillaPlanesForm.textoNiveles.value.split(separadorPerspectivas);

					//Se dibuja la tabla
					dibujarTablaPerspectivas();

				} 
				else 
				{
					// Solo se establecen valores por defecto para el caso de Nuevo
					<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">

						//Se establece valores por defecto
						window.document.editarPlantillaPlanesForm.nombreNivel.value = '<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.perspectiva" />';
						window.document.editarPlantillaPlanesForm.tipoNivel.value = 0;
						agregarNivel();
						window.document.editarPlantillaPlanesForm.nombreNivel.value = '<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.objetivo" />';
						window.document.editarPlantillaPlanesForm.tipoNivel.value = 1;
						agregarNivel();

					</logic:equal>
				}
			}

			function agregarNivel() 
			{
				//Declaración de las variables locales
				var	nombreNivel =  window.document.editarPlantillaPlanesForm.nombreNivel.value;
				var	tipoNivel = window.document.editarPlantillaPlanesForm.tipoNivel.value;

				//Se añade el Nivel en el arreglo global
				arreglo[(arreglo.length)] = (arreglo.length) + separadorAtributosPerspectivas + tipoNivel + separadorAtributosPerspectivas + nombreNivel;

				//Se dibuja la tabla
				dibujarTablaPerspectivas();
			}

			function eliminarNivel(valor) 
			{
				//Declaración de las variables locales
				var arregloTemporal = new Array();
				var n = 0;

				//Se pasa el arreglo global a uno temporal
				for (var i = 0; i < arreglo.length; i++) 
					arregloTemporal[i] = arreglo[i];

				//Se limpia el arreglo global
				arreglo = new Array();

				//Se evalua cada elemento con el valor que se desea eliminar
				for (var i = 0; i < arregloTemporal.length; i++) 
				{
					//Declaración de las variables locales
					var ordenNivel = arregloTemporal[i].split(separadorAtributosPerspectivas);

					//Se es igual no se añade al arreglo global
					if (ordenNivel[0] != valor) 
					{
						arreglo[n] = arregloTemporal[i];
						n++;
					}
				}

				//Se ordenan nuevamente los valores del arreglo
				for (var i = 0; i < arreglo.length; i++) 
				{
					//Declaración de las variables locales
					var arregloOrdenado = arreglo[i].split(separadorAtributosPerspectivas);

					//Se ordena los valores
					arreglo[i] = i + separadorAtributosPerspectivas + arregloOrdenado[1] + separadorAtributosPerspectivas + arregloOrdenado[2];
				}

				//Se dibuja la tabla
				dibujarTablaPerspectivas();
			}

			function dibujarTablaPerspectivas() 
			{
				//Declaración de las variables locales
				var tabla = document.getElementById("tablaListaNiveles");
				var espacios = '';
				accionNiveles = "";

				//Se eliminan todas las filas
				var numFilas = tabla.getElementsByTagName("tr").length;
				if (numFilas > 0) 
				{
					valor = numFilas - 1;
					for (var i = 0; i < numFilas; i++)
					{
						tabla.deleteRow(valor);
						valor--;												
					}
				}

				//Se recorre el arreglo global
				for (var i = 0; i < arreglo.length; i++) 
				{
					//Se establecen los valores de la tabla
					var arregloTabla = arreglo[i].split(separadorAtributosPerspectivas);
					tabla.insertRow(tabla.rows.length);
					var tr = tabla.rows[tabla.rows.length-1];
					tr.height = "20px";
					var td = document.createElement("td");
					td.align = "left";
					td.width = "100%";
					var srcImgEliminar = "<html:rewrite page='/componentes/visorLista/eliminar.gif'/>";
					var titleEliminar = "<vgcutil:message key="boton.eliminar.alt" />";
					var srcImgTipoA = "<html:rewrite page='/paginas/strategos/planes/plantillas/imagenes/perspectiva.gif'/>";
					var srcImgTipoB = "<html:rewrite page='/paginas/strategos/planes/plantillas/imagenes/objetivo.gif'/>";
					var funcion = "onClick=javascript:eliminarNivel('" + arregloTabla[0] +"');";
					var tituloTipoA = "<vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nivel.superior" />";
					var tituloTipoB = "<vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nivel.inferior" />";

					if ((i+1) == arreglo.length) 
					{
						// Solo se establecen valores por defecto para el caso de Nuevo
						<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
							imagenPuedeEliminar = '<img style="cursor:pointer" src="' + srcImgEliminar + '" ' + funcion + ' title="' + titleEliminar + '" border="0" width="10" height="10">';
						</logic:equal>
					} 
					else 
						imagenPuedeEliminar = '';

					if (arregloTabla[1] == 0) 
					{
						accionNiveles = "0";
						td.innerHTML = (espacios + imagenPuedeEliminar + '&nbsp;&nbsp;' + '<img src="' + srcImgTipoA + '" title="' + tituloTipoA + '" border="0" width="10" height="10">' + '&nbsp;&nbsp;' + arregloTabla[2]);
					} 
					else 
					{
						accionNiveles = "1";
						td.innerHTML = (espacios + imagenPuedeEliminar + '&nbsp;&nbsp;' + '<img src="' + srcImgTipoB + '" title="' + tituloTipoB + '" border="0" width="10" height="10">' + '&nbsp;&nbsp;' + arregloTabla[2]);
					}
					espacios = espacios + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					tr.appendChild(td);
				}
			}
			
			function asignarNiveles()
			{								
				var str = "";				
				for (var i = 0; i < arreglo.length; i++) 
					str = str + arreglo[i] + separadorPerspectivas;

				return str.substring(0, str.length - separadorPerspectivas.length);
			}
			
			function validar(forma) 
			{		
				// Se valida que se tengan al menos 2 niveles
				if (arreglo.length < 2) 
				{
					alert('<vgcutil:message key="jsp.editarplantillaplanes.validacion.noniveles" />');										
					return false;
				}
				
				// Se valida que el último de los niveles sea siempre un Objetivo
				if (accionNiveles != "1") 
				{
					alert('<vgcutil:message key="jsp.editarplantillaplanes.validacion.noesobjetivo" />');
					return false;
				}
				
				// Se validan los valores requeridos
				if (validateEditarPlantillaPlanesForm(forma)) 
				{
					window.document.editarPlantillaPlanesForm.action = '<html:rewrite action="/planes/plantillas/guardarPlantillaPlanes"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{
				window.document.editarPlantillaPlanesForm.textoNiveles.value = asignarNiveles();
				if (validar(document.editarPlantillaPlanesForm)) 
					window.document.editarPlantillaPlanesForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarPlantillaPlanesForm.action = '<html:rewrite action="/planes/plantillas/cancelarGuardarPlantillaPlanes"/>';
				window.document.editarPlantillaPlanesForm.submit();
			}

			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPlantillaPlanesForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/plantillas/guardarPlantillaPlanes" focus="nombre">

			<html:hidden property="plantillaPlanesId" />
			<html:hidden property="textoNiveles" />
			<input type="hidden" name="nombreNivel">
			<input type="hidden" name="tipoNivel">
			<input type="hidden" name="ordenNivel">

			<vgcinterfaz:contenedorForma width="730px" height="455px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
						<vgcutil:message key="jsp.editarplantillaplanes.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
						<vgcutil:message key="jsp.editarplantillaplanes.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center">

					<%-- Campos: Nombre --%>
					<tr>
						<td><vgcutil:message key="jsp.editarplantillaplanes.ficha.nombre" /></td>
						<td><html:text style="width: 500px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" onkeypress="ejecutarPorDefecto(event)" size="100" maxlength="50" styleClass="cuadroTexto" /></td>
					</tr>

					<%-- Espacio en Blanco --%>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>

					<%-- Paneles --%>
					<tr>
						<td colspan="2"><%-- Paneles --%> 
							<vgcinterfaz:contenedorPaneles height="280px" width="120px" nombre="editarPlantillaPlanes">

							<%-- Datos Básicos --%>
							<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="datosBasicos">

								<%-- Título de la Pestaña --%>
								<vgcinterfaz:panelContenedorTitulo>
									<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.titulo" />
								</vgcinterfaz:panelContenedorTitulo>

								<%-- Cuerpo de la Pestaña --%>
								<table class="panelContenedor" cellpadding="3" cellspacing="3" border="0">

									<tr>

										<%-- Lado A --%>
										<td>
											<table class="contenedorBotonesSeleccion" style="width: 100%;height: 100%;">
												<%-- Para el caso de Nuevo --%>
												<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
													<tr height="20px">
														<td><input type="button" style="width:50%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.nuevo" />" onclick="abrirNiveles();"> <input type="button" style="width:20%" class="cuadroTexto" value="<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.borrartodo" />" onclick="borrarTodo();"></td>
													</tr>
												</logic:equal>
												<tr>
													<td>
														<div style="width:100%; height:100%; overflow:auto;">
															<table class="listView" cellpadding="3" cellspacing="0" align="center" height="100%" width="400px" border="1">
																<tr class="encabezadoListView" height="20px">
																	<td align="center" width="20px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.niveles" /></td>
																</tr>
																<tr valign="top">
																	<td>
																		<div>
																			<table id="tablaListaNiveles" style="width: 100%;">
																				<colgroup>
																					<col style="width:100%">
																				</colgroup>
																				<tbody class="cuadroTexto">
																				</tbody>
																			</table>
																		</div>
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</td>

										<%-- Lado B --%>
										<td><%-- Indicador --%>
											<table class="contenedorBotonesSeleccion" style="width: 100%;height: 47%;" cellpadding="4" cellspacing="7">
												<tr>
													<td><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreIndicadorSingular" size="27" maxlength="50" styleClass="cuadroTexto" /></td>
												</tr>
											</table>
	
											<br>
	
											<%-- Indicador --%>
											<table class="contenedorBotonesSeleccion" style="width: 100%;height: 48%;" cellpadding="4" cellspacing="7">
												<tr>
													<td><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreIniciativaSingular" size="27" maxlength="50" styleClass="cuadroTexto" /><br>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<html:rewrite page='/paginas/strategos/planes/plantillas/imagenes/linea.gif'/>"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreActividadSingular" size="19" maxlength="50" styleClass="cuadroTexto" /></td>
												</tr>
	
											</table>
										</td>

									</tr>
								</table>

							</vgcinterfaz:panelContenedor>

							<%-- Descripción --%>
							<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="descripcion">

								<%-- Título de la Pestaña --%>
								<vgcinterfaz:panelContenedorTitulo>
									<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.descripcion.titulo" />
								</vgcinterfaz:panelContenedorTitulo>

								<%-- Ficha de datos --%>
								<table class="panelContenedor" cellpadding="3" cellspacing="4">

									<%-- Campos de la Ficha de Datos --%>
									<tr>
										<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="descripcion" rows="18" cols="90" styleClass="cuadroTexto" /></td>
									</tr>

								</table>

							</vgcinterfaz:panelContenedor>
						</vgcinterfaz:contenedorPaneles></td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarPlantillaPlanesForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			
			//Para el caso de Nuevo
			<logic:equal name="editarPlantillaPlanesForm" property="plantillaPlanesId" value="0">
							
				window.document.editarPlantillaPlanesForm.nombreIndicadorSingular.value = '<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.indicador" />';
				window.document.editarPlantillaPlanesForm.nombreIniciativaSingular.value = '<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.iniciativa" />';
				window.document.editarPlantillaPlanesForm.nombreActividadSingular.value = '<vgcutil:message key="jsp.editarplantillaplanes.ficha.pestana.datosBasicos.actividad" />';				
				
			</logic:equal>
			
			//Carga los niveles de la Plantilla de Planes
			cargarNivelesExistentes();
		
		</script>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarPlantillaPlanesForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
