<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/03/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configuraredicionmetas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
				
			function editarMetas() 
			{
				if (!document.editarMetasForm.tipoOperacion[0].checked) 
					document.editarMetasForm.establecerMetasSoloIndicadoresSeleccionados.value = false;	
				if (document.editarMetasForm.anoDesde != null) 
				{
					if (document.editarMetasForm.anoDesde.value > document.editarMetasForm.anoHasta.value) 
					{
				 		alert('<vgcutil:message key="jsp.configuraredicionmetas.mensaje.errorano" />');
				 		return;
					}					
				}		
				document.editarMetasForm.action='<html:rewrite action="planes/metas/editarMetas" />';
				document.editarMetasForm.submit();				
			}
		
			function cancelar() 
			{
				document.editarMetasForm.action='<html:rewrite action="planes/metas/cancelarConfigurarEdicionMetas" />';
				document.editarMetasForm.submit();
			}

		</script>

		<html:form onsubmit="return true;" action="planes/metas/configurarEdicionMetas" focus="anoHasta">

			<html:hidden property="establecerMetasSoloIndicadoresSeleccionados" />

			<vgcinterfaz:contenedorForma width="350px" height="300px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.configuraredicionmetas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="95%" height="100%">

					<%-- Campo: Organización --%>
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.organizacion" /></b>: <bean:write name="editarMetasForm" property="nombreOrganizacion" /></td>						
					</tr>

					<%-- Campo: Plan --%>
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.plan" /></b>: <bean:write name="editarMetasForm" property="nombrePlan" /></td>
					</tr>			
					
					<%-- Espacio en Blanco --%>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>

					<%-- Campo: Año Periodo Desde / Hasta --%>
					<tr>
						<td width="50%">
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" >
								<tr>
									<td colspan="2"><img src="<html:rewrite page='/componentes/visorArbol/arrow_gris.gif'/>" border="0"> <b><vgcutil:message key="jsp.configuraredicionmetas.ficha.desde" /></b></td>
								</tr>
								<tr>
									<td><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.ano" /></b></td>
									<td width="100%">
										<html:select property="anoDesde" styleClass="cuadroTexto">
											<html:optionsCollection property="anos" value="clave" label="valor" />
										</html:select>
									</td>
								</tr>
							</table>
						</td>
						<td width="50%">
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" >
								<tr>
									<td colspan="2"><img src="<html:rewrite page='/componentes/visorArbol/arrow_gris.gif'/>" border="0"> <b><vgcutil:message key="jsp.configuraredicionmetas.ficha.hasta" /></b></td>
								</tr>
								<tr>
									<td><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.ano" /></b></td>
									<td width="100%">
										<html:select property="anoHasta" styleClass="cuadroTexto">
											<html:optionsCollection property="anos" value="clave" label="valor" />
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<%-- Espacio en Blanco --%>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					
					<!-- Campo: Solo Seleccionados / Todos los Seleccionados -->
					<tr>
						<td colspan="2" >
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" >
								<tr>
									<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="tipoOperacion" value="0" class="botonSeleccionSimple" ></td>
									<td><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion.soloseleccionados" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="tipoOperacion" value="1" class="botonSeleccionSimple" checked="true" ></td>
									<td><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion.todos" /></b></td>
								</tr>
							</table>
						</td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:editarMetas();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>
