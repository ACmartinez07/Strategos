<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<bean:write name="editarMetasParcialesForm" property="nombreIndicador" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		<bean:define id="tipoCorteLongitudinal">
			<bean:write name="editarMetasParcialesForm" property="indicadorTipoCorteLongitudinal" />
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			var cerrarVentana = '<bean:write name="editarMetasParcialesForm" property="cerrarVentana" />';
			var actualizarMetaAnual = false;

			function guardar() 
			{
				if (validar()) 
				{
					window.opener.actualizarMetaAnual('<bean:write name="editarMetasParcialesForm" property="indicadorId"/>', '<bean:write name="editarMetasParcialesForm" property="ano"/>', document.editarMetasParcialesForm.valorTotalMetasParciales.value);

					window.document.editarMetasParcialesForm.action = '<html:rewrite action="/planes/metas/guardarMetasParciales"/>' + '?ts=<%=(new java.util.Date()).getTime()%>';
					window.document.editarMetasParcialesForm.submit();
				}
			}

			function validar() 
			{
				totalElementos = document.editarMetasParcialesForm.elements.length;
				for (i = 0; i < totalElementos; i++) 
				{
					var objetoForm = document.editarMetasParcialesForm.elements[i];
					if (objetoForm.name.indexOf('valorPeriodo') == 0) 
					{
						objetoForm.value = stringTrim(objetoForm.value);
						if ((objetoForm.value == null) || (objetoForm.value == '')) 
						{
							alert('No se ha definido todas las metas parciales');
							return false;
						}
					}
				}
				var tipoCorte = '<bean:write name="editarMetasParcialesForm" property="tipoCorte" />';
				var valorMetaAnual = '<bean:write name="editarMetasParcialesForm" property="valor" />';
				var valorMetaAnualNumerico = convertirNumeroFormateadoToNumero(valorMetaAnual, false);
				var valorTotalMetasParcialesNumerico = convertirNumeroFormateadoToNumero(document.editarMetasParcialesForm.valorTotalMetasParciales.value, false);
				if (tipoCorte == '<bean:write name="editarMetasParcialesForm" property="indicadorTipoCorteLongitudinal" />') 
				{
					if (valorMetaAnualNumerico != valorTotalMetasParcialesNumerico) 
					{
						actualizarMetaAnual = confirm('La suma de los valores de las Metas Parciales debe ser igual a ' + valorMetaAnual + '. ¿Desea actualizar la Meta anual a ' + document.editarMetasParcialesForm.valorTotalMetasParciales.value + '?');
						if (!actualizarMetaAnual) 
							return false;
					}
				} 
				else 
				{
					if (valorMetaAnualNumerico != valorTotalMetasParcialesNumerico) 
					{
						actualizarMetaAnual = confirm('El último valor de Meta Parcial o el acumulado es diferente a la Meta Anual ¿Desea mantener esa diferencia?');
						if (!actualizarMetaAnual) 
							return false;
					}
				}
				return true;
			}

			function cancelar() 
			{
				this.close();
			}

			if (cerrarVentana == 'true') 
			{
				alert('<vgcutil:message key="jsp.editarmetasparciales.noconincidencias" />');
				cancelar();
			}

			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
			function calcularValorTotal() 
			{
				var valorTotalAcumulado = 0;
				var valorTotalNoAcumulado = 0;

				<logic:iterate name="editarMetasParcialesForm" property="metaAnualParciales.metasParciales" id="metaParcial">
					var valorMetaParcial = document.editarMetasParcialesForm.valorPeriodo<bean:write name="metaParcial" property="metaId.periodo" />.value;
					if ((valorMetaParcial != null) && (valorMetaParcial != '')) 
					{
						valorNumerico = convertirNumeroFormateadoToNumero(valorMetaParcial, false);
						valorTotalAcumulado = valorTotalAcumulado + valorNumerico;
						valorTotalNoAcumulado = valorMetaParcial;
					}
				</logic:iterate>
				var objetoTipoCargaMeta = null;
				totalElementos = document.editarMetasParcialesForm.elements.length;
				for (i = 0; i < totalElementos; i++) 
				{
					var objetoForm = document.editarMetasParcialesForm.elements[i];
					if ((objetoForm.name  == 'tipoCargaMeta') && (objetoForm.value == '<bean:write name="editarMetasParcialesForm" property="indicadorTipoCargaMedicionEnElPeriodo" />')) 
						objetoTipoCargaMeta = objetoForm;
				}
				if (objetoTipoCargaMeta == null) 
					document.editarMetasParcialesForm.valorTotalMetasParciales.value = formatearNumero(valorTotalNoAcumulado, true, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />);
				else 
				{
					if (objetoTipoCargaMeta.checked) 
						document.editarMetasParcialesForm.valorTotalMetasParciales.value = formatearNumero(valorTotalAcumulado, false, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />);
					else 
						document.editarMetasParcialesForm.valorTotalMetasParciales.value = formatearNumero(valorTotalNoAcumulado, true, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />);
				}
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="planes/metas/guardarMetasParciales" styleClass="formaHtml">

			<vgcinterfaz:contenedorForma width="265px" height="520px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<bean:write name="editarMetasParcialesForm" property="nombreIndicador" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="40px"><b><vgcutil:message key="jsp.editarmetasparciales.ano" /></b>:</td>
							<td><bean:write name="editarMetasParcialesForm" property="ano" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><b><vgcutil:message key="jsp.editarmetasparciales.valor" /></b>:</td>
							<td><bean:write name="editarMetasParcialesForm" property="valor" /></td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Metas Parciales --%>
				<table cellpadding="3" cellspacing="1" width="250px">

					<!-- Encabezado -->
					<tr class="encabezadoListView" height="20px">
						<td align="center"><bean:message key="jsp.editarmetasparciales.periodos" /></td>
						<td align="center"><bean:message key="jsp.editarmetasparciales.valorparcial" /></td>
					</tr>

					<%-- Se itera por la lista de Metas Parciales y sus respectivas Periodos --%>
					<logic:iterate name="editarMetasParcialesForm" property="metaAnualParciales.metasParciales" id="metaParcial">

						<bean:define id="claseEstiloCelda" scope="page" value="cuadroTexto"></bean:define>
						<bean:define id="medicionSoloLectura" scope="page" value=""></bean:define>
						<logic:equal name="editarMetasParcialesForm" property="bloquear" value="true">
							<bean:define id="claseEstiloCelda" scope="page" value="medicionProtegida"></bean:define>
							<bean:define id="medicionSoloLectura" scope="page" value="this.blur();"></bean:define>
						</logic:equal>

						<tr class="mouseFueraCuerpoListView" height="20px">
							<td class="celdaMetas" align="center"><input 
									type="text" 
									size="6" 
									disabled="true" 
									style="font-weight: bold; text-align: center;" 
									value="<bean:write name="metaParcial" 
									property="metaId.periodo" />"
									onfocus="<bean:write name="medicionSoloLectura" scope="page"/>"
									class="<bean:write name="claseEstiloCelda" scope="page"/>">
							</td>
							<td class="celdaMetas" align="center">
								<input 
									type="text" 
									onfocus="<bean:write name="medicionSoloLectura" scope="page"/>"
									onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />, true);ejecutarPorDefecto(event)" 
									onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />, true);calcularValorTotal();" 
									onblur="validarEntradaNumeroEventoOnBlur(this, event, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />, true);calcularValorTotal()"
									id="valorPeriodo<bean:write name="metaParcial" property="metaId.periodo" />" 
									name="valorPeriodo<bean:write name="metaParcial" property="metaId.periodo" />" 
									size="20" 
									style="text-align: right;" 
									value="<bean:write name="metaParcial" property="valorString" />"
									class="<bean:write name="claseEstiloCelda" scope="page"/>">
							</td>
						</tr>

					</logic:iterate>

					<tr class="mouseFueraCuerpoListView" height="20px">
						<td class="celdaMetas" align="center"><input 
							type="text" 
							class="cuadroTexto" 
							size="6" 
							disabled="true" 
							style="font-weight: bold; text-align: center;" 
							value="Total"></td>
						<td class="celdaMetas" align="center"><input 
							type="text" 
							name="valorTotalMetasParciales" 
							id="valorTotalMetasParciales" 
							class="cuadroTexto" 
							size="20" 
							disabled="true" 
							style="font-weight: bold; text-align: right;"></td>
					</tr>

				</table>

				<logic:equal name="editarMetasParcialesForm" property="tipoCorte" value="<%=tipoCorteLongitudinal %>">
					<table cellpadding="3" cellspacing="1" width="250px">

						<!-- Encabezado -->
						<tr class="encabezadoListView" height="20px">
							<td align="center">Las mediciones se ingresarán</td>
						</tr>
						<!-- Encabezado -->
						<tr class="encabezadoListView" height="20px">
							<td align="center"><html:radio property="tipoCargaMeta" value="0" onclick="calcularValorTotal()">En el periodo</html:radio><html:radio property="tipoCargaMeta" value="1" onclick="calcularValorTotal()">Al periodo</html:radio></td>
						</tr>
					</table>
				</logic:equal>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarMetasParcialesForm" property="bloquear" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			<logic:iterate name="editarMetasParcialesForm" property="metaAnualParciales.metasParciales" id="metaParcial">
				var numero = document.editarMetasParcialesForm.valorPeriodo<bean:write name="metaParcial" property="metaId.periodo" />.value;
				var numeroFormateado = formatearNumero(numero, false, <bean:write name="editarMetasParcialesForm" property="numeroDecimales" />);
				document.editarMetasParcialesForm.valorPeriodo<bean:write name="metaParcial" property="metaId.periodo" />.value = numeroFormateado;
			</logic:iterate>
	
			calcularValorTotal();
		</script>

	</tiles:put>

</tiles:insert>
