<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/09/2012) --%>
<div id="modal2" class="modalmask">
	<div id="tblDatos" class="modalbox movedown" style="width:800px; height:600px; padding: 0px; overflow: auto">
		<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px; border-collapse: collapse;">
			<tr style="height:4px;">
				<td colspan="2">
					<a href="javascript:closeModal(false);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a>
				</td>
			</tr>
			<tr style="vertical-align: text-top; text-align:center; width:100%;">
				<td>
					<table id="background-imagekerwin" style="background-color: #ffffff;">
						<colgroup>
							<col class="oce-firstKerwin" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col" style="color: #666666;"><vgcutil:message key="jsp.asistente.grafico.tabla.periodo" /></th>
								<logic:iterate name="graficoForm" property="series" id="serie">
									<logic:equal name="serie" property="visualizar" value="true">
										<th scope="col" style="color: #666666;">
											<bean:write name='serie' property='pathClase' />
										</th>
									</logic:equal>
								</logic:iterate>
								<logic:equal name="graficoForm" property="condicion" value="true">
									<th scope="col" style="color: #666666;"><vgcutil:message key="jsp.grafico.editor.alerta" /></th>
								</logic:equal>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="100" class="rounded-foot-right">&nbsp;</td>
							</tr>
						</tfoot>
						<tbody class="tbody">
							<logic:iterate name="graficoForm" property="anosPeriodos"
								id="anoPeriodo">
								<tr>
									<td class="columna">
										<bean:write name='anoPeriodo' property='nombre' />
									</td>
									<logic:iterate name="anoPeriodo" property="series" id="serie">
										<td class="body"><logic:empty property="valor"
												name="serie">
												&nbsp;
											</logic:empty><logic:notEmpty property="valor" name="serie">
												<bean:write name="serie" property="valor" format="#,##0.00" />
											</logic:notEmpty></td>
									</logic:iterate>
									<logic:equal name="graficoForm" property="condicion"
										value="true">
										<td class="bodyAlerta"><logic:notEmpty
												property="alerta" name="anoPeriodo">
												<bean:define id="imagenAlerta" name="anoPeriodo"
													property="alerta.nombreArchivo" toScope="page"></bean:define>
												<img
													src="<html:rewrite page='/paginas/strategos/indicadores/imagenes/'/><%=imagenAlerta%>" />
											</logic:notEmpty> <logic:empty property="alerta" name="anoPeriodo">
												&nbsp;
											</logic:empty></td>
									</logic:equal>
								</tr>
							</logic:iterate>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
