package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;







public class SplitterHorizontalTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal";
  
  public SplitterHorizontalTag() {}
  
  protected String splitterId = null;
  
  protected String anchoPorDefecto = null;
  
  protected String overflowPanelIzquierdo = null;
  
  protected String overflowPanelDerecho = null;
  
  public String getSplitterId() {
    return splitterId;
  }
  
  public void setSplitterId(String splitterId) {
    this.splitterId = splitterId;
  }
  
  public String getAnchoPorDefecto() {
    return anchoPorDefecto;
  }
  
  public void setAnchoPorDefecto(String anchoPorDefecto) {
    this.anchoPorDefecto = anchoPorDefecto;
  }
  
  public String getOverflowPanelDerecho() {
    return overflowPanelDerecho;
  }
  
  public void setOverflowPanelDerecho(String overflowPanelDerecho) {
    this.overflowPanelDerecho = overflowPanelDerecho;
  }
  
  public String getOverflowPanelIzquierdo() {
    return overflowPanelIzquierdo;
  }
  
  public void setOverflowPanelIzquierdo(String overflowPanelIzquierdo) {
    this.overflowPanelIzquierdo = overflowPanelIzquierdo;
  }
  
  public int doStartTag() throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal" + splitterId, this);
    
    return 2;
  }
  






  private StringBuffer agregarInicio()
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("\n");
    resultado.append("\n<script type=\"text/javascript\">\n");
    
    resultado.append("var " + getSplitterId() + "AnchoActual=0;" + "\n");
    resultado.append("var " + getSplitterId() + "PosicionActual=0;" + "\n");
    resultado.append("var " + getSplitterId() + "PosicionNueva=0;" + "\n");
    resultado.append("var startHorizontal=false;\n");
    

    String ancho = anchoPorDefecto;
    String anchoNuevo = (String)pageContext.getSession().getAttribute("com.visiongc.framework.web.controles.Split." + splitterId + ".anchoPanelIzquierdo");
    if ((anchoNuevo != null) && (!anchoNuevo.equals("")))
      ancho = anchoNuevo;
    resultado.append("var " + getSplitterId() + "AnchoPanelIzquierdo='" + ancho + "';" + "\n");
    

    resultado.append("var " + getSplitterId() + "MouseStatus='up';" + "\n");
    

    resultado.append("function " + getSplitterId() + "SetPosicion(e) {" + "\n");
    
    resultado.append(" eventoActual = (typeof event == 'undefined'? e: event) ;\n");
    
    resultado.append("  " + getSplitterId() + "MouseStatus = 'down';" + "\n");
    
    resultado.append("\t" + getSplitterId() + "PosicionActual = eventoActual.clientX;" + "\n");
    
    resultado.append("  anchoTemp = document.getElementById('" + getSplitterId() + "PanelIzquierdo').style.width;" + "\n");
    
    resultado.append("  arregloAncho = anchoTemp.split('p');\n");
    resultado.append("  " + getSplitterId() + "AnchoActual = parseInt(arregloAncho[0]);" + "\n");
    resultado.append("  startHorizontal=true;\n");
    resultado.append("}\n");
    


    resultado.append("function " + getSplitterId() + "GetPosicion(e) {" + "\n");
    resultado.append(" if (" + getSplitterId() + "MouseStatus == 'down') {" + "\n");
    resultado.append("    eventoActual = (typeof event == 'undefined'? e: event);\n");
    
    resultado.append("    " + getSplitterId() + "PosicionNueva = eventoActual.clientX;" + "\n");
    
    resultado.append("    var movimientoPx = parseInt(" + getSplitterId() + "PosicionNueva - " + getSplitterId() + "PosicionActual);" + "\n");
    
    resultado.append("    var anchoNuevo = parseInt(" + getSplitterId() + "AnchoActual + movimientoPx);" + "\n");
    
    resultado.append("    anchoNuevo = (anchoNuevo < 50 ? 50 : anchoNuevo);\n");
    
    resultado.append("    document.getElementById('" + getSplitterId() + "PanelIzquierdo').style.width = anchoNuevo + 'px';" + "\n");
    
    resultado.append("    appGlobalRefreshHtmlElements();\n");
    resultado.append("    " + getSplitterId() + "AnchoPanelIzquierdo = anchoNuevo + 'px';" + "\n");
    resultado.append("  }\n");
    resultado.append("}\n");
    
    resultado.append("function checkSetPosicion()\n");
    resultado.append("{\n");
    resultado.append("  if (" + getSplitterId() + "MouseStatus == 'up' && window.setAnchoPanel)" + "\n");
    resultado.append("    \tsetAnchoPanel();\n");
    resultado.append("}\n");
    resultado.append("</script>\n");
    
    resultado.append("<style type=\"text/css\">\n");
    resultado.append("#" + getSplitterId() + "HSplit {" + "\n");
    resultado.append("    cursor: w-resize;\n");
    resultado.append("    background-color: #C0C0C0;\n");
    resultado.append("    height: 100%;\n");
    resultado.append("    padding: 0px;\n");
    resultado.append("    width: 1px;\n");
    resultado.append("}\n");
    resultado.append("</style>\n");
    
    resultado.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" onmousemove=\"" + getSplitterId() + "GetPosicion(event)\" onmouseup=\"" + getSplitterId() + "MouseStatus='up'; checkSetPosicion();\">" + "\n");
    resultado.append("  <tr>\n");
    resultado.append("    <td id=\"" + getSplitterId() + "PanelIzquierdo\" class=\"panelSplit\" style=\"width:" + ancho + "\">" + "\n");
    resultado.append("    <div style=\"position:relative;overflow:" + overflowPanelIzquierdo + "; width:100%; height:100%; padding:0px; z-index:1\">" + "\n");
    resultado.append("    <div style=\"position:absolute; width:100%; height:100%; z-index:1; left:0px; top:0px; padding:0px;\">\n");
    resultado.append("    <div style=\"position:relative;\">\n");
    resultado.append(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelIzquierdo" + getSplitterId()));
    resultado.append("    </div></div></div>\n");
    resultado.append("    </td>\n");
    resultado.append("    <td onmousedown=\"" + getSplitterId() + "SetPosicion(event)\" id=\"" + getSplitterId() + "HSplit\"></td>" + "\n");
    resultado.append("    <td id=\"" + getSplitterId() + "PanelDerecho\" class=\"panelSplit\">" + "\n");
    resultado.append("    \t<div style=\"position:relative;overflow:" + overflowPanelDerecho + "; width:100%; height:100%; padding:0px; z-index:1\">" + "\n");
    resultado.append("    \t\t<div style=\"position:absolute; width:100%; height:100%; z-index:1; left:0px; top:0px; padding:0px\">\n");
    resultado.append("    \t\t\t<div style=\"position:relative;\">\n");
    resultado.append(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelDerecho" + getSplitterId()));
    resultado.append("    \t\t\t</div>\n");
    resultado.append("    \t\t</div>\n");
    resultado.append("    \t</div>\n");
    resultado.append("    </td>\n");
    resultado.append("  </tr>\n");
    resultado.append("</table>\n");
    
    return resultado;
  }
  
  public int doEndTag() throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelIzquierdo" + getSplitterId()) == null)
      throw new JspException("El tag SplitterHorizontal debe contener un tag SplitterHorizontalPanelIzquierdo");
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelDerecho" + getSplitterId()) == null) {
      throw new JspException("El tag SplitterHorizontal debe contener un tag SplitterHorizontalPanelDerecho");
    }
    StringBuffer resultado = agregarInicio();
    TagUtils.getInstance().write(pageContext, resultado.toString());
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal");
    
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
