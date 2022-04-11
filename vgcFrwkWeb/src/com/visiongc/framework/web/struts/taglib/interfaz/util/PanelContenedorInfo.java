package com.visiongc.framework.web.struts.taglib.interfaz.util;








public class PanelContenedorInfo
{
  protected String nombre = null;
  
  protected String titulo = null;
  
  protected String anchoPestana = null;
  protected String onclick;
  
  public PanelContenedorInfo() {}
  
  public String getNombre() { return nombre; }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getAnchoPestana() {
    return anchoPestana;
  }
  
  public void setAnchoPestana(String anchoPestana) {
    this.anchoPestana = anchoPestana;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
}
