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
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.GestionarUsuariosForm;

public class ReporteUsuariosResumidoExcelAction extends VgcAction {
	
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
		
		
		String atributoOrden = request.getParameter("atributoOrden");
	    String tipoOrden = request.getParameter("tipoOrden");
	    
	    String selectCondicionType = (request.getParameter("selectType"));
	    short condicionType= Short.parseShort(selectCondicionType);
	    
	   
	    MessageResources messageResources = getResources(request);
	    
	    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
	    
	    List usuarios = usuariosService.getUsuarios(0, atributoOrden, tipoOrden).getLista();
	    Object[][] data = new Object[usuarios.size()+1][6];
	    
	    
	    	 data[0][0]=messageResources.getMessage("reporte.framework.usuarios.resumido.uid");
	    	 data[0][1]=messageResources.getMessage("reporte.framework.usuarios.resumido.fullname");
	    	 data[0][2]=messageResources.getMessage("reporte.framework.usuarios.resumido.administrador");
	    	 data[0][3]=messageResources.getMessage("reporte.framework.usuarios.resumido.estatus");
	    	 data[0][4]=messageResources.getMessage("reporte.framework.usuarios.resumido.creado");
	    	 data[0][5]=messageResources.getMessage("reporte.framework.usuarios.resumido.modificado");
	    	 int x=1;
	    	 if (usuarios.size() > 0) {
	    		 for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
	    		        Usuario usuario = (Usuario)iter.next();
	    		        switch(condicionType){
	    		        	// todos los usuarios
	    		        	case 0:
	    		        		data[x][0]=usuario.getUId();
	    	    		        data[x][1]=usuario.getFullName();
	    	    		        
	    	    		        if (usuario.getIsAdmin().booleanValue()) {
	    	    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.si");
	    	    		        } else {
	    	    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.no");
	    	    		        }
	    	    		        
	    	    		        if (usuario.getEstatus().intValue() == 0) {
	    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.activo");
	    	    		        }
	    	    		        else if (usuario.getEstatus().intValue() == 1) {
	    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.inactivo");
	    	    		        }
	    	    		        else if (usuario.getEstatus().intValue() == 2) {
	    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.deshabilitado");
	    	    		        }
	    	    		        
	    	    		        data[x][4]=VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa");
	    	    		        data[x][5]=VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa");
	    	    		        x=x+1; 
	    		        		break;
	    		        	case 1:
	    		        		if (usuario.getEstatus().intValue() == 0) {
	    		        			data[x][0]=usuario.getUId();
	    		    		        data[x][1]=usuario.getFullName();
	    		    		        
	    		    		        if (usuario.getIsAdmin().booleanValue()) {
	    		    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.si");
	    		    		        } else {
	    		    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.no");
	    		    		        }
	    		    		        
	    		    		        if (usuario.getEstatus().intValue() == 0) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.activo");
		    	    		        }
		    	    		        else if (usuario.getEstatus().intValue() == 1) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.inactivo");
		    	    		        }
		    	    		        else if (usuario.getEstatus().intValue() == 2) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.deshabilitado");
		    	    		        }  		        
	    		    		        data[x][4]=VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa");
	    		    		        data[x][5]=VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa");
	    		    		        x=x+1; 
	    		        		}
	    		        		break;
	    		        	case 2:
	    		        		if (usuario.getEstatus().intValue() == 1) {
	    		        			data[x][0]=usuario.getUId();
	    		    		        data[x][1]=usuario.getFullName();
	    		    		        
	    		    		        if (usuario.getIsAdmin().booleanValue()) {
	    		    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.si");
	    		    		        } else {
	    		    		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.no");
	    		    		        }
	    		    		        
	    		    		        if (usuario.getEstatus().intValue() == 0) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.activo");
		    	    		        }
		    	    		        else if (usuario.getEstatus().intValue() == 1) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.inactivo");
		    	    		        }
		    	    		        else if (usuario.getEstatus().intValue() == 2) {
		    	    		        	data[x][3]=VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.deshabilitado");
		    	    		        }    		        
	    		    		        data[x][4]=VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa");
	    		    		        data[x][5]=VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa");
	    		    		        x=x+1; 
	    		        		}
	    		        		break;
	    		        }
	    		        
	    		      }
	    	 
	    	 }
	    
	    	 
	    	 
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
		        
		        String header = "Reporte Usuarios Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        
		        for (int i = 0; i < data.length; ++i) {
		            HSSFRow dataRow = sheet.createRow(i + 1);

		            Object[] d = data[i];
		            String usId = (String) d[0];
		            String name = (String) d[1];
		            String admin = (String) d[2];
		            String estatus = (String) d[3];
		            String creado = (String) d[4];
		            String modificado = (String) d[5];
		            

		            dataRow.createCell(0).setCellValue(usId);
		            dataRow.createCell(1).setCellValue(name);
		            dataRow.createCell(2).setCellValue(admin);
		            dataRow.createCell(3).setCellValue(estatus);
		            dataRow.createCell(4).setCellValue(creado);
		            dataRow.createCell(5).setCellValue(modificado);
		        }
		        
		        HSSFRow dataRow = sheet.createRow(1 + data.length);   
		        
		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String ruta="UsuariosResumido_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+ruta);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
		        
	    forward="exito";
	    usuariosService.close();
	    
		/** Código de lógica de Negocio del action	*/

		/** Otherwise, return "success" */
		return mapping.findForward(forward);     	
	    
   }
	
	
}

