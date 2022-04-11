package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoPlan implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_PLAN_ESTRATEGICO = 0;
  private static final byte TIPO_PLAN_OPERATIVO = 2;
  private byte tipoId;
  private String nombre;
  
  public TipoPlan() {}
  
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
  
  public static Byte getTipoPlanEstrategico() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoPlanOperativo() {
    return new Byte((byte)2);
  }
  
  public static List getTipos() {
    return getTipos(null);
  }
  
  public static List getTipos(VgcMessageResources messageResources) {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List tipos = new java.util.ArrayList();
    TipoPlan tipo = new TipoPlan();
    tipo.tipoId = 0;
    tipo.nombre = messageResources.getResource("plan.tipo.estrategico");
    tipos.add(tipo);
    tipo = new TipoPlan();
    tipo.tipoId = 2;
    tipo.nombre = messageResources.getResource("plan.tipo.operativo");
    tipos.add(tipo);
    return tipos;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    if (tipo == 0) {
      nombre = messageResources.getResource("plan.tipo.estrategico");
    }
    if (tipo == 2) {
      nombre = messageResources.getResource("plan.tipo.operativo");
    }
    return nombre;
  }
}
