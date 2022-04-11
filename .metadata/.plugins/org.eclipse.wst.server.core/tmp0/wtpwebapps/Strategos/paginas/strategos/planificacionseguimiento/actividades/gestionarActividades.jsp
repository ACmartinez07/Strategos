<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (29/10/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<bean:define id="tituloIniciativaPlural">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>
	<bean:define id="tituloIniciativaSingular">
		<bean:write scope="session" name="activarIniciativa" property="nombreSingular" />
	</bean:define>
	
	<tiles:put name="title" type="String">
		<logic:notEmpty name="gestionarActividadesForm" property="nombreActividadPlural" scope="session">..::  <vgcutil:message key="comunes.strategos.gestionar" />
			<bean:write name="gestionarActividadesForm" property="nombreActividadPlural" />
		</logic:notEmpty>
		<logic:empty name="gestionarActividadesForm" property="nombreActividadPlural" scope="session">
			<vgcutil:message key="jsp.gestionariniciativas.titulo" arg0="<%= tituloIniciativaPlural %>" />
		</logic:empty>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">	

			var actividades = new Array(<bean:write name="paginaActividades" property="total"/>);
			<logic:iterate id="actividad" name="paginaActividades" property="lista" indexId="indexActividades">
				actividades[<bean:write name="indexActividades"/>] = new Array(2);
				actividades[<bean:write name="indexActividades"/>][0] = '<bean:write name="actividad" property="actividadId"/>';
				actividades[<bean:write name="indexActividades"/>][1] = '<bean:write name="actividad" property="padreId"/>';
			</logic:iterate>
		
			function actualizar()
			{
				var url = "";
				if (document.gestionarActividadesForm.seleccionados.value != "")
					url = url + "?seleccionados=" + document.gestionarActividadesForm.seleccionados.value;
				
				window.location.href='<html:rewrite action="/planificacionseguimiento/actividades/gestionarActividades"/>' + url;
			}
			
			function nuevo() 
			{
				if (document.gestionarActividadesForm.seleccionados.value == "")
				{
					var parametros = '?seleccionados=' + document.gestionarActividadesForm.seleccionados.value;
					parametros = parametros + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />';
					parametros = parametros + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />';
					parametros = parametros + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />';
					parametros = parametros + '&desdeInstrumento=<bean:write name="gestionarActividadesForm" property="desdeInstrumento" />';
					parametros = parametros + '&funcion=Inicializar';
				
					abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/crearActividad" />' + parametros, "Actividad", 695, 425);
				}
				else if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					var parametros = '?seleccionados=' + document.gestionarActividadesForm.seleccionados.value;
					parametros = parametros + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />';
					parametros = parametros + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />';
					parametros = parametros + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />';
					parametros = parametros + '&desdeInstrumento=<bean:write name="gestionarActividadesForm" property="desdeInstrumento" />';
					parametros = parametros + '&funcion=Inicializar';
				
					abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/crearActividad" />' + parametros, "Actividad", 695, 425);
				}
			}
			
			function modificar(actividadId) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0))
					{
						var parametros = '?actividadId=' + actividadId;
						parametros = parametros + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />';
						parametros = parametros + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />';
						parametros = parametros + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />';
						parametros = parametros + '&desdeInstrumento=<bean:write name="gestionarActividadesForm" property="desdeInstrumento" />';
						parametros = parametros + '&funcion=Inicializar';

						<logic:equal name="gestionarActividadesForm" property="editarForma" value="true">
							abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/modificarActividad" />' + parametros, "Actividad", 695, 425);
						</logic:equal>
						<logic:notEqual name="gestionarActividadesForm" property="editarForma" value="true">
							<logic:equal name="gestionarActividadesForm" property="verForma" value="true">
								abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/verActividad" />' + parametros, "Actividad", 695, 425);
							</logic:equal>
						</logic:notEqual>
					}
				}
			}
			
			function eliminar(actividadId) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0)) 
					{
						var eliminar = confirm('<vgcutil:message key="jsp.gestionaractividades.eliminaractividades.confirmar" />');
						if (eliminar)
						{
							activarBloqueoEspera();
							window.location.href='<html:rewrite action="/planificacionseguimiento/actividades/eliminarActividad"/>?actividadId=' + actividadId + '&ts=<%= (new Date()).getTime() %>';
						}
					}
				}
			}
			
			function propiedades(actividadId) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0)) 
						abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/propiedadesActividad" />?actividadId=' + actividadId, "Actividad", 490, 505);
				}
			}
			
			function reporte() 
			{
				abrirReporte('<html:rewrite action="/planificacionseguimiento/actividades/generarReporteActividades.action"/>?atributoOrden=' + gestionarActividadesForm.atributoOrden.value + '&tipoOrden=' + gestionarActividadesForm.tipoOrden.value + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />' + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />' + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />', 'reporte');
			}
			
			function gantt(mostrarGantt) 
			{
				window.document.gestionarActividadesForm.action = '<html:rewrite action="/planificacionseguimiento/actividades/gestionarActividades" />?mostrarGantt=' + mostrarGantt;
				window.document.gestionarActividadesForm.submit();
			}
			
			function identar(identarFila) 
			{
				var forma = document.gestionarActividadesForm;
			
				if (verificarElementoUnicoSeleccionMultiple(forma.seleccionados))
				{
					if ((forma.seleccionados.value != null) && (forma.seleccionados.value != '')) 
					{
						var actividadId = document.gestionarActividadesForm.seleccionados.value;
						window.document.gestionarActividadesForm.action = '<html:rewrite action="/planificacionseguimiento/actividades/gestionarActividades" />?seleccionados=' + actividadId + '&identar=' + identarFila + '&ancla=#ancla';
						window.document.gestionarActividadesForm.submit();
					}
				}
			}
			
			function ascender(ascenderFila) 
			{
				var forma = document.gestionarActividadesForm;
			
				if (verificarElementoUnicoSeleccionMultiple(forma.seleccionados))
				{
					if ((forma.seleccionados.value != null) && (forma.seleccionados.value != '')) 
					{
						var actividadId = document.gestionarActividadesForm.seleccionados.value;
						window.document.gestionarActividadesForm.action = '<html:rewrite action="/planificacionseguimiento/actividades/gestionarActividades" />?seleccionados=' + actividadId + '&ascender=' + ascenderFila + '&ancla=#ancla';
						window.document.gestionarActividadesForm.submit();
					}
				}
			}
			
			function limpiarFiltros() 
			{
				gestionarActividadesForm.filtroNombre.value = "";
				gestionarActividadesForm.submit();
			}
			
			function buscar() 
			{
				gestionarActividadesForm.submit();
			}
			
			function zoomInOut(zoom_In_Out) 
			{
				var forma = document.gestionarActividadesForm;
				forma.zoom.value = zoom_In_Out;
				forma.action = '<html:rewrite action="/planificacionseguimiento/actividades/gestionarActividades"/>';
				forma.submit();
			}
			
			function resaltar(fila, estilo) 
			{
				var visorActividades = getEl('visorActividades');
				var tablaGantt = getEl('tablaGantt');
				if (visorActividades != null) 
					visorActividades.rows[fila.rowIndex].className=estilo;
				if (tablaGantt != null) 
					tablaGantt.rows[fila.rowIndex].className=estilo;
			}
			
			function eventoClickFilaCheckPadre(fila)
			{
				if ((fila == null) || (fila == '') || (fila == 0)) 
					return ;
				var filaInicial = 1;
				<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
					filaInicial = 0;
				</logic:equal>
				var visorActividades = getEl('visorActividades');
				var estiloSeleccionado = 'cuerpoListViewFilaSeleccionada';
				var estiloFuera = 'mouseFueraCuerpoListView';
				
				var actividadId = visorActividades.rows[fila.rowIndex].id;
				var padreId = "";
				for (var i = 0; i < actividades.length; i++) 
				{
					if (actividades[i][0] == actividadId) 
					{
						padreId = actividades[i][1];
						break;
					}
				}
				
				var seleccionados = document.gestionarActividadesForm.seleccionados.value;
				var limpiarSeleccionados = false;
				if (seleccionados != null || seleccionados != "")
				{
					var seleccion = seleccionados.split(",");
					for (var i = 0; i < seleccion.length; i++)
					{
						actividadId = seleccion[i];
						for (var i = 0; i < actividades.length; i++) 
						{
							if (actividades[i][0] == actividadId) 
							{
								if (padreId.length == 0 && actividades[i][1].length != 0)
									limpiarSeleccionados = true;
								else if (padreId.length != 0 && actividades[i][1].length == 0)
									limpiarSeleccionados = true;
								else if (padreId != actividades[i][1])
									limpiarSeleccionados = true;
								break;
							}
						}
					}
				}
				
				if (limpiarSeleccionados)
					document.gestionarActividadesForm.seleccionados.value = visorActividades.rows[fila.rowIndex].id;
				if (visorActividades != null) 
				{
					for (var i = filaInicial; i < visorActividades.rows.length; i++) 
					{
						var filaAux = visorActividades.rows[i];
						if (limpiarSeleccionados)
							filaAux.className=estiloFuera;
					}
					if (limpiarSeleccionados)
						visorActividades.rows[fila.rowIndex].className=estiloSeleccionado;
				}
			}
			
			function eventoClickFilaCombinada(fila) 
			{
				if ((fila == null) || (fila == '') || (fila == 0)) 
					return ;
				var filaInicial = 1;
				<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
					filaInicial = 0;
				</logic:equal>
				var visorActividades = getEl('visorActividades');
				var tablaGantt = getEl('tablaGantt');
				var estiloSeleccionado = 'cuerpoListViewFilaSeleccionada';
				var estiloFuera = 'mouseFueraCuerpoListView';
				document.gestionarActividadesForm.seleccionados.value = visorActividades.rows[fila.rowIndex].id;
				if (visorActividades != null) 
				{
					for (var i = filaInicial; i < visorActividades.rows.length; i++) 
					{
						var filaAux = visorActividades.rows[i];
						filaAux.className=estiloFuera;
					}
					visorActividades.rows[fila.rowIndex].className=estiloSeleccionado;
				}
				if (tablaGantt != null) 
				{
					for (var i = filaInicial; i < tablaGantt.rows.length; i++) 
					{
						var filaAux = tablaGantt.rows[i];
						filaAux.className=estiloFuera;
					}
					tablaGantt.rows[fila.rowIndex].className=estiloSeleccionado;
				}
			}
			
			function eventoMouseEncimaFilaCombinada(fila) 
			{
				var visorActividades = getEl('visorActividades');
				var tablaGantt = getEl('tablaGantt');
				var filaSeleccionada = (document.gestionarActividadesForm.seleccionados.value == visorActividades.rows[fila.rowIndex].id);
				var estiloSeleccionado = 'cuerpoListViewFilaSeleccionada';
				var estiloEncima = 'mouseEncimaCuerpoListView';
				if (visorActividades != null) 
				{
					if (filaSeleccionada) 
						visorActividades.rows[fila.rowIndex].className=estiloSeleccionado;
					else 
						visorActividades.rows[fila.rowIndex].className=estiloEncima;
				}
				if (tablaGantt != null) 
				{
					if (filaSeleccionada) 
						tablaGantt.rows[fila.rowIndex].className=estiloSeleccionado;
					else 
						tablaGantt.rows[fila.rowIndex].className=estiloEncima;
				}
			}
			
			function eventoMouseFueraFilaCombinada(fila) 
			{
				var visorActividades = getEl('visorActividades');
				var tablaGantt = getEl('tablaGantt');
				var filaSeleccionada = (document.gestionarActividadesForm.seleccionados.value == visorActividades.rows[fila.rowIndex].id);
				var estiloSeleccionado = 'cuerpoListViewFilaSeleccionada';
				var estiloFuera = 'mouseFueraCuerpoListView';
				if (visorActividades != null) 
				{
					if (filaSeleccionada) 
						visorActividades.rows[fila.rowIndex].className=estiloSeleccionado;
					else 
						visorActividades.rows[fila.rowIndex].className=estiloFuera;
				}
				if (tablaGantt != null) 
				{
					if (filaSeleccionada) 
						tablaGantt.rows[fila.rowIndex].className=estiloSeleccionado;
					else 
						tablaGantt.rows[fila.rowIndex].className=estiloFuera;
				}
			}
			
			function asignarPesosActividadedes(actividadId)
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0))
					{
		    			var nombreForma = '?nombreForma=' + 'gestionarActividadesForm';
		    			var funcionCierre = '&funcionCierre=';
		    			var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		    			var url = '&actividadId=' + actividadId + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />' + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />' + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />';

						abrirVentanaModal('<html:rewrite action="/planificacionseguimiento/actividades/asignarPesosActividad" />' + nombreForma + funcionCierre + nombreCampoOculto + url, 'asignarPesosActividad', '730', '600');
					}
				}
			}
			
			function onAsignarPesosActividadedes()
			{
				actualizar();					
			}

			function editarMediciones(actividadId, tipo) 
			{
				if (tipo == undefined)
					tipo = 0;
				
				if ((actividadId != null) && (actividadId != '') && (actividadId != 0))
				{
					var url = '?actividadId=' + actividadId + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />' + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />' + '&source=3';
					if (tipo == 1)
					{
						url = url + "&funcion=Ejecutar&tipo=1";
			    		window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>' + url;
					}
		    		else
	    			{
		    			document.gestionarActividadesForm.respuesta.value = "";
		    			url = url + '&tipo=0';
		    			var nombreForma = '&nombreForma=' + 'gestionarActividadesForm';
		    			var funcionCierre = '&funcionCierre=' + 'onEditarMediciones()';
		    			var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';

		    			abrirVentanaModal('<html:rewrite action="/mediciones/configurarEdicionMediciones" />' + url + nombreForma + funcionCierre + nombreCampoOculto, 'cargarMediciones', '470', '290');
	    			}
				}
			}
			
			function onEditarMediciones()
			{
				var status = document.gestionarActividadesForm.respuesta.value;
				if ((status != null) && (status != '') && (status == "Sucess") && (document.gestionarActividadesForm.seleccionados.value != null && document.gestionarActividadesForm.seleccionados.value != ''))
				{
					var url = '?actividadId=' + document.gestionarActividadesForm.seleccionados.value + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />' + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />' + "&source=3" + "&funcion=Ejecutar&tipo=0";
			    	window.location.href='<html:rewrite action="/mediciones/editarMediciones"/>' + url;
				}
			}
			
			function asociarIniciativa()
			{
				var queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />';
				abrirSelectorIniciativas('gestionarActividadesForm', 'nombreObjeto', 'objetoId', null, 'objetoPlanId', 'onAsociacionIniciativa()', queryStringFiltros);
			}
			
			function onAsociacionIniciativa() 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					activarBloqueoEspera();
					window.location.href='<html:rewrite action="/planificacionseguimiento/actividades/asociarIniciativa" />?seleccionados=' + document.gestionarActividadesForm.seleccionados.value + '&proyectoId=<bean:write name="gestionarActividadesForm" property="proyectoId" />' + '&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />' + '&organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />&iniciativaAsociadaId=' + document.gestionarActividadesForm.objetoId.value + '&className=Iniciativa&iniciativaAsociadaNombre=' + document.gestionarActividadesForm.nombreObjeto.value + '&planAsociadaId=' + document.gestionarActividadesForm.objetoPlanId.value;
				}
			}

			function desasociarIniciativa(actividadId) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0)) 
					{
						var eliminar = confirm('<vgcutil:message key="jsp.gestionaractividades.desasociar.confirmar" />');
						if (eliminar)
						{
							activarBloqueoEspera();
							window.location.href='<html:rewrite action="/planificacionseguimiento/actividades/eliminarActividad"/>?actividadId=' + actividadId + '&ts=<%= (new Date()).getTime() %>';
						}
					}
				}
			}
			
			function asociarActividad()
			{
			}

			function desasociarActividad(actividadId) 
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
				{
					if ((actividadId != null) && (actividadId != '') && (actividadId != 0)) 
						var eliminar = confirm('<vgcutil:message key="jsp.gestionaractividades.desasociar.actividades.confirmar" />');
				}
			}
			
			function protegerLiberarMediciones(actividadId, proteger)
			{
				
					
						if (proteger == undefined)
							proteger = true;
						
						var nombreForma = '?nombreForma=' + 'gestionarActividadesForm';
						var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
						var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
						var parametros = '&proteger=' + proteger;
						parametros = parametros + '&origen=5';
						parametros = parametros + '&actividadId=' + document.gestionarActividadesForm.seleccionados.value;
						
						abrirVentanaModal('<html:rewrite action="/mediciones/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarMediciones', '610', '435');
				
				
			}
		    
		    function onProtegerLiberarMediciones()
		    {
		    }
		    
			function enviarEmail()
			{
				if (verificarElementoUnicoSeleccionMultiple(document.gestionarActividadesForm.seleccionados))
					ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/planificacionseguimiento/actividades/enviarEmail" />?objetoId=' + document.gestionarActividadesForm.seleccionados.value + "&tipoObjeto=Actividad", document.gestionarActividadesForm.respuesta, 'onEnviarEmail()');
			}

			function onEnviarEmail()
			{
				var respuesta = document.gestionarActividadesForm.respuesta.value.split("|");
				if (respuesta.length > 0)
				{
					if (respuesta[0] == "true")
					{
						if (respuesta[1] == "true")
						{
							var to = respuesta[2]; 
							var cc = respuesta[3];
							var asunto = respuesta[4];
							
							SendEmail(to, cc, undefined, asunto, undefined);
						}
						else
							alert('<vgcutil:message key="jsp.email.reponsable.correo.empty" />');
					}
					else
						alert('<vgcutil:message key="jsp.email.objeto.reponsable.empty" />');
				}
				else
					alert('<vgcutil:message key="jsp.email.objeto.invalido" />');
			}
			
			function showRelacion(objetoId, className)
			{
				if (typeof to != "undefined")
					className = "";
				if (className == "Iniciativa")
				{
					var url = '?iniciativaId=' + objetoId + '&accion=refrescar';
					window.location.href='<html:rewrite action="/iniciativas/visualizarIniciativasRelacionadas" />' + url;
				}
			}
			
			function calcularIndicadores() 
			{
				var url = '?organizacionId=<bean:write name="gestionarActividadesForm" property="organizacionId" />&desdeIniciativa=true&claseId=<bean:write name="gestionarActividadesForm" property="iniciativaClaseId"/>&iniciativaId=<bean:write name="gestionarActividadesForm" property="iniciativaId" />';

				var _object = new Calculo();
				_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
		    }
			
			function configurarVisorActividades() 
			{
				configurarVisorLista('com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase', 'visorActividades', '<vgcutil:message key="jsp.seleccionariniciativas.panel.actividades.titulo" />');
			}

			function importarMediciones()
			{
				var nombreForma = '?nombreForma=' + 'gestionarActividadesForm';
				var nombreCampoOculto = '&nombreCampoOculto=' + 'objetoId';
				var funcionCierre = '&funcionCierre=' + 'onImportarMediciones()';

				abrirVentanaModal('<html:rewrite action="/indicadores/importar" />' + nombreForma + nombreCampoOculto + funcionCierre, 'importarMediciones', '590', '410');
			}

		    function onImportarMediciones()
		    {
		    }
		    
		    function moveScroll(object)
		    {
		    	if (object.id == 'marcoActividadesGeneral')
	    		{
		    		var div = document.getElementById('filaGantt');
		    		div.scrollTop = object.scrollTop;
	    		}
		    	else if (object.id == 'filaGantt')
	    		{
		    		var div = document.getElementById('marcoActividadesGeneral');
		    		div.scrollTop = object.scrollTop;
	    		}
		    }
			
		</script>

		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/planificacionseguimiento/actividades/actividadesJs/scrollJs.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/calculos/calculosJs/Calculo.js'/>"></script>

		<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
			<script type="text/javascript">
			</script>
		</logic:equal>

		<style type="text/css">

			.scroll 
			{
				width: 150px;
				height: 50px;
				overflow: hidden;
				border: 1px solid #000000;
			}
			
			.flecha 
			{
				cursor: pointer;
			}
			
			.filaGantt 
			{
				height: 20px;
			}

		</style>

		<%-- Se pasan los valores de los Objetos que estan en "session" o "request" a las propiedades locales --%>
		<bean:define id="totalElementos" name="paginaActividades" property="total" scope="request" toScope="page" />
		<bean:define id="tamanoPagina" name="paginaActividades" property="tamanoPagina" scope="request" toScope="page" />
		<bean:define id="tamanoSetPaginas" name="paginaActividades" property="tamanoSetPaginas" scope="request" toScope="page" />
		<bean:define id="nroPagina" name="paginaActividades" property="nroPagina" scope="request" toScope="page" />

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Representación de la Forma --%>
		<html:form action="/planificacionseguimiento/actividades/gestionarActividades" styleClass="formaHtmlCompleta">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="respuesta" />
			<html:hidden property="desdeInstrumento" />
			<input type="hidden" name="nombreObjeto">
			<input type="hidden" name="objetoId">
			<input type="hidden" name="objetoPlanId">

			<vgcinterfaz:contenedorForma idContenedor="body-actividades" bodyAlign="center" bodyValign="center" scrolling="none">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					<logic:notEmpty name="gestionarActividadesForm" property="nombreActividadPlural" scope="session">
						..::  <vgcutil:message key="comunes.strategos.gestionar" /><bean:write name="gestionarActividadesForm" property="nombreActividadPlural" />
					</logic:notEmpty>
					<logic:empty name="gestionarActividadesForm" property="nombreActividadPlural" scope="session">
						<vgcutil:message key="jsp.gestionariniciativas.titulo" arg0="<%= tituloIniciativaPlural %>" />
					</logic:empty>
					<br>..::  <%= tituloIniciativaSingular %> : <bean:write name="gestionarActividadesForm" property="nombreIniciativa" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
				
					<logic:equal name="gestionarActividadesForm" property="desdeInstrumento" value="true">
						javascript:irAtras(3)
					</logic:equal>
					
					<logic:notEqual name="gestionarActividadesForm" property="desdeInstrumento" value="true">
						javascript:irAtras(2)
					</logic:notEqual>
					
					
									
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>

					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>

						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" permisoId="ACTIVIDAD_PRINT" aplicaOrganizacion="true" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="ACTIVIDAD_ADD" aplicaOrganizacion="true"/>
								</logic:notEqual>
								<logic:equal name="gestionarActividadesForm" property="editarForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar(document.gestionarActividadesForm.seleccionados.value);" />
								</logic:equal>
								<logic:notEqual name="gestionarActividadesForm" property="editarForma" value="true">
									<logic:equal name="gestionarActividadesForm" property="verForma" value="true">
										<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar(document.gestionarActividadesForm.seleccionados.value);" />
									</logic:equal>
								</logic:notEqual>
								<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar(document.gestionarActividadesForm.seleccionados.value);" permisoId="ACTIVIDAD_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
									<vgcinterfaz:botonMenu key="menu.edicion.asociar.iniciativa" onclick="asociarIniciativa();" permisoId="ACTIVIDAD_ASOCIAR" aplicaOrganizacion="true"/>
									<vgcinterfaz:botonMenu key="menu.edicion.desasociar.iniciativa" onclick="desasociarIniciativa(document.gestionarActividadesForm.seleccionados.value);" permisoId="ACTIVIDAD_DESASOCIAR" aplicaOrganizacion="true" agregarSeparador="true" />
									 
									<vgcinterfaz:botonMenu key="menu.edicion.asociar.actividad" onclick="asociarActividad();" permisoId="ACTIVIDAD_ASOCIAR" />
									<vgcinterfaz:botonMenu key="menu.edicion.desasociar.actividad" onclick="desasociarActividad(document.gestionarActividadesForm.seleccionados.value);" permisoId="ACTIVIDAD_DESASOCIAR" aplicaOrganizacion="true" agregarSeparador="true" />
									
								 </logic:notEqual>
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades(document.gestionarActividadesForm.seleccionados.value);" permisoId="ACTIVIDAD" aplicaOrganizacion="true" agregarSeparador="true" />
								<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.asignarpesos" onclick="asignarPesosActividadedes(document.gestionarActividadesForm.seleccionados.value);" permisoId="ACTIVIDAD_PESO" aplicaOrganizacion="true" agregarSeparador="true" />
								</logic:notEqual>
								<vgcinterfaz:botonMenu key="menu.edicion.email" permisoId="ACTIVIDAD_EMAIL" onclick="enviarEmail();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ver --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">
								<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
									<vgcinterfaz:botonMenu icon="/componentes/menu/activo.gif" key="menu.ver.gantt" onclick="gantt(0);" permisoId="ACTIVIDAD" aplicaOrganizacion="true"/>
								</logic:equal>
								<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="false">
									<vgcinterfaz:botonMenu key="menu.ver.gantt" onclick="gantt(1);" permisoId="ACTIVIDAD" aplicaOrganizacion="true"/>
								</logic:equal>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Mediciones --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuMediciones" key="menu.mediciones">
								<vgcinterfaz:menuAnidado key="menu.mediciones.actividad.mediciones" agregarSeparador="true">
									<vgcinterfaz:botonMenu key="menu.mediciones.actividad.mediciones.programado" onclick="editarMediciones(document.gestionarActividadesForm.seleccionados.value, 1);" permisoId="ACTIVIDAD_MEDICION_PROGRAMADO" aplicaOrganizacion="true" />
									<vgcinterfaz:botonMenu key="menu.mediciones.actividad.mediciones.ejecutado" onclick="editarMediciones(document.gestionarActividadesForm.seleccionados.value, 0);" permisoId="ACTIVIDAD_MEDICION_REAL" aplicaOrganizacion="true" />
								</vgcinterfaz:menuAnidado>
								<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
									<vgcinterfaz:botonMenu key="menu.mediciones.importar" onclick="importarMediciones();" permisoId="ACTIVIDAD_IMPORTAR" />
									<vgcinterfaz:botonMenu key="menu.mediciones.calcular" onclick="calcularIndicadores();" permisoId="ACTIVIDAD_CALCULAR" agregarSeparador="true" />
									<vgcinterfaz:menuAnidado key="menu.mediciones.proteccion">
										<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.liberar" onclick="protegerLiberarMediciones(document.gestionarActividadesForm.seleccionados.value, false);" permisoId="ACTIVIDAD_PROTEGER_LIBERAR" aplicaOrganizacion="true" />
										<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.bloquear" onclick="protegerLiberarMediciones(document.gestionarActividadesForm.seleccionados.value, true)" permisoId="ACTIVIDAD_PROTEGER_BLOQUEAR" aplicaOrganizacion="true" />
									</vgcinterfaz:menuAnidado>
								</logic:notEqual>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Herramientas --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();"/>
								<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorActividades();" agregarSeparador="true"/>
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

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraGestionarActividades">
						<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_ADD" aplicaOrganizacion="true" nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.nuevo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar(document.gestionarActividadesForm.seleccionados.value);">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.modificar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_DELETE" aplicaOrganizacion="true" nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar(document.gestionarActividadesForm.seleccionados.value);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.eliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedades(document.gestionarActividadesForm.seleccionados.value);">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.propiedades" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdf" onclick="javascript:reporte();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.archivo.presentacionpreliminar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<logic:notEqual name="gestionarActividadesForm" property="bloqueado" value="true">
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_ASOCIAR" aplicaOrganizacion="true" nombreImagen="asociar" pathImagenes="/componentes/barraHerramientas/" nombre="asociarIndicador" onclick="javascript:asociarIniciativa();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.asociar.iniciativa" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_DESASOCIAR" aplicaOrganizacion="true" nombreImagen="desasociar" pathImagenes="/componentes/barraHerramientas/" nombre="desasociarIndicador" onclick="javascript:desasociarIniciativa();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.desasociar.iniciativa" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							
							<!--  
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_ASOCIAR" aplicaOrganizacion="true" nombreImagen="actividad" pathImagenes="/componentes/barraHerramientas/" nombre="actividad" onclick="javascript:asociarActividad();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.asociar.actividad" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							 
							 -->
							<vgcinterfaz:barraHerramientasSeparador />
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="flechaIzquierda" pathImagenes="/componentes/barraHerramientas/" nombre="noidentar" onclick="javascript:identar(0);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.gestionaractividades.barraherramientas.flechaizquierda" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="flechaDerecha" pathImagenes="/componentes/barraHerramientas/" nombre="identar" onclick="javascript:identar(1);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.gestionaractividades.barraherramientas.flechaderecha" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="flechaArriba" pathImagenes="/componentes/barraHerramientas/" nombre="ascender" onclick="javascript:ascender(1);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.gestionaractividades.barraherramientas.flechaarriba" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="flechaAbajo" pathImagenes="/componentes/barraHerramientas/" nombre="descender" onclick="javascript:ascender(0);">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="jsp.gestionaractividades.barraherramientas.flechaabajo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
								<vgcinterfaz:barraHerramientasSeparador />
								<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="zoomFuera" pathImagenes="/componentes/barraHerramientas/" nombre="zoomout" onclick="javascript:zoomInOut(2);">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="jsp.gestionaractividades.barraherramientas.zoomalejar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
								<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EDIT" aplicaOrganizacion="true" nombreImagen="zoomAdentro" pathImagenes="/componentes/barraHerramientas/" nombre="zoomin" onclick="javascript:zoomInOut(1);">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="jsp.gestionaractividades.barraherramientas.zoomacercar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
							</logic:equal>
						</logic:notEqual>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton permisoId="ACTIVIDAD_EMAIL" nombreImagen="email" pathImagenes="/componentes/barraHerramientas/" nombre="email" onclick="javascript:enviarEmail();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.email" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>

				<html:hidden property="seleccionados" />

				<%-- Gráfico de Gantt --%>
				<html:hidden name="gestionarActividadesForm" property="proyectoId" />
				<html:hidden name="gestionarActividadesForm" property="zoom" />
				<html:hidden name="gestionarActividadesForm" property="frecuenciaGantt" />

				<bean:define id="valor" toScope="page">
					<bean:write name="gestionarActividadesForm" property="frecuenciaGantt" />
				</bean:define>
				<bean:define id="nombreFrecuencia" value=""></bean:define>
				<logic:iterate name="gestionarActividadesForm" property="frecuenciasGantt" id="objFrecuencia">
					<logic:equal name="objFrecuencia" property="frecuenciaId" value="<%=valor%>">
						<bean:define id="nombreFrecuencia">
							<bean:write name="objFrecuencia" property="nombre" />
						</bean:define>
					</logic:equal>
				</logic:iterate>

				<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="false">
					<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/gestionarActividadesVisorSimple.jsp"></jsp:include>
				</logic:equal>

				<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
					<jsp:include flush="true" page="/paginas/strategos/planificacionseguimiento/actividades/gestionarActividadesGantt.jsp"></jsp:include>
				</logic:equal>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaActividades" labelPage="inPagina" action="javascript:consultarV2(gestionarActividadesForm, gestionarActividadesForm.pagina, gestionarActividadesForm.atributoOrden, gestionarActividadesForm.tipoOrden, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
		
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-actividades'), 240);
		</script>
		<script type="text/javascript">
			if (document.gestionarActividadesForm.seleccionados.value != "")
			{
				var filaSeleccionada = getEl(document.gestionarActividadesForm.seleccionados.value);
				<logic:equal name="gestionarActividadesForm" property="mostrarGantt" value="true">
				
					function inicio() 
					{
						inicializar();
					}
					window.onload=inicio;
					var anchoPagina = parseInt(_myWidth);
					var altoPagina = parseInt(_myHeight);
					var objeto = document.getElementById('body-actividades');
					if (objeto != null)
						altoPagina = parseInt(objeto.style.height.replace("px", "").replace("%", "")) - 10;
					var columnaEscala = getEl('columnaEscala');
					var columnaGantt = getEl('columnaGantt');
					var marcoActividadesGeneral = getEl('marcoActividadesGeneral');
					var filaGantt = getEl('filaGantt');
					var marcoAlto = altoPagina;
					var marcoAncho = ((anchoPagina - _anchoAreBar) / 2) - 14;
					columnaEscala.width = anchoPagina - 670;
					columnaGantt.width = anchoPagina - 670;
					marcoActividadesGeneral.style.width = marcoAncho + "px";
					marcoActividadesGeneral.style.height = marcoAlto + "px";
					filaGantt.style.width = marcoAncho + 20 + "px";
					filaGantt.style.height = marcoAlto + "px";
				</logic:equal>
			
				eventoClickFilaCombinada(filaSeleccionada);
			}
		</script>

	</tiles:put>

</tiles:insert>
