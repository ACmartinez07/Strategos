package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryCalendario implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private Long calendarioId;
  private Long proyectoId;
  private String nombre;
  private Boolean domingo;
  private Boolean lunes;
  private Boolean martes;
  private Boolean miercoles;
  private Boolean jueves;
  private Boolean viernes;
  private Boolean sabado;
  private Set excepciones;
  private Map mapExcepciones;
  
  public PryCalendario() {}
  
  public Long getCalendarioId()
  {
    return calendarioId;
  }
  
  public void setCalendarioId(Long calendarioId) {
    this.calendarioId = calendarioId;
  }
  
  public Long getProyectoId() {
    return proyectoId;
  }
  
  public void setProyectoId(Long proyectoId) {
    this.proyectoId = proyectoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Boolean getDomingo() {
    return domingo;
  }
  
  public void setDomingo(Boolean domingo) {
    this.domingo = domingo;
  }
  
  public Boolean getLunes() {
    return lunes;
  }
  
  public void setLunes(Boolean lunes) {
    this.lunes = lunes;
  }
  
  public Boolean getMartes() {
    return martes;
  }
  
  public void setMartes(Boolean martes) {
    this.martes = martes;
  }
  
  public Boolean getMiercoles() {
    return miercoles;
  }
  
  public void setMiercoles(Boolean miercoles) {
    this.miercoles = miercoles;
  }
  
  public Boolean getJueves() {
    return jueves;
  }
  
  public void setJueves(Boolean jueves) {
    this.jueves = jueves;
  }
  
  public Boolean getViernes() {
    return viernes;
  }
  
  public void setViernes(Boolean viernes) {
    this.viernes = viernes;
  }
  
  public Boolean getSabado() {
    return sabado;
  }
  
  public void setSabado(Boolean sabado) {
    this.sabado = sabado;
  }
  
  public Set getExcepciones() {
    return excepciones;
  }
  
  public void setExcepciones(Set excepciones) {
    this.excepciones = excepciones;
  }
  
  public Map getMapExcepciones() {
    return mapExcepciones;
  }
  
  public void setMapExcepciones(Map mapExcepciones) {
    this.mapExcepciones = mapExcepciones;
  }
  
  public int compareTo(Object o) {
    PryCalendario or = (PryCalendario)o;
    return getCalendarioId().compareTo(or.getCalendarioId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryCalendario other = (PryCalendario)obj;
    if (calendarioId == null) {
      if (calendarioId != null)
        return false;
    } else if (!calendarioId.equals(calendarioId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("calendarioId", getCalendarioId()).toString();
  }
}
