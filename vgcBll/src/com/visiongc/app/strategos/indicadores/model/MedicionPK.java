package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MedicionPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long indicadorId;
  private Integer ano;
  private Integer periodo;
  private Long serieId;
  
  public MedicionPK(Long indicadorId, Integer ano, Integer periodo, Long serieId)
  {
    this.indicadorId = indicadorId;
    this.ano = ano;
    this.periodo = periodo;
    this.serieId = serieId;
  }
  

  public MedicionPK() {}
  

  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Integer getAno()
  {
    return ano;
  }
  
  public void setAno(Integer ano)
  {
    this.ano = ano;
  }
  
  public Integer getPeriodo()
  {
    return periodo;
  }
  
  public void setPeriodo(Integer periodo)
  {
    this.periodo = periodo;
  }
  
  public Long getSerieId()
  {
    return serieId;
  }
  
  public void setSerieId(Long serieId)
  {
    this.serieId = serieId;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).append("ano", getAno()).append("periodo", getPeriodo()).append("serieId", getSerieId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof MedicionPK))
      return false;
    MedicionPK castOther = (MedicionPK)other;
    return new EqualsBuilder().append(getIndicadorId(), castOther.getIndicadorId()).append(getSerieId(), castOther.getSerieId()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getIndicadorId()).append(getAno()).append(getPeriodo()).append(getSerieId()).toHashCode();
  }
}
