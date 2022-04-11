package com.visiongc.app.strategos.problemas.model;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Problema
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long problemaId;
  private Long organizacionId;
  private Long claseId;
  private Date fecha;
  private Long indicadorEfectoId;
  private Long iniciativaEfectoId;
  private Long responsableId;
  private Long auxiliarId;
  private Long estadoId;
  private Date fechaEstimadaInicio;
  private Date fechaRealInicio;
  private Date fechaEstimadaFinal;
  private Date fechaRealFinal;
  private String nombre;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Boolean readOnly;
  private Set memosProblema;
  private Responsable responsable;
  private Responsable auxiliar;
  private EstadoAcciones estado;
  private Usuario usuarioCreado;
  private Usuario usuarioModificado;
  private ClaseProblemas claseProblemas;
  private Set causas;
  private List listaAccionesCorrectivas = new ArrayList();
  
  public Problema(Long problemaId, Long organizacionId, Long claseId, Date fecha, Long indicadorEfectoId, Long iniciativaEfectoId, Long responsableId, Long auxiliarId, Long estadoId, Date fechaEstimadaInicio, Date fechaRealInicio, Date fechaEstimadaFinal, Date fechaRealFinal, String nombre, Date creado, Date modificado, Long creadoId, Long modificadoId, Boolean readOnly)
  {
    this.problemaId = problemaId;
    this.organizacionId = organizacionId;
    this.claseId = claseId;
    this.fecha = fecha;
    this.indicadorEfectoId = indicadorEfectoId;
    this.iniciativaEfectoId = iniciativaEfectoId;
    this.responsableId = responsableId;
    this.auxiliarId = auxiliarId;
    this.estadoId = estadoId;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.fechaRealInicio = fechaRealInicio;
    this.fechaEstimadaFinal = fechaEstimadaFinal;
    this.fechaRealFinal = fechaRealFinal;
    this.nombre = nombre;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.readOnly = readOnly;
  }
  

  public Problema() {}
  

  public Long getProblemaId()
  {
    return problemaId;
  }
  
  public void setProblemaId(Long problemaId) {
    this.problemaId = problemaId;
  }
  
  public Long getOrganizacionId() {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public Long getClaseId() {
    return claseId;
  }
  
  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }
  
  public Date getFecha() {
    return fecha;
  }
  
  public String getFechaFormateada() {
    return VgcFormatter.formatearFecha(fecha, "formato.fecha.corta");
  }
  
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  public Long getIndicadorEfectoId() {
    return indicadorEfectoId;
  }
  
  public void setIndicadorEfectoId(Long indicadorEfectoId) {
    this.indicadorEfectoId = indicadorEfectoId;
  }
  
  public Long getIniciativaEfectoId() {
    return iniciativaEfectoId;
  }
  
  public void setIniciativaEfectoId(Long iniciativaEfectoId) {
    this.iniciativaEfectoId = iniciativaEfectoId;
  }
  
  public Long getResponsableId() {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }
  
  public Long getAuxiliarId() {
    return auxiliarId;
  }
  
  public void setAuxiliarId(Long auxiliarId) {
    this.auxiliarId = auxiliarId;
  }
  
  public Long getEstadoId() {
    return estadoId;
  }
  
  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }
  
  public Date getFechaEstimadaInicio() {
    return fechaEstimadaInicio;
  }
  
  public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }
  
  public Date getFechaRealInicio() {
    return fechaRealInicio;
  }
  
  public void setFechaRealInicio(Date fechaRealInicio) {
    this.fechaRealInicio = fechaRealInicio;
  }
  
  public Date getFechaEstimadaFinal() {
    return fechaEstimadaFinal;
  }
  
  public void setFechaEstimadaFinal(Date fechaEstimadaFinal) {
    this.fechaEstimadaFinal = fechaEstimadaFinal;
  }
  
  public Date getFechaRealFinal() {
    return fechaRealFinal;
  }
  
  public void setFechaRealFinal(Date fechaRealFinal) {
    this.fechaRealFinal = fechaRealFinal;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
  
  public Set getMemosProblema() {
    return memosProblema;
  }
  
  public void setMemosProblema(Set memosProblema) {
    this.memosProblema = memosProblema;
  }
  
  public Responsable getResponsable() {
    return responsable;
  }
  
  public void setResponsable(Responsable responsable) {
    this.responsable = responsable;
  }
  
  public Responsable getAuxiliar() {
    return auxiliar;
  }
  
  public void setAuxiliar(Responsable auxiliar) {
    this.auxiliar = auxiliar;
  }
  
  public String getFechaEstimadaInicioFormateada() {
    return VgcFormatter.formatearFecha(getFechaEstimadaInicio(), "formato.fecha.corta");
  }
  
  public String getFechaRealInicioFormateada() {
    return VgcFormatter.formatearFecha(getFechaRealInicio(), "formato.fecha.corta");
  }
  
  public String getFechaRealFinalFormateada() {
    return VgcFormatter.formatearFecha(getFechaRealFinal(), "formato.fecha.corta");
  }
  
  public String getFechaEstimadaFinalFormateada() {
    return VgcFormatter.formatearFecha(getFechaEstimadaFinal(), "formato.fecha.corta");
  }
  
  public EstadoAcciones getEstado() {
    return estado;
  }
  
  public void setEstado(EstadoAcciones estado) {
    this.estado = estado;
  }
  
  public Usuario getUsuarioCreado() {
    return usuarioCreado;
  }
  
  public void setUsuarioCreado(Usuario usuarioCreado) {
    this.usuarioCreado = usuarioCreado;
  }
  
  public Usuario getUsuarioModificado() {
    return usuarioModificado;
  }
  
  public void setUsuarioModificado(Usuario usuarioModificado) {
    this.usuarioModificado = usuarioModificado;
  }
  
  public ClaseProblemas getClaseProblemas() {
    return claseProblemas;
  }
  
  public void setClaseProblemas(ClaseProblemas claseProblemas) {
    this.claseProblemas = claseProblemas;
  }
  
  public Set getCausas() {
    return causas;
  }
  
  public void setCausas(Set causas) {
    this.causas = causas;
  }
  
  public List getListaAccionesCorrectivas() {
    return listaAccionesCorrectivas;
  }
  
  public void setListaAccionesCorrectivas(List listaAccionesCorrectivas) {
    this.listaAccionesCorrectivas = listaAccionesCorrectivas;
  }
  
  public int compareTo(Object o)
  {
    Problema or = (Problema)o;
    return getProblemaId().compareTo(or.getProblemaId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Problema other = (Problema)obj;
    if (problemaId == null) {
      if (problemaId != null)
        return false;
    } else if (!problemaId.equals(problemaId))
      return false;
    return true;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("problemaId", getProblemaId()).toString();
  }
}
