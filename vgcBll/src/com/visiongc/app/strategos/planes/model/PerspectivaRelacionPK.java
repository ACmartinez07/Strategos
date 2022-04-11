package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;








public class PerspectivaRelacionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long relacionId;
  
  public PerspectivaRelacionPK(Long perspectivaId, Long relacionId)
  {
    this.perspectivaId = perspectivaId;
    this.relacionId = relacionId;
  }
  

  public PerspectivaRelacionPK() {}
  

  public Long getPerspectivaId()
  {
    return perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId)
  {
    this.perspectivaId = perspectivaId;
  }
  
  public Long getRelacionId()
  {
    return relacionId;
  }
  
  public void setRelacionId(Long relacionId)
  {
    this.relacionId = relacionId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).append("relacionId", getRelacionId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) return true;
    if (!(other instanceof PerspectivaRelacionPK)) return false;
    PerspectivaRelacionPK castOther = (PerspectivaRelacionPK)other;
    
    return new EqualsBuilder().append(getPerspectivaId(), castOther.getPerspectivaId()).append(getRelacionId(), castOther.getRelacionId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPerspectivaId()).append(getRelacionId()).toHashCode();
  }
}
