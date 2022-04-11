package com.visiongc.framework.web.struts.taglib.util;

import com.visiongc.commons.VgcConfiguration;
import java.util.Properties;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.util.MessageResources;


public class ValorPropertyIgualTag
  extends TagSupport
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.util.ValorPropertieIgual";
  
  public ValorPropertyIgualTag() {}
  
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");
  
  protected String nombre = null;
  
  protected String property = null;
  protected String valor = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getProperty() {
    return property;
  }
  
  public void setProperty(String property) {
    this.property = property;
  }
  
  public String getValor() {
    return valor;
  }
  
  public void setValor(String valor) {
    this.valor = valor;
  }
  


  public int doStartTag()
    throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.util.ValorPropertieIgual", this);
    
    boolean condicion = false;
    String valorBuscado = null;
    try
    {
      valorBuscado = VgcConfiguration.loadProperties(Class.forName(nombre)).getProperty(property);
    }
    catch (Exception localException) {}
    

    if ((valorBuscado != null) && 
      (valorBuscado.equals(valor))) {
      condicion = true;
    }
    
    if (condicion) {
      return 1;
    }
    return 0;
  }
}
