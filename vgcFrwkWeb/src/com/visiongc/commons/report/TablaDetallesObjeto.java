package com.visiongc.commons.report;

import com.lowagie.text.Table;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import javax.servlet.http.HttpServletRequest;


public class TablaDetallesObjeto
{
  private TablaBasicaPDF tabla;
  
  public TablaDetallesObjeto(ConfiguracionPagina configuracionPagina, int tamanoColumnaTitulo, int tamanoColumnaDetalle, int anchoTabla, HttpServletRequest request)
    throws Exception
  {
    tabla = new TablaBasicaPDF(configuracionPagina, request);
    
    int[] columnas = new int[2];
    
    columnas[0] = tamanoColumnaTitulo;
    columnas[1] = tamanoColumnaDetalle;
    
    tabla.setAmplitudTabla(anchoTabla);
    tabla.crearTabla(columnas);
  }
  
  public void agregarDetalle(String titulo, String detalle) throws Exception
  {
    tabla.setFormatoFont(1);
    tabla.agregarCelda(titulo);
    tabla.setFormatoFont(0);
    tabla.agregarCelda(detalle);
  }
  
  public Table getTabla()
  {
    return tabla.getTabla();
  }
}
