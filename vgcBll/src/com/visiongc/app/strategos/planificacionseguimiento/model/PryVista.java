package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryVista
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long vistaId;
  private String nombre;
  
  public PryVista(Long vistaId, String nombre)
  {
    this.vistaId = vistaId;
    this.nombre = nombre;
  }
  

  public PryVista() {}
  

  public Long getVistaId()
  {
    return vistaId;
  }
  
  public void setVistaId(Long vistaId) {
    this.vistaId = vistaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o) {
    PryVista or = (PryVista)o;
    return getVistaId().compareTo(or.getVistaId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryVista other = (PryVista)obj;
    if (vistaId == null) {
      if (vistaId != null)
        return false;
    } else if (!vistaId.equals(vistaId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("vistaId", getVistaId()).toString();
  }
}
