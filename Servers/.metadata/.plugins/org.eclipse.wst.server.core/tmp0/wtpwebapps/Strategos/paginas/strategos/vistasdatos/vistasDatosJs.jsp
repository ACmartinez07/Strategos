<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (11/09/2012) -->

<script type="text/javascript">

	function abrirSelectorMiembro(url, nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre, parametrosComplementarios) 
	{
		var parametroFuncionCierre = ''
		if ((funcionCierre != null) && (funcionCierre != '')) {
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		}
		
		var agregarParametrosComplementarios = '';
		if ((parametrosComplementarios != null) && (parametrosComplementarios != '')) {
			agregarParametrosComplementarios = '&' + parametrosComplementarios;
		}
				
		abrirVentanaModal(url + '?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto
			+ '&seleccionados=' + seleccionados + parametroFuncionCierre + agregarParametrosComplementarios,'seleccionarPersonas', '750', '650');
	}

</script>