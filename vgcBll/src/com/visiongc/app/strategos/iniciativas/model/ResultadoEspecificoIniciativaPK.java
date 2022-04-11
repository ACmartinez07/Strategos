package com.visiongc.app.strategos.iniciativas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ResultadoEspecificoIniciativaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private Integer ano;
  
  public ResultadoEspecificoIniciativaPK(Long iniciativaId, Integer ano)
  {
    this.iniciativaId = iniciativaId;
    this.ano = ano;
  }
  

  public ResultadoEspecificoIniciativaPK() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }
  
  public Integer getAno() {
    return ano;
  }
  
  public void setAno(Integer ano) {
    this.ano = ano;
  }
  
  public String toString() {
    return 
      new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).append("ano", getAno()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof ResultadoEspecificoIniciativaPK))
      return false;
    ResultadoEspecificoIniciativaPK castOther = (ResultadoEspecificoIniciativaPK)other;
    return new EqualsBuilder().append(getIniciativaId(), 
      castOther.getIniciativaId()).append(getAno(), 
      castOther.getAno()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIniciativaId()).append(getAno()).toHashCode();
  }
}
