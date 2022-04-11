package com.visiongc.app.strategos.iniciativas.model.util;

import com.visiongc.commons.util.VgcFormatter;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


public class IniciativaEstatus
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long id;
  private String nombre;
  private Double porcentajeInicial;
  private Double porcentajeFinal;
  private Boolean sistema;
  private Boolean bloquearMedicion;
  private Boolean bloquearIndicadores;
  
  public IniciativaEstatus(Long id, String nombre, Double porcentajeInicial, Double porcentajeFinal, Boolean sistema, Boolean bloquearMedicion, Boolean bloquearIndicadores)
  {
    this.id = id;
    this.nombre = nombre;
    this.porcentajeInicial = porcentajeInicial;
    this.porcentajeFinal = porcentajeFinal;
    this.sistema = sistema;
    this.bloquearMedicion = bloquearMedicion;
    this.bloquearIndicadores = bloquearIndicadores;
  }
  

  public IniciativaEstatus() {}
  

  public Long getId()
  {
    return id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Double getPorcentajeInicial()
  {
    return porcentajeInicial;
  }
  
  public void setPorcentajeInicial(Double porcentajeInicial)
  {
    this.porcentajeInicial = porcentajeInicial;
  }
  
  public String getPorcentajeInicialFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeInicial, 2);
  }
  
  public Double getPorcentajeFinal()
  {
    return porcentajeFinal;
  }
  
  public void setPorcentajeFinal(Double porcentajeFinal)
  {
    this.porcentajeFinal = porcentajeFinal;
  }
  
  public String getPorcentajeFinalFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeFinal, 2);
  }
  
  public Boolean getSistema()
  {
    return sistema;
  }
  
  public void setSistema(Boolean sistema)
  {
    this.sistema = sistema;
  }
  
  public Boolean getBloquearMedicion()
  {
    return bloquearMedicion;
  }
  
  public void setBloquearMedicion(Boolean bloquearMedicion)
  {
    this.bloquearMedicion = bloquearMedicion;
  }
  
  public Boolean getBloquearIndicadores()
  {
    return bloquearIndicadores;
  }
  
  public void setBloquearIndicadores(Boolean bloquearIndicadores)
  {
    this.bloquearIndicadores = bloquearIndicadores;
  }
  
  public int compareTo(Object o)
  {
    IniciativaEstatus or = (IniciativaEstatus)o;
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
    IniciativaEstatus other = (IniciativaEstatus)obj;
    if (id == null)
    {
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
  
  public static class EstatusType
  {
    private static final Long ESTATUS_SININICIAR = Long.valueOf(1L);
    private static final Long ESTATUS_CANCELADO = Long.valueOf(3L);
    private static final Long ESTATUS_SUSPENDIDO = Long.valueOf(4L);
    private static final Long ESTATUS_CULMINADO = Long.valueOf(5L);
    
    public EstatusType() {}
    
    public static Long getEstatusSinIniciar() { return ESTATUS_SININICIAR; }
    

    public static Long getEstatusCencelado()
    {
      return ESTATUS_CANCELADO;
    }
    
    public static Long getEstatusSuspendido()
    {
      return ESTATUS_SUSPENDIDO;
    }
    
    public static Long getEstatusCulminado()
    {
      return ESTATUS_CULMINADO;
    }
    
    public static IniciativaEstatus setEstatusIniciado()
    {
      return new IniciativaEstatus(ESTATUS_SININICIAR, "Sin Iniciar", Double.valueOf(0.0D), Double.valueOf(0.0D), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false));
    }
  }
}
