package com.visiongc.framework.web.struts.actions.servicio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacionPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;


import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcDownloadAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.ReporteServicioService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Servicio;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DescargarAdjuntoAction extends VgcAction
{
	
	 private static final int BYTES_DOWNLOAD = 0;

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	 {
	 }
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			    throws Exception
	 {
	    super.execute(mapping, form, request, response);
	    String forward = mapping.getParameter();
	     
	    ReporteServicioService reporteServicioService =FrameworkServiceFactory.getInstance().openReporteServicioService();
		
	    Servicio servicio = new Servicio();
	    
	    String[] fields = request.getParameter("data").split(servicio.getSeparador());
		
		
		Long usuarioId;
		Long fecha = null;
		String nombre="Calcular";
		
		/*
		if (fields.length > 0) {
			usuarioId = Long.valueOf(Long.parseLong(fields[0]));
			fecha = Long.valueOf(Long.parseLong(fields[1]));
		    nombre = fields[2];
		    
		    List<ReporteServicio> reportes =new ArrayList();
		    ReporteServicio reporte = new ReporteServicio();
		    reportes = reporteServicioService.getReporte(usuarioId, nombre);
			
		    Date fecha1 = new Date(fecha);
		   
		    
		    
		    String strDateFormat = "yyyy-MM-dd HH:mm";
		    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		    String fechafor=objSDF.format(fecha1);
		    
		    for(ReporteServicio rep: reportes) {
		    	
		    	String fechafor1=objSDF.format(rep.getFecha());
		    	
		    	if(fechafor.equals(fechafor1)) {
		    		reporte = rep;
		    	}
		    	
		    	
		    }

			if (reporte != null) {
				 try {
			            
					 FileInputStream archivo = new FileInputStream(reporte.getRuta()); 
					 int longitud = archivo.available();
					 byte[] datos = new byte[longitud];
					 archivo.read(datos);
					 archivo.close();

					 response.setContentType("application/download"); 
					 response.setHeader("Content-Disposition","attachment;filename="+reporte.getTitulo()+".xls");    

					 response.getOutputStream().write(datos);
					 response.getOutputStream().flush();
					 response.getOutputStream().close();
			     }catch (IOException ex) {

			            System.out.println(ex);
			     }
			}
		    
		}
		*/
		
	
		reporteServicioService.close();
		return mapping.findForward(forward);
	 
	 }
}