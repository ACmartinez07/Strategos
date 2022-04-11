package com.visiongc.app.strategos.presentaciones.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import java.util.List;

public class TipoCelda
{
  private static final byte TIPO_CELDA_GRAFICO = 1;
  private static final byte TIPO_CELDA_MEDIDOR = 2;
  private byte tipoCeldaId;
  private String nombre;
  
  public TipoCelda() {}
  
  public static TipoCelda getTipoCelda(byte tipo)
  {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    TipoCelda tipoCelda = new TipoCelda();
    
    if (tipo == 1) {
      tipoCelda.tipoCeldaId = 1;
      tipoCelda.nombre = messageResources.getResource("tipocelda.grafico");
    }
    
    if (tipo == 2) {
      tipoCelda.tipoCeldaId = 2;
      tipoCelda.nombre = messageResources.getResource("tipocelda.medidor");
    }
    
    return tipoCelda;
  }
  
  public static List getTiposCelda()
  {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    List tiposCelda = new java.util.ArrayList();
    
    TipoCelda tipoCelda = new TipoCelda();
    tipoCelda.tipoCeldaId = 1;
    tipoCelda.nombre = messageResources.getResource("tipocelda.grafico");
    tiposCelda.add(tipoCelda);
    
    tipoCelda = new TipoCelda();
    tipoCelda.tipoCeldaId = 2;
    tipoCelda.nombre = messageResources.getResource("tipocelda.medidor");
    tiposCelda.add(tipoCelda);
    
    return tiposCelda;
  }
  
  public static Byte getTipoCeldaGrafico() {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoCeldaMedidor() {
    return new Byte((byte)2);
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public byte getTipoCeldaId() {
    return tipoCeldaId;
  }
  
  public void setTipoCeldaId(byte tipoCeldaId) {
    this.tipoCeldaId = tipoCeldaId;
  }
}
