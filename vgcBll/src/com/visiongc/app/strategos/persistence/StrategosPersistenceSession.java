package com.visiongc.app.strategos.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.ListaMap;

public abstract interface StrategosPersistenceSession
  extends VgcPersistenceSession
{
  public abstract ListaMap getDependenciasGenericas(Long paramLong);
}
