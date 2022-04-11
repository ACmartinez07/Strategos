<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (25/10/2012) -->

<script language="javascript">

	function abrirSelectorSeriesTiempo(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) {
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}
		abrirVentanaModal('<html:rewrite action="/seriestiempo/seleccionarSeriesTiempo" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarSeriesTiempo', '600', '400');
	}

</script>