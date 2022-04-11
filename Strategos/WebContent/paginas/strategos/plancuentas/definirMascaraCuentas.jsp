<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Kerwin Arias (30/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">		
		<vgcutil:message key="jsp.definirmascaracuentas" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<script language="Javascript" src="<html:rewrite page='/paginas/strategos/plancuentas/plancuentasJs/mascara.js'/>"></script>
		<script language="Javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
		
            inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
		    var pasoActual = 1;

			function guardar() {
				if (validar(document.editarCuentaForm)) {
					window.document.definirMascaraCuentasForm.submit();
				}
			}

			function cancelar() {					
				window.document.definirMascaraCuentasForm.action = '<html:rewrite action="/plancuentas/cancelarGuardarMascaraCuentas"/>?cancel=1';
				window.document.definirMascaraCuentasForm.submit();
			}
			
			function siguiente() {			     
			     var valido = true;
				 switch (pasoActual) {
				  case 1:				  
				  break;
			   	  case 2:
				  valido = insertarNiveles();
				  break;
				  case 3:
				  valido = verificarTextosVacios();
				  mostrarMensajeFinalizar();		  
				  break;	
				}				
				if (valido) {
				  pasoActual = pasoActual + 1; 
			      mostrarBotones(pasoActual);
				}			
				mostarTitulo();   
			}
			
			function previo() {				
				pasoActual = pasoActual - 1;
				mostrarBotones(pasoActual);
				mostarTitulo();
			} 
			
			function validar(forma) {
				window.document.definirMascaraCuentasForm.action = '<html:rewrite action="/plancuentas/guardarMascaraCuentas" />' + '?ts=<%= (new Date()).getTime() %>';
				return true;
			}
				
			function crearBoton(nombreBoton, accionBoton){
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
			         + ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"' 
			         + ' href=\"' + accionBoton + '\"'
			         + ' class=\"mouseFueraBarraInferiorForma\" >'
			         + nombreBoton + '</a>';				         
				return boton;
		    }	
				
			function mostrarBotones(paso) {	
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.finalizar.alt" />';
				var accionBotonFinalizar = 'javascript:guardar();';
				switch (paso) {
				  case 1:
					  <logic:notEqual name="definirMascaraCuentasForm" property="bloqueado" value="true">
					  	botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					  </logic:notEqual>
					  	botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					  <vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesDefinirMascara" nombrePanel="tabPaso1" />
				  break;				  
				  case 2:
					  botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					  botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					  botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					  <vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesDefinirMascara" nombrePanel="tabPaso2" />
				  break;				  
				  case 3:
					  botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					  botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					  botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					  <vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesDefinirMascara" nombrePanel="tabPaso3" />
				  break;				  
				  case 4:
					  botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					  botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
					  botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					  <vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesDefinirMascara" nombrePanel="tabPaso4" />
				  break;
				}
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}	
			
			function mostarTitulo() {			  
				var titulo = '..:: <vgcutil:message key="jsp.definirmascaracuentas.titulo1" /> ' + pasoActual + ' <vgcutil:message key="jsp.definirmascaracuentas.titulo2" />';
				var celda = document.getElementById("tituloFicha");			  
				celda.innerHTML = titulo;			
			}

			function verificarTextosVacios() {
			    var valido = true;
   				for (i = 0 ; i < document.forms[0].elements.length ; i++) {
   					if (document.forms[0].elements[i].type == 'text') {
			    		if (document.forms[0].elements[i].value == '') {
			    		    valido = false;
					   		alert ('<vgcutil:message key="jsp.definirmascaracuentas.validacion.mascarasdescripcion" />');
					   		break;
					    }
					}
   				}
   				return valido;
			}
			
			function mostrarMensajeFinalizar() {			
				var mensaje = '<vgcutil:message key="jsp.definirmascaracuentas.paso4.resumen.operacion2.1" />';
				mensaje = mensaje + ' ' + document.forms[0].niveles.value + ' ' + '<vgcutil:message key="jsp.definirmascaracuentas.paso4.resumen.operacion2.2" />. <vgcutil:message key="jsp.definirmascaracuentas.paso4.finalizar" />';
				var celda = document.getElementById('mensajeFinalizar');
				celda.innerHTML = mensaje + mostrarNivelesCreados();
			}

			function mostrarNivelesCreados() {
			   var niveles = document.forms[0].niveles.value;
			   var nivelesCreados = '';
			   for (i = 1 ; i <= parseInt(niveles) ; i++) {
			      nivelesCreados = nivelesCreados + '<tr><td><b>' + eval('document.forms[0].mascara_' + i).value + '</b> </td>';
			      nivelesCreados = nivelesCreados + '<td> - <b>' + eval('document.forms[0].nombre_' + i).value + '</b> </td></tr>';
			   }
			   nivelesCreados = '<br/><br/><table class="bordeFichaDatos" cellpadding="0" cellspacing="3" border="0">' + nivelesCreados;
			   nivelesCreados = nivelesCreados + '</table>';
			   return nivelesCreados;
			}

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/plancuentas/guardarMascaraCuentas">

			<%-- Id de la tabla --%>
			<html:hidden property="mascaraId" />

			<vgcinterfaz:contenedorForma width="530px" height="330px">
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">

					<tr height="250px">

						<%-- Imágen del asistente --%>
						<td width="120px"><img src="<html:rewrite page='/paginas/strategos/plancuentas/imagenes/definirMascara.gif'/>" border="0" width="120px" height="250px"></td>

						<td><vgcinterfaz:contenedorPaneles height="250px" width="380px" nombre="contenedorPanelesDefinirMascara" mostrarSelectoresPaneles="false">

							<%-- Panel: Paso 1 --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="tabPaso1" mostrarBorde="false">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%">
									<tr>
										<td><b><vgcutil:message key="jsp.definirmascaracuentas.paso1.texto1" /></b>: <vgcutil:message key="jsp.definirmascaracuentas.paso1.texto2" /></td>
									</tr>
									<tr>
										<td><br>
										<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
											<%-- Titulo --%>
											<tr>
												<td><b><vgcutil:message key="jsp.definirmascaracuentas.paso1.texto3" /></b>: <bean:write name="definirMascaraCuentasForm" property="mascara" /></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel: Paso 2 --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="tabPaso2" mostrarBorde="false">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%">
									<tr>
										<td>
										<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" border="0">
											<%-- Titulo --%>
											<tr>
												<td colspan="2"><b><vgcutil:message key="jsp.definirmascaracuentas.paso2.texto1" /></b></td>
											</tr>
											<tr height="20px">
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td><vgcutil:message key="jsp.definirmascaracuentas.paso2.text2" /></td>
												<td width="145px">
												<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
													<tr>
														<td valign="middle" align="left"><html:text property="niveles" size="1" readonly="true" styleClass="cuadroTexto" /></td>
														<td valign="middle"><img id="botonNiveles" name="botonNiveles" src="<html:rewrite  page='/componentes/cuadroNumerico/updowncontrol.gif'/>" width="16" height="17" border="0" usemap="#MapControlUpDown1" /></td>
													</tr>
												</table>
												<map name="MapControlUpDown1" id="MapControlUpDown1">
													<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonNiveles')" onmouseout="normalAction('botonNiveles')" onmousedown="iniciarConteoContinuo('niveles', 6, 1);aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
													<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonNiveles')" onmouseout="normalAction('botonNiveles')" onmousedown="iniciarConteoContinuo('niveles', 6, 1);decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
												</map></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel: Paso 3 --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="tabPaso3" mostrarBorde="false">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%">
									<tr>
										<td>
										<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
											<%-- Titulo --%>
											<tr>
												<td><b><vgcutil:message key="jsp.definirmascaracuentas.paso3.texto1" /></b></td>
											</tr>
											<tr height="20px">
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td><vgcutil:message key="jsp.definirmascaracuentas.paso3.texto2" /></td>
											</tr>
											<tr>
												<td>
												<table align="center" id="tablaNiveles" cellspacing="3" cellpadding="0" border="0">
												</table>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel: Paso 4 --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="tabPaso4" mostrarBorde="false">
								<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" width="100%">
									<tr>
										<td>
										<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3">
											<%-- Titulo --%>
											<tr>
												<td><b><vgcutil:message key="jsp.definirmascaracuentas.paso4.resumen.operacion1" /></b></td>
											</tr>
											<tr height="20px">
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td id="mensajeFinalizar"></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

						</vgcinterfaz:contenedorPaneles></td>
					</tr>
				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script language="Javascript1.1">
			mostarTitulo();
			mostrarBotones(pasoActual);
		</script>

	</tiles:put>

</tiles:insert>
