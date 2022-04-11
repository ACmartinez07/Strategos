<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (27/01/2012) -->

<script language="javascript">
	
	function abrirSelectorActividades(nombreForma, nombreCampoValores, nombreCampoIds, nombreCampoRutasCompletas, nombreCampoPlanId, funcionCierre, seleccionados, queryStringFiltros) 
	{		
		var parametroNombreCampoRutasCompletas = '';
		if ((nombreCampoRutasCompletas != null) && (nombreCampoRutasCompletas != '')) 
			parametroNombreCampoRutasCompletas = '&nombreCampoRutasCompletas=' + nombreCampoRutasCompletas;
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		var parametroSeleccionados = ''
		if ((seleccionados != null) && (seleccionados != '')) 
			parametroSeleccionados = '&seleccionados=' + seleccionados;
		var parametroPlanId = ''
		if ((nombreCampoPlanId != null) && (nombreCampoPlanId != '')) 
			parametroPlanId = '&nombreCampoPlanId=' + nombreCampoPlanId;
		if (queryStringFiltros == null) 
			queryStringFiltros = '';
		else if (queryStringFiltros.indexOf('&') != 0) 
			queryStringFiltros = '&' + queryStringFiltros;

		abrirVentanaModal('<html:rewrite action="/actividades/selector/seleccionarActividad" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValores + '&nombreCampoOculto=' + nombreCampoIds
			+ parametroNombreCampoRutasCompletas + parametroFuncionCierre + parametroSeleccionados + parametroPlanId
			+ queryStringFiltros, 'seleccionarActividad', '730', '595');			
	}

</script>