package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;








public class MostrarPanelContenedorJsTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.MostrarPanelContenedorJs";
  
  public MostrarPanelContenedorJsTag() {}
  
  protected String nombreContenedor = null;
  
  protected String nombrePanel = null;
  
  public String getNombreContenedor() {
    return nombreContenedor;
  }
  
  public void setNombreContenedor(String nombreContenedor) {
    this.nombreContenedor = nombreContenedor;
  }
  
  public String getNombrePanel() {
    return nombrePanel;
  }
  
  public void setNombrePanel(String nombrePanel) {
    this.nombrePanel = nombrePanel;
  }
  
  public int doStartTag() throws JspException
  {
    return 0;
  }
  


  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("mostrarPanelContenedorPaneles(contenedorPaneles" + getNombreContenedor() + ", 'panelContenedor" + getNombreContenedor() + getNombrePanel() + "', 'contenedorPanelesEncabezados" + getNombreContenedor() + "', 'contenedorPanelesEncabezado" + getNombreContenedor() + getNombrePanel() + "');" + "\n");
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
