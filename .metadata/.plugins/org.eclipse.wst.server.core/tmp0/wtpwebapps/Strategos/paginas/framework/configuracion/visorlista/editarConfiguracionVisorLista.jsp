<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (12/06/2012) --%>
<%-- Modificado por: Kerwin Arias (06/11/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript">

			function validar(forma) 
			{
				window.document.editarConfiguracionVisorListaForm.action = '<html:rewrite action="/framework/configuracion/guardarConfiguracionVisorLista"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
				return true;
			}

			function guardar() 
			{
				if (validar(document.editarConfiguracionVisorListaForm)) 
					window.document.editarConfiguracionVisorListaForm.submit();
			}

			function cancelar() 
			{			
				window.document.editarConfiguracionVisorListaForm.action = '<html:rewrite action="/framework/configuracion/cancelarGuardarConfiguracionVisorLista"/>';
				window.document.editarConfiguracionVisorListaForm.submit();
			}

			function ejecutarPorDefecto(e) 
			{			
				if(e.keyCode==13) 
					guardar();
			}

			function cambiarOrden(orden, tipo) 
			{
				nombreSeleccionado = getInputHtmlByName('nombre' + orden, window.document).value;
				tituloSeleccionado = getInputHtmlByName('titulo' + orden, window.document).value;
				visibleSeleccionado = getInputHtmlByName('visible' + orden, window.document).checked;
				anchoSeleccionado = getInputHtmlByName('ancho' + orden, window.document).value;
				nroOrden = orden;
				if (nroOrden.indexOf('0') == 0) 
					nroOrden = nroOrden.substring(1, nroOrden.length);
				nroOrden = parseInt(nroOrden);
				if (tipo == 'subir') 
					nroOrden = nroOrden - 1;
				else 
					nroOrden = nroOrden + 1;
				ordenOtro = String(nroOrden);
				if (nroOrden < 10) 
					ordenOtro = '0' + ordenOtro;
				nombreOtro = getInputHtmlByName('nombre' + ordenOtro, window.document).value;
				tituloOtro = getInputHtmlByName('titulo' + ordenOtro, window.document).value;
				visibleOtro = getInputHtmlByName('visible' + ordenOtro, window.document).checked;
				anchoOtro = getInputHtmlByName('ancho' + ordenOtro, window.document).value;
				getInputHtmlByName('nombre' + ordenOtro, window.document).value = nombreSeleccionado;
				getInputHtmlByName('titulo' + ordenOtro, window.document).value = tituloSeleccionado;
				getInputHtmlByName('visible' + ordenOtro, window.document).checked = visibleSeleccionado;
				getInputHtmlByName('ancho' + ordenOtro, window.document).value = anchoSeleccionado;
				getInputHtmlByName('nombre' + orden, window.document).value = nombreOtro;
				getInputHtmlByName('titulo' + orden, window.document).value = tituloOtro;
				getInputHtmlByName('visible' + orden, window.document).checked = visibleOtro;
				getInputHtmlByName('ancho' + orden, window.document).value = anchoOtro;
			}

			function subirOrden(orden) 
			{
				nroOrden = parseInt(orden);
				if (nroOrden == 1) 
					return;
				cambiarOrden(orden, 'subir');
			}

			function bajarOrden(orden) 
			{
				nroOrden = parseInt(orden);
				nroOrden++;
				ordenAbajo = String(nroOrden);
				if (nroOrden < 10) 
					ordenAbajo = '0' + ordenAbajo;
				if (!getInputHtmlByName('nombre' + ordenAbajo, window.document)) 
					return;
				cambiarOrden(orden, 'bajar');
			}

		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarConfiguracionVisorListaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/configuracion/guardarConfiguracionVisorLista">

			<html:hidden property="nombreConfiguracionBase" />
			<html:hidden property="nombreVisorLista" />

			<vgcinterfaz:contenedorForma width="510px" bodyAlign="center" height="510px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::	<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.titulo" /> (<bean:write name="editarConfiguracionVisorListaForm" property="tituloVisorLista" />)
				</vgcinterfaz:contenedorFormaTitulo>

				<div style="overflow: auto; height: <%= (String) request.getAttribute("altoVisor") %>; width: 485px">
					<vgcinterfaz:visorLista namePaginaLista="paginaColumnas" messageKeyNoElementos="jsp.framework.editarconfiguracionvisorlista.nocolumnas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase" nombre="visorColumnasVisorLista" width="450">
						<vgcinterfaz:columnaAccionesVisorLista width="50px">
							<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.orden" />
						</vgcinterfaz:columnaAccionesVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="nombre" width="100px">
							<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.nombre" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="visible">
							<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.visible" />
						</vgcinterfaz:columnaVisorLista>
						<vgcinterfaz:columnaVisorLista nombre="ancho">
							<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.ancho" />
						</vgcinterfaz:columnaVisorLista>
						
						<vgcinterfaz:filasVisorLista nombreObjeto="columna">
							<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/subir.gif">
								<vgcinterfaz:accionTituloVisorLista>
									<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.accion.subir" />
								</vgcinterfaz:accionTituloVisorLista>subirOrden('<bean:write name="columna" property="orden" />');
							</vgcinterfaz:accionVisorLista>
							<vgcinterfaz:accionVisorLista urlImage="/componentes/visorLista/bajar.gif">
								<vgcinterfaz:accionTituloVisorLista>
									<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.accion.bajar" />
								</vgcinterfaz:accionTituloVisorLista>bajarOrden('<bean:write name="columna" property="orden" />');
							</vgcinterfaz:accionVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
								<input type="hidden" name="nombre<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="nombre" />" />
								<input type="text" size="38" class="cuadroTexto" onfocus="this.blur();" name="titulo<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="titulo" />" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="visible" align="center">
								<logic:equal name="columna" property="visible" value="true">
									<input type="checkbox" name="visible<bean:write name="columna" property="orden" />" checked />
								</logic:equal>
								<logic:equal name="columna" property="visible" value="false">
									<input type="checkbox" name="visible<bean:write name="columna" property="orden" />" />
								</logic:equal>
	
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="ancho">
								<input type="text" size="7" class="cuadroTexto" name="ancho<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="ancho" />" onkeyup="javascript:verificarNumero(this, false);" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
						</vgcinterfaz:filasVisorLista>
					</vgcinterfaz:visorLista>
				</div>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>
	</tiles:put>
</tiles:insert>
