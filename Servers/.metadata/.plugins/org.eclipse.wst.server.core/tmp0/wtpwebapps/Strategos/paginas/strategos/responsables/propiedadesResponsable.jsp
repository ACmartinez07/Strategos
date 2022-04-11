<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (18/07/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.propiedadesresponsable.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cerrarVentana() {				
				window.close();						
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/responsables/propiedadesResponsable">

			<html:hidden property="responsableId" />

			<vgcinterfaz:contenedorForma width="440px" height="450px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message key="jsp.propiedadesresponsable.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="357px" width="400px" nombre="propiedadesResponsable">

					<%-- Panel: General  --%>
					<vgcinterfaz:panelContenedor anchoPestana="150px" nombre="general">

						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.propiedadesresponsable.pestana.titulo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<!-- Imágen + Nombre del Responsable -->
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/responsables/imagenes/responsables.gif'/>" border="0" width="40" height="40">&nbsp;&nbsp;<b><bean:write scope="request"
									name="editarResponsableForm" property="nombre" /></b></td>
							</tr>

							<tr>
								<td><hr width="100%"></td>
							</tr>

							<!-- Organizacion -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesresponsable.ficha.organizacion" /></b>: <bean:write name="editarResponsableForm" property="organizacion.nombre" /> </td>
							</tr>
							
							<!-- Cargo -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesresponsable.ficha.cargo" /></b>: <bean:write name="editarResponsableForm" property="cargo" /> </td>
							</tr>
							
							<!-- Email -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesresponsable.ficha.email" /></b>: <bean:write name="editarResponsableForm" property="email" /> </td>
							</tr>
							
							<!-- Usuario -->
							<tr>
								<td><b><vgcutil:message key="jsp.propiedadesresponsable.ficha.usuario" /></b>:
								<logic:notEmpty name="editarResponsableForm" property="usuario">
									(<bean:write name="editarResponsableForm" property="usuario.UId" />) <bean:write name="editarResponsableForm" property="usuario.fullName" /> </td>								
								</logic:notEmpty>
							</tr>							
							
							<tr>
								<td><hr width="100%"></td>
							</tr>
							
							<!-- Título -->
							<tr>
								<td>									
									
									<b><vgcutil:message key="jsp.propiedadesresponsable.ficha.contiene" /></b><br><br>
									
									<div style="overflow:auto; height:118px; width:375px;">
									
									<%-- Visor Tipo Lista --%>
									<table class="listView" width="100%" cellpadding="5" cellspacing="1">																				
										<tr class="encabezadoListView">
											<td align="center" ><vgcutil:message key="jsp.propiedadesresponsable.ficha.nombre" /></td>
											<td align="center" ><vgcutil:message key="jsp.propiedadesresponsable.ficha.cargo" /></td>
											<td align="center" ><vgcutil:message key="jsp.propiedadesresponsable.ficha.email" /></td>
										</tr>									
									
										<%-- Cuerpo del Visor Tipo Lista --%>
										<logic:iterate id="responsableAsociado" name="editarResponsableForm" property="responsables" >				
											<tr class="mouseFueraCuerpoListView" onMouseOver="this.className='mouseEncimaCuerpoListView'" onMouseOut="this.className='mouseFueraCuerpoListView'" height="20px">				
												<td><bean:write name="responsableAsociado" property="nombre" /></td>										
												<td><bean:write name="responsableAsociado" property="cargo" /></td>
												<td><bean:write name="responsableAsociado" property="email" /></td>
											</tr>									
										</logic:iterate>
										
										<%-- Validación cuando no hay dependencias --%>
										<vgclogica:tamanoColeccionIgual name="editarResponsableForm" property="responsables" value="0">
											<tr class="mouseFueraCuerpoListView" id="0" height="20px">
												<td valign="middle" align="center" colspan="3"><vgcutil:message key="jsp.gestionarresponsables.noregistros" /></td>
											</tr> 
										</vgclogica:tamanoColeccionIgual>
										
									</table>
									
									</div>
									
								</td>
							</tr>
							
						</table>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cerrar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>
