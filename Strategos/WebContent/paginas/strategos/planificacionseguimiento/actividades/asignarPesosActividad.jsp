<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (01/03/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.asignarpesosactividad.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function guardar() 
			{
				if (validar(document.asignarPesosActividadForm)) 
				{
					window.document.asignarPesosActividadForm.action='<html:rewrite action="/planificacionseguimiento/actividades/asignarPesosActividad" />?funcion=guardar&funcionCierre=cerrar';
					window.document.asignarPesosActividadForm.submit();
				}
			}
	
			function actualizar() 
			{
				window.document.asignarPesosActividadForm.action='<html:rewrite action="/planificacionseguimiento/actividades/asignarPesosActividad" />?actividadId=' + document.asignarPesosActividadForm.actividadId.value + '&proyectoId=' + document.asignarPesosActividadForm.proyectoId.value + '&iniciativaId=' + document.asignarPesosActividadForm.iniciativaId.value + '&organizacionId=' + document.asignarPesosActividadForm.organizacionId.value + '&funcionCierre=';
				window.document.asignarPesosActividadForm.submit();
			}
	
			function cancelar() 
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planificacionseguimiento/actividades/asignarPesosActividad" />?funcion=cancelar', null, 'finalizarCancelar()');
			}
	
			function finalizarCancelar() 
			{
				window.close();
			}
	
			function validar(forma) 
			{
				if (!actualizarPesoTotal(true)) 
					return false;	
				
				if (forma.pesoTotalActividades.value != '') 
				{
					if (parseInt(forma.pesoTotalActividades.value) == 100) 
					{
						return true;
					} 
					else if (parseInt(forma.pesoTotalActividades.value) >= 100)
					{
						alert('<vgcutil:message key="jsp.asignarpesosactividad.mensaje.sumapesocienmayor" />');
						return false;
					}
					else
					{
						alert('<vgcutil:message key="jsp.asignarpesosactividad.mensaje.sumapesocienmenor" />');
						return false;
					}					
				} 
				else 
				{
					return true;
				}
			}
	
			function actualizarPesoTotal(validar) 
			{
				var total = 0;
				var hayPesos = false;
				var totalActividades = 0;
				var totalConPeso = 0;
				for (var index = 0; index < window.document.asignarPesosActividadForm.elements.length; index++) 
				{
					if (window.document.asignarPesosActividadForm.elements[index].name.indexOf('pesoActividad') > -1) 
					{
						totalActividades++;
						if ((window.document.asignarPesosActividadForm.elements[index].value != null) && (window.document.asignarPesosActividadForm.elements[index].value != '')) 
						{
							hayPesos = true;
							total = total + convertirNumeroFormateadoToNumero(window.document.asignarPesosActividadForm.elements[index].value, false);
							totalConPeso++;
						}
					}
				}
	
				if (hayPesos) 
					window.document.asignarPesosActividadForm.pesoTotalActividades.value = formatearNumero(total, false, 2);
				else 
					window.document.asignarPesosActividadForm.pesoTotalActividades.value = '';
				
				if ((validar != null) && (validar == true)) 
				{
					if ((totalConPeso > 0) && (totalConPeso != totalActividades)) 
					{
						alert('<vgcutil:message key="jsp.asignarpesosactividad.mensaje.noactividadsinpeso" />');
						return false;
					} 
					else 
						return true;
				}
			}
	
			function limpiarPesos() 
			{
				if (!confirm('<vgcutil:message key="jsp.asignarpesosactividad.mensaje.confirmarlimpiarpesos" />')) 
					return;
				
				for (var index = 0; index < window.document.asignarPesosActividadForm.elements.length; index++) 
				{
					if (window.document.asignarPesosActividadForm.elements[index].name.indexOf('pesoActividad') > -1) 
						window.document.asignarPesosActividadForm.elements[index].value = '';
				}
				actualizarPesoTotal(false);
			}

		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planificacionseguimiento/actividades/asignarPesosActividad" styleClass="formaHtml">

			<html:hidden property="actividadId" />
			<html:hidden property="proyectoId" />
			<html:hidden property="iniciativaId" />
			<html:hidden property="organizacionId" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.asignarpesosactividad.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.asignarpesosactividad.organizacion" /></b></td>
							<td>
								<logic:notEmpty scope="session" name="asignarPesosActividadForm" property="organizacionNombre">
									<bean:write scope="session" name="asignarPesosActividadForm" property="organizacionNombre" />
								</logic:notEmpty>
								<logic:empty scope="session" name="asignarPesosActividadForm" property="organizacionNombre">
									<vgcutil:message key="jsp.asignarpesosactividad.noaplica" />
								</logic:empty>
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="100px">
								<logic:equal scope="session" name="asignarPesosActividadForm" property="tipoPadre" value="0">
									<b><vgcutil:message key="jsp.asignarpesosactividad.iniciativa" /></b>
								</logic:equal>
								<logic:equal scope="session" name="asignarPesosActividadForm" property="tipoPadre" value="1">
									<b><vgcutil:message key="jsp.asignarpesosactividad.actividad" /></b>
								</logic:equal>
							</td>
							<td>
								<logic:notEmpty scope="session" name="asignarPesosActividadForm" property="padreNombre">
									<bean:write scope="session" name="asignarPesosActividadForm" property="padreNombre" />
								</logic:notEmpty>
								<logic:empty scope="session" name="asignarPesosActividadForm" property="padreNombre">
									<vgcutil:message key="jsp.asignarpesosactividad.noaplica" />
								</logic:empty>
							</td>
						</tr>
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Encabezados --%>
				<div style="height: 390px; overflow: auto;">
					<vgcinterfaz:visorLista 
						namePaginaLista="asignarPesosActividad.paginaActividades" 
						nombre="visorActividades" 
						messageKeyNoElementos="jsp.asignarpesosactividad.noactividades" 
						nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

					<%-- Encabezados --%>
					<vgcinterfaz:columnaVisorLista nombre="nombre" width="580px">
						<vgcutil:message key="jsp.asignarpesosactividad.columna.nombre" />
					</vgcinterfaz:columnaVisorLista>

					<vgcinterfaz:columnaVisorLista nombre="peso" width="100px">
						<vgcutil:message key="jsp.asignarpesosactividad.columna.peso" />
					</vgcinterfaz:columnaVisorLista>

					<%-- Filas --%>
					<vgcinterfaz:filasVisorLista nombreObjeto="actividad">

						<vgcinterfaz:visorListaFilaId>
							<bean:write name="actividad" property="actividadId" />
						</vgcinterfaz:visorListaFilaId>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="actividad" property="nombre" />
						</vgcinterfaz:valorFilaColumnaVisorLista>

						<vgcinterfaz:valorFilaColumnaVisorLista nombre="peso" align="center">
							<input 
								style="text-align: right" 
								onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 3, false);" 
								onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 3, false);actualizarPesoTotal();" 
								onblur="validarEntradaNumeroEventoOnBlur(this, event, 3, false)" 
								type="text" 
								name="pesoActividad<bean:write name="actividad" property="actividadId" />" 
								value="<bean:write name="actividad" property="peso" />" 
								class="cuadroTexto" 
								size="10" 
								maxlength="10" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista></div>
				<table width="100%">
					<tr>
						<td>
						<hr width="100%">
						</td>
					</tr>
					<tr class="mouseFueraCuerpoListView">
						<td align="right">
							<b><vgcutil:message key="jsp.asignarpesosactividad.pesototal" />:</b>&nbsp;&nbsp;&nbsp;&nbsp;
							<input 
								type="text" 
								name="pesoTotalActividades" 
								class="cuadroTexto" 
								size="10" 
								maxlength="10" 
								disabled="disabled" 
								style="text-align: right; color: black;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<table width="100%">
						<tr>
							<td align="right">
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:limpiarPesos();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="jsp.asignarpesosactividad.limpiarpesos" />
								</a>&nbsp;&nbsp;
								<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> 
									<vgcutil:message key="boton.aceptar" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> 
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="boton.cancelar" />
								</a>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script type="text/javascript">
			<logic:notEmpty name="asignarPesosActividadForm" property="funcionCierre">
				if (this.opener.onAsignarPesosActividadedes)
					this.opener.onAsignarPesosActividadedes();
				window.close();
			</logic:notEmpty>

			for (var index = 0; index < window.document.asignarPesosActividadForm.elements.length; index++) 
			{
				if (window.document.asignarPesosActividadForm.elements[index].name.indexOf('pesoActividad') > -1) 
				{
					var numero = window.document.asignarPesosActividadForm.elements[index].value;
					var numeroFormateado = formatearNumero(numero, false, 2);
					window.document.asignarPesosActividadForm.elements[index].value = numeroFormateado;
				}
			}

			actualizarPesoTotal();
		</script>
	</tiles:put>
</tiles:insert>
