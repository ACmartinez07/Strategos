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
		<vgcutil:message key="jsp.reporte.transaccion.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript">

			// Inicializar botones de los cuadros Numéricos
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
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
			
			function validar()
			{
		 		if (!fechaValida(document.transaccionForm.fechaInicial))
		 			return false;

		 		if (!fechaValida(document.transaccionForm.fechaFinal))
		 			return false;
				
		 		if (!fechasRangosValidos(document.transaccionForm.fechaInicial, document.transaccionForm.fechaFinal))
		 			return false;

		 		return true;
			}
			
			function aceptar()
			{
				if (!validar())
					return;
				
				var url = "transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;
				url = url + "&funcion=chequear";
				
				window.location.href='<html:rewrite action="/transacciones/reporteTransaccion" />?' + url;
			}

			function onAceptar()
			{
				var url = "transaccionId=" + document.transaccionForm.transaccionId.value;
				url = url + "&fechaInicial=" + document.transaccionForm.fechaInicial.value;
				url = url + "&fechaFinal=" + document.transaccionForm.fechaFinal.value;
				url = url + "&numeroRegistros=" + document.transaccionForm.numeroRegistros.value;
				
				window.opener.onReporteTransaccion(url);
				cancelar();
			}
			
			function seleccionarFechaComienzo() 
			{
				mostrarCalendarioConFuncionCierre('document.transaccionForm.fechaInicial' , document.transaccionForm.fechaInicial.value, '<vgcutil:message key="formato.fecha.corta" />', null);
			}
			
			function seleccionarFechaFin() 
			{
				mostrarCalendarioConFuncionCierre('document.transaccionForm.fechaFinal' , document.transaccionForm.fechaFinal.value, '<vgcutil:message key="formato.fecha.corta" />', null);
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<html:form action="/transacciones/reporteTransaccion" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
			<html:hidden property="transaccionId" />
		
			<vgcinterfaz:contenedorForma width="360px" height="200px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.reporte.transaccion.titulo" />
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
									<td colspan="2"><b><vgcutil:message key="jsp.reporte.transaccion.ficha.seleccion" /></b></td>
								</tr>

								<%-- Campo Fecha de comienzo --%>
								<tr>
									<td align="right"><vgcutil:message key="jsp.reporte.transaccion.ficha.fechacomienzo" /></td>
									<td>
										<html:text 
											property="fechaInicial" 
											size="12" 
											maxlength="10"
											styleClass="cuadroTexto" />
											<span style="padding:2px">
												<img 
													style="cursor: pointer" 
													onclick="seleccionarFechaComienzo();" 
													src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
													border="0" 
													width="10" 
													height="10" 
													title="<vgcutil:message 
													key="boton.calendario.alt" />">
											</span>
									</td>
									<td align="right"><vgcutil:message key="jsp.reporte.transaccion.ficha.fechafin" /></td>
									<td>
										<html:text 
											property="fechaFinal" 
											size="12" 
											maxlength="10"
											styleClass="cuadroTexto" />
											<span style="padding:2px">
												<img 
													style="cursor: pointer" 
													onclick="seleccionarFechaFin();" 
													src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" 
													border="0" 
													width="10" 
													height="10" 
													title="<vgcutil:message 
													key="boton.calendario.alt" />">
											</span>
									</td>
								</tr>
								
								<%-- Campo numero de registros --%>
								<tr>
									<td align="right" colspan="2" width="162px">
										<vgcutil:message key="jsp.reporte.transaccion.ficha.numeroregistro" />:&nbsp;
										<html:text property="numeroRegistros" size="2" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
									</td>
									<td align="left" colspan="2" style="width:5px">
										<img id="numeroRegistros" name="numeroRegistros" src="<html:rewrite page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
									</td>
								</tr>
							</table>
							<map name="MapControlUpDown1" id="MapControlUpDown">
								<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('numeroRegistros')" onmouseout="normalAction('numeroRegistros')" onmousedown="iniciarConteoContinuo('numeroRegistros', 999, 0);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('numeroRegistros')" onmouseout="normalAction('numeroRegistros')" onmousedown="iniciarConteoContinuo('numeroRegistros', 999, 0);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
							</map>
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
		<script>
			// Hay Datos
			<logic:equal name="transaccionForm" property="status" value="4">
				onAceptar();
			</logic:equal>

			// Servicio no configurado
			<logic:equal name="transaccionForm" property="status" value="5">
				_setCloseParent = true;
			</logic:equal>
		</script>
		
	</tiles:put>
</tiles:insert>
