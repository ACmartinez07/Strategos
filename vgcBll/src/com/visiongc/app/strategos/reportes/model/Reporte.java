package com.visiongc.app.strategos.reportes.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;










public class Reporte
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long reporteId;
  private String nombre;
  private Long organizacionId;
  private String configuracion;
  private OrganizacionStrategos organizacion;
  private Long usuarioId;
  private Usuario usuario;
  private String descripcion;
  private Boolean publico;
  private Byte tipo;
  private Byte corte;
  
  public Reporte() {}
  
  public Long getReporteId()
  {
    return reporteId;
  }
  
  public void setReporteId(Long reporteId)
  {
    this.reporteId = reporteId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
  }
  
  public String getConfiguracion()
  {
    return configuracion;
  }
  
  public void setConfiguracion(String configuracion)
  {
    this.configuracion = configuracion;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public Long getUsuarioId()
  {
    return usuarioId;
  }
  
  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public Usuario getUsuario()
  {
    return usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public Boolean getPublico()
  {
    return publico;
  }
  
  public void setPublico(Boolean publico)
  {
    this.publico = publico;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = ReporteTipo.getTipo(tipo);
  }
  
  public Byte getCorte()
  {
    return corte;
  }
  
  public void setCorte(Byte corte)
  {
    this.corte = ReporteCorte.getCorte(corte);
  }
  
  public static class ReporteTipo {
    private static final byte TIPO_VISTA_DATOS = 1;
    
    public ReporteTipo() {}
    
    private static Byte getTipo(Byte tipo) {
      if (tipo.byteValue() == 1) {
        return new Byte((byte)1);
      }
      return null;
    }
    
    public static Byte getTipoVistaDatos()
    {
      return new Byte((byte)1);
    }
  }
  
  public static class ReporteCorte {
    private static final byte CORTE_LONGITUDINAL = 1;
    private static final byte CORTE_TRANSVERSAL = 2;
    
    public ReporteCorte() {}
    
    public static Byte getCorte(Byte corte) {
      if (corte.byteValue() == 2)
        return new Byte((byte)2);
      if (corte.byteValue() == 1) {
        return new Byte((byte)1);
      }
      return null;
    }
    
    public static Byte getCorteTransversal()
    {
      return new Byte((byte)2);
    }
    
    public static Byte getCorteLongitudinal()
    {
      return new Byte((byte)1);
    }
  }
}
