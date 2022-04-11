package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ColumnaVisorListaInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;






public class ColumnaVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Columna";
  
  public ColumnaVisorListaTag() {}
  
  private static String ALINEACION_POR_DEFECTO = "center";
  
  protected String nombre = null;
  
  protected String align = null;
  
  protected String onclick = null;
  
  protected String width = null;
  
  protected String titulo = null;
  
  private VisorListaTag visorLista = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public int doStartTag() throws JspException {
    visorLista = ((VisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista"));
    
    if (visorLista == null) {
      throw new JspException("El tag ColumnaVisorLista debe estar dentro de un tag VisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Columna", this);
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    ColumnaVisorListaInfo columna = new ColumnaVisorListaInfo();
    
    columna.setNombre(getNombre());
    
    columna.setAncho(getWidth());
    
    String alineacion = ALINEACION_POR_DEFECTO;
    
    if ((align != null) && (!align.equals(""))) {
      alineacion = align;
    }
    columna.setAlineacion(alineacion);
    
    String titulo = this.titulo;
    
    if (bodyContent != null) {
      titulo = bodyContent.getString();
    }
    
    columna.setTitulo(HtmlUtil.trimTextoHtml(titulo));
    
    columna.setTextoOrdenAscDesc(getMessageResource(null, null, "boton.ascendentedescendente.alt"));
    
    columna.setOnclick(onclick);
    
    columna.setUrlAplicacion(getUrlAplicacion());
    
    columna.setVisorLista(visorLista);
    
    visorLista.getColumnas().add(columna, nombre);
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Columna");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
