package com.visiongc.app.strategos.planes.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorEstadoPK implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private Long planId;
  private Long indicadorId;
  private Byte tipo;
  private Integer ano;
  private Integer periodo;
  
  public IndicadorEstadoPK() {}
  
  public Long getPlanId()
  {
    return planId;
  }
  
  public void setPlanId(Long planId) {
    this.planId = planId;
  }
  
  public Long getIndicadorId() {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public Integer getAno() {
    return ano;
  }
  
  public void setAno(Integer ano) {
    this.ano = ano;
  }
  
  public Integer getPeriodo() {
    return periodo;
  }
  
  public void setPeriodo(Integer periodo) {
    this.periodo = periodo;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).append("planId", getPlanId()).append("tipo", getTipo()).append("ano", getAno()).append("periodo", getPeriodo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorEstadoPK))
      return false;
    IndicadorEstadoPK castOther = (IndicadorEstadoPK)other;
    return new EqualsBuilder().append(getIndicadorId(), castOther.getIndicadorId()).append(getPlanId(), castOther.getPlanId()).append(getTipo(), castOther.getTipo()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIndicadorId()).append(getPlanId()).append(getTipo()).append(getAno()).append(getPeriodo()).toHashCode();
  }
}
