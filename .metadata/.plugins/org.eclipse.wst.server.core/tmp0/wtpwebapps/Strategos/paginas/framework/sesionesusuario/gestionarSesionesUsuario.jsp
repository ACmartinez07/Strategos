<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (04/09/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">
		..:: <bean:message key="jsp.framework.gestionarsesionesusuario.titulo" />
	</tiles:put>

	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp" />

	<tiles:put name="body" type="String">

		<script type="text/javascript" language="javascript">

function eliminarBloqueos(sessionId) {
	if (confirm('¿<bean:message key="jsp.framework.gestionarsesionesusuario.eliminarbloqueosporsesionusuario.confirm" /> <' + sessionId + '>?')) {
		window.location.href='<html:rewrite action="/framework/sesionesusuario/eliminarBloqueosPorSesionUsuario"/>?sessionId=' + sessionId + '&ts=<%= (new java.util.Date()).getTime() %>';
	}
}

</script>

		<html:form action="/framework/sesionesusuario/gestionarSesionesUsuario" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />

			<vgcinterfaz:contenedorForma>
				<vgcinterfaz:contenedorFormaTitulo>..:: <bean:message key="jsp.framework.gestionarsesionesusuario.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/framework/sesionesusuario/gestionarSesionesUsuario' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:visorLista namePaginaLista="paginaSesiones" width="100%" messageKeyNoElementos="jsp.framework.gestionarsesionesusuario.nosesionesusuario" nombre="visorSesionesUsuario" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="sessionId" width="260px" onclick="javascript:consultar(self.document.gestionarSesionesUsuarioForm, 'sessionId', null)">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.sessionid" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="usuarioId" width="200px" onclick="javascript:consultar(self.document.gestionarSesionesUsuarioForm, 'usuarioId', null)">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.usuario" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="url" width="150px" onclick="javascript:consultar(self.document.gestionarSesionesUsuarioForm, 'url', null)">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.url" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="loginTs" width="180px" onclick="javascript:consultar(self.document.gestionarSesionesUsuarioForm, 'loginTs', null)">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.timestamp" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="remoteAddress" width="100px" onclick="javascript:consultar(self.document.gestionarSesionesUsuarioForm, 'remoteAddress', null)">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.remoteaddress" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="nroBloqueos" width="130px">
						<vgcutil:message key="jsp.framework.gestionarsesionesusuario.columna.nrobloqueos" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="sesion">

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="sessionId">
							<bean:write name="sesion" property="sessionId" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="usuarioId">
							<bean:write name="sesion" property="usuario.fullName" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="url">
							<bean:write name="sesion" property="url" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="loginTs">
							<bean:write name="sesion" format="HH:mm:ss - dd/MM/yyyy" property="loginTs" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="remoteAddress">
							<bean:write name="sesion" property="remoteAddress" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista align="right" nombre="nroBloqueos">

							<logic:greaterThan name="sesion" property="nroBloqueos" value="0">
								<a href="javascript:eliminarBloqueos('<bean:write name="sesion" property="sessionId" />');"><img title="<vgcutil:message key="jsp.framework.gestionarsesionesusuario.eliminarbloqueosporsesionusuario" />" border="0px" src="<html:rewrite page="/componentes/visorLista/eliminar.gif"/>"></a>
							</logic:greaterThan> &nbsp;&nbsp;
							<bean:write name="sesion" property="nroBloqueos" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaSesiones" labelPage="inPagina" action="javascript:consultar(gestionarSesionesUsuarioForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarSesionesUsuarioForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaSesiones" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

	</tiles:put>

</tiles:insert>
