<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%-- Creado por: Kerwin Arias (03/03/2012) --%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configurarmedicionesactividad.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<script language="Javascript" src="<html:rewrite page='/paginas/strategos/mediciones/medicionesJs/visores.js'/>"></script>

		<script language="Javascript1.1">

			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.editarMedicionesForm.fechaDesde' , document.editarMedicionesForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.editarMedicionesForm.fechaHasta' , document.editarMedicionesForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
		
			function eventoCambioFrecuencia() 
			{
				document.editarMedicionesForm.action='<html:rewrite action="mediciones/configurarmedicionesactividad" />?cambioFrecuencia=true';
				document.editarMedicionesForm.submit();
			}

			function editarMediciones() 
			{
				var haySeriesSeleccionadas = false;
				for (i = 0; i < document.editarMedicionesForm.elements.length; i++) 
				{
					if (document.editarMedicionesForm.elements[i].name == 'serieId') 
					{
						if (document.editarMedicionesForm.elements[i].checked) 
						{
							haySeriesSeleccionadas = true;
						}
					}
				}
		
				if (document.editarMedicionesForm.anoDesde != null) {
					// Esta condición no se valida en los indicadores diarios
					if (document.editarMedicionesForm.anoDesde.value > document.editarMedicionesForm.anoHasta.value) 
					{
				 		alert('<vgcutil:message key="jsp.configurarmedicionesactividad.mensaje.errorano" />');
				 		return;
					}
					if (document.editarMedicionesForm.anoDesde.value == document.editarMedicionesForm.anoHasta.value) 
					{
						if (document.editarMedicionesForm.periodoDesde.value > document.editarMedicionesForm.periodoHasta.value) 
						{
					 		alert('<vgcutil:message key="jsp.configurarmedicionesactividad.mensaje.errorperiodo" />');
					 		return;
						}
					}
				}
		
		
			 	if (!haySeriesSeleccionadas) 
			 	{
			 		alert('<vgcutil:message key="jsp.configurarmedicionesactividad.mensaje.noseries" />');
			 		return;
			 	}
		
				document.editarMedicionesForm.action='<html:rewrite action="mediciones/editarMediciones" />';
				document.editarMedicionesForm.submit();
			}
		
			function cancelar() 
			{
				document.editarMedicionesForm.action='<html:rewrite action="mediciones/cancelarconfigurarmedicionesactividad" />';
				document.editarMedicionesForm.submit();
			}

		</script>

		<html:form onsubmit="return true;" action="mediciones/configurarmedicionesactividad" focus="frecuencia">

			<html:hidden property="numeroMaximoPeriodos" />
			<input type="hidden" name="periodoDesdeAnt" value="1" />
			<input type="hidden" name="periodoHastaAnt" value="1" />

			<bean:define id="mostrarSeleccion" value="false"></bean:define>
			<logic:notEmpty name="editarMedicionesForm" property="claseId">
				<logic:notEmpty name="editarMedicionesForm" property="indicadorId">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:notEmpty>
			</logic:notEmpty>
			<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
				<logic:notEmpty name="editarMedicionesForm" property="indicadorId">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:notEmpty>
			</logic:notEmpty>
			<bean:define id="altoContenedor" value="400px"></bean:define>
			<logic:equal name="mostrarSeleccion" value="true">
				<bean:define id="altoContenedor" value="500px"></bean:define>
			</logic:equal>

			<vgcinterfaz:contenedorForma width="400px" height="<%=altoContenedor %>">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
						<vgcutil:message key="jsp.configurarmedicionesactividad.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table width="100%" class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">

					<%-- Organización --%>
					<tr>
						<td align="right" valign="top"><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.organizacion" /></b></td>
						<td colspan="3"><bean:write name="editarMedicionesForm" property="organizacion" />&nbsp;</td>
					</tr>
					<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
						<%-- Plan --%>
						<tr>
							<td align="right" valign="top"><b><bean:write name="editarMedicionesForm" property="nombreObjetoPerspectiva" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm" property="perspectivaNombre" />&nbsp;</td>
						</tr>
					</logic:notEmpty>
					<logic:empty name="editarMedicionesForm" property="perspectivaId">
						<%-- Clase de Indicadores --%>
						<tr>
							<td align="right"><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.clase" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm" property="clase" />&nbsp;</td>
						</tr>
					</logic:empty>
					<%-- Frecuencia --%>
					<tr>
						<td align="right"><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.frecuencia" /></b></td>
						<td align="left"><html:select property="frecuencia" styleClass="cuadroTexto" onchange="eventoCambioFrecuencia();">
							<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
						</html:select></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<%-- Año Periodo Desde --%>
					<tr>
						<td colspan="4"><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.desde" /></b></td>
					</tr>
					<tr>
						<logic:equal name="editarMedicionesForm" property="frecuencia" value="0">
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.dia" /></td>
							<td width="25%" align="left"><html:text property="fechaDesde" size="10" onfocus="this.blur();" styleClass="cuadroTexto" /><img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />"></td>
							<td width="25%">&nbsp;</td>
							<td width="25%">&nbsp;</td>
						</logic:equal>
						<logic:notEqual name="editarMedicionesForm" property="frecuencia" value="0">
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.ano" /></td>
							<td width="25%" align="left"><html:select property="anoDesde" styleClass="cuadroTexto">
								<html:optionsCollection property="anos" value="clave" label="valor" />
							</html:select></td>
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.periodo" /></td>
							<td width="25%" align="left"><html:select property="periodoDesde" styleClass="cuadroTexto">
								<html:optionsCollection property="periodos" value="clave" label="valor" />
							</html:select></td>
						</logic:notEqual>
					</tr>
					<%-- Año Periodo Hasta --%>
					<tr>
						<td colspan="4"><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.hasta" /></b></td>
					</tr>
					<tr>
						<logic:equal name="editarMedicionesForm" property="frecuencia" value="0">
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.dia" /></td>
							<td width="25%" align="left"><html:text property="fechaHasta" size="10" onfocus="this.blur();" styleClass="cuadroTexto" /><img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />"></td>
							<td width="25%">&nbsp;</td>
							<td width="25%">&nbsp;</td>
						</logic:equal>
						<logic:notEqual name="editarMedicionesForm" property="frecuencia" value="0">
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.ano" /></td>
							<td width="25%" align="left"><html:select property="anoHasta" styleClass="cuadroTexto">
								<html:optionsCollection property="anos" value="clave" label="valor" />
							</html:select></td>
							<td width="25%" align="right"><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.periodo" /></td>
							<td width="25%" align="left"><html:select property="periodoHasta" styleClass="cuadroTexto">
								<html:optionsCollection property="periodos" value="clave" label="valor" />
							</html:select></td>
						</logic:notEqual>

					</tr>
					<tr>
						<td colspan="4"><b>&nbsp;</b></td>
					</tr>
					<tr>
						<td colspan="4"><bean:define id="paginaSeries" name="editarMedicionesForm" property="paginaSeriesTiempo" scope="session" toScope="request"></bean:define>
						<div style="position: relative; overflow: auto; height: 100px; border-style: solid; border-width: 1px; border-color: #666666;"><vgcinterfaz:visorLista nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase" namePaginaLista="paginaSeries" messageKeyNoElementos="jsp.gestionarindicadores.noregistros" nombre="visorSeriesTiempo" scopePaginaLista="request">
							<%-- Selección múltiple --%>
							<vgcinterfaz:visorListaColumnaSeleccion nombreCampoObjetoId="serieId" nombreFormaHtml="editarMedicionesForm" />

							<vgcinterfaz:columnaVisorLista nombre="nombre" width="100%">
								<vgcutil:message key="jsp.configurarmedicionesactividad.ficha.serietiempo" />
							</vgcinterfaz:columnaVisorLista>

							<%-- Filas --%>
							<vgcinterfaz:filasVisorLista nombreObjeto="serie">

								<%-- Selección múltiple --%>
								<vgcinterfaz:visorListaValorSeleccion>
									<bean:write name="serie" property="serieId" />
								</vgcinterfaz:visorListaValorSeleccion>

								<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre" align="left">
									<bean:write name="serie" property="nombre" />
								</vgcinterfaz:valorFilaColumnaVisorLista>
							</vgcinterfaz:filasVisorLista>

						</vgcinterfaz:visorLista></div>
						</td>
					</tr>

					<logic:equal name="mostrarSeleccion" value="true">
						<%-- Espacio en Blanco --%>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<!-- Campo: Solo Seleccionados / Todos los Seleccionados -->
						<tr>
							<td colspan="4">
							<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
								<tr>
									<td colspan="2"><b><vgcutil:message key="jsp.configuraredicionmetas.ficha.seleccion" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="soloSeleccionados" value="true" class="botonSeleccionSimple" checked="true"></td>
									<td><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.seleccion.soloseleccionados" /></b></td>
								</tr>
								<tr>
									<td width="20px" align="center"><input type="radio" name="soloSeleccionados" value="false" class="botonSeleccionSimple"></td>
									<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
										<td><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.seleccion.todosperspectiva" /></b></td>
									</logic:notEmpty>
									<logic:empty name="editarMedicionesForm" property="perspectivaId">
										<td><b><vgcutil:message key="jsp.configurarmedicionesactividad.ficha.seleccion.todosclase" /></b></td>
									</logic:empty>
								</tr>
							</table>
							</td>
						</tr>
					</logic:equal>

					<logic:empty name="editarMedicionesForm" property="indicadores" scope="session">
						<tr>
							<td colspan="4"><b>&nbsp;</b></td>
						</tr>
						<tr>
							<td colspan="4"><html:checkbox property="visualizarIndicadoresCompuestos"></html:checkbox>&nbsp;<vgcutil:message key="jsp.configurarmedicionesactividad.visualizarindicadorescompuestos" /></td>
						</tr>
					</logic:empty>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:editarMediciones();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
						
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script language="Javascript1.1">

			function inicializarSeriesTiempo () 
			{
				for (i = 0; i < document.editarMedicionesForm.elements.length; i++) 
				{
					if (document.editarMedicionesForm.elements[i].name == 'serieId') 
					{
						<logic:iterate name="paginaSeries" property="lista" id="serie">
							if (document.editarMedicionesForm.elements[i].value == '<bean:write name="serie" property="serieId"/>') 
							{
								document.editarMedicionesForm.elements[i].checked = <bean:write name="serie" property="preseleccionada"/>;
							}
						</logic:iterate>
					}
				}
			
			}
			
			inicializarSeriesTiempo();

		</script>

	</tiles:put>

</tiles:insert>
