package com.visiongc.framework.web.struts.actions.usuarios;

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
import java.util.Iterator;
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
import org.apache.struts.util.MessageResources;
import org.hibernate.cfg.Environment;

import java.io.FileOutputStream;
import java.math.BigDecimal;


import com.lowagie.text.Document;

import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;

public class ReporteUsuarioDetalladoExcelAction extends VgcAction {
	
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegación válido
		 */

		navBar.agregarUrl(url, nombre);
	}
	 
	
	  public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);
			
		String forward = mapping.getParameter();	
		
		
	    MessageResources messageResources = getResources(request);
	    UsuariosService us = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
	    Usuario usuario = (Usuario)us.load(Usuario.class, new Long(request.getParameter("usuarioId")));
	    
	    // parametros excel
	    
	   Object[][] data = new Object[7][2];
	   
	   
	    // hoja 
	  
	    
	   data[0][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.nombrecompleto"));
	   data[0][1]= String.valueOf(usuario.getFullName());
	   
	   data[1][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.loginusuario"));
	   data[1][1]= String.valueOf(usuario.getUId());
	  
	       
	    // detalles con informacion de la tabla
	    
	   	if (usuario.getCreado() != null) {
	   	  data[2][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.creado"));
		  data[2][1]= String.valueOf(VgcFormatter.formatearFecha(usuario.getCreado(), "formato.timestamp"));	
	    }
	    if (usuario.getModificado() != null) {
	    	
	      data[3][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.modificado"));
		  data[3][1]= String.valueOf(VgcFormatter.formatearFecha(usuario.getModificado(), "formato.timestamp"));	
	    }
	    
	    String detalle = "";
	    if (usuario.getIsAdmin().booleanValue()) {
	      detalle = getResources(request).getMessage("commons.yes");
	    } else {
	      detalle = getResources(request).getMessage("commons.no");
	    }
	    
	    data[4][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.esadministrador"));
		data[4][1]= String.valueOf(detalle);	
		

	    if (usuario.getEstatus().intValue() == 0) {
	      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.activo");
	    }
	    else if (usuario.getEstatus().intValue() == 1) {
	      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.inactivo");
	    }
	    else if (usuario.getEstatus().intValue() == 2) {
	      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.deshabilitado");
	    }
	    data[5][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.estatus"));
		data[5][1]= String.valueOf(detalle);
	    
	    if (usuario.getBloqueado().booleanValue()) {
	      detalle = getResources(request).getMessage("commons.yes");
	    } else {
	      detalle = getResources(request).getMessage("commons.no");
	    }
	    data[6][0]= String.valueOf(messageResources.getMessage("reporte.framework.usuarios.detallado.bloqueado"));
		data[6][1]= String.valueOf(detalle);
	    
		// tabla excel
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
		        
		        HSSFRow headerRow = sheet.createRow(0);
		        
		        String header = "Datos generales";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        
		        for (int i = 0; i < data.length; ++i) {
		            HSSFRow dataRow = sheet.createRow(i + 1);

		            Object[] d = data[i];
		            String descripcion = (String) d[0];
		            String valor = (String) d[1];
		            

		            dataRow.createCell(0).setCellValue(descripcion);
		            dataRow.createCell(1).setCellValue(valor);
		            
		        }
		        
		        
		
		// Grupos 
		
		if (!usuario.getIsAdmin().booleanValue())
	    {
			Set grupos = usuario.getUsuarioGrupos();
			Object[][] dataGrupos = new Object[grupos.size()+1][2];
			if(grupos.size()>0){
				
				dataGrupos[0][0]=messageResources.getMessage("reporte.framework.usuarios.detallado.organizacion");
			    dataGrupos[0][1]=messageResources.getMessage("reporte.framework.usuarios.detallado.grupopermisos");
			    Long organizacionId = null;
			    String nombreOrganizacion = "";
			    boolean hayGrupos = false;
			    ArrayList nombresGrupos = null;
			    int x=0;
			    int y=0;
			    
			    
			    for (Iterator i = grupos.iterator(); i.hasNext();) {
			        UsuarioGrupo ug = (UsuarioGrupo)i.next();
			        
			        if (ug.getOrganizacion().getOrganizacionId() != organizacionId) {
			          hayGrupos = true;
			          if ((nombresGrupos != null) && (nombresGrupos.size() > 0)) {
			            for (Iterator iter = nombresGrupos.iterator(); iter.hasNext();) {
			            	dataGrupos[y+1][0]=nombreOrganizacion;
			            	dataGrupos[y+1][1]=(iter.next());
			            	y=y+1;
			            }
			          }
			          Organizacion org = ug.getOrganizacion();
			          organizacionId = org.getOrganizacionId();
			          nombreOrganizacion = "";
			          while (org.getPadreId() != null) {
			            nombreOrganizacion = org.getNombre() + " \\ " + nombreOrganizacion;
			            org = org.getPadre();
			          }
			          nombreOrganizacion = org.getNombre() + " \\ " + nombreOrganizacion;
			          nombreOrganizacion = nombreOrganizacion.substring(0, nombreOrganizacion.length() - 3);
			          
			          nombresGrupos = new ArrayList();
			        }
			        nombresGrupos.add(ug.getGrupo().getGrupo());
			      }
			    
			    ///////
			    
			    if ((nombresGrupos != null) && (nombresGrupos.size() > 0)) {
			    	
			    	
			        for (Iterator iter = nombresGrupos.iterator(); iter.hasNext();) {
			        	dataGrupos[y+1][0]=nombreOrganizacion;
			        	dataGrupos[y+1][1]=(iter.next());
		            	y=y+1;
			        }
			     }
			    
			}
			
			int cont=data.length+1;
			
			
			for (int i =0; i < dataGrupos.length; ++i) {
	            HSSFRow dataRow = sheet.createRow(cont + 1);

	            Object[] d = dataGrupos[i];
	            String org = (String) d[0];
	            String grup = (String) d[1];
	            
	            dataRow.createCell(0).setCellValue(org);
	            dataRow.createCell(1).setCellValue(grup);
	            
	            cont=cont+1;
	        }
			
			
			
	    }
		
		

        
        HSSFRow dataRow = sheet.createRow(1 + data.length);   
      
        Date date = new Date();
        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
        
        String ruta="UsuarioDetallado_"+hourdateFormat.format(date)+".xls"; 
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+ruta);    
       
        ServletOutputStream file  = response.getOutputStream();
      
        workbook.write(file);
        file.close();
		
	   
	   
	    forward="exito";
		 
		us.close();
		
		
		
		/** Código de lógica de Negocio del action	*/

		/** Otherwise, return "success" */
		return mapping.findForward(forward);     	
	    
   }
	
	
}
