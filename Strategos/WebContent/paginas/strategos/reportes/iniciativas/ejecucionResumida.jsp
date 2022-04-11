<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Gustavo Chaparro (01/09/2013) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

		<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.reportes.iniciativa.ejecucion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			
	
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
			 	 if(!<%= session.getAttribute("isAdmin") %> && document.reporteForm.alcance.value == 3 ){
			 		 alert ('Este reporte  solo puede ser ejecutado desde una cuenta Administrador');
			 	 }else{
					var url = '&alcance=' + (document.reporteForm.alcance[0].checked ? "1" : "2");	
					url = url + '&filtroNombre=' + document.reporteForm.filtroNombre.value;
					url = url + '&selectHitoricoType=' + document.reporteForm.selectHitoricoType.value;
					url = url + '&orientacion=H';
					
					if (document.reporteForm.tipoReporte[0].checked)
						abrirReporte('<html:rewrite action="/reportes/iniciativas/ejecucionIniciativaPdf"/>?'+url+'&organizacionId=<bean:write name="organizacionId" scope="session" />');
		    	 	
					else if (document.reporteForm.tipoReporte[1].checked)
						abrirReporte('<html:rewrite action="/reportes/iniciativas/ejecucionIniciativaXls"/>?'+url+'&organizacionId=<bean:write name="organizacionId" scope="session" />');
			 	 }
				cancelar();
				
	    	 	
			}
			
			
	

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/iniciativas/ejecucionResumida">

			<html:hidden property="nombrePlan" />
			<html:hidden property="planId" />
			<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			
			
			<input type='hidden' name='filtroNombre' value='<bean:write name="reporteForm" property="filtro.nombre" />'>
			<input type='hidden' name='selectHitoricoType' value='<bean:write name="reporteForm" property="filtro.historico" />'>
			
			<vgcinterfaz:contenedorForma width="460px" height="460px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="340px" width="400px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><bean:write name="reporteForm" property="nombreOrganizacion"/></b></td>
								<td></td>
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
									<bean:define id="alcanceObjetivo" toScope="page">
										<bean:write name="reporteForm" property="alcanceObjetivo" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceObjetivo %>">
										<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector.objetivo.seleccionado" />
									</html:radio>
								</td>
							</tr>
								<tr>
									<td colspan="3">
										<bean:define id="alcanceOrganizacion" toScope="page">
											<bean:write name="reporteForm" property="alcanceOrganizacion" />
										</bean:define>
										<html:radio property="alcance" value="<%= alcanceOrganizacion %>">
											<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector.organizacion" />
										</html:radio>
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
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					
				<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3" ><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
														
							<tr>
								<td colspan="3" ><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />&nbsp;&nbsp;&nbsp;
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />
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