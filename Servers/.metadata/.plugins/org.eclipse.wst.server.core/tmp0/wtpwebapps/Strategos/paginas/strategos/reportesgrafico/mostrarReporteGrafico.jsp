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
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.mostrarvistadatos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery-1.7.1.min.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/tableExport.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/jquery.base64.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/png/html2canvas.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/pdf/base64.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/pdf/jspdf.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/export/pdf/sprintf.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/reportes/Imprimir.js'/>"></script>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">		
			var _showTotalFilas = null;
			var _showTotalColumnas = null;
			var _organizacionId = null;
			var _tiempo = null;
			var _tiempoDesde = null;
			var _tiempoHasta = null;

			function visualizarOcultarTabla()
			{
				var visible = true;
				if (document.configurarVistaDatosForm.showTablaParametro.value == null || document.configurarVistaDatosForm.showTablaParametro.value == "")
					visible = true;
				else if (document.configurarVistaDatosForm.showTablaParametro.value.toUpperCase() == "TRUE")
					visible = true;
				else
					visible = false;
				
				document.configurarVistaDatosForm.showTablaParametro.value = !visible;
				setVisualizarOcultarTabla(!visible);
			}
			
			function setVisualizarOcultarTabla(visible)
			{
				if (typeof visible == "undefined")
					visible = true;
				
				var tabla = document.getElementById("tblParametro");
				if (tabla != null)
				{
					if (visible)
						tabla.style.display = "";
					else
						tabla.style.display = "none";
				}
			}
		
			function cancelar() 
			{
				window.close();
			}
			
			function validar()
			{
				_tiempoDesde = null;
				_tiempoHasta = null;
				if ((document.configurarVistaDatosForm.selector1Id != null && document.configurarVistaDatosForm.selector1Id.value == "3") ||
					(document.configurarVistaDatosForm.selector2Id != null && document.configurarVistaDatosForm.selector2Id.value == "3") ||
					(document.configurarVistaDatosForm.selector3Id != null && document.configurarVistaDatosForm.selector3Id.value == "3") ||
					(document.configurarVistaDatosForm.selector4Id != null && document.configurarVistaDatosForm.selector4Id.value == "3"))
				{
					if (document.configurarVistaDatosForm.valorSelectorTiempoDesde != null && document.configurarVistaDatosForm.valorSelectorTiempoDesde.value != "")
						_tiempoDesde = document.configurarVistaDatosForm.valorSelectorTiempoDesde.value;
					if (document.configurarVistaDatosForm.valorSelectorTiempoHasta != null && document.configurarVistaDatosForm.valorSelectorTiempoHasta.value != "")
						_tiempoHasta = document.configurarVistaDatosForm.valorSelectorTiempoHasta.value;
				}

				if (_tiempoDesde != null && _tiempoHasta != null)
				{
					var campos = _tiempoDesde.split("_");
					var periodo = campos[0]; 
					var ano = campos[1];
			  		var desde = parseInt(ano + "" + (periodo.length == 1 ? "00" : "0") + periodo);

					campos = _tiempoHasta.split("_");
					periodo = campos[0];
					ano = campos[1];
					var hasta = parseInt(ano + "" + (periodo.length == 1 ? "00" : "0") + periodo);

					if (hasta<desde) 
					{
						alert('<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />');
						return false;
					} 
					else
						return true;
				}
				else
					return true;
			}
	
			function guardar()
			{
				if (!validar())
					return;
			    window.document.configurarVistaDatosForm.action = '<html:rewrite action="/vistasdatos/guardarConfiguracionVistaDatos"/>?reporteId=' + document.configurarVistaDatosForm.reporteId.value + configuracion();
			    window.document.configurarVistaDatosForm.submit();	    
			}
			
			function buscarAtributos()
			{
				var xmlAtributos = "";
	            for (var i = 1; i < 7; i++)
	            {
	            	if (document.getElementById("nombre_" + i) != null)
            		{
		            	if (xmlAtributos != "")
		            		xmlAtributos = xmlAtributos + "*sepRow*";
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
			 
			function establecerEstilo(objeto) 
			{
			 	objeto.style.borderLeft="solid 1px #CCCC00";
			 	objeto.style.borderRight="solid 1px #CCCC00";
			 	objeto.style.borderTop="solid 1px #CCCC00";
			 	objeto.style.borderBottom="solid 1px #CCCC00";			 	
			}
			 
			function quitarEstilo(objeto) 
			{
			 	objeto.style.borderLeft="none";
			 	objeto.style.borderRight="none";
			 	objeto.style.borderTop="none";
			 	objeto.style.borderBottom="none";
			}
			
			function configuracion()
			{
				var campos = '&corte=' + document.configurarVistaDatosForm.corte.value;
				campos = campos + '&showTotalFilas=' + _showTotalFilas;
				campos = campos + '&showTotalColumnas=' + _showTotalColumnas;
				var acumularPeriodos = document.configurarVistaDatosForm.acumularPeriodos;
				if (acumularPeriodos != null)
					campos = campos + '&acumularPeriodos=' + (acumularPeriodos.checked ? "true" : "false");
				campos = campos + '&xmlAtributos=' + buscarAtributos(); 
				
				return campos;
			}
			
			function refrescar() 
			{
				if (!validar())
					return;
				activarBloqueoEspera();
				window.document.configurarVistaDatosForm.action = '<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>?source=' + document.configurarVistaDatosForm.source.value + configuracion();
			    window.document.configurarVistaDatosForm.submit();
			}
			
			function imprimir(tipo) 
			{
				if (!validar())
					return;
				var url = '?source=' + document.configurarVistaDatosForm.source.value;
				url = url + '&reporteId=' + document.configurarVistaDatosForm.reporteId.value;
				url = url + "&nombre=" + document.configurarVistaDatosForm.nombre.value;
				url = url + "&orientacion=H&corte=1";
				if (_organizacionId != null)
					url = url + "&organizacionId=" + _organizacionId;
				if (_tiempo != null)
					url = url + "&tiempo=" + _tiempo;
				if (_tiempoDesde != null)
					url = url + "&tiempoDesde=" + _tiempoDesde;
				if (_tiempoHasta != null)
					url = url + "&tiempoHasta=" + _tiempoHasta;
				if (typeof tipo != "undefined") 
					url = url + "&tipoPresentacion=" + tipo;
				
				abrirReporte('<html:rewrite action="/vistasdatos/imprimir"/>' + url, 'Reporte');
				
				//var imprimir = new Imprimir();
				//imprimir.Print(document.getElementById('tblVistaDatos'));
			}

			function exportarToXls()
			{
				if (!validar())
					return;
				//var url = '?source=' + document.configurarVistaDatosForm.source.value;
				//url = url + '&reporteId=' + document.configurarVistaDatosForm.reporteId.value;
				//url = url + "&nombre=" + document.configurarVistaDatosForm.nombre.value;
				//url = url + "&corte=1";
				//if (_organizacionId != null)
					//url = url + "&organizacionId=" + _organizacionId;
				//if (_tiempo != null)
					//url = url + "&tiempo=" + _tiempo;
				//if (_tiempoDesde != null)
					//url = url + "&tiempoDesde=" + _tiempoDesde;
				//if (_tiempoHasta != null)
					//url = url + "&tiempoHasta=" + _tiempoHasta;

				//abrirReporte('<html:rewrite action="/vistasdatos/exportarXLS"/>' + url, 'Reporte');
				
				$('#tblVistaDatos').tableExport({type:'excel',escape:'false'});
			}

			function exportarToCVS()
			{
				if (!validar())
					return;
				
				$('#tblVistaDatos').tableExport({type:'csv',escape:'false',separator:'|'});
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
			
			function showTotalFilas_click(checked)
			{
				_showTotalFilas = checked;
			}

			function showTotalColumnas_click(checked)
			{
				_showTotalColumnas = checked;
			}
			
		</script>

		<html:form action="/vistasdatos/mostrarVistaDatos" styleClass="formaHtml">
			<html:hidden property="reporteId" />
			<html:hidden property="source" />
			<html:hidden property="configuracion" />
			<html:hidden property="nombre" />
			<html:hidden property="corte" />
			<html:hidden property="showTablaParametro" />
			<html:hidden property="selector1Id" />
			<html:hidden property="selector2Id" />
			<html:hidden property="selector3Id" />
			<html:hidden property="selector4Id" />

			<vgcinterfaz:contenedorForma>
				<bean:define toScope="page" id="haySelector" value="false"></bean:define>
				<logic:equal scope="page" name="haySelector" value="false">
					<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector1" value="">
						<bean:define toScope="page" id="haySelector" value="true"></bean:define>
					</logic:notEqual>
				</logic:equal>
				<logic:equal scope="page" name="haySelector" value="false">
					<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector2" value="">
						<bean:define toScope="page" id="haySelector" value="true"></bean:define>
					</logic:notEqual>
				</logic:equal>
				<logic:equal scope="page" name="haySelector" value="false">
					<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector3" value="">
						<bean:define toScope="page" id="haySelector" value="true"></bean:define>
					</logic:notEqual>
				</logic:equal>
				<logic:equal scope="page" name="haySelector" value="false">
					<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector4" value="">
						<bean:define toScope="page" id="haySelector" value="true"></bean:define>
					</logic:notEqual>
				</logic:equal>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.mostrarvistadatos.titulo" /> / <bean:write scope="session" name="organizacionNombre"/>
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
						
						<logic:notEqual name="configurarVistaDatosForm" property="configuracion" value="">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton nombreImagen="imprimir"
								pathImagenes="/componentes/barraHerramientas/" nombre="imprimir"
								onclick="javascript:imprimir();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.imprimir.vista.datos" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:exportarToXls();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.vista.datos.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="csv"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:exportarToCVS();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.vista.datos.csv" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="html"
								pathImagenes="/componentes/barraHerramientas/" nombre="html"
								onclick="javascript:imprimir('HTML');">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.vista.datos.html" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton nombreImagen="visible"
								pathImagenes="/componentes/barraHerramientas/" nombre="visible"
								onclick="javascript:visualizarOcultarTabla();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.visible" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>

					</vgcinterfaz:barraHerramientas>

					<%-- Filtros --%>
					<table id="tblParametro" name="tblParametro" style="width: 100%; padding: 1px; border-spacing: 0px; border-collapse: collapse;">
						<tr class="barraFiltrosForma">
							<td class="encabezadoListView" align="center" colspan="2" style="border: 1px black solid;width: 300px;">
								<vgcutil:message key="jsp.mostrarvistadatos.informacion" />
							</td>
							<td class="encabezadoListView" align="center" width="100%" valign="top" style="border: 1px black solid;">
								<vgcutil:message key="jsp.mostrarvistadatos.parametros" />
							</td>
						</tr>
							
						<tr class="barraFiltrosForma">
							<td colspan="2" style="border: 1px black solid;width: 300px;">
								<vgcinterfaz:visorLista namePaginaLista="paginaColumnas" messageKeyNoElementos="jsp.framework.editarconfiguracionvisorlista.nocolumnas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase" nombre="visorColumnasVisorDatosLista" width="300">
									<vgcinterfaz:columnaAccionesVisorLista width="50px">
										<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.orden" />
									</vgcinterfaz:columnaAccionesVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="nombre" width="100px">
										<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.nombre" />
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="visible">
										<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.visible" />
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="ancho">
										<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.ancho" />
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="agrupar">
										<vgcutil:message key="jsp.mostrarvistadatos.parametros.columna.agrupar" />
									</vgcinterfaz:columnaVisorLista>
									
									<vgcinterfaz:filasVisorLista nombreObjeto="columna">
										<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/subir.gif">
											<vgcinterfaz:accionTituloVisorLista>
												<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.accion.subir" />
											</vgcinterfaz:accionTituloVisorLista>subirOrden('<bean:write name="columna" property="orden" />');
										</vgcinterfaz:accionVisorLista>
										<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/bajar.gif">
											<vgcinterfaz:accionTituloVisorLista>
												<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.accion.bajar" />
											</vgcinterfaz:accionTituloVisorLista>bajarOrden('<bean:write name="columna" property="orden" />');
										</vgcinterfaz:accionVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
											<input type="text" size="20" readonly class="cuadroTexto" onfocus="this.blur();" 
												name="nombre_<bean:write name="columna" property="orden" />" id="nombre_<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="nombre" />" />
											<input type="hidden" 
													id="orden_<bean:write name="columna" property="orden" />"
													name="orden_<bean:write name="columna" property="orden" />"
													value="<bean:write name="columna" property="orden" />" />
											<input type="hidden" 
													id="id_<bean:write name="columna" property="orden" />"
													name="id_<bean:write name="columna" property="orden" />"
													value="<bean:write name="columna" property="tipoAtributoId" />" />
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="visible" align="center">
											<logic:equal name="columna" property="visible" value="true">
												<input type="checkbox" id="showVisible_<bean:write name="columna" property="orden" />" name="showVisible_<bean:write name="columna" property="orden" />" checked />
											</logic:equal>
											<logic:equal name="columna" property="visible" value="false">
												<input type="checkbox" id="showVisible_<bean:write name="columna" property="orden" />" name="showVisible_<bean:write name="columna" property="orden" />" />
											</logic:equal>
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="ancho">
											<input type="text" size="5" maxlength="3" class="cuadroTexto"
												id="ancho_<bean:write name="columna" property="orden" />" 
												name="ancho_<bean:write name="columna" property="orden" />"
												onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 0, false);" 
												onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 0, false);" 
												onblur="validarEntradaNumeroEventoOnBlur(this, event, 0, false)" 
												value="<bean:write name="columna" property="ancho" />" />
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="agrupar" align="center">
											<logic:equal name="columna" property="agrupar" value="true">
												<input type="checkbox" id="showAgrupar_<bean:write name="columna" property="orden" />" name="showAgrupar_<bean:write name="columna" property="orden" />" checked />
											</logic:equal>
											<logic:equal name="columna" property="agrupar" value="false">
												<input type="checkbox" id="showAgrupar_<bean:write name="columna" property="orden" />" name="showAgrupar_<bean:write name="columna" property="orden" />" />
											</logic:equal>
										</vgcinterfaz:valorFilaColumnaVisorLista>
									</vgcinterfaz:filasVisorLista>
								</vgcinterfaz:visorLista>
							</td>
							<td width="100%" valign="top" style="border: 1px black solid;">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" width="100%" height="100%">
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px"><vgcutil:message key="jsp.mostrarvistadatos.nombrereporte" /></td>
										<td colspan="2">
											<bean:write name="configurarVistaDatosForm" property="nombre" />
										</td>
									</tr>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px">
											<vgcutil:message key="jsp.mostrarvistadatos.frecuencia" />
										</td>
										<td colspan="2">
											<bean:write name="configurarVistaDatosForm" property="nombreFrecuencia" />
										</td>
									</tr>
									<%-- Selector 1 --%>
									<bean:define toScope="page" id="hayAcumular" value="true"></bean:define>
									<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector1" value="">
										<tr class="barraFiltrosForma">
											<td class="encabezadoListView" width="140px"><bean:write name="configurarVistaDatosForm" property="nombreSelector1" /></td>
											<logic:equal name="configurarVistaDatosForm" property="selector1Id" value="3">
												<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
												<td colspan="2">
													<table class="contenedorTextoSeleccion tablaSpacing0Padding0Width100Collapse" style="width: 180px">
														<tr>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.desde" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoDesde" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoDesde" value="valor" label="nombre" />
																</html:select>
															</td>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.hasta" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoHasta" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoHasta" value="valor" label="nombre" />
																</html:select>
															</td>
														</tr>
													</table>
												</td>
											</logic:equal>
											<logic:notEqual name="configurarVistaDatosForm" property="selector1Id" value="3">
												<td colspan="2">
													<html:select property="valorSelector1" styleClass="cuadroTexto">
													<html:option value="">-</html:option>
													<html:optionsCollection property="selector1" value="valor" label="nombre" />
													</html:select>
												</td>
											</logic:notEqual>
										</tr>
									</logic:notEqual>
			
									<%-- Selector 2 --%>
									<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector2" value="">
										<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
										<tr class="barraFiltrosForma">
											<td class="encabezadoListView" width="140px"><bean:write name="configurarVistaDatosForm" property="nombreSelector2" /></td>
											<logic:equal name="configurarVistaDatosForm" property="selector2Id" value="3">
												<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
												<td colspan="2">
													<table class="contenedorTextoSeleccion tablaSpacing0Padding0Width100Collapse" style="width: 180px">
														<tr>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.desde" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoDesde" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoDesde" value="valor" label="nombre" />
																</html:select>
															</td>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.hasta" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoHasta" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoHasta" value="valor" label="nombre" />
																</html:select>
															</td>
														</tr>
													</table>
												</td>
											</logic:equal>
											<logic:notEqual name="configurarVistaDatosForm" property="selector2Id" value="3">
												<td colspan="2">
													<html:select property="valorSelector2" styleClass="cuadroTexto">
													<html:option value="">-</html:option>
													<html:optionsCollection property="selector2" value="valor" label="nombre" />
													</html:select>
												</td>
											</logic:notEqual>
										</tr>
									</logic:notEqual>
			
									<%-- Selector 3 --%>
									<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector3" value="">
										<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
										<tr class="barraFiltrosForma">
											<td class="encabezadoListView" width="140px"><bean:write name="configurarVistaDatosForm" property="nombreSelector3" /></td>
											<logic:equal name="configurarVistaDatosForm" property="selector3Id" value="3">
												<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
												<td colspan="2">
													<table class="contenedorTextoSeleccion tablaSpacing0Padding0Width100Collapse" style="width: 180px">
														<tr>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.desde" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoDesde" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoDesde" value="valor" label="nombre" />
																</html:select>
															</td>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.hasta" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoHasta" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoHasta" value="valor" label="nombre" />
																</html:select>
															</td>
														</tr>
													</table>
												</td>
											</logic:equal>
											<logic:notEqual name="configurarVistaDatosForm" property="selector3Id" value="3">
												<td colspan="2">
													<html:select property="valorSelector3" styleClass="cuadroTexto">
													<html:option value="">-</html:option>
													<html:optionsCollection property="selector3" value="valor" label="nombre" />
													</html:select>
												</td>
											</logic:notEqual>
										</tr>
									</logic:notEqual>
			
									<%-- Selector 4 --%>
									<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector4" value="">
										<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
										<tr class="barraFiltrosForma">
											<td class="encabezadoListView" width="140px"><bean:write name="configurarVistaDatosForm" property="nombreSelector4" /></td>
											<logic:equal name="configurarVistaDatosForm" property="selector4Id" value="3">
												<bean:define toScope="page" id="hayAcumular" value="false"></bean:define>
												<td colspan="2">
													<table class="contenedorTextoSeleccion tablaSpacing0Padding0Width100Collapse" style="width: 180px">
														<tr>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.desde" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoDesde" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoDesde" value="valor" label="nombre" />
																</html:select>
															</td>
															<td style="width: 40px">
																<vgcutil:message key="jsp.mostrarvistadatos.tiempo.hasta" />
															</td>
															<td style="width: 50px">
																<html:select property="valorSelectorTiempoHasta" styleClass="cuadroTexto">
																<html:option value="">-</html:option>
																<html:optionsCollection property="selectorTiempoHasta" value="valor" label="nombre" />
																</html:select>
															</td>
														</tr>
													</table>
												</td>
											</logic:equal>
											<logic:notEqual name="configurarVistaDatosForm" property="selector4Id" value="3">
												<td colspan="2">
													<html:select property="valorSelector4" styleClass="cuadroTexto">
													<html:option value="">-</html:option>
													<html:optionsCollection property="selector4" value="valor" label="nombre" />
													</html:select>
												</td>
											</logic:notEqual>
										</tr>
									</logic:notEqual>
									
									<%-- Mostrar Total por Filas --%>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px"><html:checkbox property="showTotalFilas" styleClass="botonSeleccionMultiple" onclick="showTotalFilas_click(this.checked)"><vgcutil:message key="jsp.mostrarvistadatos.tabla.total.fila" /></html:checkbox></td>
										<td colspan="2">&nbsp;</td>
									</tr>

									<%-- Mostrar Total por Columnas --%>
									<tr class="barraFiltrosForma">
										<td class="encabezadoListView" width="140px"><html:checkbox property="showTotalColumnas" styleClass="botonSeleccionMultiple" onclick="showTotalColumnas_click(this.checked)"><vgcutil:message key="jsp.mostrarvistadatos.tabla.total.columna" /></html:checkbox></td>
										<td colspan="2">&nbsp;</td>
									</tr>
									
									<%-- Agregar opcion Acumular --%>
									<logic:equal scope="page" name="hayAcumular" value="true">
										<tr class="barraFiltrosForma">
											<td class="encabezadoListView" width="140px">
												<html:checkbox property="acumularPeriodos" styleClass="botonSeleccionMultiple">
													<vgcutil:message key="jsp.mostrarvistadatos.tabla.acumular.periodos" />
												</html:checkbox>
											</td>
											<td colspan="2">&nbsp;</td>
										</tr>
									</logic:equal>
								</table>
							</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<bean:define id="colorCelda" value="oscuro" />
				<bean:define id="estilo" value="#F4F4F4" />

				<logic:notEmpty name="configurarVistaDatosForm" property="filas">
					<vgclogica:tamanoColeccionMayorQue name="configurarVistaDatosForm" property="filas" value="0">
						<vgclogica:tamanoColeccionMayorQue name="configurarVistaDatosForm" property="columnas" value="0">
	
							<%-- Datos --%>
							<table id="tblVistaDatos" class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="1" id="datos" width="<bean:write name="configurarVistaDatosForm" property="anchoTablaDatos" />">
								<logic:iterate name="configurarVistaDatosForm" property="matrizDatos" scope="session" id="fila" indexId="filaIndex">
	
									<%-- Validaciones para determinar el color de la celda --%>
									<logic:equal name="colorCelda" value="oscuro">
										<bean:define id="estilo" value="#FFFFF2" />
									</logic:equal>
									<logic:equal name="colorCelda" value="claro">
										<bean:define id="estilo" value="#F4F4F4" />
									</logic:equal>
	
									<tr height="30px">
										<logic:iterate name="fila" id="columna" indexId="columnaIndex">
	
											<%-- Validaciones de la tabla --%>
											<bean:define id="esEncabezado" value="false" />
											<bean:define id="centrado" value="false" />
											<logic:equal name="columnaIndex" value="0">
												<bean:define id="esEncabezado" value="true" />
											</logic:equal>
											<logic:equal name="columna" property="esEncabezado" value="true">
												<bean:define id="esEncabezado" value="true" />
											</logic:equal>
											<logic:equal name="filaIndex" value="0">
												<bean:define id="esEncabezado" value="true" />
												<bean:define id="centrado" value="true" />
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
	
											<%-- Se escribe la celda correspondiente de la tabla --%>
											<logic:equal name="esEncabezado" value="true">
												<logic:equal name="centrado" value="true">
													<td width="<%=ancho%>px" class="encabezadoListView" align="center">
														<b><bean:write name="columna" property="valor" /></b>
													</td>
												</logic:equal>
												<logic:equal name="centrado" value="false">
													<td bgcolor="<%=estilo%>" width="<%=ancho%>" align="center">
														<b><bean:write name="columna" property="valor" /></b>
													</td>
												</logic:equal>
											</logic:equal>
	
											<%-- Validaciones para determinar el color de la celda --%>
											<logic:equal name="esEncabezado" value="false">
	
												<logic:equal name="columna" property="esAlerta" value="true">
													<td width="<%=ancho%>px" onmouseover="establecerEstilo(this);"
														onmouseout="quitarEstilo(this);" align="center" bgcolor="<%=estilo%>">
														<logic:notEqual name="columna" property="valor" value="">
															<vgcst:imagenAlertaIndicador name="columna" property="valor" />
														</logic:notEqual>
														<logic:empty name="columna" property="valor">
															<vgcst:imagenAlertaIndicador name="columna" property="valor" />
														</logic:empty> &nbsp;
													</td>
												</logic:equal>
	
												<logic:equal name="columna" property="esAlerta" value="false">
													<td width="<%=ancho%>px" onmouseover="establecerEstilo(this);"
														onmouseout="quitarEstilo(this);" align="center" bgcolor="<%=estilo%>">
														<bean:write name="columna" property="valor" />&nbsp;
													</td>
												</logic:equal>
											</logic:equal>
										</logic:iterate>
	
									</tr>
	
									<%-- Validaciones de la tabla --%>
									<logic:equal name="estilo" value="#FFFFF2">
										<bean:define id="colorCelda" value="claro" />
									</logic:equal>
									<logic:equal name="estilo" value="#F4F4F4">
										<bean:define id="colorCelda" value="oscuro" />
									</logic:equal>
	
								</logic:iterate>
							</table>
	
						</vgclogica:tamanoColeccionMayorQue>
					</vgclogica:tamanoColeccionMayorQue>
				</logic:notEmpty>

			</vgcinterfaz:contenedorForma>

		</html:form>
		<script>
			_showTotalFilas = false;
			_showTotalColumnas = false;
			<logic:equal name="configurarVistaDatosForm" property="showTotalFilas" value="true">
				_showTotalFilas = true;
			</logic:equal>
			<logic:equal name="configurarVistaDatosForm" property="showTotalColumnas" value="true">
				_showTotalColumnas = true;
			</logic:equal>
			
			// Organizacion
			<logic:equal name="configurarVistaDatosForm" property="showTablaParametro" value="true">
				<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector1" value="">
					<logic:equal name="configurarVistaDatosForm" property="selector1Id" value="6">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector1">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector1" value="">
								_organizacionId = document.configurarVistaDatosForm.valorSelector1.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
					<logic:equal name="configurarVistaDatosForm" property="selector1Id" value="3">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector1">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector1" value="">
								_tiempo = document.configurarVistaDatosForm.valorSelector1.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoDesde">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoDesde" value="">
								_tiempoDesde = document.configurarVistaDatosForm.valorSelectorTiempoDesde.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoHasta">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoHasta" value="">
								_tiempoHasta = document.configurarVistaDatosForm.valorSelectorTiempoHasta.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEqual>
				<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector2" value="">
					<logic:equal name="configurarVistaDatosForm" property="selector2Id" value="6">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector2">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector2" value="">
								_organizacionId = document.configurarVistaDatosForm.valorSelector2.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
					<logic:equal name="configurarVistaDatosForm" property="selector2Id" value="3">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector2">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector2" value="">
								_tiempo = document.configurarVistaDatosForm.valorSelector2.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoDesde">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoDesde" value="">
								_tiempoDesde = document.configurarVistaDatosForm.valorSelectorTiempoDesde.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoHasta">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoHasta" value="">
								_tiempoHasta = document.configurarVistaDatosForm.valorSelectorTiempoHasta.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEqual>
				<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector3" value="">
					<logic:equal name="configurarVistaDatosForm" property="selector3Id" value="6">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector3">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector3" value="">
								_organizacionId = document.configurarVistaDatosForm.valorSelector3.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
					<logic:equal name="configurarVistaDatosForm" property="selector3Id" value="3">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector3">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector3" value="">
								_tiempo = document.configurarVistaDatosForm.valorSelector3.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoDesde">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoDesde" value="">
								_tiempoDesde = document.configurarVistaDatosForm.valorSelectorTiempoDesde.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoHasta">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoHasta" value="">
								_tiempoHasta = document.configurarVistaDatosForm.valorSelectorTiempoHasta.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEqual>
				<logic:notEqual name="configurarVistaDatosForm" property="nombreSelector4" value="">
					<logic:equal name="configurarVistaDatosForm" property="selector4Id" value="6">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector4">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector4" value="">
								_organizacionId = document.configurarVistaDatosForm.valorSelector4.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
					<logic:equal name="configurarVistaDatosForm" property="selector4Id" value="3">
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelector4">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelector4" value="">
								_tiempo = document.configurarVistaDatosForm.valorSelector4.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoDesde">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoDesde" value="">
								_tiempoDesde = document.configurarVistaDatosForm.valorSelectorTiempoDesde.value; 
							</logic:notEqual>
						</logic:notEmpty>
						<logic:notEmpty name="configurarVistaDatosForm" property="valorSelectorTiempoHasta">
							<logic:notEqual name="configurarVistaDatosForm" property="valorSelectorTiempoHasta" value="">
								_tiempoHasta = document.configurarVistaDatosForm.valorSelectorTiempoHasta.value; 
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEqual>
				setVisualizarOcultarTabla(true);
			</logic:equal>
			<logic:notEqual name="configurarVistaDatosForm" property="showTablaParametro" value="true">
				setVisualizarOcultarTabla(false);
			</logic:notEqual>
		</script>
	</tiles:put>
</tiles:insert>
