<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<bean:define id="tituloReporte">
		Demo
	</bean:define>
	<logic:notEmpty name="transaccionForm" property="nombre">
		<bean:define id="tituloReporte">
			<bean:write name="transaccionForm" property="nombre" />
		</bean:define>
	</logic:notEmpty>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.reporte.transaccion.reporte.titulo" arg0="<%=tituloReporte%>" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var _setCloseParent = false;
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}

			function visualizarOcultarTabla()
			{
				var visible = true;
				if (document.transaccionForm.showTablaParametro.value == null || document.transaccionForm.showTablaParametro.value == "")
					visible = true;
				else if (document.transaccionForm.showTablaParametro.value.toUpperCase() == "TRUE")
					visible = true;
				else
					visible = false;
				
				document.transaccionForm.showTablaParametro.value = !visible;
				refrescar();
			}
		
			function cancelar() 
			{
				 javascript:irAtras(1);
			}
	
			function guardar()
			{			  
				var url = "transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;
				url = url + "&funcion=salvar";

				window.document.transaccionForm.action = '<html:rewrite action="/transacciones/reporteEjecucionTransaccion"/>?' + url + '&xmlAtributos=' + buscarAtributos();
			    window.document.transaccionForm.submit();
			}
			
			function buscarAtributos()
			{
				var xmlAtributos = "";
				var totalColumnas = parseInt(document.transaccionForm.totalColumnas.value) + 1;
	            for (var i = 1; i < totalColumnas; i++)
	            {
	            	if (document.getElementById("nombre_" + i) != null)
            		{
		            	if (xmlAtributos != "")
		            		xmlAtributos = xmlAtributos + "|";
		            	xmlAtributos = xmlAtributos + i + ",";
		            	xmlAtributos = xmlAtributos + document.getElementById("id_" + i).value + ",";
		            	xmlAtributos = xmlAtributos + document.getElementById("nombre_" + i).value + ",";
		            	xmlAtributos = xmlAtributos + (document.getElementById("showVisible_" + i).checked ? "1" : "0") + ",";
		            	xmlAtributos = xmlAtributos + document.getElementById("ancho_" + i).value + ",";
		            	xmlAtributos = xmlAtributos + (document.getElementById("showAgrupar_" + i).checked ? "1" : "0");
            		}
            	}

	            return xmlAtributos;
			}
			 
			function refrescar() 
			{
				var url = "transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;

				window.document.transaccionForm.action = '<html:rewrite action="/transacciones/reporteEjecucionTransaccion"/>?' + url + '&xmlAtributos=' + buscarAtributos();
			    window.document.transaccionForm.submit();
			}
			
			function imprimir(tipo) 
			{
				var url = "?transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;
				url = url + "&nombre=" + document.transaccionForm.nombre.value;
				url = url + "&orientacion=H&corte=1";
				if (typeof tipo != "undefined") 
					url = url + "&tipoPresentacion=" + tipo;
				
				abrirReporte('<html:rewrite action="/transacciones/imprimirTransaccion"/>' + url + '&xmlAtributos=' + buscarAtributos(), 'Reporte');
			}

			function exportarToXls()
			{
				var url = "?transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;
				url = url + "&nombre=" + document.transaccionForm.nombre.value;
				url = url + "&orientacion=H&corte=1";
				if (typeof tipo != "undefined") 
					url = url + "&tipoPresentacion=" + tipo;

				abrirReporte('<html:rewrite action="/transacciones/imprimirExcelTransaccion"/>' + url + '&xmlAtributos=' + buscarAtributos(), 'Reporte');
			}
			
			function cambiarOrden(orden, tipo) 
			{
				nombreSeleccionado = document.getElementById('nombre_' + orden).value;
				visibleSeleccionado = document.getElementById('showVisible_' + orden).checked;
				anchoSeleccionado = document.getElementById('ancho_' + orden).value;
				agruparSeleccionado = document.getElementById('showAgrupar_' + orden).checked;
				ordenSeleccionado = document.getElementById('orden_' + orden).value;
				idSeleccionado = document.getElementById('id_' + orden).value;
				ordenSwap = parseInt(orden);
				if (tipo == 'subir') 
					ordenSwap = ordenSwap - 1;
				else 
					ordenSwap = ordenSwap + 1;

				nombreNew = document.getElementById('nombre_' + ordenSwap).value;
				visibleNew = document.getElementById('showVisible_' + ordenSwap).checked;
				anchoNew = document.getElementById('ancho_' + ordenSwap).value;
				agruparNew = document.getElementById('showAgrupar_' + ordenSwap).checked;
				ordenNew = document.getElementById('orden_' + ordenSwap).value;
				idNew = document.getElementById('id_' + ordenSwap).value;
				
				document.getElementById('nombre_' + ordenSwap).value = nombreSeleccionado;
				document.getElementById('showVisible_' + ordenSwap).checked = visibleSeleccionado;
				document.getElementById('ancho_' + ordenSwap).value = anchoSeleccionado;
				document.getElementById('showAgrupar_' + ordenSwap).checked = agruparSeleccionado;
				document.getElementById('orden_' + ordenSwap).value = ordenSeleccionado;
				document.getElementById('id_' + ordenSwap).value = idSeleccionado;
				
				document.getElementById('nombre_' + orden).value = nombreNew;
				document.getElementById('showVisible_' + orden).checked = visibleNew;
				document.getElementById('ancho_' + orden).value = anchoNew;
				document.getElementById('showAgrupar_' + orden).checked = agruparNew;
				document.getElementById('orden_' + orden).value = ordenNew;
				document.getElementById('id_' + orden).value = idNew;
			}

			function subirOrden(orden) 
			{
				nroOrden = parseInt(orden);
				if (nroOrden == 1) 
					return;
				
				cambiarOrden(orden, 'subir');
			}

			function bajarOrden(orden) 
			{
				nroOrden = parseInt(orden);
				nroOrden++;
				
				if (!document.getElementById('nombre_' + nroOrden)) 
					return;
				
				cambiarOrden(orden, 'bajar');
			}
	 
		</script>

		<html:form action="/transacciones/reporteEjecucionTransaccion" styleClass="formaHtml">
			<html:hidden property="transaccionId" />
			<html:hidden property="showTablaParametro" />
			<html:hidden property="fechaInicial" />
			<html:hidden property="fechaFinal" />
			<html:hidden property="numeroRegistros" />
			<html:hidden property="totalColumnas" />
			<html:hidden property="nombre" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.reporte.transaccion.reporte.titulo" arg0="<%=tituloReporte%>" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:refrescar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar();
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarDimensiones">

						<vgcinterfaz:barraHerramientasBoton nombreImagen="refrescar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:refrescar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.refrescar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

						<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.guardar.configuracion" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir"
							pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
							onclick="javascript:imprimir();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.imprimir" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="html"
							pathImagenes="/componentes/barraHerramientas/" nombre="html"
							onclick="javascript:imprimir('HTML');">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.html" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
							pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
							onclick="javascript:exportarToXls();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.exportar.excel" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="visible"
							pathImagenes="/componentes/barraHerramientas/" nombre="visible"
							onclick="javascript:visualizarOcultarTabla();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.visible" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>

					</vgcinterfaz:barraHerramientas>

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<logic:equal name="transaccionForm" property="showTablaParametro" value="true">
									<td class="encabezadoListView" align="center" colspan="2" style="border: 1px black solid;width: 300px;">
										<vgcutil:message key="jsp.reporte.transaccion.reporte.campos" />
									</td>
									<td class="encabezadoListView" align="center" width="100%" valign="top" style="border: 1px black solid;">
										<vgcutil:message key="jsp.reporte.transaccion.reporte.parametros" />
									</td>
								</tr>
								<tr class="barraFiltrosForma">
									<td colspan="2" style="border: 1px black solid;width: 300px;">
										<vgcinterfaz:visorLista namePaginaLista="paginaColumnas" messageKeyNoElementos="jsp.reporte.transaccion.reporte.nocolumnas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase" nombre="visorColumnasVisorDatosLista" width="300">
											<vgcinterfaz:columnaAccionesVisorLista width="50px">
												<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.orden" />
											</vgcinterfaz:columnaAccionesVisorLista>
											<vgcinterfaz:columnaVisorLista nombre="nombre" width="100px">
												<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.nombre" />
											</vgcinterfaz:columnaVisorLista>
											<vgcinterfaz:columnaVisorLista nombre="visible">
												<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.visible" />
											</vgcinterfaz:columnaVisorLista>
											<vgcinterfaz:columnaVisorLista nombre="ancho">
												<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.ancho" />
											</vgcinterfaz:columnaVisorLista>
											<vgcinterfaz:columnaVisorLista nombre="agrupar">
												<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.agrupar" />
											</vgcinterfaz:columnaVisorLista>
											
											<vgcinterfaz:filasVisorLista nombreObjeto="columna">
												<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/subir.gif">
													<vgcinterfaz:accionTituloVisorLista>
														<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.subir" />
													</vgcinterfaz:accionTituloVisorLista>subirOrden('<bean:write name="columna" property="orden" />');
												</vgcinterfaz:accionVisorLista>
												<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/bajar.gif">
													<vgcinterfaz:accionTituloVisorLista>
														<vgcutil:message key="jsp.reporte.transaccion.reporte.columna.bajar" />
													</vgcinterfaz:accionTituloVisorLista>bajarOrden('<bean:write name="columna" property="orden" />');
												</vgcinterfaz:accionVisorLista>
												<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
													<input type="text" size="20" readonly class="cuadroTexto" onfocus="this.blur();" 
														name="nombre_<bean:write name="columna" property="orden" />" id="nombre_<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="alias" />" />
													<input type="hidden" 
															id="orden_<bean:write name="columna" property="orden" />"
															name="orden_<bean:write name="columna" property="orden" />"
															value="<bean:write name="columna" property="orden" />" />
													<input type="hidden" 
															id="id_<bean:write name="columna" property="orden" />"
															name="id_<bean:write name="columna" property="orden" />"
															value="<bean:write name="columna" property="nombre" />" />
												</vgcinterfaz:valorFilaColumnaVisorLista>
												<vgcinterfaz:valorFilaColumnaVisorLista nombre="visible" align="center">
													<logic:equal name="columna" property="visible" value="true">
														<input type="checkbox" name="showVisible_<bean:write name="columna" property="orden" />" checked />
													</logic:equal>
													<logic:equal name="columna" property="visible" value="false">
														<input type="checkbox" name="showVisible_<bean:write name="columna" property="orden" />" />
													</logic:equal>
												</vgcinterfaz:valorFilaColumnaVisorLista>
												<vgcinterfaz:valorFilaColumnaVisorLista nombre="ancho">
													<input type="text" size="5" maxlength="3" class="cuadroTexto" 
														name="ancho_<bean:write name="columna" property="orden" />"
														onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 0, false);" 
														onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 0, false);" 
														onblur="validarEntradaNumeroEventoOnBlur(this, event, 0, false)" 
														value="<bean:write name="columna" property="ancho" />" />
												</vgcinterfaz:valorFilaColumnaVisorLista>
												<vgcinterfaz:valorFilaColumnaVisorLista nombre="agrupar" align="center">
													<logic:equal name="columna" property="agrupar" value="true">
														<input type="checkbox" name="showAgrupar_<bean:write name="columna" property="orden" />" checked />
													</logic:equal>
													<logic:equal name="columna" property="agrupar" value="false">
														<input type="checkbox" name="showAgrupar_<bean:write name="columna" property="orden" />" />
													</logic:equal>
												</vgcinterfaz:valorFilaColumnaVisorLista>
											</vgcinterfaz:filasVisorLista>
										</vgcinterfaz:visorLista>
									</td>
							</logic:equal>
							<td width="100%" valign="top" style="border: 1px black solid;">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" width="100%" height="100%">
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="180px"><vgcutil:message key="jsp.reporte.transaccion.reporte.nombrereporte" /></td>
										<td colspan="3">
											<bean:write name="transaccionForm" property="nombre" />
										</td>
									</tr>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="180px"><vgcutil:message key="jsp.reporte.transaccion.reporte.fechainicial" /></td>
										<td>
											<bean:write name="transaccionForm" property="fechaInicial" />
										</td>
										<td class="encabezadoListView" width="90px"><vgcutil:message key="jsp.reporte.transaccion.reporte.fechafinal" /></td>
										<td>
											<bean:write name="transaccionForm" property="fechaFinal" />
										</td>
									</tr>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="180px">
											<vgcutil:message key="jsp.reporte.transaccion.reporte.montoconsolidado" />
										</td>
										<td colspan="3">
											<bean:write name="transaccionForm" property="totalConsolidado" />
										</td>
									</tr>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="180px">
											<vgcutil:message key="jsp.reporte.transaccion.reporte.totaloperacion" />
										</td>
										<td colspan="3">
											<bean:write name="transaccionForm" property="totalOperacion" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<bean:define id="colorCelda" value="oscuro" />
				<bean:define id="estilo" value="#F4F4F4" />
				<vgclogica:tamanoColeccionMayorQue name="transaccionForm" property="datos" value="0">
					<vgclogica:tamanoColeccionMayorQue name="transaccionForm" property="columnas" value="0">

						<%-- Datos --%>
						<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" id="datos" width="<bean:write name="transaccionForm" property="anchoTablaDatos" />">
							<tr height="30px">
								<logic:iterate name="transaccionForm" property="columnas" scope="session" id="columna" indexId="columnaIndex">
									<bean:define id="ancho">
										<logic:notEmpty name="columna" property="ancho">
											<logic:notEqual name="columna" property="ancho" value="">
												<bean:write name="columna" property="ancho" />
											</logic:notEqual>
											<logic:equal name="columna" property="ancho" value="">
												200
											</logic:equal>
										</logic:notEmpty>
										<logic:empty name="columna" property="ancho">
											200
										</logic:empty>
									</bean:define>

									<%-- Se escribe la celda correspondiente de la tabla --%>
									<td width="<%=ancho%>px" class="encabezadoListView" align="center">
										<b><bean:write name="columna" property="alias" /></b>
									</td>
								</logic:iterate>
							</tr>

							<logic:equal name="colorCelda" value="claro">
								<bean:define id="estilo" value="#F4F4F4" />
							</logic:equal>
							<logic:iterate name="transaccionForm" property="datos" scope="session" id="fila" indexId="filaIndex">
								<%-- Validaciones para determinar el color de la celda --%>
								<tr height="30px">
									<logic:iterate name="fila" id="columna" indexId="columnaIndex">
										<%-- Validaciones de la tabla --%>
										<bean:define id="centrado" value="center" />
										<logic:equal name="columna" property="tipo" value="1">
											<bean:define id="centrado">
												<bean:write name="columna" property="formato" />
											</bean:define>
										</logic:equal>
										<logic:equal name="columna" property="tipo" value="2">
											<bean:define id="centrado" value="right" />
										</logic:equal>
										<logic:equal name="columna" property="tipo" value="3">
											<bean:define id="centrado" value="left" />
										</logic:equal>
										<bean:define id="ancho">
											<logic:notEmpty name="columna" property="ancho">
												<logic:notEqual name="columna" property="ancho" value="">
													<bean:write name="columna" property="ancho" />
												</logic:notEqual>
												<logic:equal name="columna" property="ancho" value="">
													200
												</logic:equal>
											</logic:notEmpty>
											<logic:empty name="columna" property="ancho">
												200
											</logic:empty>
										</bean:define>
										
										<%-- Validaciones para determinar el color de la celda --%>
										<td width="<%=ancho%>px" align="<%=centrado%>" bgcolor="<%=estilo%>">
											<bean:write name="columna" property="valorArchivo" />
										</td>
									</logic:iterate>

								</tr>
							</logic:iterate>
						</table>

					</vgclogica:tamanoColeccionMayorQue>
				</vgclogica:tamanoColeccionMayorQue>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
			// Servicio no configurado
			<logic:equal name="transaccionForm" property="status" value="3">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>

</tiles:insert>
