<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (29/10/2012) -->

<table class="barraIzquierdaPrincipal" cellpadding="6" cellspacing="6">
	<tr valign="top" height="20px">
		<td><img
			src="<html:rewrite page='/componentes/comunes/vineta.gif'/>"
			border="0" width="10" height="10" title="Título de Módulo"> <a
			onMouseOver="this.className='mouseEncimaBarraIzquierdaPrincipal'"
			onMouseOut="this.className='mouseFueraBarraIzquierdarincipal'"
			href="javascript:alert('Vínculo a Módulo 1');" target="_self"
			class="mouseFueraBarraIzquierdarincipal">Título de Módulo 1</a></td>
	</tr>
	<tr valign="top" height="20px">
		<td><img
			src="<html:rewrite page='/componentes/comunes/vineta.gif'/>"
			border="0" width="10" height="10" title="Título de Módulo"> <a
			onMouseOver="this.className='mouseEncimaBarraIzquierdaPrincipal'"
			onMouseOut="this.className='mouseFueraBarraIzquierdarincipal'"
			href="javascript:alert('Vínculo a Módulo 2');" target="_self"
			class="mouseFueraBarraIzquierdarincipal">Título de Módulo 2</a></td>
	</tr>
	<tr valign="top" height="20px">
		<td>
			<hr width="100%">
		</td>
	</tr>
	<tr valign="top" height="20px">
		<td><img
			src="<html:rewrite page='/componentes/comunes/vineta.gif'/>"
			border="0" width="10" height="10" title="Título de Módulo"> <a
			onMouseOver="this.className='mouseEncimaBarraIzquierdaPrincipal'"
			onMouseOut="this.className='mouseFueraBarraIzquierdarincipal'"
			href="<html:rewrite page='/paginas/framework/administracion.jsp'/>" target="_self"
			class="mouseFueraBarraIzquierdarincipal">Adm del Sistema</a></td>
	</tr>	
	<tr valign="top">
		<td></td>
	</tr>
</table>