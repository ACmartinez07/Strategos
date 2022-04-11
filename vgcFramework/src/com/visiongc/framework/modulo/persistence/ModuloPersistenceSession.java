package com.visiongc.framework.modulo.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.framework.model.Modulo;

public interface ModuloPersistenceSession
    extends VgcPersistenceSession
{

    public abstract Modulo getModulo(String s);
}