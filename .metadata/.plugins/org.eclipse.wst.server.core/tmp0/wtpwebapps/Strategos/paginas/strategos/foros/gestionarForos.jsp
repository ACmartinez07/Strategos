<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (28/11/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarforos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{
				window.location.href='<html:rewrite action="/foros/crearForo" />?padreId=' + gestionarForosForm.foroId.value + '&tipo=' + gestionarForosForm.tipo.value;
			}
			
			function gestionarForos(foroId, tipo) 
			{
				gestionarForosForm.foroId.value = foroId;
				gestionarForosForm.tipo.value = tipo + 1;
				gestionarForosForm.submit();
			}

		</script>
		
		<%-- Inclusión de los JavaScript externos a esta página --%>		
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/foros/gestionarForos" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="foroId" />
			<html:hidden property="tipo" />

			<vgcinterfaz:contenedorForma>

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarforos.titulo" /> - <bean:write name="gestionarForosForm" property="tipoObjetoKey" /> (<bean:write name="gestionarForosForm" property="nombreObjetoKey" />)						
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2);
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
						
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="FORO_ADD" />						
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Herramientas --%>				
						<vgcinterfaz:contenedorMenuHorizontalItem>	
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" permisoId="SEGUIMIENTO_MENSAJE" agregarSeparador="true"/>
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								--%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>					
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>	
						</vgcinterfaz:contenedorMenuHorizontalItem>
					
					</vgcinterfaz:contenedorMenuHorizontal>	
					
				</vgcinterfaz:contenedorFormaBarraMenus>
				
				<logic:greaterThan name="gestionarForosForm" property="tipo" value="-1">
				
					<%-- Barra Genérica --%>
					<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					
						<%-- Barra de Herramientas --%>	
						<vgcinterfaz:barraHerramientas nombre="barraGestionarUnidadesMedida">						
							<vgcinterfaz:barraHerramientasBoton permisoId="FORO_ADD" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.gestionarforos.nuevo.comentario" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcinterfaz:barraHerramientas>
						
					</vgcinterfaz:contenedorFormaBarraGenerica>
					
				</logic:greaterThan>

				<%-- Foro --%>
				<table class="foro" width="100%" cellpadding="5" cellspacing="1">

					<tr class="categoriaForo">

						<%-- Categoría --%>
						<td colspan="4"><img src="<html:rewrite page='/paginas/strategos/foros/imagenes/tipo.gif'/>"> <vgcutil:message key="jsp.gestionarforos.organizacion" /> (<bean:write
							name="gestionarForosForm" property="nombreOrganizacion" />) --> <a onMouseOver="this.className='mouseEncimaCuerpoForo'" onMouseOut="this.className='mouseFueraCuerpoForo'"
							href="javascript:gestionarForos(null, null)" class="mouseFueraCuerpoForo"> <bean:write name="gestionarForosForm" property="tipoObjetoKey" /> (<bean:write name="gestionarForosForm"
							property="nombreObjetoKey" />) </a>
							
							<%-- Lista de Foros --%>
							<logic:notEmpty name="gestionarForosForm" property="listaForos">

								<logic:iterate id="foro" name="gestionarForosForm" property="listaForos">
									--> <a onMouseOver="this.className='mouseEncimaCuerpoForo'" onMouseOut="this.className='mouseFueraCuerpoForo'" href="javascript:gestionarForos('<bean:write name="foro" property="foroId" />', null)" class="mouseFueraCuerpoForo">(<bean:write name="foro" property="asunto" />)</a>
								</logic:iterate>

							</logic:notEmpty>
							
						</td>

						

					</tr>

					<%-- Encabezado --%>
					<tr class="encabezadoForo">
						<td align="center" width="30px"></td>
						<td align="center" width="400px"><%-- Comentario --%> <logic:greaterThan name="gestionarForosForm" property="tipo" value="-1">
							<vgcutil:message key="jsp.gestionarforos.tipo.comentario" />
						</logic:greaterThan></td>
						<td align="center" width="100px"><vgcutil:message key="jsp.gestionarforos.respuestas" /></td>
						<td align="center" width="200px"><vgcutil:message key="jsp.gestionarforos.ultimarespuesta" /></td>
					</tr>


					<%-- Cuerpo --%>
					<logic:iterate name="paginaForos" property="lista" scope="request" id="foro">

						<tr class="cuerpoForo">
							<td align="center"><logic:equal name="foro" property="numeroRespuestas" value="0">
								<img src="<html:rewrite page='/paginas/strategos/foros/imagenes/noControversial.gif'/>">
							</logic:equal> <logic:notEqual name="foro" property="numeroRespuestas" value="0">
								<img src="<html:rewrite page='/paginas/strategos/foros/imagenes/controversial.gif'/>">
							</logic:notEqual></td>
							<td><a onMouseOver="this.className='mouseEncimaCuerpoForo'" onMouseOut="this.className='mouseFueraCuerpoForo'"
								href="javascript:gestionarForos(<bean:write name="foro" property="foroId" />, <bean:write name="foro" property="tipo" />);" class="mouseFueraCuerpoForo"> <bean:write name="foro"
								property="asunto" /> </a>
							<table class="autorForo">
								<tr>
									<td><vgcutil:message key="jsp.gestionarforos.creado" /> <bean:write name="foro" property="fechaFormateadaCreado" /> <vgcutil:message key="jsp.gestionarforos.creadopor" /> <b><bean:write
										name="foro" property="usuarioCreado.fullName" /></b></td>
								</tr>
							</table>
							<hr width="100%">
							<bean:write name="foro" property="comentario" /></td>
							<td align="center"><bean:write name="foro" property="numeroRespuestas" /></td>
							<td>
							<table class="autorForo">
								<tr>
									<td><logic:notEmpty name="foro" property="ultimaRepuestaForo">
										<bean:write name="foro" property="ultimaRepuestaForo.fechaFormateadaCreado" />
										<br>
										<vgcutil:message key="jsp.gestionarforos.creadopor" />
										<b><bean:write name="foro" property="ultimaRepuestaForo.usuarioCreado.fullName" /></b>
									</logic:notEmpty> <logic:empty name="foro" property="ultimaRepuestaForo">---</logic:empty></td>
								</tr>
							</table>
							</td>
						</tr>

					</logic:iterate>

					<%-- Validación cuando no hay registros --%>
					<logic:equal name="paginaForos" property="total" value="0" scope="request">
						<tr class="cuerpoForo" id="0" height="30px">
							<td valign="middle" align="center" colspan="4"><%-- Comentario --%> <logic:greaterThan name="gestionarForosForm" property="tipo" value="-1">
								<vgcutil:message key="jsp.gestionarforos.nocomentarios" />
							</logic:greaterThan></td>
						</tr>
					</logic:equal>

				</table>

				<br>

				<%-- Resumen --%>
				<table class="foro" width="100%" cellpadding="5" cellspacing="1">

					<tr class="categoriaForo">
						<td width="50%"><vgcutil:message key="jsp.gestionarforos.leyenda" /></td>
						<td width="50%"><vgcutil:message key="jsp.gestionarforos.resumen" /></td>
					</tr>

					<tr class="encabezadoForo">
						<td><img src="<html:rewrite page='/paginas/strategos/foros/imagenes/controversial.gif'/>">&nbsp;&nbsp;&nbsp;<vgcutil:message key="jsp.gestionarforos.resumen.imagen.controversial" /><br>
						<br>
						<img src="<html:rewrite page='/paginas/strategos/foros/imagenes/noControversial.gif'/>">&nbsp;&nbsp;&nbsp;<vgcutil:message key="jsp.gestionarforos.resumen.imagen.nocontroversial" /></td>
						<td><vgcutil:message key="jsp.gestionarforos.respuestas" />: <vgcutil:message key="jsp.gestionarforos.resumen.texto" /> <bean:write name="paginaForos" property="total" /> <%-- Comentario --%>
						<logic:greaterThan name="gestionarForosForm" property="tipo" value="-1">
							<vgcutil:message key="jsp.gestionarforos.tipo.comentario" />
						</logic:greaterThan></td>
					</tr>

				</table>

			</vgcinterfaz:contenedorForma>

		</html:form>

	</tiles:put>

</tiles:insert>
