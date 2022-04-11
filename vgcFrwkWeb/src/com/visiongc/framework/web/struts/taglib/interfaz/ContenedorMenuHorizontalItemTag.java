package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;






public class ContenedorMenuHorizontalItemTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorMenuHorizontal.Item";
  
  public ContenedorMenuHorizontalItemTag() {}
  
  protected String width = null;
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public int doStartTag() throws JspException {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorMenuHorizontal") == null) {
      throw new JspException("El tag ContenedorMenuHorizontalItem debe estar dentro de un tag ContenedorMenuHorizontal");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if ((bodyContent != null) && (!bodyContent.equals("")))
    {

      StringBuffer resultado = new StringBuffer();
      if ((width != null) && (!width.equals(""))) {
        resultado.append("    <td width='" + width + "'>");
      } else {
        resultado.append("    <td>");
      }
      resultado.append(bodyContent.getString());
      resultado.append("</td>\n");
      TagUtils.getInstance().write(pageContext, resultado.toString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
