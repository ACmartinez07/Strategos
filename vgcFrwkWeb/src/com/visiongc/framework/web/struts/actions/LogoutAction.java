package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class LogoutAction extends VgcAction
{
  public LogoutAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    if (request.getParameter("usuario") != null)
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      usuario = frameworkService.getUsuarioPorLogin(request.getParameter("usuario"));
      
      if (usuario != null)
      {
        for (Iterator<?> iter = usuario.getSesiones().iterator(); iter.hasNext();)
        {
          UserSession us = (UserSession)iter.next();
          frameworkService.setConectado(Boolean.valueOf(false), us.getUsuarioId());
          frameworkService.clearUserSessionPorId(us.getSessionId());
        }
      }
      
      frameworkService.close();
    }
    else if (usuario != null)
    {
      if (!usuario.getPermitirConeccionVirtual().booleanValue())
      {
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        usuario = frameworkService.getUsuarioPorLogin(usuario.getUId());
        for (Iterator<?> iter = usuario.getSesiones().iterator(); iter.hasNext();)
        {
          UserSession us = (UserSession)iter.next();
          frameworkService.setConectado(Boolean.valueOf(false), us.getUsuarioId());
          frameworkService.clearUserSessionPorId(us.getSessionId());
        }
        
        frameworkService.close();
      }
    }
    

    Enumeration<?> keys = request.getSession().getAttributeNames();
    while (keys.hasMoreElements())
    {
      String key = (String)keys.nextElement();
      request.getSession().removeAttribute(key);
    }
    
    request.getSession().invalidate();
    request.getSession().setMaxInactiveInterval(1);
    
    Boolean closeApp = Boolean.valueOf(request.getParameter("closeApp") != null ? Boolean.parseBoolean(request.getParameter("closeApp")) : false);
    String forward = "";
    if (!closeApp.booleanValue())
    {
      forward = "inicio";
      if (mapping.getParameter() != null) {
        forward = mapping.getParameter();
      }
    }
    else {
      forward = "ajaxResponse";
      request.setAttribute("ajaxResponse", "");
    }
    
    return mapping.findForward(forward);
  }
}
