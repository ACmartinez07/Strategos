package com.visiongc.app.strategos.estadosacciones.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EstadoAcciones
{
  private Long estadoId;
  private String nombre;
  private Boolean aplicaSeguimiento;
  private Integer orden;
  private Boolean condicion;
  
  public EstadoAcciones(Long estadoId, String nombre, Boolean aplica_seguimiento, Integer orden, Boolean condicion)
  {
    this.estadoId = estadoId;
    this.nombre = nombre;
    aplicaSeguimiento = aplica_seguimiento;
    this.orden = orden;
    this.condicion = condicion;
  }
  

  public EstadoAcciones() {}
  

  public Long getEstadoId()
  {
    return estadoId;
  }
  
  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Boolean getAplicaSeguimiento() {
    return aplicaSeguimiento;
  }
  
  public void setAplicaSeguimiento(Boolean aplicaSeguimiento) {
    this.aplicaSeguimiento = aplicaSeguimiento;
  }
  
  public Integer getOrden() {
    return orden;
  }
  
  public void setOrden(Integer orden) {
    this.orden = orden;
  }
  
  public Boolean getCondicion() {
    return condicion;
  }
  
  public void setCondicion(Boolean condicion) {
    this.condicion = condicion;
  }
  
  public int compareTo(Object o) {
    EstadoAcciones or = (EstadoAcciones)o;
    return getEstadoId().compareTo(or.getEstadoId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("estadoId", getEstadoId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EstadoAcciones other = (EstadoAcciones)obj;
    return new EqualsBuilder().append(getEstadoId(), other.getEstadoId()).isEquals();
  }
}
