<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesclaseproblemas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/clasesproblemas/propiedadesClaseProblemas" styleClass="formaHtml">

			<html:hidden property="claseId" />

			<vgcinterfaz:contenedorForma width="430px" height="465px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesclaseproblemas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="370" width="390" nombre="propiedadesClaseProblemas">

					<%-- Panel: General --%>
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="general">

						<%-- Título del Panel General --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesclaseproblemas.pestana.general" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="0">

							<!-- Nombre de la Clase -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/imagenes/problemas.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="editarClaseProblemasForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td>
								<hr width="100%">
								</td>
							</tr>

							<!-- Campo Número de Clases Asociadas -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesclaseproblemas.pestana.general.numeroclasesasociadas" />:<b> <bean:write scope="request" name="editarClaseProblemasForm" property="numeroHijos" /></b></td>
							</tr>

							<!-- Campo Número de Indicadores Asociados -->
							<tr>
								<td><vgcutil:message key="jsp.propiedadesclaseproblemas.pestana.general.numeroproblemasasociados" />:<b> <bean:write scope="request" name="editarClaseProblemasForm" property="numeroProblemas" /></b></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>