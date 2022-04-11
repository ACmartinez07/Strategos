package com.visiongc.app.strategos.planificacionseguimiento.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.util.List;

public class NaturalezaActividad
{
  public static final byte NATURALEZA_SIMPLE = 0;
  public static final byte NATURALEZA_FORMULA = 1;
  public static final byte NATURALEZA_ASOCIADO = 2;
  private byte naturalezaId;
  private String nombre;
  
  public NaturalezaActividad() {}
  
  public byte getNaturalezaId()
  {
    return naturalezaId;
  }
  
  public void setNaturalezaId(byte naturalezaId)
  {
    this.naturalezaId = naturalezaId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static List<NaturalezaActividad> getNaturalezas(Boolean incluirAsociado)
  {
    return getNaturalezas(null, incluirAsociado);
  }
  
  public static List<NaturalezaActividad> getNaturalezas(VgcMessageResources messageResources, Boolean incluirAsociado)
  {
    if (messageResources == null)
    {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    
    List<NaturalezaActividad> naturalezas = new java.util.ArrayList();
    
    NaturalezaActividad naturaleza = new NaturalezaActividad();
    naturaleza.naturalezaId = 0;
    naturaleza.nombre = messageResources.getResource("naturalezaactividad.simple");
    naturalezas.add(naturaleza);
    
    naturaleza = new NaturalezaActividad();
    naturaleza.naturalezaId = 1;
    naturaleza.nombre = messageResources.getResource("naturalezaactividad.formula");
    naturalezas.add(naturaleza);
    
    if (incluirAsociado.booleanValue())
    {
      naturaleza = new NaturalezaActividad();
      naturaleza.naturalezaId = 2;
      naturaleza.nombre = messageResources.getResource("naturalezaactividad.asociado");
      naturalezas.add(naturaleza);
    }
    
    return naturalezas;
  }
  
  public static String getNombre(byte naturaleza)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (naturaleza == 0)
      nombre = messageResources.getResource("naturalezaactividad.simple");
    if (naturaleza == 1)
      nombre = messageResources.getResource("naturalezaactividad.formula");
    if (naturaleza == 2) {
      nombre = messageResources.getResource("naturalezaactividad.asociado");
    }
    return nombre;
  }
  
  public static Byte getNaturalezaSimple()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getNaturalezaFormula()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getNaturalezaAsociado()
  {
    return new Byte((byte)2);
  }
}
