package com.visiongc.app.strategos.vistasdatos.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TipoAtributo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_ATRIBUTO_NOMBRE = 1;
  private static final byte TIPO_ATRIBUTO_ORGANIZACION = 2;
  private static final byte TIPO_ATRIBUTO_CLASE = 3;
  private static final byte TIPO_ATRIBUTO_UNIDADMEDIDA = 4;
  private static final byte TIPO_ATRIBUTO_FRECUENCIA = 5;
  private static final byte TIPO_ATRIBUTO_SERIE = 6;
  private Byte tipoAtributoId;
  private String nombre;
  private Boolean visible = Boolean.valueOf(true);
  private String ancho = "80";
  private Boolean agrupar = Boolean.valueOf(false);
  private String orden;
  
  public TipoAtributo() {}
  
  public Byte getTipoAtributoId() { return tipoAtributoId; }
  

  public void setTipoAtributoId(Byte tipoAtributoId)
  {
    this.tipoAtributoId = tipoAtributoId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Boolean getVisible()
  {
    return visible;
  }
  
  public void setVisible(Boolean visible)
  {
    this.visible = visible;
  }
  
  public String getAncho()
  {
    return ancho;
  }
  
  public void setAncho(String ancho)
  {
    this.ancho = ancho;
  }
  
  public Boolean getAgrupar()
  {
    return agrupar;
  }
  
  public void setAgrupar(Boolean agrupar)
  {
    this.agrupar = agrupar;
  }
  
  public String getOrden()
  {
    return orden;
  }
  
  public void setOrden(String orden)
  {
    this.orden = orden;
  }
  
  public static Byte getTipoAtributoNombre()
  {
    return Byte.valueOf((byte)1);
  }
  
  public static Byte getTipoAtributoOrganizacion()
  {
    return Byte.valueOf((byte)2);
  }
  
  public static Byte getTipoAtributoClase()
  {
    return Byte.valueOf((byte)3);
  }
  
  public static Byte getTipoAtributoUnidadMedida()
  {
    return Byte.valueOf((byte)4);
  }
  
  public static Byte getTipoAtributoFrecuencia()
  {
    return Byte.valueOf((byte)5);
  }
  
  public static Byte getTipoAtributoSerie()
  {
    return Byte.valueOf((byte)6);
  }
  
  public static List<TipoAtributo> getTiposAtributos()
  {
    return getTiposAtributos(null);
  }
  
  public static List<TipoAtributo> getTiposAtributos(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoAtributo> tiposAtributos = new ArrayList();
    
    TipoAtributo tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)1);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.nombre");
    tipoAtributo.orden = "1";
    tiposAtributos.add(tipoAtributo);
    
    tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)2);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.organizacion");
    tipoAtributo.orden = "2";
    tiposAtributos.add(tipoAtributo);
    
    tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)3);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.clase");
    tipoAtributo.orden = "3";
    tiposAtributos.add(tipoAtributo);
    
    tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)4);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.unidadmedida");
    tipoAtributo.orden = "4";
    tiposAtributos.add(tipoAtributo);
    
    tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)5);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.frecuencia");
    tipoAtributo.orden = "5";
    tiposAtributos.add(tipoAtributo);
    
    tipoAtributo = new TipoAtributo();
    tipoAtributo.tipoAtributoId = Byte.valueOf((byte)6);
    tipoAtributo.nombre = messageResources.getResource("tipoatributo.serie");
    tipoAtributo.orden = "6";
    tiposAtributos.add(tipoAtributo);
    
    return tiposAtributos;
  }
  
  public static String getNombre(Byte tipo)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo.byteValue() == 1) {
      nombre = messageResources.getResource("tipoatributo.nombre");
    }
    if (tipo.byteValue() == 2) {
      nombre = messageResources.getResource("tipoatributo.organizacion");
    }
    if (tipo.byteValue() == 3) {
      nombre = messageResources.getResource("tipoatributo.clase");
    }
    if (tipo.byteValue() == 4) {
      nombre = messageResources.getResource("tipoatributo.unidadmedida");
    }
    if (tipo.byteValue() == 5) {
      nombre = messageResources.getResource("tipoatributo.frecuencia");
    }
    if (tipo.byteValue() == 6) {
      nombre = messageResources.getResource("tipoatributo.serie");
    }
    return nombre;
  }
}
