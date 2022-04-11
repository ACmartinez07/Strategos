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
		<vgcutil:message key="jsp.framework.gestionarauditoriasdetalle.titulo" />
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
		<html:form action="/framework/grupos/propiedadesGrupo" styleClass="formaHtml">

			<html:hidden property="grupoId" />

			<vgcinterfaz:contenedorForma width="900px" height="670px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..:: <vgcutil:message key="jsp.framework.gestionarauditoriasdetalle.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

			

					<vgcinterfaz:visorLista namePaginaLista="paginaAuditoriasDetalle" nombre="visorAuditoriasDetalle" messageKeyNoElementos="jsp.framework.gestionarauditorias.noauditorias" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
						<vgcinterfaz:columnaVisorLista nombre="accion" width="100px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.accion" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="ano" width="100px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.detalle.columna.ano" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="periodo" width="100px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.detalle.columna.periodo" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="serie" width="200px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.detalle.columna.valor" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="valor" width="150px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.detalle.columna.serie" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="valorAnterior" width="200px" >
							<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.detalle.columna.valor.anterior" />
						</vgcinterfaz:columnaVisorLista>
						
						
						<vgcinterfaz:filasVisorLista nombreObjeto="auditoriaDetalleMedicion">
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="accion" align="center">
								<bean:write name="auditoriaDetalleMedicion" property="accion" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="ano" align="center">
								<bean:write name="auditoriaDetalleMedicion" property="ano" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="periodo" align="center">
								<bean:write name="auditoriaDetalleMedicion" property="periodo" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="serie" align="left">
								<bean:write name="auditoriaDetalleMedicion" property="valor" format="###0"/>
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="valor" align="left">
								<bean:write name="auditoriaDetalleMedicion" property="serieNombre" />
							</vgcinterfaz:valorFilaColumnaVisorLista>					
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="valorAnterior" align="left">
								<logic:notEqual name="auditoriaDetalleMedicion" property="accion" value="Inserción">
									<bean:write name="auditoriaDetalleMedicion" property="valorAnterior" format="###0"/>
								</logic:notEqual>
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
