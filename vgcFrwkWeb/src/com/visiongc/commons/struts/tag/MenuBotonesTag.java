package com.visiongc.commons.struts.tag;

import com.visiongc.commons.struts.tag.util.BotonMenuInfo;
import com.visiongc.commons.struts.tag.util.MenuBotonesInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;




public class MenuBotonesTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.commons.struts.tag.MenuBotones";
  public static final String CODIGO_REEMPLAZO_ANCHO = "#codigoAncho#";
  public static final String CODIGO_REEMPLAZO_ANCHO_SEPARADOR = "#codigoAnchoSeparador#";
  public static final String CODIGO_REEMPLAZO_DESPLAZAMIENTO_SUBMENU = "#offsetSubMenu#";
  protected String bundle = null;
  protected String key = null;
  protected String localeKey = "org.apache.struts.action.LOCALE";
  
  private boolean agregarDependenciasJs = false;
  private MenuBotonesInfo menuBotonesInfo = null;
  
  public MenuBotonesTag() {}
  
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
  
  public MenuBotonesInfo getMenuBotonesInfo()
  {
    return menuBotonesInfo;
  }
  
  public void setMenuBotonesInfo(MenuBotonesInfo menuBotonesInfo)
  {
    this.menuBotonesInfo = menuBotonesInfo;
  }
  



  public int doStartTag()
    throws JspException
  {
    if (pageContext.getRequest().getAttribute("com.visiongc.commons.struts.tag.MenuBotones") == null) {
      agregarDependenciasJs = true;
    } else {
      agregarDependenciasJs = false;
    }
    
    pageContext.getRequest().setAttribute("com.visiongc.commons.struts.tag.MenuBotones", this);
    pageContext.setAttribute("com.visiongc.commons.struts.tag.MenuBotones", this);
    
    menuBotonesInfo = new MenuBotonesInfo();
    menuBotonesInfo.setBotonesMenu(new ArrayList());
    menuBotonesInfo.setCodigoHtml(new StringBuffer());
    menuBotonesInfo.getCodigoHtml().append(agregarInicioMenuBotones());
    
    return 2;
  }
  





  private String agregarInicioMenuBotones()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (agregarDependenciasJs)
    {
      resultado.append("\n<script type=\"text/javascript\" src=\"" + getUrlAplicacion() + "/componentes/menu/fsmenu.js\"></script>" + "\n");
      resultado.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getUrlAplicacion() + "/componentes/menu/listmenu.css\" />" + "\n");
      resultado.append("<link rel=\"stylesheet\" type=\"text/css\" id=\"fsmenu-fallback\" href=\"" + getUrlAplicacion() + "/componentes/menu/listmenu_fallback.css\" />" + "\n");
      resultado.append("<script type=\"text/javascript\">\n");
      resultado.append("var appGlobalListMenuIds = '';\n");
      resultado.append("</script>\n");
    }
    
    String titulo = getMessageResource(bundle, localeKey, key);
    menuBotonesInfo.setTitulo(titulo);
    String ancho = null;
    if (titulo.length() < 4) {
      ancho = "width:50px";
    } else if (titulo.length() < 7) {
      ancho = "width:70px";
    } else
      ancho = "width:" + Integer.toString((titulo.length() + 2) * 8) + "px";
    resultado.append("<ul class=\"menulist\" id=\"" + id + "\" style=\"" + ancho + "\">" + "\n");
    resultado.append("  <li style=\"" + ancho + "\"><a href=\"#\">");
    resultado.append("&nbsp;&nbsp;" + titulo + "</a>" + "\n");
    resultado.append("    <ul class=\"menulist\">\n");
    
    return resultado.toString();
  }
  







  public int doEndTag()
    throws JspException
  {
    int longitudMaxima = 0;
    boolean hayBotonConImagen = false;
    for (Iterator<Object> iter = menuBotonesInfo.getBotonesMenu().iterator(); iter.hasNext();)
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
    if (longitudMaxima < 7) {
      ancho = longitudMaxima * 12;
    } else if (longitudMaxima < 11) {
      ancho = longitudMaxima * 9;
    } else if (longitudMaxima < 22) {
      ancho = longitudMaxima * 9;
    } else {
      ancho = longitudMaxima * 7;
    }
    if (hayBotonConImagen) {
      ancho += 15;
    }
    for (Iterator<?> iter = menuBotonesInfo.getBotonesMenu().iterator(); iter.hasNext();)
    {
      Object objeto = iter.next();
      
      if ((objeto instanceof BotonMenuInfo))
      {
        BotonMenuInfo botonMenuInfo = (BotonMenuInfo)objeto;
        menuBotonesInfo.getCodigoHtml().append(botonMenuInfo.getCodigoHtml(ancho));
      }
      else
      {
        MenuBotonesInfo mbInfo = (MenuBotonesInfo)objeto;
        menuBotonesInfo.getCodigoHtml().append(mbInfo.getCodigoHtml(ancho));
      }
    }
    

    StringBuffer results = new StringBuffer();
    
    results.append("    </ul>\n");
    results.append("  </li>\n");
    results.append("</ul>\n");
    
    results.append("<script type=\"text/javascript\">\n");
    results.append("\tvar " + id + " = new FSMenu('" + id + "', true, 'display', 'block', 'none');" + "\n");
    results.append("\tappGlobalListMenuIds = appGlobalListMenuIds + '" + id + "' + ',';" + "\n");
    results.append("\t" + id + ".animations[" + id + ".animations.length] = FSMenu.animFade;" + "\n");
    results.append("\t" + id + ".animations[" + id + ".animations.length] = FSMenu.animSwipeDown;" + "\n");
    results.append("\t" + id + ".animations[" + id + ".animations.length] = FSMenu.animClipDown;" + "\n");
    results.append("\tvar arrow = null;\n");
    results.append("\tif (document.createElement && document.documentElement)\n");
    results.append("\t{\n");
    results.append("  \t\tarrow = document.createElement('span');\n");
    results.append("  \t\tvar imagenOpenSubMenu = document.createElement('img');\n");
    results.append("  \t\timagenOpenSubMenu.src = '" + getUrlAplicacion() + "/componentes/menu/submenu.gif';" + "\n");
    results.append("  \t\timagenOpenSubMenu.style.borderWidth = '0px';\n");
    results.append("  \t\timagenOpenSubMenu.className = 'subind';\n");
    results.append("  \t\tarrow.appendChild(imagenOpenSubMenu);\n");
    results.append("\t}\n");
    results.append("\taddReadyEvent(new Function('" + id + ".activateMenu(\"" + id + "\", arrow)'));\n");
    results.append("</script>\n");
    
    menuBotonesInfo.getCodigoHtml().append(results.toString());
    TagUtils.getInstance().write(pageContext, menuBotonesInfo.getCodigoHtml().toString());
    menuBotonesInfo = null;
    
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
