package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class ContenedorFormaBarraInferiorTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraInferior";
  protected String idBarraInferior = null;
  
  public ContenedorFormaBarraInferiorTag() {}
  
  public String getIdBarraInferior() { return idBarraInferior; }
  
  public void setIdBarraInferior(String idBarraInferior)
  {
    this.idBarraInferior = idBarraInferior;
  }
  
  public int doStartTag() throws JspException {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") == null) {
      throw new JspException(
        "El tag ContenedorFormaBarraInferior debe estar dentro de un tag ContenedorForma");
    }
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if ((bodyContent != null) && (!bodyContent.equals("")))
    {

      StringBuffer resultado = new StringBuffer();
      

      resultado.append("      <tr class=\"barraInferiorForma\">\n");
      
      if (idBarraInferior == null) {
        idBarraInferior = "";
      }
      
      String paginador = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Paginador");
      if ((paginador == null) || (paginador.equals(""))) {
        resultado.append("        <td colspan=\"2\" id=\"" + 
          idBarraInferior + "\">");
        
        resultado.append(bodyContent.getString());
      } else {
        resultado.append("        <td colspan=\"2\">\n");
        resultado.append("          <table width=\"100%\" class=\"barraInferiorForma\" cellpadding=\"0\" cellspacing=\"0\">");
        resultado.append("            <tr>\n");
        resultado.append("              <td valign=\"middle\" align=\"left\">\n");
        resultado.append(paginador);
        resultado.append("              </td>");
        resultado.append("              <td align=\"right\" valign=\"middle\" id=\"" + 
          idBarraInferior + "\">");
        resultado.append(bodyContent.getString());
        resultado.append("              </td>");
        resultado.append("            </tr>");
        resultado.append("          </table>");
      }
      resultado.append("</td>\n");
      resultado.append("      </tr>\n");
      
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraInferior", resultado.toString());
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
