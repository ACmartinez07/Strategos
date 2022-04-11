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
		<vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
			function cerrarVentana() {
				this.close();
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/auditorias/gestionarAuditoriasPorAtributo" styleClass="formaHtmlCompleta">

		

			<vgcinterfaz:contenedorForma width="950px" height="670px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..:: <vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
				
					<table width="100%" cellpadding="1" cellspacing="0">
						
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.columna.fecha" /></td>
							<td><bean:write name="gestionarAuditoriasPorAtributoForm" property="fechaDesde"/></td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.columna.nombreobjeto" /></td>
							<td><bean:write name="gestionarAuditoriasPorAtributoForm" property="entidad"/></td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.columna.usuario" /></td>
							<td><bean:write name="gestionarAuditoriasPorAtributoForm" property="usuarioNombre"/></td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.columna.tipoevento" /></td>
							<td><bean:write name="gestionarAuditoriasPorAtributoForm" property="tipoEvento"/></td>
						</tr>
					
					</table>
				
				
						
				
					
				</vgcinterfaz:contenedorFormaBarraGenerica>


				
				
				<vgcinterfaz:visorLista namePaginaLista="paginaAuditoriasDetalle" nombre="visorAuditoriasPorAtributo" messageKeyNoElementos="jsp.framework.gestionarauditoriasporatributo.noauditorias" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					
					<%-- Columnas visor --%>
					
					<vgcinterfaz:columnaVisorLista nombre="atributo" width="280px" >
						<vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.columna.nombreatributo" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="valor" width="600px" >
						<vgcutil:message key="jsp.framework.gestionarauditoriasporatributo.columna.valor" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:filasVisorLista nombreObjeto="auditoriaDetalle">
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="atributo" align="left">
							<bean:write name="auditoriaDetalle" property="atributo" />			
						</vgcinterfaz:valorFilaColumnaVisorLista>
												
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="valor" align="left">
							<bean:write name="auditoriaDetalle" property="valor" />
						</vgcinterfaz:valorFilaColumnaVisorLista>	
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>

			

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentana();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
                </vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>
</tiles:insert>