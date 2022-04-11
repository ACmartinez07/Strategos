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
				if ((document.seleccionarGraficoForm.seleccionados.value == null) || (document.seleccionarGraficoForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				
				getPropiedades(document.seleccionarGraficoForm.seleccionados.value);
			}
			
			function getPropiedades(graficoId)
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/eliminarGrafico" />?funcion=readFull&Id=' + graficoId, document.seleccionarGraficoForm.seleccionados, 'onGetPropiedades()');
			}
			
			function onGetPropiedades()
			{
				this.opener.document.<bean:write name="seleccionarGraficoForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarGraficoForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarGraficoForm.seleccionados.value;
				this.opener.<bean:write name="seleccionarGraficoForm" property="funcionCierre" scope="session" />;
				this.close();
			}
			
			function eliminar()
			{
				if ((document.seleccionarGraficoForm.seleccionados.value == null) || (document.seleccionarGraficoForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/graficos/eliminarGrafico" />?funcion=eliminar&Id=' + document.seleccionarGraficoForm.seleccionados.value, null, 'onEliminar()');
			}
			
			function onEliminar()
			{
				actualizar();
			}
			
			function actualizar()
			{
				document.seleccionarGraficoForm.action = '<html:rewrite action="/graficos/listaGrafico"/>';
				document.seleccionarGraficoForm.submit();
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/graficos/listaGrafico" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="graficoNombre" />
			
			<input type="hidden" name="actualizar" value="true" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.lista.titulo" />
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
							<td><bean:write name="seleccionarGraficoForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>

					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="457" width="457" nombre="panelesSeleccionarGraficos" mostrarSelectoresPaneles="false">

					<%-- Panel: Graficos --%>
					<vgcinterfaz:panelContenedor anchoPestana="120px" nombre="panelGraficos" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.lista.panel.graficos.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/graficos/listaPrincipalGrafico.jsp"></jsp:include>						
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
