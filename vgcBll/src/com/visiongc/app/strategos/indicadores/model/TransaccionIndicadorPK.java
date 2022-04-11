package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;









public class TransaccionIndicadorPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long transaccionId;
  private Long indicadorId;
  private String campo;
  
  public TransaccionIndicadorPK(Long transaccionId, Long indicadorId, String campo)
  {
    this.transaccionId = transaccionId;
    this.indicadorId = indicadorId;
    this.campo = campo;
  }
  

  public TransaccionIndicadorPK() {}
  

  public Long getTransaccionId()
  {
    return transaccionId;
  }
  
  public void setTransaccionId(Long transaccionId)
  {
    this.transaccionId = transaccionId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public String getCampo()
  {
    return campo;
  }
  
  public void setCampo(String campo)
  {
    this.campo = campo;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("transaccionId", getTransaccionId()).append("indicadorId", getIndicadorId()).append("camnpo", getCampo()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof TransaccionIndicadorPK))
      return false;
    TransaccionIndicadorPK castOther = (TransaccionIndicadorPK)other;
    return new EqualsBuilder().append(getTransaccionId(), castOther.getTransaccionId()).append(getIndicadorId(), castOther.getIndicadorId()).append(getCampo(), castOther.getCampo()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getTransaccionId()).append(getIndicadorId()).append(getCampo()).toHashCode();
  }
}
