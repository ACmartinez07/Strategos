package com.visiongc.commons.impl;

import com.visiongc.commons.persistence.VgcPersistenceSession;

// Referenced classes of package com.visiongc.commons.impl:
//            VgcServiceFactory, VgcAbstractService

public abstract class VgcDefaultServiceFactory
    implements VgcServiceFactory
{

    public VgcDefaultServiceFactory()
    {
    }

    public void closeService(VgcAbstractService service)
    {
        if(service.getPersistenceOwned())
            service.getVgcPersistenceSession().close();
    }
}