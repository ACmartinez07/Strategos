<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<bean:message key="jsp.editarmetas.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			var nombreObjetoSeleccionado = "";

			function confirmar() 
			{
				var confirmado = confirm('<bean:message key="jsp.editarmetas.mensaje.confirmarguardar" />');
				if (confirmado) 
					return true;
				else 
					return false;
			}
			
			function guardar() 
			{
				if (confirmar()) 
				{
					window.document.editarMetasForm.action = '<html:rewrite action="/planes/metas/guardarMetas"/>' + '?ts=<%=(new java.util.Date()).getTime()%>';
					window.document.editarMetasForm.submit();
				}
			}

			function cancelar() {
				window.document.editarMetasForm.action = '<html:rewrite action="/planes/metas/cancelarGuardarMetas"/>';
				window.document.editarMetasForm.submit();
			}

			function mostrarUnidadMedida(valor) {
				var queryString = "?mostrarUnidadMedida=" + valor + "&mostrarCodigoEnlace=" + document.editarMetasForm.mostrarCodigoEnlace.value;
				window.location.href='<html:rewrite action="/planes/metas/editarMetas" />' + queryString;
			}
			
			function mostrarCodigoEnlace(valor) {			
				var queryString = "?mostrarCodigoEnlace=" + valor + "&mostrarUnidadMedida=" + document.editarMetasForm.mostrarUnidadMedida.value;
				window.location.href='<html:rewrite action="/planes/metas/editarMetas" />' + queryString;
			}

			function obtenerFoco(objeto) {
				nombreObjetoSeleccionado = objeto.name;
			}

			function editarMetasParciales() 
			{
				if ((nombreObjetoSeleccionado != null) && (nombreObjetoSeleccionado != "")) 
				{
					var objeto = document.getElementById(nombreObjetoSeleccionado);
					if ((objeto == undefined) || (objeto == null) || (objeto.value == "")) 
					{
						alert('<bean:message key="jsp.editarmetas.mensaje.faltavalor" />');
						objeto.focus();
					} 
					else 
					{
						objeto.focus();
						var nombre = nombreObjetoSeleccionado.substring(5);
						var valor = document.getElementById("valor" + nombre);
						var ano = document.getElementById("ano" + nombre);
						var numeroPeriodos = document.getElementById("numeroPeriodos" + nombre);
						var nombreIndicador = document.getElementById("nombre" + nombre);
						var numeroDecimales = document.getElementById("numeroDecimales" + nombre);
						var tipoCorte = document.getElementById("tipoCorte" + nombre);
						var tipoCargaMeta = document.getElementById("tipoCargaMeta" + nombre);
						var indicadorId = document.getElementById(nombre);
						var serieId = '<bean:write name="editarMetasForm" property="serieId" />';
						var queryString = '?indicadorId=' + indicadorId.value + '&ano=' + ano.value + '&valor=' + valor.value + '&numeroPeriodos=' + numeroPeriodos.value + '&nombreIndicador=' + nombreIndicador.value + '&serieId=' + serieId + '&numeroDecimales=' + numeroDecimales.value + '&tipoCorte=' + tipoCorte.value + '&tipoCargaMedicion=' + tipoCargaMeta.value;
						abrirVentanaModal('<html:rewrite action="/planes/metas/editarMetasParciales" />' + queryString , 'metas', 315, 570);
					}
				} 
				else 
					alert('<bean:message key="jsp.editarmetas.mensaje.seleccionarmetaanual" />');
			}

			function actualizarMetaAnual(indicadorId, ano, valor) 
			{
				totalElementos = document.editarMetasForm.elements.length;
				for (var i = 0; i < totalElementos; i++) 
				{
					var objetoForm = document.editarMetasForm.elements[i];
					if (objetoForm.name.indexOf('valorIndicadorId' + indicadorId + 'serieId<bean:write name="editarMetasForm" property="serieId" />periodo0ano' + ano) == 0) 
						objetoForm.value = valor;
				}
			}

		</script>

		<html:form action="planes/metas/guardarMetas" styleClass="formaHtml">

			<html:hidden property="mostrarUnidadMedida" />
			<html:hidden property="mostrarCodigoEnlace" />
			<bean:define id="mostrarMetasParciales" scope="page" value="false"></bean:define>

			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true" bodyAlign="left">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.editarmetas.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>

					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">

								<%-- Validación: Unidad de Medida --%>
								<logic:equal name="editarMetasForm" property="mostrarUnidadMedida" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarmetas.menu.ver.unidadmedida" icon="/componentes/menu/activo.gif" onclick="mostrarUnidadMedida(false);" permisoId="META" />
								</logic:equal>
								<logic:notEqual name="editarMetasForm" property="mostrarUnidadMedida" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarmetas.menu.ver.unidadmedida" onclick="mostrarUnidadMedida(true);" permisoId="META" />
								</logic:notEqual>

								<%-- Validación: Código de Enlace --%>
								<logic:equal name="editarMetasForm" property="mostrarCodigoEnlace" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarmetas.menu.ver.codigoenlace" icon="/componentes/menu/activo.gif" onclick="mostrarCodigoEnlace(false);" permisoId="META" />
								</logic:equal>
								<logic:notEqual name="editarMetasForm" property="mostrarCodigoEnlace" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarmetas.menu.ver.codigoenlace" onclick="mostrarCodigoEnlace(true);" permisoId="META" />
								</logic:notEqual>

							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

					</vgcinterfaz:contenedorMenuHorizontal>

				</vgcinterfaz:contenedorFormaBarraMenus>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Organización y Plan --%>
					<table width="100%" cellpadding="3" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.editarmetas.organizacion" /></b></td>
							<td><bean:write name="editarMetasForm" property="nombreOrganizacion" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.editarmetas.plan" /></b></td>
							<td><bean:write name="editarMetasForm" property="nombrePlan" /></td>
						</tr>
					</table>

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraMetas">
						<logic:notEqual name="editarMetasForm" property="bloquear" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasSeparador />
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="vistas_detalle" pathImagenes="/componentes/barraHerramientas/" nombre="metasParciales" onclick="javascript:editarMetasParciales();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="jsp.editarmetas.menu.ver.metaparciales" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<table style="" cellpadding="3" cellspacing="1" width="<bean:write name="editarMetasForm" property="anchoMatriz" />">

					<!-- Encabezado -->
					<tr class="encabezadoListView" height="20px">
						<td width="400px" align="center"><bean:message key="jsp.editarmetas.indicador" /></td>

						<!-- Validación: Unidad de Medida -->
						<logic:equal name="editarMetasForm" property="mostrarUnidadMedida" value="true">
							<td width="150px" align="center"><bean:message key="jsp.editarmetas.unidad" /></td>
						</logic:equal>

						<!-- Validación: Código de Enlace -->
						<logic:equal name="editarMetasForm" property="mostrarCodigoEnlace" value="true">
							<td width="150px" align="center"><bean:message key="jsp.editarmetas.codigoenlace" /></td>
						</logic:equal>

						<!-- Se muestran los Años -->
						<logic:iterate name="editarMetasForm" property="listaAnos" id="ano">
							<td width="150px" align="center"><bean:write name="ano" /></td>
						</logic:iterate>

					</tr>

					<%-- Se itera por la lista de Indicadores y sus respectivas Metas Anuales --%>
					<logic:iterate name="editarMetasForm" property="metasIndicadores" id="metasIndicador">

						<tr class="mouseFueraCuerpoListView" height="20px">
							<td class="celdaMetas"><bean:write name="metasIndicador" property="indicador.nombre" /></td>

							<!-- Validación: Unidad de Medida -->
							<logic:equal name="editarMetasForm" property="mostrarUnidadMedida" value="true">
								<td class="celdaMetas"><logic:notEmpty name="metasIndicador" property="indicador.unidad">
									<bean:write name="metasIndicador" property="indicador.unidad.nombre" />
								</logic:notEmpty></td>
							</logic:equal>

							<!-- Validación: Código de Enlace -->
							<logic:equal name="editarMetasForm" property="mostrarCodigoEnlace" value="true">
								<td class="celdaMetas"><bean:write name="metasIndicador" property="indicador.codigoEnlace" /></td>
							</logic:equal>

							<!-- Declaración de variables locales para hacer las validaciones de:
								a) metaSoloLectura  ; variable para poder saber si la meta está bloqueada o no
								b) claseEstiloCelda ; variable para poder establecer el estilo de cada celda depéndiendo si está bloqueado el Indicado o una Meta en particular
								c) numeroDecimales  ; variable que indica el número de decimales con el que trabaja cada Indicador
							 -->
							<bean:define id="metaSoloLectura" scope="page" value="obtenerFoco(this);"></bean:define>
							<bean:define id="claseEstiloCelda" scope="page" value="cuadroTexto"></bean:define>
							<bean:define id="numeroDecimales" name="metasIndicador" property="indicador.numeroDecimales" type="java.lang.Byte" />

							<!-- Se valida si el Indicador está bloqueado -->
							<logic:equal name="metasIndicador" property="indicador.estaBloqueado" value="true">
								<bean:define id="metaSoloLectura" scope="page" value="this.blur();"></bean:define>
								<bean:define id="claseEstiloCelda" scope="page" value="metaProtegida"></bean:define>
							</logic:equal>

							<!-- Se muestran los Años -->
							<logic:iterate name="metasIndicador" property="metasAnualesParciales" id="metaAnualParciales">

								<!-- Se valida si la Meta está bloqueada -->
								<logic:equal name="metaAnualParciales" property="metaAnual.protegido" value="true">
									<bean:define id="metaSoloLectura" scope="page" value="this.blur();"></bean:define>
									<bean:define id="claseEstiloCelda" scope="page" value="metaProtegida"></bean:define>
								</logic:equal>

								<td class="celdaMetas" align="center">
									<input 
										type="text" 
										onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, <bean:write name="numeroDecimales" />, true)" 
										ondblclick="editarMetasParciales()" 
										onfocus="<bean:write name="metaSoloLectura" scope="page"/>" 
										onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, <bean:write name="numeroDecimales" />, true);" 
										onblur="validarEntradaNumeroEventoOnBlur(this, event, <bean:write name="numeroDecimales" />, true)" 
										align="right" class="<bean:write name="claseEstiloCelda" scope="page"/>" 
										style="text-align: right" 
										name="valorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										id="valorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metaAnualParciales" property="metaAnual.valorGrande" />">
									<input 
										type="hidden" 
										id="valorAnteriorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="valorAnteriorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metaAnualParciales" property="metaAnual.valorGrande" />">
									<input 
										type="hidden" 
										id="numeroPeriodosIndicadorId<bean:write 
										name="metasIndicador" 
										property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="numeroPeriodosIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metaAnualParciales" property="numeroPeriodos"  />">
									<input 
										type="hidden" 
										id="nombreIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="nombreIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metasIndicador" property="indicador.nombre"  />">
									<input 
										type="hidden" 
										id="anoIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="anoIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano"  />">
									<input 
										type="hidden" 
										id="IndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="IndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metasIndicador" property="indicador.indicadorId"  />">
									<input 
										type="hidden" 
										id="numeroDecimalesIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="anoIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="numeroDecimales" />">
									<input 
										type="hidden" 
										id="tipoCorteIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="tipoCorteIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metasIndicador" property="indicador.corte" />">
									<input 
										type="hidden" 
										id="tipoCargaMetaIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />"
										name="tipoCargaMetaIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />" 
										value="<bean:write name="metasIndicador" property="indicador.tipoCargaMeta" />">
								</td>
							</logic:iterate>
						</tr>

					</logic:iterate>

				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

		<%-- Se itera por la lista de Indicadores y sus respectivas Metas Anuales --%>
		<logic:iterate name="editarMetasForm" property="metasIndicadores" id="metasIndicador">
			<bean:define id="numeroDecimales" name="metasIndicador" property="indicador.numeroDecimales" type="java.lang.Byte" />
			<!-- Se muestran los Años -->
			<logic:iterate name="metasIndicador" property="metasAnualesParciales" id="metaAnualParciales">
			var numero = document.editarMetasForm.valorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />.value;
			var numeroFormateado = formatearNumero(numero, false, <bean:write name="numeroDecimales" />);
			document.editarMetasForm.valorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />.value = numeroFormateado;
			document.editarMetasForm.valorAnteriorIndicadorId<bean:write name="metasIndicador" property="indicador.indicadorId" />serieId<bean:write name="editarMetasForm" property="serieId" />periodo<bean:write name="metaAnualParciales" property="metaAnual.metaId.periodo" />ano<bean:write name="metaAnualParciales" property="metaAnual.metaId.ano" />.value = numeroFormateado;
			</logic:iterate>
		</logic:iterate>

		</script>

	</tiles:put>

</tiles:insert>