package com.visiongc.app.strategos.reportes.model;

import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;

public class ReporteGrafico
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long reporteId;
  private String nombre;
  private String configuracion;
  private Long usuarioId;
  private Usuario usuario;
  private String descripcion;
  private Boolean publico;
  private Byte tipo;
  private Date fecha;
  private String indicadores;
  private String organizaciones;
  private String periodoIni;
  private String periodoFin;
  private String tiempo;
  private Long graficoId;
  
  public ReporteGrafico() {}
  
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
  
  public String getConfiguracion()
  {
    return configuracion;
  }
  
  public void setConfiguracion(String configuracion)
  {
    this.configuracion = configuracion;
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
    
  public Date getFecha() {
	return fecha;
  }

  public void setFecha(Date fecha) {
	this.fecha = fecha;
  }
  
  public String getIndicadores() {
	return indicadores;
  }

  public void setIndicadores(String indicadores) {
	this.indicadores = indicadores;
  }

  public String getOrganizaciones() {
	return organizaciones;
  }

  public void setOrganizaciones(String organizaciones) {
	this.organizaciones = organizaciones;
  }

  
  public String getPeriodoIni() {
	return periodoIni;
  }

  public void setPeriodoIni(String periodoIni) {
	this.periodoIni = periodoIni;
  }

  public String getPeriodoFin() {
	return periodoFin;
  }

  public void setPeriodoFin(String periodoFin) {
	this.periodoFin = periodoFin;
  }

  public String getTiempo() {
	return tiempo;
  }

  public void setTiempo(String tiempo) {
	this.tiempo = tiempo;
  }

  public Long getGraficoId() {
	return graficoId;
  }

  public void setGraficoId(Long graficoId) {
	this.graficoId = graficoId;
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
  
 
  
}
