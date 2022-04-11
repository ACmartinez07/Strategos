<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (24/11/2012) -->

<jsp:include flush="true" page="/componentes/mensajes/bloqueoEspera/bloqueoEspera.jsp"></jsp:include>

<%-- Mensajes de Error de la Aplicación --%>
<logic:messagesPresent>
	<center>
		<div id="mensajeError" style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; visibility: visible; z-index: 2" align="center">
			<table style="width: 100%; height: 100%; background: url('<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />')">
				<tr>
					<td align="center" valign="middle">
					<vgcinterfaz:contenedorForma width="350" height="200">
						<vgcinterfaz:contenedorFormaTitulo>
						..:: <vgcutil:message key="jsp.error.titulo" />
						</vgcinterfaz:contenedorFormaTitulo>
		
						<table class="bordeFichaDatos" style="border-spacing: 0px; border-collapse: collapse; padding: 3px;">
		
							<%-- Este es el cuerpo de la "Ficha de Datos" --%>
							<tr>
								<td align="center">&nbsp;</td>
							</tr>
							<html:messages id="message" message="false">
								<tr>
									<td align="left"><img src="<html:rewrite page="/componentes/formulario/buscar.gif"/>"> <bean:write name="message" /></td>
								</tr>
							</html:messages>
							<tr>
								<td align="center">&nbsp;</td>
							</tr>
		
						</table>
		
						<vgcinterfaz:contenedorFormaBarraInferior>
							<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.cerrar" />">
							<a id="errores.botonCerrar" onblur="setTimeout('erroresSetFocoBotonCerrar()', 100);" onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentanaMensajeError();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cerrar" /></a>
						</vgcinterfaz:contenedorFormaBarraInferior>
					</vgcinterfaz:contenedorForma></td>
				</tr>
			</table>
		</div>
	</center>
	<script type="text/javascript">
		function cerrarVentanaMensajeError() 
		{
			mostrarCombos(true);
			hideElemento('mensajeError');
			// Se determina si la función para el foco existe
			if (window.setFocoObjetoInterfaz ? true: false) 
				setFocoObjetoInterfaz();
		}
	
		function erroresSetFocoBotonCerrar() 
		{
			var botonCerrar = document.getElementById('errores.botonCerrar');
			botonCerrar.focus();
		}
		setTimeout("erroresSetFocoBotonCerrar()", 100);
	</script>
</logic:messagesPresent>

<%-- Mensajes Informativos de la Aplicación --%>
<logic:messagesPresent message="true">
	<center>
		<div id="mensajeInformativo" style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; visibility: visible; z-index: 2; font-size:11px; font-family:Verdana;" align="center">
			<table style="width: 100%; height: 100%; background: url('<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />')">
				<tr>
					<td style="width: 470px; height: 220px;" align="center" valign="middle">
						<vgcinterfaz:contenedorForma width="450px" height="200px">
							<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.mensaje.informativo" /></vgcinterfaz:contenedorFormaTitulo>
							<table class="bordeFichaDatos" style="border-spacing:0px; border-collapse: collapse; padding: 3px;">
								<tr>
									<td align="center">&nbsp;</td>
								</tr>
								<html:messages id="message" message="true">
									<tr>
										<td align="left"><img src="<html:rewrite page="/componentes/formulario/buscar.gif"/>"> <bean:write name="message" /></td>
									</tr>
								</html:messages>
								<tr>
									<td align="center">&nbsp;</td>
								</tr>
							</table>
							<vgcinterfaz:contenedorFormaBarraInferior>
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.cerrar" />">
								<a id="mensajes.botonCerrar" onblur="setTimeout('mensajesSetFocoBotonCerrar()', 100);" onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cerrarVentanaMensajeInformativo();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cerrar" /></a>
							</vgcinterfaz:contenedorFormaBarraInferior>
						</vgcinterfaz:contenedorForma>
					</td>
				</tr>
			</table>
		</div>
	</center>
	<script type="text/javascript">
		function cerrarVentanaMensajeInformativo() 
		{
			mostrarCombos(true);
			hideElemento('mensajeInformativo');
			// Se determina si la función para el foco existe
			if (window.setFocoObjetoInterfaz ? true: false) 
				setFocoObjetoInterfaz();
			if (window._setCloseParent && window.onClose)
				onClose();
			else if(window._setAlerta && window.onAlerta)
				onAlerta();
			else if(window._setShowReport && window.onShowReport)
				onShowReport();
		}	
			
		function mensajesSetFocoBotonCerrar() 
		{
			var botonCerrar = document.getElementById('mensajes.botonCerrar');
			botonCerrar.focus();
		}
		
		setTimeout("mensajesSetFocoBotonCerrar()", 100);
	</script>
</logic:messagesPresent>


