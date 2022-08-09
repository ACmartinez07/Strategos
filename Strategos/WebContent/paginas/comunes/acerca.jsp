<%@page import="com.visiongc.framework.util.ObjetosSistema"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (13/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.acerca.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<vgcinterfaz:contenedorForma width="450px" height="670px" bodyCellpadding="10" marginTop="5px" >
			
			<%-- Título --%>
			<vgcinterfaz:contenedorFormaTitulo>
				..:: <vgcutil:message key="jsp.acerca.titulo" />
			</vgcinterfaz:contenedorFormaTitulo>
			<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
			<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
			<bean:define toScope="page" id="estiloSuperior_left" value=""></bean:define>
			<bean:define toScope="page" id="es_Demostracion" value="false"></bean:define>
			<%
				com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
				if (estilos != null)
				{
					hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
					estiloSuperior = estilos.getEstiloSuperiorForma();
					estiloSuperior_left = estilos.getEstiloSuperiorFormaIzquierda();
				}
				else
					hayEstilo = "false";
				es_Demostracion = new com.visiongc.framework.web.struts.actions.WelcomeAction().esLicenciaDemostracion().toString().toLowerCase();
			%>
			<bean:define toScope="page" id="es_Demostracion" value="<%=es_Demostracion%>"></bean:define>
			<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
			<logic:equal scope="page" name="hayEstilo" value="true">
				<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
				<bean:define toScope="page" id="estiloSuperior_left" value="<%=estiloSuperior_left%>"></bean:define>
			</logic:equal>

			<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
			
			<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="100%">
				<tr>
					<td colspan="2">
					<table class="bordeFichaDatosIngreso" cellpadding="3" cellspacing="0">
						<logic:equal scope="page" name="hayEstilo" value="true">
							<logic:notEmpty scope="page" name="estiloSuperior">
								<logic:equal scope="page" name="es_Demostracion" value="true">
									<tr>
										<td colspan="2" style="<%=estiloSuperior%>"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
									</tr>
								</logic:equal>
								<tr>
									<td colspan="2" style="<%=estiloSuperior%>"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
								</tr>
								<tr>
									<td colspan="2" style="<%=estiloSuperior%>"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
								</tr>
							</logic:notEmpty>
							<logic:empty scope="page" name="estiloSuperior">
								<logic:equal scope="page" name="es_Demostracion" value="true">
									<tr>
										<td colspan="2" class="barraSuperiorForma"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
									</tr>
								</logic:equal>
								<tr>
									<td colspan="2" class="barraSuperiorForma"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
								</tr>
								<tr>
									<td colspan="2" class="barraSuperiorForma"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
								</tr>
							</logic:empty>
						</logic:equal>
						<logic:equal scope="page" name="hayEstilo" value="false">
							<logic:equal scope="page" name="es_Demostracion" value="true">
								<tr>
									<td colspan="2" class="barraSuperiorForma"><b><vgcutil:message key="aplicacion.licenciamiento.tipo.demostracion" /></b></td>
								</tr>
							</logic:equal>
							<tr>
								<td colspan="2" class="barraSuperiorForma"><vgcutil:message key="aplicacion.copyright" /><vgcutil:message key="aplicacion.ano" /> <vgcutil:message key="aplicacion.autor" /></td>
							</tr>
							<tr>
								<td colspan="2" class="barraSuperiorForma"><div align="justify"><vgcutil:message key="aplicacion.nombre" /> <vgcutil:message key="aplicacion.licenciamiento.texto" /></div></td>
							</tr>
						</logic:equal>
					</table>
				</tr>

				<%-- Este es el cuerpo de la "Ficha de Datos" --%>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.usuario" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="30" disabled value="<bean:write scope='session' name='usuario' property='fullName' />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.host" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="20" disabled value="<%=request.getRemoteHost()%>" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.ip" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="20" disabled value="<%=request.getRemoteAddr()%>" /></td>
				</tr>
				<tr>
					<td colspan="3">
					<hr width="100%">
					</td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.frameworkversion" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write name="frameworkVersion" />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.fecha" />:</td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write name="frameworkVersionFecha" />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.frameworkwebversion" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<vgcutil:message key="frameworkweb.version" />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.fecha" />:</td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<vgcutil:message key="frameworkweb.version.fecha" />" /></td>
				</tr>
				<tr>
					<td colspan="3">
					<hr width="100%">
					</td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.aplicacion" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="40" disabled value="<vgcutil:message key="aplicacion.nombre" />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.version" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<vgcutil:message key="aplicacion.version" /> <vgcutil:message key="aplicacion.version.text.build" /> <vgcutil:message key="aplicacion.version.build" />" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="aplicacion.version.text.fecha" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<vgcutil:message key="aplicacion.version.fecha" />" /></td>
				</tr>
				<logic:notEmpty scope="session" name="licencia">
					<tr>
						<td colspan="3">
						<hr width="100%">
						</td>
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.acerca.licencia.company" /></td>
						
						<!-- 
						<td colspan="2"><input type="text" class="cuadroTexto" size="40" disabled value="Defensoria del Pueblo" /></td>
						--> 
						
						<td colspan="2"><input type="text" class="cuadroTexto" size="40" disabled value="<bean:write scope='session' name='licencia' property='companyName' />" /></td>
						
						
					</tr>
					<tr>
						<td align="right"><vgcutil:message key="jsp.acerca.licencia.serial" /></td>
						
						<!--  
						<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="0" /></td>
						-->
						
						<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='serial' />" /></td>
						
						
					</tr>
					<logic:notEqual scope="session" name="licencia" property="expiracion" value="">
						<tr>
							<td align="right"><vgcutil:message key="jsp.acerca.licencia.expiracion" /></td>
							<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='expiracion' />" /></td>
						</tr>
					</logic:notEqual>
					<tr>
						<td align="right"><vgcutil:message key="jsp.acerca.licencia.tipo" /></td>
						
						<!-- 
						<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="Full Licenciamento" /></td>
					 	-->
					 	
						<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<bean:write scope='session' name='licencia' property='tipo' />" /></td>
						
						
					</tr>
				</logic:notEmpty>
				<tr>
					<td colspan="3">
					<hr width="100%">
					</td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.memoria.used" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<%=new ObjetosSistema().GetHeapSizeUsed().toString()%> Mg" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.memoria.free" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<%=new ObjetosSistema().GetHeapSizeFree().toString()%> Mg" /></td>
				</tr>
				<tr>
					<td align="right"><vgcutil:message key="jsp.acerca.memoria.max" /></td>
					<td colspan="2"><input type="text" class="cuadroTexto" size="15" disabled value="<%=new ObjetosSistema().GetHeapSizeMax().toString()%> Mg" /></td>
				</tr>
				<tr>
					<td colspan="3">
					<hr width="100%">
					</td>
				</tr>
			</table>

			<%-- Barra Inferior --%>
			<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="descripcionOrden">
				<table class="formatoBarraInferior" cellpadding="0" cellspacing="0" align="left" height="100%">
					<tr>
						<td><vgcutil:message key="jsp.acerca.derechos" /></br><vgcutil:message key="aplicacion.autor" /> - <vgcutil:message key="aplicacion.ano" /></td>
					</tr>
				</table>
			</vgcinterfaz:contenedorFormaBarraInferior>
			
		</vgcinterfaz:contenedorForma>

	</tiles:put>

</tiles:insert>
