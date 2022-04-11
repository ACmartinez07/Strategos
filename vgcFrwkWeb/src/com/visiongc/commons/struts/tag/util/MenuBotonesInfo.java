package com.visiongc.commons.struts.tag.util;

import java.util.List;


public class MenuBotonesInfo
{
  private StringBuffer codigoHtml;
  private String titulo;
  private List<Object> botonesMenu;
  private MenuBotonesInfo menuPadre;
  
  public MenuBotonesInfo() {}
  
  public String getCodigoHtml(int ancho)
  {
    String width = "; width:" + Integer.toString(ancho) + "px";
    String resultado = codigoHtml.toString().replaceAll("#codigoAncho#", width);
    String desplazamientoSubMenu = "left:" + Integer.toString(ancho) + "px";
    return resultado.replaceAll("#offsetSubMenu#", desplazamientoSubMenu);
  }
  
  public StringBuffer getCodigoHtml()
  {
    return codigoHtml;
  }
  
  public void setCodigoHtml(StringBuffer codigoHtml)
  {
    this.codigoHtml = codigoHtml;
  }
  
  public String getTitulo()
  {
    return titulo;
  }
  
  public void setTitulo(String titulo)
  {
    this.titulo = titulo;
  }
  
  public List<Object> getBotonesMenu()
  {
    return botonesMenu;
  }
  
  public void setBotonesMenu(List<Object> botonesMenu)
  {
    this.botonesMenu = botonesMenu;
  }
  
  public MenuBotonesInfo getMenuPadre()
  {
    return menuPadre;
  }
  
  public void setMenuPadre(MenuBotonesInfo menuPadre)
  {
    this.menuPadre = menuPadre;
  }
}
