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
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;

public class ReporteUsuarioGrupoExcelAction extends VgcAction {
	
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
	    
	    String estatus = request.getParameter("estatus");
		String grupo = request.getParameter("selectGrupos");
		 
		UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
		 
		String atributoOrden = "";
		    
		String tipoOrden = "ASC";
			
		String[] ordenArray = new String[1];
		String[] tipoOrdenArray = new String[1];
		ordenArray[0] = atributoOrden;
		tipoOrdenArray[0] = tipoOrden;
		
		
		   
		List<Usuario> usuarios = new ArrayList();
		List<Grupo> grupos = new ArrayList();	 
		 
		Map<String, Object> filtros = new HashMap();
		
		//grupo seleccionado
		if((grupo != null) && (!grupo.equals("") && !grupo.equals("0"))) {
			
			if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
		    	filtros.put("estatus", estatus);
		 
			filtros.put("grupoId", grupo);
		 
			
			
			HSSFWorkbook objWB = new HSSFWorkbook();

			// Creamos la celda, aplicamos el estilo y definimos
			// el tipo de dato que contendrá la celda
			HSSFCell celda = null;

			// Creo la hoja
			HSSFSheet hoja1 = objWB.createSheet("Usuario Grupo");

			// Proceso la información y genero el xls.
			int numeroFila = 1;
			int numeroCelda = 1;
			HSSFRow fila = hoja1.createRow(numeroFila++); 
			
			CellStyle headerStyle = objWB.createCellStyle();
	        Font font = objWB.createFont();
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(font);

	        CellStyle style = objWB.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        
	        
			
			celda = fila.createCell(numeroCelda);
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellStyle(headerStyle);
			celda.setCellValue(messageResources.getMessage("reporte.framework.usuarios.grupo.titulo"));

			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			
			Grupo grupoNombre = (Grupo)usuariosService.load(Grupo.class,  new Long(grupo));
			
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
			
			String header = "Grupo: "+ grupoNombre.getGrupo();
	        HSSFCell cell = fila.createCell(1);
	        cell.setCellStyle(headerStyle);
	        cell.setCellValue(header);
			
	        numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue("");
			
			
			usuarios= usuariosService.getUsuarios(1, atributoOrden, tipoOrden, true, filtros).getLista();
					
			if(usuarios == null || usuarios.size() == 0) {
				
				String texto = "No existen usuarios asociados al Grupo";
		        HSSFCell celdatex = fila.createCell(1);
		        celdatex.setCellStyle(headerStyle);
		        celdatex.setCellValue(texto);
				
		        numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");	
				 
										 
			 }else {
				// Creamos la celda, aplicamos el estilo y definimos
					// el tipo de dato que contendrá la celda
					
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.uid"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.fullname"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.isadmin"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.estatus"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.estatus.bloqueado"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.creado"));
							
