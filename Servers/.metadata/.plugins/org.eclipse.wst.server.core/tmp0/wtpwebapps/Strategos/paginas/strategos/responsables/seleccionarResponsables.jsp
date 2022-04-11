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
		<vgcutil:message key="jsp.seleccionarresponsables.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function seleccionar() {
				if ((document.seleccionarResponsablesForm.seleccionados.value == null) || (document.seleccionarResponsablesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarResponsablesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarResponsablesForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarResponsablesForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarResponsablesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarResponsablesForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarResponsablesForm.seleccionados.value;
				<logic:notEmpty name="seleccionarResponsablesForm" property="funcionCierre" scope="request">					
					this.opener.<bean:write name="seleccionarResponsablesForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/responsables/seleccionarResponsables" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="organizacionId" />
			<html:hidden property="mostrarGrupos" />
			<html:hidden property="mostrarGlobales" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarresponsables.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarResponsablesForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaResponsables" messageKeyNoElementos="jsp.seleccionarresponsables.noregistros" nombre="visorResponsables" seleccionSimple="true" nombreForma="seleccionarResponsablesForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">

					<vgcinterfaz:columnaVisorLista nombre="nombre" width="400px" onclick="javascript:consultar(document.seleccionarResponsablesForm, 'nombre', null)">
						<vgcutil:message key="jsp.seleccionarresponsables.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="tipo" width="100px" onclick="javascript:consultar(document.seleccionarResponsablesForm, 'tipo', null)">
						<vgcutil:message key="jsp.seleccionarresponsables.columna.tipo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="grupo" width="100px">
						<vgcutil:message key="jsp.gestionarresponsables.columna.grupo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="organizacionId" width="200px" onclick="javascript:consultar(document.seleccionarResponsablesForm, 'organizacionId', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.organizacion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="email" width="250px" onclick="javascript:consultar(document.seleccionarResponsablesForm, 'email', null)">
						<vgcutil:message key="jsp.gestionarresponsables.columna.email" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="responsable">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="responsable" property="responsableId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="responsable" property="nombreCargo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="email">
							<bean:write name="responsable" property="email" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacionId">
							<logic:notEmpty name="responsable" property="organizacion">
								<bean:write name="responsable" property="organizacion.nombre" />
							</logic:notEmpty>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipo">
							<%-- Si --%>
							<logic:equal name="responsable" property="tipo" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="responsable" property="tipo" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="grupo">
							<logic:notEmpty name="responsable" property="responsables">
								<vgcutil:message key="comunes.si" />
							</logic:notEmpty>
							<logic:empty name="responsable" property="responsables">
								<vgcutil:message key="comunes.no" />
							</logic:empty>
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>
