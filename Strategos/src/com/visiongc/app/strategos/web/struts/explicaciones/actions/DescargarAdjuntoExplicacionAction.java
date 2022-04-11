package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacionPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcDownloadAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DescargarAdjuntoExplicacionAction extends VgcAction
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
	     
		String explicacionId = request.getParameter("explicacionId");
		String adjuntoId = request.getParameter("adjuntoId");
		String indice = request.getParameter("indice");

		
		AdjuntoExplicacion adjunto = null;
			
		if ((indice != null) && (!indice.equals("")))
		{
			EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)request.getSession().getAttribute("editarExplicacionForm");

			adjunto = (AdjuntoExplicacion)editarExplicacionForm.getAdjuntosExplicacion().get(Integer.parseInt(indice));

			
		}
		else
		{
			StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
			
			AdjuntoExplicacionPK adjuntoPK = new AdjuntoExplicacionPK();
			adjuntoPK.setAdjuntoId(new Long(adjuntoId));
			adjuntoPK.setExplicacionId(new Long(explicacionId));
			adjunto = (AdjuntoExplicacion)strategosExplicacionesService.load(AdjuntoExplicacion.class, adjuntoPK);

			

			strategosExplicacionesService.close();
		}

		if (adjunto != null) {
			 try {
		            
				 FileInputStream archivo = new FileInputStream(adjunto.getRuta()); 
				 int longitud = archivo.available();
				 byte[] datos = new byte[longitud];
				 archivo.read(datos);
				 archivo.close();

				 response.setContentType("application/download"); 
				 response.setHeader("Content-Disposition","attachment;filename="+adjunto.getTitulo());    

				 response.getOutputStream().write(datos);
				 response.getOutputStream().flush();
				 response.getOutputStream().close();
		     }catch (IOException ex) {

		            System.out.println(ex);
		     }
		}
		
		return mapping.findForward(forward);
	 }
}