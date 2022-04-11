package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoAlertaControl implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_ALERTA_CONTROL_FIJO = 0;
  private static final byte TIPO_ALERTA_CONTROL_VARIABLE = 1;
  private byte tipoAlertaId;
  private String nombre;
  
  public TipoAlertaControl() {}
  
  public byte getTipoAlertaId()
  {
    return tipoAlertaId;
  }
  
  public void setTipoAlertaId(byte tipoAlertaId) {
    this.tipoAlertaId = tipoAlertaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoAlertaControlFijo() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoAlertaControlVariable() {
    return new Byte((byte)1);
  }
  
  public static List getTipoAlertas() {
    return getTipoAlertas(null);
  }
  
  public static List getTipoAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tipoAlertas = new java.util.ArrayList();
    
    TipoAlertaControl tipoAlerta = new TipoAlertaControl();
    
    tipoAlerta.tipoAlertaId = 0;
    tipoAlerta.nombre = messageResources.getResource("tipoalertacontrol.fijo");
    tipoAlertas.add(tipoAlerta);
    
    tipoAlerta = new TipoAlertaControl();
    tipoAlerta.tipoAlertaId = 1;
    tipoAlerta.nombre = messageResources.getResource("tipoalertacontrol.variable");
    tipoAlertas.add(tipoAlerta);
    
    return tipoAlertas;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 0) {
      nombre = messageResources.getResource("tipoalertacontrol.fijo");
    }
    
    if (tipo == 1) {
      nombre = messageResources.getResource("tipoalertacontrol.variable");
    }
    
    return nombre;
  }
}
