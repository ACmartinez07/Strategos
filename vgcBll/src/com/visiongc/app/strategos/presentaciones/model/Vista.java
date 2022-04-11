package com.visiongc.app.strategos.presentaciones.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Vista
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long vistaId;
  private Long organizacionId;
  private String nombre;
  private String descripcion;
  private String fechaInicio;
  private String fechaFin;
  private Boolean visible;
  private OrganizacionStrategos organizacion;
  private Set<Pagina> paginas;
  
  public Vista(Long vistaId, Long organizacionId, String nombre, String descripcion, String fechaInicio, String fechaFin, Boolean visible)
  {
    this.vistaId = vistaId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.visible = visible;
  }
  

  public Vista() {}
  

  public Long getVistaId()
  {
    return vistaId;
  }
  
  public void setVistaId(Long vistaId)
  {
    this.vistaId = vistaId;
  }
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
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
  
  public String getFechaInicio()
  {
    return fechaInicio;
  }
  
  public void setFechaInicio(String fechaInicio)
  {
    this.fechaInicio = fechaInicio;
  }
  
  public String getFechaFin()
  {
    return fechaFin;
  }
  
  public void setFechaFin(String fechaFin)
  {
    this.fechaFin = fechaFin;
  }
  
  public Boolean getVisible()
  {
    return visible;
  }
  
  public void setVisible(Boolean visible)
  {
    this.visible = visible;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public Set<Pagina> getPaginas()
  {
    return paginas;
  }
  
  public void setPaginas(Set<Pagina> paginas)
  {
    this.paginas = paginas;
  }
  
  public int compareTo(Object o)
  {
    Vista or = (Vista)o;
    return getVistaId().compareTo(or.getVistaId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("vistaId", getVistaId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) {
      return false;
    }
    Vista other = (Vista)obj;
    return new EqualsBuilder().append(getVistaId(), other.getVistaId()).isEquals();
  }
}
