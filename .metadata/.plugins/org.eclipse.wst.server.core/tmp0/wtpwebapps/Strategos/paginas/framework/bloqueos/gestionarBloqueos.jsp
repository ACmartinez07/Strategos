<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (18/08/2012) --%>

<script type="text/javascript" language="javascript">

function eliminarBloqueo(sessionId, objetoId, tipo) {
	if ((tipo == 'D') || (tipo == 'U')) {
		var tipoTexto = '<bean:message key="jsp.framework.gestionarbloqueos.tipo.modificacion" />';
		if (tipo == 'D') {
			tipoTexto = '<bean:message key="jsp.framework.gestionarbloqueos.tipo.eliminacion" />';
		}
		if (confirm('¿<bean:message key="jsp.framework.eliminarbloqueo.confirm1" /> <' + objetoId + '> <bean:message key="jsp.framework.eliminarbloqueo.confirm2" /> <' + tipoTexto + '>?')) {
			window.location.href='<html:rewrite action="/framework/bloqueos/eliminarBloqueo"/>?sessionId=' + sessionId + '&objetoId=' + objetoId + '&tipo=' + tipo + '&ts=<%= (new java.util.Date()).getTime() %>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitBloqueos" />;
		}
	} else {
		if (confirm('¿<bean:message key="jsp.framework.eliminarbloqueo.lectura.confirm1" /> <' + objetoId + '> <bean:message key="jsp.framework.eliminarbloqueo.lectura.confirm2" /> <' + sessionId + '>?')) {
			window.location.href='<html:rewrite action="/framework/bloqueos/eliminarBloqueo"/>?sessionId=' + sessionId + '&objetoId=' + objetoId + '&tipo=' + tipo + '&ts=<%= (new java.util.Date()).getTime() %>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitBloqueos" />;
		}
	}
}

function getActionSubmit() {
	return '<html:rewrite action="/framework/bloqueos/gestionarBloqueos"/>' + <vgcinterfaz:queryStringConfSplitHorizontal splitId="splitBloqueos" primerParametro="true" />
}

</script>

<html:form action="/framework/bloqueos/gestionarBloqueos" styleClass="formaHtmlGestionar">

	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />

	<vgcinterfaz:contenedorForma>
		<vgcinterfaz:contenedorFormaTitulo>..:: <bean:message key="jsp.framework.gestionarbloqueos.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<vgcinterfaz:visorLista namePaginaLista="paginaBloqueos" width="100%" messageKeyNoElementos="jsp.framework.gestionarbloqueos.nobloqueos" nombre="visorBloqueos" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

			<vgcinterfaz:columnaAccionesVisorLista width="80px">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.acciones" />
			</vgcinterfaz:columnaAccionesVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="instancia" width="50%" onclick="javascript:consultarConfigurable(document.gestionarBloqueosForm, getActionSubmit(), null, document.gestionarBloqueosForm.atributoOrden, document.gestionarBloqueosForm.tipoOrden, 'instancia', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.sessionid" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="pk.objetoId" width="120px" onclick="javascript:consultarConfigurable(document.gestionarBloqueosForm, getActionSubmit(), null, document.gestionarBloqueosForm.atributoOrden, document.gestionarBloqueosForm.tipoOrden, 'pk.objetoId', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.idobjeto" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="timeStamp" width="160px" onclick="javascript:consultarConfigurable(document.gestionarBloqueosForm, getActionSubmit(), null, document.gestionarBloqueosForm.atributoOrden, document.gestionarBloqueosForm.tipoOrden, 'timeStamp', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.horabloqueo" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="pk.tipo" width="130px" onclick="javascript:consultarConfigurable(document.gestionarBloqueosForm, getActionSubmit(), null, document.gestionarBloqueosForm.atributoOrden, document.gestionarBloqueosForm.tipoOrden, 'pk.tipo', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.tipobloqueo" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="bloqueo">

				<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/eliminar.gif">
					<vgcinterfaz:accionTituloVisorLista>
						<vgcutil:message key="boton.eliminar.alt" />
					</vgcinterfaz:accionTituloVisorLista>javascript:eliminarBloqueo('<bean:write name="bloqueo" property="instancia" />', '<bean:write name="bloqueo" property="pk.objetoId" />', '<bean:write name="bloqueo" property="pk.tipo" />');
						</vgcinterfaz:accionVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="instancia">
					<bean:write name="bloqueo" property="instancia" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="pk.objetoId">
					<bean:write name="bloqueo" property="pk.objetoId" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="timeStamp">
					<bean:write name="bloqueo" format="HH:mm:ss - dd/MM/yyyy" property="timeStamp" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="pk.tipo">
					<logic:equal name="bloqueo" property="pk.tipo" value="U">
						<vgcutil:message key="jsp.framework.gestionarbloqueos.tipo.modificacion" />
					</logic:equal>
					<logic:equal name="bloqueo" property="pk.tipo" value="D">
						<vgcutil:message key="jsp.framework.gestionarbloqueos.tipo.eliminacion" />
					</logic:equal>
				</vgcinterfaz:valorFilaColumnaVisorLista>
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior>
			<logic:notEmpty name="gestionarBloqueosForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaBloqueos" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>
</html:form>

<script type="text/javascript" language="javascript">

	document.gestionarBloqueosForm.action= getActionSubmit();

</script>
