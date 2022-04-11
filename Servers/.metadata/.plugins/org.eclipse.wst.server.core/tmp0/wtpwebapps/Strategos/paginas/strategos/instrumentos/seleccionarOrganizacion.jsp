<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (19/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarorganizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">			
			
			var seleccionMultiple = '<bean:write name="seleccionarOrganizacionForm" property="seleccionMultiple" scope="session" />';
			
			function seleccionar() {
				if ((document.seleccionarOrganizacionForm.seleccionados.value == null) || (document.seleccionarOrganizacionForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				seleccionarOrganizacionForm.submit();				
				this.close();
			}
			
			function eventoClickFilaSeleccion(idsSeleccionados, valoresSeleccionados, nombreTabla, fila) {	
		       if (seleccionMultiple == '1') {
		         eventoClickFilaSeleccionMultiple(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);										 
		       } else {
		         eventoClickFilaV2(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);
		       }		   		
		    }

			function eventoMouseFueraFilaSeleccion(idsSeleccionados, fila) {	
		       if (seleccionMultiple == '1') {
		   	      eventoMouseFueraFilaSeleccionMultiple(idsSeleccionados, fila);		      
		       } else {
		          eventoMouseFueraFilaV2(idsSeleccionados, fila);
		       }		   		
		    }           
           
			function eventoMouseEncimaFilaSeleccion(idsSeleccionados, fila) {
		       if (seleccionMultiple == '1') {
		   	      eventoMouseEncimaFilaSeleccionMultiple(idsSeleccionados, fila);		      
		       } else {
		          eventoMouseEncimaFilaV2(idsSeleccionados, fila);
		       }		   		
		    }           		   
		   	
		   	function inicializarVisorListaSeleccion(idsSeleccionados, nombreTabla) {			
   		   	   if (seleccionMultiple == '1') {
		   		  inicializarVisorListaSeleccionMultiple(idsSeleccionados, nombreTabla);			      
		       } else {
		          inicializarVisorListaSeleccionSimple(idsSeleccionados, nombreTabla);
		       }		   		
		   	}			
		   	
		   	function limpiarFiltros() {
				seleccionarOrganizacionForm.filtroNombre.value = "";				
				seleccionarOrganizacionForm.submit();
			}

			function buscar() {
				seleccionarOrganizacionForm.submit();
			}		
                        
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="javascript" src="<html:rewrite page='/componentes/visorLista/visorListaJsTag.jsp'/>"></script>


		<%-- Representación de la Forma --%>
		<html:form action="/instrumentos/seleccionarOrganizacion" styleClass="formaHtml">

			<%-- Organizaciones de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="seleccionMultiple" />
			<html:hidden property="funcionCierre" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionarorganizacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarOrganizacionForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarOrganizacion">
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
							<td colspan="2">
							<hr width="100%">
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.seleccionarorganizacion.nombre" />&nbsp;:</td>
							<td><html:text styleClass="cuadroTexto" property="filtroNombre" size="50"></html:text></td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>


				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" id="tablaOrganizacion" cellspacing="1">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">

						<td align="center" width="10px" class="mouseFueraEncabezadoListView"><img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10"></td>

						<td align="center" width="40%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.seleccionarorganizacion.nombre" /><img name="nombre"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="60%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.seleccionarorganizacion.rutacompleta" /><img name="rutacompleta"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="seleccionarOrganizacionForm" property="listaOrganizaciones" scope="session" id="organizacion">

						<tr onclick="eventoClickFilaSeleccion(document.seleccionarOrganizacionForm.seleccionados, document.seleccionarOrganizacionForm.valoresSeleccionados, 'tablaOrganizacion', this)"
							id="<bean:write name="organizacion" property="organizacionId" />" class="mouseFueraCuerpoListView"
							onMouseOver="eventoMouseEncimaFilaSeleccion(document.seleccionarOrganizacionForm.seleccionados, this)"
							onMouseOut="eventoMouseFueraFilaSeleccion(document.seleccionarOrganizacionForm.seleccionados, this)" height="20px">
							<td width="10" valign="middle" align="center"><img name="img<bean:write name="organizacion" property="organizacionId" />"
								src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"></td>

							<td id="valor<bean:write name="organizacion" property="organizacionId" />"><bean:write name="organizacion" property="nombre" /></td>

							<td id="valor<bean:write name="organizacion" property="organizacionId" />"><bean:write name="organizacion" property="rutaCompleta" /></td>

						</tr>

					</logic:iterate>
					
				</table>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">

				// Invoca la función que hace el ordenamiento de las columnas
				inicializarVisorListaSeleccion(document.seleccionarOrganizacionForm.seleccionados, 'tablaOrganizacion');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
