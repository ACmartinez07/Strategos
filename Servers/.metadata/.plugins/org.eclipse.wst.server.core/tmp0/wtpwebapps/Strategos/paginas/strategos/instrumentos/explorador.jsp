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
		..:: <vgcutil:message key="jsp.gestionarportafolio.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		
		
		<%-- Split --%>
		<bean:define id="anchoPanel" toScope="page">
				600px
		</bean:define>
		<bean:define id="altoPanel" toScope="page">
				350px
		</bean:define>
	
	
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoPanel %>" splitterId="splitInstrumentos" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

			<%-- Panel Izquierdo: Gestionar Portafolio --%>
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitInstrumentos">
				<jsp:include flush="true" page="/paginas/strategos/instrumentos/gestionar.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>

			<%-- Panel Derecho:  --%>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitInstrumentos">
				<table class="fichaDatostablaGeneral" style="border-collapse: collapse; padding: 0px; width: 100%; height: 100%;" >
					<tr style="width: 100%;  vertical-align: text-top;">
						<td class="panelSplit" style="width: 100%; height: <%= altoPanel %>; vertical-align: text-top;" >
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIniciativas.jsp"></jsp:include>
						</td>
					</tr>
															
				</table>
				
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	
	
	</tiles:put>
</tiles:insert>
