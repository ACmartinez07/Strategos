package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;







public class BarraHerramientasEspacioTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasEspacio";
  protected String ancho;
  
  public BarraHerramientasEspacioTag() {}
  
  protected BarraHerramientasTag barraHerramientas = null;
  
  public String getAncho() {
    return ancho;
  }
  
  public void setAncho(String ancho) {
    this.ancho = ancho;
  }
  
  public int doStartTag() throws JspException {
    barraHerramientas = ((BarraHerramientasTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas"));
    
    if (barraHerramientas == null) {
      throw new JspException("El tag BarraHerramientasEspacio debe estar dentro de un tag BarraHerramientas");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasEspacio", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("<td width=\"" + ancho + "\" >");
    resultado.append(getBodyContent().getString());
    resultado.append("</td>\n");
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    clear();
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasEspacio");
    
    return 6;
  }
  
  private void clear() {
    ancho = null;
  }
  


  public void release()
  {
    super.release();
  }
}
