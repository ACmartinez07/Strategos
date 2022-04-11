package com.visiongc.commons.struts.tag;

import com.visiongc.framework.model.ObjetoSistema;
import com.visiongc.framework.util.ObjetosSistema;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;











public class ObjetoSistemaTag
  extends TagSupport
{
  static final long serialVersionUID = 0L;
  private String objetoId;
  private String articuloSingular;
  private String nombreSingular;
  private String articuloPlural;
  private String nombrePlural;
  private String mayuscula;
  private String minuscula;
  private String html;
  public static final String SPACE = "&nbsp;";
  
  public ObjetoSistemaTag() {}
  
  public String getArticuloPlural()
  {
    return articuloPlural;
  }
  
  public void setArticuloPlural(String articuloPlural) {
    this.articuloPlural = articuloPlural;
  }
  
  public String getArticuloSingular() {
    return articuloSingular;
  }
  
  public void setArticuloSingular(String articuloSingular) {
    this.articuloSingular = articuloSingular;
  }
  
  public String getMayuscula() {
    return mayuscula;
  }
  
  public void setMayuscula(String mayuscula) {
    this.mayuscula = mayuscula;
  }
  
  public String getMinuscula() {
    return minuscula;
  }
  
  public void setMinuscula(String minuscula) {
    this.minuscula = minuscula;
  }
  
  public String getNombrePlural() {
    return nombrePlural;
  }
  
  public void setNombrePlural(String nombrePlural) {
    this.nombrePlural = nombrePlural;
  }
  
  public String getNombreSingular() {
    return nombreSingular;
  }
  
  public void setNombreSingular(String nombreSingular) {
    this.nombreSingular = nombreSingular;
  }
  
  public String getObjetoId() {
    return objetoId;
  }
  
  public void setObjetoId(String objetoId) {
    this.objetoId = objetoId;
  }
  
  public String getHtml()
  {
    return html;
  }
  
  public void setHtml(String html) {
    this.html = html;
  }
  
  public int doStartTag()
  {
    ObjetoSistema os = null;
    StringBuffer sb = null;
    
    try
    {
      if ((objetoId != null) && (!objetoId.equals(""))) {
        os = ObjetosSistema.getInstance().getObjetoSistema(
          objetoId);
      }
      
      JspWriter out = pageContext.getOut();
      
      if (os != null) {
        writeObjetoSistema(out, os);
      } else {
        sb = new StringBuffer();
        
        sb.append("El objeto de sistema no está definido");
        out.println(sb);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  private void writeObjetoSistema(JspWriter out, ObjetoSistema os) throws Exception
  {
    boolean enMayuscula = false;
    boolean enMinuscula = false;
    boolean hayArticulo = false;
    boolean haySingular = false;
    boolean enHtml = false;
    StringBuffer sb = null;
    
    sb = new StringBuffer();
    
    if ((mayuscula != null) && (mayuscula.equals("true"))) {
      enMayuscula = true;
    } else if ((minuscula != null) && 
      (minuscula.equals("true"))) {
      enMinuscula = true;
    }
    
    if ((html != null) && (html.equals("true"))) {
      enHtml = true;
    }
    
    if ((articuloSingular != null) && 
      (articuloSingular.equals("true"))) {
      hayArticulo = true;
      if (enMayuscula) {
        sb.append(os.getArticuloSingular().toUpperCase());
      } else if (enMinuscula) {
        sb.append(os.getArticuloSingular().toLowerCase());
      } else {
        sb.append(os.getArticuloSingular());
      }
    }
    

    if ((articuloPlural != null) && 
      (articuloPlural.equals("true"))) {
      if (hayArticulo) {
        sb.append("(");
      }
      if (enMayuscula) {
        sb.append(os.getArticuloPlural().toUpperCase());
      } else if (enMinuscula) {
        sb.append(os.getArticuloPlural().toLowerCase());
      } else {
        sb.append(os.getArticuloPlural());
      }
      if (hayArticulo) {
        sb.append(")");
      }
      hayArticulo = true;
    }
    

    if ((nombreSingular != null) && 
      (nombreSingular.equals("true"))) {
      haySingular = true;
      if (hayArticulo) {
        if (enHtml) {
          sb.append("&nbsp;");
        } else {
          sb.append(" ");
        }
      }
      if (enMayuscula) {
        sb.append(os.getNombreSingular().toUpperCase());
      } else if (enMinuscula) {
        sb.append(os.getNombreSingular().toLowerCase());
      } else {
        sb.append(os.getNombreSingular());
      }
    }
    
    if ((nombrePlural != null) && 
      (nombrePlural.equals("true"))) {
      if (haySingular) {
        sb.append("(");
      } else if (hayArticulo) {
        if (enHtml) {
          sb.append("&nbsp;");
        } else {
          sb.append(" ");
        }
      }
      if (enMayuscula) {
        sb.append(os.getNombrePlural().toUpperCase());
      } else if (enMinuscula) {
        sb.append(os.getNombrePlural().toLowerCase());
      } else {
        sb.append(os.getNombrePlural());
      }
      if (haySingular) {
        sb.append(")");
      }
    }
    
    if (sb.length() > 0) {
      out.println(sb);
    }
  }
  
  public int doEndTag() {
    return 6;
  }
}
