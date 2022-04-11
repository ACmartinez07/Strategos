package com.visiongc.app.strategos.util;

import java.io.Serializable;






public class TipoObjeto
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte OBJETO_INDICADOR = 1;
  private static final byte OBJETO_INCIATIVA = 2;
  private static final byte OBJETO_ACTIVIDAD = 3;
  private static final byte OBJETO_PERSPECTIVA = 4;
  private static final byte OBJETO_PLAN = 5;
  
  public TipoObjeto() {}
  
  public static Byte getObjetoIndicador()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getObjetoIniciativa()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getObjetoActividad()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getObjetoPerspectiva()
  {
    return new Byte((byte)4);
  }
  
  public static Byte getObjetoPlan()
  {
    return new Byte((byte)5);
  }
}
