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
		<vgcutil:message key="jsp.seleccionarestadosacciones.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			function seleccionar() {
				if ((document.seleccionarEstadosAccionesForm.seleccionados.value == null) || (document.seleccionarEstadosAccionesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarEstadosAccionesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarEstadosAccionesForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarEstadosAccionesForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarEstadosAccionesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarEstadosAccionesForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarEstadosAccionesForm.seleccionados.value;
				<logic:notEmpty name="seleccionarEstadosAccionesForm" property="funcionCierre" scope="request">					
					this.opener.<bean:write name="seleccionarEstadosAccionesForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}
                        
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/estadosacciones/seleccionarEstadosAcciones" styleClass="formaHtml">

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
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarestadosacciones.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarEstadosAccionesForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaEstadosAcciones" width="100%" messageKeyNoElementos="jsp.seleccionarestadosacciones.noregistros" nombre="visorEstadosAcciones" esSelector="true" nombreForma="seleccionarEstadosAccionesForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%" >
					<vgcutil:message
							key="jsp.seleccionarestadosacciones.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="estadoAccion">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="estadoAccion" property="estadoId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="estadoAccion" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>