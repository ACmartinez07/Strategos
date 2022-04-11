package com.visiongc.app.strategos.web.struts.taglib;

import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;

public class ImagenAlertaIndicadorTag extends VgcBaseTag
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

      if (valorAlerta.equals(AlertaIndicador.getAlertaRoja()))
        nombreImagen = "alertaRoja.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaAmarilla()))
        nombreImagen = "alertaAmarilla.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaAzul()))
        nombreImagen = "alertaAzul.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaVerde()))
        nombreImagen = "alertaVerde.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaInalterada()))
        nombreImagen = "alertaInalterada.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaNegra()))
        nombreImagen = "alertaNegra.gif";
      else if (valorAlerta.equals(AlertaIndicador.getAlertaNoDefinible())) {
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
  
  public String getUrlApp()
  {
	  return getUrlAplicacion();
  }
}