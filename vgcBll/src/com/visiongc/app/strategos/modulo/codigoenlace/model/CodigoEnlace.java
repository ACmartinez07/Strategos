package com.visiongc.app.strategos.modulo.codigoenlace.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;











public class CodigoEnlace
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long id;
  private String codigo;
  private String nombre;
  private Long bi;
  private Long categoria;
  
  public CodigoEnlace() {}
  
  public Long getId()
  {
    return id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getCodigo()
  {
    return codigo;
  }
  
  public void setCodigo(String codigo)
  {
    this.codigo = codigo;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Long getBi()
  {
    return bi;
  }
  
  public void setBi(Long bi)
  {
    this.bi = bi;
  }
  
  public Long getCategoria()
  {
    return categoria;
  }
  
  public void setCategoria(Long categoria)
  {
    this.categoria = categoria;
  }
  
  public int compareTo(Object o)
  {
    CodigoEnlace or = (CodigoEnlace)o;
    return getId().compareTo(or.getId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CodigoEnlace other = (CodigoEnlace)obj;
    return new EqualsBuilder().append(getId(), other.getId()).isEquals();
  }
}
