<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Juan Manuel Cruz (11/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			var pasoActual = 1;
			var indiceOrganizaciones;
			var indiceClases;
			var indiceIndicadores;
			var orgSeleccionadas;
			var clsSeleccionadas;			
			var indicadoresSeleccionados;
			var sepOrg = '<bean:write name="reporteComiteEjecutivoForm" property="separadorCampos" scope="request"/>';
			var sepGrp = '<bean:write name="reporteComiteEjecutivoForm" property="separadorFilas" scope="request"/>';

			function cerrar() 
			{
				this.close();
			}

			function loadIndicesObjeto(myForm, nombreObjeto) 
			{
				var indices = new Array();
				var len = myForm.elements.length;
				for (index = 0; index < len; index++) 
				{
					if (myForm.elements[index].name == nombreObjeto) 
						indices.push(index);
				}

				return indices;
			}
			
			function loadIndicadoresSeleccionados(myForm, checkbox) 
			{
				if (checkbox != null) 
				{
					if (checkbox.value == '0') 
					{
						checkbox.checked = false;
						return;
					}
				}

				// Buscar las organizaciones seleccionadas
				var len = indiceIndicadores.length;
				indicadoresSeleccionados = "";
				for (index = 0; index < len; index++) 
				{
					if (myForm.elements[indiceIndicadores[index]].checked) 
					{
						indicadoresSeleccionados = indicadoresSeleccionados
								+ myForm.elements[indiceIndicadores[index]].value
								+ sepOrg;
					}
				}
			}

			// Funcion que carga las organizaciones seleccionados por organización
			function loadOrgSeleccionados(myForm, checkbox) 
			{

				if (checkbox != null) 
				{
					if (checkbox.value == '0') 
					{
						checkbox.checked = false;
						return;
					}
				}

				// Buscar las organizaciones seleccionadas
				var len = indiceOrganizaciones.length;
				orgSeleccionadas = "";
				for (index = 0; index < len; index++) 
				{
					if (myForm.elements[indiceOrganizaciones[index]].checked) 
					{
						orgSeleccionadas = orgSeleccionadas
								+ myForm.elements[indiceOrganizaciones[index]].value
								+ sepOrg;
					}
				}
			}
			
			// Funcion que carga las clases seleccionados por organización
			function loadClasesSeleccionados(myForm, checkbox) 
			{
				if (checkbox != null) 
				{
					if (checkbox.value == '0') 
					{
						checkbox.checked = false;
						return;
					}
				}

				// Buscar las organizaciones seleccionadas
				var len = indiceClases.length;
				clsSeleccionadas = "";
				for (index = 0; index < len; index++) 
				{
					if (myForm.elements[indiceClases[index]].checked) 
						clsSeleccionadas = clsSeleccionadas + myForm.elements[indiceClases[index]].value + sepOrg;
				}
			}


			function seleccionarFechaReporte() 
			{
				mostrarCalendario('document.all.fecha', document.reporteComiteEjecutivoForm.fecha.value, '<vgcutil:message key="formato.fecha.corta" />');
			}

			function limpiarFechaReporte(campoNombre) 
			{
				campoNombre.value = "";
			}
			
			function ejecutar()
			{	
				var valido = true;
				
				if (!document.reporteComiteEjecutivoForm.vista[0].checked && !document.reporteComiteEjecutivoForm.vista[1].checked)
				{
					alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.vista.requerido" />');
					valido = false;
					return;
				}
				
				if (document.reporteComiteEjecutivoForm.fecha.value == '')
				{
					alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.fecha.requerido" />');
					valido = false;
					return;
				}
				
				if (orgSeleccionadas.length == 0) 
				{
					alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.organizaciones.requerido" />');
					valido = false;
					return;
				}
				
				if (!document.reporteComiteEjecutivoForm.vista[1].checked && clsSeleccionadas.length == 0) 
				{
					alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.clases.requerido" />');
					valido = false;
					return;
				}
				
				if (indicadoresSeleccionados.length == 0) 
				{
					alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.indicadores.requerido" />');
					valido = false;
					return;
				}
				
				if (valido) 
				{
					var vista = "";
					if (document.reporteComiteEjecutivoForm.vista[0].checked)
						vista = "1";
					else if (document.reporteComiteEjecutivoForm.vista[1].checked)
						vista = "2";
					
					abrirReporte('<html:rewrite action="/reportes/indicadores/reporteComiteEjecutivoPDF"/>?vista=' + vista 
							+ '&orientacion=H' 
							+ '&fecha=' + document.reporteComiteEjecutivoForm.fecha.value 
							+ '&organizaciones=' + orgSeleccionadas 
							+ '&clases=' + clsSeleccionadas
							+ '&indicadores=' + indicadoresSeleccionados , 'reporte');
				}
			}

			function cancelar() 
			{
				this.close();
			}

			function siguiente() 
			{
				var valido = true;

				switch (pasoActual) 
				{
				case 1:
					if (!document.reporteComiteEjecutivoForm.vista[0].checked && !document.reporteComiteEjecutivoForm.vista[1].checked)
					{
						alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.vista.requerido" />');
						valido = false;							
					}
					
					if (document.reporteComiteEjecutivoForm.fecha.value == '')
					{
						alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.fecha.requerido" />');
						valido = false;					
					}
					break;
				case 2:
					if (orgSeleccionadas.length == 0) {
						alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.organizaciones.requerido" />');
						valido = false;	
					}
					break;
				case 3:
					if (clsSeleccionadas.length == 0) {
						alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.clases.requerido" />');
						valido = false;	
					}
					break;
				case 4:
					if (indicadoresSeleccionados.length == 0) {
						alert('<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.indicadores.requerido" />');
						valido = false;	
					}
					break;
				}
				
				if (valido) 
				{
					pasoActual = pasoActual + 1;
					mostrarBotones(pasoActual);
				}
			}

			function previo() 
			{
				pasoActual = pasoActual - 1;
				mostrarBotones(pasoActual);
			}

			function crearBoton(nombreBoton, accionBoton) 
			{
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
						+ nombreBoton + '</a>';

				return boton;
			}

			function mostrarBotones(paso) 
			{
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.ejecutar.alt" />';
				var accionBotonFinalizar = 'javascript:ejecutar();';

				switch (paso) 
				{
				case 1:
					botones = botones
							+ crearBoton(nombreBotonFinalizar,
									accionBotonFinalizar) + separacion;
					botones = botones
							+ crearBoton(nombreBotonSiguiente,
									accionBotonSiguiente) + separacion;
					botones = botones
							+ crearBoton(nombreBotonCancelar,
									accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelConfiguracionInicial" />
					break;
				case 2:
					botones = botones
							+ crearBoton(nombreBotonFinalizar,
							accionBotonFinalizar) + separacion;
					botones = botones
							+ crearBoton(nombreBotonPrevio, accionBotonPrevio)
							+ separacion;
					botones = botones
							+ crearBoton(nombreBotonSiguiente,
									accionBotonSiguiente) + separacion;
					botones = botones
							+ crearBoton(nombreBotonCancelar,
									accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelMostrarOrganizaciones" />
					break;
				case 3:
					
					if (document.reporteComiteEjecutivoForm.vista[0].checked)
					{
						botones = botones
						+ crearBoton(nombreBotonFinalizar,
								accionBotonFinalizar) + separacion;
						botones = botones
								+ crearBoton(nombreBotonPrevio, accionBotonPrevio)
								+ separacion;
						botones = botones
								+ crearBoton(nombreBotonSiguiente,
										accionBotonSiguiente) + separacion;
						botones = botones
								+ crearBoton(nombreBotonCancelar,
										accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelMostrarProductos" />
						
					}
					else if (document.reporteComiteEjecutivoForm.vista[1].checked)
					{
						botones = botones
						+ crearBoton(nombreBotonFinalizar,
								accionBotonFinalizar) + separacion;
						
						botones = botones
								+ crearBoton(nombreBotonPrevio, accionBotonPrevio)
								+ separacion;
						
						botones = botones
								+ crearBoton(nombreBotonCancelar,
										accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelMostrarVariables" />
					}
															
					break;
				case 4:
					botones = botones
					+ crearBoton(nombreBotonFinalizar,
							accionBotonFinalizar) + separacion;
					
					botones = botones
							+ crearBoton(nombreBotonPrevio, accionBotonPrevio)
							+ separacion;
					
					botones = botones
							+ crearBoton(nombreBotonCancelar,
									accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelMostrarVariables" />
					break;
				}

				var barraBotones = document.getElementById('barraBotones');
				barraBotones.innerHTML = botones;
			}
		</script>

		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/indicadores/reporteComiteEjecutivo">

			<html:hidden property="organizaciones" />
			<html:hidden property="clases" />
			<html:hidden property="indicadores" />

			<vgcinterfaz:contenedorForma>
				<!-- width="440px" height="450px" bodyAlign="center" bodyValign="middle" bodyCellpadding="15" -->

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
			    	..::<vgcutil:message
						key="jsp.reportes.parametroscomiteejecutivo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">

					<%-- Panel Presentacion --%>
					<vgcinterfaz:panelContenedor anchoPestana="50px"
						nombre="panelConfiguracionInicial" mostrarBorde="false">
						<b>
						<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.configuracioninicial" />
						</b>
						<br>
						<br>
						<br>
						<table class="bordeFichaDatos" width="100%" cellpadding="3"
							cellspacing="0" align="center" height="100%" border="0">
							<tr height="80px" valign="top">

								<td align="left"><vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.vista" />:</td>
								<td><html:radio property="vista" value="1" /> <vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.vista.producto" /><br>
									<br> <html:radio property="vista" value="2" /> <vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.vista.bancasegmento" />
								</td>
							</tr>

							<!-- Fecha -->
							<tr height="60px" valign="top">
								<td align="left"><vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.fecha" />:</td>
								<td><html:text property="fecha" size="10"
										onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
									<img style="cursor: pointer"
									onclick="seleccionarFechaReporte();"
									src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
									border="0" width="10" height="10"
									title="<vgcutil:message key="boton.calendario.alt" />"> <img
									style="cursor: pointer"
									onclick="limpiarFechaReporte(document.reporteComiteEjecutivoForm.fecha);"
									src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
									border="0" width="10" height="10"
									title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							</tr>
							<tr height="60%" valign="top">
								<td>&nbsp;</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<%-- Panel seleccion de plantilla --%>
					<vgcinterfaz:panelContenedor anchoPestana="50px"
						nombre="panelMostrarOrganizaciones" mostrarBorde="false">
						<b>
						<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.seleccioneorganizaciones" />
						</b>
						<br>
						<br>
						<br>
						<table class="bordeFichaDatos" width="600px" cellpadding="3"
							cellspacing="0" align="center" height="350px" border="0">
							<!-- Organizaciones -->
							<tr valign="top">
								<td width="50px" align="left"><vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.organizaciones" />:</td>
								<td><treeview:treeview
										name="parametrosReporteComiteEjecutivoArbolOrganizaciones"
										scope="session"
										baseObject="parametrosReporteComiteEjecutivoOrganizacionRoot"
										isRoot="true" fieldName="nombre" fieldId="organizacionId"
										fieldChildren="hijos" lblUrlObjectId="orgId"
										styleClass="treeview" checkbox="true"
										checkboxName="organizacion"
										pathImages="/componentes/visorArbol/"
										urlJs="/componentes/visorArbol/visorArbol.js" 
										checkboxOnClick="loadOrgSeleccionados(self.document.reporteComiteEjecutivoForm, this);" 
										/> 
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="50px"
						nombre="panelMostrarProductos" mostrarBorde="false">
						<b>
						<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.seleccioneclases" />
						</b>
						<br>
						<br>
						<br>
						<table class="bordeFichaDatos" width="600px" cellpadding="3"
							cellspacing="0" align="center" height="350px" border="0">
							<!-- Clases -->
							<tr valign="top">
								<td width="50px" align="left"><vgcutil:message
										key="jsp.reportes.parametroscomiteejecutivo.clases" />:</td>
								<td><treeview:treeview
										name="parametrosReporteComiteEjecutivoArbolClases"
										scope="session"
										baseObject="parametrosReporteComiteEjecutivoClases"
										isRoot="true" fieldName="nombre" fieldId="claseId"
										fieldChildren="hijos" lblUrlObjectId="clsId"
										styleClass="treeview" checkbox="true"
										checkboxName="clase"
										pathImages="/componentes/visorArbol/"
										urlJs="/componentes/visorArbol/visorArbol.js"
										checkboxOnClick="loadClasesSeleccionados(self.document.reporteComiteEjecutivoForm, this);" 
										/> 
										
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="50px"
						nombre="panelMostrarVariables" mostrarBorde="false">
						<b>
						<vgcutil:message key="jsp.reportes.parametroscomiteejecutivo.seleccioneindicadores" />
						</b>
						<br>
						<br>
						<br>
						<table class="bordeFichaDatos" width="600px" cellpadding="3"
							cellspacing="0" align="center" height="100%" border="0">
							<!-- Indicadores  -->
							<tr valign="top">
								<td width="50px" align="left"><vgcutil:message
									key="jsp.reportes.parametroscomiteejecutivo.indicadores" />:
								</td>
								<td>
									<vgcinterfaz:visorLista width="100%" useFrame="true" namePaginaLista="parametrosComiteEjecutivoIndicadores" messageKeyNoElementos="jsp.framework.editarusuario.nogrupos" nombre="visorListaIndicadores" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
									<vgcinterfaz:columnaVisorLista nombre="check" width="20px">
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="nombre">
										<vgcutil:message key="jsp.framework.editarusuario.listagrupos.nombre" />
									</vgcinterfaz:columnaVisorLista>

									<vgcinterfaz:filasVisorLista nombreObjeto="indicador">
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="check" align="center">
											<input type="checkbox" name="indicador" value="<bean:write name='indicador' property='indicadorId' />" onclick="loadIndicadoresSeleccionados(self.document.reporteComiteEjecutivoForm, this);">
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
											<bean:write name="indicador" property="nombre" />
										</vgcinterfaz:valorFilaColumnaVisorLista>

									</vgcinterfaz:filasVisorLista>

								</vgcinterfaz:visorLista>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

			<script language="Javascript">
				mostrarBotones(pasoActual);

				// Inicializacion de objetos de Javascript que manejan los grupos por organización
				indiceOrganizaciones = loadIndicesObjeto(self.document.reporteComiteEjecutivoForm, 'organizacion');
				
				indiceClases = loadIndicesObjeto(self.document.reporteComiteEjecutivoForm, 'clase');
				
				indiceIndicadores = loadIndicesObjeto(self.document.reporteComiteEjecutivoForm, 'indicador');
	
				if (indiceOrganizaciones.length > 0) 
				{
					var orgconf = document.reporteComiteEjecutivoForm.organizaciones.value.split(sepOrg);
					if (orgconf.length > 0)
					{
						for (index = 0; index < orgconf.length; index++) 
						{
							for (index2 = 0; index2 < indiceOrganizaciones.length; index2++) 
							{
								if (window.document.reporteComiteEjecutivoForm.elements[indiceOrganizaciones[index2]].value == orgconf[index])
								{
									window.document.reporteComiteEjecutivoForm.elements[indiceOrganizaciones[index2]].checked = true;
									break;
								}
							}
						}
					}

					loadOrgSeleccionados(self.document.reporteComiteEjecutivoForm, window.document.reporteComiteEjecutivoForm.elements[indiceOrganizaciones[0]]);
				}
				
				if (indiceClases.length > 0) 
				{
					var clsconf = document.reporteComiteEjecutivoForm.clases.value.split(sepOrg);
					if (clsconf.length > 0)
					{
						for (index = 0; index < clsconf.length; index++) 
						{
							for (index2 = 0; index2 < indiceClases.length; index2++) 
							{
								if (window.document.reporteComiteEjecutivoForm.elements[indiceClases[index2]].value == clsconf[index])
								{
									window.document.reporteComiteEjecutivoForm.elements[indiceClases[index2]].checked = true;
									break;
								}
							}
						}
					}
					
					loadClasesSeleccionados(self.document.reporteComiteEjecutivoForm, window.document.reporteComiteEjecutivoForm.elements[indiceClases[0]]);
				}
				
				if (indiceIndicadores.length > 0) 
				{
					var indconf = document.reporteComiteEjecutivoForm.indicadores.value.split(sepOrg);
					if (indconf.length > 0)
					{
						for (index = 0; index < indconf.length; index++) 
						{							
							for (index2 = 0; index2 < indiceIndicadores.length; index2++) 
							{
								if (window.document.reporteComiteEjecutivoForm.elements[indiceIndicadores[index2]].value == indconf[index])
								{
									window.document.reporteComiteEjecutivoForm.elements[indiceIndicadores[index2]].checked = true;
									break;
								}
							}
						}
					}
					
					loadIndicadoresSeleccionados(self.document.reporteComiteEjecutivoForm, window.document.reporteComiteEjecutivoForm.elements[indiceIndicadores[0]]);
				}
			</script>
		</html:form>
	</tiles:put>
</tiles:insert>