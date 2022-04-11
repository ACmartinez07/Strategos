package com.visiongc.app.strategos.web.struts.responsables.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.Set;

public class GestionarResponsablesForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private String filtroCargo;
  private Long responsableId;
  private String organizacionId;
  private Set responsables;

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public String getFiltroCargo() {
    return this.filtroCargo;
  }

  public void setFiltroCargo(String filtroCargo) {
    this.filtroCargo = filtroCargo;
  }

  public Set getResponsables() {
    return this.responsables;
  }

  public void setResponsables(Set responsables) {
    this.responsables = responsables;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public String getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(String organizacionId) {
    this.organizacionId = organizacionId;
  }
}