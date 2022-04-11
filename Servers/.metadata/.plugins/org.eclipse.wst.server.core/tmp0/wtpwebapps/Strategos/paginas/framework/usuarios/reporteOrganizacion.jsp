<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="reporte.framework.usuarios.organizacion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cerrarVentana() {
				this.close();
			}
			
			function seleccionarOrganizaciones() 
		    {
				abrirSelectorOrganizaciones('reporteUsuariosForm', 'organizacionNombre', 'organizacionId', null);
			}
			
			function generarReporte(){

				var url = 'estatus=' + document.reporteUsuariosForm.estatus.value;				
				url = url + '&organizacionId='+ document.reporteUsuariosForm.organizacionId.value;
				
				if (document.reporteUsuariosForm.tipoReporte[0].checked)
					abrirReporte('<html:rewrite action="/framework/usuarios/reporteUsuariosOrganizacionPdf"/>?'+ url);
				
				else if (document.reporteUsuariosForm.tipoReporte[1].checked)
					abrirReporte('<html:rewrite action="/framework/usuarios/reporteUsuariosOrganizacionExcel"/>?'+url);
				window.close();
			}

		</script>
	
		<jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/usuarios/reporteUsuariosOrganizacion" styleClass="formaHtmlCompleta">
		
		
		<html:hidden property="organizacionId"/>
		<html:hidden property="organizacionNombre"/>
		
			<vgcinterfaz:contenedorForma width="520px" height="300px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..:: <vgcutil:message key="reporte.framework.usuarios.organizacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="180px" width="400px" nombre="reporteUsuariosOrganizacion">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							
							</tr>
							
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							
							<!-- Organizacion Seleccionada-->
							<tr >
								<td><vgcutil:message key="reporte.framework.usuarios.detallado.organizacion" /></td>
								<td align="left"><input  type="button" style="width:80%" class="cuadroTexto" value="<vgcutil:message key="jsp.seleccionarindicador.seleccionarorganizacion" />" onclick="seleccionarOrganizaciones();"></td>
						</tr>
							
							</tr>
							
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							
							<tr >
							<td><vgcutil:message key="reporte.framework.usuarios.resumido.estatus" /></td>
							<td align="left">
								<html:select property="estatus" styleClass="cuadroTexto" size="1">
									<html:option value="2">
										<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.todos" />
									</html:option>
									<html:option value="0">
										<vgcutil:message key="jsp.framework.editarusuario.label.estatus.activo" />
									</html:option>
									<html:option value="1">
										<vgcutil:message key="jsp.framework.seleccionarusuarios.columna.inactivo" />
									</html:option>
									
									
								</html:select></td>
							</td>							
							</tr>
							
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
																												
							<tr >
							<td><vgcutil:message key="reporte.framework.usuarios.organizacion.tiporeporte" /></td>
							<td align="left">
								<html:radio property="tipoReporte" value="1" />
									<vgcutil:message key="reporte.framework.usuarios.organizacion.tiporeporte.pdf" />
								
								<html:radio property="tipoReporte" value="2" /> 
									<vgcutil:message key="reporte.framework.usuarios.organizacion.tiporeporte.excel" />
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

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarReporte();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>
				
				
				
			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>