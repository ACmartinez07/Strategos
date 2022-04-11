package com.visiongc.app.strategos.vistasdatos.model.util;

import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.util.StringUtil;
import java.util.List;

public class VistaDatosUtil implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  
  public VistaDatosUtil() {}
  
  public static List<ObjetoValorNombre> convertirCadenaLista(String cadena, String separadorElementos, String separadorAtributos)
  {
    List<ObjetoValorNombre> elementos = new java.util.ArrayList();
    
    if ((cadena != null) && (cadena != ""))
    {
      String[] arrElementos = StringUtil.split(cadena, separadorElementos);
      for (int i = 0; i < arrElementos.length; i++)
      {
        String[] arrAtributos = StringUtil.split(arrElementos[i], separadorAtributos);
        
        ObjetoValorNombre objetoValorNombre = new ObjetoValorNombre();
        objetoValorNombre.setValor(arrAtributos[0]);
        if ((arrAtributos.length > 1) && (!arrAtributos[1].equals(""))) {
          objetoValorNombre.setNombre(arrAtributos[1]);
        } else
          objetoValorNombre.setNombre("No Definido");
        if ((arrAtributos.length > 2) && (!arrAtributos[2].equals(""))) {
          objetoValorNombre.setValorOculto(arrAtributos[2]);
        }
        elementos.add(objetoValorNombre);
      }
    }
    
    return elementos;
  }
  
  public static String convertirListaCadena(List<ObjetoValorNombre> elementos, String separadorElementos, String separadorAtributos)
  {
    String cadena = "";
    if ((elementos != null) && (elementos.size() != 0))
    {
      for (int i = 0; i < elementos.size(); i++)
      {
        ObjetoValorNombre objetoValorNombre = (ObjetoValorNombre)elementos.get(i);
        cadena = cadena + objetoValorNombre.getValor() + separadorAtributos + objetoValorNombre.getNombre();
        if ((objetoValorNombre.getValorOculto() != null) && (!objetoValorNombre.getValorOculto().equals("")))
          cadena = cadena + separadorAtributos + objetoValorNombre.getValorOculto();
        cadena = cadena + separadorElementos;
      }
      
      if (!cadena.equals("")) {
        cadena = cadena.substring(0, cadena.length() - separadorElementos.length());
      }
    }
    return cadena;
  }
}
