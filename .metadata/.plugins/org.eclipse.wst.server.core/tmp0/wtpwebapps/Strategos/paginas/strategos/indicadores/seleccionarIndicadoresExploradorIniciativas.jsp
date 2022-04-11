<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>

<vgcinterfaz:splitterHorizontal anchoPorDefecto="300" splitterId="splitIniciativas" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
	<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitIniciativas">
		<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresIniciativas.jsp"></jsp:include>
	</vgcinterfaz:splitterHorizontalPanelIzquierdo>
	<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitIniciativas">
		<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresIndicadores.jsp"></jsp:include>
	</vgcinterfaz:splitterHorizontalPanelDerecho>
</vgcinterfaz:splitterHorizontal>
