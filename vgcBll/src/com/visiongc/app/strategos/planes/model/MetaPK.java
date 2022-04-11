package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MetaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long planId;
  private Long indicadorId;
  private Long serieId;
  private Byte tipo;
  private Integer ano;
  private Integer periodo;
  
  public MetaPK(Long planId, Long indicadorId, Long serieId, Byte tipo, Integer ano, Integer periodo)
  {
    this.planId = planId;
    this.indicadorId = indicadorId;
    this.serieId = serieId;
    this.tipo = tipo;
    this.ano = ano;
    this.periodo = periodo;
  }
  

  public MetaPK() {}
  
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
  
  public Long getSerieId() {
    return serieId;
  }
  
  public void setSerieId(Long serieId) {
    this.serieId = serieId;
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
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).append("planId", getPlanId()).append("serieId", getSerieId()).append("tipo", getTipo()).append("ano", getAno()).append("periodo", getPeriodo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MetaPK))
      return false;
    MetaPK castOther = (MetaPK)other;
    return new EqualsBuilder().append(getIndicadorId(), castOther.getIndicadorId()).append(getPlanId(), castOther.getPlanId()).append(getSerieId(), castOther.getSerieId()).append(getTipo(), castOther.getTipo()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIndicadorId()).append(getPlanId()).append(getSerieId()).append(getTipo()).append(getAno()).append(getPeriodo()).toHashCode();
  }
}
