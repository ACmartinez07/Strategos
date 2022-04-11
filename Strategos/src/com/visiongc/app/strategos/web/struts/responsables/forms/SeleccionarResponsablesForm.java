package com.visiongc.app.strategos.web.struts.responsables.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarResponsablesForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private String organizacionId;
  private String mostrarGrupos;
  private String mostrarGlobales;

  public String getOrganizacionId()
  {
    return this.organizacionId;
  }

  public void setOrganizacionId(String organizacionId) {
    this.organizacionId = organizacionId;
  }

  public String getMostrarGrupos() {
    return this.mostrarGrupos;
  }

  public void setMostrarGrupos(String mostrarGrupos) {
    this.mostrarGrupos = mostrarGrupos;
  }

  public String getMostrarGlobales() {
    return this.mostrarGlobales;
  }

  public void setMostrarGlobales(String mostrarGlobales) {
    this.mostrarGlobales = mostrarGlobales;
  }
}