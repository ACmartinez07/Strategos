package com.visiongc.framework.web.struts.forms;

import org.apache.struts.action.ActionForm;








public class VisorArbolForm
  extends ActionForm
{
  static final long serialVersionUID = 0L;
  private Object raiz = null;
  private Object seleccionado = null;
  private String nodosAbiertos = null;
  private Boolean editarForma = null;
  private Boolean verForma = null;
  private String bodyHeight = null;
  

  public VisorArbolForm() {}
  
  public Object getRaiz()
  {
    return raiz;
  }
  




  public void setRaiz(Object raiz)
  {
    this.raiz = raiz;
  }
  



  public Object getSeleccionado()
  {
    return seleccionado;
  }
  




  public void setSeleccionado(Object seleccionado)
  {
    this.seleccionado = seleccionado;
  }
  



  public String getNodosAbiertos()
  {
    return nodosAbiertos;
  }
  




  public void setNodosAbiertos(String nodosAbiertos)
  {
    this.nodosAbiertos = nodosAbiertos;
  }
  



  public Object getEditarForma()
  {
    return editarForma;
  }
  




  public void setEditarForma(Boolean editarForma)
  {
    this.editarForma = editarForma;
  }
  



  public Object getVerForma()
  {
    return verForma;
  }
  




  public void setVerForma(Boolean verForma)
  {
    this.verForma = verForma;
  }
  



  public String getBodyHeight()
  {
    return bodyHeight;
  }
  




  public void setBodyHeight(String bodyHeight)
  {
    this.bodyHeight = bodyHeight;
  }
}
