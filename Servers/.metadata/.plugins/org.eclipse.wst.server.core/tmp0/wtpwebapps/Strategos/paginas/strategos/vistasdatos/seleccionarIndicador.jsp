<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarindicador.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas --%>	
		<jsp:include page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/unidadesmedida/unidadesMedidaJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>		
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">			
			
			var seleccionMultiple = '<bean:write name="seleccionarIndicadorForm" property="seleccionMultiple" scope="session" />';
			
			function seleccionar() 
			{
				if ((document.seleccionarIndicadorForm.seleccionados.value == null) || (document.seleccionarIndicadorForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;				
				}
				
				this.opener.document.<bean:write name="seleccionarIndicadorForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadorForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarIndicadorForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarIndicadorForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarIndicadorForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarIndicadorForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarIndicadorForm" property="funcionCierre" scope="session">					
					this.opener.<bean:write name="seleccionarIndicadorForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}

			function limpiarFiltros() 
			{  
				seleccionarIndicadorForm.filtroNombre.value = "";				
				seleccionarIndicadorForm.filtroOrganizacionId.value = "";
				seleccionarIndicadorForm.filtroOrganizacionNombre.value = "";				
				seleccionarIndicadorForm.filtroPlanId.value = "";
				seleccionarIndicadorForm.filtroPlanNombre.value = "";
				seleccionarIndicadorForm.filtroUnidadId.value = "";
				seleccionarIndicadorForm.filtroUnidadNombre.value = "";
				seleccionarIndicadorForm.filtroNaturaleza.value = "";
				seleccionarIndicadorForm.filtroCaracteristica.value = "";
				seleccionarIndicadorForm.filtroTipoIndicador.value = "";
				seleccionarIndicadorForm.filtroResponsableFijarMetaId.value = "";
				seleccionarIndicadorForm.filtroResponsableFijarMetaNombre.value = "";
				seleccionarIndicadorForm.filtroResponsableLograrMetaId.value = "";
				seleccionarIndicadorForm.filtroResponsableLograrMetaNombre.value = "";
				seleccionarIndicadorForm.filtroResponsableSeguimientoId.value = "";
				seleccionarIndicadorForm.filtroResponsableSeguimientoNombre.value = "";			  
				seleccionarIndicadorForm.submit();
			}

			function buscar() 
			{
				seleccionarIndicadorForm.submit();
			}

			function seleccionarOrganizacion() 
			{
				abrirSelectorOrganizaciones('seleccionarIndicadorForm', 'filtroOrganizacionNombre', 'filtroOrganizacionId', document.seleccionarIndicadorForm.filtroOrganizacionId.value, null, 'funcionCierreSelectorOrganizaciones()');
			}

			function limpiarOrganizacion() 
			{
				document.seleccionarIndicadorForm.filtroOrganizacionId.value = "";
				document.seleccionarIndicadorForm.filtroOrganizacionNombre.value = "";
			}

			function funcionCierreSelectorOrganizaciones() 
			{
			}
						
			function seleccionarPlan() 
			{
				abrirSelectorPlanes('seleccionarIndicadorForm', 'filtroPlanNombre', 'filtroPlanId');
			}
			
			function limpiarPlan() 
			{				
				document.seleccionarIndicadorForm.filtroPlanId.value = "";				
				document.seleccionarIndicadorForm.filtroPlanNombre.value = "";
			}

			function seleccionarUnidad() 
			{
				abrirSelectorUnidadesMedida('seleccionarIndicadorForm', 'filtroUnidadNombre', 'filtroUnidadId', document.seleccionarIndicadorForm.filtroUnidadId.value);
			}
			
			function limpiarUnidad() 
			{				
				document.seleccionarIndicadorForm.filtroUnidadId.value = "";				
				document.seleccionarIndicadorForm.filtroUnidadNombre.value = "";
			}
			
			function seleccionarResponsableFijarMeta() 
			{						
				abrirSelectorResponsables('seleccionarIndicadorForm', 'filtroResponsableFijarMetaNombre', 'filtroResponsableFijarMetaId', document.seleccionarIndicadorForm.filtroResponsableFijarMetaId.value);		
			}
			
			function limpiarResponsableFijarMeta() 
			{
				document.seleccionarIndicadorForm.filtroResponsableFijarMetaId.value = "";				
				document.seleccionarIndicadorForm.filtroResponsableFijarMetaNombre.value = "";
			}				
			
			function seleccionarResponsableLograrMeta() {
				abrirSelectorResponsables('seleccionarIndicadorForm', 'filtroResponsableLograrMetaNombre', 'filtroResponsableLograrMetaId', document.seleccionarIndicadorForm.filtroResponsableLograrMetaId.value);		
			}
			
			function limpiarResponsableLograrMeta() 
			{
				document.seleccionarIndicadorForm.filtroResponsableLograrMetaId.value = "";				
				document.seleccionarIndicadorForm.filtroResponsableLograrMetaNombre.value = "";
			}
			
			function seleccionarResponsableSeguimiento() 
			{
				abrirSelectorResponsables('seleccionarIndicadorForm', 'filtroResponsableSeguimientoNombre', 'filtroResponsableSeguimientoId', document.seleccionarIndicadorForm.filtroResponsableSeguimientoId.value);		
			}
			
			function limpiarResponsableSeguimiento() 
			{
				document.seleccionarIndicadorForm.filtroResponsableSeguimientoId.value = "";				
				document.seleccionarIndicadorForm.filtroResponsableSeguimientoNombre.value = "";
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/vistasdatos/seleccionarIndicador" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="seleccionMultiple" />
			<html:hidden property="filtroOrganizacionId" />
			<html:hidden property="filtroPlanId" />
			<html:hidden property="filtroUnidadId" />			
			<html:hidden property="filtroResponsableFijarMetaId" />
			<html:hidden property="filtroResponsableLograrMetaId" />
			<html:hidden property="filtroResponsableSeguimientoId" />			
			<html:hidden property="primeraVez"/>
			<html:hidden property="funcionCierre"/>

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarindicador.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarIndicadorForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarIndicadores">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:buscar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.buscar.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="limpiar" pathImagenes="/componentes/barraHerramientas/" nombre="limpiar" onclick="javascript:limpiarFiltros();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.limpiar.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>

					<%-- Filtros --%>
					<table width="100%" cellpadding="3" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td colspan="3">
							<hr width="100%">
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtrofrecuencia" /></td>
							<logic:equal name="seleccionarIndicadorForm" property="frecuenciaBloqueada" value="1">
								<td><html:text styleClass="cuadroTexto" property="nombreFrecuencia" readonly="true"></html:text></td>
							</logic:equal>
							<logic:equal name="seleccionarIndicadorForm" property="frecuenciaBloqueada" value="0">
								<td><html:select property="frecuencia" styleClass="cuadroTexto">
									<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
								</html:select></td>
							</logic:equal>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtronombre" /></td>
							<td><html:text property="filtroNombre" size="55" styleClass="cuadroTexto" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtroorganizacion" /></td>
							<td><html:text property="filtroOrganizacionNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarOrganizacion();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarorganizacion' />"> <img style="cursor:pointer" onclick="limpiarOrganizacion();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtroplan" /></td>
							<td><html:text property="filtroPlanNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarPlan();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarplan' />"> <img style="cursor:pointer" onclick="limpiarPlan();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtrounidad" /></td>
							<td><html:text property="filtroUnidadNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarUnidad();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarunidad' />"> <img style="cursor:pointer" onclick="limpiarUnidad();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtronaturaleza" /></td>
							<td><html:select property="filtroNaturaleza" styleClass="cuadroCombinado" size="1" style="width=344px" >
								<html:option value=""></html:option>
								<html:optionsCollection property="listaNaturalezas" value="naturalezaId" label="nombre" />
							</html:select></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtrocaracteristica" /></td>
							<td><html:select property="filtroCaracteristica" styleClass="cuadroCombinado" size="1" style="width=344px" >
								<html:option value=""></html:option>
								<html:optionsCollection property="listaCaracteristicas" value="caracteristicaId" label="nombre" />
							</html:select></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtrotipoindicador" /></td>
							<td><html:select property="filtroTipoIndicador" styleClass="cuadroCombinado" size="1" style="width=344px" >
								<html:option value=""></html:option>
								<html:optionsCollection property="listaTiposIndicadores" value="tipoId" label="nombre" />
							</html:select></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtroresponsablefijarmeta" /></td>
							<td><html:text property="filtroResponsableFijarMetaNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarResponsableFijarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarresponsable' />"> <img style="cursor:pointer" onclick="limpiarResponsableFijarMeta();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtroresponsablelogarmeta" /></td>
							<td><html:text property="filtroResponsableLograrMetaNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarResponsableLograrMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarresponsable' />"> <img style="cursor:pointer" onclick="limpiarResponsableLograrMeta();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="180px" align="right"><vgcutil:message key="jsp.seleccionarindicador.filtroresponsableseguimientometa" /></td>
							<td><html:text property="filtroResponsableSeguimientoNombre" size="55" styleClass="cuadroTexto" readonly="true" />&nbsp; <img style="cursor:pointer" onclick="seleccionarResponsableSeguimiento();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='jsp.seleccionarindicador.seleccionarresponsable' />"> <img style="cursor:pointer" onclick="limpiarResponsableSeguimiento();" src="<html:rewrite page='/componentes/formulario/cancelarGris.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td>&nbsp;</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>
				
				<%-- Visor Lista --%>
				<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" seleccionMultiple="true" nombreForma="seleccionarIndicadorForm" nombreCampoSeleccionados="seleccionados" nombreCampoValores="valoresSeleccionados" messageKeyNoElementos="jsp.seleccionarindicador.noregistros" nombre="tablaIndicadores" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

					<%-- Encabezados del Visor Lista --%>
					<vgcinterfaz:columnaVisorLista nombre="alerta" align="center" width="45">
						<vgcutil:message key="jsp.seleccionarindicador.columna.alerta" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="nombre" width="350">
						<vgcutil:message key="jsp.seleccionarindicador.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="unidad" align="center" width="70">
						<vgcutil:message key="jsp.seleccionarindicador.columna.unidad" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="frecuencia" align="center" width="200">
						<vgcutil:message key="jsp.seleccionarindicador.columna.frecuencia" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="naturaleza" align="center" width="150">
						<vgcutil:message key="jsp.seleccionarindicador.columna.naturaleza" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" align="center" width="90">
						<vgcutil:message key="jsp.seleccionarindicador.columna.ultimoperiodomedicion" />
					</vgcinterfaz:columnaVisorLista>

					<%-- Filas del Visor Lista --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="indicador">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="indicador" property="indicadorId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center" >							
							<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" esValorSelector="true" >							
							<bean:write name="indicador" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
							<logic:notEmpty name="indicador" property="unidad">
								<bean:write name="indicador" property="unidad.nombre" />
							</logic:notEmpty>&nbsp;
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">							
							<bean:write name="indicador" property="frecuenciaNombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">							
							<bean:write name="indicador" property="naturalezaNombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="center" >							
							<bean:write name="indicador" property="fechaUltimaMedicion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

					</vgcinterfaz:filasVisorLista>

				</vgcinterfaz:visorLista>
				
			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>
