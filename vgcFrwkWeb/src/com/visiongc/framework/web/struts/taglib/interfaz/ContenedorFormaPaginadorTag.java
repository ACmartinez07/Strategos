package com.visiongc.framework.web.struts.taglib.interfaz;

import javax.servlet.jsp.JspException;

public class ContenedorFormaPaginadorTag extends com.visiongc.commons.struts.tag.VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Paginador";
  
  public ContenedorFormaPaginadorTag() {}
  
  public int doStartTag() throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") == null) {
      throw new JspException(
        "El tag ContenedorFormaPaginador debe estar dentro de un tag ContenedorForma");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if (bodyContent != null) {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Paginador", bodyContent.getString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
