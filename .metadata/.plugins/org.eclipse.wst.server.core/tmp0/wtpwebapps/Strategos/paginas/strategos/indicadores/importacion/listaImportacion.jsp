<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (08/06/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.lista.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function seleccionar() 
			{
				if ((document.importarSeleccionForm.seleccionados.value == null) || (document.importarSeleccionForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				
				getSeleccion(document.importarSeleccionForm.seleccionados.value);
			}
			
			function getSeleccion(seleccionId)
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/eliminarImportacion" />?funcion=read&Id=' + seleccionId, document.importarSeleccionForm.seleccionados, 'onGetSeleccion()');
			}
			
			function onGetSeleccion()
			{
				this.opener.document.<bean:write name="importarSeleccionForm" property="nombreForma" scope="session" />.<bean:write name="importarSeleccionForm" property="nombreCampoOculto" scope="session" />.value=document.importarSeleccionForm.seleccionados.value;
				this.opener.<bean:write name="importarSeleccionForm" property="funcionCierre" scope="session" />;
				this.close();
			}
			
			function eliminar()
			{
				if ((document.importarSeleccionForm.seleccionados.value == null) || (document.importarSeleccionForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/indicadores/eliminarImportacion" />?funcion=eliminar&Id=' + document.importarSeleccionForm.seleccionados.value, null, 'onEliminar()');
			}
			
			function onEliminar()
			{
				actualizar();
			}
			
			function actualizar()
			{
				document.importarSeleccionForm.action = '<html:rewrite action="/indicadores/listaImportacion"/>';
				document.importarSeleccionForm.submit();
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/indicadores/listaImportacion" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombre" />
			
			<input type="hidden" name="actualizar" value="true" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionar.lista.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px">
								<b><vgcutil:message key="jsp.lista.seleccionar.barraherramientas.organizacion" /></b>
							</td>
							<td><bean:write name="importarSeleccionForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>

					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="470" width="470" nombre="panelesSeleccionarRegistros" mostrarSelectoresPaneles="false">

					<%-- Panel: Registros --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelRegistros" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.seleccionar.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/indicadores/importacion/listaPrincipalSeleccion.jsp"></jsp:include>						
					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/delete.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:eliminar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.eliminar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

		</html:form>
	</tiles:put>
</tiles:insert>
