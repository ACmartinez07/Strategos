package com.visiongc.commons.web;

public class NavigationUrl
{
  private String nombre = null;
  private String url = null;
  
  public NavigationUrl(String nombre, String url) {
    this.nombre = nombre;
    this.url = url;
  }
  
  public NavigationUrl() {
    nombre = null;
    url = null;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}
