<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (12/06/2012) --%>
<%-- Modificado por: Kerwin Arias (06/11/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		<script type="text/javascript">

			function validar() 
			{
				for (var index = 0; index < window.document.editarConfiguracionVisorListaForm.elements.length; index++) 
				{
					if (window.document.editarConfiguracionVisorListaForm.elements[index].name.indexOf('ancho') > -1) 
					{
						if ((window.document.editarConfiguracionVisorListaForm.elements[index].value == null) || (window.document.editarConfiguracionVisorListaForm.elements[index].value == '')) 
						{
							alert('<vgcutil:message key="jsp.configurarvistacolumna.mensaje.ancho.vacio" />');
							return false;
						}
					}
				}
				
				return true;
			}

			function guardar() 
			{
				if (validar())
				{
					document.editarConfiguracionVisorListaForm.respuesta.value = "";

					var parametros = 'data=' + getXml(true);
					parametros = parametros + '&nombreConfiguracionBase=' + document.editarConfiguracionVisorListaForm.nombreConfiguracionBase.value;
					parametros = parametros + '&nombreVisorLista=' + document.editarConfiguracionVisorListaForm.nombreVisorLista.value; 
					parametros = parametros + '&esPropio=' + document.editarConfiguracionVisorListaForm.esPropio.value; 
					
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/framework/configuracion/guardarConfiguracionVisor" />?' + parametros, document.editarConfiguracionVisorListaForm.respuesta, 'onSalvar()');
				}
			}
			
			function onSalvar()
			{
				cancelar();
				window.opener.onConfigurarVisorEdicion();
			}
			
			function getXml(codificar)
			{
				var writer = new XmlTextWriter();
				writer.Formatting = true;
	            writer.WriteStartDocument();
	            writer.WriteStartElement("Forma");
	            writer.WriteStartElement("properties");

	            var seguir = true;
	            var index = 1;
	            var titulo = null;
	            var orden = null;
	            var visible = null;
	            var ancho = null;
	            
	    		while (seguir) 
	    		{
    				orden = index;
	    			
	    			titulo = document.getElementById("nombre" + orden);
	    			if (titulo == null) 
	    				seguir = false;
	    			else 
	    			{
						writer.WriteStartElement("columnas");

						if (document.getElementById("visible" + orden) == null)
							visible = "true";
						else
							visible = document.getElementById("visible" + orden).checked ? "true" : "false";
						if (document.getElementById("ancho" + orden) == null)
							ancho = "150";
						else
							ancho = document.getElementById("ancho" + orden).value;
							
						writer.WriteElementString("titulo", titulo.value);
						writer.WriteElementString("orden", orden);
						writer.WriteElementString("visible", visible);
						writer.WriteElementString("ancho", ancho.replace(/^\s+/,"").replace(/\s+$/,""));
						
						writer.WriteEndElement();

	    				index = index + 1;
	    			}
	    		}
	            
	            writer.WriteEndElement();
	            writer.WriteEndElement();
	            writer.WriteEndDocument();
				
	            return writer.unFormatted();
			}

			function CodificarString(value, codificar)
			{
				var valor = value;

				if (codificar)
					valor = valor.replace("%", "[[por]]");
				
				return valor;
			}

			function cancelar() 
			{			
				this.close();
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

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarConfiguracionVisorListaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/configuracion/guardarConfiguracionVisorLista">

			<html:hidden property="nombreConfiguracionBase" />
			<html:hidden property="nombreVisorLista" />
			<html:hidden property="esPropio" />
			<html:hidden property="respuesta" />
			
			<bean:define id="altoForma" toScope="page">
				<logic:notEmpty name="editarConfiguracionVisorListaForm" property="alto">
					<bean:write name="editarConfiguracionVisorListaForm" property="alto" />px
				</logic:notEmpty>
				<logic:empty name="editarConfiguracionVisorListaForm" property="alto">
					500px
				</logic:empty>
			</bean:define>
			<bean:define id="anchoForma" toScope="page">
				<logic:notEmpty name="editarConfiguracionVisorListaForm" property="ancho">
					<bean:write name="editarConfiguracionVisorListaForm" property="ancho" />px
				</logic:notEmpty>
				<logic:empty name="editarConfiguracionVisorListaForm" property="ancho">
					480px
				</logic:empty>
			</bean:define>

			<vgcinterfaz:contenedorForma width="<%=anchoForma%>" bodyAlign="center" bodyValign="center" height="<%=altoForma%>">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::	<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.titulo" /> (<bean:write name="editarConfiguracionVisorListaForm" property="tituloVisorLista" />)
				</vgcinterfaz:contenedorFormaTitulo>

				<div style="overflow: auto; height: <%= (String) request.getAttribute("altoVisor") %>; width: 100%">
					<vgcinterfaz:visorLista namePaginaLista="paginaColumnas" messageKeyNoElementos="jsp.framework.editarconfiguracionvisorlista.nocolumnas" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase" nombre="visorColumnasVisorLista" width="450">
						<%-- 
						<vgcinterfaz:columnaAccionesVisorLista width="50px">
							<vgcutil:message key="jsp.framework.editarconfiguracionvisorlista.columna.orden" />
						</vgcinterfaz:columnaAccionesVisorLista>
						--%>
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
							<%-- 
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
							--%>
							<%-- Nombre --%>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
								<input type="hidden" id="nombre<bean:write name="columna" property="orden" />" name="nombre<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="nombre" />" />
								<input type="text" size="38" class="cuadroTexto" onfocus="this.blur();" id="titulo<bean:write name="columna" property="orden" />" name="titulo<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="titulo" />" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<%-- Visible --%>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="visible" align="center">
								<logic:equal name="columna" property="visible" value="true">
									<input type="checkbox" id="visible<bean:write name="columna" property="orden" />" name="visible<bean:write name="columna" property="orden" />" checked />
								</logic:equal>
								<logic:equal name="columna" property="visible" value="false">
									<input type="checkbox" id="visible<bean:write name="columna" property="orden" />" name="visible<bean:write name="columna" property="orden" />" />
								</logic:equal>
							</vgcinterfaz:valorFilaColumnaVisorLista>
							<%-- Ancho --%>
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="ancho">
								<input type="text" size="7" class="cuadroTexto" id="ancho<bean:write name="columna" property="orden" />" name="ancho<bean:write name="columna" property="orden" />" value="<bean:write name="columna" property="ancho" />" onkeyup="javascript:verificarNumero(this, false);" />
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
