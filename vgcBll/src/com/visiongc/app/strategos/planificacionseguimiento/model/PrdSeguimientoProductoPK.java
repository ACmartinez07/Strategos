package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrdSeguimientoProductoPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Long productoId;
  private Integer ano;
  private Integer periodo;
  
  public PrdSeguimientoProductoPK(Long iniciativaId, Long productoId, Integer ano, Integer periodo)
  {
    this.iniciativaId = iniciativaId;
    this.productoId = productoId;
    this.ano = ano;
    this.periodo = periodo;
  }
  

  public PrdSeguimientoProductoPK() {}
  

  public Integer getAno()
  {
    return ano;
  }
  
  public void setAno(Integer ano) {
    this.ano = ano;
  }
  
  public Long getIniciativaId() {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }
  
  public Integer getPeriodo() {
    return periodo;
  }
  
  public void setPeriodo(Integer periodo) {
    this.periodo = periodo;
  }
  
  public Long getProductoId() {
    return productoId;
  }
  
  public void setProductoId(Long productoId) {
    this.productoId = productoId;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("productoId", getProductoId()).append("ano", getAno()).append("periodo", getPeriodo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof PrdSeguimientoProductoPK))
      return false;
    PrdSeguimientoProductoPK castOther = (PrdSeguimientoProductoPK)other;
    return new EqualsBuilder().append(getIniciativaId(), castOther.getIniciativaId()).append(getProductoId(), castOther.getProductoId()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIniciativaId()).append(getProductoId()).append(getAno()).append(getPeriodo()).toHashCode();
  }
}
