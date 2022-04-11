package com.visiongc.app.strategos.iniciativas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;








public class IndicadorIniciativaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Long indicadorId;
  
  public IndicadorIniciativaPK(Long iniciativaId, Long indicadorId)
  {
    this.iniciativaId = iniciativaId;
    this.indicadorId = indicadorId;
  }
  

  public IndicadorIniciativaPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorIniciativaPK))
      return false;
    IndicadorIniciativaPK castOther = (IndicadorIniciativaPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getIndicadorId(), castOther.getIndicadorId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIniciativaId()).append(getIndicadorId()).toHashCode();
  }
}
