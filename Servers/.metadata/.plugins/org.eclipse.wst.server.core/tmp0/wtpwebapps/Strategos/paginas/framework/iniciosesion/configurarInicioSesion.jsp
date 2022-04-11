<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.framework.iniciosesion.titulo" />
	</tiles:put>
	
	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<tiles:put name="body" type="String">
	<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/timepicker/jquery.timepicker.js'/>"></script>
  	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/paginas/comunes/jQuery/timepicker/css/jquery.timepicker.css'/>" />
	
	<script type="text/javascript">
	
		var AUTH_SUCCESS = 0;
	  	var AUTH_INVALID_USER = 1;
	  	var AUTH_INVALID_PASSWORD = 2;
	  	var AUTH_PASSWORD_EXPIRED = 3;
	  	var AUTH_PASSWORD_MUST_CHANGE = 4;
	  	var AUTH_EXCEED_PASSWORD_RETRY_LIMIT = 5;
	  	var AUTH_LOCKED_ACCOUNT = 6;
	  	var AUTH_AUTHENTICATOR_FAILURE_USER = 7;
	  	var AUTH_AUTHENTICATOR_FAILURE_CONFIGURATION = 8;
	  	
		function guardar()
		{
			//conf contraseña
			if ((document.configurarInicioSesionForm.minimoCaracteres.value == '') || (document.configurarInicioSesionForm.minimoCaracteres.value == '0')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.minimocaracteres.requerido" />');
				return;
			}
			
			if ((document.configurarInicioSesionForm.maximoReintentos.value == '') || (document.configurarInicioSesionForm.maximoReintentos.value == '0')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.maximoreintentos.requerido" />');
				return;
			}
			
			//expiracion
			if (document.configurarInicioSesionForm.expiraContrasena[0].checked) 
			{
				if ((document.configurarInicioSesionForm.duracionExpiracion.value == '') || (document.configurarInicioSesionForm.duracionExpiracion.value == '0')) 
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.duracionexpiracion.requerido" />');
					return;
				}
				
				if ((document.configurarInicioSesionForm.advertenciaCaducidad.value == '') || (document.configurarInicioSesionForm.advertenciaCaducidad.value == '0')) 
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.advertenciacaducidad.requerido" />');
					return;
				}
			}
			
			//reutilizacion
			if ((document.configurarInicioSesionForm.reutilizacionContrasena.value == '') || (document.configurarInicioSesionForm.reutilizacionContrasena.value == '0')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.reutilizacioncontrasena.requerido" />');
				return;
			}
			
			//deshabilitar
			if ((document.configurarInicioSesionForm.deshabilitarUsuarios.value == '') || (document.configurarInicioSesionForm.deshabilitarUsuarios.value == '0')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.deshabilitarusuarios.requerido" />');
				return;
			}
			
			//rango de horas
			if ((document.configurarInicioSesionForm.txtRestriccionUsoHoraDesde.value == '') && (document.configurarInicioSesionForm.txtRestriccionUsoHoraHasta.value != '')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.restriccionuso.requerido" />');
				return;
			}
			if ((document.configurarInicioSesionForm.txtRestriccionUsoHoraDesde.value != '') && (document.configurarInicioSesionForm.txtRestriccionUsoHoraHasta.value == '')) 
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.restriccionuso.requerido" />');
				return;
			}
			
			if (document.configurarInicioSesionForm.tipoConexion.value == '1')
			{
				if (document.configurarInicioSesionForm.conexionPropiaValidacion.value == '' || document.configurarInicioSesionForm.conexionPropiaParametros.value == '')				
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.invalido" />');
					return;
				}
			}

			if (document.configurarInicioSesionForm.tipoConexion.value == '2')
			{
				if (document.configurarInicioSesionForm.conexionLDAPServidor.value == ''
						|| document.configurarInicioSesionForm.conexionLDAPDn.value == '')				
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.invalido" />');
					return;
				}
			}
			
			document.configurarInicioSesionForm.restriccionUsoHoraDesde.value = document.configurarInicioSesionForm.txtRestriccionUsoHoraDesde.value;
			document.configurarInicioSesionForm.restriccionUsoHoraHasta.value = document.configurarInicioSesionForm.txtRestriccionUsoHoraHasta.value;
	
			document.configurarInicioSesionForm.submit();
		}
		
		function esEnteroCeroCien(obtTexto) 
		{  
			var valor = obtTexto.value;
		    var valido = valor.indexOf('.') == -1;
			if (valido) 
			   valido = valor.indexOf(',') == -1;
			if (valido) 
			   valido = !isNaN(valor);
			if (valido) 
			   valido = (valor >= 0) && (valor <= 999);
	
		    return valido;
		}
		
		function verificarNumero(obtTexto) 
		{	   
		   var valido = false;
		   valido = esEnteroCeroCien(obtTexto);
		   if (!valido) 
		      obtTexto.value = obtTexto.value.substring(0, obtTexto.value.length-1);   
		}	
		
		function limpiarHoraDesde() 
		{
			document.configurarInicioSesionForm.txtRestriccionUsoHoraDesde.value = '';
		}
	
		function limpiarHoraHasta() 
		{
			document.configurarInicioSesionForm.txtRestriccionUsoHoraHasta.value = '';
		}
		
		function eventoCambioConexion()
		{
			if (document.configurarInicioSesionForm.tipoConexion.value == '0')
			{
				var tr = document.getElementById("conexion-Personal");
				tr.style.display = "none";
				
				tr = document.getElementById("conexion-Ldap");
				tr.style.display = "none";

				tr = document.getElementById("test-conexion");
				tr.style.display = "none";

				tr = document.getElementById("conexion-Obligatoria");
				tr.style.display = "none";
			}

			if (document.configurarInicioSesionForm.tipoConexion.value == '1')
			{
				var tr = document.getElementById("conexion-Ldap");
				tr.style.display = "none";

				tr = document.getElementById("test-conexion");
				tr.style.display = "none";
				
				tr = document.getElementById("conexion-Personal");
				tr.style.display = "";
				
				tr = document.getElementById("conexion-Obligatoria");
				tr.style.display = "";
			}

			if (document.configurarInicioSesionForm.tipoConexion.value == '2')
			{
				var tr = document.getElementById("conexion-Personal");
				tr.style.display = "none";

				tr = document.getElementById("conexion-Ldap");
				tr.style.display = "";

				tr = document.getElementById("conexion-Obligatoria");
				tr.style.display = "";

				tr = document.getElementById("test-conexion");
				tr.style.display = "";
			}
		}
		
		function enviarEmail()
		{
			var receptor = document.getElementById("receptor");
			var user = document.configurarInicioSesionForm.conexionMAILUser.value;
			var pass = document.configurarInicioSesionForm.conexionMAILPassword.value;
			var host = document.configurarInicioSesionForm.conexionMAILHost.value;
			var puerto = document.configurarInicioSesionForm.conexionMAILPort. value;
			
			
			
			
			if (receptor.value == '') 
			{
				alert('<vgcutil:message key="jsp.licencia.email.receptor.requerido" />');
				return;
			}
			if (!validarEmail(receptor.value))
				return;
			
			var parametros = '';
			parametros = parametros + 'receptor=' + URLEncode(receptor.value); 
			parametros = parametros + '&funcion=test';
			parametros = parametros + '&user=' +user;
			parametros = parametros + '&pass=' +pass;
			parametros = parametros + '&host=' +host;
			parametros = parametros + '&port=' +puerto;

			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/licencia/enviar/mail" />?' + parametros, document.configurarInicioSesionForm.respuesta, 'onEnviarEmail()');
		}
		
		function onEnviarEmail()
		{
			var respuesta = document.configurarInicioSesionForm.respuesta.value;
			if (respuesta == 10000)
				alert('<vgcutil:message key="jsp.licencia.email.enviado" />');
			else if (respuesta == 10020)
				alert('<vgcutil:message key="mail.send.noconf" />');
			else if (respuesta == 10006)
				alert('<vgcutil:message key="mail.send.error.autenticacion" />');
			else 
				alert('<vgcutil:message key="mail.send.inicio.error" />');
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
		
		function testConexion()
		{
			var parametros = '';
			parametros = parametros + '?accion=testConexion';
			parametros = parametros + '&servidor=' + URLEncode(document.configurarInicioSesionForm.conexionLDAPServidor.value); 
			parametros = parametros + '&puerto=' + URLEncode(document.configurarInicioSesionForm.conexionLDAPPuerto.value);
			parametros = parametros + '&dn=' + URLEncode(document.configurarInicioSesionForm.conexionLDAPDn.value);
			var campo = document.getElementById("txtTestLdapUser");
			if (campo != null && campo.value != "")
				parametros = parametros + '&user=' + URLEncode(campo.value);
			else
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.usuario.requerido" />');
				return;
			}
			campo = document.getElementById("txtTestLdapPwd");
			if (campo != null && campo.value != "")
				parametros = parametros + '&pwd=' + URLEncode(campo.value);
			else
			{
				alert('<vgcutil:message key="jsp.framework.iniciosesion.password.requerido" />');
				return;
			}
			
			ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/iniciosesion/configurarInicioSesion" />' + parametros, document.configurarInicioSesionForm.respuesta, 'onTestConexion()');
		}
		
		function onTestConexion()
		{
			var respuesta = document.configurarInicioSesionForm.respuesta.value;
			if (respuesta == AUTH_AUTHENTICATOR_FAILURE_USER)
				alert('<vgcutil:message key="error.user.ldap.not.exist" />');
			else if (respuesta == AUTH_AUTHENTICATOR_FAILURE_CONFIGURATION)
				alert('<vgcutil:message key="error.user.ldap.failure.conetion" />');
			else if (respuesta == AUTH_INVALID_USER)
				alert('<vgcutil:message key="error.user.ldap.invalid.user.system" />');
			else if (respuesta == AUTH_SUCCESS)
				alert('<vgcutil:message key="error.user.ldap.sucess.user" />');
			else
				alert(respuesta);
		}
		
		$(function() 
		{
			$('#txtRestriccionUsoHoraDesde').timepicker({'timeFormat': 'h:i A' });
		});
	
		$(function() 
		{
			$('#txtRestriccionUsoHoraHasta').timepicker({'timeFormat': 'h:i A' });
		});
			
		// validar: expiracion si aplica, duracion y advertencia deben tener valor
		// todos los demas campos deben tener numero y ademas mayor a cero	
	</script>
	
	<jsp:include flush="true" page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<html:form action="/framework/iniciosesion/configurarInicioSesion?accion=Guardar" method="POST" styleClass="formaHtmlCompleta">
			<html:hidden property="respuesta" />
			
			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.iniciosesion.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<html:hidden property="restriccionUsoHoraDesde" />
				<html:hidden property="restriccionUsoHoraHasta" />
			
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.configcontrasena" /></b></td>
					</tr>

					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.minimocaracteres" />
						</td>
						<td>
							<html:text property="minimoCaracteres" size="5" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />
						</td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.maximoreintentos" />
						</td>
						<td>
							<html:text property="maximoReintentos" size="5" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />
						</td>
					</tr>				
				</table>
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.expiracioncontrasena" /></b></td>
					</tr>

					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.expiracontrasena" />
						</td>
						<td>
							<html:radio property="expiraContrasena" value="1" /> <vgcutil:message key="comunes.si" />
							&nbsp;&nbsp;&nbsp;
							<html:radio property="expiraContrasena" value="0" /> <vgcutil:message key="comunes.no" />							
						</td>
					</tr>					
					<tr>
						<td align="left" width="350px" style="padding-left:10px;"><vgcutil:message key="jsp.framework.iniciosesion.periodoexpiracion" /></td>
						<td><html:select property="periodoExpiracion" styleClass="cuadroTexto">
							<html:option value="0"><vgcutil:message key="jsp.framework.frecuencia.diaria" /></html:option>
							<html:option value="1"><vgcutil:message key="jsp.framework.frecuencia.semanal" /></html:option>
							<html:option value="2"><vgcutil:message key="jsp.framework.frecuencia.mensual" /></html:option>
							<html:option value="3"><vgcutil:message key="jsp.framework.frecuencia.anual" /></html:option>
						</html:select></td>
					</tr>
					
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.duracionexpiracion" />
						</td>
						<td>
							<html:text property="duracionExpiracion" size="5" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />
						</td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.advertenciacaducidad" />
						</td>
						<td>
							<html:text property="advertenciaCaducidad" size="5" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />
							&nbsp;&nbsp;
							<vgcutil:message key="jsp.framework.iniciosesion.dias" />
						</td>
					</tr>				
				</table>
				
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.reutilizacioncontrasena" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.permitirreusocontrasena" />
						</td>
						<td>
							<html:text property="reutilizacionContrasena" size="5" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />
							&nbsp;&nbsp;
							<vgcutil:message key="jsp.framework.iniciosesion.cambioscontrasena" />
						</td>
					</tr>				
				</table>
				
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.tipocontrasena" /></b></td>
					</tr>
					<tr>
						<td colspan="2" style="padding-left:10px;">
							
							<html:radio property="tipoContrasena" value="0" /> <vgcutil:message key="jsp.framework.iniciosesion.tipocontrasena.debil" />
							<br>
							<html:radio property="tipoContrasena" value="1" /> <vgcutil:message key="jsp.framework.iniciosesion.tipocontrasena.media" />
							<br>
							<html:radio property="tipoContrasena" value="2" /> <vgcutil:message key="jsp.framework.iniciosesion.tipocontrasena.fuerte" />
						</td>
					</tr>
				</table>
				
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.deshabilitarusuarios" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.numerodedias" />
						</td>
						<td>
							<html:text property="deshabilitarUsuarios" size="5" maxlength="3" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this);" />							
						</td>
					</tr>
				</table>
				
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.restriccionuso" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.restriccionusodias" />
						</td>
						<td>				
							<html:checkbox property="restriccionUsoDiaLunes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.lunes" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaMartes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.martes" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaMiercoles" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.miercoles" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaJueves" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.jueves" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaViernes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.viernes" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaSabado" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.sabado" />
							&nbsp;&nbsp;&nbsp;
							<html:checkbox property="restriccionUsoDiaDomingo" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.domingo" />							
						</td>
					</tr>
					<tr>						
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.restriccionusohoras" />
						</td>
						<td>
							<input type="text" id="txtRestriccionUsoHoraDesde" value="<bean:write name="configurarInicioSesionForm" property="restriccionUsoHoraDesde" />" size="12" maxlength="15" class="cuadroTexto" readonly="true">
							<img style="cursor:pointer" onclick="limpiarHoraDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							&nbsp;&nbsp;a&nbsp;&nbsp;
							<input type="text" id="txtRestriccionUsoHoraHasta" value="<bean:write name="configurarInicioSesionForm" property="restriccionUsoHoraHasta" />" size="12" maxlength="15" class="cuadroTexto" readonly="true">
							<img style="cursor:pointer" onclick="limpiarHoraHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
						</td>
					</tr>
				</table>
				
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.habilitarauditoria" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.auditoria" />
						</td>
						<td>
							<html:radio property="habilitarAuditoria" value="1" /> <vgcutil:message key="comunes.si" />
							&nbsp;&nbsp;&nbsp;
							<html:radio property="habilitarAuditoria" value="0" /> <vgcutil:message key="comunes.no" />								
						</td>
					</tr>
				</table>

				<%-- Mail --%>
				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.configurarmail" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.configurarmail.host" />
						</td>
						<td>
							<html:text property="conexionMAILHost" size="30" styleClass="cuadroTexto" />
						</td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.configurarmail.port" />
						</td>
						<td>
							<html:text property="conexionMAILPort" size="30" styleClass="cuadroTexto" />
						</td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.configurarmail.user" />
						</td>
						<td>
							<html:text property="conexionMAILUser" size="30" styleClass="cuadroTexto" />
						</td>
						<td colspan="3" align="left"><b><vgcutil:message key="jsp.framework.iniciosesion.configurarmail.test" /></b></td>
					</tr>
					<tr>
						<td width="350px" style="padding-left:10px;">
							<vgcutil:message key="jsp.framework.iniciosesion.configurarmail.password" />
						</td>
						<td>
							<html:password styleClass="cuadroTexto" property="conexionMAILPassword" size="30" />
						</td>
						<td align="left"><vgcutil:message key="jsp.licencia.email.receptor" /></td>
						<td><input id="receptor" type="text" class="cuadroTexto" size="50" value="" /></td>
						<td align="left" valign="top">
							<input type="button" style="width:80px;height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.licencia.email.enviar" />" onclick="enviarEmail();">
						</td>
					</tr>
				</table>

				<br>
				<table class="contenedorBotonesSeleccion" width="100%">
					<tr>
						<td colspan="2"><b><vgcutil:message key="jsp.framework.iniciosesion.tipoconexion" /></b></td>
					</tr>
					<tr>
						<td colspan="2">
							<table class="bordeFichaDatos">
								<tr>
									<td align="left" width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.conexion" />
									</td>
									<td>
										<html:select property="tipoConexion" styleClass="cuadroTexto" onchange="eventoCambioConexion()">
											<html:option value="0"><vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.propia" /></html:option>
											<html:option value="1"><vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.personal" /></html:option>
											<html:option value="2"><vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap" /></html:option>
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="conexion-Personal" style="display:none">
						<td colspan="2">
							<table class="bordeFichaDatos">
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.propia.validacion" />
									</td>
									<td>
										<html:text property="conexionPropiaValidacion" size="30" styleClass="cuadroTexto" />
									</td>
									<td>
										*
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.propia.parametros" />
									</td>
									<td>
										<html:text property="conexionPropiaParametros" size="30" styleClass="cuadroTexto" />
									</td>
									<td>
										*
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="conexion-Ldap" style="display:none">
						<td colspan="2" width="630px">
							<table class="bordeFichaDatos">
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.servidor" />
									</td>
									<td>
										<html:text property="conexionLDAPServidor" size="30" styleClass="cuadroTexto" />
									</td>
									<td>
										*
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.puerto" />
									</td>
									<td>
										<html:text property="conexionLDAPPuerto" size="30" styleClass="cuadroTexto" />
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.dn" />
									</td>
									<td>
										<html:text property="conexionLDAPDn" size="30" styleClass="cuadroTexto" />
									</td>
									<td>
										*
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.user" />
									</td>
									<td colspan="2">
										<html:text property="conexionLDAPUser" size="30" styleClass="cuadroTexto" />
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.test.user" />
									</td>
									<td colspan="2">
										<input type="text" id="txtTestLdapUser" value="" size="12" maxlength="15" class="cuadroTexto">
									</td>
								</tr>
								<tr>
									<td width="350px" style="padding-left:10px;">
										<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.ldap.test.pwd" />
									</td>
									<td colspan="2">
										<input type="password" id="txtTestLdapPwd" value="" size="12" maxlength="15" class="cuadroTexto">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="conexion-Obligatoria" style="display:none">
						<td colspan="2" style="padding-left:367px; width: 150px; text-align:left;">
							<vgcutil:message key="jsp.framework.iniciosesion.tipoconexion.obligatoria" />
						</td>
					</tr>
					<tr id="test-conexion" style="display:none">
						<td colspan="2" style="padding-left:367px; width: 150px; text-align:left;">
							<input type="button" style="width:150px; height:20px" class="cuadroTexto" value="<vgcutil:message key="jsp.licencia.test.conexion" />" onclick="testConexion();">
						</td>
					</tr>
				</table>
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.guardar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		
		<script>
			eventoCambioConexion();
		</script>
		
	</tiles:put>

</tiles:insert>