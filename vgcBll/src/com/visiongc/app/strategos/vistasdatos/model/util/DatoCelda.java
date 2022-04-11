package com.visiongc.app.strategos.vistasdatos.model.util;

import java.io.Serializable;

public class DatoCelda implements Serializable
{
  static final long serialVersionUID = 0L;
  private String valor;
  private String alineacion;
  private Boolean esAlerta;
  private Boolean esEncabezado;
  private String nombre;
  private String ancho;
  
  public DatoCelda() {}
  
  public String getAlineacion() {
    return alineacion;
  }
  
  public void setAlineacion(String alineacion)
  {
    this.alineacion = alineacion;
  }
  
  public Boolean getEsAlerta()
  {
    return esAlerta;
  }
  
  public void setEsAlerta(Boolean esAlerta)
  {
    this.esAlerta = esAlerta;
  }
  
  public String getValor()
  {
    return valor;
  }
  
  public void setValor(String valor)
  {
    this.valor = valor;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Boolean getEsEncabezado()
  {
    return esEncabezado;
  }
  
  public void setEsEncabezado(Boolean esEncabezado)
  {
    this.esEncabezado = esEncabezado;
  }
  
  public String getAncho()
  {
    return ancho;
  }
  
  public void setAncho(String ancho)
  {
    this.ancho = ancho;
  }
}
