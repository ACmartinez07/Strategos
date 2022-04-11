package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.app.strategos.indicadores.model.Indicador;

public class MetasIndicador
{
  private Indicador indicador;
  private java.util.List metasAnualesParciales;
  
  public MetasIndicador() {}
  
  public Indicador getIndicador() {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador) {
    this.indicador = indicador;
  }
  
  public java.util.List getMetasAnualesParciales() {
    return metasAnualesParciales;
  }
  
  public void setMetasAnualesParciales(java.util.List metasAnualesParciales) {
    this.metasAnualesParciales = metasAnualesParciales;
  }
}
