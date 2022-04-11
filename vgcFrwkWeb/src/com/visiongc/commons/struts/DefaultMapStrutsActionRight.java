package com.visiongc.commons.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.web.FrameworkWebConfiguration;
import com.visiongc.framework.web.struts.AuditoriasMapStrutsActionRight;
import com.visiongc.framework.web.struts.ConfiguracionMapStrutsActionRight;
import com.visiongc.framework.web.struts.FrameworkMapStrutsActionRight;
import com.visiongc.framework.web.struts.UsuariosMapStrutsActionRight;
import java.util.HashMap;
import java.util.Map;

public class DefaultMapStrutsActionRight
  implements MapStrutsActionRight
{
  private static DefaultMapStrutsActionRight instance = null;
  private HashMap<String, StrutsActionRight> mapa = null;
  


  public DefaultMapStrutsActionRight()
  {
    instance = this;
    

    mapa = new HashMap();
    
    mapa.putAll(FrameworkMapStrutsActionRight.getInstance().getMapa());
    mapa.putAll(UsuariosMapStrutsActionRight.getInstance().getMapa());
    mapa.putAll(ConfiguracionMapStrutsActionRight.getInstance().getMapa());
    mapa.putAll(AuditoriasMapStrutsActionRight.getInstance().getMapa());
    mapa.putAll(((MapStrutsActionRight)FrameworkWebConfiguration.getInstance().instantiate("com.visiongc.app.web.struts.map.action.right", MapStrutsActionRight.class)).getMapa());
  }
  
  public static DefaultMapStrutsActionRight getInstance()
  {
    if (instance == null)
    {
      try
      {
        new DefaultMapStrutsActionRight();
      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("El mapeo de acciones a permisos no está configurado correctamente.", e);
      }
    }
    
    return instance;
  }
  
  public StrutsActionRight getPermisoPorURI(String uri)
  {
    int punto = uri.indexOf("/", 1);
    uri = uri.substring(punto + 1);
    uri = uri.replaceAll("/", ".");
    uri = uri.toLowerCase();
    
    uri = uri.substring(0, uri.length() - "action".length() - 1);
    
    return (StrutsActionRight)mapa.get(uri);
  }
  
  public StrutsActionRight getPermisoPorPath(String uri)
  {
    uri = uri.substring(1);
    uri = uri.replaceAll("/", ".");
    uri = uri.toLowerCase();
    
    return (StrutsActionRight)mapa.get(uri);
  }
  
  public Map<String, StrutsActionRight> getMapa()
  {
    return mapa;
  }
}
