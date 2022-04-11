package com.visiongc.app.strategos.foros.model.util;

import com.visiongc.commons.util.VgcMessageResources;

public class TipoForo
{
  public static final byte TIPO_FORO = 0;
  public static final byte TIPO_TEMA = 1;
  public static final byte TIPO_COMENTARIO = 2;
  
  public TipoForo() {}
  
  public static String getTipoObjeto(byte objetoId) {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    String tipoObjeto = "";
    
    if (objetoId == 0) {
      tipoObjeto = messageResources.getResource("tipoforo.foro");
    }
    
    if (objetoId == 1) {
      tipoObjeto = messageResources.getResource("tipoforo.tema");
    }
    
    if (objetoId == 2) {
      tipoObjeto = messageResources.getResource("tipoforo.comentario");
    }
    
    return tipoObjeto;
  }
}
