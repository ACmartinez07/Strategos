package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoriaIndicadorPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long categoriaId;
  private Long indicadorId;
  
  public CategoriaIndicadorPK(Long categoriaId, Long indicadorId)
  {
    this.categoriaId = categoriaId;
    this.indicadorId = indicadorId;
  }
  

  public CategoriaIndicadorPK() {}
  

  public Long getCategoriaId()
  {
    return categoriaId;
  }
  
  public void setCategoriaId(Long categoriaId) {
    this.categoriaId = categoriaId;
  }
  
  public Long getIndicadorId() {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("categoriaId", getCategoriaId()).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof CategoriaIndicadorPK)) return false;
    CategoriaIndicadorPK castOther = (CategoriaIndicadorPK)other;
    return new EqualsBuilder()
      .append(getCategoriaId(), castOther.getCategoriaId())
      .append(getIndicadorId(), castOther.getIndicadorId())
      .isEquals();
  }
  
  public int hashCode() {
    return 
    

      new HashCodeBuilder().append(getCategoriaId()).append(getIndicadorId()).toHashCode();
  }
}
