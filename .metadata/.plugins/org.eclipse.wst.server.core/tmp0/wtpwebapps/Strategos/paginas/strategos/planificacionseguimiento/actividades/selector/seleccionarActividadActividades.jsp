<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (03/09/2012) --%>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionaractividades.panel.actividades.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaActividades" messageKeyNoElementos="jsp.seleccionaractividades.noregistrosactividades" nombre="tablaActividades" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="400" onclick="javascript:consultar(seleccionarActividadForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionaractividades.actividades.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'frecuencia', null);" nombre="fechaInicioPlan">
			<vgcutil:message key="jsp.seleccionaractividades.actividades.columna.fechainicioplan" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'naturaleza', null);" nombre="fechaFinPlan">
			<vgcutil:message key="jsp.seleccionaractividades.actividades.columna.fechafinplan" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'frecuencia', null);" nombre="fechaInicioReal">
			<vgcutil:message key="jsp.seleccionaractividades.actividades.columna.fechainicioreal" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="150" onclick="javascript:consultar(seleccionarActividadForm, 'frecuencia', null);" nombre="fechaFinReal">
			<vgcutil:message key="jsp.seleccionaractividades.actividades.columna.fechafinreal" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="pryActividad">
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<img name="img<bean:write name="pryActividad" property="actividadId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:visorListaFilaEventoOnclick>				
				eventoClickFila(document.seleccionarActividadForm, 'tablaActividades', this);								
			</vgcinterfaz:visorListaFilaEventoOnclick>			
			<vgcinterfaz:visorListaFilaEventoOnmouseout>
				eventoMouseFueraFila(document.seleccionarActividadForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseout>			
			<vgcinterfaz:visorListaFilaEventoOnmouseover>
				eventoMouseEncimaFila(document.seleccionarActividadForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseover>			
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="pryActividad" property="actividadId" />
			</vgcinterfaz:visorListaFilaId>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="pryActividad" property="actividadId" />
				</vgcinterfaz:visorListaValorFilaColumnaId>
				<bean:write name="pryActividad" property="nombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaInicioPlan">
				<bean:write name="pryActividad" property="comienzoPlanFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaFinPlan">
				<bean:write name="pryActividad" property="finPlanFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaInicioReal">
				<bean:write name="pryActividad" property="comienzoRealFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaFinReal">
				<bean:write name="pryActividad" property="finRealFormateada" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
		</vgcinterfaz:filasVisorLista>

	</vgcinterfaz:visorLista>

</vgcinterfaz:contenedorForma>
