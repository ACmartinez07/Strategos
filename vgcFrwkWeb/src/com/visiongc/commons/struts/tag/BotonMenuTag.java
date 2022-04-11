package com.visiongc.commons.struts.tag;

import com.visiongc.commons.struts.tag.util.BotonMenuInfo;
import com.visiongc.commons.struts.tag.util.MenuBotonesInfo;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import com.visiongc.framework.util.PermisologiaUsuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;










public class BotonMenuTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  protected String bundle = null;
  



  protected String localeKey = "org.apache.struts.action.LOCALE";
  



  protected String key = null;
  



  protected String onclick = null;
  



  protected String permisoId = null;
  



  protected String aplicaOrganizacion = null;
  



  protected String icon = null;
  protected String disabled = null;
  protected String agregarSeparador = null;
  protected String arg0 = null;
  protected String arg1 = null;
  protected String arg2 = null;
  protected String arg3 = null;
  protected String arg4 = null;
  
  private MenuBotonesTag menu = null;
  
  public BotonMenuTag() {}
  
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
  
  public String getId()
  {
    return id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getOnclick()
  {
    return onclick;
  }
  
  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }
  
  public String getAplicaOrganizacion()
  {
    return aplicaOrganizacion;
  }
  
  public void setAplicaOrganizacion(String aplicaOrganizacion)
  {
    this.aplicaOrganizacion = aplicaOrganizacion;
  }
  
  public String getPermisoId()
  {
    return permisoId;
  }
  
  public void setPermisoId(String permisoId)
  {
    this.permisoId = permisoId;
  }
  
  public String getIcon()
  {
    return icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public String getDisabled()
  {
    return disabled;
  }
  
  public void setDisabled(String disabled)
  {
    this.disabled = disabled;
  }
  
  public String getAgregarSeparador()
  {
    return agregarSeparador;
  }
  
  public void setAgregarSeparador(String agregarSeparador)
  {
    this.agregarSeparador = agregarSeparador;
  }
  
  public String getArg0()
  {
    return arg0;
  }
  
  public void setArg0(String arg0)
  {
    this.arg0 = arg0;
  }
  
  public String getArg1()
  {
    return arg1;
  }
  
  public void setArg1(String arg1)
  {
    this.arg1 = arg1;
  }
  
  public String getArg2()
  {
    return arg2;
  }
  
  public void setArg2(String arg2)
  {
    this.arg2 = arg2;
  }
  
  public String getArg3()
  {
    return arg3;
  }
  
  public void setArg3(String arg3)
  {
    this.arg3 = arg3;
  }
  
  public String getArg4()
  {
    return arg4;
  }
  
  public void setArg4(String arg4)
  {
    this.arg4 = arg4;
  }
  







  public int doStartTag()
    throws JspException
  {
    menu = ((MenuBotonesTag)pageContext.getAttribute("com.visiongc.commons.struts.tag.MenuBotones"));
    if (menu == null) {
      throw new JspException("El tag BotonMenu debe estar dentro de un tag MenuBotones");
    }
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    boolean dibujarBoton = true;
    
    if ((permisoId != null) && (!permisoId.equals("")))
    {
      PermisologiaUsuario pu = (PermisologiaUsuario)pageContext.getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
      
      if (pu == null)
      {
        JspException e = new JspException("La permisología de Usuario no ha sido cargada");
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      if ((aplicaOrganizacion != null) && (aplicaOrganizacion.equals("true")))
      {
        OrganizacionPermisologia o = (OrganizacionPermisologia)pageContext.getSession().getAttribute("organizacion");
        
        if (o.getOrganizacionId() != null) {
          dibujarBoton = pu.tienePermiso(permisoId, o.getOrganizacionId().longValue());
        } else {
          dibujarBoton = false;
        }
      } else {
        dibujarBoton = pu.tienePermiso(permisoId);
      }
    }
    if (dibujarBoton)
    {
      String textoMenu = "";
      
      if ((bodyContent != null) && (!bodyContent.equals(""))) {
        textoMenu = bodyContent.getString();
      }
      dibujarBoton(textoMenu);
    }
    
    return 6;
  }
  








  private void dibujarBoton(String textoMenu)
    throws JspException
  {
    StringBuffer buf = new StringBuffer();
    BotonMenuInfo botonMenuInfo = new BotonMenuInfo();
    boolean hayIcono = icon != null;
    boolean hayTexto = key != null;
    boolean enabled = true;
    
    if (disabled != null)
    {
      if (!disabled.equalsIgnoreCase("true")) {
        disabled = "false";
      } else
        disabled = "true";
      enabled = !Boolean.valueOf(disabled).booleanValue();
    }
    
    buf.append("      <li style=\"padding-top:4px; padding-bottom:4px; padding-left:0px; padding-right:0px#codigoAncho#\">");
    buf.append("<a href=\"");
    if (enabled)
    {
      if (onclick.indexOf("javascript:") < 0)
        buf.append("javascript:");
      buf.append(onclick);
    }
    else {
      buf.append("#"); }
    buf.append("\" >");
    
    if (hayIcono)
    {
      buf.append("<img border=\"0\" src=\"" + getUrlAplicacion() + icon + "\" />&nbsp;&nbsp;");
      botonMenuInfo.setTieneImagen(true);
    }
    if (hayTexto)
    {
      Object[] args = { arg0, arg1, arg2, arg3, arg4 };
      
      String texto = getMessageResource(bundle, localeKey, key, Boolean.valueOf(true), args);
      buf.append("&nbsp;&nbsp;" + texto);
      botonMenuInfo.setTitulo(texto);
    }
    else
    {
      buf.append("&nbsp;&nbsp;" + textoMenu);
      botonMenuInfo.setTitulo(textoMenu);
    }
    buf.append("</a></li>\n");
    
    if ((agregarSeparador != null) && (agregarSeparador.equals("true")))
    {
      buf.append("      <li style=\"height:16px; padding-top:0px; padding-bottom:0px; padding-left:0px; padding-right:0px#codigoAnchoSeparador#\">");
      buf.append("<img src=\"" + getUrlAplicacion() + "/componentes/menu/separador.gif\" border=\"0\" style=\"height:3px" + "#codigoAnchoSeparador#" + "\"  />");
      buf.append("</li>\n");
    }
    
    botonMenuInfo.setCodigoHtml(buf.toString());
    menu.getMenuBotonesInfo().getBotonesMenu().add(botonMenuInfo);
  }
  



  public void release()
  {
    super.release();
    bundle = "org.apache.struts.action.MESSAGE";
    key = null;
    localeKey = "org.apache.struts.action.LOCALE";
  }
}
