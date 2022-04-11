package com.visiongc.framework.web.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.struts.StrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;

public class AuditoriasMapStrutsActionRight
  implements MapStrutsActionRight
{
  private static AuditoriasMapStrutsActionRight instance = null;
  private HashMap<String, StrutsActionRight> mapa = null;
  





  public AuditoriasMapStrutsActionRight()
  {
    instance = this;
    

    mapa = new HashMap();
    


    StrutsActionRight actionRight = new StrutsActionRight("framework.auditorias.gestionarauditorias", true, false, null);
    mapa.put("framework.auditorias.gestionarauditorias", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.gestionarauditoriasporatributo", true, false, null);
    mapa.put("framework.auditorias.gestionarauditoriasporatributo", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.gestionarauditoriasmedicion", true, false, null);
    mapa.put("framework.auditorias.gestionarauditoriasmedicion", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.reporteauditoriapdf", true, false, null);
    mapa.put("framework.auditorias.reporteauditoriapdf", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.reporteauditoriaexcel", true, false, null);
    mapa.put("framework.auditorias.reporteauditoriaexcel", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.gestionarauditoriasmediciondetalle", true, false, null);
    mapa.put("framework.auditorias.gestionarauditoriasmediciondetalle", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.reportemedicionproyecto", true, false, null);
    mapa.put("framework.auditorias.reportemedicionproyecto", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.reportemedicionproyectopdf", true, false, null);
    mapa.put("framework.auditorias.reportemedicionproyectopdf", actionRight);
    
    actionRight = new StrutsActionRight("framework.auditorias.reportemedicionproyectoxls", true, false, null);
    mapa.put("framework.auditorias.reportemedicionproyectoxls", actionRight);
    
   
            
    
  }
  
  public static MapStrutsActionRight getInstance()
  {
    if (instance == null)
    {
      try
      {
        new AuditoriasMapStrutsActionRight();

      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("El mapeo de acciones a permisos de Auditorías no está configurado correctamente.", e);
      }
    }
    
    return instance;
  }
  
  public Map<String, StrutsActionRight> getMapa()
  {
    return mapa;
  }
}
