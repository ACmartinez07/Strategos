package com.visiongc.framework.web;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener
{
  public SessionListener() {}
  
  public void sessionCreated(HttpSessionEvent event) {}
  
  public void sessionDestroyed(HttpSessionEvent event)
  {
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    frameworkService.clearUserSessionPorId(event.getSession().getId());
    
    frameworkService.close();
  }
}
