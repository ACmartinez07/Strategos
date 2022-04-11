<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarcausas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			var funcionCierre = '';
	
			function seleccionarNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/causas/seleccionarCausas"/>?funcionCierre=' + funcionCierre + '&seleccionarCausaId=' + nodoId + marcadorAncla;
			}
			
			function abrirNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/causas/seleccionarCausas"/>?funcionCierre=' + funcionCierre + '&abrirCausaId=' + nodoId + marcadorAncla;
			}
			
			function cerrarNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/causas/seleccionarCausas"/>?funcionCierre=' + funcionCierre + '&cerrarCausaId=' + nodoId + marcadorAncla;
			}
			
			function seleccionar() {
				this.opener.document.<bean:write name="seleccionarCausasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarCausasForm" property="nombreCampoValor" scope="session" />.value='<bean:write name="seleccionarCausasArbolBean" scope="session" property="nodoSeleccionado.nombre"/>';
				this.opener.document.<bean:write name="seleccionarCausasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarCausasForm" property="nombreCampoOculto" scope="session" />.value='<bean:write name="seleccionarCausasArbolBean" scope="session" property="nodoSeleccionado.causaId"/>';
				<logic:notEmpty name="seleccionarCausasForm" property="funcionCierre" scope="session">
					this.opener.<bean:write name="seleccionarCausasForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}

		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/causas/seleccionarCausas" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="funcionCierre" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarcausas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/causas/seleccionarCausas' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Arbol de Causas --%>
				<treeview:treeview useFrame="false" arbolBean="seleccionarCausasArbolBean" scope="session" isRoot="true" fieldName="nombre" fieldId="causaId" fieldChildren="hijos" lblUrlObjectId="causaId"
					styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="causaId"
					urlName="javascript:seleccionarNodo(causaId, marcadorAncla);" urlConnectorOpen="javascript:abrirNodo(causaId, marcadorAncla);" urlConnectorClose="javascript:cerrarNodo(causaId, marcadorAncla);"
					lblUrlAnchor="marcadorAncla" />

			</vgcinterfaz:contenedorForma>

		</html:form>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">		
			
			if ((window.document.seleccionarCausasForm.funcionCierre != null) && (window.document.seleccionarCausasForm.funcionCierre.value != '')) {
				funcionCierre = window.document.seleccionarCausasForm.funcionCierre.value;
			}
			
		</script>

	</tiles:put>

</tiles:insert>
