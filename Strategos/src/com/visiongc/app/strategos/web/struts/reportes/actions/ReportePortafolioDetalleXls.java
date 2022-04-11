package com.visiongc.app.strategos.web.struts.reportes.actions;

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
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
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
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReportePortafolioDetalleXls extends VgcAction{
	
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
			String portafolioId = (request.getParameter("portafolioId"));
			String filtroNombre = (request.getParameter("filtroNombre"));
			
			int columna = 1;
			
			Calendar fecha = Calendar.getInstance();
	        int anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;
	       	       						
			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;
						
	        MessageResources messageResources = getResources(request);
		
			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
			StrategosPortafoliosService portafoliosservice = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
			
			Portafolio portafolio = (Portafolio)portafoliosservice.load(Portafolio.class,new Long(portafolioId));	
			
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
	        
	        String header = "Reporte Portafolio Detallado";
	        HSSFCell cell = headerRow.createCell(1);
	        cell.setCellStyle(headerStyle);
	        cell.setCellValue(header);
	        
	        sheet.createRow(columna++);
	        
	        HSSFRow dataRow = sheet.createRow(columna++);
	       
	        
	        dataRow.createCell(0).setCellValue(messageResources.getMessage("action.reportecomiteejecutivo.organizacion"));
	        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.reportes.plan.consolidado.reporte.columna.nombre"));
	        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.gestionarportafolio.columna.porcentajecompletado"));
	        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.gestionarportafolio.columna.ultimoperiodocalculo"));
	        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.gestionarportafolio.columna.estatus"));
	        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.gestionarportafolio.columna.frecuencia"));
	       		
	        HSSFRow datapRow = sheet.createRow(columna++);
			
	        	
			if(portafolio.getOrganizacion() != null) {
				datapRow.createCell(0).setCellValue(portafolio.getOrganizacion().getNombre());
	        }else {
	        	datapRow.createCell(0).setCellValue("");
	        }
	        
			datapRow.createCell(1).setCellValue(portafolio.getNombre());
	       	        
			datapRow.createCell(2).setCellValue(portafolio.getFechaUltimoCalculo());
					
	        
	        if(portafolio.getPorcentajeCompletadoFormateado() != null) {
	        	datapRow.createCell(3).setCellValue(portafolio.getPorcentajeCompletadoFormateado());
	        }else {
	        	datapRow.createCell(3).setCellValue("");
	        }
	        
	        if(portafolio.getEstatus().getNombre() != null) {
	        	datapRow.createCell(4).setCellValue(portafolio.getEstatus().getNombre());
	        }else {
	        	datapRow.createCell(4).setCellValue("");
	        }
	        
	        if(portafolio.getFrecuenciaNombre() != null) {
	        	datapRow.createCell(5).setCellValue(portafolio.getFrecuenciaNombre());
	        }else {
	        	datapRow.createCell(5).setCellValue("");
	        }
			
			
	        List<PortafolioIniciativa> iniciativasPortafolio = portafoliosservice.getIniciativasPortafolio(new Long(portafolioId));
			
			
			for(PortafolioIniciativa iniX: iniciativasPortafolio) {
				
				//iniciativa
				
				Long iniciativaId = iniX.getPk().getIniciativaId();
				PryProyecto proyecto = null;
				
				Iniciativa iniciativa = (Iniciativa)iniciativaservice.load(Iniciativa.class, iniciativaId);
							
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
				
				Indicador indicador = (Indicador)iniciativaservice.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
				
				if (iniciativa.getProyectoId() != null)
				{
					StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
					proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
					strategosPryProyectosService.close();
				}
				
				sheet.createRow(columna++);
				
				HSSFRow datosRow = sheet.createRow(columna++);
				
				datosRow.createCell(0).setCellValue("Iniciativa: "+ iniciativa.getNombre());
				
				sheet.createRow(columna++);
				
				datosRow = sheet.createRow(columna++);
				
				datosRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
		        datosRow.createCell(1).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
		        datosRow.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
		        datosRow.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
		        datosRow.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
		        datosRow.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));
		        
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
		        
			    datosRow = sheet.createRow(columna++);
			    
				if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
					datosRow.createCell(0).setCellValue(indicador.getUnidad().getNombre());
			    else
			    	datosRow.createCell(0).setCellValue("");
				
				datosRow.createCell(1).setCellValue(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
				datosRow.createCell(2).setCellValue(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
				
				datosRow.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
				datosRow.createCell(4).setCellValue(VgcFormatter.formatearNumero(programado));
				
				if(iniciativa.getResponsableSeguimiento() != null) {
					datosRow.createCell(5).setCellValue(iniciativa.getResponsableSeguimiento().getNombre());
				}
				
				
				sheet.createRow(columna++);
				
				
				List<PryActividad> actividades = new ArrayList<PryActividad>();
				
				if(iniciativa.getProyectoId() != null){
					actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
					
					if(actividades != null && actividades.size() > 0) {
						
						datosRow = sheet.createRow(columna++);
						
						datosRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reporte.actividad.nombre.actividad"));
						datosRow.createCell(1).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
				        datosRow.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
				        datosRow.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
				        datosRow.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
				        datosRow.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
				        datosRow.createCell(6).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
				        datosRow.createCell(7).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
				        datosRow.createCell(8).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));
						
						
						for(PryActividad act: actividades) {
							
							
					        datosRow = sheet.createRow(columna++); 				        
					        					        
					        datosRow.createCell(0).setCellValue(act.getNombre());
					        if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
					        	datosRow.createCell(1).setCellValue(indicador.getUnidad().getNombre());					    	
						    else
						    	datosRow.createCell(1).setCellValue("");
					        
					        datosRow.createCell(2).setCellValue(VgcFormatter.formatearFecha(act.getComienzoPlan(),"formato.fecha.corta"));
					        datosRow.createCell(3).setCellValue(VgcFormatter.formatearFecha(act.getFinPlan(), "formato.fecha.corta"));
					        datosRow.createCell(4).setCellValue(VgcFormatter.formatearNumero(act.getPeso()));
					        datosRow.createCell(5).setCellValue(VgcFormatter.formatearNumero(act.getDuracionPlan()));
					        
					        datosRow.createCell(6).setCellValue(act.getPorcentajeEjecutado() != null ? act.getPorcentajeEjecutadoFormateado() : "");
					        datosRow.createCell(7).setCellValue(act.getPorcentajeEsperado() != null ? act.getPorcentajeEsperadoFormateado() : "");
					        
					        if(act.getResponsableSeguimiento() != null) {
					        	datosRow.createCell(8).setCellValue(act.getResponsableSeguimiento().getNombre());
					        }else {
					        	datosRow.createCell(8).setCellValue("");
					        }
					        
							
					        
						}
						
					}
				}
			    
			    
				
			}
			
			
	        
	        Date date = new Date();
	        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
	       
	        
	        String archivo="PortafolioDetallado_"+hourdateFormat.format(date)+".xls"; 
	        
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
	
	public String obtenerObjetivo(Iniciativa iniciativa) throws SQLException{
		String objetivo=null;
		Long id=iniciativa.getIniciativaId();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		
		if((iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0)){
			
			IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iniciativa.getIniciativaPerspectivas().toArray()[0];
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
            Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
            
            objetivo= perspectiva.getNombre();
        
		}
		return objetivo;
	}
	
	public Date obtenerFechaFinal(List<PryActividad> actividades) {
		
		Date fecha = null;
		
		for(PryActividad act: actividades) {
			
			fecha = act.getFinPlan();
			
		}
		
		return fecha;
	}
	
	public Boolean tieneEstatus(Iniciativa iniciativa, List<Medicion> medicionesEjecutadas, List<Medicion> medicionesProgramadas, int estatus){
		
		Boolean tiene=false;
		//estatus
		if (medicionesProgramadas.size() == 0 && estatus == 0) {
			//EstatusIniciar
			tiene = true;
		}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0 && estatus == 1) {
			//EstatusIniciardesfasado
			tiene = true;
		}					
		else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D && estatus == 2) {
			//EnEjecucionSinRetrasos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue() && estatus == 3) {
			//EnEjecucionConRetrasosSuperables
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue() && estatus == 4) {
			//EnEjecucionConRetrasosSignificativos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D && estatus == 5) {
			//EstatusConcluidas
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 3 && estatus == 6) {
			//EstatusCancelado
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 4 && estatus == 7) {
			//EstatusSuspendido
			tiene = true;
		}else if(iniciativa.getEstatusId() == 1  && estatus == 0) {
			//EstatusSuspendido
			tiene = true;
		}
		
		return tiene;
	}
	
}
