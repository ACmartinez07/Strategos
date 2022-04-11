package com.visiongc.app.strategos.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import java.util.List;

public class Frecuencia
{
  private static final byte FRECUENCIA_DIARIA = 0;
  private static final byte FRECUENCIA_SEMANAL = 1;
  private static final byte FRECUENCIA_QUINCENAL = 2;
  private static final byte FRECUENCIA_MENSUAL = 3;
  private static final byte FRECUENCIA_BIMENSUAL = 4;
  private static final byte FRECUENCIA_TRIMESTRAL = 5;
  private static final byte FRECUENCIA_CUATRIMESTRAL = 6;
  private static final byte FRECUENCIA_SEMESTRAL = 7;
  private static final byte FRECUENCIA_ANUAL = 8;
  private Byte frecuenciaId;
  private String nombre;
  
  public Frecuencia() {}
  
  public static Byte getFrecuenciaDiaria()
  {
    return Byte.valueOf((byte)0);
  }
  
  public static Byte getFrecuenciaSemanal()
  {
    return Byte.valueOf((byte)1);
  }
  
  public static Byte getFrecuenciaQuincenal()
  {
    return Byte.valueOf((byte)2);
  }
  
  public static Byte getFrecuenciaMensual()
  {
    return Byte.valueOf((byte)3);
  }
  
  public static Byte getFrecuenciaBimensual()
  {
    return Byte.valueOf((byte)4);
  }
  
  public static Byte getFrecuenciaTrimestral()
  {
    return Byte.valueOf((byte)5);
  }
  
  public static Byte getFrecuenciaCuatrimestral()
  {
    return Byte.valueOf((byte)6);
  }
  
  public static Byte getFrecuenciaSemestral()
  {
    return Byte.valueOf((byte)7);
  }
  
  public static Byte getFrecuenciaAnual()
  {
    return Byte.valueOf((byte)8);
  }
  
  public Byte getFrecuenciaId()
  {
    return frecuenciaId;
  }
  
