<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.configurarvistadatos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="configurarReporteGraficoForm" property="bloqueado">
				<bean:write name="configurarReporteGraficoForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="configurarReporteGraficoForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>
	
		<script type="text/javascript">
		
		function cancelar() 
		{
			javascript:irAtras(1);
		}
		
		function guardar()
		{
			if (!validar())
				return;
		    
			// evita que se pierdan los miembros de las dimensiones seleccionadas
		    setTextoMiembrosDimension(); 	
		    document.configurarReporteGraficoForm.textoSelectores.value = asignarSelectores(); 
		 
		    window.document.configurarReporteGraficoForm.action = '<html:rewrite action="/reportesgrafico/guardarConfiguracionReporteGrafico"/>?reporteId=' + document.configurarReporteGraficoForm.reporteId.value + configuracion();
		    window.document.configurarReporteGraficoForm.submit();	    
		}
		
		function validar() 
		{
		 	if (window.document.configurarReporteGraficoForm.nombre.value == "")
	 		{
	 			alert('<vgcutil:message key="jsp.configurarvistadatos.alerta.nombre.vacio" /> ');
	 			return false;
	 		}
		 	
		 	return true;
		}
		
		function configuracion()
		{
			var campos = '&showTotalFilas=' + document.configurarReporteGraficoForm.showTotalFilas.value;
			campos = campos + '&showTotalColumnas=' + document.configurarReporteGraficoForm.showTotalColumnas.value;
			
			return campos;
		}
		
		function mostrar() 
		{
			if (!validar())
				return;

			// evita que se pierdan los miembros de las dimensiones seleccionadas 
		    setTextoMiembrosDimension(); 	
		    document.configurarReporteGraficoForm.textoSelectores.value = asignarSelectores(); 
		 
		    window.document.configurarReporteGraficoForm.action = '<html:rewrite action="/reportesgrafico/mostrarReporteGrafico"/>?ts=<%= (new java.util.Date()).getTime() %>&source=2' + configuracion();
		    window.document.configurarReporteGraficoForm.submit();
		}

		function resizePaneles()
		{
			var alto = marcoVistaGeneral.style.height.replace("px", "");
			var panel_derecho = document.getElementById('panel-derecho');
			if (panel_derecho != null)
			{
				var anchoDerecho = (((_myWidth - 160) * 60) / 100);
				panel_derecho.style.width = anchoDerecho + "px";
				panel_derecho.style.height = (parseInt(alto)) + "px";
				var panel_izquierdo = document.getElementById('panel-izquierdo');
				if (panel_izquierdo != null)
				{
					panel_izquierdo.style.width = ((_myWidth - 160) - anchoDerecho) + "px";
					panel_izquierdo.style.height = (parseInt(alto)) + "px";
				}
				var panel_izquierdo_visualizar = document.getElementById('panel-izquierdo-visualizar');
				if (panel_izquierdo_visualizar != null)
				{
					panel_izquierdo_visualizar.style.width = (((_myWidth - 160) - anchoDerecho) - 20) + "px";
					panel_izquierdo_visualizar.style.height = (parseInt(alto) - 50) + "px";  
				}

				var panel_derecho_superior = document.getElementById('panel-derecho-superior');
				var panel_derecho_inferior = document.getElementById('panel-derecho-inferior');
				var altoPaneles = alto / 2;
				if (panel_derecho_superior != null)
				{
					panel_derecho_superior.style.height = altoPaneles + "px";
					var pTabla = document.getElementById('panel-derecho-superior-tabla');
					if (pTabla != null)
						pTabla.style.width = (anchoDerecho - 20) + "px";
				}
				if (panel_derecho_inferior != null)
				{
					panel_derecho_inferior.style.height = altoPaneles + "px";
					var pTabla = document.getElementById('panel-derecho-inferior-tabla');
					if (pTabla != null)
						pTabla.style.width = (anchoDerecho - 20) + "px";
				}
			}
		}
		
		function seleccionarFechaDesde() 
		{
			_calendario = mostrarCalendario('document.configurarReporteGraficoForm.fechaInicial' , document.configurarReporteGraficoForm.fechaInicial.value, '<vgcutil:message key="formato.fecha.corta" />', false);
		}
	
		function seleccionarFechaHasta() 
		{
			_calendario = mostrarCalendario('document.configurarReporteGraficoForm.fechaFinal' , document.configurarReporteGraficoForm.fechaFinal.value, '<vgcutil:message key="formato.fecha.corta" />', false);
		}

			
		</script>
		
		
		

		<html:form action="/reportesgrafico/configurarReporteGrafico" styleClass="formaHtml">
			<html:hidden property="reporteId" />
			<html:hidden property="corte" />
			<html:hidden property="showTotalFilas" />
			<html:hidden property="showTotalColumnas" />
			
			<vgcinterfaz:contenedorForma idContenedor="body-reporte-logitudinal">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.configurarreportegrafico.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar();
				</vgcinterfaz:contenedorFormaBotonRegresar>

				
				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
		
					<vgcinterfaz:barraHerramientas nombre="barraGestionarVista">
						
						<logic:notEqual name="configurarReporteGraficoForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar.configuracion" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
					</vgcinterfaz:barraHerramientas>

					
					<%-- Edicion --%>
					<div id="marcoVistaBasica" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: hidden">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="barraFiltrosForma" height="10px">
								<td colspan="3">
								</td>
							</tr>
							<tr class="barraFiltrosForma">
								<td valign="top" width="500px" style="padding-left: 30px;">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr class="barraFiltrosForma">
											<td width="65px"><vgcutil:message key="jsp.listardimensiones.frecuencia" />
											</td>
											<td>
												<html:select disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="frecuencia" styleClass="cuadroTexto">
													<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
												</html:select>
												
											</td>
										</tr>
										<tr class="barraFiltrosForma">
											<%-- Nombre --%>
											<td width="65px"><vgcutil:message key="jsp.configurarvistadatos.nombre" /></td>
											<td>
												<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="50" styleClass="cuadroTexto" />
												
											</td>
										</tr>
										
										<tr class="barraFiltrosForma">
											<td><vgcutil:message key="jsp.grafico.permiso" /></td>
											<td><html:select property="publico" styleClass="cuadroTexto" size="1">
												<html:option value="false">
													<vgcutil:message key="jsp.configurarvistadatos.admin" />
												</html:option>
												<html:option value="true">
													<vgcutil:message key="jsp.configurarvistadatos.publico" />
												</html:option>
											</html:select></td>
											<td width="30px">&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										
										<!--  
										<tr class="barraFiltrosForma">
											<td><vgcutil:message key="jsp.grafico.tipo" /></td>
											<td><html:select property="tipo" styleClass="cuadroTexto" size="1">
												<html:option value="1">
													<vgcutil:message key="jsp.asistente.grafico.tipo.lineas" />
												</html:option>
												<html:option value="2">
													<vgcutil:message key="jsp.asistente.grafico.tipo.columnas" />
												</html:option>
												<html:option value="3">
													<vgcutil:message key="jsp.asistente.grafico.tipo.barras" />
												</html:option>
												<html:option value="4">
													<vgcutil:message key="jsp.asistente.grafico.tipo.area" />
												</html:option>
												<html:option value="5">
													<vgcutil:message key="jsp.asistente.grafico.tipo.torta" />
												</html:option>
												<html:option value="14">
													<vgcutil:message key="jsp.asistente.grafico.tipo.gauge" />
												</html:option>
												<html:option value="6">
													<vgcutil:message key="jsp.asistente.grafico.tipo.pareto" />
												</html:option>
												<html:option value="8">
													<vgcutil:message key="jsp.asistente.grafico.tipo.combinado" />
												</html:option>
												<html:option value="9">
													<vgcutil:message key="jsp.asistente.grafico.tipo.cascada" />
												</html:option>
												<html:option value="10">
													<vgcutil:message key="jsp.asistente.grafico.tipo.torta3d" />
												</html:option>
												<html:option value="11">
													<vgcutil:message key="jsp.asistente.grafico.tipo.barra3d" />
												</html:option>
												<html:option value="12">
													<vgcutil:message key="jsp.asistente.grafico.tipo.barraapilada3d" />
												</html:option>
												
											</html:select></td>
											<td width="30px">&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										-->
										
										<tr class="barraFiltrosForma">
								
									<td width="10px"><vgcutil:message key="jsp.seleccionartiempo.desde" /></td>
	
									<td width="10px">
		
									<table width="100%" cellpadding="1" cellspacing="0">
										<tr>
											<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.periodo" /></td>
											<td><html:select property="periodoInicial" styleClass="cuadroTexto">
												<html:option value=""></html:option>
												<html:optionsCollection property="listaPeriodos" value="valor" label="nombre" />
											</html:select></td>
											<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.ano" /></td>
											<td><html:select property="anoInicial" styleClass="cuadroTexto">
												<html:option value=""></html:option>
												<html:optionsCollection property="listaAnos" value="valor" label="nombre" />
											</html:select></td>
										</tr>
									</table>
		
									</td>
								
								</tr>
								
								<tr class="barraFiltrosForma">
								
									<td width="10px"><vgcutil:message key="jsp.seleccionartiempo.hasta" /></td>
	
									<td width="10px">
		
									<table width="100%" cellpadding="1" cellspacing="0">
										<tr>
											<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.periodo" /></td>
											<td><html:select property="periodoFinal" styleClass="cuadroTexto">
												<html:option value=""></html:option>
												<html:optionsCollection property="listaPeriodos" value="valor" label="nombre" />
											</html:select></td>
											<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.ano" /></td>
											<td><html:select property="anoFinal" styleClass="cuadroTexto">
												<html:option value=""></html:option>
												<html:optionsCollection property="listaAnos" value="valor" label="nombre" />
											</html:select></td>
										</tr>
									</table>
		
									</td>
								</tr>	
												
																				
									</table>
								</td>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="barraFiltrosForma">
											<%-- Descripcion --%>
											<td width="75px" valign="top"><vgcutil:message key="jsp.configurarvistadatos.descripcion" /></td>
											<td>
												<html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="6" cols="80" property="descripcion" styleClass="cuadroTexto" />
											</td>
										</tr>
									</table>
								</td>
								
								<!-- periodos -->
								
															
							</tr>
							
							
							
							<tr class="barraFiltrosForma" height="10px">
								<td colspan="3">
								</td>
							</tr>
						</table>
					</div>
					
		
				</vgcinterfaz:contenedorFormaBarraGenerica>
				
				<div id="marcoVistaGeneral" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: hidden">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td id="panel-derecho" width="60%" valign="top">
								<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="100%">
										<td id="panel-derecho-superior" valign="top" width="20%">
											<jsp:include flush="true" page="/paginas/strategos/reportesgrafico/listarDimensionesGrafico.jsp"></jsp:include>
										</td>
										
										<td id="panel-derecho-inferior" valign="top" width="80%">
											<jsp:include flush="true" page="/paginas/strategos/reportesgrafico/listarMiembrosGrafico.jsp"></jsp:include>
											<jsp:include flush="true" page="/paginas/strategos/reportesgrafico/visualizarReporteGrafico.jsp"></jsp:include>
										</td>
									</tr>
									
								</table>
							</td>
							
					</table>
				</div>
				
				
			</vgcinterfaz:contenedorForma>
			
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-reporte-logitudinal'), 280);
			resizeAlto(document.getElementById('marcoVistaGeneral'), 290);
			
			marcoVistaGeneral.style.width = (_myWidth - 160) + "px";
			resizePaneles();
		</script>

	</tiles:put>

</tiles:insert>
