<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.asignar.pesos.portafolio.titulo" />
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<script type="text/javascript">
		</script>
		
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Representación de la Forma --%>
		<html:form action="/instrumentos/asignarPesos" styleClass="formaHtml">
		
			<vgcinterfaz:contenedorForma>
			
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.asignar.pesos.portafolio.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>
				
				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100" style="padding: 1px;">
						
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.asignar.pesos.portafolio.organizacion" /></b></td>
								<td>
								<logic:notEmpty name="editarInstrumentosForm" property="organizacion">
									<logic:notEmpty name="editarInstrumentosForm" property="organizacion.nombre">
										<bean:write name="editarInstrumentosForm" property="organizacion.nombre" />
									</logic:notEmpty>
								</logic:notEmpty>
								<logic:empty name="editarInstrumentosForm" property="organizacion">
									<vgcutil:message key="jsp.asignar.pesos.portafolio.noaplica" />
								</logic:empty>
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>
						
			</vgcinterfaz:contenedorForma>
		</html:form>
		
	</tiles:put>

</tiles:insert>