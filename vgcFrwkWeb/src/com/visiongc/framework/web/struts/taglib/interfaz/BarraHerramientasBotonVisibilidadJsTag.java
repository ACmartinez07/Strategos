package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;








public class BarraHerramientasBotonVisibilidadJsTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBoton";
  
  public BarraHerramientasBotonVisibilidadJsTag() {}
  
  protected String nombreBarra = null;
  
  protected String nombreBoton = null;
  
  protected String display = null;
  
  public String getNombreBarra() {
    return nombreBarra;
  }
  
  public void setNombreBarra(String nombreBarra) {
    this.nombreBarra = nombreBarra;
  }
  
  public String getNombreBoton() {
    return nombreBoton;
  }
  
  public void setNombreBoton(String nombreBoton) {
    this.nombreBoton = nombreBoton;
  }
  
  public String getDisplay() {
    return display;
  }
  
  public void setDisplay(String display) {
    this.display = display;
  }
  
  public int doStartTag() throws JspException
  {
    String codigo = "var imagen" + nombreBarra + nombreBoton + " = document.getElementById('barraHerramientas" + nombreBarra + "ImagenBoton" + nombreBoton + "');if (imagen" + nombreBarra + nombreBoton + " != null) {imagen" + nombreBarra + nombreBoton + ".style.display = '" + display + "'};";
    
    TagUtils.getInstance().write(pageContext, codigo);
    
    return 0;
  }
  


  public void release()
  {
    super.release();
  }
}
