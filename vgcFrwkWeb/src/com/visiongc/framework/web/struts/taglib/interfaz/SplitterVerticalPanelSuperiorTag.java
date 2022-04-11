package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;





public class SplitterVerticalPanelSuperiorTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelSuperior";
  
  public SplitterVerticalPanelSuperiorTag() {}
  
  protected String splitterId = null;
  
  protected SplitterVerticalTag splitter = null;
  
  public String getSplitterId() {
    return splitterId;
  }
  
  public void setSplitterId(String splitterId) {
    this.splitterId = splitterId;
  }
  
  public int doStartTag() throws JspException {
    splitter = ((SplitterVerticalTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical" + splitterId));
    
    if (splitter == null) {
      throw new JspException("El tag SplitterVerticalPanelSuperior debe estar dentro de un tag SplitterVertical");
    }
    
    return 2;
  }
  
  public int doEndTag() throws JspException
  {
    if ((bodyContent != null) && (!bodyContent.equals("")))
    {
      pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.SplitterVertical.PanelSuperior" + splitter.getSplitterId(), bodyContent.getString());
    }
    

    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
