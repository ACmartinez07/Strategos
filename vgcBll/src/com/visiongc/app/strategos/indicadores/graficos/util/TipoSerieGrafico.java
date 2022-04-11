package com.visiongc.app.strategos.indicadores.graficos.util;

import com.visiongc.commons.util.VgcMessageResources;
import java.util.List;

public class TipoSerieGrafico
{
  private static final byte TIPO_SERIE_GRAFICO_LINEA = 0;
  private static final byte TIPO_SERIE_GRAFICO_BARRA = 1;
  private static final byte TIPO_SERIE_GRAFICO_AREA = 3;
  private Integer tipo;
  private String nombre;
  
  public TipoSerieGrafico() {}
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Integer getTipo() {
    return tipo;
  }
  
  public void setTipo(Integer tipo) {
    this.tipo = tipo;
  }
  
  public static Byte getTipoSerieGraficoLinea() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoSerieGraficoBarra() {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoSerieGraficoArea() {
    return new Byte((byte)3);
  }
  
  public static List getTiposSerie()
  {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    List tiposSerie = new java.util.ArrayList();
    TipoSerieGrafico tipoSerie = new TipoSerieGrafico();
    
    tipoSerie = new TipoSerieGrafico();
    tipoSerie.setTipo(new Integer(-1));
    tipoSerie.setNombre(messageResources.getResource("graficoindicador.tiposserie.nograficar"));
    tiposSerie.add(tipoSerie);
    tipoSerie = new TipoSerieGrafico();
    tipoSerie.setTipo(new Integer(0));
    tipoSerie.setNombre(messageResources.getResource("graficoindicador.tiposserie.linea"));
    tiposSerie.add(tipoSerie);
    tipoSerie = new TipoSerieGrafico();
    tipoSerie.setTipo(new Integer(1));
    tipoSerie.setNombre(messageResources.getResource("graficoindicador.tiposserie.barra"));
    tiposSerie.add(tipoSerie);
    




    return tiposSerie;
  }
}
