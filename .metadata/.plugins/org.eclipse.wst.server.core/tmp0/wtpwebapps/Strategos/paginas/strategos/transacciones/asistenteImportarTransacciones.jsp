<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asistente.importacion.transaccion.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script>
		
			var _pasoActual = 1;
			var _archivoVerificado = false;
			var _configuracionSalvada = false;
			var _setCloseParent = false;
			var _verificarTabla = false;
			var _mostrarIndicadores = false;
			var _setShowReport = false;
			
			function init()
			{
				limpiarArchivo();
			}
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (_pasoActual) 
				{
					case 3:
						valido = validar(_pasoActual);
						break;
					case 4:
						valido = validar(_pasoActual);
						break;
					case 5:
						valido = validar(_pasoActual);
						if (valido)
							importar();
						break;
				}
				if (valido) 
				{
					_pasoActual = _pasoActual + 1;
					if (!_mostrarIndicadores && _pasoActual == 2)
						_pasoActual = _pasoActual + 1;
					mostrarBotones(_pasoActual);
				}
				mostrarTitulo();
			}
			
			function previo() 
			{
				_pasoActual = _pasoActual - 1;
				if (!_mostrarIndicadores && _pasoActual == 2)
					_pasoActual = _pasoActual - 1;
				mostrarBotones(_pasoActual);
				mostrarTitulo();
			}

			function crearBoton(nombreBoton, accionBoton)
			{
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
					+ nombreBoton + '</a>';
			
				return boton;
			}
		
			function mostrarBotones(paso) 
			{
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.finalizar.alt" />';
				var accionBotonFinalizar = 'javascript:siguiente();';
			
				switch (paso) 
				{
					case 1:
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;  
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelPresentacion" />
						break;
					case 2:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelIndicadores" />
						break;
					case 3:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelSeleccionTipo" />
						break;
					case 4:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						if (document.transaccionForm.tipoFuente.value == "1" || document.transaccionForm.tipoFuente.value == "2")
							<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelVerficar" />
						else
							<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelVerficarBD" />
						break;
					case 5:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
						botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelFin" />
						break;
				}
				
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = '..:: <vgcutil:message key="jsp.asistente.importacion.transaccion.titulo1" /> ' + (_pasoActual + ' <vgcutil:message key="jsp.asistente.importacion.transaccion.titulo2" />');
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;
			}
			
			function validar(_pasoActual) 
			{
				if (_pasoActual == 3 && document.transaccionForm.tipoFuente.value == "1" && document.transaccionForm.separador.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.separador.vacio" /> ');
					return false;
				}

				if (_pasoActual == 3 && 
						(document.transaccionForm.tipoFuente.value == "3" 
								|| document.transaccionForm.tipoFuente.value == "4"
								|| document.transaccionForm.tipoFuente.value == "5") && document.transaccionForm.bdNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.nombre.vacio" /> ');
					return false;
				}

				if (_pasoActual == 3 && 
						(document.transaccionForm.tipoFuente.value == "3" 
								|| document.transaccionForm.tipoFuente.value == "4"
								|| document.transaccionForm.tipoFuente.value == "5") && document.transaccionForm.bdUsuario.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.usuario.vacio" /> ');
					return false;
				}
				
				if (_pasoActual == 3 && 
						(document.transaccionForm.tipoFuente.value == "3" 
								|| document.transaccionForm.tipoFuente.value == "4"
								|| document.transaccionForm.tipoFuente.value == "5") && document.transaccionForm.bdPassword.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.password.vacio" /> ');
					return false;
				}
				
				if (_pasoActual == 3 && 
						(document.transaccionForm.tipoFuente.value == "3" 
								|| document.transaccionForm.tipoFuente.value == "4"
								|| document.transaccionForm.tipoFuente.value == "5") && document.transaccionForm.bdServidor.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.servidor.vacio" /> ');
					return false;
				}
				
				if (_pasoActual == 3 && 
						(document.transaccionForm.tipoFuente.value == "3" 
								|| document.transaccionForm.tipoFuente.value == "4"
								|| document.transaccionForm.tipoFuente.value == "5") && document.transaccionForm.bdPuerto.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.puerto.vacio" /> ');
					return false;
				}
				
				if (_pasoActual == 4 && !_archivoVerificado)
				{
					if (document.transaccionForm.tipoFuente.value == "1" || document.transaccionForm.tipoFuente.value == "2")
						alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar.archivo.false" /> ');
					else
						alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar.tabla.false" /> ');
					return false;
				}
				
				return true;
			}
			
			function cancelar() 
			{
				this.close();			
			}
			
			function seleccionarArchivo(obj)
			{
				var file = obj.value;
			    var fileName = file.split("\\");
			    var archivo = fileName[fileName.length-1];
			    var extension = archivo.substring(archivo.indexOf('.') + 1, archivo.length); 

				if (document.transaccionForm.tipoFuente.value == "2" && document.transaccionForm.excelTipo[0].checked && extension.toUpperCase() != 'XLS')
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.tipo.excel.2007.noexiste" /> ');
					limpiarArchivo();
					return
				}
				else if (document.transaccionForm.tipoFuente.value == "2" && document.transaccionForm.excelTipo[1].checked && extension.toUpperCase() != 'XLSX')
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.tipo.excel.2010.noexiste" /> ');
					limpiarArchivo();
					return
				}
							    
				document.getElementById("btnVerificar").disabled = false; 
				window.document.transaccionForm.fuenteSeleccion.value = obj.value;
				window.document.transaccionForm.fuenteSeleccionArchivo.value = archivo;
			}
			
			function limpiarArchivo()
			{
				window.document.transaccionForm.fuenteSeleccion.value = '';
				window.document.transaccionForm.fuenteSeleccionArchivo.value = '';
				document.getElementById("btnVerificar").disabled = true;
				document.getElementById("uploadFile_div").innerHTML=document.getElementById("uploadFile_div").innerHTML;
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);

				tablaListaInsumos = document.getElementById('tablaListaInsumosBd');
				
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
				
				document.transaccionForm.bdNombre.value = "";
				document.transaccionForm.bdUsuario.value = "";
				document.transaccionForm.bdPassword.value = "";
				document.transaccionForm.bdServidor.value = "";
				document.transaccionForm.bdPuerto.value = "";
				document.transaccionForm.bdTabla.value = "";
				
				document.getElementById("bdStatusCheck").src = "<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/No.gif'/>";
				
				_archivoVerificado = false;
				_verificarTabla = false;
			}
			
			function verificarArchivo()
			{
				if (document.transaccionForm.fuenteSeleccion.value == "" && (document.transaccionForm.tipoFuente.value == "1" || document.transaccionForm.tipoFuente.value == "2"))
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.archivo.alerta.vacio" /> ');
					return;
				}
				else
				{
					if (!_verificarTabla)
					{
						var tablaListaInsumos = document.getElementById('tablaListaInsumosBd');
						
						// Se borra la lista de insumos
						while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
							tablaListaInsumos.deleteRow(0);
					}
				}
				
				var xml = '?funcion=verificar';
				xml = xml + '&transaccionId=' + document.transaccionForm.transaccionId.value;
				if (document.transaccionForm.tipoFuente.value == "1" || document.transaccionForm.tipoFuente.value == "2")
				{
					xml = xml + '&direccion=' + document.transaccionForm.fuenteSeleccion.value;
					xml = xml + '&archivo=' + document.transaccionForm.fuenteSeleccionArchivo.value;
				}
				else
				{
					xml = xml + '&password=' + document.transaccionForm.bdPassword.value;
					if (_verificarTabla)
						xml = xml + '&verificarTabla=true';
					else
					{
						document.transaccionForm.bdTabla.value = "";
						document.getElementById("bdStatusCheck").src = "<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/No.gif'/>";
					}
				}
				_archivoVerificado = false;
				
				document.transaccionForm.action = '<html:rewrite action="/transacciones/importar"/>' + xml;
				document.transaccionForm.submit();
			}
			
			function onVerificarArchivo()
			{
				var respuesta = document.transaccionForm.respuesta.value;

				if (document.transaccionForm.tipoFuente.value == "1" || document.transaccionForm.tipoFuente.value == "2")
				{
					if (respuesta == "Error")
					{
						alert('<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar.archivo.error" /> ');
						return;
					}
					
					var tablaListaInsumos = document.getElementById('tablaListaInsumos');
					
					// Se borra la lista de insumos
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);

					var insumos = respuesta.split(',');
					var valores;
					var imagen;
					var hayCampos = true;
					for (var i = 0; i < insumos.length; i++) 
					{
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");

						if (insumos[i].length > 0) 
						{
							imagen = "No.gif";
							valores = insumos[i].split('=');
							if (valores[1] == "true")
								imagen = "Si.png";
							if (imagen == "No.gif")
								hayCampos = false;
								
							td1.innerHTML = "<img src=\"<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/" + imagen +"'/>\" border=\"0\" width=\"16\" height=\"16\" />";
							td2.appendChild(document.createTextNode(valores[0]));
							td2.style.color = "blue";
							
							row.appendChild(td1);
							row.appendChild(td2);
							tbody.appendChild(row);
						}
					}
					
					if (hayCampos)
						_archivoVerificado = true;
				}
				else if (document.transaccionForm.tipoFuente.value == "3" || document.transaccionForm.tipoFuente.value == "4" || document.transaccionForm.tipoFuente.value == "5")
				{
					var tablaListaInsumos = document.getElementById('tablaListaInsumosBd');
					
					// Se borra la lista de insumos
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);

					var insumos = null;
					var status = null;
					if (document.transaccionForm.bdTabla.value != "")
					{
						status = respuesta.split(',');
						insumos = status[0].split('|');
					}
					else
						insumos = respuesta.split('|');
					
					for (var i = 0; i < insumos.length; i++) 
					{
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");

						td1.innerHTML = "<img src=\"<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/Si.png'/>\" border=\"0\" width=\"16\" height=\"16\" /><input type='hidden' value='" + insumos[i] + "' id='tabla_" + i + "' name='tabla_" + i + "'>";
						td2.appendChild(document.createTextNode(insumos[i]));
						td2.style.color = "blue";
							
						row.appendChild(td1);
						row.appendChild(td2);
						row.onclick = function() 
						{
							selectRowColors(this.rowIndex, 
										document.getElementById('tablaListaInsumosBd'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "blue", "white");
							setTabla(this.rowIndex);
						};
						
						tbody.appendChild(row);
					}

					if (status != null && status.length > 1 && status[1] == "10000")
					{
						_archivoVerificado = true;
						document.getElementById("bdStatusCheck").src = "<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/Si.png'/>";
					}
					else if (status != null && status.length > 1 && status[1] != "10000")
					{
						document.getElementById("bdStatusCheck").src = "<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/No.gif'/>";
						document.transaccionForm.bdTabla.value = "";
					}
				}
				_verificarTabla = false;
			}
			
			function setTabla(indexRow) 
			{
				indexRow = indexRow / 1;
				document.transaccionForm.bdTabla.value = document.getElementById('tabla_' + indexRow).value;
			}		

			function selectRowColors(indexRow, oTable, fieldRowSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var rowSelected = fieldRowSelected.value / 1;
				var i = 0;

				indexRow = indexRow / 1;

				if (oTable.getElementsByTagName("tr").item(rowSelected) != null) 
				{
					for (i = 0; i < oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length; i++) 
					{
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
					};
				}
			
				for (i = 0; i < oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").length; i++) 
				{
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
				}
			
				fieldRowSelected.value = indexRow;
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function eventoCambioFuente(limpiar)
			{
				if (limpiar)
					limpiarArchivo();
				var tr = document.getElementById("trTipoPlan");
				tr.style.display = "none";
				tr = document.getElementById("trTipoExcel");
				tr.style.display = "none";
				tr = document.getElementById("trTipoOthers");
				tr.style.display = "none";
				
				switch (document.transaccionForm.tipoFuente.value) 
				{
					case "1":
						tr = document.getElementById("trTipoPlan");
						tr.style.display = "";
						break;
					case "2":
						tr = document.getElementById("trTipoExcel");
						tr.style.display = "";
						break;
					case "3":
					case "4":
					case "5":
						tr = document.getElementById("trTipoOthers");
						tr.style.display = "";
						break;
				}
			}
			
			function chequearArchivo()
			{
				_verificarTabla = true;
				verificarArchivo();
			}
			
			function importar()
			{
				var xml = '?funcion=importar';
				xml = xml + '&direccion=' + document.transaccionForm.fuenteSeleccion.value;
				xml = xml + '&archivo=' + document.transaccionForm.fuenteSeleccionArchivo.value;
				xml = xml + '&logMediciones=' + (document.transaccionForm.logMediciones.checked ? 1 : 0);
				xml = xml + '&logErrores=' + (document.transaccionForm.logErrores.checked ? 1 : 0);
				xml = xml + '&transaccionId=' + document.transaccionForm.transaccionId.value;				

				activarBloqueoEspera();

				document.transaccionForm.action = '<html:rewrite action="/transacciones/importar"/>' + xml;
				document.transaccionForm.submit();
			}
			
			function onImportar()
			{
				if (document.transaccionForm.respuesta.value == "Error")
					return;
				else if (document.transaccionForm.respuesta.value == "action.guardarmetas.mensaje.guardarmetas.relacionados")
					return;
				else if (document.transaccionForm.respuesta.value == "action.guardarmediciones.mensaje.guardarmediciones.related")
					return;
				
				cancelar();
			}
			
			function seleccionarIndicador(tipoIndicador)
			{
				var permitirCambiarClase = 'true';
				var indicadorId = "";
				var indicadorNombre = "";
				var excluirId = "";
				if (tipoIndicador == 0)
				{
					indicadorId = 'indicadorTransaccionesId';
					indicadorNombre = 'indicadorTransaccionesNombre';
					excluirId = document.transaccionForm.indicadorTransaccionesId.value; 
				}
				else
				{
					indicadorId = 'indicadorMontoId';
					indicadorNombre = 'indicadorMontoNombre';
					excluirId = document.transaccionForm.indicadorMontoId.value; 
				}
				
				abrirSelectorIndicadores('transaccionForm', indicadorNombre, indicadorId, null, 'finalizarSeleccionIndicador()', null, null, null, null, null, '&seleccionMultiple=false&mostrarSeriesTiempo=false&permitirCambiarOrganizacion=false&permitirCambiarClase=' + permitirCambiarClase + '&permitirIniciativas=false&frecuencia=' + document.transaccionForm.frecuencia.value + '&excluirIds=' + excluirId);
			}
			
			function limpiarIndicador(tipoIndicador)
			{
				if (tipoIndicador == 0)
				{
					document.transaccionForm.indicadorTransaccionesId.value = '';
					document.transaccionForm.indicadorTransaccionesNombre.value = '';
				}
				else
				{
					document.transaccionForm.indicadorMontoId.value = '';
					document.transaccionForm.indicadorMontoNombre.value = '';
				}
			}
			
			function finalizarSeleccionIndicador() 
			{
			}
			
			function onShowReport()
			{
				if (_setShowReport)
					verDetalles();
				_setShowReport = false;
			}
			
			function verDetalles() 
			{
				if (confirm('<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.importar.archivo.exito" />'))
					window.open('<html:rewrite action="/indicadores/verArchivoLog"/>','detallesImportacion','width=440,height=420,status=yes,resizable=yes,top=100,left=100,menubar=no,scrollbars=yes,,dependent=yes');
			}
			
		</script>

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/transacciones/importar" styleClass="formaHtml" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="respuesta" />
			<html:hidden property="status" />
			<html:hidden property="fuenteSeleccionArchivo" />
			<html:hidden property="fuenteSeleccion" />
			<html:hidden property="indicadorTransaccionesId" />
			<html:hidden property="indicadorMontoId" />
			<html:hidden property="frecuencia" />
			<html:hidden property="transaccionId" />
			
			<input type="hidden" name="insumoSeleccionado" id="insumoSeleccionado">
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="570px" height="390px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
					<table class="bordeFichaDatostabla bordeFichaDatos">
						<tr height=315px>
	
							<%-- Imágen del asistente --%>
							<td width="315px">
								<img src="<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/WZImportacion.gif'/>" border="0" width="150px" height="310px">
							</td>
	
							<td>
								<vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">
	
									<%-- Panel Presentacion --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<b><vgcutil:message key="jsp.asistente.importacion.transaccion.presentacion.importante" /></b><br><br>
													<div align="justify"> 
														<vgcutil:message key="jsp.asistente.importacion.transaccion.presentacion.1" /><br><br>
														<vgcutil:message key="jsp.asistente.importacion.transaccion.presentacion.2" /><br><br>
														<vgcutil:message key="jsp.asistente.importacion.transaccion.presentacion.3" />
													</div>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel Asociar Indicadores --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelIndicadores" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<div align="justify"> 
														<vgcutil:message key="jsp.asistente.importacion.transaccion.indicadores.informacion" /><br><br>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<table class="tabla contenedorBotonesSeleccion">
														<logic:equal name="transaccionForm" property="hayColumnaFecha" value="true">
															<tr>
																<td colspan="2">
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.indicadores.informacion.indicador.transaccion" />
																</td>
															</tr>
															<tr>
																<td colspan="2">
																	<div id="indicadorTransacciones">
																		<html:text property="indicadorTransaccionesNombre" size="53" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
																		<img style="cursor: pointer" onclick="seleccionarIndicador(0);" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.importacion.transaccion.indicadores.informacion.indicador.transaccion.title" />">&nbsp;
																		<img style="cursor: pointer" onclick="limpiarIndicador(0);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"><br><br>
																	</div>
																</td>
															</tr>
														</logic:equal>
														<logic:equal name="transaccionForm" property="hayColumnaMonto" value="true">
															<tr>
																<td colspan="2">
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.indicadores.informacion.indicador.total" />
																</td>
															</tr>
															<tr>
																<td colspan="2">
																	<div id="indicadorTransacciones">
																		<html:text property="indicadorMontoNombre" size="53" readonly="true" maxlength="50" styleClass="cuadroTexto" />&nbsp;
																		<img style="cursor: pointer" onclick="seleccionarIndicador(1);" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="jsp.asistente.importacion.transaccion.indicadores.informacion.indicador.total.title" />">&nbsp;
																		<img style="cursor: pointer" onclick="limpiarIndicador(1);" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
																	</div>
																</td>
															</tr>
														</logic:equal>
													</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
		
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelSeleccionTipo" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.tipos" /></td>
												<td>
													<html:select property="tipoFuente"
														styleClass="cuadroTexto" size="1"
														onchange="eventoCambioFuente(true)">
														<html:optionsCollection property="tiposFuentes" value="id" label="nombre" />
													</html:select>
													<br><br>
												</td>
											</tr>
											<tr id="trTipoPlan" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.separador" /></td>
															<td>
																<html:text property="separador" size="5" maxlength="1" styleClass="cuadroTexto"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr id="trTipoExcel" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.excel.tipo" /></td>
														</tr>
														<tr>
															<td>
																<html:radio property="excelTipo" value="0">
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.excel.tipo.2003" />
																</html:radio>
															</td>
														</tr>
														<tr>
															<td>
																<html:radio property="excelTipo" value="1">
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.excel.tipo.2010" />
																</html:radio>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr id="trTipoOthers" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.titulo" /></td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.nombre" /></td>
															<td>
																<html:text property="bdNombre" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.usuario" /></td>
															<td>
																<html:text property="bdUsuario" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.password" /></td>
															<td>
																<html:password property="bdPassword" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.servidor" /></td>
															<td>
																<html:text property="bdServidor" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.transaccion.archivo.seleccion.bd.puerto" /></td>
															<td>
																<html:text property="bdPuerto" size="10" maxlength="6" styleClass="cuadroTexto"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel Verificar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelVerficar" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.titulo" />
												</td>
											</tr>
											<tr>
												<td>
													<div id="uploadFile_div" name="uploadFile_div" height="100%" width="100%">
														<input size="38" type="file" id="fileName" name="fileName" class="cuadroTexto" title="<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.archivo" />" onchange="seleccionarArchivo(this);"/>&nbsp;
														<img style="cursor: pointer" onclick="limpiarArchivo();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.tabla" />
												</td>
											</tr>
											<tr height="200px" valign="top">
												<td colspan="2">
													<table class="tablainsumo contenedorBotonesSeleccion">
														<tr valign="top">
															<td>
																<table id="tablaListaInsumos">
																	<tbody class="cuadroTexto">
																	</tbody>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr align="left">
												<td>
													<input id="btnVerificar" name="btnVerificar" type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar" />" onclick="verificarArchivo();" disabled="false">
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<%-- Panel Verificar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelVerficarBD" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr valign="top" align="left">
												<td>
													<input id="btnVerificar" name="btnVerificar" type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar" />" onclick="verificarArchivo();">
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.tabla.bd" />
												</td>
											</tr>
											<tr height="200px" valign="top">
												<td colspan="2">
													<table class="tablainsumo contenedorBotonesSeleccion">
														<tr valign="top">
															<td>
																<div style="overflow:auto; height:200px; padding:0">
																	<table id="tablaListaInsumosBd">
																		<tbody class="cuadroTexto">
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<html:text property="bdTabla" size="30" styleClass="cuadroTexto" disabled="false"/>
													<img style="cursor: pointer" onclick="chequearArchivo();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.chequear.alt"/>">&nbsp;
													<img id="bdStatusCheck" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/No.gif'/>" border="0" width="16" height="16">
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<%-- Panel Finalizar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelFin" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<b><vgcutil:message key="jsp.asistente.importacion.transaccion.fin.mensaje1" /></b><br><br> 
													<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.mensaje2" /><br><br>
												</td>
											</tr>
											<tr>
												<td>
													<br><br><br>
													<br><br><br>
													<br><br><br>
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.reportar" />
												</td>
											</tr>
											<tr>
												<td>
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td colspan="2">
																<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.reportar.titulo" />
															</td>
														</tr>
														<tr>
															<td>
																<html:checkbox property="logMediciones" styleClass="botonSeleccionMultiple" >
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.reportar.mediciones" />
																</html:checkbox>
															</td>
															<td>
																<html:checkbox property="logErrores" styleClass="botonSeleccionMultiple" >
																	<vgcutil:message key="jsp.asistente.importacion.transaccion.fin.reportar.errores" />
																</html:checkbox>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
	
								</vgcinterfaz:contenedorPaneles>
							</td>
						</tr>
					</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			<logic:equal name="transaccionForm" property="hayColumnaFecha" value="true">
				_mostrarIndicadores = true;
			</logic:equal>

			//Inicio
			<logic:equal name="transaccionForm" property="status" value="0">
				eventoCambioFuente(true);
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				init();
			</logic:equal>

			//No se guardo la información
			<logic:equal name="transaccionForm" property="status" value="1">
				_pasoActual=4;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				eventoCambioFuente(false);
				onVerificarArchivo();
				_setShowReport = true;
			</logic:equal>
			
			//Verificar Conexion, Archivo y Tabla
			<logic:equal name="transaccionForm" property="status" value="2">
				_pasoActual=4;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				eventoCambioFuente(false);
				onVerificarArchivo();
			</logic:equal>

			// Importar
			<logic:equal name="transaccionForm" property="status" value="4">
				_pasoActual=5;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				desactivarBloqueoEspera();
				if (document.transaccionForm.respuesta.value == "Successful")
					_setCloseParent = true;
				else
					onImportar();
			</logic:equal>
			
			// Servicio no configurado
			<logic:equal name="transaccionForm" property="status" value="5">
				_setCloseParent = true;
			</logic:equal>
		</script>
	</tiles:put>
</tiles:insert>
