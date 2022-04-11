package com.visiongc.commons.persistence;

import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.sql.Blob;

public interface VgcPersistenceSession
{

    public abstract void clear();

    public abstract Object load(Class class1, Long long1);

    public abstract void evictFromSession(Object obj);

    public abstract Object reload(Class class1, String s, Long long1)
        throws Exception;

    public abstract Object load(Class class1, Serializable serializable);

    public abstract void lock(Object obj);

    public abstract int insert(Object obj, Usuario usuario);

    public abstract int update(Object obj, Usuario usuario);

    public abstract boolean existsObject(Object obj, String as[], Object aobj[]);

    public abstract boolean existsObject(Object obj, String as[], Object aobj[], String as1[], Object aobj1[]);

    public abstract Blob readBlob(String s, String s1, String s2, String s3)
        throws Exception;

    public abstract int saveBlob(String s, String s1, byte abyte0[], String as[], Object aobj[])
        throws Exception;

    public abstract long getUniqueId();

    public abstract int delete(Object obj, Usuario usuario);

    public abstract void beginTransaction();

    public abstract void commitTransaction();

    public abstract void rollbackTransaction();

    public abstract boolean isTransactionActive();

    public abstract boolean lockForInsert(String s, Object aobj[]);

    public abstract boolean lockForUse(String s, Object aobj[]);

    public abstract boolean lockForUpdate(String s, Object obj, Object aobj[]);

    public abstract boolean lockForDelete(String s, Object obj);

    public abstract boolean unlockObject(String s, Object obj);

    public abstract boolean saveSoloLectura(Object obj, boolean flag, String as[], Object aobj[]);

    public abstract void registrarAuditoriaEvento(Object obj, Usuario usuario, byte byte0);

    public abstract Object getUltimaAuditoriaAtributo(String s, String s1, byte byte0);

    public abstract void close();

    public abstract String getGuid();
}