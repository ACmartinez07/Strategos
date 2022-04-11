package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoSeguimientoPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long seguimientoId;
  private Byte tipo;
  
  public MemoSeguimientoPK(Long seguimientoId, Byte tipo)
  {
    this.seguimientoId = seguimientoId;
    this.tipo = tipo;
  }
  

  public MemoSeguimientoPK() {}
  

  public Long getSeguimientoId()
  {
    return seguimientoId;
  }
  
  public void setSeguimientoId(Long seguimientoId)
  {
    this.seguimientoId = seguimientoId;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = tipo;
  }
  
  public int compareTo(Object o)
  {
    MemoSeguimientoPK or = (MemoSeguimientoPK)o;
    return getSeguimientoId().compareTo(or.getSeguimientoId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("seguimientoId", getSeguimientoId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemoSeguimientoPK other = (MemoSeguimientoPK)obj;
    
    return new EqualsBuilder().append(getSeguimientoId(), other.getSeguimientoId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getSeguimientoId()).append(getTipo()).toHashCode();
  }
}
