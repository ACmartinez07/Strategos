package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoAsociadoIndicador implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_ASOCIADO_INDICADOR_MINIMO = 0;
  private static final byte TIPO_ASOCIADO_INDICADOR_MAXIMO = 1;
  private static final byte TIPO_ASOCIADO_INDICADOR_PROGRAMADO = 2;
  private byte tipoAsociadoId;
  private String nombre;
  
  public TipoAsociadoIndicador() {}
  
  public byte getTipoAsociadoId()
  {
    return tipoAsociadoId;
  }
  
  public void setTipoAsociadoId(byte tipoAsociadoId) {
    this.tipoAsociadoId = tipoAsociadoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoAsociadoIndicadorMinimo() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoAsociadoIndicadorMaximo() {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoAsociadoIndicadorProgramado() {
    return new Byte((byte)2);
  }
  
  public static List getTiposAsociado() {
    return getTiposAsociados(null);
  }
  
  public static List getTiposAsociados(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tiposAsociado = new java.util.ArrayList();
    
    TipoAsociadoIndicador tipoAsociado = new TipoAsociadoIndicador();
    tipoAsociado.tipoAsociadoId = 0;
    tipoAsociado.nombre = messageResources.getResource("indicador.tipoasociado.minimo");
    tiposAsociado.add(tipoAsociado);
    
    tipoAsociado = new TipoAsociadoIndicador();
    tipoAsociado.tipoAsociadoId = 1;
    tipoAsociado.nombre = messageResources.getResource("indicador.tipoasociado.maximo");
    tiposAsociado.add(tipoAsociado);
    
    tipoAsociado = new TipoAsociadoIndicador();
    tipoAsociado.tipoAsociadoId = 2;
    tipoAsociado.nombre = messageResources.getResource("indicador.tipoasociado.programado");
    tiposAsociado.add(tipoAsociado);
    
    return tiposAsociado;
  }
  
  public static String getNombre(byte tipoAsociado) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoAsociado == 0) {
      nombre = messageResources.getResource("indicador.tipoasociado.minimo");
    } else if (tipoAsociado == 1) {
      nombre = messageResources.getResource("indicador.tipoasociado.maximo");
    } else if (tipoAsociado == 2) {
      nombre = messageResources.getResource("indicador.tipoasociado.programado");
    }
    
    return nombre;
  }
}
