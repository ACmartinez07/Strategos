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
		<vgcutil:message key="jsp.visualizarperspectivasrelacionadas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function cancelar() 
			{
				window.document.seleccionarPerspectivasForm.action = '<html:rewrite action="/planes/perspectivas/visualizarPerspectivasRelacionadas"/>?accion=cancelar';
				window.document.seleccionarPerspectivasForm.submit();
			}

			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/planes/perspectivas/visualizarPerspectivasRelacionadas"/>?perspectivaId=' + document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value + '&selectedId=' + nodoId + marcadorAncla;
			}
			
			function openNodo(nodoId, marcadorAncla) 
			{
				window.location.href='<html:rewrite action="/planes/perspectivas/visualizarPerspectivasRelacionadas"/>?perspectivaId=' + document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value + '&openId=' + nodoId + marcadorAncla;
			}
			
			function closeNodo(nodoId, marcadorAncla) 
			{					
				window.location.href='<html:rewrite action="/planes/perspectivas/visualizarPerspectivasRelacionadas"/>?perspectivaId=' + document.seleccionarPerspectivasForm.perspectivaSeleccionadaId.value + '&closeId=' + nodoId + marcadorAncla;
			}

			function mostrarDetallePerspectiva()
			{
				window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectiva"/>?perspectivaId=<bean:write name="perspectiva" property="perspectivaId" scope="session" />&mostrarObjetosAsociados=true&vinculoCausaEfecto=0';
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planes/perspectivas/visualizarPerspectivasRelacionadas" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>			
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="perspectivaSeleccionadaId" />

			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true" bodyAlign="left" >

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.visualizarperspectivasrelacionadas.titulo" />					
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
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarperspectivas.barraherramientas.organizacion" /></b></td>
							<td><bean:write name="seleccionarPerspectivasForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>
						
						<%-- Plan --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px"><b><vgcutil:message key="jsp.seleccionarperspectivas.barraherramientas.plan" /></b></td>
							<td><bean:write name="seleccionarPerspectivasForm" property="nombrePlan" /></td>
						</tr>						

					</table>

					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarPerspectivas">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="perspectiva" pathImagenes="/paginas/strategos/imagenes/barraHerramientas/" nombre="perspectiva" onclick="javascript:mostrarDetallePerspectiva();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.ver.objetivos.relacionados" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Arbol --%>
				<%-- <jsp:include flush="true" page="/paginas/strategos/planes/perspectivas/visualizarPerspectivasRelacionadasArbol.jsp"></jsp:include> --%>
				<treeview:treeview 
					useFrame="false" 
					arbolBean="gestionarObjetivosRelacionadosArbolBean"
					scope="session" 
					isRoot="true" 
					fieldName="Nombre" 
					fieldId="perspectivaId" 
					fieldChildren="hijos" 
					lblUrlObjectId="perspectivaId" 
					styleClass="treeview" 
					checkbox="false" 
					pathImages="/paginas/strategos/planes/imagenes/gestionarPerspectivas/" 
					urlJs="/componentes/visorArbol/visorArbol.js" 
					nameSelectedId="perspectivaId" 
					urlName="javascript:seleccionarNodo(perspectivaId, marcadorAncla);" 
					urlConnectorOpen="javascript:openNodo(perspectivaId, marcadorAncla);" 
					urlConnectorClose="javascript:closeNodo(perspectivaId, marcadorAncla);" 
					lblUrlAnchor="marcadorAncla" 
					useNodeImage="true" />

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

	</tiles:put>

</tiles:insert>
