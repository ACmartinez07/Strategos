package com.visiongc.app.strategos.graficos.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;








public class DuppontPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long usuarioId;
  private Long indicadorId;
  
  public DuppontPK(Long usuarioId, Long indicadorId)
  {
    this.usuarioId = usuarioId;
    this.indicadorId = indicadorId;
  }
  

  public DuppontPK() {}
  

  public Long getUsuarioId()
  {
    return usuarioId;
  }
  
  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("usuarioId", getUsuarioId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) return true;
    if (!(other instanceof DuppontPK)) return false;
    DuppontPK castOther = (DuppontPK)other;
    return new EqualsBuilder().append(getUsuarioId(), castOther.getUsuarioId()).append(getIndicadorId(), castOther.getIndicadorId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getUsuarioId()).append(getIndicadorId()).toHashCode();
  }
}
