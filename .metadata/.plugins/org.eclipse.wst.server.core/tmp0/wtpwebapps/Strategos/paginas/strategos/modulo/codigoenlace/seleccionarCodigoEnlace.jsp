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
		<vgcutil:message key="jsp.seleccionar.codigoenlace.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function seleccionar() 
			{
				if ((document.seleccionarCodigoEnlaceForm.seleccionados.value == null) || (document.seleccionarCodigoEnlaceForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarCodigoEnlaceForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarCodigoEnlaceForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarCodigoEnlaceForm.valoresSeleccionados.value;
				//this.opener.document.<bean:write name="seleccionarCodigoEnlaceForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarCodigoEnlaceForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarCodigoEnlaceForm.seleccionados.value;
				<logic:notEmpty name="seleccionarCodigoEnlaceForm" property="funcionCierre" scope="request">
					this.opener.<bean:write name="seleccionarCodigoEnlaceForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/codigoenlace/seleccionarCodigoEnlace" styleClass="formaHtml">

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
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionar.codigoenlace.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarCodigoEnlaceForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>

				<vgcinterfaz:visorLista namePaginaLista="paginaCodigoEnlace" width="100%" messageKeyNoElementos="jsp.seleccionar.codigoenlace.noregistros" nombre="visorCodigoEnlace" esSelector="true" nombreForma="seleccionarCodigoEnlaceForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="codigo" width="30%" onclick="javascript:consultar(document.seleccionarCodigoEnlaceForm,'codigo', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.codigo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="nombre" onclick="javascript:consultar(document.seleccionarCodigoEnlaceForm,'nombre', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="bi" onclick="javascript:consultar(document.seleccionarCodigoEnlaceForm,'bi', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.bi" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="categoria" onclick="javascript:consultar(document.seleccionarCodigoEnlaceForm,'categoria', null)">
						<vgcutil:message key="jsp.gestionarcodigoenlace.columna.categoria" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="codigoEnlace">
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="codigoEnlace" property="id" />
						</vgcinterfaz:visorListaFilaId>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="codigo" esValorSelector="true">
							<bean:write name="codigoEnlace" property="codigo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="codigoEnlace" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="bi" esValorSelector="true">
							<bean:write name="codigoEnlace" property="bi" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="categoria" esValorSelector="true">
							<bean:write name="codigoEnlace" property="categoria" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			var visor = document.getElementById('visorCodigoEnlace');
			if (visor != null)
				visor.style.width = "540px";
		</script>
	</tiles:put>
</tiles:insert>
