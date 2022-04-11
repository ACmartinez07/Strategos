package com.visiongc.app.strategos.portafolios.model;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;










public class PortafolioIniciativa
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private PortafolioIniciativaPK pk;
  private Iniciativa iniciativa;
  private Portafolio portafolio;
  private List<Medicion> mediciones;
  private Double peso;
  
  public PortafolioIniciativa(PortafolioIniciativaPK pk)
  {
    this.pk = pk;
  }
  

  public PortafolioIniciativa() {}
  

  public PortafolioIniciativaPK getPk()
  {
    return pk;
  }
  
  public void setPk(PortafolioIniciativaPK pk)
  {
    this.pk = pk;
  }
  
  public Iniciativa getIniciativa()
  {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa)
  {
    this.iniciativa = iniciativa;
  }
  
  public Portafolio getPortafolio()
  {
    return portafolio;
  }
  
  public void setPortafolio(Portafolio portafolio)
  {
    this.portafolio = portafolio;
  }
  
  public List<Medicion> getMediciones()
  {
    return mediciones;
  }
  
  public void setMediciones(List<Medicion> mediciones)
  {
    this.mediciones = mediciones;
  }
  
  public Double getPeso()
  {
    return peso;
  }
  
  public void setPeso(Double peso)
  {
    this.peso = peso;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof PortafolioIniciativa))
      return false;
    PortafolioIniciativa castOther = (PortafolioIniciativa)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
