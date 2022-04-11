package com.visiongc.framework.web.struts.taglib.interfaz.util;

import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.xmldata.XmlAtributo;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.framework.configuracion.VgcObjetoConfigurable;
import java.util.Iterator;





public class VisorListaInfo
  implements VgcObjetoConfigurable
{
  private ListaMap columnas;
  
  public VisorListaInfo() {}
  
  public ListaMap getColumnas()
  {
    return columnas;
  }
  
  public void setColumnas(ListaMap columnas) {
    this.columnas = columnas;
  }
  
  public XmlNodo getConfiguracion()
  {
    return getConfiguracionBase();
  }
  
  public XmlNodo getConfiguracionBase()
  {
    XmlNodo configuracionVisorLista = new XmlNodo("configuracion.visorLista");
    
    XmlNodo columnas = new XmlNodo();
    columnas.setId("columnas");
    


    int orden = 0;
    
    for (Iterator iter = this.columnas.iterator(); iter.hasNext();)
    {
      ColumnaVisorListaInfo columnaInfo = (ColumnaVisorListaInfo)iter.next();
      
      orden++;
      

      XmlNodo columna = new XmlNodo();
      columna.setId(columnaInfo.getNombre());
      XmlAtributo atributo = new XmlAtributo();
      atributo.setNombre("orden");
      String strOrden = Integer.toString(orden);
      if (orden < 10) {
        strOrden = "0" + strOrden;
      }
      atributo.setValor(strOrden);
      columna.addAtributo(atributo);
      atributo = new XmlAtributo();
      atributo.setNombre("titulo");
      atributo.setValor(columnaInfo.getTitulo());
      columna.addAtributo(atributo);
      atributo = new XmlAtributo();
      atributo.setNombre("ancho");
      atributo.setValor(columnaInfo.getAncho());
      columna.addAtributo(atributo);
      atributo = new XmlAtributo();
      atributo.setNombre("visible");
      atributo.setValor("true");
      columna.addAtributo(atributo);
      columnas.addElemLista(columna, columna.getId());
    }
    
    configuracionVisorLista.addElemLista(columnas, "columnas");
    
    return configuracionVisorLista;
  }
  
  public boolean configuracionesSonIguales(XmlNodo configuracion1, XmlNodo configuracion2)
  {
    ListaMap columnas1 = ((XmlNodo)configuracion1.getElemLista("columnas")).getLista();
    ListaMap columnas2 = ((XmlNodo)configuracion2.getElemLista("columnas")).getLista();
    
    if (columnas1.size() != columnas2.size()) {
      return false;
    }
    
    for (Iterator iter = columnas1.iterator(); iter.hasNext();) {
      XmlNodo confColumna1 = (XmlNodo)iter.next();
      
      if (columnas2.get(confColumna1.getId()) == null) {
        return false;
      }
    }
    
    for (Iterator iter = columnas2.iterator(); iter.hasNext();) {
      XmlNodo confColumna2 = (XmlNodo)iter.next();
      
      if (columnas1.get(confColumna2.getId()) == null) {
        return false;
      }
    }
    
    return true;
  }
}
