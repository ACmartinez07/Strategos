package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoCorte implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_CORTE_LONGITUDINAL = 0;
  private static final byte TIPO_CORTE_TRANSVERSAL = 1;
  private byte tipoCorteId;
  private String nombre;
  
  public TipoCorte() {}
  
  public byte getTipoCorteId()
  {
    return tipoCorteId;
  }
  
  public void setTipoCorteId(byte tipoCorteId) {
    this.tipoCorteId = tipoCorteId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoCorteLongitudinal() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoCorteTransversal() {
    return new Byte((byte)1);
  }
  
  public static List getTipoCortes() {
    return getTipoCortes(null);
  }
  
  public static List getTipoCortes(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tipoCortes = new java.util.ArrayList();
    
    TipoCorte tipoCorte = new TipoCorte();
    
    tipoCorte.tipoCorteId = 0;
    tipoCorte.nombre = messageResources.getResource("tipocorte.longitudinal");
    tipoCortes.add(tipoCorte);
    
    tipoCorte = new TipoCorte();
    tipoCorte.tipoCorteId = 1;
    tipoCorte.nombre = messageResources.getResource("tipocorte.transversal");
    tipoCortes.add(tipoCorte);
    
    return tipoCortes;
  }
  
  public static String getNombre(byte tipoCorte) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoCorte == 0) {
      nombre = messageResources.getResource("tipocorte.longitudinal");
    }
    
    if (tipoCorte == 1) {
      nombre = messageResources.getResource("tipocorte.transversal");
    }
    return nombre;
  }
}
