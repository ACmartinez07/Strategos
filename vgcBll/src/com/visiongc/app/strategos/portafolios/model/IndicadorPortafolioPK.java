package com.visiongc.app.strategos.portafolios.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;









public class IndicadorPortafolioPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long indicadorId;
  private Long portafolioId;
  
  public IndicadorPortafolioPK(Long indicadorId, Long portafolioId)
  {
    this.indicadorId = indicadorId;
    this.portafolioId = portafolioId;
  }
  

  public IndicadorPortafolioPK() {}
  

  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Long getPortafolioId()
  {
    return portafolioId;
  }
  
  public void setPortafolioId(Long portafolioId)
  {
    this.portafolioId = portafolioId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).append("portafolioId", getPortafolioId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof PortafolioIniciativaPK))
      return false;
    IndicadorPortafolioPK castOther = (IndicadorPortafolioPK)other;
    return new EqualsBuilder().append(getIndicadorId(), castOther.getIndicadorId()).append(getPortafolioId(), castOther.getPortafolioId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIndicadorId()).append(getPortafolioId()).toHashCode();
  }
}
