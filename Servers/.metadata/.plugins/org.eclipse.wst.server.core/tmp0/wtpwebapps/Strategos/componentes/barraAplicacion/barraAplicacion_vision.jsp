<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (25/03/2012) -->

<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table border="0">
			<tr>
				<td><img src="<html:rewrite page='/componentes/barraAplicacion/logoAplicacion22.jpg'/>" border="0"></td>
			</tr>
		</table>
		</td>
		<td align="center" class="mouseFueraBarraAplicacionPrincipal">
			&nbsp;
		</td>
		<td width="300px" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/inicio.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.inicio.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="inicio()"><vgcutil:message key="jsp.barraaplicacion.inicio" /></a></td>
				<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/manual.gif'/>" border="0" width="10" height="10" title="<vgcutil:message	key="jsp.barraaplicacion.manual.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="<html:rewrite page='/paginas/radar/manualenlinea/ManualOnLine.pdf'/>" class="mouseFueraBarraAplicacionPrincipal"><vgcutil:message key="jsp.barraaplicacion.manual" /></a></td>
				<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/acercade.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.acerca.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="acerca()"><vgcutil:message key="jsp.barraaplicacion.acerca" /></a></td>
				<td align="center"><img src="<html:rewrite page='/componentes/barraAplicacion/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.barraaplicacion.salir.alt" />"> <a onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'" href="#" class="mouseFueraBarraAplicacionPrincipal" onclick="salir()"><vgcutil:message key="jsp.barraaplicacion.salir" /></a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
