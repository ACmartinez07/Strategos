<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (05/03/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.framework.administracion.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar"
		value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<%-- Cuerpo vacio --%>
	<tiles:put name="body" type="String">

ADMINISTRACION DE FRAMEWORK

	</tiles:put>

</tiles:insert>
