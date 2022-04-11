package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;

public class ContenedorMenuHorizontalTag extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorMenuHorizontal";
  
  public ContenedorMenuHorizontalTag() {}
  
  public int doStartTag() throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorMenuHorizontal", this);
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorMenuHorizontal");
    
    String contenido = bodyContent.getString();
    
    if ((contenido != null) && (!contenido.equals(""))) {
      StringBuffer resultado = new StringBuffer();
      
      resultado.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
      resultado.append("  <tr>\n");
      
      resultado.append(contenido);
      
      resultado.append("  </tr>\n");
      resultado.append("</table>\n");
      
      TagUtils.getInstance().write(pageContext, resultado.toString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
