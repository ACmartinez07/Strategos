<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias(28/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<logic:equal name="editarExplicacionForm" property="tipo" value="0">
			<vgcutil:message key="jsp.propiedadesexplicacion.titulo" />
		</logic:equal>
		<logic:equal name="editarExplicacionForm" property="tipo" value="1">
			<vgcutil:message key="jsp.propiedadesexplicacion.informes.cualitativos.titulo" />
		</logic:equal>
		<logic:equal name="editarExplicacionForm" property="tipo" value="2">
			<vgcutil:message key="jsp.propiedadesexplicacion.informes.ejecutivos.titulo" />
		</logic:equal>
		<logic:equal name="editarExplicacionForm" property="tipo" value="3">
			<vgcutil:message key="jsp.propiedadesexplicacion.eventos.titulo" />
		</logic:equal>
		<logic:equal name="editarExplicacionForm" property="tipo" value="4">
			<vgcutil:message key="jsp.propiedadesexplicacion.noticia.titulo" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function cancelar() 
			{
				this.close();
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/explicaciones/propiedadesExplicacion" styleClass="formaHtml">

			<html:hidden property="explicacionId" />
			
			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..::<vgcutil:message key="jsp.propiedadesexplicacion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<vgcinterfaz:contenedorPaneles width="390" height="370" nombre="propiedadesExplicacion">

					<%-- Contenedor de Paneles --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">
					
						<%-- Título del Panel General  --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesexplicacion.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor" cellspacing="0">

							<!-- Campo Organización -->
							<tr>
								<td>
									<img src="<html:rewrite page='/paginas/strategos/imagenes/explicacion.gif'/>" border="0" width="40" height="40">
									&nbsp;&nbsp;<b><bean:write scope="request" name="editarExplicacionForm" property="nombreOrganizacion" /></b>
								</td>
							</tr>

							<tr>
								<td><hr width="100%"></td>
							</tr>

							<!-- Campo nombreObjetoKey y nombreObjeto -->
							<tr>
								<td><b><bean:write scope="request" name="editarExplicacionForm" property="nombreTipoObjetoKey" /></b> (<bean:write scope="request" name="editarExplicacionForm" property="nombreObjetoKey" />)</td>
							</tr>

							<!-- Campo Título -->
							<tr>
								<td><bean:write scope="request" name="editarExplicacionForm" property="titulo" /></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
