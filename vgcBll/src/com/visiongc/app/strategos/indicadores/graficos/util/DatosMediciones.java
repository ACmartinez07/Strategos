package com.visiongc.app.strategos.indicadores.graficos.util;

import java.util.List;

public class DatosMediciones {
  private Long indicadorId;
  private Byte alerta;
  private Boolean formatoPeriodoAno;
  private Boolean drillDown;
  private List medicionesSerieReal;
  private Integer sizeMedicionesSerieReal;
  private List medicionesSerieComparacion;
  private Integer sizeMedicionesSerieComparacion;
  private String nombreSerieComparacion;
  
  public DatosMediciones() {}
  
  public String getNombreSerieComparacion() {
    return nombreSerieComparacion;
  }
  
  public void setNombreSerieComparacion(String nombreSerieComparacion) {
    this.nombreSerieComparacion = nombreSerieComparacion;
  }
  
  public Integer getSizeMedicionesSerieReal()
  {
    return sizeMedicionesSerieReal;
  }
  
  public void setSizeMedicionesSerieReal(Integer sizeMedicionesSerieReal) {
    this.sizeMedicionesSerieReal = sizeMedicionesSerieReal;
  }
  
  public Integer getSizeMedicionesSerieComparacion() {
    return sizeMedicionesSerieComparacion;
  }
  
  public void setSizeMedicionesSerieComparacion(Integer sizeMedicionesSerieComparacion) {
    this.sizeMedicionesSerieComparacion = sizeMedicionesSerieComparacion;
  }
  
  public Long getIndicadorId() {
    return indicadorId;
  }
  
  public void setIdicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }
  
  public Byte getAlerta() {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
  }
  
  public Boolean getFormatoPeriodoAno() {
    return formatoPeriodoAno;
  }
  
  public void setFormatoPeriodoAno(Boolean formatoPeriodoAno) {
    this.formatoPeriodoAno = formatoPeriodoAno;
  }
  
  public Boolean getDrillDown() {
    return drillDown;
  }
  
  public void setDrillDown(Boolean drillDown) {
    this.drillDown = drillDown;
  }
  
  public List getMedicionesSerieReal() {
    return medicionesSerieReal;
  }
  
  public void setMedicionesSerieReal(List medicionesSerieReal) {
    this.medicionesSerieReal = medicionesSerieReal;
  }
  
  public List getMedicionesSerieComparacion() {
    return medicionesSerieComparacion;
  }
  
  public void setMedicionesSerieComparacion(List medicionesSerieComparacion) {
    this.medicionesSerieComparacion = medicionesSerieComparacion;
  }
}
