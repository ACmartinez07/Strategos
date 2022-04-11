package com.visiongc.app.strategos.explicaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AdjuntoExplicacionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long explicacionId;
  private Long adjuntoId;
  
  public AdjuntoExplicacionPK(Long explicacionId, Long adjuntoId)
  {
    this.explicacionId = explicacionId;
    this.adjuntoId = adjuntoId;
  }
  

  public AdjuntoExplicacionPK() {}
  
  public Long getExplicacionId()
  {
    return explicacionId;
  }
  
  public void setExplicacionId(Long explicacionId) {
    this.explicacionId = explicacionId;
  }
  
  public Long getAdjuntoId() {
    return adjuntoId;
  }
  
  public void setAdjuntoId(Long adjuntoId) {
    this.adjuntoId = adjuntoId;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("explicacionId", getExplicacionId()).append("adjuntoId", getAdjuntoId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof AdjuntoExplicacionPK))
      return false;
    AdjuntoExplicacionPK castOther = (AdjuntoExplicacionPK)other;
    return new EqualsBuilder().append(getExplicacionId(), castOther.getExplicacionId()).append(getAdjuntoId(), castOther.getAdjuntoId()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getExplicacionId()).append(getAdjuntoId()).toHashCode();
  }
}
