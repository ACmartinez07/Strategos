package com.visiongc.commons.struts.action;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.logging.AppLogger;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;

public class VgcActionServlet
  extends ActionServlet
{
  static final long serialVersionUID = 0L;
  
  public VgcActionServlet() {}
  
  public void init()
    throws ServletException
  {
    super.init();
    String urlPath = "";
    
    try
    {
      ServletContext context = getServletContext();
      System.setProperty("rootPath", context.getRealPath("/"));
      new AppLogger().getInstance(context.getRealPath("/"));
      
      urlPath = getServletContext().getResource("/index.jsp").getPath();
      int posicion = urlPath.substring(1).indexOf("/");
      urlPath = urlPath.substring(posicion + 1);
      posicion = urlPath.substring(1).indexOf("/");
      urlPath = urlPath.substring(0, posicion + 1);

    }
    catch (Throwable t)
    {
      throw new ServletException("No se pudo determinar Url de la aplicación", t);
    }
    
    VgcMessageResources messageResourcesFWKWeb = VgcResourceManager.getMessageResources("FrameworkWeb");
    VgcMessageResources messageResourcesBll = VgcResourceManager.getMessageResources("Strategos");
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    frameworkService.clearUserSessions(urlPath);
    frameworkService.close();
  }
}
