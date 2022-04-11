package com.visiongc.app.strategos.presentaciones.model;

import com.visiongc.app.strategos.portafolios.model.Portafolio;
import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Pagina
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long paginaId;
  private Long vistaId;
  private Long portafolioId;
  private String descripcion;
  private Byte filas;
  private Byte columnas;
  private Integer ancho;
  private Integer alto;
  private Vista vista;
  private Portafolio portafolio;
  private Integer numero;
  private Byte filasNuevas;
  private Byte columnasNuevas;
  private Set<Celda> celdas;
  
  public Pagina(Long paginaId, Long vistaId, String descripcion, Byte filas, Byte columnas, Integer ancho, Integer alto, Vista vista, Integer numero)
  {
    this.paginaId = paginaId;
    this.vistaId = vistaId;
    this.descripcion = descripcion;
    this.filas = filas;
    this.columnas = columnas;
    this.ancho = ancho;
    this.alto = alto;
    this.vista = vista;
    this.numero = numero;
  }
  

  public Pagina() {}
  

  public Long getPaginaId()
  {
    return paginaId;
  }
  
  public void setPaginaId(Long paginaId)
  {
    this.paginaId = paginaId;
  }
  
  public Long getVistaId()
  {
    return vistaId;
  }
  
  public void setVistaId(Long vistaId)
  {
    this.vistaId = vistaId;
  }
  
  public Long getPortafolioId()
  {
    return portafolioId;
  }
  
  public void setPortafolioId(Long portafolioId)
  {
    this.portafolioId = portafolioId;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public Byte getFilas()
  {
    return filas;
  }
  
  public void setFilas(Byte filas)
  {
    this.filas = filas;
  }
  
  public Byte getColumnas()
  {
    return columnas;
  }
  
  public void setColumnas(Byte columnas)
  {
    this.columnas = columnas;
  }
  
  public Integer getAncho()
  {
    return ancho;
  }
  
  public void setAncho(Integer ancho)
  {
    this.ancho = ancho;
  }
  
  public Integer getAlto()
  {
    return alto;
  }
  
  public void setAlto(Integer alto)
  {
    this.alto = alto;
  }
  
  public Vista getVista()
  {
    return vista;
  }
  
  public void setVista(Vista vista)
  {
    this.vista = vista;
  }
  
  public Portafolio getPortafolio()
  {
    return portafolio;
  }
  
  public void setPortafolio(Portafolio portafolio)
  {
    this.portafolio = portafolio;
  }
  
  public Integer getNumero()
  {
    return numero;
  }
  
  public void setNumero(Integer numero)
  {
    this.numero = numero;
  }
  
  public Byte getFilasNuevas()
  {
    return filasNuevas;
  }
  
  public void setFilasNuevas(Byte filasNuevas)
  {
    this.filasNuevas = filasNuevas;
  }
  
  public Byte getColumnasNuevas()
  {
    return columnasNuevas;
  }
  
  public void setColumnasNuevas(Byte columnasNuevas)
  {
    this.columnasNuevas = columnasNuevas;
  }
  
  public Set<Celda> getCeldas()
  {
    return celdas;
  }
  
  public void setCeldas(Set<Celda> celdas)
  {
    this.celdas = celdas;
  }
  
  public int compareTo(Object o)
  {
    Pagina or = (Pagina)o;
    return getPaginaId().compareTo(or.getPaginaId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("paginaId", getPaginaId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Pagina other = (Pagina)obj;
    
    return new EqualsBuilder().append(getPaginaId(), other.getPaginaId()).isEquals();
  }
}
