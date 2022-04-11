<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (17/12/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarModeloForm" property="editar" value="1">
			<vgcutil:message key="jsp.editarmodelo.titulo.modificar" />
		</logic:notEqual>

		<%-- Para el caso de Bloqueado --%>
		<logic:equal name="editarModeloForm" property="editar" value="1">
			<vgcutil:message key="jsp.editarmodelo.titulo.ver" />
		</logic:equal>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarModeloForm" property="bloqueado">
				<bean:write name="editarModeloForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarModeloForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var _setCloseParent = false;

			function guardarDatosGrafico() 
			{
				document.editarModeloForm.binario.value = document.JGraphpadApplet.getStrDatosGrafico();
			}

			function verModeloCausaEfecto() 
			{
				document.JGraphpadApplet.init();
			}

			function validar(forma) 
			{
				if (validateEditarModeloForm(forma))
				{
					if (window.document.editarModeloForm.nombre.value == "")
					{
						alert('<vgcutil:message key="jsp.editar.modelo.alert.nombre.invalido.vacio" /> ');
						return false;
					}
					
					guardarDatosGrafico();
					return true;
				} 
				else 
					return false;
			}

			function guardar() 
			{			
				if (validar(document.editarModeloForm)) 
				{
				    window.document.editarModeloForm.action = '<html:rewrite action="/planes/modelos/guardarModelo"/>?planId=<bean:write name="editarModeloForm" property="planId" />&modeloId=<bean:write name="editarModeloForm" property="modeloId" />';
					window.document.editarModeloForm.submit();
				}
			}

			function cancelar() 
			{	
				if (window.document.editarModeloForm.source.value == "2")
					window.location.href='<html:rewrite action="/planes/gestionarPlan" />?planId=<bean:write name="editarModeloForm" property="planId" />';
				else
					window.location.href='<html:rewrite action="/planes/modelos/gestionarModelos" />?planId=<bean:write name="editarModeloForm" property="planId" />';
			}

			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}

			function vistaPrevia() 
			{			
				if (validar(document.editarModeloForm)) 
			   	{
			    	window.document.editarModeloForm.action = '<html:rewrite action="/planes/modelos/editarModelo"/>?planId=<bean:write name="editarModeloForm" property="planId" />&modeloId=<bean:write name="editarModeloForm" property="modeloId" />&evitarrecargar=1&editar=0&previsualizar=1';
				  	window.document.editarModeloForm.submit();
			   	}
			}

			function regresar() 
			{			
				window.document.editarModeloForm.action = '<html:rewrite action="/planes/modelos/editarModelo"/>?planId=<bean:write name="editarModeloForm" property="planId" />&modeloId=<bean:write name="editarModeloForm" property="modeloId" />&evitarrecargar=1&editar=1';
			  	window.document.editarModeloForm.submit();
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado 
		<vgcinterfaz:setFocoObjetoInterfaz
			objetoInputHtml="" />
			--%>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/modelos/guardarModelo" styleClass="formaHtml">

			<html:hidden property="planId" />
			<html:hidden property="modeloId" />
			<html:hidden property="binario" />
			<html:hidden property="datosArbolPlan" />
			<html:hidden property="source" />

			<vgcinterfaz:contenedorForma mostrarBarraSuperior="false" idHtml="formaModelo">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarModeloForm" property="editar" value="1">
						<vgcutil:message key="jsp.editarmodelo.titulo.modificar" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarModeloForm" property="editar" value="1">
						<vgcutil:message key="jsp.editarmodelo.titulo.ver" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<bean:define id="datosArbol" name="editarModeloForm" property="datosArbolPlan" type="String"></bean:define>
				<bean:define id="editarModelo" name="editarModeloForm" property="editar" type="String"></bean:define>

				<%-- Ficha de datos --%>
				<table class="tablaPrincipal" cellpadding="3" cellspacing="0" border="0">
					<tr class="barraFiltrosForma">
						<td>
							<table class="tablaDatos" cellpadding="3" cellspacing="0" border="0">
								<tr class="barraFiltrosForma">
									<td valign="top"><vgcutil:message key="jsp.editar.modelo.nombre" /></td>
									<td valign="top"><html:text property="nombre" size="50" maxlength="50" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" /></td>
									<td width="75px" valign="top"><vgcutil:message key="jsp.editar.modelo.descripcion" /></td>
									<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="2" cols="135" property="descripcion" styleClass="cuadroTexto" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<logic:notEqual name="editarModeloForm" property="status" value="0">
						<tr class="barraFiltrosForma">
							<table id="tablaApplet" cellpadding="3" cellspacing="0" border="0" align="center">
								<tr>
									<td>
										<applet id="appletDiagram" code="org.jgraph.JGraphpad.class"
											archive="vgcDiagramaCausaEfecto.jar, jgraphpad.jar, dom4j-1.6.jar"
											codebase="<html:rewrite page='/paginas/strategos/planes/modelos/applets'/>"
											name="JGraphpadApplet">
											<param name="mostrarHerramientasEdicion" value='<%=editarModelo%>'>
											<param name="datosArbol" value='<%=datosArbol%>'>
											<param name="datosGrafico" value='<bean:write name="editarModeloForm" property="binario" />'>
										</applet>
									</td>
								</tr>
							</table>
						</tr>
					</logic:notEqual>
				</table>
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">

					<logic:equal name="editarModeloForm" property="previsualizar"
						value="0">

						<logic:equal name="editarModeloForm" property="editar" value="1">

							<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
								onMouseOut="this.className='mouseFueraBarraInferiorForma'"
								href="javascript:vistaPrevia();"
								class="mouseFueraBarraInferiorForma"><vgcutil:message
								key="boton.vistaprevia" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					

						       <logic:notEqual name="editarModeloForm" property="bloqueado"
								value="true">
								<img
									src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
									border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
									onMouseOut="this.className='mouseFueraBarraInferiorForma'"
									href="javascript:guardar();"
									class="mouseFueraBarraInferiorForma"> <vgcutil:message
									key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;								
					            </logic:notEqual>

						</logic:equal>


						<img
							src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:cancelar();"
							class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
						</logic:equal>

					<logic:equal name="editarModeloForm" property="previsualizar"
						value="1">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:regresar();"
							class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;						
						</logic:equal>



				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarModeloForm" dynamicJavascript="true"
			staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<script type="text/javascript">
			<logic:equal name="editarModeloForm" property="status" value="0">
				_setCloseParent = true;
			</logic:equal>
			<logic:notEqual name="editarModeloForm" property="status" value="0">
		        function getEl(elementId) 
		        {
				    return document.getElementById(elementId);
				}
		        
		        function ajustarTamano()
		        {
		    		var altoPagina = screen.height;
		
		    		var appletDiagram = getEl('appletDiagram');
		        	if ((altoPagina-140) != appletDiagram.style.height.replace("px", ""))
		        		setTimeout("ajustar()", ((_totalLoadPage * 2000) + 500));
		        }
		        
		        function ajustar()
		        {
		            var anchoPagina = screen.width; 
		    		var altoPagina = screen.height;
		
		    		var tablaApplet = getEl('tablaApplet');		
		    		tablaApplet.style.height = altoPagina-140;
		    		tablaApplet.style.width = anchoPagina-60;
		    		
		    		var appletDiagram = getEl('appletDiagram');
		    		appletDiagram.style.height = altoPagina-140;
		    		appletDiagram.style.width = anchoPagina-60;
		    		
		    		ajustarTamano();
		        }

		        timeLoad();
		        ajustar();
			</logic:notEqual>
		</script>
	</tiles:put>
</tiles:insert>
