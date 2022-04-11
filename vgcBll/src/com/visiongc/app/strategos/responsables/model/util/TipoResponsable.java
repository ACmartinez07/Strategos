package com.visiongc.app.strategos.responsables.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoResponsable implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_RESPONSABLE_LOCAL = 0;
  private static final byte TIPO_RESPONSABLE_GLOBAL = 1;
  private byte tipoId;
  private String nombre;
  
  public TipoResponsable() {}
  
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
  
  public static Byte getTipoResponsableLocal()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoResponsableGlobal()
  {
    return new Byte((byte)1);
  }
  
  public static List<TipoResponsable> getTiposResponsables()
  {
    return getTiposResponsable(null);
  }
  
  public static List<TipoResponsable> getTiposResponsable(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoResponsable> tiposResponsable = new java.util.ArrayList();
    
    TipoResponsable tipoResponsable = new TipoResponsable();
    
    tipoResponsable.tipoId = 0;
    tipoResponsable.nombre = messageResources.getResource("tipo.responsable.local");
    tiposResponsable.add(tipoResponsable);
    
    tipoResponsable.tipoId = 1;
    tipoResponsable.nombre = messageResources.getResource("tipo.responsable.global");
    tiposResponsable.add(tipoResponsable);
    
    return tiposResponsable;
  }
  
  public static String getNombre(byte tipoId)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoId == 0) {
      nombre = messageResources.getResource("tipo.responsable.local");
    } else if (tipoId == 1) {
      nombre = messageResources.getResource("tipo.responsable.global");
    }
    return nombre;
  }
}
