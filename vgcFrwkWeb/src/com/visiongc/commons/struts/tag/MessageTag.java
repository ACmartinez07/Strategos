package com.visiongc.commons.struts.tag;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;














public class MessageTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.bean.LocalStrings");
  


  public MessageTag() {}
  

  protected String arg0 = null;
  



  protected String arg1 = null;
  



  protected String arg2 = null;
  



  protected String arg3 = null;
  



  protected String arg4 = null;
  



  protected String bundle = null;
  



  protected String key = null;
  



  protected String name = null;
  



  protected String property = null;
  



  protected String scope = null;
  



  protected String localeKey = "org.apache.struts.action.LOCALE";
  
  public String getArg0() {
    return arg0; }
  
  public void setArg0(String arg0)
  {
    this.arg0 = arg0;
  }
  
  public String getArg1() {
    return arg1;
  }
  
  public void setArg1(String arg1) {
    this.arg1 = arg1;
  }
  
  public String getArg2() {
    return arg2;
  }
  
  public void setArg2(String arg2) {
    this.arg2 = arg2;
  }
  
  public String getArg3() {
    return arg3;
  }
  
  public void setArg3(String arg3) {
    this.arg3 = arg3;
  }
  
  public String getArg4() {
    return arg4;
  }
  
  public void setArg4(String arg4) {
    this.arg4 = arg4;
  }
  
  public String getBundle() {
    return bundle;
  }
  
  public void setBundle(String bundle) {
    this.bundle = bundle;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
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
  
  public String getLocale() {
    return localeKey;
  }
  
  public void setLocale(String localeKey) {
    this.localeKey = localeKey;
  }
  






  public int doStartTag()
    throws JspException
  {
    String key = this.key;
    
    if (key == null)
    {
      Object value = TagUtils.getInstance().lookup(pageContext, name, property, scope);
      
      if ((value != null) && (!(value instanceof String))) {
        JspException e = new JspException(messages.getMessage("message.property", key));
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      
      key = (String)value;
    }
    

    Object[] args = { arg0, arg1, arg2, arg3, arg4 };
    
    String mensaje = getMessageResource(bundle, localeKey, key, args);
    
    TagUtils.getInstance().write(pageContext, mensaje);
    
    return 0;
  }
  


  public void release()
  {
    super.release();
    arg0 = null;
    arg1 = null;
    arg2 = null;
    arg3 = null;
    arg4 = null;
    bundle = "org.apache.struts.action.MESSAGE";
    key = null;
    name = null;
    property = null;
    scope = null;
    localeKey = "org.apache.struts.action.LOCALE";
  }
}
