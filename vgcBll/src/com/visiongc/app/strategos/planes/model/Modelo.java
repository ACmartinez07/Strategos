package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Modelo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private ModeloPK pk;
  private String nombre;
  private String descripcion;
  private String binario;
  private Plan plan;
  
  public Modelo(ModeloPK pk, String nombre, String descripcion, String binario, Plan plan)
  {
    this.pk = pk;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.binario = binario;
    this.plan = plan;
  }
  

  public Modelo() {}
  

  public void setPk(ModeloPK pk)
  {
    this.pk = pk;
  }
  
  public ModeloPK getPk()
  {
    return pk;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public String getBinario()
  {
    return binario;
  }
  
  public void setBinario(String binario)
  {
    this.binario = binario;
  }
  
  public Plan getPlan()
  {
    return plan;
  }
  
  public void setPlan(Plan plan)
  {
    this.plan = plan;
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof Modelo))
      return false;
    Modelo castOther = (Modelo)other;
    
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
}
