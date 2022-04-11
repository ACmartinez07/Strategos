<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarclasesproblemas.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="350" splitterId="splitProblemas" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitProblemas">
				<jsp:include flush="true" page="/paginas/strategos/problemas/clasesproblemas/gestionarClasesProblemas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitProblemas">
				<jsp:include flush="true" page="/paginas/strategos/problemas/gestionarProblemas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>

</tiles:insert>