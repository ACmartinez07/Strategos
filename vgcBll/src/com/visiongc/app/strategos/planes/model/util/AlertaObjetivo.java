package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlertaObjetivo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte ALERTA_NO_DEFINIBLE = null;
  private static final byte ALERTA_VERDE = 1;
  private static final byte ALERTA_VERDE_ALTERNA = 2;
  private static final byte ALERTA_AMARILLA = 3;
  private static final byte ALERTA_ROJA = 0;
  private Byte alertaId;
  private String nombre;
  
  public AlertaObjetivo() {}
  
  public Byte getAlertaId() { return alertaId; }
  
  public void setAlertaId(Byte alertaId)
  {
    this.alertaId = alertaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getAlertaNoDefinible() {
    return ALERTA_NO_DEFINIBLE;
  }
  
  public static Byte getAlertaVerde() {
    return new Byte((byte)1);
  }
  
  public static Byte getAlertaVerdeAlterna() {
    return new Byte((byte)2);
  }
  
  public static Byte getAlertaAmarilla() {
    return new Byte((byte)3);
  }
  
  public static Byte getAlertaRoja() {
    return new Byte((byte)0);
  }
  
  public static List getTiposAlertas() {
    return getTiposAlertas(null);
  }
  
  public static List getTiposAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategoss");
    }
    
    List alertas = new ArrayList();
    
    AlertaObjetivo alerta = new AlertaObjetivo();
    alerta.alertaId = ALERTA_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alertaobjetivo.nodefinible");
    alertas.add(alerta);
    
    alerta = new AlertaObjetivo();
    alerta.alertaId = Byte.valueOf((byte)1);
    alerta.nombre = messageResources.getResource("alertaobjetivo.verde");
    alertas.add(alerta);
    
    alerta = new AlertaObjetivo();
    alerta.alertaId = Byte.valueOf((byte)2);
    alerta.nombre = messageResources.getResource("alertaobjetivo.verde");
    alertas.add(alerta);
    
    alerta = new AlertaObjetivo();
    alerta.alertaId = Byte.valueOf((byte)3);
    alerta.nombre = messageResources.getResource("alertaobjetivo.amarilla");
    alertas.add(alerta);
    
    alerta = new AlertaObjetivo();
    alerta.alertaId = Byte.valueOf((byte)0);
    alerta.nombre = messageResources.getResource("alertaobjetivo.roja");
    alertas.add(alerta);
    
    return alertas;
  }
  
  public static String getNombre(byte alerta)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = messageResources.getResource("alertaobjetivo.nodefinible");
    
    if (alerta == 1) {
      nombre = messageResources.getResource("alertaobjetivo.verde");
    } else if (alerta == 2) {
      nombre = messageResources.getResource("alertaobjetivo.verde");
    } else if (alerta == 3) {
      nombre = messageResources.getResource("alertaobjetivo.amarilla");
    } else if (alerta == 0) {
      nombre = messageResources.getResource("alertaobjetivo.roja");
    }
    return nombre;
  }
  
  public static String getNombreImagen(Byte alerta)
  {
    String nombre = "AlertaBlanca";
    
    if (alerta != null) {
      if (alerta.byteValue() == 1) {
        nombre = "AlertaVerde";
      } else if (alerta.byteValue() == 2) {
        nombre = "AlertaVerde";
      } else if (alerta.byteValue() == 3) {
        nombre = "AlertaAmarilla";
      } else if (alerta.byteValue() == 0) {
        nombre = "AlertaRoja";
      }
    }
    return nombre;
  }
}
