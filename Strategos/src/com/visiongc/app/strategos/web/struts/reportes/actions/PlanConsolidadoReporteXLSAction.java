/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
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
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planes.model.util.TipoPerspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;

/**
 * Documentacion:
 * 
 * Clase para Crear el reportes consolidado en Excel.
 * 
 * @author Kerwin
 *
 */
public class PlanConsolidadoReporteXLSAction extends VgcAction
{
	
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegaci�n v�lido
		 */

		navBar.agregarUrl(url, nombre);
	}
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);
		
			String forward = mapping.getParameter();	
			
			MessageResources mensajes = getResources(request);
			ReporteForm reporte = new ReporteForm();
			reporte.clear();
			
			reporte.setPlanId(request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
	    	reporte.setObjetoSeleccionadoId(request.getParameter("objetoSeleccionadoId") != null ? Long.parseLong(request.getParameter("objetoSeleccionadoId")) : null);
			reporte.setMesFinal(request.getParameter("mes"));
	    	reporte.setAnoFinal(request.getParameter("ano"));
	    	reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance")) : reporte.getAlcancePlan());
	    	reporte.setTipoPeriodo(request.getParameter("tipoPeriodo") != null ? Byte.parseByte(request.getParameter("tipoPeriodo")) : reporte.getPeriodoAlCorte());
	    	reporte.setVisualizarIndicadores((request.getParameter("visualizarIndicadores") != null ? (request.getParameter("visualizarIndicadores").equals("1") ? true : false) : false));
	    	reporte.setVisualizarIniciativas((request.getParameter("visualizarIniciativas") != null ? (request.getParameter("visualizarIniciativas").equals("1") ? true : false) : false));
	    	reporte.setVisualizarActividad((request.getParameter("visualizarActividades") != null ? (request.getParameter("visualizarActividades").equals("1") ? true : false) : false));

			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			reporte.setFiltro(filtro);
	    			
			ConsolidadoPlan(reporte, request, mensajes, response);			
							    			
			forward="exito";
	      
			/** Otherwise, return "success" */
			return mapping.findForward(forward);  
	        
	}
	
	
	
	
	
	
	/*
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		reporte.setPlanId(request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
    	reporte.setObjetoSeleccionadoId(request.getParameter("objetoSeleccionadoId") != null ? Long.parseLong(request.getParameter("objetoSeleccionadoId")) : null);
		reporte.setMesFinal(request.getParameter("mes"));
    	reporte.setAnoFinal(request.getParameter("ano"));
    	reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance")) : reporte.getAlcancePlan());
    	reporte.setTipoPeriodo(request.getParameter("tipoPeriodo") != null ? Byte.parseByte(request.getParameter("tipoPeriodo")) : reporte.getPeriodoAlCorte());
    	reporte.setVisualizarIndicadores((request.getParameter("visualizarIndicadores") != null ? (request.getParameter("visualizarIndicadores").equals("1") ? true : false) : false));
    	reporte.setVisualizarIniciativas((request.getParameter("visualizarIniciativas") != null ? (request.getParameter("visualizarIniciativas").equals("1") ? true : false) : false));

		Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
		FiltroForm filtro = new FiltroForm();
		filtro.setHistorico(selectHitoricoType);
		reporte.setFiltro(filtro);
    			
		return ConsolidadoPlan(reporte, request, mensajes);
		
		
	
	}
	*/
	
	
	private void ConsolidadoPlan(ReporteForm reporte, HttpServletRequest request, MessageResources mensajes, HttpServletResponse response) throws Exception
	{
	   
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();	    
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
		reporte.setAnoInicial(plan.getAnoInicial().toString());
		reporte.setMesInicial("1");

	    Perspectiva perspectiva = null;
	    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
	    	perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class, reporte.getObjetoSeleccionadoId());
	    else
	    	perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
	    new com.visiongc.app.strategos.web.struts.reportes.actions.PlanConsolidadoReportePdfAction().SetMedicionesPerspectiva(perspectiva, reporte, strategosIndicadoresService, strategosMedicionesService);
	    
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		perspectiva.setConfiguracionPlan(configuracionPlan);
		
		
		Workbook objWB = new XSSFWorkbook();
		
		//HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendr� la celda
		Cell celda = null;

		// Creo la hoja
		Sheet hoja1 = objWB.createSheet("Consolidado Plan");

		// Proceso la informaci�n y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		Row fila = hoja1.createRow(numeroFila++);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendr� la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.consolidado.titulo"));

		// Subtitulo
		String subTitulo = mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(); 
		if (subTitulo != null)
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(subTitulo);
		}
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		String texto = null;
		Integer nivel = 0;
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto();
		else
		{
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			perspectivaRaiz.setConfiguracionPlan(configuracionPlan);
			nivel = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(), strategosPerspectivasService);
			texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : " + perspectiva.getNombreCompleto();
		}
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

			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " - " + reporte.getAnoFinal();
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(texto);
		}
		
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
				new com.visiongc.app.strategos.web.struts.reportes.actions.PlanConsolidadoReportePdfAction().SetMedicionesPerspectiva(perspectivaHija, reporte, strategosIndicadoresService, strategosMedicionesService);
				
				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);

				if (perspectivaHija.getTipo().byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())
				{
					if (reporte.getVisualizarIndicadores())
					{
						numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

						numeroCelda = 1;
						fila = hoja1.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue("");
					}

					if (reporte.getVisualizarIniciativas())
					{
						numeroFila = dibujarInformacionIniciativa(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1, strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

						numeroCelda = 1;
						fila = hoja1.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue("");
					}
				}
				
				numeroFila = buildReporte(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1, configuracionPlan, strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}
		else
		{
			if (reporte.getVisualizarIndicadores())
			{
				numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectiva, objWB, hoja1, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
			
			if (reporte.getVisualizarIniciativas())
			{
				numeroFila = dibujarInformacionIniciativa(numeroFila, nivel, reporte, perspectiva, objWB, hoja1, strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
						
		}
		
	    strategosPerspectivasService.close();
	    strategosIndicadoresService.close();
	    strategosIniciativasService.close();
	    strategosMedicionesService.close();	    
		
		 
        Date date = new Date();
        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
       
        
        String archivo="reporteConsolidado_"+hourdateFormat.format(date)+".xlsx"; 
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
       
        ServletOutputStream file  = response.getOutputStream();
        
        objWB.write(file);
        file.close();
		
	}
	
	
	
	private int buildReporte(int numeroFila, int nivel, ReporteForm reporte, Perspectiva perspectiva, Workbook objWB, Sheet hoja, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService, StrategosIniciativasService strategosIniciativasService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    //Lista de perspectivas del primer nivel
		String texto;
		Cell celda = null;
		Row fila;
		int numeroCelda = 1;

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
				new com.visiongc.app.strategos.web.struts.reportes.actions.PlanConsolidadoReportePdfAction().SetMedicionesPerspectiva(perspectivaHija, reporte, strategosIndicadoresService, strategosMedicionesService);
				
				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);
				
				if (perspectivaHija.getTipo().byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())
				{
					if (reporte.getVisualizarIndicadores())
					{
						numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

						numeroCelda = 1;
						fila = hoja.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue("");
					}

					if (reporte.getVisualizarIniciativas())
					{
						numeroFila = dibujarInformacionIniciativa(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja, strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
						
						numeroCelda = 1;
						fila = hoja.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue("");
					}
				}

				numeroFila = buildReporte(numeroFila, (nivel + 1), reporte, perspectivaHija, objWB, hoja, configuracionPlan, strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}
		
		return numeroFila;
	}
	
	private int dibujarInformacionIndicador(int numeroFila, int nivel , ReporteForm reporte, Perspectiva perspectiva, Workbook objWB, Sheet hoja, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;

		Cell celda = null;
		Row fila;
		int numeroCelda = 1;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();
		
		if (indicadores.size() > 0)
		{
		    String[][] columnas = new String[9][2];
		    int contador = 0;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");
		    
		    contador++;
		    columnas[contador][0] = "100";
		    columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();

		    contador++;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.unidad");

		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.ultimoperiodo");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.metaanula");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.real");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.cumplimiento");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.responsable");
		    
		    contador++;
		    columnas[contador][0] = "50";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.explicaciones");
		    
			numeroFila = crearEncabezado(columnas, numeroFila, reporte, mensajes, strategosMedicionesService, strategosIndicadoresService, objWB, hoja, request);
			
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
			{
				Indicador indicador = (Indicador)iter.next();
				
    		    // Alerta
				Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
				if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue())
				{
			    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
			    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
			    	int periodoFin = periodoInicio; 
			    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
			    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
			    	
	    			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
					List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
					List<Medicion> medicionesMetas = new ArrayList<Medicion>();
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), reporte.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), Integer.parseInt(reporte.getAnoInicial()), Integer.parseInt(reporte.getAnoFinal()), periodoInicio, periodoFin);
					for (Iterator<Meta> iterMeta = metas.iterator(); iterMeta.hasNext();)
					{
						Meta meta = (Meta)iterMeta.next();
						medicionesMetas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), meta.getMetaId().getAno(), new Integer(meta.getMetaId().getPeriodo()), meta.getMetaId().getSerieId()), (meta.getValor() != null ? new Double(meta.getValor()) : null)));
					}
					
					Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);
					Medicion ultimaMeta = null;
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
						ultimaMeta = strategosMedicionesService.getUltimaMedicionConValorEnUnPeriodo(medicionesMetas, ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo());
					
					Double metaPlan = null;
					if (ultimaMeta != null && ultimaMeta.getValor() != null)
						metaPlan = ultimaMeta.getValor();
					
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
					{
						indicador.setFechaUltimaMedicion(ultimaMedicion.getMedicionId().getPeriodo() + "/" + ultimaMedicion.getMedicionId().getAno());
						indicador.setUltimaMedicion(ultimaMedicion.getValor());
					}
					else
					{
						indicador.setFechaUltimaMedicion(null);
						indicador.setUltimaMedicion(null);
					}
					
					if (metaPlan != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), metaPlan, null, null);
					}
					indicador.setAlerta(alerta);
				}
				texto = null;
				if (alerta == null) 
					texto = "Blanca";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	    			texto = "Roja";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	    			texto = "Verde";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	    			texto = "Amarillo";
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
					
				// Nombre del indicador
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(indicador.getNombre());

    		    // Unidad
				texto = "";
				if (indicador.getUnidadId() != null)
				{
					UnidadMedida unidad = (UnidadMedida)strategosPlanesService.load(UnidadMedida.class, indicador.getUnidadId());
					if (unidad != null)
						texto = unidad.getNombre();
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
	    		    
    		    // Fecha Ultima Medicion
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null)
					texto = indicador.getFechaUltimaMedicion();
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// % Meta Anual
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null) 
				{
					List<?> metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), reporte.getPlanId(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), false);
					if (metas.size() > 0)
					{
						indicador.setMetaAnual(((Meta)metas.get(0)).getValor());
						texto = (indicador.getMetaAnual() != null ? VgcFormatter.formatearNumero(indicador.getMetaAnual()) : "");
					}
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Medicion
				texto = "";
				if (indicador.getUltimaMedicion() != null)
					texto = indicador.getUltimaMedicionFormateada();
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				// % Cumplimiento
				texto = "";
				if (indicador.getMetaAnual() != null && indicador.getUltimaMedicion() != null && indicador.getMetaAnual() != 0)
				{
					Double valor = (indicador.getUltimaMedicion() / indicador.getMetaAnual()) * 100;
					if (valor > 100D)
						valor = 100D;
					texto = (valor != null ? VgcFormatter.formatearNumero(valor) : "");
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				// Responsable
				texto = "";
				if (indicador.getResponsableLograrMetaId() != null)
					texto = (indicador.getResponsableLograrMeta() != null ? indicador.getResponsableLograrMeta().getNombre() : "");
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
    		    // Explicacion
				texto = "";
				String periodoBuscar = reporte.getMesFinal() + "/" + reporte.getAnoFinal();
				Explicacion explicacion = BuscarExplicacion(reporte, periodoBuscar, indicador.getIndicadorId(), ObjetivoKey.getKeyIndicador(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null && explicacion.getMemosExplicacion().size() > 0)
				{
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
					{
						MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						{
							texto = memoExplicacion.getMemo();
							break;
						}
					}
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
			}
		}
		else
		{
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(), new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			numeroCelda = 1;
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			//celda.setCellStyle(getEstiloCuerpo(objWB));
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(texto);
		}
		
		strategosPlanesService.close();
		strategosMetasService.close();
		strategosExplicacionesService.close();
		
		return numeroFila;
	}
	
	private int dibujarInformacionIniciativa(int numeroFila, int nivel , ReporteForm reporte, Perspectiva perspectiva, Workbook objWB, Sheet hoja, StrategosMedicionesService strategosMedicionesService, StrategosIniciativasService strategosIniciativasService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;

		Cell celda = null;
		Row fila;
		int numeroCelda = 1;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
			filtros.put("historicoDate", "IS NULL");
		else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
			filtros.put("historicoDate", "IS NOT NULL");
		List<Iniciativa> iniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		
		if (iniciativas.size() > 0)
		{
		    String[][] columnas = new String[13][2];
		    int contador = 0;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");
		    
		    contador++;
		    columnas[contador][0] = "100";
		    columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIniciativaPlural();

		    contador++;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.ultimoperiodo");

		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.esperado.acumulado");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.ejecutado.acumulado");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.programado");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.presupuesto.real");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.ejecucion");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.responsable");
		    
		    contador++;
		    columnas[contador][0] = "50";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.explicaciones");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.editaractividad.fecha.comienzo");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.editaractividad.fecha.fin");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.dias.diferencia");
		    
			numeroFila = crearEncabezado(columnas, numeroFila, reporte, mensajes, strategosMedicionesService, strategosIndicadoresService, objWB, hoja, request);
			
			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
			{
				Iniciativa iniciativa = (Iniciativa)iter.next();
				
				List<PryActividad> actividades = new ArrayList<PryActividad>();
				
				
				if(iniciativa.getProyectoId() != null){
					actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
				}
				
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
				if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue())
				{
			    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
			    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
			    	int periodoFin = periodoInicio; 
			    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
			    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

	    			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
					List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
					List<Medicion> medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
					
					Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);
					Medicion ultimaMeta = null;
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
						ultimaMeta = strategosMedicionesService.getUltimaMedicionConValorEnUnPeriodo(medicionesProgramadas, ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo());
					
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
					{
						indicador.setFechaUltimaMedicion(ultimaMedicion.getMedicionId().getPeriodo() + "/" + ultimaMedicion.getMedicionId().getAno());
						indicador.setUltimaMedicion(ultimaMedicion.getValor());
					}
					else
					{
						indicador.setFechaUltimaMedicion(null);
						indicador.setUltimaMedicion(null);
					}
					
					if (ultimaMeta != null && ultimaMeta.getValor() != null)
						indicador.setUltimoProgramado(ultimaMeta.getValor());
					else
						indicador.setUltimoProgramado(null);
					
					Byte alerta = null;
					if (indicador.getUltimoProgramado() != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), indicador.getUltimoProgramado(), null, null);
					}
					indicador.setAlerta(alerta);
				}
				
    		    // Alerta
				Byte alerta = indicador.getAlerta();
				texto = null;
				if (alerta == null) 
					texto = "Blanca";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	    			texto = "Roja";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	    			texto = "Verde";
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	    			texto = "Amarillo";
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
					
				// Nombre de la iniciativa
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getNombre());

    		    // Fecha Ultima Medicion
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null)
					texto = indicador.getFechaUltimaMedicion();
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

  			  	Double totalPresupuestoReal = null;
  			  	Double totalPresupuestoProgramado = null;
  			  	Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()); 
  			  	if (indicadorId != null)
  			  	{
	  			  	Indicador indicadorPresupuesto = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);  			  	
	  			  	if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoAlCorte())
	  			  	{
	  	  			  	if (indicadorPresupuesto.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
	  	  			  	{
	  		  			  	List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
	  				  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
	  		  			  	for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
	  		  			  	{
	  		  			  		Medicion medicion = (Medicion)iterMediciones.next();
	  		  			  		if (medicion.getValor() != null && totalPresupuestoReal == null)
	  		  			  			totalPresupuestoReal = 0D;
	  	  			  			totalPresupuestoReal = totalPresupuestoReal + medicion.getValor();
	  			  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
	  			  			  	{
	  			  			  		Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
	  			  			  		if (medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue() &&
	  			  			  			medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue())
	  			  			  		{
	  			  			  			if (medicionProgramada.getValor() != null && totalPresupuestoProgramado == null)
	  			  			  				totalPresupuestoProgramado = 0D;
	  		  			  				totalPresupuestoProgramado = totalPresupuestoProgramado + medicionProgramada.getValor();
	  						  			break;
	  			  			  		}
	  					  		}
	  		  			  	}
	  	  			  	}
	  	  			  	else
	  	  			  	{
	  	  			  		totalPresupuestoReal = indicadorPresupuesto.getUltimaMedicion();
	
	  	  			  		if (totalPresupuestoReal != null)
	  	  			  		{
	  		  			  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
	  	  			  			DecimalFormat nf3 = new DecimalFormat("#000");
	  			  				int anoPeriodoBuscar = Integer.parseInt(((Integer)indicadorPresupuesto.getFechaUltimaMedicionAno()).toString() + nf3.format(indicadorPresupuesto.getFechaUltimaMedicionPeriodo()).toString());
	  			  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
	  			  			  	{
	  			  			  		Medicion medProgramada = (Medicion)iterMedicionesProgramadas.next();
	  	  			  				int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString() + nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
	  	  			  				if (anoPeriodo <= anoPeriodoBuscar)
	  	  			  					totalPresupuestoProgramado = medProgramada.getValor();
	  					  		}
	  	  			  		}
	  	  			  	}
	  			  	}
	  			  	else
	  			  	{
	  	  			  	if (indicadorPresupuesto.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
	  	  			  	{
	  		  			  	List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), Integer.parseInt(reporte.getAnoInicial()), new Integer(000), Integer.parseInt(reporte.getMesInicial()));
	  				  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000), Integer.parseInt(reporte.getAnoInicial()), new Integer(000), Integer.parseInt(reporte.getMesInicial()));
	  		  			  	for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
	  		  			  	{
	  		  			  		Medicion medicion = (Medicion)iterMediciones.next();
	  		  			  		if (medicion.getValor() != null && totalPresupuestoReal == null)
	  		  			  			totalPresupuestoReal = 0D;
	  	  			  			totalPresupuestoReal = totalPresupuestoReal + medicion.getValor();
	  			  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
	  			  			  	{
	  			  			  		Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
	  			  			  		if (medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue() &&
	  			  			  			medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue())
	  			  			  		{
	  			  			  			if (medicionProgramada.getValor() != null && totalPresupuestoProgramado == null)
	  			  			  				totalPresupuestoProgramado = 0D;
	  		  			  				totalPresupuestoProgramado = totalPresupuestoProgramado + medicionProgramada.getValor();
	  						  			break;
	  			  			  		}
	  					  		}
	  		  			  	}
	  	  			  	}
	  	  			  	else
	  	  			  	{
	  	  			  		totalPresupuestoReal = indicadorPresupuesto.getUltimaMedicion();
	
	  	  			  		if (totalPresupuestoReal != null)
	  	  			  		{
	  		  			  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000), Integer.parseInt(reporte.getAnoInicial()), new Integer(000), Integer.parseInt(reporte.getMesInicial()));
	  	  			  			DecimalFormat nf3 = new DecimalFormat("#000");
	  			  				int anoPeriodoBuscar = Integer.parseInt(((Integer)indicadorPresupuesto.getFechaUltimaMedicionAno()).toString() + nf3.format(indicadorPresupuesto.getFechaUltimaMedicionPeriodo()).toString());
	  			  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
	  			  			  	{
	  			  			  		Medicion medProgramada = (Medicion)iterMedicionesProgramadas.next();
	  	  			  				int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString() + nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
	  	  			  				if (anoPeriodo <= anoPeriodoBuscar)
	  	  			  					totalPresupuestoProgramado = medProgramada.getValor();
	  					  		}
	  	  			  		}
	  	  			  	}
	  			  	}
  			  	}
				
  			  	// Esperado Acumulado
				texto = "";
				if (indicador.getUltimoProgramado() != null)
					texto = indicador.getUltimoProgramadoFormateado();
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Ejecutado Acumulado
				texto = "";
				if (indicador.getUltimaMedicion() != null)
					texto = indicador.getUltimaMedicionFormateada();
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				// Total Esperado Acumulado
				texto = (totalPresupuestoProgramado != null ? VgcFormatter.formatearNumero(totalPresupuestoProgramado) : "");
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
  			  	// Total Ejecutado Acumulado
				texto = (totalPresupuestoReal != null ? VgcFormatter.formatearNumero(totalPresupuestoReal) : "");
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// % Cumplimiento
				texto = "";
				if (totalPresupuestoProgramado != null && totalPresupuestoReal != null && totalPresupuestoProgramado != 0D)
				{
					Double valor = (totalPresupuestoReal / totalPresupuestoProgramado) * 100;
					if (valor > 100D)
						valor = 100D;
					texto = (valor != null ? VgcFormatter.formatearNumero(valor) : "");
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				// Responsable
				texto = "";
				if (iniciativa.getResponsableLograrMetaId() != null)
					texto = (iniciativa.getResponsableLograrMeta() != null ? iniciativa.getResponsableLograrMeta().getNombre() : "");
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
    		    // Explicacion
				texto = "";
				String periodoBuscar = reporte.getMesFinal() + "/" + reporte.getAnoFinal();
				Explicacion explicacion = BuscarExplicacion(reporte, periodoBuscar, iniciativa.getIniciativaId(), ObjetivoKey.getKeyIniciativa(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null && explicacion.getMemosExplicacion().size() > 0)
				{
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
					{
						MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						{
							texto = memoExplicacion.getMemo();
							break;
						}
					}
				}
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				Date fechaAh = new Date();
				Date fechaFi = new Date();
				
				SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy"); 
											
				
				//Fecha Inicio 
				texto = "";
				if (actividades != null) {
					fechaAh = obtenerFechaInicial(actividades);
					if(fechaAh != null) {
						texto = objSDF.format(fechaAh).toString();
					}
				}				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				//Fecha Culminacion
				texto = "";
				if (actividades != null) {
					fechaFi = obtenerFechaFinal(actividades);
					if(fechaFi != null) {
						texto = objSDF.format(fechaFi).toString();
					}
				}				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				//Dias 	
				
				texto = "";
				fechaAh = obtenerFechaInicial(actividades);
				fechaFi = obtenerFechaFinal(actividades);
				
				if(fechaAh != null && fechaFi != null) {	
					int milisecondsByDay = 86400000;					
					int dias = (int) ((fechaFi.getTime()-fechaAh.getTime()) / milisecondsByDay);
					texto = ""+dias;
				}else {
					texto = "";
				}
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
			}		
			
			if (reporte.getVisualizarActividad())
			{
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
				{
					Iniciativa iniciativa = (Iniciativa)iter.next();
										
					numeroCelda = 1;
					fila = hoja.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue("");
					
					// Subtitulo de actividades 									
					String subTitulo = mensajes.getMessage("jsp.modulo.actividad.titulo.singular") + " : " + iniciativa.getNombre(); 
					if (subTitulo != null)
					{
						numeroCelda = 1;
						fila = hoja.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue(subTitulo);
					}
					
					numeroFila = dibujarInformacionActividad(numeroFila, nivel, reporte, iniciativa, objWB, hoja,strategosMedicionesService, strategosIndicadoresService, mensajes, request);
					
					numeroCelda = 1;
					fila = hoja.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue("");
				}	
			}
			
		}
		else
		{
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIniciativaPlural().toLowerCase(), new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			numeroCelda = 1;
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			//celda.setCellStyle(getEstiloCuerpo(objWB));
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(texto);
		}
		
		strategosPlanesService.close();
		strategosExplicacionesService.close();
		strategosPryActividadesService.close();
		
		return numeroFila;
	}
	
	private int dibujarInformacionActividad(int numeroFila, int nivel , ReporteForm reporte, Iniciativa iniciativa, Workbook objWB, Sheet hoja, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception {
				
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;
		Date fechaAh = new Date();
		Date fechaFi = new Date();
		
		Cell celda = null;
		Row fila;
		int numeroCelda = 1;
		
		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		
		List<PryActividad> actividades = strategosPryActividadesService
				.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();
		
		if (actividades.size() > 0)
		{
		    String[][] columnas = new String[10][2];
		    int contador = 0;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.actividades");
		    
		    contador++;
		    columnas[contador][0] = "100";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio");

		    contador++;
		    columnas[contador][0] = "15";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion");

		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta");

		    contador++;
		    columnas[contador][0] = "35";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado");
		    
		    contador++;
		    columnas[contador][0] = "50";
		    columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable");
		    
		    contador++;
		    columnas[contador][0] = "30";
		    columnas[contador][1] = mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.dias.diferencia");
		    
			numeroFila = crearEncabezado(columnas, numeroFila, reporte, mensajes, strategosMedicionesService, strategosIndicadoresService, objWB, hoja, request);
			
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				
				numeroCelda = 1;
				
				
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(actividad.getNombre());
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(VgcFormatter.formatearFecha(actividad.getComienzoPlan(), "formato.fecha.corta"));
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(VgcFormatter.formatearNumero(actividad.getPeso()));
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
																
				texto = null;
				//String url = obtenerCadenaRecurso(request);
				if (actividad.getAlerta() == null)
					celda.setCellValue("");
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
					texto = "Roja";					
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
					texto = "Verde";
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
					texto = "Amarillo";													    										
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);				
				celda.setCellValue(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				if(actividad.getResponsableSeguimiento() != null)
					texto = actividad.getResponsableSeguimiento().getNombre();
				else
					texto = "";
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
				
				//Dias 	
				
				texto = "";
				fechaAh = actividad.getComienzoPlan();
				fechaFi = actividad.getFinPlan();
				
				if(fechaAh != null && fechaFi != null) {	
					int milisecondsByDay = 86400000;					
					int dias = (int) ((fechaFi.getTime()-fechaAh.getTime()) / milisecondsByDay);
					texto = ""+dias;
				}else {
					texto = "";
				}
				
				celda = fila.createCell(numeroCelda++);
				//celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
			}								
		}
		return numeroFila;		
	}
	
	public Date obtenerFechaFinal(List<PryActividad> actividades) {
		Date fecha = null;		
		for(PryActividad act: actividades) {
			fecha = act.getFinPlan();			
		}		
		return fecha;
	}
	
	public Date obtenerFechaInicial(List<PryActividad> actividades) {
		Date fecha = null;		
		for(PryActividad act: actividades) {
			return act.getComienzoReal();			
		}		
		return fecha;
	}
	
	private CellStyle getEstiloEncabezado(Workbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		Font fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// Luego creamos el objeto que se encargar� de aplicar el estilo a la
		// celda
		CellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// Tambi�n, podemos establecer bordes...
		estiloCelda.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		return estiloCelda; 
	}
	
	private CellStyle getEstiloCuerpo(Workbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		Font fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// Luego creamos el objeto que se encargar� de aplicar el estilo a la
		// celda
		CellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// Tambi�n, podemos establecer bordes...
		estiloCelda.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.WHITE.index);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		return estiloCelda; 
	}
	
	private int crearEncabezado(String[][] columnas, int numeroFila, ReporteForm reporte, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, Workbook objWB, Sheet hoja, HttpServletRequest request) throws Exception
	{
		int numeroCelda = 1;
		Row fila = hoja.createRow(numeroFila++);
		Cell celda = null;
		for (int k = 0; k < columnas.length; k++)
		{
			celda = fila.createCell(k+numeroCelda);
			celda.setCellStyle(getEstiloEncabezado(objWB));

			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(columnas[k][1]);
		}
		
		return numeroFila;
	}
	
	private Explicacion BuscarExplicacion(ReporteForm reporte, String fechaMedicion, Long objetoId, Byte objetoKey, StrategosExplicacionesService strategosExplicacionesService)
	{
		Map<String, String> filtros = new HashMap<String, String>();

		String[] periodo = fechaMedicion.split("/");
		int ultimoDiaMes = PeriodoUtil.ultimoDiaMes(Integer.parseInt(periodo[0]), Integer.parseInt(periodo[1]));
		
		filtros.put("tipo", "0");
		filtros.put("objetoId", objetoId.toString());
		filtros.put("objetoKey", objetoKey.toString());
		filtros.put("fechaDesde", "01/" + periodo[0] + "/" + periodo[1]);
		filtros.put("fechaHasta", ultimoDiaMes + "/" + periodo[0] + "/" + periodo[1]);

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 100, null, null, true, filtros);
		Explicacion explicacion = null;
		if (paginaExplicaciones.getLista().size() > 0)
		{
			explicacion = (Explicacion)paginaExplicaciones.getLista().get(0);
			explicacion = (Explicacion)strategosExplicacionesService.load(Explicacion.class, explicacion.getExplicacionId());
		}
		
		return explicacion;
	}
	
	
}
