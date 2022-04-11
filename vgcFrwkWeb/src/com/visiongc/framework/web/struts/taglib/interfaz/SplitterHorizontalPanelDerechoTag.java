package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;





public class SplitterHorizontalPanelDerechoTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelDerecho";
  
  public SplitterHorizontalPanelDerechoTag() {}
  
  protected String splitterId = null;
  
  protected SplitterHorizontalTag splitter = null;
  
  public String getSplitterId() {
    return splitterId;
  }
  
  public void setSplitterId(String splitterId) {
    this.splitterId = splitterId;
  }
  
  public int doStartTag() throws JspException {
    splitter = ((SplitterHorizontalTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal" + splitterId));
    
    if (splitter == null) {
      throw new JspException("El tag SplitterHorizontalPanelDerecho debe estar dentro de un tag SplitterHorizontal");
    }
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    if ((bodyContent != null) && (!bodyContent.equals("")))
    {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterHorizontal.PanelDerecho" + splitter.getSplitterId(), bodyContent.getString());
    }
    

    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
