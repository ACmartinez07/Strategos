package com.visiongc.app.strategos.planificacionseguimiento.model;

import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrdSeguimientoProducto
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private PrdSeguimientoProductoPK pk;
  private Date fechaInicio;
  private Date fechaFin;
  private Byte alerta;
  private PrdProducto producto;
  
  public PrdSeguimientoProducto(PrdSeguimientoProductoPK pk, Date fechaInicio, Date fechaFin, Byte alerta)
  {
    this.pk = pk;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.alerta = alerta;
  }
  

  public PrdSeguimientoProducto() {}
  

  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
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
  
  public Date getFechaInicio() {
    return fechaInicio;
  }
  
  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }
  
  public String getFechaInicioFormateada() {
    return VgcFormatter.formatearFecha(getFechaInicio(), "formato.fecha.corta");
  }
  
  public PrdSeguimientoProductoPK getPk() {
    return pk;
  }
  
  public void setPk(PrdSeguimientoProductoPK pk) {
    this.pk = pk;
  }
  
  public PrdProducto getProducto() {
    return producto;
  }
  
  public void setProducto(PrdProducto producto) {
    this.producto = producto;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PrdSeguimientoProducto other = (PrdSeguimientoProducto)obj;
    if (pk == null) {
      if (pk != null)
        return false;
    } else if (!pk.equals(pk))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
