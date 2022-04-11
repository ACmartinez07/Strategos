<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/08/2012) --%>

<%-- Funciones JavaScript locales de la página Jsp --%>
<script language="JavaScript" type="text/javascript">
	
	function eventoClickIniciativa(nombreObjetoFila) 
	{
		var objetoFila = document.getElementById(nombreObjetoFila);
		eventoClickFilaV2(document.seleccionarActividadForm.iniciativaSeleccionadaId, null, 'tablaIniciativas', objetoFila);
		seleccionarActividadForm.submit();
	}

</script>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionaractividades.panel.iniciativas.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" messageKeyNoElementos="jsp.seleccionaractividades.noregistrosiniciativas" nombre="tablaIniciativas" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="400" onclick="javascript:consultar(seleccionarActividadForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionaractividades.iniciativas.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'frecuencia', null);" nombre="frecuencia">
			<vgcutil:message key="jsp.seleccionaractividades.iniciativas.columna.frecuencia" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'naturaleza', null);" nombre="naturaleza">
			<vgcutil:message key="jsp.seleccionaractividades.iniciativas.columna.naturaleza" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="iniciativa" property="iniciativaId" />
			</vgcinterfaz:visorListaFilaId>
			<vgcinterfaz:visorListaFilaEventoOnclick>				
				eventoClickIniciativa('<bean:write name="iniciativa" property="iniciativaId" />');								
			</vgcinterfaz:visorListaFilaEventoOnclick>			
			<vgcinterfaz:visorListaFilaEventoOnmouseout>
				eventoMouseFueraFilaV2(document.seleccionarActividadForm.iniciativaSeleccionadaId, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseout>			
			<vgcinterfaz:visorListaFilaEventoOnmouseover>
				eventoMouseEncimaFilaV2(document.seleccionarActividadForm.iniciativaSeleccionadaId, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseover>			
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<img name="img<bean:write name="iniciativa" property="iniciativaId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<bean:write name="iniciativa" property="nombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
				<bean:write name="iniciativa" property="frecuenciaNombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
				<bean:write name="iniciativa" property="naturalezaNombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
		</vgcinterfaz:filasVisorLista>

	</vgcinterfaz:visorLista>

</vgcinterfaz:contenedorForma>
