package com.visiongc.app.strategos.explicaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoExplicacionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long explicacionId;
  private Byte tipo;
  
  public MemoExplicacionPK(Long explicacionId, Byte tipo)
  {
    this.explicacionId = explicacionId;
    this.tipo = tipo;
  }
  

  public MemoExplicacionPK() {}
  
  public Long getExplicacionId()
  {
    return explicacionId;
  }
  
  public void setExplicacionId(Long explicacionId) {
    this.explicacionId = explicacionId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("explicacionId", getExplicacionId()).append("tipo", getTipo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoExplicacionPK))
      return false;
    MemoExplicacionPK castOther = (MemoExplicacionPK)other;
    return new EqualsBuilder().append(getExplicacionId(), castOther.getExplicacionId()).append(getTipo(), castOther.getTipo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getExplicacionId()).append(getTipo()).toHashCode();
  }
}
