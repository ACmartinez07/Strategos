package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlertaIndicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte ALERTA_NO_DEFINIBLE = null;
  private static final byte ALERTA_VERDE = 2;
  private static final byte ALERTA_AMARILLA = 3;
  private static final byte ALERTA_ROJA = 0;
  private static final byte ALERTA_INALTERADA = 1;
  private static final byte ALERTA_AZUL = 4;
  private static final byte ALERTA_NEGRA = 5;
  private Byte alertaId;
  private String nombre;
  
  public AlertaIndicador() {}
  
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
    return ALERTA_NO_DEFINIBLE;
  }
  
  public static Byte getAlertaVerde()
  {
    return Byte.valueOf((byte)2);
  }
  
  public static Byte getAlertaAmarilla()
  {
    return Byte.valueOf((byte)3);
  }
  
  public static Byte getAlertaRoja()
  {
    return Byte.valueOf((byte)0);
  }
  
  public static Byte getAlertaInalterada()
  {
    return Byte.valueOf((byte)1);
  }
  
  public static Byte getAlertaAzul()
  {
    return Byte.valueOf((byte)4);
  }
  
  public static Byte getAlertaNegra()
  {
    return Byte.valueOf((byte)5);
  }
  
  public static List<AlertaIndicador> getTipoAlertas()
  {
    return getTipoAlertas(null);
  }
  
  public static Double ConvertToDouble(Byte alerta)
  {
    Double inAlerta = null;
    if (alerta == null) {
      return null;
    }
    switch (alerta.byteValue())
    {
    case 2: 
      inAlerta = Double.valueOf(2.0D);
      break;
    case 3: 
      inAlerta = Double.valueOf(3.0D);
      break;
    case 0: 
      inAlerta = Double.valueOf(0.0D);
      break;
    case 1: 
      inAlerta = Double.valueOf(1.0D);
      break;
    case 4: 
      inAlerta = Double.valueOf(4.0D);
      break;
    case 5: 
      inAlerta = Double.valueOf(5.0D);
    }
    
    
    return inAlerta;
  }
  
  public static Byte ConvertDoubleToByte(Double alerta)
  {
    Byte inAlerta = null;
    if (alerta == null) {
      return null;
    }
    if (alerta.doubleValue() == 2.0D) {
      inAlerta = Byte.valueOf((byte)2);
    } else if (alerta.doubleValue() == 3.0D) {
      inAlerta = Byte.valueOf((byte)3);
    } else if (alerta.doubleValue() == 0.0D) {
      inAlerta = Byte.valueOf((byte)0);
    } else if (alerta.doubleValue() == 1.0D) {
      inAlerta = Byte.valueOf((byte)1);
    } else if (alerta.doubleValue() == 4.0D) {
      inAlerta = Byte.valueOf((byte)4);
    } else if (alerta.doubleValue() == 5.0D) {
      inAlerta = Byte.valueOf((byte)5);
    } else {
      inAlerta = null;
    }
    return inAlerta;
  }
  
  public static List<AlertaIndicador> getTipoAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategoss");
    }
    List<AlertaIndicador> alertas = new ArrayList();
    
    AlertaIndicador alerta = new AlertaIndicador();
    alerta.alertaId = ALERTA_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alertaindicador.nodefinible");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)2);
    alerta.nombre = messageResources.getResource("alertaindicador.verde");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)3);
    alerta.nombre = messageResources.getResource("alertaindicador.amarilla");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)0);
    alerta.nombre = messageResources.getResource("alertaindicador.roja");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)1);
    alerta.nombre = messageResources.getResource("alertaindicador.inalterada");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)4);
    alerta.nombre = messageResources.getResource("alertaindicador.azul");
    alertas.add(alerta);
    
    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte)5);
    alerta.nombre = messageResources.getResource("alertaindicador.negra");
    alertas.add(alerta);
    
    return alertas;
  }
  
  public static String getNombre(byte alerta)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = messageResources.getResource("alertaindicador.nodefinible");
    
    if (alerta == 2) {
      nombre = messageResources.getResource("alertaindicador.verde");
    } else if (alerta == 3) {
      nombre = messageResources.getResource("alertaindicador.amarilla");
    } else if (alerta == 0) {
      nombre = messageResources.getResource("alertaindicador.roja");
    } else if (alerta == 1) {
      nombre = messageResources.getResource("alertaindicador.inalterada");
    } else if (alerta == 4) {
      nombre = messageResources.getResource("alertaindicador.azul");
    } else if (alerta == 5) {
      nombre = messageResources.getResource("alertaindicador.negra");
    }
    return nombre;
  }
}
