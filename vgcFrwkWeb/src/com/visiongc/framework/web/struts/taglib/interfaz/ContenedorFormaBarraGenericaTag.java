package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class ContenedorFormaBarraGenericaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraGenerica";
  protected String height = null;
  
  public ContenedorFormaBarraGenericaTag() {}
  
  public String getHeight() { return height; }
  
  public void setHeight(String height)
  {
    this.height = height;
  }
  
  public int doStartTag() throws JspException {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") == null) {
      throw new JspException(
        "El tag ContenedorFormaBarraGenerica debe estar dentro de un tag ContenedorForma");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    String barraGenerica = bodyContent.getString();
    
    String height = "";
    if ((this.height != null) && (!this.height.equals(""))) {
      height = " height=\"" + this.height + "\" ";
    }
    if ((barraGenerica != null) && (!barraGenerica.equals(""))) {
      resultado.append("      <tr>\n");
      resultado.append("        <td colspan=\"2\"" + height + ">" + "\n");
      resultado.append(barraGenerica);
      resultado.append("        </td>\n");
      resultado.append("      </tr>\n");
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraGenerica", resultado.toString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
