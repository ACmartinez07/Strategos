<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (05/09/2012) -->

<script language="javascript">

	function abrirSelectorEstadosAcciones(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) {	
	    var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}		
		abrirVentanaModal('<html:rewrite action="/estadosacciones/seleccionarEstadosAcciones" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarEstadosAcciones', '600', '400');		
	}

</script>