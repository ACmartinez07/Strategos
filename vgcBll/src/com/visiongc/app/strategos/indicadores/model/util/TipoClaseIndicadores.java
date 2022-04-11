package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoClaseIndicadores implements Serializable
{
  static final long serialVersionUID = 0L;
  public static final byte TIPO_CLASE_INDICADORES = 0;
  public static final byte TIPO_CLASE_PLANIFICACION_SEGUIMIENTO = 1;
  public static final byte TIPO_CLASE_PERSPECTIVAS = 3;
  private byte tipoId;
  private String nombre;
  
  public TipoClaseIndicadores() {}
  
  public byte getTipoId()
  {
    return tipoId;
  }
  
  public void setTipoId(byte tipoId)
  {
    this.tipoId = tipoId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getTipoClaseIndicadores()
  {
    return Byte.valueOf((byte)0);
  }
  
  public static Byte getTipoClasePlanificacionSeguimiento()
  {
    return Byte.valueOf((byte)1);
  }
  
  public static Byte getTipoClasePerspectivas()
  {
    return Byte.valueOf((byte)3);
  }
  
  public static List<TipoClaseIndicadores> getTipoClases()
  {
    return getTipoClases(null);
  }
  
  public static List<TipoClaseIndicadores> getTipoClases(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoClaseIndicadores> tipoClases = new ArrayList();
    
    TipoClaseIndicadores tipoClase = new TipoClaseIndicadores();
    
    tipoClase.tipoId = 0;
    tipoClase.nombre = messageResources.getResource("tipo.claseindicadores.indicadores");
    tipoClases.add(tipoClase);
    
    tipoClase.tipoId = 1;
    tipoClase.nombre = messageResources.getResource("tipo.claseindicadores.planificacionseguimiento");
    tipoClases.add(tipoClase);
    
    tipoClase.tipoId = 3;
    tipoClase.nombre = messageResources.getResource("tipo.claseindicadores.perspectivas");
    tipoClases.add(tipoClase);
    
    return tipoClases;
  }
  
  public static String getNombre(byte tipoId)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoId == 0) {
      nombre = messageResources.getResource("tipo.claseindicadores.indicadores");
    } else if (tipoId == 1) {
      nombre = messageResources.getResource("tipo.claseindicadores.planificacionseguimiento");
    } else if (tipoId == 3) {
      nombre = messageResources.getResource("tipo.claseindicadores.perspectivas");
    }
    return nombre;
  }
}
