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
		..:: <vgcutil:message key="jsp.reportes.plan.meta.titulo" />
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
			<%-- Validacion del Tipo de Reporte --%>
			var url = '?ano=' + document.reporteForm.anoInicial.value;
			url = url + '&planId=' + document.reporteForm.planId.value;
			url = url + '&acumular=' + (document.getElementById("checkAcumular").checked ? "1" : "0");
			url = url + "&orientacion=H";
			
			if (document.reporteForm.tipoReporte[0].checked)
    	 		abrirReporte('<html:rewrite action="/reportes/planes/metaReportePdf"/>' + url, 'Reporte');
			else if (document.reporteForm.tipoReporte[1].checked)
				abrirReporte('<html:rewrite action="/reportes/planes/metaReporteXls"/>' + url, 'Reporte');
    	 	cancelar();
		}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/planes/meta">

			<html:hidden property="planId" />
			
			<vgcinterfaz:contenedorForma width="360px" height="340px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.plan.meta.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles width="300px" height="140px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="parametros">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.titulo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombreOrganizacion" />', 30);</script></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td colspan="3"><b><script>truncarTxt('<bean:write name="reporteForm" property="nombrePlan" />', 30);</script></b></td> 
							</tr>
							<tr>
								<td colspan="4"><hr width="100%"></td>
							</tr>
							<!-- Encabezado selector de fechas -->
							<tr>
								<td align="center" colspan="4"><b><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.ano" /></b></td>
							</tr>
														
							<!-- Ano Plan -->
							<tr>
								<td align="center" colspan="4">
									<html:select property="anoInicial" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								
								</td>
							</tr>
							
							<!-- Tipo Reporte -->
							<tr>
								<td colspan="4"><hr width="100%"></td>
							</tr>
							<tr>
								<td align="center" colspan="4"><b><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" /></b></td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />&nbsp;&nbsp;&nbsp;
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />								
								</td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<input type="checkbox" name="checkAcumular" id="checkAcumular" >
									<vgcutil:message key="jsp.reportes.plan.meta.reporte.acumular.anos" />
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