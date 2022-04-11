package com.visiongc.app.strategos.explicaciones.model.util;

public class TipoMemoExplicacion
{
  public static final byte TIPO_MEMO_EXPLICACION_DESCRIPCION = 0;
  public static final byte TIPO_MEMO_EXPLICACION_CAUSAS = 1;
  public static final byte TIPO_MEMO_EXPLICACION_CORRECTIVOS = 2;
  public static final byte TIPO_MEMO_EXPLICACION_IMPACTOS = 3;
  public static final byte TIPO_MEMO_EXPLICACION_PERSPECTIVAS = 4;
  public static final byte TIPO_MEMO_EXPLICACION_URL = 5;
  public static final byte TIPO_MEMO_EXPLICACION_LOGRO1 = 6;
  public static final byte TIPO_MEMO_EXPLICACION_LOGRO2 = 7;
  public static final byte TIPO_MEMO_EXPLICACION_LOGRO3 = 8;
  public static final byte TIPO_MEMO_EXPLICACION_LOGRO4 = 9;
  private byte tipoMemoId;
  private String nombre;
  
  public TipoMemoExplicacion() {}
  
  public byte getTipoMemoId() {
    return tipoMemoId;
  }
  
  public void setTipoMemoId(byte tipoMemoId)
  {
    this.tipoMemoId = tipoMemoId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
}
