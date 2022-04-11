package com.visiongc.app.strategos;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.VgcService;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosService
  extends VgcService
{
  public abstract StrategosPersistenceSession getStrategosPersistenceSession();
  
  public abstract int deleteDependenciasGenericas(Long paramLong, Usuario paramUsuario);
}
