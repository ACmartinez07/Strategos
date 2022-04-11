<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (17/09/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript">
	function seleccionarNodo(nodoId, marcadorAncla) {
		window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectiva"/>?perspectivaId=' + nodoId + '&mostrarObjetosAsociados=true';
		//window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectivas"/>?selectedPerspectivaId=' + nodoId + '&perspectivaId=' + nodoId + '&mostrarObjetosAsociados=true' + marcadorAncla;
	}

	function openNodo(nodoId, marcadorAncla) {
		window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectivas"/>?openPerspectivaId=' + nodoId + marcadorAncla;
	}

	function closeNodo(nodoId, marcadorAncla) {
		window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectivas"/>?closePerspectivaId=' + nodoId + marcadorAncla;
	}

	function reporte() {
		var planId = '<bean:write name="visualizarPlanForm" property="planId" />';
		abrirReporte('<html:rewrite action="/planes/perspectivas/generarReportePerspectivas.action"/>?planId=' + planId, 'reporte');
	}

	function propiedadesPerspectiva() {
		abrirVentanaModal('<html:rewrite action="/planes/perspectivas/propiedadesPerspectiva" />?perspectivaId=<bean:write name="visualizarPerspectivas.perspectiva" property="perspectivaId" scope="session" />', "Perspectiva", 450, 455);
	}
</script>

<%-- Este es el "Visor Tipo Arbol" --%>
<div style="border-width:0px">
	<div style="border-width:0px;">
		<treeview:treeview 
			useFrame="false" 
			name="visualizarPerspectivas.arbolPerspectivas" 
			scope="session" 
			baseObject="visualizarPerspectivas.perspectivaRoot" 
			isRoot="true" 
			fieldName="nombre" 
			fieldId="perspectivaId" 
			fieldChildren="hijos" 
			lblUrlObjectId="perspectivaId" 
			styleClass="treeview" 
			checkbox="false" 
			pathImages="/paginas/strategos/planes/imagenes/gestionarPerspectivas/" 
			urlJs="/componentes/visorArbol/visorArbol.js" 
			nameSelectedId="perspectivaId" 
			urlName="javascript:seleccionarNodo(perspectivaId, marcadorAncla);" 
			urlConnectorOpen="javascript:openNodo(perspectivaId, marcadorAncla);" 
			urlConnectorClose="javascript:closeNodo(perspectivaId, marcadorAncla);" 
			lblUrlAnchor="marcadorAncla" 
			useNodeImage="true" />
	</div>
</div>
