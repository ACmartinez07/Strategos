package com.visiongc.framework.web.struts.taglib.logica;

import javax.servlet.jsp.JspException;










public class TamanoColeccionIgualTag
  extends VgcBaseComparacionTamanoColeccionTag
{
  static final long serialVersionUID = 0L;
  
  public TamanoColeccionIgualTag() {}
  
  protected boolean condition()
    throws JspException
  {
    return condition(0, 0);
  }
}
