package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class InstrumentoIniciativaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Long instrumentoId;
  
  public InstrumentoIniciativaPK(Long iniciativaId, Long instrumentoId)
  {
    this.iniciativaId = iniciativaId;
    this.instrumentoId = instrumentoId;
  }
  

  public InstrumentoIniciativaPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
  }
  
  public Long getInstrumentoId()
  {
    return instrumentoId;
  }
  
  public void setInstrumentoId(Long instrumentoId)
  {
    this.instrumentoId = instrumentoId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("instrumentoId", getInstrumentoId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof InstrumentoIniciativaPK))
      return false;
    InstrumentoIniciativaPK castOther = (InstrumentoIniciativaPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getInstrumentoId(), castOther.getInstrumentoId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIniciativaId()).append(getInstrumentoId()).toHashCode();
  }
}
