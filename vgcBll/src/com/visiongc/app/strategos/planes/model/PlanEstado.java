package com.visiongc.app.strategos.planes.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PlanEstado implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private PlanEstadoPK pk;
  private Double estado;
  private Integer totalConValor;
  private Integer total;
  
  public PlanEstado() {}
  
  public PlanEstadoPK getPk()
  {
    return pk;
  }
  
  public void setPk(PlanEstadoPK pk) {
    this.pk = pk;
  }
  
  public Double getEstado() {
    return estado;
  }
  
  public void setEstado(Double estado) {
    this.estado = estado;
  }
  
  public Integer getTotalConValor() {
    return totalConValor;
  }
  
  public void setTotalConValor(Integer totalConValor) {
    this.totalConValor = totalConValor;
  }
  
  public Integer getTotal() {
    return total;
  }
  
  public void setTotal(Integer total) {
    this.total = total;
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof PlanEstado))
      return false;
    PlanEstado castOther = (PlanEstado)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
