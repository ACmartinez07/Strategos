package com.visiongc.app.strategos.plancuentas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GrupoMascaraCodigoPlanCuentasPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long mascaraId;
  private Integer nivel;
  
  public GrupoMascaraCodigoPlanCuentasPK(Long mascaraId, Integer nivel)
  {
    this.mascaraId = mascaraId;
    this.nivel = nivel;
  }
  

  public GrupoMascaraCodigoPlanCuentasPK() {}
  
  public Long getMascaraId()
  {
    return mascaraId;
  }
  
  public void setMascaraId(Long mascaraId) {
    this.mascaraId = mascaraId;
  }
  
  public Integer getNivel() {
    return nivel;
  }
  
  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("mascaraId", getMascaraId()).append("nivel", getNivel()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof GrupoMascaraCodigoPlanCuentasPK))
      return false;
    GrupoMascaraCodigoPlanCuentasPK castOther = (GrupoMascaraCodigoPlanCuentasPK)other;
    return new EqualsBuilder().append(getMascaraId(), castOther.getMascaraId()).append(getNivel(), castOther.getNivel()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getMascaraId()).append(getNivel()).toHashCode();
  }
}
