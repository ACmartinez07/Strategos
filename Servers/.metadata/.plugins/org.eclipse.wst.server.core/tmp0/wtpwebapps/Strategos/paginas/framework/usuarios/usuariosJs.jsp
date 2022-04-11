<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (02/06/2012) -->

<script type="text/javascript">
	
	function abrirSelectorUsuarios(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, organizacionId, mostrarAdministradores, funcionCierre) 
	{
		if (organizacionId == null) 
			organizacionId = '';
		if (mostrarAdministradores == null) 
			mostrarAdministradores = '';
		if (funcionCierre == null) 
			funcionCierre = '';
		
		abrirVentanaModal('<html:rewrite action="/framework/usuarios/seleccionarUsuarios" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + '&organizacionId=' + organizacionId 
			+ '&mostrarAdministradores=' + mostrarAdministradores + '&funcionCierre=' + funcionCierre, 'seleccionarUsuarios', '600', '400');
	}
	
	// se añade opcion de seleccion para responsables
	function abrirSelectorUsuariosResponsables(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, desdeResponsable, funcionCierre) 
	{
		if (desdeResponsable == null) 
			desdeResponsable = 'false'; 
		if (funcionCierre == null) 
			funcionCierre = '';
		
		abrirVentanaModal('<html:rewrite action="/framework/usuarios/seleccionarUsuarios" />?nombreForma=' + nombreForma 
			+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
			+ '&seleccionados=' + seleccionados + '&desdeResponsable=' + desdeResponsable + '&funcionCierre=' + funcionCierre, 'seleccionarUsuarios', '800', '600');
	}
	
</script>