package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.util.List;

public class TipoMedicion implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_MEDICION_EN_PERIODO = 0;
  private static final byte TIPO_MEDICION_AL_PERIODO = 1;
  private byte tipoMedicionId;
  private String nombre;
  
  public TipoMedicion() {}
  
  public byte getTipoMedicionId()
  {
    return tipoMedicionId;
  }
  
  public void setTipoMedicionId(byte tipoMedicionId)
  {
    this.tipoMedicionId = tipoMedicionId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getTipoMedicionEnPeriodo()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoMedicionAlPeriodo()
  {
    return new Byte((byte)1);
  }
  
  public static List getTipoMediciones()
  {
    return getTipoMediciones(null);
  }
  
  public static List getTipoMediciones(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List tipoMediciones = new java.util.ArrayList();
    
    TipoMedicion tipoMedicion = new TipoMedicion();
    
    tipoMedicion.tipoMedicionId = 0;
    tipoMedicion.nombre = messageResources.getResource("tipomedicion.enelperiodo");
    tipoMediciones.add(tipoMedicion);
    
    tipoMedicion = new TipoMedicion();
    tipoMedicion.tipoMedicionId = 1;
    tipoMedicion.nombre = messageResources.getResource("tipomedicion.alperiodo");
    tipoMediciones.add(tipoMedicion);
    
    return tipoMediciones;
  }
  
  public static String getNombre(byte tipoMedicion)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoMedicion == 0) {
      nombre = messageResources.getResource("tipomedicion.enelperiodo");
    }
    if (tipoMedicion == 1) {
      nombre = messageResources.getResource("tipomedicion.alperiodo");
    }
    return nombre;
  }
}
