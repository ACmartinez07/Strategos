<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<logic:notEmpty name="gestionarAccionesPendientesForm" property="tipo">

			<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="1">
				.:: <vgcutil:message key="jsp.gestionaraccionespendientes.seguimiento.titulo" />
			</logic:equal>

			<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="2">
				.:: <vgcutil:message key="jsp.gestionaraccionespendientes.responder.titulo" />
			</logic:equal>

			<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="3">
				.:: <vgcutil:message key="jsp.gestionaraccionespendientes.aprobar.titulo" />
			</logic:equal>

		</logic:notEmpty>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">
		
		function limpiarFiltros() {
		   gestionarAccionesPendientesForm.filtroNombre.value = "";
		   gestionarAccionesPendientesForm.submit();
		}
		
		function buscar() {
			gestionarAccionesPendientesForm.submit();
		}
		
		function editarSeguimientoResponder(seguimientoId){
		    window.location.href='<html:rewrite action="/problemas/seguimientos/modificarSeguimiento" />?seguimientoId=' + seguimientoId +'&esResponder='+true;
		}
		
		function editarSeguimientoAprobar(seguimientoId){
		    window.location.href='<html:rewrite action="/problemas/seguimientos/modificarSeguimiento" />?seguimientoId=' + seguimientoId +'&esAprobar='+true;
		}
			
		function gestionarAccionesSeguimiento(problemaId){
		    window.location.href='<html:rewrite action="/problemas/acciones/gestionarAcciones" />?problemaId=' +problemaId;
		}
		
		</script>

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/problemas/acciones/gestionarAccionesPendientes" styleClass="formaHtml">

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>

					<logic:notEmpty name="gestionarAccionesPendientesForm" property="tipo">
						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="1">
							.:: <vgcutil:message key="jsp.gestionaraccionespendientes.seguimiento.titulo" />
						</logic:equal>

						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="2">
							.:: <vgcutil:message key="jsp.gestionaraccionespendientes.responder.titulo" />
						</logic:equal>

						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="3">
							.:: <vgcutil:message key="jsp.gestionaraccionespendientes.aprobar.titulo" />
						</logic:equal>
					</logic:notEmpty>

				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/problemas/acciones/gestionarAccionesPendientes' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<!-- En caso de ser Acciones Pendientes Por Seguimiento -->
				<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="1">
					<%-- Visor Tipo Lista --%>
					<table class="listView" cellpadding="5" cellspacing="1">

						<%-- Encabezado del "Visor Tipo Lista" --%>
						<tr class="encabezadoListView" height="14px">
							<td align="center" width="100px"></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.problema" /></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.accioncorrectiva" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.fechaformulacion" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.responsable" /></td>

						</tr>

						<%-- Cuerpo del Visor Tipo Lista --%>
						<logic:iterate name="listaProblemasCompleta" scope="request" id="problema">
							<logic:iterate name="problema" property="listaAccionesCorrectivas" id="accionCorrectiva">
								<tr class="mouseFueraCuerpoListView" onMouseOver="this.className='mouseEncimaCuerpoListView'" onMouseOut="this.className='mouseFueraCuerpoListView'" height="20px">

									<td align="center"><img onClick="javascript:gestionarAccionesSeguimiento('<bean:write name="problema" property="problemaId"/>');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.hacer.seguimiento" />">&nbsp;</td>

									<td><bean:write name="problema" property="nombre" /></td>
									<td><bean:write name="accionCorrectiva" property="nombre" /></td>
									<td align="center"><bean:write name="problema" property="fechaFormateada" /></td>
									<td align="center"><logic:notEmpty name="problema" property="nombre">
										<bean:write name="problema" property="responsable.nombre" />
									</logic:notEmpty></td>




								</tr>
							</logic:iterate>
						</logic:iterate>

						<%-- Validación cuando no hay registros --%>
						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="1">
							<logic:equal name="listaProblemasCompleta" value="0" scope="request">
								<tr class="mouseFueraCuerpoListView" id="0" height="20px">
									<td valign="middle" align="center" colspan="3"><vgcutil:message key="jsp.gestionaraccionespendientes.seguimiento.noregistros" /></td>
								</tr>
							</logic:equal>
						</logic:equal>

					</table>

				</logic:equal>


				<!-- En caso de ser Acciones Pendientes Por Responder -->
				<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="2">
					<%-- Visor Tipo Lista --%>
					<table class="listView" cellpadding="5" cellspacing="1">

						<%-- Encabezado del "Visor Tipo Lista" --%>
						<tr class="encabezadoListView" height="14px">
							<td align="center" width="100px"></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.problema" /></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.accioncorrectiva" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.numeroseguimiento" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.fechaemision" /></td>

						</tr>

						<%-- Cuerpo del Visor Tipo Lista --%>
						<logic:iterate name="listaSeguimientosCompleta" scope="request" id="pendientesResponder">

							<tr class="mouseFueraCuerpoListView" onMouseOver="this.className='mouseEncimaCuerpoListView'" onMouseOut="this.className='mouseFueraCuerpoListView'" height="20px">

								<td align="center"><vgcutil:tienePermiso permisoId="" aplicaOrganizacion="false">
									<img onClick="javascript:editarSeguimientoResponder('<bean:write name="pendientesResponder" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.responder.seguimiento" />">
								</vgcutil:tienePermiso>&nbsp;</td>

								<td><bean:write name="pendientesResponder" property="accion.problema.nombre" /></td>
								<td><bean:write name="pendientesResponder" property="accion.nombre" /></td>
								<td align="center"><bean:write name="pendientesResponder" property="numeroReporte" /></td>
								<td align="center"><bean:write name="pendientesResponder" property="fechaEmisionFormateada" /></td>

							</tr>
						</logic:iterate>



						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="2">
							<logic:equal name="listaSeguimientosCompleta" value="0" scope="request">
								<tr class="mouseFueraCuerpoListView" id="0" height="20px">
									<td valign="middle" align="center" colspan="4"><vgcutil:message key="jsp.gestionaraccionespendientes.responder.noregistros" /></td>
								</tr>
							</logic:equal>
						</logic:equal>

					</table>
				</logic:equal>

				<!-- En caso de ser Acciones Pendientes Por Aprobar -->
				<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="3">
					<%-- Visor Tipo Lista --%>
					<table class="listView" cellpadding="5" cellspacing="1">

						<%-- Encabezado del "Visor Tipo Lista" --%>
						<tr class="encabezadoListView" height="14px">
							<td align="center" width="100px"></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.problema" /></td>

							<td align="center" width="1000px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.accioncorrectiva" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.numeroseguimiento" /></td>

							<td align="center" width="500px" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionaraccionespendientes.columna.fecharecepcion" /></td>

						</tr>

						<%-- Cuerpo del Visor Tipo Lista --%>
						<logic:iterate name="listaSeguimientosPorAprobar" scope="request" id="seguimiento">

							<tr class="mouseFueraCuerpoListView" onMouseOver="this.className='mouseEncimaCuerpoListView'" onMouseOut="this.className='mouseFueraCuerpoListView'" height="20px">

								<td align="center"><vgcutil:tienePermiso permisoId="" aplicaOrganizacion="false">
									<img onClick="javascript:editarSeguimientoAprobar('<bean:write name="seguimiento" property="seguimientoId" />');" style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.aprobar.seguimiento" />">
								</vgcutil:tienePermiso>&nbsp;</td>

								<td><bean:write name="seguimiento" property="accion.problema.nombre" /></td>
								<td><bean:write name="seguimiento" property="accion.nombre" /></td>
								<td align="center"><bean:write name="seguimiento" property="numeroReporte" /></td>
								<td align="center"><bean:write name="seguimiento" property="fechaRecepcionFormateada" /></td>
							</tr>
						</logic:iterate>

						<logic:equal name="gestionarAccionesPendientesForm" property="tipo" value="3">
							<logic:equal name="listaSeguimientosPorAprobar" value="0" scope="request">
								<tr class="mouseFueraCuerpoListView" id="0" height="20px">
									<td valign="middle" align="center" colspan="4"><vgcutil:message key="jsp.gestionaraccionespendientes.responder.noregistros" /></td>
								</tr>
							</logic:equal>
						</logic:equal>
					</table>

				</logic:equal>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
