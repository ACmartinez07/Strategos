<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarorganizaciones.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

	<%-- Funciones JavaScript locales de la página Jsp --%>
	<script language="JavaScript" type="text/javascript">
		
		var funcionCierre = '';
		
		function seleccionarNodo(nodoId, marcadorAncla) {
			window.location.href='<html:rewrite action="/organizaciones/seleccionarOrganizaciones"/>?funcionCierre=' + funcionCierre + '&seleccionarOrganizacionId=' + nodoId + marcadorAncla;
		}
		
		function abrirNodo(nodoId, marcadorAncla) {
			window.location.href='<html:rewrite action="/organizaciones/seleccionarOrganizaciones"/>?funcionCierre=' + funcionCierre + '&abrirOrganizacionId=' + nodoId + marcadorAncla;
		}
		
		function cerrarNodo(nodoId, marcadorAncla) {					
			window.location.href='<html:rewrite action="/organizaciones/seleccionarOrganizaciones"/>?funcionCierre=' + funcionCierre + '&cerrarOrganizacionId=' + nodoId + marcadorAncla;
		}
		
		function seleccionar() 
		{
			this.opener.document.<bean:write name="seleccionarOrganizacionesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarOrganizacionesForm" property="nombreCampoValor" scope="session" />.value='<bean:write name="seleccionarOrganizacionesArbolBean" scope="session" property="nodoSeleccionado.nombre"/>';
			this.opener.document.<bean:write name="seleccionarOrganizacionesForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarOrganizacionesForm" property="nombreCampoOculto" scope="session" />.value='<bean:write name="seleccionarOrganizacionesArbolBean" scope="session" property="nodoSeleccionado.organizacionId"/>';						
			<logic:notEmpty name="seleccionarOrganizacionesForm" property="funcionCierre" scope="session">				
				this.opener.<bean:write name="seleccionarOrganizacionesForm" property="funcionCierre" scope="session" />				
			</logic:notEmpty>			
			this.close();
		}
	
	</script>

	<%-- Representación de la Forma --%>
	<html:form action="/organizaciones/seleccionarOrganizaciones" styleClass="formaHtmlCompleta">
	
		<%-- Atributos de la Forma --%>
		<html:hidden property="funcionCierre" />		

		<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.seleccionarorganizaciones.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>

			<%-- Botón Actualizar --%>
			<vgcinterfaz:contenedorFormaBotonActualizar>
				<html:rewrite action='/organizaciones/seleccionarOrganizaciones' />
			</vgcinterfaz:contenedorFormaBotonActualizar>

			<%-- Arbol de Organizaciones --%>
			<treeview:treeview useFrame="false" arbolBean="seleccionarOrganizacionesArbolBean" scope="session" isRoot="true" fieldName="nombre" fieldId="organizacionId" fieldChildren="hijos"
				lblUrlObjectId="organizacionId" styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="organizacionId"
				urlName="javascript:seleccionarNodo(organizacionId, marcadorAncla);" urlConnectorOpen="javascript:abrirNodo(organizacionId, marcadorAncla);"
				urlConnectorClose="javascript:cerrarNodo(organizacionId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

		</vgcinterfaz:contenedorForma>

	</html:form>
	
	<%-- Funciones JavaScript locales de la página Jsp --%>
	<script language="JavaScript" type="text/javascript">		
		
		if ((window.document.seleccionarOrganizacionesForm.funcionCierre != null) && (window.document.seleccionarOrganizacionesForm.funcionCierre.value != '')) {
			funcionCierre = window.document.seleccionarOrganizacionesForm.funcionCierre.value;
		}
		
	</script>

	</tiles:put>

</tiles:insert>
