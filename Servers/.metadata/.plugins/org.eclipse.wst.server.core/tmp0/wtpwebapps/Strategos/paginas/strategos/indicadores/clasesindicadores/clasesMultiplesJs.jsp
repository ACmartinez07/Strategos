<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (30/05/2012) -->

<script language="javascript">

function abrirSelectorClases(nombreForma, nombreCampoValor, nombreCampoOculto, claseId, organizacionId, funcionCierre) 
{
	if (organizacionId == null) 
		organizacionId = "";

	var parametroFuncionCierre = '';
	if ((funcionCierre != null) && (funcionCierre != '')) 
		parametroFuncionCierre = '&funcionCierre=' + funcionCierre;

	abrirVentanaModal('<html:rewrite action="/indicadores/seleccionarMultiplesClases" />?nombreForma=' + nombreForma 
		+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
		+ '&claseId=' + claseId + '&organizacionId=' + organizacionId + parametroFuncionCierre, 'seleccionarMultiplesClases', '600', '400');
}

</script>
