<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (21/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarOrganizacionForm" property="organizacionId" value="0">
			<vgcutil:message key="jsp.editarorganizacion.titulo.nuevo" />
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarOrganizacionForm" property="organizacionId" value="0">
			<vgcutil:message key="jsp.editarorganizacion.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarOrganizacionForm" property="bloqueado">
				<bean:write name="editarOrganizacionForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarOrganizacionForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
            
            // Inicializar botones de los cuadros Numéricos  
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');		

			function guardar() 
			{
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarOrganizacion"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarOrganizacionForm)) 
					window.document.editarOrganizacionForm.submit();
			}

			function cancelar() 
			{
				window.document.editarOrganizacionForm.action = '<html:rewrite action="/organizaciones/cancelarGuardarOrganizacion"/>';
				window.document.editarOrganizacionForm.submit();	
				this.close();
			}

			function validar(forma) 
			{
				if (validateEditarOrganizacionForm(forma)) 
				{
					var xml = '&porcentajeZonaAmarillaMinMaxIndicadores=' + document.editarOrganizacionForm.porcentajeZonaAmarillaMinMaxIndicadores.value;
					xml = xml + '&porcentajeZonaVerdeMetaIndicadores=' + document.editarOrganizacionForm.porcentajeZonaVerdeMetaIndicadores.value;
					xml = xml + '&porcentajeZonaAmarillaMetaIndicadores=' + document.editarOrganizacionForm.porcentajeZonaAmarillaMetaIndicadores.value;
					
					window.document.editarOrganizacionForm.action = '<html:rewrite action="/organizaciones/guardarOrganizacion" />'+ '?ts=<%= (new Date()).getTime() %>' + xml;
					return true;
				} 
				else 
					return false;
			}

			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}

			function corregirLongitud(cuadroTexto, longitudTexto) 
			{
				var texto = cuadroTexto;				
				var longitud = texto.value.length;				
				if (longitud > longitudTexto) 
				{
					texto.value = texto.value.substring(0, longitudTexto);
					alert('<vgcutil:message key="jsp.longitud.maxima" />');
					texto.focus();
				}
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarOrganizacionForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/organizaciones/guardarOrganizacion" focus="nombre">

			<html:hidden property="organizacionId" />
			<html:hidden property="padreId" />

			<vgcinterfaz:contenedorForma width="860px" height="570px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarOrganizacionForm" property="organizacionId" value="0">
						<vgcutil:message key="jsp.editarorganizacion.titulo.nuevo" />
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarOrganizacionForm" property="organizacionId" value="0">
						<vgcutil:message key="jsp.editarorganizacion.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="450px" width="800px" nombre="editarOrganizacion">

					<%-- Panel: Datos Básicos --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="datosBasicos">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" style="border-spacing:0px; border-collapse: collapse; padding: 0px;">

							<!-- Nombre -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.nombre" /></td>
								<td colspan="2">
									<html:text property="nombre" size="65" maxlength="150" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Rif -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.rif" /></td>
								<td colspan="2">
									<html:text property="rif" size="15" maxlength="15" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Fax -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.telefonos" /></td>
								<td colspan="2">
									<html:text property="telefono" size="50" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Campos de la ficha-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.fax" /></td>
								<td colspan="2">
									<html:text property="fax" size="50" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Direccion-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.direccion" /></td>
								<td colspan="2">
									<html:textarea property="direccion" rows="5" cols="65" styleClass="cuadroTexto" onkeyup="corregirLongitud(direccion, 150);" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Codigo Parcial de Enlace-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.codigoparcialdeenlace" /></td>
								<td colspan="2">
									<html:text property="enlaceParcial" size="50" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
								</td>
							</tr>
							<!-- Mes de Cierre -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editarorganizacion.pestana.datosbasicos.mesdecierre" /></td>
								<td colspan="2">
									<html:select name="editarOrganizacionForm" property="mesCierre" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
										<html:options collection="meses" property="clave" labelProperty="valor"></html:options>
									</html:select>
								</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<%-- Panel: Descripción --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="descripcion">
						
						<%-- Título del Panel: Descripción --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarorganizacion.pestana.descripcion" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="5" cellpadding="0" border="0">
							<!-- Campos de la ficha-->
							<tr>
								<td  align="right" colspan="2"><vgcutil:message key="jsp.editarorganizacion.pestana.descripcion" /></td>
								<td  align="left"><html:textarea property="descripcion" rows="7" cols="75" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
							</tr>
							<tr>
								<td  align="right" colspan="2"><vgcutil:message key="jsp.editarorganizacion.pestana.descripcion.observaciones" /></td>
								<td  align="left"><html:textarea property="observaciones" rows="7" cols="75" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
							</tr>
							<tr>
								<td  align="right" colspan="2"><vgcutil:message key="jsp.editarorganizacion.pestana.descripcion.personaldirectivo" /></td>
								<td  align="left"><html:textarea property="personalDirectivo" rows="7" cols="75" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Alertas --%>
					<vgcinterfaz:panelContenedor anchoPestana="90px" nombre="alertas">

						<%-- Título del Panel: Alertas --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarorganizacion.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							<!-- Campos de la ficha-->
							<tr valign="top">
								<td>
									<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" >
										<tr>
											<td colspan="4"><b><vgcutil:message key="jsp.editarorganizacion.pestana.alertas.indicadores" /></b></td>
										</tr>
										<tr>
											<td width="20px">&nbsp;</td>
											<td width="20px" align="center">
												<img src="<html:rewrite page='/componentes/visorArbol/arrow_gris.gif'/>" border="0" >
											</td>																						
											<td colspan="2">
												<b><vgcutil:message key="jsp.editarorganizacion.pestana.alertas.controlbandaminimoymaximos" /></b>
											</td>
										</tr>
										<tr>
											<td width="20px">&nbsp;</td>
											<td width="20px">&nbsp;</td>
											<td width="190">
												<vgcutil:message key="jsp.editarorganizacion.pestana.alertas.porcentajezonaamarilla" />
											</td>
											<td width="380">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td valign="top" align="right">
															<html:text property="porcentajeZonaAmarillaMinMaxIndicadores" size="2" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
														</td>
														<td valign="top" align="left">
															<img id="botonZAMM" name="botonZAMM" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td width="20px">&nbsp;</td>
											<td width="20px" align="center"><img src="<html:rewrite page='/componentes/visorArbol/arrow_gris.gif'/>" border="0" ></td>
											<td colspan="2"><b><vgcutil:message key="jsp.editarorganizacion.pestana.alertas.contoldebandapormetaoprogramado" /></b></td>
										</tr>
										<tr>
											<td width="20px">&nbsp;</td>
											<td width="20px">&nbsp;</td>
											<td width="190"><vgcutil:message key="jsp.editarorganizacion.pestana.alertas.porcentajezonaverde" /></td>
											<td width="380">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td valign="top" align="right">
															<html:text property="porcentajeZonaVerdeMetaIndicadores" size="2" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
														</td>
														<td align="left">
															<img id="botonZVI" name="botonZVI" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown2" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td width="20px">&nbsp;</td>
											<td width="20px">&nbsp;</td>
											<td width="190"><vgcutil:message key="jsp.editarorganizacion.pestana.alertas.porcentajezonaamarilla" /></td>
											<td width="380">
												<table border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td valign="top" align="right">
															<html:text property="porcentajeZonaAmarillaMetaIndicadores" size="2" maxlength="2" styleClass="cuadroTexto" onkeyup="javascript:verificarNumero(this, false);" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/>
														</td>
														<td align="left">
															<img id="botonZAI" name="botonZAI" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown3" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr height="110px">
											<td >&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>							
						</table>

						<%-- Controles de los Cuadros Númericos --%>
						<map name="MapControlUpDown1" id="MapControlUpDown1">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZAMM')" onmouseout="normalAction('botonZAMM')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaMinMaxIndicadores');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZAMM')" onmouseout="normalAction('botonZAMM')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaMinMaxIndicadores');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown2" id="MapControlUpDown2">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZVI')" onmouseout="normalAction('botonZVI')" onmousedown="iniciarConteoContinuo('porcentajeZonaVerdeMetaIndicadores');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZVI')" onmouseout="normalAction('botonZVI')" onmousedown="iniciarConteoContinuo('porcentajeZonaVerdeMetaIndicadores');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown3" id="MapControlUpDown3">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZAI')" onmouseout="normalAction('botonZAI')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaMetaIndicadores');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZAI')" onmouseout="normalAction('botonZAI')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaMetaIndicadores');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown4" id="MapControlUpDown4">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZVIN')" onmouseout="normalAction('botonZVIN')" onmousedown="iniciarConteoContinuo('porcentajeZonaVerdeIniciativas');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZVIN')" onmouseout="normalAction('botonZVIN')" onmousedown="iniciarConteoContinuo('porcentajeZonaVerdeIniciativas');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown5" id="MapControlUpDown5">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZAIN')" onmouseout="normalAction('botonZAIN')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaIniciativas');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZAIN')" onmouseout="normalAction('botonZAIN')" onmousedown="iniciarConteoContinuo('porcentajeZonaAmarillaIniciativas');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
					
					</vgcinterfaz:panelContenedor>

					<%-- Panel: Memos --%>
					<vgcinterfaz:panelContenedor anchoPestana="130px" nombre="marcoEstrategico">
							
						<%-- Título del Panel: Memos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							<tr>
								<td align="left">
								
									<%-- Contenedor de Paneles --%>
									<vgcinterfaz:contenedorPaneles height="210" width="570" nombre="editMarcoEstrategico">
								
										<%-- Panel: Misión --%>
										<vgcinterfaz:panelContenedor anchoPestana="63px" nombre="mision">
										
											<%-- Título del Panel: Misión --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.mision" />
											</vgcinterfaz:panelContenedorTitulo>
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="mision" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
										
										<%-- Panel: Visión --%>
										<vgcinterfaz:panelContenedor anchoPestana="63px" nombre="vision">
											
											<%-- Título del Panel: Visión --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.vision" />
											</vgcinterfaz:panelContenedorTitulo>
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="vision" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
										
										<%-- Panel: Oportunidades y Retos --%>
										<vgcinterfaz:panelContenedor anchoPestana="104px" nombre="oportunidadesRetos">
											
											<%-- Título del Panel: Oportunidades y Retos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.oportunidadesyretos" />
											</vgcinterfaz:panelContenedorTitulo>
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="oportunidadesRetos" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
										
										<%-- Panel: Lineamientos Estratégicos --%>
										<vgcinterfaz:panelContenedor anchoPestana="104px" nombre="lineamientosEstrategicos">
											
											<%-- Título del Panel: Lineamientos Estratégicos --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.lineamientosestrategicos" />
											</vgcinterfaz:panelContenedorTitulo>											
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="lineamientosEstrategicos" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
	
										<%-- Panel: factores Clave --%>
										<vgcinterfaz:panelContenedor anchoPestana="94px" nombre="factoresClave">
											
											<%-- Título del Panel: Factores Clave --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.factoresclaves" />
											</vgcinterfaz:panelContenedorTitulo>											
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="factoresClave" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
	
										<%-- Panel: Políticas --%>
										<vgcinterfaz:panelContenedor anchoPestana="67px" nombre="politicas">
											
											<%-- Título del Panel: Políticas --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.politicas" />
											</vgcinterfaz:panelContenedorTitulo>											
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="politicas" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>
	
										<%-- Panel: Valores --%>
										<vgcinterfaz:panelContenedor anchoPestana="72px" nombre="valores">
											
											<%-- Título del Panel: Valores --%>
											<vgcinterfaz:panelContenedorTitulo>
												<vgcutil:message key="jsp.editarorganizacion.pestana.marcoestrategico.valores" />
											</vgcinterfaz:panelContenedorTitulo>											
											
											<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">												
												<tr>
													<td><html:textarea property="valores" rows="10" cols="65" styleClass="cuadroTexto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/></td>
												</tr>
											</table>
											
										</vgcinterfaz:panelContenedor>

									</vgcinterfaz:contenedorPaneles>
									
								</td>
							</tr>
						</table>
						
					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					
					<logic:notEqual name="editarOrganizacionForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					</logic:notEqual>
					<%-- Para el caso de Nuevo, Modificar y Propiedades --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		
		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarOrganizacionForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		
	</tiles:put>
</tiles:insert>
