<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<bean:define id="seleccionados">
	<logic:notEmpty name="gestionarActividadesForm" property="seleccionados">
		<bean:write name="gestionarActividadesForm" property="seleccionados" />
	</logic:notEmpty>
	<logic:empty name="gestionarActividadesForm" property="seleccionados">
		0
	</logic:empty>
</bean:define>

<vgcinterfaz:contenedorForma idContenedor="body-visorSimple" bodyAlign="center" bodyValign="center" width="100%" height="100%" mostrarBarraSuperior="false">
	<vgcinterfaz:visorLista namePaginaLista="paginaActividades" nombre="visorActividades" seleccionMultiple="true" nombreForma="gestionarActividadesForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="jsp.gestionaractividades.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">
		<%-- Encabezados --%>
		<vgcinterfaz:columnaVisorLista nombre="fila" width="20px">
			<vgcutil:message key="jsp.gestionaractividades.columna.numero" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="alerta" width="30px" align="center">
			<vgcutil:message key="jsp.gestionaractividades.columna.alerta" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="relacion" width="30px">
			<vgcutil:message key="jsp.gestionaractividades.columna.relacion" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="nombre" width="700px">
			<vgcutil:message key="jsp.gestionaractividades.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="porcentajeCompletado" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.porcentajecompletado" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="porcentajeEjecutado" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.porcentajeejecutado" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="porcentajeEsperado" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.porcentajeesperado" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodo" width="150px">
			<vgcutil:message key="jsp.gestionaractividades.columna.ultimoperiodo" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="peso" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.peso" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="comienzoPlan" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.fecha.estimada.inicio" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="finPlan" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.fecha.estimada.culminacion" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="comienzoReal" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.inicio" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="finReal" width="70px">
			<vgcutil:message key="jsp.gestionaractividades.columna.fecha.real.culminacion" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista nombre="responsableLograrMeta" width="400px">
			<vgcutil:message key="jsp.gestionaractividades.columna.responsable.lograr.meta" />
		</vgcinterfaz:columnaVisorLista>
	
		<%-- Filas --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="pryActividad">
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="pryActividad" property="actividadId" />
			</vgcinterfaz:visorListaFilaId>
	
			<vgcinterfaz:visorListaFilaEventoOnclick>eventoClickFilaCheckPadre(this)</vgcinterfaz:visorListaFilaEventoOnclick>
	
			<%-- Columnas --%>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="fila" align="right">
				<b> <bean:write name="pryActividad" property="fila" /></b>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="left">
				<logic:notEmpty name="pryActividad" property="alerta">
					<vgcst:imagenAlertaIniciativa name="pryActividad" property="alerta" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="relacion" align="left">
				<logic:notEmpty name="pryActividad" property="relacion">
					<p ondblclick="javascript:showRelacion('<bean:write name="pryActividad" property="objetoAsociadoId" />', '<bean:write name="pryActividad" property="objetoAsociadoClassName" />');">
						<vgcst:imagenRelacion name="pryActividad" property="relacion" />
					</p>
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<logic:iterate id="identador" name="pryActividad" property="identacion">&nbsp;</logic:iterate>
				<logic:equal name="pryActividad" property="tieneHijos" value="true">-</logic:equal>
				<logic:equal name="pryActividad" property="tieneHijos" value="false">&nbsp;</logic:equal>&nbsp;<script>truncarTexto('<bean:write name="pryActividad" property="nombre" />', 1000);</script>
				<logic:equal name="pryActividad" property="actividadId" value="<%=seleccionados%>">
					<a name="ancla"></a>
				</logic:equal>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeCompletado" align="left">
				<logic:notEmpty name="pryActividad" property="porcentajeCompletadoFormateado">
					<bean:write name="pryActividad" property="porcentajeCompletadoFormateado" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeEjecutado" align="left">
				<logic:notEmpty name="pryActividad" property="porcentajeEjecutadoFormateado">
					<bean:write name="pryActividad" property="porcentajeEjecutadoFormateado" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="porcentajeEsperado" align="left">
				<logic:notEmpty name="pryActividad" property="porcentajeEsperadoFormateado">
					<bean:write name="pryActividad" property="porcentajeEsperadoFormateado" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodo" align="left">
				<bean:write name="pryActividad" property="fechaUltimaMedicion" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="peso" align="left">
				<bean:write name="pryActividad" property="pryInformacionActividad.pesoFormateado" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="comienzoPlan" align="right">
				<bean:write name="pryActividad" property="comienzoPlanFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="finPlan" align="right">
				<bean:write name="pryActividad" property="finPlanFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="comienzoReal" align="right">
				<bean:write name="pryActividad" property="comienzoRealFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="finReal" align="right">
				<bean:write name="pryActividad" property="finRealFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="responsableLograrMeta" align="left">
				<logic:notEmpty name="pryActividad" property="responsableLograrMeta">
					<bean:write name="pryActividad" property="responsableLograrMeta.nombreCargo" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
	
		</vgcinterfaz:filasVisorLista>
	
	</vgcinterfaz:visorLista>
</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-visorSimple'), 260);
	var objeto = document.getElementById('body-visorSimple');
	if (objeto != null)
		objeto.style.width = (_myWidth - (_anchoAreBar + 18)) + "px";
</script>
