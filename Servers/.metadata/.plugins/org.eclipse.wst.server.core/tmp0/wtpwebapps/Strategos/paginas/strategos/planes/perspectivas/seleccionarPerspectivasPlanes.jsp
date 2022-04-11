<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (15/10/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript">
	
	function eventoClickPlan(nombreObjetoFila) {
	    var objetoFila = document.getElementById(nombreObjetoFila);
		eventoClickFilaV2(document.seleccionarPerspectivasForm.planSeleccionadoId, null, 'tablaPlanes', objetoFila);
		seleccionarPerspectivasForm.submit();
	}

</script>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarperspectivas.panel.planes.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaPlanes"  messageKeyNoElementos="jsp.seleccionarperspectivas.noregistrosplanes" nombre="tablaPlanes" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="390" onclick="javascript:consultarV2(seleccionarPerspectivasForm, seleccionarPerspectivasForm.paginaPlanes, seleccionarPerspectivasForm.atributoOrdenPlanes, seleccionarPerspectivasForm.tipoOrdenPlanes, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionarperspectivas.planes.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultarV2(seleccionarPerspectivasForm, seleccionarPerspectivasForm.paginaPlanes, seleccionarPerspectivasForm.atributoOrdenPlanes, seleccionarPerspectivasForm.tipoOrdenPlanes, 'anoInicial', null);" nombre="anoInicial">
			<vgcutil:message key="jsp.seleccionarperspectivas.planes.columna.anoinicial" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultarV2(seleccionarPerspectivasForm, seleccionarPerspectivasForm.paginaPlanes, seleccionarPerspectivasForm.atributoOrdenPlanes, seleccionarPerspectivasForm.tipoOrdenPlanes, 'anoFinal', null);" nombre="anoFinal">
			<vgcutil:message key="jsp.seleccionarperspectivas.planes.columna.anofinal" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultarV2(seleccionarPerspectivasForm, seleccionarPerspectivasForm.paginaPlanes, seleccionarPerspectivasForm.atributoOrdenPlanes, seleccionarPerspectivasForm.tipoOrdenPlanes, 'revision', null);" nombre="revision">
			<vgcutil:message key="jsp.seleccionarperspectivas.planes.columna.revision" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="plan">
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="plan" property="planId" />
			</vgcinterfaz:visorListaFilaId>
			<vgcinterfaz:visorListaFilaEventoOnclick>
				eventoClickPlan('<bean:write name="plan" property="planId" />');
			</vgcinterfaz:visorListaFilaEventoOnclick>
			<vgcinterfaz:visorListaFilaEventoOnmouseout>
				eventoMouseFueraFilaV2(document.seleccionarPerspectivasForm.planSeleccionadoId, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseout>
			<vgcinterfaz:visorListaFilaEventoOnmouseover>
				eventoMouseEncimaFilaV2(document.seleccionarPerspectivasForm.planSeleccionadoId, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseover>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<img name="img<bean:write name="plan" property="planId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<bean:write name="plan" property="nombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoInicial">
				<bean:write name="plan" property="anoInicial" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoFinal">
				<bean:write name="plan" property="anoFinal" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista align="center" nombre="revision">
				<bean:write name="plan" property="revision" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
		</vgcinterfaz:filasVisorLista>
		
	</vgcinterfaz:visorLista>

</vgcinterfaz:contenedorForma>