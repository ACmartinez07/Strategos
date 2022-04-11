/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
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
import com.visiongc.app.strategos.planes.model.util.TipoPerspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * Documentacion:
 * 
 * Clase para Crear el reportes consolidado en Excel.
 * 
 * @author andres
 *
 */
public class PlanEjecucionReporteXlsAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
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

    	return EjecucionPlan(reporte, request, mensajes, source);
    	
    	
	}
	

	
	private StreamInfo EjecucionPlan(ReporteForm reporte, HttpServletRequest request, MessageResources mensajes, String source) throws Exception
	{
		/*
		if (source.equals("Plan"))
    		return EjecucionPlan(reporte, request, mensajes, source);
    	else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
    		return EjecucionIniciativa(reporte, request, mensajes, source);
    	*/
		
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
		
	    Perspectiva perspectiva = null;
	    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
	    	perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class, reporte.getObjetoSeleccionadoId());
	    else
	    	perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		perspectiva.setConfiguracionPlan(configuracionPlan);
		
		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("Consolidado Plan");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.titulo"));

		// Subtitulo
		String subTitulo = mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(); 
		if (subTitulo != null)
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(subTitulo);
		}
		
		
		String texto = null;
		Integer nivel = 0;
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue(texto);

		if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue())
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");

		
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/" + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " -- " + (reporte.getAnoInicial().equals(reporte.getAnoFinal()) ? reporte.getAnoInicial() : (reporte.getAnoInicial() + "/" + reporte.getAnoFinal()));
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(texto);
		}
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.plan") + " : " + perspectiva.getNombreCompleto();
		else
		{
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			perspectivaRaiz.setConfiguracionPlan(configuracionPlan);
			nivel = buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(), strategosPerspectivasService);
			texto = getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : " + perspectiva.getNombreCompleto();
		}
	   
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue(texto);
		
		// información reporte
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
				
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
    			    
    
					Byte alerta = null;
					
					if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
					{
						
						alerta = perspectivaHija.getAlertaAnual();
						if (alerta == null) 
							texto ="";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				    		texto ="Alerta Roja";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				    		texto ="Alerta Verde";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				    		texto ="Alerta Amarilla";
					}

					if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
					{
					
						alerta = perspectivaHija.getAlertaParcial();
						if (alerta == null) 
							texto ="";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				    		texto ="Alerta Roja";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				    		texto ="Alerta Verde";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				    		texto ="Alerta Amarilla";
					}
					   
					texto +="  "+ getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
    				numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue(texto);
					
    		    }
    		    else
    		    {
    		    	texto = getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
    				numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue(texto);
    		    	
    			
    		    }
    		    
    		    numeroCelda = 1;
    			fila = hoja1.createRow(numeroFila++);
    			celda = fila.createCell(numeroCelda);
    			celda.setCellValue("");
    			
				
				buildReporte((nivel + 1), fila, celda, hoja1, numeroCelda, numeroFila, reporte, source, perspectivaHija, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
				
				numeroFila = hoja1.getLastRowNum()+1;
			}
		}
		else{
			
			dibujarInformacionPerspectiva(nivel, fila, celda, hoja1, numeroCelda, numeroFila, reporte, source, perspectiva, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			numeroFila = hoja1.getLastRowNum()+1;
		}
		
		// finalización reporte 
		
		strategosPerspectivasService.close();
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		// Volcamos la información a un archivo.
		String strNombreArchivo = "exportar.xls";
		File objFile = new File(strNombreArchivo);

		FileOutputStream archivoSalida = new FileOutputStream(objFile);
		objWB.write(archivoSalida);
		archivoSalida.close();

		return new FileStreamInfo("application/vnd.ms-excel", new File(strNombreArchivo));
	}
	
	private void buildReporte(int nivel, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila,  ReporteForm reporte, String source, Perspectiva perspectiva, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
	    //Lista de perspectivas del primer nivel
		String texto = null;
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
    			    
    		    
					Byte alerta = null;
					
					if (configuracionPlan.getPlanObjetivoAlertaAnualMostrar())
					{
						alerta = perspectivaHija.getAlertaAnual();
						
						if (alerta == null) 
							texto ="";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				    		texto ="Alerta Roja";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				    		texto ="Alerta Verde";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				    		texto ="Alerta Amarilla";
						
					}

					if (configuracionPlan.getPlanObjetivoAlertaParcialMostrar())
					{
						alerta = perspectivaHija.getAlertaParcial();
					
						if (alerta == null) 
							texto ="";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				    		texto ="Alerta Roja";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				    		texto ="Alerta Verde";
				    	else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				    		texto ="Alerta Amarilla";
						
					}
						
					texto +=" "+ getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
    				
					numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue(texto);
    				
    				numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
    		    }
    		    else
    		    {
    				texto = getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
    				numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue(texto);
    				
    				
    				numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
    		    }
				
				buildReporte((nivel + 1), fila, celda, hoja1, numeroCelda, numeroFila, reporte, source, perspectivaHija, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
				numeroFila = hoja1.getLastRowNum()+1;
			}
		}
		else{
			dibujarInformacionPerspectiva(nivel, fila, celda, hoja1, numeroCelda, numeroFila, reporte, source, perspectiva, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			numeroFila = hoja1.getLastRowNum()+1;
		}
	}
	
	
	private void dibujarInformacionPerspectiva(int nivel , HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, ReporteForm reporte, String source, Perspectiva perspectiva, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		String texto =null;
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
		    
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noestados", getNombrePerspectiva(reporte, (nivel)));
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(texto);
			
		}
		else 
		{
			if (reporte.getVisualizarObjetivo()){
				crearTablaPerspectiva(reporte, fila, celda, hoja1, numeroCelda, numeroFila, perspectiva, indicador, estadosParciales, estadosAnuales, mensajes, strategosMedicionesService, strategosIndicadoresService, request);
				numeroFila = hoja1.getLastRowNum()+1;
			}
		}
		
		if (reporte.getVisualizarIndicadores()){
			dibujarInformacionIndicador(nivel, reporte, fila, celda, hoja1, numeroCelda, numeroFila, perspectiva, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			numeroFila = hoja1.getLastRowNum()+1;
		}
		if (reporte.getVisualizarIniciativas()){
			dibujarInformacionIniciativa(nivel, reporte, fila, celda, hoja1, numeroCelda, numeroFila, source, perspectiva, strategosMedicionesService, strategosIndicadoresService, mensajes, request);
			numeroFila = hoja1.getLastRowNum()+1;
		}
		
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
	
	
	
	private void crearTablaPerspectiva(ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, Perspectiva perspectiva, Indicador indicador, List<PerspectivaEstado> estadosParciales, List<PerspectivaEstado> estadosAnuales, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance().openStrategosVistasDatosService();
		
		String texto = null;
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);

		celda = fila.createCell(numeroCelda);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.periodo"));
	      
		numeroCelda++;
		for (Iterator<PerspectivaEstado> iter = estadosParciales.iterator(); iter.hasNext();)
		{
			
			PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(PeriodoUtil.convertirPeriodoToTexto(estadoParcial.getPk().getPeriodo(), perspectiva.getFrecuencia(), estadoParcial.getPk().getAno()));
		}

		boolean tablaIniciada = false;
		
		StringBuilder string;
		
		
		
	    if (reporte.getVisualizarObjetivoEstadoParcial())
	    {
						
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial"));
			
			for (Iterator<PerspectivaEstado> iter = estadosParciales.iterator(); iter.hasNext();)
			{
				PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();
				Double valor = estadoParcial.getEstado();
				if (valor != null && valor > 100D)
					valor = 100D;
					
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((estadoParcial.getEstado() != null ? VgcFormatter.formatearNumero(valor) : ""));
			}
			
			
	    }

	    if (reporte.getVisualizarObjetivoEstadoAnual())
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual"));
			
			for (Iterator<PerspectivaEstado> iter = estadosAnuales.iterator(); iter.hasNext();)
			{
				PerspectivaEstado estadoParcial = (PerspectivaEstado)iter.next();

				Double valor = estadoParcial.getEstado();
				if (valor != null && valor > 100D)
					valor = 100D;
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((estadoParcial.getEstado() != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

	    }

	    if (reporte.getVisualizarObjetivoAlerta())
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.alerta.parcial"));
		
			
			for (int i = 0; i < (estadosParciales.size()); i++)
			{
				PerspectivaEstado medicion = (PerspectivaEstado)estadosParciales.get(i);
				String alerta = strategosVistasDatosService.getValorDimensiones(TipoVariable.getTipoVariableAlerta().toString(), medicion.getPk().getPeriodo() + "_" + medicion.getPk().getAno(), medicion.getPk().getPeriodo() + "_" + medicion.getPk().getAno(), indicador.getIndicadorId().toString(), reporte.getPlanId().toString(), null, false);
				
				celda = fila.createCell(numeroCelda++);
				
				
				if (alerta == null) 
					celda.setCellValue("");
	    		else if (alerta.equals("0"))
	    			celda.setCellValue("Alerta Roja");
	        	else if (alerta.equals("2"))
	        		celda.setCellValue("Alerta Verde");
	        	else if (alerta.equals("3"))
	        		celda.setCellValue("Alerta Amarilla");
	        	else if (alerta.equals("1"))
	        		celda.setCellValue("Alerta Blanca");
			}

	    }
		
		strategosVistasDatosService.close();
	
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
	}
	
	
	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, Perspectiva perspectiva, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto = null;
		
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

				    Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
					
										
					if (alerta == null) 
		    		{
						texto ="";
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
		    		{
		    			texto ="Alerta Roja";
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
		    		{
		    			texto ="Alerta Verde";
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
		    		{
		    			texto ="Alerta Amarilla";
		    		}

					// Nombre
					texto +=" "+ reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre();
					
					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue(texto);
					
					numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
    		    }
    		    else
    		    {
    		    	texto = reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre();
    		    	
    		    	numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue(texto);
					
					numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
        			
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
					
					crearTablaIndicador(indicador, reporte, fila, celda, hoja1, numeroCelda, numeroFila, mediciones, medicionesMetas, estadosParciales, estadosAnuales, reporte.getVisualizarIndicadoresEjecutado(), reporte.getVisualizarIndicadoresMeta(), reporte.getVisualizarIndicadoresEstadoParcial(), reporte.getVisualizarIndicadoresEstadoParcialSuavisado(), reporte.getVisualizarIndicadoresEstadoAnual(), reporte.getVisualizarIndicadoresEstadoAnualSuavisado(), reporte.getVisualizarIndicadoresAlerta(), mensajes, strategosMedicionesService, strategosIndicadoresService, request);
					numeroFila = hoja1.getLastRowNum()+1;
				}
				else
				{
	    		 
	    			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.indicadores.nomediciones", reporte.getPlantillaPlanes().getNombreIndicadorSingular().toLowerCase());

    		    	numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue(texto);
					
					numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
				}
			}	
		}
		else
		{
		   
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(), getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(texto);
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
		}
		
		strategosPlanesService.close();
		strategosMetasService.close();
	}
	
	
	private void crearTablaIndicador(Indicador indicador, ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, List<Medicion> mediciones, List<Medicion> medicionesMeta, List<IndicadorEstado> estadosParciales, List<IndicadorEstado> estadosAnuales, Boolean showEjecutado, Boolean showMeta, Boolean showEstadoParcial, Boolean showEstadoParcialSuavisado, Boolean showEstadoAnual, Boolean showEstadoAnualSuavisado, Boolean showAlerta, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		boolean tablaIniciada = false;
		
		StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance().openStrategosVistasDatosService();

		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		
	   
	    StringBuilder string;
	  	    
	    celda = fila.createCell(numeroCelda);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.periodo"));
		
		numeroCelda++;
		for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
		{
			
			Medicion medicion = (Medicion)iter.next();
		   
		    celda = fila.createCell(numeroCelda++);
			celda.setCellValue(PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), medicion.getMedicionId().getAno()));
		    
    	}

		
	
	    if (showEjecutado)
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	tablaIniciada = false;
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.ejecutado"));
		
	    	
			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();)
			{
				Medicion medicion = (Medicion)iter.next();
			
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((medicion.getValor() != null ? VgcFormatter.formatearNumero(medicion.getValor()) : ""));
			}

			
	    }

	    if (showMeta)
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	tablaIniciada = false;
			
	    	celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.meta"));
		
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
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((valor != null ? VgcFormatter.formatearNumero(valor) : ""));

			}

			
	    }

	    if (showEstadoParcial)
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
	    	if (!showEstadoParcialSuavisado)
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial"));
	    	else
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial.suavisado"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(string.toString());
			
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
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
				
			}

			
	    }

	    if (showEstadoAnual)
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	tablaIniciada = false;
			
	    	string = new StringBuilder();
	    	if (!showEstadoAnualSuavisado)
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual"));
	    	else
	    		string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual.suavisado"));
			
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(string.toString());

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
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
				
			}

			
	    }
	    
	    if (showAlerta)
	    {
	    	numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	tablaIniciada = false;
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.alerta"));
	

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
					
					if (alerta == null){ 
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue("");
					}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()){
		    			celda = fila.createCell(numeroCelda++);
						celda.setCellValue("Alerta Roja");
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()){
		    			celda = fila.createCell(numeroCelda++);
						celda.setCellValue("Alerta Verde");
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
		    			celda = fila.createCell(numeroCelda++);
						celda.setCellValue("Alerta Amarilla");
		    		}
				}
				else{
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue("");
				}
			}

			
	    }

		strategosVistasDatosService.close();
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
	
	}
	
	
	
	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, String source, Perspectiva perspectiva, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		

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
    		   
    		    
    			numeroCelda = 1;
    			fila = hoja1.createRow(numeroFila++);
    			celda = fila.createCell(numeroCelda);
    			celda.setCellValue(reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : " + iniciativa.getNombre());
    			
    			numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
    			
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

				// Dibujar Informacion de la Iniciativa
    			crearTablaIniciativa(reporte, fila, celda, hoja1, numeroCelda, numeroFila, iniciativa, indicador, medicionesProgramado, medicionesEjecutado, mensajes, request);
    			numeroFila = hoja1.getLastRowNum()+1;
    			
    			numeroCelda = 1;
    			fila = hoja1.createRow(numeroFila++);
    			celda = fila.createCell(numeroCelda);
    			celda.setCellValue(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre());
    		
    			numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
    			
				// Crear tabla del Indicador
				if (medicionesEjecutado.size() != 0){
					crearTablaIndicador(indicador, reporte, fila, celda, hoja1, numeroCelda, numeroFila, medicionesEjecutado, medicionesProgramado, new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(), reporte.getVisualizarIniciativasEjecutado(), reporte.getVisualizarIniciativasMeta(), false, false, false, false, reporte.getVisualizarIniciativasAlerta(), mensajes, strategosMedicionesService, strategosIndicadoresService, request);
					numeroFila = hoja1.getLastRowNum()+1;
				}
				else
				{
					numeroCelda = 1;
	    			fila = hoja1.createRow(numeroFila++);
	    			celda = fila.createCell(numeroCelda);
	    			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.iniciativas.nomediciones", reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()));
	    	
	    			numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
				}
				
				if (reporte.getVisualizarActividad()){
					dibujarInformacionActividad(reporte, fila, celda, hoja1, numeroCelda, numeroFila, iniciativa, strategosMedicionesService, strategosIndicadoresService, mensajes, request);
					numeroFila = hoja1.getLastRowNum()+1;
				}
			}	
		}
		else
		{
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas", reporte.getPlantillaPlanes().getNombreIniciativaPlural().toLowerCase(), getNombrePerspectiva(reporte, (nivel)).toLowerCase()));

			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
		}
		
		strategosMetasService.close();
		strategosIniciativasService.close();
	}
	
	
	private void crearTablaIniciativa(ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, Iniciativa iniciativa, Indicador indicador, List<Medicion> medicionesProgramado, List<Medicion> medicionesEjecutado, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
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

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		
		
	    StringBuilder string;

	    for (int f = 0; f < titulo.length; f++)
	    {
	    	celda = fila.createCell(numeroCelda++);
			celda.setCellValue(titulo[f]);
    		
	    }
	   	
	    
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		
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

	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue(string.toString());

		
		celda = fila.createCell(numeroCelda++);
		celda.setCellValue(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
		
		celda = fila.createCell(numeroCelda++);
		celda.setCellValue(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
		
	    if (reporte.getVisualizarIniciativasEjecutado())
	    	celda = fila.createCell(numeroCelda++);
			celda.setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
	 
	    if (reporte.getVisualizarIniciativasMeta())
	    	celda = fila.createCell(numeroCelda++);
			celda.setCellValue(VgcFormatter.formatearNumero(programado));

	    if (reporte.getVisualizarIniciativasAlerta())
	    {
	  
			if (iniciativa.getAlerta() == null){ 
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue("");
			}
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()){
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Roja");
	        }
	        else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()){
	        	celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Verde");
			}
	        else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
	        	celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Amarilla");
			}
	    }
	    	  
	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue((iniciativa.getResponsableSeguimiento() !=null ? iniciativa.getResponsableSeguimiento().getNombre() : ""));

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
	}
	

	private void dibujarInformacionActividad(ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, Iniciativa iniciativa, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		numeroFila = hoja1.getLastRowNum()+1;
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		

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
				
		    	numeroCelda = 1;
    			fila = hoja1.createRow(numeroFila++);
    			celda = fila.createCell(numeroCelda);
    			celda.setCellValue(reporte.getPlantillaPlanes().getNombreActividadSingular() + " : " + actividad.getNombre());
    		
    			numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
    			
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				
				// Dibujar Informacion de la Iniciativa
    			crearTablaActividad(reporte, fila, celda, hoja1, numeroCelda, numeroFila, actividad, indicador, mensajes, request);
    			numeroFila = hoja1.getLastRowNum()+1;
    			
    			numeroCelda = 1;
    			fila = hoja1.createRow(numeroFila++);
    			celda = fila.createCell(numeroCelda);
    			celda.setCellValue(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : " + indicador.getNombre());
    		
    			numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");

				// Nombre del INDICADOR 
    			
				// Crear tabla del Indicador
				if (medicionesEjecutado.size() != 0){
					crearTablaIndicador(indicador, reporte, fila, celda, hoja1, numeroCelda, numeroFila, medicionesEjecutado, medicionesProgramado, new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(), reporte.getVisualizarActividadEjecutado(), reporte.getVisualizarActividadMeta(), false, false, false, false, reporte.getVisualizarActividadAlerta(), mensajes, strategosMedicionesService, strategosIndicadoresService, request);
					numeroFila = hoja1.getLastRowNum()+1;
				}
				else
				{
					
					numeroCelda = 1;
	    			fila = hoja1.createRow(numeroFila++);
	    			celda = fila.createCell(numeroCelda);
	    			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.actividades.nomediciones", reporte.getPlantillaPlanes().getNombreActividadSingular().toLowerCase()));
	    		
	    			numeroCelda = 1;
    				fila = hoja1.createRow(numeroFila++);
    				celda = fila.createCell(numeroCelda);
    				celda.setCellValue("");
				}
			}	
		}
		else
		{
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades", reporte.getPlantillaPlanes().getNombreActividadPlural().toLowerCase(), reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()));
	
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
		}
		
		strategosMetasService.close();
		strategosPryActividadesService.close();
	}
	

	private void crearTablaActividad(ReporteForm reporte, HSSFRow fila, HSSFCell celda, HSSFSheet hoja1, int numeroCelda, int numeroFila, PryActividad actividad, Indicador indicador, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		
		numeroFila = hoja1.getLastRowNum()+1;
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

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		
		
		StringBuilder string;
	    for (int f = 0; f < titulo.length; f++)
	    {
	    	celda = fila.createCell(numeroCelda++);
			celda.setCellValue(titulo[f]);
    		
	    }
	    
	    
	    numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
	    
		tablaIniciada = false;

		string = new StringBuilder();
	    if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
	    	string.append(indicador.getUnidad().getNombre());
	    else
	    	string.append("");
		
		
		celda = fila.createCell(numeroCelda++);
		celda.setCellValue(string.toString());
		
	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue(VgcFormatter.formatearFecha(actividad.getComienzoPlan(),"formato.fecha.corta"));
	    
	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));

		celda = fila.createCell(numeroCelda++);
		celda.setCellValue(VgcFormatter.formatearNumero(actividad.getPeso()));
		
	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
    	
    	
    	
	    if (reporte.getVisualizarActividadAlerta())
	    {
	    	
			if (actividad.getAlerta() == null){ 
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue("");    	
			}
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()){
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Roja");
	        	
			}
	        else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()){
	        	celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Verde");
	        }
	        else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
	        	celda = fila.createCell(numeroCelda++);
				celda.setCellValue("Alerta Amarilla");
	        }
	    }

	    celda = fila.createCell(numeroCelda++);
		celda.setCellValue(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
    	
    	celda = fila.createCell(numeroCelda++);
		celda.setCellValue(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
    
    	celda = fila.createCell(numeroCelda++);
		celda.setCellValue((actividad.getResponsableSeguimiento() !=null ? actividad.getResponsableSeguimiento().getNombre() : ""));
	    
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
	  
	}
	
	
}
