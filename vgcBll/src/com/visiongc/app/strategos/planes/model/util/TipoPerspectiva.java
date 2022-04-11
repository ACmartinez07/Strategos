package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoPerspectiva implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_PERSPECTIVA_PERSPECTIVA = 0;
  private static final byte TIPO_PERSPECTIVA_OBJETIVO = 1;
  private byte tipoId;
  private String nombre;
  
  public TipoPerspectiva() {}
  
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
  
  public static Byte getTipoPerspectivaPerspectiva() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoPerspectivaObjetivo() {
    return new Byte((byte)1);
  }
  
  public static List getTipos() {
    return getTipos(null);
  }
  
  public static List getTipos(VgcMessageResources messageResources) {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List tipos = new java.util.ArrayList();
    TipoPerspectiva tipo = new TipoPerspectiva();
    tipo.tipoId = 0;
    tipo.nombre = messageResources.getResource("perspectiva.tipo.perspectiva");
    tipos.add(tipo);
    tipo = new TipoPerspectiva();
    tipo.tipoId = 1;
    tipo.nombre = messageResources.getResource("perspectiva.tipo.objetivo");
    tipos.add(tipo);
    return tipos;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    if (tipo == 0) {
      nombre = messageResources.getResource("perspectiva.tipo.perspectiva");
    }
    if (tipo == 1) {
      nombre = messageResources.getResource("perspectiva.tipo.objetivo");
    }
    return nombre;
  }
}
