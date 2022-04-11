<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">

	function seleccionarNodo(tipoNodo, nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresIniciativas"/>?seleccionarId=' + nodoId + '&tipoNodo=' + tipoNodo + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}

	function abrirNodo(tipoNodo, nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresIniciativas"/>?abrirId=' + nodoId + '&tipoNodo=' + tipoNodo + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}

	function cerrarNodo(tipoNodo, nodoId, marcadorAncla) 
	{
		window.location.href='<html:rewrite action="/indicadores/seleccionarIndicadoresIniciativas"/>?cerrarId=' + nodoId + '&tipoNodo=' + tipoNodo + '&panelSeleccionado=panelIndicadores' + marcadorAncla;
	}

</script>

<vgcinterfaz:contenedorForma idContenedor="body-iniciativaSeleccion" mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		<logic:empty scope="session" name="activarIniciativa">
			..:: <vgcutil:message key="barraareas.strategos.iniciativas" />
		</logic:empty>
		<logic:notEmpty scope="session" name="activarIniciativa">
			<logic:notEmpty scope="session" name="activarIniciativa" property="objeto">
				<logic:equal scope="session" name="activarIniciativa" property="objeto.activo" value="true">
					..:: <bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
				</logic:equal>
			</logic:notEmpty>
		</logic:notEmpty>
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Este es el "Visor Tipo Arbol" --%>
	<treeview:treeview useFrame="false" arbolBean="seleccionarIndicadoresArbolIniciativasBean" scope="session" isRoot="true" fieldName="nodoArbolNombre" fieldId="nodoArbolId" fieldChildren="nodoArbolHijos" lblUrlObjectId="nodoId" styleClass="treeview" checkbox="false" pathImages="/paginas/strategos/indicadores/imagenes/selector/" urlJs="/componentes/visorArbol/visorArbol.js" urlName="javascript:seleccionarNodo(tipoNodo, nodoId, marcadorAncla);" urlConnectorOpen="javascript:abrirNodo(tipoNodo, nodoId, marcadorAncla);" urlConnectorClose="javascript:cerrarNodo(tipoNodo, nodoId, marcadorAncla);" lblUrlAnchor="marcadorAncla" labelNodeType="tipoNodo" useNodeImage="true" />

</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-iniciativaSeleccion'), 206);
</script>
