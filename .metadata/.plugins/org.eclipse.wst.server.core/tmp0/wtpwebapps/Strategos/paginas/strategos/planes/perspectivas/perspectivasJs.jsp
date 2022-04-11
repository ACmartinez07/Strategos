<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (15/10/2012) -->

<script type="text/javascript">
	
	function abrirSelectorPerspectivas(nombreForma, nombreCampoValores, nombreCampoIds, nombreCampoRutaCompleta, funcionCierre, seleccionado, queryStringFiltros) 
	{		
		var parametroNombreCampoRutaCompleta = '';
		if ((nombreCampoRutaCompleta != null) && (nombreCampoRutaCompleta != '')) 
			parametroNombreCampoRutaCompleta = '&nombreCampoRutaCompleta=' + nombreCampoRutaCompleta;
		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '')) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		var parametroSeleccionado = '';
		if ((seleccionado != null) && (seleccionado != '')) 
			parametroSeleccionado = '&seleccionado=' + seleccionado;
		if (queryStringFiltros == null) 
			queryStringFiltros = '';
		else if (queryStringFiltros.indexOf('&') != 0) 
			queryStringFiltros = '&' + queryStringFiltros;
		abrirVentanaModal('<html:rewrite action="/planes/perspectivas/seleccionarPerspectivas" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValores + '&nombreCampoOculto=' + nombreCampoIds
			+ parametroNombreCampoRutaCompleta + parametroFuncionCierre + parametroSeleccionado
			+ queryStringFiltros, 'seleccionarPerspectivas', '730', '575');
	}
	
</script>