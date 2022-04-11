package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;






public class VisorListaColumnaSeleccionTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ColumnaSeleccion";
  
  public VisorListaColumnaSeleccionTag() {}
  
  private static String ALINEACION_POR_DEFECTO = "center";
  
  private static String ANCHO_POR_DEFECTO = "20px";
  
  protected String align = null;
  
  protected String width = null;
  
  protected String nombreFormaHtml = null;
  
  protected String nombreCampoObjetoId = null;
  
  private VisorListaTag visorLista = null;
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public String getNombreCampoObjetoId() {
    return nombreCampoObjetoId;
  }
  
  public void setNombreCampoObjetoId(String nombreCampoObjetoId) {
    this.nombreCampoObjetoId = nombreCampoObjetoId;
  }
  
  public String getNombreFormaHtml() {
    return nombreFormaHtml;
  }
  
  public void setNombreFormaHtml(String nombreFormaHtml) {
    this.nombreFormaHtml = nombreFormaHtml;
  }
  
  public int doStartTag() throws JspException {
    visorLista = ((VisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista"));
    
    if (visorLista == null) {
      throw new JspException("El tag ColumnaAccionesVisorLista debe estar dentro de un tag VisorLista");
    }
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    if ((align == null) || (align.equals(""))) {
      align = ALINEACION_POR_DEFECTO;
    }
    
    if ((width == null) || (width.equals(""))) {
      width = ANCHO_POR_DEFECTO;
    }
    
    visorLista.setColumnaSeleccion(this);
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
