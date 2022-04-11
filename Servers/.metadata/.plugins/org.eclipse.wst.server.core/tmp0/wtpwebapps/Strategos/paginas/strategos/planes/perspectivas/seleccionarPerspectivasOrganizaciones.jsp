<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript">
	
	function seleccionarOrganizacion(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasOrganizaciones"/>?seleccionarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
	}
	
	function abrirOrganizacion(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasOrganizaciones"/>?abrirOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
	}
	
	function cerrarOrganizacion(nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/planes/perspectivas/seleccionarPerspectivasOrganizaciones"/>?cerrarOrganizacionId=' + nodoId + '&llamadaDesde=Organizaciones' + marcadorAncla;
	}

</script>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarperspectivas.panel.organizaciones.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Este es el "Visor Tipo Arbol" --%>
	<treeview:treeview useFrame="false" arbolBean="seleccionarPerspectivasArbolOrganizacionesBean" scope="session" isRoot="true" fieldName="nombre" fieldId="organizacionId" fieldChildren="hijos"
		lblUrlObjectId="organizacionId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js"
		urlName="javascript:seleccionarOrganizacion(organizacionId, marcadorAncla);" urlConnectorOpen="javascript:abrirOrganizacion(organizacionId, marcadorAncla);"
		urlConnectorClose="javascript:cerrarOrganizacion(organizacionId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

</vgcinterfaz:contenedorForma>
