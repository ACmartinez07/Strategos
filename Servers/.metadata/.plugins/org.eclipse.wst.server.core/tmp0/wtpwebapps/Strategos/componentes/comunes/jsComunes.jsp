<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (13/10/2012) -->

// <script type="text/javascript">

// ---------------------------------------------------------------------------------------------------------//
// ----- INICIO: Funcionalidad para desactivar la tecla backspace como atajo de ir atras -------------------//
// ---------------------------------------------------------------------------------------------------------//
var _myWidth = 0; 
var _myHeight = 0;
var _myWidthReal = 0; 
var _myHeightReal = 0;
var _anchoAreBar = 0;
var _emailDefault = null;
var _typeExplorer = null;
var _loader = null;
var _inicioLoadPage = (new Date()).getTime();
var _finLoadPage = null;
var _totalLoadPage = null;
var _dialogOpen = null;

var _EXPLORER_IE = 1;
var _EXPLORER_CHROME = 2;

backSpaceOff();
getScreenSize();
getDefaultVariables();

var respuestaBooleanaEvento = true;

function getDefaultVariables()
{
    <logic:notEmpty scope="session" name="tipoDefaultSentEmail">
		_emailDefault = '<bean:write scope="session" name="tipoDefaultSentEmail" />';
	</logic:notEmpty>
    <logic:notEmpty scope="session" name="defaultLoader">
    	_loader = '<bean:write scope="session" name="defaultLoader" />';
	</logic:notEmpty>

	var IE = !!(navigator.userAgent.match(/Trident/) && !navigator.userAgent.match(/MSIE/));
    var chrome = (navigator.userAgent.toLowerCase().indexOf('chrome') > -1);	
	if (IE)
		_typeExplorer = _EXPLORER_IE;
	else if (chrome)
		_typeExplorer = _EXPLORER_CHROME;
}

function getScreenSize() 
{
	_myWidth = 0, 
	_myHeight = 0;
	_myWidthReal = 0; 
	_myHeightReal = 0;
	if(typeof(window.innerWidth) == 'number') 
	{
		//No-IE
		_myWidth = window.innerWidth;
		_myWidthReal = _myWidth - 40;
		_myHeight = window.innerHeight;
		_myHeightReal = _myHeight - 90; 
	} 
	else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) 
	{
		//IE 6+
		_myWidth = document.documentElement.clientWidth;
		_myWidthReal = _myWidth - 40;
		_myHeight = document.documentElement.clientHeight;
		_myHeightReal = _myHeight - 90;
	} 
	else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) 
	{
		//IE 4 compatible
		_myWidth = document.body.clientWidth;
		_myWidthReal = _myWidth - 40;
		_myHeight = document.body.clientHeight;
		_myHeightReal = _myHeight - 90;
	}
	else
	{
		_myHeight = screen.height;
		_myWidth = screen.width;
		_myWidthReal = _myWidth - 6;
		_myHeightReal = _myHeight - 85;
	}

	return true;
}

function resizeAlto(forma, tamano)
{
	if (typeof(tamano) == "undefined")
		tamano = 0;
	if (_myHeight == 0)
		_myHeight = screen.height;
	
	if (forma != null)
		forma.style.height = (parseInt(_myHeight) - tamano) + "px";
}

function getAltoScreenDividida(tamano)
{
	if (typeof(tamano) == "undefined")
		tamano = 0;
	if (_typeExplorer == _EXPLORER_IE && tamano != 0)
		tamano = parseInt(tamano) - 28;
	
	return parseInt(tamano);
}

function timeLoad()
{
	_finLoadPage = (new Date()).getTime();
	_totalLoadPage = (_finLoadPage - _inicioLoadPage) / 1000;
}

// Función que maneja el evento keypress o keydown para desactivar la tecla
// backspace para impedir que se ejecute la funcionalidad ir atras
function backSpaceOffValidate(event) 
{
	event = event || window.event;
	var tgt = event.target || event.srcElement;
	// var strid = tgt.type;
	if (event.keyCode == 8 && tgt.type != "text" && tgt.type != "textarea" && tgt.type != "password") 
		return false;
	else 
	{
		if (respuestaBooleanaEvento) 
			return true;
		else 
		{
			respuestaBooleanaEvento = true;
			return false;
		}
	}
}

// Función que asigna la función backSpaceOffValidate al evento onkeydown
// y onkeypress del objeto document
function backSpaceOff() 
{
	if (document) 
	{
		if (document.onkeydown == null) 
		{
			document.onkeydown = backSpaceOffValidate;
			document.onkeypress = backSpaceOffValidate;
		} 
	}
}

// ---------------------------------------------------------------------------------------------------------//
// ----- FIN: Funcionalidad para desactivar la tecla backspace como atajo de ir atras ----------------------//
// ---------------------------------------------------------------------------------------------------------//

// ---------------------------------------------------------------------------------------------------------//
// ----- INICIO: Funcionalidad para apertura de ventanas modales -------------------------------------------//
// ---------------------------------------------------------------------------------------------------------//

var ventanaModal = null;

function verificarVentanaModal() 
{
	if ((ventanaModal != null) && (ventanaModal.closed != null && !ventanaModal.closed)) 
		ventanaModal.focus();
	if ((ventanaModal != null) && (ventanaModal.closed != null && ventanaModal.closed)) 
	{
		var elemento = document.getElementById('protectorVentanaPadre');		
		elemento.style.visibility='hidden';
	}
}

function setVerificarVentanaModal() 
{
	if (window) 
	{
		if (window.onfocus == null) 
		{
		    if (!document.all) 
		    {
				window.onfocus = function () 
				{
					try 
					{
						if (ventanaModal != null && ventanaModal.closed != null)
						{
							if (!ventanaModal.closed)
								ventanaModal.focus();
							else
							{
								var elemento = document.getElementById('protectorVentanaPadre');		
								elemento.style.visibility='hidden';
							}
						}
					}
					catch(err) 
					{
						ventanaModal.focus();
					}					
				};
		    }
		} 
	}
}

