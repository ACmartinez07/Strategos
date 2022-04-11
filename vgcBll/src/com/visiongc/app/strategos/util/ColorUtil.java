package com.visiongc.app.strategos.util;

public class ColorUtil {
  public ColorUtil() {}
  
  public static String getRndColorRGB() {
    int valor = 0;
    String strColorRGB = "#";
    
    for (int i = 1; i <= 3; i++)
    {
      valor = (int)Math.ceil(255.0D * Math.random());
      if (valor > 16) {
        strColorRGB = strColorRGB + Integer.toHexString(valor);
      } else {
        strColorRGB = strColorRGB + "0" + Integer.toHexString(valor);
      }
    }
    if (strColorRGB.length() < 7)
    {
      switch (strColorRGB.length())
      {
      case 2: 
        strColorRGB = strColorRGB + "00000";
        break;
      case 3: 
        strColorRGB = strColorRGB + "0000";
        break;
      case 4: 
        strColorRGB = strColorRGB + "000";
        break;
      case 5: 
        strColorRGB = strColorRGB + "00";
        break;
      case 6: 
        strColorRGB = strColorRGB + "0";
      }
      
    }
    
    return strColorRGB;
  }
  
  public static String convertRGBDecimal(String colorRGB)
  {
    String valor = "";
    String colorDecimal = "";
    
    if (colorRGB.substring(0, 1).equals("#")) {
      colorRGB = colorRGB.substring(1);
    }
    if (colorRGB.length() < 6)
    {
      switch (colorRGB.length())
      {
      case 1: 
        colorRGB = colorRGB + "00000";
        break;
      case 2: 
        colorRGB = colorRGB + "0000";
        break;
      case 3: 
        colorRGB = colorRGB + "000";
        break;
      case 4: 
        colorRGB = colorRGB + "00";
        break;
      case 5: 
        colorRGB = colorRGB + "0";
      }
      
    }
    
    for (int i = 1; i <= 5; i += 2)
    {
      valor = colorRGB.substring(i - 1, i + 1);
      
      colorDecimal = colorDecimal + "PF" + Integer.parseInt(valor, 16);
    }
    
    colorDecimal = colorDecimal + "PF";
    
    return colorDecimal;
  }
  
  public static String getRGBColor()
  {
    int valor = 0;
    String strColorRGB = "";
    
    for (int i = 1; i <= 3; i++)
    {
      if (i > 1)
        strColorRGB = strColorRGB + "#";
      valor = (int)Math.ceil(255.0D * Math.random());
      strColorRGB = strColorRGB + valor;
    }
    
    return strColorRGB;
  }
}
