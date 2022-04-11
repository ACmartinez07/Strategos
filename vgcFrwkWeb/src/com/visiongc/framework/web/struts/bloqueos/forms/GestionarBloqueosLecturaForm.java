package com.visiongc.framework.web.struts.bloqueos.forms;

import com.visiongc.framework.web.struts.forms.VisorGenericoForm;

public class GestionarBloqueosLecturaForm extends VisorGenericoForm
{
  static final long serialVersionUID = 0L;
  private String atributoOrdenLectura;
  private String tipoOrdenLectura;
  
  public GestionarBloqueosLecturaForm() {}
  
  public String getAtributoOrdenLectura() {
    return atributoOrdenLectura;
  }
  
  public void setAtributoOrdenLectura(String atributoOrdenLectura) {
    this.atributoOrdenLectura = atributoOrdenLectura;
  }
  
  public String getTipoOrdenLectura() {
    return tipoOrdenLectura;
  }
  
  public void setTipoOrdenLectura(String tipoOrdenLectura) {
    this.tipoOrdenLectura = tipoOrdenLectura;
  }
}
