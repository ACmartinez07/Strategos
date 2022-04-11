<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/11/2012) --%>
<%-- Modificado por: Andres Caucali (29/08/2018) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarExplicacionForm" property="explicacionId" value="0">
			<logic:equal name="editarExplicacionForm" property="tipo" value="0">
				<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="1">
				<vgcutil:message key="jsp.editarexplicacion.titulo.informes.cualitativos.nuevo" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="2">
				<vgcutil:message key="jsp.editarexplicacion.titulo.informes.ejecutivos.nuevo" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="3">
				<vgcutil:message key="jsp.editarexplicacion.titulo.eventos.nuevo" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="4">
				<vgcutil:message key="jsp.editarexplicacion.titulo.noticia.nuevo" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="5">
				<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
			</logic:equal>
			
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarExplicacionForm" property="explicacionId" value="0">
			<logic:equal name="editarExplicacionForm" property="tipo" value="0">
				<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="1">
				<vgcutil:message key="jsp.editarexplicacion.titulo.informes.cualitativos.modificar" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="2">
				<vgcutil:message key="jsp.editarexplicacion.titulo.informes.ejecutivos.modificar" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="3">
				<vgcutil:message key="jsp.editarexplicacion.titulo.eventos.modificar" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="4">
				<vgcutil:message key="jsp.editarexplicacion.titulo.noticia.modificar" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="tipo" value="5">
				<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
			</logic:equal>
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function validar(forma) 
			{
				<logic:equal name="editarExplicacionForm" property="tipo" value="3">
			 		if (!fechaValida(document.editarExplicacionForm.fechaCompromiso))
		 			{
			 			alert('<vgcutil:message key="jsp.propiedadesexplicacion.eventos.fecha.compromiso" /> ');
			 			return false;
		 			}
			 		if (!fechaValida(document.editarExplicacionForm.fechaReal))
		 			{
			 			alert('<vgcutil:message key="jsp.propiedadesexplicacion.eventos.fecha.registro" /> ');
			 			return false;
		 			}
			 		if (!fechasRangosValidos(document.editarExplicacionForm.fechaReal, document.editarExplicacionForm.fechaCompromiso))
		 			{
			 			alert('<vgcutil:message key="jsp.propiedadesexplicacion.eventos.fecha" /> ');
			 			return false;
		 			}
				</logic:equal>
				if(document.editarExplicacionForm.fecha.value == null  || document.editarExplicacionForm.fecha.value == ""){
					alert("Debe ingresar fecha");
				}else{
				if (validateEditarExplicacionForm(forma)) 
				{
					window.document.editarExplicacionForm.action = '<html:rewrite action="/explicaciones/guardarExplicacion"/>' + '?ts=<%= (new java.util.Date()).getTime() %>' + '&tipo=' + window.document.editarExplicacionForm.tipo.value;
					return true;
				} 
				else 
					return false;
				}
			}

			function guardar() 
			{	
				
				if (validar(document.editarExplicacionForm)) 
					window.document.editarExplicacionForm.submit();
					
			}
			
			
			
			function cancelar() 
			{			
				window.document.editarExplicacionForm.action = '<html:rewrite action="/explicaciones/cancelarGuardarExplicacion"/>';
				window.document.editarExplicacionForm.submit();
			}
			
			function subirAdjunto() 
			{
				if (window.document.editarExplicacionForm.adjunto.value != "") 
				{				
					window.document.editarExplicacionForm.action = '<html:rewrite action="/explicaciones/subirAdjuntoExplicacion"/>';
					window.document.editarExplicacionForm.submit();
				} 
				else 
					alert('<vgcutil:message key="jsp.editarexplicacion.validacion.adjunto.requerido" />');
			}
		      
			function descargarAdjunto(indice) 
			{			     
				window.open('<html:rewrite action="/explicaciones/descargarAdjuntoExplicacion"/>?indice=' + indice, 'AdjuntoExplicacion', '');
			}

			function eliminarAdjunto(indiceAdjunto) 
			{
				window.document.editarExplicacionForm.indiceAdjunto.value = indiceAdjunto;			 
				window.document.editarExplicacionForm.action = '<html:rewrite action="/explicaciones/eliminarAdjuntoExplicacion"/>';
				window.document.editarExplicacionForm.submit();
			}
			
			function seleccionarFecha(nombre) 
			{				
				var field = eval(nombre);
				mostrarCalendario(nombre, field.value, '<vgcutil:message key="formato.fecha.corta" />');				
			}	

			function limpiarFecha(nombre) 
			{
				var field = eval(nombre);
				field.value = "";
			}
			
			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}
			
			function comprueba_extension(archivo) { 
				   extensiones_permitidas = new Array(".ppt", ".xls", ".doc", ".pdf", ".docx",".pptx",".xlsx"); 
				   mierror = ""; 
				   file = archivo.value;
				   if (!archivo) { 
				      //Si no tengo archivo, es que no se ha seleccionado un archivo en el formulario 
				      	alert("No has seleccionado ningún archivo"); 
				   }else{ 
				      //recupero la extensión de este nombre de archivo 
					  extension=file.substring(file.indexOf("."), file.length); 
				      //alert (extension); 
				      //compruebo si la extensión está entre las permitidas 
				      permitida = false; 
				      for (var i = 0; i < extensiones_permitidas.length; i++) { 
				         if (extensiones_permitidas[i] == extension) { 
				         permitida = true; 
				         break; 
				         } 
				      } 
				      if (!permitida) { 
				    	  alert("Comprueba la extensión de los archivos a subir. \nSólo se pueden subir archivos con extensiones: " + extensiones_permitidas.join());

				      	}
				   } 
				   
				}

			
			

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarExplicacionForm.titulo" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/explicaciones/guardarExplicacion" focus="titulo" enctype="multipart/form-data" method="POST">

			<html:hidden property="explicacionId" />
			<html:hidden property="tipo" />
			<input type="hidden" name="indiceAdjunto" />

			<bean:define id="altoForma" value="560px" />
			<logic:equal name="editarExplicacionForm" property="numeroAdjuntos" value="0">
				<bean:define id="altoForma" value="535px" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="numeroAdjuntos" value="1">
				<bean:define id="altoForma" value="553px" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="numeroAdjuntos" value="2">
				<bean:define id="altoForma" value="570px" />
			</logic:equal>
			<logic:equal name="editarExplicacionForm" property="numeroAdjuntos" value="3">
				<bean:define id="altoForma" value="590px" />
			</logic:equal>
			<logic:greaterEqual name="editarExplicacionForm" property="numeroAdjuntos" value="4">
				<bean:define id="altoForma" value="610px" />
			</logic:greaterEqual>

			<bean:define id="bloquearForma" toScope="page">
				<logic:notEmpty name="editarExplicacionForm" property="bloqueado">
					<bean:write name="editarExplicacionForm" property="bloqueado" />
				</logic:notEmpty>
				<logic:empty name="editarExplicacionForm" property="bloqueado">
					false
				</logic:empty>
			</bean:define>
			<bean:define id="escoderControl" toScope="page">
				true
			</bean:define>

			<vgcinterfaz:contenedorForma width="540px" height="<%=altoForma%>">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarExplicacionForm" property="explicacionId" value="0">
						<logic:equal name="editarExplicacionForm" property="tipo" value="0">
							<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="1">
							<vgcutil:message key="jsp.editarexplicacion.titulo.informes.cualitativos.nuevo" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="2">
							<vgcutil:message key="jsp.editarexplicacion.titulo.informes.ejecutivos.nuevo" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="3">
							<vgcutil:message key="jsp.editarexplicacion.titulo.eventos.nuevo" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="4">
							<vgcutil:message key="jsp.editarexplicacion.titulo.noticia.nuevo" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="5">
							<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
						</logic:equal>
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarExplicacionForm" property="explicacionId" value="0">
						<logic:equal name="editarExplicacionForm" property="tipo" value="0">
							<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="1">
							<vgcutil:message key="jsp.editarexplicacion.titulo.informes.cualitativos.modificar" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="2">
							<vgcutil:message key="jsp.editarexplicacion.titulo.informes.ejecutivos.modificar" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="3">
							<vgcutil:message key="jsp.editarexplicacion.titulo.eventos.modificar" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="4">
							<vgcutil:message key="jsp.editarexplicacion.titulo.noticia.modificar" />
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="5">
							<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
						</logic:equal>
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarexplicacion.ficha.organizacion" /></b></td>
						<td><bean:write name="editarExplicacionForm" property="nombreOrganizacion" /></td>
					</tr>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.editarexplicacion.ficha.objeto" /></b></td>
						<td><bean:write name="editarExplicacionForm" property="nombreTipoObjetoKey" /> (<bean:write name="editarExplicacionForm" property="nombreObjetoKey" />)</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.autor" /></td>
						<td><bean:write name="editarExplicacionForm" property="nombreUsuarioCreado" /> (<bean:write name="editarExplicacionForm" property="fechaCreado" />)</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.titulo" /></td>
						<td><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="titulo" onkeypress="ejecutarPorDefecto(event)" size="58" maxlength="250" styleClass="cuadroTexto" /></td>
					</tr>
					<%-- Campo Fecha de comienzo --%>
					<tr>
						<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.fecha" /></td>
						<td>
							<logic:equal name="editarExplicacionForm" property="tipo" value="0">
								<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
								<logic:notEqual name="editarExplicacionForm" property="bloqueado" value="true">
									<img style="cursor: pointer" onclick="seleccionarFecha('document.editarExplicacionForm.fecha');" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									<img style="cursor: pointer" onclick="limpiarFecha('document.editarExplicacionForm.fecha');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
								</logic:notEqual>
							</logic:equal>
							<logic:equal name="editarExplicacionForm" property="tipo" value="1">
								<html:text disabled="true" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
							</logic:equal>
							<logic:equal name="editarExplicacionForm" property="tipo" value="2">
								<html:text disabled="true" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
							</logic:equal>
							<logic:equal name="editarExplicacionForm" property="tipo" value="3">
								<html:text disabled="true" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
							</logic:equal>
							<logic:equal name="editarExplicacionForm" property="tipo" value="4">
								<html:text disabled="true" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
							</logic:equal>
							<logic:equal name="editarExplicacionForm" property="tipo" value="5">
								<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
								<logic:notEqual name="editarExplicacionForm" property="bloqueado" value="true">
									<img style="cursor: pointer" onclick="seleccionarFecha('document.editarExplicacionForm.fecha');" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									<img style="cursor: pointer" onclick="limpiarFecha('document.editarExplicacionForm.fecha');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
								</logic:notEqual>
							</logic:equal>
						</td>
					</tr>
					<%-- Publicar --%>
					<tr>
						<bean:define scope="page" id="publicarDisabled" value="true"></bean:define>
						<logic:equal name="editarExplicacionForm" property="addPublico" scope="session" value="true">
							<bean:define scope="page" id="publicarDisabled" value="false"></bean:define>
						</logic:equal>
						<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.publicar" /></td>
						<td><html:checkbox property="publico" styleClass="botonSeleccionMultiple" disabled="<%= ((new Boolean(publicarDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"></html:checkbox></td>
					</tr>
					
					
							<tr>
								<td align="right" valign="top"><vgcutil:message key="jsp.editarexplicacion.ficha.adjuntos" /></td>
								
									<td>
										<table class="bordeFichaDatos" width="380px" cellpadding="3" cellspacing="0">
											<tr>
												
												<td colspan="3">
													<input onkeypress="javascript:titulo.focus();" type="file" accept=".xlsx,.xls,.doc, .docx,.ppt,.pptx,.pdf" name="adjunto" class="cuadroTexto">&nbsp;&nbsp; <img onClick="javascript:subirAdjunto();"
													style="cursor: pointer" src="<html:rewrite page='/componentes/visorLista/subirArchivo.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.subirarchivo.alt" />">
												</td>
												
											</tr>
											<logic:iterate id="adjunto" name="editarExplicacionForm" indexId="indiceAdjunto" scope="session" property="adjuntosExplicacion">
												<tr>
													<td align="center"><img onClick="javascript:descargarAdjunto('<bean:write name="indiceAdjunto" />');" style="cursor: pointer"
														src="<html:rewrite page='/componentes/visorLista/descargar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.descargar.alt" />"></td>
													<td align="center"><img onClick="javascript:eliminarAdjunto('<bean:write name="indiceAdjunto" />');" style="cursor: pointer"
														src="<html:rewrite page='/componentes/visorLista/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.eliminar.alt" />"></td>
													<td width="100%"><bean:write name="adjunto" property="titulo"/></td>
												</tr>
											</logic:iterate>
										</table>
									</td>
							</tr>
						
					<tr>
						<logic:equal name="editarExplicacionForm" property="tipo" value="0">
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.descripcion" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="1">
							<td valign="top" align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="25" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="2">
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros1" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="3">
							<td valign="top" align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.tipoevento" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="18" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="4">
							<td valign="top" align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.noticia" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="25" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						<logic:equal name="editarExplicacionForm" property="tipo" value="5">
							<td align="right"><vgcutil:message key="jsp.pagina.instrumentos.avance" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="8" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</logic:equal>
						
					</tr>
					<logic:equal name="editarExplicacionForm" property="tipo" value="0">
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.causas" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoCausas" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.correctivos" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoCorrectivos" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.impactos" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoImpactos" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.perspectivas" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoPerspectivas" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.url" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoUrl" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
											
						
						
					</logic:equal>
					<logic:equal name="editarExplicacionForm" property="tipo" value="2">
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros2" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoCausas" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros3" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoCorrectivos" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros4" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoImpactos" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros5" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoPerspectivas" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros6" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoUrl" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros7" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="logro1" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros8" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="logro2" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros9" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="logro3" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.tipomemo.logros10" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="logro4" rows="3" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
					    
					</logic:equal>
					<logic:equal name="editarExplicacionForm" property="tipo" value="3">
						<%-- Campo Fecha Compromiso --%>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.fecha.compromiso" /></td>
							<td>
								<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaCompromiso" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
								<img style="cursor: pointer" onclick="seleccionarFecha('document.editarExplicacionForm.fechaCompromiso');" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
								<img style="cursor: pointer" onclick="limpiarFecha('document.editarExplicacionForm.fechaCompromiso');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>
						</tr>
						<%-- Campo Fecha Real --%>
						<tr>
							<td align="right"><vgcutil:message key="jsp.editarexplicacion.ficha.fecha.real" /></td>
							<td>
								<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaReal" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
								<img style="cursor: pointer" onclick="seleccionarFecha('document.editarExplicacionForm.fechaReal');" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
								<img style="cursor: pointer" onclick="limpiarFecha('document.editarExplicacionForm.fechaReal');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>
						</tr>
						
					</logic:equal>
					
					<logic:equal name="editarExplicacionForm" property="tipo" value="5">
						<tr>
							<td align="right"><vgcutil:message key="jsp.pagina.instrumentos.observacion" /></td>
							<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoCausas" rows="8" cols="58" styleClass="cuadroTexto"></html:textarea></td>
						</tr>
					</logic:equal>
					
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarExplicacionForm" property="bloqueado" value="true">
						<logic:equal name="editarExplicacionForm" property="onlyView" value="true">
							<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
							<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
							<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
						</logic:equal>
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarExplicacionForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
