<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (21/10/2012) -->

<script type="text/javascript">

	function abrirSelectorOrganizaciones(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, organizacionId, funcionCierre) {		
		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}
		if (organizacionId == null) {
			organizacionId = '';
		}
		abrirVentanaModal('<html:rewrite action="/organizaciones/seleccionarOrganizaciones" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + '&organizacionId=' + organizacionId + parametroFuncionCierre, 'seleccionarOrganizaciones', '600', '400');			
	}

</script>