<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (02/06/2012) --%>

<%-- Representación de la Forma --%>
<html:form action="/iniciativas/mostrarGestionIniciativa" styleClass="formaHtmlCompleta">

	<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

		<%-- Visor Tipo Lista --%>
		<table class="listView" cellpadding="1">

			<%-- Encabezado del "Visor Tipo Lista" --%>
			<tr class="encabezadoListView" height="20px">

				<td width="120px">&nbsp;</td>
				<td width="120px" align="center"><vgcutil:message key='jsp.mostrargestioniniciativa.comienzo' /></td>
				<td width="120px" align="center"><vgcutil:message key='jsp.mostrargestioniniciativa.fin' /></td>
				<td width="120px" align="center"><vgcutil:message key='jsp.mostrargestioniniciativa.duracion' /></td>
			</tr>

		</table>

		<table>
			<tr class="barraFiltrosForma">
				<td align="right" width="120px"><vgcutil:message key='jsp.mostrargestioniniciativa.programado' /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="comienzoProgramadoFormateada" /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="finProgramadoFormateada" /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="duracionProgramado" /></td>
			</tr>

			<tr class="barraFiltrosForma">
				<td align="right" width="120px"><vgcutil:message key='jsp.mostrargestioniniciativa.ejecutado' /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="comienzoRealFormateada" /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="finRealFormateada" /></td>
				<td width="120px" align="center"><bean:write name="mostrarGestionIniciativaForm" property="duracionReal" /></td>
			</tr>
		</table>

		<tr>
			<td>&nbsp;</td>
		</tr>

		<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="center">Titulo</td>
			</tr>
			<tr>
				<td align="center">
				<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="0" border="0">
					<tr>
						<td><vgcutil:message key='jsp.mostrargestioniniciativa.completado' /></td>

						<td colspan="3"><html:text property="completado" size="5" disabled="true" maxlength="3" styleClass="cuadroTexto" /></td>
					</tr>
					<tr>
						<td><vgcutil:message key='jsp.mostrargestioniniciativa.alerta' /></td>
						<td align="center"><img src="<html:rewrite page='/paginas/strategos/iniciativas/imagenes/alertaAmarilla.gif'/>" border="0" width="10" height="10"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr height="10px">
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>

	</vgcinterfaz:contenedorForma>

</html:form>
