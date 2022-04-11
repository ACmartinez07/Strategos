<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/09/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.configurarvistadatos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="configurarVistaDatosForm" property="bloqueado">
				<bean:write name="configurarVistaDatosForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="configurarVistaDatosForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>
	
		<script type="text/javascript">
		
			function cancelar() 
			{
				javascript:irAtras(1);
			}
			
			function guardar()
			{
				if (!validar())
					return;
			    
				// evita que se pierdan los miembros de las dimensiones seleccionadas
			    setTextoMiembrosDimension(); 	
			    document.configurarVistaDatosForm.textoSelectores.value = asignarSelectores(); 
			 
			    window.document.configurarVistaDatosForm.action = '<html:rewrite action="/vistasdatos/guardarConfiguracionVistaDatos"/>?reporteId=' + document.configurarVistaDatosForm.reporteId.value + configuracion();
			    window.document.configurarVistaDatosForm.submit();	    
			}
			
			function validar() 
			{
			 	if (window.document.configurarVistaDatosForm.nombre.value == "")
		 		{
		 			alert('<vgcutil:message key="jsp.configurarvistadatos.alerta.nombre.vacio" /> ');
		 			return false;
		 		}
			 	
			 	return true;
			}
			
			function configuracion()
			{
				var campos = '&corte=' + document.configurarVistaDatosForm.corte.value;
				campos = campos + '&showTotalFilas=' + document.configurarVistaDatosForm.showTotalFilas.value;
				campos = campos + '&showTotalColumnas=' + document.configurarVistaDatosForm.showTotalColumnas.value;
				
				return campos;
			}
			
			function mostrar() 
			{
				if (!validar())
					return;

				// evita que se pierdan los miembros de las dimensiones seleccionadas 
			    setTextoMiembrosDimension(); 	
			    document.configurarVistaDatosForm.textoSelectores.value = asignarSelectores(); 
			 
			    window.document.configurarVistaDatosForm.action = '<html:rewrite action="/vistasdatos/mostrarVistaDatos"/>?ts=<%= (new java.util.Date()).getTime() %>&source=2' + configuracion();
			    window.document.configurarVistaDatosForm.submit();
			}

			function resizePaneles()
			{
				var alto = marcoVistaGeneral.style.height.replace("px", "");
				var panel_derecho = document.getElementById('panel-derecho');
				if (panel_derecho != null)
				{
					var anchoDerecho = (((_myWidth - 160) * 60) / 100);
					panel_derecho.style.width = anchoDerecho + "px";
					panel_derecho.style.height = (parseInt(alto)) + "px";
					var panel_izquierdo = document.getElementById('panel-izquierdo');
					if (panel_izquierdo != null)
					{
						panel_izquierdo.style.width = ((_myWidth - 160) - anchoDerecho) + "px";
						panel_izquierdo.style.height = (parseInt(alto)) + "px";
					}
					var panel_izquierdo_visualizar = document.getElementById('panel-izquierdo-visualizar');
					if (panel_izquierdo_visualizar != null)
					{
						panel_izquierdo_visualizar.style.width = (((_myWidth - 160) - anchoDerecho) - 20) + "px";
						panel_izquierdo_visualizar.style.height = (parseInt(alto) - 50) + "px";  
					}

					var panel_derecho_superior = document.getElementById('panel-derecho-superior');
					var panel_derecho_inferior = document.getElementById('panel-derecho-inferior');
					var altoPaneles = alto / 2;
					if (panel_derecho_superior != null)
					{
						panel_derecho_superior.style.height = altoPaneles + "px";
						var pTabla = document.getElementById('panel-derecho-superior-tabla');
						if (pTabla != null)
							pTabla.style.width = (anchoDerecho - 20) + "px";
					}
					if (panel_derecho_inferior != null)
					{
						panel_derecho_inferior.style.height = altoPaneles + "px";
						var pTabla = document.getElementById('panel-derecho-inferior-tabla');
						if (pTabla != null)
							pTabla.style.width = (anchoDerecho - 20) + "px";
					}
				}
			}
			
		</script>

		<html:form action="/vistasdatos/configurarVistaDatos" styleClass="formaHtml">
			<html:hidden property="reporteId" />
			<html:hidden property="corte" />
			<html:hidden property="showTotalFilas" />
			<html:hidden property="showTotalColumnas" />
			
			<vgcinterfaz:contenedorForma idContenedor="body-reporte-logitudinal">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.configurarvistadatos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:cancelar();
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
		
					<vgcinterfaz:barraHerramientas nombre="barraGestionarVista">
						<logic:notEqual name="configurarVistaDatosForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="vistas_detalle" pathImagenes="/componentes/barraHerramientas/" nombre="aceptar" onclick="javascript:mostrar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.vista" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
						<logic:notEqual name="configurarVistaDatosForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="guardar" pathImagenes="/componentes/barraHerramientas/" nombre="guardar" onclick="javascript:guardar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.guardar.configuracion" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
					</vgcinterfaz:barraHerramientas>

					<%-- Edicion --%>
					<div id="marcoVistaBasica" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: hidden">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="barraFiltrosForma" height="10px">
								<td colspan="3">
								</td>
							</tr>
							<tr class="barraFiltrosForma">
								<td valign="top" width="500px" style="padding-left: 30px;">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr class="barraFiltrosForma">
											<td width="65px"><vgcutil:message key="jsp.listardimensiones.frecuencia" /></td>
											<td>
												<html:select disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="frecuencia" styleClass="cuadroTexto">
													<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
												</html:select>
											</td>
										</tr>
										<tr class="barraFiltrosForma">
											<%-- Nombre --%>
											<td width="65px"><vgcutil:message key="jsp.configurarvistadatos.nombre" /></td>
											<td>
												<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="50" styleClass="cuadroTexto" />
											</td>
										</tr>
										<tr class="barraFiltrosForma">
											<%-- Nombre --%>
											<td width="65px"><vgcutil:message key="jsp.configurarvistadatos.publico" /></td>
											<td><html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="publico" styleClass="botonSeleccionMultiple"></html:checkbox></td>
										</tr>
									</table>
								</td>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="barraFiltrosForma">
											<%-- Descripcion --%>
											<td width="75px" valign="top"><vgcutil:message key="jsp.configurarvistadatos.descripcion" /></td>
											<td>
												<html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="5" cols="50" property="descripcion" styleClass="cuadroTexto" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="barraFiltrosForma" height="10px">
								<td colspan="3">
								</td>
							</tr>
						</table>
					</div>
		
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<div id="marcoVistaGeneral" style="position: relative; top: 0; left: 0; border: thin; border-color: black; border-style: solid; overflow: hidden">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td id="panel-derecho" width="60%" valign="top">
								<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="50%">
										<td id="panel-derecho-superior" valign="top">
											<jsp:include flush="true" page="/paginas/strategos/vistasdatos/listarDimensiones.jsp"></jsp:include>
										</td>
									</tr>
									<tr height="50%">
										<td id="panel-derecho-inferior" valign="top">
											<jsp:include flush="true" page="/paginas/strategos/vistasdatos/listarMiembros.jsp"></jsp:include>
										</td>
									</tr>
								</table>
							</td>
							<td id="panel-izquierdo" valign="top">
								<jsp:include flush="true" page="/paginas/strategos/vistasdatos/visualizarDatos.jsp"></jsp:include>
							</td>
					</table>
				</div>

			</vgcinterfaz:contenedorForma>
			
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-reporte-logitudinal'), 280);
			resizeAlto(document.getElementById('marcoVistaGeneral'), 290);
			
			marcoVistaGeneral.style.width = (_myWidth - 160) + "px";
			resizePaneles();
		</script>

	</tiles:put>

</tiles:insert>
