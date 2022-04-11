package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class PrioridadIndicador implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte PRIORIDAD_INDICADOR_BAJA = 0;
  private static final byte PRIORIDAD_INDICADOR_MEDIA = 1;
  private static final byte PRIORIDAD_INDICADOR_ALTA = 2;
  private byte prioridadId;
  private String nombre;
  
  public PrioridadIndicador() {}
  
  public byte getPrioridadId()
  {
    return prioridadId;
  }
  
  public void setPrioridadId(byte prioridadId) {
    this.prioridadId = prioridadId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static Byte getPrioridadIndicadorBaja() {
    return new Byte((byte)0);
  }
  
  public static Byte getPrioridadIndicadorMedia() {
    return new Byte((byte)1);
  }
  
  public static Byte getPrioridadIndicadorAlta() {
    return new Byte((byte)2);
  }
  
  public static List getPrioridades() {
    return getPrioridades(null);
  }
  
  public static List getPrioridades(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List prioridades = new java.util.ArrayList();
    
    PrioridadIndicador prioridad = new PrioridadIndicador();
    
    prioridad.prioridadId = 0;
    prioridad.nombre = messageResources.getResource("indicador.prioridad.baja");
    prioridades.add(prioridad);
    
    prioridad = new PrioridadIndicador();
    prioridad.prioridadId = 1;
    prioridad.nombre = messageResources.getResource("indicador.prioridad.media");
    prioridades.add(prioridad);
    
    prioridad = new PrioridadIndicador();
    prioridad.prioridadId = 2;
    prioridad.nombre = messageResources.getResource("indicador.prioridad.alta");
    prioridades.add(prioridad);
    
    return prioridades;
  }
  
  public static String getNombre(byte prioridad) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (prioridad == 0) {
      nombre = messageResources.getResource("indicador.prioridad.baja");
    } else if (prioridad == 1) {
      nombre = messageResources.getResource("indicador.prioridad.media");
    } else if (prioridad == 2) {
      nombre = messageResources.getResource("indicador.prioridad.alta");
    }
    
    return nombre;
  }
}
