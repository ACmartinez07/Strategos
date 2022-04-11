package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.PanelContenedorInfo;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;





public class PanelContenedorTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedor";
  
  public PanelContenedorTag() {}
  
  protected String nombre = null;
  
  protected String anchoPestana = null;
  
  protected String titulo = null;
  
  protected String mostrarBorde = null;
  
  protected String onclick = null;
  
  protected ContenedorPanelesTag contenedor = null;
  
  private PanelContenedorInfo panelInfo = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAnchoPestana() {
    return anchoPestana;
  }
  
  public void setAnchoPestana(String anchoPestana) {
    this.anchoPestana = anchoPestana;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getMostrarBorde() {
    return mostrarBorde;
  }
  
  public void setMostrarBorde(String mostrarBorde) {
    this.mostrarBorde = mostrarBorde;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public int doStartTag() throws JspException {
    contenedor = ((ContenedorPanelesTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles"));
    
    if (contenedor == null) {
      throw new JspException("El tag PanelContenedor debe estar dentro de un tag ContenedorPaneles");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedor", this);
    
    panelInfo = new PanelContenedorInfo();
    
    panelInfo.setNombre(getNombre());
    panelInfo.setTitulo(getTitulo());
    panelInfo.setAnchoPestana(getAnchoPestana());
    panelInfo.setOnclick(getOnclick());
    
    contenedor.getPaneles().add(panelInfo);
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo") != null) {
      panelInfo.setTitulo((String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo"));
      pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo");
    }
    
    if ((bodyContent != null) && (!bodyContent.equals("")))
    {
      StringBuffer resultado = new StringBuffer();
      
      resultado.append("\n    <div style=\"width:100%; height:100%\" id=\"panelContenedor" + contenedor.getNombre() + getNombre() + "\">" + "\n");
      
      String claseEstilos = "class=\"tabCuerpo\"";
      if ((mostrarBorde != null) && (!mostrarBorde.equalsIgnoreCase("true"))) {
        claseEstilos = "class=\"tabCuerpoSinBorde\"";
      }
      resultado.append("    <table " + claseEstilos + " cellpadding=\"5\" cellspacing=\"0\">" + "\n");
      resultado.append("      <tr valign=\"top\">\n");
      resultado.append("        <td>\n");
      
      resultado.append(bodyContent.getString());
      
      resultado.append("\n        </td>\n");
      resultado.append("      </tr>\n");
      resultado.append("    </table>\n");
      resultado.append("    </div>\n");
      
      TagUtils.getInstance().write(pageContext, resultado.toString());
    }
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedor");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
