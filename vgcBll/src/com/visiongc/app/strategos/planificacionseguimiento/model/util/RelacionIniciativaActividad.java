package com.visiongc.app.strategos.planificacionseguimiento.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelacionIniciativaActividad
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte RELACION_NO_DEFINIBLE = Byte.valueOf((byte)0);
  private static final byte RELACION_DEFINIBLE = 1;
  private Byte relacionId;
  private String nombre;
  
  public RelacionIniciativaActividad() {}
  
  public Byte getRelacionId() {
    return relacionId;
  }
  
  public void setRelacionId(Byte relacionId)
  {
    this.relacionId = relacionId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getRelacionNoDefinible()
  {
    return RELACION_NO_DEFINIBLE;
  }
  
  public static Byte getRelacionDefinible()
  {
    return Byte.valueOf((byte)1);
  }
  
  public static List<RelacionIniciativaActividad> getTipoRelacion()
  {
    return getTipoRelacion(null);
  }
  
  public static List<RelacionIniciativaActividad> getTipoRelacion(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<RelacionIniciativaActividad> relaciones = new ArrayList();
    
    RelacionIniciativaActividad relacion = new RelacionIniciativaActividad();
    relacion.relacionId = RELACION_NO_DEFINIBLE;
    relacion.nombre = messageResources.getResource("relacion.iniciativaactividad.nodefinible");
    relaciones.add(relacion);
    
    relacion = new RelacionIniciativaActividad();
    relacion.relacionId = Byte.valueOf((byte)1);
    relacion.nombre = messageResources.getResource("relacion.iniciativaactividad.definible");
    relaciones.add(relacion);
    
    return relaciones;
  }
  
  public static String getNombre(byte relacionId)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = messageResources.getResource("relacion.iniciativaactividad.nodefinible");
    
    if (relacionId == RELACION_NO_DEFINIBLE.byteValue()) {
      nombre = messageResources.getResource("relacion.iniciativaactividad.nodefinible");
    } else if (relacionId == 1) {
      nombre = messageResources.getResource("relacion.iniciativaactividad.definible");
    }
    return nombre;
  }
}
