package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoVariacionIndicadorIndice implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final String TIPO_VARIACION_DIFERENCIA_PORCENTUAL = "0";
  private static final String TIPO_VARIACION_DIFERENCIA_ABSOLUTA = "1";
  private static final String TIPO_VARIACION_VARIACION_PORCENTUAL = "2";
  private String tipoVariacionId;
  
  public TipoVariacionIndicadorIndice() {}
  
  public String getTipoVariacionId()
  {
    return tipoVariacionId;
  }
  
  public void setTipoVariacionId(String tipoVariacionId) {
    this.tipoVariacionId = tipoVariacionId;
  }
  
  public static String getTipoVariacionDiferenciaPorcentual() {
    return "0";
  }
  
  public static String getTipoVariacionDiferenciaAbsoluta() {
    return "1";
  }
  
  public static String getTipoVariacionVariacionPorcentual() {
    return "2";
  }
  
  public static List getTipoVariaciones() {
    return getTipoVariaciones(null);
  }
  
  public static List getTipoVariaciones(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tipoVariaciones = new java.util.ArrayList();
    
    TipoVariacionIndicadorIndice tipoVariacion = new TipoVariacionIndicadorIndice();
    tipoVariacion.tipoVariacionId = "0";
    tipoVariaciones.add(tipoVariacion);
    
    tipoVariacion = new TipoVariacionIndicadorIndice();
    tipoVariacion.tipoVariacionId = "1";
    tipoVariaciones.add(tipoVariacion);
    
    tipoVariacion = new TipoVariacionIndicadorIndice();
    tipoVariacion.tipoVariacionId = "2";
    tipoVariaciones.add(tipoVariacion);
    
    return tipoVariaciones;
  }
}
