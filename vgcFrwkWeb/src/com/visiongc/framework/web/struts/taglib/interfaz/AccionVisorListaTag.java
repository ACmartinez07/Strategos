package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.AccionVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;



public class AccionVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Accion";
  
  public AccionVisorListaTag() {}
  
  private static String ALINEACION_POR_DEFECTO = "center";
  
  protected String permisoId = null;
  
  protected String aplicaOrganizacion = null;
  
  protected String align = null;
  
  protected String onclick = null;
  
  protected String urlImage = null;
  
  protected String titulo = null;
  
  protected FilasVisorListaTag filasVisorLista = null;
  
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
  
  public String getUrlImage() {
    return urlImage;
  }
  
  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public int doStartTag() throws JspException {
    filasVisorLista = ((FilasVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas"));
    
    if (filasVisorLista == null) {
      throw new JspException("El tag AccionVisorLista debe estar dentro de un tag FilasVisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Accion", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    

    boolean aplicaOrganizacion = (this.aplicaOrganizacion != null) && (this.aplicaOrganizacion.equalsIgnoreCase("true"));
    boolean agregarAccion = tienePermiso(permisoId, aplicaOrganizacion);
    
    if (titulo != null)
    {
      titulo = HtmlUtil.trimTextoHtml(titulo);
    } else {
      titulo = "";
    }
    

    if (onclick == null) {
      onclick = HtmlUtil.trimTextoHtml(bodyContent.getString());
    }
    

    String alineacion = ALINEACION_POR_DEFECTO;
    if (align != null) {
      alineacion = align;
    }
    resultado.append("    <td align=\"" + alineacion + "\">");
    if (agregarAccion) {
      resultado.append("<img onclick=\"" + onclick + "\" style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + urlImage + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + titulo + "\">");
    } else {
      resultado.append("&nbsp;");
    }
    resultado.append("</td>\n");
    
    AccionVisorListaInfo accion = new AccionVisorListaInfo();
    
    accion.setCodigo(resultado.toString());
    
    filasVisorLista.getFila().getAcciones().add(accion);
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Accion");
    
    permisoId = null;
    this.aplicaOrganizacion = null;
    onclick = null;
    urlImage = null;
    titulo = null;
    
    return 6;
  }
  


  public void release()
  {
    super.release();
    permisoId = null;
    aplicaOrganizacion = null;
    onclick = null;
    urlImage = null;
    titulo = null;
  }
}
