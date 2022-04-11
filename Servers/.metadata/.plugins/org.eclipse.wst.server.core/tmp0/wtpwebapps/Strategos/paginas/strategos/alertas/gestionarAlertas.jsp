<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionaralertas.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			var _fechaDesde;
			var _fechaHasta;
			
			function buscar() 
			{
				if (validar())
					refrescar();
			}
			
			function validar()
			{
				if (document.gestionarAlertasForm.fechaDesde.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.vacio" /> ');
					return false;
				}

				if (document.gestionarAlertasForm.fechaHasta.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.vacio" /> ');
					return false;
				}
				
				if (document.gestionarAlertasForm.fechaDesde.value != "")
				{
			 		if (!fechaValida(document.gestionarAlertasForm.fechaDesde))
		 			{
			 			alert('<vgcutil:message key="jsp.general.alerta.fechadesde.invalido" /> ');
			 			return false;
		 			}
				}

				if (document.gestionarAlertasForm.fechaHasta.value != "")
				{
			 		if (!fechaValida(document.gestionarAlertasForm.fechaHasta))
		 			{
			 			alert('<vgcutil:message key="jsp.general.alerta.fechahasta.invalido" /> ');
			 			return false;
		 			}
				}
				
				if (document.gestionarAlertasForm.fechaDesde.value != "" && document.gestionarAlertasForm.fechaHasta.value != "")
				{
					var fecha1 = document.gestionarAlertasForm.fechaDesde.value.split("/");
					var fecha2 = document.gestionarAlertasForm.fechaHasta.value.split("/");
	
					var diaDesde = fecha1[0];
					var diaHasta = fecha2[0];
					
					var mesDesde = fecha1[1];
					var mesHasta = fecha2[1];
					
					var anoDesde = fecha1[2];
					var anoHasta = fecha2[2];
	
			 		var desde = parseInt(anoDesde + "" + (mesDesde.length == 1 ? ("0" + mesDesde) : mesDesde) + "" + (diaDesde.length == 1 ? ("0" + diaDesde) : diaDesde));
					var hasta = parseInt(anoHasta + "" + (mesHasta.length == 1 ? ("0" + mesHasta) : mesHasta) + "" + (diaHasta.length == 1 ? ("0" + diaHasta) : diaHasta));
	
					if (hasta<desde) 
					{
						alert('<vgcutil:message key="jsp.general.alerta.rango.fechas.invalido" /> ');
						return false;
					}
				}
		 		
				return true;
			}

			function limpiarBusqueda() 
			{
				document.gestionarAlertasForm.fechaDesde.value = _fechaDesde;
				document.gestionarAlertasForm.fechaHasta.value = _fechaHasta;
				document.gestionarAlertasForm.tipoEvento.value = '';
				document.gestionarAlertasForm.tipoEvento.options[0].value = '';
				document.gestionarAlertasForm.tipoEvento.selectedIndex = 0;
				document.gestionarAlertasForm.tipoEstatus.value = '';
				document.gestionarAlertasForm.tipoEstatus.options[0].value = '';
				document.gestionarAlertasForm.tipoEstatus.selectedIndex = 0;
				buscar();
			}
			
			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.gestionarAlertasForm.fechaDesde' , document.gestionarAlertasForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaDesde() 
			{
				document.gestionarAlertasForm.fechaDesde.value = '';
			}
			
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.gestionarAlertasForm.fechaHasta' , document.gestionarAlertasForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaHasta() 
			{
				document.gestionarAlertasForm.fechaHasta.value = '';
			}

			function actualizar() 
			{
				document.gestionarAlertasForm.submit();
			}
			
			function reporte() 
			{
				abrirReporte('<html:rewrite action="/alertas/generarReporteAlertas"/>', 'reporte');
			}
			
			function modificar(id)
			{
				if (id == undefined)
				{
					alert('<vgcutil:message key="jsp.gestionaralertas.alerta.id.empty" /> ');
					return;					
				}
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/alertas/salvarAlertas" />?funcion=completo&data=' + id, document.gestionarAlertasForm.respuesta, 'onModificar()');;
			}
			
			function onModificar()
			{
				if (document.gestionarAlertasForm.respuesta != null && document.gestionarAlertasForm.respuesta.value != null && document.gestionarAlertasForm.respuesta.value != "")
				{
					window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesMensaje','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
					refrescar();
				}
			}
			
			function verReporteLog()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarAlertasForm.seleccionados))
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/alertas/salvarAlertas" />?funcion=soloReporte&data=' + document.gestionarAlertasForm.seleccionados.value, document.gestionarAlertasForm.respuesta, 'onVerReporteLog()');
			}
			
			function onVerReporteLog()
			{
				if (document.gestionarAlertasForm.respuesta != null && document.gestionarAlertasForm.respuesta.value != null && document.gestionarAlertasForm.respuesta.value != "")
					window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesMensaje','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
			}
			
			function refrescar()
			{
				document.gestionarAlertasForm.action = '<html:rewrite action="/alertas/gestionarAlertas"/>';
				document.gestionarAlertasForm.submit();
			}
			
			function setVisto()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarAlertasForm.seleccionados))
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/alertas/salvarAlertas" />?funcion=soloModificar&data=' + document.gestionarAlertasForm.seleccionados.value, document.gestionarAlertasForm.respuesta, 'onSetVisto()');
			}
			
			function onSetVisto()
			{
				if (document.gestionarAlertasForm.respuesta != null && document.gestionarAlertasForm.respuesta.value != null && document.gestionarAlertasForm.respuesta.value != "")
					refrescar();
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<html:form action="/alertas/gestionarAlertas" styleClass="formaHtmlGestionar">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="respuesta" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-alertas">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionaralertas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
		
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:refrescar()
				</vgcinterfaz:contenedorFormaBotonActualizar>
		
				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>
					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="ALERTA_PRINT" aplicaOrganizacion="true" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Edicion --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.alerta.setvisto" onclick="setVisto();" permisoId="ALERTA_SET_VISTO" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">
								<vgcinterfaz:botonMenu key="menu.ver.reporte.log" onclick="verReporteLog();" permisoId="ALERTA_VER_LOG" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" agregarSeparador="true"/>
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								 --%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
		
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<vgcinterfaz:barraHerramientas nombre="barraGestionarAuditoriasEventos" agregarSeparadorInferior="true" agregarSeparadorSuperior="false">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="buscar" onclick="javascript:buscar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.buscar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="limpiarFiltro" pathImagenes="/componentes/barraHerramientas/" nombre="limpiarBusqueda" onclick="javascript:limpiarBusqueda();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.limpiar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
					
					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.gestionaralertas.filtro.fechadesde" /></td>
							<td><html:text property="fechaDesde" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.gestionaralertas.filtro.fechahasta" /></td>
							<td><html:text property="fechaHasta" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.gestionaralertas.columna.tipo.ejecucion" /></td>
							<td><html:select property="tipoEvento" styleClass="cuadroTexto" size="1">
								<html:option value="">
									<vgcutil:message key="jsp.gestionaralertas.filtro.todos" />
								</html:option>
								<html:optionsCollection property="tiposEventos" value="tipoEventoId" label="nombre" />
							</html:select></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.gestionaralertas.columna.tipo.estatus" /></td>
							<td><html:select property="tipoEstatus" styleClass="cuadroTexto" size="1">
								<html:option value="">
									<vgcutil:message key="jsp.gestionaralertas.filtro.todos" />
								</html:option>
								<html:optionsCollection property="tiposEstatus" value="tipoEstatusId" label="nombre" />
							</html:select></td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaAlertas" nombre="visorAlertas" messageKeyNoElementos="jsp.gestionaralertas.noalertas" seleccionSimple="true" nombreForma="gestionarAlertasForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="fecha" width="200px" onclick="javascript:consultar(self.document.gestionarAlertasForm,'fecha', null)">
						<vgcutil:message key="jsp.gestionaralertas.columna.fecha" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px" onclick="javascript:consultar(self.document.gestionarAlertasForm,'estatus', null)">
						<vgcutil:message key="jsp.gestionaralertas.columna.estatus" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="mensaje" width="500px" onclick="javascript:consultar(self.document.gestionarAlertasForm,'mensaje', null)">
						<vgcutil:message key="jsp.gestionaralertas.columna.mensaje" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="fuente" width="200px" onclick="javascript:consultar(self.document.gestionarAlertasForm,'fuente', null)">
						<vgcutil:message key="jsp.gestionaralertas.columna.fuente" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="message">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="message" property="id" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha" align="center">
							<logic:equal name="message" property="estatus" value="1">
								<b><p ondblclick="javascript:modificar('<bean:write name="message" property="id" />');"><bean:write name="message" property="fechaFormateada" /></p></b>
							</logic:equal>
							<logic:equal name="message" property="estatus" value="2">
								<bean:write name="message" property="fechaFormateada" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="left">
							<logic:equal name="message" property="estatus" value="1">
								<b><p ondblclick="javascript:modificar('<bean:write name="message" property="id" />');"><bean:write name="message" property="estatusNombre" /></p></b>
							</logic:equal>
							<logic:equal name="message" property="estatus" value="2">
								<bean:write name="message" property="estatusNombre" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>					
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="mensaje">
							<logic:equal name="message" property="estatus" value="1">
								<b><p ondblclick="javascript:modificar('<bean:write name="message" property="id" />');"><bean:write name="message" property="mensaje" /></p></b>
							</logic:equal>
							<logic:equal name="message" property="estatus" value="2">
								<bean:write name="message" property="mensaje" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fuente" align="left">
							<logic:equal name="message" property="estatus" value="1">
								<b><p ondblclick="javascript:modificar('<bean:write name="message" property="id" />');"><bean:write name="message" property="fuente" /></p></b>
							</logic:equal>
							<logic:equal name="message" property="estatus" value="2">
								<bean:write name="message" property="fuente" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>					
					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaAlertas" labelPage="inPagina" action="javascript:consultar(gestionarAlertasForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarAlertasForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarAlertasForm" property="atributoOrden" />  [<bean:write name="gestionarAlertasForm" property="tipoOrden" />]
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			_fechaDesde = document.gestionarAlertasForm.fechaDesde.value;
			_fechaHasta = document.gestionarAlertasForm.fechaHasta.value;
		</script>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-alertas'), 320);	

			var visor = document.getElementById('visorAlertas');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>

	</tiles:put>

</tiles:insert>
