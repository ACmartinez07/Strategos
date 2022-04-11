<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (24/10/2012) -->

<script language="javascript">

	function editarClave() {
		window.location.href = '<html:rewrite action="/framework/usuarios/cambiarClaveUsuario" />';
	}
	
	function editarMensajeEmail() {		
		abrirVentanaModal("<html:rewrite action='/problemas/seguimientos/modificarConfiguracionMensajeEmailSeguimientos' />", "mensaje", "460", "320");		
	}

</script>
