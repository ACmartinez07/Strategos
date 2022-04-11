package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;





public class ColumnaAccionesVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ColumnaAcciones";
  
  public ColumnaAccionesVisorListaTag() {}
  
  private static String ALINEACION_POR_DEFECTO = "center";
  
  protected String align = null;
  
  protected String width = null;
  
  protected String titulo = null;
  
  private VisorListaTag visorLista = null;
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public int doStartTag() throws JspException {
    visorLista = ((VisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista"));
    
    if (visorLista == null) {
      throw new JspException("El tag ColumnaAccionesVisorLista debe estar dentro de un tag VisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ColumnaAcciones", this);
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if ((align == null) || (align.equals(""))) {
      align = ALINEACION_POR_DEFECTO;
    }
    
    if (bodyContent != null) {
      titulo = bodyContent.getString();
    }
    titulo = HtmlUtil.trimTextoHtml(titulo);
    
    visorLista.setColumnaAcciones(this);
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ColumnaAcciones");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
