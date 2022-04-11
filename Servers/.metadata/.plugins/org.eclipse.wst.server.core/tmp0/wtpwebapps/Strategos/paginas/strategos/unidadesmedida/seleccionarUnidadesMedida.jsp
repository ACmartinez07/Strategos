<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/07/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarunidadesmedida.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

function seleccionar() {
	if ((document.seleccionarUnidadesMedidaForm.seleccionados.value == null) || (document.seleccionarUnidadesMedidaForm.seleccionados.value == "")) {
		alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
		return;
	}
	this.opener.document.<bean:write name="seleccionarUnidadesMedidaForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarUnidadesMedidaForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarUnidadesMedidaForm.valoresSeleccionados.value;
	this.opener.document.<bean:write name="seleccionarUnidadesMedidaForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarUnidadesMedidaForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarUnidadesMedidaForm.seleccionados.value;
	<logic:notEmpty name="seleccionarUnidadesMedidaForm" property="funcionCierre" scope="request">
		this.opener.<bean:write name="seleccionarUnidadesMedidaForm" property="funcionCierre" scope="request" />;
	</logic:notEmpty>
	this.close();
}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/unidadesmedida/seleccionarUnidadesMedida" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionarunidadesmedida.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarUnidadesMedidaForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>

				<vgcinterfaz:visorLista namePaginaLista="paginaUnidades" width="100%" messageKeyNoElementos="jsp.seleccionarunidadesmedida.noregistros" nombre="visorUnidades" esSelector="true" nombreForma="seleccionarUnidadesMedidaForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="90%" onclick="javascript:consultar(document.gestionarUnidadesMedidaForm,'nombre', null)">
						<vgcutil:message key="jsp.seleccionarunidadesmedida.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="tipo" onclick="javascript:consultar(document.gestionarUnidadesMedidaForm,'tipo', null)">
						<vgcutil:message key="jsp.gestionarunidadesmedida.columna.tipo" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="unidadMedida">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="unidadMedida" property="unidadId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="unidadMedida" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo">
							<%-- Si --%>
							<logic:equal name="unidadMedida" property="tipo" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="unidadMedida" property="tipo" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>
