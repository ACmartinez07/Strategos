package com.visiongc.framework.web.struts.taglib.interfaz.visorlista;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.FilasVisorListaTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;




public class VisorListaFilaEventoOnclickTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Fila.EventoOnclick";
  
  public VisorListaFilaEventoOnclickTag() {}
  
  private FilasVisorListaTag filasVisorLista = null;
  
  public int doStartTag() throws JspException {
    filasVisorLista = ((FilasVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas"));
    
    if (filasVisorLista == null) {
      throw new JspException("El tag VisorListaFilaEventoOnclick debe estar dentro de un tag FilasVisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Fila.EventoOnclick", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    filasVisorLista.getFila().setEventoOnclick(HtmlUtil.trimTextoHtml(bodyContent.getString()));
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Fila.EventoOnclick");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
