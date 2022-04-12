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
			var _setCloseParent = false;
		
		
			function cancelar() 
			{
				window.close();						
			}
		
			function generarReporte() 
			{
		 		 if(!<%= session.getAttribute("isAdmin") %> && document.reporteForm.alcance.value == 3 ){
		 			 alert ('Este reporte  solo puede ser ejecutado desde una cuenta Administrador');
		 	 	}else{
					var url = '&alcance=' + document.reporteForm.alcance.value;	
						url = url + '&instrumentoId=' +	document.reporteForm.id.value;
						url = url + '&nombre=' +	document.reporteForm.nombre.value;
						url = url + '&anio=' +	document.reporteForm.ano.value;
						url = url + '&cooperante=' +	document.reporteForm.cooperanteId.value;
						url = url + '&tipoconvenio=' +	document.reporteForm.tipoCooperanteId.value;
										
					if (document.reporteForm.tipoReporte[0].checked)
						abrirReporte('<html:rewrite action="//instrumentos/reporteProyectosAsociadosPdf"/>?'+url);
	    	 	
					else if (document.reporteForm.tipoReporte[1].checked)
						abrirReporte('<html:rewrite action="/instrumentos/reporteInstrumentoDetalleXls"/>?'+url);
		 	 	}
		 		cancelar();
			}
			
		</script>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/reporteProyectosAsociados">
		
		<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			<html:hidden property="id" />
			<html:hidden property="nombre" />
			<html:hidden property="ano" />
			<html:hidden property="cooperanteId" />
			<html:hidden property="tipoCooperanteId" />
			
		<vgcinterfaz:contenedorForma width="460px" height="360px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
			
			<%-- Título--%>
			<vgcinterfaz:contenedorFormaTitulo>..::					
				<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.resumido" />
			</vgcinterfaz:contenedorFormaTitulo>
			
			<%-- Paneles --%>
			<vgcinterfaz:contenedorPaneles height="240px" width="400px" nombre="reporteProyectosAsociados">
				<%-- Panel: Selector --%>
				<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
					<%-- Título del Panel: Datos Básicos --%>
					<vgcinterfaz:panelContenedorTitulo>
						<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
					</vgcinterfaz:panelContenedorTitulo>
					
					<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							
							<tr>
								<td colspan="3">
									<html:radio property="alcance" value="1">
										<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.instrumento" />
									</html:radio>
								</td>
							</tr>
							
							<tr>
								<td colspan="3">
									<html:radio property="alcance" value="4">
										<vgcutil:message key="jsp.pagina.instrumentos.reporte.titulo.todos" />
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
							
						</table>
				</vgcinterfaz:panelContenedor>
				
				<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
																				
							<tr>
								<td><vgcutil:message key="reporte.framework.usuarios.organizacion.tiporeporte" /></td>
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