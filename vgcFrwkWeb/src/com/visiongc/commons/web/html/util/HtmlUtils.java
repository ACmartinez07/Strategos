package com.visiongc.commons.web.html.util;

public class HtmlUtils {
  public HtmlUtils() {}
  
  public static boolean esTamanoPorcentual(String valor) { return valor.indexOf("%") > -1; }
  
  public static int getTamanoNumerico(String valor)
  {
    String str = "";
    
    for (int i = 0; i < valor.length(); i++) {
      String s = valor.substring(i, i + 1);
      if (esNumero(s)) {
        str = str + s;
      }
    }
    return Integer.parseInt(str);
  }
  
  private static boolean esNumero(String valor) {
    if ((valor == null) || (valor.equals(""))) {
      return false;
    }
    if (valor.length() == 1) {
      return "1234567890".indexOf(valor) > -1;
    }
    for (int i = 0; i < valor.length(); i++) {
      String s = valor.substring(i, i);
      if (!esNumero(s)) {
        return false;
      }
    }
    

    return true;
  }
}
