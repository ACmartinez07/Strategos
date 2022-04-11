package com.visiongc.app.strategos.responsables.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GrupoResponsablePK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long padreId;
  private Long responsableId;
  
  public GrupoResponsablePK(Long padreId, Long responsableId)
  {
    this.padreId = padreId;
    this.responsableId = responsableId;
  }
  

  public GrupoResponsablePK() {}
  
  public Long getPadreId()
  {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Long getResponsableId() {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("padreId", getPadreId()).append("responsableId", getResponsableId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof GrupoResponsablePK))
      return false;
    GrupoResponsablePK castOther = (GrupoResponsablePK)other;
    return new EqualsBuilder().append(getPadreId(), castOther.getPadreId()).append(getResponsableId(), castOther.getResponsableId()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPadreId()).append(getResponsableId()).toHashCode();
  }
}