				celda = fila.createCell(numeroCelda++);
				celda.setCellValue(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.modificado"));
			
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				
				for(Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext(); ){
					
					Usuario usuario = iter.next();
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(usuario.getUId());
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(usuario.getFullName());
					
					celda = fila.createCell(numeroCelda++);
				
					if(usuario.getIsAdmin() != null && usuario.getIsAdmin() == true) {
						celda.setCellValue("Si");
				    }else {
				    	celda.setCellValue("No");
				    }
				    
					celda = fila.createCell(numeroCelda++);
				    if(usuario.getEstatus() != null) {
				    	switch(usuario.getEstatus()) {
					    	case 0:
					    		celda.setCellValue("Activo");
					    		break;
					    	case 1:
					    		celda.setCellValue("Inactivo");
					    		break;
				    	}
				    }
				    
				    celda = fila.createCell(numeroCelda++);
				    if(usuario.getBloqueado() != null && usuario.getBloqueado() == true) {
				    	celda.setCellValue("Si");
				    }else {
				    	celda.setCellValue("No");
				    }
					
		
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
					
					celda = fila.createCell(numeroCelda++);
					celda.setCellValue(VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));
					
					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue(""); 
				}
			
			
				
			}
			
			Date date = new Date();
	        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
	        
	        String ruta="UsuarioGrupo_"+hourdateFormat.format(date)+".xls"; 
	        
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition","attachment;filename="+ruta);    
	       
	        ServletOutputStream file  = response.getOutputStream();
	      
	        objWB.write(file);
	        file.close();
			
			
		}else {
			PaginaLista paginaGrupos = usuariosService.getGrupos(1, "grupo", "ASC");
		    
			 if(paginaGrupos != null && paginaGrupos.getLista().size() > 0) {   	
			   	grupos = paginaGrupos.getLista();	
			 }
			 
			 HSSFWorkbook objWB = new HSSFWorkbook();

				// Creamos la celda, aplicamos el estilo y definimos
				// el tipo de dato que contendrá la celda
				HSSFCell celda = null;

				// Creo la hoja
				HSSFSheet hoja1 = objWB.createSheet("Usuario Grupo");

				// Proceso la información y genero el xls.
				int numeroFila = 1;
				int numeroCelda = 1;
				HSSFRow fila = hoja1.createRow(numeroFila++); 
				
				CellStyle headerStyle = objWB.createCellStyle();
		        Font font = objWB.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = objWB.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = hoja1.createRow(numeroFila++);
				
		        celda = fila.createCell(numeroCelda);
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellStyle(headerStyle);
				celda.setCellValue(messageResources.getMessage("reporte.framework.usuarios.grupo.titulo"));

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				
											 
			 //se recorren los grupos
			 for(Grupo gru: grupos) {
				 
				 
				 
				 	if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
				    	filtros.put("estatus", estatus);
				 
					filtros.put("grupoId", gru.getGrupoId());
					
					Grupo grupoNombre = (Grupo)usuariosService.load(Grupo.class,  gru.getGrupoId());

					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue("");
					
					String header = "Grupo: "+ grupoNombre.getGrupo();
			        HSSFCell cell = fila.createCell(1);
			        cell.setCellStyle(headerStyle);
			        cell.setCellValue(header);
					
			        numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					celda.setCellValue("");					
					
				 
					usuarios= usuariosService.getUsuarios(1, atributoOrden, tipoOrden, true, filtros).getLista();
					
					if(usuarios == null || usuarios.size() == 0) {
						
						String texto = "No existen usuarios asociados al Grupo";
				        HSSFCell celdatex = fila.createCell(1);
				        celdatex.setCellStyle(headerStyle);
				        celdatex.setCellValue(texto);
						
				        numeroCelda = 1;
						fila = hoja1.createRow(numeroFila++);
						celda = fila.createCell(numeroCelda);
						celda.setCellValue("");	
						 
												 
					 }else {
						 
						// Creamos la celda, aplicamos el estilo y definimos
							// el tipo de dato que contendrá la celda
							
						numeroCelda = 1;
						fila = hoja1.createRow(numeroFila++);
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.uid"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.fullname"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.isadmin"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.estatus"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.editarusuario.label.estatus.bloqueado"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.creado"));
									
						celda = fila.createCell(numeroCelda++);
						celda.setCellValue(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.modificado"));
					
						
						numeroCelda = 1;
						fila = hoja1.createRow(numeroFila++);
						
						for(Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext(); ){
							
							Usuario usuario = iter.next();
							
							celda = fila.createCell(numeroCelda++);
							celda.setCellValue(usuario.getUId());
							
							celda = fila.createCell(numeroCelda++);
							celda.setCellValue(usuario.getFullName());
							
							celda = fila.createCell(numeroCelda++);
						
							if(usuario.getIsAdmin() != null && usuario.getIsAdmin() == true) {
								celda.setCellValue("Si");
						    }else {
						    	celda.setCellValue("No");
						    }
						    
							celda = fila.createCell(numeroCelda++);
						    if(usuario.getEstatus() != null) {
						    	switch(usuario.getEstatus()) {
							    	case 0:
							    		celda.setCellValue("Activo");
							    		break;
							    	case 1:
							    		celda.setCellValue("Inactivo");
							    		break;
						    	}
						    }
						    
						    celda = fila.createCell(numeroCelda++);
						    if(usuario.getBloqueado() != null && usuario.getBloqueado() == true) {
						    	celda.setCellValue("Si");
						    }else {
						    	celda.setCellValue("No");
						    }
							
		
							celda = fila.createCell(numeroCelda++);
							celda.setCellValue(VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
							
							celda = fila.createCell(numeroCelda++);
							celda.setCellValue(VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));
							
							numeroCelda = 1;
							fila = hoja1.createRow(numeroFila++);
							celda = fila.createCell(numeroCelda);
							celda.setCellValue("");
							
						 
					 }
																	
					
				}
					
					
				 
			 }
			 
			 	Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		        
		        String ruta="UsuarioGrupo_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+ruta);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		      
		        objWB.write(file);
		        file.close();

			 	
		}
		
		forward="exito";
		 
		
		/** Código de lógica de Negocio del action	*/

		/** Otherwise, return "success" */
		return mapping.findForward(forward);  
	          	  	
	    
   }
	
}
