package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorPerspectiva
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorPerspectivaPK pk;
  private Double peso;
  private Perspectiva perspectiva;
  private Indicador indicador;
  
  public IndicadorPerspectiva(IndicadorPerspectivaPK pk, Double peso, Indicador indicador, Perspectiva perspectiva)
  {
    this.pk = pk;
    this.peso = peso;
    this.indicador = indicador;
    this.perspectiva = perspectiva;
  }
  

  public IndicadorPerspectiva() {}
  

  public IndicadorPerspectiva(IndicadorPerspectivaPK pk)
  {
    this.pk = pk;
  }
  
  public IndicadorPerspectivaPK getPk() {
    return pk;
  }
  
  public void setPk(IndicadorPerspectivaPK pk) {
    this.pk = pk;
  }
  
  public Double getPeso() {
    return peso;
  }
  
  public void setPeso(Double peso) {
    this.peso = peso;
  }
  
  public Indicador getIndicador() {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador) {
    this.indicador = indicador;
  }
  
  public Perspectiva getPerspectiva() {
    return perspectiva;
  }
  
  public void setPerspectiva(Perspectiva perspectiva) {
    this.perspectiva = perspectiva;
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorPerspectiva))
      return false;
    IndicadorPerspectiva castOther = (IndicadorPerspectiva)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
}
