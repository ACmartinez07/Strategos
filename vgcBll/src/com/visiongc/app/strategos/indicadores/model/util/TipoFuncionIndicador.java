package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoFuncionIndicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte TIPO_FUNCION_NORMAL = Byte.valueOf((byte)0);
  private static final Byte TIPO_FUNCION_SEGUIMIENTO = Byte.valueOf((byte)1);
  private static final Byte TIPO_FUNCION_IMPUTACION = Byte.valueOf((byte)2);
  private static final Byte TIPO_FUNCION_PERSPECTIVA = Byte.valueOf((byte)3);
  private static final Byte TIPO_FUNCION_TOTAL_IMPUTACION = Byte.valueOf((byte)4);
  private static final Byte TIPO_FUNCION_TOTAL_INICIATIVA = Byte.valueOf((byte)5);
  private static final Byte TIPO_FUNCION_PRESUPUESTO = Byte.valueOf((byte)6);
  private static final Byte TIPO_FUNCION_EFICACIA = Byte.valueOf((byte)7);
  private static final Byte TIPO_FUNCION_EFICIENCIA = Byte.valueOf((byte)8);
  private Byte tipoId;
  private String nombre;
  
  public TipoFuncionIndicador() {}
  
  public byte getTipoId() {
    return tipoId.byteValue();
  }
  
  public void setTipoId(byte tipoId)
  {
    this.tipoId = Byte.valueOf(tipoId);
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Byte getTipoFuncionNormal()
  {
    return TIPO_FUNCION_NORMAL;
  }
  
  public static Byte getTipoFuncionSeguimiento()
  {
    return TIPO_FUNCION_SEGUIMIENTO;
  }
  
  public static Byte getTipoFuncionImputacion()
  {
    return TIPO_FUNCION_IMPUTACION;
  }
  
  public static Byte getTipoFuncionPerspectiva()
  {
    return TIPO_FUNCION_PERSPECTIVA;
  }
  
  public static Byte getTipoFuncionTotalImputacion()
  {
    return TIPO_FUNCION_TOTAL_IMPUTACION;
  }
  
  public static Byte getTipoFuncionTotalIniciativa()
  {
    return TIPO_FUNCION_TOTAL_INICIATIVA;
  }
  
  public static Byte getTipoFuncionPresupuesto()
  {
    return TIPO_FUNCION_PRESUPUESTO;
  }
  
  public static Byte getTipoFuncionEficacia()
  {
    return TIPO_FUNCION_EFICACIA;
  }
  
  public static Byte getTipoFuncionEficiencia()
  {
    return TIPO_FUNCION_EFICIENCIA;
  }
  
  public static List<TipoFuncionIndicador> getTipos()
  {
    return getTipos(null);
  }
  
  public static List<TipoFuncionIndicador> getTipos(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoFuncionIndicador> tipos = new ArrayList();
    
    TipoFuncionIndicador tipo = new TipoFuncionIndicador();
    
    tipo.tipoId = TIPO_FUNCION_NORMAL;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.normal");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_SEGUIMIENTO;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.seguimiento");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_IMPUTACION;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.imputacion");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_PERSPECTIVA;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.perspectiva");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_TOTAL_IMPUTACION;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.totalimputacion");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_TOTAL_INICIATIVA;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.totaliniciativa");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_PRESUPUESTO;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.presupuesto");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_EFICACIA;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.eficacia");
    tipos.add(tipo);
    
    tipo = new TipoFuncionIndicador();
    tipo.tipoId = TIPO_FUNCION_EFICIENCIA;
    tipo.nombre = messageResources.getResource("indicador.tipofuncion.eficiencia");
    tipos.add(tipo);
    
    return tipos;
  }
  
  public static String getNombre(byte tipo)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == TIPO_FUNCION_NORMAL.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.normal");
    } else if (tipo == TIPO_FUNCION_SEGUIMIENTO.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.seguimiento");
    } else if (tipo == TIPO_FUNCION_IMPUTACION.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.imputacion");
    } else if (tipo == TIPO_FUNCION_PERSPECTIVA.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.perspectiva");
    } else if (tipo == TIPO_FUNCION_TOTAL_IMPUTACION.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.totalimputacion");
    } else if (tipo == TIPO_FUNCION_TOTAL_INICIATIVA.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.totaliniciativa");
    } else if (tipo == TIPO_FUNCION_PRESUPUESTO.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.presupuesto");
    } else if (tipo == TIPO_FUNCION_EFICACIA.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.eficacia");
    } else if (tipo == TIPO_FUNCION_EFICIENCIA.byteValue()) {
      nombre = messageResources.getResource("indicador.tipofuncion.eficiencia");
    }
    return nombre;
  }
}
