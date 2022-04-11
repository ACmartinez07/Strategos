<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.acerca.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			function cancelar() 
			{								
				this.close();
			}
			
			function generarEmail()
			{
				var tr = document.getElementById("licencia");
				tr.style.display = "none";

				var tr = document.getElementById("email");
				tr.style.display = "";
			}
			
			function generarLicencia()
			{
				var tr = document.getElementById("email");
				tr.style.display = "none";

				var tr = document.getElementById("licencia");
				tr.style.display = "";
			}
			
			function enviarEmail()
			{
				var receptor = document.getElementById("receptor");
				var asunto = document.getElementById("asunto");
				var cuerpo = document.getElementById("cuerpo");
				var respuesta = document.getElementById("respuesta");

				if (receptor.value == '') 
				{
					alert('<vgcutil:message key="jsp.licencia.email.receptor.requerido" />');
					return;
				}
				if (asunto.value == '') 
				{
					alert('<vgcutil:message key="jsp.licencia.email.asunto.requerido" />');
					return;
				}
				if (cuerpo.value == '') 
				{
					alert('<vgcutil:message key="jsp.licencia.email.cuerpo.requerido" />');
					return;
				}
				if (!validarEmail(receptor.value))
					return;
				
				var parametros = '';
				parametros = parametros + 'receptor=' + URLEncode(receptor.value); 
				parametros = parametros + '&asunto=' + URLEncode(asunto.value);

				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/licencia/enviar/mail" />?' + parametros, respuesta, 'onEnviarEmail()');
			}
			
			function onEnviarEmail()
			{
				var respuesta = document.getElementById("respuesta").value;
				if (respuesta == 10000)
				{
					alert('<vgcutil:message key="jsp.licencia.email.enviado" />');
					cancelar();
				}
				else if (respuesta == 10020)
					alert('<vgcutil:message key="mail.send.noconf" />');
				else if (respuesta == 10006)
					alert('<vgcutil:message key="mail.send.error.autenticacion" />');
				else
				{
					var respuesta = confirm('<vgcutil:message key="mail.send.error" />');
					if (respuesta)
					{
						var tr = document.getElementById("fileEmail");
						tr.style.display = "none";
						
						var tr = document.getElementById("fileSave");
						tr.style.display = "";
					}						
				}
			}
			
			function validarEmail(email) 
			{
				expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			    if ( !expr.test(email) )
		    	{
			        alert('<vgcutil:message key="jsp.licencia.email.invalido1" />' + ' ' + email + ' ' + '<vgcutil:message key="jsp.licencia.email.invalido2" />');
			    	return false;
		    	}
			    
			    return true;
			}			
			
			function guardarLicencia(obj)
			{
				var file = obj.value;
			    var fileName = file.split("\\");
			    var archivo = fileName[fileName.length-1];
			    var extension = archivo.substring(archivo.indexOf('.') + 1, archivo.length); 
			    
				if (extension.toUpperCase() != 'SLN')
				{
					alert('<vgcutil:message key="jsp.licencia.licencia.sln.noexiste" /> ');
					limpiarArchivoLicencia();
					return
				}

				if (file == '') 
				{
					alert('<vgcutil:message key="jsp.licencia.codigo.requerido" />');
					limpiarArchivoLicencia();
					return;
				}
				
				document.licenciaForm.action = '<html:rewrite action="/licencia/salvar"/>';
				document.licenciaForm.submit();
			}
			
			function limpiarArchivoLicencia()
			{
				document.getElementById("uploadFile_div").innerHTML=document.getElementById("uploadFile_div").innerHTML;
			}
			
			function onGuardarLicencia()
			{
				alert('<vgcutil:message key="jsp.licencia.licencia.salvada" />');
				cancelar();
			}
			
			function guardarArchivo(obj)
			{
				var fso = new ActiveXObject("Scripting.FileSystemObject"); 
				var f1 = fso.OpenTextFile(obj.value, 2, true); 
				f1.WriteLine(document.getElementById("cuerpo").value);
				f1.Close(); 				

				onGuardarArchivo();
			}
			
			function onGuardarArchivo()
			{
				alert('<vgcutil:message key="jsp.licencia.licencia.guardada" />');
				cancelar();
			}
			
		</script>
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<html:form action="licencia" styleClass="formaHtml" enctype="multipart/form-data" method="POST">

			<html:hidden property="respuesta" />
			<html:hidden property="status" />

			<vgcinterfaz:contenedorForma width="426px" height="420px" bodyCellpadding="10" marginTop="5px" >
				
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.acerca.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
				<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
				<bean:define toScope="page" id="estiloSuperior_left" value=""></bean:define>
				<bean:define toScope="page" id="es_Demostracion" value="false"></bean:define>
				<%
					com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
					if (estilos != null)
					{
						hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
						estiloSuperior = estilos.getEstiloSuperiorForma();
						estiloSuperior_left = estilos.getEstiloSuperiorFormaIzquierda();
					}
					else
						hayEstilo = "false";
					es_Demostracion = new com.visiongc.framework.web.struts.actions.WelcomeAction().esLicenciaDemostracion().toString().toLowerCase();
				%>
				<bean:define toScope="page" id="es_Demostracion" value="<%=es_Demostracion%>"></bean:define>
				<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
					<bean:define toScope="page" id="estiloSuperior_left" value="<%=estiloSuperior_left%>"></bean:define>
				</logic:equal>
	
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
					<tr>
						<td colspan="2">
						<table class="bordeFichaDatosIngreso" cellpadding="3" cellspacing="0">
							<logic:equal scope="page" name="hayEstilo" value="true">
								<logic:notEmpty scope="page" name="estiloSuperior">
									<logic:equal scope="page" name="es_Demostracion" value="true">
										<tr>
											<td colspan="2" style="<%=estiloSuperior%>"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
										</tr>
									</logic:equal>
									<tr>
										<td colspan="2" style="<%=estiloSuperior%>"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
									</tr>
									<tr>
										<td colspan="2" style="<%=estiloSuperior%>"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
									</tr>
								</logic:notEmpty>
								<logic:empty scope="page" name="estiloSuperior">
									<logic:equal scope="page" name="es_Demostracion" value="true">
										<tr>
											<td colspan="2" class="barraSuperiorForma"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
										</tr>
									</logic:equal>
									<tr>
										<td colspan="2" class="barraSuperiorForma"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
									</tr>
									<tr>
										<td colspan="2" class="barraSuperiorForma"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
									</tr>
								</logic:empty>
							</logic:equal>
							<logic:equal scope="page" name="hayEstilo" value="false">
								<logic:equal scope="page" name="es_Demostracion" value="true">
									<tr>
										<td colspan="2" class="barraSuperiorForma"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
									</tr>
								</logic:equal>
								<tr>
									<td colspan="2" class="barraSuperiorForma"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
								</tr>
								<tr>
									<td colspan="2" class="barraSuperiorForma"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
								</tr>
							</logic:equal>
						</table>
					</tr>
					<logic:notEmpty scope="session" name="licencia">
						<tr>
							<td colspan="2">
								<hr width="100%">
							</td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.acerca.licencia.company" /></td>
							<td><input type="text" class="cuadroTexto" size="40" disabled value="<bean:write scope='session' name='licencia' property='companyName' />" /></td>
						</tr>
						<tr>
							<td align="right"><vgcutil:message key="jsp.licencia.serial" /></td>
							<td><input type="text" class="cuadroTexto" size="45" disabled value="<bean:write scope='session' name='licencia' property='serial' />" /></td>
						</tr>
						<logic:notEqual scope="session" name="licencia" property="expiracion" value="">
							<tr>
								<td align="right"><vgcutil:message key="jsp.licencia.expiracion" /></td>
								<td><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='expiracion' />" /></td>
							</tr>
							<tr>
								<td align="right"><vgcutil:message key="jsp.licencia.dias.vencimiento" /></td>
								<td><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='expiracionDias' />" /></td>
							</tr>
						</logic:notEqual>
						<tr>
							<td align="right"><vgcutil:message key="jsp.licencia.tipo" /></td>
							<td><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='tipo' />" /></td>
						</tr>
						<logic:notEqual scope="session" name="licencia" property="tipo" value="Full Licenciamiento">
							<tr>
								<td align="right"><vgcutil:message key="jsp.licencia.numero.usuarios" /></td>
								<td><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='numeroUsuarios' />" /></td>
							</tr>
						</logic:notEqual>
						<tr>
							<td colspan="2">
								<hr width="100%">
							</td>
						</tr>
	
						<%-- Email --%>
						<tr id="email" style="display:none">
							<td colspan="2">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
									<tr>
										<td align="right"><vgcutil:message key="jsp.licencia.email.receptor" /></td>
										<td><input id="receptor" type="text" class="cuadroTexto" size="50" value="" /></td>
									</tr>
									<tr>
										<td align="right"><vgcutil:message key="jsp.licencia.email.asunto" /></td>
										<td><input id="asunto" type="text" class="cuadroTexto" size="50" value="" /></td>
									</tr>
									<tr id="fileEmail">
										<td colspan="2">
											<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
												<tr>
													<td align="right"><vgcutil:message key="jsp.licencia.email.cuerpo" /></td>
													<td><input id="cuerpo" type="text" class="cuadroTexto" size="50" disabled value="<bean:write scope='session' name='licencia' property='cmaxc' />" /></td>
												</tr>
												<tr>
													<td colspan="2" align="right" valign="top">
														<input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.licencia.email.enviar" />" onclick="enviarEmail();">
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr id="fileSave" style="display:none">
										<td colspan="2">
											<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
												<tr>
													<td align="right"><vgcutil:message key="jsp.licencia.direccion" /></td>
													<td>
														<input size="35" type="file" id="archivo" name="codigo" class="cuadroTexto" title="<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.archivo" />" onchange="guardarArchivo(this);"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
	
						<%-- licencia --%>
						<tr id="licencia" style="display:none">
							<td colspan="2">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
									<tr>
										<td align="right"><vgcutil:message key="jsp.licencia.codigo" /></td>
										<td>
											<div id="uploadFile_div" name="uploadFile_div" height="100%" width="100%">
												<input size="38" type="file" id="codigo" name="codigo" class="cuadroTexto" title="<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.archivo" />" onchange="guardarLicencia(this);"/>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
					</logic:notEmpty>
				</table>
	
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarEmail();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.generar.email" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/guardarBlanco.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarLicencia();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.generar.clave" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			<logic:equal name="licenciaForm" property="status" value="0">
				generarLicencia();
				onGuardarLicencia();
			</logic:equal>
			
			<logic:equal name="licenciaForm" property="status" value="1">
				generarLicencia();
				alert('<vgcutil:message key="jsp.licencia.licencia.guardada.error" />');
			</logic:equal>
		</script>
		
	</tiles:put>

</tiles:insert>
