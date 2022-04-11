package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.PanelContenedorInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;







public class ContenedorPanelesTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles";
  
  public ContenedorPanelesTag() {}
  
  protected String nombre = null;
  
  protected String width = null;
  
  protected String height = null;
  
  protected String mostrarSelectoresPaneles = null;
  
  protected String nroSelectoresPorFila = null;
  
  protected List paneles = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public String getHeight() {
    return height;
  }
  
  public void setHeight(String height) {
    this.height = height;
  }
  
  public String getMostrarSelectoresPaneles() {
    return mostrarSelectoresPaneles;
  }
  
  public void setMostrarSelectoresPaneles(String mostrarSelectoresPaneles) {
    this.mostrarSelectoresPaneles = mostrarSelectoresPaneles;
  }
  
  public String getNroSelectoresPorFila() {
    return nroSelectoresPorFila;
  }
  
  public void setNroSelectoresPorFila(String nroSelectoresPorFila) {
    this.nroSelectoresPorFila = nroSelectoresPorFila;
  }
  
  public List getPaneles() {
    return paneles;
  }
  
  public void setPaneles(List paneles) {
    this.paneles = paneles;
  }
  


  public int doStartTag()
    throws JspException
  {
    setPaneles(new ArrayList());
    
    TagUtils.getInstance().write(pageContext, agregarInicioContenedorPaneles());
    
    Stack anidados = null;
    Stack titulosAnidados = null;
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles") != null) {
      anidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.anidados");
      titulosAnidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo.anidados");
      anidados.push(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles"));
      titulosAnidados.push(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo"));
    } else {
      anidados = new Stack();
      titulosAnidados = new Stack();
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.anidados", anidados);
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo.anidados", titulosAnidados);
    }
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles", this);
    
    return 2;
  }
  
  private String agregarInicioContenedorPaneles() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (pageContext.getRequest().getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles") == null) {
      resultado.append("\n<script language=\"Javascript\" src=\"" + getUrlAplicacion() + "/componentes/contenedorPaneles/contenedorPaneles.js\">");
      resultado.append("</script>\n");
      pageContext.getRequest().setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles", "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles");
    }
    
    resultado.append("\n<script language=\"Javascript\">\n");
    resultado.append("var contenedorPaneles" + getNombre() + " = new Array();" + "\n");
    resultado.append("</script>\n");
    

    resultado.append("\n<table cellpadding=\"0\" cellspacing=\"0\" ");
    
    resultado.append(" width=\"");
    if ((width != null) && (width.length() > 0)) {
      resultado.append(width + "\"");
    }
    resultado.append(" height=\"");
    if ((height != null) && (height.length() > 0)) {
      resultado.append(height + "\">" + "\n");
    }
    
    return resultado.toString();
  }
  






  private int agregarSelectoresPaneles(StringBuffer resultado)
  {
    int nroSelectoresPorFila = getPaneles().size();
    
    if ((this.nroSelectoresPorFila != null) && (!this.nroSelectoresPorFila.equals(""))) {
      nroSelectoresPorFila = Integer.parseInt(this.nroSelectoresPorFila);
    }
    
    int nroFilas = 1;
    int resto = 0;
    
    if (getPaneles().size() > nroSelectoresPorFila) {
      resto = getPaneles().size() % nroSelectoresPorFila;
      nroFilas = getPaneles().size() / nroSelectoresPorFila;
      if (resto > 0) {
        nroFilas++;
      }
      resultado.append("  <tr>\n");
      resultado.append("    <td>\n");
      resultado.append("    <table cellspacing=\"0\" cellpadding=\"0\">\n");
      resultado.append("      <tr>\n");
      resultado.append("        <td>\n");
      resultado.append("        <table cellspacing=\"0\" cellpadding=\"0\">\n");
      resultado.append("          <tr id=\"contenedorPanelesEncabezados" + getNombre() + Integer.toString(nroFilas) + "\">" + "\n");
    } else {
      resultado.append("  <tr>\n");
      resultado.append("    <td>\n");
      resultado.append("    <table cellspacing=\"0\" cellpadding=\"0\">\n");
      resultado.append("      <tr id=\"contenedorPanelesEncabezados" + getNombre() + "\">" + "\n");
    }
    
    int nroFila = nroFilas;
    int indiceElemento = resto;
    if (indiceElemento == 0) {
      indiceElemento = nroSelectoresPorFila;
    }
    for (Iterator i = getPaneles().iterator(); i.hasNext();) {
      PanelContenedorInfo panel = (PanelContenedorInfo)i.next();
      if (nroFilas > 1) {
        resultado.append("    ");
      }
      String onclick = "javascript:mostrarPanelContenedorPaneles(contenedorPaneles" + getNombre() + ", 'panelContenedor" + getNombre() + panel.getNombre() + "', 'contenedorPanelesEncabezados" + getNombre() + "','contenedorPanelesEncabezado" + getNombre() + panel.getNombre() + "')";
      if ((panel.getOnclick() != null) && (!panel.getOnclick().equals(""))) {
        onclick = panel.getOnclick();
      }
      resultado.append("        <td id=\"contenedorPanelesEncabezado" + getNombre() + panel.getNombre() + "\" width=\"" + panel.getAnchoPestana() + "\" onclick=\"" + onclick + "\">");
      resultado.append(panel.getTitulo());
      resultado.append("</td>\n");
      if (nroFilas > 1) {
        indiceElemento--;
        if (indiceElemento == 0) {
          nroFila--;
          resultado.append("          </tr>\n");
          resultado.append("        </table>\n");
          resultado.append("        </td>\n");
          resultado.append("      </tr>\n");
          if (nroFila > 0) {
            resultado.append("      <tr>\n");
            resultado.append("        <td>\n");
            resultado.append("        <table cellspacing=\"0\" cellpadding=\"0\">\n");
            resultado.append("          <tr id=\"contenedorPanelesEncabezados" + getNombre() + Integer.toString(nroFila) + "\">" + "\n");
            indiceElemento = nroSelectoresPorFila;
          } else {
            resultado.append("    </table>\n");
          }
        }
      }
    }
    
    if (nroFilas > 1) {
      resultado.append("    </td>\n");
      resultado.append("  </tr>\n");
    } else {
      resultado.append("      </tr>\n");
      resultado.append("    </table>\n");
      resultado.append("    </td>\n");
      resultado.append("  </tr>\n");
    }
    return nroFilas;
  }
  
  private void agregarInicioPaneles(StringBuffer resultado, int nroFilas)
  {
    String altoContenedor = "height=\"100%\"";
    if ((mostrarSelectoresPaneles != null) && (!mostrarSelectoresPaneles.equalsIgnoreCase("true"))) {
      altoContenedor = "height=\"" + height + "\"";
    }
    resultado.append("  <tr " + altoContenedor + ">" + "\n");
    String colspanPanel = Integer.toString(getPaneles().size() + 1);
    if (nroFilas > 1) {
      colspanPanel = "1";
    }
    resultado.append("    <td " + altoContenedor + " align=\"center\" colspan=\"" + colspanPanel + "\">" + "\n");
    resultado.append("    <div style=\"height: 100%; width: 100%\" id=\"contenedorPanelesPrincipal" + getNombre() + "\">" + "\n");
  }
  
  private void agregarFinContenedorPaneles(StringBuffer resultado) {
    resultado.append("\n    </div>\n");
    resultado.append("\t  </td>\n");
    resultado.append("\t</tr>\n");
    resultado.append("</table>\n");
    resultado.append("<script language=\"Javascript\">\n");
    PanelContenedorInfo panel1 = (PanelContenedorInfo)getPaneles().iterator().next();
    resultado.append("    configurarContenedorPaneles(contenedorPaneles" + getNombre() + ", 'contenedorPanelesPrincipal" + getNombre() + "');" + "\n");
    resultado.append("    mostrarPanelContenedorPaneles(contenedorPaneles" + getNombre() + ", 'panelContenedor" + getNombre() + panel1.getNombre() + "', 'contenedorPanelesEncabezados" + getNombre() + "', 'contenedorPanelesEncabezado" + getNombre() + panel1.getNombre() + "');" + "\n");
    resultado.append("</script>");
  }
  



  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    int nroFilas = 1;
    if ((mostrarSelectoresPaneles == null) || (mostrarSelectoresPaneles.equalsIgnoreCase("true"))) {
      nroFilas = agregarSelectoresPaneles(resultado);
    }
    
    agregarInicioPaneles(resultado, nroFilas);
    
    resultado.append(bodyContent.getString());
    
    agregarFinContenedorPaneles(resultado);
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    Stack anidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.anidados");
    Stack titulosAnidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo.anidados");
    if (anidados != null) {
      if (anidados.empty()) {
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.anidados");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo.anidados");
      } else {
        pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles", anidados.pop());
        pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo", titulosAnidados.pop());
      }
    } else {
      pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles");
    }
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
