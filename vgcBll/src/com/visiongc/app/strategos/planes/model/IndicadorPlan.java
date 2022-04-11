package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorPlan
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorPlanPK pk;
  private Double peso;
  private Plan plan;
  private Indicador indicador;
  private Byte crecimiento;
  private Byte tipoMedicion;
  
  public IndicadorPlan(IndicadorPlanPK pk, Double peso, Indicador indicador, Plan plan)
  {
    this.pk = pk;
    this.peso = peso;
    this.plan = plan;
    this.indicador = indicador;
  }
  

  public IndicadorPlan() {}
  

  public Indicador getIndicador()
  {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public Double getPeso()
  {
    return peso;
  }
  
  public void setPeso(Double peso)
  {
    this.peso = peso;
  }
  
  public void setPk(IndicadorPlanPK pk)
  {
    this.pk = pk;
  }
  
  public IndicadorPlanPK getPk()
  {
    return pk;
  }
  
  public Plan getPlan()
  {
    return plan;
  }
  
  public void setPlan(Plan plan)
  {
    this.plan = plan;
  }
  
  public Byte getCrecimiento()
  {
    return crecimiento;
  }
  
  public void setCrecimiento(Byte crecimiento)
  {
    this.crecimiento = crecimiento;
  }
  
  public Byte getTipoMedicion()
  {
    return tipoMedicion;
  }
  
  public void setTipoMedicion(Byte tipoMedicion)
  {
    this.tipoMedicion = tipoMedicion;
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorPlan))
      return false;
    IndicadorPlan castOther = (IndicadorPlan)other;
    
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
}
