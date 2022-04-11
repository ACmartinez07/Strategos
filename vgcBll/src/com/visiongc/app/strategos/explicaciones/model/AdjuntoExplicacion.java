package com.visiongc.app.strategos.explicaciones.model;

import java.io.Serializable;
import java.sql.Blob;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AdjuntoExplicacion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private AdjuntoExplicacionPK pk;
  private Explicacion explicacion;
  private String titulo;
  private String ruta;


  
  public AdjuntoExplicacion(AdjuntoExplicacionPK pk, String titulo, String ruta, Explicacion explicacion)
  {
    this.pk = pk;
    this.titulo = titulo;
    this.ruta=ruta;
    this.explicacion = explicacion;
  }
  

  public AdjuntoExplicacion() {}
  
    
  public String getTitulo()
  {
    return titulo;
  }
  
  public void setTitulo(String titulo)
  {
    this.titulo = titulo;
  }
  
  public String getRuta()
  {
    return ruta;
  }
  
  public void setRuta(String ruta)
  {
    this.ruta = ruta;
  }
  
  public AdjuntoExplicacionPK getPk()
  {
    return pk;
  }
  
  public void setPk(AdjuntoExplicacionPK pk)
  {
    this.pk = pk;
  }
  
  public Explicacion getExplicacion()
  {
    return explicacion;
  }
  
  public void setExplicacion(Explicacion explicacion)
  {
    this.explicacion = explicacion;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof AdjuntoExplicacion))
      return false;
    AdjuntoExplicacion castOther = (AdjuntoExplicacion)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
