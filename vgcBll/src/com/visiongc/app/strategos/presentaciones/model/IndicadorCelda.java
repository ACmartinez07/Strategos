package com.visiongc.app.strategos.presentaciones.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorCelda
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IndicadorCeldaPK pk;
  private Long planId;
  private String nombreIndicador;
  private String nombreSerie;
  private Indicador indicador;
  private SerieTiempo serie;
  private Celda celda;
  
  public IndicadorCelda(IndicadorCeldaPK pk, String serieColor, Byte serieEstilo, String leyenda, Long planId)
  {
    this.pk = pk;
    this.planId = planId;
  }
  

  public IndicadorCelda() {}
  

  public IndicadorCeldaPK getPk()
  {
    return pk;
  }
  
  public void setPk(IndicadorCeldaPK pk) {
    this.pk = pk;
  }
  
  public Long getPlanId() {
    return planId;
  }
  
  public void setPlanId(Long planId) {
    this.planId = planId;
  }
  
  public String getNombreIndicador() {
    return nombreIndicador;
  }
  
  public void setNombreIndicador(String nombreIndicador) {
    this.nombreIndicador = nombreIndicador;
  }
  
  public String getNombreSerie() {
    return nombreSerie;
  }
  
  public void setNombreSerie(String nombreSerie) {
    this.nombreSerie = nombreSerie;
  }
  
  public Indicador getIndicador() {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador) {
    this.indicador = indicador;
  }
  
  public SerieTiempo getSerie() {
    return serie;
  }
  
  public void setSerie(SerieTiempo serie) {
    this.serie = serie;
  }
  
  public Celda getCelda() {
    return celda;
  }
  
  public void setCelda(Celda celda) {
    this.celda = celda;
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IndicadorCelda))
      return false;
    IndicadorCelda castOther = (IndicadorCelda)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk())
      .isEquals();
  }
}
