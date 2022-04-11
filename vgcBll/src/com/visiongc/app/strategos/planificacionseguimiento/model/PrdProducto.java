package com.visiongc.app.strategos.planificacionseguimiento.model;

import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.VgcFormatter;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrdProducto implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private Long productoId;
  private Long iniciativaId;
  private String nombre;
  private Date fechaInicio;
  private Date fechaFin;
  private String descripcion;
  private Long responsableId;
  private Byte alerta;
  private Responsable responsable;
  private Set seguimientosProducto;
  
  public PrdProducto() {}
  
  public Long getProductoId()
  {
    return productoId;
  }
  
  public void setProductoId(Long productoId) {
    this.productoId = productoId;
  }
  
  public Long getIniciativaId() {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Date getFechaInicio() {
    return fechaInicio;
  }
  
  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }
  
  public String getFechaInicioFormateada() {
    return VgcFormatter.formatearFecha(getFechaInicio(), "formato.fecha.corta");
  }
  
  public Date getFechaFin() {
    return fechaFin;
  }
  
  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }
  
  public String getFechaFinFormateada() {
    return VgcFormatter.formatearFecha(getFechaFin(), "formato.fecha.corta");
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public Long getResponsableId() {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }
  
  public Byte getAlerta() {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
  }
  
  public Responsable getResponsable() {
    return responsable;
  }
  
  public void setResponsable(Responsable responsable) {
    this.responsable = responsable;
  }
  
  public Set getSeguimientosProducto() {
    return seguimientosProducto;
  }
  
  public void setSeguimientosProducto(Set seguimientosProducto) {
    this.seguimientosProducto = seguimientosProducto;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PrdProducto other = (PrdProducto)obj;
    if (productoId == null) {
      if (productoId != null)
        return false;
    } else if (!productoId.equals(productoId))
      return false;
    return true;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("productoId", getProductoId()).toString();
  }
}
