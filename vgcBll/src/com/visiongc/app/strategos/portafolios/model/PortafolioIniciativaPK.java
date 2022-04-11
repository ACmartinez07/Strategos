package com.visiongc.app.strategos.portafolios.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;









public class PortafolioIniciativaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Long portafolioId;
  
  public PortafolioIniciativaPK(Long iniciativaId, Long portafolioId)
  {
    this.iniciativaId = iniciativaId;
    this.portafolioId = portafolioId;
  }
  

  public PortafolioIniciativaPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
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
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("portafolioId", getPortafolioId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof PortafolioIniciativaPK))
      return false;
    PortafolioIniciativaPK castOther = (PortafolioIniciativaPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getPortafolioId(), castOther.getPortafolioId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIniciativaId()).append(getPortafolioId()).toHashCode();
  }
}
