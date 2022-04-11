package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;










public class QueryStringConfSplitHorizontalTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.AnchoJsSplitHorizontal";
  
  public QueryStringConfSplitHorizontalTag() {}
  
  protected String splitId = null;
  
  protected String primerParametro = null;
  
  public String getSplitId() {
    return splitId;
  }
  
  public void setSplitId(String splitId) {
    this.splitId = splitId;
  }
  
  public String getPrimerParametro() {
    return primerParametro;
  }
  
  public void setPrimerParametro(String primerParametro) {
    this.primerParametro = primerParametro;
  }
  
  public int doStartTag() throws JspException
  {
    return 0;
  }
  




  public int doEndTag()
    throws JspException
  {
    String separadorParametro = "&";
    
    if ((primerParametro != null) && (primerParametro.equalsIgnoreCase("true"))) {
      separadorParametro = "?";
    }
    TagUtils.getInstance().write(pageContext, "'" + separadorParametro + "com.visiongc.framework.web.controles.Split" + "." + splitId + ".anchoPanelIzquierdo=' + " + splitId + "AnchoPanelIzquierdo");
    
    return 6;
  }
  


  public void release()
  {
    super.release();
  }
}
