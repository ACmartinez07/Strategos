<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (29/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.framework.gestionarservicios.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

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
			if (document.gestionarServiciosForm.fechaDesde.value == "")
			{
				alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.vacio" /> ');
				return false;
			}

			if (document.gestionarServiciosForm.fechaHasta.value == "")
			{
				alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.vacio" /> ');
				return false;
			}

			if (document.gestionarServiciosForm.fechaDesde.value != "")
			{
		 		if (!fechaValida(document.gestionarServiciosForm.fechaDesde))
	 			{
		 			alert('<vgcutil:message key="jsp.general.alerta.fechadesde.invalido" /> ');
		 			return false;
	 			}
			}

			if (document.gestionarServiciosForm.fechaHasta.value != "")
			{
		 		if (!fechaValida(document.gestionarServiciosForm.fechaHasta))
	 			{
		 			alert('<vgcutil:message key="jsp.general.alerta.fechahasta.invalido" /> ');
		 			return false;
	 			}
			}
			
			if (document.gestionarServiciosForm.fechaDesde.value != "" && document.gestionarServiciosForm.fechaHasta.value != "")
			{
				var fecha1 = document.gestionarServiciosForm.fechaDesde.value.split("/");
				var fecha2 = document.gestionarServiciosForm.fechaHasta.value.split("/");

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
			document.gestionarServiciosForm.fechaDesde.value = '';
			document.gestionarServiciosForm.fechaHasta.value = '';
			document.gestionarServiciosForm.tipoEvento.value = '';
			document.gestionarServiciosForm.tipoEvento.options[0].value = '';
			document.gestionarServiciosForm.tipoEvento.selectedIndex = 0;
			document.gestionarServiciosForm.tipoEstatus.value = '';
			document.gestionarServiciosForm.tipoEstatus.options[0].value = '';
			document.gestionarServiciosForm.tipoEstatus.selectedIndex = 0;
			document.gestionarServiciosForm.usuarioId.value = '';
			document.gestionarServiciosForm.usuarioNombre.value = '';
			buscar();
		}
		
		function seleccionarFechaDesde() 
		{
			mostrarCalendario('document.gestionarServiciosForm.fechaDesde' , document.gestionarServiciosForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
		}
		
		function limpiarFechaDesde() 
		{
			document.gestionarServiciosForm.fechaDesde.value = '';
		}
		
		function seleccionarFechaHasta() 
		{
			mostrarCalendario('document.gestionarServiciosForm.fechaHasta' , document.gestionarServiciosForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
		}
		
		function limpiarFechaHasta() 
		{
			document.gestionarServiciosForm.fechaHasta.value = '';
		}
		
		function refrescar()
		{
			document.gestionarServiciosForm.action = '<html:rewrite action="/framework/servicio/gestionarServicios"/>';
			document.gestionarServiciosForm.submit();
		}

		function setServicio() 
		{
			abrirVentanaModal('<html:rewrite action="/framework/servicio/configurarServicio" />', "Servicio", 610, 310);
		}
		
		
		function verLog() 
		{
			if (verificarElementoUnicoSeleccionMultiple(document.gestionarServiciosForm.seleccionados))
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/servicio/salvarServicio" />?funcion=soloReporte&data=' + document.gestionarServiciosForm.seleccionados.value, document.gestionarServiciosForm.respuesta, 'onVerLog()');
		}
		
		function onVerLog()
		{
			if (document.gestionarServiciosForm.respuesta != null && document.gestionarServiciosForm.respuesta.value != null && document.gestionarServiciosForm.respuesta.value != "")
				window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesServicio','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
		}
		
		function seleccionarUsuario() 
		{
			abrirSelectorUsuarios('gestionarServiciosForm', 'usuarioNombre', 'usuarioId', null, null, null, null);
		}
		
		function limpiarUsuario() 
		{
			document.gestionarServiciosForm.usuarioId.value = '';
			document.gestionarServiciosForm.usuarioNombre.value = '';
		}

		function modificar(id)
		{
			if (id == undefined)
			{
				alert('<vgcutil:message key="jsp.framework.gestionarservicios.alerta.id.empty" /> ');
				return;					
			}
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/servicio/salvarServicio" />?funcion=completo&data=' + id, document.gestionarServiciosForm.respuesta, 'onModificar()');;
		}
		
		function onModificar()
		{
			if (document.gestionarServiciosForm.respuesta != null && document.gestionarServiciosForm.respuesta.value != null && document.gestionarServiciosForm.respuesta.value != "")
			{
				window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesServicio','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
				refrescar();
			}
		}
		
		function setVisto()
		{
			if (verificarElementoUnicoSeleccionMultiple(document.gestionarServiciosForm.seleccionados))
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/servicio/salvarServicio" />?funcion=soloModificar&data=' + document.gestionarServiciosForm.seleccionados.value, document.gestionarServiciosForm.respuesta, 'onSetVisto()');
		}
		
		function onSetVisto()
		{
			if (document.gestionarServiciosForm.respuesta != null && document.gestionarServiciosForm.respuesta.value != null && document.gestionarServiciosForm.respuesta.value != "")
				refrescar();
		}
		
		</script>
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<html:form action="/framework/servicio/gestionarServicios" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="respuesta" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.gestionarservicios.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:refrescar()
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<jsp:include page="/paginas/framework/menu/menuVer.jsp"></jsp:include>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Configurar --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.servicio" onclick="setServicio();" permisoId="SERVICIO_SET" agregarSeparador="true"/>
								<vgcinterfaz:botonMenu key="menu.herramientas.servicio.log" onclick="verLog();" permisoId="SERVICIO_LOG"/>
								<vgcinterfaz:botonMenu key="menu.herramientas.servicio.setvisto" onclick="setVisto();" permisoId="SERVICIO_SET_VISTO"/>
								
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								 --%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<vgcinterfaz:barraHerramientas nombre="barraGestionarServicios" agregarSeparadorInferior="true" agregarSeparadorSuperior="false">
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
							<td width="150px"><vgcutil:message key="jsp.framework.gestionarservicios.filtro.fechadesde" /></td>
							<td colspan="2"><html:text property="fechaDesde" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.framework.gestionarservicios.filtro.fechahasta" /></td>
							<td colspan="2"><html:text property="fechaHasta" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.framework.gestionarservicios.columna.tipo.ejecucion" /></td>
							<td colspan="2"><html:select property="tipoEvento" styleClass="cuadroTexto" size="1">
								<html:option value="">
									<vgcutil:message key="jsp.framework.gestionarservicios.filtro.todos" />
								</html:option>
								<html:optionsCollection property="tiposEventos" value="tipoEventoId" label="nombre" />
							</html:select></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="150px"><vgcutil:message key="jsp.framework.gestionarservicios.columna.tipo.estatus" /></td>
							<td colspan="2">
								<html:select property="tipoEstatus" styleClass="cuadroTexto" size="1">
									<html:option value="">
										<vgcutil:message key="jsp.framework.gestionarservicios.filtro.todos" />
									</html:option>
									<html:optionsCollection property="tiposEstatus" value="tipoEstatusId" label="nombre" />
								</html:select>
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarservicios.columna.responsable" /></td>
							<td width="320px"><html:text property="usuarioNombre" styleClass="cuadroTexto" size="50" /></td>
							<td>
								<html:hidden property="usuarioId" />
								<img style="cursor:pointer" onclick="seleccionarUsuario()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Usuario'>&nbsp;<img style="cursor:pointer" onclick="limpiarUsuario()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>


				<vgcinterfaz:visorLista namePaginaLista="paginaServicios" nombre="visorServicios" messageKeyNoElementos="jsp.framework.gestionarservicios.noservicios" seleccionSimple="true" nombreForma="gestionarServiciosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="fecha" width="150px" onclick="javascript:consultar(self.document.gestionarServiciosForm, 'fecha', null)">
						<vgcutil:message key="jsp.framework.gestionarservicios.columna.ts" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="servicio" width="200px" onclick="javascript:consultar(self.document.gestionarServiciosForm, 'nombre', null)">
						<vgcutil:message key="jsp.framework.gestionarservicios.columna.servicio" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px" onclick="javascript:consultar(self.document.gestionarServiciosForm, 'estatus', null)">
						<vgcutil:message key="jsp.framework.gestionarservicios.columna.estatus" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="responsable" width="200px" onclick="javascript:consultar(self.document.gestionarServiciosForm, 'usuarioId', null)">
						<vgcutil:message key="jsp.framework.gestionarservicios.columna.responsable" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="450px" onclick="javascript:consultar(self.document.gestionarServiciosForm, 'mensaje', null)">
						<vgcutil:message key="jsp.framework.gestionarservicios.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="servicio">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="servicio" property="servicioId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha">
							<logic:notEqual name="servicio" property="estatus" value="6">
								<b><p ondblclick="javascript:modificar('<bean:write name="servicio" property="servicioId" />');"><bean:write name="servicio" property="fechaFormateada" /></p></b>
							</logic:notEqual>
							<logic:equal name="servicio" property="estatus" value="6">
								<bean:write name="servicio" property="fechaFormateada" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="servicio">
							<logic:notEqual name="servicio" property="estatus" value="6">
								<b><p ondblclick="javascript:modificar('<bean:write name="servicio" property="servicioId" />');"><bean:write name="servicio" property="nombre" /></p></b>
							</logic:notEqual>
							<logic:equal name="servicio" property="estatus" value="6">
								<bean:write name="servicio" property="nombre" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus">
							<logic:notEqual name="servicio" property="estatus" value="6">
								<b><p ondblclick="javascript:modificar('<bean:write name="servicio" property="servicioId" />');"><bean:write name="servicio" property="statusNombre" /></p></b>
							</logic:notEqual>
							<logic:equal name="servicio" property="estatus" value="6">
								<bean:write name="servicio" property="statusNombre" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsable">
							<logic:notEqual name="servicio" property="estatus" value="6">
								<b><p ondblclick="javascript:modificar('<bean:write name="servicio" property="servicioId" />');"><bean:write name="servicio" property="responsable" /></p></b>
							</logic:notEqual>
							<logic:equal name="servicio" property="estatus" value="6">
								<bean:write name="servicio" property="responsable" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>				
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<logic:notEqual name="servicio" property="estatus" value="6">
								<b><p ondblclick="javascript:modificar('<bean:write name="servicio" property="servicioId" />');"><bean:write name="servicio" property="descripcionCorta" /></p></b>
							</logic:notEqual>
							<logic:equal name="servicio" property="estatus" value="6">
								<bean:write name="servicio" property="descripcionCorta" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaServicios" labelPage="inPagina" action="javascript:consultar(gestionarServiciosForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarServiciosForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaServicios" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			_fechaDesde = document.gestionarServiciosForm.fechaDesde.value;
			_fechaHasta = document.gestionarServiciosForm.fechaHasta.value;
		</script>

	</tiles:put>

</tiles:insert>
