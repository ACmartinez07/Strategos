package com.visiongc.app.strategos.portafolios.model;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Portafolio
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long id;
  private Long organizacionId;
  private String nombre;
  private Byte activo;
  private Double porcentajeCompletado;
  private Long estatusId;
  private IniciativaEstatus estatus;
  private OrganizacionStrategos organizacion;
  private Set<Pagina> paginas;
  private Byte frecuencia;
  private Set<IndicadorPortafolio> portafolioIndicadores;
  private Long claseId;
  private ClaseIndicadores clase;
  private String fechaUltimaMedicion;
  private String fechaUltimoCalculo;
  private Integer ancho;
  private Integer alto;
  private Byte filas;
  private Byte columnas;
  
  public Portafolio() {}
  
  public Long getId()
  {
    return id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
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
  
  public Byte getActivo()
  {
    return activo;
  }
  
  public void setActivo(Byte activo)
  {
    this.activo = CondicionType.getFiltroCondicionType(activo);
  }
  
  public Double getPorcentajeCompletado()
  {
    return porcentajeCompletado;
  }
  
  public void setPorcentajeCompletado(Double porcentajeCompletado)
  {
    this.porcentajeCompletado = porcentajeCompletado;
  }
  
  public String getPorcentajeCompletadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeCompletado, 2);
  }
  
  public Long getEstatusId()
  {
    return estatusId;
  }
  
  public void setEstatusId(Long estatusId)
  {
    this.estatusId = estatusId;
  }
  
  public IniciativaEstatus getEstatus()
  {
    return estatus;
  }
  
  public void setEstatus(IniciativaEstatus estatus)
  {
    this.estatus = estatus;
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
  
  public Byte getFrecuencia()
  {
    return frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia)
  {
    this.frecuencia = frecuencia;
  }
  
  public String getFrecuenciaNombre()
  {
    if (frecuencia != null) {
      return Frecuencia.getNombre(frecuencia.byteValue());
    }
    return "";
  }
  
  public Set<IndicadorPortafolio> getPortafolioIndicadores()
  {
    return portafolioIndicadores;
  }
  
  public void setPortafolioIndicadores(Set<IndicadorPortafolio> portafolioIndicadores)
  {
    this.portafolioIndicadores = portafolioIndicadores;
  }
  
  public Long getIndicadorId(Byte tipo)
  {
    for (Iterator<IndicadorPortafolio> iter = portafolioIndicadores.iterator(); iter.hasNext();)
    {
      IndicadorPortafolio portafolioIndicador = (IndicadorPortafolio)iter.next();
      if (portafolioIndicador.getTipo().byteValue() == tipo.byteValue()) {
        return portafolioIndicador.getPk().getIndicadorId();
      }
    }
    return null;
  }
  
  public void setIndicadorId(Long indicadorId, Byte tipo)
  {
    boolean indicadorExiste = false;
    
    if (portafolioIndicadores != null)
    {
      for (Iterator<IndicadorPortafolio> iter = portafolioIndicadores.iterator(); iter.hasNext();)
      {
        IndicadorPortafolio portafolioIndicador = (IndicadorPortafolio)iter.next();
        if (portafolioIndicador.getTipo().byteValue() == tipo.byteValue())
        {
          indicadorExiste = true;
          portafolioIndicador.getPk().setIndicadorId(indicadorId);
          portafolioIndicador.getPk().setPortafolioId(id);
          break;
        }
      }
    }
    
    if (!indicadorExiste)
    {
      IndicadorPortafolioPK pk = new IndicadorPortafolioPK(indicadorId, id);
      IndicadorPortafolio portafolioIndicador = new IndicadorPortafolio();
      portafolioIndicador.setTipo(tipo);
      portafolioIndicador.setPk(pk);
      if (portafolioIndicadores == null) {
        portafolioIndicadores = new HashSet();
      }
      portafolioIndicadores.add(portafolioIndicador);
    }
  }
  
  public Long getClaseId()
  {
    return claseId;
  }
  
  public void setClaseId(Long claseId)
  {
    this.claseId = claseId;
  }
  
  public ClaseIndicadores getClase()
  {
    return clase;
  }
  
  public void setClase(ClaseIndicadores clase)
  {
    this.clase = clase;
  }
  
  public String getFechaUltimaMedicion()
  {
    return fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion)
  {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
  
  public Integer getFechaUltimaMedicionPeriodo()
  {
    Integer periodo = null;
    
    if (fechaUltimaMedicion != null)
    {
      for (int index = 0; index < fechaUltimaMedicion.length(); index++)
      {
        String caracter = fechaUltimaMedicion.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          periodo = new Integer(fechaUltimaMedicion.substring(0, index));
          break;
        }
      }
    }
    
    return periodo;
  }
  
  public Integer getFechaUltimaMedicionAno()
  {
    Integer ano = null;
    
    if (fechaUltimaMedicion != null)
    {
      for (int index = 0; index < fechaUltimaMedicion.length(); index++)
      {
        String caracter = fechaUltimaMedicion.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          ano = new Integer(fechaUltimaMedicion.substring(index + 1));
          break;
        }
      }
    }
    
    return ano;
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
  
  public String getFechaUltimoCalculo()
  {
    return fechaUltimoCalculo;
  }
  
  public void setFechaUltimoCalculo(String fechaUltimoCalculo)
  {
    this.fechaUltimoCalculo = fechaUltimoCalculo;
  }
  
  public Integer getFechaUltimoCalculoPeriodo()
  {
    Integer periodo = null;
    
    if (fechaUltimoCalculo != null)
    {
      for (int index = 0; index < fechaUltimoCalculo.length(); index++)
      {
        String caracter = fechaUltimoCalculo.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          periodo = new Integer(fechaUltimoCalculo.substring(0, index));
          break;
        }
      }
    }
    
    return periodo;
  }
  
  public Integer getFechaUltimoCalculoAno()
  {
    Integer ano = null;
    
    if (fechaUltimoCalculo != null)
    {
      for (int index = 0; index < fechaUltimoCalculo.length(); index++)
      {
        String caracter = fechaUltimoCalculo.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          ano = new Integer(fechaUltimoCalculo.substring(index + 1));
          break;
        }
      }
    }
    
    return ano;
  }
  

  public int compareTo(Object o)
  {
    Portafolio or = (Portafolio)o;
    return getId().compareTo(or.getId());
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Portafolio other = (Portafolio)obj;
    if (id == null) {
      if (id != null) {
        return false;
      }
    } else if (!id.equals(id))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }
}
