<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>


<%-- Creado por: Kerwin Arias (19/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesvistas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			function cerrarVentana() 
			{
				this.close();
			}
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/vistasdatos/propiedades">

			<html:hidden property="reporteId" />

			<vgcinterfaz:contenedorForma width="430px" height="450px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesvistas.titulo.ficha" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="345" width="390" nombre="general">
					<vgcinterfaz:panelContenedor anchoPestana="150" nombre="">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesvistas.pestana.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<table cellspacing="0" class="panelContenedor">

							<!-- Nombre -->
							<tr>
								<td>
									<img src="<html:rewrite page='/paginas/strategos/imagenes/vistaDatos.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request" name="configurarVistaDatosForm" property="nombre" /></b>
								</td>
							</tr>

							<tr>
								<td>
									<hr width="100%">
								</td>
							</tr>

							<!-- Descripcion -->
							<tr>
								<td>
									<vgcutil:message key="jsp.propiedadesvistas.descripcion" />:<b> <bean:write scope="request" name="configurarVistaDatosForm" property="descripcion" /></b>
								</td>
							</tr>

							<!-- Publico-->
							<tr>
								<td>
									<vgcutil:message key="jsp.propiedadesvistas.publico" />:
									<b>
										<logic:equal name="configurarVistaDatosForm" property="publico" value="true">
											<vgcutil:message key="comunes.si" />
										</logic:equal>
										<logic:equal name="configurarVistaDatosForm" property="publico" value="false">
											<vgcutil:message key="comunes.no" />
										</logic:equal>
								 	</b>
								</td>
							</tr>

						</table>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.regresar" />
					</a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
