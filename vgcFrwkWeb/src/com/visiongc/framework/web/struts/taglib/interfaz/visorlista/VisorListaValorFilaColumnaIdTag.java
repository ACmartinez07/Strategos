package com.visiongc.framework.web.struts.taglib.interfaz.visorlista;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.ValorFilaColumnaVisorListaTag;
import com.visiongc.framework.web.util.HtmlUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;





public class VisorListaValorFilaColumnaIdTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna.Id";
  
  public VisorListaValorFilaColumnaIdTag() {}
  
  private ValorFilaColumnaVisorListaTag valorFilaColumnaVisorLista = null;
  
  public int doStartTag() throws JspException {
    valorFilaColumnaVisorLista = ((ValorFilaColumnaVisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna"));
    
    if (valorFilaColumnaVisorLista == null) {
      throw new JspException("El tag VisorListaValorFilaColumnaId debe estar dentro de un tag ValorFilaColumnaVisorLista");
    }
    
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna.Id", this);
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    valorFilaColumnaVisorLista.setValorId(HtmlUtil.trimTextoHtml(bodyContent.getString()));
    
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.ValorFilaColumna.Id");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
