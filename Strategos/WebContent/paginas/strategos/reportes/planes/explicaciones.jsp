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
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
		function cancelar() 
		{
			window.close();						
		}
		
		function generarReporte() 
		{
			if (!validar())
				return;

			<%-- Validacion del Tipo de Reporte --%>
			var url = '?objetoKey=' + document.reporteForm.objetoStatus.value;
			url = url + '&planId=' + document.reporteForm.planId.value;
			url = url + '&fechaDesde=' + document.reporteForm.fechaDesde.value;
			url = url + '&fechaHasta=' + document.reporteForm.fechaHasta.value;
			url = url + "&orientacion=H";

    	 	abrirReporte('<html:rewrite action="/reportes/planes/explicacionReporte"/>' + url);
    	 	cancelar();
		}

		function seleccionarFechaDesde() 
		{
			mostrarCalendario('document.reporteForm.fechaDesde' , document.reporteForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
		}
	
		function seleccionarFechaHasta() 
		{
			mostrarCalendario('document.reporteForm.fechaHasta' , document.reporteForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
		}
		
		function validar() 
		{
	 		if (document.reporteForm.fechaDesde.value != "" && !fechaValida(document.reporteForm.fechaDesde))
 			{
	 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechadesde.invalido" /> ');
	 			return false;
 			}

	 		if (document.reporteForm.fechaHasta.value != "" && !fechaValida(document.reporteForm.fechaHasta))
 			{
	 			alert('<vgcutil:message key="jsp.reporteindicador.editor.alert.fechahasta.invalido" /> ');
	 			return false;
 			}
	 		
	 		if (document.reporteForm.fechaDesde.value != "" && document.reporteForm.fechaHasta.value != "" && !fechasRangosValidos(document.reporteForm.fechaDesde, document.reporteForm.fechaHasta))
	 			return false;
	 		
	 		return true;
		}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/planes/meta">

			<html:hidden property="planId" />
			
			<vgcinterfaz:contenedorForma width="440px" height="320px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reportes.plan.explicacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles width="380px" height="190px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					<vgcinterfaz:panelContenedor anchoPestana="150px" nombre="parametros">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.titulo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left" width="10px"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.organizacion" /> : </td>
								<td align="left"><b><bean:write name="reporteForm" property="nombreOrganizacion" /></b></td>
							</tr>
							<!-- Plan Seleccionado -->
							<tr>
								<td align="left" width="10px"><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.plan" /> : </td>
								<td align="left"><b><bean:write name="reporteForm" property="nombrePlan" /></b></td> 
							</tr>
							<tr>
								<td colspan="2"><hr width="100%"></td>
							</tr>
							<!-- Encabezado selector de Objetos -->
							<tr>
								<td align="center" colspan="2"><b><vgcutil:message key="jsp.reportes.plan.explicacion.objeto" /></b></td>
							</tr>
														
							<!-- Objeto -->
							<tr>
								<td align="center" colspan="2">
									<html:select property="objetoStatus" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoStatus" value="clave" label="valor" />
									</html:select>
								</td>
							</tr>
							<tr class="barraFiltrosForma">
								<td colspan="2">
									<table class="tabla contenedorBotonesSeleccion">
										<tr>
											<td width="50%">
												<table class="tabla contenedorBotonesSeleccion">
													<tr>
														<td width="40px"><vgcutil:message key="jsp.reportes.plan.explicacion.fecha.inicial" /></td>
														<td>
															<html:text property="fechaDesde" size="10" maxlength="10" styleClass="cuadroTexto" />
															<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
														</td>
													</tr>
												</table>
											</td>
											<td width="50%">
												<table class="tabla contenedorBotonesSeleccion">
													<tr>
														<td width="40px"><vgcutil:message key="jsp.reportes.plan.explicacion.fecha.final" /></td>
														<td>
															<html:text property="fechaHasta" size="10" maxlength="10" styleClass="cuadroTexto" />
															<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
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