<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (12/09/2012) -->
<script type="text/javascript">

	function abrirSelectorIndicadores(nombreForma, nombreCampoValores, nombreCampoIds, nombreCampoRutasCompletas, funcionCierre, seleccionados, nombreCampoOrganizacion, nombreCampoOrganizacionId, nombreCampoClase, nombreCampoClaseId, queryStringFiltros, planesIds) 
	{
		var parametroNombreCampoRutasCompletas = '';
		if ((nombreCampoRutasCompletas != null) && (nombreCampoRutasCompletas != '') && (typeof nombreCampoRutasCompletas != "undefined")) 
			parametroNombreCampoRutasCompletas = '&nombreCampoRutasCompletas=' + nombreCampoRutasCompletas;

		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '') && (typeof funcionCierre != "undefined")) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;

		var parametroSeleccionados = '';
		if ((seleccionados != null) && (seleccionados != '') && (typeof seleccionados != "undefined")) 
			parametroSeleccionados = '&seleccionados=' + seleccionados;
		
		var parametroPlanesIds = '';
		if ((planesIds != null) && (planesIds != '') && (typeof planesIds != "undefined")) 
			parametroPlanesIds = '&planesIds=' + planesIds;
		
		if (queryStringFiltros == null) 
			queryStringFiltros = '';
		else if (queryStringFiltros.indexOf('&') != 0 && typeof queryStringFiltros != "undefined") 
			queryStringFiltros = '&' + queryStringFiltros;
		
		if (typeof nombreForma == "undefined")
			nombreForma = "";
		if (typeof nombreCampoValores == "undefined")
			nombreCampoValores = "";
		if (typeof nombreCampoIds == "undefined")
			nombreCampoIds = "";
		
		abrirVentanaModal('<html:rewrite action="/indicadores/seleccionarIndicadores" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValores + '&nombreCampoOculto=' + nombreCampoIds
			+ parametroNombreCampoRutasCompletas + parametroFuncionCierre + parametroSeleccionados + parametroPlanesIds
			+ queryStringFiltros, 'seleccionarIndicadores', '800', '600');
	}

</script>