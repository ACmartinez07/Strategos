package com.visiongc.app.strategos.portafolios.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;










public class IndicadorPortafolio
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorPortafolioPK pk;
  private Byte tipo;
  private Portafolio portafolio;
  private Indicador indicador;
  
  public IndicadorPortafolio(IndicadorPortafolioPK pk, Byte tipo)
  {
    this.pk = pk;
    this.tipo = tipo;
  }
  

  public IndicadorPortafolio() {}
  

  public IndicadorPortafolioPK getPk()
  {
    return pk;
  }
  
  public void setPk(IndicadorPortafolioPK pk)
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
  
  public Portafolio getPortafolio()
  {
    return portafolio;
  }
  
  public void setPortafolio(Portafolio portafolio)
  {
    this.portafolio = portafolio;
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
    if (!(other instanceof IndicadorPortafolio))
      return false;
    IndicadorPortafolio castOther = (IndicadorPortafolio)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
