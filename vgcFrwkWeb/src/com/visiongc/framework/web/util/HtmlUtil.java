package com.visiongc.framework.web.util;

import com.visiongc.commons.util.StringUtil;

public class HtmlUtil {
  public HtmlUtil() {}
  
  public static int getTamanoPixeles(String str) {
    if (str != null)
    {
      String[] resultado = StringUtil.split(str, "p");
      return Integer.parseInt(resultado[0]);
    }
    
    return 0;
  }
  
  public static boolean isTamanoEnPixeles(String str)
  {
    if (str == null)
      return false;
    if (str.equals(""))
      return false;
    if (str.indexOf("%") > -1) {
      return false;
    }
    return true;
  }
  








  public static String trimTextoHtml(String str)
  {
    int tamano = 0;
    String buscados = "\n\r\t ";
    String caracter = null;
    if ((str != null) && (str.length() > 0))
    {
      tamano = str.length();
      caracter = str.substring(0, 1);
      while (buscados.indexOf(caracter) > -1)
      {
        str = str.substring(1);
        tamano--;
        if (tamano > 0) {
          caracter = str.substring(0, 1);
        } else {
          caracter = "a";
        }
      }
    }
    
    if (tamano > 0)
    {
      caracter = str.substring(str.length() - 1, str.length());
      while (buscados.indexOf(caracter) > -1)
      {
        str = str.substring(0, str.length() - 1);
        tamano--;
        if (tamano > 0) {
          caracter = str.substring(str.length() - 1, str.length());
        } else {
          caracter = "a";
        }
      }
    }
    return str;
  }
  
  public String addString(String valor, Integer tamano, Byte posicion)
  {
    String valorReturn = "";
    if (tamano.intValue() > 0)
    {
      if (posicion.byteValue() == 0) {
        valorReturn = tamano.intValue() + valor;
      }
      else {
        valorReturn = valor;
        for (int k = 0; k < tamano.intValue() - valor.length(); k++) {
          valorReturn = valorReturn + "&nbsp;";
        }
      }
    } else {
      valorReturn = valor;
    }
    return valorReturn;
  }
}
