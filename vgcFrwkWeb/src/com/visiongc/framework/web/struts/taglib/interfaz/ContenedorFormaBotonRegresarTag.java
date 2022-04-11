package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;

public class ContenedorFormaBotonRegresarTag extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonRegresar";
  
  public ContenedorFormaBotonRegresarTag() {}
  
  public int doStartTag() throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") == null) {
      throw new JspException("El tag ContenedorFormaBotonRegresar debe estar dentro de un tag ContenedorForma");
    }
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if (bodyContent != null) {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonRegresar", bodyContent.getString());
    }
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
