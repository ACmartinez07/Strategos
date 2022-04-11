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
		<vgcutil:message key="jsp.seleccionarseriestiempo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">			
			
			function seleccionar() {
				if ((document.seleccionarSeriesTiempoForm.seleccionados.value == null) || (document.seleccionarSeriesTiempoForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarSeriesTiempoForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarSeriesTiempoForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarSeriesTiempoForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarSeriesTiempoForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarSeriesTiempoForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarSeriesTiempoForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarSeriesTiempoForm" property="funcionCierre" scope="request">					
					this.opener.<bean:write name="seleccionarSeriesTiempoForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}
                        
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/seriestiempo/seleccionarSeriesTiempo" styleClass="formaHtml">

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
					..:: <vgcutil:message key="jsp.seleccionarseriestiempo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarSeriesTiempoForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaSeriesTiempo" nombre="tablaSeriesTiempo" seleccionSimple="true" nombreForma="seleccionarSeriesTiempoForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" messageKeyNoElementos="jsp.seleccionarseriestiempo.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

					<%-- Encabezados --%>
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%" onclick="javascript:consultar(self.document.seleccionarSeriesTiempoForm,'nombre', null)">
						<vgcutil:message key="jsp.seleccionarseriestiempo.nombre" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="identificador" onclick="javascript:consultar(self.document.seleccionarSeriesTiempoForm,'identificador', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.identificador" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="fija" onclick="javascript:consultar(self.document.seleccionarSeriesTiempoForm,'fija', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.fija" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="preseleccionada" onclick="javascript:consultar(self.document.seleccionarSeriesTiempoForm,'preseleccionada', null)">
						<vgcutil:message key="jsp.gestionarseriestiempo.columna.preseleccionada" />
					</vgcinterfaz:columnaVisorLista>

					<%-- Filas --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="serieTiempo">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="serieTiempo" property="serieId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true">
							<bean:write name="serieTiempo" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="identificador" >
							<bean:write name="serieTiempo" property="identificador" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="fija">
							<%-- Si --%>
							<logic:equal name="serieTiempo" property="fija" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="serieTiempo" property="fija" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="preseleccionada">
							<%-- Si --%>
							<logic:equal name="serieTiempo" property="preseleccionada" value="true">
								<vgcutil:message key="comunes.si" />
							</logic:equal>
							<%-- No --%>
							<logic:equal name="serieTiempo" property="preseleccionada" value="false">
								<vgcutil:message key="comunes.no" />
							</logic:equal>
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>

