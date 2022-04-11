package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.MenuBotonesTag;
import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.commons.struts.tag.util.BotonMenuInfo;
import com.visiongc.commons.struts.tag.util.MenuBotonesInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;



public class MenuAnidadoTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  protected String bundle = null;
  protected String key = null;
  protected String agregarSeparador = null;
  protected String localeKey = "org.apache.struts.action.LOCALE";
  
  private MenuBotonesTag menuBotonesTag = null;
  
  public MenuAnidadoTag() {}
  
  public String getBundle() { return bundle; }
  

  public void setBundle(String bundle)
  {
    this.bundle = bundle;
  }
  
  public String getKey()
  {
    return key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String getLocale()
  {
    return localeKey;
  }
  
  public void setLocale(String localeKey)
  {
    this.localeKey = localeKey;
  }
  
  public String getAgregarSeparador()
  {
    return agregarSeparador;
  }
  
  public void setAgregarSeparador(String agregarSeparador)
  {
    this.agregarSeparador = agregarSeparador;
  }
  


  public int doStartTag()
    throws JspException
  {
    menuBotonesTag = ((MenuBotonesTag)pageContext.getAttribute("com.visiongc.commons.struts.tag.MenuBotones"));
    if (menuBotonesTag == null) {
      throw new JspException("El tag MenuAnidado debe estar dentro de un tag MenuBotones");
    }
    MenuBotonesInfo menuBotonesInfo = new MenuBotonesInfo();
    
    menuBotonesInfo.setBotonesMenu(new ArrayList());
    menuBotonesInfo.setCodigoHtml(new StringBuffer());
    menuBotonesInfo.setMenuPadre(menuBotonesTag.getMenuBotonesInfo());
    menuBotonesTag.getMenuBotonesInfo().getBotonesMenu().add(menuBotonesInfo);
    if ((agregarSeparador != null) && (agregarSeparador.equalsIgnoreCase("true")))
      menuBotonesTag.getMenuBotonesInfo().getBotonesMenu().add(getSeparador());
    menuBotonesTag.setMenuBotonesInfo(menuBotonesInfo);
    menuBotonesInfo.getCodigoHtml().append(agregarInicioMenuAnidado());
    
    return 2;
  }
  
  private BotonMenuInfo getSeparador()
  {
    StringBuffer buf = new StringBuffer();
    BotonMenuInfo botonMenuInfo = new BotonMenuInfo();
    
    buf.append("      <li style=\"height:16px; padding-top:0px; padding-bottom:0px; padding-left:2px; padding-right:2px; padding-left:0px; padding-right:0px#codigoAnchoSeparador#\">");
    buf.append("<img src=\"" + getUrlAplicacion() + "/componentes/menu/separador.gif\" border=\"0\" style=\"height:3px" + "#codigoAnchoSeparador#" + "\"  />");
    buf.append("</li>\n");
    botonMenuInfo.setCodigoHtml(buf.toString());
    botonMenuInfo.setTitulo("");
    
    return botonMenuInfo;
  }
  






  private String agregarInicioMenuAnidado()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("  <li style=\"padding-top:0px; padding-bottom:0px; padding-left:0px; padding-right:0px#codigoAncho#\"><a href=\"#\">");
    
    String titulo = getMessageResource(bundle, localeKey, key);
    menuBotonesTag.getMenuBotonesInfo().setTitulo(titulo);
    
    resultado.append("&nbsp;&nbsp;" + titulo + "</a>" + "\n");
    resultado.append("    <ul class=\"menulist\" style=\"#offsetSubMenu#\">\n");
    
    return resultado.toString();
  }
  







  public int doEndTag()
    throws JspException
  {
    int longitudMaxima = 0;
    boolean hayBotonConImagen = false;
    for (Iterator<Object> iter = menuBotonesTag.getMenuBotonesInfo().getBotonesMenu().iterator(); iter.hasNext();)
    {
      Object objeto = iter.next();
      
      if ((objeto instanceof BotonMenuInfo))
      {
        BotonMenuInfo botonMenuInfo = (BotonMenuInfo)objeto;
        
        if (botonMenuInfo.isTieneImagen())
          hayBotonConImagen = true;
        if (botonMenuInfo.getTitulo().length() > longitudMaxima) {
          longitudMaxima = botonMenuInfo.getTitulo().length();
        }
      }
      else {
        MenuBotonesInfo mbInfo = (MenuBotonesInfo)objeto;
        
        if (mbInfo.getTitulo().length() > longitudMaxima)
          longitudMaxima = mbInfo.getTitulo().length();
      }
    }
    int ancho = 0;
    if (longitudMaxima < 11) {
      ancho = longitudMaxima * 9;
    } else if (longitudMaxima < 22) {
      ancho = longitudMaxima * 8;
    } else
      ancho = longitudMaxima * 7;
    if (hayBotonConImagen) {
      ancho += 15;
    }
    for (Iterator<Object> iter = menuBotonesTag.getMenuBotonesInfo().getBotonesMenu().iterator(); iter.hasNext();)
    {
      Object objeto = iter.next();
      
      if ((objeto instanceof BotonMenuInfo))
      {
        BotonMenuInfo botonMenuInfo = (BotonMenuInfo)objeto;
        menuBotonesTag.getMenuBotonesInfo().getCodigoHtml().append(botonMenuInfo.getCodigoHtml(ancho));
      }
      else
      {
        MenuBotonesInfo mbInfo = (MenuBotonesInfo)objeto;
        menuBotonesTag.getMenuBotonesInfo().getCodigoHtml().append(mbInfo.getCodigoHtml(ancho));
      }
    }
    

    StringBuffer results = new StringBuffer();
    
    results.append("    </ul>\n");
    results.append("  </li>\n");
    
    menuBotonesTag.getMenuBotonesInfo().getCodigoHtml().append(results.toString());
    menuBotonesTag.setMenuBotonesInfo(menuBotonesTag.getMenuBotonesInfo().getMenuPadre());
    
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
