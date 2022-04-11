package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;






public class BarraHerramientasTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas";
  
  public BarraHerramientasTag() {}
  
  protected String nombre = null;
  
  protected String agregarSeparadorSuperior = null;
  
  protected String agregarSeparadorInferior = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAgregarSeparadorInferior() {
    return agregarSeparadorInferior;
  }
  
  public void setAgregarSeparadorInferior(String agregarSeparadorInferior) {
    this.agregarSeparadorInferior = agregarSeparadorInferior;
  }
  
  public String getAgregarSeparadorSuperior() {
    return agregarSeparadorSuperior;
  }
  
  public void setAgregarSeparadorSuperior(String agregarSeparadorSuperior) {
    this.agregarSeparadorSuperior = agregarSeparadorSuperior;
  }
  


  public int doStartTag()
    throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas", this);
    
    return 2;
  }
  







  private String agregarInicioBarraHerramientas()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas.js") == null) {
      resultado.append("\n<script language=\"Javascript\" src=\"" + getUrlAplicacion() + "/componentes/barraHerramientas/barraHerramientas.js\"></script>" + "\n");
    } else {
      resultado.append("\n");
    }
    String estilo = "style=\"";
    boolean hayEstilo = false;
    if ((agregarSeparadorSuperior != null) && (agregarSeparadorSuperior.equalsIgnoreCase("true"))) {
      estilo = estilo + "border-top-color: #999999; border-top-style: solid; border-top-width: 1px; ";
      hayEstilo = true;
    }
    if ((agregarSeparadorInferior != null) && (agregarSeparadorInferior.equalsIgnoreCase("true"))) {
      estilo = estilo + "border-bottom-color: #999999; border-bottom-style: solid; border-bottom-width: 1px; ";
      hayEstilo = true;
    }
    if (hayEstilo) {
      estilo = estilo + "\"";
    } else {
      estilo = "";
    }
    resultado.append("<table id=\"" + nombre + "\" width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" " + estilo + " >" + "\n");
    resultado.append("\t<tr class=\"barraFiltrosForma\">\n");
    
    return resultado.toString();
  }
  
  private String agregarFinalBarraHerramientas() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    resultado.append("    <td width=\"100%\" >&nbsp;</td>\n");
    resultado.append("\t</tr>\n");
    resultado.append("</table>\n");
    
    return resultado.toString();
  }
  
  public int doEndTag() throws JspException
  {
    TagUtils.getInstance().write(pageContext, agregarInicioBarraHerramientas());
    
    TagUtils.getInstance().write(pageContext, bodyContent.getString());
    
    TagUtils.getInstance().write(pageContext, agregarFinalBarraHerramientas());
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas");
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas.js", "true");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
