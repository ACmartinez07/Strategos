package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoSuma
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_SUMAR_MEDICIONES = 0;
  private static final byte TIPO_ULTIMO_PERIODO = 1;
  private byte tipoMedicionId;
  private String nombre;
  
  public TipoSuma() {}
  
  public byte getTipoMedicionId()
  {
    return tipoMedicionId;
  }
  
  public void setTipoMedicionId(byte tipoMedicionId)
  {
    this.tipoMedicionId = tipoMedicionId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getTipoSumaSumarMediciones()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getTipoSumaUltimoPeriodo()
  {
    return new Byte((byte)1);
  }
  
  public static List getTipoMediciones()
  {
    return getTipoMediciones(null);
  }
  
  public static List getTipoMediciones(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List tipoSumas = new ArrayList();
    
    TipoSuma tipoSuma = new TipoSuma();
    
    tipoSuma.tipoMedicionId = 0;
    tipoSuma.nombre = messageResources.getResource("tiposuma.sumandomediciones");
    tipoSumas.add(tipoSuma);
    
    tipoSuma = new TipoSuma();
    tipoSuma.tipoMedicionId = 1;
    tipoSuma.nombre = messageResources.getResource("tiposuma.ultimamedicion");
    tipoSumas.add(tipoSuma);
    
    return tipoSumas;
  }
  
  public static String getNombre(byte tipoSuma)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipoSuma == 0) {
      nombre = messageResources.getResource("tiposuma.sumandomediciones");
    }
    if (tipoSuma == 1) {
      nombre = messageResources.getResource("tiposuma.ultimamedicion");
    }
    return nombre;
  }
}
