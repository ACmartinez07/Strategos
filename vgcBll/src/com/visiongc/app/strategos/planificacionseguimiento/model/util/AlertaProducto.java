package com.visiongc.app.strategos.planificacionseguimiento.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlertaProducto
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte ALERTA_PRODUCTO_NO_DEFINIBLE = null;
  private static final byte ALERTA_PRODUCTO_EN_ESPERA_COMIENZO = 0;
  private static final byte ALERTA_PRODUCTO_ENTREGADO = 1;
  private static final byte ALERTA_PRODUCTO_NO_ENTREGADO = 3;
  private Byte alertaId;
  private String nombre;
  
  public AlertaProducto() {}
  
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
    return ALERTA_PRODUCTO_NO_DEFINIBLE;
  }
  
  public static Byte getAlertaEnEsperaComienzo() {
    return new Byte((byte)0);
  }
  
  public static Byte getAlertaEntregado() {
    return new Byte((byte)1);
  }
  
  public static Byte getAlertaNoEntregado() {
    return new Byte((byte)3);
  }
  
  public static List getTipoAlertas() {
    return getTipoAlertas(null);
  }
  
  public static List getTipoAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategoss");
    }
    
    List alertas = new ArrayList();
    
    AlertaProducto alerta = new AlertaProducto();
    alerta.alertaId = ALERTA_PRODUCTO_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alerta.producto.nodefinible");
    alertas.add(alerta);
    
    alerta = new AlertaProducto();
    alerta.alertaId = new Byte((byte)0);
    alerta.nombre = messageResources.getResource("alerta.producto.enesperacomienzo");
    alertas.add(alerta);
    
    alerta = new AlertaProducto();
    alerta.alertaId = new Byte((byte)1);
    alerta.nombre = messageResources.getResource("alerta.producto.entregado");
    alertas.add(alerta);
    
    alerta = new AlertaProducto();
    alerta.alertaId = new Byte((byte)0);
    alerta.nombre = messageResources.getResource("alerta.producto.noentregado");
    alertas.add(alerta);
    
    return alertas;
  }
  
  public static String getNombre(byte alerta) {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = messageResources.getResource("alerta.producto.nodefinible");
    
    if (alerta == 0) {
      nombre = messageResources.getResource("alerta.producto.enesperacomienzo");
    } else if (alerta == 1) {
      nombre = messageResources.getResource("alerta.producto.entregado");
    } else if (alerta == 3) {
      nombre = messageResources.getResource("alerta.producto.noentregado");
    }
    
    return nombre;
  }
}
