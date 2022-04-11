package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryColumna
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long columnaId;
  private String nombre;
  private Boolean numerico;
  private Byte alineacion;
  private String formato;
  private Integer tamano;
  
  public PryColumna(Long columnaId, String nombre, Boolean numerico, Byte alineacion, String formato, Integer tamano)
  {
    this.columnaId = columnaId;
    this.nombre = nombre;
    this.numerico = numerico;
    this.alineacion = alineacion;
    this.formato = formato;
    this.tamano = tamano;
  }
  

  public PryColumna() {}
  

  public Long getColumnaId()
  {
    return columnaId;
  }
  
  public void setColumnaId(Long columnaId) {
    this.columnaId = columnaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Byte getAlineacion() {
    return alineacion;
  }
  
  public void setAlineacion(Byte alineacion) {
    this.alineacion = alineacion;
  }
  
  public String getFormato() {
    return formato;
  }
  
  public void setFormato(String formato) {
    this.formato = formato;
  }
  
  public Boolean getNumerico() {
    return numerico;
  }
  
  public void setNumerico(Boolean numerico) {
    this.numerico = numerico;
  }
  
  public Integer getTamano() {
    return tamano;
  }
  
  public void setTamano(Integer tamano) {
    this.tamano = tamano;
  }
  
  public int compareTo(Object o) {
    PryColumna or = (PryColumna)o;
    return getColumnaId().compareTo(or.getColumnaId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryColumna other = (PryColumna)obj;
    if (columnaId == null) {
      if (columnaId != null)
        return false;
    } else if (!columnaId.equals(columnaId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return 
      new ToStringBuilder(this).append("columnaId", getColumnaId()).toString();
  }
}
