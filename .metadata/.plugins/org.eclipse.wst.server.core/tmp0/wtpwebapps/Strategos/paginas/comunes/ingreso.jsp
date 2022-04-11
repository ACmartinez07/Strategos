<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ page import="java.util.Random"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>


<%-- Modificado por: Kerwin Arias (10/09/2012) --%>
<%
	Random gen = new Random();

	int intGen = gen.nextInt();
	if (intGen < 0) 
		intGen = -intGen;
	String challenge = String.valueOf(intGen);
	intGen = gen.nextInt();
	if (intGen < 0) 
		intGen = -intGen;
	challenge = String.valueOf(intGen) + "." + challenge;
%>

<tiles:insert definition="doc.loginLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.ingreso.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/md5.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/base64.js'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var IE = navigator.appName == "Microsoft Internet Explorer";
            IE = !!(navigator.userAgent.match(/Trident/) && !navigator.userAgent.match(/MSIE/));
            var chrome = (navigator.userAgent.toLowerCase().indexOf('chrome') > -1);
            var _cerrarVentana = true;
            
			function validar(forma) 
			{			
				if (validateLogonForm(forma)) 
				{
					if (document.logonForm.password.value == "")
					{
						alert("El passowrd no puede estar en blanco");
						return false;
					}
					else
					{
						var password = document.logonForm.password.value;
						var hash = password;
						hash = MD5(document.logonForm.challenge.value + password);		
						var encripta=Base64.encode(password);	//encripto la cadena
						document.logonForm.password.value=hash;
						document.logonForm.pwdEncript.value=encripta;
						document.logonForm.tipoExplorer.value = _typeExplorer;
						
						return true;				
					}
				} 
				else 
					return false;
			}
			
			// En esta página no se debe usar el sistema de advertencia de tiempo de sesión agotándose
			useSessionTimeoutWarning = false;
			
			function conectar() 
			{
				if (validar(document.logonForm)) 
				{
					_cerrarVentana = false;
					document.logonForm.action='<sslext:rewrite action="logon" />';
					document.logonForm.action='<html:rewrite action="logon" />';
					document.logonForm.submit();
				}
			}
									
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					conectar();
			}
			
			function cerrarAplicacion()
			{
				if (_cerrarVentana)
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/logout" />?closeApp=true', null, 'closedApp()');
			}
			
			function init()
			{
				if (IE && !VerifyActiveX())
				{
					var span = document.getElementById("spanMessage");
					if (span != null)
					{
						span.innerHTML = '<vgcutil:message key="jsp.ingreso.not.permite.activex" />';
						span.style.color = "red";
						span.style.fontSize = "12pt"; 
						span.style.fontWeight = "bold";
					}
					document.getElementById("trLogin").style.display = "none";
					document.getElementById("trBotones").style.display = "none";
					document.getElementById("trMessage").style.display = "";
				} 
				else if (!IE && !chrome)
				{
					var span = document.getElementById("spanMessage");
					if (span != null)
					{
						span.innerHTML = '<vgcutil:message key="jsp.ingreso.explorer.not.permite" />';
						span.style.color = "red";
						span.style.fontSize = "12pt"; 
						span.style.fontWeight = "bold";
					}
					document.getElementById("trLogin").style.display = "none";
					document.getElementById("trBotones").style.display = "none";
					document.getElementById("trMessage").style.display = "";
				}
				
				return;
				var el = document.documentElement
				, rfs = // for newer Webkit and Firefox
			           el.requestFullScreen
			        || el.webkitRequestFullScreen
			        || el.mozRequestFullScreen
			        || el.msRequestFullScreen;
				
				if(typeof rfs!="undefined" && rfs)
					rfs.call(el);
				else if(typeof window.ActiveXObject !== "undefined") // Older IE.
				{
					var isFullScreen = document.body.clientHeight == screen.height && document.body.clientWidth == screen.width;
					if (!isFullScreen)
					{
					  	var wscript = new ActiveXObject("WScript.Shell");
					  	if (wscript!=null) 
							wscript.SendKeys("{F11}");
					}
				}				
			}
		</script>
		
		
		<html:javascript formName="logonForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<html:form action="/logon" focus="username">

			<input name="challenge" type="hidden" value="<%= challenge %>">
			<input name="pwdEncript" type="hidden">
			<input name="isConnected" type="hidden" value="<bean:write name="logonForm" property="isConnected" />">
			<input name="companyName" type="hidden">
			<input name="productoId" type="hidden">
			<input name="tipoExplorer" type="hidden">
			
			<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
			<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
			<bean:define toScope="page" id="estiloSuperior_left" value=""></bean:define>
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
			%>
			<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
			<logic:equal scope="page" name="hayEstilo" value="true">
				<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
				<bean:define toScope="page" id="estiloSuperior_left" value="<%=estiloSuperior_left%>"></bean:define>
			</logic:equal>
			
			<!-- Este es el "Contenedor Secundario o Forma" -->
			
			 
			<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
			
			<%--
			<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
			
			--%>
			<!------ Include the above in your HEAD tag ---------->
						
			<!--  tabla login  -->
			<table id="tablaLogin" class="table table-borderless" style="width: 300px; padding: 2px;" >

				<!-- Barra Superior del "Contenedor Secundario o Forma" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloSuperior">
							<td style="<%=estiloSuperior%>"><vgcutil:message key="jsp.ingreso.titulo" /></td>
							<td style="<%=estiloSuperior_left%>" align="right" width="30%"><a onMouseOver="this.className='mouseEncimaBarraSuperiorForma'" onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:cerrarAplicacion();" class="mouseFueraBarraSuperiorForma"><vgcutil:message key="boton.salir" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloSuperior">
							<td ><vgcutil:message key="jsp.ingreso.titulo" /></td>
							<td align="right" width="30%"><a onMouseOver="this.className='mouseEncimaBarraSuperiorForma'" onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:cerrarAplicacion();" class="mouseFueraBarraSuperiorForma"><vgcutil:message key="boton.salir" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td class="barraSuperiorForma"><vgcutil:message key="jsp.ingreso.titulo" /></td>
						<td class="barraSuperiorForma" align="right" width="30%"><a onMouseOver="this.className='mouseEncimaBarraSuperiorForma'" onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:cerrarAplicacion();" class="mouseFueraBarraSuperiorForma"><vgcutil:message key="boton.salir" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</logic:equal>
				</tr>

				<!-- Cuerpo del "Contenedor Secundario o Forma" -->
				<tr id="trLogin" >
					<td colspan="2">
	 					<table style="padding: 2px;">	 					
							<tr>
								<td align="left" ><font size="2px"><vgcutil:message key="jsp.ingreso.usuario" /></font></td>
								<td colspan="2" ><html:text styleClass="cuadroTexto" property="username" onkeypress="ejecutarPorDefecto(event)" size="25" maxlength="40" /></td>
							</tr>
							<tr>
								<td align="left"><font size="2px"><vgcutil:message key="jsp.ingreso.contrasena" /></font></td>
								<td colspan="2"><html:password styleClass="cuadroTexto" property="password" onkeypress="ejecutarPorDefecto(event)" size="25" maxlength="40" redisplay="false" /></td>
							</tr>						
						</table>
					</td>
				</tr>

				<tr id="trMessage" style="display: none" >
					<td colspan="2">
	 					<table style="padding: 2px; border-spacing: 0; border-collapse: collapse;">
							<tr>
								<td colspan="2" align="center"><span id="spanMessage"></span></td>
							</tr>
						</table>
					</td>
				</tr>

				<!-- Barra Inferior del "Contenedor Secundario o Forma" -->
				<tr id="trBotones" >
					<td colspan="2" align="center">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.conectar" />"> <a  href="javascript:conectar();" class="btn btn-primary btn-sm"><vgcutil:message key="boton.conectar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;	
					</td>
				</tr>

			</table>
			
			
			
			
			        
        

		</html:form>

		<script type="text/javascript">				
			if (document.logonForm.isConnected.value == "true")
			{
				var mensaje = '<vgcutil:message key="error.user.alreadyconnected.confirm" />';
				if (confirm(mensaje))
				{
					_cerrarVentana = false;
					window.document.logonForm.action='<sslext:rewrite action="logout" />?usuario=<bean:write name="logonForm" property="username" />';
					window.document.logonForm.submit();
				}
			}
			
			<logic:notEmpty scope="session" name="companyName">
				document.logonForm.companyName.value = '<bean:write scope="session" name="licencia" property="companyName" />';
			</logic:notEmpty>
			<logic:empty scope="session" name="companyName">
				document.logonForm.companyName.value = 'Demo'; 
			</logic:empty>
			<logic:notEmpty scope="session" name="productoId">
				document.logonForm.productoId.value = '<bean:write scope="session" name="licencia" property="productoId" />';
			</logic:notEmpty>
			<logic:empty scope="session" name="productoId">
				document.logonForm.productoId.value = 'UA-57790239-1'; 
			</logic:empty>
		</script>
		<script type="text/javascript">
			!function(e,o,n,a,c,t,i){e.GoogleAnalyticsObject=c,e[c]=e[c]||function(){(e[c].q=e[c].q||[]).push(arguments)},e[c].l=1*new Date,t=o.createElement(n),i=o.getElementsByTagName(n)[0],t.async=1,t.src=a,i.parentNode.insertBefore(t,i)}(window,document,"script","//www.google-analytics.com/analytics.js","ga"),ga("create",document.logonForm.productoId.value,{cookieDomain:"none",cookieName:document.logonForm.companyName.value,cookieExpires:2e4}),ga("send","pageview");
		</script>
		<script type="text/javascript">
			init();
		</script>
	</tiles:put>

</tiles:insert>
