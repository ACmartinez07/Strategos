<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (11/12/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.registrarseguimiento.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<script language="Javascript1.1">

	function crearBoton(nombreBoton, accionBoton) {
		var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
					+ nombreBoton + '</a>';

		return boton;
	}

	function mostrarBotones(paso) {
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

		switch (paso) {
		case 1:
			botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
			botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;  
			break;
		case 2:
			botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
			botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
			botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
			break;
		case 3:
			botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
			botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
			botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
			break;
		}
		var barraBotones = document.getElementById('barraBotones');
		barraBotones.innerHTML = botones;
    }

	var pasoActual = <bean:write scope="session" name="registrarSeguimientoForm" property="pasoActual" />;

	function mostrarPanelSeguimiento(paso) {
		mostrarBotones(paso);
		switch (pasoActual) {
			case 1:
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorRegistroSeguimiento" nombrePanel="panelSeguimientosEfectuados" ></vgcinterfaz:mostrarPanelContenedorJs>
			break;
			case 2:
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorRegistroSeguimiento" nombrePanel="panelSeguimiento" ></vgcinterfaz:mostrarPanelContenedorJs>
			break;
			case 3:
				<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorRegistroSeguimiento" nombrePanel="panelResumenSeguimiento" ></vgcinterfaz:mostrarPanelContenedorJs>
			break;
		}
	}

	function siguiente() {
		if (pasoActual == 3) {
			guardar();
			return;
		}
		pasoActual++;
		mostrarPanelSeguimiento(pasoActual);
	}

	function previo() {
		if (pasoActual == 1) {
			alert('No Válido');
			return;
		}
		pasoActual = pasoActual - 1;
		mostrarPanelSeguimiento(pasoActual);
	}

	function guardar() {
		window.document.registrarSeguimientoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/guardarRegistroSeguimiento"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
		window.document.registrarSeguimientoForm.submit();
	}

	function cancelar() {
		window.document.registrarSeguimientoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/cancelarGuardarRegistroSeguimiento"/>';
		window.document.registrarSeguimientoForm.submit();
	}

	function eventoClickSeguimiento(nombreObjetoFila) {
		var objetoFila = document.getElementById(nombreObjetoFila);
		if (objetoFila != null) {
			eventoClickFilaV2(document.registrarSeguimientoForm.seguimientoSeleccionadoId, null, 'visorSeguimientos', objetoFila);
			document.registrarSeguimientoForm.actualizarSeguimientoSeleccionado.value = 'true';
			window.document.registrarSeguimientoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/actualizarRegistroSeguimiento"/>?pasoActual=1';
			window.document.registrarSeguimientoForm.submit();
		}
	}

	function setSeguimientoSeleccionado(nombreObjetoFila) {
		var objetoFila = document.getElementById(nombreObjetoFila);
		if (objetoFila != null) {
			eventoClickFilaV2(document.registrarSeguimientoForm.seguimientoSeleccionadoId, null, 'visorSeguimientos', objetoFila);
		}
	}

	function eliminarSeguimiento() {
		document.registrarSeguimientoForm.eliminarSeguimientoSeleccionado.value = 'true';
		window.document.registrarSeguimientoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/actualizarRegistroSeguimiento"/>?pasoActual=1';
		window.document.registrarSeguimientoForm.submit();
	}

	function imprimirSeguimiento() {
		var seguimientoSeleccionadoId = document.registrarSeguimientoForm.seguimientoSeleccionadoId.value;
		var ano = seguimientoSeleccionadoId.substring('ano'.length, seguimientoSeleccionadoId.indexOf('periodo'));
		var periodo = seguimientoSeleccionadoId.substring(seguimientoSeleccionadoId.indexOf('periodo') + 'periodo'.length);
		abrirReporte('<html:rewrite action="/planificacionseguimiento/productos/generarReporteDetalladoIniciativaPorProductos" />?iniciativaId=<bean:write scope="session" name="registrarSeguimientoForm" property="iniciativaId" />&ano=' + ano + '&periodo=' + periodo, 'reporte');
	}

	function seleccionarFecha() {
		mostrarCalendarioConFuncionCierre('document.registrarSeguimientoForm.fecha' , document.registrarSeguimientoForm.fecha.value, '<vgcutil:message key="formato.fecha.corta" />', 'cierreFecha()');
	}

	function cierreFecha() {
		window.document.registrarSeguimientoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/actualizarRegistroSeguimiento"/>?pasoActual=2';
		window.document.registrarSeguimientoForm.submit();
	}

	function seleccionarFechaReprogramada() {
		mostrarCalendarioConFuncionCierre('document.registrarSeguimientoForm.fechaReprogramada' , document.registrarSeguimientoForm.fechaReprogramada.value, '<vgcutil:message key="formato.fecha.corta" />', 'cierreFechaReprogramada()');
	}

	function cierreFechaReprogramada() {
		actualizarAtributosProductoSeleccionado();
	}


	var separadorSegPrd = '<bean:write scope="session" name="registrarSeguimientoForm" property="separadorSegPrd" />';

	function setAtributosProductoSeleccionado() {
		var productoId = document.registrarSeguimientoForm.productoSeleccionadoId.value;
		var seguimientosProductos = document.registrarSeguimientoForm.seguimientosProductos.value;
		var valorBuscado = 'productoId' + productoId + 'alerta';
		
		var index = seguimientosProductos.indexOf(valorBuscado);
		
		if (index > -1) {
			seguimientosProductos = seguimientosProductos.substring(index + valorBuscado.length, seguimientosProductos.length);
			valorBuscado = 'programado';
			index = seguimientosProductos.indexOf(valorBuscado);
			if (index > -1) {
				var alerta = seguimientosProductos.substring(0, index);
				var urlImages = '<html:rewrite page="/paginas/strategos/indicadores/imagenes/"/>';
				if (alerta == '<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEnEsperaComienzo"/>') {
					document.images['imgAlertaProducto' + productoId].src = urlImages + "alertaBlanca.gif";
				} else if (alerta == '<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEntregado"/>') {
					document.images['imgAlertaProducto' + productoId].src = urlImages + "alertaVerde.gif";
				} else if (alerta == '<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoNoEntregado"/>') {
					document.images['imgAlertaProducto' + productoId].src = urlImages + "alertaRoja.gif";
				}
				for (i = 0; i < document.registrarSeguimientoForm.alertaProducto.length; i++) {
					if (document.registrarSeguimientoForm.alertaProducto[i].value == alerta) {
						document.registrarSeguimientoForm.alertaProducto[i].checked = true;
					}
				}
				seguimientosProductos = seguimientosProductos.substring(index + valorBuscado.length, seguimientosProductos.length);
				valorBuscado = 'reprogramado';
				index = seguimientosProductos.indexOf(valorBuscado);
				if (index > -1) {
					var programado = seguimientosProductos.substring(0, index);
					document.registrarSeguimientoForm.fechaProgramada.value = programado;
					seguimientosProductos = seguimientosProductos.substring(index + valorBuscado.length, seguimientosProductos.length);
					valorBuscado = separadorSegPrd;
					index = seguimientosProductos.indexOf(valorBuscado);
					if (index > -1) {
						var reprogramado = seguimientosProductos.substring(0, index);
						document.registrarSeguimientoForm.fechaReprogramada.value = reprogramado;
					} else {
						document.registrarSeguimientoForm.fechaReprogramada.value = seguimientosProductos;
					}
				}
			}
		}
	}

	function actualizarAtributosProductoSeleccionado() {
		var productoId = document.registrarSeguimientoForm.productoSeleccionadoId.value;
		var seguimientosProductos = document.registrarSeguimientoForm.seguimientosProductos.value;
		var valorBuscado = 'productoId' + productoId + 'alerta';

		var index = seguimientosProductos.indexOf(valorBuscado);
		
		if (index > -1) {
			var resultado = seguimientosProductos.substring(0, index + valorBuscado.length);
			seguimientosProductos = seguimientosProductos.substring(index + valorBuscado.length, seguimientosProductos.length);
			for (i = 0; i < document.registrarSeguimientoForm.alertaProducto.length; i++) {
				if (document.registrarSeguimientoForm.alertaProducto[i].checked) {
					resultado = resultado + document.registrarSeguimientoForm.alertaProducto[i].value;
				}
			}
			valorBuscado = 'programado';
			index = seguimientosProductos.indexOf(valorBuscado);
			if (index > -1) {
				resultado = resultado + 'programado' + document.registrarSeguimientoForm.fechaProgramada.value;
				seguimientosProductos = seguimientosProductos.substring(index + valorBuscado.length, seguimientosProductos.length);
				valorBuscado = 'reprogramado';
				index = seguimientosProductos.indexOf(valorBuscado);
				if (index > -1) {
					resultado = resultado + 'reprogramado' + document.registrarSeguimientoForm.fechaReprogramada.value;
					valorBuscado = separadorSegPrd;
					index = seguimientosProductos.indexOf(valorBuscado);
					if (index > -1) {
						resultado = resultado + seguimientosProductos.substring(index, seguimientosProductos.length);
					}
				}
			}
		}
		document.registrarSeguimientoForm.seguimientosProductos.value = resultado;
		setAtributosProductoSeleccionado();
	}

	function eventoClickProducto(nombreObjetoFila) {
		var objetoFila = document.getElementById(nombreObjetoFila);
		eventoClickFilaV2(document.registrarSeguimientoForm.productoSeleccionadoId, null, 'visorProductos', objetoFila);
		setAtributosProductoSeleccionado();
	}

</script>

		<bean:define id="alertaProductoEnEsperaComienzo" toScope="page">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEnEsperaComienzo" />
		</bean:define>

		<bean:define id="alertaProductoEntregado" toScope="page">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEntregado" />
		</bean:define>

		<bean:define id="alertaProductoNoEntregado" toScope="page">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoNoEntregado" />
		</bean:define>

		<bean:define id="alertaIniciativaProductoEnEsperaComienzo">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaIniciativaProductoEnEsperaComienzo" />
		</bean:define>

		<bean:define id="alertaIniciativaProductoVerde">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaIniciativaProductoVerde" />
		</bean:define>

		<bean:define id="alertaIniciativaProductoAmarilla">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaIniciativaProductoAmarilla" />
		</bean:define>

		<bean:define id="alertaIniciativaProductoRoja">
			<bean:write scope="session" name="registrarSeguimientoForm" property="alertaIniciativaProductoRoja" />
		</bean:define>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planificacionseguimiento/productos/guardarRegistroSeguimiento">

			<html:hidden property="seguimientoSeleccionadoId" />
			<html:hidden property="productoSeleccionadoId" />
			<html:hidden property="seguimientosProductos" />
			<html:hidden property="actualizarSeguimientoSeleccionado" />
			<html:hidden property="eliminarSeguimientoSeleccionado" />


			<vgcinterfaz:contenedorForma width="570px" height="385px">
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..:: <vgcutil:message key="jsp.registrarseguimiento.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
					<tr>
						<td width="70px" align="right"><b><vgcutil:message key="jsp.registrarseguimiento.iniciativa" />:</b></td>
						<td width="490px" align="left"><bean:write name="registrarSeguimientoForm" property="iniciativaNombre" scope="session" /></td>
					</tr>
					<tr>
						<td colspan="2"><vgcinterfaz:contenedorPaneles height="290px" width="550px" nombre="contenedorRegistroSeguimiento" mostrarSelectoresPaneles="false">
							<%-- Panel de Seguimientos Efectuado --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelSeguimientosEfectuados" mostrarBorde="false">
								<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="100px">
									<tr height="20x">
										<td><vgcutil:message key="jsp.registrarseguimiento.seguimientosefectuados.seguimientosfectuados" />:</td>
									</tr>
									<tr>
										<td>
										<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%">
											<tr>
												<td width="430px"><vgcinterfaz:contenedorTamanoFijo>
													<vgcinterfaz:visorLista nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" namePaginaLista="registrarSeguimientoForm" scopePaginaLista="session" propertyPaginaLista="paginaSeguimientos" messageKeyNoElementos="jsp.registrarseguimientos.seguimientosefectuados.seguimientos.noregistros" nombre="visorSeguimientos">
														<vgcinterfaz:columnaVisorLista nombre="ano" width="100px">
															<vgcutil:message key="jsp.registrarseguimiento.seguimientosefectuados.columna.ano" />
														</vgcinterfaz:columnaVisorLista>
														<vgcinterfaz:columnaVisorLista nombre="periodo" width="330px">
															<vgcutil:message key="jsp.registrarseguimiento.seguimientosefectuados.columna.periodo" />
														</vgcinterfaz:columnaVisorLista>
														<vgcinterfaz:filasVisorLista nombreObjeto="seguimiento">
															<vgcinterfaz:visorListaFilaId>ano<bean:write name="seguimiento" property="pk.ano" />periodo<bean:write name="seguimiento" property="pk.periodo" />
															</vgcinterfaz:visorListaFilaId>
															<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaV2(document.registrarSeguimientoForm.seguimientoSeleccionadoId, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
															<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaV2(document.registrarSeguimientoForm.seguimientoSeleccionadoId, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
															<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickSeguimiento('ano<bean:write name="seguimiento" property="pk.ano" />periodo<bean:write name="seguimiento" property="pk.periodo" />');</vgcinterfaz:visorListaFilaEventoOnclick>
															<vgcinterfaz:valorFilaColumnaVisorLista nombre="ano">
																<bean:write name="seguimiento" property="pk.ano" />
															</vgcinterfaz:valorFilaColumnaVisorLista>
															<vgcinterfaz:valorFilaColumnaVisorLista nombre="periodo">
																<bean:write name="seguimiento" property="pk.periodo" />
															</vgcinterfaz:valorFilaColumnaVisorLista>
														</vgcinterfaz:filasVisorLista>
													</vgcinterfaz:visorLista>
												</vgcinterfaz:contenedorTamanoFijo></td>
												<td style="padding-left: 10px">
												<table height="100%" cellpadding="2px" cellspacing="0">
													<tr height="25px">
														<td><input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="boton.imprimir" />" onclick="imprimirSeguimiento();"></td>
													</tr>
													<tr height="25px">
														<td><input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="boton.eliminar" />" onclick="eliminarSeguimiento();"></td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel de Seguimiento --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelSeguimiento" mostrarBorde="false">
								<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="100px">
									<tr height="20x">
										<td>
										<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="100px">
											<tr>
												<td width="140px"><vgcutil:message key="jsp.registrarseguimiento.seguimiento.fechaseguimiento" />:</td>
												<td width="100px"><html:text property="fecha" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFecha();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />"></td>
												<td width="60px" align="right"><vgcutil:message key="jsp.registrarseguimiento.seguimiento.ano" />:</td>
												<td width="40px">&nbsp;<bean:write scope="session" name="registrarSeguimientoForm" property="ano" /></td>
												<td width="60px" align="right"><bean:write scope="session" name="registrarSeguimientoForm" property="nombrePeriodo" />:</td>
												<td>&nbsp;<bean:write scope="session" name="registrarSeguimientoForm" property="periodo" /></td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td style="padding-top: 10px">
										<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="400px">
											<tr>
												<td width="330px"><vgcinterfaz:contenedorTamanoFijo width="330px">
													<vgcinterfaz:visorLista nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" namePaginaLista="registrarSeguimientoForm" propertyPaginaLista="paginaSeguimientosProductos" scopePaginaLista="session" messageKeyNoElementos="jsp.registrarseguimientos.seguimiento.productos.noregistros" nombre="visorProductos">

														<vgcinterfaz:columnaVisorLista nombre="alerta" width="20px">
														</vgcinterfaz:columnaVisorLista>

														<vgcinterfaz:columnaVisorLista nombre="nombre" width="200px">
															<vgcutil:message key="jsp.registrarseguimiento.seguimiento.productos.columna.nombre" />
														</vgcinterfaz:columnaVisorLista>

														<vgcinterfaz:columnaVisorLista nombre="fechaInicio" width="70px">
															<vgcutil:message key="jsp.registrarseguimiento.seguimiento.productos.columna.programado" />
														</vgcinterfaz:columnaVisorLista>

														<vgcinterfaz:columnaVisorLista nombre="reprogramado" width="110px">
															<vgcutil:message key="jsp.registrarseguimiento.seguimiento.productos.columna.reprogramado" />
														</vgcinterfaz:columnaVisorLista>

														<%-- Filas --%>
														<vgcinterfaz:filasVisorLista nombreObjeto="seguimientoProducto">
															<vgcinterfaz:visorListaFilaId>
																<bean:write name="seguimientoProducto" property="pk.productoId" />
															</vgcinterfaz:visorListaFilaId>
															<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFilaV2(document.registrarSeguimientoForm.productoSeleccionadoId, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
															<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFilaV2(document.registrarSeguimientoForm.productoSeleccionadoId, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
															<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickProducto('<bean:write name="seguimientoProducto" property="pk.productoId" />');</vgcinterfaz:visorListaFilaEventoOnclick>
															<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
																<logic:equal name="seguimientoProducto" property="alerta" value="<%= alertaProductoEnEsperaComienzo %>">
																	<img id="imgAlertaProducto<bean:write name="seguimientoProducto" property="pk.productoId" />" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaBlanca.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />">
																</logic:equal>
																<logic:equal name="seguimientoProducto" property="alerta" value="<%= alertaProductoEntregado %>">
																	<img id="imgAlertaProducto<bean:write name="seguimientoProducto" property="pk.productoId" />" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaVerde.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />">
																</logic:equal>
																<logic:equal name="seguimientoProducto" property="alerta" value="<%= alertaProductoNoEntregado %>">
																	<img id="imgAlertaProducto<bean:write name="seguimientoProducto" property="pk.productoId" />" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaRoja.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />">
																</logic:equal>
															</vgcinterfaz:valorFilaColumnaVisorLista>

															<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
																<bean:write name="seguimientoProducto" property="producto.nombre" />
															</vgcinterfaz:valorFilaColumnaVisorLista>

															<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaInicio">
																<bean:write name="seguimientoProducto" property="fechaInicioFormateada" />
															</vgcinterfaz:valorFilaColumnaVisorLista>

															<vgcinterfaz:valorFilaColumnaVisorLista nombre="reprogramado">
																<bean:write name="seguimientoProducto" property="fechaFinFormateada" />
															</vgcinterfaz:valorFilaColumnaVisorLista>

														</vgcinterfaz:filasVisorLista>

													</vgcinterfaz:visorLista>
												</vgcinterfaz:contenedorTamanoFijo></td>
												<td width="200px">
												<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%">
													<tr height="120px">
														<td>
														<table class="contenedorBotonesSeleccion" width="100%" height="100%">
															<tr>
																<td colspan="3"><b><vgcutil:message key="jsp.registrarseguimiento.seguimiento.estado" />:</b></td>
															</tr>
															<tr>
																<td width="10px" align="center" valign="middle"><input type="radio" name="alertaProducto" value="<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEnEsperaComienzo" />" onclick="actualizarAtributosProductoSeleccionado();"></td>
																<td width="20px" align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaBlanca.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
																<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaProductoEnEsperaComienzo" /></td>
															</tr>
															<tr>
																<td align="center" valign="middle"><input type="radio" name="alertaProducto" value="<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoEntregado" />" onclick="actualizarAtributosProductoSeleccionado();"></td>
																<td align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaVerde.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
																<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaProductoEntregado" /></td>
															</tr>
															<tr>
																<td align="center" valign="middle"><input type="radio" name="alertaProducto" value="<bean:write scope="session" name="registrarSeguimientoForm" property="alertaProductoNoEntregado" />" onclick="actualizarAtributosProductoSeleccionado();"></td>
																<td align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaRoja.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
																<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaProductoNoEntregado" /></td>
															</tr>
														</table>
														</td>
													</tr>
													<tr>
														<td style="padding-top: 10px">
														<table class="contenedorBotonesSeleccion" width="100%" height="100%">
															<tr>
																<td><b><vgcutil:message key="jsp.registrarseguimiento.seguimiento.fechaproducto" />:</b></td>
															</tr>
															<tr>
																<td><vgcutil:message key="jsp.registrarseguimiento.seguimiento.fechaprogramada" />:</td>
															</tr>
															<tr>
																<td><input type="text" name="fechaProgramada" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" class="cuadroTexto" /></td>
															</tr>
															<tr>
																<td><vgcutil:message key="jsp.registrarseguimiento.seguimiento.fechareprogramada" />:</td>
															</tr>
															<tr>
																<td><input type="text" name="fechaReprogramada" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" class="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaReprogramada();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />"></td>
															</tr>
														</table>
														</td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel de Resumen de Seguimiento --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelResumenSeguimiento" mostrarBorde="false">
								<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="100px">
									<tr height="150x">
										<td>
										<table class="contenedorBotonesSeleccion" width="100%" height="100%">
											<tr>
												<td colspan="3"><b><vgcutil:message key="jsp.registrarseguimiento.resumenseguimiento.estado" />:</b></td>
											</tr>
											<tr>
												<td width="10px" align="center" valign="middle"><html:radio property="alerta" value="<%=alertaIniciativaProductoEnEsperaComienzo %>"></html:radio></td>
												<td width="20px" align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaBlanca.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
												<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaIniciativaProductoEnEsperaComienzo" />.</td>
											</tr>
											<tr>
												<td align="center" valign="middle"><html:radio property="alerta" value="<%=alertaIniciativaProductoVerde %>"></html:radio></td>
												<td align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaVerde.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
												<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaIniciativaProductoVerde" />.</td>
											</tr>
											<tr>
												<td align="center" valign="middle"><html:radio property="alerta" value="<%=alertaIniciativaProductoAmarilla %>"></html:radio></td>
												<td align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
												<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaIniciativaProductoAmarilla" />.</td>
											</tr>
											<tr>
												<td align="center" valign="middle"><html:radio property="alerta" value="<%=alertaIniciativaProductoRoja %>"></html:radio></td>
												<td align="center" valign="middle"><img src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/alertaRoja.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.alerta.alt" />"></td>
												<td><bean:write scope="session" name="registrarSeguimientoForm" property="nombreAlertaIniciativaProductoRoja" />.</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td style="padding-top: 10px">
										<table class="panelContenedor" cellpadding="0" cellspacing="0" border="0" height="100%" width="100px">
											<tr height="20x">
												<td><vgcutil:message key="jsp.registrarseguimiento.resumenseguimiento.resumen" />:</td>
											</tr>
											<tr>
												<td><html:textarea property="seguimiento" cols="85" rows="6" styleClass="cuadroTexto"></html:textarea></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

						</vgcinterfaz:contenedorPaneles></td>
					</tr>
				</table>
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>


		<script type="text/javascript">

	setSeguimientoSeleccionado('<bean:write name="registrarSeguimientoForm" property="seguimientoSeleccionadoId" />');

	eventoClickProducto('<bean:write name="registrarSeguimientoForm" property="productoSeleccionadoId" />');

	mostrarPanelSeguimiento(pasoActual);

</script>

	</tiles:put>

</tiles:insert>
