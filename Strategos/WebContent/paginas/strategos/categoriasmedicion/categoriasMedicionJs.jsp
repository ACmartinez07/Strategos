<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (22/10/2012) -->

<script language="javascript">

	function abrirSelectorCategoriasMedicion(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) {		
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}				
		abrirVentanaModal('<html:rewrite action="/categoriasmedicion/seleccionarCategoriasMedicion" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarCategoriasMedicion', '600', '400');		
	}

</script>