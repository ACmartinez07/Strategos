package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteProyectosAsociadosXls extends VgcAction{
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);
		
			String forward = mapping.getParameter();	
		    
			MessageResources mensajes = getResources(request);
			ReporteForm reporte = new ReporteForm();
			reporte.clear();
			String source = request.getParameter("source");
			
			String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
			if(anio != "") {
				reporte.setAno(new Integer(anio));
			}else {
				reporte.setAno(null);
			}
			
			Plan infoPlan = new Plan();
			
			Calendar fecha = Calendar.getInstance();
	        Integer anoTemp = fecha.get(Calendar.YEAR);
	        Integer mes = fecha.get(Calendar.MONTH) + 1;
			
			reporte.setAnoInicial(anoTemp.toString());
			reporte.setAnoFinal(anoTemp.toString());
			reporte.setMesInicial("1");
			reporte.setMesFinal("12");
			
			
			reporte.setCooperanteId(request.getParameter("cooperante") != null && !request.getParameter("cooperante").toString().equals("") ? Long.parseLong(request.getParameter("cooperante")) : null);
			reporte.setTipoCooperanteId(request.getParameter("tipoconvenio") != null && !request.getParameter("tipoconvenio").toString().equals("") ? Long.parseLong(request.getParameter("tipoconvenio")) : null);
			reporte.setNombre(request.getParameter("nombre") != null ? request.getParameter("nombre") : "");
			reporte.setId(request.getParameter("instrumentoId") != null && !request.getParameter("instrumentoId").toString().equals("") ? Long.parseLong(request.getParameter("instrumentoId")) : null);
			
			String alcance = (request.getParameter("alcance"));		
			
			String instrumentoId = (request.getParameter("instrumentoId"));
			
			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;
			
			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			
			MessageResources messageResources = getResources(request);
				
			//instrumento seleccionado
			if(request.getParameter("alcance").equals("1")){
				
				int columna = 1;
				
				StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
				StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
				
				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);
					    	
				Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, reporte.getId());		    	
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");

				
				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(columna++);
		        
		        String header = "Listado de Proyectos por Instrumento";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        sheet.createRow(columna++);
		        
		        
		        HSSFRow dataRow = sheet.createRow(columna++);
		       		
		        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reportes.instrumento.titulo"));
		        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
		        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
		        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
		        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));		        
		        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));
		       
		        
		        HSSFRow datapRow = sheet.createRow(columna++);
					    		    	
		        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());
		        
		    	if(instrumento.getTiposConvenioId() != null) {
					TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
					if(tipoConvenio != null) {
						datapRow.createCell(1).setCellValue(tipoConvenio.getNombre()); 
					}
					
				}else {
					datapRow.createCell(1).setCellValue("");
				}
		        
		        if(instrumento.getCooperanteId() != null) {
					Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
					if(cooperante != null) {
						datapRow.createCell(2).setCellValue(cooperante.getNombre());
					}
					
				}else {
					datapRow.createCell(2).setCellValue("");
				}
		        
		       		        
		        if(instrumento.getFechaInicio() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(3).setCellValue(format.format(instrumento.getFechaInicio()));
				}else {
					datapRow.createCell(3).setCellValue("");
				}
		        
		        if(instrumento.getFechaTerminacion() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(4).setCellValue(format.format(instrumento.getFechaTerminacion()));
				}else {
					datapRow.createCell(4).setCellValue("");
				}
		        
		        		        
		        datapRow.createCell(5).setCellValue(obtenerEstatus(instrumento.getEstatus()));
		        
		        sheet.createRow(columna++);
		        
		        int pagina = 0;
			    String atributoOrden = null;
			    String tipoOrden = null;

			    if (atributoOrden == null) 
			    	atributoOrden = "nombreCorto";
			    if (tipoOrden == null) 
			    	tipoOrden = "ASC";
			    if (pagina < 1) 
			    	pagina = 1;
		        
		        //iniciativa
		        
		        StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		        		        
	    		filtros = new HashMap();
	    		
	    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
	    		
	    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);
	    		
	    		if (paginaIniciativas.getLista().size() > 0)
	    		{
	    			HSSFRow dataIni = sheet.createRow(columna++);
  			      
    				dataIni.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));    
    			    dataIni.createCell(1).setCellValue(messageResources.getMessage("jsp.visualizariniciativasplan.columna.porcentajecompletado"));
    			    dataIni.createCell(2).setCellValue(messageResources.getMessage("jsp.gestionariniciativasplan.columna.fechaUltimaMedicion"));
    			    dataIni.createCell(3).setCellValue(messageResources.getMessage("jsp.visualizariniciativasplan.columna.estatus"));
    			    dataIni.createCell(4).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
    			    dataIni.createCell(5).setCellValue(messageResources.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.nombreplan"));
    			    dataIni.createCell(6).setCellValue(messageResources.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.objetivo"));	
    			    
	    			for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext();)
	    			{
	    				Iniciativa iniciativa = (Iniciativa)iter.next();
	    				
	    				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    				
	    				Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
	    				
	    				List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
						List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
	    				
	    			    HSSFRow datapIni = sheet.createRow(columna++);
		    			   
		    			// datos iniciativa
		    				datapIni.createCell(0).setCellValue(iniciativa.getNombre());
		    				datapIni.createCell(1).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
		    				datapIni.createCell(2).setCellValue(iniciativa.getFechaUltimaMedicion());			    							    				
		    				
		    				//estatus
							if (medicionesProgramadas.size() == 0) {
								//EstatusIniciar
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
							}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
								//EstatusIniciardesfasado
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
							}					
							else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
								//EnEjecucionSinRetrasos
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
								//EnEjecucionConRetrasosSuperables
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
								//EnEjecucionConRetrasosSignificativos
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
							}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
								//EstatusConcluidas
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.concluidas"));
							}
							else if(iniciativa.getEstatusId() == 3) {
								//EstatusCancelado
								datapIni.createCell(3).setCellValue("Cancelado");
							}
							else if(iniciativa.getEstatusId() == 4) {
								//EstatusSuspendido
								datapIni.createCell(3).setCellValue("Suspendido");
							}else {
								//EstatusIniciar
								datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
							}
		    				datapIni.createCell(4).setCellValue(iniciativa.getAnioFormulacion());
		    				
		    				infoPlan = obtenerPlan(iniciativa);		
		        		    
		        		    if(infoPlan != null)    		    			        		    	
		        		    	datapIni.createCell(5).setCellValue(infoPlan.getNombre());
		        		    else {			        		    	
		        		    	datapIni.createCell(5).setCellValue("No hay plan asociado");
		        		    }
		        		    
		        		    if(infoPlan != null)    		    
		        		    	datapIni.createCell(6).setCellValue(obtenerObjetivo(iniciativa.getIniciativaId(), infoPlan.getPlanId()));
		        		    	
		        		    else if(obtenerObjetivo(iniciativa.getIniciativaId(), infoPlan.getPlanId()) != ""){
		        		    	datapIni.createCell(6).setCellValue("No hay Objetivo descrito");			        		    	
		        		    }  
	    			}
	    			sheet.createRow(columna++);	   
	    		}
	    		
		        		        
		        
		        
		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="InstrumentoProyectosAsociados_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
						
				
				forward="exito";		        
		        iniciativaservice.close(); 

				return mapping.findForward(forward);  
		        
			}			
			// Todos los intrumentos
			else {
				
				int columna = 1;
				
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");

				
				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(columna++);
		        
		        String header = "Listado de Proyectos por Instrumento";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
				
				strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
				StrategosTiposConvenioService strategosConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
				StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
				
				Map<String, String> filtrosInstru = new HashMap<String, String>();
			    int pagina = 0;
			    String atributoOrden = null;
			    String tipoOrden = null;

			    if (atributoOrden == null) 
			    	atributoOrden = "nombreCorto";
			    if (tipoOrden == null) 
			    	tipoOrden = "ASC";
			    if (pagina < 1) 
			    	pagina = 1;
			    
			    if(reporte.getNombre() != null && reporte.getNombre() != "") {
			    	filtrosInstru.put("nombreCorto", reporte.getNombre());
			    }else if(reporte.getAno() != null && reporte.getAno() != 0) {
			    	filtrosInstru.put("anio", reporte.getAno().toString());
			    }else if(reporte.getCooperanteId() != null && reporte.getCooperanteId() != 0) {
			    	filtrosInstru.put("cooperanteId", reporte.getCooperanteId().toString());
			    }else if(reporte.getTipoCooperanteId() != null && reporte.getTipoCooperanteId() != 0) {
			    	filtrosInstru.put("tiposConvenioId", reporte.getTipoCooperanteId().toString());
			    }
			        
			    
			    PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden, tipoOrden, true, filtrosInstru);
			    
			    if (paginaInstrumentos.getLista().size() > 0) 
				{
								
			    	for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext(); ) 
					{
			    		Instrumentos instrumento = (Instrumentos)iter.next();
			    		
			    		
				        
				        sheet.createRow(columna++);
				        
				        
				        HSSFRow dataRow = sheet.createRow(columna++);				       	
				        
				        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reportes.instrumento.titulo"));
				        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
				        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
				        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
				        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));		        
				        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));
				       
				        
				        HSSFRow datapRow = sheet.createRow(columna++);
							    		    	
				        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());
				        
				    	if(instrumento.getTiposConvenioId() != null) {
							TipoConvenio tipoConvenio = (TipoConvenio)strategosConvenioService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
							if(tipoConvenio != null) {
								datapRow.createCell(1).setCellValue(tipoConvenio.getNombre()); 
							}
							
						}else {
							datapRow.createCell(1).setCellValue("");
						}
				        
				        if(instrumento.getCooperanteId() != null) {
							Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
							if(cooperante != null) {
								datapRow.createCell(2).setCellValue(cooperante.getNombre());
							}
							
						}else {
							datapRow.createCell(2).setCellValue("");
						}
				        
				       		        
				        if(instrumento.getFechaInicio() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(3).setCellValue(format.format(instrumento.getFechaInicio()));
						}else {
							datapRow.createCell(3).setCellValue("");
						}
				        
				        if(instrumento.getFechaTerminacion() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(4).setCellValue(format.format(instrumento.getFechaTerminacion()));
						}else {
							datapRow.createCell(4).setCellValue("");
						}
				        				        				     
				        datapRow.createCell(5).setCellValue(obtenerEstatus(instrumento.getEstatus()));
				        
				        sheet.createRow(columna++);
				        
				        pagina = 0;
					    atributoOrden = null;
					    tipoOrden = null;					    					    

					    if (atributoOrden == null) 
					    	atributoOrden = "nombreCorto";
					    if (tipoOrden == null) 
					    	tipoOrden = "ASC";
					    if (pagina < 1) 
					    	pagina = 1;
				        
				        //iniciativa
				        
				        StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();						

				        
			    		filtros = new HashMap();
			    		
			    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
			    		
			    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);
			    		
			    		if (paginaIniciativas.getLista().size() > 0)
			    		{
			    			HSSFRow dataIni = sheet.createRow(columna++);
		    			      
		    				dataIni.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));    
		    			    dataIni.createCell(1).setCellValue(messageResources.getMessage("jsp.visualizariniciativasplan.columna.porcentajecompletado"));
		    			    dataIni.createCell(2).setCellValue(messageResources.getMessage("jsp.gestionariniciativasplan.columna.fechaUltimaMedicion"));
		    			    dataIni.createCell(3).setCellValue(messageResources.getMessage("jsp.visualizariniciativasplan.columna.estatus"));
		    			    dataIni.createCell(4).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
		    			    dataIni.createCell(5).setCellValue(messageResources.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.nombreplan"));
		    			    dataIni.createCell(6).setCellValue(messageResources.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.objetivo"));		
		    			    
			    			for (Iterator<Iniciativa> iteri = paginaIniciativas.getLista().iterator(); iteri.hasNext();)
			    			{    
			    			   HSSFRow datapIni = sheet.createRow(columna++);
			    			   Iniciativa iniciativa = (Iniciativa)iteri.next();
			    				
			    				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			    				
			    				Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			    				
			    				List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
								List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
			    				
			    				// datos iniciativa
			    				datapIni.createCell(0).setCellValue(iniciativa.getNombre());
			    				datapIni.createCell(1).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
			    				datapIni.createCell(2).setCellValue(iniciativa.getFechaUltimaMedicion());			    							    							    				
			    				
			    				//estatus
								if (medicionesProgramadas.size() == 0) {
									//EstatusIniciar
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
									//EstatusIniciardesfasado
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
								}					
								else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
									//EnEjecucionSinRetrasos
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									//EnEjecucionConRetrasosSuperables
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									//EnEjecucionConRetrasosSignificativos
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
								}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
									//EstatusConcluidas
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.concluidas"));
								}
								else if(iniciativa.getEstatusId() == 3) {
									//EstatusCancelado
									datapIni.createCell(3).setCellValue("Cancelado");
								}
								else if(iniciativa.getEstatusId() == 4) {
									//EstatusSuspendido
									datapIni.createCell(3).setCellValue("Suspendido");
								}else {
									//EstatusIniciar
									datapIni.createCell(3).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}
			    				datapIni.createCell(4).setCellValue(iniciativa.getAnioFormulacion());
			    				
			    				infoPlan = obtenerPlan(iniciativa);		
			        		    
			        		    if(infoPlan != null)    		    			        		    	
			        		    	datapIni.createCell(5).setCellValue(infoPlan.getNombre());
			        		    else {			        		    	
			        		    	datapIni.createCell(5).setCellValue("No hay plan asociado");
			        		    }
			        		    
			        		    if(infoPlan != null)    		    
			        		    	datapIni.createCell(6).setCellValue(obtenerObjetivo(iniciativa.getIniciativaId(), infoPlan.getPlanId()));
			        		    	
			        		    else if(obtenerObjetivo(iniciativa.getIniciativaId(), infoPlan.getPlanId()) != ""){
			        		    	datapIni.createCell(6).setCellValue("No hay Objetivo descrito");			        		    	
			        		    }    			  
			    			}
			    			sheet.createRow(columna++);	
			    		}			    		
					}			    	
				}
				
				
				Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="InstrumentoProyectosAsociados_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
						
				
				forward="exito";
		        
		        iniciativaservice.close();
				return mapping.findForward(forward);  
				
				
			}
	        
	}
	
	public Plan obtenerPlan(Iniciativa iniciativa) {		 
		  ArrayList<Plan> listaPlanes = new ArrayList(); 
		    if (iniciativa != null)
		      {		        		        		        
		        Set<IniciativaPlan> iniciativaPlanes = iniciativa.getIniciativaPlanes();
		        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		        for (Iterator<IniciativaPlan> iter = iniciativaPlanes.iterator(); iter.hasNext();)
		        {
		          IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
		          		         		          
		            Plan plan = (Plan)strategosPlanesService.load(Plan.class, iniciativaPlan.getPk().getPlanId());
		            OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPlanesService.load(OrganizacionStrategos.class, plan.getOrganizacionId());
		            plan.setOrganizacion(organizacion);
		            
		            listaPlanes.add(plan);		            		            		          
		        }
		        strategosPlanesService.close();    		            		          		    
		      }
		    
		    if (listaPlanes.size() > 0)
		    {
		      Plan plan = (Plan)listaPlanes.get(0);
			    return plan;		     
		    }
			return null;
		   
	}
	
	 public String obtenerObjetivo(Long iniciativaId, Long planId) throws SQLException{
		  
			String objetivo="";
			Long id=iniciativaId;
			
			if(id != null && planId != null){
			
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				
				Iniciativa ini = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(id));
				
				
				if((ini.getIniciativaPerspectivas() != null) && (ini.getIniciativaPerspectivas().size() > 0)){
					
				  IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)ini.getIniciativaPerspectivas().toArray()[0];
		          StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		          Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
		          
		        	  objetivo= perspectiva.getNombre();
		         
				}
				strategosIniciativasService.close();
			}
			
			return objetivo;
	  }
	
	public String obtenerEstatus(Byte estatus) {
		
		String nombre = "";
		
		switch(estatus) {
			case 1:
				nombre="Sin Iniciar";
				break;
			case 2:
				nombre="En Ejecucion";
				break;
			case 3:
				nombre="Cancelado";
				break;
			case 4:
				nombre="Suspendido";
				break;
			case 5:
				nombre="Culminado";
				break;
		}
		
		return nombre;
	}	
}
