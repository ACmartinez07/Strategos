package com.visiongc.app.strategos.web.struts.planes.indicadores.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class AsignarPesosIndicadoresPlanForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long objetivoId;
  private String organizacionNombre;
  private String planNombre;
  private String perspectivaNombre;
  private String funcionCierre;

  public Long getObjetivoId()
  {
    return this.objetivoId;
  }

  public void setObjetivoId(Long objetivoId) {
    this.objetivoId = objetivoId;
  }

  public Long getPerspectivaId() {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }

  public String getOrganizacionNombre() {
    return this.organizacionNombre;
  }

  public void setOrganizacionNombre(String organizacionNombre) {
    this.organizacionNombre = organizacionNombre;
  }

  public String getPerspectivaNombre() {
    return this.perspectivaNombre;
  }

  public void setPerspectivaNombre(String perspectivaNombre) {
    this.perspectivaNombre = perspectivaNombre;
  }

  public String getPlanNombre() {
    return this.planNombre;
  }

  public void setPlanNombre(String planNombre) {
    this.planNombre = planNombre;
  }

  public String getFuncionCierre() {
    return this.funcionCierre;
  }

  public void setFuncionCierre(String funcionCierre) {
    this.funcionCierre = funcionCierre;
  }

  public void clear() {
    this.objetivoId = null;
    this.perspectivaId = null;
    this.organizacionNombre = null;
    this.planNombre = null;
    this.perspectivaNombre = null;
    this.funcionCierre = null;
  }
}