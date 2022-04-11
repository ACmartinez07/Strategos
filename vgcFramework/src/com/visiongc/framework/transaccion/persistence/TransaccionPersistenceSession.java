package com.visiongc.framework.transaccion.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public interface TransaccionPersistenceSession
    extends VgcPersistenceSession
{

    public abstract PaginaLista getTransacciones(int i, int j, String s, String s1, boolean flag, Map map, boolean flag1);
}