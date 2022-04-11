package com.visiongc.framework.message.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public interface MessagePersistenceSession
    extends VgcPersistenceSession
{

    public abstract PaginaLista getMessages(int i, int j, String s, String s1, boolean flag, Map map);

    public abstract int setVisto(Long long1, Long long2, Byte byte1)
        throws Exception;
}