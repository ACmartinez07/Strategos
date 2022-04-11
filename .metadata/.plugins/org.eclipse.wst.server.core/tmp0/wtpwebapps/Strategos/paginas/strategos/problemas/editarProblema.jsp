<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>

<%-- Modificado por: Kerwin Arias (26/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarProblemaForm" property="problemaId" value="0">
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
				<vgcutil:message key="jsp.editarproblema.titulo.nuevo" />
			</logic:equal>
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
				<vgcutil:message key="jsp.gestionaracciones.titulo.nuevo" />
			</logic:equal>
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarProblemaForm" property="problemaId" value="0">
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
				<vgcutil:message key="jsp.editarproblema.titulo.modificar" />
			</logic:equal>
			<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
				<vgcutil:message key="jsp.gestionaracciones.titulo.modificar" />
			</logic:equal>
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarProblemaForm" property="bloqueado">
				<bean:write name="editarProblemaForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarProblemaForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			var listaPadresHijosCausas = '';

			function validarRangoFechas(fechaInicio, fechaFin, separador) {
				var fecha1 = fechaInicio.split(separador);
			  	var dia1 = fecha1[0];
			  	var mes1 = fecha1[1];
			  	var ano1 = parseInt(fecha1[2]);
			 	var bisiesto1 = ((ano1 % 4) == 0);
			 	var fecha2 = fechaFin.split(separador);
			 	var dia2 = fecha2[0];
			 	var mes2 = fecha2[1];
			  	var ano2 = parseInt(fecha2[2]);
			  	var bisiesto2 = ((ano2 % 4) == 0);
			  	var fechasValidas = false;
				if (dia1.substring(0,1) == '0') {
					dia1 = parseInt(dia1.substring(1));
				}
				if (mes1.substring(0,1) == '0') {
					mes1 = parseInt(mes1.substring(1));
				}
				if (dia2.substring(0,1) == '0') {
					dia2 = parseInt(dia2.substring(1));
				}
				if (mes2.substring(0,1) == '0') {
					mes2 = parseInt(mes2.substring(1));
				}
				fechasValidas = (ano2 > ano1) || ((ano2 == ano1) && (mes2 > mes1)) || ((ano2 == ano1)  && (mes2 == mes1) && (dia2 >= dia1));
				return fechasValidas;
			}

			function validar(forma) {
				fechaInicio = document.editarProblemaForm.fechaEstimadaInicio.value;
				fechaFin = document.editarProblemaForm.fechaEstimadaFinal.value;
				var res = true;
				if (fechaInicio != "" && fechaFin != "")
					res = validarRangoFechas(fechaInicio, fechaFin, '/');
				if (validateEditarProblemaForm(forma)) 
				{
					<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
						if((document.editarProblemaForm.responsableId.value != 0) || (document.editarProblemaForm.auxiliarId.value != 0))
						{
							if(document.editarProblemaForm.responsableId.value == document.editarProblemaForm.auxiliarId.value)
							{
								alert('<vgcutil:message key="jsp.editarproblema.ficha.validacion.responsableauxiliarigual" />');
								<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="seguimientos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>
								return false;
							}
						}					
						if ((document.editarProblemaForm.responsableId.value == 0) && (document.editarProblemaForm.auxiliarId.value != 0))
						{
						    alert('Debe Seleccionar un Responsable');
						    <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="seguimientos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					</logic:equal>
					<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
						if ((document.editarProblemaForm.responsableId.value == 0))
						{
						    alert('Debe Seleccionar un Responsable');
						    <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="seguimientos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>
							return false;
						}
					</logic:equal>
					if(!res)
					{
						alert('<vgcutil:message key="jsp.editarproblema.ficha.validacion.responsablefechamayor" />');
						<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="seguimientos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>
						return false;
					}					
					window.document.editarProblemaForm.action = '<html:rewrite action="/problemas/guardarProblema"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					setCausasSeleccionadas();
					return true;
				} else {
				    <vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>
					return false;
				}
			}

			function guardar() {
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarProblema"></vgcinterfaz:mostrarPanelContenedorJs>			
				if (validar(document.editarProblemaForm)) {
					window.document.editarProblemaForm.submit();
				}
			}

			function cancelar() {
				window.document.editarProblemaForm.action = '<html:rewrite action="/problemas/cancelarGuardarProblema"/>';
				window.document.editarProblemaForm.submit();
			}

			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}

			function seleccionarFechaFormulacion() {
				mostrarCalendario('document.all.fecha' , document.editarProblemaForm.fecha.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

			function seleccionarFechaEstimadaInicio() {
				mostrarCalendario('document.all.fechaEstimadaInicio' , document.editarProblemaForm.fechaEstimadaInicio.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

			function seleccionarFechaEstimadaFinal() {
				mostrarCalendario('document.all.fechaEstimadaFinal' , document.editarProblemaForm.fechaEstimadaFinal.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

			function seleccionarResponsable() 
			{
				abrirSelectorResponsables('editarProblemaForm', 'nombreResponsable', 'responsableId', document.editarProblemaForm.responsableId.value, '<bean:write name="organizacionId" scope="session"/>', 'true', 'true');
			}

			function limpiarResponsable() {
				document.editarProblemaForm.responsableId.value = "";
				document.editarProblemaForm.nombreResponsable.value = "";
			}

			function seleccionarAuxiliar() {
				abrirSelectorResponsables('editarProblemaForm', 'nombreAuxiliar', 'auxiliarId', document.editarProblemaForm.auxiliarId.value, '<bean:write name="organizacionId" scope="session"/>', 'true', 'true');
			}

			function limpiarAuxiliar() {
				document.editarProblemaForm.auxiliarId.value = "";
				document.editarProblemaForm.nombreAuxiliar.value = "";
			}

			function seleccionarObjetoEfecto() {
				if (document.editarProblemaForm.objetoEfecto[0].checked) {
					abrirSelectorIndicadores('editarProblemaForm', 'nombreObjetoEfecto', 'indicadorEfectoId', '', '', document.editarProblemaForm.indicadorEfectoId.value, 'nombreOrganizacion', 'organizacionId', 'nombreClase', 'claseId');
				} else {
					abrirSelectorIniciativas('editarProblemaForm', 'nombreObjetoEfecto', 'iniciativaEfectoId', '', null, '', document.editarProblemaForm.iniciativaEfectoId.value, 'nombreOrganizacion', 'organizacionId', 'nombrePlan', 'planId');
				}
			}

			function limpiarObjetoEfecto() {
				document.editarProblemaForm.indicadorEfectoId.value = "";
				document.editarProblemaForm.iniciativaEfectoId.value = "";
				document.editarProblemaForm.nombreObjetoEfecto.value = "";
			}

			function inicializarCausasSeleccionadas() {
				var forma = self.document.editarProblemaForm;
				total = forma.elements.length;
				var seleccionados = self.document.editarProblemaForm.causas.value;
				for (indice = 0; indice < total; indice++) {
					if (forma.elements[indice].name == 'causa') {
						// Se busca al inicio de la cadena o string de permisos seleccionados
						if (seleccionados.indexOf('<bean:write name="editarProblemaForm" property="separador"/>' + forma.elements[indice].value + '<bean:write name="editarProblemaForm" property="separador"/>') > -1) {
							forma.elements[indice].checked = true;
						} else {
							forma.elements[indice].checked = false;
						}
					}
				}
			}

			function seleccionarNodoPadre(nodoHijo) {
				var separador = '<bean:write name="editarProblemaForm" property="separador" />';
				var separadorPadreHijo = '<bean:write name="editarProblemaForm" property="separadorPadreHijo" />';
				posicionHijo = listaPadresHijosCausas.indexOf(separadorPadreHijo + nodoHijo + separador);
				if (posicionHijo > -1) {
					posicionPadre = listaPadresHijosCausas.substring(0, posicionHijo).lastIndexOf(separador);
					var nodoPadre = listaPadresHijosCausas.substring(posicionPadre + separador.length, posicionHijo);
					if (nodoPadre == '<bean:write name="editarProblemaForm" property="causaIdRoot"/>') {
						return;
					}
					// Se marcan los nodos padres
					for (var indice = 0; indice < total; indice++) {
						var forma = self.document.editarProblemaForm;
						total = forma.elements.length;
						if (forma.elements[indice].value == nodoPadre) {
							if (forma.elements[indice].name == 'causa') {
								forma.elements[indice].checked = true;
								seleccionarNodoPadre(nodoPadre);
							}
						}
					}
				}
			}

			function desSeleccionarNodosHijos(nodoPadre) {
				var separador = '<bean:write name="editarProblemaForm" property="separador" />';
				var separadorPadreHijo = '<bean:write name="editarProblemaForm" property="separadorPadreHijo" />';
				var forma = self.document.editarProblemaForm;
				var total = forma.elements.length;
				var textoBuscado = separador + nodoPadre + separadorPadreHijo;
				posicionPadre = listaPadresHijosCausas.indexOf(textoBuscado);
				while (posicionPadre > -1) {
					posicionHijo = listaPadresHijosCausas.substring(posicionPadre + textoBuscado.length, listaPadresHijosCausas.length).indexOf(separador);
					posicionPadre = posicionPadre + textoBuscado.length;
					posicionHijo = posicionPadre + posicionHijo;
					var nodoHijo = listaPadresHijosCausas.substring(posicionPadre, posicionHijo);
					for (var indice = 0; indice < total; indice++) {
						if (forma.elements[indice].value == nodoHijo) {
							if (forma.elements[indice].name == 'causa') {
								forma.elements[indice].checked = false;
								desSeleccionarNodosHijos(nodoHijo);
							}
						}
					}
					posicionPadre = listaPadresHijosCausas.substring(posicionHijo + separador.length, listaPadresHijosCausas.length).indexOf(textoBuscado);
					if (posicionPadre > -1) {
						posicionPadre = posicionPadre + posicionHijo + separador.length;
					}
				}
			}

			function setSeleccionadosPadreHijo(objetoInputCheckbox) {
				if (objetoInputCheckbox.checked) {
					if (objetoInputCheckbox.value == '<bean:write name="editarProblemaForm" property="causaIdRoot"/>') {
						objetoInputCheckbox.checked = false;
						return;
					}
					seleccionarNodoPadre(objetoInputCheckbox.value);
				} else {
					desSeleccionarNodosHijos(objetoInputCheckbox.value);
				}
			}

			function marcarDesmarcarTodasCausas(marcar) {
				var forma = self.document.editarProblemaForm;
				var total = forma.elements.length;
				for (var indice = 0; indice < total; indice++ ) {
					if (forma.elements[indice].name == 'causa') {
						if (forma.elements[indice].value == '<bean:write name="editarProblemaForm" property="causaIdRoot"/>') {
							forma.elements[indice].checked = false;
						} else {
							forma.elements[indice].checked = marcar;
						}
					}
				}
			}

			function setCausasSeleccionadas(fieldOutput, fieldName) {
				var forma = self.document.editarProblemaForm;
				var total = forma.elements.length;
				var objetoInput = self.document.editarProblemaForm.causas;
				objetoInput.value = '';
				for(indice = 0; indice < total; indice++) {
					if (forma.elements[indice].name == 'causa') {
						if (forma.elements[indice].checked) {
							objetoInput.value = objetoInput.value + forma.elements[indice].value + '<bean:write name="editarProblemaForm" property="separador"/>';
						}
					}
				}
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarProblemaForm.nombre" />

		<%-- Selectores de la Forma --%>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/problemas/guardarProblema" focus="nombre" onsubmit="if (validar(document.editarProblemaForm)) return true; else return false;">

			<html:hidden property="problemaId" />
			<html:hidden property="causas" />
			<html:hidden property="responsableId" />
			<html:hidden property="auxiliarId" />
			<html:hidden property="indicadorEfectoId" />
			<html:hidden property="iniciativaEfectoId" />
			<html:hidden property="listaPadresHijosCausas" />

			<vgcinterfaz:contenedorForma width="635px" height="510px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarProblemaForm" property="problemaId" value="0">
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
							<vgcutil:message key="jsp.editarproblema.titulo.nuevo" />
						</logic:equal>
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
							<vgcutil:message key="jsp.gestionaracciones.titulo.nuevo" />
						</logic:equal>
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarProblemaForm" property="problemaId" value="0">
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
							<vgcutil:message key="jsp.editarproblema.titulo.modificar" />
						</logic:equal>
						<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
							<vgcutil:message key="jsp.gestionaracciones.titulo.modificar" />
						</logic:equal>
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles width="560px" height="380px" nombre="editarProblema">

					<!-- Panel: Datos Basicos -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="datosBasicos">

						<!-- Título del Panel: Datos Basicos -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.fechaformulacion" /></td>
								<td colspan="2">
									<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fecha" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
									<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true"> 
										<img style="cursor: pointer" onclick="seleccionarFechaFormulacion();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
									</logic:notEqual>
								</td>
							</tr>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.nombre" /></td>
								<td colspan="2"><html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="58" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
							</tr>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.descripcion" /></td>
								<td colspan="2"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoDescripcion" rows="5" cols="57" styleClass="cuadroTexto" /></td>
							</tr>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<tr>
									<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.estrategiasdesolucion" /></td>
									<td colspan="2"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoEstrategiaDeSolucion" rows="5" cols="57" styleClass="cuadroTexto" /></td>
								</tr>
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.plandeaccion" /></td>
									<td colspan="2"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoEstrategiaDeSolucion" rows="5" cols="57" styleClass="cuadroTexto" /></td>
								</tr>
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<tr>
									<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.conclusionesresultados" /></td>
									<td colspan="2"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoConclusionesResultados" rows="5" cols="57" styleClass="cuadroTexto" /></td>
								</tr>
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<tr>
									<td align="right"><vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.efectos" /></td>
									<td colspan="2"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoConclusionesResultados" rows="5" cols="57" styleClass="cuadroTexto" /></td>
								</tr>
							</logic:equal>
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.objetoefecto" /></td>
								<td colspan="2">
									<bean:define id="objetoEfectoIndicador" value="checked"></bean:define>
									<bean:define id="objetoEfectoIniciativa" value=""></bean:define> 
									<logic:notEmpty name="editarProblemaForm" property="iniciativaEfectoId">
										<logic:notEqual name="editarProblemaForm" property="iniciativaEfectoId" value="0">
											<bean:define id="objetoEfectoIniciativa" value="checked"></bean:define>
											<bean:define id="objetoEfectoIndicador" value=""></bean:define>
										</logic:notEqual>
									</logic:notEmpty>
									<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="radio" name="objetoEfecto" onclick="limpiarObjetoEfecto()" <bean:write name="objetoEfectoIndicador"/>> 
									<vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.objetoefecto.indicador" /> 
									<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="radio" name="objetoEfecto" onclick="limpiarObjetoEfecto()" <bean:write name="objetoEfectoIniciativa"/>> 
									<vgcutil:message key="jsp.editarproblema.ficha.pestana.datosbasicos.objetoefecto.iniciativa" />
								</td>
							</tr>
							<tr>
								<td align="right"></td>
								<td colspan="2">
									<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreObjetoEfecto" size="54" readonly="true" styleClass="cuadroTexto" />
									<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">									 
										<img style="cursor: pointer" onclick="seleccionarObjetoEfecto()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='<vgcutil:message key='jsp.editarproblema.ficha.pestana.datosbasicos.objetoefecto.seleccionar' />'> 
										<img style="cursor: pointer" onclick="limpiarObjetoEfecto()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
									</logic:notEqual>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Causas -->
					<vgcinterfaz:panelContenedor anchoPestana="70" nombre="causas">

						<!-- Título del Panel: Causas -->
						<vgcinterfaz:panelContenedorTitulo>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<vgcutil:message key="jsp.editarproblema.ficha.pestana.causas" />
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.causasriesgo" />
							</logic:equal>
						</vgcinterfaz:panelContenedorTitulo>

						<%-- Cuerpo de la Pestaña --%>
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<tr>
									<td align="left"><b><vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.causasgenerales" /></b></td>
								</tr>
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<tr>
									<td align="left"><b><vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.tiposderiesgo" /></b></td>
								</tr>
							</logic:equal>
							<tr>
								<td>
								<table width="98%" height="220px">
									<tr height="20px">
										<td>
											<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">
												<input type="button" style="width: 120px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.marcartodas" />" onclick="javascript:marcarDesmarcarTodasCausas(true);"> 
												<input type="button" style="width: 120px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.desmarcartodas" />" onclick="javascript:marcarDesmarcarTodasCausas(false);">
											</logic:notEqual>
											<logic:equal name="editarProblemaForm" property="bloqueado" value="true">
												<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width: 120px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.marcartodas" />" onclick="javascript:marcarDesmarcarTodasCausas(true);"> 
												<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" type="button" style="width: 120px" class="cuadroTexto" value="<vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.desmarcartodas" />" onclick="javascript:marcarDesmarcarTodasCausas(false);">
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td><treeview:treeview name="editarProblemaArbolCausas" scope="session" baseObject="editarProblemaCausaRoot" isRoot="true" fieldName="nombre" fieldId="causaId" fieldChildren="hijos" lblUrlObjectId="objetoId" styleClass="treeview" checkbox="true" checkboxName="causa" checkboxOnClick="javascript:setSeleccionadosPadreHijo(this);" urlJs="/componentes/visorArbol/visorArbol.js" pathImages="/componentes/visorArbol/" /></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<tr>
									<td align="left"><b><vgcutil:message key="jsp.editarproblema.ficha.pestana.causas.especificacion" /></b></td>
								</tr>
							</logic:equal>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
								<tr>
									<td align="left"><b><vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.descripcion.tiposderiesgo" /></b></td>
								</tr>
							</logic:equal>
							<tr>
								<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="memoEspecificacion" rows="4" cols="85" styleClass="cuadroTexto" /></td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>


					<!-- Panel: Seguimientos -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="seguimientos">

						<!-- Título del Panel: Seguimientos -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
							<tr>
								<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.responsable" /></td>
								<td>
									<bean:define id="nombreResponsable" value=""></bean:define> 
									<logic:notEmpty name="editarProblemaForm" property="responsable">
										<bean:define id="nombreResponsable">
											<bean:write name="editarProblemaForm" property="responsable.nombre" /> - <bean:write name="editarProblemaForm" property="responsable.cargo" />
										</bean:define>
									</logic:notEmpty>
									<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" class="cuadroTexto" type="text" name="nombreResponsable" size="58" readonly="true" value="<bean:write name="nombreResponsable" />">
									<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">									
										&nbsp; <img style="cursor: pointer" onclick="seleccionarResponsable()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Responsable'> 
										<img style="cursor: pointer" onclick="limpiarResponsable()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
									</logic:notEqual>
								</td>
							</tr>
							<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
								<tr>
									<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.auxiliar" /></td>
									<td>
										<bean:define id="nombreAuxiliar" value=""></bean:define> 
										<logic:notEmpty name="editarProblemaForm" property="auxiliar">
											<bean:define id="nombreAuxiliar">
												<bean:write name="editarProblemaForm" property="auxiliar.nombre" /> - <bean:write name="editarProblemaForm" property="auxiliar.cargo" />
											</bean:define>
										</logic:notEmpty>
										<input disabled="<%= Boolean.parseBoolean(bloquearForma) %>" class="cuadroTexto" type="text" name="nombreAuxiliar" size="58" readonly="true" value="<bean:write name="nombreAuxiliar" />">
										<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">
											&nbsp; <img style="cursor: pointer" onclick="seleccionarAuxiliar()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Responsable'> <img style="cursor: pointer" onclick="limpiarAuxiliar()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
										</logic:notEqual>
									</td>
								</tr>
							</logic:equal>
							<tr>
								<logic:equal scope="session" name="claseProblemas" property="tipo" value="0">
									<td align="right"><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.estatusproblema" /></td>
								</logic:equal>
								<logic:equal scope="session" name="claseProblemas" property="tipo" value="1">
									<td align="right"><vgcutil:message key="jsp.gestionaracciones.ficha.pestana.datosbasicos.opciontratamiento" /></td>
								</logic:equal>
								<td>
									<html:select property="estadoId" styleClass="cuadroTexto" style="width:150px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
										<html:option value=""></html:option>
										<html:options collection="paginaEstadosAcciones" property="estadoId" labelProperty="nombre" />
									</html:select>
								</td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
									<tr>
										<td colspan="4"><b><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.estimadas" /></b></td>
									</tr>
									<tr>
										<td width="49px"><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.inicio" /></td>
										<td width="200px">
											<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaEstimadaInicio" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
											<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true"> 
												<img style="cursor: pointer" onclick="seleccionarFechaEstimadaInicio();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
											</logic:notEqual>
										</td>
										<td width="85px"><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.finalizacion" /></td>
										<td>
											<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaEstimadaFinal" onkeypress="ejecutarPorDefecto(event)" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
											<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true"> 
												<img style="cursor: pointer" onclick="seleccionarFechaEstimadaFinal();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
											</logic:notEqual>
										</td>
									</tr>
									<tr>
										<td colspan="4">
										<hr width="100%">
										</td>
									</tr>
									<tr>
										<td colspan="4"><b><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.reales" /></b></td>
									</tr>
									<tr>
										<td><vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.inicio" /></td>
										<td>
											<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaRealInicio" size="10" maxlength="10" styleClass="cuadroTexto" />
										</td>
										<td>
											<vgcutil:message key="jsp.editarproblema.ficha.pestana.seguimiento.fechas.finalizacion" />
										</td>
										<td>
											<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="fechaRealFinal" size="10" maxlength="10" styleClass="cuadroTexto" />
										</td>
									</tr>

								</table>
								</td>
							</tr>
							<tr height="130px">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

				</vgcinterfaz:contenedorPaneles>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarProblemaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;						
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarProblemaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">		
			listaPadresHijosCausas = window.document.editarProblemaForm.listaPadresHijosCausas.value;			 
			inicializarCausasSeleccionadas();
		</script>

	</tiles:put>
</tiles:insert>
