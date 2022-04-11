package com.visiongc.app.strategos.iniciativas.model.util;

public class TipoCalculoEstadoIniciativa {
  private static final byte TIPO_CALCULO_ESTADO_ACTIVIDADES = 0;
  private static final byte TIPO_CALCULO_ESTADO_SEGUIMIENTOS = 1;
  private byte tipoCalculoId;
  private String nombre;
  
  public TipoCalculoEstadoIniciativa() {}
  
  public byte getTipoCalculoId() {
    return tipoCalculoId;
  }
  
  public void setTipoCalculoId(byte tipoCalculoId) {
    this.tipoCalculoId = tipoCalculoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoCalculoEstadoIniciativaPorActividades() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoCalculoEstadoIniciativaPorSeguimientos() {
    return new Byte((byte)1);
  }
}
