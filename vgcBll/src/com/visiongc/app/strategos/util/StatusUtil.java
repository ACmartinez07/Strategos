package com.visiongc.app.strategos.util;

import java.io.Serializable;





public class StatusUtil
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte STATUS_SUCCESS = 0;
  private static final byte STATUS_INVALIDO = 1;
  private static final byte STATUS_NOSUCCESS = 2;
  private static final byte STATUS_RECORDDUPLICATE = 3;
  private static final byte STATUS_SUCCESS_MODIFY = 4;
  private static final byte STATUS_INIT = 5;
  private static final byte STATUS_ALERT_NOT_DEFINIDA = 6;
  
  public StatusUtil() {}
  
  public static Byte getStatusInit()
  {
    return new Byte((byte)5);
  }
  
  public static Byte getStatusSuccess()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getStatusSuccessModify()
  {
    return new Byte((byte)4);
  }
  
  public static Byte getStatusNoSuccess()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getStatusInvalido()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getStatusRegistroDuplicado()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getStatusAlertaNotDefinida()
  {
    return new Byte((byte)6);
  }
}
