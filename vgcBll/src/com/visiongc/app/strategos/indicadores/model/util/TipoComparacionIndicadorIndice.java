package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoComparacionIndicadorIndice implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final String TIPO_COMPARACION_ANO_ACTUAL_PERIODO_ANTERIOR = "-3";
  private static final String TIPO_COMPARACION_ANO_ANTERIOR_MISMO_PERIODO = "-2";
  private static final String TIPO_COMPARACION_ANO_ACTUAL_PRIMER_PERIODO = "1";
  private static final String TIPO_COMPARACION_ANO_ACTUAL_PERIODO_CIERRE_ANTERIOR = "-1";
  private String tipoComparacionId;
  private String nombre;
  
  public TipoComparacionIndicadorIndice() {}
  
  public String getTipoComparacionId()
  {
    return tipoComparacionId;
  }
  
  public void setTipoComparacionId(String tipoComparacionId) {
    this.tipoComparacionId = tipoComparacionId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static List getTipoComparacionesIndicadorIndice() {
    return getTipoComparacionesIndicadorIndice(null);
  }
  
  public static String getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice() {
    return "-3";
  }
  
  public static String getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice() {
    return "-2";
  }
  
  public static String getTipoComparacionAnoActualPrimerPeriodoIndicadorIndice() {
    return "1";
  }
  
  public static String getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice() {
    return "-1";
  }
  
  public static List getTipoComparacionesIndicadorIndice(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tipoComparaciones = new java.util.ArrayList();
    
    TipoComparacionIndicadorIndice tipoComparacion = new TipoComparacionIndicadorIndice();
    tipoComparacion.tipoComparacionId = "-3";
    tipoComparacion.nombre = messageResources.getResource("indicador.indice.anoactualperiodoanterior");
    tipoComparaciones.add(tipoComparacion);
    
    tipoComparacion = new TipoComparacionIndicadorIndice();
    tipoComparacion.tipoComparacionId = "-2";
    tipoComparacion.nombre = messageResources.getResource("indicador.indice.anoanteriormismoperiodo");
    tipoComparaciones.add(tipoComparacion);
    
    tipoComparacion = new TipoComparacionIndicadorIndice();
    tipoComparacion.tipoComparacionId = "1";
    tipoComparacion.nombre = messageResources.getResource("indicador.indice.anoactualprimerperiodo");
    tipoComparaciones.add(tipoComparacion);
    
    tipoComparacion = new TipoComparacionIndicadorIndice();
    tipoComparacion.tipoComparacionId = "-1";
    tipoComparacion.nombre = messageResources.getResource("indicador.indice.anoactualperiodocierreanterior");
    tipoComparaciones.add(tipoComparacion);
    
    return tipoComparaciones;
  }
  
  public static String getNombre(String tipoComparacionId) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoComparacionId.equals("-3")) {
      nombre = messageResources.getResource("indicador.indice.anoactualperiodoanterior");
    } else if (tipoComparacionId.equals("-2")) {
      nombre = messageResources.getResource("indicador.indice.anoanteriormismoperiodo");
    } else if (tipoComparacionId.equals("1")) {
      nombre = messageResources.getResource("indicador.indice.anoactualprimerperiodo");
    } else if (tipoComparacionId.equals("-1")) {
      nombre = messageResources.getResource("indicador.indice.anoactualperiodocierreanterior");
    }
    return nombre;
  }
  
  public static String getFormula(String tipoComparacionId) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoComparacionId.equals("-3")) {
      nombre = messageResources.getResource("indicador.indice.anoactualperiodoanterior.formula");
    } else if (tipoComparacionId.equals("-2")) {
      nombre = messageResources.getResource("indicador.indice.anoanteriormismoperiodo.formula");
    } else if (tipoComparacionId.equals("1")) {
      nombre = messageResources.getResource("indicador.indice.anoactualprimerperiodo.formula");
    } else if (tipoComparacionId.equals("-1")) {
      nombre = messageResources.getResource("indicador.indice.anoactualperiodocierreanterior.formula");
    }
    return nombre;
  }
}
