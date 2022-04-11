<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (02/11/2012) --%>

<tiles:insert definition="doc.defaultLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String"><vgcutil:message key="jsp.permisonoconfigurado.title" /></tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">			
			
			function regresar() 
			{								
				history.back();
			}
		
		</script>

		<%-- Este es el "Contenedor Secundario o Forma" --%>
		<table class="contenedorForma" cellspacing="2" cellpadding="2" width="500px">
			<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
			<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
			<bean:define toScope="page" id="estiloSuperior_left" value=""></bean:define>
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
			%>
			<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
			<logic:equal scope="page" name="hayEstilo" value="true">
				<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
				<bean:define toScope="page" id="estiloSuperior_left" value="<%=estiloSuperior_left%>"></bean:define>
			</logic:equal>

			<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
			<tr>
				<logic:equal scope="page" name="hayEstilo" value="true">
					<logic:notEmpty scope="page" name="estiloSuperior">
						<td style="<%=estiloSuperior%>">..:: <vgcutil:message
							key="jsp.permisonoconfigurado.title" /></td>
						<td style="<%=estiloSuperior_left%>" align="right" width="30%"><img
							src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.salir"/>"> <a
							onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
							onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar()"
							class="mouseFueraBarraSuperiorForma"><vgcutil:message
							key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</logic:notEmpty>
					<logic:empty scope="page" name="estiloSuperior">
						<td class="barraSuperiorForma">..:: <vgcutil:message
							key="jsp.permisonoconfigurado.title" /></td>
						<td class="barraSuperiorForma" align="right" width="30%"><img
							src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
							border="0" width="10" height="10"
							title="<vgcutil:message key="boton.salir"/>"> <a
							onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
							onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar()"
							class="mouseFueraBarraSuperiorForma"><vgcutil:message
							key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</logic:empty>
				</logic:equal>
				<logic:equal scope="page" name="hayEstilo" value="false">
					<td class="barraSuperiorForma">..:: <vgcutil:message
						key="jsp.permisonoconfigurado.title" /></td>
					<td class="barraSuperiorForma" align="right" width="30%"><img
						src="<html:rewrite page='/componentes/formulario/regresar.gif'/>"
						border="0" width="10" height="10"
						title="<vgcutil:message key="boton.salir"/>"> <a
						onMouseOver="this.className='mouseEncimaBarraSuperiorForma'"
						onMouseOut="this.className='mouseFueraBarraSuperiorForma'" href="javascript:regresar()"
						class="mouseFueraBarraSuperiorForma"><vgcutil:message
						key="boton.regresar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</logic:equal>
			</tr>

			<%-- Cuerpo del "Contenedor Secundario o Forma" --%>
			<tr>
				<td colspan="2">
				
					<!-- Esta es la "Ficha de Datos" -->
					<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" height="200px">
	
						<!-- Este es el cuerpo de la "Ficha de Datos" -->
						<tr>
							<td><vgcutil:message key="jsp.permisonoconfigurado.message" /></td>
						</tr>
						<tr>
							<td>
								<hr width="100%">
							</td>
						</tr>
						<tr>
							<td><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" width="10" height="10"> <vgcutil:message key="jsp.permisonoconfigurado.url" /> <bean:write name="actionUrl" /></td>
						</tr>
						<tr>
							<td>
								<hr width="100%">
							</td>
						</tr>
						<tr>
							<td><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" width="10" height="10"> <vgcutil:message key="jsp.permisonoconfigurado.path" /> <bean:write name="actionPath" /></td>
						</tr>
					</table>

				</td>
			</tr>		

		</table>

	</tiles:put>

</tiles:insert>
