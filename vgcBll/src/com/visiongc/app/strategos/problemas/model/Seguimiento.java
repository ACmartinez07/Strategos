package com.visiongc.app.strategos.problemas.model;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Seguimiento
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long seguimientoId;
  private Long estadoId;
  private Long accionId;
  private Date fechaEmision;
  private Boolean emisionEnviado;
  private Date fechaRecepcion;
  private Boolean recepcionEnviado;
  private Date fechaAprobacion;
  private String preparadoPor;
  private String aprobadoPor;
  private Boolean aprobado;
  private String claveCorreo;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Boolean readOnly;
  private Integer numeroReporte;
  private String nota;
  private Set memosSeguimiento;
  private EstadoAcciones estado;
  private Boolean ultimoRegistro;
  private Accion accion;
  private List listaproblemas = new ArrayList();
  
  public Seguimiento(Long seguimientoId, Long estadoId, Long accionId, Date fechaEmision, Boolean emisionEnviado, Date fechaRecepcion, Boolean recepcionEnviado, Date fechaAprobacion, String preparadoPor, String aprobadoPor, Boolean aprobado, String claveCorreo, Date creado, Date modificado, Long creadoId, Long modificadoId, Boolean readOnly, Integer numeroReporte)
  {
    this.seguimientoId = seguimientoId;
    this.estadoId = estadoId;
    this.accionId = accionId;
    this.fechaEmision = fechaEmision;
    this.emisionEnviado = emisionEnviado;
    this.fechaRecepcion = fechaRecepcion;
    this.recepcionEnviado = recepcionEnviado;
    this.fechaAprobacion = fechaAprobacion;
    this.preparadoPor = preparadoPor;
    this.aprobadoPor = aprobadoPor;
    this.aprobado = aprobado;
    this.claveCorreo = claveCorreo;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.readOnly = readOnly;
    this.numeroReporte = numeroReporte;
  }
  

  public Seguimiento() {}
  

  public Long getSeguimientoId()
  {
    return seguimientoId;
  }
  
  public void setSeguimientoId(Long seguimientoId) {
    this.seguimientoId = seguimientoId;
  }
  
  public Boolean getUltimoRegistro() {
    return ultimoRegistro;
  }
  
  public void setUltimoRegistro(Boolean ultimoRegistro) {
    this.ultimoRegistro = ultimoRegistro;
  }
  
  public Long getEstadoId() {
    return estadoId;
  }
  
  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }
  
  public Long getAccionId() {
    return accionId;
  }
  
  public void setAccionId(Long accionId) {
    this.accionId = accionId;
  }
  
  public Date getFechaEmision() {
    return fechaEmision;
  }
  
  public String getFechaEmisionFormateada() {
    return VgcFormatter.formatearFecha(fechaEmision, "formato.fecha.corta");
  }
  
  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }
  
  public Boolean getEmisionEnviado() {
    return emisionEnviado;
  }
  
  public void setEmisionEnviado(Boolean emisionEnviado) {
    this.emisionEnviado = emisionEnviado;
  }
  
  public Date getFechaRecepcion() {
    return fechaRecepcion;
  }
  
  public String getFechaRecepcionFormateada() {
    return VgcFormatter.formatearFecha(fechaRecepcion, "formato.fecha.corta");
  }
  
  public void setFechaRecepcion(Date fechaRecepcion) {
    this.fechaRecepcion = fechaRecepcion;
  }
  
  public Boolean getRecepcionEnviado() {
    return recepcionEnviado;
  }
  
  public void setRecepcionEnviado(Boolean recepcionEnviado) {
    this.recepcionEnviado = recepcionEnviado;
  }
  
  public Date getFechaAprobacion() {
    return fechaAprobacion;
  }
  
  public String getFechaAprobacionFormateada() {
    return VgcFormatter.formatearFecha(fechaAprobacion, "formato.fecha.corta");
  }
  
  public void setFechaAprobacion(Date fechaAprobacion) {
    this.fechaAprobacion = fechaAprobacion;
  }
  
  public String getPreparadoPor() {
    return preparadoPor;
  }
  
  public void setPreparadoPor(String preparadoPor) {
    this.preparadoPor = preparadoPor;
  }
  
  public String getAprobadoPor() {
    return aprobadoPor;
  }
  
  public void setAprobadoPor(String aprobadoPor) {
    this.aprobadoPor = aprobadoPor;
  }
  
  public Boolean getAprobado() {
    return aprobado;
  }
  
  public void setAprobado(Boolean aprobado) {
    this.aprobado = aprobado;
  }
  
  public String getClaveCorreo() {
    return claveCorreo;
  }
  
  public void setClaveCorreo(String claveCorreo) {
    this.claveCorreo = claveCorreo;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public Boolean getReadOnly() {
    return readOnly;
  }
  
  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }
  
  public Integer getNumeroReporte() {
    return numeroReporte;
  }
  
  public void setNumeroReporte(Integer numeroReporte) {
    this.numeroReporte = numeroReporte;
  }
  
  public String getNota() {
    return nota;
  }
  
  public void setNota(String nota) {
    this.nota = nota;
  }
  
  public Set getMemosSeguimiento() {
    return memosSeguimiento;
  }
  
  public void setMemosSeguimiento(Set memosSeguimiento) {
    this.memosSeguimiento = memosSeguimiento;
  }
  
  public EstadoAcciones getEstado() {
    return estado;
  }
  
  public void setEstado(EstadoAcciones estado) {
    this.estado = estado;
  }
  
  public Accion getAccion() {
    return accion;
  }
  
  public void setAccion(Accion accion) {
    this.accion = accion;
  }
  
  public List getListaproblemas() {
    return listaproblemas;
  }
  
  public void setListaproblemas(List listaproblemas) {
    this.listaproblemas = listaproblemas;
  }
  
  public int compareTo(Object o) {
    Seguimiento or = (Seguimiento)o;
    return getSeguimientoId().compareTo(or.getSeguimientoId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("seguimientoId", getSeguimientoId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Seguimiento other = (Seguimiento)obj;
    return new EqualsBuilder().append(getSeguimientoId(), other.getSeguimientoId()).isEquals();
  }
}