// Modificado por: Kerwin Arias (24/03/2012)
function abrirVentanaModal(url, nombre, ancho, alto, x, y, verificarVentanaModal) 
{
	if (typeof(verificarVentanaModal) == "undefined")
		verificarVentanaModal = true;
	
	if (verificarVentanaModal)
		setVerificarVentanaModal();
	var IE = navigator.appName=="Microsoft Internet Explorer";
    var NS = navigator.appName=="Netscape";
	var elemento = document.getElementById('protectorVentanaPadre');
	elemento.style.height=screen.height-80;
	elemento.style.visibility='visible';
	var parametros = '';
	if ((ancho != null) && (ancho != '')) 
		parametros = ', width=' + ancho;
	
	if ((alto != null) && (alto != '')) 
		parametros = parametros + ', height=' + alto;
	
	if (IE)  //Para el caso de Internet Explorer
	{			
		altoPantalla = (screen.height-88);
		anchoPantalla = (screen.width-10);
	}	
	
	if (NS) //Para el caso de Mozilla Firefox
	{			
		altoPantalla = (screen.height-85);
		anchoPantalla = (screen.width-6);
	}	
	if (x == undefined)
		posicionX = (anchoPantalla/2) - (ancho/2);
	else
		posicionX = x;
	if (y == undefined)
		posicionY = (altoPantalla/2) - (alto/2);	
	else
		posicionY = y;
	parametros = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, left=" + posicionX + ", top=" + posicionY + parametros;
	//parametros = "toolbar=yes, location=yes, directories=yes, status=yes, menubar=yes, scrollbars=yes, resizable=yes, left=" + posicionX + ", top=" + posicionY + parametros;
	ventanaModal = window.open(url,nombre,parametros);
	ventanaModal.opener = self;
	
	return ventanaModal;
}

// ---------------------------------------------------------------------------------------------------------//
// ----- FIN: Funcionalidad para apertura de ventanas modales ----------------------------------------------//
// ---------------------------------------------------------------------------------------------------------//



// ---------------------------------------------------------------------------------------------------------//
// ----- INICIO: Funcionalidad de navegación a distintas partes genéricas de una aplicación ----------------//
// ---------------------------------------------------------------------------------------------------------//

// Función que llama a la página de salida del sistema
function salir(closeApp) 
{
	var xml = "";
	if (typeof(closeApp) != "undefined")
		xml = "closeApp=true";
	window.location.href='<sslext:rewrite action="logout"/>?' + xml;
	if (typeof(_cerrarApplicacion) != "undefined")
		_cerrarVentana = false;
}

function prepararPagina() 
{
	abrirVentanaModal('<sslext:rewrite action="/framework/configuracion/editarConfiguracionPagina"/>?configuracionBase=<vgcutil:message key="aplicacion.configuracionbase.clase"/>', 'preparaPagina', 450, 452);
}

// Función que llama a la página de inicio
// Modificado por: Kerwin Arias (13/06/2012)
function inicio() 
{
	window.location.href='<sslext:rewrite action="/home"/>?defaultLoader=true';
}

// Función que llama abre el Manual Online
function abrirManual() 
{
	var estilo;
	estilo = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable =yes, left=100, top=100, width=" + _myWidth + ", height=" + _myHeight;
	window.open('<html:rewrite page="/paginas/comunes/manual.jsp" />', 'manual', estilo);
}

// Función que llama a la página (Acerca de ...)
function acerca() 
{	
	abrirVentanaModal('<sslext:rewrite action="acerca"/>', 'acerca', '470', '690');
}

function licencia() 
{	
	abrirVentanaModal('<sslext:rewrite action="licencia"/>', 'licencia', '470', '440');
}

function cambiarClave() 
{
	window.location.href = '<sslext:rewrite action="/framework/usuarios/cambiarClaveUsuario"/>';
}

function configurarLogin() 
{
	window.location.href = '<sslext:rewrite action="/framework/configuracion/login/editar"/>';
}


// Función que llama al action que navega hacia atras en la pila de navegción de una sesión de usuario
function irAtras(pasos) 
{
	window.location.href='<sslext:rewrite action="/goback"/>' + '?pasos=' + pasos;
}

// Función que abre una nueva ventana para reportes
function abrirReporte(url, nombre, ancho, alto) 
{
	var parametros = '';
	if ((ancho != null) && (ancho != '')) 
		parametros = ', width=' + ancho;
	else 
		parametros = ', width=' + ((screen.width)-100);

	if ((alto != null) && (alto != '')) 
		parametros = parametros + ', height=' + alto;
	else
		parametros = parametros + ', height=' + ((screen.height)-150);

	parametros = "toolbar=no, location=no, directories=no, status=yes, menubar=no, scrollbars=no, resizable =yes, left=50, top=50 " + parametros;	
	ventana = window.open(url,nombre,parametros);
	ventana.focus();
}

// Función que abre el editor de configuración de un visor tipo lista
// Agregado por: Kerwin Arias (25/06/2012)
function configurarVisorLista(nombreConfiguracionBase, nombreVisorLista, tituloVisorLista, esPropio, ancho, alto) 
{
	if (typeof(esPropio) == "undefined")
		esPropio = false;
	if (typeof(ancho) == "undefined")
		ancho = "500";
	if (typeof(alto) == "undefined")
		alto = "480";
	
	var url = '?nombreConfiguracionBase=' + nombreConfiguracionBase + '&nombreVisorLista=' + nombreVisorLista + '&tituloVisorLista=' + tituloVisorLista + '&esPropio=' + esPropio + '&alto=' + alto + '&ancho=' + ancho; 
	
	if (!esPropio)
		window.location.href='<sslext:rewrite action="/framework/configuracion/editarConfiguracionVisorLista"/>' + url;
	else
		abrirVentanaModal('<html:rewrite action="/framework/configuracion/editarConfiguracionVisor" />' + url, "VistaMediciones", ancho, alto);
}

