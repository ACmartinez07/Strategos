package com.visiongc.app.strategos.iniciativas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoIniciativa
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private String descripcion;
  private String resultado;
  private Iniciativa iniciativa;
  
  public MemoIniciativa(Long iniciativaId, String descripcion, String resultado)
  {
    this.iniciativaId = iniciativaId;
    this.descripcion = descripcion;
    this.resultado = resultado;
  }
  

  public MemoIniciativa() {}
  

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getResultado() {
    return resultado;
  }
  
  public void setResultado(String resultado) {
    this.resultado = resultado;
  }
  
  public Iniciativa getIniciativa() {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa) {
    this.iniciativa = iniciativa;
  }
  
  public String toString() {
    return 
      new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoIniciativa))
      return false;
    MemoIniciativa castOther = (MemoIniciativa)other;
    return new EqualsBuilder().append(getIniciativaId(), 
      castOther.getIniciativaId()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getIniciativaId()).toHashCode();
  }
}
