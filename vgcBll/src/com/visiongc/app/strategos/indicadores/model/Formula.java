package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Formula
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private FormulaPK pk;
  private String expresion;
  private SerieIndicador serieIndicador;
  private Set insumos;
  
  public Formula(FormulaPK pk, String expresion, SerieIndicador serieIndicador, Set insumos)
  {
    this.pk = pk;
    this.serieIndicador = serieIndicador;
    this.expresion = expresion;
    this.insumos = insumos;
  }
  

  public Formula() {}
  

  public FormulaPK getPk()
  {
    return pk;
  }
  
  public void setPk(FormulaPK pk)
  {
    this.pk = pk;
  }
  
  public String getExpresion()
  {
    return expresion;
  }
  
  public void setExpresion(String expresion)
  {
    this.expresion = expresion;
  }
  
  public SerieIndicador getSerieIndicador()
  {
    return serieIndicador;
  }
  
  public void setSerieIndicador(SerieIndicador serieIndicador)
  {
    this.serieIndicador = serieIndicador;
  }
  
  public Set getInsumos()
  {
    return insumos;
  }
  
  public void setInsumos(Set insumos)
  {
    this.insumos = insumos;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