function SendEmail(to, cc, cco, asunto, cuerpo, ancho, alto)
{
	if ((typeof to != "undefined") && to != "")
	{
		if (!((typeof cc != "undefined") && cc != ""))
			cc = "";
		if (!((typeof cco != "undefined") && cco != ""))
			cco = "";
		if (!((typeof asunto != "undefined") && asunto != ""))
			asunto = "";
		if (!((typeof cuerpo != "undefined") && cuerpo != ""))
			cuerpo = "";
		if (!((typeof ancho != "undefined") && ancho != ""))
			ancho = _myWidth; 
		if (!((typeof alto != "undefined") && alto != ""))
			alto = _myHeight;
		
		var mensaje = "mailto:" + to;
		if (cc != "")
			mensaje = mensaje + "?cc=" + cc + (cco != "" ? ("&bcc=" + cco) : "") + (asunto != "" ? ("&subject=" + escape(asunto)) : "") + (cuerpo != "" ? ("&body=" + escape(cuerpo)) : "");
		else if (cco != "")
			mensaje = mensaje + "?cc=" + cco + (asunto != "" ? ("&subject=" + escape(asunto)) : "") + (cuerpo != "" ? ("&body=" + escape(cuerpo)) : "");
		else 
			mensaje = mensaje + "?subject=" + escape(asunto) + (cuerpo != "" ? ("&body=" + escape(cuerpo)) : "");
		
		estilo = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, fullscreen=yes, type=fullWindow, left=0, top=0, width=" + ancho + ", height=" + alto;
		// estilo = "toolbar=yes, location=yes, directories=yes, status=yes, menubar=yes, scrollbars=yes, resizable=yes, left=0, top=0, width=" + ancho + ", height=" + alto;
		
		var ventana = null;
		if (_emailDefault == null || _emailDefault == 1)
		{
			var mailTo = new mailtoUrl();
			mailTo.addMain(to);
			mailTo.addCC(cc);
			mailTo.addBCC(cco);
			mailTo.setSubject(asunto);
			mailTo.setBody(cuerpo);
			
			window.location.href = mailTo.getURL();
		}
		else if (_emailDefault == 2)
		{
			asunto = UrlEncodeText(asunto);			
			cuerpo = UrlEncodeText(cuerpo);
			
	 		var url = 'http://mail.google.com/mail/?view=cm&fs=1' + '&to=' + to + '&su=' + asunto + '&body=' + cuerpo + '&ui=1';
	 		ventana = window.open(url, "Envio de Correo", estilo, 'replace');
		}
	}
	else
		alert('<vgcutil:message key="jsp.email.to.invalido" />');
}

function configurarSistema()
{
	abrirVentanaModal('<html:rewrite action="/configuracion/sistema/editar" />', "Configuracion", 700, 570);
}

// ---------------------------------------------------------------------------------------------------------//
// ----- FIN: Funcionalidad de navegación a distintas partes genéricas de una aplicación -------------------//
// ---------------------------------------------------------------------------------------------------------//

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de advertencia al usuario de tiempo de sesión a punto de expirar ------------//
//----------------------------------------------------------------------------------------------------------//

// <logic:notEmpty name="com.visiongc.app.web.watchsessions" >
//	<logic:equal name="com.visiongc.app.web.watchsessions" value="false">

// Funcionalidad de aviso de session Timeout
// variable que indica si se usa el sistema de advertencia de tiempo de uso de la aplicación
var useSessionTimeoutWarning = true;
// Aviso de que quedan 2 minutos para ejecutar un request antes del session timeout
setTimeout("sessionTimeoutWarning(2)", <%=((request.getSession().getMaxInactiveInterval() * 1000) - 120000)%>);
// Aviso de que quedan 1 minuto para ejecutar un request antes del session timeout
setTimeout("sessionTimeoutWarning(1)", <%=((request.getSession().getMaxInactiveInterval() * 1000) - 60000)%>);
// Aviso de que se cerrará la aplicación en 10 segundos
setTimeout("sessionTimeoutWarning(0)", <%=((request.getSession().getMaxInactiveInterval() * 1000) - 10000)%>);

