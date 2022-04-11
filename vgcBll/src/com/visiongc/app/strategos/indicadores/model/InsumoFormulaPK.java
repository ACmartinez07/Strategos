package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class InsumoFormulaPK implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long padreId;
  private Long indicadorId;
  private Long serieId;
  private Long insumoSerieId;
  
  public InsumoFormulaPK(Long padreId, Long indicadorId, Long serieId, Long insumoSerieId)
  {
    this.padreId = padreId;
    this.indicadorId = indicadorId;
    this.serieId = serieId;
  }
  

  public InsumoFormulaPK() {}
  

  public Long getPadreId()
  {
    return padreId;
  }
  
  public void setPadreId(Long padreId)
  {
    this.padreId = padreId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Long getSerieId()
  {
    return serieId;
  }
  
  public void setSerieId(Long serieId)
  {
    this.serieId = serieId;
  }
  
  public Long getInsumoSerieId()
  {
    return insumoSerieId;
  }
  
  public void setInsumoSerieId(Long insumoSerieId)
  {
    this.insumoSerieId = insumoSerieId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("padreId", getPadreId()).append("indicadorId", getIndicadorId()).append("serieId", getSerieId()).append("insumoSerieId", getInsumoSerieId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) return true;
    if (!(other instanceof InsumoFormulaPK)) return false;
    InsumoFormulaPK castOther = (InsumoFormulaPK)other;
    
    return new EqualsBuilder().append(getPadreId(), castOther.getPadreId()).append(getIndicadorId(), castOther.getIndicadorId()).append(getSerieId(), castOther.getSerieId()).append(getInsumoSerieId(), castOther.getInsumoSerieId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPadreId()).append(getIndicadorId()).append(getSerieId()).append(getInsumoSerieId()).toHashCode();
  }
}
