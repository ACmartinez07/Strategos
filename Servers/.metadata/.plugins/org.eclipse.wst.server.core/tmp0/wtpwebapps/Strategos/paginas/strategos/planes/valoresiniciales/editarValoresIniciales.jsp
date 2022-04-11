<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (10/03/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<bean:message key="jsp.editarvaloresiniciales.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="Javascript1.1" src="<html:rewrite page='/paginas/comunes/validateInput.js'/>"></script>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			var caracteresNumericos= '0123456789.';			
			
			function puntoDecimalUnico(evento, texto) 
			{
				var pos;
				var punto = '.';			
				pos = texto.indexOf(punto);
				if (pos > -1) 
				{
					if (texto.substring(pos + 1).indexOf(punto) > -1) 
						return false;
				}
				return true;
			}
			
			function digitosDecimalesPermitidos(evento, texto, numeroDecimales) 
			{
				var pos;
				var punto = '.';
				var key, keychar;			
				key = getkey(evento);			
				if (key == null) return true;
				keychar = String.fromCharCode(key);
				keychar = keychar.toLowerCase();			
				if (key == 190 || key == 46 || key == 96 || key == 97 || key == 98 || key == 99 || key == 100 || key == 101  || key == 102  || key == 103  || key == 104 || key == 105 || key == 110) 
					keychar = '.';
				pos = texto.indexOf(punto);			
				if ((pos >= 0) && (pos < (texto.length - numeroDecimales - 1))) 
				{
					if (caracteresNumericos.indexOf(keychar) >= 0)
						return false;
				}			
				return true;
			}
			
			function revisionCiclicaCaracterNumericoPermitido(objetoTexto, numeroDecimales) 
			{			
				var punto = '.';
				var texto = objetoTexto.value;			
				for (i = 0 ; i < objetoTexto.value.length ; i++) 
				{
					keychar = texto.substring(i, i+1);
					if (caracteresNumericos.indexOf(keychar) == -1) 
					{
						objetoTexto.value = texto.substring(0, i);
						break;
					}
				}			
				
				for (i = 0 ; i < objetoTexto.value.length ; i++) 
				{
					keychar = texto.substring(i, i+1);
					pos = texto.indexOf(punto);
					if ((pos >= 0) && (pos < (texto.length - numeroDecimales - 1))) 
					{
						objetoTexto.value = texto.substring(0, pos);
						break;
					}
				}			
				for (i = 0 ; i < objetoTexto.value.length ; i++) 
				{
					keychar = texto.substring(i, i+1);
					pos = -1;
					if (keychar = punto) 
						pos = texto.indexOf(punto);
					if (pos != -1) 
					{
						if (texto.substring(pos+1).indexOf(punto) != -1) 
						{
							objetoTexto.value = texto.substring(0, pos);
							break;
						}
					}
				}
			}
			
			function chequearCaracteresNumericosPermitidos(evento, objTexto, numeroDecimales) 
			{
				var texto = objTexto.value;
				var valido = (goodchars(evento, caracteresNumericos)) && (puntoDecimalUnico(evento, texto)) && (digitosDecimalesPermitidos(evento, texto, numeroDecimales));			
				if (!valido) 
				{
					objTexto.value = texto.substring(0, objTexto.value.length-1);
					revisionCiclicaCaracterNumericoPermitido(objTexto, numeroDecimales);	
				}
			}
			
			function confirmar() 
			{
				var confirmado = confirm('<bean:message key="jsp.editarvaloresiniciales.mensaje.confirmarguardar" />');
				if (confirmado) 
					return true;
				else 
					return false;
			}
			
			function validar() 
			{
				for (i = 0; i < document.editarValoresInicialesForm.length; i++) 
				{
					if (document.editarValoresInicialesForm.elements[i].name.indexOf('ano') == 0) 
					{
						var nombre = (document.editarValoresInicialesForm.elements[i].name).substring(3);
						var ano = document.getElementById("ano" + nombre);
						var periodo = document.getElementById("periodo" + nombre);
						var nombreIndicador = document.getElementById("nombre" + nombre);
						var numeroPeriodos = document.getElementById("periodos" + nombre);
						if ((ano == null) || (ano.value == "")) 
						{
							if ((periodo != null) && (periodo.value != "")) 
							{
								alert ('<bean:message key="jsp.editarvaloresiniciales.validacion.ano" arg0="' + ': ' + nombreIndicador.value + '" />');
								ano.focus();
								return false;
							}
						}
						if ((periodo == null) || (periodo.value == "")) 
						{
							if ((ano != null) && (ano.value != "")) 
							{
								alert ('<bean:message key="jsp.editarvaloresiniciales.validacion.periodo" arg0="' + ': ' +  nombreIndicador.value + '" />');
								periodo.focus();
								return false;
							}
						}
						
						if ((periodo.value != "") && (ano.value != "")) 
						{
							if ((parseInt(periodo.value) < 1) || (parseInt(periodo.value) > parseInt(numeroPeriodos.value))) 
							{
								alert ('<bean:message key="jsp.editarvaloresiniciales.validacion.numeroperiodos.fuerarango" arg0="' + numeroPeriodos.value + '" />');
								periodo.focus();
								return false;
							}
						}
					}
				}
				return true;
			}
			
			function guardar() 
			{
				if (validar()) 
				{
					if (confirmar()) 
					{
						window.document.editarValoresInicialesForm.action = '<html:rewrite action="/planes/valoresiniciales/guardarValoresIniciales"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
						window.document.editarValoresInicialesForm.submit();
					}
				}					
			}
			
			function cancelar() 
			{
				window.document.editarValoresInicialesForm.action = '<html:rewrite action="/planes/valoresiniciales/cancelarGuardarValoresIniciales"/>';
				window.document.editarValoresInicialesForm.submit();
			}
			
			function formatearValores() 
			{
				for (i = 0; i < document.editarValoresInicialesForm.length; i++) 
				{
					if (document.editarValoresInicialesForm.elements[i].name.indexOf('valor') == 0) 
						document.editarValoresInicialesForm.elements[i].value = document.editarValoresInicialesForm.elements[i].value.replace(',', '.');
				}
			}
			
			function mostrarUnidadMedida(valor) 
			{				
				var queryString = "?mostrarUnidadMedida=" + valor + "&mostrarCodigoEnlace=" + document.editarValoresInicialesForm.mostrarCodigoEnlace.value;
				window.location.href='<html:rewrite action="/planes/valoresiniciales/editarValoresIniciales" />' + queryString;
			}
			
			function mostrarCodigoEnlace(valor) 
			{			
				var queryString = "?mostrarCodigoEnlace=" + valor + "&mostrarUnidadMedida=" + document.editarValoresInicialesForm.mostrarUnidadMedida.value;
				window.location.href='<html:rewrite action="/planes/valoresiniciales/editarValoresIniciales" />' + queryString;
			}			

		</script>

		<html:form onsubmit="return confirmar();" action="planes/valoresiniciales/guardarValoresIniciales" styleClass="formaHtml">
		
			<html:hidden property="mostrarUnidadMedida" />
			<html:hidden property="mostrarCodigoEnlace" />

			<vgcinterfaz:contenedorForma width="100%" height="100%" mostrarBarraSuperior="true" bodyAlign="left">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.editarvaloresiniciales.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar()
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>

					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">								
								
								<%-- Validación: Unidad de Medida --%>
								<logic:equal name="editarValoresInicialesForm" property="mostrarUnidadMedida" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarvaloresiniciales.menu.ver.unidadmedida" icon="/componentes/menu/activo.gif" onclick="mostrarUnidadMedida(false);" permisoId="VALOR INICIAL" />
								</logic:equal>
								<logic:notEqual name="editarValoresInicialesForm" property="mostrarUnidadMedida" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarvaloresiniciales.menu.ver.unidadmedida" onclick="mostrarUnidadMedida(true);" permisoId="VALOR INICIAL" />
								</logic:notEqual>
								
								<%-- Validación: Código de Enlace --%>
								<logic:equal name="editarValoresInicialesForm" property="mostrarCodigoEnlace" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarvaloresiniciales.menu.ver.codigoenlace" icon="/componentes/menu/activo.gif" onclick="mostrarCodigoEnlace(false);" permisoId="VALOR INICIAL" />
								</logic:equal>
								<logic:notEqual name="editarValoresInicialesForm" property="mostrarCodigoEnlace" value="true">
									<vgcinterfaz:botonMenu key="jsp.editarvaloresiniciales.menu.ver.codigoenlace" onclick="mostrarCodigoEnlace(true);" permisoId="VALOR INICIAL" />
								</logic:notEqual>
								
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

					</vgcinterfaz:contenedorMenuHorizontal>

				</vgcinterfaz:contenedorFormaBarraMenus>

				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Organización y Plan --%>
					<table width="100%" cellpadding="3" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.editarvaloresiniciales.organizacion" /></b></td>
							<td><bean:write name="editarValoresInicialesForm" property="nombreOrganizacion" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="20px"><b><bean:message key="jsp.editarvaloresiniciales.plan" /></b></td>
							<td><bean:write name="editarValoresInicialesForm" property="nombrePlan" /></td>
						</tr>
					</table>

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraMetas">
						<logic:notEqual name="editarValoresInicialesForm" property="bloquear" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasSeparador />
						</logic:notEqual>
					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<table style="listView" cellpadding="3" cellspacing="1" width="100%" >

					<!-- Encabezado -->
					<tr class="encabezadoListView" height="20px">
						<td width="400px" align="center"><bean:message key="jsp.editarvaloresiniciales.indicador" /></td>

						<!-- Validación: Unidad de Medida -->
						<logic:equal name="editarValoresInicialesForm" property="mostrarUnidadMedida" value="true">
							<td width="150px" align="center"><bean:message key="jsp.editarvaloresiniciales.unidad" /></td>
						</logic:equal>

						<!-- Validación: Código de Enlace -->
						<logic:equal name="editarValoresInicialesForm" property="mostrarCodigoEnlace" value="true">
							<td width="200px" align="center"><bean:message key="jsp.editarvaloresiniciales.codigoenlace" /></td>
						</logic:equal>

						<!-- Año -->
						<td width="150px" align="center"><bean:message key="jsp.editarvaloresiniciales.ano" /></td>

						<!-- Periodo -->
						<td width="150px" align="center"><bean:message key="jsp.editarvaloresiniciales.periodo" /></td>

						<!-- Periodo -->
						<td width="150px" align="center"><bean:message key="jsp.editarvaloresiniciales.medicion" /></td>

					</tr>

					<%-- Se itera por la lista de Indicadores y sus respectivas Metas Anuales --%>
					<logic:iterate name="editarValoresInicialesForm" property="valoresInicialesIndicadores" id="valorInicialIndicador">

						<tr class="mouseFueraCuerpoListView" height="20px">
							<td class="celdaMetas"><bean:write name="valorInicialIndicador" property="indicador.nombre" /></td>

							<!-- Validación: Unidad de Medida -->
							<logic:equal name="editarValoresInicialesForm" property="mostrarUnidadMedida" value="true">
								<td class="celdaMetas">
									<logic:notEmpty name="valorInicialIndicador" property="indicador.unidad">
										<bean:write name="valorInicialIndicador" property="indicador.unidad.nombre" />
									</logic:notEmpty>
								</td>
							</logic:equal>
							
							<!-- Validación: Código de Enlace -->
							<logic:equal name="editarValoresInicialesForm" property="mostrarCodigoEnlace" value="true">
								<td class="celdaMetas"><bean:write name="valorInicialIndicador" property="indicador.codigoEnlace" /></td>
							</logic:equal>

							<!-- Año -->
							<bean:define id="claseEstiloCelda" scope="page" value="cuadroTexto"></bean:define>
							<bean:define id="medicionSoloLectura" scope="page" value=""></bean:define>
							<logic:equal name="valorInicialIndicador" property="proteger" value="true">
								<bean:define id="claseEstiloCelda" scope="page" value="medicionProtegida"></bean:define>
								<bean:define id="medicionSoloLectura" scope="page" value="this.blur();"></bean:define>
							</logic:equal>
							
							<td class="celdaMetas" align="center">
								<input 
									type="text" 
									onfocus="<bean:write name="medicionSoloLectura" scope="page"/>"
									onKeyUp="chequearCaracteresNumericosPermitidos(event, this, 0);" 
									align="right" 
									style="text-align: right" 
									id="anoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									name="anoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									value="<bean:write name="valorInicialIndicador" property="valorInicial.metaId.ano" />"
									class="<bean:write name="claseEstiloCelda" scope="page"/>"
								>
								<input type="hidden" style="text-align: right" id="nombreIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" name="anoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" value="<bean:write name="valorInicialIndicador" property="indicador.nombre" />">																
								<input type="hidden" style="text-align: right" id="periodosIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" name="anoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" value="<bean:write name="valorInicialIndicador" property="numeroPeriodos" />">
							</td>
							
							<!-- Periodo -->
							<td class="celdaMetas" align="center">
								<input 
									type="text" 
									onfocus="<bean:write name="medicionSoloLectura" scope="page"/>"
									onKeyUp="chequearCaracteresNumericosPermitidos(event, this, 0);" 
									align="right" 
									style="text-align: right" 
									id="periodoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									name="periodoIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									value="<bean:write name="valorInicialIndicador" property="valorInicial.metaId.periodo" />"
									class="<bean:write name="claseEstiloCelda" scope="page"/>"
									>								
							</td>

							<!-- Medición -->
							<td class="celdaMetas" align="center">
								<input 
									type="text" 
									onfocus="<bean:write name="medicionSoloLectura" scope="page"/>"
									onKeyUp="chequearCaracteresNumericosPermitidos(event, this, <bean:write name="valorInicialIndicador" property="indicador.numeroDecimales"/>);" 
									align="right" 
									class="<bean:write name="claseEstiloCelda" scope="page"/>" 
									style="text-align: right" 
									id="valorIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									name="valorIndicadorId<bean:write name="valorInicialIndicador" property="indicador.indicadorId" />" 
									value="<bean:write name="valorInicialIndicador" property="valorInicial.valor" />"
								>								
							</td>

						</tr>

					</logic:iterate>

				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">

			formatearValores();

		</script>

	</tiles:put>

</tiles:insert>