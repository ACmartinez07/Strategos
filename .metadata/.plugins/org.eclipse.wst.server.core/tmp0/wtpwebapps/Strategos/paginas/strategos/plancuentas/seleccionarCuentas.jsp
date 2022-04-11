<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/10/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionarcuentas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">

			var funcionCierre = '';
			
			function seleccionarNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/seleccionarCuentas"/>?funcionCierre=' + funcionCierre + '&seleccionarCuentaId=' + nodoId + marcadorAncla;
			}
			
			function abrirNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/seleccionarCuentas"/>?funcionCierre=' + funcionCierre + '&abrirCuentaId=' + nodoId + marcadorAncla;
			}
			
			function cerrarNodo(nodoId, marcadorAncla) {
				window.location.href='<html:rewrite action="/plancuentas/seleccionarCuentas"/>?funcionCierre=' + funcionCierre + '&cerrarCuentaId=' + nodoId + marcadorAncla;
			}
			
			function seleccionar() {
				this.opener.document.<bean:write name="seleccionarCuentasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarCuentasForm" property="nombreCampoValor" scope="session" />.value='<bean:write name="seleccionarCuentasArbolBean" property="nodoSeleccionado.codigo"/>: <bean:write name="seleccionarCuentasArbolBean" property="nodoSeleccionado.descripcion"/>';
				this.opener.document.<bean:write name="seleccionarCuentasForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarCuentasForm" property="nombreCampoOculto" scope="session" />.value='<bean:write name="seleccionarCuentasArbolBean" property="nodoSeleccionado.cuentaId"/>';
				<logic:notEmpty name="seleccionarCuentasForm" property="funcionCierre" scope="session">
				this.opener.<bean:write name="seleccionarCuentasForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}
		
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/plancuentas/seleccionarCuentas" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="funcionCierre" />
			
			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.seleccionarcuentas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/plancuentas/seleccionarCuentas' />
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Arbol de Cuentas --%>
				<treeview:treeview useFrame="false" arbolBean="seleccionarCuentasArbolBean" scope="session" isRoot="true" fieldName="descripcion" fieldId="cuentaId" fieldChildren="hijos" lblUrlObjectId="cuentaId"
					styleClass="treeview" checkbox="false" pathImages="/componentes/visorArbol/" urlJs="/componentes/visorArbol/visorArbol.js" nameSelectedId="cuentaId"
					urlName="javascript:seleccionarNodo(cuentaId, marcadorAncla);" urlConnectorOpen="javascript:abrirNodo(cuentaId, marcadorAncla);"
					urlConnectorClose="javascript:cerrarNodo(cuentaId, marcadorAncla);" lblUrlAnchor="marcadorAncla" />

			</vgcinterfaz:contenedorForma>

		</html:form>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="JavaScript" type="text/javascript">		
			
			if ((window.document.seleccionarCuentasForm.funcionCierre != null) && (window.document.seleccionarCuentasForm.funcionCierre.value != '')) {
				funcionCierre = window.document.seleccionarCuentasForm.funcionCierre.value;
			}
			
		</script>

	</tiles:put>

</tiles:insert>
