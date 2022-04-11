<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (10/03/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configuraredicionvaloresiniciales.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
				
			function editarValoresIniciales() 
			{			
				if (!document.editarValoresInicialesForm.tipoOperacion[0].checked) 
					window.document.editarValoresInicialesForm.establecerMetasSoloIndicadoresSeleccionados.value = false;	
				window.document.editarValoresInicialesForm.action='<html:rewrite action="planes/valoresiniciales/editarValoresIniciales" />';
				window.document.editarValoresInicialesForm.submit();				
			}
		
			function cancelar() 
			{			
				window.document.editarValoresInicialesForm.action='<html:rewrite action="planes/valoresiniciales/cancelarConfigurarEdicionValoresIniciales" />';
				window.document.editarValoresInicialesForm.submit();
			}
			
			function validarClick() 
			{
				if (document.editarValoresInicialesForm.tipoOperacion[0].checked) 
					window.document.editarValoresInicialesForm.visualizarIndicadoresCompuestos.checked = true;	
			}

		</script>

		<html:form action="planes/valoresiniciales/editarValoresIniciales">

			<html:hidden property="establecerMetasSoloIndicadoresSeleccionados" />

			<vgcinterfaz:contenedorForma width="350px" height="250px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.configuraredicionvaloresiniciales.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" width="95%" height="100%">

					<%-- Campo: Organización --%>
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.organizacion" /></b>: <bean:write name="editarValoresInicialesForm" property="nombreOrganizacion" /></td>						
					</tr>

					<%-- Campo: Plan --%>
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.plan" /></b>: <bean:write name="editarValoresInicialesForm" property="nombrePlan" /></td>
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
									<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.seleccion" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="tipoOperacion" onclick="validarClick()" value="0" class="botonSeleccionSimple" ></td>
									<td><b><vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.seleccion.soloseleccionados" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="tipoOperacion" value="1" class="botonSeleccionSimple" checked="true"></td>
									<td><b><vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.seleccion.todos" /></b></td>
								</tr>
							</table>
						</td>
					</tr>
					
					<!-- Campo: Mostrar Indicadores Compuestos -->
					<tr>
						<td colspan="2" >
							<html:checkbox property="visualizarIndicadoresCompuestos" onclick="validarClick()" styleClass="botonSeleccionMultiple" /> <vgcutil:message key="jsp.configuraredicionvaloresiniciales.ficha.mostrarindicadorescompuesto" />
						</td>
					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:editarValoresIniciales();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">			
			
			document.editarValoresInicialesForm.visualizarIndicadoresCompuestos.checked = true;
			//document.editarValoresInicialesForm.visualizarIndicadoresCompuestos.disabled = true;
			
		</script>

	</tiles:put>	

</tiles:insert>
