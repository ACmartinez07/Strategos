package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class Naturaleza implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte NATURALEZA_SIMPLE = 0;
  private static final byte NATURALEZA_FORMULA = 1;
  private static final byte NATURALEZA_CUALITATIVO_ORDINAL = 2;
  private static final byte NATURALEZA_CUALITATIVO_NOMINAL = 3;
  private static final byte NATURALEZA_SUMATORIA = 4;
  private static final byte NATURALEZA_PROMEDIO = 5;
  private static final byte NATURALEZA_INDICE = 6;
  private byte naturalezaId;
  private String nombre;
  
  public Naturaleza() {}
  
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
  
  public static Byte getNaturalezaSimple() {
    return new Byte((byte)0);
  }
  
  public static Byte getNaturalezaFormula() {
    return new Byte((byte)1);
  }
  
  public static Byte getNaturalezaCualitativoOrdinal() {
    return new Byte((byte)2);
  }
  
  public static Byte getNaturalezaCualitativoNominal() {
    return new Byte((byte)3);
  }
  
  public static Byte getNaturalezaSumatoria() {
    return new Byte((byte)4);
  }
  
  public static Byte getNaturalezaPromedio() {
    return new Byte((byte)5);
  }
  
  public static Byte getNaturalezaIndice() {
    return new Byte((byte)6);
  }
  
  public static List getNaturalezas() {
    return getNaturalezas(null);
  }
  
  public static List getNaturalezas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List naturalezas = new java.util.ArrayList();
    
    Naturaleza naturaleza = new Naturaleza();
    
    naturaleza.naturalezaId = 0;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.simple");
    naturalezas.add(naturaleza);
    
    naturaleza = new Naturaleza();
    naturaleza.naturalezaId = 1;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.formula");
    naturalezas.add(naturaleza);
    
    naturaleza = new Naturaleza();
    naturaleza.naturalezaId = 2;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.cualitativoordinal");
    naturalezas.add(naturaleza);
    
    naturaleza = new Naturaleza();
    naturaleza.naturalezaId = 3;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.cualitativonominal");
    naturalezas.add(naturaleza);
    
    naturaleza = new Naturaleza();
    naturaleza.naturalezaId = 4;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.sumatoria");
    naturalezas.add(naturaleza);
    
    naturaleza = new Naturaleza();
    naturaleza.naturalezaId = 5;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.promedio");
    naturalezas.add(naturaleza);
    naturaleza = new Naturaleza();
    
    naturaleza.naturalezaId = 6;
    naturaleza.nombre = messageResources.getResource("naturalezaindicador.indice");
    naturalezas.add(naturaleza);
    
    return naturalezas;
  }
  
  public static String getNombre(byte naturaleza) {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (naturaleza == 0) {
      nombre = messageResources.getResource("naturalezaindicador.simple");
    }
    
    if (naturaleza == 1) {
      nombre = messageResources.getResource("naturalezaindicador.formula");
    }
    
    if (naturaleza == 2) {
      nombre = messageResources.getResource("naturalezaindicador.cualitativoordinal");
    }
    
    if (naturaleza == 3) {
      nombre = messageResources.getResource("naturalezaindicador.cualitativonominal");
    }
    
    if (naturaleza == 4) {
      nombre = messageResources.getResource("naturalezaindicador.sumatoria");
    }
    
    if (naturaleza == 5) {
      nombre = messageResources.getResource("naturalezaindicador.promedio");
    }
    
    if (naturaleza == 6) {
      nombre = messageResources.getResource("naturalezaindicador.indice");
    }
    
    return nombre;
  }
}
