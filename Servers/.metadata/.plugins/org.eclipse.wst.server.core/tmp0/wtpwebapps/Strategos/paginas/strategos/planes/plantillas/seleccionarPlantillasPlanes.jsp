<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (31/10/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarplantillasplanes.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">	

			function seleccionar() {
				if ((document.seleccionarPlantillasPlanesForm.seleccionados.value == null) || (document.seleccionarPlantillasPlanesForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarPlantillasPlanesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarPlantillasPlanesForm" property="nombreCampoValor" scope="request" />.value=document.seleccionarPlantillasPlanesForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarPlantillasPlanesForm" property="nombreForma" scope="request" />.<bean:write name="seleccionarPlantillasPlanesForm" property="nombreCampoOculto" scope="request" />.value=document.seleccionarPlantillasPlanesForm.seleccionados.value;
				<logic:notEmpty name="seleccionarPlantillasPlanesForm" property="funcionCierre" scope="request">					
					this.opener.<bean:write name="seleccionarPlantillasPlanesForm" property="funcionCierre" scope="request" />;
				</logic:notEmpty>
				this.close();
			}

		</script>

		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/plantillas/seleccionarPlantillasPlanes" styleClass="formaHtml">

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
					..:: <vgcutil:message key="jsp.seleccionarplantillasplanes.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarPlantillasPlanesForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" id="tablaPlantillasPlanes" cellspacing="1">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">

						<td align="center" width="10px" class="mouseFueraEncabezadoListView"><img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10"></td>
						<td align="center" width="600px" onClick="javascript:consultar(seleccionarPlantillasPlanesForm, 'nombre', null);" style="cursor: pointer"
							onMouseOver="this.className='mouseEncimaEncabezadoListView'" onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message
							key="jsp.seleccionarplantillasplanes.nombre" /><img name="nombre" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"
							title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="paginaPlantillasPlanes" property="lista" scope="request" id="plantillaPlanes">

						<tr onclick="eventoClickFila(document.seleccionarPlantillasPlanesForm, 'tablaPlantillasPlanes', this)" id="<bean:write name="plantillaPlanes" property="plantillaPlanesId" />" class="mouseFueraCuerpoListView"
							onMouseOver="eventoMouseEncimaFila(document.seleccionarPlantillasPlanesForm, this)" onMouseOut="eventoMouseFueraFila(document.seleccionarPlantillasPlanesForm, this)" height="20px">
							<td width="10" valign="middle" align="center"><img name="img<bean:write name="plantillaPlanes" property="plantillaPlanesId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>"
								border="0" width="10" height="10"></td>
							<td id="valor<bean:write name="plantillaPlanes" property="plantillaPlanesId" />"><bean:write name="plantillaPlanes" property="nombre" /></td>
						</tr>

					</logic:iterate>

					<%-- Validación cuando no hay registros --%>
					<logic:equal name="paginaPlantillasPlanes" property="total" value="0">
						<tr class="mouseFueraCuerpoListView" id="0" height="20px">
							<td valign="middle" align="center" colspan="2"><vgcutil:message key="jsp.seleccionarplantillasplanes.noregistros" /></td>
						</tr>
					</logic:equal>

				</table>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionados(seleccionarPlantillasPlanesForm, 'tablaPlantillasPlanes');

				// Invoca la función que hace el ordenamiento de las columnas
				cambioImagenOrden(seleccionarPlantillasPlanesForm);

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
