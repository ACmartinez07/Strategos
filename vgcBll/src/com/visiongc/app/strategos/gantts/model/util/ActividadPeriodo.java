package com.visiongc.app.strategos.gantts.model.util;

import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import java.io.Serializable;

public class ActividadPeriodo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Integer longitudLineaBasePlan;
  private Integer longitudLineaBaseReal;
  private Integer longitudLineaTiempoPlan;
  private Integer longitudLineaTiempoReal;
  private PryActividad actividad;
  
  public ActividadPeriodo(Integer longitudLineaBasePlan, Integer longitudLineaBaseReal, Integer longitudLineaTiempoPlan, Integer longitudLineaTiempoReal, PryActividad actividad)
  {
    this.longitudLineaBasePlan = longitudLineaBasePlan;
    this.longitudLineaBaseReal = longitudLineaBaseReal;
    this.longitudLineaTiempoPlan = longitudLineaTiempoPlan;
    this.longitudLineaTiempoReal = longitudLineaTiempoReal;
    this.actividad = actividad;
  }
  

  public ActividadPeriodo() {}
  

  public Integer getLongitudLineaBasePlan()
  {
    return longitudLineaBasePlan;
  }
  
  public void setLongitudLineaBasePlan(Integer longitudLineaBasePlan) {
    this.longitudLineaBasePlan = longitudLineaBasePlan;
  }
  
  public Integer getLongitudLineaBaseReal() {
    return longitudLineaBaseReal;
  }
  
  public void setLongitudLineaBaseReal(Integer longitudLineaBaseReal) {
    this.longitudLineaBaseReal = longitudLineaBaseReal;
  }
  
  public Integer getLongitudLineaTiempoPlan() {
    return longitudLineaTiempoPlan;
  }
  
  public void setLongitudLineaTiempoPlan(Integer longitudLineaTiempoPlan) {
    this.longitudLineaTiempoPlan = longitudLineaTiempoPlan;
  }
  
  public Integer getLongitudLineaTiempoReal() {
    return longitudLineaTiempoReal;
  }
  
  public void setLongitudLineaTiempoReal(Integer longitudLineaTiempoReal) {
    this.longitudLineaTiempoReal = longitudLineaTiempoReal;
  }
  
  public PryActividad getActividad() {
    return actividad;
  }
  
  public void setActividad(PryActividad actividad) {
    this.actividad = actividad;
  }
}
