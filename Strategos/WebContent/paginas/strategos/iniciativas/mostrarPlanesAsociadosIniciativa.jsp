<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page language="java" %>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (30/07/2012) --%>

<script type="text/javascript">

	function obtenerObjetivo(objetivo) { 
		return objetivo;
	} 

</script>


<form action="" class="formaHtml" name="mostrarPlanesAsociadosIniciativaForm">
	<input type="hidden" name="seleccionadosIniciativaPlanes" value="<bean:write name="gestionarIniciativas.seleccionadosIniciativaPeriodos" scope="request" ignore="true" />" />
	<input type="hidden" name="valoresSeleccionadosIniciativaPlanes" /> 
	
	
	<vgcinterfaz:contenedorForma idContenedor="body-iniciativasmostrarplanes">
		<bean:define id="tituloIniciativa">
			<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
		</bean:define>
	
		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.mostrarplanesasociadosiniciativa.titulo" arg0="<%= tituloIniciativa %>"/>
		</vgcinterfaz:contenedorFormaTitulo>
	
		<bean:define id="listavacia">
			<vgcutil:message key="jsp.mostrarplanesasociadosiniciativa.noregistros" arg0="<%= tituloIniciativa %>"/>
		</bean:define>
		<vgcinterfaz:visorLista scopePaginaLista="request" namePaginaLista="gestionarIniciativasPaginaPeriodos" nombre="visorIniciativaPlanes" seleccionSimple="true" nombreForma="mostrarPlanesAsociadosIniciativaForm" nombreCampoSeleccionados="seleccionadosIniciativaPlanes" messageKeyNoElementos="<%= listavacia %>" width="100%" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="30%">
				<vgcutil:message key="jsp.mostrarplanesasociadosiniciativa.columna.nombreplan" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="objetivo" width="40%">
				<vgcutil:message key="jsp.mostrarplanesasociadosiniciativa.columna.objetivo" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="organizacionNombre" width="30%">
				<vgcutil:message key="jsp.mostrarplanesasociadosiniciativa.columna.organizacion.nombre" />
			</vgcinterfaz:columnaVisorLista>
					
			<vgcinterfaz:filasVisorLista nombreObjeto="plan">
	
				<vgcinterfaz:visorListaFilaId >
					<bean:write name="plan" property="planId" />
				</vgcinterfaz:visorListaFilaId>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="plan" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="objetivo">
					<bean:write name="gestionarIniciativas.objetivo" scope="request" ignore="true" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacionNombre">
					<bean:write name="plan" property="organizacion.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
								
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>
	</vgcinterfaz:contenedorForma>
</form>
<script type="text/javascript">
	var height = (parseInt(_myHeight) - 150);
	var spliter = document.getElementById('splitPlanVerticalPanelSuperior');
	if (spliter != null)
		height = height - parseInt(spliter.style.height.replace("px", ""));
	else
		height = (parseInt(_myHeight) - 480);
	var objeto = document.getElementById('body-iniciativasmostrarplanes');
	if (objeto != null)
	{
		objeto.style.height = (height) + "px";
		
		var width = (parseInt(_myWidth) - 140);
		width = (parseInt(_myWidth) - 540);
		var visor = document.getElementById('visorIniciativaPlanes');
		if (visor != null)
			visor.style.width = (width - 60) + "px";
	}
</script>

