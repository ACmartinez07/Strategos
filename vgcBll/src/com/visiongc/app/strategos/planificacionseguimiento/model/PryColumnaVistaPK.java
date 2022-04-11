package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryColumnaVistaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long columnaId;
  private Long vistaId;
  
  public PryColumnaVistaPK(Long columnaId, Long vistaId)
  {
    this.columnaId = columnaId;
    this.vistaId = vistaId;
  }
  

  public PryColumnaVistaPK() {}
  

  public Long getColumnaId()
  {
    return columnaId;
  }
  
  public void setColumnaId(Long columnaId) {
    this.columnaId = columnaId;
  }
  
  public Long getVistaId() {
    return vistaId;
  }
  
  public void setVistaId(Long vistaId) {
    this.vistaId = vistaId;
  }
  
  public String toString() {
    return 
      new ToStringBuilder(this).append("columnaId", getColumnaId()).append("vistaId", getVistaId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof PryColumnaVistaPK))
      return false;
    PryColumnaVistaPK castOther = (PryColumnaVistaPK)other;
    return new EqualsBuilder().append(getColumnaId(), 
      castOther.getColumnaId()).append(getVistaId(), 
      castOther.getVistaId()).isEquals();
  }
  
  public int hashCode() {
    return 
      new HashCodeBuilder().append(getColumnaId()).append(getVistaId()).toHashCode();
  }
}
