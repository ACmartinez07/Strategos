<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (13/10/2012) -->

<%-- Bloqueo En espera --%>
<center>
	<div id="bloqueoEspera" style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; visibility: hidden; z-index: 2" align="center">
		<table style="background-image : url(<html:rewrite page='/componentes/mensajes/bloqueoEspera/fondo.gif' />); width: 100%; height: 100%;">
			<tr>
				<td align="center" valign="middle">
					<vgcinterfaz:contenedorForma width="180px" height="72px" mostrarBarraSuperior="false">
						<%-- Borde del Mensaje --%>
						<table class="bordeFichaDatos" style="text-align: center; border-spacing:5px; border-collapse: separate; padding: 3px; height: 30px;">
							<tr>
								<td style="vertical-align: middle; width: 19px; height: 19px; background-image: url(<html:rewrite page='/componentes/mensajes/bloqueoEspera/enEspera.gif' />);" id="imagenEnEspera">&nbsp;</td>
								<td style="vertical-align: middle;">&nbsp;&nbsp;<vgcutil:message key="jsp.bloqueoespera.espereporfavor" /></td>
							</tr>
						</table>		
					</vgcinterfaz:contenedorForma>
				</td>
			</tr>
		</table>
	</div>
</center>

<script type="text/javascript">

	function activarBloqueoEspera() {
		var obj = findObjetoHtml('bloqueoEspera');
		var obj2 = findObjetoHtml('imagenEnEspera');
		obj.style.visibility = 'visible';
		obj2.style.visibility = 'visible';
		obj2.focus();
	}
	
	function desactivarBloqueoEspera() {
		var obj = findObjetoHtml('bloqueoEspera');
		var obj2 = findObjetoHtml('imagenEnEspera');
		obj.style.visibility = 'hidden';
		obj2.style.visibility = 'hidden';
	}

</script>
