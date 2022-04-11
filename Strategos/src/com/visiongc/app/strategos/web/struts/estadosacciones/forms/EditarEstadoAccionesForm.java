package com.visiongc.app.strategos.web.struts.estadosacciones.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarEstadoAccionesForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long estadoId;
  private String nombre;
  private Boolean aplicaSeguimiento;
  private Integer orden;
  private Boolean indicaFinalizacion;

  public Long getEstadoId()
  {
    return this.estadoId;
  }

  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getAplicaSeguimiento() {
    return this.aplicaSeguimiento;
  }

  public void setAplicaSeguimiento(Boolean aplicaSeguimiento) {
    this.aplicaSeguimiento = aplicaSeguimiento;
  }

  public Integer getOrden() {
    return this.orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  public Boolean getIndicaFinalizacion() {
    return this.indicaFinalizacion;
  }

  public void setIndicaFinalizacion(Boolean indicaFinalizacion) {
    this.indicaFinalizacion = indicaFinalizacion;
  }

  public void clear() {
    this.estadoId = new Long(0L);
    this.nombre = null;
    this.aplicaSeguimiento = new Boolean(false);
    this.indicaFinalizacion = new Boolean(false);
    this.orden = new Integer(0);
    setBloqueado(new Boolean(false));
  }
}