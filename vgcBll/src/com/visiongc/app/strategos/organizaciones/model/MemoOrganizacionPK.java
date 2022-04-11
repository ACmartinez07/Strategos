package com.visiongc.app.strategos.organizaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoOrganizacionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long organizacionId;
  private Integer tipo;
  
  public MemoOrganizacionPK(Long organizacionId, Integer tipo)
  {
    this.organizacionId = organizacionId;
    this.tipo = tipo;
  }
  

  public MemoOrganizacionPK() {}
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public Integer getTipo() {
    return tipo;
  }
  
  public void setTipo(Integer tipo) {
    this.tipo = tipo;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("organizacionId", getOrganizacionId()).append("tipo", getTipo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoOrganizacionPK))
      return false;
    MemoOrganizacionPK castOther = (MemoOrganizacionPK)other;
    return new EqualsBuilder().append(getOrganizacionId(), castOther.getOrganizacionId()).append(getTipo(), castOther.getTipo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getOrganizacionId()).append(getTipo()).toHashCode();
  }
}
