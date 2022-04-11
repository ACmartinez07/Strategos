package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;







public class BarraHerramientasSeparadorTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasSeparador";
  
  public BarraHerramientasSeparadorTag() {}
  
  protected BarraHerramientasTag barraHerramientas = null;
  
  public int doStartTag() throws JspException {
    barraHerramientas = ((BarraHerramientasTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas"));
    
    if (barraHerramientas == null) {
      throw new JspException("El tag BarraHerramientasSeparador debe estar dentro de un tag BarraHerramientas");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasSeparador", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("<td><img src=\"" + getUrlAplicacion() + "/componentes/barraHerramientas/separador.gif\" border=\"0\" width=\"1px\" height=\"25px\"></td>" + "\n");
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasSeparador");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
