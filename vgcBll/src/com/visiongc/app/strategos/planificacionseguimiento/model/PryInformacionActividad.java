package com.visiongc.app.strategos.planificacionseguimiento.model;

import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


public class PryInformacionActividad
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long actividadId;
  private String productoEsperado;
  private String recursosHumanos;
  private String recursosMateriales;
  private Double peso;
  private Double porcentajeAmarillo;
  private Double porcentajeVerde;
  
  public PryInformacionActividad(Long actividadId, String productoEsperado, String recursosHumanos, String recursosMateriales, Double peso, Double porcentajeAmarillo, Double porcentajeVerde)
  {
    this.actividadId = actividadId;
    this.productoEsperado = productoEsperado;
    this.recursosHumanos = recursosHumanos;
    this.recursosMateriales = recursosMateriales;
    this.peso = peso;
    this.porcentajeAmarillo = porcentajeAmarillo;
    this.porcentajeVerde = porcentajeVerde;
  }
  

  public PryInformacionActividad() {}
  

  public Long getActividadId()
  {
    return actividadId;
  }
  
  public void setActividadId(Long actividadId)
  {
    this.actividadId = actividadId;
  }
  
  public String getProductoEsperado()
  {
    return productoEsperado;
  }
  
  public void setProductoEsperado(String productoEsperado)
  {
    this.productoEsperado = productoEsperado;
  }
  
  public String getRecursosHumanos()
  {
    return recursosHumanos;
  }
  
  public void setRecursosHumanos(String recursosHumanos)
  {
    this.recursosHumanos = recursosHumanos;
  }
  
  public String getRecursosMateriales()
  {
    return recursosMateriales;
  }
  
  public void setRecursosMateriales(String recursosMateriales)
  {
    this.recursosMateriales = recursosMateriales;
  }
  
  public Double getPeso()
  {
    return peso;
  }
  
  public void setPeso(Double peso)
  {
    this.peso = peso;
  }
  
  public String getPesoFormateado()
  {
    return VgcFormatter.formatearNumero(peso, 2);
  }
  
  public Double getPorcentajeAmarillo()
  {
    return porcentajeAmarillo;
  }
  
  public void setPorcentajeAmarillo(Double porcentajeAmarillo)
  {
    this.porcentajeAmarillo = porcentajeAmarillo;
  }
  
  public Double getPorcentajeVerde()
  {
    return porcentajeVerde;
  }
  
  public void setPorcentajeVerde(Double porcentajeVerde)
  {
    this.porcentajeVerde = porcentajeVerde;
  }
  
  public int compareTo(Object o)
  {
    PryActividad or = (PryActividad)o;
    return getActividadId().compareTo(or.getActividadId());
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryInformacionActividad other = (PryInformacionActividad)obj;
    if (actividadId == null)
    {
      if (actividadId != null) {
        return false;
      }
    } else if (!actividadId.equals(actividadId)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("actividadId", getActividadId()).toString();
  }
}
