package com.visiongc.app.strategos.web.struts.reportes.actions;

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
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteExplicacionInstrumentoXls extends VgcAction{
	
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
			
			String instrumentoId = (request.getParameter("instrumentoId"));
			
			
			String atributoOrden = "";
		
			int pagina = 0;
			
			if (pagina < 1) 
				pagina = 1;


			Map<String, Comparable> filtros = new HashMap<String, Comparable>();
			
			Paragraph texto;
			int columna = 1;
			
			Calendar fecha = Calendar.getInstance();
	        int anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;
	        
			MessageResources messageResources = getResources(request);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Hoja excel");

	        StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
	        StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
	        			
					
			CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(font);

	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        
	        HSSFRow headerRow = sheet.createRow(columna++);
	        
	        String header = "Reporte Explicaciones por instrumento";
	        HSSFCell cell = headerRow.createCell(1);
	        cell.setCellStyle(headerStyle);
	        cell.setCellValue(header);
	        
	        sheet.createRow(columna++);
	        
	        if ((instrumentoId != null) && (!instrumentoId.equals("")) && Long.parseLong(instrumentoId) != 0) 
				filtros.put("objetoId", instrumentoId);
			
			List<Explicacion> explicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, "fecha", "DESC", true, filtros).getLista();
					
			if(explicaciones != null && explicaciones.size() > 0) {
				
				Instrumentos instrumentos = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));

				sheet.createRow(columna++);		
		        
		        HSSFRow instrumentoRow = sheet.createRow(columna++);
		        
		        String instrumento = "Instrumento: "+ instrumentos.getNombreCorto();
		        HSSFCell celda = instrumentoRow.createCell(0);
		        celda.setCellStyle(headerStyle);
		        celda.setCellValue(instrumento);
		        
		        sheet.createRow(columna++);
		        
		    	
				
				HSSFRow dataRow = sheet.createRow(columna++);
				
				
				dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.gestionarexplicaciones.columna.autor"));
		        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.gestionarexplicaciones.columna.fecha"));
		        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.gestionarexplicaciones.columna.titulo"));
		        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.editarexplicacion.ficha.publicar"));
		        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.editarexplicacion.ficha.adjuntos"));
		        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.avance"));
		        dataRow.createCell(6).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.observacion"));
		       		
		       
		        HSSFRow datapRow = sheet.createRow(columna++);
		        
		        for(Explicacion exp: explicaciones) {
			    	
			    	if(exp.getUsuarioCreado() != null) {
			    		datapRow.createCell(0).setCellValue(exp.getUsuarioCreado().getFullName());
			    	}else {
			    		datapRow.createCell(0).setCellValue("");
			    	}
			        
			    	
			    	if(exp.getFechaFormateada() != null) {
			    		datapRow.createCell(1).setCellValue(exp.getFechaFormateada());
			        }else {
			        	datapRow.createCell(1).setCellValue("");
			        }
			    			    	    	
			    	datapRow.createCell(2).setCellValue(exp.getTitulo());
			        		    	
			    	if(exp.getPublico() == true) {
			    		datapRow.createCell(3).setCellValue("Si");
			        }else {
			        	datapRow.createCell(3).setCellValue("No");
			        }
			    	
			    	String cadena = "";
			    	
			    	if (exp.getAdjuntosExplicacion() != null)
					{
					
			    		for (Iterator<?> iter = exp.getAdjuntosExplicacion().iterator(); iter.hasNext(); )
						{
							AdjuntoExplicacion adjunto = (AdjuntoExplicacion)iter.next();
							cadena += " " + adjunto.getTitulo() + ",";
						}
						
			    		cadena = cadena.substring(0, cadena.length()-1);
					}
			    	
			    	datapRow.createCell(4).setCellValue(cadena);
			    	
			    	String memoDescripcion="";
			    	String memoCausas="";
			    	
			    	for (Iterator<?> i = exp.getMemosExplicacion().iterator(); i.hasNext(); ) 
					{
						MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
							memoDescripcion=memoExplicacion.getMemo();
						else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
							memoCausas=memoExplicacion.getMemo();
						
					}
			    	
			    	datapRow.createCell(5).setCellValue(memoDescripcion);
			    	datapRow.createCell(6).setCellValue(memoCausas);
			       			        
			    }
		        
		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="ExplicacionesInstrumento_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
							
			}
									
			
			forward="exito";
			strategosExplicacionesService.close();
			strategosInstrumentosService.close(); 
	        /** Código de lógica de Negocio del action	*/

			/** Otherwise, return "success" */
			return mapping.findForward(forward);  
	        
	}
	
	

	
}
