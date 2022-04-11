package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ValorFilaColumnaVisorListaInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;



public class ValorFilaColumnaVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna";
  
  public ValorFilaColumnaVisorListaTag() {}
  
  private static String ALINEACION_POR_DEFECTO = "left";
  
  protected String nombre = null;
  
  protected String align = null;
  
  protected String valorId = null;
  
  protected String esValorSelector = null;
  
  private FilasVisorListaTag filasVisorLista = null;
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAlign() {
    return align;
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
  public String getValorId() {
    return valorId;
  }
  
  public void setValorId(String valorId) {
    this.valorId = valorId;
  }
  
  public String getEsValorSelector() {
    return esValorSelector;
  }
  
  public void setEsValorSelector(String esValorSelector) {
    this.esValorSelector = esValorSelector;
  }
  
  public int doStartTag() throws JspException {
    filasVisorLista = ((FilasVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas"));
    
    if (filasVisorLista == null) {
      throw new JspException("El tag ValorFilaColumnaVisorLista debe estar dentro de un tag FilasVisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna", this);
    
    return 2;
  }
  
  private String agregarInicioColumnaVisorLista()
  {
    StringBuffer resultado = new StringBuffer();
    String alineacion = ALINEACION_POR_DEFECTO;
    String valorId = "";
    
    if ((align != null) && (!align.equals(""))) {
      alineacion = align;
    }
    
    if ((esValorSelector != null) && (esValorSelector.equalsIgnoreCase("true"))) {
      valorId = "id=\"valor" + filasVisorLista.getFila().getValorId() + "\"";
    } else if ((this.valorId != null) && (!this.valorId.equals(""))) {
      valorId = "id=\"" + this.valorId + "\"";
    }
    

    resultado.append("    <td " + valorId + " align=\"" + alineacion + "\" >");
    
    return resultado.toString();
  }
  
  private String agregarFinColumnaVisorLista() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (bodyContent != null) {
      resultado.append(HtmlUtil.trimTextoHtml(bodyContent.getString()));
    }
    resultado.append("</td>\n");
    
    return resultado.toString();
  }
  
  public int doEndTag()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append(agregarInicioColumnaVisorLista());
    resultado.append(agregarFinColumnaVisorLista());
    
    ValorFilaColumnaVisorListaInfo valorFilaColumna = new ValorFilaColumnaVisorListaInfo();
    
    valorFilaColumna.setNombre(getNombre());
    valorFilaColumna.setCodigo(resultado.toString());
    
    filasVisorLista.getFila().getValoresFilaColumna().put(nombre, valorFilaColumna);
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna");
    
    align = null;
    nombre = null;
    valorId = null;
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
