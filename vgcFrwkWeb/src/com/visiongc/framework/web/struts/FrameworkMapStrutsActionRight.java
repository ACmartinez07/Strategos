package com.visiongc.framework.web.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.struts.StrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;

public class FrameworkMapStrutsActionRight
  implements MapStrutsActionRight
{
  private static FrameworkMapStrutsActionRight instance = null;
  private HashMap<String, StrutsActionRight> mapa = null;
  







  public FrameworkMapStrutsActionRight()
  {
    instance = this;
    

    mapa = new HashMap();
    


    StrutsActionRight actionRight = new StrutsActionRight("ingreso", true, false, null);
    mapa.put("ingreso", actionRight);
    
    actionRight = new StrutsActionRight("conectar", true, false, null);
    mapa.put("conectar", actionRight);
    
    actionRight = new StrutsActionRight("conectarplan", true, false, null);
    mapa.put("conectarplan", actionRight);
    
    actionRight = new StrutsActionRight("logon", true, false, null);
    mapa.put("logon", actionRight);
    
    actionRight = new StrutsActionRight("framework.refreshsession", true, false, null);
    mapa.put("framework.refreshsession", actionRight);
    
    actionRight = new StrutsActionRight("locale", true, false, null);
    mapa.put("locale", actionRight);
    
    actionRight = new StrutsActionRight("error", true, false, null);
    mapa.put("error", actionRight);
    
    actionRight = new StrutsActionRight("goback", true, false, null);
    mapa.put("goback", actionRight);
    
    actionRight = new StrutsActionRight("logout", true, false, null);
    mapa.put("logout", actionRight);
    
    actionRight = new StrutsActionRight("acerca", true, false, null);
    mapa.put("acerca", actionRight);
    
    actionRight = new StrutsActionRight("licencia", true, false, null);
    mapa.put("licencia", actionRight);
    
    actionRight = new StrutsActionRight("licencia.enviar.mail", true, false, null);
    mapa.put("licencia.enviar.mail", actionRight);
    
    actionRight = new StrutsActionRight("licencia.salvar", true, false, null);
    mapa.put("licencia.salvar", actionRight);
    
    actionRight = new StrutsActionRight("home", true, false, null);
    mapa.put("home", actionRight);
    
    actionRight = new StrutsActionRight("framework.administracion", true, false, null);
    mapa.put("framework.administracion", actionRight);
    
    actionRight = new StrutsActionRight("framework.errores.gestionarerrores", false, false, "ERROR");
    mapa.put("framework.errores.gestionarerrores", actionRight);
    
    actionRight = new StrutsActionRight("framework.errores.reporteerror", false, false, "ERROR_PRINT");
    mapa.put("framework.errores.reporteerror", actionRight);
    
    actionRight = new StrutsActionRight("framework.errores.enviarerror", false, false, "ERROR");
    mapa.put("framework.errores.enviarerror", actionRight);
    
    actionRight = new StrutsActionRight("framework.servicio.gestionarservicios", false, false, "SERVICIO");
    mapa.put("framework.servicio.gestionarservicios", actionRight);
    
    actionRight = new StrutsActionRight("framework.servicio.configurarservicio", false, false, "SERVICIO_SET");
    mapa.put("framework.servicio.configurarservicio", actionRight);
    
    actionRight = new StrutsActionRight("framework.servicio.salvarservicio", false, false, "SERVICIO_LOG");
    mapa.put("framework.servicio.salvarservicio", actionRight);
    
    actionRight = new StrutsActionRight("framework.servicio.descargaradjunto", true, false, "SERVICIO_LOG");
    mapa.put("framework.servicio.descargaradjunto", actionRight);

    actionRight = new StrutsActionRight("alertas.gestionaralertas", true, false, "ALERTA");
    mapa.put("alertas.gestionaralertas", actionRight);
    actionRight = new StrutsActionRight("alertas.salvaralertas", true, false, "ALERTA");
    mapa.put("alertas.salvaralertas", actionRight);
    actionRight = new StrutsActionRight("alertas.generarreportealertas", true, false, "ALERTA");
    mapa.put("alertas.generarreportealertas", actionRight);
  }
  
  public static MapStrutsActionRight getInstance()
  {
    if (instance == null)
    {
      try
      {
        new FrameworkMapStrutsActionRight();

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
