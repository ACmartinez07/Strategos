package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoAlerta implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_ALERTA_PORCENTAJE = 0;
  private static final byte TIPO_ALERTA_VALOR_ABSOLUTO_MAGNITUD = 1;
  private static final byte TIPO_ALERTA_VALOR_ABSOLUTO_INDICADOR = 2;
  private byte tipoAlertaId;
  private String nombre;
  
  public TipoAlerta() {}
  
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
  
  public static Byte getTipoAlertaPorcentaje() {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoAlertaValorAbsolutoMagnitud() {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoAlertaValorAbsolutoIndicador() {
    return new Byte((byte)2);
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
    
    TipoAlerta tipoAlerta = new TipoAlerta();
    
    tipoAlerta.tipoAlertaId = 0;
    tipoAlerta.nombre = messageResources.getResource("tipoalerta.porcentaje");
    tipoAlertas.add(tipoAlerta);
    
    tipoAlerta = new TipoAlerta();
    tipoAlerta.tipoAlertaId = 1;
    tipoAlerta.nombre = messageResources.getResource("tipoalerta.valorabsolutomagnitud");
    tipoAlertas.add(tipoAlerta);
    
    tipoAlerta = new TipoAlerta();
    tipoAlerta.tipoAlertaId = 2;
    tipoAlerta.nombre = messageResources.getResource("tipoalerta.valorabsolutoindicador");
    tipoAlertas.add(tipoAlerta);
    
    return tipoAlertas;
  }
  
  public static String getNombre(byte tipo) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 0) {
      nombre = messageResources.getResource("tipoalerta.porcentaje");
    }
    
    if (tipo == 1) {
      nombre = messageResources.getResource("tipoalerta.valoradsolutomagnitud");
    }
    
    if (tipo == 2) {
      nombre = messageResources.getResource("tipoalerta.valorabsolutoindicador");
    }
    return nombre;
  }
}
