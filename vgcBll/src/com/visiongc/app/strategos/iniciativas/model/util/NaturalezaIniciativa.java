package com.visiongc.app.strategos.iniciativas.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import java.util.List;

public class NaturalezaIniciativa
{
  public static final byte NATURALEZA_INDEPENDIENTE = 0;
  public static final byte NATURALEZA_VINCULADA = 1;
  private byte naturalezaId;
  private String nombre;
  
  public NaturalezaIniciativa() {}
  
  public byte getNaturalezaId()
  {
    return naturalezaId;
  }
  
  public void setNaturalezaId(byte naturalezaId) {
    this.naturalezaId = naturalezaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static List getNaturalezas() {
    return getNaturalezas(null);
  }
  
  public static List getNaturalezas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    }
    
    List naturalezas = new java.util.ArrayList();
    
    NaturalezaIniciativa naturaleza = new NaturalezaIniciativa();
    naturaleza.naturalezaId = 0;
    naturaleza.nombre = messageResources.getResource("naturalezainiciativa.independiente");
    naturalezas.add(naturaleza);
    
    naturaleza = new NaturalezaIniciativa();
    naturaleza.naturalezaId = 1;
    naturaleza.nombre = messageResources.getResource("naturalezainiciativa.vinculada");
    naturalezas.add(naturaleza);
    
    return naturalezas;
  }
  
  public static Byte getNaturalezaIndependiente()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getNaturalezaVinculada() {
    return new Byte((byte)1);
  }
}
