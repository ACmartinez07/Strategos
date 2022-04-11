<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">
	var visible = null;
	<logic:equal scope="session" name="claseVisible" value="true">
		visible = true;
	</logic:equal>
	<logic:equal scope="session" name="claseVisible" value="false">
		visible = false;
	</logic:equal>

	function seleccionarClase(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?visible=' + visible + '&seleccionarClaseId=' + nodoId + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}

	function abrirClase(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?visible=' + visible + '&abrirClaseId=' + nodoId + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}
	
	function cerrarClase(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?visible=' + visible + '&cerrarClaseId=' + nodoId + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}
	
	function mostrarClases()
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresClasesIndicadores"/>?cambiarEstadoVisible=' + !visible;
	}

</script>

<vgcinterfaz:contenedorForma idContenedor="body-claseSeleccionIndicadores" mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarindicadores.panel.clases.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Este es el "Visor Tipo Arbol" --%>
	<treeview:treeview useFrame="false" arbolBean="seleccionarIndicadoresArbolClasesBean" scope="session" isRoot="true" fieldName="nombre" fieldId="claseId" fieldChildren="hijos" lblUrlObjectId="claseId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" urlName="javascript:seleccionarClase(claseId, marcadorAncla);" urlConnectorOpen="javascript:abrirClase(claseId, marcadorAncla);" urlConnectorClose="javascript:cerrarClase(claseId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-claseSeleccionIndicadores'), 206);
</script>
