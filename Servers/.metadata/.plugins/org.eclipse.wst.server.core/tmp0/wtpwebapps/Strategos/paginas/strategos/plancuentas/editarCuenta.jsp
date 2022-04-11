<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Kerwin Arias (28/10/2012)--%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarCuentaForm" property="cuentaId" value="0">			
			<vgcutil:message key="jsp.editarcuenta.titulo.nuevo" /> - <bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo.nombre" />			
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarCuentaForm" property="cuentaId" value="0">
			<%-- Cuando no es RAIZ --%>
			<logic:notEmpty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar">
				<vgcutil:message key="jsp.editarcuenta.titulo.modificar" /> - <bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar.nombre" />
			</logic:notEmpty>
			<%-- Cuando es RAIZ --%>
			<logic:empty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar">
				<vgcutil:message key="jsp.editarcuenta.titulo.modificar" /> - <bean:write name="editarCuentaForm" property="descripcion" />
			</logic:empty>
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="Javascript" src="<html:rewrite page='/paginas/strategos/plancuentas/plancuentasJs/cuentas.js'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">	
		
			function validar(forma) {
				if (validateEditarCuentaForm(forma)) {
				  window.document.editarCuentaForm.action = '<html:rewrite action="/plancuentas/guardarCuenta"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function guardar() {			   
				if (validar(document.editarCuentaForm)) {					
					window.document.editarCuentaForm.submit();
				}
			}

			function cancelar() {
				window.document.editarCuentaForm.action = '<html:rewrite action="/plancuentas/cancelarGuardarCuenta"/>';
				window.document.editarCuentaForm.submit();
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}			 
			
			function soloNumeros(e){
				//NOTA IMPORTANTE: Esta rutina no funciona completamente bien (hay que cambiarla por algo mas robusto)
				var nav4 = window.Event ? true : false;
				var key = nav4 ? e.which : e.keyCode;
				if(key==13) {
					guardar();
				}
				if (!(key <= 13 || (key >= 48 && key <= 57))) {
					window.document.editarCuentaForm.codigo.value = window.document.editarCuentaForm.codigo.value.substring(0, window.document.editarCuentaForm.codigo.value.length-1);
				}
			}
				
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarCuentaForm.codigo" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/plancuentas/guardarCuenta" focus="codigo" onsubmit="if (validar(document.editarCuentaForm)) return true; else return false;">			

			<html:hidden name="editarCuentaForm" property="cuentaId" />
			<html:hidden name="editarCuentaForm" property="padreId" />
			
			<vgcinterfaz:contenedorForma width="480px" height="125px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarCuentaForm" property="cuentaId" value="0">						
						<vgcutil:message key="jsp.editarcuenta.titulo.nuevo" /> - <bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo.nombre" />						
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarCuentaForm" property="cuentaId" value="0">
						<%-- Cuando no es RAIZ --%>
						<logic:notEmpty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar">
							<vgcutil:message key="jsp.editarcuenta.titulo.modificar" /> - <bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar.nombre" />
						</logic:notEmpty>
						<%-- Cuando es RAIZ --%>
						<logic:empty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar">
							<vgcutil:message key="jsp.editarcuenta.titulo.modificar" /> - <bean:write name="editarCuentaForm" property="descripcion" />
						</logic:empty>						
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%" align="center">
									
					<%-- Código --%>
					<tr>
						<td align="right"><bean:message key="jsp.editarcuenta.ficha.codigo" /></td>
						<td><html:text property="codigo" onkeyup="soloNumeros(event);" maxlength="10" size="30"  styleClass="cuadroTexto" /></td>
					</tr>					
					
					<%-- Descripción --%>
					<tr>
						<td><bean:message key="jsp.editarcuenta.ficha.descripcion" /></td>
						<td><html:text property="descripcion" onkeypress="ejecutarPorDefecto(event)" maxlength="250" size="60" styleClass="cuadroTexto" /></td>
					</tr>
					
				</table>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarCuentaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
				
		<script language="Javascript1.1">
			<logic:equal name="editarCuentaForm" property="cuentaId" value="0">
				var longitudMascara = '<bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasNuevo.mascara" />';
				document.editarCuentaForm.codigo.maxLength = longitudMascara.length;
			</logic:equal>
			<logic:notEqual name="editarCuentaForm" property="cuentaId" value="0">				 
		 		<%-- Cuando no es RAIZ --%>
				<logic:notEmpty name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar">
					var longitudMascara = '<bean:write name="gestionarCuentasForm" property="grupoMascaraCodigoPlanCuentasModificar.mascara" />';					 
					document.editarCuentaForm.codigo.maxLength = longitudMascara.length;
				</logic:notEmpty>				
			</logic:notEqual>
		</script>
				
		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarCuentaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
