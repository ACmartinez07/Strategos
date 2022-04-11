package com.visiongc.framework.web.struts.taglib.logica;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.util.MessageResources;









public abstract class VgcBaseCondicionalTag
  extends TagSupport
{
  static final long serialVersionUID = 0L;
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");
  





  protected String cookie = null;
  



  protected String header = null;
  


  public VgcBaseCondicionalTag() {}
  

  protected String name = null;
  



  protected String parameter = null;
  



  protected String property = null;
  




  protected String scope = null;
  
  public String getCookie() {
    return cookie; }
  
  public void setCookie(String cookie)
  {
    this.cookie = cookie;
  }
  
  public String getHeader() {
    return header;
  }
  
  public void setHeader(String header) {
    this.header = header;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getParameter() {
    return parameter;
  }
  
  public void setParameter(String parameter) {
    this.parameter = parameter;
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
  







  public int doStartTag()
    throws JspException
  {
    if (condition()) {
      return 1;
    }
    return 0;
  }
  





  public int doEndTag()
    throws JspException
  {
    cookie = null;
    header = null;
    name = null;
    parameter = null;
    property = null;
    scope = null;
    
    return 6;
  }
  


  public void release()
  {
    super.release();
    cookie = null;
    header = null;
    name = null;
    parameter = null;
    property = null;
    scope = null;
  }
  
  protected abstract boolean condition()
    throws JspException;
}
