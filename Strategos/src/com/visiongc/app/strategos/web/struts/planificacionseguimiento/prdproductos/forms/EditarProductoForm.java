package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms;

import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Date;

public class EditarProductoForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long productoId;
  private String nombre;
  private String fechaInicio;
  private String descripcion;
  private Long responsableId;
  private String nombreResponsable;
  private Responsable responsable;

  public Long getProductoId()
  {
    return this.productoId;
  }

  public void setProductoId(Long productoId) {
    this.productoId = productoId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getFechaInicio() {
    return this.fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public String getNombreResponsable() {
    return this.nombreResponsable;
  }

  public void setNombreResponsable(String nombreResponsable) {
    this.nombreResponsable = nombreResponsable;
  }

  public Responsable getResponsable() {
    return this.responsable;
  }

  public void setResponsable(Responsable responsable) {
    this.responsable = responsable;
  }

  public void clear()
  {
    this.productoId = new Long(0L);
    this.nombre = null;
    this.nombreResponsable = null;
    this.fechaInicio = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.descripcion = null;
    this.responsableId = null;
    setBloqueado(new Boolean(false));
  }
}