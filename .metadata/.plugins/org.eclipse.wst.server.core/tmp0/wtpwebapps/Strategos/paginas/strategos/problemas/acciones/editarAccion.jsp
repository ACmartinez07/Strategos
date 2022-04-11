<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarAccionForm" property="accionId" value="0">
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
				<vgcutil:message key="jsp.editaraccioncorrectiva.titulo.nuevo" />
			</logic:equal>
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
				<vgcutil:message key="jsp.editaraccionpreventivas.titulo.nuevo" />
			</logic:equal>
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarAccionForm" property="accionId" value="0">
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
				<vgcutil:message key="jsp.editaraccioncorrectiva.titulo.modificar" />
			</logic:equal>
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
				<vgcutil:message key="jsp.editaraccionpreventivas.titulo.modificar" />
			</logic:equal>
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarAccionForm" property="bloqueado">
				<bean:write name="editarAccionForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarAccionForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<script language="Javascript1.1">
		
			// Inicializar botones de los cuadros Numéricos  
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');	
			
			function validar(forma) {			
				if (validateEditarAccionForm(forma)) 
				{							   
					window.document.editarAccionForm.action = '<html:rewrite action="/problemas/acciones/guardarAccion"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					if(!validarFechas()) 
						return false;
					else 
					{
						if (window.document.editarAccionForm.responsableId.value == window.document.editarAccionForm.auxiliarId.value) 
						{
							alert('<vgcutil:message key="jsp.editaraccioncorrectiva.validacion.responsable.iguales" />');
							return false;
						}
						return true;
					}
				} 
				else 
					return false;
			}
	
			function guardar() 
			{
			   	<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="accionCorrectiva" nombreContenedor="editarAccion"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarAccionForm)) {
					window.document.editarAccionForm.submit();
				}
			}
			
			function cancelar() {
				window.document.editarAccionForm.action = '<html:rewrite action="/problemas/acciones/cancelarGuardarAccion"/>';
				window.document.editarAccionForm.submit();
			}
			
			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}
			
			function seleccionarFechaEstimadaDeInicio() {
				mostrarCalendario('document.all.fechaEstimadaInicio' , document.editarAccionForm.fechaEstimadaInicio.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function seleccionarFechaEstimadaDeFin() {
				mostrarCalendario('document.all.fechaEstimadaFinal' , document.editarAccionForm.fechaEstimadaFinal.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
	
			function seleccionarResponsable() {
				abrirSelectorResponsables('editarAccionForm', 'nombreResponsable', 'responsableId', document.editarAccionForm.responsableId.value, '<bean:write name="organizacionId" scope="session"/>', 'false', 'true');
			}
			
			function seleccionarAuxiliar() {
				abrirSelectorResponsables('editarAccionForm', 'nombreAuxiliar', 'auxiliarId', document.editarAccionForm.auxiliarId.value, '<bean:write name="organizacionId" scope="session"/>', 'false', 'true');
			}
			
			function limpiarResponsable() {
				window.document.editarAccionForm.responsableId.value = "";
				window.document.editarAccionForm.nombreResponsable.value = "";
			}
			
			function limpiarAuxiliar() {
				window.document.editarAccionForm.auxiliarId.value = "";
				window.document.editarAccionForm.nombreAuxiliar.value = "";
			}
			
			function validarFechas() 
			{			
				var fechaEstimadaInicioProblema = '<bean:write name="gestionarAccionesForm" property="fechaEstimadaInicioProblema" />';
				var fechaEstimadaFinalProblema = '<bean:write name="gestionarAccionesForm" property="fechaEstimadaFinalProblema" />';			
				var fechaEstimadaInicio = document.editarAccionForm.fechaEstimadaInicio.value;
				var fechaEstimadaFinal = document.editarAccionForm.fechaEstimadaFinal.value;			
				var frecuenciaSeguimiento = document.editarAccionForm.frecuencia.value;			
				if (compararFechas(fechaEstimadaInicio, fechaEstimadaInicioProblema, '/') < 0) 
				{
					alert('<vgcutil:message key="jsp.editaraccioncorrectiva.validacion.fechas.iniciomenorproblema" />');
					return false;
				}			
				if (compararFechas(fechaEstimadaFinalProblema, fechaEstimadaFinal, '/') < 0) 
				{
					alert('<vgcutil:message key="jsp.editaraccioncorrectiva.validacion.fechas.finalmayorproblema" />');
					return false;
				}			
				if (compararFechas(fechaEstimadaFinal, fechaEstimadaInicio, '/') < 0) 
				{
					alert('<vgcutil:message key="jsp.editaraccioncorrectiva.validacion.fechas.iniciomayorfinal" />');
					return false;
				}
				
		 		if (!fechaValida(document.editarAccionForm.fechaEstimadaInicio))
		 			return;

		 		if (!fechaValida(document.editarAccionForm.fechaEstimadaFinal))
		 			return;
				
		 		if (!fechasRangosValidos(document.editarAccionForm.fechaEstimadaInicio, document.editarAccionForm.fechaEstimadaFinal))
		 			return;
				
				return true;
			}	
			
			function compararFechas(fechaUno, fechaDos, separador) 
			{
				var fecha1 = fechaUno.split(separador);
			  	var dia1 = fecha1[0];
			  	var mes1 = fecha1[1];
			  	var ano1 = parseInt(fecha1[2]);
			 	var bisiesto1 = ((ano1 % 4) == 0);
			 	var fecha2 = fechaDos.split(separador);
			 	var dia2 = fecha2[0];
			 	var mes2 = fecha2[1];
			  	var ano2 = parseInt(fecha2[2]);
			  	var bisiesto2 = ((ano2 % 4) == 0);
			  	var fechasValidas = false;			
				if (dia1.substring(0,1) == '0') { dia1 = parseInt(dia1.substring(1)); }			
				if (mes1.substring(0,1) == '0') { mes1 = parseInt(mes1.substring(1)); }			
				if (dia2.substring(0,1) == '0') { dia2 = parseInt(dia2.substring(1)); }			
				if (mes2.substring(0,1) == '0') { mes2 = parseInt(mes2.substring(1)); }			
				if (ano1 > ano2) { return 1; }
				if (ano1 < ano2) { return -1; }			
				if (mes1 > mes2) { return 1; }			
				if (mes1 < mes2) { return -1; }			
				if (dia1 > dia2) { return 1; }			
				if (dia1 < dia2) { return -1; }			
				return 0;
			}

    	</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarAccionForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/acciones/guardarAccion" focus="nombre" onsubmit="if (validar(document.editarAccionForm)) return true; else return false;">


			<html:hidden property="accionId" />
			<html:hidden property="padreId" />
			<html:hidden property="responsableId" />
			<html:hidden property="auxiliarId" />

			<vgcinterfaz:contenedorForma width="590px" height="425px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarAccionForm" property="accionId" value="0">
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
							<vgcutil:message key="jsp.editaraccioncorrectiva.titulo.nuevo" />
						</logic:equal>
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
							<vgcutil:message key="jsp.editaraccionpreventivas.titulo.nuevo" />
						</logic:equal>
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarAccionForm" property="accionId" value="0">
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
							<vgcutil:message key="jsp.editaraccioncorrectiva.titulo.modificar" />
						</logic:equal>
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
							<vgcutil:message key="jsp.editaraccionpreventivas.titulo.modificar" />
						</logic:equal>
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<!-- Contenedor de Paneles -->
				<vgcinterfaz:contenedorPaneles width="510px" height="320px" nombre="editarAccion">

					<!-- Panel: Acción Correctiva -->
					<vgcinterfaz:panelContenedor anchoPestana="130" nombre="accionCorrectiva">

						<!-- Título del Panel: Acción Correctiva -->
						<vgcinterfaz:panelContenedorTitulo>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva" />
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<vgcutil:message key="jsp.gestionarproblemas.boton.accionespreventivas" />
							</logic:equal>
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<!-- Nombre -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.nombre" /></td>
								<td><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="60" onkeypress="ejecutarPorDefecto(event)" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Descripción -->
							<tr>
								<td align="right"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.descripcion" /></td>
								<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="descripcion" cols="60" rows="5" styleClass="cuadroTexto" /></td>
							</tr>

							<!-- Contenedor de las Fechas -->
							<tr>
								<td>&nbsp;</td>
								<td>
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" height="100%">

									<!-- Título: Fechas Límite -->
									<tr>
										<td colspan="2"><b><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fechas.limite" /></b></td>
									</tr>

									<!-- Fechas límite inicio y finalizacion -->
									<tr>
										<td>&nbsp;</td>
										<td>
										<table class="bordeFichaDatos" width="100%">
											<tr>
												<td width="60px"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fecha.inicio" /></td>
												<td width="110px"><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="text" value="<bean:write name='gestionarAccionesForm' property='fechaEstimadaInicioProblema' />" size="10" maxlength="10" readonly="true" class="cuadroTexto"></td>
												<td width="100px"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fecha.finalizacion" /></td>
												<td><input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="text" value="<bean:write name='gestionarAccionesForm' property='fechaEstimadaFinalProblema' />" size="10" maxlength="10" readonly="true" class="cuadroTexto"></td>
											</tr>
										</table>
										</td>
									</tr>

									<!-- Espacio en Blanco -->
									<tr>
										<td colspan="2">
										<hr width="100%">
										</td>
									</tr>

									<!-- Título: Fechas Estimadas -->
									<tr>
										<td colspan="2"><b><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fechas.estimadas" /></b></td>
									</tr>

									<tr>
										<!-- Fecha estimada inicio y finalización  -->
										<td>&nbsp;</td>
										<td>
										<table class="bordeFichaDatos" width="100%">
											<tr>
												<td width="60px"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fecha.inicio" /></td>
												<td width="110px">
													<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaEstimadaInicio" size="10" maxlength="10" styleClass="cuadroTexto" />
													<logic:notEqual name="editarAccionForm" property="bloqueado" value="true"> 
														<img style="cursor: pointer" onclick=" seleccionarFechaEstimadaDeInicio();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
													</logic:notEqual>
												</td>
												<td width="100px"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.fecha.finalizacion" /></td>
												<td>
													<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaEstimadaFinal" size="10" maxlength="10" styleClass="cuadroTexto" />
													<logic:notEqual name="editarAccionForm" property="bloqueado" value="true"> 
														<img style="cursor: pointer" onclick=" seleccionarFechaEstimadaDeFin();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
													</logic:notEqual>
												</td>
											</tr>
										</table>
										</td>
									</tr>

								</table>
								</td>
							</tr>

							<tr>
								<td colspan="2" align="right">
								<table class="bordeFichaDatos">
									<tr>

										<td><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.frecuencia" /></td>
										<td>
										<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
											<tr>
												<td valign="middle" align="left"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="frecuencia" size="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" maxlength="2" /></td>
											</tr>
										</table>
										</td>

										<td><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.accioncorrectiva.orden" /></td>
										<td>
										<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
											<tr>
												<td valign="middle" align="left"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="orden" size="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" maxlength="2" /></td>
											</tr>
										</table>
										</td>

									</tr>
								</table>
								</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Responsables -->
					<vgcinterfaz:panelContenedor anchoPestana="130" nombre="responsables">

						<!-- Título del Panel: Responsables -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editaraccioncorrectiva.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">

							<%-- Responsable --%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.responsables.responsable" /></td>
								<td>
									<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreResponsable" size="50" readonly="true" styleClass="cuadroTexto" />
									<logic:notEqual name="editarAccionForm" property="bloqueado" value="true"> 
										<img style="cursor: pointer" onclick="seleccionarResponsable();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaraccioncorrectiva.pestana.responsables.seleccionarresponsable" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsable();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<%-- Auxiliar --%>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editaraccioncorrectiva.pestana.responsables.auxiliar" /></td>
								<td>
									<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreAuxiliar" size="50" readonly="true" styleClass="cuadroTexto" />
									<logic:notEqual name="editarAccionForm" property="bloqueado" value="true"> 
										<img style="cursor: pointer" onclick="seleccionarAuxiliar();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editaraccioncorrectiva.pestana.responsables.seleccionarauxiliar" />"> <img style="cursor: pointer" onclick="limpiarAuxiliar();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>

						</table>
					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarAccionForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;						
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarAccionForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
