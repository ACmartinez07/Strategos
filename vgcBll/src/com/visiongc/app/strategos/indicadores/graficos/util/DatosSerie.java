package com.visiongc.app.strategos.indicadores.graficos.util;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import java.util.List;

public class DatosSerie
{
  private Long id;
  private Integer tipoSerie;
  private com.visiongc.app.strategos.indicadores.model.SerieIndicador serieIndicador;
  private SerieTiempo serieTiempo;
  private Boolean visualizar;
  private String color;
  private String colorDecimal;
  private String colorEntero;
  private List<com.visiongc.app.strategos.indicadores.model.Medicion> mediciones;
  private Boolean serieAlerta;
  private Indicador indicador;
  private String nombreLeyenda;
  private Long planId;
  private Boolean showOrganizacion;
  private String organizacionName;
  private Integer nivelClase;
  private String pathClase;
  private Boolean bloquear;
  private Boolean serieAnoAnterior;
  
  public DatosSerie() {}
  
  public Long getId()
  {
    return id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Boolean getSerieAlerta()
  {
    return serieAlerta;
  }
  
  public void setSerieAlerta(Boolean serieAlerta)
  {
    this.serieAlerta = serieAlerta;
  }
  
  public Integer getTipoSerie()
  {
    return tipoSerie;
  }
  
  public void setTipoSerie(Integer tipoSerie)
  {
    this.tipoSerie = tipoSerie;
  }
  
  public com.visiongc.app.strategos.indicadores.model.SerieIndicador getSerieIndicador()
  {
    return serieIndicador;
  }
  
  public void setSerieIndicador(com.visiongc.app.strategos.indicadores.model.SerieIndicador serieIndicador)
  {
    this.serieIndicador = serieIndicador;
  }
  
  public SerieTiempo getSerieTiempo()
  {
    return serieTiempo;
  }
  
  public void setSerieTiempo(SerieTiempo serieTiempo)
  {
    this.serieTiempo = serieTiempo;
  }
  
  public Boolean getVisualizar()
  {
    return visualizar;
  }
  
  public void setVisualizar(Boolean visualizar)
  {
    this.visualizar = visualizar;
  }
  
  public String getColor()
  {
    return color;
  }
  
  public void setColor(String color)
  {
    this.color = color;
  }
  
  public String getColorDecimal()
  {
    return colorDecimal;
  }
  
  public void setColorDecimal(String colorDecimal)
  {
    this.colorDecimal = colorDecimal;
  }
  
  public String getColorEntero()
  {
    return colorEntero;
  }
  
  public void setColorEntero(String colorEntero)
  {
    this.colorEntero = colorEntero;
  }
  
  public List<com.visiongc.app.strategos.indicadores.model.Medicion> getMediciones()
  {
    return mediciones;
  }
  
  public void setMediciones(List<com.visiongc.app.strategos.indicadores.model.Medicion> mediciones)
  {
    this.mediciones = mediciones;
  }
  
  public Indicador getIndicador()
  {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public String getNombreLeyenda()
  {
    return nombreLeyenda;
  }
  
  public void setNombreLeyenda(String nombreLeyenda)
  {
    this.nombreLeyenda = nombreLeyenda;
  }
  
  public Long getPlanId()
  {
    return planId;
  }
  
  public void setPlanId(Long planId)
  {
    this.planId = planId;
  }
  
  public Boolean getShowOrganizacion()
  {
    return showOrganizacion;
  }
  
  public void setShowOrganizacion(Boolean showOrganizacion)
  {
    this.showOrganizacion = showOrganizacion;
  }
  
  public String getOrganizacionName()
  {
    return organizacionName;
  }
  
  public void setOrganizacionName(String organizacionName)
  {
    this.organizacionName = organizacionName;
  }
  
  public Integer getNivelClase()
  {
    return nivelClase;
  }
  
  public void setNivelClase(Integer nivelClase)
  {
    this.nivelClase = nivelClase;
  }
  
  public String getPathClase()
  {
    return pathClase;
  }
  
  public void setPathClase(String pathClase)
  {
    this.pathClase = pathClase;
  }
  
  public Boolean getBloquear()
  {
    return bloquear;
  }
  
  public void setBloquear(Boolean bloquear)
  {
    this.bloquear = bloquear;
  }
  
  public Boolean getSerieAnoAnterior()
  {
    return serieAnoAnterior;
  }
  
  public void setSerieAnoAnterior(Boolean serieAnoAnterior)
  {
    this.serieAnoAnterior = serieAnoAnterior;
  }
}
