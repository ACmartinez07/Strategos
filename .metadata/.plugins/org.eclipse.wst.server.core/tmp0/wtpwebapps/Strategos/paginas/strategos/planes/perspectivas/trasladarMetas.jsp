<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.trasladar.metas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		

		<%-- Funciones JavaScript para el wizzard --%>
		<script>

			var _setCloseParent = false;
			var errmsgrango = '<vgcutil:message key="jsp.protegerliberar.errorrango"/>';
			
			function validarRango(desdeobj, hastaobj, desdeAntobj, hastaAntobj, errmsg)
		  	{
		  		desde = parseInt(desdeobj.value);
				hasta = parseInt(hastaobj.value);
				if (hasta<desde) 
				{
					alert(errmsg);
					desdeobj.value = desdeAntobj.value;
					hastaobj.value = hastaAntobj.value;				
				} 
				else 
				{
					desdeAntobj.value = desde;
					hastaAntobj.value = hasta;				
				}
			}

			function aplicar()
			{
				var xml = '?funcion=trasladar';
				
				if(validar()){
					activarBloqueoEspera();
					document.trasladarMetasForm.action = '<html:rewrite action="/planes/perspectivas/trasladarMetasSalvar"/>'+xml;	
					document.trasladarMetasForm.submit();	
				}
									
			
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
				var anoIni = document.trasladarMetasForm.ano.value;
				var anoFin = document.trasladarMetasForm.anoFinal.value;
				
				if(anoIni == anoFin){
					alert('El año origen y el año destino son iguales');	
					return false;
				}
				if(anoIni > anoFin){
					alert('El año origen es mayor al año destino');
					return false;
				}
				
				return true;
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/perspectivas/trasladarMetas" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
		
			<html:hidden property="planId"/>
			
			<input type="hidden" name="mesInicialAnt" value="1" />
			<input type="hidden" name="mesFinalAnt" value="12" />
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="460px" height="200px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::
					<vgcutil:message key="jsp.trasladar.metas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatostabla bordeFichaDatos">
					<tr>
						<td>
							<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
								<tr>
									<td colspan="2">
										<b><vgcutil:message key="jsp.trasladar.metas.rango.anos" /></b>
									</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td align="right">
										<vgcutil:message key="jsp.trasladar.metas.ano.origen" />
									</td>
									<td>
										<bean:define id="anoCalculo" toScope="page">
											<bean:write name="trasladarMetasForm" property="ano" />
										</bean:define>
										<html:select property="ano" value="<%= anoCalculo %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select>
									</td>
									&nbsp;
									<td align="right">
										<vgcutil:message key="jsp.trasladar.metas.ano.destino" />
									</td>
									<td>
										<bean:define id="anoCalculo" toScope="page">
											<bean:write name="trasladarMetasForm" property="anoFinal" />
										</bean:define>
										<html:select property="anoFinal" value="<%= anoCalculo %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select>
									</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>							
							</table>
						</td>
					</tr>
				</table>
							
				
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:aplicar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
		
	</tiles:put>
</tiles:insert>
