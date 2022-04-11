package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Error;
import com.visiongc.framework.model.Usuario;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;










public final class RegistrarErrorAction
  extends VgcAction
{
  public RegistrarErrorAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      Throwable t = (Throwable)request
        .getAttribute("org.apache.struts.action.EXCEPTION");
      

      StringWriter s = new StringWriter();
      PrintWriter errMsg = new PrintWriter(s);
      t.printStackTrace(errMsg);
      
      Error error = new Error();
      
      error.setErrTimestamp(new Date());
      
      error.setErrSource(t.toString());
      error.setErrDescription(t.toString());
      PropertyUtils.setSimpleProperty(form, "message", t.toString());
      
      error.setErrStackTrace(s.toString());
      PropertyUtils.setSimpleProperty(form, "stackTrace", s.toString());
      
      Throwable causa = t.getCause();
      String causas = "";
      int nroCausas = 0;
      while ((causa != null) && (nroCausas < 6)) {
        causas = causas + causa.getMessage() + "\n";
        nroCausas++;
        causa = causa.getCause();
      }
      
      if (!causas.equals("")) {
        error.setErrCause(causas);
      }
      PropertyUtils.setSimpleProperty(form, "cause", causas);
      
      Usuario usuario = (Usuario)request.getSession().getAttribute(
        "usuario");
      
      if (usuario != null) {
        error.setErrUserId(String.valueOf(usuario.getUId()));
      }
      
      String version = (String)request.getSession().getAttribute("appVersion");
      
      if (version != null) {
        error.setErrVersion(version);
      }
      
      FrameworkService frameworkService = 
        FrameworkServiceFactory.getInstance().openFrameworkService();
      
      frameworkService.registrarError(error);
      
      frameworkService.close();
    }
    catch (Throwable localThrowable1) {}
    


    String forward = mapping.getParameter();
    
    if ((forward == null) || (forward.equals(""))) {
      forward = "error";
    }
    
    return mapping.findForward(forward);
  }
}
