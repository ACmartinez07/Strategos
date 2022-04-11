package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryCalendarioDetalle
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long calendarioId;
  private Date fecha;
  private Boolean feriado;
  
  public PryCalendarioDetalle(Long calendarioId, Date fecha, Boolean feriado)
  {
    this.calendarioId = calendarioId;
    this.fecha = fecha;
    this.feriado = feriado;
  }
  

  public PryCalendarioDetalle() {}
  

  public Long getCalendarioId()
  {
    return calendarioId;
  }
  
  public void setCalendarioId(Long calendarioId) {
    this.calendarioId = calendarioId;
  }
  
  public Date getFecha() {
    return fecha;
  }
  
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  public Boolean getFeriado() {
    return feriado;
  }
  
  public void setFeriado(Boolean feriado) {
    this.feriado = feriado;
  }
  
  public int compareTo(Object o) {
    PryCalendarioDetalle or = (PryCalendarioDetalle)o;
    return getCalendarioId().compareTo(or.getCalendarioId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryCalendarioDetalle other = (PryCalendarioDetalle)obj;
    if (calendarioId == null) {
      if (calendarioId != null)
        return false;
    } else if (!calendarioId.equals(calendarioId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return 
      new ToStringBuilder(this).append("calendarioId", getCalendarioId()).toString();
  }
}
