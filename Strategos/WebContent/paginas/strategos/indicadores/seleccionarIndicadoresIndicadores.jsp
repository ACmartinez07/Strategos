<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>
<script type="text/javascript">

	function marcar(obj) { 
    	elem=obj.elements; 
    	for (i=0;i<elem.length;i++) 
        	if (elem[i].type=="checkbox") 
           	    elem[i].checked=true; 
    			chequearIndicador(elem[i]);
	} 

</script>

<vgcinterfaz:contenedorForma idContenedor="body-indicadorSeleccion" mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.seleccionarindicadores.indicadores.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Validación del ancho de la columna --%>
	<bean:define id="anchoColumnaNombre" value="350px" />
	<bean:define id="eventoClickFila" value="" />
	<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
		<bean:define id="anchoColumnaNombre" value="350px" />
		<bean:define id="eventoClickFila" value="" />
	</logic:equal>
	<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
		<bean:define id="anchoColumnaNombre" value="600px" />
		<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="true">
			<bean:define id="eventoClickFila" value="" />
		</logic:equal>
		<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
			<bean:define id="eventoClickFila" value="eventoClickFila(document.seleccionarIndicadoresForm, 'tablaIndicadores', this)" />
		</logic:equal>
	</logic:equal>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaIndicadores" messageKeyNoElementos="jsp.seleccionarindicadores.noregistros" nombre="tablaIndicadores" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			
			<button type="button" onclick="marcar(this.form)">
       			<img  src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10" >
   			</button>
			
		</vgcinterfaz:columnaVisorLista>
		<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
			<vgcinterfaz:columnaVisorLista align="center" width="150" nombre="serieTiempo">
				<vgcutil:message key="jsp.seleccionarindicadores.columna.seriestiempo" />
			</vgcinterfaz:columnaVisorLista>
		</logic:equal>
		<vgcinterfaz:columnaVisorLista align="center" width="390" onclick="javascript:consultar(seleccionarIndicadoresForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.seleccionarindicadores.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultar(seleccionarIndicadoresForm, 'unidadId', null);" nombre="unidadId">
			<vgcutil:message key="jsp.seleccionarindicadores.columna.unidad" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="100" onclick="javascript:consultar(seleccionarIndicadoresForm, 'frecuencia', null);" nombre="frecuencia">
			<vgcutil:message key="jsp.seleccionarindicadores.columna.frecuencia" />
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="120" onclick="javascript:consultar(seleccionarIndicadoresForm, 'naturaleza', null);" nombre="naturaleza">
			<vgcutil:message key="jsp.seleccionarindicadores.columna.naturaleza" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="indicador">
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="indicador" property="indicadorId" />
			</vgcinterfaz:visorListaFilaId>
			<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
				<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
					<vgcinterfaz:visorListaFilaEventoOnmouseout>eventoMouseFueraFila(document.seleccionarIndicadoresForm, this)</vgcinterfaz:visorListaFilaEventoOnmouseout>
					<vgcinterfaz:visorListaFilaEventoOnmouseover>eventoMouseEncimaFila(document.seleccionarIndicadoresForm, this)</vgcinterfaz:visorListaFilaEventoOnmouseover>
				</logic:equal>
			</logic:equal>
			<vgcinterfaz:visorListaFilaEventoOnclick>
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
						eventoClickFila(document.seleccionarIndicadoresForm, 'tablaIndicadores', this);
						seleccionar();
					</logic:equal>
				</logic:equal>
			</vgcinterfaz:visorListaFilaEventoOnclick>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="false">
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="true">

						<table class="listView" height="20px">
							<tr class="mouseFueraCuerpoListView" height="20px">
								<td>
									<input name="indicadorId<bean:write name="indicador" property="indicadorId" />" 
										onclick="chequearIndicador(this);" 
										type='checkbox' 
										value="<bean:write name="indicador" property="indicadorId" /><bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" /><bean:write name="indicador" property="nombre" />" 
										class="botonSeleccionMultiple">
								</td>
							</tr>
						</table>
					</logic:equal>
					<logic:equal name="seleccionarIndicadoresForm" property="seleccionMultiple" value="false">
						<img name="img<bean:write name="indicador" property="indicadorId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
					</logic:equal>
				</logic:equal>
				<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
					<img name="img<bean:write name="indicador" property="indicadorId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
				</logic:equal>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="indicador" property="indicadorId" /></vgcinterfaz:visorListaValorFilaColumnaId>
				<bean:write name="indicador" property="nombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<logic:equal name="seleccionarIndicadoresForm" property="mostrarSeriesTiempo" value="true">
				<vgcinterfaz:valorFilaColumnaVisorLista nombre="serieTiempo">
					<logic:notEmpty name="indicador" property="seriesIndicador">
						<table class="listView" height="20px">
							<logic:iterate id="serieIndicador" name="indicador" property="seriesIndicador">
								<tr class="mouseFueraCuerpoListView" height="20px">
									<td><input name="indicadorId<bean:write name="indicador" property="indicadorId" />serieId<bean:write name="serieIndicador" property="serieTiempo.serieId" />" onclick="chequearSerie(this);" type='checkbox' value="<bean:write name="indicador" property="indicadorId" /><bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" /><bean:write name="serieIndicador" property="serieTiempo.serieId" /><bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" /><bean:write name="indicador" property="nombre" /><bean:write name="seleccionarIndicadoresForm" property="separadorSeries" scope="session" /><bean:write name="serieIndicador" property="serieTiempo.nombre" />" class="botonSeleccionMultiple"><bean:write name="serieIndicador" property="serieTiempo.nombre" /></td>
								</tr>
							</logic:iterate>
						</table>
					</logic:notEmpty>
				</vgcinterfaz:valorFilaColumnaVisorLista>
			</logic:equal>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="unidadId">
				<logic:notEmpty name="indicador" property="unidadId">
					<bean:write name="indicador" property="unidad.nombre" />
				</logic:notEmpty>
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="frecuencia">
				<bean:write name="indicador" property="frecuenciaNombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="naturaleza">
				<bean:write name="indicador" property="naturalezaNombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>
		</vgcinterfaz:filasVisorLista>

	</vgcinterfaz:visorLista>

</vgcinterfaz:contenedorForma>
<script type="text/javascript">
	resizeAlto(document.getElementById('body-indicadorSeleccion'), 206);
</script>
