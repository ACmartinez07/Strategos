package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoIndicador implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_INDICADOR_RESULTADO = 0;
  private static final byte TIPO_INDICADOR_GUIA = 1;
  private byte tipoId;
  private String nombre;
  
  public TipoIndicador() {}
  
  public byte getTipoId()
  {
    return tipoId;
  }
  
  public void setTipoId(byte tipoId) {
    this.tipoId = tipoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoIndicadorResultado() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoIndicadorGuia() {
    return new Byte((byte)1);
  }
  
  public static List getTipos() {
    return getTipos(null);
  }
  
  public static List getTipos(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tipos = new java.util.ArrayList();
    
    TipoIndicador tipo = new TipoIndicador();
    
    tipo.tipoId = 0;
    tipo.nombre = messageResources.getResource("tipoindicador.resultado");
    tipos.add(tipo);
    
    tipo = new TipoIndicador();
    tipo.tipoId = 1;
    tipo.nombre = messageResources.getResource("tipoindicador.guia");
    tipos.add(tipo);
    
    return tipos;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 0) {
      nombre = messageResources.getResource("tipoindicador.resultado");
    }
    
    if (tipo == 1) {
      nombre = messageResources.getResource("tipoindicador.guia");
    }
    return nombre;
  }
}
