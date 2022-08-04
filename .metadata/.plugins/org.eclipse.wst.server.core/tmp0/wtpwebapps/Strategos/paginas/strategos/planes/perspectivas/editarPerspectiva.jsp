<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (06/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarPerspectivaForm" property="perspectivaId" value="0">
			<logic:equal name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
				<vgcutil:message key="jsp.editarperspectiva.titulo.nuevo" /> - <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
			</logic:equal>
			<logic:notEqual name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="1">
				<vgcutil:message key="jsp.editarperspectiva.titulo.nuevo" /> - <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
			</logic:notEqual>
		</logic:equal>
		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarPerspectivaForm" property="perspectivaId" value="0">
			<vgcutil:message key="jsp.editarperspectiva.titulo.modificar" />
		</logic:notEqual>

	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<jsp:include page="/paginas/strategos/planes/perspectivas/perspectivasJs.jsp"></jsp:include>

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarPerspectivaForm" property="bloqueado">
				<bean:write name="editarPerspectivaForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarPerspectivaForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<script type="text/javascript">      
		    
			function guardar() 
			{
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarPerspectiva"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarPerspectivaForm)) 
					window.document.editarPerspectivaForm.submit();
			}

			function cancelar() 
			{
				window.document.editarPerspectivaForm.action = '<html:rewrite action="/planes/perspectivas/cancelarGuardarPerspectiva"/>';
				window.document.editarPerspectivaForm.submit();					
			}

			function validar(forma) 
			{
				if (validateEditarPerspectivaForm(forma)) 
				{
					window.document.editarPerspectivaForm.action = '<html:rewrite action="/planes/perspectivas/guardarPerspectiva" />'+ '?ts=<%= (new Date()).getTime() %>';
					return true;
				} 
				else 
					return false;
			}
			
			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
			function limpiarResponsable() 
			{				
				document.editarPerspectivaForm.responsableId.value = "";				
				document.editarPerspectivaForm.nombreResponsable.value = "";
			}
			
			function seleccionarResponsable() 
			{				
				abrirSelectorResponsables('editarPerspectivaForm', 'nombreResponsable', 'responsableId', document.editarPerspectivaForm.responsableId.value, '<bean:write name="organizacionId" scope="session"/>', 'true', 'true');
			}
			
			function seleccionarObjetivos()
			{
				var forma = 'editarPerspectivaForm';
				var funcionRetorno = 'agregarObjetivos()';
				var url = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true';

				abrirSelectorPerspectivas(forma, 'perspectivaAsociadaNombre', 'perspectivaAsociadaId', 'perspectivaAsociadaRutaCompleta', funcionRetorno, null, url);
			}
			
			function agregarObjetivos()
			{				
				var seleccionadoIds = document.editarPerspectivaForm.perspectivaAsociadaId.value;
				var seleccionadoNombres = document.editarPerspectivaForm.perspectivaAsociadaNombre.value;
				var seleccionadoRutasCompletas = document.editarPerspectivaForm.perspectivaAsociadaRutaCompleta.value;
				
				var listaInsumos = document.editarPerspectivaForm.insumosAsociados.value;
				var correlativo = 0;
			
				if (listaInsumos != null) 
				{
					if (listaInsumos == '') // no hay insumos en la lista 
						correlativo = 1;
					else 
					{
						strBuscada = ']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>' + '[';
						indice1 = listaInsumos.lastIndexOf(strBuscada);
						if (indice1 > -1) 
						{
							// hay dos o más insumos en la lista
							indice1 = indice1 + strBuscada.length;
							indice2 = listaInsumos.substring(indice1, listaInsumos.length).indexOf(']');
							indice2 = indice1 + indice2;
							correlativo = parseInt(listaInsumos.substring(indice1 , indice2)) + 1;
						} else 
						{
							// hay un solo insumo en la lista
							indice = listaInsumos.indexOf(']');
							correlativo = parseInt(listaInsumos.substring(1, indice)) + 1;
						};
					};
				} else 
				{
					listaInsumos = '';
					correlativo = 1;
				}
			
				if (seleccionadoIds != "")
				{
					for (var i = 0; i < seleccionadoIds.length; i++) 
					{
						partesId = seleccionadoIds;
						nombresIndicador = seleccionadoNombres;
				
						insumoBuscado = '[perspectivaId:' + partesId + ']';
				
						if (listaInsumos.indexOf(insumoBuscado) < 0) 
						{
							var separadorIndicadores = '';
							if (listaInsumos != '') 
								separadorIndicadores = '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>';
							
							if (seleccionadoRutasCompletas[i] != '<bean:write name="editarPerspectivaForm" property="codigoEliminado" ignore="true"/>') 
							{
								listaInsumos = listaInsumos 
											+ separadorIndicadores
											+ '[' + correlativo + ']' 
											+ insumoBuscado 
											+ '[perspectivaNombre:' + nombresIndicador  + ']'
											+ '[rutaCompleta:' + seleccionadoRutasCompletas + ']';
								correlativo++;
							}
						}
					}
				}
				
				document.editarPerspectivaForm.insumosAsociados.value = listaInsumos;
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
			
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
			
				// Se recorre la lista de insumos
				var insumos = document.editarPerspectivaForm.insumosAsociados.value.split('<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>');
				for (var i = 0; i < insumos.length; i++) 
				{
					if (insumos[i].length > 0) 
					{
						// correlativo
						strTemp = insumos[i];
						indice = strTemp.indexOf("][") + 1;
						correlativo = strTemp.substring(0, indice);
						// perspectivaId
						strTemp = strTemp.substring(indice, strTemp.length);
						indice = strTemp.indexOf("][");
						// perspectivaNombre
						strTemp = strTemp.substring(indice + 1, strTemp.length);
						indice = strTemp.indexOf("][");
						perspectivaNombre = '[' + strTemp.substring('perspectivaNombre:'.length + 1, indice + 1);
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");
						td1.appendChild(document.createTextNode(correlativo));
						td2.appendChild(document.createTextNode(perspectivaNombre));
						row.appendChild(td1);
						row.appendChild(td2);
						row.onclick = function() 
						{
							var selAnterior = document.getElementById('insumoSeleccionado').value;
							selectRowColors(this.rowIndex, 
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
							paintListaInsumosColumnaSerie(selAnterior, document.getElementById('tablaListaInsumos'), "blue");
							mostrarRutaCompletaInsumo();
						};
						tbody.appendChild(row);
						if (numFilas == 0) 
						{
							selectRowColors(0,
										document.getElementById('tablaListaInsumos'), 
										document.getElementById('insumoSeleccionado'),
										"white", "blue", "black", "white");
						}
					}
				}
				
				mostrarRutaCompletaInsumo();
			}

			function removerObjetivos()
			{				
				var tabla = document.getElementById('tablaListaInsumos');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				var insumosAsociados = document.editarPerspectivaForm.insumosAsociados.value;
				
				if (numeroFilas == 0) 
					alert('<vgcutil:message key="jsp.editarperspectiva.insumo.noinsumos" />');
				else
				{
					// Se modifica el string que contiene los insumos seleccionados
					var posicionBuscada = parseInt(document.editarPerspectivaForm.insumoSeleccionado.value);
					if (posicionBuscada == 0) 
					{
						var index = insumosAsociados.indexOf(']');
						// Se busca el último valor de cada indicador seleccionado
						index = insumosAsociados.indexOf('[rutaCompleta:');
						insumosAsociados = insumosAsociados.substring(index + 2, insumosAsociados.length);
						index = insumosAsociados.indexOf(']');
						if (insumosAsociados.length > (index + 1)) 
							index++;
						insumosAsociados = insumosAsociados.substring(index + 3, insumosAsociados.length);
					} 
					else if ((posicionBuscada + 1) == numeroFilas) 
					{
						var strBuscada = ']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>' + '[';
						var index = insumosAsociados.lastIndexOf(strBuscada) + 2;
						index2 = index + insumosAsociados.substring(index, insumosAsociados.length).indexOf(']');
						insumosAsociados = insumosAsociados.substring(0, index - 1);
					} 
					else 
					{
						var strBuscada = ']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>' + '[';
						var index = 0;
						for (var i = 0; i < posicionBuscada; i++) 
						{
							index = index + insumosAsociados.substring(index, insumosAsociados.length).indexOf(strBuscada);
							if (index != -1) 
								index= index + 2;
						}
						str1 = '';
						if (index != -1) 
						{
							str1 = insumosAsociados.substring(0, index);
							index2 = index + insumosAsociados.substring(index, insumosAsociados.length).indexOf(']');
						}
						index = index + insumosAsociados.substring(index, insumosAsociados.length).indexOf(strBuscada);
						str2 = insumosAsociados.substring(index + 2, insumosAsociados.length);
						insumosAsociados = str1 + str2;
					}
					document.editarPerspectivaForm.insumosAsociados.value = insumosAsociados;
					var index = document.editarPerspectivaForm.insumoSeleccionado.value;
					deleteRowColors(tabla, 
									document.editarPerspectivaForm.insumoSeleccionado.value,
									document.getElementById('insumoSeleccionado'),
									"white", "blue", "black", "white");
				}
				
				tabla = document.getElementById('tablaListaInsumos');
				numeroFilas = tabla.getElementsByTagName("tr").length;
				if (numeroFilas > 0)
					mostrarRutaCompletaInsumo();
				else
				{
					var tablaRutaCompletaInsumo = document.getElementById('tablaRutaCompletaInsumos');
					// Se borra la lista de insumos
					while (tablaRutaCompletaInsumo.getElementsByTagName("tr").length > 0) 
						tablaRutaCompletaInsumo.deleteRow(0);
				}
			}
			
			function mostrarRutaCompletaInsumo() 
			{
				var tabla = document.getElementById('tablaListaInsumos');
				var numFilas = tabla.getElementsByTagName("tr").length;
				var rutaCompletaInsumo = '';

				if (numFilas > 0) 
				{
					// Si existen indicadores fórmula
					// Se modifica el string que contiene los insumos seleccionados
					var posBuscada = parseInt(document.editarPerspectivaForm.insumoSeleccionado.value);
					var valorCorrelativoFormula = '';
					var insumosAsociados = document.editarPerspectivaForm.insumosAsociados.value;
					if (posBuscada == 0) 
					{
						var strBuscada = '[rutaCompleta:';
						var index = insumosAsociados.indexOf(strBuscada);
						insumosAsociados = insumosAsociados.substring(index + strBuscada.length, insumosAsociados.length);
						var index2 = insumosAsociados.indexOf(']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>');
						if (index2 < 0) 
							rutaCompletaInsumo = insumosAsociados.substring(0, insumosAsociados.length - 1); // solo hay 1 indicador insumo
						else 
							rutaCompletaInsumo = insumosAsociados.substring(0, index2);
					} 
					else if ((posBuscada + 1) == numFilas) 
					{
						var strBuscada = '[rutaCompleta:';
						var index = insumosAsociados.lastIndexOf(strBuscada);
						rutaCompletaInsumo = insumosAsociados.substring(index + strBuscada.length, insumosAsociados.length - 1);
					} 
					else 
					{
						var strBuscada = ']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>' + '[';
						var index = 0;
						for (var i = 0; i < posBuscada; i++) 
						{
							index = index + insumosAsociados.substring(index, insumosAsociados.length).indexOf(strBuscada);
							if (index > -1) 
								index= index + strBuscada.length;
						}
						if (index > -1) 
						{
							insumosAsociados = insumosAsociados.substring(index, insumosAsociados.length);
							strBuscada = '[rutaCompleta:'
							index = insumosAsociados.indexOf(strBuscada) + strBuscada.length;
							strBuscada = ']' + '<bean:write name="editarPerspectivaForm" property="separadorObjetivos" ignore="true"/>' + '[';
							index2 = insumosAsociados.indexOf(strBuscada);
							rutaCompletaInsumo = insumosAsociados.substring(index, index2);
						}
					}
					
					setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo);
				}
			}
			
			function setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo) 
			{
				var tablaRutaCompletaInsumo = document.getElementById('tablaRutaCompletaInsumos');

				// Se borra la lista de insumos
				while (tablaRutaCompletaInsumo.getElementsByTagName("tr").length > 0) 
					tablaRutaCompletaInsumo.deleteRow(0);

				if (rutaCompletaInsumo == '') 
					return;

				// Se recorre la lista de insumos
				var partesRuta = rutaCompletaInsumo.split('<bean:write name="editarPerspectivaForm" property="separadorRuta" ignore="true"/>');
				nroPartes = partesRuta.length;
				for (var i = 0; i < partesRuta.length; i++) 
				{
					var numFilas = tablaRutaCompletaInsumo.getElementsByTagName("tr").length;
					var tbody = tablaRutaCompletaInsumo.getElementsByTagName("TBODY")[0];
					var row = document.createElement("TR");
					for (var j = 0; j < i; j++) 
					{
						var tdIdent = document.createElement("TD");
						tdIdent.appendChild(document.createTextNode('-'));
						tdIdent.width = '5px';
						row.appendChild(tdIdent);
					}
					var tdValor = document.createElement("TD");
					tdValor.appendChild(document.createTextNode(partesRuta[i]));
					if (nroPartes > 1) 
						tdValor.colSpan=nroPartes;
					row.appendChild(tdValor);
					tbody.appendChild(row);
					nroPartes--;
				}
			}
			
			function paintListaInsumosColumnaSerie(indexRow, oTable, color) 
			{
				indexRow = indexRow / 1;
				oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(1).style.color = color;			
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
			
			function deleteRowColors(objTable, indexRow, objIndexSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var numFilas = objTable.getElementsByTagName("tr").length;
				objTable.deleteRow(indexRow);
				if (numFilas > 1) 
				{
					if (objIndexSelected.value > 0) 
					{
						objIndexSelected.value = objIndexSelected.value - 1;
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
					} else 
						selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
										colorSelected, bgColorSelected,
										colorNoSelected, bgColorNoSelected);
				};
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPerspectivaForm.nombre" />

		<%-- Selectores de la Forma --%>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/perspectivas/guardarPerspectiva" focus="nombre">

			<html:hidden property="perspectivaId" />
			<html:hidden property="padreId" />
			<html:hidden property="responsableId" />
			
			<input type="hidden" name="perspectivaAsociadaRutaCompleta">			
			<input type="hidden" name="perspectivaAsociadaId">			
			<input type="hidden" name="perspectivaAsociadaNombre">
			
			<html:hidden property="insumosAsociados" />

			<vgcinterfaz:contenedorForma width="580px" height="425px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20" scrolling="none">
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
									
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarPerspectivaForm" property="perspectivaId" value="0">
						<logic:equal name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
							<vgcutil:message key="jsp.editarperspectiva.titulo.nuevo" /> - <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
						</logic:equal>
						<logic:notEqual name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
							<vgcutil:message key="jsp.editarperspectiva.titulo.nuevo" /> - <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" />
						</logic:notEqual>
					</logic:equal>
					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarPerspectivaForm" property="perspectivaId" value="0">
						<vgcutil:message key="jsp.editarperspectiva.titulo.modificar" />
					</logic:notEqual>

				</vgcinterfaz:contenedorFormaTitulo>

				<!-- Paneles -->
				<vgcinterfaz:contenedorPaneles height="320px" width="530px" nombre="editarPerspectiva">

					<!-- Panel: Datos Basicos -->
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="datosBasicos">

						<!-- Título del Panel -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos" />
						</vgcinterfaz:panelContenedorTitulo>

						<!-- Cuerpo del Panel -->
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" onkeypress="ejecutarPorDefecto(event)">
							<tr>
								<%-- Nombre --%>
								<td align="right"><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.nombre" /></td>
								<td><%-- Para el caso de Nuevo --%> <logic:equal name="editarPerspectivaForm" property="perspectivaId" value="0">
									<logic:equal name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
										<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="250" styleClass="cuadroTexto"/>
									</logic:equal>
									<logic:notEqual name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
										<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="250" styleClass="cuadroTexto"/>
									</logic:notEqual>
								</logic:equal> <%-- Para el caso de Modificar --%> <logic:notEqual name="editarPerspectivaForm" property="perspectivaId" value="0">
									<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
										<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="250" styleClass="cuadroTexto"/>
									</logic:equal>
									<logic:notEqual name="editarPerspectivaForm" property="tipo" value="0">
										<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombre" size="56" maxlength="250" styleClass="cuadroTexto"/>
									</logic:notEqual>
								</logic:notEqual></td>
							</tr>
							<tr>
								<%-- Descripcion --%>
								<td align="right"><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.descripcion" /></td>
								<td><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" rows="15" cols="55" property="descripcion" styleClass="cuadroTexto"/></td>
							</tr>

							<tr>
								<%-- Fecuencia de Evaluacion --%>
								<td align="right"><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.frecuenciadeevaluacion" /></td>
								<td><html:select disabled="<%= Boolean.parseBoolean(bloquearForma) %>" name="editarPerspectivaForm" property="frecuencia" styleClass="cuadroTexto">
									<html:options collection="frecuencias" property="frecuenciaId" labelProperty="nombre"></html:options>
								</html:select></td>
							</tr>

							<bean:define id="tipoCalculoPerspectivaAutomatico">
								<bean:write name="editarPerspectivaForm" property="tipoCalculoPerspectivaAutomatico" />
							</bean:define>
							<%-- Para el caso de Nuevo --%>
							<logic:equal name="editarPerspectivaForm" property="perspectivaId" value="0">
								<logic:equal name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
									<tr>
										<%-- Calculo Automatico --%>
										<td colspan="2">
											<html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="tipoCalculo" value="<%= tipoCalculoPerspectivaAutomatico %>"></html:checkbox>
											<vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.tipocalculo1" /><b><bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" /></b><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.tipocalculo2" />
										</td>
									</tr>
								</logic:equal>
								<logic:notEqual name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.tipo" value="0">
									<tr>
										<%-- Responsable --%>
										<td align="right"><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.responsable" /></td>
										<td>
											<html:text disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreResponsable" size="45" readonly="true" maxlength="250" styleClass="cuadroTexto"/>
											<logic:notEqual name="editarPerspectivaForm" property="bloqueado" value="true">
												&nbsp; <img style="cursor: pointer" onclick="seleccionarResponsable()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Responsable'> <img style="cursor: pointer" onclick="limpiarResponsable()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
											</logic:notEqual>
										</td>
									</tr>
								</logic:notEqual>
							</logic:equal>

							<%-- Para el caso de Modificar --%>
							<logic:notEqual name="editarPerspectivaForm" property="perspectivaId" value="0">
								<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
									<tr>
										<%-- Calculo Automatico --%>
										<td colspan="2"><html:checkbox disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="tipoCalculo" value="<%= tipoCalculoPerspectivaAutomatico %>"></html:checkbox><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.tipocalculo1" /> <b> <bean:write name="gestionarPerspectivasForm" property="elementoPlantillaPlanes.nombre" /></b> <vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.tipocalculo2" /></td>
									</tr>
								</logic:equal>
								<logic:notEqual name="editarPerspectivaForm" property="tipo" value="0">
									<tr>
										<%-- Responsable --%>
										<td align="right"><vgcutil:message key="jsp.editarperspectiva.pestana.datosbasicos.ficha.responsable" /></td>
										<td>
											<html:text style="width: 350px" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="nombreResponsable" size="45" readonly="true" maxlength="250" styleClass="cuadroTexto"/>
											<logic:notEqual name="editarPerspectivaForm" property="bloqueado" value="true">
												&nbsp; <img style="cursor: pointer" onclick="seleccionarResponsable()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Responsable'> <img style="cursor: pointer" onclick="limpiarResponsable()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
											</logic:notEqual>
										</td>
									</tr>
								</logic:notEqual>
							</logic:notEqual>
						</table>
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Relaciones -->
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="relaciones">

						<!-- Título del Panel -->
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editarperspectiva.pestana.relaciones" />
						</vgcinterfaz:panelContenedorTitulo>

						<!-- Cuerpo del Panel -->
						<table class="bordeFichaDatos" cellpadding="0" cellspacing="0" align="center" height="100%" border="0" onkeypress="ejecutarPorDefecto(event)">
							<tr>
								<td align="center">
									<bean:define scope="page" id="anchoListadoObjetivosRelacion" value="250px"></bean:define>
									<table class="contenedorBotonesSeleccion" width="500px" height="270px">
										<tr>
											<td>
												<input id="insumoSeleccionado" type="hidden" name="insumoSeleccionado" />
												<logic:notEqual name="editarPerspectivaForm" property="bloqueado" value="true"> 
													<input type="button" style="width:<%= anchoListadoObjetivosRelacion %>" class="cuadroTexto" value="<vgcutil:message key="jsp.editarperspectiva.pestana.relaciones.agregar" />" onclick="seleccionarObjetivos();">
												</logic:notEqual>
												<logic:equal name="editarPerspectivaForm" property="bloqueado" value="true">
													<input type="button" disabled style="width:<%= anchoListadoObjetivosRelacion %>" class="cuadroTexto" value="<vgcutil:message key="jsp.editarperspectiva.pestana.relaciones.agregar" />" onclick="seleccionarObjetivos();">
												</logic:equal>
											</td>
											<td>
												<logic:notEqual name="editarPerspectivaForm" property="bloqueado" value="true">
													<input type="button" style="width:<%= anchoListadoObjetivosRelacion %>" class="cuadroTexto" value="<vgcutil:message key="jsp.editarperspectiva.pestana.relaciones.remover" />" onclick="removerObjetivos();">
												</logic:notEqual>
												<logic:equal name="editarPerspectivaForm" property="bloqueado" value="true">
													<input type="button" disabled style="width:<%= anchoListadoObjetivosRelacion %>" class="cuadroTexto" value="<vgcutil:message key="jsp.editarperspectiva.pestana.relaciones.remover" />" onclick="removerObjetivos();">
												</logic:equal>
											</td>
										</tr>
										<tr height="100%">
											<td colspan="3">
												<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoListadoObjetivosRelacion %>" splitterId="splitInsumos" overflowPanelDerecho="auto" overflowPanelIzquierdo="auto">
													<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitInsumos">
														<table id="tablaListaInsumos">
															<tbody class="cuadroTexto">
															</tbody>
														</table>
													</vgcinterfaz:splitterHorizontalPanelIzquierdo>
													<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitInsumos">
														<table id="tablaRutaCompletaInsumos">
															<tbody class="cuadroTexto">
															</tbody>
														</table>
													</vgcinterfaz:splitterHorizontalPanelDerecho>
												</vgcinterfaz:splitterHorizontal>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>

				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarPerspectivaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<%-- Para el caso de Nuevo y Modificar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			agregarObjetivos();
		</script>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarPerspectivaForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>
</tiles:insert>
