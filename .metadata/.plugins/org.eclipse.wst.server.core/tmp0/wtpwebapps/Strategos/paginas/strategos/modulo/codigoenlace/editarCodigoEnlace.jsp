<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarCodigoEnlaceForm" property="id" value="0">
			<vgcutil:message key="jsp.editar.codigoenlace.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarCodigoEnlaceForm" property="id" value="0">
			<vgcutil:message key="jsp.editar.codigoenlace.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarCodigoEnlaceForm" property="bloqueado">
				<bean:write name="editarCodigoEnlaceForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarCodigoEnlaceForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var _setCloseParent = false;
		
			function cancelar() 
			{
				this.close();
			}

			function guardar() 
			{
				if (validar())
				{
					var url = '?id=' + document.editarCodigoEnlaceForm.id.value;
					url = url + '&bi=' + document.editarCodigoEnlaceForm.bi.value;
					url = url + '&categoria=' + document.editarCodigoEnlaceForm.categoria.value;
					
					window.document.editarCodigoEnlaceForm.action = '<html:rewrite action="/codigoenlace/guardarCodigoEnlace"/>' + url;
					window.document.editarCodigoEnlaceForm.submit();
				}
			}

			function validar() 
			{
				if (document.editarCodigoEnlaceForm.codigo.value == null || document.editarCodigoEnlaceForm.codigo.value == "" || document.editarCodigoEnlaceForm.codigo.value.length == 0)
				{
					showAlert('<vgcutil:message key="jsp.editar.codigoenlace.alerta.codigo.empty" />', 100, 340);
					return false;
				}
				if (document.editarCodigoEnlaceForm.nombre.value == null || document.editarCodigoEnlaceForm.nombre.value == "" || document.editarCodigoEnlaceForm.nombre.value.length == 0)
				{
					showAlert('<vgcutil:message key="jsp.editar.codigoenlace.alerta.nombre.empty" />', 100, 340);
					return false;
				}
					
				return true;
			}

			function refrescarPadre()
			{
				window.opener.actualizar();
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarCodigoEnlaceForm.codigo" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/codigoenlace/guardarCodigoEnlace" focus="codigo">
			
			<html:hidden property="id" />
			
			<vgcinterfaz:contenedorForma width="420px" height="200px" bodyAlign="center" bodyValign="middle" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarCodigoEnlaceForm" property="id" value="0">
						<vgcutil:message key="jsp.editar.codigoenlace.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarCodigoEnlaceForm" property="id" value="0">
						<vgcutil:message key="jsp.editar.codigoenlace.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left" width="180px">
							<vgcutil:message key="jsp.editar.codigoenlace.ficha.codigo" />
						</td>
						<td colspan="3">
							<html:text property="codigo" size="36" maxlength="15" styleClass="cuadroTexto" />
						</td>
					</tr>
					<tr>
						<td align="left" width="180px">
							<vgcutil:message key="jsp.editar.codigoenlace.ficha.nombre" />
						</td>
						<td colspan="3">
							<html:text property="nombre" size="36" maxlength="15" styleClass="cuadroTexto" />
						</td>
					</tr>
					<tr>
						<td align="left" width="180px">
							<vgcutil:message key="jsp.editar.codigoenlace.ficha.bi" />
						</td>
						<td colspan="3">
							<html:text property="bi" size="36" maxlength="15" styleClass="cuadroTexto" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 0, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 0, false, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 0, false)" />
						</td>
					</tr>
					<tr>
						<td align="left" width="180px">
							<vgcutil:message key="jsp.editar.codigoenlace.ficha.categoria" />
						</td>
						<td colspan="3">
							<html:text property="categoria" size="36" maxlength="15" styleClass="cuadroTexto" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
						</td>
					</tr>
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarCodigoEnlaceForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script>
			<logic:equal name="editarCodigoEnlaceForm" property="status" value="4">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarCodigoEnlaceForm" property="status" value="0">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.nuevo.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarCodigoEnlaceForm" property="status" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.modificar.no.ok" />', 80, 300);
			</logic:equal>
			<logic:equal name="editarCodigoEnlaceForm" property="status" value="3">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.duplicado" />', 80, 300);
			</logic:equal>
			<logic:equal name="editarCodigoEnlaceForm" property="status" value="6">
				_setCloseParent = false;
				showAlert('<bean:write name="editarCodigoEnlaceForm" property="respuesta" />', 80, 300);
			</logic:equal>
		</script>

	</tiles:put>
</tiles:insert>