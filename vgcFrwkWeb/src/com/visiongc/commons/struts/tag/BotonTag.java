package com.visiongc.commons.struts.tag;

import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import com.visiongc.framework.util.PermisologiaUsuario;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;









public class BotonTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  protected String bundle = null;
  



  protected String key = null;
  



  protected String localeKey = "org.apache.struts.action.LOCALE";
  



  protected String id = null;
  



  protected String width = null;
  



  protected String onclick = null;
  



  protected String permisoId = null;
  



  protected String aplicaOrganizacion = null;
  
  protected String useImage = null;
  
  protected String styleClass = null;
  
  protected String height = null;
  
  protected String disabled = null;
  

  public BotonTag() {}
  
  protected String icon = null;
  
  public String getBundle() {
    return bundle; }
  
  public void setBundle(String bundle)
  {
    this.bundle = bundle;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getLocale() {
    return localeKey;
  }
  
  public void setLocale(String localeKey) {
    this.localeKey = localeKey;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getWidth() {
    return width;
  }
  
  public void setWidth(String width) {
    this.width = width;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public String getAplicaOrganizacion() {
    return aplicaOrganizacion;
  }
  
  public void setAplicaOrganizacion(String aplicaOrganizacion) {
    this.aplicaOrganizacion = aplicaOrganizacion;
  }
  
  public String getPermisoId() {
    return permisoId;
  }
  
  public void setPermisoId(String permisoId) {
    this.permisoId = permisoId;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public void setIcon(String icon) {
    this.icon = icon;
  }
  
  public String getUseImage() {
    return useImage;
  }
  
  public void setUseImage(String useImage) {
    this.useImage = useImage;
  }
  
  public String getHeight() {
    return height;
  }
  
  public void setHeight(String height) {
    this.height = height;
  }
  
  public String getStyleClass() {
    return styleClass;
  }
  
  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }
  
  public String getDisabled() {
    return disabled;
  }
  
  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
  







  public int doStartTag()
    throws JspException
  {
    StringBuffer buf = new StringBuffer();
    boolean dibujarBoton = true;
    
    if ((permisoId != null) && (!permisoId.equals(""))) {
      PermisologiaUsuario pu = (PermisologiaUsuario)pageContext.getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
      
      if (pu == null) {
        JspException e = new JspException("La permisología de Usuario no ha sido cargada");
        
        TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
      if ((aplicaOrganizacion != null) && (aplicaOrganizacion.equals("true"))) {
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
    
    if (dibujarBoton) {
      dibujarBoton(buf);
    }
    
    TagUtils.getInstance().write(pageContext, buf.toString());
    
    return 0;
  }
  






  private void dibujarBoton(StringBuffer buf)
    throws JspException
  {
    String longitud = "";
    
    boolean hayIcono = icon != null;
    boolean hayTexto = key != null;
    boolean usarImagenes = false;
    String alto = " height=\"20\"";
    String clase = "boton";
    boolean enabled = true;
    
    if ((width != null) && (width.length() > 0)) {
      longitud = "width=\"" + width + "\"";
    }
    
    if ((height != null) && (height.length() > 0)) {
      alto = " height=\"" + height + "\"";
    }
    
    if ((styleClass != null) && (styleClass.length() > 0)) {
      clase = styleClass;
    }
    
    if (useImage != null) {
      usarImagenes = useImage.equals("true");
    }
    
    if (disabled != null) {
      if (!disabled.equalsIgnoreCase("true")) {
        disabled = "false";
      } else {
        disabled = "true";
      }
      enabled = !Boolean.valueOf(disabled).booleanValue();
    }
    
    buf.append("\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"" + clase);
    
    if (enabled) {
      buf.append("\" id=\"" + id + "\" onmouseover=\"changePropertyObjetoHtml('" + id + "', 'className', '" + clase + "Over')\" onmouseout=\"changePropertyObjetoHtml('" + id + "', 'className', '" + clase + "')\" onclick=\"changePropertyObjetoHtml('" + id + "', 'className', '" + clase + "Click');" + onclick + "\">" + "\n");
    }
    else {
      buf.append("Disabled\" >\n");
    }
    
    if (usarImagenes) {
      buf.append("  <tr height=\"11\" >\n");
      
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonSupIzq.gif\" width=\"12\" ></td>" + "\n");
      if (hayIcono) {
        buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonSupMed.gif\" ></td>" + "\n");
      }
      if (hayTexto) {
        buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonSupMed.gif\" ></td>" + "\n");
      }
      
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonSupDer.gif\" width=\"11\" ></td>" + "\n");
      buf.append("  </tr>\n");
    }
    buf.append("  <tr>\n");
    
    if (usarImagenes) {
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonMedIzq.gif\" width=\"12\" ></td>" + "\n");
    }
    
    if (hayIcono) {
      buf.append("    <td");
      
      if (usarImagenes) {
        buf.append(" background=\"" + getUrlAplicacion() + "/interfaz/boton/botonBg.gif\"");
      }
      
      buf.append(" align=\"center\" valign=\"middle\" ><img src=\"" + getUrlAplicacion() + icon + "\" />" + "</td>" + "\n");
    }
    if (hayTexto) {
      String texto = getMessageResource(bundle, localeKey, key);
      
      buf.append("    <td");
      
      if (usarImagenes) {
        buf.append(" background=\"" + getUrlAplicacion() + "/interfaz/boton/botonBg.gif\"");
      }
      buf.append(" " + longitud + alto + " align=\"center\"  valign=\"middle\" >&nbsp;" + texto + "&nbsp;</td>" + "\n");
    }
    
    if (usarImagenes) {
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonMedDer.gif\" ></td>" + "\n");
    }
    
    buf.append("  </tr>\n");
    if (usarImagenes) {
      buf.append("  <tr height=\"11\" >\n");
      
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonInfIzq.gif\" width=\"12\"></td>" + "\n");
      if (hayIcono) {
        buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonInfMed.gif\" ></td>" + "\n");
      }
      if (hayTexto) {
        buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonInfMed.gif\" ></td>" + "\n");
      }
      buf.append("    <td background=\"" + getUrlAplicacion() + "/interfaz/boton/botonInfDer.gif\" ></td>" + "\n");
      buf.append("  </tr>\n");
    }
    
    buf.append("</table>\n");
  }
  



  public void release()
  {
    super.release();
    bundle = "org.apache.struts.action.MESSAGE";
    key = null;
    localeKey = "org.apache.struts.action.LOCALE";
  }
}
