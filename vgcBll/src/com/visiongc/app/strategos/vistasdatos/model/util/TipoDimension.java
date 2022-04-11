package com.visiongc.app.strategos.vistasdatos.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.List;

public class TipoDimension implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_DIMENSION_VARIABLE = 1;
  private static final byte TIPO_DIMENSION_ATRIBUTO = 2;
  private static final byte TIPO_DIMENSION_TIEMPO = 3;
  private static final byte TIPO_DIMENSION_INDICADOR = 4;
  private static final byte TIPO_DIMENSION_PLAN = 5;
  private static final byte TIPO_DIMENSION_ORGANIZACION = 6;
  private Byte tipoDimensionId;
  private String nombre;
  
  public TipoDimension() {}
  
  public Byte getTipoDimensionId()
  {
    return tipoDimensionId;
  }
  
  public void setTipoDimensionId(Byte tipoDimensionId)
  {
    this.tipoDimensionId = tipoDimensionId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getTipoDimensionVariable()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getTipoDimensionAtributo()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getTipoDimensionTiempo()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getTipoDimensionIndicador()
  {
    return new Byte((byte)4);
  }
  
  public static Byte getTipoDimensionPlan()
  {
    return new Byte((byte)5);
  }
  
  public static Byte getTipoDimensionOrganizacion()
  {
    return new Byte((byte)6);
  }
  
  public static List<TipoDimension> getTiposDimensiones()
  {
    return getTiposDimensiones(null);
  }
  
  public static List<TipoDimension> getTiposDimensiones(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoDimension> tiposDimensiones = new java.util.ArrayList();
    
    TipoDimension tipoDimension = new TipoDimension();
    
    tipoDimension.tipoDimensionId = new Byte((byte)1);
    tipoDimension.nombre = messageResources.getResource("tipodimension.variable");
    tiposDimensiones.add(tipoDimension);
    tipoDimension = new TipoDimension();
    tipoDimension.tipoDimensionId = new Byte((byte)3);
    tipoDimension.nombre = messageResources.getResource("tipodimension.tiempo");
    tiposDimensiones.add(tipoDimension);
    
    tipoDimension = new TipoDimension();
    tipoDimension.tipoDimensionId = new Byte((byte)4);
    tipoDimension.nombre = messageResources.getResource("tipodimension.indicador");
    tiposDimensiones.add(tipoDimension);
    tipoDimension = new TipoDimension();
    tipoDimension.tipoDimensionId = new Byte((byte)6);
    tipoDimension.nombre = messageResources.getResource("tipodimension.organizacion");
    tiposDimensiones.add(tipoDimension);
    
    return tiposDimensiones;
  }
  
  public static String getNombre(byte tipo)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 1)
      nombre = messageResources.getResource("tipodimension.variable");
    if (tipo == 2)
      nombre = messageResources.getResource("tipodimension.atributo");
    if (tipo == 3)
      nombre = messageResources.getResource("tipodimension.tiempo");
    if (tipo == 4)
      nombre = messageResources.getResource("tipodimension.indicador");
    if (tipo == 5)
      nombre = messageResources.getResource("tipodimension.plan");
    if (tipo == 6) {
      nombre = messageResources.getResource("tipodimension.organizacion");
    }
    return nombre;
  }
}
