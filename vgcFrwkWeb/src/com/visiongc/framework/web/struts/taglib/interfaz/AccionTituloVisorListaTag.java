package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class AccionTituloVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.AccionTitulo";
  private AccionVisorListaTag accion = null;
  
  public AccionTituloVisorListaTag() {}
  
  public int doStartTag() throws JspException { accion = ((AccionVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Accion"));
    if (accion == null) {
      throw new JspException("El tag AccionTituloVisorLista debe estar dentro de un tag AccionVisorLista");
    }
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    if (bodyContent != null) {
      accion.setTitulo(bodyContent.getString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