  public void setFrecuenciaId(Byte frecuenciaId)
  {
    this.frecuenciaId = frecuenciaId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static List<Frecuencia> getFrecuencias()
  {
    return getFrecuencias(null);
  }
  
  public static List<Frecuencia> getFrecuencias(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    }
    List<Frecuencia> frecuencias = new java.util.ArrayList();
    
    Frecuencia frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)0);
    frecuencia.nombre = messageResources.getResource("frecuencia.diaria");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)1);
    frecuencia.nombre = messageResources.getResource("frecuencia.semanal");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)2);
    frecuencia.nombre = messageResources.getResource("frecuencia.quincenal");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)3);
    frecuencia.nombre = messageResources.getResource("frecuencia.mensual");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)4);
    frecuencia.nombre = messageResources.getResource("frecuencia.bimensual");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)5);
    frecuencia.nombre = messageResources.getResource("frecuencia.trimestral");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)6);
    frecuencia.nombre = messageResources.getResource("frecuencia.cuatrimestral");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)7);
    frecuencia.nombre = messageResources.getResource("frecuencia.semestral");
    frecuencias.add(frecuencia);
    
    frecuencia = new Frecuencia();
    frecuencia.frecuenciaId = Byte.valueOf((byte)8);
    frecuencia.nombre = messageResources.getResource("frecuencia.anual");
    frecuencias.add(frecuencia);
    
    return frecuencias;
  }
  
  public static String getNombre(byte frecuencia)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    if (frecuencia == 0)
      nombre = messageResources.getResource("frecuencia.diaria");
    if (frecuencia == 1)
      nombre = messageResources.getResource("frecuencia.semanal");
    if (frecuencia == 2)
      nombre = messageResources.getResource("frecuencia.quincenal");
    if (frecuencia == 3)
      nombre = messageResources.getResource("frecuencia.mensual");
    if (frecuencia == 4)
      nombre = messageResources.getResource("frecuencia.bimensual");
    if (frecuencia == 5)
      nombre = messageResources.getResource("frecuencia.trimestral");
    if (frecuencia == 6)
      nombre = messageResources.getResource("frecuencia.cuatrimestral");
    if (frecuencia == 7)
      nombre = messageResources.getResource("frecuencia.semestral");
    if (frecuencia == 8) {
      nombre = messageResources.getResource("frecuencia.anual");
    }
    return nombre;
  }
  
  public static Frecuencia getFrecuencia(byte inFrecuencia)
  {
    Frecuencia frecuencia = null;
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    switch (inFrecuencia)
    {
    case 0: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
      frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
      break;
    case 1: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
      frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
      break;
    case 2: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
      frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
      break;
    case 3: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
      frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
      break;
    case 4: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
      frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
      break;
    case 5: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
      frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
      break;
    case 6: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
      frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
      break;
    case 7: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
      frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
      break;
    case 8: 
      frecuencia = new Frecuencia();
      frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
      frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
    }
    
    return frecuencia;
  }
  
  public static List<Frecuencia> getFrecuenciasContenidas(byte frecuenciaId)
  {
    List<Frecuencia> frecuencias = new java.util.ArrayList();
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    try
    {
      Frecuencia frecuencia = null;
      switch (frecuenciaId)
      {
      case 0: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        break;
      case 1: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        break;
      case 2: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        break;
      case 3: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        break;
      case 4: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        break;
      case 5: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        break;
      case 6: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        break;
      case 7: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        break;
      case 8: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
      
      }
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    return frecuencias;
  }
  
  public static List<Frecuencia> getFrecuenciasCompatibles(byte frecuenciaId)
  {
    List<Frecuencia> frecuencias = new java.util.ArrayList();
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    try
    {
      Frecuencia frecuencia = null;
      switch (frecuenciaId)
      {
      case 0: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)0));
        frecuencia.setNombre(messageResources.getResource("frecuencia.diaria"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 1: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)1));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semanal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 2: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)2));
        frecuencia.setNombre(messageResources.getResource("frecuencia.quincenal"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 3: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)3));
        frecuencia.setNombre(messageResources.getResource("frecuencia.mensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 4: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)4));
        frecuencia.setNombre(messageResources.getResource("frecuencia.bimensual"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 5: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)5));
        frecuencia.setNombre(messageResources.getResource("frecuencia.trimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 6: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)6));
        frecuencia.setNombre(messageResources.getResource("frecuencia.cuatrimestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 7: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)7));
        frecuencia.setNombre(messageResources.getResource("frecuencia.semestral"));
        frecuencias.add(frecuencia);
        
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
        
        break;
      case 8: 
        frecuencia = new Frecuencia();
        frecuencia.setFrecuenciaId(Byte.valueOf((byte)8));
        frecuencia.setNombre(messageResources.getResource("frecuencia.anual"));
        frecuencias.add(frecuencia);
      }
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    return frecuencias;
  }
  
  public static Integer getNumPeriodoMax(Byte frecuencia, Integer ano)
  {
    if (frecuencia == null) {
      return new Integer(0);
    }
    if (frecuencia.byteValue() == 0)
    {
      if (ano == null)
        return new Integer(0);
      if (ano.intValue() % 4 == 0) {
        return new Integer(366);
      }
      return new Integer(365);
    }
    if (frecuencia.byteValue() == 1)
      return new Integer(52);
    if (frecuencia.byteValue() == 2)
      return new Integer(24);
    if (frecuencia.byteValue() == 3)
      return new Integer(12);
    if (frecuencia.byteValue() == 4)
      return new Integer(6);
    if (frecuencia.byteValue() == 5)
      return new Integer(4);
    if (frecuencia.byteValue() == 6)
      return new Integer(3);
    if (frecuencia.byteValue() == 7)
      return new Integer(2);
    if (frecuencia.byteValue() == 8) {
      return new Integer(1);
    }
    return new Integer(0);
  }
  
  public static String getNombrePeriodoPorFrecuencia(byte inFrecuencia)
  {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    String nombrePeriodo = "";
    
    switch (inFrecuencia)
    {
    case 0: 
      nombrePeriodo = messageResources.getResource("frecuencia.diaria.periodo.nombre");
      break;
    case 1: 
      nombrePeriodo = messageResources.getResource("frecuencia.semanal.periodo.nombre");
      break;
    case 2: 
      nombrePeriodo = messageResources.getResource("frecuencia.quincenal.periodo.nombre");
      break;
    case 3: 
      nombrePeriodo = messageResources.getResource("frecuencia.mensual.periodo.nombre");
      break;
    case 4: 
      nombrePeriodo = messageResources.getResource("frecuencia.bimensual.periodo.nombre");
      break;
    case 5: 
      nombrePeriodo = messageResources.getResource("frecuencia.trimestral.periodo.nombre");
      break;
    case 6: 
      nombrePeriodo = messageResources.getResource("frecuencia.cuatrimestral.periodo.nombre");
      break;
    case 7: 
      nombrePeriodo = messageResources.getResource("frecuencia.semestral.periodo.nombre");
      break;
    case 8: 
      nombrePeriodo = messageResources.getResource("frecuencia.anual.periodo.nombre");
    }
    
    
    return nombrePeriodo;
  }
}
