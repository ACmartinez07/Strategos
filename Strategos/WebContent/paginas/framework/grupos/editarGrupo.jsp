<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (24/04/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">
	<tiles:put name="title" type="String">
		<logic:equal name="editarGrupoForm" property="grupoId" value="0">
			<logic:equal name="editarGrupoForm" property="copiar" value="false">
				<vgcutil:message key="jsp.framework.editargrupo.titulo.nuevo" />
			</logic:equal>
			<logic:notEqual name="editarGrupoForm" property="copiar"
				value="false">
				<vgcutil:message key="jsp.framework.editargrupo.titulo.copiar" />
			</logic:notEqual>
		</logic:equal>
		<logic:notEqual name="editarGrupoForm" property="grupoId" value="0">
			<vgcutil:message key="jsp.framework.editargrupo.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			// Variable global con todas las relaciones padre-hijos de permisos
			var listaPadresHijosPermisos = '<bean:write name="editarGrupoForm" property="listaPadresHijosPermisos"/>';
			
			function validar(forma) 
			{
				if (validateEditarGrupoForm(forma)) 
				{
					setPermisosSeleccionados();
					if (forma.permisos.value == '') 
					{
						alert('<vgcutil:message key="jsp.framework.editargrupo.validacion.sinpermisos" />');
						return false;
					}
					window.document.editarGrupoForm.action = '<sslext:rewrite  action="/framework/grupos/guardarGrupo"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}
			
			function seleccionarNodo(nodoId, marcadorAncla) 
			{
				//window.location.href='<html:rewrite action="/organizaciones/gestionarPermisos"/>?selectedId=' + nodoId + marcadorAncla;
			}

			function openNodo(nodoId, marcadorAncla) 
			{
				//window.location.href='<html:rewrite action="/organizaciones/gestionarPermisos"/>?openId=' + nodoId + marcadorAncla;
			}
			
			function closeNodo(nodoId, marcadorAncla) 
			{	
				//window.location.href='<html:rewrite action="/organizaciones/gestionarPermisos"/>?closeId=' + nodoId + marcadorAncla;
			}
			
			function guardar() 
			{
				if (validar(document.editarGrupoForm)) 					
					window.document.editarGrupoForm.submit();
					activarBloqueoEspera();									
			}
			
			function cancelarGuardar() 
			{
				window.document.editarGrupoForm.action = '<sslext:rewrite action="/framework/grupos/cancelarGuardarGrupo"/>';
				window.document.editarGrupoForm.submit();
				this.close();
			}
			
			// funcion que se utiliza para cargar todos los permisos asociados al grupo a ser editado al
			// inicio de la carga de la ficha o ventana
			function inicializarPermisosSeleccionados() 
			{
				var forma = self.document.editarGrupoForm;
			
				total = forma.elements.length;
			
				var seleccionados = self.document.editarGrupoForm.permisos.value;
			
				for (var indice = 0; indice < total; indice++) 
				{
					if (forma.elements[indice].name == 'permiso') 
					{
						// Se busca al inicio de la cadena o string de permisos seleccionados
						if (seleccionados.indexOf('<bean:write name="editarGrupoForm" property="separador"/>' + forma.elements[indice].value + '<bean:write name="editarGrupoForm" property="separador"/>') > -1) 
							forma.elements[indice].checked = true;
						else if (seleccionados.indexOf(forma.elements[indice].value + '<bean:write name="editarGrupoForm" property="separador"/>') == 0) 
							forma.elements[indice].checked = true;
						else 
							forma.elements[indice].checked = false;
					}
				}
			}
			
			function seleccionarNodoPadre(nodoHijo) 
			{
				var separador = '<bean:write name="editarGrupoForm" property="separador" />';
				var separadorPadreHijo = '<bean:write name="editarGrupoForm" property="separadorPadreHijo" />';
			
				posicionHijo = listaPadresHijosPermisos.indexOf(separadorPadreHijo + nodoHijo + separador);
				if (posicionHijo > -1) 
				{
					posicionPadre = listaPadresHijosPermisos.substring(0, posicionHijo).lastIndexOf(separador);
					var nodoPadre = listaPadresHijosPermisos.substring(posicionPadre + separador.length, posicionHijo);
					// Se marcan los nodos padres
					for (var indice = 0; indice < total; indice++) 
					{
						var forma = self.document.editarGrupoForm;
						total = self.document.editarGrupoForm.elements.length;
						if (forma.elements[indice].value == nodoPadre) 
						{
							if (forma.elements[indice].name == 'permiso') 
							{
								forma.elements[indice].checked = true;
								seleccionarNodoPadre(nodoPadre);
							}
						}
					}
				}
			}
			
			function desSeleccionarNodosHijos(nodoPadre) 
			{
				var separador = '<bean:write name="editarGrupoForm" property="separador" />';
				var separadorPadreHijo = '<bean:write name="editarGrupoForm" property="separadorPadreHijo" />';
				var forma = self.document.editarGrupoForm;
				var total = self.document.editarGrupoForm.elements.length;
			
				var textoBuscado = separador + nodoPadre + separadorPadreHijo;
				posicionPadre = listaPadresHijosPermisos.indexOf(textoBuscado);
				while (posicionPadre > -1) 
				{
					posicionHijo = listaPadresHijosPermisos.substring(posicionPadre + textoBuscado.length, listaPadresHijosPermisos.length).indexOf(separador);
					posicionPadre = posicionPadre + textoBuscado.length;
					posicionHijo = posicionPadre + posicionHijo;
					var nodoHijo = listaPadresHijosPermisos.substring(posicionPadre, posicionHijo);
					for (var indice = 0; indice < total; indice++) 
					{
						var forma = self.document.editarGrupoForm;
						total = self.document.editarGrupoForm.elements.length;
						if (forma.elements[indice].value == nodoHijo) 
						{
							if (forma.elements[indice].name == 'permiso') 
							{
								forma.elements[indice].checked = false;
								desSeleccionarNodosHijos(nodoHijo);
							}
						}
					}
					posicionPadre = listaPadresHijosPermisos.substring(posicionHijo + separador.length, listaPadresHijosPermisos.length).indexOf(textoBuscado);
					if (posicionPadre > -1) 
						posicionPadre = posicionPadre + posicionHijo + separador.length;
				}
			}
			
			// Función que chequea los permisos padres de un permiso seleccionado, 
			// y deschequea los permisos hijos de un permiso des-seleccionado.
			function setSeleccionadosPadreHijo(objetoInputCheckbox) 
			{
				if (objetoInputCheckbox.checked) 
				{
					if (objetoInputCheckbox.value == '<bean:write name="editarGrupoForm" property="permisoIdRoot"/>') 
					{
						objetoInputCheckbox.checked = false;
						return;
					}
					seleccionarNodoPadre(objetoInputCheckbox.value);
				} 
				else 
					desSeleccionarNodosHijos(objetoInputCheckbox.value);
			}
			
			function selectUnselectTodosPermisos() 
			{
				var forma = self.document.editarGrupoForm;
				var total = forma.elements.length;
				var campoControl = self.document.editarGrupoForm.permisoControl;
				var seleccionado;
			
				if (campoControl.checked) 
					seleccionado = true;
				else 
					seleccionado = false;

				for (var indice = 0; indice < total; indice++ ) 
				{
					if (forma.elements[indice].name == 'permiso') 
					{
						if (forma.elements[indice].value == '<bean:write name="editarGrupoForm" property="permisoIdRoot"/>') 
							forma.elements[indice].checked = false;
						else 
							forma.elements[indice].checked = seleccionado;
					}
				}
			}
			
			function setPermisosSeleccionados(fieldOutput, fieldName) 
			{
				var forma = self.document.editarGrupoForm;
				var total = forma.elements.length;
				var objetoInput = self.document.editarGrupoForm.permisos;
				
				objetoInput.value = '';
			
				for(var indice = 0; indice < total; indice++) 
				{
					if (forma.elements[indice].name == 'permiso') 
					{
						if (forma.elements[indice].checked) 
							objetoInput.value = objetoInput.value + forma.elements[indice].value + '<bean:write name="editarGrupoForm" property="separador"/>';
					}
				}
			}
	
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarGrupoForm.grupo" />

		<html:form action="/framework/grupos/guardarGrupo" focus="grupo">

			<html:hidden property="grupoId" />
			<html:hidden property="copiar" />
			<html:hidden property="permisos" />

			<vgcinterfaz:contenedorForma width="700px" height="465px">
				<vgcinterfaz:contenedorFormaTitulo>
					<logic:equal name="editarGrupoForm" property="grupoId" value="0">
						<logic:equal name="editarGrupoForm" property="copiar"
							value="false">
							<vgcutil:message key="jsp.framework.editargrupo.titulo.nuevo" />
						</logic:equal>
						<logic:notEqual name="editarGrupoForm" property="copiar"
							value="false">
							<vgcutil:message key="jsp.framework.editargrupo.titulo.copiar" />
						</logic:notEqual>
					</logic:equal>
					<logic:notEqual name="editarGrupoForm" property="grupoId" value="0">
						<vgcutil:message key="jsp.framework.editargrupo.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0" width="100%">

					<tr>
						<td width="110px"><vgcutil:message
							key="jsp.framework.editargrupo.label.nombre" /></td>
						<td><html:text styleClass="cuadroTexto" property="grupo"
							size="65" maxlength="50" /></td>
					</tr>

					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td colspan="2" height="20"><input name='permisoControl'
							type='checkbox' styleClass="botonSeleccionMultiple"
							onClick="javascript:selectUnselectTodosPermisos()">
							<vgcutil:message key="jsp.framework.editargrupo.selectunselectall" />
						</td>
					</tr>
					<tr>
						<td colspan="2" height="310">
							<treeview:treeview
								name="editarGrupoArbolPermisos" 
								scope="session"
								baseObject="editarGrupoPermisoRoot" 
								isRoot="true"
								fieldName="permiso" 
								fieldId="permisoId"
								idType="String" 
								fieldChildren="hijos"
								lblUrlObjectId="objetoId" 
								styleClass="treeview" 
								checkbox="true"
								checkboxName="permiso"
								checkboxOnClick="javascript:setSeleccionadosPadreHijo(this);"
								urlJs="/componentes/visorArbol/visorArbol.js"
								pathImages="/paginas/framework/grupos/treeview/permisos/"
								urlConnectorOpen="javascript:openNodo(objetoId, marcadorAncla);" 
								urlConnectorClose="javascript:closeNodo(objetoId, marcadorAncla);"
								urlName="javascript:seleccionarNodo(objetoId, marcadorAncla);" 
								lblUrlAnchor="marcadorAncla"
								nameSelectedId="permisoId"
								useFrame="true"/>
						</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<logic:notEqual name="editarGrupoForm" property="bloqueado"
						value="true">
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
						href="javascript:cancelarGuardar();"
						class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

			<script language="Javascript">
				inicializarPermisosSeleccionados();
			</script>

		</html:form>
		<html:javascript formName="editarGrupoForm" dynamicJavascript="true"
			staticJavascript="false" />
		<script language="Javascript"
			src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>

</tiles:insert>
