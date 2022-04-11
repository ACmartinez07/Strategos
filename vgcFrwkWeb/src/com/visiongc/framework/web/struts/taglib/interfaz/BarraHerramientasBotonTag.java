package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import com.visiongc.framework.util.PermisologiaUsuario;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;






public class BarraHerramientasBotonTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBoton";
  
  public BarraHerramientasBotonTag() {}
  
  protected String nombre = null;
  protected String pathImagenes = null;
  protected String nombreImagen = null;
  protected String onclick = null;
  protected String titulo = null;
  protected BarraHerramientasTag barraHerramientas = null;
  protected String permisoId = null;
  protected String aplicaOrganizacion = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getPathImagenes() {
    return pathImagenes;
  }
  
  public void setPathImagenes(String pathImagenes) {
    this.pathImagenes = pathImagenes;
  }
  
  public String getNombreImagen() {
    return nombreImagen;
  }
  
  public void setNombreImagen(String nombreImagen) {
    this.nombreImagen = nombreImagen;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getPermisoId()
  {
    return permisoId;
  }
  
  public void setPermisoId(String permisoId)
  {
    this.permisoId = permisoId;
  }
  
  public String getAplicaOrganizacion()
  {
    return aplicaOrganizacion;
  }
  
  public void setAplicaOrganizacion(String aplicaOrganizacion)
  {
    this.aplicaOrganizacion = aplicaOrganizacion;
  }
  
  public int doStartTag() throws JspException
  {
    barraHerramientas = ((BarraHerramientasTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraHerramientas"));
    
    if (barraHerramientas == null) {
      throw new JspException("El tag BarraHerramientasBoton debe estar dentro de un tag BarraHerramientas");
    }
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBoton", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    boolean hayDibujarBoton = true;
    
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
          hayDibujarBoton = pu.tienePermiso(permisoId, o.getOrganizacionId().longValue());
        } else {
          hayDibujarBoton = false;
        }
      } else {
        hayDibujarBoton = pu.tienePermiso(permisoId);
      }
    }
    if (hayDibujarBoton) {
      dibujarBoton();
    }
    return 6;
  }
  
  private void dibujarBoton() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (onclick == null)
      onclick = "";
    if (titulo == null) {
      titulo = "";
    }
    String nombreBoton = "barraHerramientas" + barraHerramientas.getNombre() + "ImagenBoton" + nombre;
    resultado.append("<td width=\"30px\" id=\"" + barraHerramientas.getNombre() + "Boton" + nombre + "\">");
    resultado.append("<img src=\"" + getUrlAplicacion() + pathImagenes + nombreImagen + "_1.gif\" name=\"" + nombreBoton + "\" id=\"" + nombreBoton + "\" border=\"0\" width=\"25px\" height=\"25px\" onclick=\"" + onclick + "\" title=\"" + titulo + "\" onmouseout=\"barraHerramientas_swapImgRestore()\" onmouseover=\"barraHerramientas_swapImage('barraHerramientas" + barraHerramientas.getNombre() + "ImagenBoton" + nombre + "', '', '" + getUrlAplicacion() + pathImagenes + nombreImagen + "_2.gif', 1)\"></td>" + "\n");
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    clear();
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BarraHerramientasBoton");
  }
  
  private void clear()
  {
    nombre = null;
    nombreImagen = null;
    onclick = null;
    pathImagenes = null;
    titulo = null;
  }
  



  public void release()
  {
    super.release();
  }
}
