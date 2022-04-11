package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoPerspectivaEstado implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_PERSPECTIVA_ESTADO_ANUAL = 0;
  private static final byte TIPO_PERSPECTIVA_ESTADO_PARCIAL = 1;
  private byte tipoId;
  private String nombre;
  
  public TipoPerspectivaEstado() {}
  
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
  
  public static Byte getTipoPerspectivaEstadoParcial() {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoPerspectivaEstadoAnual() {
    return new Byte((byte)0);
  }
  
  public static List getTipos() {
    return getTipos(null);
  }
  
  public static List getTipos(VgcMessageResources messageResources) {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List tipos = new java.util.ArrayList();
    TipoPerspectivaEstado tipo = new TipoPerspectivaEstado();
    tipo.tipoId = 0;
    tipo.nombre = messageResources.getResource("perspectiva.tipo.estado.anual");
    tipos.add(tipo);
    tipo = new TipoPerspectivaEstado();
    tipo.tipoId = 1;
    tipo.nombre = messageResources.getResource("perspectiva.tipo.estado.parcial");
    tipos.add(tipo);
    return tipos;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    if (tipo == 0) {
      nombre = messageResources.getResource("perspectiva.tipo.estado.anual");
    } else if (tipo == 1) {
      nombre = messageResources.getResource("perspectiva.tipo.estado.parcial");
    }
    return nombre;
  }
}
