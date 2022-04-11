<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%-- Modificado por: Kerwin Arias (03/07/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarindicadores.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<jsp:include flush="true" page="/paginas/strategos/organizaciones/gestionarIndicadores.jsp"></jsp:include>
	</tiles:put>
	

</tiles:insert>
