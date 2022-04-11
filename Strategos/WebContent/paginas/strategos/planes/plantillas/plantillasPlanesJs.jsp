<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (31/10/2012) -->

<script language="javascript">

	function abrirSelectorPlantillasPlanes(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) 
	{		
		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '')) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		
		abrirVentanaModal('<html:rewrite action="/planes/plantillas/seleccionarPlantillasPlanes" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarPlantillasPlanes', '600', '400');
	}

</script>