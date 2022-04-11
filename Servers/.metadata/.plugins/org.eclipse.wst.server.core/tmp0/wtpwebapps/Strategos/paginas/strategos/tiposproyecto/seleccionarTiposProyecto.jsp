<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarcategoriasmedicion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">	

			function seleccionar() {
				if ((document.seleccionarCategoriasMedicionForm.seleccionados.value == null) || (document.seleccionarCategoriasMedicionForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarCategoriasMedicionForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarCategoriasMedicionForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarCategoriasMedicionForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarCategoriasMedicionForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarCategoriasMedicionForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarCategoriasMedicionForm.seleccionados.value;
				<logic:notEmpty name="seleccionarCategoriasMedicionForm" property="funcionCierre" scope="request">					
					this.opener.<bean:write name="seleccionarCategoriasMedicionForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/categoriasmedicion/seleccionarCategoriasMedicion" styleClass="formaHtml">

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
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionarcategoriasmedicion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarCategoriasMedicionForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaCategorias" width="100%" messageKeyNoElementos="jsp.seleccionarcategoriasmedicion.noregistros" nombre="visorCategoriasMedicion" esSelector="true" nombreForma="seleccionarCategoriasMedicionForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%" onclick="javascript:consultar(document.seleccionarCategoriasMedicionForm, 'nombre', null)">
						<vgcutil:message key="jsp.gestionarcategoriasmedicion.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="categoriaMedicion">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="categoriaMedicion" property="categoriaId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="categoriaMedicion" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
