package com.visiongc.commons.struts.tag.util;



public class BotonMenuInfo
{
  private String codigoHtml;
  
  private String titulo;
  
  private boolean tieneImagen = false;
  
  public BotonMenuInfo() {}
  
  public String getCodigoHtml(int ancho) { String width = "; width:" + Integer.toString(ancho) + "px";
    String resultado = codigoHtml.replaceAll("#codigoAncho#", width);
    width = "; width:" + Integer.toString(ancho) + "px";
    return resultado.replaceAll("#codigoAnchoSeparador#", width);
  }
  
  public void setCodigoHtml(String codigoHtml) {
    this.codigoHtml = codigoHtml;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public boolean isTieneImagen() {
    return tieneImagen;
  }
  
  public void setTieneImagen(boolean tieneImagen) {
    this.tieneImagen = tieneImagen;
  }
}
