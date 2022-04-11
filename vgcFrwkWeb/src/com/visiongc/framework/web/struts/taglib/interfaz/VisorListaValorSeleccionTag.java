package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.VisorListaValorSeleccionInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;




public class VisorListaValorSeleccionTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorSeleccion";
  
  public VisorListaValorSeleccionTag() {}
  
  protected FilasVisorListaTag filasVisorLista = null;
  
  public int doStartTag() throws JspException {
    filasVisorLista = ((FilasVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas"));
    
    if (filasVisorLista == null) {
      throw new JspException("El tag AccionVisorLista debe estar dentro de un tag FilasVisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorSeleccion", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("    <td align=\"center\">");
    if (filasVisorLista.getVisorLista().getColumnaSeleccion() != null) {
      resultado.append("<input name=\"" + filasVisorLista.getVisorLista().getColumnaSeleccion().getNombreCampoObjetoId() + "\" type=\"checkbox\" class=\"botonSeleccionMultiple\" value=\"" + HtmlUtil.trimTextoHtml(bodyContent.getString()) + "\">");
    } else {
      resultado.append("&nbsp;");
    }
    resultado.append("</td>\n");
    
    VisorListaValorSeleccionInfo valorSeleccion = new VisorListaValorSeleccionInfo();
    
    valorSeleccion.setCodigo(resultado.toString());
    
    filasVisorLista.getFila().setValorSeleccion(valorSeleccion);
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorSeleccion");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
