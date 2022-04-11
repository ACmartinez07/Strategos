<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (29/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.framework.gestionarerrores.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			function reporte() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarErroresForm.seleccionados)) 
					abrirReporte('<html:rewrite action="/framework/errores/reporteError"/>?errTimestamp=' + document.gestionarErroresForm.seleccionados.value, 'registroError', '750', '550');
			}
			
			function enviarError() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarErroresForm.seleccionados)) 
				{
					activarBloqueoEspera();
					window.location.href = '<html:rewrite action="/framework/errores/enviarError"/>?errTimestamp=' + document.gestionarErroresForm.seleccionados.value;
				}
			}
			
		</script>

		<html:form action="/framework/errores/gestionarErrores" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-errores">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.gestionarerrores.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/framework/errores/gestionarErrores' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="ERROR_PRINT_SET"/>
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" permisoId="ERROR_PRINT"/>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<jsp:include page="/paginas/framework/menu/menuVer.jsp"></jsp:include>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra de Herramientas --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<vgcinterfaz:barraHerramientas nombre="barraGestionarErrores">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="mensaje" pathImagenes="/componentes/barraHerramientas/" nombre="mensaje" onclick="javascript:enviarError();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.enviarerror" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaErrores" nombre="visorErrores" messageKeyNoElementos="jsp.framework.gestionarerrores.noerrores" seleccionSimple="true" nombreForma="gestionarErroresForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="errTimestamp" width="20%" onclick="javascript:consultar(self.document.gestionarErroresForm, 'errTimestamp', null)">
						<vgcutil:message key="jsp.framework.gestionarerrores.columna.ts" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="descripcion" width="80%" onclick="javascript:consultar(self.document.gestionarErroresForm, 'errDescription', null)">
						<vgcutil:message key="jsp.framework.gestionarerrores.columna.descripcion" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:filasVisorLista nombreObjeto="error">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="error" property="fechaMiliSeg" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="errTimestamp">
							<bean:write name="error" property="errTimestamp" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="descripcion">
							<bean:write name="error" property="descripcionCorta" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaErrores" labelPage="inPagina" action="javascript:consultar(gestionarErroresForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarErroresForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaErrores" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-errores'), 250);
		
			var visor = document.getElementById('visorErrores');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 160) + "px";
		</script>

	</tiles:put>

</tiles:insert>
