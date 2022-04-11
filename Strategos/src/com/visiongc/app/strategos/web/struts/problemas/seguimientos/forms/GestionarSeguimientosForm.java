package com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarSeguimientosForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private Boolean seguimientoCerrado;
  private Boolean esDiaSeguimiento;
  private Boolean existeSeguimiento;
  private Boolean esResponsableAccionCorrectiva;
  private Boolean esResponsableProblema;

  public Boolean getSeguimientoCerrado()
  {
    return this.seguimientoCerrado;
  }

  public void setSeguimientoCerrado(Boolean seguimientoCerrado) {
    this.seguimientoCerrado = seguimientoCerrado;
  }

  public Boolean getEsDiaSeguimiento() {
    return this.esDiaSeguimiento;
  }

  public void setEsDiaSeguimiento(Boolean esDiaSeguimiento) {
    this.esDiaSeguimiento = esDiaSeguimiento;
  }

  public Boolean getExisteSeguimiento() {
    return this.existeSeguimiento;
  }

  public void setExisteSeguimiento(Boolean existeSeguimiento) {
    this.existeSeguimiento = existeSeguimiento;
  }

  public Boolean getEsResponsableAccionCorrectiva() {
    return this.esResponsableAccionCorrectiva;
  }

  public void setEsResponsableAccionCorrectiva(Boolean esResponsableAccionCorrectiva) {
    this.esResponsableAccionCorrectiva = esResponsableAccionCorrectiva;
  }

  public Boolean getEsResponsableProblema() {
    return this.esResponsableProblema;
  }

  public void setEsResponsableProblema(Boolean esResponsableProblema) {
    this.esResponsableProblema = esResponsableProblema;
  }
}