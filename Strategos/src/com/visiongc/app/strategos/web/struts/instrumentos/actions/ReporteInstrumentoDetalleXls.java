package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
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
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteInstrumentoDetalleXls extends VgcAction{
	
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegación válido
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
			String alcance = (request.getParameter("alcance"));
			String instrumentoId = (request.getParameter("instrumentoId"));
			
			
			Calendar fecha = Calendar.getInstance();
	        int anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;
			
			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;
			
			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			
			MessageResources messageResources = getResources(request);
				
			//
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
					    	
				Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));
		    	
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
		        
		        String header = "Reporte Instrumento Detallado";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        sheet.createRow(columna++);
		        
		        HSSFRow dataRow = sheet.createRow(columna++);
		       
		        
		        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.nombre"));
		        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
		        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.objetivo"));
		        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.productos"));
		        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.marco"));
		        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
		        dataRow.createCell(6).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
		        dataRow.createCell(7).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.anio"));
		        dataRow.createCell(8).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
		        dataRow.createCell(9).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));
		        dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.prorroga"));
		        dataRow.createCell(11).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));
		        dataRow.createCell(12).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.recursos.pesos"));
		        dataRow.createCell(13).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.recursos.dolares"));
		        dataRow.createCell(14).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.nombre.ejecutante"));
		        dataRow.createCell(15).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.responsable.pgn"));
		        dataRow.createCell(16).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.responsable.cgi"));
		        dataRow.createCell(17).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.unidad"));
		        dataRow.createCell(18).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.observaciones"));
		        
		        HSSFRow datapRow = sheet.createRow(columna++);
					
		        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());
		        datapRow.createCell(1).setCellValue(instrumento.getNombreInstrumento());
		        datapRow.createCell(2).setCellValue(instrumento.getObjetivoInstrumento());
		        datapRow.createCell(3).setCellValue(instrumento.getProductos());
		        datapRow.createCell(4).setCellValue(instrumento.getInstrumentoMarco());
		    	
		    	if(instrumento.getTiposConvenioId() != null) {
					TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
					if(tipoConvenio != null) {
						datapRow.createCell(5).setCellValue(tipoConvenio.getNombre()); 
					}
					
				}else {
					datapRow.createCell(5).setCellValue("");
				}
		        
		        if(instrumento.getCooperanteId() != null) {
					Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
					if(cooperante != null) {
						datapRow.createCell(6).setCellValue(cooperante.getNombre());
					}
					
				}else {
					datapRow.createCell(6).setCellValue("");
				}
		        
		        datapRow.createCell(7).setCellValue(instrumento.getAnio());
		        
		        if(instrumento.getFechaInicio() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(8).setCellValue(format.format(instrumento.getFechaInicio()));
				}else {
					datapRow.createCell(8).setCellValue("");
				}
		        
		        if(instrumento.getFechaTerminacion() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(9).setCellValue(format.format(instrumento.getFechaTerminacion()));
				}else {
					datapRow.createCell(9).setCellValue("");
				}
		        
		        if(instrumento.getFechaProrroga() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(10).setCellValue(format.format(instrumento.getFechaProrroga()));
				}else {
					datapRow.createCell(10).setCellValue("");
				}
		    	
		        datapRow.createCell(11).setCellValue(obtenerEstatus(instrumento.getEstatus()));
		        datapRow.createCell(12).setCellValue(instrumento.getRecursosPesos().toString());
		        datapRow.createCell(13).setCellValue(instrumento.getRecursosDolares().toString());
		        datapRow.createCell(14).setCellValue(instrumento.getNombreEjecutante());
		        
		        datapRow.createCell(15).setCellValue(instrumento.getNombreReposnsableAreas());
		        datapRow.createCell(16).setCellValue(instrumento.getResponsableCgi());
		        datapRow.createCell(17).setCellValue(instrumento.getAreasCargo());
		        datapRow.createCell(18).setCellValue(instrumento.getObservaciones());
		        
		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="InstrumentoDetallado_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
						
				
				forward="exito";
		        organizacionservice.close();
		        iniciativaservice.close(); 
		        /** Código de lógica de Negocio del action	*/

				/** Otherwise, return "success" */
				return mapping.findForward(forward);  
		        
			}			
			else {
				
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
		        
		        String header = "Reporte Instrumento Detallado";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        sheet.createRow(columna++);
				
				List<Instrumentos> instrumentos = strategosInstrumentosService.getInstrumentos(0, 0, "nombreCorto", "ASC", true, filtros).getLista();       
			    
				if (instrumentos.size() > 0)
				{
					for (Iterator<Instrumentos> iter = instrumentos.iterator(); iter.hasNext();)
					{
						Instrumentos instrumento = (Instrumentos)iter.next();
						
						HSSFRow dataRow = sheet.createRow(columna++);
					       
				        
				        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.nombre"));
				        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
				        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.objetivo"));
				        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.productos"));
				        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.marco"));
				        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
				        dataRow.createCell(6).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
				        dataRow.createCell(7).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.anio"));
				        dataRow.createCell(8).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
				        dataRow.createCell(9).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));
				        dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.prorroga"));
				        dataRow.createCell(11).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));
				        dataRow.createCell(12).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.recursos.pesos"));
				        dataRow.createCell(13).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.recursos.dolares"));
				        dataRow.createCell(14).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.nombre.ejecutante"));
				        dataRow.createCell(15).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.responsable.pgn"));
				        dataRow.createCell(16).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.responsable.cgi"));
				        dataRow.createCell(17).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.unidad"));
				        dataRow.createCell(18).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.observaciones"));
				        
				        HSSFRow datapRow = sheet.createRow(columna++);
							
				        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());
				        datapRow.createCell(1).setCellValue(instrumento.getNombreInstrumento());
				        datapRow.createCell(2).setCellValue(instrumento.getObjetivoInstrumento());
				        datapRow.createCell(3).setCellValue(instrumento.getProductos());
				        datapRow.createCell(4).setCellValue(instrumento.getInstrumentoMarco());
				    	
				    	if(instrumento.getTiposConvenioId() != null) {
							TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
							if(tipoConvenio != null) {
								datapRow.createCell(5).setCellValue(tipoConvenio.getNombre()); 
							}
							
						}else {
							datapRow.createCell(5).setCellValue("");
						}
				        
				        if(instrumento.getCooperanteId() != null) {
							Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
							if(cooperante != null) {
								datapRow.createCell(6).setCellValue(cooperante.getNombre());
							}
							
						}else {
							datapRow.createCell(6).setCellValue("");
						}
				        
				        datapRow.createCell(7).setCellValue(instrumento.getAnio());
				        
				        if(instrumento.getFechaInicio() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(8).setCellValue(format.format(instrumento.getFechaInicio()));
						}else {
							datapRow.createCell(8).setCellValue("");
						}
				        
				        if(instrumento.getFechaTerminacion() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(9).setCellValue(format.format(instrumento.getFechaTerminacion()));
						}else {
							datapRow.createCell(9).setCellValue("");
						}
				        
				        if(instrumento.getFechaProrroga() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(10).setCellValue(format.format(instrumento.getFechaProrroga()));
						}else {
							datapRow.createCell(10).setCellValue("");
						}
				    	
				        datapRow.createCell(11).setCellValue(obtenerEstatus(instrumento.getEstatus()));
				        datapRow.createCell(12).setCellValue(instrumento.getRecursosPesos().toString());
				        datapRow.createCell(13).setCellValue(instrumento.getRecursosDolares().toString());
				        datapRow.createCell(14).setCellValue(instrumento.getNombreEjecutante());
				        
				        datapRow.createCell(15).setCellValue(instrumento.getNombreReposnsableAreas());
				        datapRow.createCell(16).setCellValue(instrumento.getResponsableCgi());
				        datapRow.createCell(17).setCellValue(instrumento.getAreasCargo());
				        datapRow.createCell(18).setCellValue(instrumento.getObservaciones());
					}
				}
				
				Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="InstrumentoDetallado_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
						
				
				forward="exito";
		        organizacionservice.close();
		        iniciativaservice.close(); 
		        /** Código de lógica de Negocio del action	*/

				/** Otherwise, return "success" */
				return mapping.findForward(forward);  
				
				
			}
	        
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
