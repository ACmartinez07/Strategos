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
		..:: <vgcutil:message key="jsp.vistasdatos.ejecucion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			function getQueryVariable(variable) {
			   var query = window.location.search.substring(1);
			   var vars = query.split("&");
			   for (var i=0; i < vars.length; i++) {
			       var pair = vars[i].split("=");
			       if(pair[0] == variable) {
			           return pair[1];
			       }
			   }
			   return false;
			}
	
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
				var reporteId = getQueryVariable('reporteId');
				
				// en linea
				if (document.reporteForm.tipoReporte[0].checked)
					abrirReporte('<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>?reporteId=' + reporteId);
				
	    	 	// segundo plano excel
				else if (document.reporteForm.tipoReporte[1].checked)
					abrirReporte('<html:rewrite action="/reportes/iniciativas/ejecucionIniciativaXls"/>?'+url+'&organizacionId=<bean:write name="organizacionId" scope="session" />');
	    	 	cancelar();
				
	    	 	
			}
			
			
	

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/vistasdatos/seleccionarVista">

			<html:hidden property="nombrePlan" />
			<html:hidden property="planId" />
			<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			
			
			<input type='hidden' name='filtroNombre' value='<bean:write name="reporteForm" property="filtro.nombre" />'>
			<input type='hidden' name='selectHitoricoType' value='<bean:write name="reporteForm" property="filtro.historico" />'>
			
			<vgcinterfaz:contenedorForma width="380px" height="220px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.vistasdatos.ejecucion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="100px" width="300px" nombre="reporteCriterios">
					
				<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="150px" nombre="salida">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.vistadatos.reporte.modo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
														
							<tr>
								<td colspan="3">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.vistasdatos.ejecucion.linea" />&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>	
								<td colspan="3">	
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.vistasdatos.ejecucion.segundo.plano" />
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