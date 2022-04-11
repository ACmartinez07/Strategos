package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.web.struts.forms.LicenciaForm;
import com.visiongc.framework.web.struts.forms.LicenciaForm.LicenciaStatus;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public final class LicenciaSalvarAction
  extends VgcAction
{
  public LicenciaSalvarAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        super.execute(mapping, form, request, response);
	        String forward = mapping.getParameter();
	        LicenciaForm licenciaForm = (LicenciaForm)form;
	        FormFile archivo = (FormFile)licenciaForm.getMultipartRequestHandler().getFileElements().get("codigo");
	        String clave = "";
	        BufferedReader BR = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
	        String linea;
	        while((linea = BR.readLine()) != null) 
	            if(!linea.equals(""))
	                clave = linea;
	        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService(getLocale(request));
	        Sistema sistema = frameworkService.getSistema(null);
	        sistema.setCmaxc(clave);
	        int respuesta = frameworkService.setProducto(sistema, request);
	        if(respuesta == 10000)
	        {
	            request.getSession().setAttribute("licencia", frameworkService.getCheckProducto(sistema, request));
	            licenciaForm.setStatus(com.visiongc.framework.web.struts.forms.LicenciaForm.LicenciaStatus.getLicenciaStatusSaveSuccess());
	        } else
	        {
	            licenciaForm.setStatus(com.visiongc.framework.web.struts.forms.LicenciaForm.LicenciaStatus.getLicenciaStatusSaveNoSuccess());
	        }
	        frameworkService.close();
	        return mapping.findForward(forward);
	    }
}
