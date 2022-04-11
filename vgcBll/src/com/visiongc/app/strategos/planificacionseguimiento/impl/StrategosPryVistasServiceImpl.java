package com.visiongc.app.strategos.planificacionseguimiento.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryVistasService;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryVistasPersistenceSession;
import com.visiongc.commons.util.VgcMessageResources;

public class StrategosPryVistasServiceImpl
  extends StrategosServiceImpl implements StrategosPryVistasService
{
  private StrategosPryVistasPersistenceSession persistenceSession = null;
  
  public StrategosPryVistasServiceImpl(StrategosPryVistasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
}
