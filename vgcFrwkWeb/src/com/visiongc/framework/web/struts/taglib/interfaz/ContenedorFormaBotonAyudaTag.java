package com.visiongc.framework.web.struts.taglib.interfaz;

import javax.servlet.jsp.JspException;

public class ContenedorFormaBotonAyudaTag extends com.visiongc.commons.struts.tag.VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonAyuda";
  
  public ContenedorFormaBotonAyudaTag() {}
  
  public int doStartTag() throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") == null) {
      throw new JspException(
        "El tag ContenedorFormaBotonAyuda debe estar dentro de un tag ContenedorForma");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if (bodyContent != null) {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonAyuda", bodyContent.getString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
