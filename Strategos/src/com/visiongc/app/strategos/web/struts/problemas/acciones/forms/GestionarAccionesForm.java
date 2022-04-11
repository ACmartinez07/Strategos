package com.visiongc.app.strategos.web.struts.problemas.acciones.forms;

import org.apache.struts.action.ActionForm;

public class GestionarAccionesForm extends ActionForm
{
  static final long serialVersionUID = 0L;
  private Long problemaId;
  private String fechaEstimadaInicioProblema;
  private String fechaEstimadaFinalProblema;
  private String nombreResponsable;
  private String nombreAuxiliar;
  private String nombreEstado;
  private String descripcion;
  private String fechaEstimadaInicio;
  private String fechaEstimadaFin;
  private String fechaRealInicio;
  private String fechaRealFin;
  private String frecuencia;
  private String orden;

  public Long getProblemaId()
  {
    return this.problemaId;
  }

  public void setProblemaId(Long problemaId) {
    this.problemaId = problemaId;
  }

  public String getFechaEstimadaInicioProblema() {
    return this.fechaEstimadaInicioProblema;
  }

  public void setFechaEstimadaInicioProblema(String fechaEstimadaInicioProblema) {
    this.fechaEstimadaInicioProblema = fechaEstimadaInicioProblema;
  }

  public String getFechaEstimadaFinalProblema() {
    return this.fechaEstimadaFinalProblema;
  }

  public void setFechaEstimadaFinalProblema(String fechaEstimadaFinalProblema) {
    this.fechaEstimadaFinalProblema = fechaEstimadaFinalProblema;
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

  public String getNombreEstado() {
    return this.nombreEstado;
  }

  public void setNombreEstado(String nombreEstado) {
    this.nombreEstado = nombreEstado;
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

  public String getFechaEstimadaFin() {
    return this.fechaEstimadaFin;
  }

  public void setFechaEstimadaFin(String fechaEstimadaFin) {
    this.fechaEstimadaFin = fechaEstimadaFin;
  }

  public String getFechaRealInicio() {
    return this.fechaRealInicio;
  }

  public void setFechaRealInicio(String fechaRealInicio) {
    this.fechaRealInicio = fechaRealInicio;
  }

  public String getFechaRealFin() {
    return this.fechaRealFin;
  }

  public void setFechaRealFin(String fechaRealFin) {
    this.fechaRealFin = fechaRealFin;
  }

  public String getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(String frecuencia) {
    this.frecuencia = frecuencia;
  }

  public String getOrden() {
    return this.orden;
  }

  public void setOrden(String orden) {
    this.orden = orden;
  }

  public void clear() {
    this.fechaEstimadaInicioProblema = null;
    this.fechaEstimadaFinalProblema = null;
    this.nombreResponsable = null;
    this.nombreAuxiliar = null;
    this.nombreEstado = null;
    this.descripcion = null;
    this.fechaEstimadaInicio = null;
    this.fechaEstimadaFin = null;
    this.fechaRealInicio = null;
    this.fechaRealFin = null;
    this.frecuencia = null;
    this.orden = null;
  }
}