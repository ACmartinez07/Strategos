<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Andres Martinez (11/04/2022) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="reporte.framework.usuarios.organizacion.titulo" />
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<%-- Funciones JavaScript locales de la pagina Jsp --%>
		<script type="text/javascript">
			
			function cancelar() 
			{
				window.close();						
			}
			
		</script>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/reporteProyectosAsociados">
		
		
		<vgcinterfaz:contenedorForma width="460px" height="360px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
			
			<%-- T�tulo--%>
			<vgcinterfaz:contenedorFormaTitulo>..::					
				<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.resumido" />
			</vgcinterfaz:contenedorFormaTitulo>
			
			<%-- Paneles --%>
			<vgcinterfaz:contenedorPaneles height="240px" width="400px" nombre="reporteProyectosAsociados">
				<%-- Panel: Selector --%>
				<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
					<%-- T�tulo del Panel: Datos B�sicos --%>
					<vgcinterfaz:panelContenedorTitulo>
						<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
					</vgcinterfaz:panelContenedorTitulo>
					
					<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							
							<tr>
								<td colspan="3">
									
								</td>
							</tr>
							
							<tr>
								<td colspan="3">
									
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
				
				<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">
						
						<%-- T�tulo del Panel: Datos B�sicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
														
							<tr >
							<td><vgcutil:message key="reporte.framework.usuarios.organizacion.tiporeporte" /></td>
																			 
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
			
			<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarReporte();" class="mouseFueraBarraInferiorForma">
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