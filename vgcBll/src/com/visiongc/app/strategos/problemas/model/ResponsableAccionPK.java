package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ResponsableAccionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long accionId;
  private Long responsableId;
  
  public ResponsableAccionPK(Long accionId, Long responsableId)
  {
    this.accionId = accionId;
    this.responsableId = responsableId;
  }
  

  public ResponsableAccionPK() {}
  

  public Long getAccionId()
  {
    return accionId;
  }
  
  public void setAccionId(Long accionId)
  {
    this.accionId = accionId;
  }
  
  public Long getResponsableId()
  {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId)
  {
    this.responsableId = responsableId;
  }
  
  public int compareTo(Object o)
  {
    ResponsableAccionPK or = (ResponsableAccionPK)o;
    return getAccionId().compareTo(or.getAccionId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("accionId", getAccionId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) {
      return false;
    }
    ResponsableAccionPK other = (ResponsableAccionPK)obj;
    
    return new EqualsBuilder().append(getAccionId(), other.getAccionId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getAccionId()).append(getResponsableId()).toHashCode();
  }
}
