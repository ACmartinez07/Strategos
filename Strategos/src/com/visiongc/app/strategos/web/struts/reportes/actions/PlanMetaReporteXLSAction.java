/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;

/**
 * @author Kerwin
 *
 */
public class PlanMetaReporteXLSAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
  		Calendar fecha = Calendar.getInstance();
		reporte.setAno(request.getParameter("ano") != null ? Integer.parseInt(request.getParameter("ano")) : fecha.get(1));
		reporte.setPlanId(request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		reporte.setAcumular((request.getParameter("acumular") != null ? (request.getParameter("acumular").equals("1") ? true : false) : false));
		
		return MetaPlan(reporte, request, mensajes);
	}

	private StreamInfo MetaPlan(ReporteForm reporte, HttpServletRequest request, MessageResources mensajes) throws Exception
	{
	    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
	    reporte.setAnoInicial(plan.getAnoInicial().toString());
		reporte.setAnoFinal(reporte.getAno().toString());
		reporte.setMesInicial("1");
		reporte.setMesFinal("12");

	    Perspectiva perspectiva = null;
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
		HSSFSheet hoja1 = objWB.createSheet("Meta Plan");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.meta.titulo"));

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

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.meta.titulo.rango") + " : " + reporte.getAno());

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
				
				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);

				numeroFila = buildReporte(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}
		else
			numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectiva, objWB, hoja1, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
		
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
	
	private int buildReporte(int numeroFila, int nivel, ReporteForm reporte, Perspectiva perspectiva, HSSFWorkbook objWB, HSSFSheet hoja, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    //Lista de perspectivas del primer nivel
		String texto;
		HSSFCell celda = null;
		HSSFRow fila;
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

				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);
				
				numeroFila = buildReporte(numeroFila, (nivel + 1), reporte, perspectivaHija, objWB, hoja, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}
		else
			numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectiva, objWB, hoja, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
		
		return numeroFila;
	}
	
	private int dibujarInformacionIndicador(int numeroFila, int nivel , ReporteForm reporte, Perspectiva perspectiva, HSSFWorkbook objWB, HSSFSheet hoja, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;
		String format = "##0.00";

		HSSFCell celda = null;
		HSSFRow fila;
		int numeroCelda = 1;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();
		
		if (indicadores.size() > 0)
		{
			numeroFila = crearEncabezado(numeroFila, reporte, mensajes, strategosMedicionesService, strategosIndicadoresService, objWB, hoja, request);
			
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
			{
				Indicador indicador = (Indicador)iter.next();
				
    			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
				List<Medicion> medicionesMetas = new ArrayList<Medicion>();
				List<Meta> metas = new ArrayList<Meta>();
				if (reporte.getAcumular())
					metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), reporte.getPlanId(), Integer.parseInt(reporte.getAnoInicial()), reporte.getAno(), acumular);
				else
					metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), reporte.getPlanId(), reporte.getAno(), reporte.getAno(), acumular);
				
				for (Iterator<Meta> iterMeta = metas.iterator(); iterMeta.hasNext();)
				{
					Meta meta = (Meta)iterMeta.next();
					medicionesMetas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), meta.getMetaId().getAno(), new Integer(meta.getMetaId().getPeriodo()), meta.getMetaId().getSerieId()), (meta.getValor() != null ? new Double(meta.getValor()) : null)));
				}

				Double metaPlan = null;
				if (medicionesMetas.size() > 0 && medicionesMetas.get(0).getValor() != null)
					metaPlan = medicionesMetas.get(0).getValor();
				
				List<Medicion> mediciones = new ArrayList<Medicion>();
				if (reporte.getAcumular())
				{
			    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
			    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
			    	int periodoFin = periodoInicio; 
			    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
			    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), reporte.getAno(), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
				}
				
				if (!reporte.getAcumular())
				{
					
	    		    // Alerta
					Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
					if (metaPlan != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), metaPlan, null, null);
					}

					// Alerta
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
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);
					
					// Nombre del indicador
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
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
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);
	    		    
	    		    // Medicion
					texto = "";
					if (indicador.getUltimaMedicion() != null)
						texto = indicador.getUltimaMedicionFormateada();
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);

	    		    // Fecha Ultima Medicion
					texto = "";
					if (indicador.getFechaUltimaMedicion() != null)
						texto = indicador.getFechaUltimaMedicion();
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);
	    		    
					if (medicionesMetas.size() > 0 && medicionesMetas.get(0).getValor() != null)
					{
						// Meta Plan
						celda = fila.createCell(numeroCelda++);
						/*
						celda.setCellStyle(getEstiloCuerpo(objWB));
						*/
						celda.setCellType(HSSFCell.CELL_TYPE_STRING);
						celda.setCellValue(medicionesMetas.get(0).getValorFormateado(format));

		    		    // % Estado Meta Plan
		    		    indicador.setUltimoProgramado(metaPlan);
						celda = fila.createCell(numeroCelda++);
						/*
						celda.setCellStyle(getEstiloCuerpo(objWB));
						*/
						celda.setCellType(HSSFCell.CELL_TYPE_STRING);
						celda.setCellValue(indicador.getPorcentajeCumplimientoFormateado(null, TipoIndicadorEstado.getTipoIndicadorEstadoParcial()));
					}
					else
					{
						// Meta Plan
						celda = fila.createCell(numeroCelda++);
						/*
						celda.setCellStyle(getEstiloCuerpo(objWB));
						*/
						celda.setCellType(HSSFCell.CELL_TYPE_STRING);
						celda.setCellValue("");

		    		    // % Estado Meta Plan
						celda = fila.createCell(numeroCelda++);
						/*
						celda.setCellStyle(getEstiloCuerpo(objWB));
						*/
						celda.setCellType(HSSFCell.CELL_TYPE_STRING);
						celda.setCellValue("");
					}
				}
				else
				{
					celda.setCellStyle(getEstiloCuerpo(objWB));
					Double valor = null;
					boolean esAcumulado = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue());
					
					if (medicionesMetas.size() > 0)
					{
						if (!esAcumulado)
						{
							for (Iterator<Medicion> iterMed = medicionesMetas.iterator(); iterMed.hasNext();)
							{
								Medicion medicion = (Medicion)iterMed.next();
								if (valor == null && medicion.getValor() != null)
									valor = medicion.getValor();
								else if (valor != null && medicion.getValor() != null)
									valor = valor + medicion.getValor();
							}
						}
						else 
							valor = medicionesMetas.get((medicionesMetas.size() - 1)).getValor();
					}
	    		    indicador.setUltimoProgramado(valor);
					
					valor = null;
					Integer numeroMaximoPeriodos = null;
					Integer ano = null;
					if (mediciones.size() > 0)
					{
						if (!esAcumulado)
						{
							for (Iterator<Medicion> iterMed = mediciones.iterator(); iterMed.hasNext();)
							{
								Medicion medicion = (Medicion)iterMed.next();
								if (ano == null)
									ano = medicion.getMedicionId().getAno();
								
								if (numeroMaximoPeriodos == null || ano.intValue() != medicion.getMedicionId().getAno().intValue())
								{
									ano = medicion.getMedicionId().getAno();
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), ano);
								}
								
								if (medicion.getMedicionId().getPeriodo().intValue() == numeroMaximoPeriodos.intValue())
								{
									if (valor == null && medicion.getValor() != null)
										valor = medicion.getValor();
									else if (valor != null && medicion.getValor() != null)
										valor = valor + medicion.getValor();
								}
							}
						}
						else
							valor = mediciones.get((mediciones.size() - 1)).getValor();
					}
					indicador.setUltimaMedicion(valor);
					
	    		    // Alerta
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (indicador.getUltimoProgramado() != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), indicador.getUltimoProgramado(), null, null);
					}
					
					// Alerta
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
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);
					
					// Nombre del indicador
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
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
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);

	    		    //Meta Plan
					texto = "";
					if (indicador.getUltimoProgramado() != null)
						texto = VgcFormatter.formatearNumero(indicador.getUltimoProgramado(), 2);
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);

					// Medicion
					texto = "";
					if (indicador.getUltimaMedicion() != null)
						texto = VgcFormatter.formatearNumero(indicador.getUltimaMedicion(), 2);
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(texto);
	    		    
					// % Estado 
	    		    valor = indicador.getPorcentajeCumplimiento(null, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(VgcFormatter.formatearNumero(valor, 2));

	    		    if (valor != null && valor > 100D)
	    		    	valor = 100D;

	    		    // % Estado Suisado
					celda = fila.createCell(numeroCelda++);
					/*
					celda.setCellStyle(getEstiloCuerpo(objWB));
					*/
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(VgcFormatter.formatearNumero(valor, 2));
				}
			}
		}
		else
		{
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(), new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			numeroCelda = 1;
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellStyle(getEstiloCuerpo(objWB));
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(texto);
		}
		
		strategosPlanesService.close();
		strategosMetasService.close();
		
		return numeroFila;
	}
	
	private HSSFCellStyle getEstiloEncabezado(HSSFWorkbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		HSSFFont fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// Luego creamos el objeto que se encargará de aplicar el estilo a la
		// celda
		HSSFCellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// También, podemos establecer bordes...
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
	
	private HSSFCellStyle getEstiloCuerpo(HSSFWorkbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		HSSFFont fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// Luego creamos el objeto que se encargará de aplicar el estilo a la
		// celda
		HSSFCellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// También, podemos establecer bordes...
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
	
	private int crearEncabezado(int numeroFila, ReporteForm reporte, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, HSSFWorkbook objWB, HSSFSheet hoja, HttpServletRequest request) throws Exception
	{
	    String[][] columnas = new String[7][2];
	    StringBuilder string;
	    columnas[0][0] = "15";
	    columnas[0][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.alerta");
	    
	    columnas[1][0] = "100";
	    columnas[1][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();

	    columnas[2][0] = "15";
	    columnas[2][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.unidad");

	    if (!reporte.getAcumular())
	    {
		    columnas[3][0] = "30";
		    columnas[3][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.ejecutado");

		    columnas[4][0] = "35";
		    columnas[4][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.fechaejecutado");
		    
		    columnas[5][0] = "30";
		    columnas[5][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.meta");

		    columnas[6][0] = "35";
		    string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.porcentaje"));
			string.append("\n");
			string.append("\n");
		    columnas[6][1] = string.toString();
	    }
	    else
	    {
		    columnas[3][0] = "30";
		    columnas[3][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.meta") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();

		    columnas[4][0] = "35";
		    columnas[4][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.ejecutado") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();
		    
		    columnas[5][0] = "30";
		    columnas[5][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.podentaje.cumplimiento") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();

		    columnas[6][0] = "35";
		    string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.podentaje.cumplimiento.suavisado"));
			string.append("\n");
			string.append("\n");
		    columnas[6][1] = string.toString();
	    }
	    
		int numeroCelda = 1;
		HSSFRow fila = hoja.createRow(numeroFila++);
		HSSFCell celda = null;
		for (int k = 0; k < columnas.length; k++)
		{
			celda = fila.createCell(k+numeroCelda);
			/*
			celda.setCellStyle(getEstiloEncabezado(objWB));
			*/
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(columnas[k][1]);
		}
		
		return numeroFila;
	}
}
