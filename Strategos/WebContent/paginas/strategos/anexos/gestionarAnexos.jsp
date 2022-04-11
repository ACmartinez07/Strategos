<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/11/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionaranexos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/foros/Foro.js'/>"></script>
		<script type="text/javascript">

			var objetoId = '<bean:write name="gestionarAnexosForm" property="objetoId" />';
			var objetoKey = '<bean:write name="gestionarAnexosForm" property="objetoKey" />';
			
			function gestionarForos() 
			{
				var foro = new Foro();
				foro.url = '<html:rewrite action="/foros/gestionarForos" />';
				foro.ShowList(true, objetoId, objetoKey);
			}
		
			function gestionarExplicaciones() 
			{
				var explicacion = new Explicacion();
				explicacion.url = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>';
				explicacion.ShowList(true, objetoId, objetoKey, 0);
			}
		
		</script>

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/anexos/gestionarAnexos" styleClass="formaHtml">

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>	
					..:: <vgcutil:message key="jsp.gestionaranexos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table width="100%" cellpadding="1" cellspacing="0">

						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.gestionaranexos.columna.organizacion" /></b></td>
							<td align="left" colspan="3"><bean:write name="organizacion" property="nombre" /></td>
						</tr>

						<tr class="barraFiltrosForma">
							<td><b><vgcutil:message key="jsp.gestionaranexos.columna.objeto" /></b></td>
							<td colspan="3"><bean:write name="gestionarAnexosForm" property="objetoKey" /></td>
						</tr>

						<tr class="barraFiltrosForma">
							<td><b><vgcutil:message key="jsp.gestionaranexos.columna.nombre" /></b></td>
							<td colspan="3"><bean:write name="gestionarAnexosForm" property="nombreObjeto" /></td>
						</tr>
						
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="1">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">
						<td colspan="2" width="200px" align="center"><vgcutil:message key='jsp.gestionaranexos.columna.anexos' /></td>
					</tr>

				</table>

				<table>
					<%-- 
					<tr class="barraFiltrosForma">
						<td align="right" width="100px"><vgcutil:message key='jsp.gestionaranexos.columna.foros' /></td>
						<td align="center" width="100px"><img onClick="javascript:gestionarForos();" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/anexos/imagenes/tieneForos.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.foros.alt" />"></td>
					</tr>
					--%>
					<tr class="barraFiltrosForma">
						<td align="right" width="100px"><vgcutil:message key='jsp.gestionaranexos.columna.explicaciones' /></td>
						<td align="center" width="100px"><img onClick="javascript:gestionarExplicaciones();" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/anexos/imagenes/tieneExplicaciones.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.explicaciones.alt" />"></td>
					</tr>
				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>