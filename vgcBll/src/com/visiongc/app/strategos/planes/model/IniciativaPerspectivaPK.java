package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IniciativaPerspectivaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long iniciativaId;
  
  public IniciativaPerspectivaPK(Long iniciativaId, Long perspectivaId)
  {
    this.iniciativaId = iniciativaId;
    this.perspectivaId = perspectivaId;
  }
  

  public IniciativaPerspectivaPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }
  
  public Long getPerspectivaId() {
    return perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("perspectivaId", getPerspectivaId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IniciativaPerspectivaPK))
      return false;
    IniciativaPerspectivaPK castOther = (IniciativaPerspectivaPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getPerspectivaId(), castOther.getPerspectivaId()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIniciativaId()).append(getPerspectivaId()).toHashCode();
  }
}
