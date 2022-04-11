package com.visiongc.commons.persistence;


// Referenced classes of package com.visiongc.commons.persistence:
//            VgcPersistenceSession

public interface VgcPersistenceSessionFactory
{

    public abstract VgcPersistenceSession openPersistenceSession();

    public abstract VgcPersistenceSession openPersistenceSession(VgcPersistenceSession vgcpersistencesession);
}