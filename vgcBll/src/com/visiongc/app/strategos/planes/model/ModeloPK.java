package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;









public class ModeloPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long modeloId;
  private Long planId;
  
  public ModeloPK(Long modeloId, Long planId)
  {
    this.modeloId = modeloId;
    this.planId = planId;
  }
  

  public ModeloPK() {}
  

  public Long getModeloId()
  {
    return modeloId;
  }
  
  public void setModeloId(Long modeloId)
  {
    this.modeloId = modeloId;
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
    return new ToStringBuilder(this).append("modeloId", getModeloId()).append("planId", getPlanId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof ModeloPK))
      return false;
    ModeloPK castOther = (ModeloPK)other;
    
    return new EqualsBuilder().append(getModeloId(), castOther.getModeloId()).append(getPlanId(), castOther.getPlanId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getModeloId()).append(getPlanId()).toHashCode();
  }
}
