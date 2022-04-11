package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.commons.web.html.util.HtmlUtils;
import com.visiongc.framework.web.struts.actions.WelcomeAction;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ContenedorFormaTituloInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Stack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.struts.taglib.TagUtils;
import org.xml.sax.SAXException;











public class ContenedorFormaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  static final String WIDTH_POR_DEFECTO = "100%";
  static final String HEIGHT_POR_DEFECTO = "100%";
  static final String BODY_WIDTH_POR_DEFECTO = "100%";
  static final String BODY_HEIGHT_POR_DEFECTO = "100%";
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma";
  protected String bundle = null;
  protected String width = null;
  protected String height = null;
  protected String idHtml = null;
  protected String idContenedor = null;
  protected String bodyWidth = null;
  protected String bodyHeight = null;
  protected String bodyAlign = null;
  protected String bodyValign = null;
  protected String bodyCellpadding = null;
  protected String mostrarBarraSuperior = null;
  protected String esSelector = null;
  protected String comandoAceptarSelector = null;
  protected String comandoCancelarSelector = null;
  protected String marginTop = null;
  protected String marginBottom = null;
  protected String marginLeft = null;
  protected String marginRight = null;
  protected String scrolling = null;
  protected ContenedorFormaTituloInfo contenedorFormaTituloInfo = null;
  protected NavegadorForm estilos = null;
  




  protected String localeKey = "org.apache.struts.action.LOCALE";
  
  public ContenedorFormaTag() {}
  
  public String getBundle() { return bundle; }
  

  public void setBundle(String bundle)
  {
    this.bundle = bundle;
  }
  
  public String getHeight()
  {
    return height;
  }
  
  public void setHeight(String height)
  {
    this.height = height;
  }
  
  public String getWidth()
  {
    return width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
  
  public String getIdHtml()
  {
    return idHtml;
  }
  
  public void setIdHtml(String idHtml)
  {
    this.idHtml = idHtml;
  }
  
  public String getIdContenedor()
  {
    return idContenedor;
  }
  
  public void setIdContenedor(String idContenedor)
  {
    this.idContenedor = idContenedor;
  }
  
  public String getBodyWidth()
  {
    return bodyWidth;
  }
  
  public void setBodyWidth(String bodyWidth)
  {
    this.bodyWidth = bodyWidth;
  }
  
  public String getBodyHeight()
  {
    return bodyHeight;
  }
  
  public void setBodyHeight(String bodyHeight)
  {
    this.bodyHeight = bodyHeight;
  }
  
  public String getBodyAlign()
  {
    return bodyAlign;
  }
  
  public void setBodyAlign(String bodyAlign)
  {
    this.bodyAlign = bodyAlign;
  }
  
  public String getBodyValign()
  {
    return bodyValign;
  }
  
  public void setBodyValign(String bodyValign)
  {
    this.bodyValign = bodyValign;
  }
  
  public String getBodyCellpadding()
  {
    return bodyCellpadding;
  }
  
  public void setBodyCellpadding(String bodyCellpadding)
  {
    this.bodyCellpadding = bodyCellpadding;
  }
  
  public String getMostrarBarraSuperior()
  {
    return mostrarBarraSuperior;
  }
  
  public void setMostrarBarraSuperior(String mostrarBarraSuperior)
  {
    this.mostrarBarraSuperior = mostrarBarraSuperior;
  }
  
  public String getComandoAceptarSelector()
  {
    return comandoAceptarSelector;
  }
  
  public void setComandoAceptarSelector(String comandoAceptarSelector)
  {
    this.comandoAceptarSelector = comandoAceptarSelector;
  }
  
  public String getComandoCancelarSelector()
  {
    return comandoCancelarSelector;
  }
  
  public void setComandoCancelarSelector(String comandoCancelarSelector)
  {
    this.comandoCancelarSelector = comandoCancelarSelector;
  }
  
  public String getEsSelector()
  {
    return esSelector;
  }
  
  public void setEsSelector(String esSelector)
  {
    this.esSelector = esSelector;
  }
  
  public String getLocale()
  {
    return localeKey;
  }
  
  public void setLocale(String locale)
  {
    localeKey = locale;
  }
  
  public ContenedorFormaTituloInfo getContenedorFormaTituloInfo()
  {
    return contenedorFormaTituloInfo;
  }
  
  public void setContenedorFormaTituloInfo(ContenedorFormaTituloInfo contenedorFormaTituloInfo)
  {
    this.contenedorFormaTituloInfo = contenedorFormaTituloInfo;
  }
  
  public String getMarginTop()
  {
    return marginTop;
  }
  
  public void setMarginTop(String marginTop)
  {
    this.marginTop = marginTop;
  }
  
  public String getMarginBottom()
  {
    return marginBottom;
  }
  
  public void setMarginBottom(String marginBottom)
  {
    this.marginBottom = marginBottom;
  }
  
  public String getMarginLeft()
  {
    return marginLeft;
  }
  
  public void setMarginLeft(String marginLeft)
  {
    this.marginLeft = marginLeft;
  }
  
  public String getMarginRight()
  {
    return marginRight;
  }
  
  public void setMarginRight(String marginRight)
  {
    this.marginRight = marginRight;
  }
  
  public String getScrolling()
  {
    return scrolling;
  }
  
  public void setScrolling(String scrolling)
  {
    this.scrolling = scrolling;
  }
  
  public int doStartTag() throws JspException
  {
    TagUtils.getInstance().write(pageContext, renderStartHtmlContenedorForma());
    
    Stack anidados = null;
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma") != null)
    {
      anidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.anidados");
      anidados.push(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma"));
    }
    else
    {
      anidados = new Stack();
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.anidados", anidados);
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma", this);
    
    return 2;
  }
  






  private String renderStartHtmlContenedorForma()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    String style = "";
    
    resultado.append("\n<table class=\"contenedorForma\"");
    if ((idHtml != null) && (!idHtml.equals("")))
      resultado.append(" id=\"" + idHtml + "\"");
    style = "border-spacing: 2px; padding: 2px;";
    


    if ((marginTop != null) && (!marginTop.equals(""))) {
      style = style + " margin-top: " + marginTop + ";";
    }
    if ((marginRight != null) && (!marginRight.equals(""))) {
      style = style + " margin-right: " + marginRight + ";";
    }
    if ((marginBottom != null) && (!marginBottom.equals(""))) {
      style = style + " margin-bottom: " + marginBottom + ";";
    }
    if ((marginLeft != null) && (!marginLeft.equals(""))) {
      style = style + " margin-left: " + marginLeft + ";";
    }
    if ((scrolling != null) && (!scrolling.equals("")))
      style = style + " overflow: " + scrolling + ";";
    style = style + " width: ";
    if ((width != null) && (width.length() > 0)) {
      style = style + width + ";";
    } else
      style = style + "100%" + ";";
    style = style + " height: ";
    if ((height != null) && (height.length() > 0)) {
      style = style + height + ";";
    } else
      style = style + "100%" + ";";
    resultado.append(" style=\"" + style + "\" >" + "\n");
    
    return resultado.toString();
  }
  



  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    agregarBarraSuperior(resultado);
    agregarBarraMenu(resultado);
    agregarBarraGenerica(resultado);
    

    resultado.append("      <tr>\n");
    resultado.append("        <td colspan=\"2\">\n");
    resultado.append("          <div ");
    if (idContenedor != null)
      resultado.append(" id=\"" + idContenedor + "\"");
    resultado.append(" class=\"contenedorForma\" style=\"overflow:");
    if ((scrolling != null) && (!scrolling.equals(""))) {
      resultado.append(" " + scrolling + "; ");
    } else {
      resultado.append(" auto; ");
    }
    String bodyHeight = "100%";
    if ((this.bodyHeight != null) && (!this.bodyHeight.equals(""))) {
      bodyHeight = this.bodyHeight;
    }
    String bodyWidth = "100%";
    if ((this.bodyWidth != null) && (!this.bodyWidth.equals(""))) {
      bodyWidth = this.bodyWidth;
    } else if ((width != null) && (!width.equals("")))
    {
      if (!HtmlUtils.esTamanoPorcentual(width))
        bodyWidth = Integer.toString(HtmlUtils.getTamanoNumerico(width) - 5);
    }
    resultado.append(" height:" + bodyHeight + ";");
    resultado.append(" width:" + bodyWidth);
    resultado.append("\">\n");
    resultado.append("\n<div style=\"position:relative\">\n");
    resultado.append("<div style=\"position:absolute\">\n");
    String bodyAlign = "left";
    if ((this.bodyAlign != null) && (!this.bodyAlign.equals("")))
      bodyAlign = this.bodyAlign;
    String bodyValign = "top";
    if ((this.bodyValign != null) && (!this.bodyValign.equals("")))
      bodyValign = this.bodyValign;
    if ((this.bodyWidth != null) && (!this.bodyWidth.equals(""))) {
      bodyWidth = this.bodyWidth;
    } else if ((width != null) && (!width.equals("")))
    {
      if (!HtmlUtils.esTamanoPorcentual(width))
        bodyWidth = Integer.toString(HtmlUtils.getTamanoNumerico(width) - 10);
    }
    String bodyCellpadding = "3";
    if ((this.bodyCellpadding != null) && (!this.bodyCellpadding.equals("")))
      bodyCellpadding = this.bodyCellpadding;
    resultado.append("<table cellpadding=\"" + bodyCellpadding + "\" cellspacing=\"0\" width=\"" + bodyWidth + "\" height=\"" + bodyHeight + "\">" + "\n");
    resultado.append("<tr valign=\"" + bodyValign + "\">" + "\n");
    resultado.append("<td align=\"" + bodyAlign + "\" valign=\"" + bodyValign + "\">" + "\n");
    
    if (bodyContent != null)
      resultado.append(bodyContent.getString());
    resultado.append("\n</td>\n");
    resultado.append("</tr>\n");
    resultado.append("</table>\n");
    resultado.append("</div>\n");
    resultado.append("</div>\n");
    resultado.append("          </div>\n");
    resultado.append("        </td>\n");
    resultado.append("      </tr>\n");
    
    agregarBarraInferior(resultado);
    
    resultado.append("    </table>\n");
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    Stack anidados = (Stack)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.anidados");
    if (anidados != null)
    {
      if (anidados.empty())
      {
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.anidados");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo");
        pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorPaneles.PanelContenedorTitulo.anidados");
      }
      else {
        pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma", anidados.pop());
      }
    } else {
      pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma");
    }
    return 6;
  }
  






  private void agregarBarraSuperior(StringBuffer resultado)
    throws JspException
  {
    if ((mostrarBarraSuperior == null) || (!mostrarBarraSuperior.equalsIgnoreCase("false")))
    {

      resultado.append("  <tr>\n");
      
      ContenedorFormaTituloInfo tituloInfo = contenedorFormaTituloInfo;
      String titulo = tituloInfo.getTitulo();
      if ((titulo == null) || (titulo.equals("")))
        titulo = "&nbsp;";
      String idTitulo = tituloInfo.getNombre();
      if ((idTitulo != null) && (!idTitulo.equals(""))) {
        idTitulo = "id=\"" + idTitulo + "\"";
      } else
        idTitulo = "";
      resultado.append("    <td " + checkEstilo() + " " + idTitulo + ">" + titulo + "</td>" + "\n");
      
      pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Titulo");
      
      agregarBotonesBarraSuperior(resultado);
      
      resultado.append("  </tr>\n");
    }
  }
  
  private void agregarBotonesBarraSuperior(StringBuffer texto) throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    boolean hayBotones = false;
    Boolean hayEstilo = Boolean.valueOf(false);
    String mouseFueraBarraSuperiorForma = null;
    String mouseFueraBarraSuperiorFormaColor = null;
    try
    {
      estilos = new WelcomeAction().checkEstilo();
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}catch (ParserConfigurationException localParserConfigurationException) {}catch (SAXException localSAXException) {}catch (IOException localIOException) {}catch (Exception localException) {}
    














    if (estilos != null)
    {
      hayEstilo = estilos.getHayConfiguracion();
      mouseFueraBarraSuperiorForma = estilos.getMouseFueraBarraSuperiorForma();
      mouseFueraBarraSuperiorFormaColor = estilos.getMouseFueraBarraSuperiorFormaColor();
    }
    

    resultado.append("<!-- Barra Superior de Botones -->    <td " + checkEstilo() + " align=\"right\" width=\"50%\">");
    

    String botonActualizar = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonActualizar");
    if ((botonActualizar != null) && (!botonActualizar.equals("")))
    {
      hayBotones = true;
      String boton = "actualizar";
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals("")))
      {
        if (mouseFueraBarraSuperiorFormaColor.indexOf("ffffff") != -1)
          boton = boton + "Blanco";
      }
      resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/formulario/" + boton + ".gif\"");
      resultado.append(" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(bundle, localeKey, "boton.actualizar.alt") + "\">&nbsp;");
      resultado.append("<a onMouseOver=\"this.className='mouseEncimaBarraSuperiorForma'\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals(""))) {
        resultado.append(" onMouseOut=\"this.style.color='" + mouseFueraBarraSuperiorFormaColor + "'\"");
      } else
        resultado.append(" onMouseOut=\"this.className='mouseFueraBarraSuperiorForma'\"");
      resultado.append(" href=\"" + botonActualizar + "\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorForma.equals(""))) {
        resultado.append(" style=\"" + mouseFueraBarraSuperiorForma + "\">" + getMessageResource(bundle, localeKey, "boton.actualizar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
      } else
        resultado.append(" class=\"mouseFueraBarraSuperiorForma\">" + getMessageResource(bundle, localeKey, "boton.actualizar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonActualizar");
    

    String botonAyuda = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonAyuda");
    if ((botonAyuda != null) && (!botonAyuda.equals("")))
    {
      hayBotones = true;
      String boton = "ayuda";
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals("")))
      {
        if (mouseFueraBarraSuperiorFormaColor.indexOf("ffffff") != -1)
          boton = boton + "blanco";
      }
      resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/formulario/" + boton + ".gif\"");
      resultado.append(" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(bundle, localeKey, "boton.ayuda.alt") + "\">&nbsp;");
      resultado.append("<a onMouseOver=\"this.className='mouseEncimaBarraSuperiorForma'\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals(""))) {
        resultado.append(" onMouseOut=\"this.style.color='" + mouseFueraBarraSuperiorFormaColor + "'\"");
      } else
        resultado.append(" onMouseOut=\"this.className='mouseFueraBarraSuperiorForma'\"");
      resultado.append(" href=\"" + botonAyuda + "\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorForma.equals(""))) {
        resultado.append(" style=\"" + mouseFueraBarraSuperiorForma + "\">" + getMessageResource(bundle, localeKey, "boton.ayuda") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
      } else
        resultado.append(" class=\"mouseFueraBarraSuperiorForma\">" + getMessageResource(bundle, localeKey, "boton.ayuda") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonAyuda");
    

    String botonRegresar = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonRegresar");
    if ((botonRegresar != null) && (!botonRegresar.equals("")))
    {
      hayBotones = true;
      String boton = "regresar";
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals("")))
      {
        if (mouseFueraBarraSuperiorFormaColor.indexOf("ffffff") != -1)
          boton = boton + "blanco";
      }
      resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/formulario/" + boton + ".gif\"");
      resultado.append(" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(bundle, localeKey, "boton.regresar.alt") + "\">&nbsp;");
      resultado.append("<a onMouseOver=\"this.className='mouseEncimaBarraSuperiorForma'\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorFormaColor.equals(""))) {
        resultado.append(" onMouseOut=\"this.style.color='" + mouseFueraBarraSuperiorFormaColor + "'\"");
      } else
        resultado.append(" onMouseOut=\"this.className='mouseFueraBarraSuperiorForma'\"");
      resultado.append(" href=\"" + botonRegresar + "\"");
      if ((hayEstilo.booleanValue()) && (!mouseFueraBarraSuperiorForma.equals(""))) {
        resultado.append(" style=\"" + mouseFueraBarraSuperiorForma + "\">" + getMessageResource(bundle, localeKey, "boton.regresar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
      } else
        resultado.append(" class=\"mouseFueraBarraSuperiorForma\">" + getMessageResource(bundle, localeKey, "boton.regresar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BotonRegresar");
    resultado.append("&nbsp;</td>\n");
    
    if (hayBotones) {
      texto.append(resultado);
    }
  }
  
  private void agregarBarraMenu(StringBuffer resultado) throws JspException
  {
    String barraMenus = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraMenus");
    if ((barraMenus != null) && (!barraMenus.equals("")))
    {
      resultado.append("      <tr class=\"barraMenusForma\">\n");
      resultado.append("        <td colspan=\"2\">\n");
      resultado.append(barraMenus);
      resultado.append("        </td>\n");
      resultado.append("      </tr>\n");
    }
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraMenus");
  }
  
  private void agregarBarraGenerica(StringBuffer resultado)
    throws JspException
  {
    String barraGenerica = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraGenerica");
    if ((barraGenerica != null) && (!barraGenerica.equals(""))) {
      resultado.append(barraGenerica);
    }
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraGenerica");
  }
  
  private void agregarBarraInferior(StringBuffer resultado) throws JspException
  {
    String contenidoBarraInferior = (String)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraInferior");
    
    if ((contenidoBarraInferior != null) && (!contenidoBarraInferior.equals(""))) {
      resultado.append(contenidoBarraInferior);
    }
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.BarraInferior");
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Paginador");
    
    if ((esSelector != null) && (esSelector.equals("true")))
    {
      String botonAceptar = "";
      String botonCancelar = "";
      if (comandoAceptarSelector != null)
        botonAceptar = comandoAceptarSelector;
      if (comandoCancelarSelector != null)
        botonCancelar = comandoCancelarSelector;
      if ((!botonAceptar.equals("")) || (!botonCancelar.equals("")))
      {
        resultado.append("      <tr class=\"barraInferiorForma\">\n");
        resultado.append("        <td colspan=\"2\">&nbsp;\n");
        if (!botonAceptar.equals(""))
          resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/formulario/aceptar.gif\" border=\"0\"  width=\"10\" height=\"10\"> <a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" href=\"javascript:" + botonAceptar + "\" class=\"mouseFueraBarraInferiorForma\" >" + getMessageResource(bundle, localeKey, "boton.aceptar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        if (!botonCancelar.equals(""))
          resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/formulario/cancelar.gif\" border=\"0\" width=\"10\" height=\"10\"> <a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" href=\"javascript:" + botonCancelar + "\" class=\"mouseFueraBarraInferiorForma\" >" + getMessageResource(bundle, localeKey, "boton.cancelar") + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        resultado.append("</td>\n");
        resultado.append("      </tr>\n");
      }
    }
  }
  
  public String checkEstilo()
  {
    String estilo = "class=\"barraSuperiorForma\"";
    
    try
    {
      if (estilos == null)
        estilos = new WelcomeAction().checkEstilo();
      if ((estilos != null) && (estilos.getHayConfiguracion().booleanValue())) {
        estilo = "style=\"" + estilos.getEstiloFormaInterna() + "\"";
      }
    }
    catch (Exception localException) {}
    

    return estilo;
  }
  



  public void release()
  {
    super.release();
  }
}
