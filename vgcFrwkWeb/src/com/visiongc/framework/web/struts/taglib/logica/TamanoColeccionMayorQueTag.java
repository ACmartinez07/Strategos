package com.visiongc.framework.web.struts.taglib.logica;

import javax.servlet.jsp.JspException;

public class TamanoColeccionMayorQueTag extends VgcBaseComparacionTamanoColeccionTag {
  static final long serialVersionUID = 0L;
  
  public TamanoColeccionMayorQueTag() {}
  
  protected boolean condition() throws JspException { return condition(1, 1); }
}
