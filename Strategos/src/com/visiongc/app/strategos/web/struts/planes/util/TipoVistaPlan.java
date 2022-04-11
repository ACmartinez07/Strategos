package com.visiongc.app.strategos.web.struts.planes.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoVistaPlan
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_VISTA_PLAN_DETALLADA = 1;
  private static final byte TIPO_VISTA_PLAN_RESUMEN = 2;
  private static final byte TIPO_VISTA_PLAN_EJECUTIVA = 3;
  private static final byte TIPO_VISTA_PLAN_ARBOL = 4;
  private byte tipoId;
  private String nombre;

  public byte getTipoId()
  {
    return this.tipoId;
  }

  public void setTipoId(byte tipoId) {
    this.tipoId = tipoId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public static Byte getTipoVistaPlanDetallada() {
    return new Byte((byte) 1);
  }

  public static Byte getTipoVistaPlanResumen() {
    return new Byte((byte) 2);
  }

  public static Byte getTipoVistaPlanEjecutiva() {
    return new Byte((byte) 3);
  }

  public static Byte getTipoVistaPlanArbol() {
    return new Byte((byte) 4);
  }

  public static List getTiposVistaPlan() {
    return getTiposVistaPlan(null);
  }

  public static List getTiposVistaPlan(VgcMessageResources messageResources) {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    }
    List tipos = new ArrayList();

    TipoVistaPlan tipo = new TipoVistaPlan();
    tipo.tipoId = 1;
    tipo.nombre = messageResources.getResource("plan.tipovista.detallada");
    tipos.add(tipo);

    tipo = new TipoVistaPlan();
    tipo.tipoId = 2;
    tipo.nombre = messageResources.getResource("plan.tipovista.resumen");
    tipos.add(tipo);

    tipo = new TipoVistaPlan();
    tipo.tipoId = 3;
    tipo.nombre = messageResources.getResource("plan.tipovista.ejecutiva");
    tipos.add(tipo);

    tipo = new TipoVistaPlan();
    tipo.tipoId = 4;
    tipo.nombre = messageResources.getResource("plan.tipovista.arbol");
    tipos.add(tipo);

    return tipos;
  }

  public static String getNombre(byte tipo) {
    String nombre = "";
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    if (tipo == 1) {
      nombre = messageResources.getResource("plan.tipovista.detallada");
    }
    if (tipo == 2) {
      nombre = messageResources.getResource("plan.tipovista.resumen");
    }
    if (tipo == 3) {
      nombre = messageResources.getResource("plan.tipovista.ejecutiva");
    }
    if (tipo == 4) {
      nombre = messageResources.getResource("plan.tipovista.arbol");
    }

    return nombre;
  }
}