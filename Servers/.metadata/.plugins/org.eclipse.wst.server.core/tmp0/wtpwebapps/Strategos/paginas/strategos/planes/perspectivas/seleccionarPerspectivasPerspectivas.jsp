<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	
	function seleccionarPerspectiva(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasPerspectivas"/>?seleccionarPerspectivaId=' + nodoId + '&llamadaDesde=Perspectivas' + marcadorAncla;
	}
	
	function abrirPerspectiva(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasPerspectivas"/>?abrirPerspectivaId=' + nodoId + '&llamadaDesde=Perspectivas' + marcadorAncla;
	}
	
	function cerrarPerspectivas(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasPerspectivas"/>?cerrarPerspectivaId=' + nodoId + '&llamadaDesde=Perspectivas' + marcadorAncla;
	}

</script>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarperspectivas.panel.perspectivas.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>
				
	<%-- CASO 1: Hay un plan seleccionado puesto que existe --%>
	<logic:notEmpty name="seleccionarPerspectivasForm" property="planSeleccionadoId">	
		
		<%-- Este es el "Visor Tipo Arbol" --%>
		<treeview:treeview 
			useFrame="false" 
			arbolBean="seleccionarPerspectivasArbolPerspectivasBean" 
			scope="session" 
			isRoot="true" 
			fieldName="nombre" 
			fieldId="perspectivaId" 
			fieldChildren="hijos"
			lblUrlObjectId="perspectivaId" 
			styleClass="treeview" 
			checkbox="false" 
			pathImages="/componentes/visorArbol/" 
			urlJs="/componentes/visorArbol/visorArbol.js"
			urlName="javascript:seleccionarPerspectiva(perspectivaId, marcadorAncla);" 
			urlConnectorOpen="javascript:abrirPerspectiva(perspectivaId, marcadorAncla);"
			urlConnectorClose="javascript:cerrarPerspectiva(perspectivaId, marcadorAncla);" 
			lblUrlAnchor="marcadorAncla" />
				
	</logic:notEmpty>
	
	<%-- CASO 2: No hay un plan seleccionado puesto que no existe --%>
	<logic:empty name="seleccionarPerspectivasForm" property="planSeleccionadoId">
		
		<%-- Visor Tipo Lista --%>
		<table class="listView" cellpadding="5" cellspacing="1">			
			<%-- Validación cuando no hay registros --%>
			<tr class="mouseFueraCuerpoListView" height="20px">
				<td valign="middle" align="center"><vgcutil:message key="jsp.seleccionarperspectivas.noregistrosperspectivas" /></td>
			</tr>
		</table>
				
	</logic:empty>

</vgcinterfaz:contenedorForma>