<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <bean:message key="jsp.framework.gestionarbloqueos.titulo" />
	</tiles:put>

	<tiles:put name="areaBar"
		value="/paginas/framework/barraAreasAdministracion.jsp" />

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="500"
			splitterId="splitBloqueos" overflowPanelDerecho="hidden"
			overflowPanelIzquierdo="hidden">
			<vgcinterfaz:splitterHorizontalPanelIzquierdo
				splitterId="splitBloqueos">
				<jsp:include flush="true"
					page="/paginas/framework/bloqueos/gestionarBloqueos.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>
			<vgcinterfaz:splitterHorizontalPanelDerecho
				splitterId="splitBloqueos">
				<jsp:include flush="true"
					page="/paginas/framework/bloqueos/gestionarBloqueosLectura.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>

</tiles:insert>
