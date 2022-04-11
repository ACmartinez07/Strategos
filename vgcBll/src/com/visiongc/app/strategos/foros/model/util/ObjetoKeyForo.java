package com.visiongc.app.strategos.foros.model.util;

import com.visiongc.commons.util.VgcMessageResources;

public class ObjetoKeyForo
{
  public static final byte OBJETO_KEY_FORO_INDICADOR = 0;
  public static final byte OBJETO_KEY_FORO_CELDA = 1;
  
  public ObjetoKeyForo() {}
  
  public static String getObjetoKeyForo(byte objetoId) {
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    String objetoKey = "";
    
    if (objetoId == 0) {
      objetoKey = messageResources.getResource("objetokeyforo.indicador");
    }
    
    if (objetoId == 1) {
      objetoKey = messageResources.getResource("objetokeyforo.celda");
    }
    
    return objetoKey;
  }
}
