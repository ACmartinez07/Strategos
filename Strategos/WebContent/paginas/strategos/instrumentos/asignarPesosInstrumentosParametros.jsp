<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (16/08/2022) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message
			key="jsp.asignar.pesos.portafolio.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
					
		
			function cancelar() {
				window.close();
			}
														
			function aceptar(){			
				
				var url = '?anio=' + document.editarInstrumentosForm.anio.value;
				url = url + '&estatus=' +document.editarInstrumentosForm.estatus.value
				
				abrirVentanaModal('<html:rewrite action="/instrumentos/asignarPesosInstrumentos"/>' + url , "instrumento", 710, 550);				
			}									
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>		

		<%-- Representación de la Forma --%>
		<html:form action="/instrumentos/asignarPesosInstrumentosParametros" styleClass="formaHtml">
					
			<vgcinterfaz:contenedorForma width="460px" height="360px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message
						key="jsp.asignar.pesos.instrumento.titulo"/>
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="240px" width="400px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							
							<tr >
								<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.estatus" /></td>
								<td>
									<html:select property="estatus" styleClass="cuadroTexto" size="1" disabled="true">																								
										<html:option value="2">
											<vgcutil:message key="jsp.pagina.instrumentos.estatus.ejecucion" />
										</html:option>																							
									</html:select>
								</td>
													
							</tr>
							
							<tr>
								<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.anio" /></td>
								<td colspan="3" >
									<html:text property="anio" size="5" maxlength="4" styleClass="cuadroTexto" />
								</td>	
							</tr>
							
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							
						</table>
					</vgcinterfaz:panelContenedor>
													
				</vgcinterfaz:contenedorPaneles>
				
				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:aceptar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<%-- Cancelar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>						
	</tiles:put>
</tiles:insert>