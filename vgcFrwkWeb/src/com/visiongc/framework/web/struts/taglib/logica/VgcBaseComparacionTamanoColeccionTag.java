package com.visiongc.framework.web.struts.taglib.logica;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;











public abstract class VgcBaseComparacionTamanoColeccionTag
  extends VgcBaseCondicionalTag
{
  static final long serialVersionUID = 0L;
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");
  


  public VgcBaseComparacionTamanoColeccionTag() {}
  


  public String value = null;
  
  public String getValue() {
    return value; }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  




  public void release()
  {
    super.release();
    value = null;
  }
  












  protected abstract boolean condition()
    throws JspException;
  











  protected boolean condition(int desired1, int desired2)
    throws JspException
  {
    Object variable = null;
    
    if (cookie != null) {
      Cookie[] cookies = ((HttpServletRequest)pageContext.getRequest()).getCookies();
      
      if (cookies == null) {
        cookies = new Cookie[0];
      }
      
      for (int i = 0; i < cookies.length; i++) {
        if (cookie.equals(cookies[i].getName())) {
          variable = cookies[i].getValue();
          
          break;
        }
      }
    } else if (header != null) {
      variable = ((HttpServletRequest)pageContext.getRequest()).getHeader(header);
    } else if (name != null) {
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
    } else if (parameter != null) {
      variable = pageContext.getRequest().getParameter(parameter);
    } else {
      JspException e = new JspException(messages.getMessage("logic.selector"));
      
      TagUtils.getInstance().saveException(pageContext, e);
      throw e;
    }
    
    Collection coleccion = (Collection)variable;
    

    int result = 0;
    
    int intValue = Integer.parseInt(value);
    
    try
    {
      if (coleccion.size() < intValue) {
        result = -1;
      } else if (coleccion.size() > intValue) {
        result = 1;
      }
    } catch (NumberFormatException e) {
      result = variable.toString().compareTo(value);
    }
    

    if (result < 0) {
      result = -1;
    } else if (result > 0) {
      result = 1;
    }
    

    return (result == desired1) || (result == desired2);
  }
}
