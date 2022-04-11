package com.visiongc.app.strategos.web.struts.taglib;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaProducto;
import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;

public class ImagenAlertaProductoTag extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  protected String name = null;

  protected String property = null;

  protected String scope = null;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScope() {
    return this.scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getProperty() {
    return this.property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public int doStartTag() throws JspException
  {
    String nombreImagen = "alertaBlanca.gif";

    Object value = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);

    if (value != null) {
      Byte valorAlerta = Byte.valueOf(value.toString());

      if (valorAlerta.equals(AlertaProducto.getAlertaNoEntregado()))
        nombreImagen = "alertaRoja.gif";
      else if (valorAlerta.equals(AlertaProducto.getAlertaEntregado()))
        nombreImagen = "alertaVerde.gif";
      else if (valorAlerta.equals(AlertaProducto.getAlertaEnEsperaComienzo()))
        nombreImagen = "alertaBlanca.gif";
      else if (valorAlerta.equals(AlertaProducto.getAlertaNoDefinible())) {
        nombreImagen = "alertaBlanca.gif";
      }
    }
    String resultado = "<img style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + "/paginas/strategos/indicadores/imagenes/" + nombreImagen + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(null, null, "boton.alerta.alt") + "\">";

    TagUtils.getInstance().write(this.pageContext, resultado);

    return 0;
  }

  public void release() {
    super.release();
    this.name = null;
    this.property = null;
    this.scope = null;
  }
}