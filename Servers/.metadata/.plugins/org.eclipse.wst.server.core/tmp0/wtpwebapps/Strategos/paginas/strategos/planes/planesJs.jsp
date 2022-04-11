<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (29/09/2012) -->

<script language="javascript">
	
	function abrirSelectorPlanes(nombreForma, nombreCampoValores, nombreCampoIds, nombreCampoRutasCompletas, funcionCierre, seleccionados, queryStringFiltros) {		
		var parametroNombreCampoRutasCompletas = '';
		if ((nombreCampoRutasCompletas != null) && (nombreCampoRutasCompletas != '')) {
			parametroNombreCampoRutasCompletas = '&nombreCampoRutasCompletas=' + nombreCampoRutasCompletas;
		}
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}
		var parametroSeleccionados = ''
		if ((seleccionados != null) && (seleccionados != '')) {
			parametroSeleccionados = '&seleccionados=' + seleccionados;
		}
		if (queryStringFiltros == null) {
			queryStringFiltros = '';
		} else if (queryStringFiltros.indexOf('&') != 0) {
			queryStringFiltros = '&' + queryStringFiltros;
		}
		abrirVentanaModal('<html:rewrite action="/planes/seleccionarPlanes" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValores + '&nombreCampoOculto=' + nombreCampoIds
			+ parametroNombreCampoRutasCompletas + parametroFuncionCierre + parametroSeleccionados
			+ queryStringFiltros, 'seleccionarPlanes', '755', '590');
	}

</script>