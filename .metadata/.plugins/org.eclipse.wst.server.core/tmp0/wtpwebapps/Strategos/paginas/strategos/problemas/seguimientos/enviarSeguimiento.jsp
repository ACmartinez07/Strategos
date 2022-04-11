<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.enviarseguimiento.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			
			function guardar(anadirNota) {			
				if (anadirNota == false) {
					window.document.editarSeguimientoForm.nota.value = "";
				}							
				window.document.editarSeguimientoForm.action = '<html:rewrite action="/problemas/seguimientos/guardarSeguimiento"/>' + '?ts=<%= (new java.util.Date()).getTime() %>'; 
				window.document.editarSeguimientoForm.submit();				
			}
			
			function cancelar() {			
				window.document.editarSeguimientoForm.action = '<html:rewrite action="/problemas/seguimientos/cancelarGuardarSeguimiento"/>';
				window.document.editarSeguimientoForm.submit();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/seguimientos/guardarSeguimiento" focus="nota">

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    ..::<vgcutil:message key="jsp.enviarseguimiento.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="enviarSeguimiento">

					<%-- Panel: Nota --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="nota">

						<%-- Título del Nota --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.enviarseguimiento.pestana.nota" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nota -->
							<tr>
								<td align="center"><html:textarea property="nota" rows="23" cols="55" styleClass="cuadroTexto" /></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar(true);" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="jsp.enviarseguimiento.anadir" /></a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar(false);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="jsp.enviarseguimiento.no.anadir" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
