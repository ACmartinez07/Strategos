<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (09/07/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">
	
	<style type="text/css">
	.debil 
	{
		background-color: Red; 
	  	color:Black; 
	  	font-weight: Bold;
	}
	
	.medio 
	{
		background-color: Yellow; 
	  	color:Black; 
	  	font-weight: Bold;
	}
	
	.fuerte 
	{
		background-color: Green; 
	  	color:Black; 
	  	font-weight: Bold;
	}
	</style>
	
		<script type="text/javascript">

			function ltrim(str, filter) 
			{
				var i = str.length;
				filter || (filter = '');
				for (var k = 0; k < i && filtering(str.charAt(k), filter); k++);
				return str.substring(k, i);
			}
			
			function rtrim(str, filter) 
			{
				filter || (filter = '');
			  	for (var j = str.length - 1; j >= 0 && filtering(str.charAt(j), filter); j--);          
				return str.substring(0, j + 1);
			}
			
			function trim(str, filter) 
			{
				if (typeof(filter) == "undefined")
					filter = "";
					
				filter || (filter = '');
				return ltrim(rtrim(str, filter), filter);
			}
			
			function filtering(charToCheck, filter) 
			{
				filter || (filter = " \t\n\r\f");
				return (filter.indexOf(charToCheck) != -1);
			}		
			
			function excluirCaracter()
			{
				var patron = /[\"'-,#&]/;
				var respuesta = true;
				<logic:notEmpty scope="session" name="cliente">
					<logic:equal scope="session" name="cliente" value="BDV">
						if (patron.test(document.cambiarClaveUsuarioForm.pwd.value) || document.cambiarClaveUsuarioForm.pwd.value.indexOf("-") != -1)
							respuesta = false;
					</logic:equal>
				</logic:notEmpty>
				if (!respuesta)
					alert("Existe caracteres no permitidos en esta ficha, tales como ' - , # & \"");
				else
				{
					var password = trim(document.cambiarClaveUsuarioForm.pwd.value.replace(" ", ""));
					if (password != document.cambiarClaveUsuarioForm.pwd.value)
					{
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdconblanco" />');
						respuesta = false;
					}
				}
				
				return respuesta;
			}
	
			function validar(forma) 
			{
				if (validateCambiarClaveUsuarioForm(forma) && excluirCaracter()) 
				{
					var password = document.cambiarClaveUsuarioForm.pwd.value;
					if (password.length < document.cambiarClaveUsuarioForm.minimoCaracteres.value)
					{
						var mensaje = '<vgcutil:message key="jsp.ingreso.minimocaracteres" />';
						mensaje = mensaje.replace("XXX", document.cambiarClaveUsuarioForm.minimoCaracteres.value);
						alert(mensaje);
						return false;
					}
					
					if (document.cambiarClaveUsuarioForm.pwd.value == document.cambiarClaveUsuarioForm.usuarioLogin.value)
				  	{
				  		alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdigualuser" />');
				  		return false;
				  	}
					
					if (document.cambiarClaveUsuarioForm.pwd.value != document.cambiarClaveUsuarioForm.pwdConfirm.value) 
					{
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdconfirm" />');
						return false;
					}
					
					if (document.cambiarClaveUsuarioForm.pwd.value.toLowerCase().indexOf("ñ") > -1)
					{
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.caracter.nopermitido" />');
						return false;
					}
					
					if (document.cambiarClaveUsuarioForm.tipoContraIngresada.value < document.cambiarClaveUsuarioForm.tipoContrasena.value)
				  	{
				  		if (document.cambiarClaveUsuarioForm.tipoContrasena.value == 0)
							alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.debil" />');
				  		if (document.cambiarClaveUsuarioForm.tipoContrasena.value == 1)
				  			alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.medio" />');
				  		if (document.cambiarClaveUsuarioForm.tipoContrasena.value == 2)
				  			alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.fuerte" />');
				  		return false;
				  	}
					
					return true;
				} 
				else 
					return false;
			}
			
			function getPasswordStrength(password)
			{
				var reDigits = /([0-9])/;
				var reLetters = /([A-Z]|[a-z])/;
				var reSymbols = /[^A-Za-z0-9]/;
				var OKDigits = reDigits.test(password);
				var OKLetters = reLetters.test(password);
				var OKSymbols = reSymbols.test(password);
				var Strength = "";

				if (OKDigits && OKLetters && OKSymbols)
					Strength = "Strong";
				else if (OKDigits && OKLetters && !OKSymbols)
					Strength = "Medium";
				else if (OKDigits && !OKLetters && OKSymbols)
					Strength = "Medium";
				else if (!OKDigits && OKLetters && OKSymbols)
					Strength = "Medium";
				else
				  	Strength = "Weak";
				return Strength;
			}
			  
			function txtPasswordChanged()
			{
				if (document.cambiarClaveUsuarioForm.pwd.value != "")
			  	{
			  		var passwordStrength = getPasswordStrength(document.cambiarClaveUsuarioForm.pwd.value);
			  		if (passwordStrength == "Strong")
			  		{
			  			document.getElementById("lblPasswordType").innerHTML = "Fuerte";
			  			document.getElementById("tdPasswordType").className = "fuerte";
			  			document.cambiarClaveUsuarioForm.tipoContraIngresada.value = "2";
			  		}
			  		else if (passwordStrength == "Medium")
			  		{
			  			document.getElementById("lblPasswordType").innerHTML = "Medio";
			  			document.getElementById("tdPasswordType").className = "medio";
			  			document.cambiarClaveUsuarioForm.tipoContraIngresada.value = "1";
			  		}
			  		else if (passwordStrength == "Weak")
			  		{
			  			document.getElementById("lblPasswordType").innerHTML = "Débil";
			  			document.getElementById("tdPasswordType").className = "debil";
			  			document.cambiarClaveUsuarioForm.tipoContraIngresada.value = "0";
			  		}
			  	}
			  	else
			  	{
			  		document.getElementById("lblPasswordType").innerHTML = "";
		  			document.getElementById("tdPasswordType").className = "";
			  		document.cambiarClaveUsuarioForm.tipoContraIngresada.value = "";
			  	}
			}
			 
			function guardar() 
			{
				if (validar(self.document.cambiarClaveUsuarioForm)) 
				{
					var parametros = 'desdeLogin=' + document.cambiarClaveUsuarioForm.desdeLogin.value;
					parametros = parametros + '&usuarioId=' + document.cambiarClaveUsuarioForm.usuarioId.value;
					parametros = parametros + '&pwdActual=' + document.cambiarClaveUsuarioForm.pwdActual.value;
					parametros = parametros + '&pwd=' + document.cambiarClaveUsuarioForm.pwd.value;
					parametros = parametros + '&reutilizacionContrasena=' + document.cambiarClaveUsuarioForm.reutilizacionContrasena.value;
					
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/usuarios/guardarClaveUsuario" />?' + parametros, document.cambiarClaveUsuarioForm.respuesta, 'onSalvar()');
				}
			}
			
			function onSalvar()
			{
				var respuesta = document.cambiarClaveUsuarioForm.respuesta.value.split("|");
				switch(respuesta[1])
				{
					case "10001":
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdincorrecto" />');
						break; 
					case "10003":
						var mensaje = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.dupkey" />';
						mensaje = mensaje.replace("XXX", respuesta[0]);
						alert(mensaje);
						break; 
					case "99999":
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdusado" />');
						break; 
					case "10000": 						
						if (document.cambiarClaveUsuarioForm.desdeLogin.value == "true")
						{
							alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.successdesdelogin" />');
							window.location.href='<sslext:rewrite action="logout"/>';
						}
						else
						{
							var mensaje = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.success" />';
							mensaje = mensaje.replace("XXX", respuesta[0]);
							alert(mensaje);
							
							window.document.cambiarClaveUsuarioForm.action = '<html:rewrite action="/framework/usuarios/cancelarGuardarClaveUsuario"/>?cancelar=true';
							window.document.cambiarClaveUsuarioForm.submit();	
						}
						break;   
				}
			}
			
			function cancelarGuardar() 
			{
				if (document.cambiarClaveUsuarioForm.desdeLogin.value == "true")
					window.location.href='<sslext:rewrite action="logout"/>';
				else
				{
					window.document.cambiarClaveUsuarioForm.action = '<html:rewrite action="/framework/usuarios/cancelarGuardarClaveUsuario"/>?cancelar=true';
					window.document.cambiarClaveUsuarioForm.submit();	
				}
			}
			
			function ejecutarPorDefecto(e, id) 
			{
				if(e.keyCode==13) 
					guardar();
				
				if (id == "pwd")
					txtPasswordChanged();
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.cambiarClaveUsuarioForm.pwdActual" />
	
		<sslext:form action="/framework/usuarios/guardarClaveUsuario" focus="pwdActual">

			<html:hidden property="usuarioId" />
			<html:hidden property="desdeLogin" /> 
			<html:hidden property="respuesta" /> 
			<input type="hidden" name="minimoCaracteres" value="<bean:write name="com.visiongc.app.web.configiniciosesion" property="minimoCaracteres" />">
			<input type="hidden" name="tipoContrasena" value="<bean:write name="com.visiongc.app.web.configiniciosesion" property="tipoContrasena" />">
			<input type="hidden" name="reutilizacionContrasena" value="<bean:write name="com.visiongc.app.web.configiniciosesion" property="reutilizacionContrasena" />">
			<input type="hidden" name="tipoContraIngresada" value="" >
			<input type="hidden" name="usuarioLogin" value="<bean:write name="usuario" property="UId" />">
			
			<vgcinterfaz:contenedorForma width="450px" height="300px" >

				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>


				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%">

					<tr>
						<td align="right"><vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.uid" /></td>
						<td><b><bean:write name="usuario" property="UId" /></b></td>
					</tr>

					<tr>
						<td align="right"><vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.fullname" /></td>
						<td><b><bean:write name="usuario" property="fullName" /></b></td>
					</tr>
					
					<tr>
						<td colspan="2"><hr width="100%"></td>
					</tr>
					
					<tr>
						<td align="right"><vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.passwordactual" /></td>
						<td><html:password styleClass="cuadroTexto" property="pwdActual" size="30" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.passwordnuevo" /></td>
						<td><html:password styleClass="cuadroTexto" property="pwd" onchange="ejecutarPorDefecto(event, this.name)" size="30" maxlength="50" /></td>
						<td id="tdPasswordType"><span id="lblPasswordType" name="lblPasswordType"></span></td>
						
					</tr>

					<tr>
						<td align="right"><vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.pwdconfirm" /></td>
						<td><html:password styleClass="cuadroTexto" property="pwdConfirm" size="30" maxlength="50" /></td>
					</tr>
					
					<tr>
						<td colspan="2" style="padding-top: 10px;"><b><span id="lblPwdLen" name="lblPwdLen"></span></b></td>
					</tr>
					<tr>
						<td colspan="2"><b><span id="lblPwdTypeDesc" name="lblPwdTypeDesc"></span></b></td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="cambiarClaveUsuarioForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelarGuardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</sslext:form>
		<script type="text/javascript">
			var mensaje = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.minimocaracteres" />';
			mensaje = mensaje.replace("XXX", document.cambiarClaveUsuarioForm.minimoCaracteres.value);
			
			document.getElementById("lblPwdLen").innerHTML = mensaje;
			
			switch(document.cambiarClaveUsuarioForm.tipoContrasena.value)
			{
				case "0":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.debil" />';
					break;
				case "1":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.media" />';
					break;
				case "2":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.fuerte" />';
					break;
			}
		</script>
		
		<html:javascript formName="cambiarClaveUsuarioForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>

</tiles:insert>
