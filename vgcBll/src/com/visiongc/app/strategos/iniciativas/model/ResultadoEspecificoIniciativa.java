package com.visiongc.app.strategos.iniciativas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ResultadoEspecificoIniciativa
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private ResultadoEspecificoIniciativaPK pk;
  private String resultado;
  private Iniciativa iniciativa;
  
  public ResultadoEspecificoIniciativa(ResultadoEspecificoIniciativaPK pk, String resultado)
  {
    this.pk = pk;
    this.resultado = resultado;
  }
  

  public ResultadoEspecificoIniciativa() {}
  

  public ResultadoEspecificoIniciativaPK getPk()
  {
    return pk;
  }
  
  public void setPk(ResultadoEspecificoIniciativaPK pk) {
    this.pk = pk;
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
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof ResultadoEspecificoIniciativa))
      return false;
    ResultadoEspecificoIniciativa castOther = (ResultadoEspecificoIniciativa)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk())
      .isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
