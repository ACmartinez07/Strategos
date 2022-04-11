<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/06/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarPaginaForm" property="paginaId" value="0">
			<vgcutil:message key="jsp.editarpaginas.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarPaginaForm" property="paginaId" value="0">
			<vgcutil:message key="jsp.editarpaginas.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarPaginaForm" property="bloqueado">
				<bean:write name="editarPaginaForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarPaginaForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			// Inicializar botones de los cuadros Numéricos  
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			
			function validar(forma) 
			{
				if (validateEditarPaginaForm(forma)) 
				{
					window.document.editarPaginaForm.action = '<html:rewrite action="/presentaciones/paginas/guardarPagina"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{			
				if (validar(document.editarPaginaForm)) 
					window.document.editarPaginaForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarPaginaForm.action = '<html:rewrite action="/presentaciones/paginas/cancelarGuardarPagina"/>';
				window.document.editarPaginaForm.submit();
			}

			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}
			
			function corregirLongitud(cuadroTexto, longitudTexto) 
			{
				var texto = cuadroTexto;				
				var longitud = texto.value.length;				
				if (longitud > longitudTexto) 
				{
					texto.value = texto.value.substring(0, longitudTexto);
					alert('<vgcutil:message key="jsp.longitud.maxima" />');
					texto.focus();
				}
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPaginaForm.descripcion" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/presentaciones/paginas/guardarPagina" focus="descripcion">

			<html:hidden property="paginaId" />
			<html:hidden property="numero" />
			<html:hidden property="vistaId" />

			<vgcinterfaz:contenedorForma width="480px" height="240px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarPaginaForm" property="paginaId" value="0">
						<vgcutil:message key="jsp.editarpaginas.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarPaginaForm" property="paginaId" value="0">
						<vgcutil:message key="jsp.editarpaginas.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarpaginas.titulo.organizacion" /></b></td>
						<td colspan="3"><bean:write name="editarPaginaForm" property="nombreOrganizacion" /></td>
					</tr>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarpaginas.titulo.vista" /></b></td>
						<td colspan="32"><bean:write name="editarPaginaForm" property="nombreVista" /></td>
					</tr>

					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarpaginas.ficha.numeropagina" /></b></td>
						<td colspan="3"><bean:write name="editarPaginaForm" property="numero" /></td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message key="jsp.editarpaginas.ficha.descripcion" /></td>
						<td colspan="3"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="descripcion" cols="50" rows="3" styleClass="cuadroTexto" onkeyup="corregirLongitud(descripcion, 2000);" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarpaginas.ficha.filas" /></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="left">
										<html:text property="filas" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
									</td>
									<logic:notEqual name="editarPaginaForm" property="bloqueado" value="true">
										<td valign="middle">
											<img id="boton1" name="boton1" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
										</td>
									</logic:notEqual>
								</tr>
							</table>
							<map name="MapControlUpDown1" id="MapControlUpDown1">
								<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton1')" onmouseout="normalAction('boton1')" onmousedown="iniciarConteoContinuo('filas', 50, 1);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton1')" onmouseout="normalAction('boton1')" onmousedown="iniciarConteoContinuo('filas', 50, 1);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
							</map>
						</td>
						<td align="right">
							<vgcutil:message key="jsp.editarpaginas.ficha.ancho" /> ( <vgcutil:message key="jsp.editarpaginas.ficha.pixel" /> )
						</td>
						<td width="150px">
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="left">
										<html:text property="ancho" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="4" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
									</td>
									<logic:notEqual name="editarPaginaForm" property="bloqueado" value="true">
										<td valign="middle">
											<img id="boton2" name="boton2" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" />
										</td>
									</logic:notEqual>
								</tr>
							</table>
							<map name="MapControlUpDown2" id="MapControlUpDown2">
								<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton2')" onmouseout="normalAction('boton2')" onmousedown="iniciarConteoContinuo('ancho', 999, 100);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton2')" onmouseout="normalAction('boton2')" onmousedown="iniciarConteoContinuo('ancho', 999, 100);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
							</map>
						</td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message key="jsp.editarpaginas.ficha.columnas" /></td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="left">
										<html:text property="columnas" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
									</td>
									<logic:notEqual name="editarPaginaForm" property="bloqueado" value="true">
										<td valign="middle"><img id="boton3" name="boton3" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" /></td>
									</logic:notEqual>
								</tr>
							</table>
							<map name="MapControlUpDown3" id="MapControlUpDown3">
								<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton3')" onmouseout="normalAction('boton3')" onmousedown="iniciarConteoContinuo('columnas', 50,1);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton3')" onmouseout="normalAction('boton3')" onmousedown="iniciarConteoContinuo('columnas', 50, 1);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
							</map>
						</td>
						<td align="right"><vgcutil:message key="jsp.editarpaginas.ficha.alto" /> ( <vgcutil:message key="jsp.editarpaginas.ficha.pixel" /> )</td>
						<td width="150px">
							<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
								<tr>
									<td valign="middle" align="left">
										<html:text property="alto" size="3" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="4" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" />
									</td>
									<logic:notEqual name="editarPaginaForm" property="bloqueado" value="true">
										<td valign="middle"><img id="boton4" name="boton4" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown4" /></td>
									</logic:notEqual>
								</tr>
							</table>
							<map name="MapControlUpDown4" id="MapControlUpDown4">
								<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('boton4')" onmouseout="normalAction('boton4')" onmousedown="iniciarConteoContinuo('alto', 999, 100);aumentoConstante()"
									onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('boton4')" onmouseout="normalAction('boton4')"
									onmousedown="iniciarConteoContinuo('alto', 999, 100);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
							</map>
						</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarPaginaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarPaginaForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>">
		</script>

	</tiles:put>
</tiles:insert>
