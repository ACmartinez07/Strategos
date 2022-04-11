<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/explicaciones/Explicacion.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/duppont/Duppont.js'/>"></script>

<script type="text/javascript">

	
	function editarMediciones() 
	{
		var nombreForma = '?nombreForma=' + 'gestionarIndicadoresForm';
		var funcionCierre = '&funcionCierre=' + 'onEditarMediciones()';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var url = '&indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + "&source=4&desdeClases=true";
	
		abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'cargarMediciones', '440', '520');
		
	}
	
	function onEditarMediciones()
	{
		var url = 'indicadorId=' + document.gestionarIndicadoresForm.seleccionados.value + "&source=0&desdeClases=true" + "&funcion=Ejecutar";
		window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>?' + url;
	}


</script>

<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

<jsp:include flush="true" page="/paginas/strategos/menu/menuVerJs.jsp"></jsp:include>
<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

<%-- Representación de la Forma --%>
<html:form action="/organizaciones/gestionarIndicadores" styleClass="formaHtmlGestionar">
	
	<%-- Atributos de la Forma --%>
	<html:hidden property="pagina" />
	<html:hidden property="atributoOrden" />
	<html:hidden property="tipoOrden" />
	<html:hidden property="seleccionados" />
	<html:hidden property="reporteSeleccionadoId" />
	<html:hidden property="graficoSeleccionadoId" />
	<html:hidden property="respuesta" />
	<html:hidden property="editarForma" />
	<html:hidden property="verForma" />
	
	<vgcinterfaz:contenedorForma idContenedor="body-indicadores">

		<%-- Título --%>
		<vgcinterfaz:contenedorFormaTitulo>
			..:: <vgcutil:message key="jsp.gestionarindicadores.titulo" />
		</vgcinterfaz:contenedorFormaTitulo>

		<%-- Botón Actualizar --%>
		<vgcinterfaz:contenedorFormaBotonRegresar>
		    javascript:irAtras(1)
		</vgcinterfaz:contenedorFormaBotonRegresar>
		
		<%-- Barra de Herramientas --%>
		<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

			<vgcinterfaz:barraHerramientas nombre="barraGestionarIndicadores">
			
				<vgcinterfaz:barraHerramientasBoton permisoId="INDICADOR_MEDICION" aplicaOrganizacion="true" nombreImagen="mediciones" pathImagenes="/paginas/strategos/indicadores/imagenes/barraHerramientas/" nombre="medicionesIndicadores" onclick="javascript:editarMediciones();">
					<vgcinterfaz:barraHerramientasBotonTitulo>
						<vgcutil:message key="jsp.gestionarindicadores.barraherramientas.mediciones" />
					</vgcinterfaz:barraHerramientasBotonTitulo>
				</vgcinterfaz:barraHerramientasBoton>

			</vgcinterfaz:barraHerramientas>

		</vgcinterfaz:contenedorFormaBarraGenerica>
		
		<bean:define id="valorNaturalezaFormula">
			<bean:write name="gestionarIndicadoresForm" property="naturalezaFormula" />
		</bean:define>

		<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" nombre="visorIndicadores" seleccionMultiple="true" nombreForma="gestionarIndicadoresForm" nombreCampoSeleccionados="seleccionados" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

			<%-- Encabezados --%>
			<vgcinterfaz:columnaVisorLista nombre="clase" width="200px">
				<vgcutil:message key="jsp.propiedadesindicador.clase" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="alerta" width="30px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.alerta" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="nombre" width="350px" onclick="javascript:consultar(gestionarIndicadoresForm, 'nombre', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.nombre" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="unidad" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.unidad" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="real" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.real" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="programado" width="60px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.programado" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="ultimoPeriodoMedicion" width="100px" onclick="javascript:consultar(gestionarIndicadoresForm, 'fechaUltimaMedicion', null);">
				<vgcutil:message key="jsp.gestionarindicadores.columna.ultimoperiodomedicion" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="fechaActualizacionEsperada" width="100px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="organizacion" width="250px">
				<vgcutil:message key="action.reporteresponsables.organizacion" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="observacion" width="100px">
				<vgcutil:message key="action.reporte.estatus.iniciativa.observacion" />
			</vgcinterfaz:columnaVisorLista>
			
			<vgcinterfaz:columnaVisorLista nombre="naturaleza" width="80px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.naturaleza" />
			</vgcinterfaz:columnaVisorLista>
			<vgcinterfaz:columnaVisorLista nombre="frecuencia" width="80px">
				<vgcutil:message key="jsp.gestionarindicadores.columna.frecuencia" />
			</vgcinterfaz:columnaVisorLista>
			
		
			<%-- Filas --%>
			<vgcinterfaz:filasVisorLista nombreObjeto="indicador">
				<vgcinterfaz:visorListaFilaId>
					<bean:write name="indicador" property="indicadorId" />
				</vgcinterfaz:visorListaFilaId>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="clase">
					<bean:write name="indicador" property="clase.nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<%-- Columnas --%>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="alerta" align="center">
					<vgcst:imagenAlertaIndicador name="indicador" property="alerta" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
					<bean:write name="indicador" property="nombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidad">
					<logic:notEmpty name="indicador" property="unidad">
						<bean:write name="indicador" property="unidad.nombre" />
					</logic:notEmpty>&nbsp;
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="real" align="right">
					<bean:write name="indicador" property="ultimaMedicionFormateada" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="programado" align="right">
					<bean:write name="indicador" property="ultimoProgramadoFormateado" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="ultimoPeriodoMedicion" align="right">
					<bean:write name="indicador" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaActualizacionEsperada" align="right">
					<bean:write name="indicador" property="fechaUltimaMedicion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacion">
					<bean:write name="indicador" property="organizacionNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="observacion">
					<bean:write name="indicador" property="observacion" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
					<bean:write name="indicador" property="frecuenciaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
					<bean:write name="indicador" property="naturalezaNombre" />
				</vgcinterfaz:valorFilaColumnaVisorLista>
				
			
				
			</vgcinterfaz:filasVisorLista>
		</vgcinterfaz:visorLista>
		
		<%-- Paginador --%>
		<vgcinterfaz:contenedorFormaPaginador>
			<pagination-tag:pager nombrePaginaLista="paginaIndicadores" labelPage="inPagina" action="javascript:consultar(gestionarIndicadoresForm, null, inPagina)" styleClass="paginador" />
		</vgcinterfaz:contenedorFormaPaginador>

		<%-- Barra Inferior --%>
		<vgcinterfaz:contenedorFormaBarraInferior>
			<logic:notEmpty name="gestionarIndicadoresForm" property="atributoOrden">
				<b><vgcutil:message key="jsp.gestionarlista.ordenado" /></b>: <bean:write name="gestionarIndicadoresForm" property="atributoOrden" />  [<bean:write name="gestionarIndicadoresForm" property="tipoOrden" />]
			</logic:notEmpty>
		</vgcinterfaz:contenedorFormaBarraInferior>
		
	</vgcinterfaz:contenedorForma>

</html:form>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-indicadores'), 110);
</script>
