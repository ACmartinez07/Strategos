<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (22/10/2012) -->
<script type="text/javascript">

	function abrirSelectorResponsables(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, organizacionId, mostrarGrupos, mostrarGlobales, funcionCierre) 
	{
		if (organizacionId == null) 
			organizacionId = "";
		else 
			organizacionId = '&organizacionId=' + organizacionId;
		if (mostrarGrupos == null) 
			mostrarGrupos = '';
		else 
			mostrarGrupos = '&mostrarGrupos=' + mostrarGrupos;
		if (mostrarGlobales == null) 
			mostrarGlobales = '';
		else 
			mostrarGlobales = '&mostrarGlobales=' + mostrarGlobales;
		var parametroFuncionCierre = '';
		if ((funcionCierre != null) && (funcionCierre != '')) 
			parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
		
		abrirVentanaModal('<html:rewrite action="/responsables/seleccionarResponsables" />?nombreForma=' + nombreForma
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto
			+ '&seleccionados=' + seleccionados + organizacionId + mostrarGrupos + mostrarGlobales + parametroFuncionCierre, 'seleccionarResponsables', '600', '400');
	}

</script>
