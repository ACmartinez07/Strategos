package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;








public class SetFocoObjetoInterfazTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.SetFocoObjetoInterfaz";
  
  public SetFocoObjetoInterfazTag() {}
  
  protected String objetoInputHtml = null;
  
  public String getObjetoInputHtml() {
    return objetoInputHtml;
  }
  
  public void setObjetoInputHtml(String objetoInputHtml) {
    this.objetoInputHtml = objetoInputHtml;
  }
  
  public int doStartTag() throws JspException
  {
    return 0;
  }
  
  public int doEndTag() throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("\n<script language=\"Javascript\">\n");
    resultado.append("function setFocoObjetoInterfaz() {\n");
    resultado.append("\t" + objetoInputHtml + ".focus();" + "\n");
    resultado.append("}\n");
    
    resultado.append("</script>\n");
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
