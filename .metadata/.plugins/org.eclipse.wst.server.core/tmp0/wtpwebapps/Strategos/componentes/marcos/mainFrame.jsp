<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<!-- Modificado por: Kerwin Arias (11/10/2012) -->
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

		<%-- Título de la página --%>
		<title><vgcutil:message key="aplicacion.nombre" />&nbsp;-&nbsp;<tiles:insert attribute='title' /></title>

		<style>
			/* Center the loader */
			.loading {
				display: block;
				position: fixed;
				left: 50%;
				top: 50%;
				width: 100px;
				height: 100px;
				margin: -75px 0 0 -75px;
				border-radius: 50%;
				border: 3px solid transparent;
				border-top-color: #3498db;
				/*background: #ffffff;*/
				background: url('<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />')
	
				-webkit-transform: translateX(0);  /* Chrome, Opera 15+, Safari 3.1+ */
				-ms-transform: translateX(0);  /* IE 9 */
				transform: translateX(0);  /* Firefox 16+, IE 10+, Opera */
	
				-webkit-animation: spin 2s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
				animation: spin 2s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
				z-index: 1001;
			}
	
			.loading:before {
				content: "";
				position: absolute;
				top: 5px;
				left: 5px;
				right: 5px;
				bottom: 5px;
				border-radius: 50%;
				border: 3px solid transparent;
				border-top-color: #e74c3c;
				-webkit-animation: spin 3s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
				animation: spin 3s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
			}
	
			.loading:after {
				content: "";
				position: absolute;
				top: 15px;
				left: 15px;
				right: 15px;
				bottom: 15px;
				border-radius: 50%;
				border: 3px solid transparent;
				border-top-color: #f9c922;
				-webkit-animation: spin 1.5s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
				  animation: spin 1.5s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
			}
			
			@-webkit-keyframes spin {
				0%   { 
					-webkit-transform: rotate(0deg);  /* Chrome, Opera 15+, Safari 3.1+ */
					-ms-transform: rotate(0deg);  /* IE 9 */
					transform: rotate(0deg);  /* Firefox 16+, IE 10+, Opera */
				}
				100% {
					-webkit-transform: rotate(360deg);  /* Chrome, Opera 15+, Safari 3.1+ */
					-ms-transform: rotate(360deg);  /* IE 9 */
					transform: rotate(360deg);  /* Firefox 16+, IE 10+, Opera */
				}
			}
			@keyframes spin {
				0%   { 
					-webkit-transform: rotate(0deg);  /* Chrome, Opera 15+, Safari 3.1+ */
					-ms-transform: rotate(0deg);  /* IE 9 */
					transform: rotate(0deg);  /* Firefox 16+, IE 10+, Opera */
				}
				100% {
					-webkit-transform: rotate(360deg);  /* Chrome, Opera 15+, Safari 3.1+ */
					-ms-transform: rotate(360deg);  /* IE 9 */
					transform: rotate(360deg);  /* Firefox 16+, IE 10+, Opera */
				}
			}
	
			/* Add animation to "page content" */
			.animate-bottom {
			  position: relative;
			  -webkit-animation-name: animatebottom;
			  -webkit-animation-duration: 1s;
			  animation-name: animatebottom;
			  animation-duration: 1s
			}
	
			@-webkit-keyframes animatebottom {
			  from { bottom:-100px; opacity:0 } 
			  to { bottom:0px; opacity:1 }
			}
	
			@keyframes animatebottom { 
			  from{ bottom:-100px; opacity:0 } 
			  to{ bottom:0; opacity:1 }
			}
	
			#bodyDiv 
			{
				cursor: default;
			    width: 100%;
			    height: 100%;
			}
	
			#loaderDiv 
			{
				display: none;
				cursor: wait;
			}
			
			#loadingtextDiv 
			{
				display: none;
				position:absolute;
				left:calc(50% - 80px);
				top:calc(50% - 25px);
				color:#39C0C4;
				text-anchor: middle;
				font-weight: bold;
				font-size: 1em;
				font-family: sans-serif;
				z-index:1002
			}
	
			.ld-c {opacity:0;animation: ld-in 3s 0.0s ease infinite;}
			.ld-a {opacity:0;animation: ld-in 3s 0.1s ease infinite;}
			.ld-r {opacity:0;animation: ld-in 3s 0.2s ease infinite;}
			.ld-g {opacity:0;animation: ld-in 3s 0.3s ease infinite;}
			.ld-a {opacity:0;animation: ld-in 3s 0.4s ease infinite;}
			.ld-n {opacity:0;animation: ld-in 3s 0.5s ease infinite;}
			.ld-d {opacity:0;animation: ld-in 3s 0.6s ease infinite;}
			.ld-o {opacity:0;animation: ld-in 3s 0.7s ease infinite;}
			.ld-1 {opacity:0;animation: ld-in 3s 0.8s ease infinite;}
			.ld-2 {opacity:0;animation: ld-in 3s 0.9s ease infinite;}
			.ld-3 {opacity:0;animation: ld-in 3s 0.9s ease infinite;}
	
			@keyframes ld-in 
			{
				0% {opacity:0;transform:scale(0);}
				30% {opacity:1;transform:scale(1);}
				100% {opacity:1;transform:scale(1);}
			}
			
			.fadeIn {
			    animation: fadein 2s;
			    -moz-animation: fadein 2s; /* Firefox */
			    -webkit-animation: fadein 2s; /* Safari and Chrome */
			    -o-animation: fadein 2s; /* Opera */
			}
			@keyframes fadein {
			    from {
			        opacity:0;
			    }
			    to {
			        opacity:1;
			    }
			}
			@-moz-keyframes fadein { /* Firefox */
			    from {
			        opacity:0;
			    }
			    to {
			        opacity:1;
			    }
			}
			@-webkit-keyframes fadein { /* Safari and Chrome */
			    from {
			        opacity:0;
			    }
			    to {
			        opacity:1;
			    }
			}
			@-o-keyframes fadein { /* Opera */
			    from {
			        opacity:0;
			    }
			    to {
			        opacity: 1;
			    }
			}			
			
		</style>

		<%-- Estilos de la aplicación --%>
		<tiles:insert attribute="styleCss" />
		
		<%-- Js comunes --%>
		<tiles:insert attribute="jsCommons" />
	</head>

	<body style="visibility: hidden;" class="cuerpo" onload="inicializarComunesEventoWindowOnLoad();" oncontextmenu="return false;">
		<div id="fondo"></div>
		<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
		<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
		<bean:define toScope="page" id="estiloInferior" value=""></bean:define>
		<%
			com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
			if (estilos != null)
			{
				hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
				estiloSuperior = estilos.getEstiloSuperior();
				estiloInferior = estilos.getEstiloInferior();
			}
			else
				hayEstilo = "false";
		%>
		<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
		<logic:equal scope="page" name="hayEstilo" value="true">
			<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
			<bean:define toScope="page" id="estiloInferior" value="<%=estiloInferior%>"></bean:define>
		</logic:equal>
		<%-- Barra de progreso --%>
		<tiles:insert attribute="barraProgreso" />
		
		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="messages" />
		
		<!-- Este es el "Contenedor Principal" -->
		<table id="bodyTable" class="contenedorPrincipal">
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.encabezado.mostrar" valor="true">
				<!-- Esta es la "Barra Superior del Contenedor Principal" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloSuperior">
							<td align="center" style="<%=estiloSuperior%>" colspan="2"><tiles:insert attribute="header" /></td>
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloSuperior">
							<td align="center" class="barraSuperiorPrincipal" colspan="2"><tiles:insert attribute="header" /></td>
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td align="center" class="barraSuperiorPrincipal" colspan="2"><tiles:insert attribute="header" /></td>
					</logic:equal>
				</tr>
			</vgcutil:valorPropertyIgual>
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.barraaplicacion.mostrar" valor="true">
				<!-- Esta es la "Barra de Aplicación del Contenedor Principal" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloSuperior">
							<td align="center" style="<%=estiloSuperior%>" colspan="2"><tiles:insert attribute="barraAplicacion" /></td>
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloSuperior">
							<td align="center" class="barraAplicacionPrincipal" colspan="2"><tiles:insert attribute="barraAplicacion" /></td>
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td align="center" class="barraAplicacionPrincipal" colspan="2"><tiles:insert attribute="barraAplicacion" /></td>
					</logic:equal>
				</tr>
			</vgcutil:valorPropertyIgual>
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.barranavegacion.mostrar" valor="true">
				<!-- Esta es la "Barra de Navegación del Contenedor Principal" -->
				<tr>
					<td class="barraNavegacionPrincipal" colspan="2"><tiles:insert attribute="navigationBar" /></td>
				</tr>
			</vgcutil:valorPropertyIgual>
		
			<!-- Este es el "Cuerpo del Contenedor Principal" -->
			<tr>
				<%-- Barra de Areas (Vertical) --%>
				<td id="tdAreaBarDiv" style="width:110px;">
					<div id="areaBarDiv" style="width:110px; height:100%;">
						<tiles:insert attribute="areaBar" />
					</div>
				</td>
		
				<%-- Area principal del layout --%>
				<td id="bodyCuerpo">
					<div id="loaderDiv"></div>
					<div id="loadingtextDiv">
						<span class="ld-c">C</span>
						<span class="ld-a">a</span>
						<span class="ld-r">r</span>
						<span class="ld-g">g</span>
						<span class="ld-a">a</span>
						<span class="ld-n">n</span>
						<span class="ld-d">d</span>
						<span class="ld-o">o</span>
						<span class="ld-1">.</span>
						<span class="ld-2">.</span>
						<span class="ld-3">.</span>
					</div>
					<div id="bodyDiv">
						<tiles:insert attribute='body' />
					</div>
				</td>
			</tr>
		
			<vgcutil:valorPropertyIgual nombre="com.visiongc.framework.web.FrameworkWebConfiguration" property="jsp.frame.piepagina.mostrar" valor="true">
				<!-- Esta es la "Barra Inferior del Contenedor Principal" -->
				<tr>
					<logic:equal scope="page" name="hayEstilo" value="true">
						<logic:notEmpty scope="page" name="estiloInferior">
							<td style="<%=estiloInferior%>" colspan="2"><tiles:insert attribute="footer" /></td>		
						</logic:notEmpty>
						<logic:empty scope="page" name="estiloInferior">
							<td class="barraInferiorPrincipal" colspan="2"><tiles:insert attribute="footer" /></td>		
						</logic:empty>
					</logic:equal>
					<logic:equal scope="page" name="hayEstilo" value="false">
						<td class="barraInferiorPrincipal" colspan="2"><tiles:insert attribute="footer" /></td>		
					</logic:equal>
				</tr>
			</vgcutil:valorPropertyIgual>
		
		</table>
		
		<%-- Mensajes de error de la aplicación --%>
		<tiles:insert attribute="errorJsp" />
		
		<%-- Funciones de Cierre que se ejecutan al final de la página Html --%>
		<tiles:insert attribute="funcionesCierre" />

		<%-- Funciones de Loader --%>
		<script type="text/javascript">
			$(document).ready(function() {
				$('body').css('visibility', 'visible');
				$("#bodyDiv").css("display", "none");
				
				function loaderStart() 
				{
					$("#loaderDiv").css("display", "block");
			    	$("#loaderDiv").addClass("loading");
					$("#bodyDiv").addClass("animate-bottom");
					$("#loadingtextDiv").css("display", "block");
					$('#protectorVentanaPadre').css('height', _myHeight).css('visibility', 'visible');
				};    
	
				function setEnviroment()
				{
				    <logic:notEmpty scope="session" name="defaultLoader">
			    		_loader = '<bean:write scope="session" name="defaultLoader" />';
					</logic:notEmpty>
				};
				
				function loaderStop() 
				{
					$("#loaderDiv").css("display", "none");
			    	$("#loaderDiv").removeClass("loading");
					$("#loadingtextDiv").css("display", "none");
					$('#protectorVentanaPadre').css('visibility', 'hidden');
				};
				
				function setAncho()
				{
					var anchoBarra = "100";
					var tdAreaBarDiv = document.getElementById('tdAreaBarDiv');
					if (tdAreaBarDiv != null)
					{
						tdAreaBarDiv.style.width = anchoBarra + "px";
						var areaBarDiv = document.getElementById('areaBarDiv');
						if (areaBarDiv != null)
							areaBarDiv.style.width = anchoBarra + "px";
					}
					
					var bodyCuerpo = document.getElementById('bodyCuerpo');
					if (bodyCuerpo != null)
						bodyCuerpo.style.width = (parseInt(_myWidth) - (parseInt(anchoBarra) + 80)) + "px";
				};

				setAncho();
				setEnviroment();
				if (typeof(_loader) != "undefined" && eval(_loader) && _typeExplorer == _EXPLORER_CHROME)
				{
					loaderStart();
					timeLoad();
					setTimeout(function(){
						loaderStop();
						$("#bodyDiv").css("display", "block");
					}, ((_totalLoadPage * 1000) + 500));
				}
				else if (typeof(_loader) != "undefined" && !eval(_loader) && _typeExplorer == _EXPLORER_CHROME)
				{
					$("#bodyDiv").addClass("fadeIn");
					$("#bodyDiv").css("display", "block");
				}
				else
					$("#bodyDiv").css("display", "block");
			});
			
		</script>
		<% request.getSession().setAttribute("defaultLoader", false); %>
	</body>
</html>
