package com.visiongc.app.strategos.model.util;

public class LapsoTiempo {
  private Integer periodoInicio;
  private Integer periodoFin;
  
  public LapsoTiempo() {}
  
  public Integer getPeriodoFin() {
    return periodoFin;
  }
  
  public void setPeriodoFin(Integer periodoFin)
  {
    this.periodoFin = periodoFin;
  }
  
  public Integer getPeriodoInicio()
  {
    return periodoInicio;
  }
  
  public void setPeriodoInicio(Integer periodoInicio)
  {
    this.periodoInicio = periodoInicio;
  }
}
