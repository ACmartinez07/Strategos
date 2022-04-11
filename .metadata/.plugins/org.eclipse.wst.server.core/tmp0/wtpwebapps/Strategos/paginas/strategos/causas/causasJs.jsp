<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (24/10/2012) -->

<script type="text/javascript">

	function abrirSelectorCausas(nombreForma, nombreCampoValor, nombreCampoOculto, funcionCierre) 
	{	
		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '')) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		abrirVentanaModal('<html:rewrite action="/causas/seleccionarCausas" />?nombreForma=' + nombreForma + '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto + parametroFuncionCierre, 'seleccionarCausas', '600', '400');			
	}

</script>