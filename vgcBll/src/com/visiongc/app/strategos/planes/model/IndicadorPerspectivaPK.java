package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorPerspectivaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long indicadorId;
  
  public IndicadorPerspectivaPK(Long perspectivaId, Long indicadorId)
  {
    this.perspectivaId = perspectivaId;
    this.indicadorId = indicadorId;
  }
  

  public IndicadorPerspectivaPK() {}
  

  public Long getPerspectivaId()
  {
    return perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }
  
  public Long getIndicadorId() {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorPerspectivaPK))
      return false;
    IndicadorPerspectivaPK castOther = (IndicadorPerspectivaPK)other;
    return new EqualsBuilder().append(getPerspectivaId(), castOther.getPerspectivaId()).append(getIndicadorId(), castOther.getIndicadorId()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPerspectivaId()).append(getIndicadorId()).toHashCode();
  }
}
