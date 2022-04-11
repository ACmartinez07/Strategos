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
		..:: <vgcutil:message key="reporte.framework.usuarios.grupo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
		var _fechaDesde;
		var _fechaHasta;
			
	
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() {
				
				var url = '?estatus=' + document.reporteUsuariosForm.estatus.value;
			
				var selectGrupo = document.getElementById('selectGrupos');
				if (selectGrupo != null)
					url = url + '&selectGrupos=' + selectGrupo.value;
			

	          if (document.reporteUsuariosForm.tipoReporte[0].checked)
	              abrirReporte('<html:rewrite action="/framework/usuarios/reporteGrupoPdf"/>' + url);

	          else if (document.reporteUsuariosForm.tipoReporte[1].checked)
	              abrirReporte('<html:rewrite action="/framework/usuarios/reporteGrupoXls"/>' + url);

	           window.close();
	          
	       }
			
	

		</script>

		<jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/calendario/calendario.jsp"></jsp:include>
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/usuarios/reporteGrupo">
			
				
			<vgcinterfaz:contenedorForma width="460px" height="300px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="reporte.framework.usuarios.grupo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="150px" width="400px" nombre="reporteUsuariosGrupo">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							
							<!-- Campo tipo proyecto -->
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>		
												
							<tr>
								<td align="right"><vgcutil:message key="reporte.framework.grupos.listagrupos.grupo" /></td>
								<td>									
									&nbsp;
									<select class="cuadroCombinado" name="selectGrupos" id="selectGrupos">
										<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
										<logic:iterate name="reporteUsuariosForm" property="grupos" id="gru">
											<bean:define id="grupoId" toScope="page"><bean:write name='gru' property='grupoId' /></bean:define>
											<bean:define id="grupo" toScope="page"><bean:write name='gru' property='grupo' /></bean:define>
											<logic:equal name='reporteUsuariosForm' property='grupoId' value='<%=grupoId.toString()%>'>
												<option value="<%=grupoId%>" selected><%=grupo%></option>
											</logic:equal>
											<logic:notEqual name='reporteUsuariosForm' property='grupoId' value='<%=grupoId.toString()%>'>
												<option value="<%=grupoId%>"><%=grupo%></option>
											</logic:notEqual>
										</logic:iterate>
									</select>
						
								</td>
								<td></td>
								
							</tr>
							<!-- Campo tipo proyecto -->
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
													
							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.gestionarusuarios.columna.estatus" /></td>
								<td>
									&nbsp;
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
							
								<td></td>
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
								<td colspan="3">
									&nbsp;
								</td>
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