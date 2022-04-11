<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/10/2012) --%>

<vgcinterfaz:splitterHorizontal anchoPorDefecto="300" splitterId="splitPlanes" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
	<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitPlanes">
		<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresPlanes.jsp"></jsp:include>
	</vgcinterfaz:splitterHorizontalPanelIzquierdo>
	<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitPlanes">
		<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresIndicadores.jsp"></jsp:include>
	</vgcinterfaz:splitterHorizontalPanelDerecho>
</vgcinterfaz:splitterHorizontal>
