package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoCalculoPerspectiva implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_CALCULO_PERSPECTIVA_MANUAL = 0;
  private static final byte TIPO_CALCULO_PERSPECTIVA_AUTOMATICO = 1;
  private byte tipoCalculoId;
  private String nombre;
  
  public TipoCalculoPerspectiva() {}
  
  public byte getTipoCalculoId()
  {
    return tipoCalculoId;
  }
  
  public void setTipoCalculoId(byte tipoCalculoId) {
    this.tipoCalculoId = tipoCalculoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getTipoCalculoPerspectivaManual() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoCalculoPerspectivaAutomatico() {
    return new Byte((byte)1);
  }
  
  public static List getTiposCalculo() {
    return getTiposCalculos(null);
  }
  
  public static List getTiposCalculos(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List tiposCalculo = new java.util.ArrayList();
    
    TipoCalculoPerspectiva tipoCalculoPerspectiva = new TipoCalculoPerspectiva();
    tipoCalculoPerspectiva.tipoCalculoId = 0;
    tipoCalculoPerspectiva.nombre = messageResources.getResource("tipocalculo.perspectiva.manual");
    tiposCalculo.add(tipoCalculoPerspectiva);
    
    tipoCalculoPerspectiva = new TipoCalculoPerspectiva();
    tipoCalculoPerspectiva.tipoCalculoId = 1;
    tipoCalculoPerspectiva.nombre = messageResources.getResource("tipocalculo.perspectiva.automatico");
    tiposCalculo.add(tipoCalculoPerspectiva);
    
    return tiposCalculo;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 0) {
      nombre = messageResources.getResource("tipocalculo.perspectiva.manual");
    } else if (tipo == 1) {
      nombre = messageResources.getResource("tipocalculo.perspectiva.automatico");
    }
    
    return nombre;
  }
}
