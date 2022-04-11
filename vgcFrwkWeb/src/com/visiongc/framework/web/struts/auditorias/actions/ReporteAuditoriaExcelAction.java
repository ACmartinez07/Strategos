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
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.util.AuditoriaDetalle;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;

public class ReporteAuditoriaExcelAction extends VgcAction {
	
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
	    
	    AuditoriasService auditoriaService = FrameworkServiceFactory.getInstance().openAuditoriasService();
		  
		List<Auditoria> auditorias = new ArrayList();
		
		Map<String, Object> filtros = new HashMap();
	    
		
		String usuario = request.getParameter("usuario");
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");
		String accion = request.getParameter("accion");
		String entidad = request.getParameter("entidad");
		
		String atributoOrden = request.getParameter("atributoOrden");
	    
	    String tipoOrden = request.getParameter("tipoOrden");
		
	    String[] ordenArray = new String[1];
	    String[] tipoOrdenArray = new String[1];
	    ordenArray[0] = atributoOrden;
	    tipoOrdenArray[0] = tipoOrden;
	      
	    if ((fechaDesde != null) && (!fechaDesde.equals("")))
	        filtros.put("fechaDesde", FechaUtil.convertirStringToDate(fechaDesde, VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((fechaHasta != null) && (!fechaHasta.equals("")))
	        filtros.put("fechaHasta", FechaUtil.convertirStringToDate(fechaHasta, VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((usuario != null) && (!usuario.equals("")))
	    	filtros.put("usuario", usuario);
	    if ((accion != null) && (!accion.equals("")))
	    	filtros.put("tipoEvento", accion);
	    if ((entidad != null) && (!entidad.equals("")))
	    	filtros.put("entidad", entidad);
		 
		auditorias= auditoriaService.getAuditorias(ordenArray, tipoOrdenArray, true, filtros);
		 
		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("Auditoria ");

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
		
		for(Iterator<Auditoria> iter = auditorias.iterator(); iter.hasNext(); ){
			
			Auditoria auditoria = iter.next();
			
			

			// Creamos la celda, aplicamos el estilo y definimos
			// el tipo de dato que contendrá la celda
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.fecha"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.usuario"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.objeto"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.accion"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(messageResources.getMessage("reporte.framework.auditorias.detalle.clase"));
			
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(VgcFormatter.formatearFecha(auditoria.getFechaEjecucion(), "dd-MM-yyyy hh:mm:ss aa"));
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(auditoria.getUsuario());
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(auditoria.getEntidad());
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(auditoria.getTipoEvento());
			
			celda = fila.createCell(numeroCelda++);
			celda.setCellValue(auditoria.getClaseEntidad());
				
			
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
			
			
			String cadena = auditoria.getDetalle();
			String[] cad = cadena.split(",");
			
			
			String signo =":";
			int index=0;
			int size=0;
			
			List<String> atributos = new ArrayList();
			List<String> valores = new ArrayList();
			
			
			// obtener detalle
			 
			 for(int x=0; x<cad.length; x++) {
				 
											
				String detalle= cad[x];

				index=detalle.indexOf(signo);
				size=detalle.length();
				
				String valor ="";
				String atributo ="";
				
				
				

				if(x==0) {
					atributo=detalle.substring(1,index);
				}else{
					atributo=detalle.substring(0,index);
				}
				
				atributos.add(atributo);
				
				if(x == cad.length-1) {
					valor= detalle.substring(index+1, size-1);
				}else{
					valor= detalle.substring(index+1, size);
				}
				
				valores.add(valor);
								
				
			 }
			 
			 
			 
			 // imprimir detalle
			 
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
				
				
			 
			 for(int x=0; x<atributos.size(); x++) {
				 
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(atributos.get(x));
					
				 
			 }
			 
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
				
			 
			 for(int x=0; x<valores.size(); x++) {
				 
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(valores.get(x));
				
			 }
			 
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
		}
	
		      
        Date date = new Date();
        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
        
        String ruta="Auditoria_"+hourdateFormat.format(date)+".xls"; 
        
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
	
	 
	
}
