package com.visiongc.app.strategos.planificacionseguimiento.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlertaIniciativaProducto
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte ALERTA_INICIATIVA_PRODUCTO_NO_DEFINIBLE = null;
  private static final Byte ALERTA_INICIATIVA_PRODUCTO_VERDE = Byte.valueOf((byte)2);
  private static final Byte ALERTA_INICIATIVA_PRODUCTO_AMARILLA = Byte.valueOf((byte)3);
  private static final Byte ALERTA_INICIATIVA_PRODUCTO_ROJA = Byte.valueOf((byte)0);
  private Byte alertaId;
  private String nombre;
  
  public AlertaIniciativaProducto() {}
  
  public Byte getAlertaId() { return alertaId; }
  

  public void setAlertaId(Byte alertaId)
  {
    this.alertaId = alertaId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getAlertaNoDefinible()
  {
    return ALERTA_INICIATIVA_PRODUCTO_NO_DEFINIBLE;
  }
  
  public static Byte getAlertaEnEsperaComienzo()
  {
    return ALERTA_INICIATIVA_PRODUCTO_NO_DEFINIBLE;
  }
  
  public static Byte getAlertaVerde()
  {
    return ALERTA_INICIATIVA_PRODUCTO_VERDE;
  }
  
  public static Byte getAlertaAmarilla()
  {
    return ALERTA_INICIATIVA_PRODUCTO_AMARILLA;
  }
  
  public static Byte getAlertaRoja()
  {
    return ALERTA_INICIATIVA_PRODUCTO_ROJA;
  }
  
  public static List<AlertaIniciativaProducto> getTipoAlertas()
  {
    return getTipoAlertas(null);
  }
  
  public static List<AlertaIniciativaProducto> getTipoAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategoss");
    }
    List<AlertaIniciativaProducto> alertas = new ArrayList();
    
    AlertaIniciativaProducto alerta = new AlertaIniciativaProducto();
    alerta.alertaId = ALERTA_INICIATIVA_PRODUCTO_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alerta.iniciativaproducto.nodefinible");
    alertas.add(alerta);
    
    alerta = new AlertaIniciativaProducto();
    alerta.alertaId = ALERTA_INICIATIVA_PRODUCTO_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alerta.iniciativaproducto.enesperacomienzo");
    alertas.add(alerta);
    
    alerta = new AlertaIniciativaProducto();
    alerta.alertaId = ALERTA_INICIATIVA_PRODUCTO_VERDE;
    alerta.nombre = messageResources.getResource("alerta.iniciativaproducto.verde");
    alertas.add(alerta);
    
    alerta = new AlertaIniciativaProducto();
    alerta.alertaId = ALERTA_INICIATIVA_PRODUCTO_AMARILLA;
    alerta.nombre = messageResources.getResource("alerta.iniciativaproducto.amarilla");
    alertas.add(alerta);
    
    alerta = new AlertaIniciativaProducto();
    alerta.alertaId = ALERTA_INICIATIVA_PRODUCTO_ROJA;
    alerta.nombre = messageResources.getResource("alerta.iniciativaproducto.roja");
    alertas.add(alerta);
    
    return alertas;
  }
  
  public static String getNombre(Byte alerta)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = messageResources.getResource("alerta.iniciativaproducto.nodefinible");
    
    if (alerta == null) {
      nombre = messageResources.getResource("alerta.iniciativaproducto.enesperacomienzo");
    } else if (alerta == ALERTA_INICIATIVA_PRODUCTO_VERDE) {
      nombre = messageResources.getResource("alerta.iniciativaproducto.verde");
    } else if (alerta == ALERTA_INICIATIVA_PRODUCTO_AMARILLA) {
      nombre = messageResources.getResource("alerta.iniciativaproducto.amarilla");
    } else if (alerta == ALERTA_INICIATIVA_PRODUCTO_ROJA) {
      nombre = messageResources.getResource("alerta.iniciativaproducto.roja");
    }
    return nombre;
  }
}
