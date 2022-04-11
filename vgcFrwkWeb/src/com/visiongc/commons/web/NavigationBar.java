package com.visiongc.commons.web;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.Stack;

public class NavigationBar
{
  private Stack barra = null;
  
  public NavigationBar() {
    barra = new Stack();
  }
  
  public void clear() {
    barra.clear();
  }
  
  public Stack getBarra() {
    return barra;
  }
  






  public void agregarUrlSinParametros(String inURL, String inNombre)
  {
    agregarUrl(inURL, inNombre, false, null, true);
  }
  







  public void agregarUrlSinParametros(String inURL, String inNombre, Integer posicion)
  {
    agregarUrl(inURL, inNombre, false, posicion, true);
  }
  









  public void agregarUrl(String url, String nombre, boolean reset, Integer position, boolean sinParametros)
  {
    try
    {
      if (reset) {
        barra.clear();
      }
      
      if ((nombre == null) || (nombre.equals(""))) {
        nombre = url;
      }
      
      if (sinParametros) {
        int index = url.indexOf("?");
        
        if (index > -1) {
          url = url.substring(0, index);
        }
      }
      NavigationUrl objUrl = new NavigationUrl(nombre, url);
      
      if (position == null)
      {
        String url1 = "";
        int index1 = 0;
        String url2 = "";
        int index2 = 0;
        url2 = url;
        index2 = url2.indexOf("?");
        if (index2 >= 0) {
          url2 = url2.substring(0, index2);
        }
        for (int i = 0; i < barra.size(); i++) {
          NavigationUrl objUrlTemp = (NavigationUrl)barra.get(i);
          
          url1 = objUrlTemp.getUrl();
          index1 = url1.indexOf("?");
          if (index1 >= 0) {
            url1 = url1.substring(0, index1);
          }
          if (url1.equals(url2))
          {
            for (int y = barra.size(); y > i; y--) {
              barra.pop();
            }
            break;
          }
        }
      }
      else if ((position.intValue() > 0) && (position.intValue() <= barra.size()))
      {



        barra.setSize(position.intValue() - 1);
      }
      

      barra.push(objUrl);
    } catch (Throwable e) {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  






  public void agregarUrl(String inURL, String inNombre)
  {
    agregarUrl(inURL, inNombre, false, null, false);
  }
  







  public void agregarUrl(String inURL, String inNombre, Integer position)
  {
    agregarUrl(inURL, inNombre, false, position, false);
  }
  
  public void agregarUrlNoExistente(String url, String nombre) {
    try {
      if ((nombre == null) || (nombre.equals(""))) {
        nombre = url;
      }
      
      NavigationUrl objUrl = new NavigationUrl(nombre, url);
      

      boolean existe = false;
      String url1 = "";
      int index1 = 0;
      String url2 = "";
      int index2 = 0;
      url2 = url;
      index2 = url2.indexOf("?");
      if (index2 >= 0) {
        url2 = url2.substring(0, index2);
      }
      for (int i = 0; i < barra.size(); i++) {
        NavigationUrl objUrlTemp = (NavigationUrl)barra.get(i);
        
        url1 = objUrlTemp.getUrl();
        index1 = url1.indexOf("?");
        if (index1 >= 0) {
          url1 = url1.substring(0, index1);
        }
        if (url1.equals(url2)) {
          existe = true;
          
          for (int y = barra.size() - 1; y > i; y--) {
            barra.pop();
          }
          break;
        }
      }
      
      if (!existe) {
        barra.push(objUrl);
      }
    } catch (Throwable e) {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  
  public String getPenultimoUrl() {
    if (barra.size() > 1) {
      NavigationUrl url = (NavigationUrl)barra.get(barra.size() - 2);
      return url.getUrl();
    }
    return "";
  }
  







  public String getUrl(int index)
  {
    if (barra.size() > index - 1) {
      NavigationUrl url = (NavigationUrl)barra.get(barra.size() - index);
      return url.getUrl();
    }
    return "";
  }
}
