<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/06/2012) --%>
<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarclasesindicadores.titulo" /> [<bean:write
			name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<vgcinterfaz:splitterHorizontal anchoPorDefecto="500px"
			splitterId="splitPresentaciones" overflowPanelDerecho="hidden"
			overflowPanelIzquierdo="hidden">
			<vgcinterfaz:splitterHorizontalPanelIzquierdo
				splitterId="splitPresentaciones">
				<jsp:include flush="true"
					page="/paginas/strategos/presentaciones/vistas/gestionarVistas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>
			<vgcinterfaz:splitterHorizontalPanelDerecho
				splitterId="splitPresentaciones">
				<jsp:include flush="true"
					page="/paginas/strategos/presentaciones/paginas/gestionarPaginas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>

</tiles:insert>
