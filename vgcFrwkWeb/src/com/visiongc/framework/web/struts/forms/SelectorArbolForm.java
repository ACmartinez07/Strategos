package com.visiongc.framework.web.struts.forms;

import org.apache.struts.action.ActionForm;












public class SelectorArbolForm
  extends ActionForm
{
  static final long serialVersionUID = 0L;
  private String valoresSeleccionados;
  private String nombreForma;
  private String nombreCampoValor;
  private String nombreCampoOculto;
  private String funcionCierre;
  
  public SelectorArbolForm() {}
  
  public String getValoresSeleccionados()
  {
    return valoresSeleccionados;
  }
  
  public void setValoresSeleccionados(String valoresSeleccionados) {
    this.valoresSeleccionados = valoresSeleccionados;
  }
  
  public String getNombreForma() {
    return nombreForma;
  }
  
  public void setNombreForma(String nombreForma) {
    this.nombreForma = nombreForma;
  }
  
  public String getNombreCampoOculto() {
    return nombreCampoOculto;
  }
  
  public void setNombreCampoOculto(String nombreCampoOculto) {
    this.nombreCampoOculto = nombreCampoOculto;
  }
  
  public String getNombreCampoValor() {
    return nombreCampoValor;
  }
  
  public void setNombreCampoValor(String nombreCampoValor) {
    this.nombreCampoValor = nombreCampoValor;
  }
  
  public String getFuncionCierre() {
    return funcionCierre;
  }
  
  public void setFuncionCierre(String funcionCierre) {
    this.funcionCierre = funcionCierre;
  }
}
