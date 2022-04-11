<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (07/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesperspectiva.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cerrar() {				
				window.close();						
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/perspectivas/propiedadesPerspectiva" styleClass="formaHtml">			

			<vgcinterfaz:contenedorForma width="440px" height="450px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesperspectiva.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<vgcinterfaz:contenedorPaneles height="357px" width="400px" nombre="propiedadesPerspectiva">

					<%-- Panel: General  --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">
						
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesperspectiva.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<!-- Nombre Perspectiva -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/perspectiva.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request"
									name="editarPerspectivaForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Elementos Asociados -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesperspectiva.pestana.general.elementosasociados" />: </b><bean:write scope="request" name="editarPerspectivaForm" property="elementosAsociados" /></td>
							</tr>
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesperspectiva.pestana.general.nombrefrecuencia" />: </b><bean:write scope="request" name="editarPerspectivaForm" property="nombreFrecuencia" /></td>
							</tr>							

						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		
	</tiles:put>
</tiles:insert>
