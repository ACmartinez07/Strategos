<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/10/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.visualizariniciativasrelacionadas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function cancelar() 
			{
				window.document.relacionIniciativaForm.action = '<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas"/>?accion=cancelar';
				window.document.relacionIniciativaForm.submit();
			}

			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas"/>?iniciativaId=' + document.relacionIniciativaForm.iniciativaSeleccionadaId.value + '&selectedId=' + nodoId + marcadorAncla;
			}
			
			function openNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas"/>?iniciativaId=' + document.relacionIniciativaForm.iniciativaSeleccionadaId.value + '&openId=' + nodoId + marcadorAncla;
			}
			
			function closeNodo(nodoId, marcadorAncla) 
			{					
				window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas"/>?iniciativaId=' + document.relacionIniciativaForm.iniciativaSeleccionadaId.value + '&closeId=' + nodoId + marcadorAncla;
			}

			function mostrarDetalleIniciativa()
			{
				window.location.href = '<html:rewrite action="/planificacionseguimiento/gestionarPlanificacionSeguimientos"/>?iniciativaId=<bean:write name="iniciativa" property="iniciativaId" scope="session" />';
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/iniciativas/visualizarIniciativasRelacionadas" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>			
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="iniciativaSeleccionadaId" />

			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true" bodyAlign="left">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.visualizariniciativasrelacionadas.titulo" />					
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">
						<%-- Organización --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px">
								<b><vgcutil:message key="jsp.visualizariniciativasrelacionadas.barraherramientas.organizacion" /></b> :
							</td>
							<td><bean:write name="relacionIniciativaForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>
					</table>

					<vgcinterfaz:barraHerramientas nombre="barraVisualizarIniciativas">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="planificacion" pathImagenes="/paginas/strategos/iniciativas/imagenes/" nombre="planificacion" onclick="javascript:mostrarDetalleIniciativa();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.actividades" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Arbol --%>
				<treeview:treeview 
					useFrame="false" 
					arbolBean="gestionarIniciativasRelacionadasArbolBean"
					scope="session" 
					isRoot="true" 
					fieldName="Nombre" 
					fieldId="iniciativaId" 
					fieldChildren="hijos" 
					lblUrlObjectId="iniciativaId" 
					styleClass="treeview" 
					checkbox="false" 
					pathImages="/paginas/strategos/planes/imagenes/gestionarPerspectivas/" 
					urlJs="/componentes/visorArbol/visorArbol.js" 
					nameSelectedId="iniciativaId" 
					urlName="javascript:seleccionarNodo(iniciativaId, marcadorAncla);" 
					urlConnectorOpen="javascript:openNodo(iniciativaId, marcadorAncla);" 
					urlConnectorClose="javascript:closeNodo(iniciativaId, marcadorAncla);" 
					lblUrlAnchor="marcadorAncla" 
					useNodeImage="true" />

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

	</tiles:put>

</tiles:insert>
