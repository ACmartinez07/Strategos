package com.visiongc.app.strategos.planificacionseguimiento.model.util;

import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;

public class ComparatorSeguimientosProductos implements java.util.Comparator
{
  public ComparatorSeguimientosProductos() {}
  
  public int compare(Object arg0, Object arg1)
  {
    PrdSeguimientoProducto seguimientoProducto1 = (PrdSeguimientoProducto)arg0;
    PrdSeguimientoProducto seguimientoProducto2 = (PrdSeguimientoProducto)arg1;
    return seguimientoProducto1.getProducto().getNombre().compareTo(seguimientoProducto2.getProducto().getNombre());
  }
}
