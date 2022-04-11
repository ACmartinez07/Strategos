<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">

	function enviarSeguimiento() {
		<logic:notEqual name="gestionarSeguimientosForm" property="esResponsableProblema" value="true">
			alert("<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.problema.noseguimientos" />");
			return;
		</logic:notEqual>
		var respuesta = confirm('<vgcutil:message key="jsp.gestionarseguimientos.enviarseguimiento.confirmar" />');
		if (respuesta) {
			window.location.href='<html:rewrite action="/problemas/seguimientos/enviarSeguimiento" />';
		}
	}

	function propiedadesSeguimiento(seguimientoId) {
		abrirVentanaModal('<html:rewrite action="/problemas/seguimientos/propiedadesSeguimiento" />?seguimientoId=' + seguimientoId, "seguimiento", 450, 470);
	}

	function reporteSeguimientos() {
		abrirReporte('<html:rewrite action="/problemas/seguimientos/generarReporteSeguimientos.action"/>?atributoOrden=' + gestionarSeguimientosForm.atributoOrden.value + '&tipoOrden=' + gestionarSeguimientosForm.tipoOrden.value, 'reporte');
	}

	function modificarSeguimiento(seguimientoId, verSeguimiento) {
		window.location.href='<html:rewrite action="/problemas/seguimientos/modificarSeguimiento" />?seguimientoId=' + seguimientoId + '&verSeguimiento=' + verSeguimiento;
	}

	function eliminarSeguimiento(seguimientoId) {
		var eliminar = confirm('<vgcutil:message key="jsp.gestionarseguimientos.eliminarseguimientos.confirmar" />');
		if (eliminar) {
			window.location.href='<html:rewrite action="/problemas/seguimientos/eliminarSeguimiento"/>?seguimientoId=' + seguimientoId + '&ts=<%= (new Date()).getTime() %>';
		}
	}

</script>

<%-- Inclusión de los JavaScript externos a esta página --%>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

<%-- Representación de la Forma --%>
<html:form action="/problemas/seguimientos/gestionarSeguimientos" styleClass="formaHtmlCompleta">

	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />

	<vgcinterfaz:contenedorForma idContenedor="body-seguimiento">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarseguimientos.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			<html:rewrite action='/problemas/acciones/gestionarAcciones' />
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
					<vgcinterfaz:menuBotones id="menuArchivoSeguimientos" key="menu.archivo">
						<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
						<logic:notEmpty name="accionCorrectiva" property="padreId">
							<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteSeguimientos();" permisoId="SEGUIMIENTO_PRINT" agregarSeparador="true" />
						</logic:notEmpty>
						<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<logic:notEmpty name="accionCorrectiva" property="padreId">
					<%-- Menú: Edición --%>
					<vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:menuBotones id="menuEdicionSeguimientos" key="menu.edicion">
							<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="enviarSeguimiento();" permisoId="SEGUIMIENTO_ADD" />
						</vgcinterfaz:menuBotones>
					</vgcinterfaz:contenedorMenuHorizontalItem>
				</logic:notEmpty>

				<%-- Menú: Herramientas --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuHerramientasSeguimientos" key="menu.herramientas">
						<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" permisoId="SEGUIMIENTO_MENSAJE"/>
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

				<%-- Menú: Ayuda --%>
				<vgcinterfaz:contenedorMenuHorizontalItem>
					<vgcinterfaz:menuBotones id="menuAyudaSeguimientos" key="menu.ayuda">
						<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
						<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
					</vgcinterfaz:menuBotones>
				</vgcinterfaz:contenedorMenuHorizontalItem>

			</vgcinterfaz:contenedorMenuHorizontal>

		</vgcinterfaz:contenedorFormaBarraMenus>

		<logic:notEmpty name="accionCorrectiva" property="padreId">
			<%-- Barra Genérica --%>
			<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
				<%-- Barra de Herramientas --%>
				<vgcinterfaz:barraHerramientas nombre="barraGestionarSeguimientos">
					<vgcinterfaz:barraHerramientasBoton permisoId="SEGUIMIENTO_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:enviarSeguimiento();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.edicion.nuevo" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
					<vgcinterfaz:barraHerramientasSeparador />
					<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporteSeguimientos();">
						<vgcinterfaz:barraHerramientasBotonTitulo>
							<vgcutil:message key="menu.archivo.presentacionpreliminar" />
						</vgcinterfaz:barraHerramientasBotonTitulo>
					</vgcinterfaz:barraHerramientasBoton>
				</vgcinterfaz:barraHerramientas>
			</vgcinterfaz:contenedorFormaBarraGenerica>
		</logic:notEmpty>

		<%-- Visor Tipo Lista --%>
		<vgcinterfaz:visorLista namePaginaLista="paginaSeguimientos" width="100%" messageKeyNoElementos="jsp.gestionarseguimientos.noregistros" nombre="visorSeguimientos" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
			<vgcinterfaz:columnaVisorLista nombre="acciones" width="15%">
				<vgcutil:message key="boton.acciones" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaSolicitud" width="15%">
				<vgcutil:message key="jsp.gestionarseguimientos.columna.fechasolicitud" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="numeroReporte" width="15%">
				<vgcutil:message key="jsp.gestionarseguimientos.columna.numeroreporte" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="fechaRecepcion" width="15%">
				<vgcutil:message key="jsp.gestionarseguimientos.columna.fecharecepcion" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="estado" width="30%">
				<vgcutil:message key="jsp.gestionarseguimientos.columna.estadoreportado" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="aprobado" width="10%">
				<vgcutil:message key="jsp.gestionarseguimientos.columna.aprobado" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="seguimiento">

				<vgcinterfaz:visorListaFilaId>
					<bean:write name="seguimiento" property="seguimientoId" />
				</vgcinterfaz:visorListaFilaId>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="acciones">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<%-- <TD> Responder Seguimiento con todas las validaciones --%>
							<td align="center"><%-- Validación 01: se validad si el usuario tiene el permiso para Modificar Seguimientos --%> <vgcutil:tienePermiso permisoId="SEGUIMIENTO_EDIT" aplicaOrganizacion="true">

								<%-- Validación 02: corresponde SI Responder Seguimiento --%>
								<logic:equal name="seguimiento" property="recepcionEnviado" value="false">

									<%-- Validación 03: cuando el Usuario actual SI es Responsable de la Acción Correctiva SI puede Responder el Seguimiento --%>
									<logic:equal name="gestionarSeguimientosForm" property="esResponsableAccionCorrectiva" value="true">
										<img onClick="javascript:modificarSeguimiento('<bean:write name="seguimiento" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/responder.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.responder" />">
									</logic:equal>

									<%-- Validación 04: cuando el Usuario actual NO es Responsable de la Acción Correctiva NO puede Responder el Seguimiento --%>
									<logic:notEqual name="gestionarSeguimientosForm" property="esResponsableAccionCorrectiva" value="true">
										<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.accion.noresponder" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/noResponder.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.accion.noresponder" />">
									</logic:notEqual>

								</logic:equal>

								<%-- Validación 05: corresponde NO Responder Seguimiento --%>
								<logic:equal name="seguimiento" property="recepcionEnviado" value="true">
									<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noresponder" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/noResponder.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noresponder" />">
								</logic:equal>

							</vgcutil:tienePermiso></td>

							<%-- <TD> Aprobar Seguimiento con todas las validaciones --%>
							<td align="center"><%-- Validación 01: se validad si el usuario tiene el permiso para Modificar Seguimientos --%> <vgcutil:tienePermiso permisoId="SEGUIMIENTO_EDIT" aplicaOrganizacion="true">

								<%-- Validación 02: corresponde SI Aprobar Seguimiento --%>
								<logic:equal name="seguimiento" property="recepcionEnviado" value="true">

									<%-- Validación 03: corresponde SI Aprobar Seguimiento --%>
									<logic:empty name="seguimiento" property="fechaAprobacion">

										<%-- Validación 04: cuando el Usuario actual SI es Responsable del Problema SI puede Aprobar el Seguimiento --%>
										<logic:equal name="gestionarSeguimientosForm" property="esResponsableProblema" value="true">
											<img onClick="javascript:modificarSeguimiento('<bean:write name="seguimiento" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/aprobar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.aprobar" />">
										</logic:equal>

										<%-- Validación 05: cuando el Usuario actual NO es Responsable del Problema NO puede Aprobar el Seguimiento --%>
										<logic:notEqual name="gestionarSeguimientosForm" property="esResponsableProblema" value="true">
											<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.problema.noaprobar" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/noAprobar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.problema.noaprobar" />">
										</logic:notEqual>

									</logic:empty>

									<%-- Validación 06: ya se ha , Respondido y Aprobado el Seguimiento y ahora está cerrado --%>
									<logic:notEmpty name="seguimiento" property="fechaAprobacion">
										<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noaprobar.cerrado" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/noAprobar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noaprobar.cerrado" />">
									</logic:notEmpty>

								</logic:equal>

								<%-- Validación 05: corresponde NO Aprobar Seguimiento, porque aún no se ha respondido --%>
								<logic:equal name="seguimiento" property="recepcionEnviado" value="false">
									<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noaprobar.porresponder" />');" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/noAprobar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.noaprobar.porresponder" />">
								</logic:equal>

							</vgcutil:tienePermiso></td>

							<%-- <TD> Ver Seguimiento --%>
							<td align="center"><%-- Validación 01: se validad si el usuario tiene el permiso para Ver Seguimientos --%> <vgcutil:tienePermiso permisoId="SEGUIMIENTO" aplicaOrganizacion="true">
								<img onClick="javascript:modificarSeguimiento('<bean:write name="seguimiento" property="seguimientoId" />', true);" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/problemas/seguimientos/imagenes/ver.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.seguimiento.ver" />">
							</vgcutil:tienePermiso></td>

							<%-- <TD> Eliminar Seguimiento --%>
							<td align="center"><%-- Validación 01: el Seguimiento SI es el Último Seguimiento, y SI se puede Eliminar --%> <logic:equal name="seguimiento" property="ultimoRegistro" value="true">

								<%-- Validación 02: se validad si el usuario tiene el permiso para Eliminar Seguimientos --%>
								<vgcutil:tienePermiso permisoId="SEGUIMIENTO_DELETE" aplicaOrganizacion="true">

									<%-- Validación 03: cuando el Usuario actual SI es Responsable del Problema SI puede Eliminar el Seguimiento --%>
									<logic:equal name="gestionarSeguimientosForm" property="esResponsableProblema" value="true">
										<img onClick="javascript:eliminarSeguimiento('<bean:write name="seguimiento" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.eliminar.alt" />">
									</logic:equal>

									<%-- Validación 04: cuando el Usuario actual NO es Responsable del Problema NO puede Aprobar el Seguimiento --%>
									<logic:notEqual name="gestionarSeguimientosForm" property="esResponsableProblema" value="true">
										<img onClick="javascript:alert('<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.problema.noeliminar" />');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.gestionarseguimientos.validacion.noresponsable.problema.noeliminar" />">
									</logic:notEqual>

								</vgcutil:tienePermiso>

							</logic:equal> <%-- Validación 05: el Seguimiento NO es el Último Seguimiento, y NO se puede Eliminar --%> <logic:notEqual name="seguimiento" property="ultimoRegistro" value="true">
							--
						</logic:notEqual></td>

							<%-- <TD> Propiedades Seguimiento --%>
							<td align="center"><%-- Validación 01: se validad si el usuario tiene el permiso para Ver Seguimientos --%> <vgcutil:tienePermiso permisoId="SEGUIMIENTO" aplicaOrganizacion="true">
								<img onClick="javascript:propiedadesSeguimiento('<bean:write name="seguimiento" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/propiedades.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.propiedades.alt" />">
							</vgcutil:tienePermiso></td>
						</tr>
					</table>
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaSolicitud" align="center">
					<bean:write name="seguimiento" property="fechaEmisionFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="numeroReporte" align="right">
					<bean:write name="seguimiento" property="numeroReporte" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaRecepcion" align="center">
					<bean:write name="seguimiento" property="fechaRecepcionFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="estado" align="center">
					<logic:notEmpty name="seguimiento" property="estado">
						<bean:write name="seguimiento" property="estado.nombre" />
					</logic:notEmpty>
				</vgcinterfaz:valorFilaColumnaVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="aprobado" align="center">
					<logic:equal name="seguimiento" property="aprobado" value="true">
						<vgcutil:message key="comunes.si" />
					</logic:equal>
					<logic:equal name="seguimiento" property="aprobado" value="false">
						<vgcutil:message key="comunes.no" />
					</logic:equal>
				</vgcinterfaz:valorFilaColumnaVisorLista>

			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaSeguimientos" labelPage="inPagina" action="javascript:consultar(gestionarSeguimientosForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
			<logic:notEmpty name="gestionarSeguimientosForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaSeguimientos" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>
</html:form>
<script type="text/javascript">
	var visor = document.getElementById('visorSeguimientos');
	if (visor != null)
		visor.style.width = (parseInt(_myWidth) - 140) + "px";
</script>
