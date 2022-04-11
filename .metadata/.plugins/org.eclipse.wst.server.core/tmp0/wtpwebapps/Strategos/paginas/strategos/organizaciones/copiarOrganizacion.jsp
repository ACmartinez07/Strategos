<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.copiarorganizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript">
		
			var pasoActual = 1;
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (pasoActual) 
				{
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						copiar();
						break;
				}
				if (valido) 
				{
					pasoActual = pasoActual + 1;
					mostrarBotones(pasoActual);
				}
				mostrarTitulo();
			}
			
			function previo() 
			{
				pasoActual = pasoActual - 1;
				mostrarBotones(pasoActual);
				mostrarTitulo();
			}

			function crearBoton(nombreBoton, accionBoton)
			{
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
					+ nombreBoton + '</a>';
			
				return boton;
			}
		
			function mostrarBotones(paso) 
			{
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.finalizar.alt" />';
				var accionBotonFinalizar = 'javascript:siguiente();';
			
				switch (paso) 
				{
					case 1:
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;  
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelPresentacion" />
					break;
					case 2:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelRango" />
					break;
					case 3:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelAlcance" />
					break;
					case 4:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelLog" />
					break;
				}
				
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = '..:: <vgcutil:message key="jsp.copiarorganizacion.titulo1" /> ' + pasoActual + ' <vgcutil:message key="jsp.copiarorganizacion.titulo2" />';
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;

				celda = document.getElementById("mensajeFinal");
				celda.innerHTML = '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente" />' + ' "' + document.editarOrganizacionForm.nombre.value + '" ' + ' <vgcutil:message key="jsp.copiarorganizacion.finalizarasistente2" />' + ' "' + document.editarOrganizacionForm.padre.value + '" ' + '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente3" />' + ' "' + document.editarOrganizacionForm.nuevoNombre.value + '" ' + '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente4" />';
			}
			
			function validar() 
			{

				if (window.document.editarOrganizacionForm.nuevoNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.editarorganizacion.alert.nombre.invalido.vacio" /> ');
					return false;
				}
				if (window.document.editarOrganizacionForm.nuevoNombre.value == document.editarOrganizacionForm.nombre.value)
				{
					alert('<vgcutil:message key="jsp.editarorganizacion.alert.nombre.invalido.igualalafuente" /> ');
					return false;
				}
				return true;
			}
			
			function cancelar() 
			{
				window.document.editarOrganizacionForm.action = '<html:rewrite action="/organizaciones/cancelarCopiarOrganizacion"/>?cancelar=true';
				window.document.editarOrganizacionForm.submit();			
			}

			function copiar() 
			{
				if (validar())
				{
					document.editarOrganizacionForm.action= '<html:rewrite action="/organizaciones/guardarCopiarOrganizacion" />' + '?ts=<%= (new Date()).getTime() %>' + 
					'?oragnizacionId=' + document.editarOrganizacionForm.organizacionId.value + 
					'&nuevoNombre=' + document.editarOrganizacionForm.nuevoNombre.value + 
					'&copiarIndicadores=' + document.editarOrganizacionForm.copiarIndicadores.checked +
					'&copiarIniciativas=' + document.editarOrganizacionForm.copiarIniciativas.checked +
					'&copiarPlanes=' + document.editarOrganizacionForm.copiarPlanes.checked +
					'&copiarMediciones=' + document.editarOrganizacionForm.copiarMediciones.checked +
					'&copiarInsumos=' + document.editarOrganizacionForm.copiarInsumos.checked +
					'&copiarOrganizacionHija=' + document.editarOrganizacionForm.copiarOrganizacionHija.checked +
					'&showPresentacion=' + (document.editarOrganizacionForm.showPresentacion.checked ? 1 : 0);
					
					activarBloqueoEspera();
					document.editarOrganizacionForm.submit();
				}
				else
				{
					pasoActual = 3;
					mostrarBotones(pasoActual);
					mostrarTitulo();
				}
			}

			function SeleccionarIndicares()
			{
				if (document.editarOrganizacionForm.copiarIndicadores.checked)
				{
					document.editarOrganizacionForm.copiarMediciones.disabled = false;
					document.editarOrganizacionForm.copiarInsumos.disabled = false;
				}
				else
				{
					document.editarOrganizacionForm.copiarMediciones.checked = false;
					document.editarOrganizacionForm.copiarMediciones.disabled = true;
					document.editarOrganizacionForm.copiarInsumos.checked = false;
					document.editarOrganizacionForm.copiarInsumos.disabled = true;
				}
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/copiarOrganizacion">

			<%-- Id de las tablas --%>
			<html:hidden property="organizacionId" />
			<html:hidden property="padreId" />
			<input type="hidden" name="copiaNombre">

			<vgcinterfaz:contenedorForma width="570px" height="385px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr height=315px>

						<%-- Imágen del asistente --%>
						<td width="315px">
							<img src="<html:rewrite page='/paginas/strategos/organizaciones/imagenes/wzcopiarorganizacion.gif'/>" border="0" width="150px" height="310px">
						</td>

						<td><vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesCopiar" mostrarSelectoresPaneles="false">

							<%-- Panel Presentacion --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
								<table class="tablaSpacing0Padding0Width100Collapse">
									<tr>
										<td>
											<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
												<tr>
													<td>
														<b><vgcutil:message key="jsp.editarorganizacion.paso1.texto1" /></b></br></br> 
														<vgcutil:message key="jsp.copiarorganizacion.presentacion" /></br></br>
														<vgcutil:message key="jsp.copiarorganizacion.presentacion1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<br><br><br>
											<br><br><br>
											<br><br><br>
										</td>
									</tr>
									<tr>
										<td class="contenedorTextoSeleccion">
											<html:checkbox property="showPresentacion" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.copiarorganizacion.presentacion.mostrar" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Nombre --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelRango" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td colspan="2"><b><vgcutil:message key="jsp.copiarorganizacion.nombreCopia" /></b></td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.organizacionfuente" />
										</td>
									</tr>
									<tr>
										<td align="left">
											<html:text property="nombre" styleClass="cuadroTexto" size="46" readonly="true" ></html:text>
										</td>
									</tr>
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.organizaciondestino" />
										</td>
									</tr>
									<tr>
										<td align="left">
											<html:text property="padre" styleClass="cuadroTexto" size="46" readonly="true" ></html:text>
										</td>
									</tr>
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.nuevonombre" />
										</td>
									</tr>
									<tr>
										<td> 
											<html:text property="nuevoNombre" styleClass="cuadroTexto" size="46"></html:text>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel seleccion de indicadores --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelAlcance" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.seleccionarcomponentes" />
										</td>
									</tr>
									<tr>
										<td>
											<html:checkbox property="copiarOrganizacionHija" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.copiarorganizacion.copiarOrganizacionHija" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>
											<html:checkbox property="copiarIndicadores" styleClass="botonSeleccionMultiple" onclick="SeleccionarIndicares();">
												<vgcutil:message key="jsp.copiarorganizacion.copiarindicadores" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarMediciones" styleClass="botonSeleccionMultiple" disabled="true">
												<vgcutil:message key="jsp.copiarorganizacion.copiarmediciones" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarInsumos" styleClass="botonSeleccionMultiple" disabled="true">
												<vgcutil:message key="jsp.copiarorganizacion.copiarinsumos" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>
											<html:checkbox property="copiarIniciativas" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.copiarorganizacion.copiariniciativas" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>
											<html:checkbox property="copiarPlanes" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.copiarorganizacion.copiarplanes" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Finalizar --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelLog" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td><span id="mensajeFinal"></span></td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

						</vgcinterfaz:contenedorPaneles></td>
					</tr>
				</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<html:javascript formName="editarOrganizacionForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/calculos/calculosJs/staticJavascript.jsp'/>"></script>
		<script type="text/javascript">
			if (document.editarOrganizacionForm.showPresentacion.checked)
				pasoActual = 2;
			mostrarTitulo();
			mostrarBotones(pasoActual);
			document.editarOrganizacionForm.nuevoNombre.value = '<vgcutil:message key="jsp.editarorganizacion.nuevonombreCopia" /> ' + '<bean:write name="editarOrganizacionForm" property="nombre" />';
			if (document.editarOrganizacionForm.padreId.value == "")
			{
				var mensaje = '<vgcutil:message key="jsp.editarorganizacion.alert.esnodopadre" /> ';
				alert(mensaje);
				cancelar(); 
			} 
		</script>
	</tiles:put>
</tiles:insert>
