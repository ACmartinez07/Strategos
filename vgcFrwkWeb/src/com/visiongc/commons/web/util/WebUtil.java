package com.visiongc.commons.web.util;

import javax.servlet.http.HttpServletRequest;






public class WebUtil
{
  public WebUtil() {}
  
  public static String getUrl(HttpServletRequest request, String resource)
  {
    String result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + resource;
    result = request.getScheme() + "://" + result.replaceAll("//", "/");
    
    return result;
  }
  
  public static Boolean getValorInputCheck(HttpServletRequest request, String nombreParametro)
  {
    Boolean resultado = new Boolean(false);
    String valor = request.getParameter(nombreParametro);
    if ((valor != null) && ((valor.equalsIgnoreCase("on")) || (valor.equalsIgnoreCase("true"))))
    {
      resultado = new Boolean(true);
    }
    return resultado;
  }
}
