<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (08/07/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarProductoForm" property="productoId"
			value="0">
			<vgcutil:message key="jsp.editarproducto.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarProductoForm" property="productoId"
			value="0">
			<vgcutil:message key="jsp.editarproducto.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
        
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validarProducto(forma) {
				if (validateEditarProductoForm(forma)) {
					window.document.editarProductoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/guardarProducto"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else {
					return false;
				}
			}

			function guardarProducto() {
				if (validarProducto(document.editarProductoForm)) {
					window.document.editarProductoForm.submit();
				}
			}

			function cancelarProducto() {			
				window.document.editarProductoForm.action = '<html:rewrite action="/planificacionseguimiento/productos/cancelarGuardarProducto"/>';
				window.document.editarProductoForm.submit();
			}

			function ejecutarPorDefectoProducto(e) {			
				if(e.keyCode==13) {
					guardarProducto();
				}
			}
			
			function seleccionarFechaFormulacionProducto() {				
				mostrarCalendario('document.all.fechaInicio' , document.editarProductoForm.fechaInicio.value, '<vgcutil:message key="formato.fecha.corta" />');
			}			

			function limpiarFechaProducto(campoNombre) {
				campoNombre.value="";
			}	
			
			function limpiarResponsableProducto() {				
				document.editarProductoForm.responsableId.value = "";				
				document.editarProductoForm.nombreResponsable.value = "";
			}			

			function seleccionarResponsableProducto(){					 
				abrirSelectorResponsables('editarProductoForm', 'nombreResponsable', 'responsableId');
			}
			
		</script>
		
		  	<%-- Función JavaScript externa --%>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>      
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarProductoForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planificacionseguimiento/productos/guardarProducto" focus="nombre"
			onsubmit="if (validarProducto(document.editarProductoForm)) return true; else return false;">

			<html:hidden property="productoId" />
			<html:hidden property="responsableId" />			

			<vgcinterfaz:contenedorForma width="550px" height="238px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarProductoForm" property="productoId"
						value="0">
						<vgcutil:message key="jsp.editarproducto.titulo.nuevo" />
					</logic:equal>
					
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarProductoForm" property="productoId"
						value="0">
						<vgcutil:message key="jsp.editarproducto.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0"
					align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><vgcutil:message
							key="jsp.editarproducto.ficha.nombre" /></td>
						<td><html:text property="nombre" size="59" maxlength="50"
							styleClass="cuadroTexto" onkeypress="ejecutarPorDefectoProducto(event)"/></td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message
							key="jsp.editarproducto.ficha.fechaprogramado" /></td>
						<td colspan="2"><html:text property="fechaInicio" size="10"
							onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
						<img style="cursor: pointer"
							onclick="seleccionarFechaFormulacionProducto();"
							src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.calendario.alt" />"> <img
							style="cursor: pointer"
							onclick="limpiarFechaProducto(document.editarProductoForm.fechaInicio);"
							src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.limpiar.alt" />"></td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message key="jsp.editarproducto.ficha.descripcion" /></td>
						<td colspan="2"><html:textarea property="descripcion" rows="5" cols="58" styleClass="cuadroTexto" /></td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message
							key="jsp.editarproducto.ficha.responsable" /></td>

						<td><html:text property="nombreResponsable" size="59"
							readonly="true" disabled="false" maxlength="50"
							styleClass="cuadroTexto" />&nbsp;
							<img style="cursor: pointer"
							onclick="seleccionarResponsableProducto();"
							src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="jsp.editarproducto.ficha.responsable.fijar.meta" />">&nbsp;<img style="cursor: pointer"
							onclick="limpiarResponsableProducto();"
							src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.limpiar.alt" />"></td>
					</tr>					
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<logic:notEqual name="editarProductoForm" property="bloqueado"
						value="true">
						<img
							src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:guardarProducto();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img
						src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:cancelarProducto();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarProductoForm"
			dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1"
			src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>

	</tiles:put>
</tiles:insert>