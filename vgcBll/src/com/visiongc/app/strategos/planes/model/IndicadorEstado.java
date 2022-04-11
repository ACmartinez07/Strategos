package com.visiongc.app.strategos.planes.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorEstado implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorEstadoPK pk;
  private Double estado;
  
  public IndicadorEstado() {}
  
  public IndicadorEstadoPK getPk()
  {
    return pk;
  }
  
  public void setPk(IndicadorEstadoPK pk)
  {
    this.pk = pk;
  }
  
  public Double getEstado()
  {
    return estado;
  }
  
  public void setEstado(Double estado)
  {
    this.estado = estado;
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorEstado))
      return false;
    IndicadorEstado castOther = (IndicadorEstado)other;
    
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
