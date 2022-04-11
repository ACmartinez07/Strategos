package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;








public class IniciativaPlanPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Long planId;
  
  public IniciativaPlanPK(Long iniciativaId, Long planId)
  {
    this.iniciativaId = iniciativaId;
    this.planId = planId;
  }
  

  public IniciativaPlanPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
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
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("planId", getPlanId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IniciativaPlanPK)) {
      return false;
    }
    IniciativaPlanPK castOther = (IniciativaPlanPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getPlanId(), castOther.getPlanId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIniciativaId()).append(getPlanId()).toHashCode();
  }
}
