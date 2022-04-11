package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.app.strategos.indicadores.model.Indicador;

public class ValorInicialIndicador
{
  private Indicador indicador;
  private com.visiongc.app.strategos.planes.model.Meta valorInicial;
  private Integer numeroPeriodos;
  private Boolean proteger;
  
  public ValorInicialIndicador() {}
  
  public Indicador getIndicador() {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public com.visiongc.app.strategos.planes.model.Meta getValorInicial()
  {
    return valorInicial;
  }
  
  public void setValorInicial(com.visiongc.app.strategos.planes.model.Meta valorInicial)
  {
    this.valorInicial = valorInicial;
  }
  
  public Integer getNumeroPeriodos()
  {
    return numeroPeriodos;
  }
  
  public void setNumeroPeriodos(Integer numeroPeriodos)
  {
    this.numeroPeriodos = numeroPeriodos;
  }
  
  public Boolean getProteger()
  {
    return proteger;
  }
  
  public void setProteger(Boolean proteger)
  {
    this.proteger = proteger;
  }
}
