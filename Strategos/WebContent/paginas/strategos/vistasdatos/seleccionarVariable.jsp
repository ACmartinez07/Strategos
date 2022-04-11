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
		<vgcutil:message key="jsp.seleccionarvariable.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">			
			
			var seleccionMultiple = '<bean:write name="seleccionarVariableForm" property="seleccionMultiple" scope="session" />';
			
			function seleccionar() 
			{
				if ((document.seleccionarVariableForm.seleccionados.value == null) || (document.seleccionarVariableForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarVariableForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarVariableForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarVariableForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarVariableForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarVariableForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarVariableForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarVariableForm" property="funcionCierre" scope="session">					
					this.opener.<bean:write name="seleccionarVariableForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}
			
			function eventoClickFilaSeleccion(idsSeleccionados, valoresSeleccionados, nombreTabla, fila) 
			{	
				if (seleccionMultiple == '1') 
					eventoClickFilaSeleccionMultiple(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);										 
		       	else 
		         	eventoClickFilaV2(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);
		    }

			function eventoMouseFueraFilaSeleccion(idsSeleccionados, fila) 
			{	
		       	if (seleccionMultiple == '1') 
		   	      	eventoMouseFueraFilaSeleccionMultiple(idsSeleccionados, fila);		      
		       	else 
		          	eventoMouseFueraFilaV2(idsSeleccionados, fila);
		    }           
           
			function eventoMouseEncimaFilaSeleccion(idsSeleccionados, fila) 
			{
		    	if (seleccionMultiple == '1') 
		   	      	eventoMouseEncimaFilaSeleccionMultiple(idsSeleccionados, fila);		      
		    	else 
		        	eventoMouseEncimaFilaV2(idsSeleccionados, fila);
		    }           		   
		   	
		   	function inicializarVisorListaSeleccion(idsSeleccionados, nombreTabla) 
		   	{			
   		   	   	if (seleccionMultiple == '1') 
		   		  	inicializarVisorListaSeleccionMultiple(idsSeleccionados, nombreTabla);			      
   		   	   	else 
		          	inicializarVisorListaSeleccionSimple(idsSeleccionados, nombreTabla);
		   	}		
		   	
		   	function limpiarFiltros() 
		   	{
				seleccionarVariableForm.filtroNombre.value = "";
				seleccionarVariableForm.submit();
			}

			function buscar() 
			{
				seleccionarVariableForm.submit();
			}		
                        
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="javascript" src="<html:rewrite page='/componentes/visorLista/visorListaJsTag.jsp'/>"></script>

		<%-- Representación de la Forma --%>
		<html:form action="/vistasdatos/seleccionarVariable" styleClass="formaHtml">

			<%-- Variables de la Forma --%>
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
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionarvariable.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarVariableForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>				
				
				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarVariable">
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
							<td width="60px" align="right"><vgcutil:message key="jsp.seleccionarvariable.nombre" /></td>
							<td><html:text property="filtroNombre" size="55" styleClass="cuadroTexto" /></td>
							<td>&nbsp;</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>


				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" id="tablaVariable" cellspacing="1">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">

						<td align="center" width="10px" class="mouseFueraEncabezadoListView"><img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10"></td>
						<td align="center" width="100%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.seleccionarvariable.nombre" /><img name="nombre"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="seleccionarVariableForm" property="listaVariables" scope="session" id="variable">

						<tr onclick="eventoClickFilaSeleccion(document.seleccionarVariableForm.seleccionados, document.seleccionarVariableForm.valoresSeleccionados, 'tablaVariable', this)"
							id="<bean:write name="variable" property="valor" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaSeleccion(document.seleccionarVariableForm.seleccionados, this)"
							onMouseOut="eventoMouseFueraFilaSeleccion(document.seleccionarVariableForm.seleccionados, this)" height="20px">
							<td width="10" valign="middle" align="center"><img name="img<bean:write name="variable" property="valor" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>"
								border="0" width="10" height="10"></td>
							<td id="valor<bean:write name="variable" property="valor" />"><bean:write name="variable" property="nombre" /></td>
						</tr>

					</logic:iterate>

					<%-- Validación cuando no hay registros --%>


				</table>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la página Jsp --%>
			<script language="JavaScript" type="text/javascript">

				// Invoca la función que hace el ordenamiento de las columnas
				inicializarVisorListaSeleccion(document.seleccionarVariableForm.seleccionados, 'tablaVariable');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
