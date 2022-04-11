package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.FrameworkWebConfiguration;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.TagUtils;









public class BotonBarraAreasTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.BotonBarraAreas";
  public static final int ALTURA_IMAGEN_POR_DEFECTO = 35;
  public static final int ALTURA_TITULO_POR_DEFECTO = 35;
  public static final int ANCHO_IMAGEN_POR_DEFECTO = 35;
  
  public BotonBarraAreasTag() {}
  
  protected String nombre = null;
  
  protected String urlImage = null;
  
  protected String titulo = null;
  
  protected String agregarSeparador = null;
  
  protected String onclick = null;
  
  protected String permisoId = null;
  
  protected String aplicaOrganizacion = null;
  
  protected BarraAreasTag barraAreas = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
  
  public String getAgregarSeparador() {
    return agregarSeparador;
  }
  
  public void setAgregarSeparador(String agregarSeparador) {
    this.agregarSeparador = agregarSeparador;
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
  

  public int doStartTag()
    throws JspException
  {
    barraAreas = ((BarraAreasTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas"));
    
    if (barraAreas == null) {
      throw new JspException("El tag BotonBarraAreas debe estar dentro de un tag BarraAreas");
    }
    
    barraAreas.setNumeroBotones(barraAreas.getNumeroBotones() + 1);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    BarraAreaInfo barraAreasInfo = (BarraAreaInfo)pageContext.getSession().getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas" + barraAreas.getNombre());
    
    boolean aplicaOrganizacion = (this.aplicaOrganizacion != null) && (this.aplicaOrganizacion.equalsIgnoreCase("true"));
    boolean agregarBoton = tienePermiso(permisoId, aplicaOrganizacion);
    String titulo = "";
    if (this.titulo == null) {
      titulo = bodyContent.getString();
    }
    titulo = HtmlUtil.trimTextoHtml(titulo);
    
    if (agregarBoton) {
      boolean mostrarIcono = true;
      String propertyMostrarIcono = FrameworkWebConfiguration.getInstance().getString("jsp.frame.barraareas.botones.iconos.mostrar");
      if ((propertyMostrarIcono != null) && 
        (!propertyMostrarIcono.equals("true"))) {
        mostrarIcono = false;
      }
      
      if (mostrarIcono) {
        resultado.append("\t\t<tr height=\"" + Integer.toString(35) + "\">" + "\n");
        
        String bg = "";
        if (nombre.equals(barraAreasInfo.getBotonSeleccionado())) {
          bg = "background=\"" + getUrlAplicacion() + "/componentes/barraArea/botonSeleccionado.gif" + "\"";
        }
        resultado.append("\t\t\t<td valign=\"middle\" align=\"center\" style=\"cursor: pointer;\"" + bg + "id=\"" + barraAreas.getNombre() + "Boton" + nombre + "\" onclick=\"" + onclick + "\">");
        
        if ((urlImage != null) && (!urlImage.equals(""))) {
          resultado.append("<img src=\"" + getUrlAplicacion() + urlImage + "\" border=\"0\" width=\"" + 35 + "\" height=\"" + 35 + "\" title=\"" + titulo + "\">" + "\n");
        } else {
          resultado.append("&nbsp;\n");
        }
        resultado.append("\t\t\t</td>\n");
        resultado.append("\t\t</tr>\n");
      }
      

      resultado.append("\t\t<tr height=\"35\">\n");
      String estiloSeleccionado = "";
      if ((nombre.equals(barraAreasInfo.getBotonSeleccionado())) && 
        (!mostrarIcono)) {
        estiloSeleccionado = "border-color:black;border-width:1px;border-style:solid";
      }
      
      String valignTexto = "top";
      String alignTexto = "center";
      if (!mostrarIcono) {
        valignTexto = "middle";
        alignTexto = "left";
      }
      resultado.append("\t\t\t<td valign=\"" + valignTexto + "\" align=\"" + alignTexto + "\" style=\"cursor: pointer;" + estiloSeleccionado + "\" onclick=\"" + onclick + "\">");
      resultado.append("<a onMouseOver=\"this.className='mouseEncimaBarraIzquierdaPrincipal'\" onMouseOut=\"this.className='mouseFueraBarraIzquierdarincipal'\" href=\"#\" class=\"mouseFueraBarraIzquierdarincipal\">" + titulo + "</a>" + "\n");
      resultado.append("\t\t\t</td>\n");
      resultado.append("\t\t</tr>\n");
      if ((agregarSeparador != null) && (agregarSeparador.equalsIgnoreCase("true"))) {
        resultado.append("\t\t<tr valign=\"top\" height=\"10px\">\n");
        resultado.append("\t\t\t<td>\n");
        resultado.append("\t\t\t<hr width=\"100%\">\n");
        resultado.append("\t\t\t</td>\n");
        resultado.append("\t\t</tr>\n");
      }
      TagUtils.getInstance().write(pageContext, resultado.toString());
    }
    
    return 6;
  }
  
  public void release() {
    super.release();
  }
}
