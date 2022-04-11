package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorPlanPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long planId;
  private Long indicadorId;
  
  public IndicadorPlanPK(Long indicadorId, Long planId)
  {
    this.planId = planId;
    this.indicadorId = indicadorId;
  }
  

  public IndicadorPlanPK() {}
  

  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Long getPlanId()
  {
    return planId;
  }
  
  public void setPlanId(Long planId)
  {
    this.planId = planId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).append("planId", getPlanId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorPlanPK))
      return false;
    IndicadorPlanPK castOther = (IndicadorPlanPK)other;
    
    return new EqualsBuilder().append(getIndicadorId(), castOther.getIndicadorId()).append(getPlanId(), castOther.getPlanId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIndicadorId()).append(getPlanId()).toHashCode();
  }
}
