package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryProyecto implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private Long proyectoId;
  private String nombre;
  private Date comienzo;
  private Date comienzoPlan;
  private Date comienzoReal;
  private Date finPlan;
  private Date finReal;
  private Double duracionPlan;
  private Double duracionReal;
  private Double variacionComienzo;
  private Double variacionFin;
  private Double variacionDuracion;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private PryCalendario pryCalendario;
  
  public PryProyecto() {}
  
  public Date getComienzo()
  {
    return comienzo;
  }
  
  public void setComienzo(Date comienzo) {
    this.comienzo = comienzo;
  }
  
  public Date getComienzoPlan() {
    return comienzoPlan;
  }
  
  public void setComienzoPlan(Date comienzoPlan) {
    this.comienzoPlan = comienzoPlan;
  }
  
  public Date getComienzoReal() {
    return comienzoReal;
  }
  
  public void setComienzoReal(Date comienzoReal) {
    this.comienzoReal = comienzoReal;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public Double getDuracionPlan() {
    return duracionPlan;
  }
  
  public void setDuracionPlan(Double duracionPlan) {
    this.duracionPlan = duracionPlan;
  }
  
  public Double getDuracionReal() {
    return duracionReal;
  }
  
  public void setDuracionReal(Double duracionReal) {
    this.duracionReal = duracionReal;
  }
  
  public Date getFinPlan() {
    return finPlan;
  }
  
  public void setFinPlan(Date finPlan) {
    this.finPlan = finPlan;
  }
  
  public Date getFinReal() {
    return finReal;
  }
  
  public void setFinReal(Date finReal) {
    this.finReal = finReal;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Long getProyectoId() {
    return proyectoId;
  }
  
  public void setProyectoId(Long proyectoId) {
    this.proyectoId = proyectoId;
  }
  
  public Double getVariacionComienzo() {
    return variacionComienzo;
  }
  
  public void setVariacionComienzo(Double variacionComienzo) {
    this.variacionComienzo = variacionComienzo;
  }
  
  public Double getVariacionDuracion() {
    return variacionDuracion;
  }
  
  public void setVariacionDuracion(Double variacionDuracion) {
    this.variacionDuracion = variacionDuracion;
  }
  
  public Double getVariacionFin() {
    return variacionFin;
  }
  
  public void setVariacionFin(Double variacionFin) {
    this.variacionFin = variacionFin;
  }
  
  public PryCalendario getPryCalendario() {
    return pryCalendario;
  }
  
  public void setPryCalendario(PryCalendario pryCalendario) {
    this.pryCalendario = pryCalendario;
  }
  
  public int compareTo(Object o) {
    PryProyecto or = (PryProyecto)o;
    return getProyectoId().compareTo(or.getProyectoId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryProyecto other = (PryProyecto)obj;
    if (proyectoId == null) {
      if (proyectoId != null)
        return false;
    } else if (!proyectoId.equals(proyectoId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("proyectoId", getProyectoId()).toString();
  }
}
