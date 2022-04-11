<!-- Creado por: Kerwin Arias (01/07/2012) -->
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ page import="java.util.Date"%>
<script type="text/javascript">
	//----------------------------------------------------------------------------------------------------------//
	//------ INICIO: Funcionalidad de manejo de AJAX -----------------------------------------------------------//
	//----------------------------------------------------------------------------------------------------------//
	function ajaxSendRequestReceiveInput(metodoGetPost, url, objetoInput) 
	{
		var xmlHttp;
		var abortado = false;
	
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
				} catch (e) 
				{
					alert("El navegador utilizado no soporta AJAX!");
					return false;
				}
			}
		};
		
		// Tiempo de espera de 2 minutos
		var xmlHttpRequestTimer = setTimeout(function() { alert('Tiempo de Espera agotado para respuesta AJAX'); abortado = true; xmlHttp.abort();}, 2 * 60 * 1000);
		xmlHttp.onreadystatechange = function() {
			if(xmlHttp.readyState == 4) 
			{
				clearTimeout(xmlHttpRequestTimer);
				if (!abortado) 
				{
					if (objetoInput != null) 
						objetoInput.value = xmlHttp.responseText;
				}
			}
	    };
		xmlHttp.open(metodoGetPost, url, true);
		xmlHttp.send(null);
	}
	
	// EJEMPLO:	ajaxSendRequestReceiveInput('GET', '<html:rewrite action="/indicadores/seleccionarIndicadores" />?ajax=true', document.forms[0].testAjax)	
	function ajaxSendRequestReceiveInputSincronica(metodoGetPost, url, objetoInput, llamadaFuncionSincronica) 
	{
		var xmlHttp;
		var abortado = false;
	
		try 
		{  // Firefox, Opera 8.0+, Safari
			xmlHttp = new XMLHttpRequest();
		} 
		catch (e) 
		{  // Internet Explorer
			try 
			{
				xmlHttp= new ActiveXObject("Msxml2.XMLHTTP");
			} 
			catch (e) 
			{
				try 
				{
					xmlHttp= new ActiveXObject("Microsoft.XMLHTTP");
				} 
				catch (e) 
				{
					alert("El navegador utilizado no soporta AJAX!");
					return false;
				}
			}
		};
		
		// Tiempo de espera de 2 minutos
		var xmlHttpRequestTimer = setTimeout(function() { alert('Tiempo de Espera agotado para respuesta AJAX'); abortado = true; xmlHttp.abort();}, 2 * 60 * 1000);
		xmlHttp.onreadystatechange = function() {
			if(xmlHttp.readyState == 4) 
			{
				clearTimeout(xmlHttpRequestTimer);
				if (!abortado) 
				{
					if (objetoInput != null) 
						objetoInput.value = xmlHttp.responseText;
					eval(llamadaFuncionSincronica);
				}
			}
	    };
	    url = url + '&tsAjax=<%= (new Date()).getTime() %>';
		xmlHttp.open(metodoGetPost, url, true);
		xmlHttp.send(null);
	}
	
	//----------------------------------------------------------------------------------------------------------//
	//------ FIN: Funcionalidad de manejo de AJAX --------------------------------------------------------------//
	//----------------------------------------------------------------------------------------------------------//	
</script>
