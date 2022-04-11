package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;





public class BarraHerramientasBotonTituloTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBotonTitulo";
  
  public BarraHerramientasBotonTituloTag() {}
  
  protected BarraHerramientasBotonTag barraHerramientasBoton = null;
  
  public int doStartTag() throws JspException {
    barraHerramientasBoton = ((BarraHerramientasBotonTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBoton"));
    
    if (barraHerramientasBoton == null) {
      throw new JspException("El tag BarraHerramientasBotonTitulo debe estar dentro de un tag BarraHerramientasBoton");
    }
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    if ((bodyContent != null) && (bodyContent.getString() != null)) {
      barraHerramientasBoton.setTitulo(HtmlUtil.trimTextoHtml(bodyContent.getString()));
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
