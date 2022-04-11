package com.visiongc.commons.struts.tag;

import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import com.visiongc.framework.util.PermisologiaUsuario;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.TagUtils;





public class VgcBodyBaseTag
  extends BodyTagSupport
{
  static final long serialVersionUID = 0L;
  
  public VgcBodyBaseTag() {}
  
  protected String getMessageResource(String bundle, String localeKey, String key)
    throws JspException
  {
    return getMessageResource(bundle, localeKey, key, Boolean.valueOf(false));
  }
  
  protected String getMessageResource(String bundle, String localeKey, String key, Boolean overwriteMessage) throws JspException
  {
    String mensaje = VgcResourceManager.getResourceApp(key);
    
    if (mensaje == null)
    {

      String message = TagUtils.getInstance().message(pageContext, bundle, localeKey, key);
      
      if ((message == null) && (!overwriteMessage.booleanValue()))
      {
        JspException e = new JspException("El Recurso de Lenguaje '" + key + "' no existe");
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      
      message = key;
      
      mensaje = message;
    }
    
    return mensaje;
  }
  
  protected String getMessageResource(String bundle, String localeKey, String key, Boolean overwriteMessage, Object[] args) throws JspException
  {
    String mensaje = VgcResourceManager.getResourceApp(key);
    
    if (mensaje == null)
    {

      String message = TagUtils.getInstance().message(pageContext, bundle, localeKey, key, args);
      
      if ((message == null) && (!overwriteMessage.booleanValue()))
      {
        Locale locale = TagUtils.getInstance().getUserLocale(pageContext, localeKey);
        String localeVal = locale == null ? "locale por defecto" : locale.toString();
        JspException e = new JspException("No existe el Recurso de Lenguaje '" + key + "' en el locale " + localeVal);
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      
      message = key;
      
      mensaje = message;
    }
    else {
      mensaje = replaceArgs(mensaje, args);
    }
    return mensaje;
  }
  






  protected String replaceArgs(String resource, Object[] argsReemplazo)
  {
    int numParam = 0;
    
    numParam = argsReemplazo.length;
    
    if (resource.indexOf("{") > 0)
    {
      for (int i = 0; i < numParam; i++) {
        resource = resource.replaceAll("\\{" + i + "\\}", (String)argsReemplazo[i]);
      }
    }
    return resource;
  }
  








  protected boolean tienePermiso(String permisoId, boolean aplicaOrganizacion)
    throws JspException
  {
    boolean tienePermiso = true;
    
    if ((permisoId != null) && (!permisoId.equals("")))
    {
      PermisologiaUsuario permisologiaUsuario = (PermisologiaUsuario)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
      
      if (permisologiaUsuario == null)
      {
        JspException e = new JspException("La Permisología de Usuario no ha sido cargada");
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      if (aplicaOrganizacion)
      {
        OrganizacionPermisologia o = (OrganizacionPermisologia)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("organizacion");
        
        if (o.getOrganizacionId() != null) {
          tienePermiso = permisologiaUsuario.tienePermiso(permisoId, o.getOrganizacionId().longValue());
        } else {
          tienePermiso = false;
        }
      } else {
        tienePermiso = permisologiaUsuario.tienePermiso(permisoId);
      }
    }
    return tienePermiso;
  }
  






  protected String getUrlAplicacion()
  {
    return ((HttpServletRequest)pageContext.getRequest()).getContextPath();
  }
  



  public void release()
  {
    super.release();
  }
}
