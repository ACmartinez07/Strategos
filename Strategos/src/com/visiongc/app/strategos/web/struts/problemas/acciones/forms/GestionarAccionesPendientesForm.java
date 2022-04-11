package com.visiongc.app.strategos.web.struts.problemas.acciones.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarAccionesPendientesForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Integer tipo;

  public Integer getTipo()
  {
    return this.tipo;
  }

  public void setTipo(Integer tipo) {
    this.tipo = tipo;
  }

  public String getFiltroNombre() {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }
}