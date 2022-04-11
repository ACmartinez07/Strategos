<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (02/12/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.exploradorplan.titulo" /> [<bean:write name="gestionarPlanForm" scope="session" property="nombrePlan" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<script type="text/javascript">
			var splitPlanVerticalAltoActual=0;
			var splitPlanVerticalPosicionActual=0;
			var splitPlanVerticalPosicionNueva=0;
			var splitPlanVerticalAltoPanelSuperior='400';
			var splitPlanVerticalMouseStatus='up';
			var anchoPagina = screen.width;
			var altoPagina = screen.height;
			var startVertical = false;
			
			function splitPlanVerticalSetPosicion(e) 
			{
				eventoActual = (typeof event == 'undefined'? e: event);
				splitPlanVerticalMouseStatus = 'down';
				splitPlanVerticalPosicionActual = eventoActual.clientY;
				altoTemp = document.getElementById('splitPlanVerticalPanelSuperior').style.height;
				arregloAlto = altoTemp.split('p');
				splitPlanVerticalAltoActual = parseInt(arregloAlto[0]);
				startVertical = true;
			}
			
			function splitPlanVerticalGetPosicion(e) 
			{
				if (splitPlanVerticalMouseStatus == 'down') 
				{
					eventoActual = (typeof event == 'undefined'? e: event);
					splitPlanVerticalPosicionNueva = eventoActual.clientY;
					var movimientoPx = parseInt(splitPlanVerticalPosicionNueva - splitPlanVerticalPosicionActual);
					var altoNuevo = parseInt(splitPlanVerticalAltoActual + movimientoPx);
					altoNuevo = (altoNuevo < 70 ? 70 : altoNuevo > (altoPagina - 250) ? (altoPagina - 250) : altoNuevo);
					document.getElementById('splitPlanVerticalPanelSuperior').style.height = altoNuevo + 'px';
					splitPlanVerticalAltoPanelSuperior = altoNuevo + 'px';
					
					if (typeof (resizePanelIniciativa) == "function")
						resizePanelIniciativa();
				}
			}
			
		</script>
	
		<style type=text/css>
			#splitPlanVerticalVSplit 
			{
				cursor: s-resize; 
				width: 1px; 
				padding-bottom: 0px; 
				padding-top: 0px; 
				padding-left: 0px; 
				padding-right: 0px; 
				background-color: #c0c0c0; 
				witdth: 100%;
			}
		</style>

		<%-- Split --%>
		<bean:define id="anchoPanel" toScope="page">
			<logic:notEmpty name="gestionarPlanForm" property="anchoPorDefecto">
				<bean:write name="gestionarPlanForm" property="anchoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarPlanForm" property="anchoPorDefecto">
				400px
			</logic:empty>
		</bean:define>
		<bean:define id="altoPanel" toScope="page">
			<logic:notEmpty name="gestionarPlanForm" property="altoPorDefecto">
				<bean:write name="gestionarPlanForm" property="altoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarPlanForm" property="altoPorDefecto">
				400px
			</logic:empty>
		</bean:define>
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoPanel %>" splitterId="splitPlan" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

			<%-- Panel Izquierdo: Gestionar Perspectivas --%>
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitPlan">
				<jsp:include flush="true" page="/paginas/strategos/planes/perspectivas/gestionarPerspectivas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>

			<%-- Panel Derecho:  --%>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitPlan">
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" onmouseup="splitPlanVerticalMouseStatus='up'; setAltoPanel();" onmousemove="splitPlanVerticalGetPosicion(event)">
					<tr height="50%">
						<td valign="top" id="splitPlanVerticalPanelSuperior" class="panelSplit" style="height: <%= altoPanel %>">
							<jsp:include flush="true" page="/paginas/strategos/planes/indicadores/gestionarIndicadoresPlan.jsp"></jsp:include>
						</td>
					</tr>
					<tr height="2px">
						<td onmousedown="splitPlanVerticalSetPosicion(event)" id="splitPlanVerticalVSplit"></td>
					</tr>
					<tr>
						<td valign="top" class="panelSplit" style="height: 100%">
							<jsp:include flush="true" page="/paginas/strategos/planes/iniciativas/gestionarIniciativasPlan.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</vgcinterfaz:splitterHorizontalPanelDerecho>

		</vgcinterfaz:splitterHorizontal>
	</tiles:put>

</tiles:insert>