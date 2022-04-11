<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>
<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.mover.iniciativa.historico.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript">
			var _selectHitoricoTypeIndex = 1;
			var _setCloseParent = false;
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function cancelar() 
			{
				if (document.gestionarIniciativasForm.respuesta.value == 2 && _setCloseParent && this.opener.refrescar)
					this.opener.refrescar();
				window.close();
			}
			
			function limpiarFiltros()
			{
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					filtroNombre.value = "";
				var selectHitoricoType = document.getElementById('selectHitoricoType');
				if (selectHitoricoType != null)
					selectHitoricoType.selectedIndex = _selectHitoricoTypeIndex;
			}
			
			function refrescar()
			{
				var url = '?source=' + document.gestionarIniciativasForm.source.value;
				url = url + '&planId=' + document.gestionarIniciativasForm.planId.value;
				var filtroNombre = document.getElementById('filtroNombre');
				if (filtroNombre != null)
					url = url + '&filtroNombre=' + filtroNombre.value;
				var selectHitoricoType = document.getElementById('selectHitoricoType');
				if (selectHitoricoType != null)
					url = url + '&selectHitoricoType=' + selectHitoricoType.value;
				var selectEstatusType = document.getElementById('selectEstatusType');
				if (selectEstatusType != null)
					url = url + '&selectEstatusType=' + selectEstatusType.value;

				window.location.href = '<html:rewrite action="/iniciativas/historicoIniciativa" />' + url;
			}
			
			function guardar()
			{
				if (verificarSeleccionMultiple(document.gestionarIniciativasForm.seleccionados))
				{
					var selectHitoricoType = document.getElementById('selectHitoricoType');
					if (selectHitoricoType != null && selectHitoricoType.value == NO_MARCADO)
						showAlert('<vgcutil:message key="jsp.mover.iniciativa.historico.marcar" />', 120, 320, undefined, IMG_QUESTION, 'setGuardar()');
					else if (selectHitoricoType != null)
						showAlert('<vgcutil:message key="jsp.mover.iniciativa.historico.desmarcar" />', 120, 320, undefined, IMG_QUESTION, 'setGuardar()');
				}
			}
			
			function setGuardar()
			{
				var url = '?funcion=MarcarDesmarcar';
				url = url + '&seleccionados=' + document.gestionarIniciativasForm.seleccionados.value;
				var selectHitoricoType = document.getElementById('selectHitoricoType');
				if (selectHitoricoType != null)
					url = url + '&selectHitoricoType=' + selectHitoricoType.value;
				window.location.href = '<html:rewrite action="/iniciativas/historicoIniciativa" />' + url;
			}
	
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/historicoIniciativa" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="respuesta" />
			<html:hidden property="source" />
			<html:hidden property="planId" />
			
			<bean:define id="tituloIniciativa">
				<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
			</bean:define>
			<vgcinterfaz:contenedorForma width="750px" height="470px" bodyAlign="center" bodyValign="center">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..:: <vgcutil:message key="jsp.mover.iniciativa.historico.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<%-- Filtro --%>
					<table class="tablaSpacing0Padding0Width100Collapse">
						<tr><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
						<tr class="barraFiltrosForma">
							<td style="width: 450px;">
								<jsp:include flush="true" page="/paginas/strategos/iniciativas/filtroIniciativas.jsp"></jsp:include>
							</td>
							<td>
								<table class="tablaSpacing0Padding0Width100Collapse" >
									<tr class="barraFiltrosForma" style="height: 40px;">
										<td colspan="2" valign="top">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr><td colspan="2" valign="top"><hr style="width: 100%;"></td></tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<bean:define id="listavacia">
					<vgcutil:message key="jsp.gestionariniciativas.noregistros" arg0="<%= tituloIniciativa %>"/>
				</bean:define>
				<vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" nombre="visorIniciativas" seleccionMultiple="true" nombreForma="gestionarIniciativasForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" messageKeyNoElementos="<%= listavacia %>" >
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'nombre', null)">
						<vgcutil:message key="jsp.gestionariniciativas.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="70px">
						<vgcutil:message key="jsp.gestionariniciativasplan.columna.porcentajecompletado" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="fechaUltimaMedicion" width="100px">
						<vgcutil:message key="jsp.gestionariniciativasplan.columna.fechaUltimaMedicion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="140px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'frecuencia', null)">
						<vgcutil:message key="jsp.gestionariniciativas.columna.frecuencia" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="responsableSeguimiento" width="300px" onclick="javascript:consultarConfigurable(document.gestionarIniciativasForm, getActionSubmitIniciativasGestion(), document.gestionarIniciativasForm.pagina, document.gestionarIniciativasForm.atributoOrden, document.gestionarIniciativasForm.tipoOrden, 'responsableSeguimientoId', null)">
						<vgcutil:message key="jsp.gestionariniciativas.columna.responsableseguimiento" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="condicion" width="40px">
						<vgcutil:message key="jsp.gestionariniciativas.columna.condicion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="estatus" width="100px">
						<vgcutil:message key="jsp.gestionariniciativas.columna.estatus" />
					</vgcinterfaz:columnaVisorLista>
		
					<%-- Filas --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="iniciativa" property="iniciativaId" />
						</vgcinterfaz:visorListaFilaId>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="iniciativa" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="center">
							<bean:write name="iniciativa" property="porcentajeCompletadoFormateado" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaUltimaMedicion" align="center">
							<bean:write name="iniciativa" property="fechaUltimaMedicion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
							<bean:write name="iniciativa" property="frecuenciaNombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableSeguimiento">
							<logic:notEmpty name="iniciativa" property="responsableSeguimiento">
								<bean:write name="iniciativa" property="responsableSeguimiento.nombreCargo" />
							</logic:notEmpty>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="condicion" align="center">
							<vgcst:imagenHistoricoIniciativa name="iniciativa" property="condicion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="estatus" align="center">
							<bean:write name="iniciativa" property="estatus.nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
					</vgcinterfaz:filasVisorLista>
		
				</vgcinterfaz:visorLista>
		
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaIniciativas" labelPage="inPagina" action="javascript:consultarV2(gestionarIniciativasForm, gestionarIniciativasForm.pagina, gestionarIniciativasForm.atributoOrden, gestionarIniciativasForm.tipoOrden, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarIniciativasForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIniciativasForm" property="atributoOrden" />  [<bean:write name="gestionarIniciativasForm" property="tipoOrden" />]
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><span id="bntAceptar"><vgcutil:message key="boton.marcar" /></span></a>&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;
					&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			var selectHitoricoType = document.getElementById('selectHitoricoType');
			document.gestionarIniciativasForm.seleccionados.value = "";
			actualizarSeleccionados(gestionarIniciativasForm, 'visorIniciativas');
			if (selectHitoricoType != null)
			{
				_selectHitoricoTypeIndex = selectHitoricoType.selectedIndex;
				if (selectHitoricoType.value == NO_MARCADO)
				{
					var periodo = document.getElementById("bntAceptar");
					periodo.innerHTML = '<vgcutil:message key="boton.marcar" />';
				}
				else
				{
					var periodo = document.getElementById("bntAceptar");
					periodo.innerHTML = '<vgcutil:message key="boton.desmarcar" />';
				}
			}
			var tblFiltro = document.getElementById('tblFiltro');
			if (tblFiltro != null)
				tblFiltro.style.display = "";
			<logic:equal name="gestionarIniciativasForm" property="respuesta" value="2">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
