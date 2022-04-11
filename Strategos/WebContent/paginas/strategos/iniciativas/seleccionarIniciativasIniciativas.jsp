<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (03/09/2012) --%>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionariniciativas.panel.iniciativas.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaIniciativas" messageKeyNoElementos="jsp.seleccionariniciativas.noregistrosiniciativas" nombre="tablaIniciativas" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="400" onclick="javascript:consultar(seleccionarIniciativasForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionariniciativas.iniciativas.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarIniciativasForm, 'frecuencia', null);" nombre="frecuencia">
			<vgcutil:message key="jsp.seleccionariniciativas.iniciativas.columna.frecuencia" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarIniciativasForm, 'naturaleza', null);" nombre="naturaleza">
			<vgcutil:message key="jsp.seleccionariniciativas.iniciativas.columna.naturaleza" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="iniciativa">
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<img name="img<bean:write name="iniciativa" property="iniciativaId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:visorListaFilaEventoOnclick>				
				eventoClickFila(document.seleccionarIniciativasForm, 'tablaIniciativas', this);								
			</vgcinterfaz:visorListaFilaEventoOnclick>			
			<vgcinterfaz:visorListaFilaEventoOnmouseout>
				eventoMouseFueraFila(document.seleccionarIniciativasForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseout>			
			<vgcinterfaz:visorListaFilaEventoOnmouseover>
				eventoMouseEncimaFila(document.seleccionarIniciativasForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseover>			
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="iniciativa" property="iniciativaId" />
			</vgcinterfaz:visorListaFilaId>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="iniciativa" property="iniciativaId" />
				</vgcinterfaz:visorListaValorFilaColumnaId>
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
