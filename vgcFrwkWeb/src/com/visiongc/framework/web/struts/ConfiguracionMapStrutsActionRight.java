package com.visiongc.framework.web.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.struts.StrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;


public class ConfiguracionMapStrutsActionRight
  implements MapStrutsActionRight
{
  private static ConfiguracionMapStrutsActionRight instance = null;
  private HashMap<String, StrutsActionRight> mapa = null;
  





  public ConfiguracionMapStrutsActionRight()
  {
    instance = this;
    

    mapa = new HashMap();
    


    StrutsActionRight actionRight = new StrutsActionRight("framework.configuracion.editarconfiguracionvisorlista", true, false, null);
    mapa.put("framework.configuracion.editarconfiguracionvisorlista", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.guardarconfiguracionvisorlista", true, false, null);
    mapa.put("framework.configuracion.guardarconfiguracionvisorlista", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.editarconfiguracionvisor", true, false, null);
    mapa.put("framework.configuracion.editarconfiguracionvisor", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.guardarconfiguracionvisor", true, false, null);
    mapa.put("framework.configuracion.guardarconfiguracionvisor", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.cancelarguardarconfiguracionvisorlista", true, false, null);
    mapa.put("framework.configuracion.cancelarguardarconfiguracionvisorlista", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.editarconfiguracionpagina", true, false, null);
    mapa.put("framework.configuracion.editarconfiguracionpagina", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.guardarconfiguracionpagina", true, false, null);
    mapa.put("framework.configuracion.guardarconfiguracionpagina", actionRight);
    
    actionRight = new StrutsActionRight("framework.configuracion.cancelarguardarconfiguracionpagina", true, false, null);
    mapa.put("framework.configuracion.cancelarguardarconfiguracionpagina", actionRight);
  }
  
  public static MapStrutsActionRight getInstance()
  {
    if (instance == null)
    {
      try
      {
        new ConfiguracionMapStrutsActionRight();

      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("El mapeo de acciones a permisos no está configurado correctamente.", e);
      }
    }
    
    return instance;
  }
  
  public Map<String, StrutsActionRight> getMapa()
  {
    return mapa;
  }
}
