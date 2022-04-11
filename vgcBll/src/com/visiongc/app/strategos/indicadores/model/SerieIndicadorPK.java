package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SerieIndicadorPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long serieId;
  private Long indicadorId;
  
  public SerieIndicadorPK(Long serieId, Long indicadorId)
  {
    this.serieId = serieId;
    this.indicadorId = indicadorId;
  }
  

  public SerieIndicadorPK() {}
  

  public Long getSerieId()
  {
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
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("serieId", getSerieId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof SerieIndicadorPK)) return false;
    SerieIndicadorPK castOther = (SerieIndicadorPK)other;
    return new EqualsBuilder()
      .append(getSerieId(), castOther.getSerieId())
      .append(getIndicadorId(), castOther.getIndicadorId())
      .isEquals();
  }
  
  public int hashCode() {
    return 
    

      new HashCodeBuilder().append(getSerieId()).append(getIndicadorId()).toHashCode();
  }
}
