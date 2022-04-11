package com.visiongc.framework.web.struts.forms;





public class SelectorListaForm
  extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  



  private String valoresSeleccionados = null;
  
  private String nombreForma = null;
  
  private String nombreCampoValor = null;
  
  private String nombreCampoOculto = null;
  private String funcionCierre;
  
  public SelectorListaForm() {}
  
  public String getValoresSeleccionados() { return valoresSeleccionados; }
  
  public void setValoresSeleccionados(String valoresSeleccionados)
  {
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
