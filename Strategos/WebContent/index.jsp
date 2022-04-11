<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<!-- Modificado por: Kerwin Arias (02/09/2012) -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<html>
	<head>
		<title><vgcutil:message key="aplicacion.nombre" /></title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page='/componentes/estilos/estilos.css'/>">
		<META http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1">
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.min.js'/>"></script>
		<script type="text/javascript">

			// Declaración de las variables globales
			var IE = navigator.appName == "Microsoft Internet Explorer";
			var NS = navigator.appName == "Netscape";
			var bVer = parseInt(navigator.appVersion);
			var ventana;

			function abrirAplicacion() 
			{
				var estilo;
				var alto = (screen.height - 88);
				var ancho = (screen.width - 10);
			
				//Para el caso de Internet Explorer
				if (IE) 
				{
					alto = (screen.height - 88);
					ancho = (screen.width - 10);
			
					alto = screen.availHeight - 50;
					ancho = screen.availWidth - 10;
				}
			
				//Para el caso de Mozilla Firefox
				if (NS) 
				{
					alto = (screen.height - 85);
					ancho = (screen.width - 6);
			
					alto = screen.availHeight - 60;
					ancho = screen.availWidth - 15;
				}
			
				estilo = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, fullscreen=yes, type=fullWindow, left=0, top=0, width=" + ancho + ", height=" + alto;
				// estilo = "toolbar=yes, location=yes, directories=yes, status=yes, menubar=yes, scrollbars=yes, resizable=yes, left=0, top=0, width=" + ancho + ", height=" + alto;
				ventana = window.open("ingreso.action?pantallaAlto=" + alto + "&pantallaAncho=" + ancho + "&navegadorNombre=" + navigator.appName + "&navegadorVersion=" + navigator.appVersion + "&sesId=<%= request.getSession().getId() %>" + "&versionApp=" + '<vgcutil:message key="aplicacion.version" />' + "&buildApp=" + '<vgcutil:message key="aplicacion.version.build" />' + "&fechaApp=" + '<vgcutil:message key="aplicacion.version.fecha" />', "<vgcutil:message key='aplicacion.acronimo'/>", estilo, 'replace');
				
				var timer = setInterval(function() 
				{   
				    if(ventana.closed) 
				    {  
				        clearInterval(timer);  
				        ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/logout" />', null, 'usuarioLoggedOut()');
				       // window.location.href='<html:rewrite action="/logout" />';
				    }  
				}, 200);
			}

			function usuarioLoggedOut()
			{
				closeAppWindows();
			}

			function closeAppWindows() 
			{
				ventana.close();
				setInterval(function() 
				{
					window.close();
				}, 200);
				return false;
			}

			abrirAplicacion();
			document.onkeydown = mykeyhandler;

			function mykeyhandler() 
			{
				if(event.altLeft && window.event.keyCode == 37 || event.ctrlKey)
					alert('<vgcutil:message key="jsp.index.funcionNoPermitida" />');
				if ((window.event && window.event.keyCode == 116) || (window.event && window.event.keyCode == 122)) 
				{
					// try to cancel the backspace
					window.event.cancelBubble = true;
					window.event.keyCode = 8;
					window.event.returnValue = false;
					return false;
				}
			}

			function clickIE() 
			{
				if (document.all) 
					return false;
			}

			function clickNS(e) 
			{
				if (document.layers ||(document.getElementById && !document.all)) 
				{
					if (e.which == 2 || e.which == 3) 
						return false;
				}
			}

			if (document.layers) 
			{
				document.captureEvents(Event.MOUSEDOWN);
				document.onmousedown = clickNS;
			} 
			else 
			{
				document.onmouseup = clickNS;
				document.oncontextmenu = clickIE;
			}

			document.oncontextmenu = new Function("return false");

			function mouseDown(e) 
			{
				var ctrlPressed=0;
				var altPressed=0;
				var shiftPressed=0;
				if (parseInt(navigator.appVersion) > 3) 
				{
					if (navigator.appName == "Netscape") 
					{
						var mString =(e.modifiers + 32).toString(2).substring(3, 6);
						shiftPressed = (mString.charAt(0)=="1");
						ctrlPressed = (mString.charAt(1)=="1");
						altPressed = (mString.charAt(2)=="1");
						self.status = "modifiers=" + e.modifiers + " (" + mString + ")";
			  		} 
					else 
					{
						shiftPressed = event.shiftKey;
						altPressed = event.altKey;
						ctrlPressed = event.ctrlKey;
					}
					if (shiftPressed || altPressed || ctrlPressed) 
						alert ('<vgcutil:message key="jsp.index.funcionNoPermitida" />');
			 	}
				return true;
			}

			if (parseInt(navigator.appVersion) > 3) 
			{
				document.onmousedown = mouseDown;
				if (navigator.appName=="Netscape")
					document.captureEvents(Event.MOUSEDOWN);
			}
		</script>
	</head>

	<body class="cuerpo">
		<div id="fondo"></div>
		<!-- Este es el "Contenedor Principal" -->
		<table class="contenedorBienvenida">
			<!-- Este es el inicio de la aplicación -->
			<tr>
				<td><img src="<html:rewrite page='/componentes/comunes/vineta.gif'/>" border="0" width="10" height="10">&nbsp; <a onMouseOver="this.className='mouseEncimaBienvenida'" onMouseOut="this.className='mouseFueraBienvenida'" href="#" target="_self" class="mouseFueraBienvenida" onClick="abrirAplicacion()"><vgcutil:message key="jsp.index.bienvenida" /> <vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.version" /> <vgcutil:message key="aplicacion.version.text.build" /> <vgcutil:message key="aplicacion.version.build" /></a></td>
			</tr>
		</table>
	</body>
</html>
