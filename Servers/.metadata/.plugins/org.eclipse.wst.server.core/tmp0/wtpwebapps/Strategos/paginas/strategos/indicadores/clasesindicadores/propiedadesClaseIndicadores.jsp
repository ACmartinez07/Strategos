<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (23/04/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesclaseindicadores.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cancelar() {
				window.document.editarClaseIndicadoresForm.action = '<html:rewrite action="/indicadores/clasesindicadores/cancelarClaseIndicadores"/>';
				window.close();				
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form
			action="/indicadores/clasesindicadores/propiedadesClaseIndicadores" styleClass="formaHtml">

			<html:hidden property="claseId" />

			<vgcinterfaz:contenedorForma width="520px" height="470px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20" marginTop="5px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..::<vgcutil:message
						key="jsp.propiedadesclaseindicadores.titulo.ficha" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="345" width="400"
					nombre="propiedadesClaseIndicadores">
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message
								key="jsp.propiedadesclaseindicadores.pestana.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellspacing="0">

							<!-- Campo Nombre de la Clase -->
							<tr>
								<td><img
									src="<html:rewrite page='/paginas/strategos/imagenes/indicadores.gif'/>"
									border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write
									scope="request" name="editarClaseIndicadoresForm"
									property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Campo Número de Calses Asociadas -->
							<tr>
								<td><vgcutil:message
									key="jsp.propiedadesclaseindicadores.ficha.numeroclasesasociadas" />:<b>
								<bean:write scope="request" name="editarClaseIndicadoresForm"
									property="cantidadHijos" /></b></td>
							</tr>

							<!-- Campo Número de Indicadores Asociados -->
							<tr>
								<td><vgcutil:message
									key="jsp.propiedadesclaseindicadores.ficha.numeroindicadoresasociados" />:<b>
								En Desarrollo... 
							</tr>

						</table>


					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>


				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<img
						src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script language="Javascript1.1"
			src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
