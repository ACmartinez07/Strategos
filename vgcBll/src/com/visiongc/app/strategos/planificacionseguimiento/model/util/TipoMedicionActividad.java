package com.visiongc.app.strategos.planificacionseguimiento.model.util;

public class TipoMedicionActividad {
  public static final byte EN_EL_PERIODO = 0;
  public static final byte AL_PERIODO = 1;
  private byte tipoMedicionId;
  private String nombre;
  
  public TipoMedicionActividad() {}
  
  public byte getTipoMedicionId() {
    return tipoMedicionId;
  }
  
  public void setTipoMedicionId(byte tipoMedicionId) { this.tipoMedicionId = tipoMedicionId; }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) { this.nombre = nombre; }
}
