package com.visiongc.app.strategos.iniciativas.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;



public class IndicadorIniciativa
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorIniciativaPK pk;
  private Byte tipo;
  private Iniciativa iniciativa;
  private Indicador indicador;
  
  public IndicadorIniciativa(IndicadorIniciativaPK pk, Byte tipo)
  {
    this.pk = pk;
    this.tipo = tipo;
  }
  

  public IndicadorIniciativa() {}
  

  public IndicadorIniciativaPK getPk()
  {
    return pk;
  }
  
  public void setPk(IndicadorIniciativaPK pk)
  {
    this.pk = pk;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = tipo;
  }
  
  public Iniciativa getIniciativa()
  {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa)
  {
    this.iniciativa = iniciativa;
  }
  
  public Indicador getIndicador()
  {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorIniciativa))
      return false;
    IndicadorIniciativa castOther = (IndicadorIniciativa)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
