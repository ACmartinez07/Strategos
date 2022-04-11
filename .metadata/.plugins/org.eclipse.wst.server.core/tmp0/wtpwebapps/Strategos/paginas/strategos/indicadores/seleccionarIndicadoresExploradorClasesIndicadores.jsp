<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>
<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarClase" value="true">
	<vgcinterfaz:splitterHorizontal anchoPorDefecto="200" splitterId="splitClasesIndicadores" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
		<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitClasesIndicadores">
			<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresClasesIndicadores.jsp"></jsp:include>
		</vgcinterfaz:splitterHorizontalPanelIzquierdo>
		<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitClasesIndicadores">
			<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresIndicadores.jsp"></jsp:include>
		</vgcinterfaz:splitterHorizontalPanelDerecho>
	</vgcinterfaz:splitterHorizontal>
</logic:equal>
<logic:equal name="seleccionarIndicadoresForm" property="permitirCambiarClase" value="false">
	<jsp:include flush="true" page="/paginas/strategos/indicadores/seleccionarIndicadoresIndicadores.jsp"></jsp:include>
</logic:equal>
