package com.visiongc.app.strategos.planificacionseguimiento.model;

import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrdSeguimiento
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private PrdSeguimientoPK pk;
  private Date fecha;
  private Byte alerta;
  private String seguimiento;
  private Set seguimientosProductos;
  
  public PrdSeguimiento(PrdSeguimientoPK pk, Date fecha, Byte alerta, String seguimiento)
  {
    this.pk = pk;
    this.fecha = fecha;
    this.alerta = alerta;
    this.seguimiento = seguimiento;
  }
  

  public PrdSeguimiento() {}
  

  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
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
  
  public PrdSeguimientoPK getPk() {
    return pk;
  }
  
  public void setPk(PrdSeguimientoPK pk) {
    this.pk = pk;
  }
  
  public String getSeguimiento() {
    return seguimiento;
  }
  
  public void setSeguimiento(String seguimiento) {
    this.seguimiento = seguimiento;
  }
  
  public Set getSeguimientosProductos() {
    return seguimientosProductos;
  }
  
  public void setSeguimientosProductos(Set seguimientosProductos) {
    this.seguimientosProductos = seguimientosProductos;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PrdSeguimiento other = (PrdSeguimiento)obj;
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