<!-- ----------------------------------------------------------------------------------------------------- -->
<!-- --- INICIO: Pantalla de Fondo de Mensajes ----------------------------------------------------------- -->
<!-- ----------------------------------------------------------------------------------------------------- -->
<div id="protectorVentanaPadre" style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; visibility: hidden; z-index: 12" align="center" onclick="verificarVentanaModal()" onmouseOver="verificarVentanaModal()">
	<table style="width: 100%; height: 100%; background: url('<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />')">
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	var IMG_INFO = 1;
	var IMG_IMPORTANT = 2;
	var IMG_QUESTION = 3;
	var IMG_ERROR = 4;
	var _callFunction = null;
	
	function closeModal(hasValidate)
	{
		window.location.href = "#close";
		if (window._setCloseParent && window.onClose)
			onClose();
	}
	
	function showAlert(mensaje, alto, ancho, title, imgTipo, callFunction)
	{
		if (typeof title == "undefined")
			title = '..:: ' + '<vgcutil:message key="jsp.mensaje.informativo.titulo" />';
		if (typeof imgTipo == "undefined")
			imgTipo = IMG_INFO;
		if (typeof mensaje != "undefined")
		{
			if (typeof callFunction != "undefined")
				_callFunction = callFunction;
			var modalbox = document.getElementById('modalbox');
			if (modalbox != null)
			{
				if (typeof alto != "undefined")
					modalbox.style.height = alto + "px";
				if (typeof ancho != "undefined")
					modalbox.style.width = ancho + "px";
			}

			var mensajeTexto = document.getElementById("MensajeTexto");
			if (mensajeTexto != null)
				mensajeTexto.innerHTML = mensaje;
			else
				mensajeTexto.innerHTML = "undefined";
			var mensajeTitle = document.getElementById("MensajeTitle");
			if (mensajeTitle != null)
				mensajeTitle.innerHTML = title;
			else
				mensajeTitle.innerHTML = "undefined";
			var imgIcon = document.getElementById('imgIcon');
			if (imgIcon != null)
			{
				if (imgTipo == IMG_INFO)
					imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/info.png'/>";
				else if (imgTipo == IMG_IMPORTANT)
					imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/important.png'/>";
				else if (imgTipo == IMG_QUESTION)
					imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/question.png'/>";
				else if (imgTipo == IMG_ERROR)
					imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/error.png'/>";
				else
					imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/info.png'/>";
			}
			else
				imgIcon.src = "<html:rewrite page='/componentes/mensajes/imagenes/info.png'/>";
			
			var trBotones = document.getElementById('trBotones');
			if (trBotones != null && imgTipo == IMG_QUESTION)
			{
				trBotones.style.display = "";
				var tdCloseModal = document.getElementById('tdCloseModal');
				if (tdCloseModal != null)
					tdCloseModal.style.display = "none";
			}
				
			window.location.href = "#modalAlerta";
		}
	}
	
	function mensajeAceptar()
	{
		setTimeout(_callFunction, 1);
		window.location.href = "#close";
	}

	function mensajeCancelar()
	{
		window.location.href = "#close";
	}
	
</script>
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
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/paginas/comunes/jQuery/window-modal/css/window-modal.css" />">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/componentes/estilos/botones.css" />" media="screen"/>
<div id="modalAlerta" class="modalmask">
	<div id="modalbox" class="modalbox movedown" style="padding: 0px;">
		<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px;border-collapse: collapse;">
			<logic:equal scope="page" name="hayEstilo" value="true">
				<logic:notEmpty scope="page" name="estiloSuperior">
					<tr style="<%=estiloSuperior%>" id="trMensajeTitle">
						<td style="text-align: left; width:100%;"><span style="font-family:Verdana; font-size:11px; color:#ffffff;" id="MensajeTitle"></span></td>
						<td id="tdCloseModal"><a href="javascript:closeModal(true);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a></td>
					</tr>
				</logic:notEmpty>
				<logic:empty scope="page" name="estiloSuperior">
					<tr class="barraSuperiorPrincipal" id="trMensajeTitle">
						<td style="text-align: left; width:100%;"><span style="font-family:Verdana; font-size:11px; color:#ffffff;" id="MensajeTitle"></span></td>
						<td><a href="javascript:closeModal(true);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a></td>
					</tr>
				</logic:empty>	
			</logic:equal>
			<logic:equal scope="page" name="hayEstilo" value="false">
				<tr class="barraSuperiorPrincipal" id="trMensajeTitle">
					<td style="text-align: left; width:100%;"><span style="font-family:Verdana; font-size:11px; color:#ffffff;" id="MensajeTitle"></span></td>
					<td><a href="javascript:closeModal(true);" title="<vgcutil:message key="boton.cancelar.alt" />" class="close-modal">X</a></td>
				</tr>
			</logic:equal>
			<tr>
				<td colspan="2">
					<table class="tabtable tabFichaDatostabla bordeFichaDatos" style="padding: 0px;border-collapse: collapse;">
						<tr>
							<td style="text-align: left; width:20px;"><img id="imgIcon" src="<html:rewrite page="/componentes/formulario/buscar.gif"/>"></td>
							<td style="text-align: left;"><span style="font-family:verdana; font-size:1.2em; font-weight: bold;" id="MensajeTexto"></span></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr id="trBotones" style="display: none;">
				<td colspan="2" style="text-align: right;">
					<a class="boton" title="<vgcutil:message key="boton.mensaje.si" />" onclick="mensajeAceptar()"><vgcutil:message key="boton.mensaje.si" /></a>
					<a class="boton" title="<vgcutil:message key="boton.mensaje.no" />" onclick="mensajeCancelar()"><vgcutil:message key="boton.mensaje.no" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- ----------------------------------------------------------------------------------------------------- -->
<!-- --- FIN: Pantalla de Fondo de Mensajes -------------------------------------------------------------- -->
<!-- ----------------------------------------------------------------------------------------------------- -->
