package com.visiongc.framework.web.configuracion;

import com.visiongc.framework.configuracion.VgcObjetoConfigurable;
import javax.servlet.http.HttpServletRequest;

public abstract class VgcConfiguracionesBaseWeb implements com.visiongc.framework.configuracion.VgcConfiguracionesBase
{
  public VgcConfiguracionesBaseWeb() {}
  
  public com.visiongc.commons.util.xmldata.XmlNodo getConfiguracionBase(String nombreObjeto, HttpServletRequest request)
  {
    com.visiongc.commons.util.xmldata.XmlNodo configuracionBase = null;
    VgcObjetoConfigurable objetoConfigurable = null;
    
    objetoConfigurable = (VgcObjetoConfigurable)request.getAttribute(nombreObjeto);
    
    if (objetoConfigurable == null) {
      objetoConfigurable = (VgcObjetoConfigurable)request.getSession().getAttribute(nombreObjeto);
    }
    
    if (objetoConfigurable != null) {
      configuracionBase = objetoConfigurable.getConfiguracionBase();
    }
    return configuracionBase;
  }
  
  public VgcObjetoConfigurable getObjetoConfigurable(String nombreObjeto, HttpServletRequest request) {
    VgcObjetoConfigurable objetoConfigurable = null;
    
    objetoConfigurable = (VgcObjetoConfigurable)request.getAttribute(nombreObjeto);
    
    if (objetoConfigurable == null) {
      objetoConfigurable = (VgcObjetoConfigurable)request.getSession().getAttribute(nombreObjeto);
    }
    
    return objetoConfigurable;
  }
}
