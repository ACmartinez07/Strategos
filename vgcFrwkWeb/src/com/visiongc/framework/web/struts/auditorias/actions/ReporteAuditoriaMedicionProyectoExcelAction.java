package com.visiongc.framework.web.struts.auditorias.actions;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import jxl.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.util.MessageResources;
import org.hibernate.cfg.Environment;

import java.io.FileOutputStream;
import java.math.BigDecimal;


import com.lowagie.text.Document;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;

public class ReporteAuditoriaMedicionProyectoExcelAction extends VgcAction {
	
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {
		navBar.agregarUrl(url, nombre);
	}
	  
	
	  public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

	
		super.execute(mapping, form, request, response);
			
		String forward = mapping.getParameter();	
		
	    MessageResources messageResources = getResources(request);
	    
	    AuditoriaMedicionService auditoriaMedicionService = FrameworkServiceFactory.getInstance().openAuditoriaMedicionService();
	    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		  
		List<AuditoriaMedicion> auditorias = new ArrayList();
		
		Map<String, Object> filtros = new HashMap();
	    
		
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");
		String organizacionId = request.getParameter("organizacionId");
				
		String atributoOrden = "";	    
	    String tipoOrden = "ASC";
		
			    	      
	    String[] ordenArray = new String[1];
	    String[] tipoOrdenArray = new String[1];
	    ordenArray[0] = atributoOrden;
	    tipoOrdenArray[0] = tipoOrden;
	      
	    if ((fechaDesde != null) && (!fechaDesde.equals("")))
	        filtros.put("fechaDesde", FechaUtil.convertirStringToDate(fechaDesde, VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((fechaHasta != null) && (!fechaHasta.equals("")))
	        filtros.put("fechaHasta", FechaUtil.convertirStringToDate(fechaHasta, VgcResourceManager.getResourceApp("formato.fecha.corta")));


	    if(organizacionId != null && organizacionId != "") {
	    	
	    	OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));
	    		    	
	    	if ((organizacion != null) && (!organizacion.getNombre().equals("")))
		    	filtros.put("organizacion", organizacion.getNombre());
	    }
		 
		auditorias= auditoriaMedicionService.getAuditoriasMedicion(ordenArray, tipoOrdenArray, true, filtros);
		
		 
		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("Auditoria Medición por Proyecto");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++); 
		
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detallado.titulo"));

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		for(Iterator<AuditoriaMedicion> iter = auditorias.iterator(); iter.hasNext(); ){
			
			AuditoriaMedicion auditoria = iter.next();
			
			if(!auditoria.getIniciativa().equals("")) {
				
				// Creamos la celda, aplicamos el estilo y definimos
				// el tipo de dato que contendrá la celda
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.organizacion"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.proyecto.titulo.usuario"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.iniciativa"));
				
					
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(auditoria.getOrganizacion());
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(auditoria.getUsuario());
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(auditoria.getIniciativa());
				
				//detalle
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.accion"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.ano"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.periodo"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.valor.actual"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.serie"));
				
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.valor.anterior"));
				
				List<AuditoriaDetalleMedicion> auditoriasDetalle = new ArrayList(); 
				    
				auditoriasDetalle=obtenerDetalles(auditoria);
				
							
				for(AuditoriaDetalleMedicion aud: auditoriasDetalle){
				
					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(aud.getAccion());
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(aud.getAno().toString());
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(aud.getPeriodo().toString());
					
					Double valor= aud.getValor();
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(Long.toString(valor.longValue()));
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(aud.getSerieNombre());
					
					Double valorAnt= aud.getValorAnterior();
					
					if(aud.getAccion().equals("Inserción")){
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue("");
					}else{
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(Long.toString(valorAnt.longValue()));
					}
					
					
					
						    	    	
			    }
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
				
			}
			
		}
	
		      
        Date date = new Date();
        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
        
        String ruta="AuditoriaDetallado_"+hourdateFormat.format(date)+".xls"; 
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+ruta);    
       
        ServletOutputStream file  = response.getOutputStream();
      
        objWB.write(file);
        file.close();
		
	   
	   
	    forward="exito";
		 
			
		/** Código de lógica de Negocio del action	*/

		/** Otherwise, return "success" */
		return mapping.findForward(forward);     	  	
	    
   }
	
	  private List<AuditoriaDetalleMedicion> obtenerDetalles(AuditoriaMedicion auditoria){
		  
		  List<AuditoriaDetalleMedicion> auditorias = new ArrayList();
		  
		  String detalle = auditoria.getDetalle();
		    
		  String[] cadena= detalle.split("]");
		    
		    	for(int x=0; x<cadena.length; x++){
		    		String[] cad = cadena[x].split(":");
		    		AuditoriaDetalleMedicion auditoriaDetalle = new AuditoriaDetalleMedicion();
		    		auditoriaDetalle.setAuditoriaMedicionId(new Long(auditoria.getAuditoriaMedicionId()));
		    		List<String> detalles = obtenerDetalle(auditoriaDetalle, cad);
		    		
		    		if(detalles.size() >0){
		    			auditoriaDetalle.setAno(new Integer(detalles.get(0)));
		    			auditoriaDetalle.setPeriodo(new Integer(detalles.get(1)));
		    			auditoriaDetalle.setValor(new Double(detalles.get(2)).longValue());
		    			auditoriaDetalle.setSerie(detalles.get(3));
		    			auditoriaDetalle.setSerieNombre(obtenerNombreSerie(auditoriaDetalle.getSerie()));
		    			auditoriaDetalle.setAccion(detalles.get(4));
		    			if(detalles.size() == 6){
		    				auditoriaDetalle.setValorAnterior(new Double(detalles.get(5)).longValue());
		    			}
		    			auditorias.add(auditoriaDetalle);
		    		}
		    		    		
		    	}
		  
		  return auditorias;
	  }
	  
	  private List<String> obtenerDetalle(AuditoriaDetalleMedicion auditoriaDetalle, String[] cad){
			 
			 int index=0; 
			 int cont=1;
			 String signo =",";
			 List<String> detalles = new ArrayList();		
			 
			 for(int x=0; x<cad.length; x++) {
				String cadena= cad[x];
				String valor= "";
				index=cadena.indexOf(signo);
				
				if(index >0){
					valor = cadena.substring(0, index);
					detalles.add(valor);
				}else if(cont == cad.length){
					valor = cadena;
					detalles.add(valor);
				}
				cont++;
			 }
			  
			 return detalles; 
	  }
		  
	  private String obtenerNombreSerie(String serie){
			  Long serieId = new Long(serie);
			  
			  String serieNombre = "";
			  
			  if(serieId == SerieTiempo.getSerieRealId().longValue()){
				serieNombre = "Real";  
			  }else if(serieId == SerieTiempo.getSerieProgramadoId().longValue()){
				serieNombre = "Programado";  
			  }else if(serieId == SerieTiempo.getSerieMetaId().longValue()){
				serieNombre = "Meta";  
			  }
			  	  
			  return serieNombre;
			  
	  }
	
}
