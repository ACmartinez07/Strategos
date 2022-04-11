package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;







public class SplitterVerticalTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical";
  
  public SplitterVerticalTag() {}
  
  protected String splitterId = null;
  
  protected String altoPorDefecto = null;
  
  protected String overflowPanelSuperior = null;
  
  protected String overflowPanelInferior = null;
  
  public String getSplitterId() {
    return splitterId;
  }
  
  public void setSplitterId(String splitterId) {
    this.splitterId = splitterId;
  }
  
  public String getAltoPorDefecto() {
    return altoPorDefecto;
  }
  
  public void setAltoPorDefecto(String altoPorDefecto) {
    this.altoPorDefecto = altoPorDefecto;
  }
  
  public String getOverflowPanelSuperior() {
    return overflowPanelSuperior;
  }
  
  public void setOverflowPanelSuperior(String overflowPanelSuperior) {
    this.overflowPanelSuperior = overflowPanelSuperior;
  }
  
  public String getOverflowPanelInferior() {
    return overflowPanelInferior;
  }
  
  public void setOverflowPanelInferior(String overflowPanelInferior) {
    this.overflowPanelInferior = overflowPanelInferior;
  }
  
  public int doStartTag() throws JspException
  {
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical" + splitterId, this);
    
    return 2;
  }
  






  private StringBuffer agregarInicio()
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("\n");
    resultado.append("\n<script type=\"text/javascript\">\n");
    resultado.append("<!--\n");
    
    resultado.append("var " + getSplitterId() + "AltoActual=0;" + "\n");
    resultado.append("var " + getSplitterId() + "PosicionActual=0;" + "\n");
    resultado.append("var " + getSplitterId() + "PosicionNueva=0;" + "\n");
    
    String alto = altoPorDefecto;
    String altoNuevo = (String)pageContext.getSession().getAttribute("com.visiongc.framework.web.controles.Split." + splitterId + ".altoPanelSuperior");
    if ((altoNuevo != null) && (!altoNuevo.equals(""))) {
      alto = altoNuevo;
    }
    resultado.append("var " + getSplitterId() + "AltoPanelSuperior='" + alto + "';" + "\n");
    

    resultado.append("var " + getSplitterId() + "MouseStatus='up';" + "\n");
    

    resultado.append("function " + getSplitterId() + "SetPosicion(e) {" + "\n");
    
    resultado.append("  eventoActual = (typeof event == 'undefined'? e: event) ;\n");
    
    resultado.append("  " + getSplitterId() + "MouseStatus = 'down';" + "\n");
    
    resultado.append("\t" + getSplitterId() + "PosicionActual = eventoActual.clientY;" + "\n");
    
    resultado.append("  altoTemp = document.getElementById('" + getSplitterId() + "PanelSuperior').style.height;" + "\n");
    
    resultado.append("  arregloAlto = altoTemp.split('p');\n");
    resultado.append("  " + getSplitterId() + "AltoActual = parseInt(arregloAlto[0]);" + "\n");
    resultado.append("}\n");
    


    resultado.append("function " + getSplitterId() + "GetPosicion(e) {" + "\n");
    resultado.append("  if (" + getSplitterId() + "MouseStatus == 'down') {" + "\n");
    resultado.append("    eventoActual = (typeof event == 'undefined'? e: event);\n");
    
    resultado.append("    " + getSplitterId() + "PosicionNueva = eventoActual.clientY;" + "\n");
    
    resultado.append("    var movimientoPx = parseInt(" + getSplitterId() + "PosicionNueva - " + getSplitterId() + "PosicionActual);" + "\n");
    
    resultado.append("    var altoNuevo = parseInt(" + getSplitterId() + "AltoActual + movimientoPx);" + "\n");
    
    resultado.append("    altoNuevo = (altoNuevo < 50 ? 50 : altoNuevo);\n");
    
    resultado.append("    document.getElementById('" + getSplitterId() + "PanelSuperior').style.height = altoNuevo + 'px';" + "\n");
    resultado.append("    " + getSplitterId() + "AltoPanelSuperior = altoNuevo + 'px';" + "\n");
    resultado.append("  }\n");
    resultado.append("}\n");
    resultado.append("// -->\n");
    resultado.append("</script>\n");
    
    resultado.append("<style type=\"text/css\">\n");
    resultado.append("#" + getSplitterId() + "VSplit {" + "\n");
    resultado.append("    cursor: s-resize;\n");
    resultado.append("    background-color: #C0C0C0;\n");
    resultado.append("    witdth: 100%;\n");
    resultado.append("    padding: 0px;\n");
    resultado.append("    width: 1px;\n");
    resultado.append("}\n");
    resultado.append("</style>\n");
    
    resultado.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" onmousemove=\"" + getSplitterId() + "GetPosicion(event)\" onmouseup=\"" + getSplitterId() + "MouseStatus='up'\">" + "\n");
    resultado.append("  <tr>\n");
    resultado.append("    <td id=\"" + getSplitterId() + "PanelSuperior\" class=\"panelSplit\" style=\"height:" + alto + "\">" + "\n");
    resultado.append("    <div style=\"position:relative;overflow:" + overflowPanelSuperior + "; width:100%; height:100%; padding:0px; z-index:1\">" + "\n");
    resultado.append("    <div style=\"position:absolute; width:100%; height:100%; z-index:1; left:0px; top:0px; padding:0px;\">\n");
    resultado.append("    <div style=\"position:relative;\">\n");
    resultado.append(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelSuperior" + getSplitterId()));
    resultado.append("    </div></div></div>\n");
    resultado.append("    </td>\n");
    resultado.append("  </tr>\n");
    resultado.append("  <tr>\n");
    resultado.append("    <td onmousedown=\"" + getSplitterId() + "SetPosicion(event)\" id=\"" + getSplitterId() + "VSplit\"></td>" + "\n");
    resultado.append("  </tr>\n");
    resultado.append("  <tr>\n");
    resultado.append("    <td class=\"panelSplit\">\n");
    resultado.append("    <div style=\"position:relative;overflow:" + overflowPanelInferior + "; width:100%; height:100%; padding:0px; z-index:1\">" + "\n");
    resultado.append("    <div style=\"position:absolute; width:100%; height:100%; z-index:1; left:0px; top:0px; padding:0px\">\n");
    resultado.append("    <div style=\"position:relative;\">\n");
    resultado.append(pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelInferior" + getSplitterId()));
    resultado.append("    </div></div></div>\n");
    resultado.append("    </td>\n");
    resultado.append("  </tr>\n");
    resultado.append("</table>\n");
    
    return resultado;
  }
  
  public int doEndTag() throws JspException
  {
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelSuperior" + getSplitterId()) == null) {
      throw new JspException("El tag SplitterVertical debe contener un tag SplitterVerticalPanelSuperior");
    }
    
    if (pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelInferior" + getSplitterId()) == null) {
      throw new JspException("El tag SplitterVertical debe contener un tag SplitterVerticalPanelInferior");
    }
    
    StringBuffer resultado = agregarInicio();
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
