package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class SubirAdjuntoExplicacionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();
    ActionMessages messages = getMessages(request);

    EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)request.getSession().getAttribute("editarExplicacionForm");
    AdjuntoExplicacion adjunto = new AdjuntoExplicacion();
    
    if(editarExplicacionForm.getAdjuntosExplicacion().size() <3)
    {
    	FormFile archivo = (FormFile)editarExplicacionForm.getMultipartRequestHandler().getFileElements().get("adjunto");
        if(archivo.getFileName() !=""){
        	
        	
        	
    	    Date date = new Date();
    	    SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
    	    
    	    File folder = new File("C:/Vision/Documentos/"+"e_"+hourdateFormat.format(date));
    	    folder.mkdirs();
    	    if(folder.exists()){
    	    	adjunto.setTitulo(archivo.getFileName());
    		    String nombre = archivo.getFileName();
    		    String path = folder+"/"+nombre;
    		    File file= new File(path);
    		    adjunto.setRuta(path);
    		    FileOutputStream out = new FileOutputStream(file);
    		    
    		    out.write(archivo.getFileData());
    	
    		    out.flush();
    	
    		    out.close();
    	    } 
    	    
    	    editarExplicacionForm.getAdjuntosExplicacion().add(adjunto);
    	    editarExplicacionForm.setNumeroAdjuntos(new Long(editarExplicacionForm.getAdjuntosExplicacion().size()));
        }
    }
    else{
    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("explicacion.subir.limitemaximo"));
		saveMessages(request, messages);
    }
    
	
    return mapping.findForward(forward);
  }
}