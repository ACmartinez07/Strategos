/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * @author Kerwin
 *
 */
public class PlanEjecucionReporteAction extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		String source = request.getParameter("source");
		if (source.equals("Plan"))
			return mensajes.getMessage("jsp.reportes.plan.ejecucion.titulo");
		else
			return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.titulo");
	}
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{	
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		/*Parametros para el reporte*/
		String source = request.getParameter("source");
		reporte.setPlanId(request.getParameter("planId") != null && !request.getParameter("planId").toString().equals("") ? Long.parseLong(request.getParameter("planId")) : null);
    	reporte.setObjetoSeleccionadoId(request.getParameter("objetoSeleccionadoId") != null && !request.getParameter("objetoSeleccionadoId").toString().equals("") ? Long.parseLong(request.getParameter("objetoSeleccionadoId")) : null);
		reporte.setMesInicial(request.getParameter("mesInicial"));
		reporte.setMesFinal(request.getParameter("mesFinal"));
    	reporte.setAnoInicial(request.getParameter("anoInicial"));
    	reporte.setAnoFinal(request.getParameter("anoFinal"));
    	reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance")) : reporte.getAlcancePlan());
    	// Visualizar Objetivos
    	reporte.setVisualizarObjetivo((request.getParameter("visualizarObjetivo") != null ? (request.getParameter("visualizarObjetivo").equals("1") ? true : false) : false));
    	reporte.setVisualizarObjetivoEstadoParcial((request.getParameter("visualizarObjetivoEstadoParcial") != null ? (request.getParameter("visualizarObjetivoEstadoParcial").equals("1") ? true : false) : false));
    	reporte.setVisualizarObjetivoEstadoAnual((request.getParameter("visualizarObjetivoEstadoAnual") != null ? (request.getParameter("visualizarObjetivoEstadoAnual").equals("1") ? true : false) : false));
    	reporte.setVisualizarObjetivoAlerta((request.getParameter("visualizarObjetivoAlerta") != null ? (request.getParameter("visualizarObjetivoAlerta").equals("1") ? true : false) : false));
    	// Visualizar Indicadores
    	reporte.setVisualizarIndicadores((request.getParameter("visualizarIndicadores") != null ? (request.getParameter("visualizarIndicadores").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresEjecutado((request.getParameter("visualizarIndicadoresEjecutado") != null ? (request.getParameter("visualizarIndicadoresEjecutado").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresMeta((request.getParameter("visualizarIndicadoresMeta") != null ? (request.getParameter("visualizarIndicadoresMeta").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresEstadoParcial((request.getParameter("visualizarIndicadoresEstadoParcial") != null ? (request.getParameter("visualizarIndicadoresEstadoParcial").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresEstadoParcialSuavisado((request.getParameter("visualizarIndicadoresEstadoParcialSuavisado") != null ? (request.getParameter("visualizarIndicadoresEstadoParcialSuavisado").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresEstadoAnual((request.getParameter("visualizarIndicadoresEstadoAnual") != null ? (request.getParameter("visualizarIndicadoresEstadoAnual").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresEstadoAnualSuavisado((request.getParameter("visualizarIndicadoresEstadoAnualSuavisado") != null ? (request.getParameter("visualizarIndicadoresEstadoAnualSuavisado").equals("1") ? true : false) : false));
    	reporte.setVisualizarIndicadoresAlerta((request.getParameter("visualizarIndicadoresAlerta") != null ? (request.getParameter("visualizarIndicadoresAlerta").equals("1") ? true : false) : false));
    	// Visualizar Iniciativas
    	reporte.setVisualizarIniciativas((request.getParameter("visualizarIniciativas") != null ? (request.getParameter("visualizarIniciativas").equals("1") ? true : false) : false));
    	reporte.setVisualizarIniciativasEjecutado((request.getParameter("visualizarIniciativasEjecutado") != null ? (request.getParameter("visualizarIniciativasEjecutado").equals("1") ? true : false) : false));
    	reporte.setVisualizarIniciativasMeta((request.getParameter("visualizarIniciativasMeta") != null ? (request.getParameter("visualizarIniciativasMeta").equals("1") ? true : false) : false));
    	reporte.setVisualizarIniciativasAlerta((request.getParameter("visualizarIniciativasAlerta") != null ? (request.getParameter("visualizarIniciativasAlerta").equals("1") ? true : false) : false));
    	// Visualizar Actividades
    	reporte.setVisualizarActividad((request.getParameter("visualizarActividad") != null ? (request.getParameter("visualizarActividad").equals("1") ? true : false) : false));
    	reporte.setVisualizarActividadEjecutado((request.getParameter("visualizarActividadEjecutado") != null ? (request.getParameter("visualizarActividadEjecutado").equals("1") ? true : false) : false));
    	reporte.setVisualizarActividadMeta((request.getParameter("visualizarActividadMeta") != null ? (request.getParameter("visualizarActividadMeta").equals("1") ? true : false) : false));
    	reporte.setVisualizarActividadAlerta((request.getParameter("visualizarActividadAlerta") != null ? (request.getParameter("visualizarActividadAlerta").equals("1") ? true : false) : false));

		if (source.equals("Plan"))
    		EjecucionPlan(reporte, documento, request, mensajes, source);
    	else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
    		EjecucionIniciativa(reporte, documento, request, mensajes, source);
	}
	
	private TablaBasicaPDF crearTabla(boolean newTable, boolean isInformativo, String[][] columnas, ReporteForm reporte, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		Color colorLetra = null;  
		Color colorFondo = null;
		Integer anchoBorde = null;
		Integer anchoBordeCelda = null;
		Boolean bold = true;
		Integer alineacionHorizontal = null;
		Integer alineacionVertical = null;
		
		if (isInformativo)
		{
			colorLetra = new Color(0, 0, 0); 
			colorFondo = new Color(255, 255, 255);			
			anchoBorde = 0;
			anchoBordeCelda = 0;
			bold = false;
			alineacionHorizontal = TablaBasicaPDF.H_ALINEACION_LEFT;
			alineacionVertical = TablaBasicaPDF.V_ALINEACION_TOP;
		}
		else
		{
			colorLetra = new Color(255, 255, 255); 
			colorFondo = new Color(128, 128, 128);			
		}
		
		if (tablaHeader != null && newTable)
			tablaHeader = null;
		
		TablaBasicaPDF tabla = inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra, colorFondo, alineacionHorizontal, alineacionVertical, request);

		lineas = getNumeroLinea((lineas + (isInformativo ? 0 : 3)), inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
			tabla = crearTabla(false, isInformativo, columnas, reporte, font, mensajes, documento, request);
		}
		
		if (!isInformativo)
		{
		    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
		    tabla.setFont(Font.NORMAL);
		    tabla.setFormatoFont(Font.NORMAL);
		    tabla.setColorLetra(0, 0, 0);
		    tabla.setColorFondo(255, 255, 255);
		    tabla.setTamanoFont(7);
		}
	    
		return tabla;
	}
	
	private void EjecucionPlan(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String source) throws Exception
	{
	    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
		   
	    //Raiz del plan
	    lineas = 2;
	    tamanoPagina = inicioTamanoPagina;
	    Perspectiva perspectiva = null;
	    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
	    	perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class, reporte.getObjetoSeleccionadoId());
	    else
	    	perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
	    
	    
	    
	    
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		perspectiva.setConfiguracionPlan(configuracionPlan);
	    
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

	    Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/" + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " -- " + (reporte.getAnoInicial().equals(reporte.getAnoFinal()) ? reporte.getAnoInicial() : (reporte.getAnoInicial() + "/" + reporte.getAnoFinal())), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		Integer nivel = 0;
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.plan") + " : " + perspectiva.getNombreCompleto(), fontBold);
		else
		{
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			perspectivaRaiz.setConfiguracionPlan(configuracionPlan);
			nivel = buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(), strategosPerspectivasService);
			texto = new Paragraph(getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : " + perspectiva.getNombreCompleto(), fontBold);
		}
	    texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);
		
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		if (perspectivas.size() > 0)
		{
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;

			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
    		    if (reporte.getVisualizarObjetivoAlerta())
    		    {
        		    int numeroColumnas = 1;
    		    	if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
    		    		numeroColumnas++;
    		    	if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
    		    		numeroColumnas++;

    			    String[][] columnas = new String[numeroColumnas][2];
				    for (int f = 0; f < numeroColumnas; f++)
				    {
				    	if (f == (numeroColumnas - 1))
				    		columnas[f][0] = "100";
				    	else
				    		columnas[f][0] = "2";
				    	columnas[f][1] = "";
				    }
    			    
    		    	TablaBasicaPDF tab = crearTabla(true, true, columnas, reporte, font, mensajes, documento, request);

					Byte alerta = null;
					
					if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
					{
						String url=obtenerCadenaRecurso(request);
						alerta = perspectivaHija.getAlertaAnual();
						if (alerta == null) 
							tab.agregarCelda("");
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
					}

					if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
					{
						String url=obtenerCadenaRecurso(request);
						alerta = perspectivaHija.getAlertaParcial();
						if (alerta == null) 
							tab.agregarCelda("");
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
					}
					tab.agregarCelda(getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto());
	    		    lineas = getNumeroLinea(lineas, inicioLineas);
					documento.add(tab.getTabla());
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
    		    }
    		    else
    		    {
    				texto = new Paragraph(getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto(), font);
    				texto.setAlignment(Element.ALIGN_LEFT);
    				documento.add(texto);
    				lineas = getNumeroLinea(lineas, inicioLineas);
    		    }
				
				buildReporte((nivel + 1), reporte, font, source, perspectivaHija, documento, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionPerspectiva(nivel, reporte, font, source, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
		
	    strategosPerspectivasService.close();
	    strategosIndicadoresService.close();
	    strategosMedicionesService.close();
	}

	private void EjecucionIniciativa(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String source) throws Exception
	{
	    //Raiz del plan
	    lineas = 2;

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);
		
	    Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/" + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " -- " + (reporte.getAnoInicial().equals(reporte.getAnoFinal()) ? reporte.getAnoInicial() : (reporte.getAnoInicial() + "/" + reporte.getAnoFinal())), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		Integer nivel = 0;
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
		if (reporte.getVisualizarIniciativas())
		{
		    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();

			dibujarInformacionIniciativa(nivel, reporte, font, source, null, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);

		    strategosIndicadoresService.close();
		    strategosMedicionesService.close();
		}
	}
	
	public Integer buscarNivelPerspectiva(int nivel, Long perspectivaPadreId, Long perspectivaId, StrategosPerspectivasService strategosPerspectivasService)
	{
		Integer nivelInterno = null;
		if (perspectivaPadreId.longValue() != perspectivaId.longValue())
		{
		    Map<String, Object> filtros = new HashMap<String, Object>();

		    filtros.put("padreId", perspectivaPadreId);
		    String[] orden = new String[1];
		    String[] tipoOrden = new String[1];
		    orden[0] = "nombre";
		    tipoOrden[0] = "asc";
			List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				if (perspectivaHija.getPerspectivaId().longValue() == perspectivaId.longValue())
				{
					nivelInterno = nivel;
					break;
				}
				else
				{
					nivelInterno = buscarNivelPerspectiva((nivel + 1), perspectivaHija.getPerspectivaId(), perspectivaId, strategosPerspectivasService);
					if (nivelInterno != null)
						break;
				}
			}
		}
		else
			nivelInterno = nivel;
		
		return nivelInterno;
	}
	
	public String getNombrePerspectiva(ReporteForm reporte, int nivel)
	{
		String nombre = "";
		
		Set<?> elementosPlantillaPlanes = reporte.getPlantillaPlanes().getElementos();
		if (elementosPlantillaPlanes != null) 
		{
			for (Iterator<?> iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext(); ) 
			{
				ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes)iterElemento.next();
				if (elemento.getOrden().intValue() == nivel) 
				{
					nombre = elemento.getNombre();
					break;
				}
			}
		}
		
		return nombre;
	}
	
	private void buildReporte(int nivel, ReporteForm reporte, Font font, String source, Perspectiva perspectiva, Document documento, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    //Lista de perspectivas del primer nivel
		Paragraph texto;
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		
		if (perspectivas.size() > 0)
		{
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
    		    if (reporte.getVisualizarObjetivoAlerta())
    		    {
        		    int numeroColumnas = 1;
    		    	if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
    		    		numeroColumnas++;
    		    	if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
    		    		numeroColumnas++;

    			    String[][] columnas = new String[numeroColumnas][2];
				    for (int f = 0; f < numeroColumnas; f++)
				    {
				    	if (f == (numeroColumnas - 1))
				    		columnas[f][0] = "100";
				    	else
				    		columnas[f][0] = "2";
				    	columnas[f][1] = "";
				    }
    			    
    		    	TablaBasicaPDF tab = crearTabla(true, true, columnas, reporte, font, mensajes, documento, request);

					Byte alerta = null;
					
					if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
					{
						alerta = perspectivaHija.getAlertaAnual();
						
						String url=obtenerCadenaRecurso(request);
						
						if (alerta == null) 
							tab.agregarCelda("");
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
					}

					if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
					{
						alerta = perspectivaHija.getAlertaParcial();
						
						String url=obtenerCadenaRecurso(request);
						
						if (alerta == null) 
							tab.agregarCelda("");
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
			    			tab.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
					}
					tab.agregarCelda(getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto());
	    		    lineas = getNumeroLinea(lineas, inicioLineas);
					documento.add(tab.getTabla());
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
    		    }
    		    else
    		    {
        		    documento.add(lineaEnBlanco(font));
        			lineas = getNumeroLinea(lineas, inicioLineas);
    				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
        			{
        				lineas = inicioLineas;
        				tamanoPagina = inicioTamanoPagina;
        				saltarPagina(documento, false, font, null, null, request);
        			}

    				texto = new Paragraph(getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto(), font);
    				texto.setAlignment(Element.ALIGN_LEFT);
    				documento.add(texto);
    				lineas = getNumeroLinea(lineas, inicioLineas);
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}

	    		    documento.add(lineaEnBlanco(font));
	    			lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
    		    }
				
				buildReporte((nivel + 1), reporte, font, source, perspectivaHija, documento, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionPerspectiva(nivel, reporte, font, source, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
	}
	
	private void dibujarInformacionPerspectiva(int nivel , ReporteForm reporte, Font font, String source, Perspectiva perspectiva, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		Paragraph texto;
    	List<PerspectivaEstado> estadosParciales = null;
    	List<PerspectivaEstado> estadosAnuales = null;	    

    	Indicador indicador = null;
    	if (perspectiva.getNlParIndicadorId() != null)
    	{
        	indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(perspectiva.getNlParIndicadorId()));

	    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
	    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
	    	int periodoFin = periodoInicio; 
	    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
	    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
	
	    	estadosParciales = strategosPerspectivasService.getPerspectivaEstados(perspectiva.getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), Integer.parseInt(reporte.getAnoInicial()), Integer.parseInt(reporte.getAnoFinal()), periodoInicio, periodoFin);
			estadosAnuales = strategosPerspectivasService.getPerspectivaEstados(perspectiva.getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), Integer.parseInt(reporte.getAnoInicial()), Integer.parseInt(reporte.getAnoFinal()), periodoInicio, periodoFin);
    	}
    	else
    	{
	    	estadosParciales = new ArrayList<PerspectivaEstado>();
			estadosAnuales = new ArrayList<PerspectivaEstado>();
    	}
		
		// Crea tabla con estados y alertas
		if(estadosParciales.size() == 0 && estadosAnuales.size() == 0)
		{
		    documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noestados", getNombrePerspectiva(reporte, (nivel))), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

		    documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		}
		else 
		{
			if (reporte.getVisualizarObjetivo())
				crearTablaPerspectiva(reporte, perspectiva, indicador, estadosParciales, estadosAnuales, font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
		}
		
		if (reporte.getVisualizarIndicadores())
			dibujarInformacionIndicador(nivel, reporte, font, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

		if (reporte.getVisualizarIniciativas())
			dibujarInformacionIniciativa(nivel, reporte, font, source, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);

		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
	}
	
	private void crearTablaPerspectiva(ReporteForm reporte, Perspectiva perspectiva, Indicador indicador, List<PerspectivaEstado> estadosParciales, List<PerspectivaEstado> estadosAnuales, Font font, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, Document documento, HttpServletRequest request) throws Exception
	{
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
		StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance().openStrategosVistasDatosService();
		
	    String[][] columnas = new String[estadosParciales.size()+ 1][2];
	    int contador = 0;
	    columnas[contador][0] = "8";
	    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.periodo");
	    
		for (Iterator<PerspectivaEstado> iter = estadosParciales.iterator(); iter.hasNext();)
		{
			contador++;
			PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();
		    columnas[contador][0] = "8";
		    columnas[contador][1] = PeriodoUtil.convertirPeriodoToTexto(estadoParcial.getPk().getPeriodo(), perspectiva.getFrecuencia(), estadoParcial.getPk().getAno());
		}

		boolean tablaIniciada = false;
		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
		StringBuilder string;
		
	    if (reporte.getVisualizarObjetivoEstadoParcial())
	    {
			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial"));
			string.append("\n");
			string.append("\n");
	    	
			tabla.agregarCelda(string.toString());
			for (Iterator<PerspectivaEstado> iter = estadosParciales.iterator(); iter.hasNext();)
			{
				PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();
				Double valor = estadoParcial.getEstado();
				if (valor != null && valor > 100D)
					valor = 100D;
					
				tabla.agregarCelda((estadoParcial.getEstado() != null ? VgcFormatter.formatearNumero(valor) : ""));
			}
			
			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

	    if (reporte.getVisualizarObjetivoEstadoAnual())
	    {
	    	tablaIniciada = false;

	    	string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());
			
			for (Iterator<PerspectivaEstado> iter = estadosAnuales.iterator(); iter.hasNext();)
			{
				PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();

				Double valor = estadoParcial.getEstado();
				if (valor != null && valor > 100D)
					valor = 100D;
				tabla.agregarCelda((estadoParcial.getEstado() != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

	    if (reporte.getVisualizarObjetivoAlerta())
	    {
	    	tablaIniciada = false;
	    	string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.alerta.parcial"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (int i = 0; i < (estadosParciales.size()); i++)
			{
				PerspectivaEstado medicion = (PerspectivaEstado)estadosParciales.get(i);
				String alerta = strategosVistasDatosService.getValorDimensiones(TipoVariable.getTipoVariableAlerta().toString(), medicion.getPk().getPeriodo() + "_" + medicion.getPk().getAno(), medicion.getPk().getPeriodo() + "_" + medicion.getPk().getAno(), indicador.getIndicadorId().toString(), reporte.getPlanId().toString(), null, false);
				
				String url=obtenerCadenaRecurso(request);
				
				if (alerta == null) 
	    			tabla.agregarCelda("");
	    		else if (alerta.equals("0"))
		        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
	        	else if (alerta.equals("2"))
		        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
	        	else if (alerta.equals("3"))
		        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
	        	else if (alerta.equals("1"))
		        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }
		
		strategosVistasDatosService.close();
		
		if (!tablaIniciada)
			documento.add(tabla.getTabla());
	}
	
	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();
		
		if (indicadores.size() > 0)
		{
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
			{
				Indicador indicador = (Indicador)iter.next();
				
		    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio; 
		    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
		    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				
				// Nombre del INDICADOR 
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

    		    if (reporte.getVisualizarIndicadoresAlerta())
    		    {
        		    int numeroColumnas = 1;
    			    String[][] columnas = new String[numeroColumnas][2];
				    for (int f = 0; f < numeroColumnas; f++)
				    {
				    	if (f == (numeroColumnas - 1))
				    		columnas[f][0] = "100";
				    	else
				    		columnas[f][0] = "2";
				    	columnas[f][1] = "";
				    }

				    font.setStyle(Font.NORMAL);
				    TablaBasicaPDF tab = crearTabla(true, true, columnas, reporte, font, mensajes, documento, request);
    		    	
					Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
					texto = new Paragraph("", font);
					
					String url=obtenerCadenaRecurso(request);
					
					if (alerta == null) 
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}

					// Nombre
					tab.agregarCelda(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre());
	    		    lineas = getNumeroLinea(lineas, inicioLineas);
					documento.add(tab.getTabla());
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
    		    }
    		    else
    		    {
        			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre(), font);
        			texto.setAlignment(Element.ALIGN_LEFT);
        			texto.setIndentationLeft(16);
        			documento.add(texto);
        			lineas = getNumeroLinea(lineas, inicioLineas);
    		    }

    			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
				List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
				List<Medicion> medicionesMetas = new ArrayList<Medicion>();
				if (reporte.getVisualizarIndicadoresMeta() || reporte.getVisualizarIndicadoresAlerta())
				{
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), reporte.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), Integer.parseInt(reporte.getAnoInicial()), Integer.parseInt(reporte.getAnoFinal()), periodoInicio, periodoFin);
					for (Iterator<Meta> iterMeta = metas.iterator(); iterMeta.hasNext();)
					{
						Meta meta = (Meta)iterMeta.next();
						medicionesMetas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), meta.getMetaId().getAno(), new Integer(meta.getMetaId().getPeriodo()), meta.getMetaId().getSerieId()), (meta.getValor() != null ? new Double(meta.getValor()) : null)));
					}
				}
				List<IndicadorEstado> estadosParciales = new ArrayList<IndicadorEstado>();
				List<IndicadorEstado> estadosAnuales = new ArrayList<IndicadorEstado>();
				if (reporte.getVisualizarIndicadoresEstadoParcial())
					estadosParciales = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), reporte.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				if (reporte.getVisualizarIndicadoresEstadoAnual())
					estadosAnuales = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), reporte.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				
				// Crear tabla del Indicador
				if (mediciones.size() != 0)
				{
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
					crearTablaIndicador(indicador, reporte, mediciones, medicionesMetas, estadosParciales, estadosAnuales, reporte.getVisualizarIndicadoresEjecutado(), reporte.getVisualizarIndicadoresMeta(), reporte.getVisualizarIndicadoresEstadoParcial(), reporte.getVisualizarIndicadoresEstadoParcialSuavisado(), reporte.getVisualizarIndicadoresEstadoAnual(), reporte.getVisualizarIndicadoresEstadoAnualSuavisado(), reporte.getVisualizarIndicadoresAlerta(), font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
				}
				else
				{
	    		    font.setColor(0, 0, 255);
	    			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.indicadores.nomediciones", reporte.getPlantillaPlanes().getNombreIndicadorSingular().toLowerCase()), font);
	    			texto.setIndentationLeft(50);
	    			documento.add(texto);
	    			font.setColor(0, 0, 0);
	    			lineas = getNumeroLinea(lineas, inicioLineas);
	    		    if (lineas >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
				}
			}	
		}
		else
		{
		    documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(), getNombrePerspectiva(reporte, (nivel)).toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
		    if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		
		    documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		}
		
		strategosPlanesService.close();
		strategosMetasService.close();
	}
	
	private void crearTablaIndicador(Indicador indicador, ReporteForm reporte, List<Medicion> mediciones, List<Medicion> medicionesMeta, List<IndicadorEstado> estadosParciales, List<IndicadorEstado> estadosAnuales, Boolean showEjecutado, Boolean showMeta, Boolean showEstadoParcial, Boolean showEstadoParcialSuavisado, Boolean showEstadoAnual, Boolean showEstadoAnualSuavisado, Boolean showAlerta, Font font, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, Document documento, HttpServletRequest request) throws Exception
	{
		boolean tablaIniciada = false;
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
		StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance().openStrategosVistasDatosService();

	    String[][] columnas = new String[mediciones.size()+ 1][2];
	    StringBuilder string;
	    int contador = 0;
	    columnas[contador][0] = "8";
	    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.periodo");
		for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
		{
			contador++;
			Medicion medicion = (Medicion)iter.next();
		    columnas[contador][0] = "8";

    		if (contador == (mediciones.size()))
    		{
    		    string = new StringBuilder();
    			string.append(PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), medicion.getMedicionId().getAno()));
    			string.append("\n");
    			string.append("\n");
    		    columnas[contador][1] = string.toString();
    		}
    		else
    			columnas[contador][1] = PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), medicion.getMedicionId().getAno());
		}

		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);

	    if (showEjecutado)
	    {
	    	tablaIniciada = false;
			
			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.ejecutado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());
	    	
			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
				tabla.agregarCelda((medicion.getValor() != null ? VgcFormatter.formatearNumero(medicion.getValor()) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

	    if (showMeta)
	    {
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.meta"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
				Double valor = null;
				for (Iterator<Medicion> iterMeta = medicionesMeta.iterator(); iterMeta.hasNext();)
				{
					Medicion meta = (Medicion)iterMeta.next();
					if (medicion.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() && medicion.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
					{
						valor = meta.getValor();
						break;
					}
				}
				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

	    if (showEstadoParcial)
	    {
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
	    	if (!showEstadoParcialSuavisado)
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial"));
	    	else
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial.suavisado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
				Double valor = null;
				for (Iterator<IndicadorEstado> iterEstado = estadosParciales.iterator(); iterEstado.hasNext();)
				{
					IndicadorEstado estado = (IndicadorEstado)iterEstado.next();
					if (medicion.getMedicionId().getAno().intValue() == estado.getPk().getAno().intValue() && medicion.getMedicionId().getPeriodo().intValue() == estado.getPk().getPeriodo().intValue())
					{
						valor = estado.getEstado();
						break;
					}
				}
				if (showEstadoParcialSuavisado && valor != null && valor > 100D)
					valor = 100D;
					
				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

	    if (showEstadoAnual)
	    {
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
	    	if (!showEstadoAnualSuavisado)
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual"));
	    	else
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual.suavisado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
				Double valor = null;
				for (Iterator<IndicadorEstado> iterEstado = estadosAnuales.iterator(); iterEstado.hasNext();)
				{
					IndicadorEstado estado = (IndicadorEstado)iterEstado.next();
					if (medicion.getMedicionId().getAno().intValue() == estado.getPk().getAno().intValue() && medicion.getMedicionId().getPeriodo().intValue() == estado.getPk().getPeriodo().intValue())
					{
						valor = estado.getEstado();
						break;
					}
				}
				if (showEstadoAnualSuavisado && valor != null && valor > 100D)
					valor = 100D;
				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }
	    
	    if (showAlerta)
	    {
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.alerta"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
				Medicion valorMeta = null;
				for (Iterator<Medicion> iterMeta = medicionesMeta.iterator(); iterMeta.hasNext();)
				{
					Medicion meta = (Medicion)iterMeta.next();
					if (medicion.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() && medicion.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
					{
						valorMeta = meta;
						break;
					}
				}
				if (valorMeta != null && valorMeta.getValor() != null && medicion != null && medicion.getValor() != null)
				{
					Double zonaVerde = null;
					Double zonaAmarilla = null;
					zonaVerde = strategosIndicadoresService.zonaVerde(indicador, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), valorMeta.getValor(), strategosMedicionesService);
					zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), valorMeta.getValor(), zonaVerde, strategosMedicionesService);

					Byte alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, medicion.getValor(), valorMeta.getValor(), null, null);
					
					String url=obtenerCadenaRecurso(request);
					
					if (alerta == null) 
		    			tabla.agregarCelda("");
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
			        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
			        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
			        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
				}
				else
					tabla.agregarCelda("");
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
	    }

		strategosVistasDatosService.close();
		
		if (!tablaIniciada)
			documento.add(tabla.getTabla());
	}
	
	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, Font font, String source, Perspectiva perspectiva, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		font.setStyle(Font.NORMAL);

		filtros = new HashMap<String, Object>();
		if (source.equals("Plan"))
		{
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			reporte.setFiltro(filtro);

			filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
			if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
		}
		else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
		{
			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);
			reporte.setFiltro(filtro);

			Plan plan = null;
			if (reporte.getPlanId() != null)
			{
			    plan = (Plan)strategosIniciativasService.load(Plan.class, reporte.getPlanId());
			    reporte.setPlantillaPlanes((PlantillaPlanes)strategosIniciativasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
			}
			else
			{
				PlantillaPlanes plantilla = new PlantillaPlanes();
				plantilla.setNombreActividadSingular(mensajes.getMessage("jsp.modulo.actividad.titulo.singular"));
				plantilla.setNombreIniciativaSingular(((NavegadorForm)request.getSession().getAttribute("activarIniciativa")).getNombreSingular());
				plantilla.setNombreIndicadorSingular(mensajes.getMessage("jsp.modulo.indicador.titulo.singular"));
				reporte.setPlantillaPlanes(plantilla);
			}
		    
		    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
		    	filtros.put("iniciativaId", reporte.getObjetoSeleccionadoId());
		    else if (plan != null)
		    	filtros.put("planId", plan.getPlanId());
		    else if (reporte.getAlcance().byteValue() == reporte.getAlcanceOrganizacion().byteValue())
		    	filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
		}
		
		List<Iniciativa> iniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		
		if (iniciativas.size() > 0)
		{
			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
			{
				Iniciativa iniciativa = (Iniciativa)iter.next();
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
		    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio; 
		    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
		    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				
				// Nombre del INDICADOR 
    		    if (lineas >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}

    		    documento.add(lineaEnBlanco(font));
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}

    		    texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : " + iniciativa.getNombre(), font);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}
 
    			documento.add(lineaEnBlanco(font));
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}
    			
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

				// Dibujar Informacion de la Iniciativa
    			crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado, medicionesEjecutado, font, mensajes, documento, request);
				
    			documento.add(lineaEnBlanco(font));
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}

    			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre(), font);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}

    			documento.add(lineaEnBlanco(font));
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
    			{
    				lineas = inicioLineas;
    				tamanoPagina = inicioTamanoPagina;
    				saltarPagina(documento, false, font, null, null, request);
    			}
    			
				// Crear tabla del Indicador
				if (medicionesEjecutado.size() != 0)
					crearTablaIndicador(indicador, reporte, medicionesEjecutado, medicionesProgramado, new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(), reporte.getVisualizarIniciativasEjecutado(), reporte.getVisualizarIniciativasMeta(), false, false, false, false, reporte.getVisualizarIniciativasAlerta(), font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
				else
				{
	    			documento.add(lineaEnBlanco(font));
	    			lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}

					font.setColor(0, 0, 255);
	    			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.iniciativas.nomediciones", reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()), font);
	    			texto.setIndentationLeft(50);
	    			documento.add(texto);
	    			font.setColor(0, 0, 0);
	    			lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
				
	    			documento.add(lineaEnBlanco(font));
	    			lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
	    			{
	    				lineas = inicioLineas;
	    				tamanoPagina = inicioTamanoPagina;
	    				saltarPagina(documento, false, font, null, null, request);
	    			}
				}
				
				if (reporte.getVisualizarActividad())
					dibujarInformacionActividad(reporte, font, iniciativa, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);
			}	
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas", reporte.getPlantillaPlanes().getNombreIniciativaPlural().toLowerCase(), getNombrePerspectiva(reporte, (nivel)).toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		}
		
		strategosMetasService.close();
		strategosIniciativasService.close();
	}

	private void crearTablaIniciativa(ReporteForm reporte, Iniciativa iniciativa, Indicador indicador, List<Medicion> medicionesProgramado, List<Medicion> medicionesEjecutado, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

		PryProyecto proyecto = null;
		if (iniciativa.getProyectoId() != null)
		{
			StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
			proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
			strategosPryProyectosService.close();
		}
		
		String encabezado = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio") + ",";	
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion") + ",";
		if (reporte.getVisualizarIniciativasEjecutado())
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance") + ",";
		if (reporte.getVisualizarIniciativasMeta())
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado") + ",";
		if (reporte.getVisualizarIniciativasAlerta())
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable");
		
		String[] titulo = encabezado.split(",");
		boolean tablaIniciada = false;

	    String[][] columnas = new String[titulo.length][2];
	    StringBuilder string;

	    for (int f = 0; f < titulo.length; f++)
	    {
    		columnas[f][0] = "8";
    		if (f == (titulo.length - 1))
    		{
    			string = new StringBuilder();
    			string.append(titulo[f]);
    			string.append("\n");
    			string.append("\n");
    		    columnas[f][1] = string.toString();
    		}
    		else
    			columnas[f][1] = titulo[f];
	    }
	    
		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
		
	    Double programado = 0D;
	    Double porcentajeEsperado = 0D;
	    for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();)
	    {
	    	Medicion ejecutado = (Medicion)iterEjecutado.next();
			for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();)
			{
				Medicion meta = (Medicion)iterMeta.next();
				if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() &&
						ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
				{
					if (meta.getValor() != null)
						programado = programado + meta.getValor();
					break;
				}
			}
	    }
		if (programado.doubleValue() != 0)
			porcentajeEsperado = (porcentajeEsperado * 100D) / 100D;

		tablaIniciada = false;
		string = new StringBuilder();
	    if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
	    	string.append(indicador.getUnidad().getNombre());
	    else
	    	string.append("");
		string.append("\n");
		string.append("\n");
		tabla.agregarCelda(string.toString());
		
	    tabla.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
	    tabla.agregarCelda(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
	    if (reporte.getVisualizarIniciativasEjecutado())
	    	tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
	    if (reporte.getVisualizarIniciativasMeta())
	    	tabla.agregarCelda(VgcFormatter.formatearNumero(programado));
	    if (reporte.getVisualizarIniciativasAlerta())
	    {
	    	String url=obtenerCadenaRecurso(request);
			if (iniciativa.getAlerta() == null) 
				tabla.agregarCelda("");
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
	    }
	    tabla.agregarCelda(iniciativa.getResponsableSeguimiento() !=null ? iniciativa.getResponsableSeguimiento() : "");	    

		lineas = getNumeroLinea((lineas + 3), inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
			tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
			tablaIniciada = true;
		}

		if (!tablaIniciada)
			documento.add(tabla.getTabla());
	}
	
	private void dibujarInformacionActividad(ReporteForm reporte, Font font, Iniciativa iniciativa, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		font.setStyle(Font.NORMAL);

		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		List<PryActividad> actividades = strategosPryActividadesService.getActividades(0, 0, "fila", "ASC", true, filtros).getLista(); 
		
		if (actividades.size() > 0)
		{
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();)
			{
				PryActividad actividad = (PryActividad)iter.next();
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, actividad.getIndicadorId());
				
		    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio; 
		    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
		    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				
				documento.add(lineaEnBlanco(font));
				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

    			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreActividadSingular() + " : " + actividad.getNombre(), font);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
    			
				documento.add(lineaEnBlanco(font));
				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				
				// Dibujar Informacion de la Iniciativa
    			crearTablaActividad(reporte, actividad, indicador, font, mensajes, documento, request);

				documento.add(lineaEnBlanco(font));
				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

				// Nombre del INDICADOR 
    			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre(), font);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

				documento.add(lineaEnBlanco(font));
				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				
				// Crear tabla del Indicador
				if (medicionesEjecutado.size() != 0)
					crearTablaIndicador(indicador, reporte, medicionesEjecutado, medicionesProgramado, new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(), reporte.getVisualizarActividadEjecutado(), reporte.getVisualizarActividadMeta(), false, false, false, false, reporte.getVisualizarActividadAlerta(), font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
				else
				{
					documento.add(lineaEnBlanco(font));
					lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
					{
						lineas = inicioLineas;
						tamanoPagina = inicioTamanoPagina;
						saltarPagina(documento, false, font, null, null, request);
					}

					font.setColor(0, 0, 255);
	    			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.actividades.nomediciones", reporte.getPlantillaPlanes().getNombreActividadSingular().toLowerCase()), font);
	    			texto.setIndentationLeft(50);
	    			documento.add(texto);
	    			font.setColor(0, 0, 0);
	    			lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
					{
						lineas = inicioLineas;
						tamanoPagina = inicioTamanoPagina;
						saltarPagina(documento, false, font, null, null, request);
					}

					documento.add(lineaEnBlanco(font));
					lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
					{
						lineas = inicioLineas;
						tamanoPagina = inicioTamanoPagina;
						saltarPagina(documento, false, font, null, null, request);
					}
				}
			}	
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades", reporte.getPlantillaPlanes().getNombreActividadPlural().toLowerCase(), reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		}
		
		strategosMetasService.close();
		strategosPryActividadesService.close();
	}

	private void crearTablaActividad(ReporteForm reporte, PryActividad actividad, Indicador indicador, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

		String encabezado = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio") + ",";	
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion") + ",";
		if (reporte.getVisualizarActividadAlerta())
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable");

		String[] titulo = encabezado.split(",");

		boolean tablaIniciada = false;

		String[][] columnas = new String[titulo.length][2];
		StringBuilder string;
	    for (int f = 0; f < titulo.length; f++)
	    {
    		columnas[f][0] = "8";
    		if (f == (titulo.length - 1))
    		{
    		    string = new StringBuilder();
    			string.append(titulo[f]);
    			string.append("\n");
    			string.append("\n");
    		    columnas[f][1] = string.toString();
    		}
    		else
    			columnas[f][1] = titulo[f];
	    }
	    
		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
		
		tablaIniciada = false;

		string = new StringBuilder();
	    if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
	    	string.append(indicador.getUnidad().getNombre());
	    else
	    	string.append("");
		string.append("\n");
		string.append("\n");
		tabla.agregarCelda(string.toString());
	    tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getComienzoPlan(),"formato.fecha.corta"));
	    tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
    	tabla.agregarCelda (VgcFormatter.formatearNumero(actividad.getPeso()));
    	tabla.agregarCelda(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
	    if (reporte.getVisualizarActividadAlerta())
	    {
	    	String url=obtenerCadenaRecurso(request);
			if (actividad.getAlerta() == null) 
				tabla.agregarCelda("");
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
	    }

    	tabla.agregarCelda(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
    	tabla.agregarCelda(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
	    tabla.agregarCelda(actividad.getResponsableSeguimiento() !=null ? actividad.getResponsableSeguimiento() : "");	    

	    lineas = getNumeroLinea((lineas + 3), inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
			tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
			tablaIniciada = true;
		}

		if (!tablaIniciada)
			documento.add(tabla.getTabla());
	}
	private String obtenerCadenaRecurso(HttpServletRequest request){
		String result= null;
		if(request.getServerPort()==80 && request.getScheme().equals("http")){
			
		    result = request.getServerName() + "/" + request.getContextPath();
		    result = "https" + "://" + result.replaceAll("//", "/");
			
		}else{
			result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
		    result = request.getScheme() + "://" + result.replaceAll("//", "/");
		   
		}
		return result;
		
	}
}
