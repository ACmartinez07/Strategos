package com.visiongc.app.strategos.model.util;

public class VgcReturnCodeStrategos {
  public static final int CALCULO_DB_NOT_FOUND_PARENT_INDICADOR = 10000;
  public static final int CALCULO_DB_NOT_FOUND_INSUMOS_FORMULA = 10001;
  public static final int CALCULO_DB_FAILURE_PROCESSING_INDICADOR = 10002;
  public static final int CALCULO_DB_NULL_MEDICION_INSUMO = 10003;
  public static final int CALCULO_DB_VALOR_NO_NUMERICO = 10004;
  
  public VgcReturnCodeStrategos() {}
  
  public static String getCode(int inCode) {
    if (inCode == 10000) {
      return "CALCULO_DB_NOT_FOUND_PARENT_INDICADOR";
    }
    if (inCode == 10001) {
      return "CALCULO_DB_NOT_FOUND_INSUMOS_FORMULA";
    }
    if (inCode == 10002) {
      return "CALCULO_DB_FAILURE_PROCESSING_INDICADOR";
    }
    if (inCode == 10003) {
      return "CALCULO_DB_NULL_MEDICION_INSUMO";
    }
    if (inCode == 10004) {
      return "CALCULO_DB_VALOR_NO_NUMERICO";
    }
    return Integer.toString(inCode);
  }
}
