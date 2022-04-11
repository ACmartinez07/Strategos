package com.visiongc.app.strategos.indicadores.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoMedicionImportar
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte IMPORTAR_EJECUTADO_TODOS = 0;
  private static final byte IMPORTAR_EJECUTADO_REALES = 1;
  private static final byte IMPORTAR_EJECUTADO_MINIMOS = 2;
  private static final byte IMPORTAR_EJECUTADO_MAXIMOS = 3;
  private static final byte IMPORTAR_EJECUTADO_PROGRAMADOS = 4;
  private static final byte IMPORTAR_META_PARCIALES = 5;
  private static final byte IMPORTAR_META_ANUALES = 6;
  private byte tipoMedicionId;
  private String nombre;
  
  public TipoMedicionImportar() {}
  
  public byte getTipoMedicionId()
  {
    return tipoMedicionId;
  }
  
  public void seTipoMedicionId(byte tipoMedicionId)
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
  
  public static Byte getImportarEjecutadoTodos()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getImportarEjecutadoReales()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getImportarEjecutadoMinimos()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getImportarEjecutadoMaximo()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getImportarEjecutadoProgramado()
  {
    return new Byte((byte)4);
  }
  
  public static Byte getImportarMetaParciales()
  {
    return new Byte((byte)5);
  }
  
  public static Byte getImportarMetaAnuales()
  {
    return new Byte((byte)6);
  }
  
  public static List<TipoMedicionImportar> getTiposMedicionesImportar()
  {
    return getTiposMedicionesImportar(null);
  }
  
  public static List<TipoMedicionImportar> getTiposMedicionesImportar(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoMedicionImportar> tipos = new ArrayList();
    
    TipoMedicionImportar tipo = new TipoMedicionImportar();
    
    tipo.tipoMedicionId = 0;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.todos");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 1;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.reales");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 2;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.minimos");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 3;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.maximos");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 4;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.programados");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 5;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.metas.parciales");
    tipos.add(tipo);
    
    tipo = new TipoMedicionImportar();
    tipo.tipoMedicionId = 6;
    tipo.nombre = messageResources.getResource("tipomedicionimportacion.metas.anuales");
    tipos.add(tipo);
    
    return tipos;
  }
  
  public static String getNombre(byte tipo)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo == 0) {
      nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.todos");
    }
    if (tipo == 1) {
      nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.reales");
    }
    if (tipo == 2) {
      nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.minimos");
    }
    if (tipo == 3) {
      nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.maximos");
    }
    if (tipo == 4) {
      nombre = messageResources.getResource("tipomedicionimportacion.ejecutado.programado");
    }
    if (tipo == 5) {
      nombre = messageResources.getResource("tipomedicionimportacion.metas.parciales");
    }
    if (tipo == 6) {
      nombre = messageResources.getResource("tipomedicionimportacion.metas.anuales");
    }
    return nombre;
  }
}
