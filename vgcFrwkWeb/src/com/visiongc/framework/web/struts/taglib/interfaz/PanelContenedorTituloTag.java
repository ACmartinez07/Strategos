package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;




public class PanelContenedorTituloTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo";
  
  public PanelContenedorTituloTag() {}
  
  public int doStartTag()
    throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedor") == null) {
      throw new JspException("El tag PanelContenedorTitulo debe estar dentro de un tag ContenedorPaneles");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if ((bodyContent != null) && (!bodyContent.equals(""))) {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo", bodyContent.getString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
