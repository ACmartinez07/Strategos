package com.visiongc.framework.web.struts.taglib.util;

import com.visiongc.commons.util.PaginaLista;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;



public class CrearPaginaListaTag
  extends TagSupport
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.util.CrearPaginaLista";
  
  public CrearPaginaListaTag() {}
  
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");
  
  protected String nombre = null;
  
  protected String name = null;
  
  protected String property = null;
  
  protected String scope = null;
  
  protected String toScope = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getProperty() {
    return property;
  }
  
  public void setProperty(String property) {
    this.property = property;
  }
  
  public String getScope() {
    return scope;
  }
  
  public void setScope(String scope) {
    this.scope = scope;
  }
  
  public String getToScope() {
    return toScope;
  }
  
  public void setToScope(String toScope) {
    this.toScope = toScope;
  }
  


  public int doStartTag()
    throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.util.CrearPaginaLista", this);
    

    Object variable = null;
    
    Object bean = TagUtils.getInstance().lookup(pageContext, name, scope);
    
    if (property != null) {
      if (bean == null) {
        JspException e = new JspException(messages.getMessage("logic.bean", name));
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      try
      {
        variable = PropertyUtils.getProperty(bean, property);
      } catch (InvocationTargetException e) {
        Throwable t = e.getTargetException();
        
        if (t == null) {
          t = e;
        }
        
        TagUtils.getInstance().saveException(pageContext, t);
        throw new JspException(messages.getMessage("logic.property", name, property, t.toString()));
      } catch (Throwable t) {
        TagUtils.getInstance().saveException(pageContext, t);
        throw new JspException(messages.getMessage("logic.property", name, property, t.toString()));
      }
    } else {
      variable = bean;
    }
    
    List lista = (List)variable;
    
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(lista);
    paginaLista.setNroPagina(0);
    paginaLista.setTamanoPagina(0);
    paginaLista.setTamanoSetPaginas(0);
    paginaLista.setTotal(lista.size());
    
    if (toScope == null) {
      pageContext.setAttribute(nombre, paginaLista);
    } else if (toScope.equals("page")) {
      pageContext.setAttribute(nombre, paginaLista);
    } else if (toScope.equals("request")) {
      pageContext.getRequest().setAttribute(nombre, paginaLista);
    } else if (toScope.equals("session")) {
      pageContext.getSession().setAttribute(nombre, paginaLista);
    }
    
    return 0;
  }
}
