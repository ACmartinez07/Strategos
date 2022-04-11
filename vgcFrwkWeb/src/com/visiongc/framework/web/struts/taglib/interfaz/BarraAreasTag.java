package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;









public class BarraAreasTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas";
  private static final String ANCHO_POR_DEFECTO = "100px";
  private int numeroBotones = 0;
  
  protected String nombre = null;
  protected String width = null;
  
  public BarraAreasTag() {}
  
  public String getNombre() { return nombre; }
  

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getWidth()
  {
    return width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
  
  public int getNumeroBotones()
  {
    return numeroBotones;
  }
  
  public void setNumeroBotones(int numeroBotones)
  {
    this.numeroBotones = numeroBotones;
  }
  


  public int doStartTag()
    throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas", this);
    
    numeroBotones = 0;
    
    if (pageContext.getSession().getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas" + nombre) == null)
    {
      BarraAreaInfo barraAreaInfo = new BarraAreaInfo();
      pageContext.getSession().setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas" + nombre, barraAreaInfo);
    }
    
    return 2;
  }
  







  private String agregarInicioBarraAreas(int numeroBotones)
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    String ancho = "100px";
    if ((width != null) && (!width.equals(""))) {
      ancho = width;
    }
    resultado.append("\n<script language=\"Javascript\">\n");
    resultado.append("registrarScrollVerticalInvisible('botonBajarBarraAreas" + nombre + "', 'botonSubirBarraAreas" + nombre + "', 'barraAreasDiv" + nombre + "', 10, -10);" + "\n");
    resultado.append("</script>\n");
    
    resultado.append("<table class=\"barraIzquierdaPrincipal\" cellpadding=\"0\" cellspacing=\"2\" style=\"height: 100%; width: " + ancho + ";\">" + "\n");
    resultado.append("\t<tr>\n");
    resultado.append("\t\t<td id=\"botonSubirBarraAreas" + nombre + "\" style=\"cursor: pointer\" align=\"center\" >" + "\n");
    resultado.append("\t\t<div id=\"botonSubirBarraAreasDiv" + nombre + "\" style=\"overflow:hidden;\"><table cellpadding=\"0px\" cellspacing=\"0px\"><tr><td valign=\"top\">");
    resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/barraArea/subir.gif\" border=\"0\" title=\"" + getMessageResource(null, null, "tag.barraareas.subir") + "\"></td></tr></table>" + "\n");
    resultado.append("\t\t</div>\n");
    resultado.append("\t\t</td>\n");
    resultado.append("\t</tr>\n");
    resultado.append("\t<tr style=\"height: 100%; width: " + ancho + ";\">" + "\n");
    resultado.append("\t\t<td>\n");
    resultado.append("\t\t<div id=\"barraAreasDiv" + nombre + "\" style=\"position: relative; overflow: hidden; width:" + ancho + "; height:100%; top: 0px; left: 0px\">" + "\n");
    resultado.append("\t\t<div style=\"position:absolute; top: 0px; left: 0px\" id=\"barraAreas" + nombre + "\">" + "\n");
    resultado.append("\t\t<table style=\"height: 100%; width: " + ancho + ";\" cellpadding=\"2\" cellspacing=\"0\">" + "\n");
    
    return resultado.toString();
  }
  






  private String agregarFinalBarraAreas()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    resultado.append("\t\t\t<tr valign=\"top\" height=\"100%\">\n");
    resultado.append("\t\t\t\t<td>&nbsp;</td>\n");
    resultado.append("\t\t\t</tr>\n");
    resultado.append("\t\t</table>\n");
    resultado.append("\t\t</div>\n");
    resultado.append("\t\t</div>\n");
    resultado.append("\t\t</td>\n");
    resultado.append("\t</tr>\n");
    resultado.append("\t<tr>\n");
    resultado.append("\t\t<td id=\"botonBajarBarraAreas" + nombre + "\" style=\"cursor: pointer;\" align=\"center\" >" + "\n");
    resultado.append("\t\t<div id=\"botonBajarBarraAreasDiv" + nombre + "\" style=\"overflow:hidden\"><img src=\"" + getUrlAplicacion() + "/componentes/barraArea/bajar.gif\" border=\"0\" title=\"" + getMessageResource(null, null, "tag.barraareas.bajar") + "\"></div>" + "\n");
    resultado.append("\t\t</td>\n");
    resultado.append("\t</tr>\n");
    
    resultado.append("</table>\n");
    
    return resultado.toString();
  }
  


  public int doEndTag()
    throws JspException
  {
    TagUtils.getInstance().write(pageContext, agregarInicioBarraAreas(numeroBotones));
    TagUtils.getInstance().write(pageContext, bodyContent.getString());
    TagUtils.getInstance().write(pageContext, agregarFinalBarraAreas());
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas");
    
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
