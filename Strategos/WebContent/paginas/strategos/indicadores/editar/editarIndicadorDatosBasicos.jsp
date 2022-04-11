<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<bean:define id="bloquearForma" toScope="page">
	<logic:notEmpty name="editarIndicadorForm" property="bloqueado">
		<bean:write name="editarIndicadorForm" property="bloqueado" />
	</logic:notEmpty>
	<logic:empty name="editarIndicadorForm" property="bloqueado">
		false
	</logic:empty>
</bean:define>
<bean:define scope="page" id="iniciativaDisabled" value="false"></bean:define>
<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
	<bean:define scope="page" id="iniciativaDisabled" value="true"></bean:define>
</logic:equal>

<%-- Datos Básicos --%>
<%-- Cuerpo de la Pestaña --%>
<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
	<%-- Organizacion --%>
	<tr>
		<td align="right"><b><vgcutil:message key="jsp.editarindicador.ficha.organizacion" /></b></td>
		<td colspan="3"><bean:write name="editarIndicadorForm" property="organizacion.nombre" /></td>
	</tr>

	<%-- Clase --%>
	<tr>
		<td align="right"><b><vgcutil:message key="jsp.editarindicador.ficha.clase" /></b></td>
		<td colspan="3"><bean:write name="editarIndicadorForm" property="claseIndicadores.nombre" /></td>
	</tr>

	<!-- Campo Nombre Corto-->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.nombre" /></td>
		<td colspan="3"><html:text property="nombre" size="82" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="150" styleClass="cuadroTexto" onkeyup="sincronizarCamposTexto(document.editarIndicadorForm.nombre, document.editarIndicadorForm.nombreLargo, 50, false)" onkeypress="ejecutarPorDefecto(event)" /></td>
		<!--
		<td rowspan="2" align="right" background="<html:rewrite page='/paginas/strategos/indicadores/imagenes/combinarNombres.gif'/>">
			<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
				<input type="checkbox" name="sincronizarNombres" title="SincronizarNombres">
			</logic:notEqual>
			<logic:equal name="editarIndicadorForm" property="bloqueado" value="true">
				<input type="checkbox" name="sincronizarNombres" title="SincronizarNombres" disabled=" Boolean.parseBoolean(bloquearForma) ">
			</logic:equal>
		</td>
		-->
	</tr>

	<!-- Campo Nombre Largo-->
	<!--
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.nombrelargo" /></td>
		<td colspan="2">
			<html:text property="nombreLargo" size="82" disabled=" Boolean.parseBoolean(bloquearForma) " maxlength="100" styleClass="cuadroTexto" onkeyup="sincronizarCamposTexto(document.editarIndicadorForm.nombreLargo, document.editarIndicadorForm.nombre, 100, document.editarIndicadorForm.sincronizarNombres.checked)" onkeypress="ejecutarPorDefecto(event)" />
		</td>
	</tr>
	-->

	<%-- Campo descripción --%>
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.descripcion" /></td>
		<td colspan="3"><html:textarea property="descripcion" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" cols="88" rows="6" styleClass="cuadroTexto" /></td>
	</tr>

	<!-- Campo Comportamiento-->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.comportamiento" /></td>
		<td colspan="3"><html:text property="comportamiento" size="89" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
	</tr>

	<!-- Campo Fuente-->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.fuente" /></td>
		<td colspan="3"><html:text property="fuente" size="89" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
	</tr>

	<!-- Orden-->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.orden" /></td>
		<td colspan="3"><html:text property="orden" size="10" maxlength="10" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" onkeypress="ejecutarPorDefecto(event)" /></td>
	</tr>

	<bean:define scope="page" id="naturalezaDisabled" value="false"></bean:define>
	<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
		<bean:define scope="page" id="naturalezaDisabled" value="true"></bean:define>
	</logic:equal>
	<!-- Naturaleza -->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.naturaleza" /></td>
		<td><html:select property="naturaleza" disabled="<%= ((new Boolean(naturalezaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" styleClass="cuadroTexto" size="1" onchange="eventoCambioNaturaleza()">
			<html:option value="">
			</html:option>
			<html:optionsCollection property="naturalezas" value="naturalezaId" label="nombre" />
		</html:select></td>
	</tr>

	<bean:define scope="page" id="frecuenciaDisabled" value="false"></bean:define>
	<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
		<bean:define scope="page" id="frecuenciaDisabled" value="true"></bean:define>
	</logic:equal>
	<!-- Campo Frecuencia -->
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.frecuencia" /></td>
		<td><html:select property="frecuencia" disabled="<%= ((new Boolean(frecuenciaDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma))%>" styleClass="cuadroTexto" size="1" onchange="eventoOnchangeFrecuencia();">
			<html:option value=""></html:option>
			<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
		</html:select></td>

	</tr>

	<!-- Unidad Medida -->
	<bean:define scope="page" id="unidadDisabled" value="false"></bean:define>
	<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
		<bean:define scope="page" id="unidadDisabled" value="true"></bean:define>
	</logic:equal>
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.unidadmedida" /></td>
		<td>
			<html:select property="unidadId" disabled="<%= ((new Boolean(unidadDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>" styleClass="cuadroTexto" size="1" onclick="onclickUnidadId(this)" >
				<html:option value=""></html:option>
				<html:optionsCollection property="unidadesMedida" value="unidadId" label="nombre" />
			</html:select>
		</td>
	</tr>

	<!-- Campo Numero de Decimales -->
	<bean:define scope="page" id="numeroDecimalesDisabled" value="false"></bean:define>
	<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
		<bean:define scope="page" id="numeroDecimalesDisabled" value="true"></bean:define>
	</logic:equal>
	<tr>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.nrodecimales" /></td>
		<td align="left">
			<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
				<tr>
					<td valign="middle" align="left">
						<html:text property="numeroDecimales" size="1" readonly="true" styleClass="cuadroTexto" disabled="<%= ((new Boolean(numeroDecimalesDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"/>
					</td>
					<logic:notEqual name="editarIndicadorForm" property="bloqueado" value="true">
						<logic:notEqual name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
							<td valign="middle"><img id="botonNumeroDecimales" name="botonNumeroDecimales" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" />
							</td>
						</logic:notEqual>
					</logic:notEqual>
				</tr>
			</table>
		</td>

		<!-- Mostrar en Arbol -->
		<bean:define scope="page" id="mostrarEnArbolDisabled" value="false"></bean:define>
		<logic:equal name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
			<bean:define scope="page" id="mostrarEnArbolDisabled" value="true"></bean:define>
		</logic:equal>
		<td align="right"><vgcutil:message key="jsp.editarindicador.ficha.mostrararbol" /></td>
		<td><html:checkbox property="mostrarEnArbol" styleClass="botonSeleccionMultiple" disabled="<%= ((new Boolean(mostrarEnArbolDisabled)).booleanValue() || Boolean.parseBoolean(bloquearForma)) %>"></html:checkbox></td>
	</tr>
</table>

<logic:notEqual name="editarIndicadorForm" property="bloquearIndicadorIniciativa" scope="session" value="true">
	<map name="MapControlUpDown1" id="MapControlUpDown1">
		<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonNumeroDecimales')" onmouseout="normalAction('botonNumeroDecimales')" onmousedown="onmousedownMascaraDecimalesUp()" onmouseup="finalizarConteoContinuo()" />
		<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonNumeroDecimales')" onmouseout="normalAction('botonNumeroDecimales')" onmousedown="onmousedownMascaraDecimalesDown()" onmouseup="finalizarConteoContinuo()" />
	</map>
</logic:notEqual>
