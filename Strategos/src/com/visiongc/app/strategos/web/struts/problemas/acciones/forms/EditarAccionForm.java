package com.visiongc.app.strategos.web.struts.problemas.acciones.forms;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Date;

public class EditarAccionForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long accionId;
  private Long padreId;
  private Long estadoId;
  private Long problemaId;
  private String nombre;
  private String descripcion;
  private String fechaEstimadaInicio;
  private String fechaRealInicio;
  private String fechaEstimadaFinal;
  private String fechaRealFinal;
  private Integer frecuencia;
  private Byte orden;
  private Long responsableId;
  private Long auxiliarId;
  private String nombreResponsable;
  private String nombreAuxiliar;
  private Boolean readOnly;
  private String nombreUsuarioCreado;
  private String nombreUsuarioModificado;
  private String fechaCreado;
  private String fechaModificado;

  public Long getAccionId()
  {
    return this.accionId;
  }

  public void setAccionId(Long accionId) {
    this.accionId = accionId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public Long getEstadoId() {
    return this.estadoId;
  }

  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }

  public Long getProblemaId() {
    return this.problemaId;
  }

  public void setProblemaId(Long problemaId) {
    this.problemaId = problemaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFechaEstimadaInicio() {
    return this.fechaEstimadaInicio;
  }

  public void setFechaEstimadaInicio(String fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }

  public String getFechaRealInicio() {
    return this.fechaRealInicio;
  }

  public void setFechaRealInicio(String fechaRealInicio) {
    this.fechaRealInicio = fechaRealInicio;
  }

  public String getFechaEstimadaFinal() {
    return this.fechaEstimadaFinal;
  }

  public void setFechaEstimadaFinal(String fechaEstimadaFinal) {
    this.fechaEstimadaFinal = fechaEstimadaFinal;
  }

  public String getFechaRealFinal() {
    return this.fechaRealFinal;
  }

  public void setFechaRealFinal(String fechaRealFinal) {
    this.fechaRealFinal = fechaRealFinal;
  }

  public Integer getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Integer frecuencia) {
    this.frecuencia = frecuencia;
  }

  public Byte getOrden() {
    return this.orden;
  }

  public void setOrden(Byte orden) {
    this.orden = orden;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public Long getAuxiliarId() {
    return this.auxiliarId;
  }

  public void setAuxiliarId(Long auxiliarId) {
    this.auxiliarId = auxiliarId;
  }

  public String getNombreResponsable() {
    return this.nombreResponsable;
  }

  public void setNombreResponsable(String nombreResponsable) {
    this.nombreResponsable = nombreResponsable;
  }

  public String getNombreAuxiliar() {
    return this.nombreAuxiliar;
  }

  public void setNombreAuxiliar(String nombreAuxiliar) {
    this.nombreAuxiliar = nombreAuxiliar;
  }

  public Boolean getReadOnly() {
    return this.readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  public String getNombreUsuarioCreado() {
    return this.nombreUsuarioCreado;
  }

  public void setNombreUsuarioCreado(String nombreUsuarioCreado) {
    this.nombreUsuarioCreado = nombreUsuarioCreado;
  }

  public String getNombreUsuarioModificado() {
    return this.nombreUsuarioModificado;
  }

  public void setNombreUsuarioModificado(String nombreUsuarioModificado) {
    this.nombreUsuarioModificado = nombreUsuarioModificado;
  }

  public String getFechaCreado() {
    return this.fechaCreado;
  }

  public void setFechaCreado(String fechaCreado) {
    this.fechaCreado = fechaCreado;
  }

  public String getFechaModificado() {
    return this.fechaModificado;
  }

  public void setFechaModificado(String fechaModificado) {
    this.fechaModificado = fechaModificado;
  }

  public void clear() {
    this.accionId = new Long(0L);
    this.padreId = null;
    this.problemaId = null;
    this.nombre = null;
    this.descripcion = null;
    this.estadoId = null;
    this.fechaEstimadaInicio = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.fechaEstimadaFinal = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.fechaRealInicio = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.fechaRealFinal = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.orden = new Byte("1");
    this.frecuencia = new Integer(15);
    this.responsableId = null;
    this.auxiliarId = null;
    this.nombreResponsable = null;
    this.nombreAuxiliar = null;
    this.readOnly = new Boolean(false);
  }
}