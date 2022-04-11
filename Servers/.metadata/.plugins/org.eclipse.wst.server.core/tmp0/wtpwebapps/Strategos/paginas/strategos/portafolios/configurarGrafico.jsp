<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Gustavo Chaparro (01/09/2013) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

		<%-- Título --%>
	<tiles:put name="title" type="String">..:: 
		<logic:equal name="graficoForm" property="tipoGrafico" value="0">
			<vgcutil:message key="jsp.portafolio.configurar.grafico.tipo.estatus.titulo" />
		</logic:equal>
		<logic:equal name="graficoForm" property="tipoGrafico" value="1">
			<vgcutil:message key="jsp.portafolio.configurar.grafico.tipo.porcentaje.titulo" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			function cancelar() 
			{
				window.close();						
			}
			
			function eventoCambioFrecuencia()
			{
				document.getElementById("trFechaLabel").style.display = "none";
				document.getElementById("trFecha").style.display = "none";
				document.getElementById("trPeriodoLabel").style.display = "none";
				document.getElementById("trPeriodo").style.display = "none";
				document.getElementById("tdPeriodoLabelSelect").style.display = "";
				document.getElementById("tdPeriodoSelect").style.display = "";
				
				switch (document.graficoForm.frecuencia.value) 
				{
					case "0":
						document.getElementById("trFechaLabel").style.display = "";
						document.getElementById("trFecha").style.display = "";
						document.getElementById("tdPeriodoLabelSelect").style.display = "none";
						document.getElementById("tdPeriodoSelect").style.display = "none";
						break;		
					case "1":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
						break;		
					case "2":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
						break;		
					case "3":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
						break;		
					case "4":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
						break;		
					case "5":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
						break;		
					case "6":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
						break;		
					case "7":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
						break;		
					case "8":
						document.getElementById("trFechaLabel").style.display = "none";
						document.getElementById("trFecha").style.display = "none";
						
						document.getElementById("trPeriodoLabel").style.display = "";
						document.getElementById("trPeriodo").style.display = "";

						var periodo = document.getElementById("tdPeriodo");
						periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';
						
						document.getElementById("tdPeriodoLabelSelect").style.display = "none";
						document.getElementById("tdPeriodoSelect").style.display = "none";
						break;		
				}
				
				agregarPeriodos(document.graficoForm.frecuencia.value);
			}
			
			function agregarPeriodos(frecuencia)
			{
				var select = document.getElementById("selectPeriodo");
				var max = select.options.length;
			 	if (max > 0)
			 		select.options.length=0;

			    for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
			    	addElementToSelect(select, i+1, i+1);

			    if (document.graficoForm.periodo.value != "")
			    	select.selectedIndex = document.graficoForm.periodo.value -1;
			    else
			    	select.selectedIndex = 0;
			}
			
			function numeroPeriodoMaximo(frecuencia)
			{
			    switch (frecuencia) 
			    {
		    		case "0":
		      			return 365;
		    		case "1":
		      			return 52;
	    			case "2":
		      			return 24;
				    case "3":
				      return 12;
				    case "4":
				      return 6;
				    case "5":
				      return 4;
				    case "6":
				      return 3;
				    case "7":
				      return 2;
				    case "8":
				      return 1;
		    	}
		    	return -1;
			}
			
			function addElementToSelect(combo, texto, valor)
			{
			    var idxElemento = combo.options.length; //Numero de elementos de la combo si esta vacio es 0
			    
			    //Este indice será el del nuevo elemento
			    combo.options[idxElemento] = new Option();
			    combo.options[idxElemento].text = texto; //Este es el texto que verás en la combo
			    combo.options[idxElemento].value = valor; //Este es el valor que se enviará cuando hagas un submit del
		   	}
			
			function generarGrafico()
			{
				document.graficoForm.periodo.value = document.getElementById("selectPeriodo").value;
				
				var xml = "?objetoId=" + document.graficoForm.objetoId.value;
				xml = xml + "&objetoNombre=" + document.graficoForm.objetoNombre.value;
				xml = xml + "&source=" + document.graficoForm.source.value;
				xml = xml + "&ano=" + document.graficoForm.ano.value;
				xml = xml + "&periodo=" + document.graficoForm.periodo.value;
				xml = xml + "&tipoObjeto=" + document.graficoForm.tipoObjeto.value;
				xml = xml + "&tipoGrafico=" + document.graficoForm.tipoGrafico.value;
				xml = xml + "&organizacionId=" + document.graficoForm.organizacionId.value;
				xml = xml + "&tipo=" + document.graficoForm.tipo.value;
				xml = xml + "&titulo=" + CodificarString(document.graficoForm.titulo.value, true);
				xml = xml + "&tituloEjeY=" + document.graficoForm.tituloEjeY.value;
				xml = xml + "&tituloEjeX=" + document.graficoForm.tituloEjeX.value;
				xml = xml + "&graficoNombre=" + CodificarString(document.graficoForm.graficoNombre.value, true);
				if (document.graficoForm.verTituloImprimir.value == "true")
					xml = xml + "&verTituloImprimir=" + "1";
				else
					xml = xml + "&verTituloImprimir=" + "0";
				if (document.graficoForm.ajustarEscala.value == "true")
					xml = xml + "&ajustarEscala=" + "1";
				else
					xml = xml + "&ajustarEscala=" + "0";
				xml = xml + "&fecha=" + document.graficoForm.fecha.value;
				xml = xml + "&frecuencia=" + document.graficoForm.frecuencia.value;
				
				cancelar();
				window.opener.onGraficarIniciativa(xml);
			}
			
			function init()
			{
				eventoCambioFrecuencia();
			}
			
			function CodificarString(value, codificar)
			{
				var valor = value;

				if (codificar)
					valor = valor.replace("%", "[[por]]");
				
				return valor;
			}
		
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativa/grafico/configurar">

			<html:hidden property="objetoId" />
			<html:hidden property="objetoNombre" />
			<html:hidden property="source" />
			<html:hidden property="periodo" />
			<html:hidden property="tipoObjeto" />
			<html:hidden property="tipoGrafico" />
			<html:hidden property="organizacionId" />
			<html:hidden property="tipo" />
			<html:hidden property="titulo" />
			<html:hidden property="tituloEjeY" />
			<html:hidden property="tituloEjeX" />
			<html:hidden property="graficoNombre" />
			<html:hidden property="verTituloImprimir" />
			<html:hidden property="ajustarEscala" />
			
			<vgcinterfaz:contenedorForma width="430px" height="300px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<logic:equal name="graficoForm" property="tipoGrafico" value="0">
						<vgcutil:message key="jsp.portafolio.configurar.grafico.tipo.estatus.titulo" />
					</logic:equal>
					<logic:equal name="graficoForm" property="tipoGrafico" value="1">
						<vgcutil:message key="jsp.portafolio.configurar.grafico.tipo.porcentaje.titulo" />
					</logic:equal>
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">

					<!-- Organizacion Seleccionada-->
					<tr>
						<td align="left"><vgcutil:message key="jsp.portafolio.configurar.grafico.plantilla.organizacion" /> : </td>
						<td><b><bean:write name="graficoForm" property="organizacionNombre" /></b></td>
					</tr>
					<!-- Plan Seleccionado -->
					<logic:equal name="graficoForm" property="tipoObjeto" value="5">
						<tr>
							<td align="left"><vgcutil:message key="jsp.portafolio.configurar.grafico.plantilla.plan" /> : </td>
							<td><b><bean:write name="graficoForm" property="objetoNombre" /></b></td> 
						</tr>
					</logic:equal>
					<tr>
						<td colspan="2"><hr width="100%"></td>
					</tr>
					
					<!-- Encabezado selector de fechas -->
					<tr>
						<td align="left" colspan="2"><b><vgcutil:message key="jsp.portafolio.configurar.grafico.periodo.titulo" /></b></td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					
					<tr>
						<td align="left" colspan="2"><b><vgcutil:message key="jsp.portafolio.configurar.grafico.periodo.frecuencia" /></b></td>
					<tr>
						<td align="left" colspan="2">
							<html:select property="frecuencia" styleClass="cuadroTexto" size="1" onchange="eventoCambioFrecuencia();">
								<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
							</html:select>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr id="trPeriodoLabel">
						<td align="left"><b><vgcutil:message key="jsp.portafolio.configurar.grafico.periodo.ano" /></b></td>
						<td align="left" id="tdPeriodoLabelSelect"><b><span id="tdPeriodo"></span></b></td>
					</tr>
												
					<tr id="trPeriodo">
						<td>
							<bean:define id="anoInicial" toScope="page"><bean:write name="graficoForm" property="ano" /></bean:define>
							<html:select property="ano" value="<%=anoInicial%>" styleClass="cuadroTexto">
								<%
									for (int i = 1900; i <= 2050; i++) {
								%>
								<html:option value="<%=String.valueOf(i)%>">
									<%=i%>
								</html:option>
								<%
									}
								%>
							</html:select>
						</td>
						<td id="tdPeriodoSelect">
							<select id="selectPeriodo" class="cuadroTexto">
							</select>
						</td>
					</tr>

					<tr id="trFechaLabel">
						<td align="left" colspan="2"><b><vgcutil:message key="jsp.portafolio.configurar.grafico.periodo.dia" /></b></td>
					</tr>
					
					<tr id="trFecha">
						<td id="tdFecha">
							<html:text property="fecha" size="10" maxlength="10" styleClass="cuadroTexto" />
							<img style="cursor: pointer" onclick="seleccionarFecha();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
						</td>
					</tr>
				</table>
				
				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarGrafico();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<%-- Cancelar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			init();
		</script>
	</tiles:put>
</tiles:insert>