<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (30/05/2012) -->

<script type="text/javascript">

function abrirSelectorClasesIndicadores(nombreForma, nombreCampoValor, nombreCampoOculto, claseId, organizacionId, funcionCierre, permitirCambiarClase, permitirCambiarOrganizacion) 
{
	if (organizacionId == null) 
		organizacionId = "";
	if (permitirCambiarOrganizacion == undefined)
		permitirCambiarOrganizacion = false;
	if (permitirCambiarClase == undefined)
		permitirCambiarClase = false;

	var parametroFuncionCierre = '';
	if ((funcionCierre != null) && (funcionCierre != '')) 
		parametroFuncionCierre = '&funcionCierre=' + funcionCierre;

	abrirVentanaModal('<html:rewrite action="/indicadores/seleccionarClasesOrganizaciones" />?nombreForma=' + nombreForma 
		+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto + '&permitirCambiarOrganizacion=' + permitirCambiarOrganizacion +
		'&permitirCambiarClase=' + permitirCambiarClase + '&claseId=' + claseId + '&organizacionId=' + organizacionId + parametroFuncionCierre, 'seleccionarClasesIndicadores', '600', '400');
}

</script>
