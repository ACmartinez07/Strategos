<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asistente.tipo.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript">

			var _setCloseParent = false;

			function init()
			{
			}

			function cancelar() 
			{
				this.close();
			}

			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function aceptar()
			{
				var corteSeleccionado = document.getElementById("corteSeleccionado");
				var corte = null;
				if (corteSeleccionado.checked)
					corte = 1;
				else
					corte = 2;

				window.opener.onNueva(corte);
				cancelar();
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<html:form action="/reportes/corteReporte" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
			<vgcinterfaz:contenedorForma width="350px" height="190px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.asistente.tipo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table width="100%" class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
					<%-- Espacio en Blanco --%>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<!-- Campo: Solo Seleccionados / Todos los Seleccionados -->
					<tr>
						<td colspan="4">
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
								<tr>
									<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" id="corteSeleccionado" name="corteSeleccionado" value="true" class="botonSeleccionSimple" checked="true"></td>
									<td><b><vgcutil:message key="jsp.asistente.tipo.longitudinal" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" id="corteSeleccionado" name="corteSeleccionado" value="false" class="botonSeleccionSimple"></td>
									<td><b><vgcutil:message key="jsp.asistente.tipo.transversal" /></b></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:aceptar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
						
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
	</tiles:put>
</tiles:insert>
