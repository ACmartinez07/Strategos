package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PerspectivaEstadoPK implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Byte tipo;
  private Integer ano;
  private Integer periodo;
  
  public PerspectivaEstadoPK() {}
  
  public Long getPerspectivaId()
  {
    return perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public String getTipoNombre() {
    if (tipo == null) {
      return "";
    }
    return com.visiongc.app.strategos.planes.model.util.TipoPerspectivaEstado.getNombre(tipo.byteValue());
  }
  
  public void setTipo(Byte tipo)
  {
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
    return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).append("tipo", getTipo()).append("ano", getAno()).append("periodo", getPeriodo()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof PerspectivaEstadoPK))
      return false;
    PerspectivaEstadoPK castOther = (PerspectivaEstadoPK)other;
    return new EqualsBuilder().append(getPerspectivaId(), castOther.getPerspectivaId()).append(getTipo(), castOther.getTipo()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPerspectivaId()).append(getTipo()).append(getAno()).append(getPeriodo()).toHashCode();
  }
}
