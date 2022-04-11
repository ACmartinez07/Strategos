package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;






public class ContenedorTamanoFijoTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorEstatico";
  private String width;
  
  public ContenedorTamanoFijoTag() {}
  
  public String getWidth()
  {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public int doStartTag() throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorEstatico", this);
    
    TagUtils.getInstance().write(pageContext, agregarInicio());
    
    return 2;
  }
  
  private String agregarInicio()
  {
    StringBuffer resultado = new StringBuffer();
    
    String ancho = "width:";
    
    if ((width != null) && (!width.equals(""))) {
      ancho = ancho + width + "; ";
    }
    else {
      ancho = ancho + "100%; ";
    }
    
    resultado.append("<div style=\"overflow: auto; " + ancho + "height:100%; border-width: 1px; border-color: #666666; border-style: solid\">" + "\n");
    
    return resultado.toString();
  }
  
  private String agregarFin() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (bodyContent != null) {
      resultado.append(HtmlUtil.trimTextoHtml(bodyContent.getString()));
    }
    resultado.append("</div>\n");
    
    return resultado.toString();
  }
  
  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append(agregarFin());
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorEstatico");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
