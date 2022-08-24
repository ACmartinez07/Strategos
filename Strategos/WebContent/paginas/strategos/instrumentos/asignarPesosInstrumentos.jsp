<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (16/08/2022) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message
			key="jsp.asignar.pesos.portafolio.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">
			
		console.log()
			function cancelar() {
				window.close();
			}
			
			function aceptar(){				
			}
			
			function actualizar() 
			{								
			}
		</script>

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representaci�n de la Forma --%>
		<html:form action="/instrumentos/asignarPesosInstrumentos" styleClass="formaHtml">
					
			<vgcinterfaz:contenedorForma>

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message
						key="jsp.asignar.pesos.portafolio.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>
				
				<%-- Barra Gen�rica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100" style="padding: 1px;">						
						 <tr class="barraFiltrosForma">
							<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.estatus" /></td>
							<td>
								<html:select property="estatus" styleClass="cuadroTexto" size="1" disabled="true">																								
									<html:option value="1">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.ejecucion" />
									</html:option>																							
								</html:select>
							</td>
							<td align="left"><vgcutil:message key="jsp.pagina.instrumentos.anio" /></td>
							<td colspan="3" >
								<html:text property="anio" size="5" maxlength="4" styleClass="cuadroTexto"/>
							</td>						
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>
				
				<%-- Encabezados --%>
				<div style="height: 390px; overflow: auto;">
					<vgcinterfaz:visorLista namePaginaLista="paginaInstrumentos" width="100%" messageKeyNoElementos="jsp.asignar.pesos.portafolio.nohayelementos" 
						nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase"
						nombre="visorInstrumentos" 
						>	
						
						<vgcinterfaz:columnaVisorLista nombre="nombre" width="580px" >
							<vgcutil:message key="jsp.pagina.instrumentos.nombre" />
						</vgcinterfaz:columnaVisorLista>
									
					
					
					<vgcinterfaz:filasVisorLista nombreObjeto="instrumentos">
		
						<vgcinterfaz:visorListaFilaId>
							<bean:write name="instrumentos" property="instrumentoId" />
						</vgcinterfaz:visorListaFilaId>																										
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
							<bean:write name="instrumentos" property="nombreCorto" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
											
						
					</vgcinterfaz:filasVisorLista>					
					</vgcinterfaz:visorLista>
				</div>
				
				<table style="width: 100%;">
					<tr>
						<td>
							<hr style="width: 100%;">
						</td>
					</tr>
					<tr class="mouseFueraCuerpoListView">
						<td align="right">
							<b><vgcutil:message key="jsp.asignar.pesos.portafolio.pesototal" />:</b>&nbsp;&nbsp;&nbsp;&nbsp;
							<input 
								type="text" 
								name="pesoTotal" 
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
					<table style="width: 100%;">
						<tr>
							<td align="right">
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:limpiarPesos();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="jsp.asignar.pesos.portafolio.limpiarpesos" />
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
			for (var index = 0; index < window.document.gestionarInstrumentosForm.elements.length; index++) 
			{
				if (window.document.gestionarInstrumentosForm.elements[index].name.indexOf('pesoTotal') > -1) 
				{
					var numero = window.document.gestionarInstrumentosForm.elements[index].value;
					var numeroFormateado = formatearNumero(numero, false, 2);
					window.document.gestionarInstrumentosForm.elements[index].value = numeroFormateado;
				}
			}
	
			actualizarPesoTotal();		
		</script>
		
		<script>
			<logic:equal name="gestionarInstrumentosForm" property="estatus" value="0">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.actualizados.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="gestionarInstrumentosForm" property="estatus" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.actualizados.no.ok" />', 80, 300);
			</logic:equal>
			<logic:equal name="gestionarInstrumentosForm" property="estatus" value="2">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.editarregistro.noencontrado" />', 80, 300);
			</logic:equal>
		</script>
		
	</tiles:put>
</tiles:insert>