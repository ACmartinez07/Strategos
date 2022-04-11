<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/09/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">
	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.visualizariniciativa.titulo" />&nbsp;
		<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
			<vgcutil:message key="jsp.editariniciativa.titulo.nuevo" arg0="<%= tituloIniciativa %>"/>
		</logic:empty>
		<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
			<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
		</logic:notEmpty>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript">
			function cancel() 
			{
				this.close();
			}

			function mostrarResultadoEspecifico() 
			{
				var posicion = obtenerPosicionAno(document.editarIniciativaForm.ano.options[document.editarIniciativaForm.ano.selectedIndex].value);
				document.editarIniciativaForm.resultadoEspecificoIniciativa.value = resultadosEspecificos[posicion];
			}

			var resultadosEspecificos;
			var SEPARADOR = "|+|";
			
			function obtenerPosicionAno(ano) 
			{
				var anoInicial = document.editarIniciativaForm.ano.options[0].value;
				return (ano - anoInicial);
			}
			
			function inicializarResultadosEspecificos() 
			{
				resultadosEspecificos = document.editarIniciativaForm.resultadoEspecificoIniciativa.value.split(SEPARADOR);
				mostrarResultadoEspecifico();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/visualizarIniciativa">
		
			<vgcinterfaz:contenedorForma width="600px" height="380px" bodyAlign="left" bodyValign="middle" bodyCellpadding="15">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.visualizariniciativa.titulo" />&nbsp;
					<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
						<vgcutil:message key="jsp.editariniciativa.titulo.nuevo" arg0="<%= tituloIniciativa %>"/>
					</logic:empty>
					<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
						<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancel()
				</vgcinterfaz:contenedorFormaBotonRegresar>
				
				<vgcinterfaz:contenedorPaneles width="520px" height="275px" nombre="editarIniciativa">
					<!-- Panel: Datos Básicos -->
					<vgcinterfaz:panelContenedor anchoPestana="105" nombre="datosBasicos">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.datos.basicos" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<%-- Organizacion --%>
							<tr>
								<td align="right"><b><vgcutil:message key="jsp.editariniciativa.ficha.organizacion" /></b></td>
								<td colspan="3"><bean:write name="editarIniciativaForm" property="organizacionNombre" /></td>
							</tr>
							
							<!-- Campo Nombre -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.nombre" /></td>
								<td colspan="3"><html:text readonly="true" property="nombre" size="56" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Campo Nombre Largo -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.nombrelargo" /></td>
								<td colspan="3"><html:text readonly="true" property="nombreLargo" size="56" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo descripción --%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.descripcion" /></td>
								<td colspan="3"><html:textarea readonly="true" property="descripcion" cols="56" rows="7" styleClass="cuadroTexto" /></td>

							</tr>

							<!-- Campo enteEjecutor -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.enteejecutor" /></td>
								<td colspan="3"><html:text readonly="true" property="enteEjecutor" size="56" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Campo frecuencia -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.frecuencia" /></td>
								<td colspan="3"><html:text readonly="true" property="frecuenciaNombre" size="10" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Responsables -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="responsables">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<%-- Responsable de Fijar Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.responsablefijarmeta" /></td>
								<td><html:text readonly="true" property="responsableFijarMeta" size="40" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.responsablelograrmeta" /></td>
								<td><html:text property="responsableLograrMeta" size="40" readonly="true" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Responsable de Seguimiento--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.responsableseguimiento" /></td>
								<td><html:text property="responsableSeguimiento" size="40" readonly="true" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.responsablecargarmeta" /></td>
								<td><html:text property="responsableCargarMeta" size="40" readonly="true" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Responsable de Cargar Ejecutado --%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.responsablecargarejecutado" /></td>
								<td><html:text property="responsableCargarEjecutado" size="40" readonly="true" styleClass="cuadroTexto" /></td>
							</tr>

							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Alertas -->
					<vgcinterfaz:panelContenedor anchoPestana="70" nombre="alertas">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
							<tr>
								<td>
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" border="0">
									<tr>
										<td colspan="2">
											<html:radio disabled="true" property="tipoAlerta" value="0" />
											<vgcutil:message key="jsp.editariniciativa.ficha.calculo.automatico" />&nbsp;<b><vgcutil:message key="jsp.editariniciativa.ficha" /></b>
										</td>
									</tr>
									<tr>
										<td align="right" width="30%"><vgcutil:message key="jsp.editariniciativa.ficha.alerta.zonaverde" /></td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
												<tr>
													<td valign="middle" align="left">
														<html:text readonly="true" property="alertaZonaVerde" size="2" styleClass="cuadroTexto" />
													</td>
													<td>&nbsp;%</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;<vgcutil:message key="jsp.editariniciativa.ficha.alerta.zonaamarilla" /></td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
												<tr>
													<td valign="middle">
														<html:text readonly="true" property="alertaZonaAmarilla" size="2" styleClass="cuadroTexto" />
													</td>
													<td>&nbsp;%</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
										<tr>
											<td>
												<html:radio disabled="true" property="tipoAlerta" value="1" />
												<vgcutil:message key="jsp.editariniciativa.ficha.calculo.manual" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Complementarios -->
					<vgcinterfaz:panelContenedor anchoPestana="170" nombre="complementarios">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.datos.complementarios" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="2" cellspacing="0" border="0">
							<tr>
								<!-- Campo resultado Global -->
								<td valign="bottom"><b><vgcutil:message key="jsp.editariniciativa.ficha.resultado.global" /></b></td>

								<!-- Campo Resultado Especifico -->
								<td valign="bottom">
									<b><vgcutil:message key="jsp.editariniciativa.ficha.resultado.especifico" />&nbsp;&nbsp;</b>
									<html:select property="ano" onchange="mostrarResultadoEspecifico();" styleClass="cuadroTexto">
									<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								</td>
							</tr>

							<tr>
								<td valign="top"><html:textarea readonly="true" property="resultado" cols="35" rows="15" styleClass="cuadroTexto" /></td>
								<td valign="top"><html:textarea readonly="true" property="resultadoEspecificoIniciativa" cols="35" rows="15" styleClass="cuadroTexto" /></td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script type="text/javascript">
			inicializarResultadosEspecificos();
		</script>

	</tiles:put>

</tiles:insert>