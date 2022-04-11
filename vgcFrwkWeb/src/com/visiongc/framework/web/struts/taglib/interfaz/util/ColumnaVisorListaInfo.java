package com.visiongc.framework.web.struts.taglib.interfaz.util;

import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.web.struts.taglib.interfaz.VisorListaTag;
import com.visiongc.framework.web.util.HtmlUtil;






public class ColumnaVisorListaInfo
{
  public ColumnaVisorListaInfo() {}
  
  protected String nombre = null;
  
  protected String ancho = null;
  
  protected String alineacion = null;
  
  protected String titulo = null;
  
  protected String textoOrdenAscDesc = null;
  
  protected String onclick = null;
  
  protected VisorListaTag visorLista = null;
  
  protected String urlAplicacion = null;
  

  protected String visible = null;
  
  protected String orden = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAncho() {
    return ancho;
  }
  
  public void setAncho(String ancho) {
    this.ancho = ancho;
  }
  
  public String getAlineacion() {
    return alineacion;
  }
  
  public void setAlineacion(String alineacion) {
    this.alineacion = alineacion;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getTextoOrdenAscDesc() {
    return textoOrdenAscDesc;
  }
  
  public void setTextoOrdenAscDesc(String textoOrdenAscDesc) {
    this.textoOrdenAscDesc = textoOrdenAscDesc;
  }
  
  public String getOnclick() {
    return onclick;
  }
  
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public String getUrlAplicacion() {
    return urlAplicacion;
  }
  
  public void setUrlAplicacion(String urlAplicacion) {
    this.urlAplicacion = urlAplicacion;
  }
  
  public String getVisible() {
    return visible;
  }
  
  public void setVisible(String visible) {
    this.visible = visible;
  }
  
  public String getOrden() {
    return orden;
  }
  
  public void setOrden(String orden) {
    this.orden = orden;
  }
  
  public VisorListaTag getVisorLista() {
    return visorLista;
  }
  
  public void setVisorLista(VisorListaTag visorLista) {
    this.visorLista = visorLista;
  }
  






  private String construirCodigo()
  {
    StringBuffer resultado = new StringBuffer();
    
    boolean tieneOnclick = (onclick != null) && (!onclick.equals(""));
    

    resultado.append("    <td align=\"" + alineacion + "\" ");
    if (tieneOnclick) {
      resultado.append("style=\"cursor: pointer\" ");
      resultado.append("onMouseOver=\"this.className='mouseEncimaEncabezadoListView'\" ");
      resultado.append("onMouseOut=\"this.className='mouseFueraEncabezadoListView'\" ");
    }
    resultado.append("class=\"mouseFueraEncabezadoListView\" ");
    if ((ancho != null) && (!ancho.equals(""))) {
      resultado.append(" width=\"");
      resultado.append(ancho + "\" ");
    }
    
    if (tieneOnclick) {
      resultado.append("onclick=\"");
      resultado.append(onclick + "\" ");
    }
    resultado.append(">");
    
    resultado.append(HtmlUtil.trimTextoHtml(titulo));
    if (tieneOnclick) {
      String imgOrden = "transparente.gif";
      if ((visorLista.getPaginaLista().getOrden() != null) && 
        (visorLista.getPaginaLista().getOrden().equalsIgnoreCase(nombre))) {
        visorLista.getPaginaLista().setInfoOrden(HtmlUtil.trimTextoHtml(titulo) + " [" + visorLista.getPaginaLista().getTipoOrden() + "]");
        if (visorLista.getPaginaLista().getTipoOrden().equalsIgnoreCase("desc")) {
          imgOrden = "arriba.gif";
        } else {
          imgOrden = "abajo.gif";
        }
      }
      
      resultado.append("<img src=\"" + urlAplicacion + "/componentes/visorLista/" + imgOrden + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + textoOrdenAscDesc + "\">");
    }
    resultado.append("</td>\n");
    
    return resultado.toString();
  }
  
  public String getCodigo()
  {
    return construirCodigo();
  }
}
