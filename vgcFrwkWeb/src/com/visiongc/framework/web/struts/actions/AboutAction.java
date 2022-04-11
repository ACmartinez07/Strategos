package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkConfiguration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;









public final class AboutAction
  extends VgcAction
{
  public AboutAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    request.setAttribute("frameworkVersion", FrameworkConfiguration.getInstance().getString("version"));
    request.setAttribute("frameworkVersionFecha", FrameworkConfiguration.getInstance().getString("version.fecha"));
    
    new WelcomeAction().checkProducto(null, request);
    
    return mapping.findForward("acerca");
  }
}
