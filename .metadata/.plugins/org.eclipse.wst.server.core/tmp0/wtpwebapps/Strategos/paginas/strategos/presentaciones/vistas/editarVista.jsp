<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (09/06/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="false">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarVistaForm" property="vistaId"
			value="0">
			<vgcutil:message key="jsp.editarvista.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarVistaForm" property="vistaId"
			value="0">
			<vgcutil:message key="jsp.editarvista.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarVistaForm" property="bloqueado">
				<bean:write name="editarVistaForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarVistaForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			function validar(forma) 
			{								
				if (validateEditarVistaForm(forma))
				{				    	    				    					
					window.document.editarVistaForm.action = '<html:rewrite action="/presentaciones/vistas/guardarVista"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{			    		    			
				if (validar(document.editarVistaForm)) 
					window.document.editarVistaForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarVistaForm.action = '<html:rewrite action="/presentaciones/vistas/cancelarGuardarVista"/>';
				window.document.editarVistaForm.submit();
			}
			
			function limpiarFecha(campoNombre) 
			{
				campoNombre.value="";
			}

			function seleccionarFechaInicio() 
			{
				mostrarCalendario('document.all.fechaInicio' , document.editarVistaForm.fechaInicio.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
					
		    function seleccionarFechaFin() 
		    {
				mostrarCalendario('document.all.fechaFin' , document.editarVistaForm.fechaFin.value, '<vgcutil:message key="formato.fecha.corta" />');
			}	
						
            function ejecutarPorDefecto(e) 
            {			
				if(e.keyCode==13) 
					guardar();
			}			
	
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarVistaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/presentaciones/vistas/guardarVista" focus="nombre"
			onsubmit="if (validar(document.editarVistaForm)) return true; else return false;">

			<html:hidden property="vistaId" />

			<vgcinterfaz:contenedorForma width="520px" height="230px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarVistaForm" property="vistaId"
						value="0">
						<vgcutil:message key="jsp.editarvista.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarVistaForm" property="vistaId"
						value="0">
						<vgcutil:message key="jsp.editarvista.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0"
					align="center" height="100%" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td><b><vgcutil:message key="jsp.editarvista.titulo.organizacion" /></b></td>
						<td colspan="4"><bean:write name="organizacion" property="nombre" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarvista.ficha.nombre" /></td>
						<td colspan="4"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="57" maxlength="100" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
					</tr>
					
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarvista.ficha.descripcion" /></td>
						<td colspan="4"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="descripcion" rows="7" cols="56" styleClass="cuadroTexto"></html:textarea></td>
					</tr>
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<logic:notEqual name="editarVistaForm" property="bloqueado" value="true">
						<img
							src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img
						src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarVistaForm"
			dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1"
			src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>

	</tiles:put>
	
</tiles:insert>
