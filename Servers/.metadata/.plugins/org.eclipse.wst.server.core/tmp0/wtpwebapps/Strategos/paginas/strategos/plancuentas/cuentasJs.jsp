<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (28/10/2012) -->

<script language="javascript">

	function abrirSelectorCuentas(nombreForma, nombreCampoValor, nombreCampoOculto, funcionCierre) {	    
	    var parametroFuncionCierre = ''
			if ((funcionCierre != null) && (funcionCierre != '')) {
				parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
			}	        
		abrirVentanaModal('<html:rewrite action="/plancuentas/seleccionarCuentas" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto + parametroFuncionCierre, 'seleccionarCuentas', '600', '400');			
	}

</script>