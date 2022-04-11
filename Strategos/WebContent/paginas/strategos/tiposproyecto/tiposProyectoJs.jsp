<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (22/10/2012) -->

<script language="javascript">

	function abrirSelectorTiposProyecto(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) {		
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}				
		abrirVentanaModal('<html:rewrite action="/tiposproyecto/seleccionarTiposProyecto" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarTiposProyecto', '600', '400');		
	}

</script>