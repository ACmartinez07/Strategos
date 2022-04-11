package com.visiongc.framework.modulo.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.modulo.ModuloService;
import com.visiongc.framework.modulo.persistence.ModuloPersistenceSession;

public class ModuloServiceImpl extends VgcAbstractService
    implements ModuloService
{

    public ModuloServiceImpl(ModuloPersistenceSession inPersistenceSession, boolean persistenceOwned, FrameworkServiceFactory inServiceFactory, VgcMessageResources messageResources)
    {
        super(inPersistenceSession, persistenceOwned, inServiceFactory, messageResources);
        persistenceSession = null;
        persistenceSession = inPersistenceSession;
        persistenceSession = inPersistenceSession;
    }

    public Modulo getModulo(String id)
    {
        return persistenceSession.getModulo(id);
    }

    private ModuloPersistenceSession persistenceSession;
}