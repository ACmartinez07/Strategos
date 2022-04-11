<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%-- Modificado por: Kerwin Arias (03/11/2012) --%>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarplanes.panel.planes.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaPlanes" messageKeyNoElementos="jsp.seleccionarplanes.noregistrosplanes" nombre="tablaPlanes" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="400" onclick="javascript:consultar(seleccionarPlanesForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionariniciativas.planes.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="90" onclick="javascript:consultar(seleccionarPlanesForm, 'revision', null);" nombre="revision">
			<vgcutil:message key="jsp.seleccionarplanes.planes.columna.revision" />
		</vgcinterfaz:columnaVisorLista>		
		<vgcinterfaz:columnaVisorLista align="center" width="85" onclick="javascript:consultar(seleccionarPlanesForm, 'anoInicial', null);" nombre="anoInicial">
			<vgcutil:message key="jsp.seleccionarplanes.planes.columna.anoinicial" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="85" onclick="javascript:consultar(seleccionarPlanesForm, 'anoFinal', null);" nombre="anoFinal">
			<vgcutil:message key="jsp.seleccionarplanes.planes.columna.anofinal" />
		</vgcinterfaz:columnaVisorLista>		

	
		<vgcinterfaz:filasVisorLista nombreObjeto="plan">
			
				<!-- Si tien permiso estrategico -->
			<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PLAN_VIEW_ES">
				
				<logic:equal name="plan" property="tipo" value="0">		
					
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
						<img name="img<bean:write name="plan" property="planId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
					</vgcinterfaz:valorFilaColumnaVisorLista>			
					<vgcinterfaz:visorListaFilaEventoOnclick>				
						eventoClickFila(document.seleccionarPlanesForm, 'tablaPlanes', this);								
					</vgcinterfaz:visorListaFilaEventoOnclick>			
					<vgcinterfaz:visorListaFilaEventoOnmouseout>
						eventoMouseFueraFila(document.seleccionarPlanesForm, this)
					</vgcinterfaz:visorListaFilaEventoOnmouseout>			
					<vgcinterfaz:visorListaFilaEventoOnmouseover>
						eventoMouseEncimaFila(document.seleccionarPlanesForm, this)
					</vgcinterfaz:visorListaFilaEventoOnmouseover>			
					<vgcinterfaz:visorListaFilaId>
						<bean:write name="plan" property="planId" />
					</vgcinterfaz:visorListaFilaId>
					
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
						<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="plan" property="planId" />
						</vgcinterfaz:visorListaValorFilaColumnaId>
						<bean:write name="plan" property="nombre" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="revision">
						[<bean:write name="plan" property="revisionNombre" />]
					</vgcinterfaz:valorFilaColumnaVisorLista>			
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoInicial">
						<bean:write name="plan" property="anoInicial" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoFinal">
						<bean:write name="plan" property="anoFinal" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					
				</logic:equal>
				
			</vgcutil:tienePermiso>
			
			<!-- Si tien permiso operativo -->
			<vgcutil:tienePermiso aplicaOrganizacion="true" permisoId="PLAN_VIEW_OP">
				
				<logic:equal name="plan" property="tipo" value="2">
					
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
						<img name="img<bean:write name="plan" property="planId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
					</vgcinterfaz:valorFilaColumnaVisorLista>			
					<vgcinterfaz:visorListaFilaEventoOnclick>				
						eventoClickFila(document.seleccionarPlanesForm, 'tablaPlanes', this);								
					</vgcinterfaz:visorListaFilaEventoOnclick>			
					<vgcinterfaz:visorListaFilaEventoOnmouseout>
						eventoMouseFueraFila(document.seleccionarPlanesForm, this)
					</vgcinterfaz:visorListaFilaEventoOnmouseout>			
					<vgcinterfaz:visorListaFilaEventoOnmouseover>
						eventoMouseEncimaFila(document.seleccionarPlanesForm, this)
					</vgcinterfaz:visorListaFilaEventoOnmouseover>			
					<vgcinterfaz:visorListaFilaId>
						<bean:write name="plan" property="planId" />
					</vgcinterfaz:visorListaFilaId>
					
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
						<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="plan" property="planId" />
						</vgcinterfaz:visorListaValorFilaColumnaId>
						<bean:write name="plan" property="nombre" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="revision">
						[<bean:write name="plan" property="revisionNombre" />]
					</vgcinterfaz:valorFilaColumnaVisorLista>			
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoInicial">
						<bean:write name="plan" property="anoInicial" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="anoFinal">
						<bean:write name="plan" property="anoFinal" />
					</vgcinterfaz:valorFilaColumnaVisorLista>
					
				</logic:equal>	
				
			</vgcutil:tienePermiso>
			
			<!-- si no tiene permisos asignados -->
		
					
			
			
		</vgcinterfaz:filasVisorLista>

	</vgcinterfaz:visorLista>

</vgcinterfaz:contenedorForma>
