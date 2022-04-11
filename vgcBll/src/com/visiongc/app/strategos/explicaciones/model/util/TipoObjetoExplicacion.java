package com.visiongc.app.strategos.explicaciones.model.util;

import com.visiongc.commons.util.VgcMessageResources;

public class TipoObjetoExplicacion
{
  public static final byte TIPO_OBJETO_EXPLICACION_INDICADOR = 0;
  public static final byte TIPO_OBJETO_EXPLICACION_CELDA = 1;
  public static final byte TIPO_OBJETO_EXPLICACION_INICIATIVA = 2;
  public static final byte TIPO_OBJETO_EXPLICACION_ORGANIZACION = 3;
  public static final byte TIPO_OBJETO_EXPLICACION_PLAN = 4;
  public static final byte TIPO_OBJETO_EXPLICACION_OBJETIVO = 5;
  
  public TipoObjetoExplicacion() {}
  
  public static String getTipoExplicacion(byte tipoObjetoId) {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    String tipoExplicacion = "";
    
    if (tipoObjetoId == 0)
      tipoExplicacion = messageResources.getResource("tipoexplicacion.indicador");
    if (tipoObjetoId == 1)
      tipoExplicacion = messageResources.getResource("tipoexplicacion.celda");
    if (tipoObjetoId == 2)
      tipoExplicacion = messageResources.getResource("tipoexplicacion.iniciativa");
    if (tipoObjetoId == 3)
      tipoExplicacion = messageResources.getResource("tipoexplicacion.organizacion");
    if (tipoObjetoId == 4)
      tipoExplicacion = messageResources.getResource("tipoexplicacion.plan");
    if (tipoObjetoId == 5) {
      tipoExplicacion = messageResources.getResource("tipoexplicacion.objetivo");
    }
    return tipoExplicacion;
  }
}
