package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoProblemaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long problemaId;
  private Byte tipo;
  
  public MemoProblemaPK(Long problemaId, Byte tipo)
  {
    this.problemaId = problemaId;
    this.tipo = tipo;
  }
  

  public MemoProblemaPK() {}
  

  public Long getProblemaId()
  {
    return problemaId;
  }
  
  public void setProblemaId(Long problemaId)
  {
    this.problemaId = problemaId;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = tipo;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("problemaId", getProblemaId()).append("tipo", getTipo()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof MemoProblemaPK))
      return false;
    MemoProblemaPK castOther = (MemoProblemaPK)other;
    return new EqualsBuilder().append(getProblemaId(), castOther.getProblemaId()).append(getTipo(), castOther.getTipo()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getProblemaId()).append(getTipo()).toHashCode();
  }
}
