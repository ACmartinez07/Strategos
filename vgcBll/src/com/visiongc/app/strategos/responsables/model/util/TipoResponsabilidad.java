package com.visiongc.app.strategos.responsables.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoResponsabilidad
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_RESPONSABLE_FIJAR_META = 1;
  private static final byte TIPO_RESPONSABLE_LOGRAR_META = 2;
  private static final byte TIPO_RESPONSABLE_CARGAR_META = 3;
  private static final byte TIPO_RESPONSABLE_CARGAR_EJECUTADO = 4;
  private static final byte TIPO_RESPONSABLE_SEGUIMIENTO = 5;
  private byte tipoId;
  private String nombre;
  
  public TipoResponsabilidad() {}
  
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
  
  public static Byte getTipoResponsableFijarMeta()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoResponsableLograrMeta()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getTipoResponsableSeguimiento()
  {
    return new Byte((byte)5);
  }
  
  public static Byte getTipoResponsableCargarMeta()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getTipoResponsableCargarEjecutado()
  {
    return new Byte((byte)4);
  }
  
  public static List<TipoResponsabilidad> getTiposResponsabilidades()
  {
    return getTiposResponsabilidades(null);
  }
  
  public static List<TipoResponsabilidad> getTiposResponsabilidades(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoResponsabilidad> tiposResponsabilidades = new ArrayList();
    
    TipoResponsabilidad tipoResponsabilidad = new TipoResponsabilidad();
    
    tipoResponsabilidad.tipoId = 1;
    tipoResponsabilidad.nombre = messageResources.getResource("tipo.responsabilidad.fijar.meta");
    tiposResponsabilidades.add(tipoResponsabilidad);
    
    tipoResponsabilidad.tipoId = 2;
    tipoResponsabilidad.nombre = messageResources.getResource("tipo.responsabilidad.lograr.meta");
    tiposResponsabilidades.add(tipoResponsabilidad);
    
    tipoResponsabilidad.tipoId = 5;
    tipoResponsabilidad.nombre = messageResources.getResource("tipo.responsabilidad.seguimiento");
    tiposResponsabilidades.add(tipoResponsabilidad);
    
    tipoResponsabilidad.tipoId = 3;
    tipoResponsabilidad.nombre = messageResources.getResource("tipo.responsabilidad.cargar.meta");
    tiposResponsabilidades.add(tipoResponsabilidad);
    
    tipoResponsabilidad.tipoId = 4;
    tipoResponsabilidad.nombre = messageResources.getResource("tipo.responsabilidad.cargar.ejecutado");
    tiposResponsabilidades.add(tipoResponsabilidad);
    

    return tiposResponsabilidades;
  }
  
  public static String getNombre(byte tipoId)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoId == 1) {
      nombre = messageResources.getResource("tipo.responsabilidad.fijar.meta");
    } else if (tipoId == 2) {
      nombre = messageResources.getResource("tipo.responsabilidad.lograr.meta");
    } else if (tipoId == 5) {
      nombre = messageResources.getResource("tipo.responsabilidad.seguimiento");
    } else if (tipoId == 3) {
      nombre = messageResources.getResource("tipo.responsabilidad.cargar.meta");
    } else if (tipoId == 4) {
      nombre = messageResources.getResource("tipo.responsabilidad.cargar.ejecutado");
    }
    return nombre;
  }
}