// Función que muestra una ventana de mensaje que le indica al usuario que el tiempo de uso del sistema se agota
function sessionTimeoutWarning(time) 
{
	if (useSessionTimeoutWarning) 
	{								
		ventanaHija = window.open('<html:rewrite page="/paginas/comunes/sessionTimeoutWarning.jsp" />?time=' + time, 'sessionTimeoutWarning', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable =no, left=50, top=50, height=150, width=400');
		ventanaHija.focus();
		if (time == 0) 
			// Si ha transcurrido el tiempo de sesión, se cierra el sistema
			setTimeout("sessionTimeoutCloseApp()", <%=11000%>);
	}
}

// Función que cierra la sesión y le indica al usuario que la sesión ha concluido
function sessionTimeoutCloseApp() 
{
	window.location.href='<html:rewrite page="/paginas/comunes/sessionTimeout.jsp" />';
}

//	</logic:equal>
// </logic:notEmpty>

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funcionalidad de advertencia al usuario de tiempo de sesión a punto de expirar ---------------//
//----------------------------------------------------------------------------------------------------------//

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de refresco o actualización de session web  ---------------------------------//
//----------------------------------------------------------------------------------------------------------//

// <logic:notEmpty name="com.visiongc.app.web.watchsessions" >
//	<logic:equal name="com.visiongc.app.web.watchsessions" value="true">

var strTimeoutRefreshSession = '<bean:write name="com.visiongc.app.web.watchsessions.refreshinterval"/>';

setTimeout("refreshSession()", parseInt(strTimeoutRefreshSession));
function refreshSession() 
{
	var xmlHttp;
	//var abortado = false;

	try 
	{  // Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} 
	catch (e) 
	{  // Internet Explorer
		try 
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} 
		catch (e) 
		{
			try 
			{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} 
			catch (e) 
			{
				alert("El navegador utilizado no soporta AJAX!");
				return false;
			}
		}
	}
	
	// Tiempo de espera de 2 minutos
	var xmlHttpRequestTimer = setTimeout(function() { alert('Tiempo de Espera agotado para respuesta AJAX'); abortado = true; xmlHttp.abort();}, 2 * 60 * 1000);
	xmlHttp.onreadystatechange = function() 
	{
		if(xmlHttp.readyState == 4) 
			clearTimeout(xmlHttpRequestTimer);
    };
	xmlHttp.open('GET', '<sslext:rewrite action="/framework/refreshSession"/>', true);
	xmlHttp.send(null);
	setTimeout("refreshSession()", 60000);
}

//	</logic:equal>
// </logic:notEmpty>

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funcionalidad de refresco o actualización de session web  ------------------------------------//
//----------------------------------------------------------------------------------------------------------//

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de refresco de elementos HTML de una página ---------------------------------//
//----------------------------------------------------------------------------------------------------------//

function appGlobalRefreshHtmlElements() 
{
	if (typeof(appGlobalListMenuIds) != 'undefined') 
		appGlobalRefreshMenus();
}

function URLEncode(html)
{
    if (html == null)
        return "";
			
    // Reserved Characters
    html = html.replace(new RegExp("&", "gi"), "%26");
    html = html.replace(new RegExp("#", "gi"), "%23");
    html = Replace(html, "+", "%2B");
    Replace(html, "$", "%24");

    return html;
}

function isHtml(text)
{
	if (text.indexOf("<") > -1)
		return true;
	else 
		return false;
}

function Replace(sentence, lookFor, replaceWith)
{
    while (sentence.indexOf(lookFor) > -1)
        sentence = sentence.replace( lookFor, replaceWith ); 

    return sentence;
}

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funcionalidad de refresco de elementos HTML de una página ------------------------------------//
//----------------------------------------------------------------------------------------------------------//

// </script>

<!-- Agregado por: Kerwin Arias (19/01/2012) -->
// <script type="text/JavaScript">

// Función que oculta un objeto HTML (div, table, etc)
function hideElemento(id) 
{
	var elemento = document.getElementById(id);
	elemento.innerHTML = '';
    if (elemento.style) 
    	elemento.style.visibility='hidden';
}

function findObjetoHtml(nombre, documento) { //v4.01

	var p, i, x;

	if (!documento)
		documento = document;
	if ((p = nombre.indexOf("?")) > 0 && parent.frames.length) 
	{
		documento = parent.frames[nombre.substring(p + 1)].document;
		nombre = nombre.substring(0, p);
	}
	
	if (!(x = documento[nombre]) && documento.all)
		x = documento.all[nombre];
	for (i = 0; !x && i < documento.forms.length; i++)
		x = documento.forms[i][nombre];
	for (i = 0; !x && documento.layers && i < documento.layers.length; i++)
		x = findObjetoHtml(nombre, documento.layers[i].document);
	if (!x && documento.getElementById)
		x = documento.getElementById(nombre);
	return x;
}

function changePropertyObjetoHtml(objName, theProp, theValue) //v6.0
{
	var obj = findObjetoHtml(objName);

	if (obj && (theProp.indexOf("style.")==-1 || obj.style)) 
	{
		if (theValue == true || theValue == false)
			eval("obj." + theProp + "=" + theValue);
		else
			eval("obj." + theProp + "='" + theValue + "'");
	}
}

// Creado por: Kerwin Arias (13/06/2012)
// Obtiene un objeto INPUT HTML de un documento
function getInputHtmlByName(nombre, documento) 
{
	var i, x = null;

	if (!documento)
		documento = document;
	for (i = 0; !x && i < documento.forms.length; i++)
		x = documento.forms[i][nombre];
	for (i = 0; !x && documento.layers && i < documento.layers.length; i++)
		x = getInputHtmlByName(nombre, documento.layers[i].document);

	return x;
}

function getElementByName(nombre, documento) 
{
	for (var f = 0; f < documento.forms.length; f++) 
	{	   
		forma = documento.forms[f];
		elementosForma = forma.elements;
		for (var i = 0; i < elementosForma.length; i++) 
		{
	    	var obj = elementosForma[i];
	    	if (obj.name == nombre)
	    		return obj.value;
		}
	}

	return null;
}

function stringTrim(str) 
{
	var resultado = '';
	var agregar = false;
	var ultimoCaracter = 0;
	for (var index = 0; index < str.length; index++) 
	{
		if (str.substring(index, index + 1) != ' ') 
		{
			agregar = true;
			ultimoCaracter = index;
		}

		if (agregar) 
			resultado = resultado + str.substring(index, index + 1);
	}
	if (resultado.length > 0) 
	{
		// Numero de caracteres espacio al final
		index = str.length - ultimoCaracter - 1;
		// Indice final del resultado
		index = resultado.length - index;
		resultado = resultado.substring(0, index);
	}
	
	return resultado;
}

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funciones de inicialización comunes -------------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

function inicializarComunesEventoWindowOnLoad() 
{
	inicializarScrollInvisible();
}

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funciones de inicialización comunes ----------------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//


//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de scroll invisible en objetos div ------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

listaScrollVerticalInvisible = new Array();
listaScrollHorizontalInvisible = new Array();
 
function registrarScrollVerticalInvisible(objetoIdMoverAbajo, objetoIdMoverArriba, divId, velocidadMoverAbajo, velocidadMoverArriba) 
{
	if (listaScrollVerticalInvisible[objetoIdMoverAbajo] == null) 
		listaScrollVerticalInvisible[objetoIdMoverAbajo] = new Array();
	if (listaScrollVerticalInvisible[objetoIdMoverArriba] == null) 
		listaScrollVerticalInvisible[objetoIdMoverArriba] = new Array();
	listaScrollVerticalInvisible[objetoIdMoverAbajo].push(new Array(divId, velocidadMoverAbajo));
	listaScrollVerticalInvisible[objetoIdMoverArriba].push(new Array(divId, velocidadMoverArriba));
}

function registrarScrollHorizontalInvisible(objetoIdMoverDerecha, objetoIdMoverIzquierda, divId, velocidadMoverDerecha, velocidadMoverIzquierda) 
{
	if (listaScrollHorizontalInvisible[objetoIdMoverDerecha] == null) 
		listaScrollHorizontalInvisible[objetoIdMoverDerecha] = new Array();
    if (listaScrollHorizontalInvisible[objetoIdMoverIzquierda] == null) 
		listaScrollHorizontalInvisible[objetoIdMoverIzquierda] = new Array();
    listaScrollHorizontalInvisible[objetoIdMoverDerecha].push(new Array(divId, velocidadMoverDerecha));
    listaScrollHorizontalInvisible[objetoIdMoverIzquierda].push(new Array(divId, velocidadMoverIzquierda));
}

function inicializarScrollInvisible() 
{
	for (key in listaScrollVerticalInvisible) 
	{
		var elemento = document.getElementById(key);
		elemento.onmouseover = iniciarScrollVerticalInvisible;
		elemento.onmouseout = detenerScrollVerticalInvisible;
	}

	for (key in listaScrollHorizontalInvisible) 
	{
		var elemento = document.getElementById(key);
		elemento.onmouseover=iniciarScrollHorizontalInvisible;
		elemento.onmouseout=detenerScrollHorizontalInvisible;
	}
}

function iniciarScrollVerticalInvisible() 
{
	scrollDivsVerticalInvisible = new Array();
	velocidadDivsVerticalInvisible = new Array();

	for (key in listaScrollVerticalInvisible[this.id]) 
	{
		scrollDivsVerticalInvisible.push(document.getElementById(listaScrollVerticalInvisible[this.id][key][0]));
		velocidadDivsVerticalInvisible.push(listaScrollVerticalInvisible[this.id][key][1]);
	}
	identificadorScrollVerticalInvisible = setInterval('hacerScrollVerticalInvisible()', 50);
}

function iniciarScrollHorizontalInvisible() 
{
	scrollDivsHorizontalInvisible = new Array();
	velocidadDivsHorizontalInvisible = new Array();

	for (key in listaScrollHorizontalInvisible[this.id]) 
	{
		scrollDivsHorizontalInvisible.push(document.getElementById(listaScrollHorizontalInvisible[this.id][key][0]));
		velocidadDivsHorizontalInvisible.push(listaScrollHorizontalInvisible[this.id][key][1]);
	}
	identificadorScrollHorizontalInvisible = setInterval('hacerScrollHorizontalInvisible()', 50);
}

function detenerScrollVerticalInvisible() 
{
	if (typeof(identificadorScrollVerticalInvisible) != "undefined")
		clearInterval(identificadorScrollVerticalInvisible);
}

function detenerScrollHorizontalInvisible() 
{
	if (typeof(identificadorScrollHorizontalInvisible) != "undefined")
		clearInterval(identificadorScrollHorizontalInvisible);
}

function hacerScrollVerticalInvisible() 
{
	for (key in scrollDivsVerticalInvisible) 
	{
		var desplazamientoActual = scrollDivsVerticalInvisible[key].scrollTop;
		var nuevoDesplazamiento = desplazamientoActual + velocidadDivsVerticalInvisible[key];
		scrollDivsVerticalInvisible[key].scrollTop = nuevoDesplazamiento;
	}
}

function hacerScrollHorizontalInvisible() 
{
	for (key in scrollDivsHorizontalInvisible) 
	{
		var desplazamientoActual = scrollDivsHorizontalInvisible[key].scrollLeft;
		var nuevoDesplazamiento = desplazamientoActual + velocidadDivsHorizontalInvisible[key];
		scrollDivsHorizontalInvisible[key].scrollLeft = nuevoDesplazamiento;
	}
}

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funcionalidad de scroll invisible en objetos div ---------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de manejo de campos de entrada de datos numéricas ---------------------------//
//----------------------------------------------------------------------------------------------------------//
var separadorDecimalesAplicacion = '<vgcutil:message key="formato.numero.separadordecimales" />';
var separadorMilesAplicacion = '<vgcutil:message key="formato.numero.separadormiles" />';
var codigoSeparadorDecimalesAplicacion = separadorDecimalesAplicacion.charCodeAt(0);
var codigoSeparadorMilesAplicacion = separadorMilesAplicacion.charCodeAt(0);

function validarEntradaNumeroEventoOnKeyPress(campoInput, evento, numeroDecimales, permiteNegativo) 
{
	var codigoCaracter = 0;
	var esSeparadorDecimales = false;
	var esSimboloNegativo = false;
	var esNumero = false;
	var existeSeparadorDecimales = false;

	if (window.event) // IE
		codigoCaracter = evento.keyCode;
	else if (evento.which) // Safari 4, Firefox 3.0.4 
		codigoCaracter = evento.which;
	if (codigoCaracter != null) 
	{
		if ((codigoCaracter == 44) || (codigoCaracter == 46)) 
			esSeparadorDecimales = true;
		else if (codigoCaracter == 45) 
			esSimboloNegativo = true;
		else if (codigoCaracter > 31) 
		{
			if ((codigoCaracter < 48) || (codigoCaracter > 57))
			{
				respuestaBooleanaEvento = false;
				return false;
			} 
			else 
				esNumero = true;
		}
	}
	if (campoInput.value != null) 
	{
		if (campoInput.value.indexOf(separadorDecimalesAplicacion) > -1) 
			existeSeparadorDecimales = true;
	} 
	else 
		campoInput.value = '';
	if (esSeparadorDecimales) 
	{
		if (numeroDecimales == 0) 
		{
			respuestaBooleanaEvento = false;
			return false;
		} 
		else 
		{
			if (existeSeparadorDecimales) 
			{
				respuestaBooleanaEvento = false;
				return false;
			} 
			else 
			{
				if (campoInput.value == '') 
					campoInput.value = '0';
				if (window.event) 
					window.event.keyCode = codigoSeparadorDecimalesAplicacion;
				else 
				{
					var newEvent = document.createEvent("KeyEvents");
					newEvent.initKeyEvent("keypress", true, true, document.defaultView, evento.ctrlKey, evento.altKey, evento.shiftKey, evento.metaKey, 0, codigoSeparadorDecimalesAplicacion);
					evento.preventDefault();
					evento.target.dispatchEvent(newEvent);
				}
			}
		}
	}
	if (esSimboloNegativo) 
	{
		if (permiteNegativo) 
		{
			if (campoInput.value != null) 
			{
				if (campoInput.value.indexOf('-') == -1) 
					campoInput.value = '-' + campoInput.value;
			} 
			else 
				campoInput.value = '-';
			respuestaBooleanaEvento = false;
			return false;
		} 
		else 
		{
			respuestaBooleanaEvento = false;
			return false;
		}
	}
	if (esNumero) 
	{
		if ((numeroDecimales > 0) && (existeSeparadorDecimales)) 
		{
			posicionSeparadorDecimales = campoInput.value.indexOf(separadorDecimalesAplicacion);
			var parteDecimal = campoInput.value.substring(posicionSeparadorDecimales);
			if ((parteDecimal.length - 1) >= numeroDecimales) 
			{
				var posicionCursor = getPosicionCursor(campoInput);
				if (posicionCursor > posicionSeparadorDecimales) 
				{
					respuestaBooleanaEvento = false;
					return false;
				}
			}
		}
		if (codigoCaracter == 48) 
		{
			// El numero es cero
			if (campoInput.value.length > 0) 
			{
				var primerCaracter = campoInput.value.substring(0, 1);
				if (getPosicionCursor(campoInput) == 0) 
				{
					if (primerCaracter != separadorDecimalesAplicacion) 
					{
						respuestaBooleanaEvento = false;
						return false;
					}
				} 
				else if (getPosicionCursor(campoInput) == 1) 
				{
					if (primerCaracter == '0') 
					{
						respuestaBooleanaEvento = false;
						return false;
					}
				}
			}
		}
	}
	return true;
}

function getPosicionCursor(campoInput) 
{
	var posicion = 0;
	if (campoInput.createTextRange) 
	{
		if (window.getSelection) 
		{
			var r = window.getSelection().toString();
			if (r == '') 
				posicion = campoInput.value.length;
			else 
				posicion = campoInput.value.lastIndexOf(r.text);
		}
		else
		{
			var r = document.selection.createRange().duplicate();
			r.moveEnd('character', campoInput.value.length);
			if (r.text == '') 
				posicion = campoInput.value.length;
			else 
				posicion = campoInput.value.lastIndexOf(r.text);
		}
	} 
	else 
		posicion = campoInput.selectionStart;
	return posicion;
}

/***  Sets the caret (cursor) position of the specified text field.
 **  Valid positions are 0-oField.length.
*/
function setPosicionCursor(campoInput, posicion) 
{

	if (document.selection) 
	{ 
		// IE Support
		// Set focus on the element
		campoInput.focus();

		// Create empty selection range
		var oSel = document.selection.createRange ();

		// Move selection start and end to 0 position
		oSel.moveStart ('character', -campoInput.value.length);

		// Move selection start and end to desired position
		oSel.moveStart ('character', posicion);
		oSel.moveEnd ('character', ((campoInput.value.length - posicion) * -1));

		oSel.select ();
	} 
	else if (campoInput.selectionStart || campoInput.selectionStart == '0') 
	{
	// Firefox support
		campoInput.selectionStart = posicion;
		campoInput.selectionEnd = posicion;
		campoInput.focus ();
	}
}

function validarEntradaNumeroEventoOnKeyUp(campoInput, evento, numeroDecimales, permiteNegativo, permitirSeparadorMiles) 
{
	if (typeof(permitirSeparadorMiles) == "undefined")
		permitirSeparadorMiles = true;
	var codigoCaracter = 0;
	var esEventoModificador = false;
	if (window.event) // IE 
		codigoCaracter = evento.keyCode;
	else if (evento.which)  // Safari 4, Firefox 3.0.4
		codigoCaracter = evento.which;
	if (codigoCaracter != null) 
	{
		// Codigos 46: delete
		if ((codigoCaracter == 44) || (codigoCaracter == 46)) 
			esEventoModificador = true;
		else if ((codigoCaracter == 188) || (codigoCaracter == 190)) 
			// En evento keyup, esto equivale a la tecla (.) y la tecla (,)
			esEventoModificador = true;
		else if (codigoCaracter == 45) 
			// En evento keyup, esto equivale a la tecla de separador decimal (.) en el numpad
			esEventoModificador = true;
		else if (codigoCaracter == 45) 
			esEventoModificador = true;
		else if (codigoCaracter == 8) 
			esEventoModificador = true;
		else if (codigoCaracter > 31) 
		{
			if  ((codigoCaracter > 47) && (codigoCaracter < 58)) 
				esEventoModificador = true;
			else if (codigoCaracter > 95 && codigoCaracter < 106) 
				// En evento keyup, esto equivale a los numeros del 0 al 9 en el numpad
				esEventoModificador = true;
		}
	}

	if ((campoInput.value != null) && (campoInput.value.length > 1) && (esEventoModificador)) 
	{
		// Se obtiene la posición del cursor en el campo input
		// var tamanoInicial = campoInput.value.length;
		var totalInicialSeparadoresMiles = 0;
		var totalFinalSeparadoresMiles = 0;
		var posicionCursor = getPosicionCursor(campoInput);
		var cambiarPosicionCursor = false;
		if (posicionCursor < campoInput.value.length) 
			// Solo se cambia la posición si el cursor no está al final del texto
			cambiarPosicionCursor = true;
		
		if (campoInput.value.length > 3) 
		{
			// Se construye la parte entera quitando los separadores de miles
			var parteEntera = '';
			for (var i = 0; i < campoInput.value.length; i++) 
			{
				var pedazo = campoInput.value.substring(i, i + 1);
				if (pedazo != separadorMilesAplicacion) 
					parteEntera = parteEntera + pedazo;
				else
					totalInicialSeparadoresMiles++;
			}
			// Colocacion de separadores de miles
			var posicionSeparadorDecimales = parteEntera.indexOf(separadorDecimalesAplicacion);
			campoInput.value = '';
			if (posicionSeparadorDecimales > -1) 
			{
				campoInput.value = parteEntera.substring(posicionSeparadorDecimales);
				parteEntera = parteEntera.substring(0, posicionSeparadorDecimales);
			}
			while (parteEntera.length > 3) 
			{
				if (permitirSeparadorMiles)
					campoInput.value = separadorMilesAplicacion + parteEntera.substring(parteEntera.length - 3, parteEntera.length) + campoInput.value;
				else
					campoInput.value = parteEntera.substring(parteEntera.length - 3, parteEntera.length) + campoInput.value;
				parteEntera = parteEntera.substring(0, parteEntera.length - 3);
				totalFinalSeparadoresMiles++;
			}
			campoInput.value = parteEntera + campoInput.value;
		}
		if (cambiarPosicionCursor) 
		{
			if (totalInicialSeparadoresMiles < totalFinalSeparadoresMiles) 
				posicionCursor++;
			if (totalInicialSeparadoresMiles > totalFinalSeparadoresMiles) 
			{
				if (posicionCursor > 0) 
					posicionCursor--;
			}
			setPosicionCursor(campoInput, posicionCursor);
		}
	}
	return true;
}

function validarEntradaNumeroEventoOnBlur(campoInput, evento, numeroDecimales, permiteNegativo) 
{
	// opcional, simplemente quita los ceros a la izquierda
	while (campoInput.value.length > 1) 
	{
		var primerCaracter = campoInput.value.substring(0, 1);
		var segundoCaracter = campoInput.value.substring(1, 2);
		if ((primerCaracter == '0') && (segundoCaracter != separadorDecimalesAplicacion)) 
			campoInput.value = campoInput.value.substring(1);
		else if (primerCaracter == separadorMilesAplicacion) 
			campoInput.value = campoInput.value.substring(1);
		else 
			break;
	}
}

function convertirNumeroFormateadoToNumero(numeroFormateado, esEntero) 
{
	var strNumero = '';
	if (numeroFormateado == null) 
		return null;

	if (numeroFormateado.length < 1) 
		return null;

	// Eliminar Separadores de Miles
	if (numeroFormateado.indexOf(separadorMilesAplicacion > -1)) 
	{
		for (var i = 0; i < numeroFormateado.length; i++) 
		{
			var pedazo = numeroFormateado.substring(i, i + 1);
			if (pedazo != separadorMilesAplicacion) 
				strNumero = strNumero + pedazo;
		}
	}
	var posicionSeparadorDecimales = strNumero.indexOf(separadorDecimalesAplicacion);
	if (posicionSeparadorDecimales > -1) 
	{
		var parteEntera = strNumero.substring(0, posicionSeparadorDecimales);
		strNumero = parteEntera + '.' + strNumero.substring(posicionSeparadorDecimales + 1);
	}
	if (esEntero) 
		return parseInt(strNumero);
	else 
		return parseFloat(strNumero);
}

function formatearNumero(strNumero, estaFormateado, numeroDecimales, permitirSeparadorMiles) 
{
	var strNumeroFormateado = '';
	if (strNumero != null)
		strNumeroFormateado = String(strNumero);
	if (typeof(permitirSeparadorMiles) == "undefined")
		permitirSeparadorMiles = true;

	if ((strNumeroFormateado != null) && (strNumeroFormateado.length > 0)) 
	{
		// Se eliminan los ceros a la izquierda
		var eliminarCeros = true;
		while (eliminarCeros) 
		{
			if (strNumero.length > 1) 
			{
				var primerCaracter = strNumeroFormateado.substring(0, 1);
				var segundoCaracter = strNumeroFormateado.substring(1, 2);
				if ((primerCaracter == '0') && (segundoCaracter != separadorDecimalesAplicacion)) 
					strNumeroFormateado = strNumeroFormateado.substring(1);
				else
					eliminarCeros = false;
			} 
			else 
				eliminarCeros = false;
		}
		var parteEntera = '';
		if (estaFormateado) 
		{
			// Se construye la parte entera quitando los separadores de miles
			for (var i = 0; i < strNumeroFormateado.length; i++) 
			{
				var pedazo = strNumeroFormateado.substring(i, i + 1);
				if (pedazo != separadorMilesAplicacion) 
					parteEntera = parteEntera + pedazo;
			}
			// Colocacion de separadores de miles
			var posicionSeparadorDecimales = parteEntera.indexOf(separadorDecimalesAplicacion);
			strNumeroFormateado = '';
			if (posicionSeparadorDecimales > -1) 
			{
				if (numeroDecimales > 0) 
				{
					strNumeroFormateado = parteEntera.substring(posicionSeparadorDecimales + 1, posicionSeparadorDecimales + 1 + numeroDecimales);
					if ((strNumeroFormateado.length) < numeroDecimales) 
					{
						while (strNumeroFormateado.length < numeroDecimales) 
							strNumeroFormateado = strNumeroFormateado + '0';
					}
					strNumeroFormateado = separadorDecimalesAplicacion + strNumeroFormateado;
				} 
				else 
					strNumeroFormateado = '';
				parteEntera = parteEntera.substring(0, posicionSeparadorDecimales);
			} 
			else 
			{
				if (numeroDecimales > 0) 
				{
					for (i = 0; i < numeroDecimales; i++) 
						strNumeroFormateado = strNumeroFormateado + '0';
					strNumeroFormateado = separadorDecimalesAplicacion + strNumeroFormateado;
				}
			}
		} 
		else 
		{
			var posicionSeparadorDecimales = strNumeroFormateado.indexOf('.');
			if (posicionSeparadorDecimales > -1) 
			{
				parteEntera = strNumeroFormateado.substring(0, posicionSeparadorDecimales);
				if (numeroDecimales > 0) 
				{
					strNumeroFormateado = strNumeroFormateado.substring(posicionSeparadorDecimales + 1, posicionSeparadorDecimales + 1 + numeroDecimales);
					if (strNumeroFormateado.length < numeroDecimales) 
					{
						while (strNumeroFormateado.length < numeroDecimales) 
							strNumeroFormateado = strNumeroFormateado + '0';
					}
					strNumeroFormateado = separadorDecimalesAplicacion + strNumeroFormateado;
				} 
				else 
					strNumeroFormateado = '';
			} 
			else 
			{
				parteEntera = strNumeroFormateado;
				strNumeroFormateado = '';
				if (numeroDecimales > 0) 
				{
					for (i = 0; i < numeroDecimales; i++) 
						strNumeroFormateado = strNumeroFormateado + '0';
					strNumeroFormateado = separadorDecimalesAplicacion + strNumeroFormateado;
				}
			}
		}
		while (parteEntera.length > 3) 
		{
			if (permitirSeparadorMiles)
				strNumeroFormateado = separadorMilesAplicacion + parteEntera.substring(parteEntera.length - 3, parteEntera.length) + strNumeroFormateado;
			else
				strNumeroFormateado = parteEntera.substring(parteEntera.length - 3, parteEntera.length) + strNumeroFormateado;
			parteEntera = parteEntera.substring(0, parteEntera.length - 3);
		}
		strNumeroFormateado = parteEntera + strNumeroFormateado;
	}
	return strNumeroFormateado;
}

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funcionalidad de manejo de campos de entrada de datos numéricas ---------------------------//
//----------------------------------------------------------------------------------------------------------//

//----------------------------------------------------------------------------------------------------------//
//------ INICIO: Funcionalidad de manejo de Frecuencia                           ---------------------------//
//----------------------------------------------------------------------------------------------------------//

function Frecuencia()
{
	this.Id = null;
	this.Nombre = null;
}

function frecuenciasCompatibles(frecuenciaId)
{
	var frecuencias = new Array();
	var frecuencia = null;
	switch (frecuenciaId) 
	{
		case "0":
			frecuencia = new Frecuencia();
			frecuencia.Id = "0";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.0" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "1";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.1" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "2";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.2" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "3";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.3" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "4";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.4" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "5";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.5" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
			break;
		case "1":
			frecuencia = new Frecuencia();
			frecuencia.Id = "1";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.1" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "2";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.2" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "3";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.3" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "4";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.4" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "5";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.5" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "2":
			frecuencia = new Frecuencia();
			frecuencia.Id = "2";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.2" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "3";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.3" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "4";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.4" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "5";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.5" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "3":
			frecuencia = new Frecuencia();
			frecuencia.Id = "3";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.3" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "4";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.4" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "5";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.5" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "4":
			frecuencia = new Frecuencia();
			frecuencia.Id = "4";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.4" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "5":
			frecuencia = new Frecuencia();
			frecuencia.Id = "5";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.5" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "6":
			frecuencia = new Frecuencia();
			frecuencia.Id = "6";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.6" />';
			frecuencias.push(frecuencia);

			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "7":
			frecuencia = new Frecuencia();
			frecuencia.Id = "7";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.7" />';
			frecuencias.push(frecuencia);
			
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
		case "8":
			frecuencia = new Frecuencia();
			frecuencia.Id = "8";
			frecuencia.Nombre = '<vgcutil:message key="jsp.asistente.grafico.frecuencia.8" />';
			frecuencias.push(frecuencia);
		  	break;
	}

	return frecuencias;
}


function truncarTexto(texto, longitudColumna) 
{
	var factorDivisor = 9;
	var longitudTexto = texto.length;
	var longitudResultante = parseInt(longitudColumna/factorDivisor);

	if (longitudTexto >= longitudResultante) 
		texto = texto.substring(0,longitudResultante) + '...';
	document.write(texto);
}

function truncarTxt(texto, longitudColumna) 
{
	var longitudTexto = texto.length;

	if (longitudTexto > longitudColumna) 
		texto = texto.substring(0,longitudColumna - 3) + '...';
	document.write(texto);
}

/// <summary>
/// Verifies if the browser supports/has enabled ActiveX components.
/// </summary>
function VerifyActiveX() 
{
	try 
	{
		// Instantiate all the ActiveX components used by the application
		new ActiveXObject("Microsoft.XMLDOM");
		new ActiveXObject("WScript.Shell");
		new ActiveXObject("WScript.Network");
		new ActiveXObject("Microsoft.XMLHTTP");

		return true;
	} 
	catch (e) 
	{
		// Hide login options and display message for ActiveX components not implemented.
		return false;
	}
}

function ConverStrToUnicode(texto)
{
	var unicodeString = '';
 	for (var i=0; i < texto.length; i++) 
 	{
   		var theUnicode = texto.charCodeAt(i).toString(16).toUpperCase();
   		while (theUnicode.length < 4) 
   		{
     		theUnicode = '0' + theUnicode;
   		}
   		theUnicode = '\\u' + theUnicode;
   		unicodeString += theUnicode;
 	}
	
 	return unicodeString;
}

function HtmlEntities(texto)
{ 
    var carac;
    var result = "";
    var string = texto;
    
    for(var i=0; i < texto.length; i++)
    { 
        carac = texto[i].charCodeAt(0); 
        if( (carac > 47 && carac < 58) || (carac > 62 && carac < 127) )
        	result += texto[i]; 
        else
        	result += "&#" + carac + ";"; 
    } 

    var rp = String(string);
	//
	rp = rp.replace(/á/g, '&aacute;');
	rp = rp.replace(/é/g, '&eacute;');
	rp = rp.replace(/í/g, '&iacute;');
	rp = rp.replace(/ó/g, '&oacute;');
	rp = rp.replace(/ú/g, '&uacute;');
	rp = rp.replace(/ñ/g, '&ntilde;');
	rp = rp.replace(/ü/g, '&uuml;');
	//
	rp = rp.replace(/Á/g, '&Aacute;');
	rp = rp.replace(/É/g, '&Eacute;');
	rp = rp.replace(/Í/g, '&Iacute;');
	rp = rp.replace(/Ó/g, '&Oacute;');
	rp = rp.replace(/Ú/g, '&Uacute;');
	rp = rp.replace(/Ñ/g, '&Ntilde;');
	rp = rp.replace(/Ü/g, '&Uuml;');
	//
	
	var str = String(string);
	str = str.replace('á','\u00e1');
	str = str.replace('é','\u00e9');
	str = str.replace('í','\u00ed');
	str = str.replace('ó','\u00f3');
	str = str.replace('ú','\u00fa');

	str = str.replace('Á','\u00c1');
	str = str.replace('É','\u00c9');
	str = str.replace('Í','\u00cd');
	str = str.replace('Ó','\u00d3');
	str = str.replace('Ú','\u00da');

	str = str.replace('ñ','\u00f1');
	str = str.replace('Ñ','\u00d1');	
	
    return str; 
}

function EliminarCaracteres(texto)
{ 
	var str = String(texto);
	str = str.replace('á','a');
	str = str.replace('é','e');
	str = str.replace('í','i');
	str = str.replace('ó','o');
	str = str.replace('ú','u');

	str = str.replace('Á','A');
	str = str.replace('É','E');
	str = str.replace('Í','I');
	str = str.replace('Ó','O');
	str = str.replace('Ú','U');

	str = str.replace('ñ','ñ');
	str = str.replace('Ñ','Ñ');	
	str = str.replace('Ñ','Ñ');
	str = str.replace('&','');
	str = str.replace('?','');
	
    return str; 
}

function UrlEncodeText(text) 
{
	return encodeURIComponent(text).replace(/!/g,  '%21')
                                   .replace(/'/g,  '%27')
                                   .replace(/\(/g, '%28')
                                   .replace(/\)/g, '%29')
                                   .replace(/\*/g, '%2A')
                                   .replace(/á/g, '%E1')
                                   .replace(/Á/g, '\u00c1')
                                   .replace(/%20/g, '+');
}

function mailtoUrl()
{
	var encode_mailto_component = function(str)
	{
		try{ return encodeURIComponent(str); }
		catch(e){ return escape(str); }
	};
	var AddressList = function()
	{
		var list = [];
		this.length = 0;
		this.add = function(address){
			if(address) {
				list.push(address);
				this.length = list.length;
			}
		};
		this.get = function(){
			return list.join(';');
		};
	};
	var subject = '',
		body = '',
		mainList = new AddressList(),
		ccList = new AddressList(),
		bccList = new AddressList();
	this.setSubject = function(str){ subject = encode_mailto_component(str); };
	this.setBody = function(str){ body = encode_mailto_component(str); };
	this.addMain = function(x) { mainList.add(x); };
	this.addCC = function(x) { ccList.add(x); };
	this.addBCC = function(x) { bccList.add(x); };
	this.getURL = function(allow_empty_mainList){
		var out = ['mailto:'];
		var extras = [];
		if(mainList.length === 0 && !allow_empty_mainList){
			throw('mailtoUrl: no main addressees');
		}
		else{
			out.push(mainList.get());
		}
		if(subject) { extras.push('subject=' + subject); }
		if(ccList.length) { extras.push('cc=' + ccList.get()); }
		if(bccList.length) { extras.push('bcc=' + bccList.get()); }
		if(body) { extras.push('body=' + body); }
		if(extras.length) { out.push('?' + extras.join('&')); }
		return out.join('');
	};
}

function configurarEmail()
{
	abrirVentanaModal('<html:rewrite action="/configuracion/sistema/email" />', "Email", 380, 190);
}

function closedApp()
{
	if (IE)
		window.opener.closeAppWindows();
	else
		window.close();
}

//----------------------------------------------------------------------------------------------------------//
//------    FIN: Funcionalidad de manejo de Frecuencia                           ---------------------------//
//----------------------------------------------------------------------------------------------------------//

// </script>
