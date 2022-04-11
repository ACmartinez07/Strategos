package com.visiongc.app.strategos.presentaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorCeldaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long celdaId;
  private Long serieId;
  private Long indicadorId;
  
  public IndicadorCeldaPK(Long celdaId, Long serieId, Long indicadorId)
  {
    this.celdaId = celdaId;
    this.serieId = serieId;
    this.indicadorId = indicadorId;
  }
  

  public IndicadorCeldaPK() {}
  

  public Long getCeldaId()
  {
    return celdaId;
  }
  
  public void setCeldaId(Long celdaId) {
    this.celdaId = celdaId;
  }
  
  public Long getSerieId() {
    return serieId;
  }
  
  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }
  
  public Long getIndicadorId() {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }
  
  public String toString() {
    return 
    
      new ToStringBuilder(this).append("celdaId", getCeldaId()).append("serieId", getSerieId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorCeldaPK))
      return false;
    IndicadorCeldaPK castOther = (IndicadorCeldaPK)other;
    return new EqualsBuilder().append(getCeldaId(), 
      castOther.getCeldaId()).append(getSerieId(), 
      castOther.getSerieId()).append(getIndicadorId(), 
      castOther.getIndicadorId()).isEquals();
  }
  
  public int hashCode() {
    return 
      new HashCodeBuilder().append(getCeldaId()).append(getSerieId()).append(getIndicadorId()).toHashCode();
  }
}
