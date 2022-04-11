<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/12/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <bean:write scope="session" name="activarIniciativa" property="nombrePlural" />  [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<vgcinterfaz:splitterHorizontal anchoPorDefecto="500px" splitterId="splitIniciativas" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitIniciativas">
				<table class="fichaDatostablaGeneral" style="border-collapse: collapse; padding: 0px; width: 100%;">
					<tr style="width: 100%; height: 100%; vertical-align: text-top;">
						<td style="width: 100%; height: 100%; vertical-align: text-top;">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIniciativas.jsp"></jsp:include>
						</td>
					</tr>
				</table>						
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>

			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitIniciativas">
				<table class="fichaDatostablaGeneral" style="border-collapse: collapse; padding: 0px; width: 100%;">
					<tr style="width: 100%; height: 400px; vertical-align: text-top;">
						<td style="width: 100%; height: 400px; vertical-align: text-top;" id="splitPlanVerticalPanelSuperior" class="panelSplit">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIndicadoresIniciativa.jsp"></jsp:include>
						</td>
					</tr>
					<tr style="width: 100%; height: 2px;">
						<td onmousedown="splitPlanVerticalSetPosicion(event)" id="splitPlanVerticalVSplit"></td>
					</tr>
					<tr style="width: 100%; height: 100%; vertical-align: text-top;">
						<td style="width: 100%; height: 100%; vertical-align: text-top;" class="panelSplit">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/mostrarPlanesAsociadosIniciativa.jsp"></jsp:include>
						</td>
					</tr>
					<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="1">
						<tr>
							<td style="vertical-align: text-top;">
								<%-- Iniciativa por Actividad --%>
								<%--  
								<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="0">
									<jsp:include flush="true" page="/paginas/strategos/iniciativas/mostrarGestionIniciativa.jsp"></jsp:include>
								</logic:equal> 
								 --%>
								<%-- Iniciativa por Producto --%>
								<logic:equal name="gestionarIniciativasForm" property="tipoAlerta" value="1">
									<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/productos/gestionarProductos.jsp"></jsp:include>
								</logic:equal>
							</td>
						</tr>
					</logic:equal>
				</table>
			</vgcinterfaz:splitterHorizontalPanelDerecho>

		</vgcinterfaz:splitterHorizontal>
		
	</tiles:put>

</tiles:insert>
