package com.visiongc.app.strategos.unidadesmedida.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UnidadMedida
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long unidadId;
  private String nombre;
  private Boolean tipo;
  
  public UnidadMedida(Long unidadId, String nombre, Boolean tipo)
  {
    this.unidadId = unidadId;
    this.nombre = nombre;
    this.tipo = tipo;
  }
  

  public UnidadMedida() {}
  

  public Long getUnidadId()
  {
    return unidadId;
  }
  
  public void setUnidadId(Long unidadId)
  {
    this.unidadId = unidadId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Boolean getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Boolean tipo)
  {
    this.tipo = tipo;
  }
  
  public int compareTo(Object o)
  {
    UnidadMedida or = (UnidadMedida)o;
    return getUnidadId().compareTo(or.getUnidadId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("unidadId", getUnidadId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UnidadMedida other = (UnidadMedida)obj;
    return new EqualsBuilder().append(getUnidadId(), other.getUnidadId()).isEquals();
  }
}
