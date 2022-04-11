<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarclasesproblemas.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" />]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="350" splitterId="splitSeguimientos" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitSeguimientos">
				<jsp:include flush="true" page="/paginas/strategos/problemas/acciones/gestionarAcciones.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitSeguimientos">
				<table id="tbSeguimiento" height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr id="trSeguimiento" valign="top" style="height: 100px">
						<td><jsp:include flush="true" page="/paginas/strategos/problemas/seguimientos/gestionarSeguimientos.jsp"></jsp:include></td>
					</tr>
					<tr valign="top" id="trDetalleSeguimiento" style="height: 250px">
						<td>			
							<table class="bordeFichaDatos" id="tblDetalleSeguimiento" style="width: 100%;" cellpadding="3" cellspacing="0" >								
								<tr>
									<td colspan="4">&nbsp;</td>									
								</tr>
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.reponsable" /></td>
									<td colspan="3"><input name="a" type="text" style="width:99%" readonly="true" value="<bean:write name="gestionarAccionesForm" property="nombreResponsable"/>" class="cuadroTexto" ></td>
								</tr>
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.auxiliar" /></td>
									<td colspan="3"><input type="text" style="width:99%" readonly="true" value="<bean:write name="gestionarAccionesForm" property="nombreAuxiliar"/>" class="cuadroTexto" ></td>
								</tr>
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.estado" /></td>
									<td colspan="3"><input type="text" style="width:99%" readonly="true" value="<bean:write name="gestionarAccionesForm" property="nombreEstado"/>" class="cuadroTexto" ></td>
								</tr>
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.descripcion" /></td>
									<td colspan="3"><textarea class="cuadroTexto" style="width:99%" readonly="true" rows="4" cols="40"><bean:write name="gestionarAccionesForm" property="descripcion"/></textarea></td>
								</tr>
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.inicioestimado" /></td>
									<td><input type="text" size="10" readonly="true" value="<bean:write name="gestionarAccionesForm" property="fechaEstimadaInicio"/>" class="cuadroTexto" ></td>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.finestimado" /></td>
									<td width="70%"><input type="text" size="10" readonly="true" value="<bean:write name="gestionarAccionesForm" property="fechaEstimadaFin"/>" class="cuadroTexto" ></td>
								</tr>								
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.inicioreal" /></td>
									<td><input type="text" size="10" readonly="true" value="<bean:write name="gestionarAccionesForm" property="fechaRealInicio"/>" class="cuadroTexto" ></td>
									<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.finreal" /></td>
									<td><input type="text" size="10" readonly="true" value="<bean:write name="gestionarAccionesForm" property="fechaRealFin"/>" class="cuadroTexto" ></td>
								</tr>								
								<logic:notEmpty name="accionCorrectiva" property="padreId">
									<tr>
										<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.frecuencia" /></td>
										<td><input type="text" size="2" readonly="true" value="<bean:write name="gestionarAccionesForm" property="frecuencia"/>" class="cuadroTexto" ></td>
										<td align="right"><vgcutil:message key="jsp.gestionarseguimientos.orden" /></td>
										<td><input type="text" size="2" readonly="true" value="<bean:write name="gestionarAccionesForm" property="orden"/>" class="cuadroTexto" ></td>
									</tr>									
								</logic:notEmpty>
							</table>
						</td>
					</tr>
				</table>				
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>
</tiles:insert>
<script type="text/javascript">
	var objeto = document.getElementById('tbSeguimiento');
	if (objeto != null)
	{
		var height = (parseInt(_myHeight) - 230); 
		objeto.style.height = height + "px";
		var seguimiento = document.getElementById('trSeguimiento');
		var seguimientoDetalle = document.getElementById('trDetalleSeguimiento');
		if (seguimientoDetalle != null && seguimiento != null)
			seguimiento.style.height = (height - parseInt(seguimientoDetalle.style.height.replace("px", ""))) + "px";
	}
</script>
