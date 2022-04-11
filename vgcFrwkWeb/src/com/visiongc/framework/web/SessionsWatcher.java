package com.visiongc.framework.web;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;


public class SessionsWatcher
{
  private static SessionsWatcher instance;
  private static long INTERVALO_CLEANING = 150000L;
  public static long INTERVALO_REFRESH_SESSION = 60000L;
  
  private Map<String, Date> sessions;
  private Timer timer;
  private long intervalo;
  
  public SessionsWatcher() {}
  
  public static SessionsWatcher getInstance()
  {
    if (instance == null)
    {
      instance = new SessionsWatcher();
      instance.sessions = new HashMap();
      instance.timer = new Timer();
      boolean watchSessions = false;
      watchSessions = FrameworkWebConfiguration.getInstance().getBoolean("com.visiongc.app.web.watchsessions");
      if (watchSessions)
      {
        instance.intervalo = INTERVALO_CLEANING;
        String intervaloSegundos = FrameworkWebConfiguration.getInstance().getString("com.visiongc.app.web.watchsessions.cleaninterval");
        if ((intervaloSegundos != null) && (!intervaloSegundos.equals("")))
          instance.intervalo = (Long.parseLong(intervaloSegundos) * 1000L);
        instance.timer.scheduleAtFixedRate(new SessionsWatcherCleaner(instance), instance.intervalo, instance.intervalo);
      }
    }
    
    return instance;
  }
  
  public void addSession(String sessionId)
  {
    sessions.put(sessionId, new Date());
  }
  
  public void refreshSession(String sessionId)
  {
    if (sessions.containsKey(sessionId))
    {
      sessions.remove(sessionId);
      sessions.put(sessionId, new Date());
    }
  }
  
  public void clean()
  {
    Date ahora = new Date();
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    for (Iterator<String> iter = sessions.keySet().iterator(); iter.hasNext();)
    {
      String sessionId = (String)iter.next();
      
      Date ultimaActualizacion = (Date)sessions.get(sessionId);
      if (ahora.getTime() - ultimaActualizacion.getTime() > intervalo) {
        frameworkService.clearUserSessionPorId(sessionId);
      }
    }
    frameworkService.close();
  }
}
