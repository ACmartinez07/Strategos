<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.framework.gestionarauditorias.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			var _fechaDesde;
			var _fechaHasta;
			
			function configurarVisorAuditorias() 
			{
				configurarVisorLista('com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase', 'visorAuditorias', '<vgcutil:message key="jsp.framework.gestionarauditorias.titulo" />');
			}
			
			function validar() 
			{
				if (document.gestionarAuditoriasMedicionForm.fechaDesde.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.vacio" /> ');
					return false;
				}

				if (document.gestionarAuditoriasMedicionForm.fechaHasta.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.vacio" /> ');
					return false;
				}
				
		 		if (!fechaValida(document.gestionarAuditoriasMedicionForm.fechaDesde))
	 			{
		 			alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.invalido" /> ');
		 			return;
	 			}

		 		if (!fechaValida(document.gestionarAuditoriasMedicionForm.fechaHasta))
	 			{
		 			alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.invalido" /> ');
		 			return;
	 			}
				
				var fecha1 = document.gestionarAuditoriasMedicionForm.fechaDesde.value.split("/");
				var fecha2 = document.gestionarAuditoriasMedicionForm.fechaHasta.value.split("/");

				var diaDesde = fecha1[0];
				var diaHasta = fecha2[0];
				
				var mesDesde = fecha1[1];
				var mesHasta = fecha2[1];
				
				var anoDesde = fecha1[2];
				var anoHasta = fecha2[2];

		 		var desde = parseInt(anoDesde + "" + (mesDesde.length == 1 ? ("0" + mesDesde) : mesDesde) + "" + (diaDesde.length == 1 ? ("0" + diaDesde) : diaDesde));
				var hasta = parseInt(anoHasta + "" + (mesHasta.length == 1 ? ("0" + mesHasta) : mesHasta) + "" + (diaHasta.length == 1 ? ("0" + diaHasta) : diaHasta));

				if (hasta<desde) 
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.rango.fechas.invalido" /> ');
					return false;
				} 
		 		
				return true;
			}
			
			function buscar() 
			{
				if (validar())
					document.gestionarAuditoriasMedicionForm.submit();
			}

			function limpiarBusqueda() 
			{
				document.gestionarAuditoriasMedicionForm.fechaDesde.value = _fechaDesde;
				document.gestionarAuditoriasMedicionForm.fechaHasta.value = _fechaHasta;
				document.gestionarAuditoriasMedicionForm.accion.value = '';
				document.gestionarAuditoriasMedicionForm.usuario.value = '';
				buscar();
			}
			
			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.gestionarAuditoriasMedicionForm.fechaDesde' , document.gestionarAuditoriasMedicionForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaDesde() 
			{
				document.gestionarAuditoriasMedicionForm.fechaDesde.value = '';
			}
			
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.gestionarAuditoriasMedicionForm.fechaHasta' , document.gestionarAuditoriasMedicionForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaHasta() 
			{
				document.gestionarAuditoriasMedicionForm.fechaHasta.value = '';
			}

			function actualizar() 
			{
				document.gestionarAuditoriasMedicionForm.submit();
			}
											
			function gestionarAuditoriasDetalle(auditoriaId) 
			{
				queryString = '?';
				if (auditoriaId != null) 
					queryString = queryString + 'auditoriaId=' + auditoriaId ;
				
				abrirVentanaModal('<html:rewrite action="/framework/auditorias/gestionarAuditoriasMedicionDetalle"/>' + queryString,'auditorias', '930', '690');
			}
			
			function reporteAuditoriaExcel() 
			{						
				queryString = '?';
				if(document.gestionarAuditoriasMedicionForm.fechaDesde.value != null || document.gestionarAuditoriasMedicionForm.fechaDesde.value !=''){
					queryString = queryString + 'fechaDesde=' + document.gestionarAuditoriasMedicionForm.fechaDesde.value ;
				}
				if(document.gestionarAuditoriasMedicionForm.fechaHasta.value != null || document.gestionarAuditoriasMedicionForm.fechaHasta.value !=''){
					queryString = queryString + '&fechaHasta=' + document.gestionarAuditoriasMedicionForm.fechaHasta.value ;
				}
				if(document.gestionarAuditoriasMedicionForm.accion.value != null || document.gestionarAuditoriasMedicionForm.accion.value !=''){
					if(document.gestionarAuditoriasMedicionForm.accion.value == 'Inserción'){
						queryString = queryString + '&accion=insercion'	
					}else if(document.gestionarAuditoriasMedicionForm.accion.value == 'Modificación'){
						queryString = queryString + '&accion=modificacion'	
					}else if(document.gestionarAuditoriasMedicionForm.accion.value == 'Inserción-Modificación'){
						queryString = queryString + '&accion=insercion-modificacion'	
					}else{
						queryString = queryString + '&accion=' + document.gestionarAuditoriasMedicionForm.accion.value;
					}
				}
				if(document.gestionarAuditoriasMedicionForm.usuario.value != null || document.gestionarAuditoriasMedicionForm.usuario.value !=''){
					if(document.gestionarAuditoriasMedicionForm.usuarioId.value != 0){
						queryString = queryString + '&usuario=' + document.gestionarAuditoriasMedicionForm.usuarioId.value ;	
					}else{
						queryString = queryString + '&usuario=';
					}					
				}
				if(document.gestionarAuditoriasMedicionForm.organizacion.value != null || document.gestionarAuditoriasMedicionForm.organizacion.value !=''){
					if(document.gestionarAuditoriasMedicionForm.organizacionId.value != 0){
						queryString = queryString + '&organizacion=' + document.gestionarAuditoriasMedicionForm.organizacionId.value ;	
					}else{
						queryString = queryString + '&organizacion=';
					}						
				}
				
				window.location.href='<sslext:rewrite action="/framework/auditorias/reporteAuditoriaMedicionExcel"/>' + queryString;	
			}
			
			function reporteAuditoriaPdf() 
			{
				queryString = '?';
				if(document.gestionarAuditoriasMedicionForm.fechaDesde.value != null || document.gestionarAuditoriasMedicionForm.fechaDesde.value !=''){
					queryString = queryString + 'fechaDesde=' + document.gestionarAuditoriasMedicionForm.fechaDesde.value ;
				}
				if(document.gestionarAuditoriasMedicionForm.fechaHasta.value != null || document.gestionarAuditoriasMedicionForm.fechaHasta.value !=''){
					queryString = queryString + '&fechaHasta=' + document.gestionarAuditoriasMedicionForm.fechaHasta.value ;
				}
				if(document.gestionarAuditoriasMedicionForm.accion.value != null || document.gestionarAuditoriasMedicionForm.accion.value !=''){
					if(document.gestionarAuditoriasMedicionForm.accion.value == 'Inserción'){
						queryString = queryString + '&accion=insercion'	
					}else if(document.gestionarAuditoriasMedicionForm.accion.value == 'Modificación'){
						queryString = queryString + '&accion=modificacion'	
					}else if(document.gestionarAuditoriasMedicionForm.accion.value == 'Inserción-Modificación'){
						queryString = queryString + '&accion=insercion-modificacion'	
					}else{
						queryString = queryString + '&accion=' + document.gestionarAuditoriasMedicionForm.accion.value;
					}
				}
				if(document.gestionarAuditoriasMedicionForm.usuario.value != null || document.gestionarAuditoriasMedicionForm.usuario.value !=''){
					if(document.gestionarAuditoriasMedicionForm.usuarioId.value != 0){
						queryString = queryString + '&usuario=' + document.gestionarAuditoriasMedicionForm.usuarioId.value ;	
					}else{
						queryString = queryString + '&usuario=';
					}					
				}
				if(document.gestionarAuditoriasMedicionForm.organizacion.value != null || document.gestionarAuditoriasMedicionForm.organizacion.value !=''){
					if(document.gestionarAuditoriasMedicionForm.organizacionId.value != 0){
						queryString = queryString + '&organizacion=' + document.gestionarAuditoriasMedicionForm.organizacionId.value ;	
					}else{
						queryString = queryString + '&organizacion=';
					}						
				}
				
				abrirReporte('<html:rewrite action="/framework/auditorias/reporteAuditoriaMedicionPdf"/>'+ queryString, 'auditoriasResumido', '750', '550');
			}
			
			function seleccionarOrganizacion() 
			{
				abrirSelectorOrganizaciones('gestionarAuditoriasMedicionForm', 'organizacion', 'organizacionId', null, null, null);
			}
			
			function reporteAuditoriaProyecto(){
				abrirVentanaModal('<html:rewrite action="/framework/auditorias/reporteMedicionProyecto" />?' , "reporteAuditoriaMedicionProyecto", 500, 490);
			}
			
			function limpiarOrganizacion() 
			{
				document.gestionarAuditoriasForm.organizacionId.value = '';
				document.gestionarAuditoriasForm.organizacion.value = '';
			}
			
			function seleccionarUsuario() 
			{
				abrirSelectorUsuarios('gestionarAuditoriasMedicionForm', 'usuario', 'usuarioId', null, null, null, null);
			}
			
			function limpiarUsuario() 
			{
				document.gestionarAuditoriasMedicionForm.usuarioId.value = '';
				document.gestionarAuditoriasMedicionForm.usuario.value = '';
			}
			
			function limpiarInstanciaNombre() 
			{
				document.gestionarAuditoriasMedicionForm.organizacionId.value = '';
				document.gestionarAuditoriasMedicionForm.organizacion.value = '';
			}
			
			function seleccionarOrganizaciones() 
		    {
				abrirSelectorOrganizaciones('gestionarAuditoriasMedicionForm', 'organizacion', 'organizacionId', null);
			}

		</script>

		<jsp:include flush="true" page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<html:form action="/framework/auditorias/gestionarAuditoriasMedicion" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.gestionarauditorias.mediciones.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="AUDITORIA_PRINT_SET"/>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientasAuditorias" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorAuditorias();" permisoId="AUDITORIA_SET_VIEW" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Evaluacion --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEvaluacionAuditorias" key="menu.evaluacion">
								<vgcinterfaz:botonMenu key="reporte.framework.auditorias.detallado.titulo" onclick=";" />
								<vgcinterfaz:botonMenu key="reporte.framework.auditorias.proyecto.detallado.titulo" onclick="reporteAuditoriaProyecto();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<vgcinterfaz:barraHerramientas nombre="barraGestionarAuditoriasEventos" agregarSeparadorInferior="true" agregarSeparadorSuperior="false">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="buscar" onclick="javascript:buscar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.buscar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="limpiarFiltro" pathImagenes="/componentes/barraHerramientas/" nombre="limpiarBusqueda" onclick="javascript:limpiarBusqueda();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.limpiar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfResumido" onclick="javascript:reporteAuditoriaPdf();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.detallada" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:reporteAuditoriaExcel();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.usuarios.reportes.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						
					 <vgcinterfaz:barraHerramientasSeparador />
						
					</vgcinterfaz:barraHerramientas>
					<%-- Filtros --%>
					
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechadesde" /></td>
							<td><html:text property="fechaDesde" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechahasta" /></td>
							<td><html:text property="fechaHasta" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.columna.tipoevento" /></td>
							<td><html:select property="accion" styleClass="cuadroTexto" size="1">
								<html:option value="">
									<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.todos" />
								</html:option>
								<html:option value="Inserción">
									<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.filtro.insercion" />
								</html:option>
								<html:option value="Modificación">
									<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.filtro.modificacion"   />
								</html:option>
								<html:option value="Inserción-Modificación">
									<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.filtro.inser.modificacion" />
								</html:option>
							</html:select></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="120px"><vgcutil:message key="jsp.framework.gestionarauditorias.columna.usuario" /></td>
							<td width="320px"><html:text property="usuario" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><html:hidden property="usuarioId" />
								<img style="cursor:pointer" onclick="seleccionarUsuario()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Usuario'>&nbsp;<img style="cursor:pointer" onclick="limpiarUsuario()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="120px"><vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.organizacion" /></td>
							<td width="320px"><html:text property="organizacion" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><html:hidden property="organizacionId" />
								<img style="cursor:pointer" onclick="seleccionarOrganizaciones();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Organizacion'>&nbsp;<img style="cursor:pointer" onclick="limpiarInstanciaNombre()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>							
							<td>&nbsp;</td>
						</tr>
						
					</table>
					
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<vgcinterfaz:visorLista namePaginaLista="paginaAuditorias" nombre="visorAuditorias" messageKeyNoElementos="jsp.framework.gestionarauditorias.noauditorias" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="fecha" width="180px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.fecha" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="usuario" width="200px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.usuario" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="accion" width="130px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.accion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="organizacion" width="250px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.organizacion" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="indicador" width="340px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.indicador" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="clase" width="200px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.clase" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="iniciativa" width="200px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.iniciativa" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="periodoInicial" width="90px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.periodo.inicial" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="periodoFinal" width="80px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.periodo.final" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="detalle" width="150px" >
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.detalle" />
					</vgcinterfaz:columnaVisorLista>
					
					<vgcinterfaz:filasVisorLista nombreObjeto="auditoriaMedicion">
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fecha" align="center">
							<bean:write name="auditoriaMedicion" property="fechaEjecucion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="usuario" align="left">
							<bean:write name="auditoriaMedicion" property="usuario" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="accion">
							<bean:write name="auditoriaMedicion" property="accion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="organizacion" align="left">
							<bean:write name="auditoriaMedicion" property="organizacion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>					
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="indicador" align="left">
							<bean:write name="auditoriaMedicion" property="indicador" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="clase" align="left">
							<bean:write name="auditoriaMedicion" property="clase" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="iniciativa" align="left">
							<bean:write name="auditoriaMedicion" property="iniciativa" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="periodoInicial" align="left">
							<bean:write name="auditoriaMedicion" property="periodo" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="periodoFinal" align="left">
							<bean:write name="auditoriaMedicion" property="periodoFinal" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="detalle" align="left">
							<a href="javascript:gestionarAuditoriasDetalle('<bean:write name="auditoriaMedicion" property="auditoriaMedicionId" />');">Consultar Detalle</a>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaAuditorias" labelPage="inPagina" action="javascript:consultar(gestionarAuditoriasMedicionForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarAuditoriasMedicionForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaAuditorias" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>
				

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			_fechaDesde = document.gestionarAuditoriasMedicionForm.fechaDesde.value;
			_fechaHasta = document.gestionarAuditoriasMedicionForm.fechaHasta.value;
		</script>

	</tiles:put>

</tiles:insert>
