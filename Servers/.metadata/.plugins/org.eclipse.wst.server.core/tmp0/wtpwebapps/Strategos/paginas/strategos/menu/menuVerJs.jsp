<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (20/10/2012) -->

<script type="text/javascript">

	function gestionarUnidadesMedida() 
	{	
		window.location.href="<html:rewrite action='/unidadesmedida/gestionarUnidadesMedida'/>?defaultLoader=true";
	}
	
	function gestionarCategoriasMedicion() 
	{
		window.location.href="<html:rewrite action='/categoriasmedicion/gestionarCategoriasMedicion'/>?defaultLoader=true";
	}
	
	function gestionarResponsables() 
	{	
		window.location.href="<html:rewrite action='/responsables/gestionarResponsables'/>?defaultLoader=true";
	}
	
	function gestionarEstadosAcciones() 
	{
		window.location.href="<html:rewrite action='/estadosacciones/gestionarEstadosAcciones'/>?defaultLoader=true";
	}
			
    function gestionarEstatusIniciativas()
    {
    	window.location.href="<html:rewrite action='/iniciativas/estatus/gestionarEstatus'/>?defaultLoader=true";
    }
	
	function gestionarCausas() 
	{
		window.location.href="<html:rewrite action='/causas/gestionarCausas'/>?defaultLoader=true";
	}
	
	function gestionarCuentas() 
	{	
		window.location.href="<html:rewrite action='/plancuentas/gestionarCuentas'/>?defaultLoader=true";
	}
	
	function gestionarSeriesTiempo() 
	{	
		window.location.href="<html:rewrite action='/seriestiempo/gestionarSeriesTiempo'/>?defaultLoader=true";
	}
		
</script>
