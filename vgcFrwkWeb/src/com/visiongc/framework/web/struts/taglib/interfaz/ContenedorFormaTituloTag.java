package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ContenedorFormaTituloInfo;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class ContenedorFormaTituloTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma.Titulo";
  protected String nombre = null;
  protected ContenedorFormaTag contenedorFormaTag = null;
  
  public ContenedorFormaTituloTag() {}
  
  public String getNombre() { return nombre; }
  

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public int doStartTag() throws JspException
  {
    contenedorFormaTag = ((ContenedorFormaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.ContenedorForma"));
    
    if (contenedorFormaTag == null) {
      throw new JspException("El tag ContenedorFormaTitulo debe estar dentro de un tag ContenedorForma");
    }
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    ContenedorFormaTituloInfo tituloInfo = new ContenedorFormaTituloInfo();
    
    tituloInfo.setNombre(nombre);
    if (bodyContent != null) {
      tituloInfo.setTitulo(bodyContent.getString());
    }
    contenedorFormaTag.setContenedorFormaTituloInfo(tituloInfo);
    

    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
