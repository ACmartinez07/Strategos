<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (18/08/2012) --%>


<html:form action="/framework/bloqueos/gestionarBloqueosLectura" styleClass="formaHtmlGestionar">

	<html:hidden property="atributoOrdenLectura" />
	<html:hidden property="tipoOrdenLectura" />

	<vgcinterfaz:contenedorForma>
		<vgcinterfaz:contenedorFormaTitulo>..:: <bean:message key="jsp.framework.gestionarbloqueos.lectura.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonActualizar>
			<html:rewrite action='/framework/bloqueos/gestionarBloqueos' />
		</vgcinterfaz:contenedorFormaBotonActualizar>

		<vgcinterfaz:visorLista namePaginaLista="paginaBloqueosLectura" width="100%" messageKeyNoElementos="jsp.framework.gestionarbloqueos.lectura.nobloqueos" nombre="visorBloqueosLectura" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

			<vgcinterfaz:columnaAccionesVisorLista width="80px">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.acciones" />
			</vgcinterfaz:columnaAccionesVisorLista>

			<vgcinterfaz:columnaVisorLista nombre="pk.instancia" width="50%" onclick="javascript:consultarConfigurable(document.gestionarBloqueosLecturaForm, getActionSubmit(), null, document.gestionarBloqueosLecturaForm.atributoOrdenLectura, document.gestionarBloqueosLecturaForm.tipoOrdenLectura, 'pk.instancia', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.sessionid" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="pk.objetoId" width="120px" onclick="javascript:consultarConfigurable(document.gestionarBloqueosLecturaForm, getActionSubmit(), null, document.gestionarBloqueosLecturaForm.atributoOrdenLectura, document.gestionarBloqueosLecturaForm.tipoOrdenLectura, 'pk.objetoId', null)">
				<vgcutil:message key="jsp.framework.gestionarbloqueos.columna.idobjeto" />
			</vgcinterfaz:columnaVisorLista>

			<vgcinterfaz:filasVisorLista nombreObjeto="bloqueoLectura">

				<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/eliminar.gif">
					<vgcinterfaz:accionTituloVisorLista>
						<vgcutil:message key="boton.eliminar.alt" />
					</vgcinterfaz:accionTituloVisorLista>javascript:eliminarBloqueo('<bean:write name="bloqueoLectura" property="pk.instancia" />', '<bean:write name="bloqueoLectura" property="pk.objetoId" />', '');
						</vgcinterfaz:accionVisorLista>

				<vgcinterfaz:valorFilaColumnaVisorLista nombre="pk.instancia">
					<bean:write name="bloqueoLectura" property="pk.instancia" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="pk.objetoId">
					<bean:write name="bloqueoLectura" property="pk.objetoId" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior>
			<logic:notEmpty name="gestionarBloqueosLecturaForm" property="atributoOrdenLectura">
				<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaBloqueosLectura" property="infoOrden" />
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>

	</vgcinterfaz:contenedorForma>
</html:form>

<script type="text/javascript" language="javascript">

	document.gestionarBloqueosLecturaForm.action = getActionSubmit();

</script>
